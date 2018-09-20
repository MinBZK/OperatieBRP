/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.synchronisatie.stappen.persoon;

import java.util.Collections;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import nl.bzk.brp.levering.algemeen.LeveringConstanten;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.synchronisatie.dto.synchronisatie.SynchronisatieBerichtContext;
import nl.bzk.brp.levering.synchronisatie.dto.synchronisatie.SynchronisatieResultaat;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Stelsel;
import nl.bzk.brp.model.hisvolledig.predikaat.HistorieVanafPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.internbericht.SynchronisatieBerichtGegevens;
import nl.bzk.brp.model.levering.VolledigBericht;
import nl.bzk.brp.model.operationeel.ber.BerichtStuurgegevensGroepModel;
import nl.bzk.brp.model.synchronisatie.GeefSynchronisatiePersoonBericht;
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
public class ZetBerichtOpQueueStap extends AbstractBerichtVerwerkingStap<GeefSynchronisatiePersoonBericht,
    SynchronisatieBerichtContext, SynchronisatieResultaat>
{
    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private JmsTemplate afnemersJmsTemplate;

    private JsonStringSerializer<SynchronisatieBerichtGegevens> jsonStringSerialiseerder =
        new JsonStringSerializer<>(SynchronisatieBerichtGegevens.class);

    @Override
    public final boolean voerStapUit(final GeefSynchronisatiePersoonBericht onderwerp,
        final SynchronisatieBerichtContext context,
        final SynchronisatieResultaat resultaat)
    {
        final Leveringinformatie leveringAutorisatie = context.getLeveringinformatie();
        final VolledigBericht volledigBericht = context.getVolledigBericht();
        final String xmlBericht = context.getXmlBericht();

        final String jmsMetaGegevens = maakMetaGegevens(leveringAutorisatie, volledigBericht);

        zetBerichtOpQueue(xmlBericht, jmsMetaGegevens, leveringAutorisatie);
        return DOORGAAN;
    }

    /**
     * Maakt de gegevens die mee worden gegeven aan het JMS bericht.
     *
     * @param leveringAutorisatie de levering autorisatie
     * @param volledigBericht     het volledigbericht
     * @return de metagegevens als string
     * @brp.bedrijfsregel VR00045
     * @brp.bedrijfsregel VR00046
     * @brp.bedrijfsregel VR00047
     * @brp.bedrijfsregel VR00048
     */
    @Regels({ Regel.VR00045, Regel.VR00046, Regel.VR00047, Regel.VR00048 })
    private String maakMetaGegevens(final Leveringinformatie leveringAutorisatie, final VolledigBericht volledigBericht) {
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = leveringAutorisatie.getToegangLeveringsautorisatie();
        final SynchronisatieBerichtGegevens metaGegevens =
            new SynchronisatieBerichtGegevens(null, toegangLeveringsautorisatie.getID());

        final DatumTijdAttribuut tsReg = volledigBericht.getAdministratieveHandeling().getTijdstipRegistratie();

        final BerichtStuurgegevensGroepModel berichtStuurgegevensGroepModel = new BerichtStuurgegevensGroepModel(volledigBericht.getStuurgegevens());
        metaGegevens.setStuurgegevens(berichtStuurgegevensGroepModel);
        berichtStuurgegevensGroepModel.setZendendePartijId(volledigBericht.getStuurgegevens().getZendendePartij().getWaarde().getID());
        berichtStuurgegevensGroepModel.setOntvangendePartijId(volledigBericht.getStuurgegevens().getOntvangendePartij().getWaarde().getID());
        metaGegevens.setStelsel(toegangLeveringsautorisatie.getLeveringsautorisatie().getStelsel());
        metaGegevens.setGeleverdePersoonsIds(Collections.singletonList(volledigBericht.getAdministratieveHandeling()
            .getBijgehoudenPersonen().get(0).getID()));
        metaGegevens.setAdministratieveHandelingTijdstipRegistratie(tsReg);
        final DatumAttribuut materieleResultaat = zoekMaterieelVanafMoment(volledigBericht);
        DatumEvtDeelsOnbekendAttribuut datumAanvangMaterielePeriodeResultaat = null;
        if (materieleResultaat != null) {
            datumAanvangMaterielePeriodeResultaat = new DatumEvtDeelsOnbekendAttribuut(materieleResultaat);
        }
        metaGegevens.setDatumAanvangMaterielePeriodeResultaat(datumAanvangMaterielePeriodeResultaat);
        metaGegevens.setDatumTijdAanvangFormelePeriodeResultaat(tsReg);
        metaGegevens.setDatumTijdEindeFormelePeriodeResultaat(tsReg);

        //altijd volledigbericht
        metaGegevens.setSoortSynchronisatie(new SoortSynchronisatieAttribuut(SoortSynchronisatie.VOLLEDIGBERICHT));
        metaGegevens.setDienstId(leveringAutorisatie.getDienst().getID());
        metaGegevens.setSoortDienst(leveringAutorisatie.getDienst().getSoort());

        return jsonStringSerialiseerder.serialiseerNaarString(metaGegevens);
    }

    /**
     * Voor volledigbericht zoek naar peilmomentMaterieelResultaat. Hiervoor is de aanname dat er een MaterieleHistorieVanafPredikaat aanwezig is in de
     * predikaten van de view.
     *
     * @param volledigBericht het volledigbericht
     * @return het moment indien aanwezig
     */
    private DatumAttribuut zoekMaterieelVanafMoment(final VolledigBericht volledigBericht) {
        final PersoonHisVolledigView view = volledigBericht.getAdministratieveHandeling().getBijgehoudenPersonen().get(0);

        DatumAttribuut materieelVanafMoment = null;
        final HistorieVanafPredikaat historieVanafPredikaat = view
            .getPredikaatVanType(HistorieVanafPredikaat.class);
        if (historieVanafPredikaat != null) {
            materieelVanafMoment = historieVanafPredikaat.getLeverenVanafMoment();
        }

        return materieelVanafMoment;
    }

    /**
     * Zet het xmlbericht op een queue.
     *
     * @param uitgaandeXmlBericht het uitgaandeXmlBericht
     * @param metaGegevens        de metaGegevens
     * @param leveringinformatie  de levering autorisatie informatie
     */
    private void zetBerichtOpQueue(final String uitgaandeXmlBericht, final String metaGegevens,
        final Leveringinformatie leveringinformatie)
    {
        final Partij afnemer = leveringinformatie.getToegangLeveringsautorisatie().getGeautoriseerde().getPartij();
        final String afnemerNaam = afnemer.getNaam().getWaarde();

        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = leveringinformatie.getToegangLeveringsautorisatie();
        final Stelsel stelsel = toegangLeveringsautorisatie.getLeveringsautorisatie().getStelsel();
        LOGGER.info("Zet bericht op de queue voor afnemer {} en kanaal {}", afnemerNaam, stelsel.getNaam());
        final MessageCreator jmsBerichtMetAfnemerXml = new MessageCreator() {
            @Override
            public final Message createMessage(final Session session) throws JMSException {
                final Message message = session.createMessage();
                message.setStringProperty(LeveringConstanten.JMS_PROPERTY_KEY_XML_BERICHT, uitgaandeXmlBericht);
                message.setStringProperty(LeveringConstanten.JMS_PROPERTY_KEY_BERICHT_GEGEVENS, metaGegevens);
                message.setJMSType(stelsel.toString());
                final Leveringsautorisatie leveringsautorisatie = toegangLeveringsautorisatie.getLeveringsautorisatie();
                message.setStringProperty(LeveringConstanten.JMS_PROPERTY_PARTIJ_CODE,
                    toegangLeveringsautorisatie.getGeautoriseerde().getPartij().getCode().getWaarde().toString());
                message.setStringProperty(LeveringConstanten.JMS_PROPERTY_LEVERINGS_AUTORISATIE_ID, leveringsautorisatie.getID().toString());
                if (leveringsautorisatie.getProtocolleringsniveau() != null) {
                    message.setStringProperty(LeveringConstanten.JMS_PROPERTY_PROTOCOLLERINGNIVEAU, leveringsautorisatie
                        .getProtocolleringsniveau().name());
                }
                if (stelsel == Stelsel.BRP) {
                    message
                        .setStringProperty(LeveringConstanten.JMS_PROPERTY_BRP_AFLEVER_URI, toegangLeveringsautorisatie.getAfleverpunt().getWaarde());
                }
                return message;
            }
        };
        afnemersJmsTemplate.send(afnemer.getQueueNaam(), jmsBerichtMetAfnemerXml);
        LOGGER.debug("Bericht op queue gezet met naam {}.", afnemer.getQueueNaam());
    }
}
