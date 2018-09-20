/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.bzk.brp.business.dto.bevraging.OpvragenPersoonResultaat;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.GemeenteCode;
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.attribuuttype.LandCode;
import nl.bzk.brp.model.attribuuttype.PlaatsCode;
import nl.bzk.brp.model.groep.impl.usr.PersoonGeslachtsAanduidingGroepMdl;
import nl.bzk.brp.model.groep.impl.usr.RelatieStandaardGroepMdl;
import nl.bzk.brp.model.objecttype.impl.usr.BetrokkenheidMdl;
import nl.bzk.brp.model.objecttype.impl.usr.PersoonMdl;
import nl.bzk.brp.model.objecttype.impl.usr.RelatieMdl;
import nl.bzk.brp.model.objecttype.statisch.GeslachtsAanduiding;
import nl.bzk.brp.model.objecttype.statisch.Land;
import nl.bzk.brp.model.objecttype.statisch.Partij;
import nl.bzk.brp.model.objecttype.statisch.Plaats;
import nl.bzk.brp.model.objecttype.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.statisch.SoortIndicatie;
import nl.bzk.brp.model.objecttype.statisch.SoortRelatie;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import nl.bzk.brp.utils.XmlUtils;
import nl.bzk.brp.web.bevraging.VraagDetailsPersoonAntwoord;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.w3c.dom.Document;


