/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.huwelijkgeregistreerdpartnerschap.acties
        .registratieaanvanghuwelijkpartnerschap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.definitieregels.BRBY0001;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NationaliteitcodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestLandGebiedBuilder;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.PartnerBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.bericht.kern.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonNationaliteitHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonNationaliteitHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

public class BRBY0409Test {

    private static final String BSN_PERSOON_1 = "123456789";
    private static final String BSN_PERSOON_2 = "123456780";

    private static final LandGebiedCodeAttribuut NEDERLAND  = LandGebiedCodeAttribuut.NEDERLAND;
    private static final LandGebiedCodeAttribuut BUITENLAND = new LandGebiedCodeAttribuut((short) 9999);

    @Mock
    private BRBY0001 brby0001;

    private BRBY0409 brby0409;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        brby0409 = new BRBY0409();

        ReflectionTestUtils.setField(brby0409, "brby0001", brby0001);
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0409, brby0409.getRegel());
    }

    @Test
    public void testGeenTechnischeSleutel() {
        final HuwelijkGeregistreerdPartnerschapBericht relatieBericht = maakRelatie(null, null, NEDERLAND);
        final List<BerichtEntiteit> resultaat = brby0409.voerRegelUit(null, relatieBericht, null, null);

        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testGeenTechnischeSleutelPartner() {
        final HuwelijkGeregistreerdPartnerschapBericht relatieBericht = maakRelatie(BSN_PERSOON_1, null, NEDERLAND);
        final Map<String, PersoonView> bestaandeBetrokkenen =
            maakBestaandeBetrokkenen(BSN_PERSOON_1, BSN_PERSOON_2, true, true, true, true);

        final List<BerichtEntiteit> resultaat = brby0409.voerRegelUit(null, relatieBericht, null, bestaandeBetrokkenen);
        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testNietIngeschrevenPersonen() {
        final HuwelijkGeregistreerdPartnerschapBericht relatieBericht =
            maakRelatie(BSN_PERSOON_1, BSN_PERSOON_2, NEDERLAND);
        final Map<String, PersoonView> bestaandeBetrokkenen =
            maakBestaandeBetrokkenen(BSN_PERSOON_1, BSN_PERSOON_2, false, false, true, true);

        final List<BerichtEntiteit> resultaat = brby0409.voerRegelUit(null, relatieBericht, null, bestaandeBetrokkenen);
        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testPersoon1NietIngeschrevene() {
        final HuwelijkGeregistreerdPartnerschapBericht relatieBericht =
            maakRelatie(BSN_PERSOON_1, BSN_PERSOON_2, NEDERLAND);
        final Map<String, PersoonView> bestaandeBetrokkenen =
            maakBestaandeBetrokkenen(BSN_PERSOON_1, BSN_PERSOON_2, false, true, true, true);

        final List<BerichtEntiteit> resultaat = brby0409.voerRegelUit(null, relatieBericht, null, bestaandeBetrokkenen);
        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testPersoon2NietIngeschrevene() {
        final HuwelijkGeregistreerdPartnerschapBericht relatieBericht =
                maakRelatie(BSN_PERSOON_1, BSN_PERSOON_2, NEDERLAND);
        final Map<String, PersoonView> bestaandeBetrokkenen =
                maakBestaandeBetrokkenen(BSN_PERSOON_1, BSN_PERSOON_2, true, false, true, true);

        final List<BerichtEntiteit> resultaat = brby0409.voerRegelUit(null, relatieBericht, null, bestaandeBetrokkenen);
        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testPersonenZonderNederlandseNationaliteit() {
        final HuwelijkGeregistreerdPartnerschapBericht relatieBericht =
                maakRelatie(BSN_PERSOON_1, BSN_PERSOON_2, NEDERLAND);
        final Map<String, PersoonView> bestaandeBetrokkenen =
                maakBestaandeBetrokkenen(BSN_PERSOON_1, BSN_PERSOON_2, true, true, false, false);

        final List<BerichtEntiteit> resultaat = brby0409.voerRegelUit(null, relatieBericht, null, bestaandeBetrokkenen);
        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testPersoon1ZonderNederlandseNationaliteit() {
        final HuwelijkGeregistreerdPartnerschapBericht relatieBericht =
                maakRelatie(BSN_PERSOON_1, BSN_PERSOON_2, NEDERLAND);
        final Map<String, PersoonView> bestaandeBetrokkenen =
                maakBestaandeBetrokkenen(BSN_PERSOON_1, BSN_PERSOON_2, true, true, false, true);

        final List<BerichtEntiteit> resultaat = brby0409.voerRegelUit(null, relatieBericht, null, bestaandeBetrokkenen);
        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testPersoon2ZonderNederlandseNationaliteit() {
        final HuwelijkGeregistreerdPartnerschapBericht relatieBericht =
                maakRelatie(BSN_PERSOON_1, BSN_PERSOON_2, NEDERLAND);
        final Map<String, PersoonView> bestaandeBetrokkenen =
                maakBestaandeBetrokkenen(BSN_PERSOON_1, BSN_PERSOON_2, true, true, true, false);

        final List<BerichtEntiteit> resultaat = brby0409.voerRegelUit(null, relatieBericht, null, bestaandeBetrokkenen);
        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testZijnVerwantNederland() {
        Mockito.when(brby0001.isErVerwantschap(Matchers.any(PersoonView.class),
                Matchers.any(PersoonView.class))).thenReturn(true);

        final HuwelijkGeregistreerdPartnerschapBericht relatieBericht =
                maakRelatie(BSN_PERSOON_1, BSN_PERSOON_2, NEDERLAND);
        final Map<String, PersoonView> bestaandeBetrokkenen =
                maakBestaandeBetrokkenen(BSN_PERSOON_1, BSN_PERSOON_2, true, true, true, true);

        final List<BerichtEntiteit> resultaat = brby0409.voerRegelUit(null, relatieBericht, null, bestaandeBetrokkenen);

        Assert.assertEquals(1, resultaat.size());
        Assert.assertTrue(resultaat.get(0) instanceof HuwelijkGeregistreerdPartnerschapBericht);
    }

    @Test
    public void testZijnVerwantBuitenland() {
        Mockito.when(brby0001.isErVerwantschap(Matchers.any(PersoonView.class),
                Matchers.any(PersoonView.class))).thenReturn(true);

        final HuwelijkGeregistreerdPartnerschapBericht relatieBericht =
                maakRelatie(BSN_PERSOON_1, BSN_PERSOON_2, BUITENLAND);
        final Map<String, PersoonView> bestaandeBetrokkenen =
                maakBestaandeBetrokkenen(BSN_PERSOON_1, BSN_PERSOON_2, true, true, true, true);

        final List<BerichtEntiteit> resultaat = brby0409.voerRegelUit(null, relatieBericht, null, bestaandeBetrokkenen);

        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testZijnNietVerwant() {
        Mockito.when(brby0001.isErVerwantschap(Matchers.any(PersoonView.class),
                Matchers.any(PersoonView.class))).thenReturn(false);

        final HuwelijkGeregistreerdPartnerschapBericht relatieBericht =
                maakRelatie(BSN_PERSOON_1, BSN_PERSOON_2, NEDERLAND);
        final Map<String, PersoonView> bestaandeBetrokkenen =
                maakBestaandeBetrokkenen(BSN_PERSOON_1, BSN_PERSOON_2, true, true, true, true);

        final List<BerichtEntiteit> resultaat = brby0409.voerRegelUit(null, relatieBericht, null, bestaandeBetrokkenen);
        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testGeenLandAanvang() {
        final HuwelijkGeregistreerdPartnerschapBericht relatieBericht =
                maakRelatie(BSN_PERSOON_1, BSN_PERSOON_2, NEDERLAND);
        relatieBericht.getStandaard().setLandGebiedAanvang(null);
        final Map<String, PersoonView> bestaandeBetrokkenen =
                maakBestaandeBetrokkenen(BSN_PERSOON_1, BSN_PERSOON_2, true, true, true, true);

        final List<BerichtEntiteit> resultaat = brby0409.voerRegelUit(null, relatieBericht, null, bestaandeBetrokkenen);
        Assert.assertEquals(0, resultaat.size());
    }

    /**
     * Maakt een relatie bericht voor huwelijk geregistreerd partnerschap. Ook kun je meegeven of je wilt dat een van
     * de twee partners ontbreekt in de relatie.
     *
     * @param bsn1 bsn persoon 1
     * @param bsn2 bsn persoon 2
     * @param landcode de landcode
     * @return huwelijk geregistreerd partnerschap bericht
     */
    private HuwelijkGeregistreerdPartnerschapBericht maakRelatie(final String bsn1,
                                                                 final String bsn2,
                                                                 final LandGebiedCodeAttribuut landcode)
    {
        final PersoonBericht persoon1 = maakPersoonBericht(bsn1);

        final PartnerBericht partner1 = new PartnerBericht();
        partner1.setPersoon(persoon1);

        final List<BetrokkenheidBericht> partners = new ArrayList<>();
        partners.add(partner1);

        final PersoonBericht persoon2 = maakPersoonBericht(bsn2);
        final PartnerBericht partner2 = new PartnerBericht();
        partner2.setPersoon(persoon2);
        partners.add(partner2);

        final RelatieStandaardGroepBericht relatieGegevens = new RelatieStandaardGroepBericht();
        relatieGegevens.setDatumAanvang(new DatumEvtDeelsOnbekendAttribuut(20120303));
        relatieGegevens.setLandGebiedAanvang(new LandGebiedAttribuut(
            TestLandGebiedBuilder.maker().metCode(landcode).maak()));

        final HuwelijkGeregistreerdPartnerschapBericht relatieBericht = new HuwelijkBericht();
        relatieBericht.setStandaard(relatieGegevens);
        relatieBericht.setBetrokkenheden(partners);

        return relatieBericht;
    }

    /**
     * Maakt een persoon bericht op basis van een bsn
     *
     * @param bsn bsn
     * @return persoon bericht
     */
    private PersoonBericht maakPersoonBericht(final String bsn) {
        final PersoonIdentificatienummersGroepBericht idGroep = new PersoonIdentificatienummersGroepBericht();
        if (bsn != null) {
            idGroep.setBurgerservicenummer(new BurgerservicenummerAttribuut(bsn));
        }

        final PersoonBericht persoon = new PersoonBericht();
        persoon.setIdentificatienummers(idGroep);
        persoon.setIdentificerendeSleutel(bsn);

        return persoon;
    }

    /**
     * Maak bestaande betrokkenen.
     *
     * @param bsn1                 bsn persoon 1
     * @param bsn2                 bsn persoon 2
     * @param persoon1Ingeschreven persoon 1 is ingeschreven
     * @param persoon2Ingeschreven persoon 2 is ingeschreven
     * @param persoon1Nederlander  persoon 1 is nederlander
     * @param persoon2Nederlander  persoon 2 is nederlander
     * @return map met betrokkenen
     */
    private Map<String, PersoonView> maakBestaandeBetrokkenen(final String bsn1,
                                                                         final String bsn2,
                                                                         final boolean persoon1Ingeschreven,
                                                                         final boolean persoon2Ingeschreven,
                                                                         final boolean persoon1Nederlander,
                                                                         final boolean persoon2Nederlander)
    {
        final Map<String, PersoonView> bestaandeBetrokkenen = new HashMap<>();

        final PersoonView persoonView1 = maakPersoonView(persoon1Ingeschreven, persoon1Nederlander);
        final PersoonView persoonView2 = maakPersoonView(persoon2Ingeschreven, persoon2Nederlander);

        bestaandeBetrokkenen.put(bsn1, persoonView1);
        bestaandeBetrokkenen.put(bsn2, persoonView2);

        return bestaandeBetrokkenen;
    }

    /**
     * Maakt een persoon his volledig view op basis van een bsn. De status ingeschreven kan via een boolean worden
     * meegegeven. Ook kan de persoon Nederlander zijn via een boolean.
     *
     * @param isIngeschrevene persoon is ingeschrevene
     * @param isNederlander   persoon is nederlander
     * @return persoon his volledig view
     */
    private PersoonView maakPersoonView(final boolean isIngeschrevene, final boolean isNederlander) {
        SoortPersoon soortPersoon = null;
        if (isIngeschrevene) {
            soortPersoon = SoortPersoon.INGESCHREVENE;
        } else {
            soortPersoon = SoortPersoon.NIET_INGESCHREVENE;
        }

        final PersoonHisVolledigImpl persoonHisVolledigImpl = new PersoonHisVolledigImplBuilder(soortPersoon)
                .build();

        final Set<PersoonNationaliteitHisVolledigImpl> nationaliteiten;
        if (isNederlander) {
            nationaliteiten = maakNationaliteiten(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE);
        } else {
            nationaliteiten = maakNationaliteiten(new NationaliteitcodeAttribuut("0002"));
        }
        persoonHisVolledigImpl.setNationaliteiten(nationaliteiten);

        return new PersoonView(persoonHisVolledigImpl);
    }

    /**
     * Maakt de set nationaliteiten.
     *
     * @param code code
     * @return set nationaliteiten
     */
    private Set<PersoonNationaliteitHisVolledigImpl> maakNationaliteiten(final NationaliteitcodeAttribuut code) {
        final Set<PersoonNationaliteitHisVolledigImpl> nationaliteiten =
                new HashSet<PersoonNationaliteitHisVolledigImpl>();
        final PersoonNationaliteitHisVolledigImpl nederlandseNationaliteit =
                new PersoonNationaliteitHisVolledigImplBuilder(maakNationaliteit(code))
                        .nieuwStandaardRecord(20000101, null, 20000101)
                        .eindeRecord()
                        .build();
        nationaliteiten.add(nederlandseNationaliteit);
        return nationaliteiten;
    }

    /**
     * Maakt een nationaliteit op basis van Nationaliteit code.
     *
     * @param code code
     * @return nationaliteit
     */
    private Nationaliteit maakNationaliteit(final NationaliteitcodeAttribuut code) {
        return new Nationaliteit(code, null, new DatumEvtDeelsOnbekendAttribuut(20000101), null);
    }
}
