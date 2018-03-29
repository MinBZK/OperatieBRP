/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.alaag;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Aangever;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidOuderHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstAttenderingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstSelectieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LeveringsautorisatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Nationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Onderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRolHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdresHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfgeleidAdministratiefHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonDeelnameEuVerkiezingenHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeboorteHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsaanduidingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponent;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponentHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIDHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonInschrijvingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonMigratieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNaamgebruikHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteitHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNummerverwijzingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonOverlijdenHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonPersoonskaartHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonUitsluitingKiesrechtHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerblijfsrechtHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerificatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerificatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVoornaam;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVoornaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenBeeindigingRelatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerkrijgingNLNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerliesNLNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenWijzigingVerblijf;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RelatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortNederlandsReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsautorisatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Verblijfsrecht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Naamgebruik;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Protocolleringsniveau;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMigratie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;

import org.junit.Before;
import org.junit.Test;

/**
 * Unittest voor {@link ALaagAfleidingsUtil}.
 */
public class ALaagAfleidingsUtilTest {

    private static final Nationaliteit NEDERLANDSE = new Nationaliteit("Nederlandse", "0001");

    private static final RedenVerkrijgingNLNationaliteit REDEN_VERKRIJGING_NL_NATIONALITEIT =
            new RedenVerkrijgingNLNationaliteit("184", "Rijkswet Nederlanderschap 1984 ivm wijziging 2010, art. 6, lid 1, sub n");
    private static final RedenVerliesNLNationaliteit REDEN_VERLIES_NL_NATIONALITEIT =
            new RedenVerliesNLNationaliteit("188", "Rijkswet Nederlanderschap 1984 ivm wijziging 2010, art. 15, lid 1, sub f");
    private static final String MIGRATIE_REDEN_OPNAME_NATIONALITEIT = "310";
    private static final String MIGRATIE_REDEN_VERLIES_NATIONALITEIT = "410";
    private static final Integer MIGRATIE_DATUM_EINDE_BIJHOUDING = 20160101;
    private static final LandOfGebied NEDERLAND = new LandOfGebied("6030", "Nederland");
    private static final Partij DEN_HAAG = new Partij("Gemeente 's-Gravenhage", "051801");
    private static final Timestamp DATUM_TIJDSTIP = Timestamp.from(Instant.now());

    private Persoon persoon;
    private PersoonNationaliteit persoonNationaliteit;
    private static final AdministratieveHandeling ADMINISTRATIEVE_HANDELING =
            new AdministratieveHandeling(DEN_HAAG, SoortAdministratieveHandeling.AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND, DATUM_TIJDSTIP);

    @Before
    public void setUp() {
        persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        persoonNationaliteit = new PersoonNationaliteit(persoon, NEDERLANDSE);
    }

