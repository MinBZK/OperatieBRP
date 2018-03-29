/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.brpnaarlo3.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
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
     * @param is inputstream
     * @return ingelezen personen
     * @throws IOException bij lees fouten
     */
    public List<Persoon> leesExcel(final InputStream is) throws IOException {
        LOG.info("leesExcel");
        final HSSFWorkbook workbook = new HSSFWorkbook(is);

        final List<DataObject> dtos = leesWorksheet(workbook.getSheetAt(0));

        final ConverterContext context = new ConverterContext();
        for (final DataObject dto : dtos) {
            LOG.debug("Dataobject: {}", dto);
            if (dto.getData().size() > 0) {
                final DataObjectConverter converter = converterFactory.getConverter(dto);
                LOG.debug("Using converter: {}", converter.getClass().getName());
                converter.convert(dto, context);
            } else {
                LOG.debug("Skipping (no data)");
            }
        }

        final List<Persoon> personen = context.getPersonen();
        LOG.debug("personen: {}", personen);
        return personen;
    }

    private List<DataObject> leesWorksheet(final HSSFSheet sheet) {
        final List<DataObject> result = new ArrayList<>();
        DataObject current = null;

        final int rows = sheet.getLastRowNum();
        for (int rowIndex = 0; rowIndex <= rows; rowIndex++) {
            final HSSFRow row = sheet.getRow(rowIndex);
            if (row == null) {
                continue;
            }

            if (isHeaderCell(getCellStringValue(row, 0))) {
                current = new DataObject();
                result.add(current);

                final int cells = row.getLastCellNum();
                for (int cellIndex = 0; cellIndex < cells; cellIndex++) {
                    final String cellValue = getCellStringValue(row, cellIndex);
                    if (cellValue == null || cellValue.isEmpty()) {
                        break;
                    }
                    current.header(cellValue);
                }
            } else if (current != null && row.getPhysicalNumberOfCells() > 0) {
                final List<String> data = new ArrayList<>();
                boolean heeftData = false;

                final int cells = current.getHeaders().size();
                for (int cellIndex = 0; cellIndex < cells; cellIndex++) {
                    final String dataValue = getCellStringValue(row, cellIndex);
                    data.add(dataValue);
                    if (!heeftData) {
                        heeftData = dataValue != null && !dataValue.isEmpty();
                    }
                }
                if (heeftData) {
                    current.data(data);
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
        boolean result = false;
        if (value != null) {
            final String valueLowerCase = value.toLowerCase();
            if (valueLowerCase.startsWith("kern.") || valueLowerCase.startsWith("ist.")) {
                result = true;
            }
        }
        return result;
    }
}
