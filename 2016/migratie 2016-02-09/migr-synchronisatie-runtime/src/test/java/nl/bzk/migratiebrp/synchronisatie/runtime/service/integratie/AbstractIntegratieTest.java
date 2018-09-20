/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.integratie;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.migratiebrp.bericht.model.lo3.parser.Lo3PersoonslijstParser;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerLo3NaarBrpService;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Bericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3BerichtenBron;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import nl.bzk.migratiebrp.synchronisatie.dal.util.brpkern.DBUnitBrpUtil;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.integratie.AbstractSynchronisatieServiceIntegratieTest;
import nl.bzk.migratiebrp.util.excel.ExcelAdapter;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterImpl;
import nl.bzk.migratiebrp.util.excel.ExcelData;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.IDatabaseConnection;
import org.junit.Assert;
import org.junit.Before;

public abstract class AbstractIntegratieTest {

    @Inject
    private BrpDalService brpDalService;
    @Inject
    private ConverteerLo3NaarBrpService converteerLo3NaarBrpService;
    @Inject
    private DBUnitBrpUtil dbUnitUtil;

    @PersistenceContext(name = "syncDalEntityManagerFactory", unitName = "BrpEntities")
    private EntityManager em;

    private final ExcelAdapter excelAdapter = new ExcelAdapterImpl();

    private final Lo3PersoonslijstParser lo3PlParser = new Lo3PersoonslijstParser();

    @Before
    public void setupStamtabellenObvDbUnit() throws DatabaseUnitException, SQLException, JMSException {
        final IDatabaseConnection connection = dbUnitUtil.createConnection();
        dbUnitUtil.insert(
            connection,
            AbstractSynchronisatieServiceIntegratieTest.class,
            "/sql/data/brpStamgegevens-kern.xml",
            "/sql/data/brpStamgegevens-autaut.xml",
            "/sql/data/brpStamgegevens-conv.xml",
            "/sql/data/brpStamgegevens-verconv.xml");
        connection.close();
    }

    /**
     * Geef de waarde van brp dal service.
     *
     * @return brp dal service
     */
    protected BrpDalService getBrpDalService() {
        return brpDalService;
    }

    protected boolean komtAnummerActueelVoor(final long anummer) {
        return getBrpDalService().zoekPersoonOpAnummer(anummer) != null;
    }

    protected boolean komtPartijVoor(final int code) {
        final List<Partij> partijen = em.createQuery("from Partij where code = :code", Partij.class).setParameter("code", code).getResultList();
        return partijen != null && partijen.size() == 1;

    }

    protected BrpPersoonslijst leesBrpPersoonslijst(final long anummer) {
        return brpDalService.bevraagPersoonslijst(anummer);
    }

    protected void persisteerPersoonslijst(final Lo3Persoonslijst persoonslijst) {
        // Converteer lo3 persoonslijst naar brp persoonlijst
        Logging.initContext();
        final BrpPersoonslijst brpPersoonslijst = converteerLo3NaarBrpService.converteerLo3Persoonslijst(persoonslijst);
        Logging.destroyContext();

        final Lo3Bericht lo3Bericht = Lo3Bericht.newInstance(UUID.randomUUID().toString(), Lo3BerichtenBron.SYNCHRONISATIE, "testgeval", true);

        brpDalService.persisteerPersoonslijst(brpPersoonslijst, lo3Bericht);
    }

    protected void persisteerPersoonslijst(final BrpPersoonslijst persoonslijst) {
        final Lo3Bericht lo3Bericht = Lo3Bericht.newInstance(UUID.randomUUID().toString(), Lo3BerichtenBron.SYNCHRONISATIE, "testgeval", true);

        brpDalService.persisteerPersoonslijst(persoonslijst, lo3Bericht);
    }

    protected Lo3Persoonslijst leesPl(final String resource) throws Exception {
        final List<ExcelData> excelData;
        try (InputStream is = this.getClass().getResourceAsStream(resource)) {
            if (is == null) {
                Assert.fail("Resource '" + resource + "' kan niet worden gevonden.");
            }
            excelData = excelAdapter.leesExcelBestand(is);
        }

        return lo3PlParser.parse(excelData.get(0).getCategorieLijst());
    }

}
