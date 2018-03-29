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
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisVerbintenis;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisVerbintenisOntbinding;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisVerbintenisSluiting;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
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
public class ToevalligeGebeurtenisVerbintenisConverteerderTest {

    private ToevalligeGebeurtenisVerbintenisConverteerder subject;

    @Before
    public void setup() {
        final Lo3AttribuutConverteerder lo3AttribuutConverteerder = new Lo3AttribuutConverteerder(new ConversietabelFactoryImpl());
        final ToevalligeGebeurtenisPersoonConverteerder persoonconverteerder = new ToevalligeGebeurtenisPersoonConverteerder(lo3AttribuutConverteerder);
        subject = new ToevalligeGebeurtenisVerbintenisConverteerder(lo3AttribuutConverteerder, persoonconverteerder);
    }

    @Test
    public void testLeeg() {
        assertNull("Indien null antwoord ook null", subject.converteer(null));
    }

    @Test
    public void testConverteerSluiting() {
        Lo3HuwelijkOfGpInhoud sluiting = maakHuwelijk("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M",
                19990101, "0600", "6030", null, null, null, null, "H");
        Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorie = new Lo3Categorie<>(sluiting, null, new Lo3Historie(null, null, null), null);
        Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel = new Lo3Stapel<>(Collections.singletonList(categorie));
        final BrpToevalligeGebeurtenisVerbintenis verbintenis = subject.converteer(stapel);
        final BrpToevalligeGebeurtenisPersoon persoon = verbintenis.getPartner();
        assertEquals("anummer moet gelijk zijn", "100", persoon.getAdministratienummer().getWaarde());
        assertEquals("Burgerservicenummer moet gelijk zijn", "100", persoon.getBurgerservicenummer().getWaarde());
        assertEquals("Voornaam moet gelijk zijn", "voornaam", persoon.getVoornamen().getWaarde());
        assertEquals("Adelijke titel moet graaf zijn", "G", persoon.getAdellijkeTitelCode().getWaarde());
        assertEquals("voorvoegsels moeten gelijk zijn", "voor de", persoon.getVoorvoegsel().getWaarde());
        assertEquals("Achternaam moet gelijk zijn", "achternaam", persoon.getGeslachtsnaamstam().getWaarde());
        assertEquals("Geboortedatum moet gelijk zijn", Integer.valueOf(19700101), persoon.getGeboorteDatum().getWaarde());
        assertEquals("Geboorteplaats moet gelijk zijn", "0599", persoon.getGeboorteGemeenteCode().getWaarde());
        assertEquals("Geboorteland moet gelijk zijn", "6030", persoon.getGeboorteLandOfGebiedCode().getWaarde());

        assertNull(verbintenis.getOmzetting());
        assertNull(verbintenis.getOntbinding());
        final BrpToevalligeGebeurtenisVerbintenisSluiting brpSluiting = verbintenis.getSluiting();
        assertNull("Buitenlandse plaats moet niet gevuld zijn", brpSluiting.getBuitenlandsePlaats());
        assertEquals("Sluitinggsdatum moet gelijk zijn", Integer.valueOf(19990101), brpSluiting.getDatum().getWaarde());
        assertEquals("Sluitingsplaats moet gelijk zijn", "0600", brpSluiting.getGemeenteCode().getWaarde());
        assertEquals("Sluitingsland moet gelijk zijn", "6030", brpSluiting.getLandOfGebiedCode().getWaarde());
        assertEquals("Soort verbintenis moet gelijk zijn", "H", brpSluiting.getRelatieCode().getWaarde());
    }

    @Test
    public void testConverteerOmzetting() {
        Lo3HuwelijkOfGpInhoud sluiting = maakHuwelijk("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M",
                19990101, "0600", "6030", null, null, null, null, "P");
        Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorieSluiting = new Lo3Categorie<>(sluiting, null, new Lo3Historie(null, null, null), null);
        Lo3HuwelijkOfGpInhoud omzetting = maakHuwelijk("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M",
                20000101, "0600", "6030", null, null, null, null, "H");
        Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorieOmzetting = new Lo3Categorie<>(omzetting, null, new Lo3Historie(null, null, null), null);
        Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel = new Lo3Stapel<>(Arrays.asList(categorieOmzetting, categorieSluiting));
        final BrpToevalligeGebeurtenisVerbintenis verbintenis = subject.converteer(stapel);
        final BrpToevalligeGebeurtenisPersoon persoon = verbintenis.getPartner();
        assertEquals("anummer moet gelijk zijn", "100", persoon.getAdministratienummer().getWaarde());
        assertEquals("Burgerservicenummer moet gelijk zijn", "100", persoon.getBurgerservicenummer().getWaarde());
        assertEquals("Voornaam moet gelijk zijn", "voornaam", persoon.getVoornamen().getWaarde());
        assertEquals("Adelijke titel moet graaf zijn", "G", persoon.getAdellijkeTitelCode().getWaarde());
        assertEquals("voorvoegsels moeten gelijk zijn", "voor de", persoon.getVoorvoegsel().getWaarde());
        assertEquals("Achternaam moet gelijk zijn", "achternaam", persoon.getGeslachtsnaamstam().getWaarde());
        assertEquals("Geboortedatum moet gelijk zijn", Integer.valueOf(19700101), persoon.getGeboorteDatum().getWaarde());
        assertEquals("Geboorteplaats moet gelijk zijn", "0599", persoon.getGeboorteGemeenteCode().getWaarde());
        assertEquals("Geboorteland moet gelijk zijn", "6030", persoon.getGeboorteLandOfGebiedCode().getWaarde());

        BrpToevalligeGebeurtenisVerbintenisSluiting brpSluiting = verbintenis.getSluiting();
        assertNull("Buitenlandse plaats moet niet gevuld zijn", brpSluiting.getBuitenlandsePlaats());
        assertEquals("Sluitinggsdatum moet gelijk zijn", Integer.valueOf(19990101), brpSluiting.getDatum().getWaarde());
        assertEquals("Sluitingsplaats moet gelijk zijn", "0600", brpSluiting.getGemeenteCode().getWaarde());
        assertEquals("Sluitingsland moet gelijk zijn", "6030", brpSluiting.getLandOfGebiedCode().getWaarde());
        assertEquals("Soort verbintenis moet gelijk zijn", "G", brpSluiting.getRelatieCode().getWaarde());

        brpSluiting = verbintenis.getOmzetting();
        assertNull("Buitenlandse plaats moet niet gevuld zijn", brpSluiting.getBuitenlandsePlaats());
        assertEquals("Sluitinggsdatum moet gelijk zijn", Integer.valueOf(20000101), brpSluiting.getDatum().getWaarde());
        assertEquals("Sluitingsplaats moet gelijk zijn", "0600", brpSluiting.getGemeenteCode().getWaarde());
        assertEquals("Sluitingsland moet gelijk zijn", "6030", brpSluiting.getLandOfGebiedCode().getWaarde());
        assertEquals("Soort verbintenis moet gelijk zijn", "H", brpSluiting.getRelatieCode().getWaarde());
    }

