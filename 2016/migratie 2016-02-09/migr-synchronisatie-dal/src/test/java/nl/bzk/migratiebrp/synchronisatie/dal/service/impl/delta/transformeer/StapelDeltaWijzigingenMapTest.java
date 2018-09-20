/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.transformeer;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.Sleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Bijhoudingsaard;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EntiteitSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FormeleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.LandOfGebied;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Bericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3BerichtenBron;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Voorkomen;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.NadereBijhoudingsaard;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Nationaliteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonBijhoudingHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonGeboorteHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonGeslachtsnaamcomponent;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonGeslachtsnaamcomponentHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonIDHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonInschrijvingHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonNationaliteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonNationaliteitHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonVoornaam;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonVoornaamHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortAdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.Verschil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilGroep;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class StapelDeltaWijzigingenMapTest {

    private static final Partij PARTIJ = new Partij("test partij", 0);
    private StapelDeltaWijzigingenMap map;

    private void voegVerschilGroepToe(final Verschil verschil, final DeltaWijziging deltaWijziging) {
        final FormeleHistorie historieRij;
        if (verschil.getBestaandeHistorieRij() != null) {
            historieRij = verschil.getBestaandeHistorieRij();
        } else {
            historieRij = verschil.getNieuweHistorieRij();
        }

        final VerschilGroep verschilGroep = VerschilGroep.maakVerschilGroepMetHistorie(historieRij);
        verschilGroep.addVerschillen(Collections.singletonList(verschil));
        map.addHistorieDeltaWijziging(verschilGroep, deltaWijziging);
    }

    private void voegVerschilGroepToe(final List<Verschil> verschillen, final DeltaWijziging deltaWijziging) {
        final FormeleHistorie historieRij;
        final Verschil verschil = verschillen.get(0);
        if (verschil.getBestaandeHistorieRij() != null) {
            historieRij = verschil.getBestaandeHistorieRij();
        } else {
            historieRij = verschil.getNieuweHistorieRij();
        }

        final VerschilGroep verschilGroep = VerschilGroep.maakVerschilGroepMetHistorie(historieRij);
        verschilGroep.addVerschillen(verschillen);
        map.addHistorieDeltaWijziging(verschilGroep, deltaWijziging);
    }

    @Before
    public void setup() {
        map = new StapelDeltaWijzigingenMap();
    }

    @Test
    public void testActualiseringVanActueleGegevens() {
        final Verschil actualiseringVerschil = maakPersoonNationaliteitHistorieRijVerschil(2, (short) 0);
        voegVerschilGroepToe(actualiseringVerschil, DeltaWijziging.DW_002_ACT);
        assertTrue(map.isBijhoudingActueel());
    }

    @Test
    public void testActualiseringVanBestaandeGegevens() {
        final Verschil actualiseringVerschil1 = maakPersoonNationaliteitHistorieRijVerschil(2, (short) 0);
        final Verschil actualiseringVerschil2 = maakPersoonNationaliteitHistorieRijVerschil(2, (short) 0);
        final Verschil actualiseringVerschil3 = maakPersoonNationaliteitHistorieRijVerschil(3, (short) 1);
        final Verschil actualiseringVerschil4 = maakPersoonNationaliteitHistorieRijVerschil(3, (short) 1);
        final Verschil actualiseringVerschil5 = maakPersoonNationaliteitHistorieRijVerschil(4, (short) 2);
        final Verschil actualiseringVerschil6 = maakPersoonNationaliteitHistorieRijVerschil(4, (short) 2);
        final Verschil actualiseringVerschil7 = maakPersoonNationaliteitHistorieRijVerschil(5, (short) 3);
        final Verschil actualiseringVerschil8 = maakPersoonNationaliteitHistorieRijVerschil(5, (short) 3);
        final Verschil actualiseringVerschil9 = maakPersoonNationaliteitHistorieRijVerschil(5, (short) 3);

        voegVerschilGroepToe(actualiseringVerschil1,DeltaWijziging.DW_002_ACT);
        voegVerschilGroepToe(actualiseringVerschil2,DeltaWijziging.DW_021);
        voegVerschilGroepToe(actualiseringVerschil3,DeltaWijziging.DW_002_ACT);
        voegVerschilGroepToe(actualiseringVerschil4,DeltaWijziging.DW_011);
        voegVerschilGroepToe(actualiseringVerschil5,DeltaWijziging.DW_002_ACT);
        voegVerschilGroepToe(actualiseringVerschil6,DeltaWijziging.DW_024);
        voegVerschilGroepToe(actualiseringVerschil7,DeltaWijziging.DW_002_ACT);
        voegVerschilGroepToe(actualiseringVerschil8,DeltaWijziging.DW_024);
        voegVerschilGroepToe(actualiseringVerschil9,DeltaWijziging.DW_023);
        assertTrue(map.isBijhoudingActueel());
    }

    @Test
    public void testActualiseringVanBeeindigdeGegevens() {
        final Verschil actualiseringVerschil1 = maakPersoonNationaliteitHistorieRijVerschil(2, (short) 0);
        voegVerschilGroepToe(actualiseringVerschil1,DeltaWijziging.DW_021);
        assertTrue(map.isBijhoudingActueel());
    }

    @Test
    public void testCorrectieInActueleGegevens() {
        final Verschil actualiseringVerschil1 = maakPersoonNationaliteitHistorieRijVerschil(2, (short) 0);
        final Verschil actualiseringVerschil2 = maakPersoonNationaliteitHistorieRijVerschil(2, (short) 0);
        final Verschil actualiseringVerschil3 = maakPersoonNationaliteitHistorieRijVerschil(3, (short) 1);
        final Verschil actualiseringVerschil4 = maakPersoonNationaliteitHistorieRijVerschil(3, (short) 1);
        final Verschil actualiseringVerschil5 = maakPersoonNationaliteitHistorieRijVerschil(4, (short) 2);
        final Verschil actualiseringVerschil6 = maakPersoonNationaliteitHistorieRijVerschil(4, (short) 2);
        final Verschil actualiseringVerschil7 = maakPersoonNationaliteitHistorieRijVerschil(4, (short) 2);
        voegVerschilGroepToe(actualiseringVerschil1,DeltaWijziging.DW_002_ACT);
        voegVerschilGroepToe(actualiseringVerschil2,DeltaWijziging.DW_022);
        voegVerschilGroepToe(actualiseringVerschil3,DeltaWijziging.DW_002_ACT);
        voegVerschilGroepToe(actualiseringVerschil4,DeltaWijziging.DW_012);
        voegVerschilGroepToe(actualiseringVerschil5,DeltaWijziging.DW_002_ACT);
        voegVerschilGroepToe(actualiseringVerschil6,DeltaWijziging.DW_022);
        voegVerschilGroepToe(actualiseringVerschil7,DeltaWijziging.DW_023);
        assertTrue(map.isBijhoudingActueel());
    }

    @Test
    public void testCorrectieOnterechtOpgenomenGegevens() {
        final Verschil actualiseringVerschil1 = maakPersoonNationaliteitHistorieRijVerschil(2, (short) 0);
        final Verschil actualiseringVerschil2 = maakPersoonNationaliteitHistorieRijVerschil(3, (short) 1);
        voegVerschilGroepToe(actualiseringVerschil1,DeltaWijziging.DW_012);
        voegVerschilGroepToe(actualiseringVerschil2,DeltaWijziging.DW_025);
        assertTrue(map.isBijhoudingActueel());
    }

    @Test
    public void testWijzigingGroepenZonderGBAHistorie() {
        final Verschil actualiseringVerschil1 = maakPersoonInschrijvingHistorieRijVerschil();
        final Verschil actualiseringVerschil2 = maakPersoonInschrijvingHistorieRijVerschil();
        final Verschil actualiseringVerschil3 = maakPersoonInschrijvingHistorieRijVerschil();
        final Verschil actualiseringVerschil4 = maakPersoonInschrijvingHistorieRijVerschil();
        final Verschil actualiseringVerschil5 = maakPersoonInschrijvingHistorieRijVerschil();
        final Verschil actualiseringVerschil6 = maakPersoonInschrijvingHistorieRijVerschil();
        voegVerschilGroepToe(actualiseringVerschil1,DeltaWijziging.DW_001);
        voegVerschilGroepToe(actualiseringVerschil2,DeltaWijziging.DW_002);
        voegVerschilGroepToe(actualiseringVerschil3,DeltaWijziging.DW_002_ACT);
        voegVerschilGroepToe(actualiseringVerschil4,DeltaWijziging.DW_901);
        voegVerschilGroepToe(actualiseringVerschil5,DeltaWijziging.DW_021);
        voegVerschilGroepToe(actualiseringVerschil6,DeltaWijziging.DW_022);
        assertTrue(map.isBijhoudingActueel());
    }

    @Test
    public void testWijzigingBijhoudingGegevens() {
        final Verschil actualiseringVerschil1 = maakPersoonNationaliteitHistorieRijVerschil(2, (short) 0);
        final Verschil actualiseringVerschil2 = maakPersoonNationaliteitHistorieRijVerschil(2, (short) 0);
        final Verschil actualiseringVerschil3 = maakPersoonNationaliteitHistorieRijVerschil(3, (short) 1);
        final Verschil actualiseringVerschil4 = maakPersoonNationaliteitHistorieRijVerschil(3, (short) 1);
        final Verschil actualiseringVerschil5 = maakPersoonNationaliteitHistorieRijVerschil(4, (short) 2);
        final Verschil actualiseringVerschil6 = maakPersoonNationaliteitHistorieRijVerschil(4, (short) 2);
        final Verschil actualiseringVerschil7 = maakPersoonNationaliteitHistorieRijVerschil(5, (short) 3);
        final Verschil actualiseringVerschil8 = maakPersoonNationaliteitHistorieRijVerschil(5, (short) 3);
        final Verschil actualiseringVerschil9 = maakPersoonNationaliteitHistorieRijVerschil(5, (short) 3);
        final Verschil actualiseringVerschil10 = maakPersoonNationaliteitHistorieRijVerschil(6, (short) 4);
        final Verschil actualiseringVerschil11 = maakPersoonNationaliteitHistorieRijVerschil(6, (short) 4);
        final Verschil actualiseringVerschil12 = maakPersoonNationaliteitHistorieRijVerschil(6, (short) 4);
        voegVerschilGroepToe(actualiseringVerschil1,DeltaWijziging.DW_002_ACT);
        voegVerschilGroepToe(actualiseringVerschil2,DeltaWijziging.DW_031);
        voegVerschilGroepToe(actualiseringVerschil3,DeltaWijziging.DW_002_ACT);
        voegVerschilGroepToe(actualiseringVerschil4,DeltaWijziging.DW_032);
        voegVerschilGroepToe(actualiseringVerschil5,DeltaWijziging.DW_002_ACT);
        voegVerschilGroepToe(actualiseringVerschil6,DeltaWijziging.DW_034);
        voegVerschilGroepToe(actualiseringVerschil7,DeltaWijziging.DW_002_ACT);
        voegVerschilGroepToe(actualiseringVerschil8,DeltaWijziging.DW_031);
        voegVerschilGroepToe(actualiseringVerschil9,DeltaWijziging.DW_023);
        voegVerschilGroepToe(actualiseringVerschil10,DeltaWijziging.DW_002_ACT);
        voegVerschilGroepToe(actualiseringVerschil11,DeltaWijziging.DW_034);
        voegVerschilGroepToe(actualiseringVerschil12,DeltaWijziging.DW_023);
        assertTrue(map.isBijhoudingActueel());
    }

    @Test
    public void testAddLegeVerschilGroep() {
        final VerschilGroep verschilGroep = VerschilGroep.maakVerschilGroepZonderHistorie();
        assertEquals(0, map.getStapelSleutels().size());
        map.addHistorieDeltaWijziging(verschilGroep, DeltaWijziging.DW_001);
        assertEquals(0, map.getStapelSleutels().size());
    }

    @Test
    public void testOnbekendeSleutel() {
        final EntiteitSleutel geboorteStapelSleutel = new EntiteitSleutel(Persoon.class, "persoonGeboorteHistorieSet");
        assertNull(map.getDeltaWijzigingen(geboorteStapelSleutel));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddHistorieDeltaWijzigingFoutNietHistorisch() {
        final Sleutel dummySleutel = new Sleutel() {
            @Override public void addSleuteldeel(final String naam, final Object sleuteldeel) {

            }

            @Override public Map<String, Object> getDelen() {
                return null;
            }

            @Override public void setId(final Long id) {

            }

            @Override public Long getId() {
                return null;
            }

            @Override public String getVeld() {
                return null;
            }

            @Override public int hashCode() {
                return 0;
            }

            @Override public boolean equals(final Object obj) {
                return false;
            }

            @Override public String toShortString() {
                return null;
            }
        };
        final Verschil foutVerschil =
                new Verschil(dummySleutel , null, "x", null, new PersoonGeboorteHistorie(
                    new Persoon(SoortPersoon.INGESCHREVENE),
                    0,
                    new LandOfGebied((short) 0, "x")));
        final VerschilGroep verschilGroep = VerschilGroep.maakVerschilGroepZonderHistorie();
        verschilGroep.addVerschil(foutVerschil);
        /* Verwacht fout omdat verschil niet gebaseerd is op een entiteit sleutel. */
        map.addHistorieDeltaWijziging(verschilGroep, DeltaWijziging.DW_001);
    }

    @Test
    public void testAddHistorieDeltaWijziging() {
        // groep 1a en 1b hebben dezelfde stapel voor persoon geboorte maar verschillen in stapel van 2a en 2b
        voegVerschilGroepToe(maakPersoonGeboorteHistorieVerschil(20010101), DeltaWijziging.DW_001);
        voegVerschilGroepToe(maakPersoonGeboorteHistorieVerschil(20020202), DeltaWijziging.DW_002);
        // groep 2a en 2b hebben dezelfde stapel voor persoon ID maar verschillen in stapel van 1a en 1b
        voegVerschilGroepToe(maakPersoonIDHistorieVerschil(1234567890L), DeltaWijziging.DW_003);
        voegVerschilGroepToe(maakPersoonIDHistorieRijVerschil(9012345678L), DeltaWijziging.DW_011);
        // groep 3a, 3b hebben dezelfde stapel maar 3c een andere stapel, alle 3 stapels verschillen van de 1 en 2 serie
        voegVerschilGroepToe(maakPersoonNationaliteitHistorieVerschil(1, (short) 1), DeltaWijziging.DW_021);
        voegVerschilGroepToe(maakPersoonNationaliteitHistorieRijVerschil(1, (short) 1), DeltaWijziging.DW_023);
        voegVerschilGroepToe(maakPersoonNationaliteitHistorieVerschil(2, (short) 2), DeltaWijziging.DW_025);

        // verify
        final Set<EntiteitSleutel> stapelSleutels = map.getStapelSleutels();
            assertEquals(4, stapelSleutels.size());
        final EntiteitSleutel geboorteStapelSleutel = new EntiteitSleutel(Persoon.class, "persoonGeboorteHistorieSet");
        final EntiteitSleutel idStapelSleutel = new EntiteitSleutel(Persoon.class, "persoonIDHistorieSet");
        final EntiteitSleutel nationaliteitPersoonSleutel = new EntiteitSleutel(Persoon.class, "persoonNationaliteitSet");
        nationaliteitPersoonSleutel.addSleuteldeel("nation", 1);
        final EntiteitSleutel nationaliteitStapelSleutel1 =
                new EntiteitSleutel(PersoonNationaliteit.class, "persoonNationaliteitHistorieSet", nationaliteitPersoonSleutel);
        final EntiteitSleutel nationaliteitPersoonSleutel2 = new EntiteitSleutel(Persoon.class, "persoonNationaliteitSet");
        nationaliteitPersoonSleutel2.addSleuteldeel("nation", 2);
        final EntiteitSleutel nationaliteitStapelSleutel2 =
                new EntiteitSleutel(PersoonNationaliteit.class, "persoonNationaliteitHistorieSet", nationaliteitPersoonSleutel2);
        final List<DeltaWijziging> deltaWijzigingenVoorGeboorte = map.getDeltaWijzigingen(geboorteStapelSleutel);
        final List<DeltaWijziging> deltaWijzigingenVoorID = map.getDeltaWijzigingen(idStapelSleutel);
        final List<DeltaWijziging> deltaWijzigingenVoorNationaliteit1 = map.getDeltaWijzigingen(nationaliteitStapelSleutel1);
        final List<DeltaWijziging> deltaWijzigingenVoorNationaliteit2 = map.getDeltaWijzigingen(nationaliteitStapelSleutel2);
        assertNotNull(deltaWijzigingenVoorGeboorte);
        assertNotNull(deltaWijzigingenVoorID);
        assertEquals(2, deltaWijzigingenVoorGeboorte.size());
        assertEquals(2, deltaWijzigingenVoorID.size());
        assertNotNull(deltaWijzigingenVoorNationaliteit1);
        assertNotNull(deltaWijzigingenVoorNationaliteit2);
        assertEquals(2, deltaWijzigingenVoorNationaliteit1.size());
        assertEquals(1, deltaWijzigingenVoorNationaliteit2.size());
    }

    @Test
    public void testVerwijderTeNegerenVerschilGroepenEnDw901Wijzigingen() {
        // setup
        final BRPActie actie =
                new BRPActie(
                    SoortActie.CONVERSIE_GBA,
                    new AdministratieveHandeling(PARTIJ, SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL),
                    PARTIJ,
                    new Timestamp(System.currentTimeMillis()));
        voegVerschilGroepToe(Arrays.asList(maakPersoonBijhoudingTsRegHistorieVerschil(PARTIJ), maakPersoonBijhoudingAiHistorieVerschil(actie)), DeltaWijziging.DW_901);
        voegVerschilGroepToe(maakPersoonBijhoudingAagHistorieVerschil(actie), DeltaWijziging.DW_023);
        voegVerschilGroepToe(maakPersoonIDHistorieVerschil(1234567890L),DeltaWijziging.DW_901);
        voegVerschilGroepToe(maakPersoonIDHistorieVerschil(1234567891L), DeltaWijziging.DW_011);

        // execute
        final List<VerschilGroep> result = map.verwijderTeNegerenVerschilGroepenEnDw901Wijzigingen();
        // verify
        assertEquals(2, result.size());
        assertEquals(1, map.getStapelSleutels().size());
        final List<DeltaWijziging> deltaWijzigingen = map.getDeltaWijzigingen(map.getStapelSleutels().iterator().next());
        assertNotNull(deltaWijzigingen);
        assertEquals(1, deltaWijzigingen.size());
    }

    @Test
    public void testTeNegerenVoornaamWijziging() {
        // setup & execute
        voegVerschilGroepToe(maakPersoonVoornaamHistorieVerschil("Piet"),
            DeltaWijziging.DW_022);
        // verify
        assertTrue(map.isBijhoudingActueel());
        // additional setup & execute
        voegVerschilGroepToe(maakPersoonIDHistorieVerschil(1234567891L), DeltaWijziging.DW_022);
        assertFalse(map.isBijhoudingActueel());
    }

    @Test
    public void testTeNegerenGeslachtsnaamcomponentWijziging() {
        // setup & execute
        voegVerschilGroepToe(maakPersoonGeslachtsnaamcomponentHistorieVerschil("Jansen"),
            DeltaWijziging.DW_022);
        // verify
        assertTrue(map.isBijhoudingActueel());
        // additional setup & execute
        voegVerschilGroepToe(maakPersoonIDHistorieVerschil(1234567891L),
            DeltaWijziging.DW_022);
        assertFalse(map.isBijhoudingActueel());
    }

    @Test
    public void testTeNegerenVoornaamEnGeslachtsnaamcomponentWijziging() {
        // setup & execute
        voegVerschilGroepToe(maakPersoonVoornaamHistorieVerschil("Piet"),
            DeltaWijziging.DW_022);
        voegVerschilGroepToe(maakPersoonGeslachtsnaamcomponentHistorieVerschil("Jansen"), DeltaWijziging.DW_022);
        // verify
        assertTrue(map.isBijhoudingActueel());
        // additional setup & execute
        voegVerschilGroepToe(maakPersoonIDHistorieVerschil(1234567891L),
            DeltaWijziging.DW_022);
        assertFalse(map.isBijhoudingActueel());
    }

    @Test
    public void testNieuweNietActueleBijhoudingUitCat8() {
        // setup & execute
        voegVerschilGroepToe(maakPersoonBijhoudingNieuweRijHistorieVerschilMetCat08(PARTIJ), DeltaWijziging.DW_002);
        // verify
        assertTrue(map.isBijhoudingActueel());
        // additional setup & execute
        voegVerschilGroepToe(maakPersoonBijhoudingNieuweRijHistorieVerschilZonderCat08(PARTIJ), DeltaWijziging.DW_002);
        assertFalse(map.isBijhoudingActueel());
    }

    private Verschil maakPersoonGeboorteHistorieVerschil(final int datumGeboorte) {
        final EntiteitSleutel geboorteHistorieSleutel =
                maakPersoonGeboorteHistorieEntiteitSleutel("datumGeboorte", new Timestamp(System.currentTimeMillis()));
        final PersoonGeboorteHistorie nieuweGeboorteHistorie =
                new PersoonGeboorteHistorie(new Persoon(SoortPersoon.INGESCHREVENE), datumGeboorte, new LandOfGebied((short) 1, "t"));
        return new Verschil(geboorteHistorieSleutel, null, datumGeboorte, null, nieuweGeboorteHistorie);
    }

    private Verschil maakPersoonBijhoudingTsRegHistorieVerschil(final Partij partij) {
        final EntiteitSleutel bijhoudingTsRegHistorieSleutel = maakPersoonBijhoudingHistorieEntiteitSleutel("datumTijdRegistratie");
        final PersoonBijhoudingHistorie bijhoudingHistorie =
                new PersoonBijhoudingHistorie(
                    new Persoon(SoortPersoon.INGESCHREVENE),
                    partij,
                    Bijhoudingsaard.INGEZETENE,
                    NadereBijhoudingsaard.ACTUEEL,
                    false);
        final Timestamp tsReg1 = new Timestamp(System.currentTimeMillis());
        final Timestamp tsReg2 = new Timestamp(tsReg1.getTime());
        return new Verschil(bijhoudingTsRegHistorieSleutel, tsReg1, tsReg2, bijhoudingHistorie, bijhoudingHistorie);
    }

    private Verschil maakPersoonBijhoudingNieuweRijHistorieVerschilZonderCat08(final Partij partij) {
        final EntiteitSleutel bijhoudingTsRegHistorieSleutel = maakPersoonBijhoudingHistorieEntiteitSleutel("datumTijdRegistratie");
        final PersoonBijhoudingHistorie bijhoudingHistorie =
                new PersoonBijhoudingHistorie(
                    new Persoon(SoortPersoon.INGESCHREVENE),
                    partij,
                    Bijhoudingsaard.INGEZETENE,
                    NadereBijhoudingsaard.ACTUEEL,
                    false);
        final BRPActie actieInhoud =
                new BRPActie(
                    SoortActie.CONVERSIE_GBA,
                    new AdministratieveHandeling(partij, SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL),
                    partij,
                    new Timestamp(System.currentTimeMillis()));
        actieInhoud.setLo3Voorkomen(new Lo3Voorkomen(new Lo3Bericht("test bijhouding cat08", Lo3BerichtenBron.INITIELE_VULLING, new Timestamp(
            System.currentTimeMillis()), "test", true), "58"));
        bijhoudingHistorie.setActieInhoud(actieInhoud);
        return new Verschil(
            bijhoudingTsRegHistorieSleutel,
            null,
            new Timestamp(System.currentTimeMillis()),
            VerschilType.RIJ_TOEGEVOEGD,
            null,
            bijhoudingHistorie);
    }

    private Verschil maakPersoonBijhoudingNieuweRijHistorieVerschilMetCat08(final Partij partij) {
        final EntiteitSleutel bijhoudingTsRegHistorieSleutel = maakPersoonBijhoudingHistorieEntiteitSleutel("datumTijdRegistratie");
        final PersoonBijhoudingHistorie bijhoudingHistorie =
                new PersoonBijhoudingHistorie(
                    new Persoon(SoortPersoon.INGESCHREVENE),
                    partij,
                    Bijhoudingsaard.INGEZETENE,
                    NadereBijhoudingsaard.ACTUEEL,
                    false);
        final BRPActie actieInhoud =
                new BRPActie(
                    SoortActie.CONVERSIE_GBA,
                    new AdministratieveHandeling(partij, SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL),
                    partij,
                    new Timestamp(System.currentTimeMillis()));
        actieInhoud.setLo3Voorkomen(new Lo3Voorkomen(new Lo3Bericht("test bijhouding cat08", Lo3BerichtenBron.INITIELE_VULLING, new Timestamp(
            System.currentTimeMillis()), "test", true), "08"));
        bijhoudingHistorie.setActieInhoud(actieInhoud);
        return new Verschil(
            bijhoudingTsRegHistorieSleutel,
            null,
            new Timestamp(System.currentTimeMillis()),
            VerschilType.RIJ_TOEGEVOEGD,
            null,
            bijhoudingHistorie);
    }

    private Verschil maakPersoonBijhoudingAiHistorieVerschil(final BRPActie actie) {
        final EntiteitSleutel bijhoudingTsRegHistorieSleutel = maakPersoonBijhoudingHistorieEntiteitSleutel("actieInhoud");
        final PersoonBijhoudingHistorie bijhoudingHistorie =
                new PersoonBijhoudingHistorie(
                    new Persoon(SoortPersoon.INGESCHREVENE),
                    actie.getPartij(),
                    Bijhoudingsaard.INGEZETENE,
                    NadereBijhoudingsaard.ACTUEEL,
                    false);
        return new Verschil(bijhoudingTsRegHistorieSleutel, actie, actie, bijhoudingHistorie, bijhoudingHistorie);
    }

    private Verschil maakPersoonBijhoudingAagHistorieVerschil(final BRPActie actie) {
        final EntiteitSleutel bijhoudingTsRegHistorieSleutel = maakPersoonBijhoudingHistorieEntiteitSleutel("actieAanpassingGeldigheid");
        final PersoonBijhoudingHistorie bijhoudingHistorie =
                new PersoonBijhoudingHistorie(
                    new Persoon(SoortPersoon.INGESCHREVENE),
                    actie.getPartij(),
                    Bijhoudingsaard.INGEZETENE,
                    NadereBijhoudingsaard.ACTUEEL,
                    false);
        return new Verschil(bijhoudingTsRegHistorieSleutel, actie, actie, bijhoudingHistorie, bijhoudingHistorie);
    }

    private Verschil maakPersoonIDHistorieRijVerschil(final long administratienummer) {
        final EntiteitSleutel idHistorieRijSleutel =
                maakPersoonIDHistorieEntiteitSleutel("administratienummer", new Timestamp(System.currentTimeMillis())).getEigenaarSleutel();
        idHistorieRijSleutel.addSleuteldeel("tsreg", new Timestamp(System.currentTimeMillis()));
        final PersoonIDHistorie nieuweIDHistorie = new PersoonIDHistorie(new Persoon(SoortPersoon.INGESCHREVENE));
        return new Verschil(idHistorieRijSleutel, null, administratienummer, VerschilType.RIJ_TOEGEVOEGD, null, nieuweIDHistorie);
    }

    private Verschil maakPersoonIDHistorieVerschil(final long administratienummer) {
        final EntiteitSleutel idHistorieSleutel = maakPersoonIDHistorieEntiteitSleutel("administratienummer", new Timestamp(System.currentTimeMillis()));
        final PersoonIDHistorie nieuweIDHistorie = new PersoonIDHistorie(new Persoon(SoortPersoon.INGESCHREVENE));
        return new Verschil(idHistorieSleutel, null, administratienummer, null, nieuweIDHistorie);
    }

    private Verschil maakPersoonGeslachtsnaamcomponentHistorieVerschil(final String geslachtsnaam) {
        final EntiteitSleutel geslachtsnaamcomponentHistorieSleutel =
                maakPersoonGeslachtsnaamcomponentHistorieEntiteitSleutel(geslachtsnaam, new Timestamp(System.currentTimeMillis()), 20000101);
        final PersoonGeslachtsnaamcomponent persoonGeslachtsnaamcomponent = new PersoonGeslachtsnaamcomponent(new Persoon(SoortPersoon.INGESCHREVENE), 1);
        final PersoonGeslachtsnaamcomponentHistorie persoonGeslachtsnaamcomponentHistorie =
                new PersoonGeslachtsnaamcomponentHistorie(persoonGeslachtsnaamcomponent, geslachtsnaam);
        return new Verschil(geslachtsnaamcomponentHistorieSleutel, null, geslachtsnaam, null, persoonGeslachtsnaamcomponentHistorie);
    }

    private Verschil maakPersoonVoornaamHistorieVerschil(final String voornaam) {
        final EntiteitSleutel voornaamHistorieSleutel =
                maakPersoonVoornaamHistorieEntiteitSleutel(voornaam, new Timestamp(System.currentTimeMillis()), 20000101);
        final PersoonVoornaam persoonVoornaam = new PersoonVoornaam(new Persoon(SoortPersoon.INGESCHREVENE), 1);
        final PersoonVoornaamHistorie persoonVoornaamHistorie = new PersoonVoornaamHistorie(persoonVoornaam, voornaam);
        return new Verschil(voornaamHistorieSleutel, null, voornaam, null, persoonVoornaamHistorie);
    }

    private Verschil maakPersoonNationaliteitHistorieVerschil(final Integer nationaliteitId, final short redenVerliesId) {
        final EntiteitSleutel nationaliteitHistorieSleutel =
                maakPersoonNationaliteitHistorieEntiteitSleutel(
                    nationaliteitId,
                    "redenVerliesNLNationaliteit",
                    new Timestamp(System.currentTimeMillis()),
                    20000101);
        final PersoonNationaliteit persoonNationaliteit =
                new PersoonNationaliteit(new Persoon(SoortPersoon.INGESCHREVENE), new Nationaliteit("test", redenVerliesId));
        final PersoonNationaliteitHistorie persoonNationaliteitHistorie = new PersoonNationaliteitHistorie(persoonNationaliteit);
        return new Verschil(nationaliteitHistorieSleutel, null, redenVerliesId, null, persoonNationaliteitHistorie);
    }

    private Verschil maakPersoonNationaliteitHistorieRijVerschil(final Integer nationaliteitId, final short redenVerliesId) {
        final EntiteitSleutel nationaliteitHistorieRijSleutel =
                maakPersoonNationaliteitHistorieEntiteitSleutel(
                    nationaliteitId,
                    "redenVerliesNLNationaliteit",
                    new Timestamp(System.currentTimeMillis()),
                    20000101).getEigenaarSleutel();
        final PersoonNationaliteit persoonNationaliteit =
                new PersoonNationaliteit(new Persoon(SoortPersoon.INGESCHREVENE), new Nationaliteit("test", redenVerliesId));
        final PersoonNationaliteitHistorie persoonNationaliteitHistorie = new PersoonNationaliteitHistorie(persoonNationaliteit);
        return new Verschil(nationaliteitHistorieRijSleutel, null, redenVerliesId, VerschilType.RIJ_TOEGEVOEGD, null, persoonNationaliteitHistorie);
    }

    private Verschil maakPersoonInschrijvingHistorieRijVerschil() {
        final EntiteitSleutel sleutel = maakPersoonInschrijvingHistorieEntiteitSleutel("datumInschrijving", new Timestamp(System.currentTimeMillis()));
        final PersoonInschrijvingHistorie inschrijvingHistorie =
                new PersoonInschrijvingHistorie(new Persoon(SoortPersoon.INGESCHREVENE), 20000101, 1L, new Timestamp(System.currentTimeMillis()));
        return new Verschil(sleutel, null, 20000101, VerschilType.ELEMENT_NIEUW, inschrijvingHistorie, null);
    }

    private EntiteitSleutel maakPersoonGeboorteHistorieEntiteitSleutel(final String veld, final Timestamp tsReg) {
        final EntiteitSleutel eigenaar = new EntiteitSleutel(Persoon.class, "persoonGeboorteHistorieSet");
        eigenaar.addSleuteldeel("tsreg", tsReg);
        return new EntiteitSleutel(PersoonGeboorteHistorie.class, veld, eigenaar);
    }

    private EntiteitSleutel maakPersoonBijhoudingHistorieEntiteitSleutel(final String veld) {
        final EntiteitSleutel eigenaar = new EntiteitSleutel(Persoon.class, "persoonBijhoudingHistorieSet");
        return new EntiteitSleutel(PersoonBijhoudingHistorie.class, veld, eigenaar);
    }

    private EntiteitSleutel maakPersoonIDHistorieEntiteitSleutel(final String veld, final Timestamp tsReg) {
        final EntiteitSleutel eigenaar = new EntiteitSleutel(Persoon.class, "persoonIDHistorieSet");
        eigenaar.addSleuteldeel("tsreg", tsReg);
        return new EntiteitSleutel(PersoonIDHistorie.class, veld, eigenaar);
    }

    private EntiteitSleutel maakPersoonGeslachtsnaamcomponentHistorieEntiteitSleutel(final String veld, final Timestamp tsReg, final Integer datAanvGel) {
        final EntiteitSleutel eigenaar = new EntiteitSleutel(Persoon.class, "persoonGeslachtsnaamcomponentSet");
        eigenaar.addSleuteldeel("volgnr", 1);
        final EntiteitSleutel stapel = new EntiteitSleutel(PersoonGeslachtsnaamcomponent.class, "persoonGeslachtsnaamcomponentHistorieSet", eigenaar);
        stapel.addSleuteldeel("tsreg", tsReg);
        stapel.addSleuteldeel("dataanvgel", datAanvGel);
        return new EntiteitSleutel(PersoonGeslachtsnaamcomponentHistorie.class, veld, stapel);
    }

    private EntiteitSleutel maakPersoonVoornaamHistorieEntiteitSleutel(final String veld, final Timestamp tsReg, final Integer datAanvGel) {
        final EntiteitSleutel eigenaar = new EntiteitSleutel(Persoon.class, "persoonVoornaamSet");
        eigenaar.addSleuteldeel("dataanvgel", datAanvGel);
        final EntiteitSleutel stapel = new EntiteitSleutel(PersoonNationaliteit.class, "persoonVoornaamHistorieSet", eigenaar);
        stapel.addSleuteldeel("tsreg", tsReg);
        stapel.addSleuteldeel("volgnr", 1);
        return new EntiteitSleutel(PersoonNationaliteitHistorie.class, veld, stapel);
    }

    private EntiteitSleutel maakPersoonNationaliteitHistorieEntiteitSleutel(
        final Integer nationaliteitId,
        final String veld,
        final Timestamp tsReg,
        final Integer datAanvGel)
    {
        final EntiteitSleutel eigenaar = new EntiteitSleutel(Persoon.class, "persoonNationaliteitSet");
        eigenaar.addSleuteldeel("nation", nationaliteitId);
        final EntiteitSleutel stapel = new EntiteitSleutel(PersoonNationaliteit.class, "persoonNationaliteitHistorieSet", eigenaar);
        stapel.addSleuteldeel("tsreg", tsReg);
        stapel.addSleuteldeel("dataanvgel", datAanvGel);
        return new EntiteitSleutel(PersoonNationaliteitHistorie.class, veld, stapel);
    }

    private EntiteitSleutel maakPersoonInschrijvingHistorieEntiteitSleutel(final String veld, final Timestamp tsReg) {
        final EntiteitSleutel eigenaar = new EntiteitSleutel(Persoon.class, "persoonInschrijvingHistorieSet");
        eigenaar.addSleuteldeel("tsreg", tsReg);
        return new EntiteitSleutel(PersoonInschrijvingHistorie.class, veld, eigenaar);
    }
}