    @Test(expected = InvocationTargetException.class)
    public void testPrivateConstructor() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        final Constructor<ALaagAfleidingsUtil> c = ALaagAfleidingsUtil.class.getDeclaredConstructor();
        c.setAccessible(true);
        c.newInstance();
    }

    @Test
    public void testHistorieAlleenVervallen() throws ReflectiveOperationException {
        final PersoonGeboorteHistorie geboorteHistorie = new PersoonGeboorteHistorie(persoon, 20160101, NEDERLAND);
        geboorteHistorie.setDatumTijdVerval(DATUM_TIJDSTIP);
        persoon.addPersoonGeboorteHistorie(geboorteHistorie);
        persoon.setLandOfGebiedGeboorte(geboorteHistorie.getLandOfGebied());
        persoon.setDatumGeboorte(geboorteHistorie.getDatumGeboorte());
        persoon.setActueelVoorGeboorte(true);
        ALaagAfleidingsUtil.vulALaag(persoon);
        assertNull(persoon.getLandOfGebiedGeboorte());
        assertNull(persoon.getDatumGeboorte());
        assertFalse(persoon.isActueelVoorGeboorte());
    }

    @Test
    public void testPersoonALaagAfleiden() throws ReflectiveOperationException {
        final PersoonNationaliteitHistorie historieActueel = maakActueleNationaliteitHistorie();

        final PersoonGeboorteHistorie geboorteHistorie = new PersoonGeboorteHistorie(persoon, 20160101, NEDERLAND);
        geboorteHistorie.setBuitenlandsePlaatsGeboorte("Berlijn");
        geboorteHistorie.setGemeente(new Gemeente((short) 521, "'s-Gravenhage", "0518", DEN_HAAG));
        geboorteHistorie.setOmschrijvingGeboortelocatie("Omschrijving geboorte locatie");
        geboorteHistorie.setWoonplaatsnaamGeboorte("'s-Gravenhage");

        final PersoonMigratieHistorie migratieHistorie = new PersoonMigratieHistorie(persoon, SoortMigratie.EMIGRATIE);
        migratieHistorie.setAangeverMigratie(new Aangever('I', "Ingeschrevene", "De ingeschrevene zelf"));
        migratieHistorie.setBuitenlandsAdresRegel1("adresRegel1");
        migratieHistorie.setBuitenlandsAdresRegel2("adresRegel2");
        migratieHistorie.setBuitenlandsAdresRegel3("adresRegel3");
        migratieHistorie.setBuitenlandsAdresRegel4("adresRegel4");
        migratieHistorie.setBuitenlandsAdresRegel5("adresRegel5");
        migratieHistorie.setBuitenlandsAdresRegel6("adresRegel6");
        migratieHistorie.setLandOfGebied(NEDERLAND);
        migratieHistorie.setRedenWijzigingMigratie(new RedenWijzigingVerblijf('A', "Ambtshalve"));

        persoonNationaliteit.addPersoonNationaliteitHistorie(historieActueel);
        persoon.addPersoonNationaliteit(persoonNationaliteit);
        persoon.addPersoonGeboorteHistorie(geboorteHistorie);
        persoon.addPersoonMigratieHistorie(migratieHistorie);

        ALaagAfleidingsUtil.vulALaag(persoon);
        controleerNationaliteitALaag(true);

        controleerPersoonALaag(persoon, geboorteHistorie, migratieHistorie);
    }

    @Test
    public void testPersoonNationaliteitALaagAfleiden() throws ReflectiveOperationException {
        final PersoonNationaliteitHistorie historieActueel = maakActueleNationaliteitHistorie();

        final PersoonNationaliteitHistorie historieVervallen = new PersoonNationaliteitHistorie(persoonNationaliteit);
        historieVervallen.setRedenVerliesNLNationaliteit(
            new RedenVerliesNLNationaliteit("187", "Rijkswet Nederlanderschap 1984 ivm wijziging 2010," + " art. 14, lid 4"));
        historieVervallen.setRedenVerkrijgingNLNationaliteit(
            new RedenVerkrijgingNLNationaliteit("185", "Rijkswet Nederlanderschap 1984 ivm wijziging 2010, art. 6, lid 1, sub o"));
        historieVervallen.setIndicatieBijhoudingBeeindigd(false);
        historieVervallen.setMigratieRedenOpnameNationaliteit("311");
        historieVervallen.setMigratieRedenBeeindigenNationaliteit("411");
        historieVervallen.setMigratieDatumEindeBijhouding(20120101);
        historieVervallen.setDatumTijdVerval(DATUM_TIJDSTIP);

        persoonNationaliteit.addPersoonNationaliteitHistorie(historieVervallen);
        persoonNationaliteit.addPersoonNationaliteitHistorie(historieActueel);

        ALaagAfleidingsUtil.vulALaag(persoonNationaliteit);
        controleerNationaliteitALaag(true);
    }

    @Test
    public void testNamenVanHistorieKomenOvereenMetALaag() throws ReflectiveOperationException {
        final Relatie relatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final RelatieHistorie relatieHistorie = new RelatieHistorie(relatie);

        final Betrokkenheid betrokkenheid = new Betrokkenheid(SoortBetrokkenheid.KIND, relatie);
        relatie.addRelatieHistorie(relatieHistorie);
        relatie.addBetrokkenheid(betrokkenheid);

        final PersoonAdres persoonAdres = new PersoonAdres(persoon);
        persoonAdres.addPersoonAdresHistorie(
            new PersoonAdresHistorie(persoonAdres, SoortAdres.BRIEFADRES, NEDERLAND, new RedenWijzigingVerblijf('A', "Test")));
        final PersoonGeslachtsnaamcomponent persoonGeslachtsnaamcomponent = new PersoonGeslachtsnaamcomponent(persoon, 0);
        persoonGeslachtsnaamcomponent.addPersoonGeslachtsnaamcomponentHistorie(
            new PersoonGeslachtsnaamcomponentHistorie(persoonGeslachtsnaamcomponent, "Stam"));
        final PersoonIndicatie persoonIndicatie = new PersoonIndicatie(persoon, SoortIndicatie.BEHANDELD_ALS_NEDERLANDER);
        persoonIndicatie.addPersoonIndicatieHistorie(new PersoonIndicatieHistorie(persoonIndicatie, true));
        final PersoonNationaliteit persoonNationaliteit = new PersoonNationaliteit(persoon, NEDERLANDSE);
        persoonNationaliteit.addPersoonNationaliteitHistorie(new PersoonNationaliteitHistorie(persoonNationaliteit));
        final PersoonVerificatie persoonVerificatie = new PersoonVerificatie(persoon, DEN_HAAG, "soortVerificatie");
        persoonVerificatie.addPersoonVerificatieHistorie(new PersoonVerificatieHistorie(persoonVerificatie, 20160101));
        final PersoonVoornaam persoonVoornaam = new PersoonVoornaam(persoon, 1);
        persoonVoornaam.addPersoonVoornaamHistorie(new PersoonVoornaamHistorie(persoonVoornaam, "Jaap"));

        persoon.addPersoonAdres(persoonAdres);
        persoon.addPersoonAfgeleidAdministratiefHistorie(
            new PersoonAfgeleidAdministratiefHistorie((short) 1, persoon, ADMINISTRATIEVE_HANDELING, DATUM_TIJDSTIP));
        persoon.addBetrokkenheid(betrokkenheid);
        persoon.addPersoonBijhoudingHistorie(new PersoonBijhoudingHistorie(persoon, DEN_HAAG, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL));
        persoon.addPersoonDeelnameEuVerkiezingenHistorie(new PersoonDeelnameEuVerkiezingenHistorie(persoon, true));
        persoon.addPersoonGeboorteHistorie(new PersoonGeboorteHistorie(persoon, 20160101, NEDERLAND));
        persoon.addPersoonMigratieHistorie(new PersoonMigratieHistorie(persoon, SoortMigratie.EMIGRATIE));
        persoon.addPersoonGeslachtsaanduidingHistorie(new PersoonGeslachtsaanduidingHistorie(persoon, Geslachtsaanduiding.MAN));
        persoon.addPersoonGeslachtsnaamcomponent(persoonGeslachtsnaamcomponent);
        persoon.addPersoonIDHistorie(new PersoonIDHistorie(persoon));
        persoon.addPersoonIndicatie(persoonIndicatie);
        persoon.addPersoonInschrijvingHistorie(new PersoonInschrijvingHistorie(persoon, 20160101, 1L, DATUM_TIJDSTIP));
        persoon.addPersoonNaamgebruikHistorie(new PersoonNaamgebruikHistorie(persoon, "Stam", true, Naamgebruik.EIGEN));
        persoon.addPersoonNationaliteit(persoonNationaliteit);
        persoon.addPersoonNummerverwijzingHistorie(new PersoonNummerverwijzingHistorie(persoon));
        persoon.addPersoonOverlijdenHistorie(new PersoonOverlijdenHistorie(persoon, 20160101, NEDERLAND));
        persoon.addOnderzoek(new Onderzoek(DEN_HAAG, persoon));
        persoon.addPersoonPersoonskaartHistorie(new PersoonPersoonskaartHistorie(persoon, true));
        persoon.addPersoonReisdocument(new PersoonReisdocument(persoon, new SoortNederlandsReisdocument("NI", "Nederlands paspoort")));
        persoon.addPersoonSamengesteldeNaamHistorie(new PersoonSamengesteldeNaamHistorie(persoon, "Stam", true, true));
        persoon.addPersoonUitsluitingKiesrechtHistorie(new PersoonUitsluitingKiesrechtHistorie(persoon, true));
        persoon.addPersoonVerblijfsrechtHistorie(
            new PersoonVerblijfsrechtHistorie(persoon, new Verblijfsrecht("00", "Onbekend"), 20160101, 20160101));
        persoon.addPersoonVerificatie(persoonVerificatie);
        persoon.addPersoonVoornaam(persoonVoornaam);

        ALaagAfleidingsUtil.vulALaag(persoon);
        ALaagAfleidingsUtil.vulALaag(relatie);
    }

    @Test
    public void testLegenALaag() {
        final PersoonNationaliteitHistorie nationaliteitHistorie = maakActueleNationaliteitHistorie();
        persoonNationaliteit.addPersoonNationaliteitHistorie(nationaliteitHistorie);

        ALaagAfleidingsUtil.vulALaag(persoonNationaliteit);
        controleerNationaliteitALaag(true);

        ALaagAfleidingsUtil.leegALaag(persoonNationaliteit);
        controleerNationaliteitALaag(false);
    }

    @Test
    public void testRelatieALaagVullenEnLegen() {
        final Relatie relatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final RelatieHistorie relatieHistorie = new RelatieHistorie(relatie);
        relatieHistorie.setBuitenlandsePlaatsAanvang("buitenlandsePlaatsAanvang");
        relatieHistorie.setBuitenlandsePlaatsEinde("buitenlandsePlaatsEinde");
        relatieHistorie.setBuitenlandseRegioAanvang("buitenlandseRegioAanvang");
        relatieHistorie.setBuitenlandseRegioEinde("buitenlandseRegioEinde");
        relatieHistorie.setDatumAanvang(20150101);
        relatieHistorie.setDatumEinde(20160101);
        relatieHistorie.setOmschrijvingLocatieAanvang("omschrijvingLocatieAanvang");
        relatieHistorie.setOmschrijvingLocatieEinde("omschrijvingLocatieEinde");
        relatieHistorie.setLandOfGebiedAanvang(NEDERLAND);
        relatieHistorie.setLandOfGebiedEinde(new LandOfGebied("7088", "Amerikaanse Maagdeneilanden"));
        relatieHistorie.setGemeenteAanvang(new Gemeente((short) 3, "Onbekend", "0000", DEN_HAAG));
        relatieHistorie.setGemeenteEinde(new Gemeente((short) 8, "Bedum", "0005", DEN_HAAG));
        relatieHistorie.setWoonplaatsnaamAanvang("woonplaatsAanvang");
        relatieHistorie.setWoonplaatsnaamEinde("woonplaatsEinde");
        relatieHistorie.setRedenBeeindigingRelatie(new RedenBeeindigingRelatie('Z', "omzetting"));

        relatie.addRelatieHistorie(relatieHistorie);
        final Betrokkenheid betrokkenheid = new Betrokkenheid(SoortBetrokkenheid.KIND, relatie);
        betrokkenheid.setPersoon(persoon);

        final Betrokkenheid andereBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.OUDER, relatie);
        final BetrokkenheidOuderHistorie ouderHistorie = new BetrokkenheidOuderHistorie(andereBetrokkenheid);
        ouderHistorie.setIndicatieOuderUitWieKindIsGeboren(true);
        andereBetrokkenheid.addBetrokkenheidOuderHistorie(ouderHistorie);

        final Persoon anderePersoon = new Persoon(SoortPersoon.PSEUDO_PERSOON);
        final PersoonSamengesteldeNaamHistorie samengesteldeNaamHistorie = new PersoonSamengesteldeNaamHistorie(anderePersoon, "stam", true, true);
        anderePersoon.addPersoonSamengesteldeNaamHistorie(samengesteldeNaamHistorie);
        andereBetrokkenheid.setPersoon(anderePersoon);
        anderePersoon.addBetrokkenheid(andereBetrokkenheid);

        relatie.addBetrokkenheid(betrokkenheid);
        relatie.addBetrokkenheid(andereBetrokkenheid);
        persoon.addBetrokkenheid(betrokkenheid);

        ALaagAfleidingsUtil.vulALaag(persoon);

        assertEquals(relatieHistorie.getBuitenlandsePlaatsAanvang(), relatie.getBuitenlandsePlaatsAanvang());
        assertEquals(relatieHistorie.getBuitenlandsePlaatsEinde(), relatie.getBuitenlandsePlaatsEinde());
        assertEquals(relatieHistorie.getBuitenlandseRegioAanvang(), relatie.getBuitenlandseRegioAanvang());
        assertEquals(relatieHistorie.getBuitenlandseRegioEinde(), relatie.getBuitenlandseRegioEinde());
        assertEquals(relatieHistorie.getDatumAanvang(), relatie.getDatumAanvang());
        assertEquals(relatieHistorie.getDatumEinde(), relatie.getDatumEinde());
        assertEquals(relatieHistorie.getOmschrijvingLocatieAanvang(), relatie.getOmschrijvingLocatieAanvang());
        assertEquals(relatieHistorie.getOmschrijvingLocatieEinde(), relatie.getOmschrijvingLocatieEinde());
        assertEquals(relatieHistorie.getLandOfGebiedAanvang(), relatie.getLandOfGebiedAanvang());
        assertEquals(relatieHistorie.getLandOfGebiedEinde(), relatie.getLandOfGebiedEinde());
        assertEquals(relatieHistorie.getGemeenteAanvang(), relatie.getGemeenteAanvang());
        assertEquals(relatieHistorie.getGemeenteEinde(), relatie.getGemeenteEinde());
        assertEquals(relatieHistorie.getWoonplaatsnaamAanvang(), relatie.getWoonplaatsnaamAanvang());
        assertEquals(relatieHistorie.getWoonplaatsnaamEinde(), relatie.getWoonplaatsnaamEinde());
        assertEquals(relatieHistorie.getRedenBeeindigingRelatie(), relatie.getRedenBeeindigingRelatie());
        assertEquals(ouderHistorie.getIndicatieOuderUitWieKindIsGeboren(), andereBetrokkenheid.getIndicatieOuderUitWieKindIsGeboren());
        assertEquals(samengesteldeNaamHistorie.getGeslachtsnaamstam(), anderePersoon.getGeslachtsnaamstam());

        ALaagAfleidingsUtil.leegALaag(persoon);
        assertNull(relatie.getBuitenlandsePlaatsAanvang());
        assertNull(relatie.getBuitenlandsePlaatsEinde());
        assertNull(relatie.getBuitenlandseRegioAanvang());
        assertNull(relatie.getBuitenlandseRegioEinde());
        assertNull(relatie.getDatumAanvang());
        assertNull(relatie.getDatumEinde());
        assertNull(relatie.getOmschrijvingLocatieAanvang());
        assertNull(relatie.getOmschrijvingLocatieEinde());
        assertNull(relatie.getLandOfGebiedAanvang());
        assertNull(relatie.getLandOfGebiedEinde());
        assertNull(relatie.getGemeenteAanvang());
        assertNull(relatie.getGemeenteEinde());
        assertNull(relatie.getWoonplaatsnaamAanvang());
        assertNull(relatie.getWoonplaatsnaamEinde());
        assertNull(relatie.getRedenBeeindigingRelatie());
        assertNotNull(relatie.getSoortRelatie());
    }

    @Test
    public void testIndicatieActueelEnGeldig() {
        System.out.println("Geen historie");

        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        // persoon.setAdministratienummer(1233455L);
        // persoon.setActueelVoorIds(true);

        // Geen historie
        ALaagAfleidingsUtil.leegALaag(persoon);
        ALaagAfleidingsUtil.vulALaag(persoon);
        assertEquals(false, persoon.isActueelVoorIds());
        assertEquals(null, persoon.getAdministratienummer());

        // Actuele historie
        System.out.println("Actuele historie");
        final PersoonIDHistorie idHistorie = new PersoonIDHistorie(persoon);
        idHistorie.setAdministratienummer("1233455000");
        idHistorie.setBurgerservicenummer("345340000");
        persoon.getPersoonIDHistorieSet().add(idHistorie);
        ALaagAfleidingsUtil.leegALaag(persoon);
        ALaagAfleidingsUtil.vulALaag(persoon);
        assertEquals(true, persoon.isActueelVoorIds());
        assertEquals("1233455000", persoon.getAdministratienummer());

        // Vervallen historie
        System.out.println("Vervallen historie");
        idHistorie.setDatumTijdVerval(new Timestamp(System.currentTimeMillis()));
        ALaagAfleidingsUtil.leegALaag(persoon);
        ALaagAfleidingsUtil.vulALaag(persoon);
        assertEquals(false, persoon.isActueelVoorIds());
        assertEquals(null, persoon.getAdministratienummer());

        // Actuele historie
        System.out.println("Actuele historie");
        idHistorie.setDatumTijdVerval(null);
        ALaagAfleidingsUtil.leegALaag(persoon);
        ALaagAfleidingsUtil.vulALaag(persoon);
        assertEquals(true, persoon.isActueelVoorIds());
        assertEquals("1233455000", persoon.getAdministratienummer());

        // Beeindigde historie
        System.out.println("Beeindigde historie");
        idHistorie.setDatumEindeGeldigheid(20000101);
        ALaagAfleidingsUtil.leegALaag(persoon);
        ALaagAfleidingsUtil.vulALaag(persoon);
        assertEquals(false, persoon.isActueelVoorIds());
        assertEquals(null, persoon.getAdministratienummer());
    }

    private PersoonNationaliteitHistorie maakActueleNationaliteitHistorie() {
        final PersoonNationaliteitHistorie historieActueel = new PersoonNationaliteitHistorie(persoonNationaliteit);
        historieActueel.setRedenVerkrijgingNLNationaliteit(REDEN_VERKRIJGING_NL_NATIONALITEIT);
        historieActueel.setRedenVerliesNLNationaliteit(REDEN_VERLIES_NL_NATIONALITEIT);
        historieActueel.setIndicatieBijhoudingBeeindigd(true);
        historieActueel.setMigratieRedenOpnameNationaliteit(MIGRATIE_REDEN_OPNAME_NATIONALITEIT);
        historieActueel.setMigratieRedenBeeindigenNationaliteit(MIGRATIE_REDEN_VERLIES_NATIONALITEIT);
        historieActueel.setMigratieDatumEindeBijhouding(MIGRATIE_DATUM_EINDE_BIJHOUDING);
        return historieActueel;
    }

    private void controleerNationaliteitALaag(final boolean gevuldOfLeeg) {
        if (gevuldOfLeeg) {
            assertEquals(REDEN_VERKRIJGING_NL_NATIONALITEIT, persoonNationaliteit.getRedenVerkrijgingNLNationaliteit());
            assertEquals(REDEN_VERLIES_NL_NATIONALITEIT, persoonNationaliteit.getRedenVerliesNLNationaliteit());
            assertTrue(persoonNationaliteit.getIndicatieBijhoudingBeeindigd());
            assertEquals(MIGRATIE_REDEN_OPNAME_NATIONALITEIT, persoonNationaliteit.getMigratieRedenOpnameNationaliteit());
            assertEquals(MIGRATIE_REDEN_VERLIES_NATIONALITEIT, persoonNationaliteit.getMigratieRedenBeeindigenNationaliteit());
            assertEquals(MIGRATIE_DATUM_EINDE_BIJHOUDING, persoonNationaliteit.getMigratieDatumEindeBijhouding());
        } else {
            assertNull(persoonNationaliteit.getRedenVerkrijgingNLNationaliteit());
            assertNull(persoonNationaliteit.getRedenVerliesNLNationaliteit());
            assertNull(persoonNationaliteit.getIndicatieBijhoudingBeeindigd());
            assertNull(persoonNationaliteit.getMigratieRedenOpnameNationaliteit());
            assertNull(persoonNationaliteit.getMigratieRedenBeeindigenNationaliteit());
            assertNull(persoonNationaliteit.getMigratieDatumEindeBijhouding());
            assertNotNull(persoonNationaliteit.getNationaliteit());
        }
    }

    private void controleerPersoonALaag(
        final Persoon persoon,
        final PersoonGeboorteHistorie geboorteHistorie,
        final PersoonMigratieHistorie migratieHistorie)
    {
        // Migratie
        assertEquals(migratieHistorie.getAangeverMigratie(), persoon.getAangeverMigratie());
        assertEquals(migratieHistorie.getBuitenlandsAdresRegel1(), persoon.getBuitenlandsAdresRegel1Migratie());
        assertEquals(migratieHistorie.getBuitenlandsAdresRegel2(), persoon.getBuitenlandsAdresRegel2Migratie());
        assertEquals(migratieHistorie.getBuitenlandsAdresRegel3(), persoon.getBuitenlandsAdresRegel3Migratie());
        assertEquals(migratieHistorie.getBuitenlandsAdresRegel4(), persoon.getBuitenlandsAdresRegel4Migratie());
        assertEquals(migratieHistorie.getBuitenlandsAdresRegel5(), persoon.getBuitenlandsAdresRegel5Migratie());
        assertEquals(migratieHistorie.getBuitenlandsAdresRegel6(), persoon.getBuitenlandsAdresRegel6Migratie());
        assertEquals(migratieHistorie.getLandOfGebied(), persoon.getLandOfGebiedMigratie());
        assertEquals(migratieHistorie.getRedenWijzigingMigratie(), persoon.getRedenWijzigingMigratie());
        assertEquals(migratieHistorie.getSoortMigratie(), persoon.getSoortMigratie());

        // Geboorte
        assertEquals(geboorteHistorie.getBuitenlandsePlaatsGeboorte(), persoon.getBuitenlandsePlaatsGeboorte());
        assertEquals(geboorteHistorie.getBuitenlandseRegioGeboorte(), persoon.getBuitenlandseRegioGeboorte());
        assertEquals(Integer.valueOf(geboorteHistorie.getDatumGeboorte()), persoon.getDatumGeboorte());
        assertEquals(geboorteHistorie.getOmschrijvingGeboortelocatie(), persoon.getOmschrijvingGeboortelocatie());
        assertEquals(geboorteHistorie.getLandOfGebied(), persoon.getLandOfGebiedGeboorte());
        assertEquals(geboorteHistorie.getGemeente(), persoon.getGemeenteGeboorte());
        assertEquals(geboorteHistorie.getWoonplaatsnaamGeboorte(), persoon.getWoonplaatsGeboorte());
    }

    @Test
    public void testPartijALaagVullen() {
        final String naam = "partij";
        final Partij partij = new Partij(naam, "000001");
        final Integer datumIngang = 20150101;
        final Integer datumOvergangNaarBrp = 20150101;
        final Integer datumEinde = 20151231;
        final String oin = "oin";
        final boolean indicatieVerstrekkingsbeperkingMogelijk = false;
        final boolean indicatieAutomatischFiatteren = false;
        final PartijHistorie partijHistorie = new PartijHistorie(partij, DATUM_TIJDSTIP, datumIngang, indicatieVerstrekkingsbeperkingMogelijk, naam);
        partijHistorie.setDatumEinde(datumEinde);
        partijHistorie.setOin(oin);
        partijHistorie.setDatumOvergangNaarBrp(datumOvergangNaarBrp);

        final PartijHistorie partijHistorie_vervallen = new PartijHistorie(partij, DATUM_TIJDSTIP, 20160101, true, "jitrap");
        partijHistorie_vervallen.setDatumTijdVerval(DATUM_TIJDSTIP);
        partijHistorie_vervallen.setDatumOvergangNaarBrp(20160101);
        partij.addHisPartij(partijHistorie);
        partij.addHisPartij(partijHistorie_vervallen);

        final PartijBijhoudingHistorie partijBijhoudingHistorie =
                new PartijBijhoudingHistorie(partij, DATUM_TIJDSTIP, indicatieAutomatischFiatteren);

        final PartijBijhoudingHistorie partijBijhoudingHistorie_vervallen = new PartijBijhoudingHistorie(partij, DATUM_TIJDSTIP, true);
        partijBijhoudingHistorie_vervallen.setDatumTijdVerval(DATUM_TIJDSTIP);

        partij.addPartijBijhoudingHistorie(partijBijhoudingHistorie);
        partij.addPartijBijhoudingHistorie(partijBijhoudingHistorie_vervallen);

        ALaagAfleidingsUtil.vulALaag(partij);
        assertEquals(datumIngang, partij.getDatumIngang());
        assertEquals(datumEinde, partij.getDatumEinde());
        assertEquals(oin, partij.getOin());

        assertEquals(datumOvergangNaarBrp, partij.getDatumOvergangNaarBrp());
        assertEquals(indicatieAutomatischFiatteren, partij.getIndicatieAutomatischFiatteren());
    }

    @Test
    public void testPartijALaagVullenPartijVervallen() {
        final String naam = "partij";
        final Partij partij = new Partij(naam, "000001");
        final Integer datumOvergangNaarBrp = 20150101;
        final boolean indicatieVerstrekkingsbeperkingMogelijk = false;
        final boolean indicatieAutomatischFiatteren = false;

        final PartijHistorie partijHistorie_vervallen =
                new PartijHistorie(partij, DATUM_TIJDSTIP, 20150101, indicatieVerstrekkingsbeperkingMogelijk, naam);
        partijHistorie_vervallen.setDatumTijdVerval(DATUM_TIJDSTIP);
        partij.addHisPartij(partijHistorie_vervallen);

        final PartijBijhoudingHistorie partijBijhoudingHistorie_vervallen =
                new PartijBijhoudingHistorie(partij, DATUM_TIJDSTIP, indicatieAutomatischFiatteren);
        partijBijhoudingHistorie_vervallen.setDatumTijdVerval(DATUM_TIJDSTIP);

        partij.addPartijBijhoudingHistorie(partijBijhoudingHistorie_vervallen);

        ALaagAfleidingsUtil.vulALaag(partij);
        assertNull(partij.getDatumIngang());
        assertNull(partij.getDatumEinde());
        assertNull(partij.getOin());

        assertNull(partij.getDatumOvergangNaarBrp());
        assertNull(partij.getIndicatieAutomatischFiatteren());
    }

    @Test
    public void testPartijRolALaagVullen() {
        final Partij partij = new Partij("naam", "000001");
        final Integer datumIngang = 20150101;
        final Integer datumEinde = 20151231;
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);
        final PartijRolHistorie historie = new PartijRolHistorie(partijRol, DATUM_TIJDSTIP, datumIngang);
        historie.setDatumEinde(datumEinde);

        final PartijRolHistorie historie_vervallen = new PartijRolHistorie(partijRol, DATUM_TIJDSTIP, 20160101);
        historie_vervallen.setDatumTijdVerval(DATUM_TIJDSTIP);

        partijRol.addPartijBijhoudingHistorie(historie);
        partijRol.addPartijBijhoudingHistorie(historie_vervallen);

        ALaagAfleidingsUtil.vulALaag(partijRol);
        assertEquals(datumIngang, partijRol.getDatumIngang());
        assertEquals(datumEinde, partijRol.getDatumEinde());
    }

    @Test
    public void testPartijRolALaagVullenVervallenHistorie() {
        final Partij partij = new Partij("naam", "000001");
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);

        final PartijRolHistorie historie_vervallen = new PartijRolHistorie(partijRol, DATUM_TIJDSTIP, 20160101);
        historie_vervallen.setDatumTijdVerval(DATUM_TIJDSTIP);

        partijRol.addPartijBijhoudingHistorie(historie_vervallen);

        ALaagAfleidingsUtil.vulALaag(partijRol);
        assertNull(partijRol.getDatumIngang());
        assertNull(partijRol.getDatumEinde());
    }

    @Test
    public void testToegangsLeveringsAutorisatieALaagVullen() {
        final String afleverpunt = "afleverpunt";
        final Integer datumIngang = 20150101;
        final Integer datumEinde = 20151231;
        final String naderePopulatieBeperking = "naderePopulatieBeperking";
        final String naam = "naam";
        final Partij partij = new Partij(naam, "000001");
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.GBA, true);
        final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);

        final ToegangLeveringsautorisatieHistorie historie =
                new ToegangLeveringsautorisatieHistorie(toegangLeveringsAutorisatie, DATUM_TIJDSTIP, datumIngang);
        historie.setAfleverpunt(afleverpunt);
        historie.setDatumEinde(datumEinde);
        historie.setIndicatieGeblokkeerd(true);
        historie.setNaderePopulatiebeperking(naderePopulatieBeperking);

        final ToegangLeveringsautorisatieHistorie historie_vervallen =
                new ToegangLeveringsautorisatieHistorie(toegangLeveringsAutorisatie, DATUM_TIJDSTIP, 20160101);
        historie_vervallen.setDatumTijdVerval(DATUM_TIJDSTIP);

        toegangLeveringsAutorisatie.addToegangLeveringsautorisatieHistorieSet(historie);
        toegangLeveringsAutorisatie.addToegangLeveringsautorisatieHistorieSet(historie_vervallen);

        final Protocolleringsniveau geenBeperkingen = Protocolleringsniveau.GEEN_BEPERKINGEN;
        final LeveringsautorisatieHistorie leveringsautorisatieHistorie =
                new LeveringsautorisatieHistorie(leveringsautorisatie, DATUM_TIJDSTIP, naam, geenBeperkingen, true, datumIngang);
        leveringsautorisatie.addLeveringsautorisatieHistorieSet(leveringsautorisatieHistorie);

        ALaagAfleidingsUtil.vulALaag(toegangLeveringsAutorisatie);
        assertEquals(afleverpunt, toegangLeveringsAutorisatie.getAfleverpunt());
        assertEquals(datumEinde, toegangLeveringsAutorisatie.getDatumEinde());
        assertEquals(datumIngang, toegangLeveringsAutorisatie.getDatumIngang());
        assertTrue(toegangLeveringsAutorisatie.getIndicatieGeblokkeerd());
        assertEquals(naderePopulatieBeperking, toegangLeveringsAutorisatie.getNaderePopulatiebeperking());
    }

    @Test
    public void testLeveringautorisatieALaagVullen() {
        final Integer datumIngang = 20150101;
        final Integer datumEinde = 20151231;
        final String naam = "naam";
        final String populatiebeperking = "populatiebeperking";
        final String toelichting = "toelichting";
        final Partij partij = new Partij(naam, "000001");
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.GBA, true);

        final Protocolleringsniveau geenBeperkingen = Protocolleringsniveau.GEEN_BEPERKINGEN;
        final LeveringsautorisatieHistorie leveringsautorisatieHistorie =
                new LeveringsautorisatieHistorie(leveringsautorisatie, DATUM_TIJDSTIP, naam, geenBeperkingen, true, datumIngang);

        leveringsautorisatieHistorie.setDatumEinde(datumEinde);
        leveringsautorisatieHistorie.setIndicatieGeblokkeerd(true);
        leveringsautorisatieHistorie.setPopulatiebeperking(populatiebeperking);
        leveringsautorisatieHistorie.setToelichting(toelichting);

        final LeveringsautorisatieHistorie leveringsautorisatieHistorie_vervallen =
                new LeveringsautorisatieHistorie(leveringsautorisatie, DATUM_TIJDSTIP, naam, geenBeperkingen, false, 20160101);
        leveringsautorisatieHistorie_vervallen.setDatumTijdVerval(DATUM_TIJDSTIP);

        leveringsautorisatie.addLeveringsautorisatieHistorieSet(leveringsautorisatieHistorie);
        leveringsautorisatie.addLeveringsautorisatieHistorieSet(leveringsautorisatieHistorie_vervallen);

        ALaagAfleidingsUtil.vulALaag(leveringsautorisatie);
        assertEquals(datumEinde, leveringsautorisatie.getDatumEinde());
        assertEquals(datumIngang, leveringsautorisatie.getDatumIngang());
        assertTrue(leveringsautorisatie.getIndicatieAliasSoortAdministratieveHandelingLeveren());
        assertTrue(leveringsautorisatie.getIndicatieGeblokkeerd());
        assertEquals(naam, leveringsautorisatie.getNaam());
        assertEquals(populatiebeperking, leveringsautorisatie.getPopulatiebeperking());
        assertEquals(geenBeperkingen, leveringsautorisatie.getProtocolleringsniveau());
        assertEquals(toelichting, leveringsautorisatie.getToelichting());
    }

    @Test
    public void testToegangsLeveringsAutorisatieALaagVullenAlleenVervallenHistorie() {
        final Partij partij = new Partij("naam", "000001");
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);
        final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie =
                new ToegangLeveringsAutorisatie(partijRol, new Leveringsautorisatie(Stelsel.GBA, true));

        final ToegangLeveringsautorisatieHistorie historie_vervallen =
                new ToegangLeveringsautorisatieHistorie(toegangLeveringsAutorisatie, DATUM_TIJDSTIP, 20160101);
        historie_vervallen.setDatumTijdVerval(DATUM_TIJDSTIP);

        toegangLeveringsAutorisatie.addToegangLeveringsautorisatieHistorieSet(historie_vervallen);

        ALaagAfleidingsUtil.vulALaag(toegangLeveringsAutorisatie);
        assertNull(toegangLeveringsAutorisatie.getAfleverpunt());
        assertNull(toegangLeveringsAutorisatie.getDatumEinde());
        assertNull(toegangLeveringsAutorisatie.getDatumIngang());
        assertNull(toegangLeveringsAutorisatie.getIndicatieGeblokkeerd());
        assertNull(toegangLeveringsAutorisatie.getNaderePopulatiebeperking());
    }

    @Test
    public void testDienstbundelALaagVullen() {
        final String naam = "naam";
        final String naderePopulatiebeperking = "naderePopulatiebeperking";
        final String toelichting = "toelichting";
        final int datumIngang = 20150101;
        final Integer datumEinde = 20151231;
        final Partij partij = new Partij(naam, "000001");
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.GBA, true);
        final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        final Dienstbundel dienstbundel = new Dienstbundel(leveringsautorisatie);
        final DienstbundelHistorie dienstbundelHistorie = new DienstbundelHistorie(dienstbundel, DATUM_TIJDSTIP, naam, datumIngang);

        dienstbundelHistorie.setDatumEinde(datumEinde);
        dienstbundelHistorie.setIndicatieGeblokkeerd(true);
        dienstbundelHistorie.setIndicatieNaderePopulatiebeperkingVolledigGeconverteerd(false);
        dienstbundelHistorie.setNaderePopulatiebeperking(naderePopulatiebeperking);
        dienstbundelHistorie.setToelichting(toelichting);

        ALaagAfleidingsUtil.vulALaag(toegangLeveringsAutorisatie);
        assertEquals(datumIngang, dienstbundelHistorie.getDatumIngang());
        assertEquals(datumEinde, dienstbundelHistorie.getDatumEinde());
        assertTrue(dienstbundelHistorie.getIndicatieGeblokkeerd());
        assertFalse(dienstbundelHistorie.getIndicatieNaderePopulatiebeperkingVolledigGeconverteerd());
        assertEquals(naderePopulatiebeperking, dienstbundelHistorie.getNaderePopulatiebeperking());
        assertEquals(toelichting, dienstbundelHistorie.getToelichting());
    }

    @Test
    public void testDienstALaagVullen() {
        final String naam = "naam";
        final String attenderingscriterium = "attenderingscriterium";
        final Integer datumIngang = 20150101;
        final Integer datumEinde = 20151231;
        final Integer eersteSelectieDatum = 20120101;
        final Short selectiePeriodeInMaanden = 5;
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.GBA, true);
        final Dienstbundel dienstbundel = new Dienstbundel(leveringsautorisatie);
        leveringsautorisatie.addDienstbundelSet(dienstbundel);

        final Dienst dienst = new Dienst(dienstbundel, SoortDienst.SYNCHRONISATIE_PERSOON);
        dienstbundel.addDienstSet(dienst);

        final DienstAttenderingHistorie attenderingHistorie = new DienstAttenderingHistorie(dienst);
        attenderingHistorie.setAttenderingscriterium(attenderingscriterium);

        final DienstSelectieHistorie selectieHistorie = new DienstSelectieHistorie(dienst);
        selectieHistorie.setEersteSelectieDatum(eersteSelectieDatum);

        final DienstHistorie dienstHistorie = new DienstHistorie(dienst, DATUM_TIJDSTIP, datumIngang);
        dienstHistorie.setDatumEinde(datumEinde);
        dienstHistorie.setIndicatieGeblokkeerd(true);

        dienst.setDienstAttenderingHistorieSet(Collections.singleton(attenderingHistorie));
        dienst.setDienstSelectieHistorieSet(Collections.singleton(selectieHistorie));
        dienst.setDienstHistorieSet(Collections.singleton(dienstHistorie));

        ALaagAfleidingsUtil.vulALaag(leveringsautorisatie);
        assertEquals(attenderingscriterium, dienst.getAttenderingscriterium());
        assertEquals(eersteSelectieDatum, dienst.getEersteSelectieDatum());
        assertEquals(datumIngang, dienst.getDatumIngang());
        assertEquals(datumEinde, dienst.getDatumEinde());
        assertTrue(dienst.getIndicatieGeblokkeerd());
    }

    @Test
    public void testDienstALaagVullenVervallenHistorie() {
        final String naam = "naam";
        final String attenderingscriterium = "attenderingscriterium";
        final Integer datumIngang = 20150101;
        final Integer datumEinde = 20151231;
        final Integer eersteSelectieDatum = 20120101;
        final Short selectiePeriodeInMaanden = 5;
        final Partij partij = new Partij(naam, "000001");
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.GBA, true);
        final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        final Dienstbundel dienstbundel = new Dienstbundel(leveringsautorisatie);
        leveringsautorisatie.addDienstbundelSet(dienstbundel);

        final Dienst dienst = new Dienst(dienstbundel, SoortDienst.SYNCHRONISATIE_PERSOON);
        dienstbundel.addDienstSet(dienst);

        final DienstAttenderingHistorie attenderingHistorie = new DienstAttenderingHistorie(dienst);
        attenderingHistorie.setAttenderingscriterium(attenderingscriterium);
        attenderingHistorie.setDatumTijdVerval(DATUM_TIJDSTIP);

        final DienstSelectieHistorie selectieHistorie = new DienstSelectieHistorie(dienst);
        selectieHistorie.setEersteSelectieDatum(eersteSelectieDatum);
        selectieHistorie.setDatumTijdVerval(DATUM_TIJDSTIP);

        final DienstHistorie dienstHistorie = new DienstHistorie(dienst, DATUM_TIJDSTIP, datumIngang);
        dienstHistorie.setDatumEinde(datumEinde);
        dienstHistorie.setIndicatieGeblokkeerd(true);
        dienstHistorie.setDatumTijdVerval(DATUM_TIJDSTIP);

        dienst.addDienstAttenderingHistorieSet(attenderingHistorie);
        dienst.addDienstSelectieHistorieSet(selectieHistorie);
        dienst.addDienstHistorieSet(dienstHistorie);

        ALaagAfleidingsUtil.vulALaag(toegangLeveringsAutorisatie);
        assertNull(dienst.getAttenderingscriterium());
        assertNull(dienst.getEersteSelectieDatum());
        assertNull(dienst.getDatumIngang());
        assertNull(dienst.getDatumEinde());
        assertNull(dienst.getIndicatieGeblokkeerd());
    }
}
