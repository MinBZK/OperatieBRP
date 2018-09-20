/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.symbols.ExpressieAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonAdresHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonVoornaamHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.OuderHisVolledig;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonPredikaatView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAdresModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIdentificatienummersModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonSamengesteldeNaamModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVoornaamModel;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.apache.commons.collections.functors.TruePredicate;
import org.junit.Assert;
import org.junit.Test;


/**
 * Test voor de ExpressieAttribuut enumeratie.
 */
public class ExpressieAttribuutTest {

    private static final String BIJHOUDING_DATUM_AANVANG_GELDIGHEID = "$bijhouding.datum_aanvang_geldigheid";
    private static final String INDICATIE_ONDER_CURATELE_DATUM_TIJD_REGISTRATIE = "$indicatie.onder_curatele.datum_tijd_registratie";
    private static final String INDICATIE_ONDER_CURATELE_DATUM_AANVANG_GELDIGHEID = "$indicatie.onder_curatele.datum_aanvang_geldigheid";

    @Test
    public void testAantalExpressieAttributenIsGroterDanNul() {
        final ExpressieAttribuut[] expressieAttributen = ExpressieAttribuut.values();

        assertTrue("Er zijn geen expressie-attributen", expressieAttributen.length > 0);
    }

    @Test
    public void testExpressieAttributenMethodes() {
        assertTrue("Expressie afnemerindicaties is geen lijst", ExpressieAttribuut.PERSOON_AFNEMERINDICATIES.isLijst());
        assertEquals(ExpressieType.DATUM, ExpressieAttribuut.PERSOON_GEBOORTE_DATUM.getType());
        assertEquals(ExpressieType.PERSOON, ExpressieAttribuut.PERSOON_GEBOORTE_DATUM.getParentType());
        Assert.assertNull(ExpressieAttribuut.PERSOON_GEBOORTE_DATUM.getParent());
    }

    @Test
    public void testExpressieAttributenTsRegOphalen() {
        final PersoonHisVolledigImpl persoon = TestPersoonJohnnyJordaan.maak();
        final Expressie resultaat = BRPExpressies.evalueer("$geboorte.datum_tijd_registratie", persoon);

        assertNotNull(resultaat);
        assertTrue(persoon.getPersoonGeboorteHistorie().getActueleRecord().getTijdstipRegistratie() == resultaat.getElement(0).getAttribuut());
    }

    @Test
    public void testExpressieAttributenActiesOphalen() {
        final PersoonHisVolledigImpl persoon = TestPersoonJohnnyJordaan.maak();
        final Expressie resultaat = BRPExpressies.evalueer("$geboorte.verantwoordingInhoud.tijdstip_registratie", persoon);

        assertNotNull(resultaat);
        assertTrue(persoon.getPersoonGeboorteHistorie().getActueleRecord().getVerantwoordingInhoud().getTijdstipRegistratie()
                       == resultaat.getElement(0).getAttribuut());
    }

    @Test
    public void testExpressieAttributenActieVervalOphalen() {
        final PersoonHisVolledigImpl persoon = TestPersoonJohnnyJordaan.maak();
        final Expressie resultaat = BRPExpressies.evalueer("$geboorte.verantwoordingVerval.tijdstip_registratie", persoon);

        assertNotNull(resultaat);
        assertFalse(resultaat.isFout());
        assertEquals(0, resultaat.aantalElementen());
    }

    @Test
    public void testExpressieAttributenActiesKinderenOphalen() {
        final PersoonHisVolledigImpl persoon = TestPersoonJohnnyJordaan.maak();
        final Expressie resultaat = BRPExpressies.evalueer("RMAP(KINDEREN(), k, $k.identificatienummers.verantwoordingInhoud.tijdstip_registratie)",
                                                           persoon);

        final List<DatumTijdAttribuut> verwachteAttributen = new ArrayList<>();
        for (final BetrokkenheidHisVolledigImpl betrokkenheid : persoon.getBetrokkenheden()) {
            if (betrokkenheid.getRol().getWaarde() == SoortBetrokkenheid.OUDER) {
                for (final BetrokkenheidHisVolledig betrokkenheidVanRelatie : betrokkenheid.getRelatie().getBetrokkenheden()) {
                    if (!betrokkenheidVanRelatie.getPersoon().getID().equals(persoon.getID())) {
                        for (final HisPersoonIdentificatienummersModel idHis : betrokkenheidVanRelatie.getPersoon()
                            .getPersoonIdentificatienummersHistorie().getHistorie())
                        {
                            verwachteAttributen.add(idHis.getVerantwoordingInhoud().getTijdstipRegistratie());
                        }
                    }
                }
            }
        }
        assertNotNull(resultaat);
        assertFalse(resultaat.isFout());
        final List<DatumTijdAttribuut> werkelijkeAttributen = new ArrayList<>();
        for (final Expressie element : resultaat.getElementen()) {
            werkelijkeAttributen.add((DatumTijdAttribuut) element.getElement(0).getAttribuut());
        }
        assertEquals(verwachteAttributen, werkelijkeAttributen);
    }

