/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.bericht.request;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtRootObject;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.PartnerBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNationaliteitBericht;
import nl.bzk.brp.model.bericht.kern.PersoonReisdocumentBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.HuwelijkHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.KindHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PartnerHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonAdresHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonGeslachtsnaamcomponentHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonNationaliteitHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonReisdocumentHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonVoornaamHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.RelatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.FamilierechtelijkeBetrekkingView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.HuwelijkView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.RelatieView;
import nl.bzk.brp.util.hisvolledig.kern.FamilierechtelijkeBetrekkingHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.HuwelijkHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.OuderHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonAdresHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonGeslachtsnaamcomponentHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonNationaliteitHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonReisdocumentHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonVoornaamHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

/** Unit test klasse voor de {@link BRBY9910} regel. */
public class BRBY9910Test {

    private static final Integer PERSOONID1 = 1234;
    private static final Integer PERSOONID2 = 5678;
    private static final Integer PERSOONID3 = 3456;
    private static final Integer NIID1 = 4321;
    private static final Integer NIID2 = 8765;
    private static final Integer HUWID1 = 12;
    private static final Integer FAMID1 = 21;
    private static final Integer FAMID2 = 43;
    private static final Integer BETRID1 = 56;
    private static final Integer BETRID2 = 78;
    private static final Integer BETRID3 = 65;
    private static final Integer GROEPID1 = 987;
    private static final Integer GROEPID2 = 789;

    private BRBY9910 brby9910;

