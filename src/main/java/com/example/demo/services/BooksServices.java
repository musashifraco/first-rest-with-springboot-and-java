package com.example.demo.services;

import com.example.demo.controllers.BooksController;
import com.example.demo.controllers.PersonController;
import com.example.demo.data.vo.v1.BooksVO;
import com.example.demo.exceptions.RequiredObjectIsNullException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.mapper.DozerMapper;
import com.example.demo.model.Books;
import com.example.demo.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;



@Service
public class BooksServices {
    final private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    BooksRepository repository;

    public List<BooksVO> findAll() {
        logger.info("Finding all books!");
        var books = DozerMapper.parseListObjects(repository.findAll(), BooksVO.class);
        books.stream().forEach(b -> {
            try {
                b.add(linkTo(methodOn(BooksController.class).findById(b.getKey())).withSelfRel());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        return books;
    }

    public BooksVO findById(Long id) {
        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No record founds for this ID"));

        var vo = DozerMapper.parseObject(entity, BooksVO.class);

        vo.add(linkTo(methodOn(BooksController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public BooksVO create(BooksVO book) throws Exception {
        if(book == null) throw new RequiredObjectIsNullException();

        logger.info("Book created!");

        var entity = DozerMapper.parseObject(book, Books.class);
        var vo = DozerMapper.parseObject(repository.save(entity), BooksVO.class);

        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public BooksVO update(BooksVO book) throws Exception {
        if(book == null) throw new RequiredObjectIsNullException();

        logger.info("Book updated!");

        var entity = repository.findById(book.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No record founds for this ID"));

        entity.setAuthor(book.getAuthor());
        entity.setLaunchDate(book.getLaunchDate());
        entity.setPrice(book.getPrice());
        entity.setTitle(book.getTitle());

        var vo = DozerMapper.parseObject(repository.save(entity), BooksVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public void delete(Long id) {
        logger.info("Person deleted!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No record founds for this ID"));

        repository.delete(entity);
    }
}
