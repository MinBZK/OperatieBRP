/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.csv.processor;

import nl.bzk.brp.testdatageneratie.domain.kern.HisPersgeslnaamcomp;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.ParseLong;
import org.supercsv.cellprocessor.ift.CellProcessor;

/**
 * Csv proc his persgeslachtsnaamcomp.
 */
public class CsvProcHisPersgeslachtsnaamcomp extends AbstractCsvProc<HisPersgeslnaamcomp> {

    private static final String CSV_FILENAME = "kern.his_persgeslnaamcomp.csv";

    // volgorde van velden in de csv bestand moet overeenkomen met de volgorde van de processoren.
    private static final CellProcessor[] PROCESSORS = new CellProcessor[] {
            new ParseInt(),     // id
            new ParseInt(),     // persgeslnaamcomp

            new Optional(new ParseInt()),     // dataanvgel
            new Optional(new ParseInt()),     // dateindegel
            new Optional(new ParseDate("yyyyMMdd")), //tsreg
            new Optional(new ParseDate("yyyyMMdd")), // tsverval
            new Optional(new ParseLong()),  // actieinh
            new Optional(new ParseLong()),  // actieverval
            new Optional(new ParseLong()),  // actieaanpgel
            null, // voorvoegsel
            null, // scheidingsteken
            null, // stam
            new Optional(new ParseShort()),  // predikaat
            new Optional(new ParseShort()),  // adellijketitel
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
    public Class<HisPersgeslnaamcomp> getLoadingClass() {
        return HisPersgeslnaamcomp.class;
    }
}
