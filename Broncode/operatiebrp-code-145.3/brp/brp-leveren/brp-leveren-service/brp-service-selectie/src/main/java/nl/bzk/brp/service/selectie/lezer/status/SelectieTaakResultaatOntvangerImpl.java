/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.lezer.status;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.internbericht.selectie.SelectieTaakResultaat;
import nl.bzk.brp.service.selectie.algemeen.JobEventStopType;
import nl.bzk.brp.service.selectie.algemeen.JobStopEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * SelectieTaakResultaatOntvangerImpl.
 */
@Service
final class SelectieTaakResultaatOntvangerImpl implements SelectieTaakResultaatOntvanger {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private SelectieJobRunStatusService selectieJobRunStatusService;

    @Inject
    private ApplicationContext applicationContext;

    private SelectieTaakResultaatOntvangerImpl() {
    }

    @Override
    public void ontvang(SelectieTaakResultaat selectieTaakResultaat) {
        final SelectieJobRunStatus status = selectieJobRunStatusService.getStatus();
        switch (selectieTaakResultaat.getType()) {
            case VERWERK:
                verwerkVerwerkResultaat(selectieTaakResultaat, status);
                break;
            case SCHRIJF:
                LOGGER.debug("aantalSelectieSchrijfResultaatTaken: " + status.incrementEnGetSchrijfKlaarTaken());
                break;
            case SELECTIE_RESULTAAT_SCHRIJF:
                LOGGER.debug("aantalSelectieResultaatSchrijfResultaatTaken: " + status.incrementEnGetSelectieResultaatSchrijfTakenIncrement());
                break;
            case AFNEMERINDICATIE_VERWERKT:
                afnemerindicatieVerwerkt(selectieTaakResultaat);
                break;
            case FOUT:
                LOGGER.error("fout ontvangen");
                applicationContext.publishEvent(new JobStopEvent(this, JobEventStopType.FOUT));
        }
    }

    private void verwerkVerwerkResultaat(SelectieTaakResultaat selectieTaakResultaat, SelectieJobRunStatus status) {
        LOGGER.debug("aantalSelectieSchrijfTaken: " + status.incrementEnGetSchrijfTaken(selectieTaakResultaat.getSchrijfTaken()));
        LOGGER.debug("aantalPlaatsAfnemerindicatieTaken: " +
                status.addEnGetAantalPlaatsAfnemerindicatieTaken(selectieTaakResultaat.getAantalAfnemerindicatieTaken()));
        LOGGER.debug("aantalVerwerktePersonenNetwerk: " +
                status.addEnGetAantalVerwerktePesonenNetwerk(selectieTaakResultaat.getAantalVerwerktePersonenNetwerk()));
        status.voegOngeldigeSelectietakenToe(selectieTaakResultaat.getOngeldigeTaken());
        LOGGER.debug("aantalSelectieResultaatTaken: " + status.incrementEnGetVerwerkKlaarTaken());
    }

    private void afnemerindicatieVerwerkt(final SelectieTaakResultaat selectieTaakResultaat) {
        final Integer taakId = selectieTaakResultaat.getTaakId();
        final SelectieJobRunStatus status = selectieJobRunStatusService.getStatus();
        LOGGER.debug("aantalSelectieSchrijfTaken na afnemerindicatie schrijf: " + status.incrementEnGetSchrijfTaken(selectieTaakResultaat.getSchrijfTaken()));
        status.incrementAfnemerindicatieTaakVerwerkt(taakId);
        LOGGER.debug(
                "aantalVerwerktePersonenNetwerk: " + status.incrementAndGetAantalVerwerktePersonenNetwerk());
        LOGGER.info("afnemerindicatie verwerkt voor taak {}.", taakId);
    }
}
