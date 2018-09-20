/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.csv.processor;

import nl.bzk.brp.testdatageneratie.domain.kern.Pers;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseBool;
import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.ParseLong;
import org.supercsv.cellprocessor.ift.CellProcessor;

/**
 * Csv proc pers.
 */
public class CsvProcPers  extends AbstractCsvProc<Pers> {
    private static final String CSV_FILENAME = "kern.pers.csv";

    // volgorde van velden in de csv bestand moet overeenkomen met de volgorde van de processoren.
    private static final CellProcessor[] PROCESSORS = new CellProcessor[] {
            new ParseInt(),     // id
            new ParseShort(),     // srt
            new Optional(new ParseDate("yyyyMMdd")), //tslaatstewijz
            new Optional(new ParseInt()),   // datgeboorte
            new Optional(new ParseShort()),   // gemgeboorte
            null, //new Optional(new ParseInt()),   // wplnaamgeboorte
            null,   // blplaatsgeboorte
            null,   // blregiogeboorte
            new Optional(new ParseInt()),   // landgebiedgeboorte
            null,   // omslocgeboorte

            new Optional(new ParseInt()),   // datoverlijden
            new Optional(new ParseShort()),   // gemoverlijden
            null, // new Optional(new ParseInt()),   // wplnaamoverlijden
            null,   // blplaatsoverlijden
            null,   // blregiooverlijden
            new Optional(new ParseInt()),   // landgebiedoverlijden
            null,   // omslocoverlijden
            new Optional(new ParseShort()),   // naderebijhaard
            new Optional(new ParseInt()),   // datinschr
            new Optional(new ParseLong()),   // versienr
            new Optional(new ParseInt()),   // vorigebsn
            new Optional(new ParseInt()),   // volgendebsn
            new Optional(new ParseShort()),   // naamgebruik
            new Optional(new ParseBool()),   //indnaamgebruikafgeleid

            new Optional(new ParseShort()),   // predikaatnaamgebruik
            null,   // voornamennaamgebruik
            null,   // voorvoegselnaamgebruik
            null,   // scheidingstekennaamgebruik
            new Optional(new ParseShort()),   // adellijketitelnaamgebruik
            null,   // geslnaamstamnaamgebruik
            new Optional(new ParseShort()),   // gempk
            new Optional(new ParseBool()),   //indpkvollediggeconv

            new Optional(new ParseShort()), // srtmigratie
            new Optional(new ParseShort()), // rdnwijzmigratie
            new Optional(new ParseShort()), // aangmigratie
            new Optional(new ParseInt()), // landgebiedmigratie,
            null, // bladresregel1migratie
            null, // bladresregel2migratie
            null, // bladresregel3migratie
            null, // bladresregel4migratie
            null, // bladresregel5migratie
            null, // bladresregel6migratie

            new Optional(new ParseShort()),   // aandverblijfsr
            new Optional(new ParseInt()),   // datmededelingverblijfsr
            new Optional(new ParseInt()),   // datvoorzeindeverblijfstitel
            new Optional(new ParseBool()),   //inddeelneuverkiezingen

            new Optional(new ParseInt()),   // dataanlaanpdeelneuverkiezing
            new Optional(new ParseInt()),   // datvoorzeindeuitsleuverkiezi

            new Optional(new ParseShort()),   // predicaat
            null,   // voornamen
            null,   // voorvoegsel
            null,   // scheidingsteken
            new Optional(new ParseShort()),   // adellijketitel
            null,   // geslnaamstam

            new Optional(new ParseBool()),   //indnreeks
            new Optional(new ParseBool()),   //indafgeleid

            new Optional(new ParseShort()),   // bijhpartij

            new Optional(new ParseBool()),  // indonverwdocaanw

            new Optional(new ParseShort()),   // geslachtsaand
            new Optional(new ParseInt()),   // bsn
            new Optional(new ParseLong()),   // anr
            new Optional(new ParseBool()),   //induitslnlkiesr
            new Optional(new ParseInt()),   // datvoorzeindeuitslkiesr

            new Optional(new ParseShort()), // bijhaard

            new Optional(new ParseBool()), // indonverwbijhvoorstelnieting
            new Optional(new ParseBool()), // indonderzoeknaarnietopgenome

            new Optional(new ParseDate("yyyyMMdd")), // dattijdstempel
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
    public Class<Pers> getLoadingClass() {
        return Pers.class;
    }
}