public class VraagDetailsPersoonAntwoordBindingTest
    extends AbstractVraagBerichtBindingUitTest<VraagDetailsPersoonAntwoord>
{

    @Override
    public Class<VraagDetailsPersoonAntwoord> getBindingClass() {
        return VraagDetailsPersoonAntwoord.class;
    }

    @Test
    public void testOutBindingMetLeegResultaat() throws JiBXException {
        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(null);

        final VraagDetailsPersoonAntwoord response = new VraagDetailsPersoonAntwoord(resultaat);
        String xml = marshalObject(response);
        Assert.assertNotNull(xml);

        String verwachteWaarde = bouwVerwachteAntwoordBericht("20120325143506789");
        Assert.assertEquals(verwachteWaarde, xml);

        valideerOutputTegenSchema(xml);
    }

    @Test
    public void testOutBindingMetMelding() throws JiBXException {
        List<Melding> meldingen = new ArrayList<Melding>();
        meldingen.add(new Melding(SoortMelding.INFO, MeldingCode.ALG0001, "TEST"));

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(meldingen);
        VraagDetailsPersoonAntwoord response = new VraagDetailsPersoonAntwoord(resultaat);

        String xml = marshalObject(response);
        Assert.assertNotNull(xml);

        String verwachteWaarde = bouwVerwachteAntwoordBericht("20120325143506789", "I", "ALG0001", "TEST", null);
        Assert.assertEquals(verwachteWaarde, xml);

        valideerOutputTegenSchema(xml);
    }

    @Test
    public void testOutBindingMetPersoonInResultaat() throws Exception {
        Partij gemeente = new Partij();
        ReflectionTestUtils.setField(gemeente, "gemeenteCode", new GemeenteCode("0010"));

        Land nederland = new Land();
        ReflectionTestUtils.setField(nederland, "landCode", new LandCode("0031"));

        Plaats amsterdam = new Plaats();
        ReflectionTestUtils.setField(amsterdam, "code", new PlaatsCode("0020"));

        OpvragenPersoonResultaat resultaat = bouwOpvragenPersoonResultaatVoorCompleetPersoonMetRelaties(gemeente,
            nederland, amsterdam, SoortRelatie.HUWELIJK);

        VraagDetailsPersoonAntwoord response = new VraagDetailsPersoonAntwoord(resultaat);
        String xml = marshalObject(response);
        Assert.assertNotNull(xml);
        Document document = XmlUtils.bouwDocument(xml);
        //Voor het gemak alle bsn's vervangen.
        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "burgerservicenummer", "123456789");
        valideerOutputTegenSchema(xml);
        Assert.assertTrue("bevragen_VraagDetailsPersoon_Antwoord root node bestaat niet",
                XmlUtils.isNodeAanwezig("/bevragen_VraagDetailsPersoon_Antwoord", document));
        Assert.assertTrue("persoon node bestaat niet",
                XmlUtils.isNodeAanwezig("//persoon", document));
        String verwachteWaarde = bouwVerwachteAntwoordBericht("20120325143506789", null, null, null,
            "vraagDetailsPersoonMetRelatiesAntwoordBindingResultaat_metVerzendendId.xml");
        Assert.assertEquals(verwachteWaarde, xml);

    }

    @Test
    public void testOutBindingMinimaalPersoonMetHuwelijk() throws JiBXException, ParseException {
        Land nederland = new Land();
        ReflectionTestUtils.setField(nederland, "landCode", new LandCode("0031"));

        PersoonMdl minimaalPersoon = bouwMinimaalPersoon();

        RelatieMdl minimaalHuwelijk = new RelatieMdl();
        ReflectionTestUtils.setField(minimaalHuwelijk, "soort", SoortRelatie.HUWELIJK);
        RelatieStandaardGroepMdl gegevens = new RelatieStandaardGroepMdl();
        ReflectionTestUtils.setField(gegevens, "datumAanvang", new Datum(20000312));
        ReflectionTestUtils.setField(gegevens, "landAanvang", nederland);
        ReflectionTestUtils.setField(minimaalHuwelijk, "gegevens", gegevens);

        Set<BetrokkenheidMdl> minimaleBetrokkenheden = new HashSet<BetrokkenheidMdl>();

        BetrokkenheidMdl partner = new BetrokkenheidMdl();
        ReflectionTestUtils.setField(partner, "rol", SoortBetrokkenheid.PARTNER);
        ReflectionTestUtils.setField(partner, "betrokkene", new PersoonMdl());
        ReflectionTestUtils.setField(partner, "relatie", minimaalHuwelijk);

        minimaleBetrokkenheden.add(partner);
        ReflectionTestUtils.setField(minimaalHuwelijk, "betrokkenheden", minimaleBetrokkenheden);
        ReflectionTestUtils.setField(minimaalPersoon, "betrokkenheden", minimaleBetrokkenheden);
        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(null);
        Set<PersoonMdl> gevondenPersonen = new HashSet<PersoonMdl>();
        gevondenPersonen.add(minimaalPersoon);
        resultaat.setGevondenPersonen(gevondenPersonen);

        final VraagDetailsPersoonAntwoord response = new VraagDetailsPersoonAntwoord(resultaat);

        String xml = marshalObject(response);
        Assert.assertNotNull(xml);

        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "tijdstipLaatsteWijziging", "2011111111111100");
        String verwachteWaarde = bouwVerwachteAntwoordBericht("20120325143506789", null, null, null,
            "vraagDetailsPersoonMinimaalMetHuwelijkAntwoordBindingResultaat.xml");
        valideerOutputTegenSchema(xml);

        Assert.assertEquals(verwachteWaarde, xml);

    }

    @Test
    public void testOutBindingMinimaalPersoonMetFamRechtBet() throws JiBXException, ParseException {
        Partij gemeente = new Partij();
        ReflectionTestUtils.setField(gemeente, "gemeenteCode", new GemeenteCode("0010"));

        Land nederland = new Land();
        ReflectionTestUtils.setField(nederland, "landCode", new LandCode("0031"));

        PersoonMdl minimaalPersoon = bouwMinimaalPersoon();
        PersoonMdl moeder = new PersoonMdl();
        PersoonMdl dochter = new PersoonMdl();

        PersoonGeslachtsAanduidingGroepMdl geslachtsAanduiding = new PersoonGeslachtsAanduidingGroepMdl();
        ReflectionTestUtils.setField(geslachtsAanduiding, "geslachtsAanduiding", GeslachtsAanduiding.VROUW);

        //Relaties
        RelatieMdl relatieOuders = new RelatieMdl();
        ReflectionTestUtils.setField(relatieOuders, "soort", SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);

        RelatieMdl relatieDochter = new RelatieMdl();
        ReflectionTestUtils.setField(relatieOuders, "soort", SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);

        //Betrokkenheden
        BetrokkenheidMdl minPersoonNaarOuder = new BetrokkenheidMdl();
        ReflectionTestUtils.setField(minPersoonNaarOuder, "rol", SoortBetrokkenheid.KIND);
        ReflectionTestUtils.setField(minPersoonNaarOuder, "betrokkene", minimaalPersoon);
        ReflectionTestUtils.setField(minPersoonNaarOuder, "relatie", relatieOuders);

        BetrokkenheidMdl minPersoonNaarDochter = new BetrokkenheidMdl();
        ReflectionTestUtils.setField(minPersoonNaarDochter, "rol", SoortBetrokkenheid.OUDER);
        ReflectionTestUtils.setField(minPersoonNaarDochter, "betrokkene", minimaalPersoon);
        ReflectionTestUtils.setField(minPersoonNaarDochter, "relatie", relatieDochter);

        BetrokkenheidMdl moederBetrokkenheid = new BetrokkenheidMdl();
        ReflectionTestUtils.setField(moederBetrokkenheid, "rol", SoortBetrokkenheid.OUDER);
        ReflectionTestUtils.setField(moederBetrokkenheid, "betrokkene", moeder);
        ReflectionTestUtils.setField(moederBetrokkenheid, "relatie", relatieOuders);
        ReflectionTestUtils.setField(moederBetrokkenheid.getBetrokkene(), "geslachtsAanduiding", geslachtsAanduiding);

        BetrokkenheidMdl dochterBetrokkenheid = new BetrokkenheidMdl();
        ReflectionTestUtils.setField(dochterBetrokkenheid, "rol", SoortBetrokkenheid.KIND);
        ReflectionTestUtils.setField(dochterBetrokkenheid, "betrokkene", dochter);
        ReflectionTestUtils.setField(dochterBetrokkenheid, "relatie", relatieDochter);
        ReflectionTestUtils.setField(dochterBetrokkenheid.getBetrokkene(), "geslachtsAanduiding", geslachtsAanduiding);

        ReflectionTestUtils.setField(relatieDochter, "betrokkenheden", new HashSet<BetrokkenheidMdl>());
        ReflectionTestUtils.invokeMethod(relatieDochter.getBetrokkenheden(), "add", minPersoonNaarDochter);
        ReflectionTestUtils.invokeMethod(relatieDochter.getBetrokkenheden(), "add", dochterBetrokkenheid);

        ReflectionTestUtils.setField(relatieOuders, "betrokkenheden", new HashSet<BetrokkenheidMdl>());
        ReflectionTestUtils.invokeMethod(relatieOuders.getBetrokkenheden(), "add", minPersoonNaarOuder);
        ReflectionTestUtils.invokeMethod(relatieOuders.getBetrokkenheden(), "add", moederBetrokkenheid);

        ReflectionTestUtils.setField(minimaalPersoon, "betrokkenheden", new HashSet<BetrokkenheidMdl>());
        ReflectionTestUtils.invokeMethod(minimaalPersoon.getBetrokkenheden(), "add", minPersoonNaarDochter);
        ReflectionTestUtils.invokeMethod(minimaalPersoon.getBetrokkenheden(), "add", minPersoonNaarOuder);

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(null);
        Set<PersoonMdl> gevondenPersonen = new HashSet<PersoonMdl>();
        gevondenPersonen.add(minimaalPersoon);
        resultaat.setGevondenPersonen(gevondenPersonen);

        final VraagDetailsPersoonAntwoord response = new VraagDetailsPersoonAntwoord(resultaat);

        String xml = marshalObject(response);
        Assert.assertNotNull(xml);

        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "tijdstipLaatsteWijziging", "2011111111111100");
        String verwachteWaarde = bouwVerwachteAntwoordBericht("20120325143506789", null, null, null,
            "vraagDetailsPersoonMinimaalMetFamRechtBetrAntwoordBindingResultaat.xml");
        Assert.assertEquals(verwachteWaarde, xml);

        valideerOutputTegenSchema(xml);
    }

    @Test
    public void testIndicatieBinding() throws Exception {
        Partij gemeente = new Partij();
        ReflectionTestUtils.setField(gemeente, "gemeenteCode", new GemeenteCode("0010"));

        Land nederland = new Land();
        ReflectionTestUtils.setField(nederland, "landCode", new LandCode("0031"));

        Plaats amsterdam = new Plaats();
        ReflectionTestUtils.setField(amsterdam, "code", new PlaatsCode("0020"));

        Set<PersoonMdl> gevondenPersonen = new HashSet<PersoonMdl>();
        PersoonMdl opTeVragenPersoon = maakPersoon(1L, gemeente, amsterdam, nederland, "persoon");

        // Indicaties
        voegIndicatieToeAanPersoon(opTeVragenPersoon, SoortIndicatie.ONDER_CURATELE, JaNee.Nee);
        voegIndicatieToeAanPersoon(opTeVragenPersoon, SoortIndicatie.VERSTREKKINGSBEPERKING, JaNee.Ja);

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(null);
        gevondenPersonen.add(opTeVragenPersoon);
        resultaat.setGevondenPersonen(gevondenPersonen);

        final VraagDetailsPersoonAntwoord response = new VraagDetailsPersoonAntwoord(resultaat);

        String xml = marshalObject(response);
        Assert.assertNotNull(xml);
        Document document = XmlUtils.bouwDocument(xml);
        //Voor het gemak alle bsn's vervangen.
        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "burgerservicenummer", "123456789");

        String verwachteWaarde = bouwVerwachteAntwoordBericht("20120325143506789", null, null, null,
            "vraagDetailsPersoonMetIndicatiesAntwoordBindingResultaat.xml");
        Assert.assertEquals(verwachteWaarde, xml);

        valideerOutputTegenSchema(xml);
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
