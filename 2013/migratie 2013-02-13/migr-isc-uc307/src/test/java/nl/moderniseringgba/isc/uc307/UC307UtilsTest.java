/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc307;

import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;

import org.junit.Assert;
import org.junit.Test;

public class UC307UtilsTest {

    // Gebruikte gegevens moeder.
    private static final String WAARDE_ANUMMER_MOEDER = "12345678";
    private static final String WAARDE_BSN_MOEDER = "1234567890";
    private static final String WAARDE_VOORNAMEN_MOEDER = "Carla";
    private static final String WAARDE_ADELLIJKEPREDIKAATCODE_MOEDER = "K";
    private static final String WAARDE_VOORVOEGSEL_MOEDER = "van";
    private static final String WAARDE_GESLACHTSNAAM_MOEDER = "Leeuwen";
    private static final String WAARDE_GEBOORTEDATUM_MOEDER = "19680607";
    private static final String WAARDE_GEBOORTEGEMEENTECODE_MOEDER = "1904";
    private static final String WAARDE_GEBOORTELANDCODE_MOEDER = "3010";
    private static final String WAARDE_GESLACHTSAANDUIDING_MOEDER = "V";
    private static final String WAARDE_FAMILIERECHTELIJKEBETREKKING_MOEDER = "20130104";

    // Gebruikte gegevens vader.
    private static final String WAARDE_ANUMMER_VADER = "87654321";
    private static final String WAARDE_BSN_VADER = "0987654321";
    private static final String WAARDE_VOORNAMEN_VADER = "Piet";
    private static final String WAARDE_ADELLIJKEPREDIKAATCODE_VADER = "H";
    private static final String WAARDE_VOORVOEGSEL_VADER = "de";
    private static final String WAARDE_GESLACHTSNAAM_VADER = "Graaff";
    private static final String WAARDE_GEBOORTEDATUM_VADER = "19600302";
    private static final String WAARDE_GEBOORTEGEMEENTECODE_VADER = "1905";
    private static final String WAARDE_GEBOORTELANDCODE_VADER = "3010";
    private static final String WAARDE_GESLACHTSAANDUIDING_VADER = "M";
    private static final String WAARDE_FAMILIERECHTELIJKEBETREKKING_VADER = "20130104";

    @Test
    public void testNormalePersoonslijst() throws Exception {

        final Lo3Persoonslijst persoonslijst = maakLo3Persoonslijst(true, true, false);

        final Lo3OuderInhoud moedergegevens = UC307Utils.actueleMoederGegevens(persoonslijst);

        Assert.assertNotNull("Moedergegevens horen niet 'null' te zijn.", moedergegevens);
        Assert.assertEquals("Anummer komt niet overeen met dat van de moeder.", WAARDE_ANUMMER_MOEDER, moedergegevens
                .getaNummer().toString());
        Assert.assertEquals("Burgerservicenummer komt niet overeen met dat van de moeder.", WAARDE_BSN_MOEDER,
                moedergegevens.getBurgerservicenummer().toString());
        Assert.assertEquals("Voornamen komen niet overeen met dat van de moeder.", WAARDE_VOORNAMEN_MOEDER,
                moedergegevens.getVoornamen());
        Assert.assertEquals("Adellijke titel/Predikaatcode komt niet overeen met dat van de moeder.",
                WAARDE_ADELLIJKEPREDIKAATCODE_MOEDER, moedergegevens.getAdellijkeTitelPredikaatCode().getCode());
        Assert.assertEquals("Voorvoegsel geslachtsnaam komt niet overeen met dat van de moeder.",
                WAARDE_VOORVOEGSEL_MOEDER, moedergegevens.getVoorvoegselGeslachtsnaam());
        Assert.assertEquals("Geslachtsnaam komt niet overeen met dat van de moeder.", WAARDE_GESLACHTSNAAM_MOEDER,
                moedergegevens.getGeslachtsnaam());
        Assert.assertEquals("Geboortedatum komt niet overeen met dat van de moeder.", WAARDE_GEBOORTEDATUM_MOEDER,
                String.valueOf(moedergegevens.getGeboortedatum().getDatum()));
        Assert.assertEquals("Geboortegemeentecode komt niet overeen met dat van de moeder.",
                WAARDE_GEBOORTEGEMEENTECODE_MOEDER, moedergegevens.getGeboorteGemeenteCode().getCode());
        Assert.assertEquals("Geboortelandcode komt niet overeen met dat van de moeder.",
                WAARDE_GEBOORTELANDCODE_MOEDER, moedergegevens.getGeboorteLandCode().getCode());
        Assert.assertEquals("Geslachtsaanduiding komt niet overeen met dat van de moeder.",
                WAARDE_GESLACHTSAANDUIDING_MOEDER, moedergegevens.getGeslachtsaanduiding().getCode());
        Assert.assertEquals("Datum familierechtelijkebetrekking komt niet overeen met dat van de moeder.",
                WAARDE_FAMILIERECHTELIJKEBETREKKING_MOEDER,
                String.valueOf(moedergegevens.getFamilierechtelijkeBetrekking().getDatum()));
    }

