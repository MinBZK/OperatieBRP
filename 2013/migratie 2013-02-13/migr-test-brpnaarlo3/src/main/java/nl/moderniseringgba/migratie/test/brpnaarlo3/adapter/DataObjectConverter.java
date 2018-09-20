/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.brpnaarlo3.adapter;

/**
 * Converteer een data object naar een BRP entiteit.
 */
public interface DataObjectConverter {

    /**
     * Type entiteit dat deze converter kan converteren.
     * 
     * @return type
     */
    String getType();

    /**
     * Converteer.
     * 
     * @param dataObject
     *            data object
     * @param context
     *            context van de conversie
     */
    void convert(DataObject dataObject, ConverterContext context);

}