    @Test
    public void testExpressieAttributenActiesVoornamenOphalen() {
        final PersoonHisVolledigImpl persoon = TestPersoonJohnnyJordaan.maak();
        final Expressie resultaat =
                BRPExpressies.evalueer(
                        "RMAP(voornamen, v, $v.verantwoordingInhoud.tijdstip_registratie)",
                        persoon);

        final List<DatumTijdAttribuut> verwachteAttributen = new ArrayList<>();
        for (final PersoonVoornaamHisVolledigImpl voornaamHisVolledig : persoon.getVoornamen()) {
            for (final HisPersoonVoornaamModel hisPersoonVoornaamModel : voornaamHisVolledig.getPersoonVoornaamHistorie()
                    .getHistorie())
            {
                final DatumTijdAttribuut tijdstipRegistratie =
                        hisPersoonVoornaamModel.getVerantwoordingInhoud().getTijdstipRegistratie();
                verwachteAttributen.add(tijdstipRegistratie);
            }
        }
        assertNotNull(resultaat);
        assertFalse(resultaat.isFout());
        final List<DatumTijdAttribuut> werkelijkeAttributen = new ArrayList<>();
        for (final Expressie element : resultaat.getElementen()) {
            for (final Expressie element2 : element.getElement(0).getElementen()) {
                werkelijkeAttributen.add((DatumTijdAttribuut) element2.getAttribuut());
            }
        }
        assertEquals(verwachteAttributen, werkelijkeAttributen);
    }

    @Test
    public void testExpressieAttributenActiesAdressenOphalen() {
        final PersoonHisVolledigImpl persoon = TestPersoonJohnnyJordaan.maak();
        final Expressie resultaat =
                BRPExpressies.evalueer(
                        "RMAP(adressen, a, $a.verantwoordingInhoud.tijdstip_registratie)",
                        persoon);

        final List<DatumTijdAttribuut> verwachteAttributen = new ArrayList<>();
        for (final PersoonAdresHisVolledigImpl adresHisVolledig : persoon.getAdressen()) {
            for (final HisPersoonAdresModel hisPersoonAdresModel : adresHisVolledig.getPersoonAdresHistorie()
                    .getHistorie())
            {
                final DatumTijdAttribuut tijdstipRegistratie =
                        hisPersoonAdresModel.getVerantwoordingInhoud().getTijdstipRegistratie();
                verwachteAttributen.add(tijdstipRegistratie);
            }
        }
        assertNotNull(resultaat);
        assertFalse(resultaat.isFout());
        final List<DatumTijdAttribuut> werkelijkeAttributen = new ArrayList<>();
        for (final Expressie element : resultaat.getElementen()) {
            for (final Expressie element2 : element.getElement(0).getElementen()) {
                werkelijkeAttributen.add((DatumTijdAttribuut) element2.getAttribuut());
            }
        }
        assertEquals(verwachteAttributen, werkelijkeAttributen);
    }

    @Test
    public void testExpressieAttributenActiesVoornamenSoortOphalen() {
        final PersoonHisVolledigImpl persoon = TestPersoonJohnnyJordaan.maak();
        final Expressie resultaat =
                BRPExpressies.evalueer(
                        "RMAP(voornamen, v, $v.verantwoordingInhoud.soort)",
                        persoon);

        final List<SoortActieAttribuut> verwachteAttributen = new ArrayList<>();
        for (final PersoonVoornaamHisVolledigImpl voornaamHisVolledig : persoon.getVoornamen()) {
            for (final HisPersoonVoornaamModel hisPersoonVoornaamModel : voornaamHisVolledig.getPersoonVoornaamHistorie()
                    .getHistorie())
            {
                final SoortActieAttribuut tijdstipRegistratie =
                        hisPersoonVoornaamModel.getVerantwoordingInhoud().getSoort();
                verwachteAttributen.add(tijdstipRegistratie);
            }
        }
        assertNotNull(resultaat);
        assertFalse(resultaat.isFout());
        final List<SoortActieAttribuut> werkelijkeAttributen = new ArrayList<>();
        for (final Expressie element : resultaat.getElementen()) {
            for (final Expressie element2 : element.getElement(0).getElementen()) {
                werkelijkeAttributen.add((SoortActieAttribuut) element2.getAttribuut());
            }
        }
        assertEquals(verwachteAttributen, werkelijkeAttributen);
    }

