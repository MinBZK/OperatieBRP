/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.Sleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EntiteitSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Geslachtsaanduiding;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.IstSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Bericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3BerichtenBron;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Voorkomen;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonAdres;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonGeslachtsaanduidingHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonIDHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonNummerverwijzingHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonReisdocument;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Relatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortAdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortRelatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Stapel;

import org.junit.Test;

/**
 * Unit test voor {@link DeltaVergelijkerResultaat}.
 */
public class DeltaVergelijkerResultaatTest {

    private final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
    private final PersoonIDHistorie idOud = new PersoonIDHistorie(persoon);
    private final PersoonIDHistorie idNieuw = new PersoonIDHistorie(persoon);
    private final Partij partij = new Partij("naam", 1);
    private final AdministratieveHandeling administratieveHandeling = new AdministratieveHandeling(
        partij,
        SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL);
    private final Timestamp timestamp = Timestamp.valueOf("2002-02-10 12:00:00.000");
    private final BRPActie actie = new BRPActie(SoortActie.CONVERSIE_GBA, administratieveHandeling, partij, timestamp);

    @Test
    public void testVoegToeOfVervangVerschil() {
        final VergelijkerResultaat vergelijkerResultaat = new DeltaVergelijkerResultaat();

        final Verschil veldVerschil = new Verschil(new EntiteitSleutel(PersoonIDHistorie.class, "bsn"), 1, 2, idOud, idNieuw);
        vergelijkerResultaat.voegToeOfVervangVerschil(veldVerschil);

        final Set<Verschil> verschillen = vergelijkerResultaat.getVerschillen();
        assertNotNull(verschillen);
        assertFalse(verschillen.isEmpty());

        final List<VerschilGroep> verschilGroepen = vergelijkerResultaat.getVerschilGroepen();
        assertNotNull(verschilGroepen);
        assertFalse(verschilGroepen.isEmpty());
        assertEquals(verschilGroepen.size(), verschillen.size());

        final Verschil veldVerschil2 = new Verschil(new EntiteitSleutel(PersoonIDHistorie.class, "anr"), 1, 2, null, idNieuw);
        vergelijkerResultaat.voegToeOfVervangVerschil(veldVerschil2);
        final Set<Verschil> verschillen2 = vergelijkerResultaat.getVerschillen();
        assertNotNull(verschillen2);
        assertTrue(verschillen2.size() == 2);

        vergelijkerResultaat.voegToeOfVervangVerschil(veldVerschil);
        final Set<Verschil> verschillen3 = vergelijkerResultaat.getVerschillen();
        assertNotNull(verschillen3);
        assertTrue(verschillen3.size() == 2);
    }

    @Test
    public void testGetVerschillen() {
        final VergelijkerResultaat vergelijkerResultaat = new DeltaVergelijkerResultaat();

        final Verschil veldVerschil = new Verschil(new EntiteitSleutel(PersoonIDHistorie.class, "bsn"), 1, 2, idOud, idNieuw);
        vergelijkerResultaat.voegToeOfVervangVerschil(veldVerschil);

        final Set<Verschil> geenVerschillen = vergelijkerResultaat.getVerschillen(VerschilType.ELEMENT_NIEUW, "bsn");
        assertTrue(geenVerschillen.isEmpty());

        final Set<Verschil> verschillen = vergelijkerResultaat.getVerschillen(VerschilType.ELEMENT_AANGEPAST, "bsn");
        assertFalse(verschillen.isEmpty());

        final Set<Verschil> verschillen2 = vergelijkerResultaat.getVerschillen(VerschilType.ELEMENT_AANGEPAST, false, "bsn");
        assertFalse(verschillen2.isEmpty());

        final Set<Verschil> verschillen3 = vergelijkerResultaat.getVerschillen(VerschilType.ELEMENT_AANGEPAST, true, "bsn");
        assertFalse(verschillen3.isEmpty());
    }

