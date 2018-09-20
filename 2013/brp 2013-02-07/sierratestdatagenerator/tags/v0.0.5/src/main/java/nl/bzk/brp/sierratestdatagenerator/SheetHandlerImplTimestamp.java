/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.sierratestdatagenerator;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import jxl.Cell;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SheetHandlerImplTimestamp extends AbstractSheetHandlerImpl {


    private final static Logger log = LoggerFactory.getLogger(SheetHandlerImplTimestamp.class);

    public SheetHandlerImplTimestamp(final int sheetNum) {
        super(sheetNum);
    }

    @Override
    protected long printImpl(final Workbook workbook, final PrintWriter result) {

        long _count = 0;

        final Sheet sheet = workbook.getSheet(getSheetNum());

        int rowNum = 0;
        try {

            // log.debug("Generating sheet '"+sheet.getName()+"' voor gemeente '"
            // + gemeente.naam + "' , ambtenaar=" + ambtenaarCount + "/"
            // + Generator.AMBTENAAR_COUNT);

            result.println("--------------------------------------------------");
            result.println("--- Sheet: " + sheet.getName());
            result.println("--------------------------------------------------");

            result.println();
            result.println("begin;");
            result.println();

            String timestamp = null;
            final Cell cellTimestamp = sheet.getCell(1, 1);
            if (null != cellTimestamp) {
                DateCell dcell = (DateCell)cellTimestamp;
                Date d = dcell.getDate();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", new Locale("nl", "nl"));
                sdf.setTimeZone(TimeZone.getTimeZone("CEST"));
                timestamp = sdf.format(d);
                _count++;
            }

            result.println("DELETE FROM kern.ARTversion;");
            result.println("INSERT INTO kern.ARTversion (ID, FullVersion, ReleaseVersion, BuildTimestamp, ExcelTimestamp) VALUES (1, '" +
                    SierraTestdataGenerator.getFullVersion() + "','" +
                    SierraTestdataGenerator.getReleaseVersion() + "','" +
                    SierraTestdataGenerator.getTimestamp() + "'," +
                    ((null == timestamp) ? "null" : ("'" + timestamp + "'")) +
                    ");");

            result.println();
            result.println("commit;");
            result.println();

        } catch (final Exception e) {
            log.error("Sheet=" + sheet.getName() + ", row=" + rowNum + ":" + e.getMessage());
            throw new RuntimeException(e);
        }

        return _count;
    }

}
