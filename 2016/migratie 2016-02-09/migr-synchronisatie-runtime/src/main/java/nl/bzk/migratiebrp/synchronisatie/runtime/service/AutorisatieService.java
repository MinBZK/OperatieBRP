/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service;

import javax.inject.Inject;

import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AutorisatieAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AutorisatieBericht;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAutorisatie;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigeAutorisatieException;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Autorisatie;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerLo3NaarBrpService;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.PreconditiesService;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Bericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3BerichtenBron;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.LoggingMapper;
import nl.bzk.migratiebrp.synchronisatie.runtime.util.MessageId;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Verwerkt het AutorisatieBericht en retourneerd een AutorisatieAntwoordBericht.
 */
public class AutorisatieService implements SynchronisatieBerichtService<AutorisatieBericht, AutorisatieAntwoordBericht> {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private BrpDalService brpDalService;
    @Inject
    private ConverteerLo3NaarBrpService conversieService;
    @Inject
    private PreconditiesService preconditieService;

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.synchronisatie.runtime.service.SynchronisatieBerichtService#getVerzoekType()
     */
    @Override
    public final Class<AutorisatieBericht> getVerzoekType() {
        return AutorisatieBericht.class;
    }

    /**
     * Slaat de gegeven autorisatie op in BRP.
     *
     * @param autorisatieBericht
     *            het autorisatie bericht met daarin de autorisaties van een afnemer
     * @return het antwoordbericht met daarin de status van de verwerking (ok/fout)
     */
    @Override
    @SuppressWarnings("checkstyle:illegalcatch")
    @Transactional(value = "syncDalTransactionManager", propagation = Propagation.REQUIRED)
    public final AutorisatieAntwoordBericht verwerkBericht(final AutorisatieBericht autorisatieBericht) {
        // Initieren berichtlog
        final Lo3Bericht bericht =
                Lo3Bericht.newInstance(autorisatieBericht.getMessageId(), Lo3BerichtenBron.INITIELE_VULLING, autorisatieBericht.format(), false);

        final AutorisatieAntwoordBericht result = new AutorisatieAntwoordBericht();
        result.setMessageId(MessageId.generateSyncMessageId());
        result.setCorrelationId(autorisatieBericht.getMessageId());
        try {
            // Init logging
            Logging.initContext();

            // Input uit bericht
            final Lo3Autorisatie lo3Autorisatie = autorisatieBericht.getAutorisatie();

            // Controleer precondities
            preconditieService.verwerk(lo3Autorisatie);

            // Converteer naar BRP
            final BrpAutorisatie brpAutorisatie = conversieService.converteerLo3Autorisatie(lo3Autorisatie);

            // Opslaan in BRP
            brpDalService.persisteerAutorisatie(brpAutorisatie);

            // Result: OK
            result.setStatus(StatusType.OK);

        } catch (final OngeldigeAutorisatieException e) {
            result.setStatus(StatusType.FOUT);

            bericht.setFoutcode(e.getClass().getName());
            bericht.setVerwerkingsmelding(e.getMessage());
        } catch (final Exception e /* Catch exception voor het robuust afhandelen van exceptions op service niveau */) {
            // Result: FOUT
            result.setStatus(StatusType.FOUT);
            result.setFoutmelding(e.getMessage());

            bericht.setFoutcode(e.getClass().getName());
            bericht.setVerwerkingsmelding(e.getMessage());
        } finally {
            try {
                // Logging naar berichtLog
                new LoggingMapper().mapLogging(Logging.getLogging(), bericht);

                // Opslaan berichtLog
                brpDalService.persisteerLo3Bericht(bericht);
            } catch (final Exception e) {
                // Catch exception voor het robuust afhandelen van exceptions tijdens foutafhandeling
                LOG.error("Fout opgetreden bij opslaan BerichtLog", e);
            } finally {
                Logging.destroyContext();
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getServiceNaam() {
        return this.getClass().getSimpleName();
    }

}
