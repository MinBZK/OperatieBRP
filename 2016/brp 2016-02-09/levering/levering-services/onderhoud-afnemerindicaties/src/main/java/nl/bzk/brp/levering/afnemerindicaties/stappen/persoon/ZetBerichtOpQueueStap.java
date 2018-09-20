/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.stappen.persoon;

import java.util.Collections;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import nl.bzk.brp.levering.afnemerindicaties.service.OnderhoudAfnemerindicatiesBerichtContext;
import nl.bzk.brp.levering.afnemerindicaties.service.OnderhoudAfnemerindicatiesResultaat;
import nl.bzk.brp.levering.algemeen.LeveringConstanten;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Stelsel;
import nl.bzk.brp.model.internbericht.SynchronisatieBerichtGegevens;
import nl.bzk.brp.model.levering.VolledigBericht;
import nl.bzk.brp.model.operationeel.ber.BerichtStuurgegevensGroepModel;
import nl.bzk.brp.model.synchronisatie.RegistreerAfnemerindicatieBericht;
import nl.bzk.brp.serialisatie.JsonStringSerializer;
import nl.bzk.brp.webservice.business.stappen.AbstractBerichtVerwerkingStap;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;


/**
 * De stap waarin het bericht op de JMS queue wordt gezet.
 *
 * @brp.bedrijfsregel R1615
 */
