package com.example.demo.services;

import com.example.demo.data.vo.v1.PersonVO;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.mapper.ModelMapperLib;
import com.example.demo.model.Person;
import com.example.demo.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonServices {
    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;

    public List<PersonVO> findAll() {
        logger.info("Finding all persons!");
        return ModelMapperLib.parseListObjects(repository.findAll(), PersonVO.class);
    }

    public PersonVO findById(Long id) {
        logger.info("Finding one person!");

        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No record founds for this ID"));

        return ModelMapperLib.parseObject(entity, PersonVO.class);
    }

    public PersonVO create(PersonVO person) {
        logger.info("PersonVO created!");

        var entity = ModelMapperLib.parseObject(person, Person.class);
        var vo = repository.save(entity);

        return ModelMapperLib.parseObject(vo, PersonVO.class);
    }

    public PersonVO update(PersonVO person) {
        logger.info("PersonVO updated!");

        var entity = repository.findById(person.getId())
                               .orElseThrow(() -> new ResourceNotFoundException("No record founds for this ID"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var vo = ModelMapperLib.parseObject(repository.save(entity), PersonVO.class);

        return vo;
    }

    public void delete(Long id) {
        logger.info("PersonVO deleted!");

        var entity = repository.findById(id)
                               .orElseThrow(() -> new ResourceNotFoundException("No record founds for this ID"));

        repository.delete(entity);
    }
}