    @Test
    public void testGetVerschillenIst() {
        final VergelijkerResultaat vergelijkerResultaat = new DeltaVergelijkerResultaat();
        final IstSleutel istSleutel = new IstSleutel(new Stapel(new Persoon(SoortPersoon.ONBEKEND), "09", 0), true);
        final Verschil istVerschil = new Verschil(istSleutel, 1, 2, null, null);
        vergelijkerResultaat.voegToeOfVervangVerschil(istVerschil);

        final Set<Verschil> verschillen4 = vergelijkerResultaat.getVerschillen(VerschilType.ELEMENT_AANGEPAST, false, null);
        assertFalse(verschillen4.isEmpty());

        final Set<Verschil> verschillen5 = vergelijkerResultaat.getVerschillen(VerschilType.ELEMENT_AANGEPAST, true, null);
        assertTrue(verschillen5.isEmpty());
    }

    @Test
    public void testVervangVerschillen() {
        final VergelijkerResultaat vergelijkerResultaat = new DeltaVergelijkerResultaat();
        final Verschil verschil = new Verschil(new EntiteitSleutel(PersoonReisdocument.class, "redenVerlies"), 1, 2, null, null);
        vergelijkerResultaat.voegToeOfVervangVerschil(verschil);

        final Set<Verschil> verschillen = vergelijkerResultaat.getVerschillen();
        assertNotNull(verschillen);
        assertFalse(verschillen.isEmpty());

        final Verschil verschil2 = new Verschil(new EntiteitSleutel(PersoonIDHistorie.class, "bsn"), 1, 2, idOud, idNieuw);
        final VerschilGroep verschilGroep = VerschilGroep.maakVerschilGroepMetHistorie(idOud);
        verschilGroep.addVerschil(verschil2);
        vergelijkerResultaat.vervangVerschillen(Collections.singletonList(verschilGroep));
        final Set<Verschil> verschillen2 = vergelijkerResultaat.getVerschillen();
        assertNotNull(verschillen2);
        assertFalse(verschillen2.isEmpty());
        assertFalse(verschillen2.iterator().next().equals(verschil));
    }

    @Test
    public void testVerwijderVerschilVoorHistorie() {
        final VergelijkerResultaat vergelijkerResultaat = new DeltaVergelijkerResultaat();
        final Verschil verschil = new Verschil(new EntiteitSleutel(PersoonIDHistorie.class, "bsn"), 1, 2, idOud, idNieuw);
        vergelijkerResultaat.voegToeOfVervangVerschil(verschil);

        final Set<Verschil> verschillen = vergelijkerResultaat.getVerschillen();
        assertNotNull(verschillen);
        assertFalse(verschillen.isEmpty());

        vergelijkerResultaat.verwijderVerschillenVoorHistorie(idOud);

        final Set<Verschil> verschillen2 = vergelijkerResultaat.getVerschillen();
        assertNotNull(verschillen2);
        assertTrue(verschillen2.isEmpty());

        vergelijkerResultaat.voegToeOfVervangVerschil(verschil);
        vergelijkerResultaat.verwijderVerschillenVoorHistorie(new PersoonGeslachtsaanduidingHistorie(persoon, Geslachtsaanduiding.MAN));

        final Set<Verschil> verschillen3 = vergelijkerResultaat.getVerschillen();
        assertNotNull(verschillen3);
        assertFalse(verschillen3.isEmpty());
    }

    @Test
    public void testVerwijderenVerschil() {
        final VergelijkerResultaat vergelijkerResultaat = new DeltaVergelijkerResultaat();
        final Verschil verschil = new Verschil(new EntiteitSleutel(PersoonIDHistorie.class, "bsn"), 1, 2, idOud, idNieuw);

        assertFalse(vergelijkerResultaat.verwijderVerschil(verschil));

        vergelijkerResultaat.voegToeOfVervangVerschil(verschil);
        final Verschil verschil2 = new Verschil(new EntiteitSleutel(PersoonIDHistorie.class, "anr"), 1, 2, idOud, idNieuw);
        assertFalse(vergelijkerResultaat.verwijderVerschil(verschil2));

        assertTrue(vergelijkerResultaat.verwijderVerschil(verschil));

        vergelijkerResultaat.voegToeOfVervangVerschil(verschil);
        vergelijkerResultaat.voegToeOfVervangVerschil(verschil2);
        assertTrue(vergelijkerResultaat.verwijderVerschil(verschil2));
    }

