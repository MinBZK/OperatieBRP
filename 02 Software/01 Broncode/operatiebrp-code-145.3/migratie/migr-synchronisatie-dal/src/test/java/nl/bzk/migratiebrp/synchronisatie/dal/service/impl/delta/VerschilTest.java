/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AbstractEntiteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdresHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsaanduidingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonMigratieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMigratie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.Sleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EntiteitSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.IstSleutel;

/**
 * Unit test voor {@link Verschil}
 */
public class VerschilTest extends AbstractDeltaTest {

    private Verschil verschil;
    private Sleutel sleutel;
    private Persoon nieuwPersoon;
    private Persoon bestaandPersoon;
    private FormeleHistorie bestaandeHistorie;
    private FormeleHistorie nieuweHistorie;

    @Before
    public void setUp() {
        bestaandPersoon = maakPersoon(true);
        nieuwPersoon = maakPersoon(false);
        sleutel = new EntiteitSleutel(Persoon.class, "persoonBijhoudingHistorieSet");
        bestaandeHistorie = new PersoonBijhoudingHistorie(bestaandPersoon, maakPartij(), Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL);
        nieuweHistorie = new PersoonBijhoudingHistorie(nieuwPersoon, maakPartij(), Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL);

        verschil = new Verschil(sleutel, bestaandPersoon, nieuwPersoon, bestaandeHistorie, nieuweHistorie);
    }

    @Test
    public void testConstructors() {
        final Verschil verschil1 = new Verschil(sleutel, bestaandPersoon, nieuwPersoon, bestaandeHistorie, nieuweHistorie);
        assertEquals(sleutel, verschil1.getSleutel());
        assertEquals(bestaandPersoon, verschil1.getOudeWaarde());
        assertEquals(nieuwPersoon, verschil1.getNieuweWaarde());
        assertEquals(bestaandeHistorie, verschil1.getBestaandeHistorieRij());
        assertEquals(nieuweHistorie, verschil1.getNieuweHistorieRij());
        assertEquals(VerschilType.ELEMENT_AANGEPAST, verschil1.getVerschilType());
        assertFalse(verschil1.isConsolidatieVerschil());
        assertFalse(verschil1.isOnderzoekVerschil());

        final Verschil verschil2 =
                new Verschil(sleutel, bestaandPersoon, nieuwPersoon, VerschilType.NIEUWE_RIJ_ELEMENT_AANGEPAST, bestaandeHistorie, nieuweHistorie);
        assertEquals(sleutel, verschil2.getSleutel());
        assertEquals(bestaandPersoon, verschil2.getOudeWaarde());
        assertEquals(nieuwPersoon, verschil2.getNieuweWaarde());
        assertEquals(bestaandeHistorie, verschil2.getBestaandeHistorieRij());
        assertEquals(nieuweHistorie, verschil2.getNieuweHistorieRij());
        assertEquals(VerschilType.NIEUWE_RIJ_ELEMENT_AANGEPAST, verschil2.getVerschilType());
        assertFalse(verschil1.isConsolidatieVerschil());
        assertFalse(verschil1.isOnderzoekVerschil());
    }

    @Test
    public void testmaakConsolidatieVerschilRijToevoegen() {
        final Verschil verschil1 = Verschil.maakConsolidatieVerschilRijToevoegen(sleutel, nieuweHistorie);
        assertEquals(sleutel, verschil1.getSleutel());
        assertNull(verschil1.getOudeWaarde());
        assertEquals(nieuweHistorie, verschil1.getNieuweWaarde());
        assertNull(verschil1.getBestaandeHistorieRij());
        assertEquals(nieuweHistorie, verschil1.getNieuweHistorieRij());
        assertEquals(VerschilType.RIJ_TOEGEVOEGD, verschil1.getVerschilType());
        assertTrue(verschil1.isConsolidatieVerschil());
        assertFalse(verschil1.isOnderzoekVerschil());
    }