@Regels(Regel.R1615)
public class ZetBerichtOpQueueStap extends AbstractBerichtVerwerkingStap<RegistreerAfnemerindicatieBericht,
    OnderhoudAfnemerindicatiesBerichtContext, OnderhoudAfnemerindicatiesResultaat>
{

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private JmsTemplate jmsTemplate;

    private JsonStringSerializer<SynchronisatieBerichtGegevens> jsonStringSerialiseerder =
        new JsonStringSerializer<>(SynchronisatieBerichtGegevens.class);

    @Override
    public final boolean voerStapUit(final RegistreerAfnemerindicatieBericht onderwerp,
        final OnderhoudAfnemerindicatiesBerichtContext context,
        final OnderhoudAfnemerindicatiesResultaat resultaat)
    {
        final VolledigBericht volledigBericht = context.getVolledigBericht();
        final String xmlBericht = context.getXmlBericht();
        final Leveringinformatie leveringAutorisatie = context.getLeveringinformatie();
        final String jmsMetaGegevens = maakMetaGegevens(leveringAutorisatie, volledigBericht, context);

        zetBerichtOpQueue(xmlBericht, jmsMetaGegevens, leveringAutorisatie);

        LOGGER.debug("Bericht op de queue gezet.");

        return DOORGAAN;
    }

    /**
     * Maakt de gegevens die mee worden gegeven aan het JMS bericht.
     *
     * @param leveringAutorisatie de levering autorisatie
     * @param volledigBericht     het volledigbericht
     * @param context             verwerking context
     * @return de metagegevens als string
     * @brp.bedrijfsregel VR00045
     * @brp.bedrijfsregel VR00047
     * @brp.bedrijfsregel VR00048
     */
    @Regels({ Regel.VR00045, Regel.VR00047, Regel.VR00048 })
    private String maakMetaGegevens(final Leveringinformatie leveringAutorisatie, final VolledigBericht volledigBericht,
        final OnderhoudAfnemerindicatiesBerichtContext context)
    {
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = leveringAutorisatie.getToegangLeveringsautorisatie();
        final SynchronisatieBerichtGegevens metaGegevens =
            new SynchronisatieBerichtGegevens(null, toegangLeveringsautorisatie.getID());

        final DatumTijdAttribuut tsReg = volledigBericht.getAdministratieveHandeling().getTijdstipRegistratie();
        final BerichtStuurgegevensGroepModel berichtStuurgegevensGroepModel = new BerichtStuurgegevensGroepModel(volledigBericht.getStuurgegevens());
        metaGegevens.setStuurgegevens(berichtStuurgegevensGroepModel);
        berichtStuurgegevensGroepModel.setZendendePartijId(volledigBericht.getStuurgegevens().getZendendePartij().getWaarde().getID());
        berichtStuurgegevensGroepModel.setOntvangendePartijId(volledigBericht.getStuurgegevens().getOntvangendePartij().getWaarde().getID());
        metaGegevens.setStelsel(leveringAutorisatie.getToegangLeveringsautorisatie().getLeveringsautorisatie().getStelsel());
        metaGegevens.setGeleverdePersoonsIds(Collections.singletonList(volledigBericht.getAdministratieveHandeling()
            .getBijgehoudenPersonen().get(0).getID()));
        metaGegevens.setAdministratieveHandelingTijdstipRegistratie(tsReg);
        metaGegevens.setDatumAanvangMaterielePeriodeResultaat(context.getDatumAanvangMaterielePeriode());
        metaGegevens.setDatumTijdAanvangFormelePeriodeResultaat(tsReg);
        metaGegevens.setDatumTijdEindeFormelePeriodeResultaat(tsReg);
        metaGegevens.setSoortSynchronisatie(new SoortSynchronisatieAttribuut(volledigBericht.geefSoortSynchronisatie()));
        metaGegevens.setDienstId(leveringAutorisatie.getDienst().getID());
        metaGegevens.setSoortDienst(leveringAutorisatie.getDienst().getSoort());

        return jsonStringSerialiseerder.serialiseerNaarString(metaGegevens);
    }

    /**
     * Zet het xmlbericht op een queue.
     *
     * @param uitgaandeXmlBericht het uitgaandeXmlBericht
     * @param metaGegevens        de metaGegevens
     * @param leveringInformatie de levering autorisatie
     */
    private void zetBerichtOpQueue(final String uitgaandeXmlBericht, final String metaGegevens,
        final Leveringinformatie leveringInformatie)
    {
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = leveringInformatie.getToegangLeveringsautorisatie();
        final Leveringsautorisatie leveringsautorisatie = toegangLeveringsautorisatie.getLeveringsautorisatie();
        final Partij afnemer = toegangLeveringsautorisatie.getGeautoriseerde().getPartij();
        final String afnemerNaam = afnemer.getNaam().getWaarde();

        final Stelsel stelsel = leveringsautorisatie.getStelsel();
        LOGGER.info("Zet bericht op de queue voor afnemer {} en kanaal {}", afnemerNaam, stelsel.getNaam());

        final MessageCreator messageCreator = new MessageCreator() {
            @Override
            public final Message createMessage(final Session session) throws JMSException {
                final Message message = session.createMessage();
                message.setStringProperty(LeveringConstanten.JMS_PROPERTY_KEY_XML_BERICHT, uitgaandeXmlBericht);
                message.setStringProperty(LeveringConstanten.JMS_PROPERTY_KEY_BERICHT_GEGEVENS, metaGegevens);
                message.setJMSType(stelsel.toString());

                message.setStringProperty(LeveringConstanten.JMS_PROPERTY_PARTIJ_CODE, toegangLeveringsautorisatie.getGeautoriseerde().getPartij()
                    .getCode().getWaarde().toString());
                message.setStringProperty(LeveringConstanten.JMS_PROPERTY_LEVERINGS_AUTORISATIE_ID, leveringsautorisatie.getID().toString());

                if (leveringsautorisatie.getProtocolleringsniveau() != null) {
                    message.setStringProperty(LeveringConstanten.JMS_PROPERTY_PROTOCOLLERINGNIVEAU, leveringsautorisatie
                        .getProtocolleringsniveau().name());
                }
                if (stelsel == Stelsel.BRP && toegangLeveringsautorisatie.getAfleverpunt() != null) {
                    message.setStringProperty(LeveringConstanten.JMS_PROPERTY_BRP_AFLEVER_URI,
                        toegangLeveringsautorisatie.getAfleverpunt().getWaarde());
                }
                return message;
            }
        };

        jmsTemplate.send(afnemer.getQueueNaam(), messageCreator);
        LOGGER.debug("Bericht op queue gezet met naam {}.", afnemer.getQueueNaam());


    }
}
