/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.huwelijkgeregistreerdpartnerschap.acties
        .registratiehuwelijkpartnerschap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NationaliteitcodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.PartnerBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonNationaliteitHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.HuwelijkGeregistreerdPartnerschapView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.HuwelijkView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.RelatieBuilder;
import nl.bzk.brp.util.StamgegevenBuilder;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.hisvolledig.kern.HuwelijkHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonNationaliteitHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Unit test voor de regel {@link BRBY0437}.
 */
public class BRBY0437Test {

    private static final String IDENTIFICIERENDE_SLEUTEL_MAN = "id.man";

    private static final String IDENTIFICIERENDE_SLEUTEL_VROUW = "id.vrouw";
    private final BRBY0437 brby0437 = new BRBY0437();

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0437, brby0437.getRegel());
    }

    /**
     * Test huwelijk in Nederland met nederlands persoon met opgegeven geslachtsnaamcomponent.
     * Dit is niet toegestaan.
     */
    @Test
    public void testHuidigHuwelijkMetAanvangNLmetNLPersoonWijzigingNaam() {
        final HuwelijkGeregistreerdPartnerschapBericht huwelijk = maakHuwelijkBericht(true, true);

        final PersoonView man = maakPersoonView(true);
        final PersoonView vrouw = maakPersoonView(true);

        final List<BerichtEntiteit> berichtEntiteiten =
                brby0437.voerRegelUit(maakHuidigeSituatie(StatischeObjecttypeBuilder.LAND_NEDERLAND),
                                      huwelijk, null, maakBestaandeBetrokkenen(man, vrouw));

        Assert.assertEquals(2, berichtEntiteiten.size());
    }

    /**
     * Test huwelijk in Nederland met nederlands persoon zonder opgegeven geslachtsnaamcomponent.
     * Dit is toegestaan.
     */
    @Test
    public void testHuidigHuwelijkMetAanvangNLmetNLPersoonZonderWijzigingNaam() {
        final HuwelijkGeregistreerdPartnerschapBericht huwelijk = maakHuwelijkBericht(false, false);

        final PersoonView man = maakPersoonView(true);
        final PersoonView vrouw = maakPersoonView(true);

        final List<BerichtEntiteit> berichtEntiteiten =
                brby0437.voerRegelUit(maakHuidigeSituatie(StatischeObjecttypeBuilder.LAND_NEDERLAND),
                                      huwelijk, null, maakBestaandeBetrokkenen(man, vrouw));

        Assert.assertEquals(0, berichtEntiteiten.size());
    }

    /**
     * Test huwelijk in Nederland met een buitenlandse persoon met opgegeven geslachtsnaamcomponent.
     * Dit is toegestaan.
     */
    @Test
    public void testHuidigHuwelijkMetAanvangNLmetBuitelandsPersoonWijzigingNaam() {
        final HuwelijkGeregistreerdPartnerschapBericht huwelijk = maakHuwelijkBericht(true, true);

        final PersoonView man = maakPersoonView(false);
        final PersoonView vrouw = maakPersoonView(true);

        final List<BerichtEntiteit> berichtEntiteiten =
                brby0437.voerRegelUit(maakHuidigeSituatie(StatischeObjecttypeBuilder.LAND_NEDERLAND),
                                      huwelijk, null, maakBestaandeBetrokkenen(man, vrouw));

        Assert.assertEquals(1, berichtEntiteiten.size());
    }

    /**
     * Test huwelijk in Nederland met een buitenlandse persoon zonder opgegeven geslachtsnaamcomponent.
     * Dit is toegestaan.
     */
    @Test
    public void testHuidigHuwelijkMetAanvangNLmetBuitenlandsPersoonZonderWijzigingNaam() {
        final HuwelijkGeregistreerdPartnerschapBericht huwelijk = maakHuwelijkBericht(false, false);

        final PersoonView man = maakPersoonView(false);
        final PersoonView vrouw = maakPersoonView(false);

        final List<BerichtEntiteit> berichtEntiteiten =
                brby0437.voerRegelUit(maakHuidigeSituatie(StatischeObjecttypeBuilder.LAND_NEDERLAND),
                                      huwelijk, null, maakBestaandeBetrokkenen(man, vrouw));

        Assert.assertEquals(0, berichtEntiteiten.size());
    }


    /**
     * Test huwelijk in buiteland met nederlands persoon met opgegeven geslachtsnaamcomponent.
     * Dit is toegestaan.
     */
    @Test
    public void testHuidigHuwelijkMetAanvangInBuitenlandMetNLPersoonWijzigingNaam() {
        final HuwelijkGeregistreerdPartnerschapBericht huwelijk = maakHuwelijkBericht(true, true);

        final PersoonView man = maakPersoonView(true);
        final PersoonView vrouw = maakPersoonView(true);

        final List<BerichtEntiteit> berichtEntiteiten =
                brby0437.voerRegelUit(maakHuidigeSituatie(StatischeObjecttypeBuilder.LAND_AFGANISTAN),
                                      huwelijk, null, maakBestaandeBetrokkenen(man, vrouw));

        Assert.assertEquals(0, berichtEntiteiten.size());
    }

    /**
     * Test huwelijk in buiteland met nederlands persoon zonder opgegeven geslachtsnaamcomponent.
     * Dit is toegestaan.
     */
    @Test
    public void testHuidigHuwelijkMetAanvangBuitelandMetNLPersoonZonderWijzigingNaam() {
        final HuwelijkGeregistreerdPartnerschapBericht huwelijk = maakHuwelijkBericht(false, false);

        final PersoonView man = maakPersoonView(true);
        final PersoonView vrouw = maakPersoonView(true);

        final List<BerichtEntiteit> berichtEntiteiten =
                brby0437.voerRegelUit(maakHuidigeSituatie(StatischeObjecttypeBuilder.LAND_BELGIE),
                                      huwelijk, null, maakBestaandeBetrokkenen(man, vrouw));

        Assert.assertEquals(0, berichtEntiteiten.size());
    }

    /**
     * Test huwelijk in buiteland met een buitenlandse persoon met opgegeven geslachtsnaamcomponent.
     * Dit is toegestaan.
     */
    @Test
    public void testHuidigHuwelijkMetAanvangBuitelandMetBuitelandsPersoonWijzigingNaam() {
        final HuwelijkGeregistreerdPartnerschapBericht huwelijk = maakHuwelijkBericht(true, true);

        final PersoonView man = maakPersoonView(false);
        final PersoonView vrouw = maakPersoonView(false);

        final List<BerichtEntiteit> berichtEntiteiten =
                brby0437.voerRegelUit(maakHuidigeSituatie(StatischeObjecttypeBuilder.LAND_FRANKRIJK),
                                      huwelijk, null, maakBestaandeBetrokkenen(man, vrouw));

        Assert.assertEquals(0, berichtEntiteiten.size());
    }

    /**
     * Test huwelijk in buiteland met een buitenlandse persoon zonder opgegeven geslachtsnaamcomponent.
     * Dit is toegestaan.
     */
    @Test
    public void testHuidigHuwelijkMetAanvangBuitelandMetBuitenlandsPersoonZonderWijzigingNaam() {
        final HuwelijkGeregistreerdPartnerschapBericht huwelijk = maakHuwelijkBericht(false, false);

        final PersoonView man = maakPersoonView(false);
        final PersoonView vrouw = maakPersoonView(false);

        final List<BerichtEntiteit> berichtEntiteiten =
                brby0437.voerRegelUit(maakHuidigeSituatie(StatischeObjecttypeBuilder.LAND_AFGANISTAN),
                                      huwelijk, null, maakBestaandeBetrokkenen(man, vrouw));

        Assert.assertEquals(0, berichtEntiteiten.size());
    }

    /**
     * Test huwelijk zonder land aanvang met Nederlands persoon met opgegeven geslachtsnaamcomponent.
     * Dit zou moeten worden toegestaan.
     */
    @Test
    public void testHuidigHuwelijkZonderLandAanvang() {
        final HuwelijkGeregistreerdPartnerschapBericht huwelijk = maakHuwelijkBericht(true, true);
        huwelijk.getStandaard().setLandGebiedAanvang(null);

        final PersoonView man = maakPersoonView(true);
        final PersoonView vrouw = maakPersoonView(true);

        final List<BerichtEntiteit> berichtEntiteiten =
            brby0437.voerRegelUit(maakHuidigeSituatie(null), huwelijk, null, maakBestaandeBetrokkenen(man, vrouw));

        Assert.assertEquals(0, berichtEntiteiten.size());
    }

    /**
     * Test huwelijk met land aanvang Nederland en met Nederlands persoon zonder opgegeven geslachtsnaamcomponent,
     * maar wel met een niet-ingeschrevene.
     * Dit zou moeten worden toegestaan.
     */
    @Test
    public void testHuidigHuwelijkMetAanvangNLMetNietIngeschrevene() {
        final HuwelijkGeregistreerdPartnerschapBericht huwelijk = maakHuwelijkBericht(false, false);

        // Zet de vrouw naar een niet ingeschrevene
        for (final PartnerBericht partner : huwelijk.getPartnerBetrokkenheden()) {
            if (partner.getPersoon().getIdentificerendeSleutel().equals(IDENTIFICIERENDE_SLEUTEL_VROUW)) {
                partner.getPersoon().setSoort(new SoortPersoonAttribuut(SoortPersoon.NIET_INGESCHREVENE));
            }
        }

        final PersoonView man = maakPersoonView(true);

        // Bij de bestaande betrokkenen wordt de vrouw niet geretourneerd, daar deze een niet-ingeschrevene is
        // en dus niet uit de database komt.
        final List<BerichtEntiteit> berichtEntiteiten =
            brby0437.voerRegelUit(maakHuidigeSituatie(StatischeObjecttypeBuilder.LAND_NEDERLAND),
                                  huwelijk, null, maakBestaandeBetrokkenen(man, null));

        Assert.assertEquals(0, berichtEntiteiten.size());
    }

    /**
     * Test huwelijk met land aanvang Nederland en met Nederlands persoon zonder opgegeven geslachtsnaamcomponent,
     * maar wel met een niet-ingeschrevene die uit de database komt en een geslachtsnaamcomponent heeft.
     * (dit kan officieel niet, maar puur ter test, want dient wel op getest te worden gezien de beschrijving van de
     * regel).
     * Dit zou moeten worden toegestaan.
     */
    @Test
    public void testHuidigHuwelijkMetAanvangNLMetNietIngeschreveneUitDatabase() {
        final HuwelijkGeregistreerdPartnerschapBericht huwelijk = maakHuwelijkBericht(false, true);

        // Zet de vrouw naar een niet ingeschrevene
        for (final PartnerBericht partner : huwelijk.getPartnerBetrokkenheden()) {
            if (partner.getPersoon().getIdentificerendeSleutel().equals(IDENTIFICIERENDE_SLEUTEL_VROUW)) {
                partner.getPersoon().setSoort(new SoortPersoonAttribuut(SoortPersoon.NIET_INGESCHREVENE));
            }
        }

        final PersoonView man = maakPersoonView(true);
        final PersoonView vrouw = maakPersoonView(true);
        // Zet ook de uit de database afkomstige vrouw naar niet ingeschrevene
        ReflectionTestUtils.setField(
            ReflectionTestUtils.getField(vrouw, "persoon"), "soort",
            new SoortPersoonAttribuut(SoortPersoon.NIET_INGESCHREVENE));

        final List<BerichtEntiteit> berichtEntiteiten =
            brby0437.voerRegelUit(maakHuidigeSituatie(StatischeObjecttypeBuilder.LAND_NEDERLAND),
                                  huwelijk, null, maakBestaandeBetrokkenen(man, vrouw));

        Assert.assertEquals(0, berichtEntiteiten.size());
    }

    /**
     * Nieuw huwelijk, dus huidige situatie is null.
     * Test huwelijk in Nederland met nederlands persoon met opgegeven geslachtsnaamcomponent.
     * Dit is niet toegestaan.
     */
    @Test
    public void testNieuwHuwelijkMetAanvangNLmetNLPersoonWijzigingNaam() {
        final HuwelijkGeregistreerdPartnerschapBericht huwelijk = maakHuwelijkBericht(true, true);
        huwelijk.getStandaard().setLandGebiedAanvang(StatischeObjecttypeBuilder.LAND_NEDERLAND);

        final PersoonView man = maakPersoonView(true);
        final PersoonView vrouw = maakPersoonView(true);

        final List<BerichtEntiteit> berichtEntiteiten =
                brby0437.voerRegelUit(null, huwelijk, null, maakBestaandeBetrokkenen(man, vrouw));

        Assert.assertEquals(2, berichtEntiteiten.size());
    }

    /**
     * Nieuw huwelijk, dus huidige situatie is null.
     * Test huwelijk in Nederland met nederlands persoon zonder opgegeven geslachtsnaamcomponent.
     * Dit is toegestaan.
     */
    @Test
    public void testNieuwHuwelijkMetAanvangNLmetNLPersoonZonderWijzigingNaam() {
        final HuwelijkGeregistreerdPartnerschapBericht huwelijk = maakHuwelijkBericht(false, false);

        final PersoonView man = maakPersoonView(true);
        final PersoonView vrouw = maakPersoonView(true);

        final List<BerichtEntiteit> berichtEntiteiten =
                brby0437.voerRegelUit(null, huwelijk, null, maakBestaandeBetrokkenen(man, vrouw));

        Assert.assertEquals(0, berichtEntiteiten.size());
    }

    /**
     * Maakt een huwelijk bericht met een man, vrouw en standaard id's.
     *
     * @return huwelijk geregistreerd partnerschap bericht
     */
    private HuwelijkGeregistreerdPartnerschapBericht maakHuwelijkBericht(final boolean wijzigingGeslachtsnaamMan,
                                                                         final boolean wijzigingGeslachtsnaamVrouw)
    {
        final PersoonBericht manBericht = new PersoonBericht();
        manBericht.setIdentificerendeSleutel(IDENTIFICIERENDE_SLEUTEL_MAN);

        if (wijzigingGeslachtsnaamMan) {
            manBericht.setGeslachtsnaamcomponenten(new ArrayList<PersoonGeslachtsnaamcomponentBericht>());
            manBericht.getGeslachtsnaamcomponenten().add(new PersoonGeslachtsnaamcomponentBericht());
        }

        final PersoonBericht vrouwBericht = new PersoonBericht();
        vrouwBericht.setIdentificerendeSleutel(IDENTIFICIERENDE_SLEUTEL_VROUW);

        if (wijzigingGeslachtsnaamVrouw) {
            vrouwBericht.setGeslachtsnaamcomponenten(new ArrayList<PersoonGeslachtsnaamcomponentBericht>());
            vrouwBericht.getGeslachtsnaamcomponenten().add(new PersoonGeslachtsnaamcomponentBericht());
        }

        return new RelatieBuilder<HuwelijkGeregistreerdPartnerschapBericht>().bouwHuwelijkRelatie()
                .voegPartnerToe(manBericht)
                .voegPartnerToe(vrouwBericht)
                .getRelatie();
    }

    private HuwelijkGeregistreerdPartnerschapView maakHuidigeSituatie(final LandGebiedAttribuut landAanvang)
    {
        final HuwelijkHisVolledigImplBuilder.HuwelijkHisVolledigImplBuilderStandaard huwelijk =
                new HuwelijkHisVolledigImplBuilder().nieuwStandaardRecord(20120101);

        if (landAanvang != null) {
            huwelijk.landGebiedAanvang(landAanvang.getWaarde());
        }
        return new HuwelijkView(huwelijk.eindeRecord().build(), DatumTijdAttribuut.nu());
    }

    /**
     * Maakt een map met 'bestaande' (dus uit de database opgehaalde) personen voor de opgegeven man en vrouw.
     *
     * @param man de man.
     * @param vrouw de vrouw.
     * @return een map met daarin de man en vrouw gemapt naar hun sleutel.
     */
    private Map<String, PersoonView> maakBestaandeBetrokkenen(
            final PersoonView man, final PersoonView vrouw)
    {
        final Map<String, PersoonView> bestaandeBetrokkenen = new HashMap<>();
        bestaandeBetrokkenen.put(IDENTIFICIERENDE_SLEUTEL_MAN, man);
        bestaandeBetrokkenen.put(IDENTIFICIERENDE_SLEUTEL_VROUW, vrouw);
        return bestaandeBetrokkenen;
    }

    /**
     * Instantieert en retourneert een {@link PersoonView} met een nationaliteit, waarbij op basis van de opgegeven
     * boolean de nationaliteit nederlands is. De Persoon is een ingeschrevene.
     *
     * @param isNederlands of de Nederlandse nationaliteit moet worden toegevoegd of niet.
     * @return een persoonView.
     */
    private PersoonView maakPersoonView(final boolean isNederlands) {
        final PersoonHisVolledigImpl persoon = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .build();

        if (isNederlands) {
            final PersoonNationaliteitHisVolledigImpl persoonNationaliteit = new PersoonNationaliteitHisVolledigImplBuilder(
                    StamgegevenBuilder
                            .bouwDynamischStamgegeven(Nationaliteit.class, NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE))
                    .nieuwStandaardRecord(DatumAttribuut.vandaag().getWaarde(), null, DatumAttribuut.vandaag().getWaarde())
                    .eindeRecord().build();
            persoon.getNationaliteiten().add(persoonNationaliteit);
        }

        return new PersoonView(persoon);
    }
}
