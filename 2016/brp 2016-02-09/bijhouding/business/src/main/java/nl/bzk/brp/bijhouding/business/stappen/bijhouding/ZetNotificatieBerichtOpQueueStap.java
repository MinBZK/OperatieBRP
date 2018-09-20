/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.bijhouding;

import java.util.UUID;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.Resultaat;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.dataaccess.repository.ToegangLeveringsautorisatieRepository;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.SysteemNaamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReferentienummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.internbericht.NotificatieBerichtGegevens;
import nl.bzk.brp.model.operationeel.ber.BerichtStuurgegevensGroepModel;
import nl.bzk.brp.serialisatie.JsonStringSerializer;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

/**
 * De stap waarin het uitgaande notificatiebericht op de queue wordt gezet voor verzending.
 */
@Component
public class ZetNotificatieBerichtOpQueueStap {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private JmsTemplate afnemersJmsTemplate;

    @Inject
    private ToegangLeveringsautorisatieRepository toegangLeveringsautorisatieRepository;

    @Inject
    private ReferentieDataRepository referentieDataRepository;

    // Bij release Bernadette versturen we nog geen notificatieberichten
    @SuppressWarnings("checkstyle:explicitinitialization")
    // REDEN: het is wel zo duidelijk om hier expliciet aan te geven dat het verzenden van notificatiebericht UIT staat
    private boolean verzendNotificatieberichten = false;

    private JsonStringSerializer<NotificatieBerichtGegevens> jsonStringSerialiseerder =
        new JsonStringSerializer<>(NotificatieBerichtGegevens.class);

    /**
     * Voer de stap uit.
     * @param context de bijhoudingberichtcontext
     * @return Resultaat
     */
    public Resultaat voerStapUit(final BijhoudingBerichtContext context) {
        final String xmlBericht = context.getXmlBericht();

        if (xmlBericht != null) {
            final PersoonHisVolledigImpl persoonHisVolledig = context.getBijgehoudenPersonen().get(0);
            final PartijAttribuut partijAttribuut = persoonHisVolledig.getPersoonBijhoudingHistorie().getActueleRecord().getBijhoudingspartij();
            // TODO POC xmlBericht zou door MaakNotificatieBerichtStap al xsd valide gemaakt moeten zijn.
            final String xsdValideXmlBericht = xmlBericht.replaceAll("(?s)<brp:geboorteInNederland.*<brp:notificatieBRPLevering\\/>", "");
            final String jmsMetaGegevens = maakMetaGegevens(partijAttribuut);
            zetBerichtOpQueue(partijAttribuut.getWaarde(), xsdValideXmlBericht, jmsMetaGegevens);

            LOGGER.debug("Bericht op de queue gezet.");
        }

        return Resultaat.LEEG;
    }

    /**
     * Maakt de gegevens die mee worden gegeven aan het JMS bericht.
     *
     * @param partijAttribuut   de ontvangende partijattribuut
     * @return de metagegevens als string
     */
    @Regels({ Regel.VR00045, Regel.VR00047, Regel.VR00048 })
    private String maakMetaGegevens(final PartijAttribuut partijAttribuut) {
        final NotificatieBerichtGegevens metaGegevens = new NotificatieBerichtGegevens();

        final BerichtStuurgegevensGroepBericht berichtStuurgegevensGroepBericht = maakStuurgegevensVoorAntwoordBericht(partijAttribuut);
        final BerichtStuurgegevensGroepModel berichtStuurgegevensGroepModel = new BerichtStuurgegevensGroepModel(berichtStuurgegevensGroepBericht);
        metaGegevens.setStuurgegevens(berichtStuurgegevensGroepModel);
        if (berichtStuurgegevensGroepBericht.getZendendePartij() != null) {
            berichtStuurgegevensGroepModel.setZendendePartijId(berichtStuurgegevensGroepBericht.getZendendePartij().getWaarde().getID());
        }
        if (berichtStuurgegevensGroepBericht.getOntvangendePartij() != null) {
            berichtStuurgegevensGroepModel.setOntvangendePartijId(berichtStuurgegevensGroepBericht.getOntvangendePartij().getWaarde().getID());
        }

        return jsonStringSerialiseerder.serialiseerNaarString(metaGegevens);
    }

