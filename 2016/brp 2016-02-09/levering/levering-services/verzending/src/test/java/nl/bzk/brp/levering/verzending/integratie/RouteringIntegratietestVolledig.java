/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.verzending.integratie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Date;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.xml.ws.Endpoint;
import nl.bzk.brp.dataaccess.repository.archivering.BerichtRepository;
import nl.bzk.brp.levering.algemeen.LeveringConstanten;
import nl.bzk.brp.levering.algemeen.service.PartijService;
import nl.bzk.brp.levering.dataaccess.AbstractIntegratieTest;
import nl.bzk.brp.levering.verzending.LvgSynchronisatieVerwerkingGeeftExceptieImpl;
import nl.bzk.brp.levering.verzending.LvgSynchronisatieVerwerkingImpl;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.SysteemNaamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReferentienummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Stelsel;
import nl.bzk.brp.model.internbericht.SynchronisatieBerichtGegevens;
import nl.bzk.brp.model.operationeel.ber.BerichtModel;
import nl.bzk.brp.model.operationeel.ber.BerichtStuurgegevensGroepModel;
import nl.bzk.brp.serialisatie.JsonStringSerializer;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

@Ignore //kandidaatje vervangen door component test
public class RouteringIntegratietestVolledig extends AbstractIntegratieTest {

    private static final int LEVERINGSAUTORISATIE_GOED      = 8;
    private static final int LEVERINGSAUTORISATIE_EXCEPTION = 9;
    private static final int LEVERINGSAUTORISATIE_FOUTIEF   = 10;

    private static final String AFNEMER_QUEUE           = "AFNEMER-36101?acknowledgementModeName=SESSION_TRANSACTED";
    private static final String AFNEMER_QUEUE_EXCEPTION = "AFNEMER-36301?acknowledgementModeName=SESSION_TRANSACTED";
    private static final String AFNEMER_QUEUE_FOUTIEF   = "AFNEMER-FOUTIEF";

    private static Endpoint endpoint;
    private static Endpoint endpointMetExceptie;

    private static LvgSynchronisatieVerwerkingImpl              lvgSynchronisatieVerwerking            =
        new LvgSynchronisatieVerwerkingImpl();
    private static LvgSynchronisatieVerwerkingGeeftExceptieImpl lvgSynchronisatieVerwerkingMetExceptie =
        new LvgSynchronisatieVerwerkingGeeftExceptieImpl();

    @Inject
    private BerichtRepository berichtRepository;

    @Inject
    private PartijService partijService;

    private SynchronisatieBerichtGegevens metaGegevens = new SynchronisatieBerichtGegevens();

    private String xmlBerichtString;

    private JsonStringSerializer<SynchronisatieBerichtGegevens> jsonStringSerialiseerder =
        new JsonStringSerializer<>(SynchronisatieBerichtGegevens.class);

    @Inject
    @Named("afnemersJmsTemplate")
    private JmsTemplate afnemersJmsTemplate;

    @Test
    public final void testStappenVerwerking() throws InterruptedException {
        final int startAantalBerichten = lvgSynchronisatieVerwerking.getAantalOntvangenBerichten();
        final int verwachtResultaat = startAantalBerichten + 2;
        final String jsonMetaGegevens = jsonStringSerialiseerder.serialiseerNaarString(metaGegevens);
        final MessageCreator jmsBerichtMetAfnemerXml = maakBericht(jsonMetaGegevens);

        afnemersJmsTemplate.send(AFNEMER_QUEUE, jmsBerichtMetAfnemerXml);
        afnemersJmsTemplate.send(AFNEMER_QUEUE, jmsBerichtMetAfnemerXml);

        wachtMaximaalEenMinuutOpResultaat(verwachtResultaat);

        Assert.assertEquals(verwachtResultaat, lvgSynchronisatieVerwerking.getAantalOntvangenBerichten());

        // Controleer archivering
        int aantalBerichten = 0;
        for (final BerichtModel bericht : berichtRepository.findAll()) {
            if (bericht.getParameters().getLeveringsautorisatieId() == LEVERINGSAUTORISATIE_GOED) {
                aantalBerichten++;
            }
        }
        Assert.assertEquals(2, aantalBerichten);
    }

