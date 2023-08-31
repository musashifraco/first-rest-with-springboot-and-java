package com.example.demo.unittests.mapper;

import com.example.demo.data.vo.v1.BookVO;
import com.example.demo.mapper.DozerMapper;
import com.example.demo.model.Book;
import com.example.demo.unittests.mapper.mocks.MockBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DozerConverterTestBook {
    MockBook inputBookVO;

    @BeforeEach
    public void setUp() {
        inputBookVO = new MockBook();

    }

    @Test
    public void parseEntityToVOTest() {
        BookVO outputBookVO = DozerMapper.parseObject(inputBookVO.mockEntity(), BookVO.class);
        assertEquals(Long.valueOf(0L), outputBookVO.getKey());
        assertEquals("Author Test 0", outputBookVO.getAuthor());
        assertTrue(outputBookVO.getLaunchDate().toString().contains(""));
        assertEquals(0L, outputBookVO.getPrice());
        assertEquals("Title Test 0", outputBookVO.getTitle());
    }

    @Test
    public void parseEntityListToVOListTest() {
        List<BookVO> outputListBookVO = DozerMapper.parseListObjects(inputBookVO.mockEntityList(), BookVO.class);

        BookVO outputZeroBookVO = outputListBookVO.get(0);
        assertEquals(Long.valueOf(0L), outputZeroBookVO.getKey());
        assertEquals("Author Test 0", outputZeroBookVO.getAuthor());
        assertTrue(outputZeroBookVO.getLaunchDate().toString().contains(""));
        assertEquals(0L, outputZeroBookVO.getPrice());
        assertEquals("Title Test 0", outputZeroBookVO.getTitle());

        BookVO outputSevenBookVO = outputListBookVO.get(7);
        assertEquals(Long.valueOf(7L), outputSevenBookVO.getKey());
        assertEquals("Author Test 7", outputSevenBookVO.getAuthor());
        assertTrue(outputSevenBookVO.getLaunchDate().toString().contains(""));
        assertEquals(7L, outputSevenBookVO.getPrice());
        assertEquals("Title Test 7", outputSevenBookVO.getTitle());

        BookVO outputTwelveBookVO = outputListBookVO.get(12);
        assertEquals(Long.valueOf(12L), outputTwelveBookVO.getKey());
        assertEquals("Author Test 12", outputTwelveBookVO.getAuthor());
        assertTrue(outputTwelveBookVO.getLaunchDate().toString().contains(""));
        assertEquals(12L, outputTwelveBookVO.getPrice());
        assertEquals("Title Test 12", outputTwelveBookVO.getTitle());
    }

    @Test
    public void parseVOToEntityTest() {
        Book outputBookEntity = DozerMapper.parseObject(inputBookVO.mockVO(), Book.class);
        assertEquals(Long.valueOf(0L), outputBookEntity.getId());
        assertEquals("Author Test 0", outputBookEntity.getAuthor());
        assertTrue(outputBookEntity.getLaunchDate().toString().contains(""));
        assertEquals(0L, outputBookEntity.getPrice());
        assertEquals("Title Test 0", outputBookEntity.getTitle());
    }

    @Test
    public void parserVOListToEntityListTest() {
        List<Book> outputListBook = DozerMapper.parseListObjects(inputBookVO.mockVOList(), Book.class);

        Book outputZeroBookVO = outputListBook.get(0);
        assertEquals(Long.valueOf(0L), outputZeroBookVO.getId());
        assertEquals("Author Test 0", outputZeroBookVO.getAuthor());
        assertTrue(outputZeroBookVO.getLaunchDate().toString().contains(""));
        assertEquals(0L, outputZeroBookVO.getPrice());
        assertEquals("Title Test 0", outputZeroBookVO.getTitle());

        Book outputSevenBookVO = outputListBook.get(7);
        assertEquals(Long.valueOf(7L), outputSevenBookVO.getId());
        assertEquals("Author Test 7", outputSevenBookVO.getAuthor());
        assertTrue(outputSevenBookVO.getLaunchDate().toString().contains(""));
        assertEquals(7L, outputSevenBookVO.getPrice());
        assertEquals("Title Test 7", outputSevenBookVO.getTitle());

        Book outputTwelveBookVO = outputListBook.get(12);
        assertEquals(Long.valueOf(12L), outputTwelveBookVO.getId());
        assertEquals("Author Test 12", outputTwelveBookVO.getAuthor());
        assertTrue(outputTwelveBookVO.getLaunchDate().toString().contains(""));
        assertEquals(12L, outputTwelveBookVO.getPrice());
        assertEquals("Title Test 12", outputTwelveBookVO.getTitle());
    }
}
