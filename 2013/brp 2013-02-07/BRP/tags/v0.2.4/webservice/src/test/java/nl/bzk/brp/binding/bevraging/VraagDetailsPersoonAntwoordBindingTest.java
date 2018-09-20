/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.bevraging;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.bzk.brp.business.dto.bevraging.OpvragenPersoonResultaat;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.attribuuttype.Gemeentecode;
import nl.bzk.brp.model.attribuuttype.Ja;
import nl.bzk.brp.model.attribuuttype.Landcode;
import nl.bzk.brp.model.attribuuttype.PlaatsCode;
import nl.bzk.brp.model.groep.bericht.PersoonGeslachtsaanduidingGroepBericht;
import nl.bzk.brp.model.groep.bericht.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonGeslachtsaanduidingGroepModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonAdresHisModel;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.operationeel.BetrokkenheidModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonAdresModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.RelatieModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Geslachtsaanduiding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Land;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Plaats;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortIndicatie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortRelatie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortbericht;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortmelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.utils.XmlUtils;
import nl.bzk.brp.web.AntwoordBerichtFactory;
import nl.bzk.brp.web.AntwoordBerichtFactoryImpl;
import nl.bzk.brp.web.bevraging.VraagDetailsPersoonAntwoord;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.w3c.dom.Document;


