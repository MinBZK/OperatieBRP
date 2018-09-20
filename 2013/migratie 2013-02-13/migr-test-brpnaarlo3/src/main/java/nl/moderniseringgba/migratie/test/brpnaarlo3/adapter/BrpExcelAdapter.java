/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.brpnaarlo3.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;

/**
 * Excel adapter.
 */
@Component
public final class BrpExcelAdapter {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private DataObjectConverterFactory converterFactory;

    /**
     * Lees een excel document in.
     * 
     * @param is
     *            inputstream
     * @return ingelezen personen
     * @throws IOException
     *             bij lees fouten
     */
    public List<Persoon> leesExcel(final InputStream is) throws IOException {
        LOG.info("leesExcel");
        final HSSFWorkbook workbook = new HSSFWorkbook(is);

        final List<DataObject> dtos = leesWorksheet(workbook.getSheetAt(0));

        final ConverterContext context = new ConverterContext();
        for (final DataObject dto : dtos) {
            LOG.info("Dataobject: {}", dto);
            if (dto.getData().size() > 0) {
                final DataObjectConverter converter = converterFactory.getConverter(dto);
                LOG.info("Using converter: {}", converter.getClass().getName());
                converter.convert(dto, context);
            } else {
                LOG.info("Skipping (no data)");
            }
        }

        final List<Persoon> personen = context.getPersonen();
        LOG.info("personen: {}", personen);
        return personen;
    }

    private List<DataObject> leesWorksheet(final HSSFSheet sheet) {
        final List<DataObject> result = new ArrayList<DataObject>();
        DataObject current = null;

        final int rows = sheet.getLastRowNum();
        for (int rowIndex = 0; rowIndex <= rows; rowIndex++) {
            final HSSFRow row = sheet.getRow(rowIndex);
            if (row == null) {
                continue;
            }

            final int cells = row.getLastCellNum();

            final boolean isHeaderRow = isHeaderCell(getCellStringValue(row, 0));
            if (isHeaderRow) {
                current = new DataObject();
                result.add(current);

                for (int cellIndex = 0; cellIndex < cells; cellIndex++) {
                    final String cellValue = getCellStringValue(row, cellIndex);
                    if (cellValue == null || "".equals(cellValue)) {
                        break;
                    }
                    current.header(cellValue);
                }
            } else if (current != null) {
                if (row.getPhysicalNumberOfCells() > 0) {
                    current.record();

                    for (int cellIndex = 0; cellIndex < cells; cellIndex++) {
                        current.data(getCellStringValue(row, cellIndex));
                    }

                }
            }
        }

        return result;

    }

    private String getCellStringValue(final HSSFRow row, final int i) {
        final HSSFCell cell = row.getCell(i);

        return cell == null ? null : cell.getStringCellValue();
    }

    private static boolean isHeaderCell(final String value) {
        return value != null && value.toLowerCase().startsWith("kern.");
    }

}
