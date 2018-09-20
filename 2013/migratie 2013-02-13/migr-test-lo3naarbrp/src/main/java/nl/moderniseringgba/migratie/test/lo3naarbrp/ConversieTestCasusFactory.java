/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.lo3naarbrp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.BerichtOnbekendException;
import nl.moderniseringgba.isc.esb.message.BerichtSyntaxException;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3BerichtFactory;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Inhoud;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Lg01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.parser.Lo3PersoonslijstParser;
import nl.moderniseringgba.migratie.adapter.excel.ExcelAdapter;
import nl.moderniseringgba.migratie.adapter.excel.ExcelAdapterException;
import nl.moderniseringgba.migratie.adapter.excel.ExcelAdapterImpl;
import nl.moderniseringgba.migratie.adapter.excel.ExcelData;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;
import nl.moderniseringgba.migratie.test.TestCasus;
import nl.moderniseringgba.migratie.test.TestCasusFactory;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

/**
 * Test casus factory: conversie lo3 naar brp.
 */
// CHECKSTYLE:OFF - Fan out complexity
public final class ConversieTestCasusFactory extends TestCasusFactory {
    // CHECKSTYLE:ON

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String INPUT_EXTENSIE_LO3_EXCEL = ".xls";
    private static final String INPUT_EXTENSIE_LO3_LG01 = ".lg01";
    private static final String INPUT_EXTENSIE_LO3_PL = ".txt";

    private final AutowireCapableBeanFactory autowireBeanFactory;

    private final ExcelAdapter excelAdapter = new ExcelAdapterImpl();
    private final Lo3PersoonslijstParser parser = new Lo3PersoonslijstParser();

    /**
     * Constructor.
     * 
     * @param autowireBeanFactory
     *            spring bean factory
     */
    protected ConversieTestCasusFactory(final AutowireCapableBeanFactory autowireBeanFactory) {
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
        } else if (input.getName().endsWith(INPUT_EXTENSIE_LO3_LG01)) {
            result = leesLg01(input);
        } else if (input.getName().endsWith(INPUT_EXTENSIE_LO3_PL)) {
            result = leesPl(input);
        } else {
            LOG.error("Bestand '{}' is van een onbekend type (extensie).");
            result = Collections.emptyList();
        }
        return result;
    }

    private List<TestCasus> leesExcel(final File file) throws IOException, ExcelAdapterException {

        // Lees excel
        final List<ExcelData> excelDatas = excelAdapter.leesExcelBestand(new FileInputStream(file));

        // Parsen input *ZONDER* syntax en precondite controles
        final List<Lo3Persoonslijst> lo3Persoonslijsten = new ArrayList<Lo3Persoonslijst>();
        for (final ExcelData excelData : excelDatas) {
            lo3Persoonslijsten.add(parser.parse(excelData.getCategorieLijst()));
        }

        final List<TestCasus> result = new ArrayList<TestCasus>();
        for (int i = 0; i < lo3Persoonslijsten.size(); i++) {

            final Lo3Persoonslijst lo3Persoonslijst = lo3Persoonslijsten.get(i);

            final TestCasus testCasus =
                    new ConversieTestCasus(getThema(), maakNaam(file.getName(), i), getOutputFolder(),
                            getExpectedFolder(), lo3Persoonslijst);
            autowireBeanFactory.autowireBean(testCasus);

            result.add(testCasus);
        }

        return result;
    }

    // CHECKSTYLE:OFF - Throws count - private gebruik
    private List<TestCasus> leesLg01(final File file) throws IOException, BerichtSyntaxException,
            BerichtOnbekendException, BerichtInhoudException {
        // CHECKSTYLE:ON
        final String berichtString = IOUtils.toString(new FileInputStream(file));

        final Lo3BerichtFactory bf = new Lo3BerichtFactory();
        final Lo3Bericht lo3Bericht = bf.getBericht(berichtString);

        final Lo3Persoonslijst lo3Persoonslijst = ((Lg01Bericht) lo3Bericht).getLo3Persoonslijst();

        final TestCasus testCasus =
                new ConversieTestCasus(getThema(), maakNaam(file.getName(), 0), getOutputFolder(),
                        getExpectedFolder(), lo3Persoonslijst);
        autowireBeanFactory.autowireBean(testCasus);

        return Collections.<TestCasus>singletonList(testCasus);
    }

    // CHECKSTYLE:OFF - Throws count - private gebruik
    private List<TestCasus> leesPl(final File file) throws IOException, BerichtSyntaxException,
            BerichtOnbekendException, BerichtInhoudException {
        // CHCEKSTYLE:ON
        final String berichtString = IOUtils.toString(new FileInputStream(file));

        final List<Lo3CategorieWaarde> categorieen = Lo3Inhoud.parseInhoud(berichtString);
        final Lo3Persoonslijst lo3Persoonslijst = new Lo3PersoonslijstParser().parse(categorieen);

        final TestCasus testCasus =
                new ConversieTestCasus(getThema(), maakNaam(file.getName(), 0), getOutputFolder(),
                        getExpectedFolder(), lo3Persoonslijst);
        autowireBeanFactory.autowireBean(testCasus);

        return Collections.<TestCasus>singletonList(testCasus);
    }
}