    @Test
    public void testZonderMoedergegevens() throws Exception {

        final Lo3Persoonslijst persoonslijst = maakLo3Persoonslijst(false, false, false);

        final Lo3OuderInhoud moedergegevens = UC307Utils.actueleMoederGegevens(persoonslijst);

        Assert.assertNull("Moedergegevens horen 'null' te zijn.", moedergegevens);

    }

    @Test
    public void testMoedergegevensInOuderInhoud1() throws Exception {

        final Lo3Persoonslijst persoonslijst = maakLo3Persoonslijst(true, true, false);

        final Lo3OuderInhoud moedergegevens = UC307Utils.actueleMoederGegevens(persoonslijst);

        Assert.assertNotNull("Moedergegevens horen niet 'null' te zijn.", moedergegevens);
        Assert.assertEquals("Anummer komt niet overeen met dat van de moeder.", WAARDE_ANUMMER_MOEDER, moedergegevens
                .getaNummer().toString());
        Assert.assertEquals("Burgerservicenummer komt niet overeen met dat van de moeder.", WAARDE_BSN_MOEDER,
                moedergegevens.getBurgerservicenummer().toString());
        Assert.assertEquals("Voornamen komen niet overeen met dat van de moeder.", WAARDE_VOORNAMEN_MOEDER,
                moedergegevens.getVoornamen());
        Assert.assertEquals("Adellijke titel/Predikaatcode komt niet overeen met dat van de moeder.",
                WAARDE_ADELLIJKEPREDIKAATCODE_MOEDER, moedergegevens.getAdellijkeTitelPredikaatCode().getCode());
        Assert.assertEquals("Voorvoegsel geslachtsnaam komt niet overeen met dat van de moeder.",
                WAARDE_VOORVOEGSEL_MOEDER, moedergegevens.getVoorvoegselGeslachtsnaam());
        Assert.assertEquals("Geslachtsnaam komt niet overeen met dat van de moeder.", WAARDE_GESLACHTSNAAM_MOEDER,
                moedergegevens.getGeslachtsnaam());
        Assert.assertEquals("Geboortedatum komt niet overeen met dat van de moeder.", WAARDE_GEBOORTEDATUM_MOEDER,
                String.valueOf(moedergegevens.getGeboortedatum().getDatum()));
        Assert.assertEquals("Geboortegemeentecode komt niet overeen met dat van de moeder.",
                WAARDE_GEBOORTEGEMEENTECODE_MOEDER, moedergegevens.getGeboorteGemeenteCode().getCode());
        Assert.assertEquals("Geboortelandcode komt niet overeen met dat van de moeder.",
                WAARDE_GEBOORTELANDCODE_MOEDER, moedergegevens.getGeboorteLandCode().getCode());
        Assert.assertEquals("Geslachtsaanduiding komt niet overeen met dat van de moeder.",
                WAARDE_GESLACHTSAANDUIDING_MOEDER, moedergegevens.getGeslachtsaanduiding().getCode());
        Assert.assertEquals("Datum familierechtelijkebetrekking komt niet overeen met dat van de moeder.",
                WAARDE_FAMILIERECHTELIJKEBETREKKING_MOEDER,
                String.valueOf(moedergegevens.getFamilierechtelijkeBetrekking().getDatum()));

    }

