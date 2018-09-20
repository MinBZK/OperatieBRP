/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.notificator.commands;

import java.util.List;
import javax.inject.Inject;

import nl.bzk.brp.serialisatie.notificator.app.ContextParameterNames;
import nl.bzk.brp.serialisatie.notificator.exceptions.CommandException;
import nl.bzk.brp.serialisatie.notificator.service.JmsService;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * De stap waarin een lijst met personen op de queue wordt gezet.
 */
@Component
public class PlaatsPersonenOpQueueStap implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlaatsPersonenOpQueueStap.class);

    @Inject
    private JmsService jmsService;


    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    // REDEN: Implementatie van Apache Commons Chain Command, welke de signature bepaald van de methode.
    @Override
    public final boolean execute(final Context context) throws Exception {
        LOGGER.debug("### Start PlaatsPersonenOpQueueStap ###");

        try {
            final List<Integer> persoonIdLijst = (List<Integer>) context.get(ContextParameterNames.PERSOON_ID_LIJST);
            jmsService.creeerEnPubliceerJmsBerichtenVoorPersonen(persoonIdLijst);
        } catch (Exception e) {
            throw new CommandException("Het aanmaken en publiceren van de JMS berichten is mislukt.", e);
        }

        return false;
    }

}