    @Test
    public void testVoegActieHerkomstToe() {
        final VergelijkerResultaat vergelijkerResultaat = new DeltaVergelijkerResultaat();

        final Map<BRPActie, Lo3Voorkomen> legeMap = vergelijkerResultaat.getActieHerkomstMap();
        assertNotNull(legeMap);
        assertTrue(legeMap.isEmpty());

        vergelijkerResultaat.koppelHerkomstAanActie(actie, null);
        assertFalse(vergelijkerResultaat.getActieHerkomstMap().isEmpty());

        final Lo3Voorkomen nieuweHerkomst = new Lo3Voorkomen(new Lo3Bericht("4321", Lo3BerichtenBron.INITIELE_VULLING, timestamp, "", false), "01");
        final Lo3Voorkomen nieuweHerkomst2 = new Lo3Voorkomen(new Lo3Bericht("1234", Lo3BerichtenBron.INITIELE_VULLING, timestamp, "", false), "02");

        vergelijkerResultaat.koppelHerkomstAanActie(actie,
                nieuweHerkomst);

        final Map<BRPActie, Lo3Voorkomen> gevuldeMap = vergelijkerResultaat.getActieHerkomstMap();
        assertNotNull(gevuldeMap);
        assertFalse(gevuldeMap.isEmpty());
        assertEquals(nieuweHerkomst, gevuldeMap.get(actie));

        vergelijkerResultaat.koppelHerkomstAanActie(actie, nieuweHerkomst2);
        final Map<BRPActie, Lo3Voorkomen> gevuldeMap2 = vergelijkerResultaat.getActieHerkomstMap();
        assertNotNull(gevuldeMap2);
        assertFalse(gevuldeMap2.isEmpty());
        assertEquals(nieuweHerkomst, gevuldeMap2.get(actie));

        final Map<BRPActie, Lo3Voorkomen> actieHerkomstMap = new HashMap<>();
        actieHerkomstMap.put(actie, new Lo3Voorkomen(new Lo3Bericht("1234", Lo3BerichtenBron.INITIELE_VULLING, timestamp, "", false), "09"));

        vergelijkerResultaat.voegActieHerkomstMapInhoudToe(actieHerkomstMap);
        final Map<BRPActie, Lo3Voorkomen> gevuldeMap3 = vergelijkerResultaat.getActieHerkomstMap();
        assertNotNull(gevuldeMap3);
        assertFalse(gevuldeMap3.isEmpty());
    }

    @Test
    public void testZoekVerschilEnBevatSleutel() {
        final VergelijkerResultaat vergelijkerResultaat = new DeltaVergelijkerResultaat();
        final Sleutel sleutel = new EntiteitSleutel(PersoonIDHistorie.class, "bsn");
        final Sleutel sleutel2 = new EntiteitSleutel(PersoonIDHistorie.class, "anr");
        final Verschil verschil = new Verschil(sleutel, 1, 2, idOud, idNieuw);

        vergelijkerResultaat.voegToeOfVervangVerschil(verschil);
        assertTrue(vergelijkerResultaat.bevatSleutel(sleutel));
        assertFalse(vergelijkerResultaat.bevatSleutel(sleutel2));

        assertEquals(verschil, vergelijkerResultaat.zoekVerschil(sleutel));
        assertEquals(verschil, vergelijkerResultaat.zoekVerschil(sleutel, VerschilType.ELEMENT_AANGEPAST));
        assertNull(vergelijkerResultaat.zoekVerschil(sleutel, VerschilType.ELEMENT_NIEUW));
        assertNull(vergelijkerResultaat.zoekVerschil(sleutel2));
        assertNull(vergelijkerResultaat.zoekVerschil(new EntiteitSleutel(Persoon.class, Persoon.VERSIENUMMER), VerschilType.ELEMENT_AANGEPAST));
    }

