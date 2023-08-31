package com.example.demo.unittests.mockito.services;

import com.example.demo.data.vo.v1.BookVO;
import com.example.demo.exceptions.RequiredObjectIsNullException;
import com.example.demo.model.Book;
import com.example.demo.repositories.BookRepository;
import com.example.demo.services.BookServices;
import com.example.demo.unittests.mapper.mocks.MockBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServicesTest {

	MockBook input; // os próprios mocks

	@InjectMocks // O objeto simula um Mock do service com acesso ao repository
	private BookServices service;

	@Mock
	BookRepository repository;

	@BeforeEach // o método abaixo setta os mocks. Ou seja, instancia os mocks antes somente declarados
	void setUp() throws Exception {
		input = new MockBook();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindAll() {
		List<Book> list = input.mockEntityList();

		when(repository.findAll()).thenReturn(list);

		var books = service.findAll();

		assertNotNull(books);
		assertEquals(14, books.size());

		for(var i = 0; i < books.size(); i++) {


			String author = "Author Test " + i;
			String title = "Title Test " + i;
			Double price = Double.parseDouble(Integer.toString(i));

			var book = books.get(i);

			assertNotNull(book);
			assertNotNull(book.getKey());
			assertNotNull(book.getLinks());
			assertTrue(book.toString().contains(""));
			assertEquals(author, book.getAuthor());
			assertEquals(title, book.getTitle());
			assertTrue(book.getLaunchDate().toString().contains(""));
			assertEquals(price, book.getPrice());
		}
	}

	@Test
	void testFindById() throws Exception {
		Book entity = input.mockEntity(1);
		entity.setId(1L);

		when(repository.findById(1L)).thenReturn(Optional.of(entity));

		var result = service.findById(1L);

		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains(""));
		assertEquals("Author Test 1", result.getAuthor());
		assertTrue(result.getLaunchDate().toString().contains(""));
		assertEquals(1L, result.getPrice());
		assertEquals("Title Test 1", result.getTitle());
	}

	@Test
	void testCreate() throws Exception {
		Book entity = input.mockEntity(1);
		Book persisted = entity;
		persisted.setId(1L);

		BookVO vo = input.mockVO(1);
		vo.setKey(1L);

		when(repository.save(entity)).thenReturn(persisted);

		var result = service.create(vo);

		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains(""));
		assertEquals("Author Test 1", result.getAuthor());
		assertTrue(result.getLaunchDate().toString().contains(""));
		assertEquals(1L, result.getPrice());
		assertEquals("Title Test 1", result.getTitle());
	}

	@Test
	void testCreateWithNullPerson() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> service.create(null));

		String expectedMessage = "Is not allowed to persist a null object!";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void testUpdate() throws Exception {
		Book entity = input.mockEntity(1);
		entity.setId(1L);

		Book persisted = entity;
		persisted.setId(1L);

		BookVO vo = input.mockVO(1);
		vo.setKey(1L);

		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		when(repository.save(entity)).thenReturn(persisted);

		var result = service.update(vo);

		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains(""));
		assertEquals("Author Test 1", result.getAuthor());
		assertTrue(result.getLaunchDate().toString().contains(""));
		assertEquals(1L, result.getPrice());
		assertEquals("Title Test 1", result.getTitle());
	}

	@Test
	void testUpdateWithNullPerson() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> service.update(null));

		String expectedMessage = "Is not allowed to persist a null object!";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void testDelete() throws Exception {
		Book entity = input.mockEntity(1);
		entity.setId(1L);

		when(repository.findById(1L)).thenReturn(Optional.of(entity));

		var result = service.findById(1L);
	}

}