    @Test
    public void testExpressieAttributenDatumAanvangKinderenOphalen() {
        final PersoonHisVolledigImpl persoon = TestPersoonJohnnyJordaan.maak();
        final Expressie resultaat =
                BRPExpressies.evalueer(
                        "RMAP(KINDEREN(), k, $k.samengestelde_naam.datum_aanvang_geldigheid)",
                        persoon);

        final List<DatumEvtDeelsOnbekendAttribuut> verwachteAttributen = new ArrayList<>();
        for (final BetrokkenheidHisVolledigImpl betrokkenheid : persoon.getBetrokkenheden()) {
            if (betrokkenheid.getRol().getWaarde() == SoortBetrokkenheid.OUDER) {
                for (final BetrokkenheidHisVolledig betrokkenheidVanRelatie : betrokkenheid.getRelatie().getBetrokkenheden()) {
                    if (!betrokkenheidVanRelatie.getPersoon().getID().equals(persoon.getID())) {
                        for (final HisPersoonSamengesteldeNaamModel naamHis : betrokkenheidVanRelatie.getPersoon()
                            .getPersoonSamengesteldeNaamHistorie().getHistorie())
                        {
                            verwachteAttributen.add(naamHis.getDatumAanvangGeldigheid());
                        }
                    }
                }
            }
        }
        assertNotNull(resultaat);
        assertFalse(resultaat.isFout());
        final List<DatumEvtDeelsOnbekendAttribuut> werkelijkeAttributen = new ArrayList<>();
        for (final Expressie element : resultaat.getElementen()) {
            werkelijkeAttributen.add((DatumEvtDeelsOnbekendAttribuut) element.getElement(0).getAttribuut());
        }
        assertEquals(verwachteAttributen, werkelijkeAttributen);
    }

    @Test
    public void testExpressieAttributenTsRegOuderschap() {
        final PersoonHisVolledigImpl persoon = TestPersoonJohnnyJordaan.maak();
        final Expressie resultaat =
                BRPExpressies.evalueer(
                        "RMAP(GERELATEERDE_BETROKKENHEDEN(\"KIND\", \"FAMILIERECHTELIJKE_BETREKKING\", \"OUDER\"), b, $b.ouderschap.datum_tijd_registratie)",
                        persoon);

        final List<DatumTijdAttribuut> verwachteAttributen = new ArrayList<>();
        for (final BetrokkenheidHisVolledigImpl betrokkenheid : persoon.getBetrokkenheden()) {
            if (betrokkenheid.getRol().getWaarde() == SoortBetrokkenheid.KIND) {
                for (final BetrokkenheidHisVolledig betrokkenheidVanRelatie : betrokkenheid.getRelatie()
                        .getBetrokkenheden())
                {
                    if (betrokkenheidVanRelatie.getRol().getWaarde() == SoortBetrokkenheid.OUDER) {
                        final OuderHisVolledig ouderHisVolledig = (OuderHisVolledig) betrokkenheidVanRelatie;

                        verwachteAttributen.add(ouderHisVolledig.getOuderOuderschapHistorie().getActueleRecord()
                                .getTijdstipRegistratie());
                    }
                }
            }
        }
        assertNotNull(resultaat);
        assertFalse(resultaat.isFout());
        final List<DatumTijdAttribuut> werkelijkeAttributen = new ArrayList<>();
        for (final Expressie element : resultaat.getElementen()) {
            werkelijkeAttributen.add((DatumTijdAttribuut) element.getElement(0).getAttribuut());
        }
        assertEquals(verwachteAttributen, werkelijkeAttributen);
    }

    @Test
    public void testExpressieAttributenActiesIndicatieCurateleOphalen() {
        final PersoonHisVolledigImpl persoon = TestPersoonJohnnyJordaan.maak();
        final SoortActieAttribuut verwachteWaarde =
                persoon.getIndicatieOnderCuratele().getPersoonIndicatieHistorie().getActueleRecord()
                        .getVerantwoordingInhoud()
                        .getSoort();

        final Expressie resultaat =
                BRPExpressies.evalueer(
                        "$indicatie.onder_curatele.verantwoordingInhoud.soort",
                        persoon);

        assertNotNull(resultaat);
        assertFalse(resultaat.isFout());

        assertEquals(verwachteWaarde, resultaat.getElement(0).getAttribuut());
    }

