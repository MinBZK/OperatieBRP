/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.toevalligegebeurtenis;

import java.util.Arrays;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisFamilierechtelijkeBetrekking;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingNaamgebruikCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortAkte;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Lo3AttribuutConverteerder;
import nl.bzk.migratiebrp.conversie.regels.tabel.ConversietabelFactoryImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class ToevalligeGebeurtenisFamilierechtelijkeBetrekkingConverteerderTest {

    private ToevalligeGebeurtenisFamilierechtelijkeBetrekkingConverteerder subject;

    @Before
    public void setup() {
        final Lo3AttribuutConverteerder lo3AttribuutConverteerder = new Lo3AttribuutConverteerder(new ConversietabelFactoryImpl());
        final ToevalligeGebeurtenisPersoonConverteerder persoonconverteerder = new ToevalligeGebeurtenisPersoonConverteerder(lo3AttribuutConverteerder);
        subject = new ToevalligeGebeurtenisFamilierechtelijkeBetrekkingConverteerder(lo3AttribuutConverteerder, persoonconverteerder);
    }

    @Test
    public void testConverteerErkenning() {
        final Lo3OuderInhoud ouder1Oud = maakOuder("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M", null);
        final Lo3OuderInhoud ouder1Nieuw =
                maakOuder("101", "101", "nieuweNaam", "H", "achter de", "nieuweachternaam", 19780101, "0599", "6030", "M", 20150401);
        final Lo3Categorie<Lo3OuderInhoud> categorieNieuw = new Lo3Categorie<>(ouder1Nieuw, null, new Lo3Historie(null, null, null), null);
        final Lo3Categorie<Lo3OuderInhoud> categorieOud = new Lo3Categorie<>(ouder1Oud, null, new Lo3Historie(null, null, null), null);
        final Lo3Stapel<Lo3OuderInhoud> stapelOuder = new Lo3Stapel<>(Arrays.asList(categorieNieuw, categorieOud));

        final Lo3PersoonInhoud nieuw = maakPersoon("100", "100", "voornaam", "G", "achter de", "nieuweachternaam", 19700101, "0599", "6030", "M");
        final Lo3PersoonInhoud oud = maakPersoon("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M");
        final Lo3Categorie<Lo3PersoonInhoud> categorieNieuwPersoon = new Lo3Categorie<>(nieuw, null, new Lo3Historie(null, null, null), null);
        final Lo3Categorie<Lo3PersoonInhoud> categorieOudPersoon = new Lo3Categorie<>(oud, null, new Lo3Historie(null, null, null), null);
        final Lo3Stapel<Lo3PersoonInhoud> stapelKind = new Lo3Stapel<>(Arrays.asList(categorieNieuwPersoon, categorieOudPersoon));

        final BrpToevalligeGebeurtenisFamilierechtelijkeBetrekking betrekking = subject.converteer(Lo3SoortAkte.AKTE_1C, stapelKind, stapelOuder, null);
        Assert.assertNull(betrekking.getAdoptie());
        Assert.assertNull(betrekking.getOntkenning());
        Assert.assertNull(betrekking.getVernietiging());
        Assert.assertNotNull(betrekking.getErkenning());
        Assert.assertNotNull(betrekking.getNaamsWijziging());
        Assert.assertEquals("Naamwijziging achternaam gewijzigd", "nieuweachternaam", betrekking.getNaamsWijziging().getGeslachtsnaamstam().getWaarde());
        Assert.assertEquals("Ouder nieuw achternaam", "nieuweachternaam", betrekking.getErkenning().getNieuweOuder().getGeslachtsnaamstam().getWaarde());
        Assert.assertEquals("Ouder oud achternaam", "achternaam", betrekking.getErkenning().getOudeOuder().getGeslachtsnaamstam().getWaarde());
    }

    @Test
    public void testConverteerErkenning2() {
        final Lo3OuderInhoud ouder1Oud = maakOuder("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M", null);
        final Lo3OuderInhoud ouder1Nieuw =
                maakOuder("101", "101", "nieuweNaam", "H", "achter de", "nieuweachternaam", 19780101, "0599", "6030", "M", 20150401);
        final Lo3Categorie<Lo3OuderInhoud> categorieNieuw = new Lo3Categorie<>(ouder1Nieuw, null, new Lo3Historie(null, null, null), null);
        final Lo3Categorie<Lo3OuderInhoud> categorieOud = new Lo3Categorie<>(ouder1Oud, null, new Lo3Historie(null, null, null), null);
        final Lo3Stapel<Lo3OuderInhoud> stapelOuder = new Lo3Stapel<>(Arrays.asList(categorieNieuw, categorieOud));

        final Lo3PersoonInhoud nieuw = maakPersoon("100", "100", "voornaam", "G", "achter de", "nieuweachternaam", 19700101, "0599", "6030", "M");
        final Lo3PersoonInhoud oud = maakPersoon("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M");
        final Lo3Categorie<Lo3PersoonInhoud> categorieNieuwPersoon = new Lo3Categorie<>(nieuw, null, new Lo3Historie(null, null, null), null);
        final Lo3Categorie<Lo3PersoonInhoud> categorieOudPersoon = new Lo3Categorie<>(oud, null, new Lo3Historie(null, null, null), null);
        final Lo3Stapel<Lo3PersoonInhoud> stapelKind = new Lo3Stapel<>(Arrays.asList(categorieNieuwPersoon, categorieOudPersoon));

        final BrpToevalligeGebeurtenisFamilierechtelijkeBetrekking betrekking = subject.converteer(Lo3SoortAkte.AKTE_1C, stapelKind, null, stapelOuder);
        Assert.assertNull(betrekking.getAdoptie());
        Assert.assertNull(betrekking.getOntkenning());
        Assert.assertNull(betrekking.getVernietiging());
        Assert.assertNotNull(betrekking.getErkenning());
        Assert.assertNotNull(betrekking.getNaamsWijziging());
        Assert.assertEquals("Naamwijziging achternaam gewijzigd", "nieuweachternaam", betrekking.getNaamsWijziging().getGeslachtsnaamstam().getWaarde());
        Assert.assertEquals("Ouder nieuw achternaam", "nieuweachternaam", betrekking.getErkenning().getNieuweOuder().getGeslachtsnaamstam().getWaarde());
        Assert.assertEquals("Ouder oud achternaam", "achternaam", betrekking.getErkenning().getOudeOuder().getGeslachtsnaamstam().getWaarde());
    }

    @Test
    public void testVerkeerdeAkte() {
        final Lo3OuderInhoud ouder1Oud = maakOuder("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M", null);
        final Lo3OuderInhoud ouder1Nieuw =
                maakOuder("101", "101", "nieuweNaam", "H", "achter de", "nieuweachternaam", 19780101, "0599", "6030", "M", 20150401);
        final Lo3Categorie<Lo3OuderInhoud> categorieNieuw = new Lo3Categorie<>(ouder1Nieuw, null, new Lo3Historie(null, null, null), null);
        final Lo3Categorie<Lo3OuderInhoud> categorieOud = new Lo3Categorie<>(ouder1Oud, null, new Lo3Historie(null, null, null), null);
        final Lo3Stapel<Lo3OuderInhoud> stapelOuder = new Lo3Stapel<>(Arrays.asList(categorieNieuw, categorieOud));
        Assert.assertNull("Niet ondersteunde akte moet null geven", subject.converteer(Lo3SoortAkte.AKTE_2A, null, null, stapelOuder));
    }

    @Test
    public void testAdoptie() {
        final Lo3OuderInhoud ouder1Oud = maakOuder("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M", null);
        final Lo3OuderInhoud ouder1Nieuw =
                maakOuder("101", "101", "nieuweNaam", "H", "achter de", "nieuweachternaam", 19780101, "0599", "6030", "M", 20150401);
        final Lo3Categorie<Lo3OuderInhoud> categorieNieuw = new Lo3Categorie<>(ouder1Nieuw, null, new Lo3Historie(null, null, null), null);
        final Lo3Categorie<Lo3OuderInhoud> categorieOud = new Lo3Categorie<>(ouder1Oud, null, new Lo3Historie(null, null, null), null);
        final Lo3Stapel<Lo3OuderInhoud> stapelOuder = new Lo3Stapel<>(Arrays.asList(categorieNieuw, categorieOud));

        final Lo3PersoonInhoud nieuw = maakPersoon("100", "100", "voornaam", "G", "achter de", "nieuweachternaam", 19700101, "0599", "6030", "M");
        final Lo3PersoonInhoud oud = maakPersoon("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M");
        final Lo3Categorie<Lo3PersoonInhoud> categorieNieuwPersoon = new Lo3Categorie<>(nieuw, null, new Lo3Historie(null, null, null), null);
        final Lo3Categorie<Lo3PersoonInhoud> categorieOudPersoon = new Lo3Categorie<>(oud, null, new Lo3Historie(null, null, null), null);
        final Lo3Stapel<Lo3PersoonInhoud> stapelKind = new Lo3Stapel<>(Arrays.asList(categorieNieuwPersoon, categorieOudPersoon));

        final BrpToevalligeGebeurtenisFamilierechtelijkeBetrekking betrekking =
                subject.converteer(Lo3SoortAkte.AKTE_1Q, stapelKind, stapelOuder, stapelOuder);
        Assert.assertNotNull(betrekking.getAdoptie());
        Assert.assertNull(betrekking.getOntkenning());
        Assert.assertNull(betrekking.getVernietiging());
        Assert.assertNull(betrekking.getErkenning());
        Assert.assertNotNull(betrekking.getNaamsWijziging());
        Assert.assertEquals("Naamwijziging achternaam gewijzigd", "nieuweachternaam", betrekking.getNaamsWijziging().getGeslachtsnaamstam().getWaarde());
        Assert.assertEquals(
                "Ouder nieuw achternaam",
                "nieuweachternaam",
                betrekking.getAdoptie().getOuder1().getNieuweOuder().getGeslachtsnaamstam().getWaarde());
        Assert.assertEquals("Ouder oud achternaam", "achternaam", betrekking.getAdoptie().getOuder1().getOudeOuder().getGeslachtsnaamstam().getWaarde());
    }

    private Lo3OuderInhoud maakOuder(
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
        return new Lo3OuderInhoud(
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
}
