/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service;

import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AfnemersindicatiesAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AfnemersindicatiesBericht;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAfnemersindicaties;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Afnemersindicatie;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerLo3NaarBrpService;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.PreconditiesService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import nl.bzk.migratiebrp.synchronisatie.runtime.util.MessageId;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Verwerkt het AfnemersindicatiesBericht en retourneerd een AfnemersindicatiesAntwoordBericht.
 */
public class AfnemersindicatieService implements SynchronisatieBerichtService<AfnemersindicatiesBericht, AfnemersindicatiesAntwoordBericht> {

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
    public final Class<AfnemersindicatiesBericht> getVerzoekType() {
        return AfnemersindicatiesBericht.class;
    }

    /**
     * Slaat de gegeven afnemersindicaties op in BRP.
     *
     * @param afnemersindicatiesBericht
     *            het afnemersindicaties bericht met daarin de afnemersindicaties voor een persoon
     * @return het antwoordbericht met daarin de status van de verwerking (ok/fout)
     */
    @Override
    @Transactional(value = "syncDalTransactionManager", propagation = Propagation.REQUIRED)
    @SuppressWarnings("checkstyle:illegalcatch")
    public final AfnemersindicatiesAntwoordBericht verwerkBericht(final AfnemersindicatiesBericht afnemersindicatiesBericht) {
        final AfnemersindicatiesAntwoordBericht result = new AfnemersindicatiesAntwoordBericht();
        result.setMessageId(MessageId.generateSyncMessageId());
        result.setCorrelationId(afnemersindicatiesBericht.getMessageId());
        try {
            // Init logging
            Logging.initContext();

            // Input uit bericht
            Lo3Afnemersindicatie lo3Afnemersindicaties = afnemersindicatiesBericht.getAfnemersindicaties();

            // Controleer precondities (en opschonen)
            lo3Afnemersindicaties = preconditieService.verwerk(lo3Afnemersindicaties);

            // Converteer naar BRP
            final BrpAfnemersindicaties brpAfnemersindicaties = conversieService.converteerLo3Afnemersindicaties(lo3Afnemersindicaties);

            // Opslaan in BRP
            brpDalService.persisteerAfnemersindicaties(brpAfnemersindicaties);

            // Result: OK / WAARSCHUWING
            if (Logging.getLogging().getRegels().isEmpty()) {
                result.setStatus(StatusType.OK);
            } else {
                result.setStatus(StatusType.WAARSCHUWING);
            }

        } catch (final Exception e /* Catch exception voor het robuust afhandelen van exceptions op service niveau */) {
            // Result: FOUT
            result.setStatus(StatusType.FOUT);
            result.setFoutmelding(e.getMessage());
        } finally {
            result.setLogging(Logging.getLogging().getRegels());
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
