/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.vrijbericht.gba;

import java.util.UUID;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import nl.bzk.algemeenbrp.util.common.jms.TextMessageReader;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import nl.bzk.brp.gba.domain.vrijbericht.VrijBerichtAntwoord;
import nl.bzk.brp.gba.domain.vrijbericht.VrijBerichtOpdracht;
import nl.bzk.brp.service.vrijbericht.VrijBerichtBericht;
import nl.bzk.brp.service.vrijbericht.VrijBerichtResultaat;
import nl.bzk.brp.service.vrijbericht.VrijBerichtService;
import nl.bzk.brp.service.vrijbericht.VrijBerichtVerzoek;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.JmsUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * VrijBericht message listener.
 */
public final class VrijBerichtMessageListener implements MessageListener {

    /**
     * JMS property waarop de bericht referentie wordt gezet.
     */
    static final String BERICHT_REFERENTIE = "iscBerichtReferentie";
    /**
     * JMS property waarop de correlatie referentie wordt gezet.
     */
    static final String CORRELATIE_REFERENTIE = "iscCorrelatieReferentie";

    private final JmsTemplate antwoordTemplate;
    private final JsonStringSerializer jsonSerializer = new JsonStringSerializer();
    private final VrijBerichtService vrijBerichtService;


    /**
     * Constructor.
     * @param gbaAfnemerindicatieAntwoordTemplate jmstemplate voor gba vrij bericht antwoord
     * @param vrijBerichtService vrij bericht service
     */
    @Inject
    public VrijBerichtMessageListener(final JmsTemplate gbaAfnemerindicatieAntwoordTemplate, final VrijBerichtService vrijBerichtService) {
        this.antwoordTemplate = gbaAfnemerindicatieAntwoordTemplate;
        this.vrijBerichtService = vrijBerichtService;
    }

    @Override
    @Transactional(value = "masterTransactionManager", propagation = Propagation.REQUIRED)
    public void onMessage(final Message message) {
        final TextMessageReader reader = new TextMessageReader(message);
        final VrijBerichtOpdracht opdracht = jsonSerializer.deserialiseerVanuitString(reader.readMessage(), VrijBerichtOpdracht.class);
        final String berichtReferentie = leesBerichtReferentie(message);
        final VrijBerichtResultaat resultaat = vrijBerichtService.verwerkVerzoek(maakVrijBerichtVerzoek(opdracht));
        antwoordTemplate.send(session -> {
            final VrijBerichtAntwoord antwoord = maakVrijBerichtAntwoord(resultaat, opdracht.getReferentienummer());
            final Message jmsMessage = session.createTextMessage(jsonSerializer.serialiseerNaarString(antwoord));
            jmsMessage.setStringProperty(BERICHT_REFERENTIE, UUID.randomUUID().toString());
            jmsMessage.setStringProperty(CORRELATIE_REFERENTIE, berichtReferentie);
            return jmsMessage;
        });
    }

    private VrijBerichtAntwoord maakVrijBerichtAntwoord(final VrijBerichtResultaat resultaat, final String referentienummer) {
        final VrijBerichtAntwoord antwoord = new VrijBerichtAntwoord();
        antwoord.setGeslaagd(resultaat.getMeldingen().isEmpty());
        antwoord.setReferentienummer(referentienummer);

        return antwoord;
    }

    private VrijBerichtVerzoek maakVrijBerichtVerzoek(final VrijBerichtOpdracht opdracht) {
        final VrijBerichtBericht vrijBericht = new VrijBerichtBericht();
        vrijBericht.setSoortNaam("GBA partij");
        vrijBericht.setInhoud(opdracht.getBericht());

        final VrijBerichtVerzoek verzoek = new VrijBerichtVerzoek();
        verzoek.getParameters().setZenderVrijBericht(opdracht.getVerzendendePartijCode());
        verzoek.getParameters().setOntvangerVrijBericht(opdracht.getOntvangendePartijCode());
        verzoek.getParameters().setCommunicatieId(opdracht.getReferentienummer());
        verzoek.getStuurgegevens().setReferentieNummer(opdracht.getReferentienummer());
        verzoek.getStuurgegevens().setZendendePartijCode(opdracht.getVerzendendePartijCode());
        verzoek.getStuurgegevens().setZendendSysteem("ISC");
        verzoek.setVrijBericht(vrijBericht);

        return verzoek;
    }

    private String leesBerichtReferentie(final Message message) {
        try {
            return message.getStringProperty(BERICHT_REFERENTIE);
        } catch (final JMSException e) {
            throw JmsUtils.convertJmsAccessException(e);
        }
    }
}
