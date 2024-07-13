package com.example.demo.integrationtests.repositories;

import com.example.demo.integrationtests.testcontainers.AbstractIntegrationTest;
import com.example.demo.model.Person;
import com.example.demo.repositories.PersonRepository;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    public PersonRepository repository;

    private static Person person;

    @BeforeAll
    public static void setup() {
        person = new Person();
    }

    @Test
    @Order(1)
    public void testFindByName() throws JsonMappingException, JsonProcessingException {
        Pageable pageable = PageRequest.of(0, 6, Sort.by(Sort.Direction.ASC, "firstName"));
        person = repository.findPersonsByName("ayr", pageable).getContent().get(0);

        assertNotNull(person.getId());
        assertNotNull(person.getFirstName());
        assertNotNull(person.getLastName());
        assertNotNull(person.getAddress());
        assertNotNull(person.getGender());
        assertTrue(person.getEnabled());

        assertEquals(694, person.getId());
        assertEquals("Sayre", person.getFirstName());
        assertEquals("Lulham", person.getLastName());
        assertEquals("525 Gulseth Alley", person.getAddress());
        assertEquals("Female", person.getGender());
    }

    @Test
    @Order(2)
    public void testDisablePerson() throws JsonMappingException, JsonProcessingException {

        repository.disablePerson(person.getId());

        Pageable pageable = PageRequest.of(0, 6, Sort.by(Sort.Direction.ASC, "firstName"));
        person = repository.findPersonsByName("ayr", pageable).getContent().get(0);

        assertNotNull(person.getId());
        assertNotNull(person.getFirstName());
        assertNotNull(person.getLastName());
        assertNotNull(person.getAddress());
        assertNotNull(person.getGender());

        assertEquals(694, person.getId());
        assertEquals(694, person.getId());
        assertEquals("Sayre", person.getFirstName());
        assertEquals("Lulham", person.getLastName());
        assertEquals("525 Gulseth Alley", person.getAddress());
        assertFalse(person.getEnabled());
    }
}

