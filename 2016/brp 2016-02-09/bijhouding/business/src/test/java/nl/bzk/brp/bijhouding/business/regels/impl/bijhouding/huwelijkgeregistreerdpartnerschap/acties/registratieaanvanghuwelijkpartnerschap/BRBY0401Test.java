/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.huwelijkgeregistreerdpartnerschap.acties
        .registratieaanvanghuwelijkpartnerschap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.bijhouding.business.testconfig.AttribuutAdministratieTestConfig;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NationaliteitcodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebied;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieAanvangHuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonNationaliteitHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;

import nl.bzk.brp.util.RelatieBuilder;
import nl.bzk.brp.util.StamgegevenBuilder;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonNationaliteitHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AttribuutAdministratieTestConfig.class })
public class BRBY0401Test {

    private static final String IDENTIFICIERENDE_SLEUTEL_MAN = "id.man";

    private static final String IDENTIFICIERENDE_SLEUTEL_VROUW = "id.vrouw";
    private static final DatumTijdAttribuut TIJDSTIP_REGISTRATIE = DatumTijdAttribuut.bouwDatumTijd(2013, 8, 15, 0, 0, 0);

    private static final int HUWELIJKS_DATUM = 20010404;
    private static final int GEBOORTE_DATUM_18 = 19830404;
    private static final int GEBOORTE_DATUM_VOOR18_1 = 19900404;

    private static final int GEBOORTE_DATUM_VOOR18_2 = 19910404;
    private static final int GEBOORTE_DATUM_VOOR18_3 = 19900404;
    private static final int GEBOORTE_DATUM_OUDER18_1 = 19800404;

    private static final int GEBOORTE_DATUM_OUDER18_2 = 19790404;
    private BRBY0401 brby0401;