    @Test
    public void testmaakConsolidatieHistorieContextVerschil() {
        final Verschil verschil1 = Verschil.maakConsolidatieHistorieContextVerschil(sleutel, nieuweHistorie);
        assertEquals(sleutel, verschil1.getSleutel());
        assertNull(verschil1.getOudeWaarde());
        assertNull(verschil1.getNieuweWaarde());
        assertEquals(nieuweHistorie, verschil1.getBestaandeHistorieRij());
        assertNull(verschil1.getNieuweHistorieRij());
        assertNull(verschil1.getVerschilType());
        assertTrue(verschil1.isConsolidatieVerschil());
        assertFalse(verschil1.isOnderzoekVerschil());
    }

    @Test
    public void testMaakConsolidatieVerschil() {
        final Verschil verschil1 = Verschil.maakConsolidatieVerschil(sleutel, bestaandPersoon, nieuwPersoon, bestaandeHistorie, nieuweHistorie);
        assertEquals(sleutel, verschil1.getSleutel());
        assertEquals(bestaandPersoon, verschil1.getOudeWaarde());
        assertEquals(nieuwPersoon, verschil1.getNieuweWaarde());
        assertEquals(bestaandeHistorie, verschil1.getBestaandeHistorieRij());
        assertEquals(nieuweHistorie, verschil1.getNieuweHistorieRij());
        assertEquals(VerschilType.ELEMENT_AANGEPAST, verschil1.getVerschilType());
        assertTrue(verschil1.isConsolidatieVerschil());
        assertFalse(verschil1.isOnderzoekVerschil());
    }

    @Test
    public void testMaakVerschil() {
        final Verschil verschil1 = Verschil.maakVerschil(sleutel, bestaandPersoon, nieuwPersoon, bestaandeHistorie, nieuweHistorie);
        assertEquals(sleutel, verschil1.getSleutel());
        assertEquals(bestaandPersoon, verschil1.getOudeWaarde());
        assertEquals(nieuwPersoon, verschil1.getNieuweWaarde());
        assertEquals(bestaandeHistorie, verschil1.getBestaandeHistorieRij());
        assertEquals(nieuweHistorie, verschil1.getNieuweHistorieRij());
        assertEquals(VerschilType.ELEMENT_AANGEPAST, verschil1.getVerschilType());
        assertFalse(verschil1.isConsolidatieVerschil());
        assertFalse(verschil1.isOnderzoekVerschil());
    }

    @Test
    public void testMaakOnderzoekVerschil() {
        final Sleutel onderzoekSleutel = new EntiteitSleutel(Persoon.class, AbstractEntiteit.GEGEVEN_IN_ONDERZOEK);
        final Verschil verschil1 = Verschil.maakVerschil(onderzoekSleutel, bestaandPersoon, nieuwPersoon, bestaandeHistorie, nieuweHistorie);
        assertEquals(onderzoekSleutel, verschil1.getSleutel());
        assertEquals(bestaandPersoon, verschil1.getOudeWaarde());
        assertEquals(nieuwPersoon, verschil1.getNieuweWaarde());
        assertEquals(bestaandeHistorie, verschil1.getBestaandeHistorieRij());
        assertEquals(nieuweHistorie, verschil1.getNieuweHistorieRij());
        assertEquals(VerschilType.ELEMENT_AANGEPAST, verschil1.getVerschilType());
        assertFalse(verschil1.isConsolidatieVerschil());
        assertTrue(verschil1.isOnderzoekVerschil());
    }