    private void wachtMaximaalEenMinuutOpResultaat(final int verwachtResultaat) throws InterruptedException {
        int aantalPogingen = 0;
        final int minimaalAantalPogingen = 10;
        boolean moetWachten = true;
        while (moetWachten) {
            // Asynchrone verwerking
            Thread.sleep(1000);

            if (lvgSynchronisatieVerwerking.getAantalOntvangenBerichten() == verwachtResultaat) {
                moetWachten = false;
            }
            if (aantalPogingen == 60) {
                moetWachten = false;
            } else if (aantalPogingen < minimaalAantalPogingen) {
                moetWachten = true;
            }
            aantalPogingen++;
        }

    }

    private void wachtMaximaalEenMinuutOpResultaatInExceptieService(final int verwachtResultaat)
        throws InterruptedException
    {
        int aantalPogingen = 0;
        final int minimaalAantalPogingen = 10;
        boolean moetWachten = true;
        while (moetWachten) {
            // Asynchrone verwerking
            Thread.sleep(1000);

            if (lvgSynchronisatieVerwerkingMetExceptie.getAantalOntvangenBerichten() == verwachtResultaat) {
                moetWachten = false;
            }
            if (aantalPogingen == 60) {
                moetWachten = false;
            } else if (aantalPogingen < minimaalAantalPogingen) {
                moetWachten = true;
            }
            aantalPogingen++;
        }

    }

    @Test
    public final void testStappenVerwerkingFoutieveAfnemer() throws InterruptedException {
        metaGegevens.setToegangLeveringsautorisatieIdId(LEVERINGSAUTORISATIE_FOUTIEF);
        metaGegevens.setDienstId(LEVERINGSAUTORISATIE_FOUTIEF);
        final int startAantalBerichten = lvgSynchronisatieVerwerking.getAantalOntvangenBerichten();
        final String jsonMetaGegevens = jsonStringSerialiseerder.serialiseerNaarString(metaGegevens);
        final MessageCreator jmsBerichtMetAfnemerXml = maakBericht(jsonMetaGegevens);

        afnemersJmsTemplate.send(AFNEMER_QUEUE_FOUTIEF, jmsBerichtMetAfnemerXml);

        wachtMaximaalEenMinuutOpResultaat(startAantalBerichten);

        Assert.assertEquals(startAantalBerichten + 0, lvgSynchronisatieVerwerking.getAantalOntvangenBerichten());

        // Controleer archivering
        int aantalBerichten = 0;
        for (final BerichtModel bericht : berichtRepository.findAll()) {
            if (bericht.getParameters().getLeveringsautorisatieId() == LEVERINGSAUTORISATIE_FOUTIEF) {
                aantalBerichten++;
            }
        }
        Assert.assertEquals(0, aantalBerichten);
    }

    @Test
    public final void testStappenVerwerkingExceptieInOntvanger() throws InterruptedException {
        metaGegevens.setToegangLeveringsautorisatieIdId(LEVERINGSAUTORISATIE_EXCEPTION);
        metaGegevens.setDienstId(LEVERINGSAUTORISATIE_EXCEPTION);
        final String jsonMetaGegevens = jsonStringSerialiseerder.serialiseerNaarString(metaGegevens);
        final MessageCreator jmsBerichtMetAfnemerXml = maakBericht(jsonMetaGegevens);

        afnemersJmsTemplate.send(AFNEMER_QUEUE_EXCEPTION, jmsBerichtMetAfnemerXml);

        wachtMaximaalEenMinuutOpResultaatInExceptieService(1);

        // Controleer archivering
        // Doordat er oneway communicatie plaatsvindt met de ontvanger/verwerker, wordt er niet gewacht op een
        // response. Zodoende zal een exceptie in de verwerking niet leiden tot een rolback.
        int berichtenDieGearchiveerdWordenDoorOneWayCommunicatieMetOntvanger = 0;
        for (final BerichtModel bericht : berichtRepository.findAll()) {
            if (bericht.getParameters().getLeveringsautorisatieId() == LEVERINGSAUTORISATIE_EXCEPTION) {
                berichtenDieGearchiveerdWordenDoorOneWayCommunicatieMetOntvanger++;
            }
        }
        Assert.assertEquals(1, berichtenDieGearchiveerdWordenDoorOneWayCommunicatieMetOntvanger);
    }

    /**
     * Maakt een JMS bericht.
     *
     * @param jsonMetaGegevens de json meta gegevens
     * @return de message creator
     */
    private MessageCreator maakBericht(
            final String jsonMetaGegevens) {
        final MessageCreator jmsBerichtMetAfnemerXml = new MessageCreator() {

            @Override
            public final Message createMessage(final Session session) throws JMSException {
                final Message message = session.createMessage();
                message.setStringProperty(LeveringConstanten.JMS_PROPERTY_KEY_XML_BERICHT, xmlBerichtString);
                message.setStringProperty(LeveringConstanten.JMS_PROPERTY_KEY_BERICHT_GEGEVENS, jsonMetaGegevens);
                message.setJMSType(Stelsel.BRP.toString());
                return message;
            }
        };
        return jmsBerichtMetAfnemerXml;
    }


