/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AdellijkeTitel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Naamgebruik;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import org.junit.Before;
import org.junit.Test;

/**
 * Testen van afleiding voor {@link Persoon}.
 */
public class PersoonAfleidingTest {

    private final Integer datumAanvangGeldigheidIniteel   = 20010101;
    private final Integer datumAanvangGeldigheidWijziging = 20020202;
    private BRPActie samengesteldeNaamActie;
    private BRPActie voornaamActie;
    private BRPActie geslachtsnaamcomponentActie;

    @Before
    public void setup() {
        final AdministratieveHandeling administratieveHandelingInitieel =
            new AdministratieveHandeling(new Partij("partij", "000000"), SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND, new Timestamp(
                System.currentTimeMillis()));
        final AdministratieveHandeling administratieveHandelingWijziging =
            new AdministratieveHandeling(new Partij("partij", "000000"), SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND, new Timestamp(
                administratieveHandelingInitieel.getDatumTijdRegistratie().getTime() + 1000));
        samengesteldeNaamActie =
            new BRPActie(
                SoortActie.REGISTRATIE_AANVANG_HUWELIJK,
                administratieveHandelingInitieel,
                administratieveHandelingInitieel.getPartij(),
                administratieveHandelingInitieel.getDatumTijdRegistratie());
        samengesteldeNaamActie.setId(1L);
        voornaamActie =
            new BRPActie(
                SoortActie.REGISTRATIE_AANVANG_HUWELIJK,
                administratieveHandelingWijziging,
                administratieveHandelingWijziging.getPartij(),
                administratieveHandelingWijziging.getDatumTijdRegistratie());
        voornaamActie.setId(2L);
        geslachtsnaamcomponentActie =
            new BRPActie(
                SoortActie.REGISTRATIE_AANVANG_HUWELIJK,
                administratieveHandelingWijziging,
                administratieveHandelingWijziging.getPartij(),
                administratieveHandelingWijziging.getDatumTijdRegistratie());
        geslachtsnaamcomponentActie.setId(3L);
    }

    @Test
    public void testLeidAfZonderWijziging() {
        // setup
        final Persoon persoon = maakTemplatePersoon();
        voegNaamgebruikHistorieToe(persoon);
        assertEquals(1, persoon.getPersoonNaamgebruikHistorieSet().size());
        assertEquals(1, persoon.getPersoonSamengesteldeNaamHistorieSet().size());
        // execute
        persoon.leidAf(geslachtsnaamcomponentActie, datumAanvangGeldigheidWijziging, false);
        // verify
        assertEquals(1, persoon.getPersoonSamengesteldeNaamHistorieSet().size());
        assertEquals(1, persoon.getPersoonNaamgebruikHistorieSet().size());
    }

    private void voegNaamgebruikHistorieToe(final Persoon persoon) {
        final PersoonNaamgebruikHistorie naamgebruikHistorie = new PersoonNaamgebruikHistorie(persoon, "Puk", true, Naamgebruik.EIGEN);
        naamgebruikHistorie.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        persoon.addPersoonNaamgebruikHistorie(naamgebruikHistorie);
    }

    private Persoon voegRelatieToeEnGeefPartnerTerug(final Persoon persoon) {
        final Persoon partner = new Persoon(SoortPersoon.INGESCHREVENE);
        final Relatie relatie = new Relatie(SoortRelatie.HUWELIJK);
        final RelatieHistorie relatieHistorie = new RelatieHistorie(relatie);
        relatieHistorie.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        relatie.addRelatieHistorie(relatieHistorie);

        final Betrokkenheid ikBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatie);
        ikBetrokkenheid.setPersoon(persoon);
        persoon.addBetrokkenheid(ikBetrokkenheid);
        relatie.addBetrokkenheid(ikBetrokkenheid);

