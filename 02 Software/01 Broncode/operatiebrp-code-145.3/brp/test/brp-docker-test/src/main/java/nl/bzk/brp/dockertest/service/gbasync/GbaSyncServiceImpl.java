/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.service.gbasync;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Bericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Lo3BerichtenBron;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.logging.LoggingContext;
import nl.bzk.brp.test.common.TestclientExceptie;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.parser.Lo3PersoonslijstParser;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.logging.LogRegel;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerLo3NaarBrpService;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.Lo3SyntaxControle;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.PreconditiesService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpPersoonslijstService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.PersoonslijstPersisteerResultaat;
import nl.bzk.migratiebrp.synchronisatie.dal.service.TeLeverenAdministratieveHandelingenAanwezigException;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import nl.bzk.migratiebrp.util.excel.ExcelAdapter;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterException;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterImpl;
import nl.bzk.migratiebrp.util.excel.ExcelData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * Deze implementatie is afgeleid van code geschreven door migratie (migr-test-levering /
 * LeveringMutatieberichtTestCasus.java).
 */
@Service
public final class GbaSyncServiceImpl implements GbaSyncService {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final ExcelAdapter EXCEL_ADAPTER = new ExcelAdapterImpl();
    private static final Lo3PersoonslijstParser LO3_PARSER = new Lo3PersoonslijstParser();
    private static final String PERSOON_XLS_BEVAT_GEEN_PERSOONSGEGEVENS = "Persoon.xls bevat geen persoonsgegevens";

    @Autowired
    private Lo3SyntaxControle syntaxControle;

    @Autowired
    private PreconditiesService preconditieService;

    @Autowired
    private ConverteerLo3NaarBrpService converteerLo3NaarBrpService;

    @Autowired
    private BrpPersoonslijstService persoonslijstService;

    @Inject
    @Named("masterDataSource")
    private DataSource syncDalDataSource;

    @Override
    public PersoonslijstPersisteerResultaat converteer(final Resource persoonFile) throws IOException,
            ExcelAdapterException, Lo3SyntaxException, OngeldigePersoonslijstException, TeLeverenAdministratieveHandelingenAanwezigException {
        try (final InputStream fis = persoonFile.getInputStream()) {
            final List<ExcelData> excelDatas = EXCEL_ADAPTER.leesExcelBestand(fis);
            if (excelDatas.isEmpty()) {
                throw new TestclientExceptie(PERSOON_XLS_BEVAT_GEEN_PERSOONSGEGEVENS);
            }

            final PersoonIdHolder persoonIdHolder = new PersoonIdHolder();
            for (int i = 0; i < excelDatas.size(); i++) {
                final ExcelData excelData = excelDatas.get(i);
                final Lo3BerichtenBron bron;
                if (i == 0) {
                    bron = Lo3BerichtenBron.INITIELE_VULLING;
                } else {
                    bron = Lo3BerichtenBron.SYNCHRONISATIE;
                }
                converteer(excelData, persoonIdHolder, bron);
            }
            return null;
        }
    }

    @Override
    public PersoonslijstPersisteerResultaat synchroniseer(final Resource persoonFile) throws IOException,
            ExcelAdapterException, Lo3SyntaxException, OngeldigePersoonslijstException, TeLeverenAdministratieveHandelingenAanwezigException {
        try (final InputStream fis = persoonFile.getInputStream()) {
            final List<ExcelData> excelDatas = EXCEL_ADAPTER.leesExcelBestand(fis);
            if (excelDatas.isEmpty()) {
                throw new TestclientExceptie(PERSOON_XLS_BEVAT_GEEN_PERSOONSGEGEVENS);
            }
            final PersoonIdHolder persoonIdHolder = new PersoonIdHolder();
            persoonIdHolder.setId(1L);
            for (final ExcelData excelData : excelDatas) {
                final Lo3BerichtenBron bron = Lo3BerichtenBron.SYNCHRONISATIE;
                converteer(excelData, persoonIdHolder, bron);
            }
            return null;
        }
    }

    private void converteer(final ExcelData excelData, final PersoonIdHolder persoonIdHolder, final Lo3BerichtenBron bron) throws
            OngeldigePersoonslijstException, TeLeverenAdministratieveHandelingenAanwezigException {
        persoonslijstService.getSyncParameters().setInitieleVulling(bron == Lo3BerichtenBron.INITIELE_VULLING);

        LOG.info("Inlezen Excel sheet: " + bron);
        try {
            Logging.initContext();
            SynchronisatieLogging.init();

            final List<Lo3CategorieWaarde> lo3Inhoud = excelData.getCategorieLijst();

            // Lo3 syntax controle
            final List<Lo3CategorieWaarde> lo3InhoudNaSyntaxControle;
            try {
                lo3InhoudNaSyntaxControle = syntaxControle.controleer(lo3Inhoud);
            } catch (final OngeldigePersoonslijstException e) {
                throw new TestclientExceptie(e);
            }

            // Parse persoonslijst
            final Lo3Persoonslijst lo3Persoonslijst = LO3_PARSER.parse(lo3InhoudNaSyntaxControle);

            // Controleer precondities
            final Lo3Persoonslijst schoneLo3Persoonslijst;
            try {
                schoneLo3Persoonslijst = preconditieService.verwerk(lo3Persoonslijst);
            } catch (final OngeldigePersoonslijstException e) {
                final StringBuilder sb = new StringBuilder();
                for (final LogRegel logRegel : Logging.getLogging().getRegels()) {
                    sb.append(logRegel.toString());
                    sb.append("\n");
                }
                throw new TestclientExceptie(sb.toString(), e);
            }

            final BrpPersoonslijst brpPl = converteerLo3NaarBrpService.converteerLo3Persoonslijst(schoneLo3Persoonslijst);

            final String plText = Lo3Inhoud.formatInhoud(lo3Inhoud);

            final Lo3Bericht lo3Bericht = new Lo3Bericht("persoon", bron, new Timestamp(System.currentTimeMillis()), plText, true);
            LOG.info("Logging: {}", lo3Bericht);

            final PersoonslijstPersisteerResultaat result;
            if (persoonIdHolder.getId() == null) {
                result = persoonslijstService.persisteerPersoonslijst(brpPl, lo3Bericht);
            } else {
                result = persoonslijstService.persisteerPersoonslijst(brpPl, persoonIdHolder.getId(), lo3Bericht);
            }
            persoonIdHolder.setId(result.getPersoon().getId());
            //zet status levering adm.hnd.
            setAdministratieveHandelingenAlsGeleverd(result);

            LOG.info("Result: {}", result);
            LOG.info("Persoon: {}", result.getPersoon());
            LOG.info("Administratieve handeling(en): {}", result.getAdministratieveHandelingen());
            LOG.info("Persoon.id: {}", result.getPersoon().getId());
        } finally {
            LoggingContext.reset();
            Logging.destroyContext();
        }
    }

    private void setAdministratieveHandelingenAlsGeleverd(final PersoonslijstPersisteerResultaat persoonslijstPersisteerResultaat) {
        for (final AdministratieveHandeling administratieveHandeling : persoonslijstPersisteerResultaat.getAdministratieveHandelingen()) {
            new JdbcTemplate(syncDalDataSource).update("update kern.admhnd set statuslev = 4 where id = ?", administratieveHandeling.getId());
        }
    }

    /**
     * Holder class voor het ID van de persoon.
     */
    private static final class PersoonIdHolder {
        private Long id;

        Long getId() {
            return id;
        }

        void setId(final Long id) {
            this.id = id;
        }
    }
}