    @Test
    public void testZoekVerschillen() {
        final VergelijkerResultaat vergelijkerResultaat = new DeltaVergelijkerResultaat();
        final Sleutel sleutel = new EntiteitSleutel(PersoonIDHistorie.class, "bsn");
        final Sleutel sleutel1a = new EntiteitSleutel(PersoonIDHistorie.class, "bsn");
        final Sleutel sleutel2 = new IstSleutel(new Stapel(persoon, "02", 0), false);
        sleutel.addSleuteldeel("deel", "ander");
        sleutel1a.addSleuteldeel("ander", "deel");

        final Verschil verschil = new Verschil(sleutel, 1, 2, idOud, idNieuw);
        final Verschil verschil1a = new Verschil(sleutel1a, 1, 2, idOud, idNieuw);
        final Verschil verschil2 = new Verschil(sleutel2, 1, 2, idOud, idNieuw);

        vergelijkerResultaat.voegToeOfVervangVerschil(verschil);
        vergelijkerResultaat.voegToeOfVervangVerschil(verschil1a);
        vergelijkerResultaat.voegToeOfVervangVerschil(verschil2);
        assertTrue(vergelijkerResultaat.bevatSleutel(sleutel));

        final EntiteitSleutel zoekSleutel = new EntiteitSleutel(PersoonIDHistorie.class, "bsn");
        final List<Verschil> verschillen = vergelijkerResultaat.zoekVerschillen(zoekSleutel);
        assertFalse(verschillen.isEmpty());

        final EntiteitSleutel zoekSleutel2 = new EntiteitSleutel(PersoonIDHistorie.class, "anr");
        final List<Verschil> verschillen2 = vergelijkerResultaat.zoekVerschillen(zoekSleutel2);
        assertTrue(verschillen2.isEmpty());
    }

    @Test
    public void testIsLeeg() {
        final VergelijkerResultaat vergelijkerResultaat = new DeltaVergelijkerResultaat();
        final Sleutel sleutel = new EntiteitSleutel(PersoonIDHistorie.class, "bsn");
        final Verschil verschil = new Verschil(sleutel, 1, 2, idOud, idNieuw);

        assertTrue(vergelijkerResultaat.isLeeg());
        vergelijkerResultaat.voegToeOfVervangVerschil(verschil);
        assertFalse(vergelijkerResultaat.isLeeg());
        assertEquals(
            String.format("Verschillen:%n"
                          + "Sleutel      : EntiteitSleutel[Entiteit=class nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonIDHistorie,Veld=bsn,EigenaarSleutel=<null>]%n"
                          + "Oude waarde  : 1%n"
                          + "Nieuwe waarde: 2%n"
                          + "Type         : ELEMENT_AANGEPAST%n"
                          + "%n"),
            vergelijkerResultaat.toString());
    }

    @Test
    public void testGetVerschil() {
        final VergelijkerResultaat vergelijkerResultaat = new DeltaVergelijkerResultaat();
        final Sleutel sleutel = new EntiteitSleutel(PersoonIDHistorie.class, "bsn");
        final Verschil verschil = new Verschil(sleutel, 1, 2, idOud, idNieuw);
        vergelijkerResultaat.voegToeOfVervangVerschil(verschil);

        assertEquals(verschil, vergelijkerResultaat.getVerschil(VerschilType.ELEMENT_AANGEPAST));
        assertNull(vergelijkerResultaat.getVerschil(VerschilType.ELEMENT_NIEUW));
    }