public class VraagDetailsPersoonAntwoordBindingTest extends
        AbstractVraagBerichtBindingUitIntegratieTest<VraagDetailsPersoonAntwoord>
{

    private AntwoordBerichtFactory antwoordBerichtFactory;

    @Before
    public void init() {
        antwoordBerichtFactory = new AntwoordBerichtFactoryImpl();
    }

    @Override
    public Class<VraagDetailsPersoonAntwoord> getBindingClass() {
        return VraagDetailsPersoonAntwoord.class;
    }

    @Test
    public void testOutBindingMetLeegResultaat() throws JiBXException {
        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(null);
        final VraagDetailsPersoonAntwoord response =
                (VraagDetailsPersoonAntwoord) antwoordBerichtFactory.bouwAntwoordBericht(
                        bouwIngaandBericht(Soortbericht.VRAAG_DETAILSPERSOON_BEVRAGEN), resultaat);
        response.setBerichtStuurgegevensGroep(bouwBerichtStuurgegevens());
        String xml = marshalObject(response);
        Assert.assertNotNull(xml);

        String verwachteWaarde = bouwVerwachteAntwoordBericht("20120325143506789");
        Assert.assertEquals(verwachteWaarde, xml);

        valideerTegenSchema(xml);
    }

    @Test
    public void testOutBindingMetMelding() throws JiBXException {
        List<Melding> meldingen = new ArrayList<Melding>();
        meldingen.add(new Melding(Soortmelding.INFORMATIE, MeldingCode.ALG0001, "TEST"));

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(meldingen);
        final VraagDetailsPersoonAntwoord response =
                (VraagDetailsPersoonAntwoord) antwoordBerichtFactory.bouwAntwoordBericht(
                        bouwIngaandBericht(Soortbericht.VRAAG_DETAILSPERSOON_BEVRAGEN), resultaat);
        response.setBerichtStuurgegevensGroep(bouwBerichtStuurgegevens());
        response.setMeldingen(meldingen);
        String xml = marshalObject(response);
        Assert.assertNotNull(xml);

        String verwachteWaarde = bouwVerwachteAntwoordBericht("20120325143506789", "I", "ALG0001", "TEST", null);
        Assert.assertEquals(verwachteWaarde, xml);

        valideerTegenSchema(xml);
    }

    @Test
    public void testOutBindingMetPersoonInResultaat() throws Exception {
        Partij gemeente = new Partij();
        gemeente.setGemeentecode(new Gemeentecode((short) 10));

        Land nederland = new Land();
        nederland.setCode(new Landcode((short) 31));

        Plaats amsterdam = new Plaats();
        amsterdam.setCode(new PlaatsCode((short) 20));

        OpvragenPersoonResultaat resultaat =
            bouwOpvragenPersoonResultaatVoorCompleetPersoonMetRelaties(gemeente, nederland, amsterdam,
                    SoortRelatie.HUWELIJK);

        VraagDetailsPersoonAntwoord response =
                (VraagDetailsPersoonAntwoord) antwoordBerichtFactory.bouwAntwoordBericht(bouwIngaandBericht(Soortbericht.VRAAG_DETAILSPERSOON_BEVRAGEN), resultaat);

        String xml = marshalObject(response);
        Assert.assertNotNull(xml);
        Document document = XmlUtils.bouwDocument(xml);
        // Voor het gemak alle bsn's vervangen.
        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "burgerservicenummer", "123456789");
        valideerTegenSchema(xml);
        Assert.assertTrue("bevragen_VraagDetailsPersoon_Antwoord root node bestaat niet",
                XmlUtils.isNodeAanwezig("/bevragen_VraagDetailsPersoon_Antwoord", document));
        Assert.assertTrue("persoon node bestaat niet", XmlUtils.isNodeAanwezig("//persoon", document));
        String verwachteWaarde =
            bouwVerwachteAntwoordBericht("20120325143506789", null, null, null,
                    "vraagDetailsPersoonMetRelatiesAntwoordBindingResultaat_metVerzendendId.xml");
        Assert.assertEquals(verwachteWaarde, xml);

    }

    @Test
    public void testOutBindingMinimaalPersoonMetHuwelijk() throws JiBXException, ParseException {
        Land nederland = new Land();
        nederland.setCode(new Landcode((short) 31));

        PersoonModel minimaalPersoon = bouwMinimaalPersoon();

        RelatieStandaardGroepBericht gegevens = new RelatieStandaardGroepBericht();
        gegevens.setDatumAanvang(new Datum(20000312));
        gegevens.setLandAanvang(nederland);

        RelatieBericht minimaalHuwelijkBericht = new RelatieBericht();
        minimaalHuwelijkBericht.setSoort(SoortRelatie.HUWELIJK);
        minimaalHuwelijkBericht.setGegevens(gegevens);
        RelatieModel minimaalHuwelijk = new RelatieModel(minimaalHuwelijkBericht);

        Set<BetrokkenheidModel> minimaleBetrokkenheden = new HashSet<BetrokkenheidModel>();

        BetrokkenheidModel partner = new BetrokkenheidModel(new BetrokkenheidBericht(), new PersoonModel(new PersoonBericht()),
            minimaalHuwelijk);
        ReflectionTestUtils.setField(partner, "rol", SoortBetrokkenheid.PARTNER);

        minimaleBetrokkenheden.add(partner);
        ReflectionTestUtils.setField(minimaalHuwelijk, "betrokkenheden", minimaleBetrokkenheden);
        ReflectionTestUtils.setField(minimaalPersoon, "betrokkenheden", minimaleBetrokkenheden);

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(null);
        Set<PersoonModel> gevondenPersonen = new HashSet<PersoonModel>();
        gevondenPersonen.add(minimaalPersoon);
        resultaat.setGevondenPersonen(gevondenPersonen);

        final VraagDetailsPersoonAntwoord response =
                (VraagDetailsPersoonAntwoord) antwoordBerichtFactory.bouwAntwoordBericht(
                        bouwIngaandBericht(Soortbericht.VRAAG_DETAILSPERSOON_BEVRAGEN), resultaat);

        String xml = marshalObject(response);
        Assert.assertNotNull(xml);

        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "tijdstipLaatsteWijziging", "2011111111111100");
        String verwachteWaarde =
            bouwVerwachteAntwoordBericht("20120325143506789", null, null, null,
                    "vraagDetailsPersoonMinimaalMetHuwelijkAntwoordBindingResultaat.xml");
        valideerTegenSchema(xml);

        Assert.assertEquals(verwachteWaarde, xml);
    }

    @Test
    public void testOutBindingMinimaalPersoonMetFamRechtBet() throws JiBXException, ParseException {
        Partij gemeente = new Partij();
        gemeente.setGemeentecode(new Gemeentecode((short) 10));

        Land nederland = new Land();
        nederland.setCode(new Landcode((short) 31));

        PersoonModel minimaalPersoon = bouwMinimaalPersoon();
        PersoonModel moeder = new PersoonModel(new PersoonBericht());
        PersoonModel dochter = new PersoonModel(new PersoonBericht());

        PersoonGeslachtsaanduidingGroepBericht geslachtsaanduidingGroepBericht =
            new PersoonGeslachtsaanduidingGroepBericht();
        geslachtsaanduidingGroepBericht.setGeslachtsaanduiding(Geslachtsaanduiding.VROUW);
        PersoonGeslachtsaanduidingGroepModel geslachtsaanduiding =
            new PersoonGeslachtsaanduidingGroepModel(geslachtsaanduidingGroepBericht);

        // Relaties
        RelatieBericht relatieOudersBericht = new RelatieBericht();
        relatieOudersBericht.setSoort(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        relatieOudersBericht.setGegevens(new RelatieStandaardGroepBericht());
        RelatieModel relatieOuders = new RelatieModel(relatieOudersBericht);

        RelatieBericht relatieDochterBericht = new RelatieBericht();
        relatieDochterBericht.setSoort(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        relatieDochterBericht.setGegevens(new RelatieStandaardGroepBericht());
        RelatieModel relatieDochter = new RelatieModel(relatieDochterBericht);

        // Betrokkenheden
        BetrokkenheidModel minPersoonNaarOuder = new BetrokkenheidModel(new BetrokkenheidBericht(), minimaalPersoon,
            relatieOuders);
        ReflectionTestUtils.setField(minPersoonNaarOuder, "rol", SoortBetrokkenheid.KIND);
//        ReflectionTestUtils.setField(minPersoonNaarOuder, "betrokkene", minimaalPersoon);
//        ReflectionTestUtils.setField(minPersoonNaarOuder, "relatie", relatieOuders);

        BetrokkenheidModel minPersoonNaarDochter = new BetrokkenheidModel(new BetrokkenheidBericht(), minimaalPersoon,
            relatieDochter);
        ReflectionTestUtils.setField(minPersoonNaarDochter, "rol", SoortBetrokkenheid.OUDER);
//        ReflectionTestUtils.setField(minPersoonNaarDochter, "betrokkene", minimaalPersoon);
//        ReflectionTestUtils.setField(minPersoonNaarDochter, "relatie", relatieDochter);

        BetrokkenheidModel moederBetrokkenheid = new BetrokkenheidModel(new BetrokkenheidBericht(), moeder,
            relatieOuders);
        ReflectionTestUtils.setField(moederBetrokkenheid, "rol", SoortBetrokkenheid.OUDER);
//        ReflectionTestUtils.setField(moederBetrokkenheid, "betrokkene", moeder);
//        ReflectionTestUtils.setField(moederBetrokkenheid, "relatie", relatieOuders);
        ReflectionTestUtils.setField(moederBetrokkenheid.getBetrokkene(), "geslachtsaanduiding", geslachtsaanduiding);

        BetrokkenheidModel dochterBetrokkenheid = new BetrokkenheidModel(new BetrokkenheidBericht(), dochter,
            relatieDochter);
        ReflectionTestUtils.setField(dochterBetrokkenheid, "rol", SoortBetrokkenheid.KIND);
//        ReflectionTestUtils.setField(dochterBetrokkenheid, "betrokkene", dochter);
//        ReflectionTestUtils.setField(dochterBetrokkenheid, "relatie", relatieDochter);
        ReflectionTestUtils.setField(dochterBetrokkenheid.getBetrokkene(), "geslachtsaanduiding", geslachtsaanduiding);

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

        final VraagDetailsPersoonAntwoord response =
                (VraagDetailsPersoonAntwoord) antwoordBerichtFactory.bouwAntwoordBericht(
                        bouwIngaandBericht(Soortbericht.VRAAG_DETAILSPERSOON_BEVRAGEN), resultaat);

        String xml = marshalObject(response);
        Assert.assertNotNull(xml);

        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "tijdstipLaatsteWijziging", "2011111111111100");
        String verwachteWaarde =
            bouwVerwachteAntwoordBericht("20120325143506789", null, null, null,
                    "vraagDetailsPersoonMinimaalMetFamRechtBetrAntwoordBindingResultaat.xml");
        Assert.assertEquals(verwachteWaarde, xml);

        valideerTegenSchema(xml);
    }

    @Test
    public void testIndicatieBinding() throws Exception {
        Partij gemeente = new Partij();
        gemeente.setGemeentecode(new Gemeentecode((short) 10));

        Land nederland = new Land();
        nederland.setCode(new Landcode((short) 31));

        Plaats amsterdam = new Plaats();
        amsterdam.setCode(new PlaatsCode((short) 20));

        Set<PersoonModel> gevondenPersonen = new HashSet<PersoonModel>();
        PersoonModel opTeVragenPersoon = maakPersoon(1, gemeente, amsterdam, nederland, "persoon");

        // Indicaties
        voegIndicatieToeAanPersoon(opTeVragenPersoon, SoortIndicatie.VERSTREKKINGSBEPERKING, Ja.Ja);

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(null);
        gevondenPersonen.add(opTeVragenPersoon);
        resultaat.setGevondenPersonen(gevondenPersonen);

        final VraagDetailsPersoonAntwoord response =
                (VraagDetailsPersoonAntwoord) antwoordBerichtFactory.bouwAntwoordBericht(
                        bouwIngaandBericht(Soortbericht.VRAAG_DETAILSPERSOON_BEVRAGEN), resultaat);

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

    @Test
    public void testOutBindingMetPersoonHistorieInResultaat() throws Exception {
        Land nederland = new Land();
        nederland.setCode(new Landcode((short) 31));

        PersoonModel minimaalPersoon = bouwMinimaalPersoon();

        //Maak historie aan voor adressen
        for (PersoonAdresModel adres : minimaalPersoon.getAdressen()) {
            PersoonAdresHisModel hisAdres = new PersoonAdresHisModel(adres.getGegevens(), adres);

            hisAdres.setDatumTijdRegistratie(new DatumTijd(new Date(1L)));
            hisAdres.setDatumAanvangGeldigheid(new Datum(20120101));
            hisAdres.setDatumEindeGeldigheid(new Datum(20120102));
            adres.setHistorie(Arrays.asList(hisAdres, hisAdres));
        }

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(null);
        Set<PersoonModel> gevondenPersonen = new HashSet<PersoonModel>();
        gevondenPersonen.add(minimaalPersoon);
        resultaat.setGevondenPersonen(gevondenPersonen);

        final VraagDetailsPersoonAntwoord response =
                (VraagDetailsPersoonAntwoord) antwoordBerichtFactory.bouwAntwoordBericht(
                        bouwIngaandBericht(Soortbericht.VRAAG_DETAILSPERSOON_BEVRAGEN), resultaat);

        String xml = marshalObject(response);
        Assert.assertNotNull(xml);

        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "tijdstipLaatsteWijziging", "2011111111111100");
        String verwachteWaarde =
            bouwVerwachteAntwoordBericht("20120325143506789", null, null, null,
                    "vraagDetailsPersoonMinimaalMetHistorieAntwoordBindingResultaat.xml");
        valideerTegenSchema(xml);

        Assert.assertEquals(verwachteWaarde, xml);
    }

    @Override
    public String getBerichtElementNaam() {
        return "bevragen_VraagDetailsPersoon_Antwoord";
    }

    @Override
    protected String getSchemaBestand() {
        return "/xsd/BRP_Bevraging_Berichten.xsd";
    }
}