    @Test
    public void testExpressieAttributenTsRegIndicatieCurateleOphalen() {
        final PersoonHisVolledigImpl persoon = TestPersoonJohnnyJordaan.maak();
        final DatumTijdAttribuut verwachteWaarde =
                persoon.getIndicatieOnderCuratele().getPersoonIndicatieHistorie().getActueleRecord().getTijdstipRegistratie();

        final Expressie resultaat = BRPExpressies.evalueer(INDICATIE_ONDER_CURATELE_DATUM_TIJD_REGISTRATIE, persoon);

        assertNotNull(resultaat);
        assertFalse(resultaat.isFout());

        assertEquals(verwachteWaarde, resultaat.getElement(0).getAttribuut());
    }

    @Test
    public void testPredikaatViewBevraagMaterieelAttribuut() {
        final PersoonHisVolledigImpl persoon = TestPersoonJohnnyJordaan.maak();
        final Expressie expressie = BRPExpressies.evalueer(BIJHOUDING_DATUM_AANVANG_GELDIGHEID,
            new PersoonPredikaatView(persoon, TruePredicate.INSTANCE));
        assertEquals(persoon.getPersoonBijhoudingHistorie().getActueleRecord().getDatumAanvangGeldigheid().getWaarde().toString(),
            expressie.getAttribuut().getWaarde().toString());
    }

    @Test
    public void testPersoonViewBevraagMaterieelAttribuut() {
        final PersoonHisVolledigImpl persoon = TestPersoonJohnnyJordaan.maak();
        final Expressie expressie = BRPExpressies.evalueer(BIJHOUDING_DATUM_AANVANG_GELDIGHEID,
            new PersoonView(persoon));
        assertEquals(persoon.getPersoonBijhoudingHistorie().getActueleRecord().getDatumAanvangGeldigheid().getWaarde().toString(),
            expressie.getAttribuut().getWaarde().toString());
    }

    @Test
    public void testPredikaatViewBevraagMaterieelIndicatieAttribuut() {
        final PersoonHisVolledigImpl persoon = TestPersoonJohnnyJordaan.maak();
        final Expressie expressie = BRPExpressies.evalueer(INDICATIE_ONDER_CURATELE_DATUM_AANVANG_GELDIGHEID,
            new PersoonPredikaatView(persoon, TruePredicate.INSTANCE));
        assertEquals(persoon.getIndicatieOnderCuratele().getPersoonIndicatieHistorie().getActueleRecord().getDatumAanvangGeldigheid(), expressie
            .getAttribuut());
    }

    @Test
    public void testPersoonViewBevraagMaterieelIndicatieAttribuut() {
        final PersoonHisVolledigImpl persoon = TestPersoonJohnnyJordaan.maak();
        final Expressie expressie = BRPExpressies.evalueer(INDICATIE_ONDER_CURATELE_DATUM_AANVANG_GELDIGHEID,
            new PersoonView(persoon));
        assertEquals(persoon.getIndicatieOnderCuratele().getPersoonIndicatieHistorie().getActueleRecord().getDatumAanvangGeldigheid(), expressie
            .getAttribuut());
    }

    @Test
    public void testPredikaatViewBevraagFormeelIndicatieAttribuut() {
        final PersoonHisVolledigImpl persoon = TestPersoonJohnnyJordaan.maak();
        final Expressie expressie = BRPExpressies.evalueer(INDICATIE_ONDER_CURATELE_DATUM_TIJD_REGISTRATIE,
            new PersoonPredikaatView(persoon, TruePredicate.INSTANCE));
        assertEquals(persoon.getIndicatieOnderCuratele().getPersoonIndicatieHistorie().getActueleRecord().getTijdstipRegistratie().getWaarde(),
            expressie.getAttribuut().getWaarde());
    }

    @Test
    public void testPersoonViewBevraagFormeelIndicatieAttribuut() {
        final PersoonHisVolledigImpl persoon = TestPersoonJohnnyJordaan.maak();
        final Expressie expressie = BRPExpressies.evalueer(INDICATIE_ONDER_CURATELE_DATUM_TIJD_REGISTRATIE,
            new PersoonView(persoon));
        assertEquals(persoon.getIndicatieOnderCuratele().getPersoonIndicatieHistorie().getActueleRecord().getTijdstipRegistratie().getWaarde(),
            expressie.getAttribuut().getWaarde());
    }

    @Test
    public void testExpressieAttributenActiesIdentificatienummersOphalen() {
        final PersoonHisVolledigImpl persoon = TestPersoonJohnnyJordaan.maak();
        final SoortActieAttribuut verwachteWaarde =
            persoon.getPersoonIdentificatienummersHistorie().getHistorie().iterator().next().getVerantwoordingInhoud().getSoort();

        final Expressie resultaat = BRPExpressies.evalueer("$identificatienummers.verantwoordingInhoud.soort", persoon);

        assertNotNull(resultaat);
        assertFalse(resultaat.isFout());

        assertEquals(verwachteWaarde, resultaat.getElement(0).getAttribuut());
    }

}