    @Test
    public void testGroepeer() {
        final List<Verschil> verschillen = new ArrayList<>();

        final Verschil verschilHistorie1 = new Verschil(new EntiteitSleutel(PersoonIDHistorie.class, "bsn"), 1, 2, idOud, idNieuw);
        verschillen.add(verschilHistorie1);
        final Verschil verschilHistorie2 = new Verschil(new EntiteitSleutel(PersoonIDHistorie.class, "anr"), 3, 4, idOud, idNieuw);
        verschillen.add(verschilHistorie2);
        final Verschil verschilHistorie3 = new Verschil(new EntiteitSleutel(PersoonIDHistorie.class, "dag"), 5, 6, idOud, idNieuw);
        verschillen.add(verschilHistorie3);
        final Verschil verschilActueel1 = new Verschil(new EntiteitSleutel(Persoon.class, "bsn"), 1, 2, null, null);
        verschillen.add(verschilActueel1);
        final Verschil verschilActueel2 = new Verschil(new EntiteitSleutel(Persoon.class, "anr"), 3, 4, null, null);
        verschillen.add(verschilActueel2);

        final VergelijkerResultaat vergelijkerResultaat = new DeltaVergelijkerResultaat();
        vergelijkerResultaat.voegVerschillenToe(verschillen);

        final List<VerschilGroep> groepen = vergelijkerResultaat.getVerschilGroepen();

        assertEquals(3, groepen.size());

        for (final VerschilGroep verschilGroep : groepen) {
            if (verschilGroep.isHistorieVerschil()) {
                final List<Verschil> historieVerschillen = Arrays.asList(verschilHistorie1, verschilHistorie2, verschilHistorie3);
                assertEquals(3, verschilGroep.size());
                for (final Verschil verschil : verschilGroep) {
                    assertTrue(historieVerschillen.contains(verschil));
                }
            } else {
                assertEquals(1, verschilGroep.size());
                assertTrue(verschilActueel1 == verschilGroep.get(0) || verschilActueel2 == verschilGroep.get(0));
            }
        }
    }

    @Test
    public void testToevoegenEnOphalenVerschil() {
        final VergelijkerResultaat vergelijkerResultaat = new DeltaVergelijkerResultaat();
        // Er zijn nog geen verschillen
        assertTrue(vergelijkerResultaat.isLeeg());
        final Verschil geenVerkregenVerschil = vergelijkerResultaat.getVerschil(VerschilType.ELEMENT_VERWIJDERD);
        assertNull(geenVerkregenVerschil);

        // Verschil toegevoegd aan vergelijker resultaat
        final Verschil testVerschil = new Verschil(new EntiteitSleutel(String.class, "mesg"), null, null, VerschilType.ELEMENT_VERWIJDERD, null, null);
        vergelijkerResultaat.voegToeOfVervangVerschil(testVerschil);
        assertFalse(vergelijkerResultaat.isLeeg());
        final Verschil verkregenVerschil = vergelijkerResultaat.getVerschil(VerschilType.ELEMENT_VERWIJDERD);
        assertEquals(testVerschil, verkregenVerschil);

        // Geen verschillen voor dit verschiltype
        final Verschil nietGevondenVerschil = vergelijkerResultaat.getVerschil(VerschilType.RIJ_TOEGEVOEGD);
        assertNull(nietGevondenVerschil);

        // Aangezien er geen IST-sleutel is toegevoegd aan deze set, is er ook geen resultaat
        final Set<Verschil> verschillenSet = vergelijkerResultaat.getVerschillen(VerschilType.ELEMENT_VERWIJDERD, null);
        assertTrue(verschillenSet.isEmpty());
    }

    @Test
    public void testDeltaEntiteitPaar() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final VergelijkerResultaat resultaat = new DeltaVergelijkerResultaat();
        assertTrue(resultaat.getDeltaEntiteitPaarSet().isEmpty());
        resultaat.addDeltaEntiteitPaar(persoon, persoon);
        assertEquals(1, resultaat.getDeltaEntiteitPaarSet().size());

        resultaat.addDeltaEntiteitPaar(persoon, persoon);
        assertEquals(1, resultaat.getDeltaEntiteitPaarSet().size());