    @Before
    public void init() {
        brby0401 = new BRBY0401();
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0401, brby0401.getRegel());
    }

    @Test
    public void testBeidePartnersMinderjarig() {
        // man jonger dan 18, vrouw jonger dan 18, beide NL nationaliteit ==> NOT OK
        final PersoonView man = maakPersoonView(GEBOORTE_DATUM_VOOR18_1);
        final PersoonView vrouw = maakPersoonView(GEBOORTE_DATUM_VOOR18_2);

        final List<BerichtEntiteit> resultaat =
                brby0401.voerRegelUit(null, maakHuwelijkBericht(), maakActie(), maakBestaandeBetrokkenen(man, vrouw));

        Assert.assertEquals(2, resultaat.size());
        Assert.assertTrue(resultaat.get(0) instanceof PersoonBericht);
        Assert.assertTrue(resultaat.get(1) instanceof PersoonBericht);
    }

    @Test
    public void testBeidePartnersMeerderjarig() {
        // man, vrouw beide ouder 18, beide NL nationaliteit ==> OK
        final PersoonView man = maakPersoonView(GEBOORTE_DATUM_OUDER18_1);
        final PersoonView vrouw = maakPersoonView(GEBOORTE_DATUM_OUDER18_2);

        final List<BerichtEntiteit> resultaat =
                brby0401.voerRegelUit(null, maakHuwelijkBericht(), maakActie(), maakBestaandeBetrokkenen(man, vrouw));
        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testBeidePartnersMinderjarigNietNL() {
        // man jonger dan 18, vrouw jonger dan 18, beide NL nationaliteit, maar huwelijk niet in NL
        final PersoonView man = maakPersoonView(GEBOORTE_DATUM_VOOR18_1);
        final PersoonView vrouw = maakPersoonView(GEBOORTE_DATUM_VOOR18_2);

        final HuwelijkGeregistreerdPartnerschapBericht huwelijkGeregistreerdPartnerschapBericht = maakHuwelijkBericht();
        huwelijkGeregistreerdPartnerschapBericht.getStandaard().setLandGebiedAanvang(
                StatischeObjecttypeBuilder.LAND_BELGIE);

        final List<BerichtEntiteit> resultaat =
                brby0401.voerRegelUit(null, huwelijkGeregistreerdPartnerschapBericht, maakActie(),
                        maakBestaandeBetrokkenen(man, vrouw));

        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testBeidePartners18Jaar() {
        // man, vrouw beide exact 18, beide NL nationaliteit ==> OK
        final PersoonView man = maakPersoonView(GEBOORTE_DATUM_18);
        final PersoonView vrouw = maakPersoonView(GEBOORTE_DATUM_18);

        final List<BerichtEntiteit> resultaat =
                brby0401.voerRegelUit(null, maakHuwelijkBericht(), maakActie(), maakBestaandeBetrokkenen(man, vrouw));
        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testVrouwMinderjarigVerschilMetHuwelijksDatum1Maand() {
        // man exact 18, vrouw 18 - 1 mnd, beide NL nationaliteit ==> NOT OK
        final PersoonView man = maakPersoonView(GEBOORTE_DATUM_18);
        final PersoonView vrouw = maakPersoonView(GEBOORTE_DATUM_VOOR18_1);

        final List<BerichtEntiteit> resultaat =
                brby0401.voerRegelUit(null, maakHuwelijkBericht(), maakActie(), maakBestaandeBetrokkenen(man, vrouw));
        Assert.assertEquals(1, resultaat.size());
        Assert.assertTrue(resultaat.get(0) instanceof PersoonBericht);
    }

    @Test
    public void testVrouwMinderjarigVerschilMetHuwelijksDatum1Dag() {
        // man exact 18, vrouw 18 - 1 dag, beide NL nationaliteit ==> NOT OK
        final PersoonView man = maakPersoonView(GEBOORTE_DATUM_18);
        final PersoonView vrouw = maakPersoonView(GEBOORTE_DATUM_18 + 1);

        final List<BerichtEntiteit> resultaat =
                brby0401.voerRegelUit(null, maakHuwelijkBericht(), maakActie(), maakBestaandeBetrokkenen(man, vrouw));
        Assert.assertEquals(1, resultaat.size());
        Assert.assertTrue(resultaat.get(0) instanceof PersoonBericht);
    }

    @Test
    public void testManMinderJarigMaarGeenNederlandseNationaliteit() {
        // man voor 18 GEEN NL, vrouw ouder 18,  ==> OK
        final PersoonView man = maakPersoonView(GEBOORTE_DATUM_VOOR18_3, false);
        final PersoonView vrouw = maakPersoonView(GEBOORTE_DATUM_OUDER18_1);

        final List<BerichtEntiteit> resultaat =
                brby0401.voerRegelUit(null, maakHuwelijkBericht(), maakActie(), maakBestaandeBetrokkenen(man, vrouw));
        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testBeidePartnersMinderjarigLandAanvangBelgie() {
        // man jonger dan 18, vrouw jonger dan 18, beide NL nationaliteit ==> NOT OK
        final PersoonView man = maakPersoonView(GEBOORTE_DATUM_VOOR18_1);
        final PersoonView vrouw = maakPersoonView(GEBOORTE_DATUM_VOOR18_2);

        final List<BerichtEntiteit> resultaat =
                brby0401.voerRegelUit(null, maakHuwelijkBericht(SoortPersoon.INGESCHREVENE, false), maakActie(),
                                      maakBestaandeBetrokkenen(man, vrouw));
        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testBeidePartnersMeerderjarigLandAanvangBelgie() {
        // man, vrouw beide ouder 18, beide NL nationaliteit, land aanvang belgie ==> OK
        final PersoonView man = maakPersoonView(GEBOORTE_DATUM_OUDER18_1);
        final PersoonView vrouw = maakPersoonView(GEBOORTE_DATUM_OUDER18_2);

        final List<BerichtEntiteit> resultaat =
                brby0401.voerRegelUit(null, maakHuwelijkBericht(SoortPersoon.INGESCHREVENE, false), maakActie(),
                                      maakBestaandeBetrokkenen(man, vrouw));
        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testPartnerNietIngeschreven() {
        // man 18 Niet Ingeschrevene, vrouw ouder 18,  ==> OK
        final PersoonView man = maakPersoonView(
                GEBOORTE_DATUM_VOOR18_1, true, SoortPersoon.NIET_INGESCHREVENE);
        final PersoonView vrouw = maakPersoonView(GEBOORTE_DATUM_OUDER18_1);

        final List<BerichtEntiteit> resultaat =
                brby0401.voerRegelUit(null, maakHuwelijkBericht(SoortPersoon.NIET_INGESCHREVENE, true), maakActie(),
                        maakBestaandeBetrokkenen(man, vrouw));
        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testPartnerNietIngeschrevenVrouwJonger18() {
        // man 18 Niet Ingeschrevene, vrouw jonger 18,  ==> NOT OK
        final PersoonView man = maakPersoonView(
                GEBOORTE_DATUM_VOOR18_1, true, SoortPersoon.NIET_INGESCHREVENE);
        final PersoonView vrouw = maakPersoonView(GEBOORTE_DATUM_VOOR18_2);

        final List<BerichtEntiteit> resultaat =
                brby0401.voerRegelUit(null, maakHuwelijkBericht(SoortPersoon.NIET_INGESCHREVENE, true), maakActie(),
                                      maakBestaandeBetrokkenen(man, vrouw));
        Assert.assertEquals(1, resultaat.size());
        Assert.assertTrue(resultaat.get(0) instanceof PersoonBericht);
    }

    @Test
    public void testBeidePartnersMinderjarigOpDitMomentMaarInToekomstMeerderjarig() {
        // man en vrouw jonger dan 18 maar in toekomst (huwelijksdatum) wel ouder dan 18, beide NL nationaliteit ==> OK
        final PersoonView man = maakPersoonView(GEBOORTE_DATUM_VOOR18_1);
        final PersoonView vrouw = maakPersoonView(GEBOORTE_DATUM_VOOR18_2);

        final HuwelijkGeregistreerdPartnerschapBericht huwelijkGeregistreerdPartnerschapBericht = maakHuwelijkBericht();
        //Ver in de toekomst zijn ze meerderjarig
        huwelijkGeregistreerdPartnerschapBericht.getStandaard().setDatumAanvang(new DatumEvtDeelsOnbekendAttribuut(21000101));

        //Maar op actie datum zijn ze minderjarig
        final ActieBericht actie = maakActie();
        actie.setTijdstipRegistratie(DatumTijdAttribuut.bouwDatumTijd(2001, 4, 4, 0, 0, 0));

        final List<BerichtEntiteit> resultaat =
                brby0401.voerRegelUit(null, huwelijkGeregistreerdPartnerschapBericht, actie,
                        maakBestaandeBetrokkenen(man, vrouw));

        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testBeidePartnersMinderjarigOpDitMomentMaarInToekomstMeerderjarigZonderNederlandseNationaliteit() {
        // man jonger dan 18, vrouw jonger dan 18, beide geen NL nationaliteit ==> OK
        final PersoonView man = maakPersoonView(GEBOORTE_DATUM_VOOR18_1, false);
        final PersoonView vrouw = maakPersoonView(GEBOORTE_DATUM_VOOR18_2, false);

        final HuwelijkGeregistreerdPartnerschapBericht huwelijkGeregistreerdPartnerschapBericht = maakHuwelijkBericht();
        //Ver in de toekomst zijn ze meerderjarig
        huwelijkGeregistreerdPartnerschapBericht.getStandaard().setDatumAanvang(new DatumEvtDeelsOnbekendAttribuut(21000101));

        //Maar op actie datum zijn ze minderjarig
        final ActieBericht actie = maakActie();
        actie.setTijdstipRegistratie(DatumTijdAttribuut.bouwDatumTijd(2001, 4, 4, 0, 0, 0));

        final List<BerichtEntiteit> resultaat =
                brby0401.voerRegelUit(null, huwelijkGeregistreerdPartnerschapBericht, actie,
                                      maakBestaandeBetrokkenen(man, vrouw));

        Assert.assertEquals(0, resultaat.size());
    }

    /**
     * Maakt een huwelijk bericht met een man, vrouw en standaard id's.
     *
     * @return huwelijk geregistreerd partnerschap bericht
     */
    private HuwelijkGeregistreerdPartnerschapBericht maakHuwelijkBericht() {
        return maakHuwelijkBericht(SoortPersoon.INGESCHREVENE, true);
    }

    /**
     * Maakt een huwelijk bericht met een man, vrouw en standaard id's.
     *
     * @param soortMan SoortPersoon van de man
     * @return huwelijk geregistreerd partnerschap bericht
     */
    private HuwelijkGeregistreerdPartnerschapBericht maakHuwelijkBericht(final SoortPersoon soortMan,
                                                                         final boolean heeftAanvangNederland)
    {
        final PersoonBericht manBericht = new PersoonBericht();
        manBericht.setSoort(new SoortPersoonAttribuut(soortMan));
        manBericht.setIdentificerendeSleutel(IDENTIFICIERENDE_SLEUTEL_MAN);
        final PersoonBericht vrouwBericht = new PersoonBericht();
        vrouwBericht.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        vrouwBericht.setIdentificerendeSleutel(IDENTIFICIERENDE_SLEUTEL_VROUW);

        LandGebied landAanvang = StatischeObjecttypeBuilder.LAND_NEDERLAND.getWaarde();
        if (!heeftAanvangNederland) {
            landAanvang = StatischeObjecttypeBuilder.LAND_BELGIE.getWaarde();
        }

        return new RelatieBuilder<HuwelijkGeregistreerdPartnerschapBericht>().bouwHuwelijkRelatie()
                .setLandGebiedAanvang(landAanvang)
                .voegPartnerToe(manBericht)
                .voegPartnerToe(vrouwBericht)
                .setDatumAanvang(HUWELIJKS_DATUM).getRelatie();
    }

    private Map<String, PersoonView> maakBestaandeBetrokkenen(
            final PersoonView man, final PersoonView vrouw)
    {
        final Map<String, PersoonView> bestaandeBetrokkenen = new HashMap<>();
        bestaandeBetrokkenen.put(IDENTIFICIERENDE_SLEUTEL_MAN, man);
        bestaandeBetrokkenen.put(IDENTIFICIERENDE_SLEUTEL_VROUW, vrouw);
        return bestaandeBetrokkenen;
    }


    private PersoonView maakPersoonView(final int geboorteDatum) {
        return maakPersoonView(geboorteDatum, true);
    }

    private PersoonView maakPersoonView(final int geboorteDatum, final boolean isNederlands) {
        return maakPersoonView(geboorteDatum, isNederlands, SoortPersoon.INGESCHREVENE);
    }

    private PersoonView maakPersoonView(final int geboorteDatum,
                                                              final boolean isNederlands,
                                                              final SoortPersoon soortPersoon)
    {
        final PersoonHisVolledigImpl persoon = new PersoonHisVolledigImplBuilder(soortPersoon)
                .nieuwGeboorteRecord(geboorteDatum)
                .datumGeboorte(geboorteDatum)
                .eindeRecord()
                .build();

        if (isNederlands) {
            final PersoonNationaliteitHisVolledigImpl persoonNationaliteit = new PersoonNationaliteitHisVolledigImplBuilder(
                    StamgegevenBuilder
                            .bouwDynamischStamgegeven(Nationaliteit.class, NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE))
                    .nieuwStandaardRecord(geboorteDatum, null, geboorteDatum)
                    .eindeRecord()
                    .build();
            persoon.getNationaliteiten().add(persoonNationaliteit);
        }

        return new PersoonView(persoon);
    }

    private ActieBericht maakActie() {
        final ActieBericht actie = new ActieRegistratieAanvangHuwelijkGeregistreerdPartnerschapBericht();
        actie.setTijdstipRegistratie(TIJDSTIP_REGISTRATIE);

        return actie;
    }

}