        final Betrokkenheid partnerBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatie);
        partnerBetrokkenheid.setPersoon(partner);
        partner.addBetrokkenheid(partnerBetrokkenheid);
        relatie.addBetrokkenheid(partnerBetrokkenheid);

        final PersoonNaamgebruikHistorie naamgebruikHistorie = new PersoonNaamgebruikHistorie(partner, "Boef", true, Naamgebruik.PARTNER);
        naamgebruikHistorie.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        partner.addPersoonNaamgebruikHistorie(naamgebruikHistorie);

        final PersoonSamengesteldeNaamHistorie samengesteldeNaamHistorie = new PersoonSamengesteldeNaamHistorie(partner, "Boef", true, false);
        partner.addPersoonSamengesteldeNaamHistorie(samengesteldeNaamHistorie);
        return partner;
    }

    @Test
    public void testLeidAfNietIngeschrevene() {
        // setup
        final Persoon persoon = maakTemplatePersoon();
        persoon.setSoortPersoon(SoortPersoon.PSEUDO_PERSOON);
        persoon.getPersoonGeslachtsnaamcomponentSet()
            .iterator()
            .next()
            .getPersoonGeslachtsnaamcomponentHistorieSet()
            .iterator()
            .next()
            .setStam("stamwijziging");
        assertEquals(1, persoon.getPersoonSamengesteldeNaamHistorieSet().size());
        // execute
        persoon.leidAf(geslachtsnaamcomponentActie, datumAanvangGeldigheidWijziging, false);
        // verify
        assertEquals(1, persoon.getPersoonSamengesteldeNaamHistorieSet().size());
    }

    @Test
    public void testLeidAfWijzigingGeslachtsnaamcomponent() {
        // setup
        final Persoon persoon = maakTemplatePersoon();
        persoon.getPersoonGeslachtsnaamcomponentSet()
            .iterator()
            .next()
            .getPersoonGeslachtsnaamcomponentHistorieSet()
            .iterator()
            .next()
            .setStam("stamwijziging");
        assertEquals(1, persoon.getPersoonSamengesteldeNaamHistorieSet().size());
        // execute
        persoon.leidAf(geslachtsnaamcomponentActie, datumAanvangGeldigheidWijziging, false);
        // verify
        assertEquals(3, persoon.getPersoonSamengesteldeNaamHistorieSet().size());
        final List<PersoonSamengesteldeNaamHistorie> voorkomens = new ArrayList<>(persoon.getPersoonSamengesteldeNaamHistorieSet());
        final PersoonSamengesteldeNaamHistorie vervallenVoorkomen = voorkomens.get(0);
        final PersoonSamengesteldeNaamHistorie eindeGeldigheidVoorkomen = voorkomens.get(1);
        final PersoonSamengesteldeNaamHistorie nieuwVoorkomen = voorkomens.get(2);
        // verantwoording
        assertTrue(vervallenVoorkomen.isVervallen());
        assertFalse(eindeGeldigheidVoorkomen.isVervallen());
        assertFalse(nieuwVoorkomen.isVervallen());
        assertEquals(datumAanvangGeldigheidIniteel, vervallenVoorkomen.getDatumAanvangGeldigheid());
        assertEquals(datumAanvangGeldigheidIniteel, eindeGeldigheidVoorkomen.getDatumAanvangGeldigheid());
        assertEquals(datumAanvangGeldigheidWijziging, nieuwVoorkomen.getDatumAanvangGeldigheid());
        assertNull(vervallenVoorkomen.getDatumEindeGeldigheid());
        assertNull(nieuwVoorkomen.getDatumEindeGeldigheid());
        assertEquals(datumAanvangGeldigheidWijziging, eindeGeldigheidVoorkomen.getDatumEindeGeldigheid());
        assertEquals(samengesteldeNaamActie.getDatumTijdRegistratie(), vervallenVoorkomen.getDatumTijdRegistratie());
        assertEquals(geslachtsnaamcomponentActie.getDatumTijdRegistratie(), vervallenVoorkomen.getDatumTijdVerval());
        assertEquals(geslachtsnaamcomponentActie.getDatumTijdRegistratie(), eindeGeldigheidVoorkomen.getDatumTijdRegistratie());
        assertEquals(geslachtsnaamcomponentActie.getDatumTijdRegistratie(), nieuwVoorkomen.getDatumTijdRegistratie());
        // actieinhoud komt voort uit wijziging geslachtsnaamcomponent
        assertEquals(geslachtsnaamcomponentActie.getId(), nieuwVoorkomen.getActieInhoud().getId());
        // inhoud
        assertEquals("stam", vervallenVoorkomen.getGeslachtsnaamstam());
        assertEquals("stam", eindeGeldigheidVoorkomen.getGeslachtsnaamstam());
        assertEquals("stamwijziging", nieuwVoorkomen.getGeslachtsnaamstam());
        assertTrue(vervallenVoorkomen.isInhoudelijkGelijkAan(eindeGeldigheidVoorkomen));
        final PersoonGeslachtsnaamcomponentHistorie geslachtsnaamcomponentVoorkomen =
            persoon.getPersoonGeslachtsnaamcomponentSet().iterator().next().getPersoonGeslachtsnaamcomponentHistorieSet().iterator().next();
        assertEquals(geslachtsnaamcomponentVoorkomen.getStam(), nieuwVoorkomen.getGeslachtsnaamstam());
        assertTrue(nieuwVoorkomen.getIndicatieAfgeleid());
        assertEquals(geslachtsnaamcomponentVoorkomen.getScheidingsteken(), nieuwVoorkomen.getScheidingsteken());
        assertEquals(geslachtsnaamcomponentVoorkomen.getVoorvoegsel(), nieuwVoorkomen.getVoorvoegsel());
        assertEquals(geslachtsnaamcomponentVoorkomen.getAdellijkeTitel(), nieuwVoorkomen.getAdellijkeTitel());
        assertEquals(geslachtsnaamcomponentVoorkomen.getPredicaat(), nieuwVoorkomen.getPredicaat());
        assertEquals("voornaam1 voornaam2", nieuwVoorkomen.getVoornamen());

        // Geen bestaande naamgebruik dus niet mogelijk om een nieuwe te maken
        assertEquals(0, persoon.getPersoonNaamgebruikHistorieSet().size());
    }

    @Test
    public void testLeidAfWijzigingVoornamenVolgorde() {
        // setup
        final Persoon persoon = maakTemplatePersoon();
        voegNaamgebruikHistorieToe(persoon);

        assertEquals(1, persoon.getPersoonNaamgebruikHistorieSet().size());

        persoon.getPersoonVoornaamSet().iterator().next().setVolgnummer(3);
        persoon.getPersoonVoornaamSet().iterator().next().getPersoonVoornaamHistorieSet().iterator().next().setNaam("X");

        // persoon.getPersoonVoornaamSet().forEach(persoonVoornaam -> persoonVoornaam.);
        assertEquals(1, persoon.getPersoonSamengesteldeNaamHistorieSet().size());
        // execute
        persoon.leidAf(voornaamActie, datumAanvangGeldigheidWijziging, false);
        // verify
        assertEquals(3, persoon.getPersoonSamengesteldeNaamHistorieSet().size());
        final List<PersoonSamengesteldeNaamHistorie> voorkomens = new ArrayList<>(persoon.getPersoonSamengesteldeNaamHistorieSet());
        final PersoonSamengesteldeNaamHistorie nieuwVoorkomen = voorkomens.get(2);

        // verify
        assertEquals("voornaam2 X", nieuwVoorkomen.getVoornamen());
    }

    @Test
    public void testLeidAfWijzigingVoornamen() {
        // setup
        final Persoon persoon = maakTemplatePersoon();
        voegNaamgebruikHistorieToe(persoon);
        final Persoon partner = voegRelatieToeEnGeefPartnerTerug(persoon);

        assertEquals(1, partner.getPersoonNaamgebruikHistorieSet().size());
        assertEquals(1, persoon.getPersoonNaamgebruikHistorieSet().size());

        persoon.getPersoonVoornaamSet().iterator().next().getPersoonVoornaamHistorieSet().iterator().next().setNaam("X");
        assertEquals(1, persoon.getPersoonSamengesteldeNaamHistorieSet().size());
        // execute
        persoon.leidAf(voornaamActie, datumAanvangGeldigheidWijziging, false);
        // verify
        assertEquals(3, persoon.getPersoonSamengesteldeNaamHistorieSet().size());
        final List<PersoonSamengesteldeNaamHistorie> voorkomens = new ArrayList<>(persoon.getPersoonSamengesteldeNaamHistorieSet());
        final PersoonSamengesteldeNaamHistorie vervallenVoorkomen = voorkomens.get(0);
        final PersoonSamengesteldeNaamHistorie eindeGeldigheidVoorkomen = voorkomens.get(1);
        final PersoonSamengesteldeNaamHistorie nieuwVoorkomen = voorkomens.get(2);
        // verantwoording
        assertTrue(vervallenVoorkomen.isVervallen());
        assertFalse(eindeGeldigheidVoorkomen.isVervallen());
        assertFalse(nieuwVoorkomen.isVervallen());
        // actieinhoud komt voort uit wijziging voornaam
        assertEquals(voornaamActie.getId(), nieuwVoorkomen.getActieInhoud().getId());
        // inhoud
        assertEquals("X voornaam2", nieuwVoorkomen.getVoornamen());

        assertEquals(2, persoon.getPersoonNaamgebruikHistorieSet().size());
        assertEquals(2, partner.getPersoonNaamgebruikHistorieSet().size());
        final PersoonNaamgebruikHistorie actueelPartnerNaamgebruikHistorie =
            FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(partner.getPersoonNaamgebruikHistorieSet());
        assertEquals(nieuwVoorkomen.getGeslachtsnaamstam(), actueelPartnerNaamgebruikHistorie.getGeslachtsnaamstamNaamgebruik());
    }

    @Test
    public void testLeidAfWijzigingZonderVoornamen() {
        // setup
        final Persoon persoon = maakTemplatePersoon();
        persoon.getPersoonVoornaamSet().clear();
        assertEquals(1, persoon.getPersoonSamengesteldeNaamHistorieSet().size());
        // execute
        persoon.leidAf(voornaamActie, datumAanvangGeldigheidWijziging, false);
        // verify
        assertEquals(3, persoon.getPersoonSamengesteldeNaamHistorieSet().size());
        final List<PersoonSamengesteldeNaamHistorie> voorkomens = new ArrayList<>(persoon.getPersoonSamengesteldeNaamHistorieSet());
        final PersoonSamengesteldeNaamHistorie vervallenVoorkomen = voorkomens.get(0);
        final PersoonSamengesteldeNaamHistorie eindeGeldigheidVoorkomen = voorkomens.get(1);
        final PersoonSamengesteldeNaamHistorie nieuwVoorkomen = voorkomens.get(2);
        // verantwoording
        assertTrue(vervallenVoorkomen.isVervallen());
        assertFalse(eindeGeldigheidVoorkomen.isVervallen());
        assertFalse(nieuwVoorkomen.isVervallen());
        // actieinhoud komt voort uit wijziging voornaam
        assertEquals(voornaamActie.getId(), nieuwVoorkomen.getActieInhoud().getId());
        // inhoud
        assertNull(nieuwVoorkomen.getVoornamen());
    }

    @Test
    public void testLeidAfWijzigingZonderActueelGeslachtsnaamcomponent() {
        // setup
        final Persoon persoon = maakTemplatePersoon();
        persoon.getPersoonGeslachtsnaamcomponentSet()
            .iterator()
            .next()
            .getPersoonGeslachtsnaamcomponentHistorieSet()
            .iterator()
            .next()
            .setDatumTijdVerval(new Timestamp(System.currentTimeMillis()));
        assertEquals(1, persoon.getPersoonSamengesteldeNaamHistorieSet().size());
        // execute
        persoon.leidAf(geslachtsnaamcomponentActie, datumAanvangGeldigheidWijziging, false);
        // verify
        assertEquals(1, persoon.getPersoonSamengesteldeNaamHistorieSet().size());
    }

    @Test
    public void testLeidAfWijzigingZonderGeslachtsnaamcomponent() {
        // setup
        final Persoon persoon = maakTemplatePersoon();
        persoon.getPersoonGeslachtsnaamcomponentSet().clear();
        assertEquals(1, persoon.getPersoonSamengesteldeNaamHistorieSet().size());
        // execute
        persoon.leidAf(geslachtsnaamcomponentActie, datumAanvangGeldigheidWijziging, false);
        // verify
        assertEquals(1, persoon.getPersoonSamengesteldeNaamHistorieSet().size());
    }

    private Persoon maakTemplatePersoon() {
        final Persoon result = new Persoon(SoortPersoon.INGESCHREVENE);
        result.getPersoonVoornaamSet().addAll(maakVoornamen(result, "voornaam1", "voornaam2"));
        final PersoonGeslachtsnaamcomponent geslachtsnaamcomponent = maakGeslachtsnaamcomponent(result);
        result.getPersoonGeslachtsnaamcomponentSet().add(geslachtsnaamcomponent);
        result.getPersoonSamengesteldeNaamHistorieSet()
            .add(
                maakPersoonSamengesteldeNaamHistorie(
                    result,
                    geslachtsnaamcomponent.getPersoonGeslachtsnaamcomponentHistorieSet().iterator().next(),
                    "voornaam1 " + "voornaam2"));
        return result;
    }

    private PersoonSamengesteldeNaamHistorie maakPersoonSamengesteldeNaamHistorie(
        final Persoon persoon,
        final PersoonGeslachtsnaamcomponentHistorie geslachtsnaamcomponent,
        final String voornamen)
    {
        final PersoonSamengesteldeNaamHistorie result = new PersoonSamengesteldeNaamHistorie(persoon, geslachtsnaamcomponent.getStam(), true, false);
        result.setScheidingsteken(geslachtsnaamcomponent.getScheidingsteken());
        result.setVoornamen(voornamen);
        result.setVoorvoegsel(geslachtsnaamcomponent.getVoorvoegsel());
        result.setAdellijkeTitel(geslachtsnaamcomponent.getAdellijkeTitel());
        result.setPredicaat(geslachtsnaamcomponent.getPredicaat());
        result.setActieInhoud(samengesteldeNaamActie);
        result.setDatumTijdRegistratie(samengesteldeNaamActie.getDatumTijdRegistratie());
        result.setDatumAanvangGeldigheid(datumAanvangGeldigheidIniteel);
        return result;
    }

    private PersoonGeslachtsnaamcomponent maakGeslachtsnaamcomponent(final Persoon persoon) {
        final PersoonGeslachtsnaamcomponent result = new PersoonGeslachtsnaamcomponent(persoon, 1);
        final PersoonGeslachtsnaamcomponentHistorie historie = new PersoonGeslachtsnaamcomponentHistorie(result, "stam");
        historie.setScheidingsteken(' ');
        historie.setVoorvoegsel("van");
        historie.setAdellijkeTitel(AdellijkeTitel.B);
        historie.setPredicaat(Predicaat.H);
        historie.setActieInhoud(geslachtsnaamcomponentActie);
        historie.setDatumTijdRegistratie(geslachtsnaamcomponentActie.getDatumTijdRegistratie());
        historie.setDatumAanvangGeldigheid(datumAanvangGeldigheidWijziging);
        result.addPersoonGeslachtsnaamcomponentHistorie(historie);
        return result;
    }

    private Set<PersoonVoornaam> maakVoornamen(final Persoon persoon, final String... voornamen) {
        final Set<PersoonVoornaam> result = new LinkedHashSet<>();
        int voornaamIndex = 1;
        for (String voornaamParam : voornamen) {
            final PersoonVoornaam voornaam = new PersoonVoornaam(persoon, voornaamIndex++);
            final PersoonVoornaamHistorie voornaamHistorie = new PersoonVoornaamHistorie(voornaam, voornaamParam);
            voornaamHistorie.setActieInhoud(voornaamActie);
            voornaamHistorie.setDatumTijdRegistratie(voornaamActie.getDatumTijdRegistratie());
            voornaamHistorie.setDatumAanvangGeldigheid(datumAanvangGeldigheidWijziging);
            voornaam.addPersoonVoornaamHistorie(voornaamHistorie);
            result.add(voornaam);
        }
        return result;
    }
}
