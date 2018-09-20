/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.csv.processor;

import nl.bzk.brp.testdatageneratie.domain.kern.Relatie;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseBool;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.ift.CellProcessor;

/**
 * Csv proc relatie.
 */
public class CsvProcRelatie extends AbstractCsvProc<Relatie> {

    private static final String CSV_FILENAME = "kern.relatie.csv";

    // volgorde van velden in de csv bestand moet overeenkomen met de volgorde van de processoren.
    private static final CellProcessor[] PROCESSORS = new CellProcessor[] {
        new ParseInt(),     // id
        new ParseShort(),     // srt

        new Optional(new ParseInt()),     // dataanv
        new Optional(new ParseShort()),     // gemaanv
        null,     // wplnaamaanv
        null, // blplaatsaanv
        null, // blregioaanv
        new Optional(new ParseInt()),     // landgebiedaanv
        null, // omslocaanv

        new Optional(new ParseShort()),     // rdneinde
        new Optional(new ParseInt()),     // dateinde
        new Optional(new ParseShort()),     // gemeinde
        null,     // wplnaameinde
        null, // blplaatseinde
        null, // blregioeinde
        new Optional(new ParseInt()),     // landgebiedeinde
        null, // omsloceinde

        new Optional(new ParseInt()),     // datnaamskeuzeongeborenvrucht
        new Optional(new ParseInt()),     // daterkenningongeborenvrucht

        new Optional((new ParseBool())), // indasymmetrisch
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
    public Class<Relatie> getLoadingClass() {
        return Relatie.class;
    }
}