    @Test
    public void testMaakKopieMetNieuweSleutel() {
        final Sleutel onderzoekSleutel = new EntiteitSleutel(Persoon.class, AbstractEntiteit.GEGEVEN_IN_ONDERZOEK);
        final Verschil kopieVerschil = verschil.maakKopieMetNieuweSleutel(onderzoekSleutel);
        assertEquals(onderzoekSleutel, kopieVerschil.getSleutel());
        assertEquals(verschil.getOudeWaarde(), kopieVerschil.getOudeWaarde());
        assertEquals(verschil.getNieuweWaarde(), kopieVerschil.getNieuweWaarde());
        assertEquals(verschil.getBestaandeHistorieRij(), kopieVerschil.getBestaandeHistorieRij());
        assertEquals(verschil.getNieuweHistorieRij(), kopieVerschil.getNieuweHistorieRij());
        assertEquals(verschil.getVerschilType(), kopieVerschil.getVerschilType());
        assertEquals(verschil.isConsolidatieVerschil(), kopieVerschil.isConsolidatieVerschil());
        assertTrue(kopieVerschil.isOnderzoekVerschil());
    }

    @Test
    public void testToString() {
        assertEquals("Verschil[verschiltype=ELEMENT_AANGEPAST]", verschil.toString());
    }

    @Test
    public void testEqualsAndHashcode() {
        final Verschil verschil1 = new Verschil(sleutel, null, nieuwPersoon, VerschilType.RIJ_TOEGEVOEGD, null, nieuweHistorie);
        assertTrue(verschil.equals(verschil));
        assertFalse(verschil.equals(verschil1));
        assertFalse(verschil.equals(bestaandPersoon));
        assertFalse(verschil1.equals(verschil));
        assertTrue(verschil.hashCode() == verschil.hashCode());
        assertFalse(verschil.hashCode() == verschil1.hashCode());
    }

    @Test
    public void testBepaalVerschilType() {
        final Verschil verschil1 = new Verschil(sleutel, null, nieuwPersoon, null, null);
        assertEquals(VerschilType.ELEMENT_NIEUW, verschil1.getVerschilType());

        final Verschil verschil2 = new Verschil(sleutel, bestaandPersoon, null, null, null);
        assertEquals(VerschilType.ELEMENT_VERWIJDERD, verschil2.getVerschilType());

        final Verschil verschil3 = new Verschil(sleutel, bestaandPersoon, nieuwPersoon, null, null);
        assertEquals(VerschilType.ELEMENT_AANGEPAST, verschil3.getVerschilType());
    }

    @Test
    public void testIsToegestaanVoorSchoneAnummerWijzigingGeenEntiteitSleutel() {
        final Sleutel sleutel = new IstSleutel(new Stapel(bestaandPersoon, "01", 0), false);
        final Verschil verschil = new Verschil(sleutel, null, null, null, null);
        assertFalse(verschil.isToegestaanVoorAnummerWijziging());
    }

    @Test
    public void testIsToegestaanVoorSchoneAnummerWijzigingRijToegevoegd() {
        final FormeleHistorie rij = new PersoonGeslachtsaanduidingHistorie(bestaandPersoon, Geslachtsaanduiding.MAN);
        final Sleutel sleutel = new EntiteitSleutel(Persoon.class, "persoonGeslachtsaanduidingHistorieSet");
        final Verschil verschil = new Verschil(sleutel, null, rij, VerschilType.RIJ_TOEGEVOEGD, null, null);
        assertTrue(verschil.isToegestaanVoorAnummerWijziging());

        final FormeleHistorie rijNok = new PersoonMigratieHistorie(bestaandPersoon, SoortMigratie.EMIGRATIE);
        final Sleutel sleutelNok = new EntiteitSleutel(Persoon.class, "persoonMigratieHistorieSet");
        final Verschil verschilNok = new Verschil(sleutelNok, null, rijNok, VerschilType.RIJ_TOEGEVOEGD, null, null);
        assertFalse(verschilNok.isToegestaanVoorAnummerWijziging());
    }

