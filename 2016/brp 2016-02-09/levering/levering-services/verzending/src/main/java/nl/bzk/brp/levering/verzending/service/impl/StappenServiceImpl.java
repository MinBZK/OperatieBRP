/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.verzending.service.impl;

import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.TextMessage;
import nl.bzk.brp.levering.verzending.context.BerichtContext;
import nl.bzk.brp.levering.verzending.service.StappenService;
import nl.bzk.brp.levering.verzending.stappen.ArchiveerStap;
import nl.bzk.brp.levering.verzending.stappen.ProtocolleerStap;
import nl.bzk.brp.levering.verzending.stappen.VerrijkBerichtStap;
import nl.bzk.brp.levering.verzending.stappen.VerzendBRPStap;
import nl.bzk.brp.levering.verzending.stappen.VerzendLO3Stap;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.logging.MDC;
import nl.bzk.brp.logging.MDCVeld;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Stelsel;
import org.springframework.stereotype.Service;

/**
 * Implementatie voor het uitvoeren van de verzend stappen.
 *
 * @brp.bedrijfsregel R1991
 */
@Service
@Regels(Regel.R1991)
public class StappenServiceImpl implements StappenService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private VerrijkBerichtStap verrijkBerichtStap;

    @Inject
    private ArchiveerStap archiveerStap;

    @Inject
    private ProtocolleerStap protocolleerStap;

    @Inject
    private VerzendBRPStap verzendBRPStap;

    @Inject
    private VerzendLO3Stap verzendLO3Stap;

    /**
     * {@inheritDoc}
     */
    @Override
    public final void voerStappenUit(final BerichtContext berichtContext) throws Exception {
        MDC.put(MDCVeld.MDC_APPLICATIE_NAAM, "verzending");
        MDC.put(MDCVeld.MDC_PARTIJ_CODE, String.valueOf(berichtContext.getOntvangendePartijCode()));

        LOGGER.debug("Start stappen om bericht naar afnemer '{}' te verzenden", berichtContext.getOntvangendePartijCode());

        // TODO POC-Bijhouding moet beter
        final Message jmsBericht = berichtContext.getJmsBericht();
        if (jmsBericht instanceof TextMessage) {
            final TextMessage textMessage = (TextMessage) jmsBericht;
            if ("Test tekst voor Notificatie".equals(textMessage.getText())) {
                verzendBRPStap.processNotificatie(berichtContext);
                LOGGER.debug("Notificatie afgerond");
                return;
            }
        }

        verrijkBerichtStap.process(berichtContext);
        LOGGER.debug("Verrijk bericht stap voltooid voor afnemer '{}'", berichtContext.getOntvangendePartijCode());

        archiveerStap.process(berichtContext);
        LOGGER.debug("Archiveer stap voltooid voor afnemer '{}'", berichtContext.getOntvangendePartijCode());

        protocolleerStap.process(berichtContext);
        LOGGER.debug("Protocolleer stap voltooid voor afnemer '{}'", berichtContext.getOntvangendePartijCode());

        final String jmsType = berichtContext.getJmsBericht().getJMSType();
        if (Stelsel.BRP.toString().equals(jmsType)) {
            verzendBRPStap.process(berichtContext);
            LOGGER.debug("Verzend BRP stap voltooid voor afnemer '{}'", berichtContext.getOntvangendePartijCode());
        } else if (Stelsel.GBA.toString().equals(jmsType)) {
            verzendLO3Stap.process(berichtContext);
            LOGGER.debug("Verzend LO3 stap voltooid voor afnemer '{}'", berichtContext.getOntvangendePartijCode());
        } else {
            LOGGER.warn("Onbekend JMSType; geen verzend stap aangeroepen");
        }

        LOGGER.debug("Einde stappen om bericht naar afnemer '{}' te verzenden", berichtContext.getOntvangendePartijCode());

        MDC.remove(MDCVeld.MDC_PARTIJ_CODE);
        MDC.remove(MDCVeld.MDC_APPLICATIE_NAAM);
    }
}
