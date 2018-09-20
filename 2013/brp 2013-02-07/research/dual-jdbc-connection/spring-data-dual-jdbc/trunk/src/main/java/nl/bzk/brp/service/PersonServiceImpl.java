/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

    package nl.bzk.brp.service;

import nl.bzk.brp.model.Person;
import nl.bzk.brp.repository.jpa.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("persoonService")
public class PersonServiceImpl implements PersonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonServiceImpl.class);

    @Resource
    private PersonRepository personRepository;

    public PersonServiceImpl() {
       // DataSourceContextHolder.setDataSourceType(AvailableDataSourceTypes.DEFAULT);
    }

    @Transactional
    @Override
    public Person create(Person created) {
        LOGGER.debug("Creating a new person with information: " + created);

        Person person = Person.getBuilder(created.getFirstName(), created.getLastName()).build();

        return personRepository.save(person);
    }

    @Transactional(rollbackFor = PersonNotFoundException.class)
    @Override
    public Person delete(Long personId) throws PersonNotFoundException {
        LOGGER.debug("Deleting person with id: " + personId);

        Person deleted = personRepository.findOne(personId);

        if (deleted == null) {
            LOGGER.debug("No person found with id: " + personId);
            throw new PersonNotFoundException();
        }

        personRepository.delete(deleted);
        return deleted;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Person> findAll() {
        LOGGER.debug("Finding all persons");
        return personRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Person findById(Long id) {
        LOGGER.debug("Finding person by id: " + id);
        return personRepository.findOne(id);
    }


    /**
     * This setter method should be used only by unit tests.
     * @param personRepository
     */
    protected void setPersonRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
}