    @BeforeClass
    public static void createServerOvereenkomstigMetAutLevXML() {
        final String adres = "http://localhost:8988/kennisgeving";
        endpoint = Endpoint.publish(adres, lvgSynchronisatieVerwerking);

        final String adresMetExceptie = "http://localhost:9191/kennisgeving";
        endpointMetExceptie = Endpoint.publish(adresMetExceptie, lvgSynchronisatieVerwerkingMetExceptie);
    }

    @AfterClass
    public static void cleanUp() {
        endpoint.stop();
        endpointMetExceptie.stop();
    }

    @Before
    public final void setup() throws IOException {
        final Partij verzendendePartij = partijService.vindPartijOpCode(199903);
        final Partij ontvangendePartij = partijService.vindPartijOpCode(1401);

        final PartijAttribuut zendendePartij = new PartijAttribuut(verzendendePartij);
        final SysteemNaamAttribuut zendendeSysteem = new SysteemNaamAttribuut("BRP");
        final PartijAttribuut ontvangendePartijAttribuut = new PartijAttribuut(ontvangendePartij);
        final SysteemNaamAttribuut ontvangendeSysteem = new SysteemNaamAttribuut("applicatie");
        final ReferentienummerAttribuut referentienummer = new ReferentienummerAttribuut("referentienr");
        final ReferentienummerAttribuut crossReferentienummer = null;
        final DatumTijdAttribuut datumTijdVerzending = new DatumTijdAttribuut(new Date());
        final DatumTijdAttribuut datumTijdOntvangst = null;

        final BerichtStuurgegevensGroepModel stuurgegevensGroepModel = new BerichtStuurgegevensGroepModel(
                zendendePartij.getWaarde().getID(),
                zendendeSysteem, ontvangendePartijAttribuut.getWaarde().getID(), ontvangendeSysteem, referentienummer,
                crossReferentienummer, datumTijdVerzending, datumTijdOntvangst);

        metaGegevens.setAdministratieveHandelingId(1L);
        metaGegevens.setToegangLeveringsautorisatieIdId(LEVERINGSAUTORISATIE_GOED);
        metaGegevens.setDienstId(LEVERINGSAUTORISATIE_GOED);
        metaGegevens.setGeleverdePersoonsIds(Arrays.asList(1));
        metaGegevens.setStelsel(Stelsel.BRP);
        metaGegevens.setStuurgegevens(stuurgegevensGroepModel);
        metaGegevens.setAdministratieveHandelingTijdstipRegistratie(DatumTijdAttribuut.bouwDatumTijd(2010, 1, 1));
        metaGegevens.setDatumAanvangMaterielePeriodeResultaat(new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.gisteren()));
        metaGegevens.setDatumTijdAanvangFormelePeriodeResultaat(DatumTijdAttribuut.bouwDatumTijd(2010, 1, 1));
        metaGegevens.setDatumTijdEindeFormelePeriodeResultaat(DatumTijdAttribuut.bouwDatumTijd(2010, 1, 1));
        metaGegevens.setSoortSynchronisatie(new SoortSynchronisatieAttribuut(SoortSynchronisatie.MUTATIEBERICHT));
        metaGegevens.setSoortDienst(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING);
        metaGegevens.getStuurgegevens().setOntvangendePartijId(StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_UTRECHT.getWaarde().getID());
        metaGegevens.getStuurgegevens().setZendendePartijId(StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM.getWaarde().getID());

        xmlBerichtString = leesBestandAlsString("test_bericht.xml");
    }

    /**
     * Leest een tekst bestand als string.
     *
     * @param bestandsnaam de bestandsnaam
     * @return de string
     * @throws IOException de iO exception
     */
    private String leesBestandAlsString(final String bestandsnaam) throws IOException {
        final StringBuilder resultaat = new StringBuilder();

        try (final BufferedReader lezer = new BufferedReader(
                new InputStreamReader(
                        Thread.currentThread().getContextClassLoader().getResourceAsStream(bestandsnaam))
        )) {
            String line;
            while ((line = lezer.readLine()) != null) {
                resultaat.append(line);
            }
        }

        return resultaat.toString();
    }

}