        resultaat.addDeltaEntiteitPaar(new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING), new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING));
        assertEquals(2, resultaat.getDeltaEntiteitPaarSet().size());

        resultaat.addDeltaEntiteitPaar(null, persoon);
        assertEquals(2, resultaat.getDeltaEntiteitPaarSet().size());

        resultaat.addDeltaEntiteitPaar(persoon, null);
        assertEquals(2, resultaat.getDeltaEntiteitPaarSet().size());
    }

    @Test
    public void testBevatAlleenInfraWijzigingen() {
        final VergelijkerResultaat resultaat = new DeltaVergelijkerResultaat();
        resultaat.voegToeOfVervangVerschil(new Verschil(
            new EntiteitSleutel(Persoon.class, Persoon.BUITENLANDSADRESREGEL1MIGRATIE),
            null,
            null,
            VerschilType.ELEMENT_AANGEPAST,
            null,
            null));
        assertTrue(resultaat.bevatAlleenInfrastructureleWijzigingen());
    }

    @Test
    public void testBevatNietAlleenInfraWijzigingen() {
        final VergelijkerResultaat resultaat = new DeltaVergelijkerResultaat();
        resultaat.voegToeOfVervangVerschil(new Verschil(
            new EntiteitSleutel(Persoon.class, "persoonSamengesteldeNaamHistorieSet"),
            null,
            new PersoonSamengesteldeNaamHistorie(new Persoon(SoortPersoon.INGESCHREVENE), "Put", false, false),
            VerschilType.RIJ_TOEGEVOEGD,
            null,
            null));
        assertFalse(resultaat.bevatAlleenInfrastructureleWijzigingen());
    }

    @Test
    public void testBevatAlleenAnummerWijzigingen() {
        final PersoonNummerverwijzingHistorie historie = new PersoonNummerverwijzingHistorie(new Persoon(SoortPersoon.INGESCHREVENE));
        final VergelijkerResultaat resultaat = new DeltaVergelijkerResultaat();
        resultaat.voegToeOfVervangVerschil(new Verschil(
            new EntiteitSleutel(Persoon.class, "persoonNummerverwijzingHistorieSet"),
            null,
            historie,
            VerschilType.RIJ_TOEGEVOEGD,
            null,
            null));

        assertTrue(resultaat.bevatAlleenAnummerWijzigingen());
    }

    @Test
    public void testBevatAlleenAnummerWijzigingenMeerWijzigingen() {
        final PersoonNummerverwijzingHistorie historie = new PersoonNummerverwijzingHistorie(new Persoon(SoortPersoon.INGESCHREVENE));
        final VergelijkerResultaat resultaat = new DeltaVergelijkerResultaat();
        resultaat.voegToeOfVervangVerschil(new Verschil(
                new EntiteitSleutel(Persoon.class, "persoonNummerverwijzingHistorieSet"),
                null,
                historie,
                VerschilType.RIJ_TOEGEVOEGD,
                null,
                null));
        resultaat.voegToeOfVervangVerschil(new Verschil(
            new EntiteitSleutel(Persoon.class, "persoonAdresSet"),
            null,
            new PersoonAdres(new Persoon(SoortPersoon.INGESCHREVENE)),
            VerschilType.RIJ_TOEGEVOEGD,
            null,
            null));

        assertFalse(resultaat.bevatAlleenAnummerWijzigingen());
    }

    @Test
    public void testBevatAlleenAnummerWijzigingenGeenNummerverwijzing() {
        final PersoonNummerverwijzingHistorie historie = new PersoonNummerverwijzingHistorie(new Persoon(SoortPersoon.INGESCHREVENE));
        final VergelijkerResultaat resultaat = new DeltaVergelijkerResultaat();
        resultaat.voegToeOfVervangVerschil(new Verschil(
            new EntiteitSleutel(Persoon.class, "persoonMigratieHistorieSet"),
            null,
            historie,
            VerschilType.RIJ_TOEGEVOEGD,
            null,
            null));

        assertFalse(resultaat.bevatAlleenAnummerWijzigingen());
    }
}
