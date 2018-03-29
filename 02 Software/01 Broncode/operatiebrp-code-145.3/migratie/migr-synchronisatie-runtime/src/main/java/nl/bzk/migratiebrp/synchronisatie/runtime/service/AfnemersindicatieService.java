/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service;

import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AfnemersindicatiesAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AfnemersindicatiesBericht;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAfnemersindicaties;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Afnemersindicatie;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerLo3NaarBrpService;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.PreconditiesService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpAfnemerIndicatiesService;
import nl.bzk.migratiebrp.synchronisatie.runtime.util.MessageId;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Verwerkt het AfnemersindicatiesBericht en retourneerd een AfnemersindicatiesAntwoordBericht.
 */
public class AfnemersindicatieService implements SynchronisatieBerichtService<AfnemersindicatiesBericht, AfnemersindicatiesAntwoordBericht> {

    private final BrpAfnemerIndicatiesService afnemerIndicatiesService;
    private final ConverteerLo3NaarBrpService conversieService;
    private final PreconditiesService preconditieService;

    /**
     * constructor.
     * @param afnemerIndicatiesService
     * @param conversieService
     * @param preconditieService
     */
    @Inject
    public AfnemersindicatieService(final BrpAfnemerIndicatiesService afnemerIndicatiesService, final ConverteerLo3NaarBrpService conversieService,
                                    final PreconditiesService preconditieService) {
        this.afnemerIndicatiesService = afnemerIndicatiesService;
        this.conversieService = conversieService;
        this.preconditieService = preconditieService;
    }

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
     * @param afnemersindicatiesBericht het afnemersindicaties bericht met daarin de afnemersindicaties voor een persoon
     * @return het antwoordbericht met daarin de status van de verwerking (ok/fout)
     */
    @Override
    @Transactional(value = "syncDalTransactionManager", propagation = Propagation.REQUIRED)
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
            afnemerIndicatiesService.persisteerAfnemersindicaties(brpAfnemersindicaties);
        } finally {
            result.verwerkLogging(afnemersindicatiesBericht, Logging.getLogging().getRegels());

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
