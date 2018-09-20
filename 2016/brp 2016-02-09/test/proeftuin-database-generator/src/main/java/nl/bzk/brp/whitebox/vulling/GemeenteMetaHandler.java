/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.whitebox.vulling;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import nl.bzk.brp.whitebox.vulling.model.Gemeente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GemeenteMetaHandler extends AbstractSheetHandlerImpl implements SheetHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final List<Gemeente> gemeenten;

    public GemeenteMetaHandler(final int sheet, final List<Gemeente> gemeenten) {
	super(sheet);
	this.gemeenten = gemeenten;
    }

    @Override
    protected long printImpl(final Workbook workbook, final PrintWriter printWriter) {

	long count = 0;

	final Sheet sheet = workbook.getSheet(getSheetNum());

	final List<Long> doneCode = new ArrayList<Long>();

	for (int rowNum = 0; rowNum < sheet.getRows(); rowNum++) {

	    final Cell[] row = sheet.getRow(rowNum);

	    if (row[2].getContents().length() > 0) {
		try {
		    final Long code = Long.valueOf(row[2].getContents());

		    final String gemeenteNaam = row[0].getContents();

		    if (!doneCode.contains(code) && gemeenteNaam != null && gemeenteNaam.length() > 0) {
			final Gemeente gemeente = new Gemeente(gemeenteNaam, code);
			gemeenten.add(gemeente);
			log.info("Found gemeente " + gemeente);
			doneCode.add(code);
			count++;
		    }
		} catch (final NumberFormatException e) {

		}

	    }

	}

	return count;
    }
}
