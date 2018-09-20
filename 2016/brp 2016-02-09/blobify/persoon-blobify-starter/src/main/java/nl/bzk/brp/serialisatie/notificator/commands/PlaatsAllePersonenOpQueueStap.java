/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.notificator.commands;

import javax.inject.Inject;

import nl.bzk.brp.serialisatie.notificator.app.ContextParameterNames;
import nl.bzk.brp.serialisatie.notificator.service.JmsService;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * De stap waarin alle personen op de queue wordt gezet.
 */
@Component
public class PlaatsAllePersonenOpQueueStap implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlaatsAllePersonenOpQueueStap.class);

    private static final Integer DEFAULT_AANTAL_PERSOON_IDS_PER_BATCH = Integer.valueOf(100000);

    @Inject
    private JmsService jmsService;

    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    // REDEN: Implementatie van Apache Commons Chain Command, welke de signature bepaald van de methode.
    @Override
    public final boolean execute(final Context context) throws Exception {

        LOGGER.debug("### Start PlaatsAllePersonenOpQueueStap ###");

        final Integer aantalIdsPerBatch = getAantalIdsPerBatch(context);
        final Integer huidigPersoonId = getVanafPersoonId(context);
        final Integer tijdInSecondenTussenBatches = getTijdTussenBatches(context);

        jmsService.creeerEnPubliceerJmsBerichtenVoorAllePersonen(aantalIdsPerBatch,
                                                                 tijdInSecondenTussenBatches,
                                                                 huidigPersoonId);
        LOGGER.info("### Einde PlaatsAllePersonenOpQueueStap ###");

        return false;
    }

    /**
     * Bepaalt de tijd tussen de batches. Als deze op de context staat wordt deze gebruikt, anders 0.
     *
     * @param context De stappencontext.
     * @return De tijd tussen de batches.
     */
    private Integer getTijdTussenBatches(final Context context) {
        final String tijdTussenBatchesString = (String) context.get(ContextParameterNames.TIJD_TUSSEN_BATCHES);
        Integer tijdTussenBatches;
        if (tijdTussenBatchesString == null) {
            tijdTussenBatches = Integer.valueOf(0);
        } else {
            tijdTussenBatches = Integer.valueOf(tijdTussenBatchesString);
        }
        return tijdTussenBatches;
    }

    /**
     * Bepaalt het aantal persoon id's per batch, als deze op de context staat wordt deze gebruikt. Anders wordt de
     * default gebruikt.
     *
     * @param context De stappencontext.
     * @return Het aantal id's per batch.
     */
    private Integer getAantalIdsPerBatch(final Context context) {
        final String aantalIdsPerBatchString = (String) context.get(ContextParameterNames.AANTAL_IDS_PER_BATCH);
        Integer aantalIdsPerBatch;
        if (aantalIdsPerBatchString == null) {
            aantalIdsPerBatch = DEFAULT_AANTAL_PERSOON_IDS_PER_BATCH;
        } else {
            aantalIdsPerBatch = Integer.valueOf(aantalIdsPerBatchString);
        }
        return aantalIdsPerBatch;
    }

    /**
     * Bepaalt het vanaf persoon id. Dit is het id waar we beginnen.
     *
     * @param context De stappencontext.
     * @return Het vanaf persoon id.
     */
    private Integer getVanafPersoonId(final Context context) {
        final String vanafPersoonIdString = (String) context.get(ContextParameterNames.VANAF_PERSOON_ID);
        Integer vanafPersoonId;
        if (vanafPersoonIdString == null) {
            vanafPersoonId = Integer.valueOf(1);
        } else {
            vanafPersoonId = Integer.valueOf(vanafPersoonIdString);
        }
        return vanafPersoonId;
    }

}
