/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.toevalligegebeurtenis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Collections;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisPersoon;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
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
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class ToevalligeGebeurtenisPersoonConverteerderTest {

    private ToevalligeGebeurtenisPersoonConverteerder subject;

    @Before
    public void setup() {
        final Lo3AttribuutConverteerder lo3AttribuutConverteerder = new Lo3AttribuutConverteerder(new ConversietabelFactoryImpl());
        subject = new ToevalligeGebeurtenisPersoonConverteerder(lo3AttribuutConverteerder);
    }

    @Test
    public void testLeegLo3Stapel() {
        assertNull("bij null antwoord ook null", subject.converteer((Lo3Stapel<Lo3PersoonInhoud>) null));
    }

    @Test
    public void testConverteerLo3Stapel() {
        Lo3PersoonInhoud inhoud = maakPersoon("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M");
        Lo3Categorie<Lo3PersoonInhoud> categorie = new Lo3Categorie<>(inhoud, null, new Lo3Historie(null, null, null), null);
        Lo3Stapel<Lo3PersoonInhoud> stapel = new Lo3Stapel<>(Collections.singletonList(categorie));
        final BrpToevalligeGebeurtenisPersoon persoon = subject.converteer(stapel);
        assertEquals("anummer moet gelijk zijn", "100", persoon.getAdministratienummer().getWaarde());
        assertEquals("Burgerservicenummer moet gelijk zijn", "100", persoon.getBurgerservicenummer().getWaarde());
        assertEquals("Voornaam moet gelijk zijn", "voornaam", persoon.getVoornamen().getWaarde());
        assertEquals("Adelijke titel moet graaf zijn", "G", persoon.getAdellijkeTitelCode().getWaarde());
        assertEquals("voorvoegsels moeten gelijk zijn", "voor de", persoon.getVoorvoegsel().getWaarde());
        assertEquals("Achternaam moet gelijk zijn", "achternaam", persoon.getGeslachtsnaamstam().getWaarde());
        assertEquals("Geboortedatum moet gelijk zijn", Integer.valueOf(19700101), persoon.getGeboorteDatum().getWaarde());
        assertEquals("Geboorteplaats moet gelijk zijn", "0599", persoon.getGeboorteGemeenteCode().getWaarde());
        assertEquals("Geboorteland moet gelijk zijn", "6030", persoon.getGeboorteLandOfGebiedCode().getWaarde());
    }

    @Test
    public void testConverteerLo3StapelGeenVoorvoegsel() {
        Lo3PersoonInhoud inhoud = maakPersoon("100", "100", "voornaam", "G", null, "achternaam", 19700101, "0599", "6030", "M");
        Lo3Categorie<Lo3PersoonInhoud> categorie = new Lo3Categorie<>(inhoud, null, new Lo3Historie(null, null, null), null);
        Lo3Stapel<Lo3PersoonInhoud> stapel = new Lo3Stapel<>(Collections.singletonList(categorie));
        final BrpToevalligeGebeurtenisPersoon persoon = subject.converteer(stapel);
        assertEquals("anummer moet gelijk zijn", "100", persoon.getAdministratienummer().getWaarde());
        assertEquals("Burgerservicenummer moet gelijk zijn", "100", persoon.getBurgerservicenummer().getWaarde());
        assertEquals("Voornaam moet gelijk zijn", "voornaam", persoon.getVoornamen().getWaarde());
        assertEquals("Adelijke titel moet graaf zijn", "G", persoon.getAdellijkeTitelCode().getWaarde());
        assertNull("voorvoegsels moeten leeg zijn", persoon.getVoorvoegsel());
        assertNull("scheidingsteken moet leeg zijn", persoon.getScheidingsteken());
        assertEquals("Achternaam moet gelijk zijn", "achternaam", persoon.getGeslachtsnaamstam().getWaarde());
        assertEquals("Geboortedatum moet gelijk zijn", Integer.valueOf(19700101), persoon.getGeboorteDatum().getWaarde());
        assertEquals("Geboorteplaats moet gelijk zijn", "0599", persoon.getGeboorteGemeenteCode().getWaarde());
        assertEquals("Geboorteland moet gelijk zijn", "6030", persoon.getGeboorteLandOfGebiedCode().getWaarde());
    }

    @Test
    public void testConverteerMeerdere() {
        Lo3PersoonInhoud nieuw = maakPersoon("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M");
        Lo3PersoonInhoud oud = maakPersoon(null, null, "oude voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "V");
        Lo3Categorie<Lo3PersoonInhoud> categorieNieuw = new Lo3Categorie<>(nieuw, null, new Lo3Historie(null, null, null), null);
        Lo3Categorie<Lo3PersoonInhoud> categorieOud = new Lo3Categorie<>(oud, null, new Lo3Historie(null, null, null), null);
        Lo3Stapel<Lo3PersoonInhoud> stapel = new Lo3Stapel<>(Arrays.asList(categorieNieuw, categorieOud));
        final BrpToevalligeGebeurtenisPersoon persoon = subject.converteer(stapel);
        assertEquals("Anummer moet overeenkomen", "100", persoon.getAdministratienummer().getWaarde());
        assertEquals("Er dient een scheidingsteken te zijn", Character.valueOf(' '), persoon.getScheidingsteken().getWaarde());
        assertEquals("Voornaam moet gelijk zijn", "oude voornaam", persoon.getVoornamen().getWaarde());
        assertEquals("Adelijke titel moet graaf zijn", "G", persoon.getAdellijkeTitelCode().getWaarde());
        assertEquals("voorvoegsels moeten gelijk zijn", "voor de", persoon.getVoorvoegsel().getWaarde());
        assertEquals("Achternaam moet gelijk zijn", "achternaam", persoon.getGeslachtsnaamstam().getWaarde());
        assertEquals("Geslacht moet gelijk zijn", "V", persoon.getGeslachtsaanduidingCode().getWaarde());
    }

    @Test
    public void testLeegLo3HuwelijkOfGpInhoud() {
        assertNull("bij null antwoord ook null", subject.converteer((Lo3HuwelijkOfGpInhoud) null));
    }

    @Test
    public void testLeegLo3OuderInhoud() {
        assertNull("bij null antwoord ook null", subject.converteer((Lo3OuderInhoud) null));
    }

    @Test
    public void testConverteerLo3HuwelijkOfGpInhoud() {
        Lo3HuwelijkOfGpInhoud inhoud = maakHuwelijk("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M",
                19990101, "0600", "6030", null, null, null, null, "H");
        final BrpToevalligeGebeurtenisPersoon persoon = subject.converteer(inhoud);

        assertEquals("anummer moet gelijk zijn", "100", persoon.getAdministratienummer().getWaarde());
        assertEquals("Burgerservicenummer moet gelijk zijn", "100", persoon.getBurgerservicenummer().getWaarde());
        assertEquals("Voornaam moet gelijk zijn", "voornaam", persoon.getVoornamen().getWaarde());
        assertEquals("Adelijke titel moet graaf zijn", "G", persoon.getAdellijkeTitelCode().getWaarde());
        assertEquals("voorvoegsels moeten gelijk zijn", "voor de", persoon.getVoorvoegsel().getWaarde());
        assertEquals("Achternaam moet gelijk zijn", "achternaam", persoon.getGeslachtsnaamstam().getWaarde());
        assertEquals("Geboortedatum moet gelijk zijn", Integer.valueOf(19700101), persoon.getGeboorteDatum().getWaarde());
        assertEquals("Geboorteplaats moet gelijk zijn", "0599", persoon.getGeboorteGemeenteCode().getWaarde());
        assertEquals("Geboorteland moet gelijk zijn", "6030", persoon.getGeboorteLandOfGebiedCode().getWaarde());
    }

    private Lo3PersoonInhoud maakPersoon(
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
        return new Lo3PersoonInhoud(
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
