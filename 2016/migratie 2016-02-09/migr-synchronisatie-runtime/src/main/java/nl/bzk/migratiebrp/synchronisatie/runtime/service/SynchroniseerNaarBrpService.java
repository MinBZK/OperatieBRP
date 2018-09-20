/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service;

import javax.inject.Inject;
import javax.inject.Named;

import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Bericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3BerichtenBron;
import nl.bzk.migratiebrp.synchronisatie.dal.service.SyncParameters;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.LoggingMapper;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieBeslissing;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.SynchronisatieVerwerker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.exception.SynchronisatieVerwerkerException;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.helper.SynchronisatieHelper;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.pl.PlService;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.logging.PlVerwerkerLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.logging.PlVerwerkerMelding;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Synchroniseer naar BRP service. Slaat een persoonslijst op in BRP.
 */
public final class SynchroniseerNaarBrpService implements
        SynchronisatieBerichtService<SynchroniseerNaarBrpVerzoekBericht, SynchroniseerNaarBrpAntwoordBericht>
{

    @Named("syncParameters")
    @Inject
    private SyncParameters syncParameters;

    @Inject
    @Named("initieleVullingSynchronisatieVerwerker")
    private SynchronisatieVerwerker initieleVullingSynchronisatieVerwerker;

    @Inject
    @Named("synchronisatieVerwerker")
    private SynchronisatieVerwerker synchronisatieVerwerker;

    @Inject
    private PlService plService;

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.synchronisatie.runtime.service.SynchronisatieBerichtService#getVerzoekType()
     */
    @Override
    public Class<SynchroniseerNaarBrpVerzoekBericht> getVerzoekType() {
        return SynchroniseerNaarBrpVerzoekBericht.class;
    }

    @Override
    @Transactional(value = "syncDalTransactionManager", propagation = Propagation.REQUIRED)
    public SynchroniseerNaarBrpAntwoordBericht verwerkBericht(final SynchroniseerNaarBrpVerzoekBericht verzoek) {
        // Init logging contexts
        Logging.initContext();
        try {

            // Lo3Bericht fungeert als logging
            final Lo3Bericht loggingBericht =
                    Lo3Bericht.newInstance(
                        verzoek.getMessageId(),
                        syncParameters.isInitieleVulling() ? Lo3BerichtenBron.INITIELE_VULLING : Lo3BerichtenBron.SYNCHRONISATIE,
                        verzoek.getLo3BerichtAsTeletexString(),
                        true);

            final PlVerwerkerLogging logging = new PlVerwerkerLogging(PlVerwerkerMelding.SERVICE);

            SynchroniseerNaarBrpAntwoordBericht result;
            try {
                if (syncParameters.isInitieleVulling()) {
                    logging.addBeslissing(SynchronisatieBeslissing.SERVICE_VERWERK_ALS_IV);
                    result = initieleVullingSynchronisatieVerwerker.verwerk(verzoek, loggingBericht);
                } else {
                    logging.addBeslissing(SynchronisatieBeslissing.SERVICE_VERWERK_ALS_SYNC);
                    result = synchronisatieVerwerker.verwerk(verzoek, loggingBericht);
                }
            } catch (final SynchronisatieVerwerkerException e) {
                final StatusType status = e.getStatus();

                final String[] kandidaten = e.getKandidaten() == null ? null : plService.converteerKandidaten(e.getKandidaten());
                result = SynchronisatieHelper.maakAntwoord(verzoek, status, kandidaten);

                new LoggingMapper().mapLogging(Logging.getLogging(), loggingBericht);
                loggingBericht.setVerwerkingsmelding(SynchronisatieLogging.getMelding());
                loggingBericht.setFoutcode(status.toString());
                plService.persisteerLogging(loggingBericht);
            }
            return result;
        } finally {
            Logging.destroyContext();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.synchronisatie.runtime.service.SynchronisatieBerichtService#getServiceNaam()
     */
    @Override
    public String getServiceNaam() {
        return this.getClass().getSimpleName();
    }

}
