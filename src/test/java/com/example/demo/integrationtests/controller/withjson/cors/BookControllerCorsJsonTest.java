package com.example.demo.integrationtests.controller.withjson.cors;

import com.example.demo.configs.TestConfigs;
import com.example.demo.integrationtests.testcontainers.AbstractIntegrationTest;
import com.example.demo.integrationtests.vo.AccountCredentialsVO;
import com.example.demo.integrationtests.vo.BookVO;
import com.example.demo.integrationtests.vo.TokenVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookControllerCorsJsonTest extends AbstractIntegrationTest {


    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;

    private static BookVO book;

    @BeforeAll
    public static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        book = new BookVO();
    }

    @Test
    @Order(0)
    public void authorization() throws JsonMappingException, JsonProcessingException {
        AccountCredentialsVO user = new AccountCredentialsVO("leandro", "admin123");

        var accessToken = given()
                .basePath("/auth/signin")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(user)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(TokenVO.class)
                .getAccessToken();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
                .setBasePath("/api/book/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }

    @Test
    @Order(1)
    public void testCreateWithWrongOrigin() throws JsonMappingException, JsonProcessingException {
        mockBook();

        given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.WRONG_ORIGIN_EXAMPLE)
                .body(book)
                .when()
                .post()
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();
    }

    @Test
    @Order(2)
    public void testUpdateWithWrongOrigin() throws JsonMappingException, JsonProcessingException {
        //book.setLastName("Piquet Souto Maior");

        given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.WRONG_ORIGIN_EXAMPLE)
                .body(book)
                .when()
                .post()
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();
    }

    @Test
    @Order(3)
    public void testFindByIdWithWrongOrigin() throws JsonMappingException, JsonProcessingException {
        given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.WRONG_ORIGIN_EXAMPLE)
                .pathParam("id", 2L)
                .when()
                .get("{id}")
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();
    }

    @Test
    @Order(4)
    public void testDeleteWithWrongOrigin() throws JsonMappingException, JsonProcessingException {
        given().spec(specification)
                .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.WRONG_ORIGIN_EXAMPLE)
                .pathParam("id", 2L)
                .when()
                .delete("{id}")
                .then()
                .statusCode(403);
    }

    @Test
    @Order(5)
    public void testFindAllWithWrongOrigin() throws JsonMappingException, JsonProcessingException {
        mockBook();

        given().spec(specification)
                .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.WRONG_ORIGIN_EXAMPLE)
                .when()
                .get()
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();
    }

    private void mockBook() {
        book.setAuthor("Author X");
        book.setPrice(1D);
        book.setTitle("Title X");
        book.setLaunchDate(new Date());
    }

}