/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.brpnaarlo3.adapter;

/**
 * Converteer een string naar een property waarde.
 * 
 * @param <T>
 *            class die de converter ondersteunt.
 * 
 */
public interface PropertyConverter<T> {

    /**
     * Geef het type property waar deze converter naar kan converteren.
     * 
     * @return type
     */
    Class<T> getType();

    /**
     * Converteer de gegevens string naar een property waarde.
     * 
     * @param value
     *            string waarde
     * @return 'typed' waarde
     */
    T convert(String value);

}
