/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.csv.processor;

import nl.bzk.brp.testdatageneratie.domain.kern.Persadres;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseBool;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.ift.CellProcessor;

/**
 * Csv proc pers adres.
 */
public class CsvProcPersAdres extends AbstractCsvProc<Persadres> {


    private static final String CSV_FILENAME = "kern.persadres.csv";

    // volgorde van velden in de csv bestand moet overeenkomen met de volgorde van de processoren.
    private static final CellProcessor[] PROCESSORS = new CellProcessor[] {
        new ParseInt(),     // id
        new ParseInt(),     // pers
        new Optional(new ParseShort()),     //srt
        new Optional(new ParseShort()),     //rdnwijz
        new Optional(new ParseShort()),     //aangadresh
        new Optional(new ParseInt()),     // dataanvadresh
        null,   // indentcodeadresseerbaarobject
        null,     // identcodenraand
        new Optional(new ParseShort()),     //gem
        null,   // nor
        null,   // afgekortenor
        null,   // gemdeel
        new Optional(new ParseInt()),     // huisnr
        null,   // huisletter
        null,   // huisnrtoevoeging
        null,   // postcode
        null,   // wplnaam
        null,   // loctovadres
        null,   // locoms
        null,   // bladresregel1
        null,   // bladresregel2
        null,   // bladresregel3
        null,   // bladresregel4
        null,   // bladresregel5
        null,   // bladresregel6
        new Optional(new ParseInt()),     // landgebied
        new Optional(new ParseBool()),     // indpersnietaangetroffenopadr
//        new Optional(new ParseInt()),     // datvertrekuitnederland

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
    public Class<Persadres> getLoadingClass() {
        return Persadres.class;
    }
}