    @Test
    public void testIsToegestaanVoorSchoneAnummerWijzigingMRijVerschillen() {
        final List<String> veldnamen =
                Arrays.asList("actieVervalTbvLeveringMutaties", "actieVerval", "datumTijdVerval", "indicatieVoorkomenTbvLeveringMutaties");
        for (final String veldnaam : veldnamen) {
            final Sleutel sleutel = new EntiteitSleutel(PersoonGeslachtsaanduidingHistorie.class, veldnaam);
            final Verschil verschilAangepast = new Verschil(sleutel, null, null, VerschilType.ELEMENT_AANGEPAST, null, null);
            final Verschil verschilNieuw = new Verschil(sleutel, null, null, VerschilType.ELEMENT_NIEUW, null, null);

            assertTrue(verschilAangepast.isToegestaanVoorAnummerWijziging());
            assertTrue(verschilNieuw.isToegestaanVoorAnummerWijziging());
        }

        final Sleutel sleutel = new EntiteitSleutel(PersoonGeslachtsaanduidingHistorie.class, "geslachtsaanduidingId");
        final Verschil verschil = new Verschil(sleutel, false, false, VerschilType.ELEMENT_AANGEPAST, null, null);
        assertFalse(verschil.isToegestaanVoorAnummerWijziging());
    }

    @Test
    public void testIsToegestaanVoorSchoneAnummerWijzigingNieuweRijElementAangepast() {
        final Sleutel sleutel = new EntiteitSleutel(PersoonGeslachtsaanduidingHistorie.class, "actieInhoud");
        final Verschil verschil = new Verschil(sleutel, null, null, VerschilType.NIEUWE_RIJ_ELEMENT_AANGEPAST, null, null);
        assertTrue(verschil.isToegestaanVoorAnummerWijziging());

        final Sleutel sleutelNok = new EntiteitSleutel(PersoonMigratieHistorie.class, "actieInhoud");
        final Verschil verschilNok = new Verschil(sleutelNok, null, null, VerschilType.NIEUWE_RIJ_ELEMENT_AANGEPAST, null, null);
        assertFalse(verschilNok.isToegestaanVoorAnummerWijziging());
    }

    @Test
    public void testIsToegestaanVoorSchoneAnummerWijzigingElementVerwijderd() {
        final Sleutel sleutel = new EntiteitSleutel(PersoonGeslachtsaanduidingHistorie.class, "actieInhoud");
        final Verschil verschil = new Verschil(sleutel, null, null, VerschilType.ELEMENT_VERWIJDERD, null, null);
        assertFalse(verschil.isToegestaanVoorAnummerWijziging());
    }

    @Test
    public void testIsToegestaanVoorInfrastructureleWijzigingGeenEntiteitSleutel() {
        final Sleutel sleutel = new IstSleutel(new Stapel(bestaandPersoon, "01", 0), false);
        final Verschil verschil = new Verschil(sleutel, null, null, null, null);
        assertFalse(verschil.isToegestaanVoorInfrastructureleWijziging());
    }

    @Test
    public void testIsToegestaanVoorInfrastructureleWijzigingPersoon() {
        final List<String> veldnamen =
                Arrays.asList(
                        Persoon.PERSOON_MIGRATIE_HISTORIE_SET,
                        "versienummer",
                        Persoon.REDEN_WIJZIGING_MIGRATIE,
                        "tijdstipLaatsteWijzigingGbaSystematiek",
                        Persoon.LAND_OF_GEBIED_MIGRATIE,
                        "tijdstipLaatsteWijziging");
        for (final String veldnaam : veldnamen) {
            final Sleutel sleutel = new EntiteitSleutel(Persoon.class, veldnaam);
            final Verschil verschilAangepast = new Verschil(sleutel, null, null, VerschilType.ELEMENT_AANGEPAST, null, null);
            final Verschil verschilNieuw = new Verschil(sleutel, null, null, VerschilType.ELEMENT_NIEUW, null, null);

            assertTrue(verschilAangepast.isToegestaanVoorInfrastructureleWijziging());
            assertTrue(verschilNieuw.isToegestaanVoorInfrastructureleWijziging());
        }

        final Sleutel sleutel = new EntiteitSleutel(Persoon.class, "burgerservicenummer");
        final Verschil verschil = new Verschil(sleutel, false, false, VerschilType.ELEMENT_AANGEPAST, null, null);
        assertFalse(verschil.isToegestaanVoorInfrastructureleWijziging());
    }

