/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service;

import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AutorisatieAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AutorisatieBericht;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAutorisatie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Autorisatie;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerLo3NaarBrpService;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.PreconditiesService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpAutorisatieService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.PartijNietGevondenException;
import nl.bzk.migratiebrp.synchronisatie.runtime.util.MessageId;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Verwerkt het AutorisatieBericht en retourneerd een AutorisatieAntwoordBericht.
 */
public class AutorisatieService implements SynchronisatieBerichtService<AutorisatieBericht, AutorisatieAntwoordBericht> {

    private final BrpAutorisatieService brpAutorisatieService;
    private final ConverteerLo3NaarBrpService conversieService;
    private final PreconditiesService preconditieService;

    /**
     * constructor.
     * @param brpAutorisatieService de aurotisatie service
     * @param conversieService de conversie service
     * @param preconditieService de preconditie service
     */
    @Inject
    public AutorisatieService(final BrpAutorisatieService brpAutorisatieService, final ConverteerLo3NaarBrpService conversieService,
                              final PreconditiesService preconditieService) {
        this.brpAutorisatieService = brpAutorisatieService;
        this.conversieService = conversieService;
        this.preconditieService = preconditieService;
    }

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
     * @param autorisatieBericht het autorisatie bericht met daarin de autorisaties van een afnemer
     * @return het antwoordbericht met daarin de status van de verwerking (ok/fout)
     */
    @Override
    @Transactional(value = "syncDalTransactionManager", propagation = Propagation.REQUIRED)
    public final AutorisatieAntwoordBericht verwerkBericht(final AutorisatieBericht autorisatieBericht) {

        final AutorisatieAntwoordBericht result = new AutorisatieAntwoordBericht();
        result.setMessageId(MessageId.generateSyncMessageId());
        result.setCorrelationId(autorisatieBericht.getMessageId());
        try {
            // Init logging
            Logging.initContext();

            // Input uit bericht
            Lo3Autorisatie lo3Autorisatie = autorisatieBericht.getAutorisatie();

            // Controleer precondities
            lo3Autorisatie = preconditieService.verwerk(lo3Autorisatie);

            if (lo3Autorisatie != null) {
                // Converteer naar BRP
                final BrpAutorisatie brpAutorisatie = conversieService.converteerLo3Autorisatie(lo3Autorisatie);

                // Opslaan in BRP
                brpAutorisatieService.persisteerAutorisatie(brpAutorisatie);
            }
        } catch (PartijNietGevondenException e) {
            // Partij niet gevonden; {@link brpAutorisatieService#persisteerAutorisatie} heeft logging toegevoegd.
            // Deze loggign wordt opgepakt in result.verwerkLogging
        } finally {
            result.verwerkLogging(autorisatieBericht, Logging.getLogging().getRegels());

            Logging.destroyContext();
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
