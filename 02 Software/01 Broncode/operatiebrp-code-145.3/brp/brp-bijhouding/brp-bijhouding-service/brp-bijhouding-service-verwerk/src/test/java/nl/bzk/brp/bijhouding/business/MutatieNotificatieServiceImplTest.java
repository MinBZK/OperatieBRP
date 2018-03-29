/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business;

import java.util.Date;
import javax.jms.Connection;
import javax.jms.JMSException;
import nl.bzk.brp.domain.internbericht.bijhoudingsnotificatie.BijhoudingsplanNotificatieBericht;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

@RunWith(MockitoJUnitRunner.class)
public class MutatieNotificatieServiceImplTest {

    private static final ActiveMQQueue DESTINATION = new ActiveMQQueue("testQueue");
    private MutatieNotificatieServiceImpl service;
    private JmsTemplate jmsTemplate = Mockito.spy(new JmsTemplate());
    private BrokerService broker = new BrokerService();

    @Before
    public void setup() throws Exception {
        broker.addConnector("tcp://localhost:61616");
        broker.setPersistent(false);
        broker.start();

        final ActiveMQConnectionFactory testConnectionFactory = Mockito.spy(new ActiveMQConnectionFactory("tcp://localhost:61616"));
        final Connection connection = testConnectionFactory.createConnection();
        connection.start();
        jmsTemplate.setConnectionFactory(testConnectionFactory);
        jmsTemplate.setDefaultDestination(DESTINATION);
        service = new MutatieNotificatieServiceImpl(jmsTemplate);
    }

    @After
    public void tearDown() {
        try {
            broker.stop();
        } catch (final Exception e) {
        }
    }