    @Test
    public void testIsToegestaanVoorInfrastructureleWijzigingRijToegevoegd() {
        final PersoonAdres rij = new PersoonAdres(bestaandPersoon);
        final Sleutel sleutel = new EntiteitSleutel(Persoon.class, "persoonAdresSet");
        final Verschil verschil = new Verschil(sleutel, null, rij, VerschilType.RIJ_TOEGEVOEGD, null, null);
        assertTrue(verschil.isToegestaanVoorInfrastructureleWijziging());

        final FormeleHistorie rijNok = new PersoonSamengesteldeNaamHistorie(bestaandPersoon, "Puk", false, false);
        final Sleutel sleutelNok = new EntiteitSleutel(Persoon.class, "persoonSamengesteldeNaamHistorieSet");
        final Verschil verschilNok = new Verschil(sleutelNok, null, rijNok, VerschilType.RIJ_TOEGEVOEGD, null, null);
        assertFalse(verschilNok.isToegestaanVoorInfrastructureleWijziging());
    }

    @Test
    public void testIsToegestaanVoorInfrastructureleWijzigingMRijVerschillen() {
        final List<String> veldnamen =
                Arrays.asList("actieVervalTbvLeveringMutaties", "actieVerval", "datumTijdVerval", "indicatieVoorkomenTbvLeveringMutaties");
        for (final String veldnaam : veldnamen) {
            final Sleutel sleutel = new EntiteitSleutel(PersoonAdresHistorie.class, veldnaam);
            final Verschil verschilAangepast = new Verschil(sleutel, null, null, VerschilType.ELEMENT_AANGEPAST, null, null);
            final Verschil verschilNieuw = new Verschil(sleutel, null, null, VerschilType.ELEMENT_NIEUW, null, null);

            assertTrue(String.format("Veld %s niet toegestaan voor infra wijziging", veldnaam), verschilAangepast.isToegestaanVoorInfrastructureleWijziging());
            assertTrue(String.format("Veld %s niet toegestaan voor infra wijziging", veldnaam), verschilNieuw.isToegestaanVoorInfrastructureleWijziging());
        }

        final Sleutel sleutel = new EntiteitSleutel(PersoonGeslachtsaanduidingHistorie.class, "datumEindeGeldigheid");
        final Verschil verschil = new Verschil(sleutel, false, false, VerschilType.ELEMENT_AANGEPAST, null, null);
        assertFalse(verschil.isToegestaanVoorInfrastructureleWijziging());
    }

    @Test
    public void testIsToegestaanVoorInfrastructureleWijzigingNieuweRijElementAangepast() {
        final Sleutel sleutel = new EntiteitSleutel(PersoonAdresHistorie.class, "actieInhoud");
        final Verschil verschil = new Verschil(sleutel, null, null, VerschilType.NIEUWE_RIJ_ELEMENT_AANGEPAST, null, null);
        assertTrue(verschil.isToegestaanVoorInfrastructureleWijziging());

        final Sleutel sleutelNok = new EntiteitSleutel(PersoonSamengesteldeNaamHistorie.class, "actieInhoud");
        final Verschil verschilNok = new Verschil(sleutelNok, null, null, VerschilType.NIEUWE_RIJ_ELEMENT_AANGEPAST, null, null);
        assertFalse(verschilNok.isToegestaanVoorInfrastructureleWijziging());
    }

    @Test
    public void testIsToegestaanVoorInfrastructureleWijzigingElementVerwijderd() {
        final Sleutel sleutel = new EntiteitSleutel(PersoonGeslachtsaanduidingHistorie.class, "actieInhoud");
        final Verschil verschil = new Verschil(sleutel, null, null, VerschilType.ELEMENT_VERWIJDERD, null, null);
        assertFalse(verschil.isToegestaanVoorInfrastructureleWijziging());
    }
}