    @Test
    public void testMoedergegevensInOuderInhoud2() throws Exception {

        final Lo3Persoonslijst persoonslijst = maakLo3Persoonslijst(true, false, true);

        final Lo3OuderInhoud moedergegevens = UC307Utils.actueleMoederGegevens(persoonslijst);

        Assert.assertNotNull("Moedergegevens horen niet 'null' te zijn.", moedergegevens);
        Assert.assertEquals("Anummer komt niet overeen met dat van de moeder.", WAARDE_ANUMMER_MOEDER, moedergegevens
                .getaNummer().toString());
        Assert.assertEquals("Burgerservicenummer komt niet overeen met dat van de moeder.", WAARDE_BSN_MOEDER,
                moedergegevens.getBurgerservicenummer().toString());
        Assert.assertEquals("Voornamen komen niet overeen met dat van de moeder.", WAARDE_VOORNAMEN_MOEDER,
                moedergegevens.getVoornamen());
        Assert.assertEquals("Adellijke titel/Predikaatcode komt niet overeen met dat van de moeder.",
                WAARDE_ADELLIJKEPREDIKAATCODE_MOEDER, moedergegevens.getAdellijkeTitelPredikaatCode().getCode());
        Assert.assertEquals("Voorvoegsel geslachtsnaam komt niet overeen met dat van de moeder.",
                WAARDE_VOORVOEGSEL_MOEDER, moedergegevens.getVoorvoegselGeslachtsnaam());
        Assert.assertEquals("Geslachtsnaam komt niet overeen met dat van de moeder.", WAARDE_GESLACHTSNAAM_MOEDER,
                moedergegevens.getGeslachtsnaam());
        Assert.assertEquals("Geboortedatum komt niet overeen met dat van de moeder.", WAARDE_GEBOORTEDATUM_MOEDER,
                String.valueOf(moedergegevens.getGeboortedatum().getDatum()));
        Assert.assertEquals("Geboortegemeentecode komt niet overeen met dat van de moeder.",
                WAARDE_GEBOORTEGEMEENTECODE_MOEDER, moedergegevens.getGeboorteGemeenteCode().getCode());
        Assert.assertEquals("Geboortelandcode komt niet overeen met dat van de moeder.",
                WAARDE_GEBOORTELANDCODE_MOEDER, moedergegevens.getGeboorteLandCode().getCode());
        Assert.assertEquals("Geslachtsaanduiding komt niet overeen met dat van de moeder.",
                WAARDE_GESLACHTSAANDUIDING_MOEDER, moedergegevens.getGeslachtsaanduiding().getCode());
        Assert.assertEquals("Datum familierechtelijkebetrekking komt niet overeen met dat van de moeder.",
                WAARDE_FAMILIERECHTELIJKEBETREKKING_MOEDER,
                String.valueOf(moedergegevens.getFamilierechtelijkeBetrekking().getDatum()));
    }

    @Test
    public void testOuder1EnOuder2Vrouw() throws Exception {
        final Lo3Persoonslijst persoonslijst = maakLo3Persoonslijst(true, true, true);

        final Lo3OuderInhoud moedergegevens = UC307Utils.actueleMoederGegevens(persoonslijst);

        Assert.assertNull("Moedergegevens horen 'null' te zijn.", moedergegevens);
    }

    private Lo3Persoonslijst maakLo3Persoonslijst(
            final boolean moederGegevensAanwezig,
            final boolean moederGegevensInOuder1Inhoud,
            final boolean moederGegevensInOuder2Inhoud) throws Exception {

        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();

        final Lo3Historie historie =
                new Lo3Historie(null, new Lo3Datum(Integer.valueOf(19500101)),
                        new Lo3Datum(Integer.valueOf(19500101)));

        final Lo3Herkomst herkomstOuder1Inhoud = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_02, 1, 1);

