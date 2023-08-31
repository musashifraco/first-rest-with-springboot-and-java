package com.example.demo.unittests.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import com.example.demo.data.vo.v1.PersonVO;
import com.example.demo.mapper.DozerMapper;
import com.example.demo.model.Person;
import com.example.demo.unittests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DozerConverterTest {
    MockPerson inputPersonVO;

    @BeforeEach
    public void setUp() {
        inputPersonVO = new MockPerson();
    }

    @Test
    public void parseEntityToVOTest() {
        PersonVO outputPersonVO = DozerMapper.parseObject(inputPersonVO.mockEntity(), PersonVO.class);
        assertEquals(Long.valueOf(0L), outputPersonVO.getKey());
        assertEquals("First Name Test0", outputPersonVO.getFirstName());
        assertEquals("Last Name Test0", outputPersonVO.getLastName());
        assertEquals("Addres Test0", outputPersonVO.getAddress());
        assertEquals("Male", outputPersonVO.getGender());
    }

    @Test
    public void parseEntityListToVOListTest() {
        List<PersonVO> outputListPersonVO = DozerMapper.parseListObjects(inputPersonVO.mockEntityList(), PersonVO.class);

        PersonVO outputZeroPersonVO = outputListPersonVO.get(0);
        
        assertEquals(Long.valueOf(0L), outputZeroPersonVO.getKey());
        assertEquals("First Name Test0", outputZeroPersonVO.getFirstName());
        assertEquals("Last Name Test0", outputZeroPersonVO.getLastName());
        assertEquals("Addres Test0", outputZeroPersonVO.getAddress());
        assertEquals("Male", outputZeroPersonVO.getGender());
        
        PersonVO outputSevenPersonVO = outputListPersonVO.get(7);
        
        assertEquals(Long.valueOf(7L), outputSevenPersonVO.getKey());
        assertEquals("First Name Test7", outputSevenPersonVO.getFirstName());
        assertEquals("Last Name Test7", outputSevenPersonVO.getLastName());
        assertEquals("Addres Test7", outputSevenPersonVO.getAddress());
        assertEquals("Female", outputSevenPersonVO.getGender());

        PersonVO outputTwelvePersonVO = outputListPersonVO.get(12);
        
        assertEquals(Long.valueOf(12L), outputTwelvePersonVO.getKey());
        assertEquals("First Name Test12", outputTwelvePersonVO.getFirstName());
        assertEquals("Last Name Test12", outputTwelvePersonVO.getLastName());
        assertEquals("Addres Test12", outputTwelvePersonVO.getAddress());
        assertEquals("Male", outputTwelvePersonVO.getGender());
    }

    @Test
    public void parseVOToEntityTest() {
        Person outputPersonEntity = DozerMapper.parseObject(inputPersonVO.mockVO(), Person.class);
        assertEquals(Long.valueOf(0L), outputPersonEntity.getId());
        assertEquals("First Name Test0", outputPersonEntity.getFirstName());
        assertEquals("Last Name Test0", outputPersonEntity.getLastName());
        assertEquals("Addres Test0", outputPersonEntity.getAddress());
        assertEquals("Male", outputPersonEntity.getGender());
    }

    @Test
    public void parserVOListToEntityListTest() {
        List<Person> outputListPersonEntity = DozerMapper.parseListObjects(inputPersonVO.mockVOList(), Person.class);
        Person outputZeroPersonEntity = outputListPersonEntity.get(0);
        
        assertEquals(Long.valueOf(0L), outputZeroPersonEntity.getId());
        assertEquals("First Name Test0", outputZeroPersonEntity.getFirstName());
        assertEquals("Last Name Test0", outputZeroPersonEntity.getLastName());
        assertEquals("Addres Test0", outputZeroPersonEntity.getAddress());
        assertEquals("Male", outputZeroPersonEntity.getGender());
        
        Person outputSevenPersonEntity = outputListPersonEntity.get(7);
        
        assertEquals(Long.valueOf(7L), outputSevenPersonEntity.getId());
        assertEquals("First Name Test7", outputSevenPersonEntity.getFirstName());
        assertEquals("Last Name Test7", outputSevenPersonEntity.getLastName());
        assertEquals("Addres Test7", outputSevenPersonEntity.getAddress());
        assertEquals("Female", outputSevenPersonEntity.getGender());
        
        Person outputTwelvePersonEntity = outputListPersonEntity.get(12);
        
        assertEquals(Long.valueOf(12L), outputTwelvePersonEntity.getId());
        assertEquals("First Name Test12", outputTwelvePersonEntity.getFirstName());
        assertEquals("Last Name Test12", outputTwelvePersonEntity.getLastName());
        assertEquals("Addres Test12", outputTwelvePersonEntity.getAddress());
        assertEquals("Male", outputTwelvePersonEntity.getGender());
    }
}
