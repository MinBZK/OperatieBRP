/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.component.services.gbasync;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;
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
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Bericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3BerichtenBron;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.PersoonslijstPersisteerResultaat;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.util.common.logging.LoggingContext;
import nl.bzk.migratiebrp.util.excel.ExcelAdapter;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterException;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterImpl;
import nl.bzk.migratiebrp.util.excel.ExcelData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

/**
 * Deze implementatie is afgeleid van code geschreven door migratie (migr-test-levering /
 * LeveringMutatieberichtTestCasus.java).
 */
@Service
public final class GbaSyncServiceImpl implements GbaSyncService {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final ExcelAdapter           EXCEL_ADAPTER                           = new ExcelAdapterImpl();
    private static final Lo3PersoonslijstParser LO3_PARSER                              = new Lo3PersoonslijstParser();
    private static final String                 PERSOON_XLS_BEVAT_GEEN_PERSOONSGEGEVENS = "Persoon.xls bevat geen persoonsgegevens";

    @Autowired
    private Lo3SyntaxControle syntaxControle;

    @Autowired
    private PreconditiesService preconditieService;

    @Autowired
    private ConverteerLo3NaarBrpService converteerLo3NaarBrpService;

    @Autowired
    private BrpDalService brpDalService;

    /**
     * Converteer sheet met daarin enkel initiele vulling.
     */
    @Override
    public PersoonslijstPersisteerResultaat converteerInitieleVullingPL(final Resource persoonFile)
        throws IOException, ExcelAdapterException, Lo3SyntaxException, OngeldigePersoonslijstException
    {
        try (InputStream fis = new BufferedInputStream(persoonFile.getInputStream())) {
            final List<ExcelData> excelDatas = EXCEL_ADAPTER.leesExcelBestand(fis);
            if (excelDatas.isEmpty()) {
                throw new IllegalArgumentException(PERSOON_XLS_BEVAT_GEEN_PERSOONSGEGEVENS);
            }
            for (int i = 0; i < 1; i++) {
                final ExcelData excelData = excelDatas.get(i);
                converteer(excelData, Lo3BerichtenBron.INITIELE_VULLING);
            }
            return null;
        }
    }

    /**
     * Converteer sheet met daarin enkel syncs.
     *
     * @param persoonFile persoon file
     * @return the persoonslijst persisteer resultaat
     * @throws IOException IO exception
     * @throws ExcelAdapterException excel adapter exception
     * @throws Lo3SyntaxException lo 3 syntax exception
     * @throws OngeldigePersoonslijstException ongeldige persoonslijst exception
     */
    @Override
    public PersoonslijstPersisteerResultaat converteerSyncPL(final Resource persoonFile)
        throws IOException, ExcelAdapterException, Lo3SyntaxException, OngeldigePersoonslijstException
    {

        try (final InputStream fis = new BufferedInputStream(persoonFile.getInputStream())) {
            final List<ExcelData> excelDatas = EXCEL_ADAPTER.leesExcelBestand(fis);
            if (excelDatas.isEmpty()) {
                throw new IllegalArgumentException(PERSOON_XLS_BEVAT_GEEN_PERSOONSGEGEVENS);
            }
            for (final ExcelData excelData : excelDatas) {
                converteer(excelData, Lo3BerichtenBron.SYNCHRONISATIE);
            }
            return null;
        }
    }

