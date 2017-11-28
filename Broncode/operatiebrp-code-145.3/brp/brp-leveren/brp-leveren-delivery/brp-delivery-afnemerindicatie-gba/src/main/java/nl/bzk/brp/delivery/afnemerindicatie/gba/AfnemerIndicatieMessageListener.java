/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.afnemerindicatie.gba;

import java.util.UUID;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.jms.TextMessageReader;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.gba.domain.afnemerindicatie.AfnemerindicatieOnderhoudAntwoord;
import nl.bzk.brp.gba.domain.afnemerindicatie.AfnemerindicatieOnderhoudOpdracht;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.JmsUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * AfnemerIndicatie message listener.
 */
public final class AfnemerIndicatieMessageListener implements MessageListener {

    /**
     * JMS property waarop de bericht referentie wordt gezet.
     */
    static final String BERICHT_REFERENTIE = "iscBerichtReferentie";
    /**
     * JMS property waarop de correlatie referentie wordt gezet.
     */
    static final String CORRELATIE_REFERENTIE = "iscCorrelatieReferentie";

    private final AfnemerindicatieOnderhoudOpdrachtVerwerker verwerker;
    private final JmsTemplate antwoordTemplate;
    private final JsonStringSerializer jsonSerializer = new JsonStringSerializer();

    /**
     * Constructor.
     * @param gbaAfnemerindicatieAntwoordTemplate jmstemplate voor gba afnemerindicatie antwoor
     * @param verwerker afnemerindicatie service
     */
    @Inject
    public AfnemerIndicatieMessageListener(final JmsTemplate gbaAfnemerindicatieAntwoordTemplate,
                                           final AfnemerindicatieOnderhoudOpdrachtVerwerker verwerker) {
        this.antwoordTemplate = gbaAfnemerindicatieAntwoordTemplate;
        this.verwerker = verwerker;
    }

    @Override
    @Transactional(value = "masterTransactionManager", propagation = Propagation.REQUIRED)
    public void onMessage(final Message message) {
        BrpNu.set(DatumUtil.nuAlsZonedDateTime());

        final TextMessageReader reader = new TextMessageReader(message);
        final AfnemerindicatieOnderhoudOpdracht opdracht =
                jsonSerializer.deserialiseerVanuitString(reader.readMessage(), AfnemerindicatieOnderhoudOpdracht.class);
        final String berichtReferentie = leesBerichtReferentie(message);
        final AfnemerindicatieOnderhoudAntwoord antwoord = verwerker.verwerk(opdracht);

        if (antwoord != null) {
            antwoordTemplate.send(session -> {
                final Message resultaat = session.createTextMessage(jsonSerializer.serialiseerNaarString(antwoord));
                resultaat.setStringProperty(BERICHT_REFERENTIE, UUID.randomUUID().toString());
                resultaat.setStringProperty(CORRELATIE_REFERENTIE, berichtReferentie);
                return resultaat;
            });
        }
    }

    private String leesBerichtReferentie(final Message message) {
        try {
            return message.getStringProperty(BERICHT_REFERENTIE);
        } catch (final JMSException e) {
            throw JmsUtils.convertJmsAccessException(e);
        }
    }
}
