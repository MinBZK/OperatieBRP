/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.adapter.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Excel adapter.
 */
public interface ExcelAdapter {

    /**
     * Lees een excel bestand in.
     * 
     * @param excelBestand
     *            excel bestand
     * @return excel data
     * @throws IOException
     *             bij lees fouten
     * @throws ExcelAdapterException
     *             bij excel fouten
     */
    List<ExcelData> leesExcelBestand(InputStream excelBestand) throws IOException, ExcelAdapterException;
}
