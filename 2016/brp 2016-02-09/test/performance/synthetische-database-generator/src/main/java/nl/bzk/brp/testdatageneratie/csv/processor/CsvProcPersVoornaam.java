/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.csv.processor;

import nl.bzk.brp.testdatageneratie.domain.kern.Persvoornaam;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.ift.CellProcessor;

/**
 * Csv proc pers voornaam.
 */
public class CsvProcPersVoornaam extends AbstractCsvProc<Persvoornaam> {

    private static final String CSV_FILENAME = "kern.persvoornaam.csv";

    // volgorde van velden in de csv bestand moet overeenkomen met de volgorde van de processoren.
    private static final CellProcessor[] PROCESSORS = new CellProcessor[] {
        null,   // naam
        new ParseInt(),     // id
        new ParseInt(),     // pers
        new Optional(new ParseInt()),     // volgnr

    };

    @Override
    public CellProcessor[] getProcessor() {
        return PROCESSORS;
    }

    @Override
    public String getCsvFileName() {
        return CSV_FILENAME;
    }

    @Override
    public Class<Persvoornaam> getLoadingClass() {
        return Persvoornaam.class;
    }
}