    @Before
    public void setup() {
        brby9910 = new BRBY9910();
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY9910, brby9910.getRegel());
    }

    /**************************************************************************
     * Test gevallen voor relatie->betrokkenheid->persoon
     * Voorbeeld: ontbinding huwelijk
     **************************************************************************/

    @Test
    public void testBestaandHuwelijkCorrect() {
        List<BerichtEntiteit> overtreders = brby9910.voerRegelUit(
                maakHuwelijkView(HUWID1, PERSOONID1, BETRID1),
                maakHuwelijkBericht(HUWID1, PERSOONID1, "sdfsdferfsdf", BETRID1), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testBestaandHuwelijkMismatchBetrokkenheid() {
        List<BerichtEntiteit> overtreders = brby9910.voerRegelUit(
                maakHuwelijkView(HUWID1, PERSOONID1, BETRID1),
                maakHuwelijkBericht(HUWID1, PERSOONID1, "e489tufg", BETRID2), null, null);
        Assert.assertEquals(1, overtreders.size());
        Assert.assertEquals(PartnerBericht.class, overtreders.get(0).getClass());
    }

    @Test
    public void testBestaandHuwelijkMismatchPersoon() {
        List<BerichtEntiteit> overtreders = brby9910.voerRegelUit(
                maakHuwelijkView(HUWID1, PERSOONID1, BETRID1),
                maakHuwelijkBericht(HUWID1, PERSOONID2, "dfger89tu67548thg", BETRID1), null, null);
        Assert.assertEquals(1, overtreders.size());
        Assert.assertEquals(PersoonBericht.class, overtreders.get(0).getClass());
    }

    @Test
    public void testBestaandHuwelijkNietIngeschreveneCorrect() {
        List<BerichtEntiteit> overtreders = brby9910.voerRegelUit(
                maakHuwelijkViewNietIngeschrevene(HUWID1, NIID1, BETRID1),
                maakHuwelijkBericht(HUWID1, NIID1, "djklfgdfkgj", BETRID1, false), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testBestaandHuwelijkNietIngeschreveneMismatchPersoon() {
        List<BerichtEntiteit> overtreders = brby9910.voerRegelUit(
                maakHuwelijkViewNietIngeschrevene(HUWID1, NIID1, BETRID1),
                maakHuwelijkBericht(HUWID1, NIID2, "4898uy589rty", BETRID1, false), null, null);
        Assert.assertEquals(1, overtreders.size());
        Assert.assertEquals(PersoonBericht.class, overtreders.get(0).getClass());
    }

    // Bestaande relatie, nieuwe betrokkenheid. Komt eigenlijk alleen voor bij
    // familierechtelijke betrekking (bijv adoptie), maar voor de test maakt het niet uit.
    @Test
    public void testBestaandHuwelijkNieuwePersoon() {
        List<BerichtEntiteit> overtreders = brby9910.voerRegelUit(
                maakHuwelijkView(HUWID1, PERSOONID1, BETRID1),
                maakHuwelijkBericht(HUWID1, PERSOONID2, "895urtjhi", null), null,
                maakBestaandeBetrokkenen(PERSOONID2, "895urtjhi"));
        Assert.assertEquals(0, overtreders.size());
    }

    /**************************************************************************
     * Test gevallen voor persoon->betrokkenheid->relatie->betrokkenheid->persoon
     * Voorbeeld: mededeling gezagsregister
     **************************************************************************/

    @Test
    public void testGezagCorrect() {
        List<BerichtEntiteit> overtreders = brby9910.voerRegelUit(
                maakGezagView(PERSOONID1, BETRID1, FAMID1, BETRID2, PERSOONID2),
                maakGezagBericht(PERSOONID1, BETRID1, FAMID1, BETRID2, PERSOONID2), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testGezagMismatchKindBetrokkenheid() {
        List<BerichtEntiteit> overtreders = brby9910.voerRegelUit(
                maakGezagView(PERSOONID1, BETRID1, FAMID1, BETRID2, PERSOONID2),
                maakGezagBericht(PERSOONID1, BETRID3, FAMID1, BETRID2, PERSOONID2), null, null);
        Assert.assertEquals(1, overtreders.size());
        Assert.assertEquals(KindBericht.class, overtreders.get(0).getClass());
    }

    @Test
    public void testGezagMismatchFamilie() {
        List<BerichtEntiteit> overtreders = brby9910.voerRegelUit(
                maakGezagView(PERSOONID1, BETRID1, FAMID1, BETRID2, PERSOONID2),
                maakGezagBericht(PERSOONID1, BETRID1, FAMID2, BETRID2, PERSOONID2), null, null);
        Assert.assertEquals(1, overtreders.size());
        Assert.assertEquals(FamilierechtelijkeBetrekkingBericht.class, overtreders.get(0).getClass());
    }

    @Test
    public void testGezagMismatchOuderBetrokkenheid() {
        List<BerichtEntiteit> overtreders = brby9910.voerRegelUit(
                maakGezagView(PERSOONID1, BETRID1, FAMID1, BETRID2, PERSOONID2),
                maakGezagBericht(PERSOONID1, BETRID1, FAMID1, BETRID3, PERSOONID2), null, null);
        Assert.assertEquals(1, overtreders.size());
        Assert.assertEquals(OuderBericht.class, overtreders.get(0).getClass());
    }

    @Test
    public void testGezagMismatchOuderPersoon() {
        List<BerichtEntiteit> overtreders = brby9910.voerRegelUit(
                maakGezagView(PERSOONID1, BETRID1, FAMID1, BETRID2, PERSOONID2),
                maakGezagBericht(PERSOONID1, BETRID1, FAMID1, BETRID2, PERSOONID3), null, null);
        Assert.assertEquals(1, overtreders.size());
        Assert.assertEquals(PersoonBericht.class, overtreders.get(0).getClass());
    }

    /**************************************************************************
     * Test gevallen voor nieuwe relatie, bestaande persoon
     * Voorbeeld: Voltrekking huwelijk
     **************************************************************************/

    @Test
    public void testNieuwHuwelijkCorrect() {
        final String objectSleutel = "fghrt89yh";
        List<BerichtEntiteit> overtreders = brby9910.voerRegelUit(
                null, maakHuwelijkBericht(null, PERSOONID1, objectSleutel, null), null,
                maakBestaandeBetrokkenen(PERSOONID1, objectSleutel));
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testNieuwHuwelijkNietIngeschrevene() {
        List<BerichtEntiteit> overtreders = brby9910.voerRegelUit(null,
                maakHuwelijkBericht(null, null, null, null, false), null,
                new HashMap<String, PersoonView>());
        Assert.assertEquals(0, overtreders.size());
    }

    // Persoon niet terug te vinden in bestaande betrokkenen: exception.
    @Test(expected = IllegalStateException.class)
    public void testNieuwHuwelijkMismatchPersoon() {
        brby9910.voerRegelUit(null, maakHuwelijkBericht(null, PERSOONID1, "89hrte78h", null), null,
                maakBestaandeBetrokkenen(PERSOONID2, "784tdfuigher78ty"));
    }

    /**************************************************************************
     * Test gevallen voor 1-op-N associaties van persoon, namelijk:
     * adres, voornaam, geslachtsnaamcomponent, nationaliteit, reisdocument
     **************************************************************************/

    @Test
    public void testPersoonAdresCorrect() {
        List<BerichtEntiteit> overtreders = brby9910.voerRegelUit(
                maakPersoonAdresView(GROEPID1),
                maakPersoonAdresBericht(GROEPID1), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testPersoonAdresGeenSleutel() {
        List<BerichtEntiteit> overtreders = brby9910.voerRegelUit(
                maakPersoonAdresView(GROEPID1),
                maakPersoonAdresBericht(null), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testMismatchPersoonAdres() {
        List<BerichtEntiteit> overtreders = brby9910.voerRegelUit(
                maakPersoonAdresView(GROEPID1),
                maakPersoonAdresBericht(GROEPID2), null, null);
        Assert.assertEquals(1, overtreders.size());
        Assert.assertEquals(PersoonAdresBericht.class, overtreders.get(0).getClass());
    }

    @Test
    public void testPersoonVoornaamCorrect() {
        List<BerichtEntiteit> overtreders = brby9910.voerRegelUit(
                maakPersoonVoornaamView(GROEPID1),
                maakPersoonVoornaamBericht(GROEPID1), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testPersoonVoornaamGeenSleutel() {
        List<BerichtEntiteit> overtreders = brby9910.voerRegelUit(
                maakPersoonVoornaamView(GROEPID1),
                maakPersoonVoornaamBericht(null), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testMismatchPersoonVoornaam() {
        List<BerichtEntiteit> overtreders = brby9910.voerRegelUit(
                maakPersoonVoornaamView(GROEPID1),
                maakPersoonVoornaamBericht(GROEPID2), null, null);
        Assert.assertEquals(1, overtreders.size());
        Assert.assertEquals(PersoonVoornaamBericht.class, overtreders.get(0).getClass());
    }

    @Test
    public void testPersoonGeslachtsnaamcomponentCorrect() {
        List<BerichtEntiteit> overtreders = brby9910.voerRegelUit(
                maakPersoonGeslachtsnaamcomponentView(GROEPID1),
                maakPersoonGeslachtsnaamcomponentBericht(GROEPID1), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testPersoonGeslachtsnaamcomponentGeenSleutel() {
        List<BerichtEntiteit> overtreders = brby9910.voerRegelUit(
                maakPersoonGeslachtsnaamcomponentView(GROEPID1),
                maakPersoonGeslachtsnaamcomponentBericht(null), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testMismatchPersoonGeslachtsnaamcomponent() {
        List<BerichtEntiteit> overtreders = brby9910.voerRegelUit(
                maakPersoonGeslachtsnaamcomponentView(GROEPID1),
                maakPersoonGeslachtsnaamcomponentBericht(GROEPID2), null, null);
        Assert.assertEquals(1, overtreders.size());
        Assert.assertEquals(PersoonGeslachtsnaamcomponentBericht.class, overtreders.get(0).getClass());
    }

    @Test
    public void testPersoonNationaliteitCorrect() {
        List<BerichtEntiteit> overtreders = brby9910.voerRegelUit(
                maakPersoonNationaliteitView(GROEPID1),
                maakPersoonNationaliteitBericht(GROEPID1), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testPersoonNationaliteitGeenSleutel() {
        List<BerichtEntiteit> overtreders = brby9910.voerRegelUit(
                maakPersoonNationaliteitView(GROEPID1),
                maakPersoonNationaliteitBericht(null), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testMismatchPersoonNationaliteit() {
        List<BerichtEntiteit> overtreders = brby9910.voerRegelUit(
                maakPersoonNationaliteitView(GROEPID1),
                maakPersoonNationaliteitBericht(GROEPID2), null, null);
        Assert.assertEquals(1, overtreders.size());
        Assert.assertEquals(PersoonNationaliteitBericht.class, overtreders.get(0).getClass());
    }

    @Test
    public void testPersoonReisdocumentCorrect() {
        List<BerichtEntiteit> overtreders = brby9910.voerRegelUit(
                maakPersoonReisdocumentView(GROEPID1),
                maakPersoonReisdocumentBericht(GROEPID1), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testPersoonReisdocumentGeenSleutel() {
        List<BerichtEntiteit> overtreders = brby9910.voerRegelUit(
                maakPersoonReisdocumentView(GROEPID1),
                maakPersoonReisdocumentBericht(null), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testMismatchPersoonReisdocument() {
        List<BerichtEntiteit> overtreders = brby9910.voerRegelUit(
                maakPersoonReisdocumentView(GROEPID1),
                maakPersoonReisdocumentBericht(GROEPID2), null, null);
        Assert.assertEquals(1, overtreders.size());
        Assert.assertEquals(PersoonReisdocumentBericht.class, overtreders.get(0).getClass());
    }

    // Apart testgeval voor testen mismatch op 2e element in het bericht (niet gedupliceerd voor alle 1-op-n associaties).

    @Test
    public void testMismatchTweedePersoonReisdocument() {
        PersoonBericht persoonBericht = maakPersoonReisdocumentBericht(GROEPID1);
        PersoonBericht dummyPersoonBericht = maakPersoonReisdocumentBericht(GROEPID2);
        persoonBericht.getReisdocumenten().add(dummyPersoonBericht.getReisdocumenten().get(0));
        List<BerichtEntiteit> overtreders = brby9910.voerRegelUit(
                maakPersoonReisdocumentView(GROEPID1),
                persoonBericht, null, null);
        Assert.assertEquals(1, overtreders.size());
        Assert.assertEquals(PersoonReisdocumentBericht.class, overtreders.get(0).getClass());
    }

    /**************************************************************************
     * Test gevallen voor het dekken van ongeldige condities.
     **************************************************************************/

    @Test(expected = IllegalArgumentException.class)
    public void testOngeldigTypeRootObject() {
        brby9910.voerRegelUit(null, Mockito.mock(BerichtRootObject.class), null, null);
    }

    @Test(expected = IllegalStateException.class)
    public void testBestaandePersoonNieuweBetrokkenheid() {
        brby9910.voerRegelUit(
                maakGezagView(PERSOONID1, null, FAMID1, BETRID2, PERSOONID2),
                maakGezagBericht(PERSOONID1, null, FAMID1, BETRID2, PERSOONID2), null, null);
    }

    @Test(expected = IllegalStateException.class)
    public void testNieuwePersoonBestaandeBetrokkenheid() {
        brby9910.voerRegelUit(null,
                maakGezagBericht(null, BETRID1, FAMID1, BETRID2, PERSOONID2), null, null);
    }

    @Test(expected = IllegalStateException.class)
    public void testBestaandeBetrokkenheidNieuweRelatie() {
        brby9910.voerRegelUit(
                maakGezagView(PERSOONID1, BETRID1, null, BETRID2, PERSOONID2),
                maakGezagBericht(PERSOONID1, BETRID1, null, BETRID2, PERSOONID2), null, null);
    }

    @Test(expected = IllegalStateException.class)
    public void testBestaandeBetrokkenheidNieuwePersoon() {
        brby9910.voerRegelUit(
                maakHuwelijkView(HUWID1, null, BETRID1),
                maakHuwelijkBericht(HUWID1, null, null, BETRID1), null, null);
    }

    @Test(expected = IllegalStateException.class)
    public void testNieuweRelatieBestaandeBetrokkenheid() {
        brby9910.voerRegelUit(
                null,
                maakHuwelijkBericht(null, PERSOONID1, "fsdfsdf", BETRID1), null, null);
    }

    /**************************************************************************
     * Util methodes voot het aanmaken van testdata.
     **************************************************************************/

    private PersoonView maakPersoonAdresView(final Integer groepId) {
        PersoonAdresHisVolledigImpl adres = new PersoonAdresHisVolledigImplBuilder()
                .nieuwStandaardRecord(20010101, null, 20010101).eindeRecord().build();
        ReflectionTestUtils.setField(adres, "iD", groepId);
        return new PersoonView(new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .voegPersoonAdresToe(adres).build());
    }

    private PersoonBericht maakPersoonAdresBericht(final Integer groepId) {
        PersoonBericht persoon = new PersoonBericht();
        persoon.setAdressen(new ArrayList<PersoonAdresBericht>());
        PersoonAdresBericht adres = new PersoonAdresBericht();
        if (groepId != null) {
            adres.setObjectSleutel(groepId.toString());
        }
        persoon.getAdressen().add(adres);
        return persoon;
    }

    private PersoonView maakPersoonVoornaamView(final Integer groepId) {
        PersoonVoornaamHisVolledigImpl voornaam = new PersoonVoornaamHisVolledigImplBuilder(new VolgnummerAttribuut(1))
                .nieuwStandaardRecord(20010101, null, 20010101).eindeRecord().build();
        ReflectionTestUtils.setField(voornaam, "iD", groepId);
        return new PersoonView(new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .voegPersoonVoornaamToe(voornaam).build());
    }

    private PersoonBericht maakPersoonVoornaamBericht(final Integer groepId) {
        PersoonBericht persoon = new PersoonBericht();
        persoon.setVoornamen(new ArrayList<PersoonVoornaamBericht>());
        PersoonVoornaamBericht voornaam = new PersoonVoornaamBericht();
        if (groepId != null) {
            voornaam.setObjectSleutel(groepId.toString());
        }
        persoon.getVoornamen().add(voornaam);
        return persoon;
    }

    private PersoonView maakPersoonGeslachtsnaamcomponentView(final Integer groepId) {
        PersoonGeslachtsnaamcomponentHisVolledigImpl geslachtsnaamcomponent = new PersoonGeslachtsnaamcomponentHisVolledigImplBuilder(new VolgnummerAttribuut(1))
                .nieuwStandaardRecord(20010101, null, 20010101).eindeRecord().build();
        ReflectionTestUtils.setField(geslachtsnaamcomponent, "iD", groepId);
        return new PersoonView(new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .voegPersoonGeslachtsnaamcomponentToe(geslachtsnaamcomponent).build());
    }

    private PersoonBericht maakPersoonGeslachtsnaamcomponentBericht(final Integer groepId) {
        PersoonBericht persoon = new PersoonBericht();
        persoon.setGeslachtsnaamcomponenten(new ArrayList<PersoonGeslachtsnaamcomponentBericht>());
        PersoonGeslachtsnaamcomponentBericht geslachtsnaamcomponent = new PersoonGeslachtsnaamcomponentBericht();
        if (groepId != null) {
            geslachtsnaamcomponent.setObjectSleutel(groepId.toString());
        }
        persoon.getGeslachtsnaamcomponenten().add(geslachtsnaamcomponent);
        return persoon;
    }

    private PersoonView maakPersoonNationaliteitView(final Integer groepId) {
        PersoonNationaliteitHisVolledigImpl nationaliteit = new PersoonNationaliteitHisVolledigImplBuilder(null)
                .nieuwStandaardRecord(20010101, null, 20010101).eindeRecord().build();
        ReflectionTestUtils.setField(nationaliteit, "iD", groepId);
        return new PersoonView(new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .voegPersoonNationaliteitToe(nationaliteit).build());
    }

    private PersoonBericht maakPersoonNationaliteitBericht(final Integer groepId) {
        PersoonBericht persoon = new PersoonBericht();
        persoon.setNationaliteiten(new ArrayList<PersoonNationaliteitBericht>());
        PersoonNationaliteitBericht nationaliteit = new PersoonNationaliteitBericht();
        if (groepId != null) {
            nationaliteit.setObjectSleutel(groepId.toString());
        }
        persoon.getNationaliteiten().add(nationaliteit);
        return persoon;
    }

    private PersoonView maakPersoonReisdocumentView(final Integer groepId) {
        PersoonReisdocumentHisVolledigImpl reisdocument = new PersoonReisdocumentHisVolledigImplBuilder(null)
                .nieuwStandaardRecord(20010101).eindeRecord().build();
        ReflectionTestUtils.setField(reisdocument, "iD", groepId);
        return new PersoonView(new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .voegPersoonReisdocumentToe(reisdocument).build());
    }

    private PersoonBericht maakPersoonReisdocumentBericht(final Integer groepId) {
        PersoonBericht persoon = new PersoonBericht();
        persoon.setReisdocumenten(new ArrayList<PersoonReisdocumentBericht>());
        PersoonReisdocumentBericht reisdocument = new PersoonReisdocumentBericht();
        if (groepId != null) {
            reisdocument.setObjectSleutel(groepId.toString());
        }
        persoon.getReisdocumenten().add(reisdocument);
        return persoon;
    }

    private RelatieView maakHuwelijkView(final Integer huwelijkId, final Integer persoonID, final Integer betrokkenheidId) {
        RelatieView huwelijk = maakHuwelijkModel(huwelijkId);
        PersoonView persoon = maakDatabasePersoonIngeschrevene(persoonID);
        koppelPersoonEnRelatieModelAlsPartner(persoon, huwelijk, betrokkenheidId);
        return huwelijk;
    }

    private Map<String, PersoonView> maakBestaandeBetrokkenen(final Integer persoonID, final String objectSleutel) {
        Map<String, PersoonView> bestaandeBetrokkenen = new HashMap<>();
        bestaandeBetrokkenen.put(objectSleutel, maakDatabasePersoonIngeschrevene(persoonID));
        return bestaandeBetrokkenen;
    }

    private RelatieView maakHuwelijkViewNietIngeschrevene(final Integer huwelijkId, final Integer persoonId, final Integer betrokkenheidId) {
        RelatieView huwelijk = maakHuwelijkModel(huwelijkId);
        PersoonView persoon = maakPersoonModelNietIngeschrevene(persoonId);
        koppelPersoonEnRelatieModelAlsPartner(persoon, huwelijk, betrokkenheidId);
        return huwelijk;
    }

    private PersoonView maakGezagView(final Integer kindPersoonBsn, final Integer kindId, final Integer familieId, final Integer ouderId, final Integer ouderPersoonBsn) {
        PersoonView kindPersoon = maakDatabasePersoonIngeschrevene(kindPersoonBsn);
        RelatieView familie = maakFamilieModel(familieId);
        koppelPersoonEnRelatieModelAlsKind(kindPersoon, familie, kindId);
        PersoonView ouderPersoon = maakDatabasePersoonIngeschrevene(ouderPersoonBsn);
        koppelPersoonEnRelatieModelAlsOuder(ouderPersoon, familie, ouderId);
        return kindPersoon;
    }

    private RelatieBericht maakHuwelijkBericht(final Integer huwelijkId, final Integer persoonId, final String persoonObjectSleutel, final Integer partnerId) {
        return maakHuwelijkBericht(huwelijkId, persoonId, persoonObjectSleutel, partnerId, true);
    }

    private RelatieBericht maakHuwelijkBericht(final Integer huwelijkId, final Integer persoonId, final String persoonObjectSleutel, final Integer partnerId, final boolean ingeschrevene) {
        RelatieBericht huwelijk = maakHuwelijkBericht(huwelijkId);
        PersoonBericht persoon = maakPersoonBericht(persoonId, persoonObjectSleutel);
        koppelRelatieNaarPersoonBericht(huwelijk, persoon, partnerId, new PartnerBericht());
        return huwelijk;
    }

    private PersoonBericht maakGezagBericht(final Integer kindPersoonID, final Integer betrKindID, final Integer familieId, final Integer ouderBetrId, final Integer ouderPersoonId) {
        PersoonBericht kindPersoon = maakPersoonBericht(kindPersoonID, "sdjkbsdjkb");
        RelatieBericht familieBericht = maakFamilieBericht(familieId);
        koppelPersoonNaarRelatieBericht(kindPersoon, familieBericht, betrKindID, new KindBericht());
        PersoonBericht ouderPersoon = maakPersoonBericht(ouderPersoonId, "dfjklhsdfj");
        koppelRelatieNaarPersoonBericht(familieBericht, ouderPersoon, ouderBetrId, new OuderBericht());
        return kindPersoon;
    }

    private PersoonBericht maakPersoonBericht(final Integer objectSleutelDatabaseId, final String objectSleutelBericht) {
        PersoonBericht persoonBericht = new PersoonBericht();
        if (objectSleutelDatabaseId != null) {
            persoonBericht.setObjectSleutelDatabaseID(objectSleutelDatabaseId);
            persoonBericht.setObjectSleutel(objectSleutelBericht);
            persoonBericht.setIdentificerendeSleutel(objectSleutelBericht);
        }
        return persoonBericht;
    }

    private PersoonView maakDatabasePersoonIngeschrevene(final Integer persoonID) {
        final PersoonHisVolledigImpl persoonHisVolledig =
                new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        ReflectionTestUtils.setField(persoonHisVolledig, "iD", persoonID);
        return new PersoonView(persoonHisVolledig);
    }

    private PersoonView maakPersoonModelNietIngeschrevene(final Integer id) {
        PersoonHisVolledigImpl persoonHisVolledig = new PersoonHisVolledigImplBuilder(SoortPersoon.NIET_INGESCHREVENE).build();
        ReflectionTestUtils.setField(persoonHisVolledig, "iD", id);
        return new PersoonView(persoonHisVolledig);
    }

    private RelatieBericht maakHuwelijkBericht(final Integer databaseId) {
        HuwelijkBericht huwelijk = new HuwelijkBericht();
        if (databaseId != null) {
            huwelijk.setObjectSleutel(String.valueOf(databaseId));
        }
        return huwelijk;
    }

    private RelatieView maakHuwelijkModel(final Integer id) {
        HuwelijkHisVolledigImpl huwelijkHisVolledig = new HuwelijkHisVolledigImplBuilder()
                .nieuwStandaardRecord(20010101).eindeRecord().build();
        ReflectionTestUtils.setField(huwelijkHisVolledig, "iD", id);
        return new HuwelijkView(huwelijkHisVolledig);
    }

    private RelatieBericht maakFamilieBericht(final Integer familieId) {
        FamilierechtelijkeBetrekkingBericht familie = new FamilierechtelijkeBetrekkingBericht();
        if (familieId != null) {
            familie.setObjectSleutel(String.valueOf(familieId));
        }
        return familie;
    }

    private RelatieView maakFamilieModel(final Integer id) {
        FamilierechtelijkeBetrekkingHisVolledigImpl familieHisVolledig = new FamilierechtelijkeBetrekkingHisVolledigImplBuilder().build();
        ReflectionTestUtils.setField(familieHisVolledig, "iD", id);
        return new FamilierechtelijkeBetrekkingView(familieHisVolledig);
    }

    private void koppelPersoonNaarRelatieBericht(final PersoonBericht persoon, final RelatieBericht relatie, final Integer objectSleutelBetrokkenheid, final BetrokkenheidBericht betrokkenheid) {
        maakBetrokkenhedenIndienNull(persoon);
        if (objectSleutelBetrokkenheid != null) {
            betrokkenheid.setObjectSleutel(String.valueOf(objectSleutelBetrokkenheid));
        }
        persoon.getBetrokkenheden().add(betrokkenheid);
        betrokkenheid.setRelatie(relatie);
    }

    private void koppelRelatieNaarPersoonBericht(final RelatieBericht relatie, final PersoonBericht persoon, final Integer partnerId, final BetrokkenheidBericht betrokkenheid) {
        maakBetrokkenhedenIndienNull(relatie);
        if (partnerId != null) {
            betrokkenheid.setObjectSleutel(String.valueOf(partnerId));
        }
        relatie.getBetrokkenheden().add(betrokkenheid);
        betrokkenheid.setPersoon(persoon);
    }

    private void koppelPersoonEnRelatieModelAlsOuder(final PersoonView persoon, final RelatieView relatie, final Integer id) {
        PersoonHisVolledigImpl persoonHisVolledig = (PersoonHisVolledigImpl) ReflectionTestUtils.getField(persoon, "persoon");
        RelatieHisVolledigImpl relatieHisVolledig = (RelatieHisVolledigImpl) ReflectionTestUtils.getField(relatie, "relatie");
        OuderHisVolledigImpl ouder = new OuderHisVolledigImplBuilder(relatieHisVolledig, persoonHisVolledig)
                .nieuwOuderschapRecord(20010101, null, 20010101).eindeRecord().build();
        ReflectionTestUtils.setField(ouder, "iD", id);
        persoonHisVolledig.getBetrokkenheden().add(ouder);
        relatieHisVolledig.getBetrokkenheden().add(ouder);
    }

    private void koppelPersoonEnRelatieModelAlsKind(final PersoonView persoon, final RelatieView relatie, final Integer id) {
        PersoonHisVolledigImpl persoonHisVolledig = (PersoonHisVolledigImpl) ReflectionTestUtils.getField(persoon, "persoon");
        RelatieHisVolledigImpl relatieHisVolledig = (RelatieHisVolledigImpl) ReflectionTestUtils.getField(relatie, "relatie");
        KindHisVolledigImpl kind = new KindHisVolledigImpl(relatieHisVolledig, persoonHisVolledig);
        ReflectionTestUtils.setField(kind, "iD", id);
        persoonHisVolledig.getBetrokkenheden().add(kind);
        relatieHisVolledig.getBetrokkenheden().add(kind);
    }

    private void koppelPersoonEnRelatieModelAlsPartner(final PersoonView persoon, final RelatieView relatie, final Integer id) {
        PersoonHisVolledigImpl persoonHisVolledig = (PersoonHisVolledigImpl) ReflectionTestUtils.getField(persoon, "persoon");
        RelatieHisVolledigImpl relatieHisVolledig = (RelatieHisVolledigImpl) ReflectionTestUtils.getField(relatie, "relatie");
        PartnerHisVolledigImpl partner = new PartnerHisVolledigImpl(relatieHisVolledig, persoonHisVolledig);
        ReflectionTestUtils.setField(partner, "iD", id);
        persoonHisVolledig.getBetrokkenheden().add(partner);
        relatieHisVolledig.getBetrokkenheden().add(partner);
    }

    private void maakBetrokkenhedenIndienNull(final PersoonBericht persoonBericht) {
        if (persoonBericht.getBetrokkenheden() == null) {
            persoonBericht.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        }
    }

    private void maakBetrokkenhedenIndienNull(final RelatieBericht relatieBericht) {
        if (relatieBericht.getBetrokkenheden() == null) {
            relatieBericht.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        }
    }

}
