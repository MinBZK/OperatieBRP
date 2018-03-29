/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.processors.conversie;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Bericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Lo3BerichtenBron;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.logging.LoggingContext;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Deze implementatie is afgeleid van code geschreven door migratie (migr-test-levering /
 * LeveringMutatieberichtTestCasus.java).
 */
public final class ConversieDataVullerImpl implements ConversieDataVuller {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final ExcelAdapter EXCEL_ADAPTER = new ExcelAdapterImpl();
    private static final Lo3PersoonslijstParser LO3_PARSER = new Lo3PersoonslijstParser();
    private static final String PERSOON_XLS_BEVAT_GEEN_PERSOONSGEGEVENS = "Persoon.xls bevat geen persoonsgegevens";

    private final BrpPersoonslijstService persoonslijstService;
    private final ConverteerLo3NaarBrpService converteerLo3NaarBrpService;
    private final Lo3SyntaxControle syntaxControle;
    private final PreconditiesService preconditieService;

    /**
     * Constructor.
     * @param syntaxControle voert de controle van de LO3 syntax uit
     * @param preconditieService voert de preconditie controles uit
     * @param converteerLo3NaarBrpService converteert LO3 naar BRP
     * @param persoonslijstService de service voor de BRP Persoonslijst
     */
    @Inject
    public ConversieDataVullerImpl(final Lo3SyntaxControle syntaxControle, final PreconditiesService preconditieService,
                                   final ConverteerLo3NaarBrpService converteerLo3NaarBrpService, final BrpPersoonslijstService persoonslijstService) {
        this.syntaxControle = syntaxControle;
        this.preconditieService = preconditieService;
        this.converteerLo3NaarBrpService = converteerLo3NaarBrpService;
        this.persoonslijstService = persoonslijstService;
    }

    /**
     * Converteer sheet met daarin enkel initiele vulling.
     */
    @Override
    @Transactional(transactionManager = "syncDalTransactionManager", propagation = Propagation.REQUIRES_NEW)
    public void converteerInitieleVullingPL(final Path persoonFile) throws ConversieException {
        try (final InputStream fis = Files.newInputStream(persoonFile)) {
            final List<ExcelData> excelDatas = EXCEL_ADAPTER.leesExcelBestand(fis);
            if (excelDatas.isEmpty()) {
                throw new IllegalArgumentException(PERSOON_XLS_BEVAT_GEEN_PERSOONSGEGEVENS);
            }
            final PersoonIdHolder persoonIdHolder = new PersoonIdHolder();
            for (int i = 0; i < 1; i++) {
                final ExcelData excelData = excelDatas.get(i);

                converteer(excelData, persoonIdHolder, Lo3BerichtenBron.INITIELE_VULLING);
            }
        } catch (final ExcelAdapterException | Lo3SyntaxException | IOException e) {
            throw new ConversieException(e);
        }
    }

    @Override
    public BrpPersoonslijst vraagPersoonOpUitDatabase(final String anummer) {
        return persoonslijstService.bevraagPersoonslijst(anummer);
    }

    private void converteer(final ExcelData excelData, final PersoonIdHolder persoonIdHolder, final Lo3BerichtenBron bron) throws ConversieException {
        persoonslijstService.getSyncParameters().setInitieleVulling(bron == Lo3BerichtenBron.INITIELE_VULLING);

        LOG.info("Inlezen Excel sheet");
        try {
            Logging.initContext();
            SynchronisatieLogging.init();

            // Lezen Excelsheet
            final List<Lo3CategorieWaarde> lo3Inhoud = excelData.getCategorieLijst();
            // Controleren syntax
            final List<Lo3CategorieWaarde> lo3InhoudNaSyntaxControle = controleerLo3Syntax(lo3Inhoud);
            // Parsen naar Lo3Persoonslijst
            final Lo3Persoonslijst lo3Persoonslijst = LO3_PARSER.parse(lo3InhoudNaSyntaxControle);
            // Controleren precondities
            final Lo3Persoonslijst schoneLo3Persoonslijst = controleerPrecondities(lo3Persoonslijst);
            // Converteren naar BrpPersoonslijst
            final BrpPersoonslijst brpPl = converteerLo3NaarBrpService.converteerLo3Persoonslijst(schoneLo3Persoonslijst);
            // Converteren naar Teletex string
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

            LOG.info("Persoon.id: {}", result.getPersoon().getId());
            LOG.info("Administratieve handeling(en): {}", result.getAdministratieveHandelingen());
        } catch (TeLeverenAdministratieveHandelingenAanwezigException e) {
            throw new ConversieException(e);
        } finally {
            LoggingContext.reset();
            Logging.destroyContext();
        }
    }

    private Lo3Persoonslijst controleerPrecondities(final Lo3Persoonslijst lo3Persoonslijst) throws ConversieException {
        final Lo3Persoonslijst schoneLo3Persoonslijst;
        try {
            schoneLo3Persoonslijst = preconditieService.verwerk(lo3Persoonslijst);
        } catch (final OngeldigePersoonslijstException e) {
            final StringBuilder sb = new StringBuilder();
            for (final LogRegel logRegel : Logging.getLogging().getRegels()) {
                sb.append(logRegel.toString());
                sb.append("\n");
            }
            throw new ConversieException(sb.toString(), e);
        }
        return schoneLo3Persoonslijst;
    }

    private List<Lo3CategorieWaarde> controleerLo3Syntax(final List<Lo3CategorieWaarde> lo3Inhoud) throws ConversieException {
        // Lo3 syntax controle
        final List<Lo3CategorieWaarde> lo3InhoudNaSyntaxControle;
        try {
            lo3InhoudNaSyntaxControle = syntaxControle.controleer(lo3Inhoud);
        } catch (final OngeldigePersoonslijstException e) {
            throw new ConversieException("Fout tijdens syntax controle", e);
        }
        return lo3InhoudNaSyntaxControle;
    }

    /**
     * Holder class voor het ID van de persoon.
     */
    private static final class PersoonIdHolder {
        private Long id;

        public Long getId() {
            return id;
        }

        public void setId(final Long id) {
            this.id = id;
        }
    }
}