        final Lo3Herkomst herkomstOuder2Inhoud = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_03, 1, 1);

        Lo3OuderInhoud moederInhoud = null;
        Lo3OuderInhoud vaderInhoud = null;

        if (moederGegevensAanwezig) {

            // Vul ouder1Inhoud met de moedergegevens.
            moederInhoud =
                    new Lo3OuderInhoud(Long.valueOf(WAARDE_ANUMMER_MOEDER), Long.valueOf(WAARDE_BSN_MOEDER),
                            WAARDE_VOORNAMEN_MOEDER, new Lo3AdellijkeTitelPredikaatCode(
                                    WAARDE_ADELLIJKEPREDIKAATCODE_MOEDER), WAARDE_VOORVOEGSEL_MOEDER,
                            WAARDE_GESLACHTSNAAM_MOEDER, new Lo3Datum(Integer.valueOf(WAARDE_GEBOORTEDATUM_MOEDER)),
                            new Lo3GemeenteCode(WAARDE_GEBOORTEGEMEENTECODE_MOEDER), new Lo3LandCode(
                                    WAARDE_GEBOORTELANDCODE_MOEDER), new Lo3Geslachtsaanduiding(
                                    WAARDE_GESLACHTSAANDUIDING_MOEDER), new Lo3Datum(
                                    Integer.valueOf(WAARDE_FAMILIERECHTELIJKEBETREKKING_MOEDER)));

            // Vul ouder2Inhoud met de vadergegevens.
            vaderInhoud =
                    new Lo3OuderInhoud(Long.valueOf(WAARDE_ANUMMER_VADER), Long.valueOf(WAARDE_BSN_VADER),
                            WAARDE_VOORNAMEN_VADER, new Lo3AdellijkeTitelPredikaatCode(
                                    WAARDE_ADELLIJKEPREDIKAATCODE_VADER), WAARDE_VOORVOEGSEL_VADER,
                            WAARDE_GESLACHTSNAAM_VADER, new Lo3Datum(Integer.valueOf(WAARDE_GEBOORTEDATUM_VADER)),
                            new Lo3GemeenteCode(WAARDE_GEBOORTEGEMEENTECODE_VADER), new Lo3LandCode(
                                    WAARDE_GEBOORTELANDCODE_VADER), new Lo3Geslachtsaanduiding(
                                    WAARDE_GESLACHTSAANDUIDING_VADER), new Lo3Datum(
                                    Integer.valueOf(WAARDE_FAMILIERECHTELIJKEBETREKKING_VADER)));
        } else {

            // Vul ouder1Inhoud met de moedergegevens.
            moederInhoud = new Lo3OuderInhoud(null, null, null, null, null, null, null, null, null, null, null);

            // Vul ouder2Inhoud met de vadergegevens.
            vaderInhoud = new Lo3OuderInhoud(null, null, null, null, null, null, null, null, null, null, null);

        }

        Lo3Categorie<Lo3OuderInhoud> categorieOuder1Inhoud;
        Lo3Categorie<Lo3OuderInhoud> categorieOuder2Inhoud;

        if (moederGegevensInOuder1Inhoud && !moederGegevensInOuder2Inhoud) {
            categorieOuder1Inhoud =
                    new Lo3Categorie<Lo3OuderInhoud>(moederInhoud, null, historie, herkomstOuder1Inhoud);
            categorieOuder2Inhoud =
                    new Lo3Categorie<Lo3OuderInhoud>(vaderInhoud, null, historie, herkomstOuder2Inhoud);
        } else if (!moederGegevensInOuder1Inhoud && moederGegevensInOuder2Inhoud) {
            categorieOuder1Inhoud =
                    new Lo3Categorie<Lo3OuderInhoud>(vaderInhoud, null, historie, herkomstOuder1Inhoud);
            categorieOuder2Inhoud =
                    new Lo3Categorie<Lo3OuderInhoud>(moederInhoud, null, historie, herkomstOuder2Inhoud);
        } else if (moederGegevensInOuder1Inhoud && moederGegevensInOuder2Inhoud) {
            categorieOuder1Inhoud =
                    new Lo3Categorie<Lo3OuderInhoud>(moederInhoud, null, historie, herkomstOuder1Inhoud);
            categorieOuder2Inhoud =
                    new Lo3Categorie<Lo3OuderInhoud>(moederInhoud, null, historie, herkomstOuder2Inhoud);
        } else {
            categorieOuder1Inhoud =
                    new Lo3Categorie<Lo3OuderInhoud>(vaderInhoud, null, historie, herkomstOuder1Inhoud);
            categorieOuder2Inhoud =
                    new Lo3Categorie<Lo3OuderInhoud>(vaderInhoud, null, historie, herkomstOuder2Inhoud);
        }

        final List<Lo3Categorie<Lo3OuderInhoud>> lijstOuder1Inhoud = new ArrayList<Lo3Categorie<Lo3OuderInhoud>>();
        lijstOuder1Inhoud.add(categorieOuder1Inhoud);

        final List<Lo3Categorie<Lo3OuderInhoud>> lijstOuder2Inhoud = new ArrayList<Lo3Categorie<Lo3OuderInhoud>>();
        lijstOuder2Inhoud.add(categorieOuder2Inhoud);

        final Lo3Stapel<Lo3OuderInhoud> ouder1Stapel = new Lo3Stapel<Lo3OuderInhoud>(lijstOuder1Inhoud);
        final Lo3Stapel<Lo3OuderInhoud> ouder2Stapel = new Lo3Stapel<Lo3OuderInhoud>(lijstOuder2Inhoud);

        builder.ouder1Stapel(ouder1Stapel);
        builder.ouder2Stapel(ouder2Stapel);

        return builder.build();
    }
}