    /**
     * Converteer een PL file met daarin initiele vulling kolom (0) en 0 of * sync kolommen.
     *
     * @param persoonFile persoon file
     * @return the persoonslijst persisteer resultaat
     * @throws IOException iO exception
     * @throws ExcelAdapterException excel adapter exception
     * @throws Lo3SyntaxException lo 3 syntax exception
     * @throws OngeldigePersoonslijstException ongeldige persoonslijst exception
     */
    public PersoonslijstPersisteerResultaat converteer(final File persoonFile)
        throws IOException, ExcelAdapterException, Lo3SyntaxException, OngeldigePersoonslijstException
    {
        try (FileInputStream fis = new FileInputStream(persoonFile)) {
            final List<ExcelData> excelDatas = EXCEL_ADAPTER.leesExcelBestand(fis);
            if (excelDatas.isEmpty()) {
                throw new IllegalArgumentException(PERSOON_XLS_BEVAT_GEEN_PERSOONSGEGEVENS);
            }

            for (int i = 0; i < excelDatas.size(); i++) {
                final ExcelData excelData = excelDatas.get(i);
                final Lo3BerichtenBron bron;
                if (i == 0) {
                    bron = Lo3BerichtenBron.INITIELE_VULLING;
                } else {
                    bron = Lo3BerichtenBron.SYNCHRONISATIE;
                }
                converteer(excelData, bron);
            }
            return null;
        }
    }

    @Override
    public BrpPersoonslijst terugMappenNaarConversieModel(final long anummer) {
        return brpDalService.bevraagPersoonslijst(anummer);
    }

    private PersoonslijstPersisteerResultaat converteer(final ExcelData excelData, final Lo3BerichtenBron bron)
        throws IOException, ExcelAdapterException, Lo3SyntaxException, OngeldigePersoonslijstException
    {
        brpDalService.getSyncParameters().setInitieleVulling(bron == Lo3BerichtenBron.INITIELE_VULLING);

        LOG.info("Inlezen Excel sheet");
        try {
            Logging.initContext();
            SynchronisatieLogging.init();

            final List<Lo3CategorieWaarde> lo3Inhoud = excelData.getCategorieLijst();

            // Lo3 syntax controle
            final List<Lo3CategorieWaarde> lo3InhoudNaSyntaxControle;
            try {
                lo3InhoudNaSyntaxControle = syntaxControle.controleer(lo3Inhoud);
            } catch (final OngeldigePersoonslijstException e) {
                throw e;
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
                throw new RuntimeException(sb.toString(), e);
            }

            final BrpPersoonslijst brpPl = converteerLo3NaarBrpService.converteerLo3Persoonslijst(schoneLo3Persoonslijst);

            final String plText = Lo3Inhoud.formatInhoud(lo3Inhoud);

            final Lo3Bericht lo3Bericht = new Lo3Bericht("persoon", bron, new Timestamp(System.currentTimeMillis()), plText, true);
            LOG.info("Logging: {}", lo3Bericht);

            final boolean isAnummerWijziging;
            final long anummerTeVervangenPersoon;
            if (isAnummerWijziging(excelData.getHeaders())) {
                isAnummerWijziging = true;
                anummerTeVervangenPersoon = Long.parseLong(excelData.getHeaders()[4]);
            } else {
                isAnummerWijziging = false;
                anummerTeVervangenPersoon = brpPl.getActueelAdministratienummer();
            }

            LOG.info("A-nummer wijziging? {}", isAnummerWijziging);
            LOG.info("A-nummer te vervangen persoon: {}", anummerTeVervangenPersoon);

            final PersoonslijstPersisteerResultaat result =
                brpDalService.persisteerPersoonslijst(brpPl, anummerTeVervangenPersoon, isAnummerWijziging, lo3Bericht);
            LOG.info("Result: {}", result);
            LOG.info("Persoon: {}", result.getPersoon());
            LOG.info("Administratieve handeling(en): {}", result.getAdministratieveHandelingen());
            LOG.info("Persoon.id: {}", result.getPersoon().getId());
            return result;
        } finally {
            LoggingContext.reset();
            Logging.destroyContext();
        }
    }

    private boolean isAnummerWijziging(final String[] headers) {
        return headers.length == 5 && "Lg01".equals(headers[1]) && isAnummer(headers[3]) && isAnummer(headers[4]);
    }

    private boolean isAnummer(final String value) {
        try {
            if (value != null && !"".equals(value) && Long.parseLong(value) > 0) {
                return true;
            }
        } catch (final NumberFormatException e) {
            // Ignore
            LOG.debug("NumberFormatException bij a-nummer", e);
        }
        return false;
    }

}
