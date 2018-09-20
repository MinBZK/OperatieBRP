/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.csv.processor;

import nl.bzk.brp.testdatageneratie.domain.kern.HisOuderouderlijkgezag;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseBool;
import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.ParseLong;
import org.supercsv.cellprocessor.ift.CellProcessor;

/**
 * Csv proc his ouderouderlijkgezag.
 */
public class CsvProcHisOuderouderlijkgezag extends AbstractCsvProc<HisOuderouderlijkgezag> {

    private static final String CSV_FILENAME = "kern.his_ouderouderlijkgezag.csv";

    // volgorde van velden in de csv bestand moet overeenkomen met de volgorde van de processoren.
    private static final CellProcessor[] PROCESSORS = new CellProcessor[] {
            new ParseInt(),     // id
            new ParseInt(),     // betr

            new Optional(new ParseInt()),     // dataanvgel
            new Optional(new ParseInt()),     // dateindegel
            new Optional(new ParseDate("yyyyMMdd")), //tsreg
            new Optional(new ParseDate("yyyyMMdd")), // tsverval
            new Optional(new ParseLong()),  // actieinh
            new Optional(new ParseLong()),  // actieverval
            new Optional(new ParseLong()),  // actieaanpgel
            new Optional(new ParseBool()),   // indouderheeftgezag
            null, // nadereaandverval
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
    public Class<HisOuderouderlijkgezag> getLoadingClass() {
        return HisOuderouderlijkgezag.class;
    }
}
