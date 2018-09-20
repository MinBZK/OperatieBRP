/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.metaregister.consistency;

import java.util.List;

import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.AttribuutType;
import nl.bzk.brp.metaregister.model.GeneriekElement;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;

/**
 * Dit is de (platform/generator onafhankelijke) interface voor een Data Access Object voor het BMR.
 * Onderstaande functies moeten een implementatie kennen willen de generatoren met behulp van de DAO hun werk doen.
 */
public interface BmrDao {

    /**
     * Retourneert een lijst van object typen uit het BMR.
     *
     * @return Lijst van object typen.
     */
    List<ObjectType> getObjectTypen();

    /**
     * Retourneert een lijst van attribuut typen uit het BMR.
     *
     * @return Lijst van object typen.
     */
    List<AttribuutType> getAttribuutTypen();

    /**
     * Retourneert alle groepen die bij een object type horen.
     *
     * @param objectType Het object type waarvoor groepen worden gezocht.
     * @return Lijst van groepen behorene bij objectType.
     */
    List<Groep> getGroepenVoorObjectType(final ObjectType objectType);

    /**
     * Retourneert een lijst van attributen behorende bij een groep.
     *
     * @param groep De groep waarvoor attributen worden gezocht.
     * @return Lijst van attributen behorende bij groep.
     */
    List<Attribuut> getAttributenVanGroep(final Groep groep);

    /**
     * Dit is een generieke functie om een element op te vragen. Dit element kan van elk soort zijn.
     *
     * @param id Id van het op te vragen element.
     * @param clazz Het java type van het element; attribuut, object type, tuple enz.
     * @param <T> Het type wat geretourneerd wordt, dit afhankelijk van het java type wat met de clazz param wordt
     * aangegeven.
     * @return Een subinstantie van een element; dus attribuut, object type, tuple enz.
     */
    <T extends GeneriekElement> T getElement(final Integer id, final Class<T> clazz);

}