    @Test
    public void testConverteerOntbinding() {
        Lo3HuwelijkOfGpInhoud sluiting = maakHuwelijk("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M",
                19990101, "0600", "6030", null, null, null, null, "H");
        Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorieSluiting = new Lo3Categorie<>(sluiting, null, new Lo3Historie(null, null, null), null);
        Lo3HuwelijkOfGpInhoud ontbinding = maakHuwelijk("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M",
                19990101, "0600", "6030", 20000101, "0600", "6030", "S", "H");
        Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorieOntbinding = new Lo3Categorie<>(ontbinding, null, new Lo3Historie(null, null, null), null);
        Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel = new Lo3Stapel<>(Arrays.asList(categorieOntbinding, categorieSluiting));
        final BrpToevalligeGebeurtenisVerbintenis verbintenis = subject.converteer(stapel);
        final BrpToevalligeGebeurtenisPersoon persoon = verbintenis.getPartner();
        assertEquals("anummer moet gelijk zijn", "100", persoon.getAdministratienummer().getWaarde());
        assertEquals("Burgerservicenummer moet gelijk zijn", "100", persoon.getBurgerservicenummer().getWaarde());
        assertEquals("Voornaam moet gelijk zijn", "voornaam", persoon.getVoornamen().getWaarde());
        assertEquals("Adelijke titel moet graaf zijn", "G", persoon.getAdellijkeTitelCode().getWaarde());
        assertEquals("voorvoegsels moeten gelijk zijn", "voor de", persoon.getVoorvoegsel().getWaarde());
        assertEquals("Achternaam moet gelijk zijn", "achternaam", persoon.getGeslachtsnaamstam().getWaarde());
        assertEquals("Geboortedatum moet gelijk zijn", Integer.valueOf(19700101), persoon.getGeboorteDatum().getWaarde());
        assertEquals("Geboorteplaats moet gelijk zijn", "0599", persoon.getGeboorteGemeenteCode().getWaarde());
        assertEquals("Geboorteland moet gelijk zijn", "6030", persoon.getGeboorteLandOfGebiedCode().getWaarde());

        BrpToevalligeGebeurtenisVerbintenisSluiting brpSluiting = verbintenis.getSluiting();
        assertNull("Buitenlandse plaats moet niet gevuld zijn", brpSluiting.getBuitenlandsePlaats());
        assertEquals("Sluitinggsdatum moet gelijk zijn", Integer.valueOf(19990101), brpSluiting.getDatum().getWaarde());
        assertEquals("Sluitingsplaats moet gelijk zijn", "0600", brpSluiting.getGemeenteCode().getWaarde());
        assertEquals("Sluitingsland moet gelijk zijn", "6030", brpSluiting.getLandOfGebiedCode().getWaarde());
        assertEquals("Soort verbintenis moet gelijk zijn", "H", brpSluiting.getRelatieCode().getWaarde());

        final BrpToevalligeGebeurtenisVerbintenisOntbinding brpOntbinding = verbintenis.getOntbinding();
        assertNull("Buitenlandse plaats moet niet gevuld zijn", brpOntbinding.getBuitenlandsePlaats());
        assertEquals("Sluitinggsdatum moet gelijk zijn", Integer.valueOf(20000101), brpOntbinding.getDatum().getWaarde());
        assertEquals("Sluitingsplaats moet gelijk zijn", "0600", brpOntbinding.getGemeenteCode().getWaarde());
        assertEquals("Sluitingsland moet gelijk zijn", "6030", brpOntbinding.getLandOfGebiedCode().getWaarde());
        assertEquals("Reden moet overeenkomen", Character.valueOf('S'), brpOntbinding.getRedenEindeRelatieCode().getWaarde());
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
