/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.arttestdatagenerator;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import jxl.Cell;
import jxl.DateCell;
import jxl.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * .
 *
 */
public class SheetHandlerImplTimestamp extends AbstractSheetHandlerImpl {
    /** . */
    private static final Logger LOG = LoggerFactory.getLogger(SheetHandlerImplTimestamp.class);

    private int count = 0;
    private Sheet sheet = null;

    /**
     * .
     * @param parmSheet .
     */
    public SheetHandlerImplTimestamp(final Sheet parmSheet) {
        sheet = parmSheet;
    }

    @Override
    protected int printImpl(final PrintWriter result) {
        try {

            begin(result);
            String timestamp = null;
            final Cell cellTimestamp = sheet.getCell(1, 1);
            if (null != cellTimestamp) {
                DateCell dcell = (DateCell) cellTimestamp;
                Date d = dcell.getDate();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", new Locale("nl", "nl"));
                sdf.setTimeZone(TimeZone.getTimeZone("CEST"));
                timestamp = sdf.format(d);
                count++;
            }

            result.println("DELETE FROM test.ARTversion;");
            result.println("INSERT INTO test.ARTversion "
                + "(ID, FullVersion, ReleaseVersion, BuildTimestamp, ExcelTimestamp) VALUES (1, '"
                + TestdataGenerator.getFullVersion() + "','"
                + TestdataGenerator.getReleaseVersion() + "','"
                + TestdataGenerator.getTimestamp() + "',"
                + ((null == timestamp) ? "null" : ("'" + timestamp + "'"))
                + ");");

            commit(result);
        } catch (final Exception e) {
            LOG.error("Sheet=" + sheet.getName() + ", row=" + count + ":" + e.getMessage());
            throw new RuntimeException(e);
        }

        return count;
    }

    @Override
    protected Sheet getSheet() {
        return sheet;
    }

    @Override
    protected String getHandlerName() {
        return getClass().getSimpleName();
    }

    @Override
    protected int getCount() {
        return count;
    }

}
