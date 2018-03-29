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
import javax.inject.Named;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Bericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Lo3BerichtenBron;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.StatusLeveringAdministratieveHandeling;
import nl.bzk.algemeenbrp.test.dal.AbstractDBUnitUtil;
import nl.bzk.migratiebrp.bericht.model.lo3.parser.Lo3PersoonslijstParser;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerLo3NaarBrpService;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpPersoonslijstService;
import nl.bzk.migratiebrp.util.excel.ExcelAdapter;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterImpl;
import nl.bzk.migratiebrp.util.excel.ExcelData;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.IDatabaseConnection;
import org.junit.Assert;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

public abstract class AbstractIT {

    @Inject
    private BrpPersoonslijstService brpPersoonslijstService;

    @Inject
    private ConverteerLo3NaarBrpService converteerLo3NaarBrpService;

    @Inject
    private AbstractDBUnitUtil dbUnitUtil;

    @Inject
    @Named("syncDalTransactionManager")
    private PlatformTransactionManager transactionManager;

    @PersistenceContext(name = "syncDalEntityManagerFactory", unitName = "BrpEntities")
    private EntityManager em;

    private final ExcelAdapter excelAdapter = new ExcelAdapterImpl();

    private final Lo3PersoonslijstParser lo3PlParser = new Lo3PersoonslijstParser();

    private JmsTemplate brpJmsTemplate;

    @Before
    public void setupStamtabellenObvDbUnit() throws DatabaseUnitException, SQLException, JMSException {
        final IDatabaseConnection connection = dbUnitUtil.createConnection();
        dbUnitUtil.insert(
                connection,
                AbstractIT.class,
                "/sql/data/brpStamgegevens-kern.xml",
                "/sql/data/brpStamgegevens-autaut.xml",
                "/sql/data/brpStamgegevens-conv.xml",
                "/sql/data/brpStamgegevens-verconv.xml");
        connection.close();
    }

    protected BrpPersoonslijstService getBrpPersoonslijstService() {
        return brpPersoonslijstService;
    }

    protected boolean komtAnummerActueelVoor(final String anummer) {
        return getBrpPersoonslijstService().zoekPersoonOpAnummer(anummer) != null;
    }

    protected boolean komtPartijVoor(final String code) {
        final List<Partij> partijen = em.createQuery("from Partij where code = :code", Partij.class).setParameter("code", code).getResultList();
        return partijen != null && partijen.size() == 1;

    }

    protected List<AdministratieveHandeling> zoekNietGeleverdeAdministratieveHandelingen() {
        return em.createQuery("from AdministratieveHandeling where statuslev = :statusTeLeveren or statuslev = :statusInLevering",
                AdministratieveHandeling.class)
                .setParameter("statusTeLeveren", StatusLeveringAdministratieveHandeling.TE_LEVEREN.getId())
                .setParameter("statusInLevering", StatusLeveringAdministratieveHandeling.IN_LEVERING.getId()).getResultList();
    }

    protected void updateLeveringStatusVoorAdministratieveHandeling(final AdministratieveHandeling administratieveHandeling, final
    StatusLeveringAdministratieveHandeling statusLeveringAdministratieveHandeling) {
        TransactionTemplate tt = new TransactionTemplate(transactionManager);
        tt.execute(status -> {
            administratieveHandeling.setStatusLevering(statusLeveringAdministratieveHandeling);
            em.merge(administratieveHandeling);
            return null;
        });
    }

    protected BrpPersoonslijst leesBrpPersoonslijst(final String anummer) {
        return getBrpPersoonslijstService().bevraagPersoonslijst(anummer);
    }

    protected void persisteerPersoonslijst(final Lo3Persoonslijst persoonslijst) {
        // Converteer lo3 persoonslijst naar brp persoonlijst
        Logging.initContext();
        final BrpPersoonslijst brpPersoonslijst = converteerLo3NaarBrpService.converteerLo3Persoonslijst(persoonslijst);
        Logging.destroyContext();

        final Lo3Bericht lo3Bericht = Lo3Bericht.newInstance(UUID.randomUUID().toString(), Lo3BerichtenBron.SYNCHRONISATIE, "testgeval", true);

        getBrpPersoonslijstService().persisteerPersoonslijst(brpPersoonslijst, lo3Bericht);
    }

    protected void persisteerPersoonslijst(final BrpPersoonslijst persoonslijst) {
        final Lo3Bericht lo3Bericht = Lo3Bericht.newInstance(UUID.randomUUID().toString(), Lo3BerichtenBron.SYNCHRONISATIE, "testgeval", true);

        getBrpPersoonslijstService().persisteerPersoonslijst(persoonslijst, lo3Bericht);
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

    @Autowired
    @Named(value = "brpQueueConnectionFactory")
    public void setBrpConnectionFactory(final ConnectionFactory connectionFactory) {
        brpJmsTemplate = new JmsTemplate(connectionFactory);
        brpJmsTemplate.setReceiveTimeout(10000);
    }

    protected Message expectBrpMessage(final String destinationName) {
        Message message;
        try {
            message = brpJmsTemplate.receive(destinationName);
        } catch (final JmsException e) {
            throw new IllegalArgumentException(e);
        }
        Assert.assertNotNull("Bericht verwacht", message);
        return message;
    }

    protected String getContent(final Message message) throws JMSException {
        if (message instanceof TextMessage) {
            return ((TextMessage) message).getText();
        } else {
            throw new IllegalArgumentException("BerichtType niet ondersteund");
        }
    }
}