    /**
     * Maakt de stuurgegevens aan voor het uitgaande bericht.
     *
     * @return Bericht stuurgegevens.
     * @param partijAttribuut   de ontvangende partijattribuut
     */
    @Regels(Regel.VR00050)
    private BerichtStuurgegevensGroepBericht maakStuurgegevensVoorAntwoordBericht(final PartijAttribuut partijAttribuut) {
        final BerichtStuurgegevensGroepBericht stuurgegevensGroepUitgaand = new BerichtStuurgegevensGroepBericht();

        // Zendende systeem en partij:
        stuurgegevensGroepUitgaand.setZendendeSysteem(SysteemNaamAttribuut.BRP);
        final Partij partijBrpVoorziening = referentieDataRepository.vindPartijOpCode(PartijCodeAttribuut.BRP_VOORZIENING);
        stuurgegevensGroepUitgaand.setZendendePartij(new PartijAttribuut(partijBrpVoorziening));

        stuurgegevensGroepUitgaand.setOntvangendePartij(partijAttribuut);

        // Referentienummers:
        stuurgegevensGroepUitgaand.setReferentienummer(new ReferentienummerAttribuut(UUID.randomUUID().toString()));
        return stuurgegevensGroepUitgaand;
    }

    private String haalWillekeurigAfleverpuntUitDatabase() {
        return toegangLeveringsautorisatieRepository.haalAlleToegangLeveringsautorisatieOp().get(0).getAfleverpunt().toString();
    }

    /**
     * Zet het xmlbericht op een queue.
     *
     * @param partij              de ontvangende partij
     * @param uitgaandeXmlBericht het uitgaandeXmlBericht
     * @param metaGegevens        de metaGegevens
     */
    private void zetBerichtOpQueue(final Partij partij, final String uitgaandeXmlBericht, final String metaGegevens)
    {
        // TODO POC Bericht met de juiste waarden op de juiste queue zetten.

//        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = leveringAutorisatie.getToegangLeveringsautorisatie();
//        final Stelsel kanaal = toegangLeveringsautorisatie.getLeveringsautorisatie().getStelsel();

        final MessageCreator jmsBerichtMetAfnemerXml = new MessageCreator() {
            @Override
            public final Message createMessage(final Session session) throws JMSException {
                final TextMessage message = session.createTextMessage();
                message.setText("Test tekst voor Notificatie");

//                message.setStringProperty(LeveringConstanten.JMS_PROPERTY_KEY_XML_BERICHT, uitgaandeXmlBericht);
                message.setStringProperty("afnemerXmlBericht", uitgaandeXmlBericht);

//                message.setStringProperty(LeveringConstanten.JMS_PROPERTY_KEY_BERICHT_GEGEVENS, metaGegevens);
                message.setStringProperty("gegevens", metaGegevens);

//                message.setJMSType(kanaal.toString());
                message.setJMSType("BRP");

//                message.setStringProperty(LeveringConstanten.JMS_PROPERTY_PARTIJ_CODE, afnemer.getCode().getWaarde().toString());
                message.setStringProperty("partijCode", "28101");
//
//                if (kanaal == Kanaal.BRP && afleverwijzeModel.getUri() != null) {
//                    message.setStringProperty(LeveringConstanten.JMS_PROPERTY_BRP_AFLEVER_URI, afleverwijzeModel.getUri().getWaarde());
                    // TODO POC afleveradres uit de juiste database tabel halen
                    message.setStringProperty("brpAfleverURI", haalWillekeurigAfleverpuntUitDatabase());
//                }
                return message;
            }
        };

        if (verzendNotificatieberichten) {
            // Met ingang van release Carice moeten we notificatieberichten sturen en kan de else-tak verdwijnen.
            afnemersJmsTemplate.send(partij.getQueueNaam(), jmsBerichtMetAfnemerXml);
            LOGGER.debug("Notificatiebericht op queue gezet met naam {}.", partij.getQueueNaam());
        } else {
            LOGGER.info("Het volgende bericht zou verstuurd worden:\n" + uitgaandeXmlBericht);
        }
    }
}
