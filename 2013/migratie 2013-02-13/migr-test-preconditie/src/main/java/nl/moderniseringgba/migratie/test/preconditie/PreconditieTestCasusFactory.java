/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.preconditie;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.moderniseringgba.migratie.adapter.excel.ExcelAdapter;
import nl.moderniseringgba.migratie.adapter.excel.ExcelAdapterException;
import nl.moderniseringgba.migratie.adapter.excel.ExcelAdapterImpl;
import nl.moderniseringgba.migratie.adapter.excel.ExcelData;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;
import nl.moderniseringgba.migratie.test.TestCasus;
import nl.moderniseringgba.migratie.test.TestCasusFactory;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

/**
 * Test casus factory: precondities.
 */
public final class PreconditieTestCasusFactory extends TestCasusFactory {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String INPUT_EXTENSIE_LO3_EXCEL = ".xls";

    private final AutowireCapableBeanFactory autowireBeanFactory;

    private final ExcelAdapter excelAdapter = new ExcelAdapterImpl();

    /**
     * Constructor.
     * 
     * @param autowireBeanFactory
     *            spring bean factory
     */
    protected PreconditieTestCasusFactory(final AutowireCapableBeanFactory autowireBeanFactory) {
        this.autowireBeanFactory = autowireBeanFactory;
    }

    @Override
    public List<TestCasus> leesTestCasussen(final File input) throws Exception {
        LOG.info("leesTestCasussen(thema={}, input={})", getThema(), input.getName());
        if (!input.isFile()) {
            LOG.error("Bestand '{}' is geen bestand.", input.getName());
        }

        final List<TestCasus> result;
        if (input.getName().endsWith(INPUT_EXTENSIE_LO3_EXCEL)) {
            result = leesExcel(input);
        } else {
            LOG.error("Bestand '{}' is van een onbekend type (extensie).");
            result = Collections.emptyList();
        }
        return result;
    }

    private List<TestCasus> leesExcel(final File file) throws IOException, ExcelAdapterException {
        final List<ExcelData> excelDatas = excelAdapter.leesExcelBestand(new FileInputStream(file));

        final List<TestCasus> result = new ArrayList<TestCasus>();
        for (int i = 0; i < excelDatas.size(); i++) {
            final ExcelData excelData = excelDatas.get(i);

            final TestCasus testCasus =
                    new PreconditieTestCasus(getThema(), maakNaam(file.getName(), i), getOutputFolder(),
                            getExpectedFolder(), excelData.getCategorieLijst());
            autowireBeanFactory.autowireBean(testCasus);

            result.add(testCasus);
        }

        return result;
    }
}