    @Test
    public void testBijhouding() throws JMSException {
        final String
                voorbeeldBericht =
                "<brp:bhg_sysVerwerkBijhoudingsplan xmlns:brp=\"http://www.bzk.nl/brp/brp0200\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                        + "xsi:schemaLocation=\"http://www.bzk.nl/brp/brp0200 ../xsd/brp0200_bhgSysteemhandeling_Berichten.xsd\">\n"
                        + "\t<brp:stuurgegevens>\n"
                        + "\t\t<brp:zendendePartij>999901</brp:zendendePartij>\n"
                        + "\t\t<brp:zendendeSysteem>BRP</brp:zendendeSysteem>\n"
                        + "\t\t<brp:ontvangendePartij>059901</brp:ontvangendePartij>\n"
                        + "\t\t<brp:referentienummer>82d62ed8-5c88-49a4-b1cd-96c8a1d1e094</brp:referentienummer>\n"
                        + "\t\t<brp:tijdstipVerzending>2015-06-13T15:32:05.326+02:00</brp:tijdstipVerzending>\n"
                        + "\t</brp:stuurgegevens>\n"
                        + "\t<brp:administratieveHandelingPlan brp:objecttype=\"AdministratieveHandeling\">\n"
                        + "\t\t<brp:code>04001</brp:code>\n"
                        + "\t\t<brp:naam>Voltrekking huwelijk in Nederland</brp:naam>\n"
                        + "\t\t<brp:categorie>Actualisering</brp:categorie>\n"
                        + "\t\t<brp:partijCode>053001</brp:partijCode>\n"
                        + "\t\t<brp:bijhoudingsplan brp:objecttype=\"Bijhoudingsplan\">\n"
                        + "\t\t\t<brp:bijhoudingsvoorstelPartijCode>053001</brp:bijhoudingsvoorstelPartijCode>\n"
                        + "\t\t\t<brp:bijhoudingsvoorstelVerzoekbericht>\n"
                        + "\t\t\t\t<!-- Element is feitelijk Ã©Ã©n lange string (deinitie '<any processContents=\"lax\"/>'; voor leesbaarheid in "
                        + "voorbeeldbericht nu netjes opgemaakt -->\n"
                        + "\t\t\t\t<brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap xmlns:brp=\"http://www.bzk.nl/brp/brp0200\" xmlns:xsi=\"http://www"
                        + ".w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.bzk.nl/brp/brp0200 "
                        + "../brp0200_bhgHuwelijkGeregistreerdPartnerschap_Berichten.xsd\">\n"
                        + "\t\t\t\t\t<brp:stuurgegevens brp:communicatieID=\"identificatie00B\">\n"
                        + "\t\t\t\t\t\t<brp:zendendePartij>053001</brp:zendendePartij>\n"
                        + "\t\t\t\t\t\t<brp:zendendeSysteem>BZM Leverancier A</brp:zendendeSysteem>\n"
                        + "\t\t\t\t\t\t<brp:referentienummer>88409eeb-1aa5-43fc-8614-43055123a165</brp:referentienummer>\n"
                        + "\t\t\t\t\t\t<brp:tijdstipVerzending>2015-06-13T15:32:03.234+02:00</brp:tijdstipVerzending>\n"
                        + "\t\t\t\t\t</brp:stuurgegevens>\n"
                        + "\t\t\t\t\t<brp:parameters brp:communicatieID=\"identificatie01B\">\n"
                        + "\t\t\t\t\t\t<brp:verwerkingswijze>Bijhouding</brp:verwerkingswijze>\n"
                        + "\t\t\t\t\t</brp:parameters>\n"
                        + "\t\t\t\t\t<brp:voltrekkingHuwelijkInNederland brp:objecttype=\"AdministratieveHandeling\" brp:communicatieID=\"identificatie02B\">\n"
                        + "\t\t\t\t\t\t<!-- Feitgemeente Hellevoetsluis -->\n"
                        + "\t\t\t\t\t\t<brp:partijCode>053001</brp:partijCode>\n"
                        + "\t\t\t\t\t\t<brp:toelichtingOntlening>Ã‰Ã©n van de partners niet Nederlander; van rechtswege "
                        + "naamswijziging</brp:toelichtingOntlening>\n"
                        + "\t\t\t\t\t\t<brp:gedeblokkeerdeMeldingen>\n"
                        + "\t\t\t\t\t\t\t<brp:gedeblokkeerdeMelding brp:objecttype=\"GedeblokkeerdeMelding\" brp:referentieID=\"identificatie11B\" "
                        + "brp:communicatieID=\"identificatie03B\">\n"
                        + "\t\t\t\t\t\t\t\t<brp:regelCode>BR0010</brp:regelCode>\n"
                        + "\t\t\t\t\t\t\t</brp:gedeblokkeerdeMelding>\n"
                        + "\t\t\t\t\t\t</brp:gedeblokkeerdeMeldingen>\n"
                        + "\t\t\t\t\t\t<brp:bronnen>\n"
                        + "\t\t\t\t\t\t\t<brp:bron brp:objecttype=\"AdministratieveHandelingBron\" brp:communicatieID=\"Bron1\">\n"
                        + "\t\t\t\t\t\t\t\t<brp:document brp:objecttype=\"Document\" brp:communicatieID=\"identificatie04B\">\n"
                        + "\t\t\t\t\t\t\t\t\t<brp:soortNaam>Huwelijksakte</brp:soortNaam>\n"
                        + "\t\t\t\t\t\t\t\t\t<brp:identificatie>HA0530-3AA0001</brp:identificatie>\n"
                        + "\t\t\t\t\t\t\t\t\t<brp:aktenummer>3AA0001</brp:aktenummer>\n"
                        + "\t\t\t\t\t\t\t\t\t<brp:partijCode>053001</brp:partijCode>\n"
                        + "\t\t\t\t\t\t\t\t</brp:document>\n"
                        + "\t\t\t\t\t\t\t</brp:bron>\n"
                        + "\t\t\t\t\t\t\t<brp:bron brp:objecttype=\"AdministratievHandelingBron\" brp:communicatieID=\"Bron2\">\n"
                        + "\t\t\t\t\t\t\t\t<brp:document brp:objecttype=\"Document\" brp:communicatieID=\"identificatie05B\">\n"
                        + "\t\t\t\t\t\t\t\t\t<brp:soortNaam>Verklaring naamgebruik</brp:soortNaam>\n"
                        + "\t\t\t\t\t\t\t\t\t<brp:identificatie>VRZ0530-100005</brp:identificatie>\n"
                        + "\t\t\t\t\t\t\t\t\t<brp:partijCode>053001</brp:partijCode>\n"
                        + "\t\t\t\t\t\t\t\t</brp:document>\n"
                        + "\t\t\t\t\t\t\t</brp:bron>\t\n"
                        + "\t\t\t\t\t\t</brp:bronnen>\n"
                        + "\t\t\t\t\t\t<brp:acties>\n"
                        + "\t\t\t\t\t\t\t<brp:registratieAanvangHuwelijk brp:objecttype=\"Actie\" brp:communicatieID=\"identificatie06B\">\n"
                        + "\t\t\t\t\t\t\t\t<brp:bronnen>\n"
                        + "\t\t\t\t\t\t\t\t\t<brp:bron brp:objecttype=\"ActieBron\" brp:referentieID=\"Bron1\" brp:communicatieID=\"identificatie07B\"/>\n"
                        + "\t\t\t\t\t\t\t\t</brp:bronnen>\n"
                        + "\t\t\t\t\t\t\t\t<brp:huwelijk brp:objecttype=\"Relatie\" brp:communicatieID=\"identificatie08B\">\n"
                        + "\t\t\t\t\t\t\t\t\t<brp:relatie brp:communicatieID=\"identificatie09B\">\n"
                        + "\t\t\t\t\t\t\t\t\t\t<brp:datumAanvang>2015-06-13</brp:datumAanvang>\n"
                        + "\t\t\t\t\t\t\t\t\t\t<brp:gemeenteAanvangCode>0530</brp:gemeenteAanvangCode>\n"
                        + "\t\t\t\t\t\t\t\t\t</brp:relatie>\n"
                        + "\t\t\t\t\t\t\t\t\t<brp:betrokkenheden>\n"
                        + "\t\t\t\t\t\t\t\t\t\t<!-- Partner Maria; woongemeente Hellevoetsluis --> \n"
                        + "\t\t\t\t\t\t\t\t\t\t<brp:partner brp:objecttype=\"Betrokkenheid\" brp:communicatieID=\"identificatie10B\">\n"
                        + "\t\t\t\t\t\t\t\t\t\t\t<brp:persoon brp:objecttype=\"Persoon\" brp:objectSleutel=\"KLMs5+h1Ky4LT5SjOtd5v+zNgtzW6TRx\" "
                        + "brp:communicatieID=\"identificatie11B\">\n"
                        + "\t\t\t\t\t\t\t\t\t\t\t\t<brp:geslachtsnaamcomponenten>\n"
                        + "\t\t\t\t\t\t\t\t\t\t\t\t\t<brp:geslachtsnaamcomponent brp:objecttype=\"PersoonGeslachtsnaamcomponent\" "
                        + "brp:communicatieID=\"identificatie12B\">\n"
                        + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<brp:voorvoegsel>dos</brp:voorvoegsel>\n"
                        + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<brp:scheidingsteken> </brp:scheidingsteken>\n"
                        + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<brp:stam>Santos da Silva Casada</brp:stam>\n"
                        + "\t\t\t\t\t\t\t\t\t\t\t\t\t</brp:geslachtsnaamcomponent>\n"
                        + "\t\t\t\t\t\t\t\t\t\t\t\t</brp:geslachtsnaamcomponenten>\n"
                        + "\t\t\t\t\t\t\t\t\t\t\t</brp:persoon>\n"
                        + "\t\t\t\t\t\t\t\t\t\t</brp:partner>\n"
                        + "\t\t\t\t\t\t\t\t\t\t<!-- Partner Willy; woongemeente Rotterdam --> \n"
                        + "\t\t\t\t\t\t\t\t\t\t<brp:partner brp:objecttype=\"Betrokkenheid\" brp:communicatieID=\"identificatie13B\">\n"
                        + "\t\t\t\t\t\t\t\t\t\t\t<brp:persoon brp:objecttype=\"Persoon\" brp:objectSleutel=\"QRSx4+h1Ky4LT5SjOtd5v+zNgtzW6GJn\" "
                        + "brp:communicatieID=\"identificatie14B\"/>\n"
                        + "\t\t\t\t\t\t\t\t\t\t</brp:partner>\n"
                        + "\t\t\t\t\t\t\t\t\t</brp:betrokkenheden>\n"
                        + "\t\t\t\t\t\t\t\t</brp:huwelijk>\n"
                        + "\t\t\t\t\t\t\t</brp:registratieAanvangHuwelijk>\n"
                        + "\t\t\t\t\t\t\t<brp:registratieNaamgebruik brp:objecttype=\"Actie\" brp:communicatieID=\"identificatie15B\">\n"
                        + "\t\t\t\t\t\t\t\t<brp:bronnen>\n"
                        + "\t\t\t\t\t\t\t\t\t<brp:bron brp:objecttype=\"ActieBron\" brp:referentieID=\"Bron2\" brp:communicatieID=\"identificatie16B\"/>\n"
                        + "\t\t\t\t\t\t\t\t</brp:bronnen>\n"
                        + "\t\t\t\t\t\t\t\t<brp:persoon brp:objecttype=\"Persoon\" brp:objectSleutel=\"KLMs5+h1Ky4LT5SjOtd5v+zNgtzW6TRx\" "
                        + "brp:communicatieID=\"identificatie17B\">\n"
                        + "\t\t\t\t\t\t\t\t\t<brp:naamgebruik brp:communicatieID=\"identificatie18B\">\n"
                        + "\t\t\t\t\t\t\t\t\t\t<brp:code>P</brp:code>\n"
                        + "\t\t\t\t\t\t\t\t\t\t<brp:indicatieAfgeleid>J</brp:indicatieAfgeleid>\n"
                        + "\t\t\t\t\t\t\t\t\t</brp:naamgebruik>\n"
                        + "\t\t\t\t\t\t\t\t</brp:persoon>\n"
                        + "\t\t\t\t\t\t\t</brp:registratieNaamgebruik>\n"
                        + "\t\t\t\t\t\t</brp:acties>\n"
                        + "\t\t\t\t\t</brp:voltrekkingHuwelijkInNederland>\n"
                        + "\t\t\t\t</brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap>\n"
                        + "\t\t\t</brp:bijhoudingsvoorstelVerzoekbericht>\n"
                        + "\t\t\t<brp:bijhoudingsvoorstelResultaatbericht>\n"
                        + "\t\t\t\t<!-- Element is feitelijk Ã©Ã©n lange string (definitie '<any processContents=\"lax\"/>'; voor leesbaarheid in "
                        + "voorbeeldbericht nu netjes opgemaakt -->\n"
                        + "\t\t\t\t<brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R xmlns:brp=\"http://www.bzk.nl/brp/brp0200\" "
                        + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.bzk.nl/brp/brp0200 "
                        + "../brp0200_bhgHuwelijkGeregistreerdPartnerschap_Berichten.xsd\">\n"
                        + "\t\t\t\t\t<brp:stuurgegevens>\n"
                        + "\t\t\t\t\t\t<brp:zendendePartij>999901</brp:zendendePartij>\n"
                        + "\t\t\t\t\t\t<brp:zendendeSysteem>BRP</brp:zendendeSysteem>\n"
                        + "\t\t\t\t\t\t<brp:referentienummer>76d62ed5-8c55-49a4-b1cd-38c8a1d1e086</brp:referentienummer>\n"
                        + "\t\t\t\t\t\t<brp:crossReferentienummer>88409eeb-1aa5-43fc-8614-43055123a165</brp:crossReferentienummer>\n"
                        + "\t\t\t\t\t\t<brp:tijdstipVerzending>2015-06-13T15:32:04.108+02:00</brp:tijdstipVerzending>\n"
                        + "\t\t\t\t\t</brp:stuurgegevens>\n"
                        + "\t\t\t\t\t<brp:resultaat>\n"
                        + "\t\t\t\t\t\t<brp:verwerking>Geslaagd</brp:verwerking>\n"
                        + "\t\t\t\t\t\t<brp:bijhouding>Conform plan</brp:bijhouding>\n"
                        + "\t\t\t\t\t\t<brp:hoogsteMeldingsniveau>Waarschuwing</brp:hoogsteMeldingsniveau>\n"
                        + "\t\t\t\t\t</brp:resultaat>\n"
                        + "\t\t\t\t\t<brp:meldingen>\n"
                        + "\t\t\t\t\t\t<brp:melding brp:objecttype=\"Melding\" brp:referentieID=\"identificatie11B\">\n"
                        + "\t\t\t\t\t\t\t<brp:regelCode>BRBY01001</brp:regelCode>\n"
                        + "\t\t\t\t\t\t\t<brp:soortNaam>Waarschuwing</brp:soortNaam>\n"
                        + "\t\t\t\t\t\t\t<brp:melding>Persoon heeft verstrekkingsbeperking</brp:melding>\n"
                        + "\t\t\t\t\t\t</brp:melding>\n"
                        + "\t\t\t\t\t</brp:meldingen>\n"
                        + "\t\t\t\t\t<brp:voltrekkingHuwelijkInNederland brp:objecttype=\"AdministratieveHandeling\">\n"
                        + "\t\t\t\t\t\t<brp:partijCode>053001</brp:partijCode>\n"
                        + "\t\t\t\t\t\t<brp:tijdstipRegistratie>2015-06-13T15:32:04.071+02:00</brp:tijdstipRegistratie>\n"
                        + "\t\t\t\t\t\t<brp:gedeblokkeerdeMeldingen>\n"
                        + "\t\t\t\t\t\t\t<brp:gedeblokkeerdeMelding brp:objecttype=\"GedeblokkeerdeMelding\" brp:referentieID=\"identificatie03B\">\n"
                        + "\t\t\t\t\t\t\t\t<brp:regelCode>BR0010</brp:regelCode>\n"
                        + "\t\t\t\t\t\t\t\t<brp:melding>Persoon jonger dan 16 jaar</brp:melding>\n"
                        + "\t\t\t\t\t\t\t</brp:gedeblokkeerdeMelding>\n"
                        + "\t\t\t\t\t\t</brp:gedeblokkeerdeMeldingen>\n"
                        + "\t\t\t\t\t\t<brp:bijgehoudenPersonen>\n"
                        + "\t\t\t\t\t\t\t<!-- Partner Maria; woongemeente Hellevoetsluis -->\n"
                        + "\t\t\t\t\t\t\t<brp:persoon brp:objecttype=\"Persoon\">\n"
                        + "\t\t\t\t\t\t\t\t<brp:identificatienummers>\n"
                        + "\t\t\t\t\t\t\t\t\t<brp:burgerservicenummer>238651974</brp:burgerservicenummer>\n"
                        + "\t\t\t\t\t\t\t\t</brp:identificatienummers>\n"
                        + "\t\t\t\t\t\t\t</brp:persoon>\n"
                        + "\t\t\t\t\t\t</brp:bijgehoudenPersonen>\n"
                        + "\t\t\t\t\t\t<brp:bijhoudingsplan brp:objecttype=\"Bijhoudingsplan\">\n"
                        + "\t\t\t\t\t\t\t<brp:bijhoudingsvoorstelPartijCode>053001</brp:bijhoudingsvoorstelPartijCode>\n"
                        + "\t\t\t\t\t\t\t<brp:bijhoudingsplanPersonen>\n"
                        + "\t\t\t\t\t\t\t\t<brp:bijhoudingsplanPersoon brp:objecttype=\"BijhoudingsplanPersoon\">\n"
                        + "\t\t\t\t\t\t\t\t\t<!-- Partner Maria; woongemeente Hellevoetsluis -->\n"
                        + "\t\t\t\t\t\t\t\t\t<brp:persoon brp:objecttype=\"Persoon\">\n"
                        + "\t\t\t\t\t\t\t\t\t\t<brp:afgeleidAdministratief>\n"
                        + "\t\t\t\t\t\t\t\t\t\t\t<brp:tijdstipLaatsteWijziging>2015-06-13T15:32:04.071+02:00</brp:tijdstipLaatsteWijziging>\n"
                        + "\t\t\t\t\t\t\t\t\t\t</brp:afgeleidAdministratief>\n"
                        + "\t\t\t\t\t\t\t\t\t\t<brp:identificatienummers>\n"
                        + "\t\t\t\t\t\t\t\t\t\t\t<brp:burgerservicenummer>238651974</brp:burgerservicenummer>\n"
                        + "\t\t\t\t\t\t\t\t\t\t</brp:identificatienummers>\n"
                        + "\t\t\t\t\t\t\t\t\t\t<brp:bijhouding>\n"
                        + "\t\t\t\t\t\t\t\t\t\t\t<brp:partijCode>053001</brp:partijCode>\n"
                        + "\t\t\t\t\t\t\t\t\t\t</brp:bijhouding>\n"
                        + "\t\t\t\t\t\t\t\t\t</brp:persoon>\n"
                        + "\t\t\t\t\t\t\t\t\t<brp:situatieNaam>Indiener is bijhoudingspartij</brp:situatieNaam>\n"
                        + "\t\t\t\t\t\t\t\t</brp:bijhoudingsplanPersoon>\n"
                        + "\t\t\t\t\t\t\t\t<brp:bijhoudingsplanPersoon brp:objecttype=\"BijhoudingsplanPersoon\">\n"
                        + "\t\t\t\t\t\t\t\t\t<!-- Partner Willy; woongemeente Rotterdam -->\n"
                        + "\t\t\t\t\t\t\t\t\t<brp:persoon brp:objecttype=\"Persoon\">\n"
                        + "\t\t\t\t\t\t\t\t\t\t<brp:afgeleidAdministratief>\n"
                        + "\t\t\t\t\t\t\t\t\t\t\t<brp:tijdstipLaatsteWijziging>2012-11-29T09:44:26.428+01:00</brp:tijdstipLaatsteWijziging>\n"
                        + "\t\t\t\t\t\t\t\t\t\t</brp:afgeleidAdministratief>\n"
                        + "\t\t\t\t\t\t\t\t\t\t<brp:identificatienummers>\n"
                        + "\t\t\t\t\t\t\t\t\t\t\t<brp:burgerservicenummer>103962438</brp:burgerservicenummer>\n"
                        + "\t\t\t\t\t\t\t\t\t\t</brp:identificatienummers>\n"
                        + "\t\t\t\t\t\t\t\t\t\t<brp:bijhouding>\n"
                        + "\t\t\t\t\t\t\t\t\t\t\t<brp:partijCode>059901</brp:partijCode>\n"
                        + "\t\t\t\t\t\t\t\t\t\t</brp:bijhouding>\n"
                        + "\t\t\t\t\t\t\t\t\t</brp:persoon>\n"
                        + "\t\t\t\t\t\t\t\t\t<brp:situatieNaam>Opnieuw indienen</brp:situatieNaam>\n"
                        + "\t\t\t\t\t\t\t\t</brp:bijhoudingsplanPersoon>\n"
                        + "\t\t\t\t\t\t\t</brp:bijhoudingsplanPersonen>\n"
                        + "\t\t\t\t\t\t</brp:bijhoudingsplan>\n"
                        + "\t\t\t\t\t</brp:voltrekkingHuwelijkInNederland>\n"
                        + "\t\t\t\t</brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R>\n"
                        + "\t\t\t</brp:bijhoudingsvoorstelResultaatbericht>\n"
                        + "\t\t\t<brp:bijhoudingsplanPersonen>\n"
                        + "\t\t\t\t<brp:bijhoudingsplanPersoon brp:objecttype=\"BijhoudingsplanPersoon\">\n"
                        + "\t\t\t\t\t<!-- Partner Maria; woongemeente Hellevoetsluis -->\n"
                        + "\t\t\t\t\t<brp:persoon brp:objecttype=\"Persoon\">\n"
                        + "\t\t\t\t\t\t<brp:afgeleidAdministratief>\n"
                        + "\t\t\t\t\t\t\t<brp:tijdstipLaatsteWijziging>2015-06-13T15:32:04.071+02:00</brp:tijdstipLaatsteWijziging>\n"
                        + "\t\t\t\t\t\t</brp:afgeleidAdministratief>\n"
                        + "\t\t\t\t\t\t<brp:identificatienummers>\n"
                        + "\t\t\t\t\t\t\t<brp:burgerservicenummer>238651974</brp:burgerservicenummer>\n"
                        + "\t\t\t\t\t\t</brp:identificatienummers>\n"
                        + "\t\t\t\t\t\t<brp:bijhouding>\n"
                        + "\t\t\t\t\t\t\t<brp:partijCode>053001</brp:partijCode>\n"
                        + "\t\t\t\t\t\t</brp:bijhouding>\n"
                        + "\t\t\t\t\t</brp:persoon>\n"
                        + "\t\t\t\t\t<brp:situatieNaam>Indiener is bijhoudingspartij</brp:situatieNaam>\n"
                        + "\t\t\t\t</brp:bijhoudingsplanPersoon>\n"
                        + "\t\t\t\t<brp:bijhoudingsplanPersoon brp:objecttype=\"BijhoudingsplanPersoon\">\n"
                        + "\t\t\t\t\t<!-- Partner Willy; woongemeente Rotterdam -->\t\t\t\t\t\n"
                        + "\t\t\t\t\t<brp:persoon brp:objecttype=\"Persoon\">\n"
                        + "\t\t\t\t\t\t<brp:afgeleidAdministratief>\n"
                        + "\t\t\t\t\t\t\t<brp:tijdstipLaatsteWijziging>2012-11-29T09:44:26.428+01:00</brp:tijdstipLaatsteWijziging>\n"
                        + "\t\t\t\t\t\t</brp:afgeleidAdministratief>\n"
                        + "\t\t\t\t\t\t<brp:identificatienummers>\n"
                        + "\t\t\t\t\t\t\t<brp:burgerservicenummer>103962438</brp:burgerservicenummer>\n"
                        + "\t\t\t\t\t\t</brp:identificatienummers>\n"
                        + "\t\t\t\t\t\t<brp:bijhouding>\n"
                        + "\t\t\t\t\t\t\t<brp:partijCode>059901</brp:partijCode>\n"
                        + "\t\t\t\t\t\t</brp:bijhouding>\n"
                        + "\t\t\t\t\t</brp:persoon>\n"
                        + "\t\t\t\t\t<brp:situatieNaam>Opnieuw indienen</brp:situatieNaam>\n"
                        + "\t\t\t\t</brp:bijhoudingsplanPersoon>\n"
                        + "\t\t\t</brp:bijhoudingsplanPersonen>\n"
                        + "\t\t</brp:bijhoudingsplan>\n"
                        + "\t</brp:administratieveHandelingPlan>\n"
                        + "</brp:bhg_sysVerwerkBijhoudingsplan>";
        //@formatter:off
        BijhoudingsplanNotificatieBericht bijhoudingsplanNotificatieBericht = new BijhoudingsplanNotificatieBericht()
                .setOntvangendePartijCode("059901")
                .setOntvangendePartijId((short) 321)
                .setOntvangendeSysteem("BijhoudingSysteem")
                .setZendendePartijCode("000003")
                .setZendendeSysteem("BRP")
                .setReferentieNummer("82d62ed8-5c88-49a4-b1cd-96c8a1d1e094")
                .setCrossReferentieNummer(null)
                .setTijdstipVerzending(new Date())
                .setAdministratieveHandelingId(1L)
                .setVerwerkBijhoudingsplanBericht(voorbeeldBericht);
        //@formatter:on
        service.verstuurBijhoudingsNotificatie(bijhoudingsplanNotificatieBericht);
        Mockito.verify(jmsTemplate, Mockito.times(1)).send(Mockito.any(ActiveMQQueue.class), Mockito.any(MessageCreator.class));
    }

}
