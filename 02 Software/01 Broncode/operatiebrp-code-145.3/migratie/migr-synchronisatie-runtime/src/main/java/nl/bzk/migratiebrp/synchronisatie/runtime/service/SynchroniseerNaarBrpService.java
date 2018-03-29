/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service;

import java.util.Arrays;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Bericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Lo3BerichtenBron;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
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
public final class SynchroniseerNaarBrpService
        implements SynchronisatieBerichtService<SynchroniseerNaarBrpVerzoekBericht, SynchroniseerNaarBrpAntwoordBericht> {

    private final SyncParameters syncParameters;

    private final SynchronisatieVerwerker initieleVullingSynchronisatieVerwerker;

    private final SynchronisatieVerwerker synchronisatieVerwerker;

    private final PlService plService;

    /**
     * constructor.
     * @param syncParameters
     * @param initieleVullingSynchronisatieVerwerker
     * @param synchronisatieVerwerker
     * @param plService
     */
    @Inject
    public SynchroniseerNaarBrpService( @Named("syncParameters") final SyncParameters syncParameters,
                                        @Named("initieleVullingSynchronisatieVerwerker") final SynchronisatieVerwerker initieleVullingSynchronisatieVerwerker,
                                        @Named("synchronisatieVerwerker") final SynchronisatieVerwerker synchronisatieVerwerker, final PlService plService) {
        this.syncParameters = syncParameters;
        this.initieleVullingSynchronisatieVerwerker = initieleVullingSynchronisatieVerwerker;
        this.synchronisatieVerwerker = synchronisatieVerwerker;
        this.plService = plService;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * nl.bzk.migratiebrp.synchronisatie.runtime.service.SynchronisatieBerichtService#getVerzoekType
     * ()
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
            final Lo3Bericht loggingBericht = Lo3Bericht.newInstance(verzoek.getMessageId(),
                    syncParameters.isInitieleVulling() ? Lo3BerichtenBron.INITIELE_VULLING : Lo3BerichtenBron.SYNCHRONISATIE, verzoek.format(), true);

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

                result = SynchronisatieHelper.maakAntwoord(verzoek, status);
                if (e.getKandidaten() != null) {
                    result.setKandidaten(Arrays.asList(plService.converteerKandidaten(e.getKandidaten())));
                }

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
     * @see
     * nl.bzk.migratiebrp.synchronisatie.runtime.service.SynchronisatieBerichtService#getServiceNaam
     * ()
     */
    @Override
    public String getServiceNaam() {
        return this.getClass().getSimpleName();
    }

}
