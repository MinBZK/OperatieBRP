/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.bevraging;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.bzk.brp.business.dto.bevraging.OpvragenPersoonResultaat;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Landcode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Woonplaatscode;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Land;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Plaats;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestLand;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPlaats;
import nl.bzk.brp.model.bericht.ber.BerichtMeldingBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.PartnerBericht;
import nl.bzk.brp.model.operationeel.kern.BetrokkenheidModel;
import nl.bzk.brp.model.operationeel.kern.FamilierechtelijkeBetrekkingModel;
import nl.bzk.brp.model.operationeel.kern.HuwelijkModel;
import nl.bzk.brp.model.operationeel.kern.KindModel;
import nl.bzk.brp.model.operationeel.kern.OuderModel;
import nl.bzk.brp.model.operationeel.kern.OuderOuderlijkGezagGroepModel;
import nl.bzk.brp.model.operationeel.kern.PartnerModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.utils.XmlUtils;
import nl.bzk.brp.web.AntwoordBerichtFactory;
import nl.bzk.brp.web.AntwoordBerichtFactoryImpl;
import nl.bzk.brp.web.bevraging.VraagDetailsPersoonAntwoordBericht;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.w3c.dom.Document;

public class VraagDetailsPersoonAntwoordBindingTest extends
        AbstractVraagBerichtBindingUitIntegratieTest<VraagDetailsPersoonAntwoordBericht>
{

    private AntwoordBerichtFactory antwoordBerichtFactory;

    @Before
    public void init() {
        antwoordBerichtFactory = new AntwoordBerichtFactoryImpl();
    }

    @Override
    public Class<VraagDetailsPersoonAntwoordBericht> getBindingClass() {
        return VraagDetailsPersoonAntwoordBericht.class;
    }

    @Test
    public void testOutBindingMetLeegResultaat() throws JiBXException {
        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(null);
        final VraagDetailsPersoonAntwoordBericht response =
            (VraagDetailsPersoonAntwoordBericht) antwoordBerichtFactory.bouwAntwoordBericht(
                    bouwIngaandBericht(SoortBericht.A_L_G_GEEF_DETAILS_PERSOON_V), resultaat);
        response.setStuurgegevens(bouwBerichtStuurgegevens());
        String xml = marshalObject(response);
        Assert.assertNotNull(xml);

        String verwachteWaarde = bouwVerwachteAntwoordBericht("20120325143506789");
        Assert.assertEquals(verwachteWaarde, xml);

        valideerTegenSchema(xml);
    }

    @Test
    public void testOutBindingMetMelding() throws JiBXException {
        List<Melding> meldingen = new ArrayList<Melding>();
        List<BerichtMeldingBericht> modelMeldingen = new ArrayList<BerichtMeldingBericht>();
        Melding melding = new Melding(SoortMelding.INFORMATIE, MeldingCode.ALG0001, "TEST", null, null);
        meldingen.add(melding);
        modelMeldingen.add(new BerichtMeldingBericht(melding));

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(meldingen);
        final VraagDetailsPersoonAntwoordBericht response =
            (VraagDetailsPersoonAntwoordBericht) antwoordBerichtFactory.bouwAntwoordBericht(
                    bouwIngaandBericht(SoortBericht.A_L_G_GEEF_DETAILS_PERSOON_V), resultaat);
        response.setStuurgegevens(bouwBerichtStuurgegevens());
        response.setMeldingen(modelMeldingen);
        String xml = marshalObject(response);
        Assert.assertNotNull(xml);

        String verwachteWaarde = bouwVerwachteAntwoordBericht("20120325143506789", "I", "ALG0001", "TEST", null);
        Assert.assertEquals(verwachteWaarde, xml);

        valideerTegenSchema(xml);
    }

    @Test
    public void testOutBindingMetPersoonInResultaat() throws Exception {
        Partij gemeente =
            new TestPartij(null, SoortPartij.GEMEENTE, new GemeenteCode((short) 10), null, null, null, null, null,
                    StatusHistorie.X, StatusHistorie.X);

        Land nederland = new TestLand(new Landcode((short) 31), null, null, null, null);

        Plaats amsterdam = new TestPlaats(new Woonplaatscode((short) 20), null, null, null);

        OpvragenPersoonResultaat resultaat =
            bouwOpvragenPersoonResultaatVoorCompleetPersoonMetRelaties(gemeente, nederland, amsterdam,
                    SoortRelatie.HUWELIJK);

        VraagDetailsPersoonAntwoordBericht response =
            (VraagDetailsPersoonAntwoordBericht) antwoordBerichtFactory.bouwAntwoordBericht(
                    bouwIngaandBericht(SoortBericht.A_L_G_GEEF_DETAILS_PERSOON_V), resultaat);

        String xml = marshalObject(response);
        Assert.assertNotNull(xml);
        Document document = XmlUtils.bouwDocument(xml);
        // Voor het gemak alle bsn's vervangen.
        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "burgerservicenummer", "123456789");

        System.out.println(xml);
        valideerTegenSchema(xml);

        Assert.assertTrue("brp:ALG_GeefDetailsPersoon_VR root node bestaat niet",
                XmlUtils.isNodeAanwezig("/brp:ALG_GeefDetailsPersoon_VR", document));
        Assert.assertTrue("stuf:zender root node bestaat niet",
                XmlUtils.isNodeAanwezig("//stuf:zender", document));
        Assert.assertTrue("persoon node bestaat niet", XmlUtils.isNodeAanwezig("//brp:persoon", document));
        String verwachteWaarde =
            bouwVerwachteAntwoordBericht("20120325143506789", null, null, null,
                    "vraagDetailsPersoonMetRelatiesAntwoordBindingResultaat_metVerzendendId.xml");
        Assert.assertEquals(verwachteWaarde, xml);

    }

    @Test
    public void testOutBindingMinimaalPersoonMetHuwelijk() throws JiBXException, ParseException {
        PersoonModel minimaalPersoon = bouwMinimaalPersoon();
        ReflectionTestUtils.setField(minimaalPersoon, "iD", 1);

        HuwelijkGeregistreerdPartnerschapStandaardGroepBericht gegevens =
            new HuwelijkGeregistreerdPartnerschapStandaardGroepBericht();
        gegevens.setDatumAanvang(new Datum(20000312));
        gegevens.setLandAanvang(new TestLand(new Landcode((short) 31), null, null, null, null));

        HuwelijkBericht minimaalHuwelijkBericht = new HuwelijkBericht();
        minimaalHuwelijkBericht.setStandaard(gegevens);
        HuwelijkModel minimaalHuwelijk = new HuwelijkModel(minimaalHuwelijkBericht);

        Set<BetrokkenheidModel> minimaleBetrokkenheden = new HashSet<BetrokkenheidModel>();

        PartnerModel partner =
            new PartnerModel(new PartnerBericht(), minimaalHuwelijk, bouwMinimaal_Objectype_PersoonBetrokkene_AntwoordA());

        minimaleBetrokkenheden.add(partner);
        ReflectionTestUtils.setField(minimaalHuwelijk, "betrokkenheden", minimaleBetrokkenheden);
        ReflectionTestUtils.setField(minimaalPersoon, "betrokkenheden", minimaleBetrokkenheden);

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(null);
        Set<PersoonModel> gevondenPersonen = new HashSet<PersoonModel>();
        gevondenPersonen.add(minimaalPersoon);
        resultaat.setGevondenPersonen(gevondenPersonen);

        final VraagDetailsPersoonAntwoordBericht response =
            (VraagDetailsPersoonAntwoordBericht) antwoordBerichtFactory.bouwAntwoordBericht(
                    bouwIngaandBericht(SoortBericht.A_L_G_GEEF_DETAILS_PERSOON_V), resultaat);

        String xml = marshalObject(response);
        Assert.assertNotNull(xml);

        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "tijdstipLaatsteWijziging", "2011-11-11T11:11:11.000");
        String verwachteWaarde =
            bouwVerwachteAntwoordBericht("20120325143506789", null, null, null,
                    "vraagDetailsPersoonMinimaalMetHuwelijkAntwoordBindingResultaat.xml");
        valideerTegenSchema(xml);

        Assert.assertEquals(verwachteWaarde, xml);
    }

    @Test
    public void testOutBindingMinimaalPersoonMetFamRechtBet() throws JiBXException, ParseException {
        PersoonModel minimaalPersoon = bouwMinimaalPersoon();
        PersoonModel moeder = bouwMinimaal_Objectype_PersoonBetrokkene_AntwoordA();
        PersoonModel dochter = bouwMinimaal_Objectype_PersoonBetrokkene_AntwoordA();

        ReflectionTestUtils.setField(minimaalPersoon, "iD", 1);
        ReflectionTestUtils.setField(moeder, "iD", 2);
        ReflectionTestUtils.setField(dochter, "iD", 3);

        // Relaties
        FamilierechtelijkeBetrekkingBericht relatieOudersBericht = new FamilierechtelijkeBetrekkingBericht();
        FamilierechtelijkeBetrekkingModel relatieOuders = new FamilierechtelijkeBetrekkingModel(relatieOudersBericht);

        FamilierechtelijkeBetrekkingBericht relatieDochterBericht = new FamilierechtelijkeBetrekkingBericht();
        FamilierechtelijkeBetrekkingModel relatieDochter = new FamilierechtelijkeBetrekkingModel(relatieDochterBericht);

        // Betrokkenheden
        KindModel minPersoonNaarOuder = new KindModel(new KindBericht(), relatieOuders, minimaalPersoon);

        OuderModel minPersoonNaarDochter = new OuderModel(new OuderBericht(), relatieDochter, minimaalPersoon);
        minPersoonNaarDochter.setOuderlijkGezag(new OuderOuderlijkGezagGroepModel(JaNee.JA));

        OuderModel moederBetrokkenheid = new OuderModel(new OuderBericht(), relatieOuders, moeder);
        moederBetrokkenheid.setOuderlijkGezag(new OuderOuderlijkGezagGroepModel(JaNee.JA));

        KindModel dochterBetrokkenheid = new KindModel(new KindBericht(), relatieDochter, dochter);

        ReflectionTestUtils.setField(relatieDochter, "betrokkenheden", new HashSet<BetrokkenheidModel>());
        ReflectionTestUtils.invokeMethod(relatieDochter.getBetrokkenheden(), "add", minPersoonNaarDochter);
        ReflectionTestUtils.invokeMethod(relatieDochter.getBetrokkenheden(), "add", dochterBetrokkenheid);

        ReflectionTestUtils.setField(relatieOuders, "betrokkenheden", new HashSet<BetrokkenheidModel>());
        ReflectionTestUtils.invokeMethod(relatieOuders.getBetrokkenheden(), "add", minPersoonNaarOuder);
        ReflectionTestUtils.invokeMethod(relatieOuders.getBetrokkenheden(), "add", moederBetrokkenheid);

        ReflectionTestUtils.setField(minimaalPersoon, "betrokkenheden", new HashSet<BetrokkenheidModel>());
        ReflectionTestUtils.invokeMethod(minimaalPersoon.getBetrokkenheden(), "add", minPersoonNaarDochter);
        ReflectionTestUtils.invokeMethod(minimaalPersoon.getBetrokkenheden(), "add", minPersoonNaarOuder);

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(null);
        Set<PersoonModel> gevondenPersonen = new HashSet<PersoonModel>();
        gevondenPersonen.add(minimaalPersoon);
        resultaat.setGevondenPersonen(gevondenPersonen);

        final VraagDetailsPersoonAntwoordBericht response =
            (VraagDetailsPersoonAntwoordBericht) antwoordBerichtFactory.bouwAntwoordBericht(
                    bouwIngaandBericht(SoortBericht.A_L_G_GEEF_DETAILS_PERSOON_V), resultaat);

        String xml = marshalObject(response);
        Assert.assertNotNull(xml);

        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "tijdstipLaatsteWijziging", "2011-11-01T11:11:11.100");
        String verwachteWaarde =
            bouwVerwachteAntwoordBericht("20120325143506789", null, null, null,
                    "vraagDetailsPersoonMinimaalMetFamRechtBetrAntwoordBindingResultaat.xml");
        Assert.assertEquals(verwachteWaarde, xml);

        valideerTegenSchema(xml);
    }

    @Test
    public void testIndicatieBinding() throws Exception {
        Partij gemeente = new TestPartij(null, null, new GemeenteCode((short) 10), null, null, null, null, null, null,
                                         null);
        Land nederland = new TestLand(new Landcode((short) 31), null, null, null, null);
        Plaats amsterdam = new TestPlaats(new Woonplaatscode((short) 20), null, null, null);

        Set<PersoonModel> gevondenPersonen = new HashSet<PersoonModel>();
        PersoonModel opTeVragenPersoon = maakPersoon(1, gemeente, amsterdam, nederland, "6235467");

        // Indicaties
        voegIndicatieToeAanPersoon(opTeVragenPersoon, SoortIndicatie.INDICATIE_VERSTREKKINGSBEPERKING, Ja.J);

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(null);
        gevondenPersonen.add(opTeVragenPersoon);
        resultaat.setGevondenPersonen(gevondenPersonen);

        final VraagDetailsPersoonAntwoordBericht response =
            (VraagDetailsPersoonAntwoordBericht) antwoordBerichtFactory.bouwAntwoordBericht(
                    bouwIngaandBericht(SoortBericht.A_L_G_GEEF_DETAILS_PERSOON_V), resultaat);

        String xml = marshalObject(response);
        Assert.assertNotNull(xml);
        Document document = XmlUtils.bouwDocument(xml);
        // Voor het gemak alle bsn's vervangen.
        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "burgerservicenummer", "123456789");

        String verwachteWaarde =
            bouwVerwachteAntwoordBericht("20120325143506789", null, null, null,
                    "vraagDetailsPersoonMetIndicatiesAntwoordBindingResultaat.xml");
        Assert.assertEquals(verwachteWaarde, xml);

        valideerTegenSchema(xml);
    }

    @Override
    public String getBerichtElementNaam() {
        return "ALG_GeefDetailsPersoon_VR";
    }

}
