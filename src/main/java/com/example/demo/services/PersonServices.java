package com.example.demo.services;

import com.example.demo.exceptions.ResourceNotFoundException;
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

    public List<Person> findAll() {
        logger.info("Finding all persons!");
        return repository.findAll();
    }

    public Person findById(Long id) {
        logger.info("Finding one person!");

        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No record founds for this ID"));
    }

    public Person create(Person person) {
        logger.info("Person created!");
        return repository.save(person);
    }

    public Person update(Person person) {
        logger.info("Person updated!");

        var entity = repository.findById(person.getId())
                               .orElseThrow(() -> new ResourceNotFoundException("No record founds for this ID"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return repository.save(entity);
    }

    public void delete(Long id) {
        logger.info("Person deleted!");

        var entity = repository.findById(id)
                               .orElseThrow(() -> new ResourceNotFoundException("No record founds for this ID"));

        repository.delete(entity);
    }
}
