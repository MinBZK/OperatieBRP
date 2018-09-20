/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.csv.processor;

import org.supercsv.cellprocessor.ift.CellProcessor;

/**
 * Abstract csv proc.
 *
 * @param <A> klasse
 */
public abstract class AbstractCsvProc<A> {

    /**
     * Get processor.
     *
     * @return the cell processor [ ]
     */
    public abstract CellProcessor[] getProcessor();

    /**
     * Geeft csv file name.
     *
     * @return csv file name
     */
    public abstract String getCsvFileName();

    /**
     * Geeft loading class.
     *
     * @return loading class
     */
    public abstract Class<A> getLoadingClass();
}
