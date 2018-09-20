/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.bijhouding;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import javax.inject.Inject;
import nl.bzk.brp.bijhouding.business.bericht.MarshallServiceImpl;
import nl.bzk.brp.bijhouding.business.stappen.administratievehandeling.integratie.AbstractIntegratieTest;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.model.MaterieleHistorieSet;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Bijhoudingssituatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartijAttribuut;
import nl.bzk.brp.model.bijhouding.NotificeerBijhoudingsplanBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.HisPersoonBijhoudingModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class MarshallServiceImplTest extends AbstractIntegratieTest {

    @Inject
    private MarshallServiceImpl marshallService = new MarshallServiceImpl();

    @Mock
    private BijhoudingBerichtContext context;

    @Mock
    private List<PersoonHisVolledigImpl> persoonLijst;

    @Mock
    private MaterieleHistorieSet<HisPersoonBijhoudingModel> bijhoudingHistorie;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        final PartijAttribuut partijAttribuut = new PartijAttribuut(
            new Partij(new NaamEnumeratiewaardeAttribuut(""), new SoortPartijAttribuut(SoortPartij.B_R_P_VOORZIENING), new PartijCodeAttribuut(36101),
                null, null, null, JaNeeAttribuut.JA, JaNeeAttribuut.JA, null));
        when(context.getPartij()).thenReturn(partijAttribuut);
        when(context.getBerichtReferentieNummer()).thenReturn("88409eeb-1aa5-43fc-8614-43055123a165");

        when(context.getBijgehoudenPersonen()).thenReturn(persoonLijst);
        final PersoonHisVolledigImpl persoonHisVolledig = mock(PersoonHisVolledigImpl.class);
        when(persoonLijst.get(0)).thenReturn(persoonHisVolledig);
        when(persoonHisVolledig.getPersoonBijhoudingHistorie()).thenReturn(bijhoudingHistorie);
        final HisPersoonBijhoudingModel hisPersoonBijhoudingModel = mock(HisPersoonBijhoudingModel.class);
        when(bijhoudingHistorie.getActueleRecord()).thenReturn(hisPersoonBijhoudingModel);
        when(hisPersoonBijhoudingModel.getBijhoudingspartij()).thenReturn(partijAttribuut);
    }

    @Test
    public void testMarshallingNotificatieberichtAutomatisch() throws Exception {
        final NotificeerBijhoudingsplanBericht notificatieBericht = MaakNotificatieBerichtStap.maakNotificatieBericht(context,
            Bijhoudingssituatie.AUTOMATISCHE_FIAT);

        final String xmlBericht = marshallService.maakBericht(notificatieBericht);
        // TODO POC xmlBericht zou door MaakNotificatieBerichtStap al xsd valide gemaakt moeten zijn.
        final String xsdValideXmlBericht = xmlBericht.replaceAll("(?s)<brp:geboorteInNederland.*<brp:notificatieBRPLevering\\/>", "");

        // TODO POC goede asserts
        assertTrue(xsdValideXmlBericht.startsWith("<brp:bhg_fiaNotificeerBijhoudingsplan xmlns:brp=\"http://www.bzk.nl/brp/brp0200\">\n"
            + "  <brp:stuurgegevens>\n"
            + "    <brp:zendendePartij>036101</brp:zendendePartij>\n"
            + "    <brp:zendendeSysteem>BRP</brp:zendendeSysteem>\n"
            + "    <brp:ontvangendePartij>036101</brp:ontvangendePartij>\n"
            + "    <brp:ontvangendeSysteem>BRP</brp:ontvangendeSysteem>\n"
            + "    <brp:referentienummer>88409eeb-1aa5-43fc-8614-43055123a165</brp:referentienummer>\n"
            + "    <brp:tijdstipVerzending>"));

        assertTrue(xsdValideXmlBericht.contains("</brp:tijdstipVerzending>\n"
            + "  </brp:stuurgegevens>\n"
            + "  <brp:parameters>\n"
            + "    <brp:redenNotificatie>Automatische fiat</brp:redenNotificatie>\n"
            + "  </brp:parameters>\n"
            + "  <brp:administratieveHandelingPlan brp:objecttype=\"AdministratieveHandeling\">\n"
            + "    <brp:code>99904</brp:code>\n"
            + "    <brp:naam>GBA - Sluiting huwelijk geregistreerd partnerschap</brp:naam>\n"
            + "    <brp:categorie>Actualisering</brp:categorie>\n"
            + "    <brp:partijCode>036101</brp:partijCode>\n"
            + "    <brp:bijhoudingsplan brp:objecttype=\"Bijhoudingsplan\">\n"
            + "      <brp:bijhoudingsvoorstelPartijCode>036101</brp:bijhoudingsvoorstelPartijCode>\n"
            + "      <brp:bijhoudingsvoorstelBericht><bijhoudingsvoorstelBerichtInhoud/></brp:bijhoudingsvoorstelBericht>\n"
            + "      <brp:bijhoudingsvoorstelBerichtResultaat><bijhoudingsvoorstelBerichtResultaatInhoud/></brp:bijhoudingsvoorstelBerichtResultaat>\n"
            + "      <brp:bijhoudingsplanPersonen>\n"
            + "        <brp:bijhoudingsplanPersoon brp:objecttype=\"BijhoudingsplanPersoon\">\n"
            + "          <brp:persoon brp:objecttype=\"Persoon\">\n"
            + "            <brp:afgeleidAdministratief>"));

        assertTrue(xsdValideXmlBericht.endsWith("</brp:tijdstipLaatsteWijziging>\n"
            + "            </brp:afgeleidAdministratief>\n"
            + "            <brp:identificatienummers>\n"
            + "              <brp:burgerservicenummer>000000001</brp:burgerservicenummer>\n"
            + "            </brp:identificatienummers>\n"
            + "            <brp:bijhouding>\n"
            + "              <brp:partijCode>036101</brp:partijCode>\n"
            + "            </brp:bijhouding>\n"
            + "          </brp:persoon>\n"
            + "          <brp:situatieNaam>Automatische fiat</brp:situatieNaam>\n"
            + "        </brp:bijhoudingsplanPersoon>\n"
            + "      </brp:bijhoudingsplanPersonen>\n"
            + "    </brp:bijhoudingsplan>\n"
            + "  </brp:administratieveHandelingPlan>\n"
            + "</brp:bhg_fiaNotificeerBijhoudingsplan>"));
    }

    @Test
    public void testMarshallingNotificatieberichtOpnieuw() throws Exception {
        final NotificeerBijhoudingsplanBericht notificatieBericht = MaakNotificatieBerichtStap.maakNotificatieBericht(context,
            Bijhoudingssituatie.OPNIEUW_INDIENEN);

        final String xmlBericht = marshallService.maakBericht(notificatieBericht);

        assertTrue(xmlBericht.contains("<brp:redenNotificatie>Opnieuw indienen</brp:redenNotificatie>"));
    }
}
