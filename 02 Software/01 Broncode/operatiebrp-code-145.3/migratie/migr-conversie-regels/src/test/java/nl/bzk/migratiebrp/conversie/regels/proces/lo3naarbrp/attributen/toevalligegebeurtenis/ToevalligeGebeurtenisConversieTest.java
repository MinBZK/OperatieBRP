/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.toevalligegebeurtenis;

import java.util.Arrays;
import java.util.Collections;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenis;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3ToevalligeGebeurtenis;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OverlijdenInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingNaamgebruikCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortVerbintenis;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Lo3AttribuutConverteerder;
import nl.bzk.migratiebrp.conversie.regels.tabel.ConversietabelFactoryImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class ToevalligeGebeurtenisConversieTest {

    private ToevalligeGebeurtenisConversie subject;

    @Before
    public void setup() {
        final Lo3AttribuutConverteerder lo3AttribuutConverteerder = new Lo3AttribuutConverteerder(new ConversietabelFactoryImpl());
        final ToevalligeGebeurtenisNaamGeslachtConverteerder
                naamGeslachtConverteerder =
                new ToevalligeGebeurtenisNaamGeslachtConverteerder(lo3AttribuutConverteerder);
        final ToevalligeGebeurtenisPersoonConverteerder persoonConverteerder = new ToevalligeGebeurtenisPersoonConverteerder(lo3AttribuutConverteerder);
        final ToevalligeGebeurtenisFamilierechtelijkeBetrekkingConverteerder
                familierechtelijkeBetrekkingConverteerder =
                new ToevalligeGebeurtenisFamilierechtelijkeBetrekkingConverteerder(lo3AttribuutConverteerder, persoonConverteerder);
        final ToevalligeGebeurtenisOverlijdenConverteerder
                overlijdenConverteerder =
                new ToevalligeGebeurtenisOverlijdenConverteerder(lo3AttribuutConverteerder);
        final ToevalligeGebeurtenisVerbintenisConverteerder
                verbintenisConverteerder =
                new ToevalligeGebeurtenisVerbintenisConverteerder(lo3AttribuutConverteerder, persoonConverteerder);
        subject = new ToevalligeGebeurtenisConversie(lo3AttribuutConverteerder, persoonConverteerder, naamGeslachtConverteerder,
                familierechtelijkeBetrekkingConverteerder, overlijdenConverteerder, verbintenisConverteerder);
    }

    @Test
    public void testAkte1C() {
        final Lo3Stapel<Lo3PersoonInhoud> lo3persoon =
                new Lo3Stapel<>(Collections.singletonList(maakPersoon("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M")));
        Lo3Stapel<Lo3OuderInhoud> ouder1 = maakOuder("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M", 20160401);

        Lo3ToevalligeGebeurtenis gebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        new Lo3GemeenteCode("0599"),
                        new Lo3GemeenteCode("0600"),
                        new Lo3String("1 C4567"),
                        lo3persoon,
                        ouder1,
                        null,
                        null,
                        null);
        BrpToevalligeGebeurtenis brpGebeurtenis = subject.converteer(gebeurtenis);
        Assert.assertNotNull("Moet een familierechtelijke betrekking zijn", brpGebeurtenis.getFamilierechtelijkeBetrekking());
        Assert.assertNotNull("Datum moet gevuld zijn", brpGebeurtenis.getDatumAanvang());
        Assert.assertNotNull("DoelPartijCode moet gevuld zijn", brpGebeurtenis.getDoelPartijCode());
        Assert.assertNotNull("Nummer akte moet gevuld zijn", brpGebeurtenis.getNummerAkte());
        Assert.assertNotNull("Persoon moet gevuld zijn", brpGebeurtenis.getPersoon());
        Assert.assertNotNull("Register moet gevuld zijn", brpGebeurtenis.getRegisterGemeente());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAkte1CLeeg() {
        final Lo3Stapel<Lo3PersoonInhoud> lo3persoon =
                new Lo3Stapel<>(Collections.singletonList(maakPersoon("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M")));
        Lo3ToevalligeGebeurtenis gebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        new Lo3GemeenteCode("0599"),
                        new Lo3GemeenteCode("0600"),
                        new Lo3String("1 C4567"),
                        lo3persoon,
                        null,
                        null,
                        null,
                        null);
        subject.converteer(gebeurtenis);
    }

    @Test
    public void testAkte1J() {
        final Lo3Stapel<Lo3PersoonInhoud> lo3persoon =
                new Lo3Stapel<>(Collections.singletonList(maakPersoon("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M")));
        Lo3Stapel<Lo3OuderInhoud> ouder1 = maakOuder("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M", 20160401);

        Lo3ToevalligeGebeurtenis gebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        new Lo3GemeenteCode("0599"),
                        new Lo3GemeenteCode("0600"),
                        new Lo3String("1 J4567"),
                        lo3persoon,
                        ouder1,
                        null,
                        null,
                        null);
        BrpToevalligeGebeurtenis brpGebeurtenis = subject.converteer(gebeurtenis);
        Assert.assertNotNull("Moet een familierechtelijke betrekking zijn", brpGebeurtenis.getFamilierechtelijkeBetrekking());
        Assert.assertNotNull("Datum moet gevuld zijn", brpGebeurtenis.getDatumAanvang());
        Assert.assertNotNull("DoelPartijCode moet gevuld zijn", brpGebeurtenis.getDoelPartijCode());
        Assert.assertNotNull("Nummer akte moet gevuld zijn", brpGebeurtenis.getNummerAkte());
        Assert.assertNotNull("Persoon moet gevuld zijn", brpGebeurtenis.getPersoon());
        Assert.assertNotNull("Register moet gevuld zijn", brpGebeurtenis.getRegisterGemeente());
    }

    @Test
    public void testAkte1E() {
        final Lo3Stapel<Lo3PersoonInhoud> lo3persoon =
                new Lo3Stapel<>(Collections.singletonList(maakPersoon("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M")));
        Lo3Stapel<Lo3OuderInhoud> ouder1 = maakOuder("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M", 20160401);

        Lo3ToevalligeGebeurtenis gebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        new Lo3GemeenteCode("0599"),
                        new Lo3GemeenteCode("0600"),
                        new Lo3String("1 E4567"),
                        lo3persoon,
                        ouder1,
                        null,
                        null,
                        null);
        BrpToevalligeGebeurtenis brpGebeurtenis = subject.converteer(gebeurtenis);
        Assert.assertNotNull("Moet een familierechtelijke betrekking zijn", brpGebeurtenis.getFamilierechtelijkeBetrekking());
        Assert.assertNotNull("Datum moet gevuld zijn", brpGebeurtenis.getDatumAanvang());
        Assert.assertNotNull("DoelPartijCode moet gevuld zijn", brpGebeurtenis.getDoelPartijCode());
        Assert.assertNotNull("Nummer akte moet gevuld zijn", brpGebeurtenis.getNummerAkte());
        Assert.assertNotNull("Persoon moet gevuld zijn", brpGebeurtenis.getPersoon());
        Assert.assertNotNull("Register moet gevuld zijn", brpGebeurtenis.getRegisterGemeente());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAkte1ELeeg() {
        final Lo3Stapel<Lo3PersoonInhoud> lo3persoon =
                new Lo3Stapel<>(Collections.singletonList(maakPersoon("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M")));
        Lo3ToevalligeGebeurtenis gebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        new Lo3GemeenteCode("0599"),
                        new Lo3GemeenteCode("0600"),
                        new Lo3String("1 E4567"),
                        lo3persoon,
                        null,
                        null,
                        null,
                        null);
        subject.converteer(gebeurtenis);
    }

    @Test
    public void testAkte1U() {
        final Lo3Stapel<Lo3PersoonInhoud> lo3persoon =
                new Lo3Stapel<>(Collections.singletonList(maakPersoon("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M")));
        Lo3Stapel<Lo3OuderInhoud> ouder1 = maakOuder("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M", 20160401);

        Lo3ToevalligeGebeurtenis gebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        new Lo3GemeenteCode("0599"),
                        new Lo3GemeenteCode("0600"),
                        new Lo3String("1 U4567"),
                        lo3persoon,
                        ouder1,
                        null,
                        null,
                        null);
        BrpToevalligeGebeurtenis brpGebeurtenis = subject.converteer(gebeurtenis);
        Assert.assertNotNull("Moet een familierechtelijke betrekking zijn", brpGebeurtenis.getFamilierechtelijkeBetrekking());
        Assert.assertNotNull("Datum moet gevuld zijn", brpGebeurtenis.getDatumAanvang());
        Assert.assertNotNull("DoelPartijCode moet gevuld zijn", brpGebeurtenis.getDoelPartijCode());
        Assert.assertNotNull("Nummer akte moet gevuld zijn", brpGebeurtenis.getNummerAkte());
        Assert.assertNotNull("Persoon moet gevuld zijn", brpGebeurtenis.getPersoon());
        Assert.assertNotNull("Register moet gevuld zijn", brpGebeurtenis.getRegisterGemeente());
    }

    @Test
    public void testAkte1N() {
        final Lo3Stapel<Lo3PersoonInhoud> lo3persoon =
                new Lo3Stapel<>(Collections.singletonList(maakPersoon("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M")));
        Lo3Stapel<Lo3OuderInhoud> ouder1 = maakOuder("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M", 20160401);

        Lo3ToevalligeGebeurtenis gebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        new Lo3GemeenteCode("0599"),
                        new Lo3GemeenteCode("0600"),
                        new Lo3String("1 N4567"),
                        lo3persoon,
                        ouder1,
                        null,
                        null,
                        null);
        BrpToevalligeGebeurtenis brpGebeurtenis = subject.converteer(gebeurtenis);
        Assert.assertNotNull("Moet een familierechtelijke betrekking zijn", brpGebeurtenis.getFamilierechtelijkeBetrekking());
        Assert.assertNotNull("Datum moet gevuld zijn", brpGebeurtenis.getDatumAanvang());
        Assert.assertNotNull("DoelPartijCode moet gevuld zijn", brpGebeurtenis.getDoelPartijCode());
        Assert.assertNotNull("Nummer akte moet gevuld zijn", brpGebeurtenis.getNummerAkte());
        Assert.assertNotNull("Persoon moet gevuld zijn", brpGebeurtenis.getPersoon());
        Assert.assertNotNull("Register moet gevuld zijn", brpGebeurtenis.getRegisterGemeente());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAkte1NLeeg() {
        final Lo3Stapel<Lo3PersoonInhoud> lo3persoon =
                new Lo3Stapel<>(Collections.singletonList(maakPersoon("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M")));
        Lo3ToevalligeGebeurtenis gebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        new Lo3GemeenteCode("0599"),
                        new Lo3GemeenteCode("0600"),
                        new Lo3String("1 N4567"),
                        lo3persoon,
                        null,
                        null,
                        null,
                        null);
        subject.converteer(gebeurtenis);
    }

    @Test
    public void testAkte1Q() {
        final Lo3Stapel<Lo3PersoonInhoud> lo3persoon =
                new Lo3Stapel<>(Collections.singletonList(maakPersoon("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M")));
        Lo3Stapel<Lo3OuderInhoud> ouder1 = maakOuder("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M", 20160401);

        Lo3ToevalligeGebeurtenis gebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        new Lo3GemeenteCode("0599"),
                        new Lo3GemeenteCode("0600"),
                        new Lo3String("1 Q4567"),
                        lo3persoon,
                        ouder1,
                        ouder1,
                        null,
                        null);
        BrpToevalligeGebeurtenis brpGebeurtenis = subject.converteer(gebeurtenis);
        Assert.assertNotNull("Moet een familierechtelijke betrekking zijn", brpGebeurtenis.getFamilierechtelijkeBetrekking());
        Assert.assertNotNull("Datum moet gevuld zijn", brpGebeurtenis.getDatumAanvang());
        Assert.assertNotNull("DoelPartijCode moet gevuld zijn", brpGebeurtenis.getDoelPartijCode());
        Assert.assertNotNull("Nummer akte moet gevuld zijn", brpGebeurtenis.getNummerAkte());
        Assert.assertNotNull("Persoon moet gevuld zijn", brpGebeurtenis.getPersoon());
        Assert.assertNotNull("Register moet gevuld zijn", brpGebeurtenis.getRegisterGemeente());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAkte1QLeeg() {
        final Lo3Stapel<Lo3PersoonInhoud> lo3persoon =
                new Lo3Stapel<>(Collections.singletonList(maakPersoon("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M")));
        Lo3ToevalligeGebeurtenis gebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        new Lo3GemeenteCode("0599"),
                        new Lo3GemeenteCode("0600"),
                        new Lo3String("1 Q4567"),
                        lo3persoon,
                        null,
                        null,
                        null,
                        null);
        subject.converteer(gebeurtenis);
    }

    @Test
    public void testAkte1H() {
        final Lo3Stapel<Lo3PersoonInhoud> lo3persoon =
                new Lo3Stapel<>(
                        Arrays.asList(
                                maakPersoon("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M"),
                                maakPersoon("100", "100", "anderevoornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M")));

        Lo3ToevalligeGebeurtenis gebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        new Lo3GemeenteCode("0599"),
                        new Lo3GemeenteCode("0600"),
                        new Lo3String("1 H4567"),
                        lo3persoon,
                        null,
                        null,
                        null,
                        null);
        BrpToevalligeGebeurtenis brpGebeurtenis = subject.converteer(gebeurtenis);
        Assert.assertNull("Moet geen familierechtelijke betrekking zijn", brpGebeurtenis.getFamilierechtelijkeBetrekking());
        Assert.assertNotNull("Datum moet gevuld zijn", brpGebeurtenis.getDatumAanvang());
        Assert.assertNotNull("DoelPartijCode moet gevuld zijn", brpGebeurtenis.getDoelPartijCode());
        Assert.assertNotNull("Nummer akte moet gevuld zijn", brpGebeurtenis.getNummerAkte());
        Assert.assertNotNull("Persoon moet gevuld zijn", brpGebeurtenis.getPersoon());
        Assert.assertNotNull("Register moet gevuld zijn", brpGebeurtenis.getRegisterGemeente());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAkte1HLeeg() {
        final Lo3Stapel<Lo3PersoonInhoud> lo3persoon =
                new Lo3Stapel<>(Collections.singletonList(maakPersoon("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M")));
        Lo3ToevalligeGebeurtenis gebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        new Lo3GemeenteCode("0599"),
                        new Lo3GemeenteCode("0600"),
                        new Lo3String("1 H4567"),
                        lo3persoon,
                        null,
                        null,
                        null,
                        null);
        subject.converteer(gebeurtenis);
    }

    @Test
    public void testAkte1M() {
        final Lo3Stapel<Lo3PersoonInhoud> lo3persoon =
                new Lo3Stapel<>(
                        Arrays.asList(
                                maakPersoon("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M"),
                                maakPersoon("100", "100", "voornaam", "G", "voor de", "andereachternaam", 19700101, "0599", "6030", "M")));

        Lo3ToevalligeGebeurtenis gebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        new Lo3GemeenteCode("0599"),
                        new Lo3GemeenteCode("0600"),
                        new Lo3String("1 M4567"),
                        lo3persoon,
                        null,
                        null,
                        null,
                        null);
        BrpToevalligeGebeurtenis brpGebeurtenis = subject.converteer(gebeurtenis);
        Assert.assertNull("Moet geen familierechtelijke betrekking zijn", brpGebeurtenis.getFamilierechtelijkeBetrekking());
        Assert.assertNotNull("Datum moet gevuld zijn", brpGebeurtenis.getDatumAanvang());
        Assert.assertNotNull("DoelPartijCode moet gevuld zijn", brpGebeurtenis.getDoelPartijCode());
        Assert.assertNotNull("Nummer akte moet gevuld zijn", brpGebeurtenis.getNummerAkte());
        Assert.assertNotNull("Persoon moet gevuld zijn", brpGebeurtenis.getPersoon());
        Assert.assertNotNull("Register moet gevuld zijn", brpGebeurtenis.getRegisterGemeente());
    }

    @Test
    public void testAkte1S() {
        final Lo3Stapel<Lo3PersoonInhoud> lo3persoon =
                new Lo3Stapel<>(
                        Arrays.asList(
                                maakPersoon("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M"),
                                maakPersoon("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "V")));

        Lo3ToevalligeGebeurtenis gebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        new Lo3GemeenteCode("0599"),
                        new Lo3GemeenteCode("0600"),
                        new Lo3String("1 S4567"),
                        lo3persoon,
                        null,
                        null,
                        null,
                        null);
        BrpToevalligeGebeurtenis brpGebeurtenis = subject.converteer(gebeurtenis);
        Assert.assertNull("Moet geen familierechtelijke betrekking zijn", brpGebeurtenis.getFamilierechtelijkeBetrekking());
        Assert.assertNotNull("Datum moet gevuld zijn", brpGebeurtenis.getDatumAanvang());
        Assert.assertNotNull("DoelPartijCode moet gevuld zijn", brpGebeurtenis.getDoelPartijCode());
        Assert.assertNotNull("Nummer akte moet gevuld zijn", brpGebeurtenis.getNummerAkte());
        Assert.assertNotNull("Persoon moet gevuld zijn", brpGebeurtenis.getPersoon());
        Assert.assertNotNull("Register moet gevuld zijn", brpGebeurtenis.getRegisterGemeente());
    }

    @Test
    public void testAkte2A() {
        final Lo3Stapel<Lo3PersoonInhoud> lo3persoon =
                new Lo3Stapel<>(Collections.singletonList(maakPersoon("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M")));
        final Lo3OverlijdenInhoud inhoud = new Lo3OverlijdenInhoud(new Lo3Datum(20160401), new Lo3GemeenteCode("0599"), new Lo3LandCode("6030"));
        final Lo3Categorie<Lo3OverlijdenInhoud> overlijden =
                new Lo3Categorie<>(
                        inhoud,
                        Lo3Documentatie.build(null, null, null, null, null, null, null),
                        new Lo3Historie(null, new Lo3Datum(20160401), null),
                        null);
        Lo3ToevalligeGebeurtenis gebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        new Lo3GemeenteCode("0599"),
                        new Lo3GemeenteCode("0600"),
                        new Lo3String("2 A4567"),
                        lo3persoon,
                        null,
                        null,
                        null,
                        overlijden);
        BrpToevalligeGebeurtenis brpGebeurtenis = subject.converteer(gebeurtenis);
        Assert.assertNotNull("Moet een overlijden zijn", brpGebeurtenis.getOverlijden());
        Assert.assertNotNull("Datum moet gevuld zijn", brpGebeurtenis.getDatumAanvang());
        Assert.assertNotNull("DoelPartijCode moet gevuld zijn", brpGebeurtenis.getDoelPartijCode());
        Assert.assertNotNull("Nummer akte moet gevuld zijn", brpGebeurtenis.getNummerAkte());
        Assert.assertNotNull("Persoon moet gevuld zijn", brpGebeurtenis.getPersoon());
        Assert.assertNotNull("Register moet gevuld zijn", brpGebeurtenis.getRegisterGemeente());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAkte2ALeeg() {
        final Lo3Stapel<Lo3PersoonInhoud> lo3persoon =
                new Lo3Stapel<>(Collections.singletonList(maakPersoon("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M")));
        Lo3ToevalligeGebeurtenis gebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        new Lo3GemeenteCode("0599"),
                        new Lo3GemeenteCode("0600"),
                        new Lo3String("2 A4567"),
                        lo3persoon,
                        null,
                        null,
                        null,
                        null);
        subject.converteer(gebeurtenis);
    }

    @Test
    public void testAkte2G() {
        final Lo3Stapel<Lo3PersoonInhoud> lo3persoon =
                new Lo3Stapel<>(Collections.singletonList(maakPersoon("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M")));
        final Lo3OverlijdenInhoud inhoud = new Lo3OverlijdenInhoud(new Lo3Datum(20160401), new Lo3GemeenteCode("0599"), new Lo3LandCode("6030"));
        final Lo3Categorie<Lo3OverlijdenInhoud> overlijden =
                new Lo3Categorie<>(
                        inhoud,
                        Lo3Documentatie.build(null, null, null, null, null, null, null),
                        new Lo3Historie(null, new Lo3Datum(20160401), null),
                        null);
        Lo3ToevalligeGebeurtenis gebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        new Lo3GemeenteCode("0599"),
                        new Lo3GemeenteCode("0600"),
                        new Lo3String("2 G4567"),
                        lo3persoon,
                        null,
                        null,
                        null,
                        overlijden);
        BrpToevalligeGebeurtenis brpGebeurtenis = subject.converteer(gebeurtenis);
        Assert.assertNotNull("Moet een overlijden zijn", brpGebeurtenis.getOverlijden());
        Assert.assertNotNull("Datum moet gevuld zijn", brpGebeurtenis.getDatumAanvang());
        Assert.assertNotNull("DoelPartijCode moet gevuld zijn", brpGebeurtenis.getDoelPartijCode());
        Assert.assertNotNull("Nummer akte moet gevuld zijn", brpGebeurtenis.getNummerAkte());
        Assert.assertNotNull("Persoon moet gevuld zijn", brpGebeurtenis.getPersoon());
        Assert.assertNotNull("Register moet gevuld zijn", brpGebeurtenis.getRegisterGemeente());
    }

    @Test
    public void testAkte3A() {
        final Lo3Stapel<Lo3PersoonInhoud> lo3persoon =
                new Lo3Stapel<>(Collections.singletonList(maakPersoon("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M")));
        Lo3HuwelijkOfGpInhoud sluiting =
                maakHuwelijk(
                        "100",
                        "100",
                        "voornaam",
                        "G",
                        "voor de",
                        "achternaam",
                        19700101,
                        "0599",
                        "6030",
                        "M",
                        19990101,
                        "0600",
                        "6030",
                        null,
                        null,
                        null,
                        null,
                        "H");
        Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorie =
                new Lo3Categorie<>(
                        sluiting,
                        Lo3Documentatie.build(null, null, null, null, null, null, null),
                        new Lo3Historie(null, new Lo3Datum(20160401), null),
                        null);
        Lo3Stapel<Lo3HuwelijkOfGpInhoud> verbintenis = new Lo3Stapel<>(Collections.singletonList(categorie));
        Lo3ToevalligeGebeurtenis gebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        new Lo3GemeenteCode("0599"),
                        new Lo3GemeenteCode("0600"),
                        new Lo3String("3 A4567"),
                        lo3persoon,
                        null,
                        null,
                        verbintenis,
                        null);
        BrpToevalligeGebeurtenis brpGebeurtenis = subject.converteer(gebeurtenis);
        Assert.assertNotNull("Moet verbintenis zijn", brpGebeurtenis.getVerbintenis());
        Assert.assertNotNull("Datum moet gevuld zijn", brpGebeurtenis.getDatumAanvang());
        Assert.assertNotNull("DoelPartijCode moet gevuld zijn", brpGebeurtenis.getDoelPartijCode());
        Assert.assertNotNull("Nummer akte moet gevuld zijn", brpGebeurtenis.getNummerAkte());
        Assert.assertNotNull("Persoon moet gevuld zijn", brpGebeurtenis.getPersoon());
        Assert.assertNotNull("Register moet gevuld zijn", brpGebeurtenis.getRegisterGemeente());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAkte3ALeeg() {
        final Lo3Stapel<Lo3PersoonInhoud> lo3persoon =
                new Lo3Stapel<>(Collections.singletonList(maakPersoon("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M")));
        Lo3ToevalligeGebeurtenis gebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        new Lo3GemeenteCode("0599"),
                        new Lo3GemeenteCode("0600"),
                        new Lo3String("3 A4567"),
                        lo3persoon,
                        null,
                        null,
                        null,
                        null);
        subject.converteer(gebeurtenis);
    }

    @Test
    public void testAkte5A() {
        final Lo3Stapel<Lo3PersoonInhoud> lo3persoon =
                new Lo3Stapel<>(Collections.singletonList(maakPersoon("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M")));
        Lo3HuwelijkOfGpInhoud sluiting =
                maakHuwelijk(
                        "100",
                        "100",
                        "voornaam",
                        "G",
                        "voor de",
                        "achternaam",
                        19700101,
                        "0599",
                        "6030",
                        "M",
                        19990101,
                        "0600",
                        "6030",
                        null,
                        null,
                        null,
                        null,
                        "H");
        Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorie =
                new Lo3Categorie<>(
                        sluiting,
                        Lo3Documentatie.build(null, null, null, null, null, null, null),
                        new Lo3Historie(null, new Lo3Datum(20160401), null),
                        null);
        Lo3Stapel<Lo3HuwelijkOfGpInhoud> verbintenis = new Lo3Stapel<>(Collections.singletonList(categorie));
        Lo3ToevalligeGebeurtenis gebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        new Lo3GemeenteCode("0599"),
                        new Lo3GemeenteCode("0600"),
                        new Lo3String("5 A4567"),
                        lo3persoon,
                        null,
                        null,
                        verbintenis,
                        null);
        BrpToevalligeGebeurtenis brpGebeurtenis = subject.converteer(gebeurtenis);
        Assert.assertNotNull("Moet verbintenis zijn", brpGebeurtenis.getVerbintenis());
        Assert.assertNotNull("Datum moet gevuld zijn", brpGebeurtenis.getDatumAanvang());
        Assert.assertNotNull("DoelPartijCode moet gevuld zijn", brpGebeurtenis.getDoelPartijCode());
        Assert.assertNotNull("Nummer akte moet gevuld zijn", brpGebeurtenis.getNummerAkte());
        Assert.assertNotNull("Persoon moet gevuld zijn", brpGebeurtenis.getPersoon());
        Assert.assertNotNull("Register moet gevuld zijn", brpGebeurtenis.getRegisterGemeente());
    }

    @Test
    public void testAkte3B() {
        final Lo3Stapel<Lo3PersoonInhoud> lo3persoon =
                new Lo3Stapel<>(Collections.singletonList(maakPersoon("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M")));
        Lo3HuwelijkOfGpInhoud sluiting =
                maakHuwelijk(
                        "100",
                        "100",
                        "voornaam",
                        "G",
                        "voor de",
                        "achternaam",
                        19700101,
                        "0599",
                        "6030",
                        "M",
                        19990101,
                        "0600",
                        "6030",
                        null,
                        null,
                        null,
                        null,
                        "H");
        Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorieSluiting = new Lo3Categorie<>(sluiting, null, new Lo3Historie(null, null, null), null);
        Lo3HuwelijkOfGpInhoud ontbinding =
                maakHuwelijk(
                        "100",
                        "100",
                        "voornaam",
                        "G",
                        "voor de",
                        "achternaam",
                        19700101,
                        "0599",
                        "6030",
                        "M",
                        19990101,
                        "0600",
                        "6030",
                        20000101,
                        "0600",
                        "6030",
                        "S",
                        "H");
        Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorieOntbinding =
                new Lo3Categorie<>(
                        ontbinding,
                        Lo3Documentatie.build(null, null, null, null, null, null, null),
                        new Lo3Historie(null, new Lo3Datum(20160401), null),
                        null);
        Lo3Stapel<Lo3HuwelijkOfGpInhoud> verbintenis = new Lo3Stapel<>(Arrays.asList(categorieOntbinding, categorieSluiting));
        Lo3ToevalligeGebeurtenis gebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        new Lo3GemeenteCode("0599"),
                        new Lo3GemeenteCode("0600"),
                        new Lo3String("3 B4567"),
                        lo3persoon,
                        null,
                        null,
                        verbintenis,
                        null);
        BrpToevalligeGebeurtenis brpGebeurtenis = subject.converteer(gebeurtenis);
        Assert.assertNotNull("Moet verbintenis zijn", brpGebeurtenis.getVerbintenis());
        Assert.assertNotNull("Datum moet gevuld zijn", brpGebeurtenis.getDatumAanvang());
        Assert.assertNotNull("DoelPartijCode moet gevuld zijn", brpGebeurtenis.getDoelPartijCode());
        Assert.assertNotNull("Nummer akte moet gevuld zijn", brpGebeurtenis.getNummerAkte());
        Assert.assertNotNull("Persoon moet gevuld zijn", brpGebeurtenis.getPersoon());
        Assert.assertNotNull("Register moet gevuld zijn", brpGebeurtenis.getRegisterGemeente());
    }

    @Test
    public void testAkte5B() {
        final Lo3Stapel<Lo3PersoonInhoud> lo3persoon =
                new Lo3Stapel<>(Collections.singletonList(maakPersoon("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M")));
        Lo3HuwelijkOfGpInhoud sluiting =
                maakHuwelijk(
                        "100",
                        "100",
                        "voornaam",
                        "G",
                        "voor de",
                        "achternaam",
                        19700101,
                        "0599",
                        "6030",
                        "M",
                        19990101,
                        "0600",
                        "6030",
                        null,
                        null,
                        null,
                        null,
                        "H");
        Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorieSluiting = new Lo3Categorie<>(sluiting, null, new Lo3Historie(null, null, null), null);
        Lo3HuwelijkOfGpInhoud ontbinding =
                maakHuwelijk(
                        "100",
                        "100",
                        "voornaam",
                        "G",
                        "voor de",
                        "achternaam",
                        19700101,
                        "0599",
                        "6030",
                        "M",
                        19990101,
                        "0600",
                        "6030",
                        20000101,
                        "0600",
                        "6030",
                        "S",
                        "H");
        Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorieOntbinding =
                new Lo3Categorie<>(
                        ontbinding,
                        Lo3Documentatie.build(null, null, null, null, null, null, null),
                        new Lo3Historie(null, new Lo3Datum(20160401), null),
                        null);
        Lo3Stapel<Lo3HuwelijkOfGpInhoud> verbintenis = new Lo3Stapel<>(Arrays.asList(categorieOntbinding, categorieSluiting));
        Lo3ToevalligeGebeurtenis gebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        new Lo3GemeenteCode("0599"),
                        new Lo3GemeenteCode("0600"),
                        new Lo3String("5 B4567"),
                        lo3persoon,
                        null,
                        null,
                        verbintenis,
                        null);
        BrpToevalligeGebeurtenis brpGebeurtenis = subject.converteer(gebeurtenis);
        Assert.assertNotNull("Moet verbintenis zijn", brpGebeurtenis.getVerbintenis());
        Assert.assertNotNull("Datum moet gevuld zijn", brpGebeurtenis.getDatumAanvang());
        Assert.assertNotNull("DoelPartijCode moet gevuld zijn", brpGebeurtenis.getDoelPartijCode());
        Assert.assertNotNull("Nummer akte moet gevuld zijn", brpGebeurtenis.getNummerAkte());
        Assert.assertNotNull("Persoon moet gevuld zijn", brpGebeurtenis.getPersoon());
        Assert.assertNotNull("Register moet gevuld zijn", brpGebeurtenis.getRegisterGemeente());
    }

    @Test
    public void testAkte3H() {
        final Lo3Stapel<Lo3PersoonInhoud> lo3persoon =
                new Lo3Stapel<>(Collections.singletonList(maakPersoon("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M")));
        Lo3HuwelijkOfGpInhoud sluiting =
                maakHuwelijk(
                        "100",
                        "100",
                        "voornaam",
                        "G",
                        "voor de",
                        "achternaam",
                        19700101,
                        "0599",
                        "6030",
                        "M",
                        19990101,
                        "0600",
                        "6030",
                        null,
                        null,
                        null,
                        null,
                        "P");
        Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorieSluiting = new Lo3Categorie<>(sluiting, null, new Lo3Historie(null, null, null), null);
        Lo3HuwelijkOfGpInhoud omzetting =
                maakHuwelijk(
                        "100",
                        "100",
                        "voornaam",
                        "G",
                        "voor de",
                        "achternaam",
                        19700101,
                        "0599",
                        "6030",
                        "M",
                        20000101,
                        "0600",
                        "6030",
                        null,
                        null,
                        null,
                        null,
                        "H");
        Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorieOmzetting =
                new Lo3Categorie<>(
                        omzetting,
                        Lo3Documentatie.build(null, null, null, null, null, null, null),
                        new Lo3Historie(null, new Lo3Datum(20160401), null),
                        null);
        Lo3Stapel<Lo3HuwelijkOfGpInhoud> verbintenis = new Lo3Stapel<>(Arrays.asList(categorieOmzetting, categorieSluiting));
        Lo3ToevalligeGebeurtenis gebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        new Lo3GemeenteCode("0599"),
                        new Lo3GemeenteCode("0600"),
                        new Lo3String("3 H4567"),
                        lo3persoon,
                        null,
                        null,
                        verbintenis,
                        null);
        BrpToevalligeGebeurtenis brpGebeurtenis = subject.converteer(gebeurtenis);
        Assert.assertNotNull("Moet verbintenis zijn", brpGebeurtenis.getVerbintenis());
        Assert.assertNotNull("Datum moet gevuld zijn", brpGebeurtenis.getDatumAanvang());
        Assert.assertNotNull("DoelPartijCode moet gevuld zijn", brpGebeurtenis.getDoelPartijCode());
        Assert.assertNotNull("Nummer akte moet gevuld zijn", brpGebeurtenis.getNummerAkte());
        Assert.assertNotNull("Persoon moet gevuld zijn", brpGebeurtenis.getPersoon());
        Assert.assertNotNull("Register moet gevuld zijn", brpGebeurtenis.getRegisterGemeente());
    }

    @Test
    public void testAkte5H() {
        final Lo3Stapel<Lo3PersoonInhoud> lo3persoon =
                new Lo3Stapel<>(Collections.singletonList(maakPersoon("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M")));
        Lo3HuwelijkOfGpInhoud sluiting =
                maakHuwelijk(
                        "100",
                        "100",
                        "voornaam",
                        "G",
                        "voor de",
                        "achternaam",
                        19700101,
                        "0599",
                        "6030",
                        "M",
                        19990101,
                        "0600",
                        "6030",
                        null,
                        null,
                        null,
                        null,
                        "P");
        Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorieSluiting = new Lo3Categorie<>(sluiting, null, new Lo3Historie(null, null, null), null);
        Lo3HuwelijkOfGpInhoud omzetting =
                maakHuwelijk(
                        "100",
                        "100",
                        "voornaam",
                        "G",
                        "voor de",
                        "achternaam",
                        19700101,
                        "0599",
                        "6030",
                        "M",
                        20000101,
                        "0600",
                        "6030",
                        null,
                        null,
                        null,
                        null,
                        "H");
        Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorieOmzetting =
                new Lo3Categorie<>(
                        omzetting,
                        Lo3Documentatie.build(null, null, null, null, null, null, null),
                        new Lo3Historie(null, new Lo3Datum(20160401), null),
                        null);
        Lo3Stapel<Lo3HuwelijkOfGpInhoud> verbintenis = new Lo3Stapel<>(Arrays.asList(categorieOmzetting, categorieSluiting));
        Lo3ToevalligeGebeurtenis gebeurtenis =
                new Lo3ToevalligeGebeurtenis(
                        new Lo3GemeenteCode("0599"),
                        new Lo3GemeenteCode("0600"),
                        new Lo3String("5 H4567"),
                        lo3persoon,
                        null,
                        null,
                        verbintenis,
                        null);
        BrpToevalligeGebeurtenis brpGebeurtenis = subject.converteer(gebeurtenis);
        Assert.assertNotNull("Moet verbintenis zijn", brpGebeurtenis.getVerbintenis());
        Assert.assertNotNull("Datum moet gevuld zijn", brpGebeurtenis.getDatumAanvang());
        Assert.assertNotNull("DoelPartijCode moet gevuld zijn", brpGebeurtenis.getDoelPartijCode());
        Assert.assertNotNull("Nummer akte moet gevuld zijn", brpGebeurtenis.getNummerAkte());
        Assert.assertNotNull("Persoon moet gevuld zijn", brpGebeurtenis.getPersoon());
        Assert.assertNotNull("Register moet gevuld zijn", brpGebeurtenis.getRegisterGemeente());
    }

    private Lo3Categorie<Lo3PersoonInhoud> maakPersoon(
            final String anummer,
            final String bsn,
            final String voornaam,
            final String titelPredicaat,
            final String voorvoegsel,
            final String achternaam,
            final Integer geboorteDatum,
            final String geboorteGemeenteCode,
            final String geboorteLandCode,
            final String geslacht) {

        final Lo3PersoonInhoud inhoud =
                new Lo3PersoonInhoud(
                        anummer == null ? null : new Lo3String(anummer),
                        bsn == null ? null : new Lo3String(bsn),
                        voornaam == null ? null : new Lo3String(voornaam),
                        titelPredicaat == null ? null : new Lo3AdellijkeTitelPredikaatCode(titelPredicaat),
                        voorvoegsel == null ? null : new Lo3String(voorvoegsel),
                        achternaam == null ? null : new Lo3String(achternaam),
                        geboorteDatum == null ? null : new Lo3Datum(geboorteDatum),
                        geboorteGemeenteCode == null ? null : new Lo3GemeenteCode(geboorteGemeenteCode),
                        geboorteLandCode == null ? null : new Lo3LandCode(geboorteLandCode),
                        geslacht == null ? null : new Lo3Geslachtsaanduiding(geslacht),
                        null,
                        null,
                        new Lo3AanduidingNaamgebruikCode("J"));
        return new Lo3Categorie<>(
                inhoud,
                Lo3Documentatie.build(null, null, null, null, null, null, null),
                new Lo3Historie(null, new Lo3Datum(20160401), null),
                null);
    }

    private Lo3Stapel<Lo3OuderInhoud> maakOuder(
            final String anummer,
            final String bsn,
            final String voornaam,
            final String titelPredicaat,
            final String voorvoegsel,
            final String achternaam,
            final Integer geboorteDatum,
            final String geboorteGemeenteCode,
            final String geboorteLandCode,
            final String geslacht,
            final Integer datumBetrekking) {
        final Lo3OuderInhoud lo3OuderInhoud =
                new Lo3OuderInhoud(
                        anummer == null ? null : new Lo3String(anummer),
                        bsn == null ? null : new Lo3String(bsn),
                        voornaam == null ? null : new Lo3String(voornaam),
                        titelPredicaat == null ? null : new Lo3AdellijkeTitelPredikaatCode(titelPredicaat),
                        voorvoegsel == null ? null : new Lo3String(voorvoegsel),
                        achternaam == null ? null : new Lo3String(achternaam),
                        geboorteDatum == null ? null : new Lo3Datum(geboorteDatum),
                        geboorteGemeenteCode == null ? null : new Lo3GemeenteCode(geboorteGemeenteCode),
                        geboorteLandCode == null ? null : new Lo3LandCode(geboorteLandCode),
                        geslacht == null ? null : new Lo3Geslachtsaanduiding(geslacht),
                        datumBetrekking == null ? null : new Lo3Datum(datumBetrekking));
        Lo3Categorie<Lo3OuderInhoud> categorie = new Lo3Categorie<>(lo3OuderInhoud, null, new Lo3Historie(null, null, null), null);
        return new Lo3Stapel<>(Arrays.asList(categorie, categorie));
    }

    private Lo3HuwelijkOfGpInhoud maakHuwelijk(
            final String anummer,
            final String bsn,
            final String voornaam,
            final String titelPredicaat,
            final String voorvoegsel,
            final String achternaam,
            final Integer geboorteDatum,
            final String geboorteGemeenteCode,
            final String geboorteLandCode,
            final String geslacht,
            final Integer sluitingDatum,
            final String sluitingGemeenteCode,
            final String sluitingLandCode,
            final Integer ontbindingDatum,
            final String ontbindingGemeenteCode,
            final String ontbindingLandCode,
            final String ontbindingReden,
            final String soortVerbindtenis) {
        return new Lo3HuwelijkOfGpInhoud(
                anummer == null ? null : new Lo3String(anummer),
                bsn == null ? null : new Lo3String(bsn),
                voornaam == null ? null : new Lo3String(voornaam),
                titelPredicaat == null ? null : new Lo3AdellijkeTitelPredikaatCode(titelPredicaat),
                voorvoegsel == null ? null : new Lo3String(voorvoegsel),
                achternaam == null ? null : new Lo3String(achternaam),
                geboorteDatum == null ? null : new Lo3Datum(geboorteDatum),
                geboorteGemeenteCode == null ? null : new Lo3GemeenteCode(geboorteGemeenteCode),
                geboorteLandCode == null ? null : new Lo3LandCode(geboorteLandCode),
                geslacht == null ? null : new Lo3Geslachtsaanduiding(geslacht),
                sluitingDatum == null ? null : new Lo3Datum(sluitingDatum),
                sluitingGemeenteCode == null ? null : new Lo3GemeenteCode(sluitingGemeenteCode),
                sluitingLandCode == null ? null : new Lo3LandCode(sluitingLandCode),
                ontbindingDatum == null ? null : new Lo3Datum(ontbindingDatum),
                ontbindingGemeenteCode == null ? null : new Lo3GemeenteCode(ontbindingGemeenteCode),
                ontbindingLandCode == null ? null : new Lo3LandCode(ontbindingLandCode),
                ontbindingReden == null ? null : new Lo3RedenOntbindingHuwelijkOfGpCode(ontbindingReden),
                soortVerbindtenis == null ? null : new Lo3SoortVerbintenis(soortVerbindtenis));
    }
}
