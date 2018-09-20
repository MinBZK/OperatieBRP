/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.brpnaarlo3;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;
import nl.moderniseringgba.migratie.synchronisatie.util.brpkern.DBUnitBrpUtil;
import nl.moderniseringgba.migratie.test.TestCasus;
import nl.moderniseringgba.migratie.test.TestCasusFactory;
import nl.moderniseringgba.migratie.test.brpnaarlo3.adapter.BrpExcelAdapter;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.FilteredDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Conversie test casus factory.
 */
// CHECKSTYLE:OFF - Fanout complexity
public final class ConversieTestCasusFactory extends TestCasusFactory {
    // CHECKSTYLE:ON

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String INPUT_EXTENSIE_BRP_EXCEL = ".brp.xls";
    private static final String INPUT_EXTENSIE_BRP_DBUNIT = ".xml";

    private final AutowireCapableBeanFactory autowireBeanFactory;

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
        if (input.getName().endsWith(INPUT_EXTENSIE_BRP_EXCEL)) {
            result = leesBrpExcel(input);
        } else if (input.getName().endsWith(INPUT_EXTENSIE_BRP_DBUNIT)) {
            result = leesDbXml(input);
        } else {
            LOG.error("Bestand '{}' is van een onbekend type (extensie).");
            result = Collections.emptyList();
        }
        return result;
    }

    // CHECKSTYLE:OFF - Meedere exceptions
    private List<TestCasus> leesDbXml(final File file) throws IOException, DatabaseUnitException, SQLException {
        // CHECKSTYLE:ON
        final DBUnitBrpUtil dbUnitUtil = autowireBeanFactory.getBean(DBUnitBrpUtil.class);

        final IDataSet dataSet = dbUnitUtil.readDataSet(file);

        // CHECKSTYLE:OFF
        final String[] tablesnames =
                new String[] { "kern.actie", "kern.doc", "kern.bron", "kern.multirealiteitregel", "kern.onderzoek",
                        "kern.pers", "kern.persadres", "kern.persgeslnaamcomp", "kern.persindicatie",
                        "kern.persnation", "kern.persreisdoc", "kern.persverificatie", "kern.persvoornaam",
                        "kern.relatie", "kern.betr", "kern.his_betrouder", "kern.his_betrouderlijkgezag",
                        "kern.his_doc", "kern.his_gem", "kern.his_multirealiteitregel", "kern.his_onderzoek",
                        "kern.his_partij", "kern.his_persaanschr", "kern.his_persadres", "kern.his_persbijhgem",
                        "kern.his_persbijhverantwoordelijk", "kern.his_perseuverkiezingen", "kern.his_persgeboorte",
                        "kern.his_persgeslachtsaand", "kern.his_persgeslnaamcomp", "kern.his_persids",
                        "kern.his_persimmigratie", "kern.his_persindicatie", "kern.his_persinschr",
                        "kern.his_persnation", "kern.his_persopschorting", "kern.his_persoverlijden",
                        "kern.his_perspk", "kern.his_persreisdoc", "kern.his_perssamengesteldenaam",
                        "kern.his_persuitslnlkiesr", "kern.his_persverblijfsr", "kern.his_persverificatie",
                        "kern.his_persvoornaam", "kern.his_relatie", };
        // CHECKSTYLE:ON

        final FilteredDataSet orderedDataSet = new FilteredDataSet(tablesnames, dataSet);
        dbUnitUtil.insert(orderedDataSet);

        final ITable persoonTable = dataSet.getTable("kern.pers");

        // Per PL in de dataset een TestCasusDb maken

        final List<String> anummers = new ArrayList<String>();
        for (int persoonIndex = 0; persoonIndex < persoonTable.getRowCount(); persoonIndex++) {
            final Object administratienummer = persoonTable.getValue(persoonIndex, "anr");
            final Object soortPersoon = persoonTable.getValue(persoonIndex, "srt");

            if (administratienummer != null && !"".equals(administratienummer)
                    || "1".equals(String.valueOf(soortPersoon))) {
                if (!anummers.contains(String.valueOf(administratienummer))) {
                    anummers.add(String.valueOf(administratienummer));
                }
            }
        }

        final List<TestCasus> result = new ArrayList<TestCasus>();
        for (int i = 0; i < anummers.size(); i++) {
            final String administratienummer = anummers.get(i);
            LOG.debug("Maak casus met anr: {}", administratienummer);

            final TestCasus testCasus =
                    new ConversieTestCasus(getThema(), maakNaam(file.getName(), i), getOutputFolder(),
                            getExpectedFolder(), Long.valueOf(administratienummer));
            autowireBeanFactory.autowireBean(testCasus);
            result.add(testCasus);
        }

        return result;
    }

    private List<TestCasus> leesBrpExcel(final File file) {
        final PlatformTransactionManager transactionManager =
                autowireBeanFactory.getBean(PlatformTransactionManager.class);

        final TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        // CHECKSTYLE:OFF - Anonymous inner class length
        final TransactionCallback<List<TestCasus>> execution = new TransactionCallback<List<TestCasus>>() {
            // CHECKSTYLE:ON
            @PersistenceContext
            private EntityManager em;

            @Inject
            private BrpExcelAdapter excelAdapter;

            @Override
            public List<TestCasus> doInTransaction(final TransactionStatus status) {

                try {
                    final List<Persoon> personen = excelAdapter.leesExcel(new FileInputStream(file));
                    final List<TestCasus> result = new ArrayList<TestCasus>();
                    for (int i = 0; i < personen.size(); i++) {
                        final Persoon persoon = personen.get(i);
                        em.persist(persoon);

                        if (persoon.getAdministratienummer() != null && !"".equals(persoon.getAdministratienummer())) {

                            final TestCasus testCasus =
                                    new ConversieTestCasus(getThema(), maakNaam(file.getName(), i),
                                            getOutputFolder(), getExpectedFolder(), persoon.getAdministratienummer()
                                                    .longValue());

                            autowireBeanFactory.autowireBean(testCasus);
                            result.add(testCasus);
                        }
                    }

                    return result;
                } catch (final IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        autowireBeanFactory.autowireBean(execution);

        return transactionTemplate.execute(execution);
    }
}
