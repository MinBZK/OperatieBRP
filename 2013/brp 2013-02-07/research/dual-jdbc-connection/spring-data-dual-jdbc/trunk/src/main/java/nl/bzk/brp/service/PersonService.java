/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service;

/**
 *
 */

import nl.bzk.brp.model.Person;

import java.util.List;

/**
 * Declares methods used to obtain and modify person information.
 */
public interface PersonService {

    /**
     * Creates a new person.
     * @param created   The information of the created person.
     * @return  The created person.
     */
    public Person create(Person created);

    /**
     * Deletes a person.
     * @param personId  The id of the deleted person.
     * @return  The deleted person.
     * @throws PersonNotFoundException  if no person is found with the given id.
     */
    public Person delete(Long personId) throws PersonNotFoundException;

    /**
     * Finds all persons.
     * @return  A list of persons.
     */
    public List<Person> findAll();

    /**
     * Finds person by id.
     * @param id    The id of the wanted person.
     * @return  The found person. If no person is found, this method returns null.
     */
    public Person findById(Long id);


}