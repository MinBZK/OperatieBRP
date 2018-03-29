/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.parser;

import java.util.ArrayList;
import java.util.List;

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
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortVerbintenis;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

import org.junit.Test;

import org.junit.Assert;

public class Lo3ToevalligeGebeurtenisParserTest extends AbstractParserTest {

    // Ouder 2
    private static final String WAARDE_ELEMENT_0110_2 = "8745316841";
    private static final String WAARDE_ELEMENT_0120_2 = "987153135";
    private static final String WAARDE_ELEMENT_0210_2 = "Gerarda";
    private static final String WAARDE_ELEMENT_0220_2 = "H";
    private static final String WAARDE_ELEMENT_0230_2 = "op het";
    private static final String WAARDE_ELEMENT_0240_2 = "Veld";
    private static final String WAARDE_ELEMENT_0310_2 = "19590312";
    private static final String WAARDE_ELEMENT_0410_2 = "V";

    private String waardeElement8310;
    private Lo3CategorieEnum categorie;

    @Test
    public void testToevalligeGebeurtenisParserOK() {
        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();

        final Lo3Stapel<Lo3PersoonInhoud> persoonStapel = vulCategoriePersoon(categorieen, Lo3CategorieEnum.CATEGORIE_01, true);
        final Lo3Stapel<Lo3OuderInhoud> ouder1Stapel = vulCategorieOuder1(categorieen, Lo3CategorieEnum.CATEGORIE_02, true);
        final Lo3Stapel<Lo3OuderInhoud> ouder2Stapel = vulCategorieOuder2(categorieen, Lo3CategorieEnum.CATEGORIE_03, true);
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> huwelijkOfGpStapel = vulCategorieHuwelijkOfGp(categorieen, Lo3CategorieEnum.CATEGORIE_05, true);
        final Lo3Stapel<Lo3OverlijdenInhoud> overlijdenStapel = vulCategorieOverlijden(categorieen, Lo3CategorieEnum.CATEGORIE_06, false);

        final Lo3ToevalligeGebeurtenisParser parser = new Lo3ToevalligeGebeurtenisParser();
        final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis =
                parser.parse(categorieen, new Lo3String("3A"), new Lo3GemeenteCode("0599"), new Lo3GemeenteCode("0600"));

        Assert.assertEquals(persoonStapel.getLaatsteElement().getInhoud(), toevalligeGebeurtenis.getPersoon().getLaatsteElement().getInhoud());
        Assert.assertEquals(ouder1Stapel.getLaatsteElement().getInhoud(), toevalligeGebeurtenis.getOuder1().getLaatsteElement().getInhoud());
        Assert.assertEquals(ouder2Stapel.getLaatsteElement().getInhoud(), toevalligeGebeurtenis.getOuder2().getLaatsteElement().getInhoud());
        Assert.assertEquals(huwelijkOfGpStapel.getLaatsteElement().getInhoud(), toevalligeGebeurtenis.getVerbintenis().getLaatsteElement().getInhoud());
        Assert.assertEquals(overlijdenStapel.getLaatsteElement().getInhoud(), toevalligeGebeurtenis.getOverlijden().getInhoud());
    }

    @Test(expected = GeenActueleCategorieExceptie.class)
    public void testToevalligeGebeurtenisParserGeenActueleCategorie01() {
        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();

        vulCategoriePersoon(categorieen, Lo3CategorieEnum.CATEGORIE_51, false);

        final Lo3ToevalligeGebeurtenisParser parser = new Lo3ToevalligeGebeurtenisParser();
        parser.parse(categorieen, new Lo3String("3A"), new Lo3GemeenteCode("0599"), new Lo3GemeenteCode("0600"));
    }

    @Test(expected = GeenActueleCategorieExceptie.class)
    public void testToevalligeGebeurtenisParserGeenActueleCategorie02() {
        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();

        vulCategorieOuder1(categorieen, Lo3CategorieEnum.CATEGORIE_52, false);

        final Lo3ToevalligeGebeurtenisParser parser = new Lo3ToevalligeGebeurtenisParser();
        parser.parse(categorieen, new Lo3String("3A"), new Lo3GemeenteCode("0599"), new Lo3GemeenteCode("0600"));
    }

    @Test(expected = GeenActueleCategorieExceptie.class)
    public void testToevalligeGebeurtenisParserGeenActueleCategorie03() {
        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();

        vulCategorieOuder2(categorieen, Lo3CategorieEnum.CATEGORIE_53, false);

        final Lo3ToevalligeGebeurtenisParser parser = new Lo3ToevalligeGebeurtenisParser();
        parser.parse(categorieen, new Lo3String("3A"), new Lo3GemeenteCode("0599"), new Lo3GemeenteCode("0600"));
    }

    @Test(expected = GeenActueleCategorieExceptie.class)
    public void testToevalligeGebeurtenisParserGeenActueleCategorie05() {
        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();

        vulCategorieHuwelijkOfGp(categorieen, Lo3CategorieEnum.CATEGORIE_55, false);

        final Lo3ToevalligeGebeurtenisParser parser = new Lo3ToevalligeGebeurtenisParser();
        parser.parse(categorieen, new Lo3String("3A"), new Lo3GemeenteCode("0599"), new Lo3GemeenteCode("0600"));
    }

    @Test(expected = GeenActueleCategorieExceptie.class)
    public void testToevalligeGebeurtenisParserGeenActueleCategorie06() {
        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();

        vulCategorieOverlijden(categorieen, Lo3CategorieEnum.CATEGORIE_56, false);

        final Lo3ToevalligeGebeurtenisParser parser = new Lo3ToevalligeGebeurtenisParser();
        parser.parse(categorieen, new Lo3String("3A"), new Lo3GemeenteCode("0599"), new Lo3GemeenteCode("0600"));
    }

    @Test(expected = ParseException.class)
    public void testToevalligeGebeurtenisParserCategorie01TeVaak() {
        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();

        vulCategoriePersoon(categorieen, Lo3CategorieEnum.CATEGORIE_01, true);
        vulCategoriePersoon(categorieen, Lo3CategorieEnum.CATEGORIE_01, true);

        final Lo3ToevalligeGebeurtenisParser parser = new Lo3ToevalligeGebeurtenisParser();
        parser.parse(categorieen, new Lo3String("3A"), new Lo3GemeenteCode("0599"), new Lo3GemeenteCode("0600"));
    }

    @Test(expected = ParseException.class)
    public void testToevalligeGebeurtenisParserCategorie02TeVaak() {
        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();

        vulCategorieOuder1(categorieen, Lo3CategorieEnum.CATEGORIE_02, true);
        vulCategorieOuder1(categorieen, Lo3CategorieEnum.CATEGORIE_02, true);

        final Lo3ToevalligeGebeurtenisParser parser = new Lo3ToevalligeGebeurtenisParser();
        parser.parse(categorieen, new Lo3String("3A"), new Lo3GemeenteCode("0599"), new Lo3GemeenteCode("0600"));
    }

    @Test(expected = ParseException.class)
    public void testToevalligeGebeurtenisParserCategorie03TeVaak() {
        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();

        vulCategorieOuder2(categorieen, Lo3CategorieEnum.CATEGORIE_03, true);
        vulCategorieOuder2(categorieen, Lo3CategorieEnum.CATEGORIE_03, true);

        final Lo3ToevalligeGebeurtenisParser parser = new Lo3ToevalligeGebeurtenisParser();
        parser.parse(categorieen, new Lo3String("3A"), new Lo3GemeenteCode("0599"), new Lo3GemeenteCode("0600"));
    }

    @Test(expected = ParseException.class)
    public void testToevalligeGebeurtenisParserCategorie05TeVaak() {
        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();

        vulCategorieHuwelijkOfGp(categorieen, Lo3CategorieEnum.CATEGORIE_05, true);
        vulCategorieHuwelijkOfGp(categorieen, Lo3CategorieEnum.CATEGORIE_05, true);

        final Lo3ToevalligeGebeurtenisParser parser = new Lo3ToevalligeGebeurtenisParser();
        parser.parse(categorieen, new Lo3String("3A"), new Lo3GemeenteCode("0599"), new Lo3GemeenteCode("0600"));
    }

    @Test(expected = ParseException.class)
    public void testToevalligeGebeurtenisParserCategorie06TeVaak() {
        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();

        vulCategorieOverlijden(categorieen, Lo3CategorieEnum.CATEGORIE_06, true);
        vulCategorieOverlijden(categorieen, Lo3CategorieEnum.CATEGORIE_06, true);

        final Lo3ToevalligeGebeurtenisParser parser = new Lo3ToevalligeGebeurtenisParser();
        parser.parse(categorieen, new Lo3String("3A"), new Lo3GemeenteCode("0599"), new Lo3GemeenteCode("0600"));
    }

    @Test
    public void testLegeInhoud() {
        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();
        final Lo3ToevalligeGebeurtenisParser parser = new Lo3ToevalligeGebeurtenisParser();
        parser.parse(categorieen, new Lo3String("3A"), new Lo3GemeenteCode("0599"), new Lo3GemeenteCode("0600"));
    }

    private Lo3Stapel<Lo3PersoonInhoud> vulCategoriePersoon(
            final List<Lo3CategorieWaarde> categorieen,
            final Lo3CategorieEnum categorie,
            final boolean metHistorischCategorie) {
        waardeElement8310 = "010000";
        final Lo3Onderzoek onderzoek = maakOnderzoek();
        final Lo3Historie historie = maakHistorie();
        final Lo3Documentatie documentatie = maakDocumentatie();

        final Lo3CategorieWaarde categorieWaarde = new Lo3CategorieWaarde(categorie, 1, 1);

        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0110, WAARDE_ELEMENT_0110);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0120, WAARDE_ELEMENT_0120);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0210, WAARDE_ELEMENT_0210);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0220, WAARDE_ELEMENT_0220);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0230, WAARDE_ELEMENT_0230);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0240, WAARDE_ELEMENT_0240);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0310, WAARDE_ELEMENT_0310);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0320, WAARDE_ELEMENT_0320);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0330, WAARDE_ELEMENT_0330);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0410, WAARDE_ELEMENT_0410);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_2010, WAARDE_ELEMENT_2010);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_2020, WAARDE_ELEMENT_2020);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_6110, WAARDE_ELEMENT_6110);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8110, WAARDE_ELEMENT_8110);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8120, WAARDE_ELEMENT_8120);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8210, WAARDE_ELEMENT_8210);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8220, WAARDE_ELEMENT_8220);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8230, WAARDE_ELEMENT_8230);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8310, waardeElement8310);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDE_ELEMENT_8320);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8330, WAARDE_ELEMENT_8330);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8410, WAARDE_ELEMENT_8410);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8510, WAARDE_ELEMENT_8510);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8610, WAARDE_ELEMENT_8610);

        categorieen.add(categorieWaarde);

        final List<Lo3Categorie<Lo3PersoonInhoud>> categorieInhoudLijst = new ArrayList<>();

        final Lo3PersoonInhoud persoonInhoud =
                new Lo3PersoonInhoud(
                        maakLo3String(WAARDE_ELEMENT_0110, null),
                        maakLo3String(WAARDE_ELEMENT_0120, onderzoek),
                        maakLo3String(WAARDE_ELEMENT_0210, onderzoek),
                        new Lo3AdellijkeTitelPredikaatCode(WAARDE_ELEMENT_0220, onderzoek),
                        maakLo3String(WAARDE_ELEMENT_0230, onderzoek),
                        maakLo3String(WAARDE_ELEMENT_0240, onderzoek),
                        maakDatum(WAARDE_ELEMENT_0310, onderzoek),
                        new Lo3GemeenteCode(WAARDE_ELEMENT_0320, onderzoek),
                        new Lo3LandCode(WAARDE_ELEMENT_0330, onderzoek),
                        new Lo3Geslachtsaanduiding(WAARDE_ELEMENT_0410, onderzoek),
                        maakLo3String(WAARDE_ELEMENT_2010, null),
                        maakLo3String(WAARDE_ELEMENT_2020, null),
                        new Lo3AanduidingNaamgebruikCode(WAARDE_ELEMENT_6110, onderzoek));

        final Lo3Categorie<Lo3PersoonInhoud> categorieInhoudCategorie =
                new Lo3Categorie<>(persoonInhoud, documentatie, onderzoek, historie, new Lo3Herkomst(categorie, 0, 0));

        categorieInhoudLijst.add(categorieInhoudCategorie);

        if (metHistorischCategorie) {
            final Lo3Categorie<Lo3PersoonInhoud> categorieInhoudCategorieHistorisch =
                    new Lo3Categorie<>(
                            persoonInhoud,
                            documentatie,
                            onderzoek,
                            historie,
                            new Lo3Herkomst(Lo3CategorieEnum.bepaalHistorischeCategorie(categorie), 0, 0));
            categorieInhoudLijst.add(categorieInhoudCategorieHistorisch);
        }
        return new Lo3Stapel<>(categorieInhoudLijst);
    }

    private Lo3Stapel<Lo3OuderInhoud> vulCategorieOuder1(
            final List<Lo3CategorieWaarde> categorieen,
            final Lo3CategorieEnum categorie,
            final boolean metHistorischCategorie) {
        waardeElement8310 = "020000";
        final Lo3Onderzoek onderzoek = maakOnderzoek();
        final Lo3Historie historie = maakHistorie();
        final Lo3Documentatie documentatie = maakDocumentatie();

        final Lo3CategorieWaarde categorieWaarde = new Lo3CategorieWaarde(categorie, 1, 1);

        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0110, WAARDE_ELEMENT_0110);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0120, WAARDE_ELEMENT_0120);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0210, WAARDE_ELEMENT_0210);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0220, WAARDE_ELEMENT_0220);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0230, WAARDE_ELEMENT_0230);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0240, WAARDE_ELEMENT_0240);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0310, WAARDE_ELEMENT_0310);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0320, WAARDE_ELEMENT_0320);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0330, WAARDE_ELEMENT_0330);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0410, WAARDE_ELEMENT_0410);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_6210, WAARDE_ELEMENT_6210);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8110, WAARDE_ELEMENT_8110);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8120, WAARDE_ELEMENT_8120);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8210, WAARDE_ELEMENT_8210);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8220, WAARDE_ELEMENT_8220);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8230, WAARDE_ELEMENT_8230);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8310, waardeElement8310);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDE_ELEMENT_8320);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8330, WAARDE_ELEMENT_8330);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8410, WAARDE_ELEMENT_8410);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8510, WAARDE_ELEMENT_8510);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8610, WAARDE_ELEMENT_8610);

        categorieen.add(categorieWaarde);

        final List<Lo3Categorie<Lo3OuderInhoud>> categorieInhoudLijst = new ArrayList<>();

        final Lo3OuderInhoud ouderInhoud =
                new Lo3OuderInhoud(
                        maakLo3String(WAARDE_ELEMENT_0110, onderzoek),
                        maakLo3String(WAARDE_ELEMENT_0120, onderzoek),
                        maakLo3String(WAARDE_ELEMENT_0210, onderzoek),
                        new Lo3AdellijkeTitelPredikaatCode(WAARDE_ELEMENT_0220, onderzoek),
                        maakLo3String(WAARDE_ELEMENT_0230, onderzoek),
                        maakLo3String(WAARDE_ELEMENT_0240, onderzoek),
                        maakDatum(WAARDE_ELEMENT_0310, onderzoek),
                        new Lo3GemeenteCode(WAARDE_ELEMENT_0320, onderzoek),
                        new Lo3LandCode(WAARDE_ELEMENT_0330, onderzoek),
                        new Lo3Geslachtsaanduiding(WAARDE_ELEMENT_0410, onderzoek),
                        maakDatum(WAARDE_ELEMENT_6210, onderzoek));

        final Lo3Categorie<Lo3OuderInhoud> categorieInhoudCategorie =
                new Lo3Categorie<>(ouderInhoud, documentatie, onderzoek, historie, new Lo3Herkomst(categorie, 0, 0));

        categorieInhoudLijst.add(categorieInhoudCategorie);

        if (metHistorischCategorie) {
            final Lo3Categorie<Lo3OuderInhoud> categorieInhoudCategorieHistorisch =
                    new Lo3Categorie<>(
                            ouderInhoud,
                            documentatie,
                            onderzoek,
                            historie,
                            new Lo3Herkomst(Lo3CategorieEnum.bepaalHistorischeCategorie(categorie), 0, 0));
            categorieInhoudLijst.add(categorieInhoudCategorieHistorisch);
        }
        return new Lo3Stapel<>(categorieInhoudLijst);
    }

    private Lo3Stapel<Lo3OuderInhoud> vulCategorieOuder2(
            final List<Lo3CategorieWaarde> categorieen,
            final Lo3CategorieEnum categorie,
            final boolean metHistorischCategorie) {
        waardeElement8310 = "030000";
        final Lo3Onderzoek onderzoek = maakOnderzoek();
        final Lo3Historie historie = maakHistorie();
        final Lo3Documentatie documentatie = maakDocumentatie();

        final Lo3CategorieWaarde categorieWaarde = new Lo3CategorieWaarde(categorie, 1, 1);

        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0110, WAARDE_ELEMENT_0110_2);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0120, WAARDE_ELEMENT_0120_2);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0210, WAARDE_ELEMENT_0210_2);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0220, WAARDE_ELEMENT_0220_2);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0230, WAARDE_ELEMENT_0230_2);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0240, WAARDE_ELEMENT_0240_2);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0310, WAARDE_ELEMENT_0310_2);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0320, WAARDE_ELEMENT_0320);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0330, WAARDE_ELEMENT_0330);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0410, WAARDE_ELEMENT_0410_2);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_6210, WAARDE_ELEMENT_6210);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8110, WAARDE_ELEMENT_8110);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8120, WAARDE_ELEMENT_8120);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8210, WAARDE_ELEMENT_8210);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8220, WAARDE_ELEMENT_8220);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8230, WAARDE_ELEMENT_8230);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8310, waardeElement8310);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDE_ELEMENT_8320);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8330, WAARDE_ELEMENT_8330);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8410, WAARDE_ELEMENT_8410);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8510, WAARDE_ELEMENT_8510);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8610, WAARDE_ELEMENT_8610);

        categorieen.add(categorieWaarde);

        final List<Lo3Categorie<Lo3OuderInhoud>> categorieInhoudLijst = new ArrayList<>();

        final Lo3OuderInhoud ouderInhoud =
                new Lo3OuderInhoud(
                        maakLo3String(WAARDE_ELEMENT_0110_2, onderzoek),
                        maakLo3String(WAARDE_ELEMENT_0120_2, onderzoek),
                        maakLo3String(WAARDE_ELEMENT_0210_2, onderzoek),
                        new Lo3AdellijkeTitelPredikaatCode(WAARDE_ELEMENT_0220_2, onderzoek),
                        maakLo3String(WAARDE_ELEMENT_0230_2, onderzoek),
                        maakLo3String(WAARDE_ELEMENT_0240_2, onderzoek),
                        maakDatum(WAARDE_ELEMENT_0310_2, onderzoek),
                        new Lo3GemeenteCode(WAARDE_ELEMENT_0320, onderzoek),
                        new Lo3LandCode(WAARDE_ELEMENT_0330, onderzoek),
                        new Lo3Geslachtsaanduiding(WAARDE_ELEMENT_0410_2, onderzoek),
                        maakDatum(WAARDE_ELEMENT_6210, onderzoek));

        final Lo3Categorie<Lo3OuderInhoud> categorieInhoudCategorie =
                new Lo3Categorie<>(ouderInhoud, documentatie, onderzoek, historie, new Lo3Herkomst(categorie, 0, 0));

        categorieInhoudLijst.add(categorieInhoudCategorie);

        if (metHistorischCategorie) {
            final Lo3Categorie<Lo3OuderInhoud> categorieInhoudCategorieHistorisch =
                    new Lo3Categorie<>(
                            ouderInhoud,
                            documentatie,
                            onderzoek,
                            historie,
                            new Lo3Herkomst(Lo3CategorieEnum.bepaalHistorischeCategorie(categorie), 0, 0));
            categorieInhoudLijst.add(categorieInhoudCategorieHistorisch);
        }
        return new Lo3Stapel<>(categorieInhoudLijst);
    }

    private Lo3Stapel<Lo3HuwelijkOfGpInhoud> vulCategorieHuwelijkOfGp(
            final List<Lo3CategorieWaarde> categorieen,
            final Lo3CategorieEnum categorie,
            final boolean metHistorischCategorie) {
        waardeElement8310 = "050000";
        final Lo3Onderzoek onderzoek = maakOnderzoek();
        final Lo3Historie historie = maakHistorie();
        final Lo3Documentatie documentatie = maakDocumentatie();

        final Lo3CategorieWaarde categorieWaarde = new Lo3CategorieWaarde(categorie, 1, 1);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0110, WAARDE_ELEMENT_0110);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0120, WAARDE_ELEMENT_0120);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0210, WAARDE_ELEMENT_0210);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0220, WAARDE_ELEMENT_0220);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0230, WAARDE_ELEMENT_0230);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0240, WAARDE_ELEMENT_0240);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0310, WAARDE_ELEMENT_0310);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0320, WAARDE_ELEMENT_0320);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0330, WAARDE_ELEMENT_0330);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0410, WAARDE_ELEMENT_0410);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0610, WAARDE_ELEMENT_0610);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0620, WAARDE_ELEMENT_0620);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0630, WAARDE_ELEMENT_0630);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0710, WAARDE_ELEMENT_0710);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0720, WAARDE_ELEMENT_0720);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0730, WAARDE_ELEMENT_0730);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0740, WAARDE_ELEMENT_0740);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_1510, WAARDE_ELEMENT_1510);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8110, WAARDE_ELEMENT_8110);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8120, WAARDE_ELEMENT_8120);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8210, WAARDE_ELEMENT_8210);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8220, WAARDE_ELEMENT_8220);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8230, WAARDE_ELEMENT_8230);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8310, waardeElement8310);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDE_ELEMENT_8320);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8330, WAARDE_ELEMENT_8330);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8410, WAARDE_ELEMENT_8410);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8510, WAARDE_ELEMENT_8510);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8610, WAARDE_ELEMENT_8610);

        categorieen.add(categorieWaarde);

        final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> huwelijkOfGpInhoudLijst = new ArrayList<>();

        final Lo3HuwelijkOfGpInhoud huwelijkOfGpInhoud =
                new Lo3HuwelijkOfGpInhoud(
                        maakLo3String(WAARDE_ELEMENT_0110, onderzoek),
                        maakLo3String(WAARDE_ELEMENT_0120, onderzoek),
                        maakLo3String(WAARDE_ELEMENT_0210, onderzoek),
                        new Lo3AdellijkeTitelPredikaatCode(WAARDE_ELEMENT_0220, onderzoek),
                        maakLo3String(WAARDE_ELEMENT_0230, onderzoek),
                        maakLo3String(WAARDE_ELEMENT_0240, onderzoek),
                        maakDatum(WAARDE_ELEMENT_0310, onderzoek),
                        new Lo3GemeenteCode(WAARDE_ELEMENT_0320, onderzoek),
                        new Lo3LandCode(WAARDE_ELEMENT_0330, onderzoek),
                        new Lo3Geslachtsaanduiding(WAARDE_ELEMENT_0410, onderzoek),
                        maakDatum(WAARDE_ELEMENT_0610, onderzoek),
                        new Lo3GemeenteCode(WAARDE_ELEMENT_0620, onderzoek),
                        new Lo3LandCode(WAARDE_ELEMENT_0630, onderzoek),
                        maakDatum(WAARDE_ELEMENT_0710, onderzoek),
                        new Lo3GemeenteCode(WAARDE_ELEMENT_0720, onderzoek),
                        new Lo3LandCode(WAARDE_ELEMENT_0730, onderzoek),
                        new Lo3RedenOntbindingHuwelijkOfGpCode(WAARDE_ELEMENT_0740, onderzoek),
                        new Lo3SoortVerbintenis(WAARDE_ELEMENT_1510, onderzoek));

        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> huwelijkOfGpInhoudCategorie =
                new Lo3Categorie<>(huwelijkOfGpInhoud, documentatie, onderzoek, historie, new Lo3Herkomst(categorie, 0, 0));

        huwelijkOfGpInhoudLijst.add(huwelijkOfGpInhoudCategorie);

        if (metHistorischCategorie) {
            final Lo3Categorie<Lo3HuwelijkOfGpInhoud> huwelijkOfGpInhoudCategorieHistorisch =
                    new Lo3Categorie<>(
                            huwelijkOfGpInhoud,
                            documentatie,
                            onderzoek,
                            historie,
                            new Lo3Herkomst(Lo3CategorieEnum.bepaalHistorischeCategorie(categorie), 0, 0));
            huwelijkOfGpInhoudLijst.add(huwelijkOfGpInhoudCategorieHistorisch);
        }
        return new Lo3Stapel<>(huwelijkOfGpInhoudLijst);
    }

    private Lo3Stapel<Lo3OverlijdenInhoud> vulCategorieOverlijden(
            final List<Lo3CategorieWaarde> categorieen,
            final Lo3CategorieEnum categorie,
            final boolean metHistorischCategorie) {
        waardeElement8310 = "060000";
        final Lo3Onderzoek onderzoek = maakOnderzoek();
        final Lo3Historie historie = maakHistorie();
        final Lo3Documentatie documentatie = maakDocumentatie();

        final Lo3CategorieWaarde categorieWaarde = new Lo3CategorieWaarde(categorie, 1, 1);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0810, WAARDE_ELEMENT_0810);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0820, WAARDE_ELEMENT_0820);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0830, WAARDE_ELEMENT_0830);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8110, WAARDE_ELEMENT_8110);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8120, WAARDE_ELEMENT_8120);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8210, WAARDE_ELEMENT_8210);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8220, WAARDE_ELEMENT_8220);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8230, WAARDE_ELEMENT_8230);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8310, waardeElement8310);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDE_ELEMENT_8320);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8330, WAARDE_ELEMENT_8330);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8410, WAARDE_ELEMENT_8410);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8510, WAARDE_ELEMENT_8510);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8610, WAARDE_ELEMENT_8610);

        categorieen.add(categorieWaarde);

        final List<Lo3Categorie<Lo3OverlijdenInhoud>> categorieInhoudLijst = new ArrayList<>();
        final Lo3OverlijdenInhoud overlijdenInhoud =
                new Lo3OverlijdenInhoud(
                        maakDatum(WAARDE_ELEMENT_0810, onderzoek),
                        new Lo3GemeenteCode(WAARDE_ELEMENT_0820, onderzoek),
                        new Lo3LandCode(WAARDE_ELEMENT_0830, onderzoek));

        final Lo3Categorie<Lo3OverlijdenInhoud> categorieInhoudCategorie =
                new Lo3Categorie<>(overlijdenInhoud, documentatie, onderzoek, historie, new Lo3Herkomst(categorie, 0, 0));

        categorieInhoudLijst.add(categorieInhoudCategorie);

        if (metHistorischCategorie) {
            final Lo3Categorie<Lo3OverlijdenInhoud> categorieInhoudCategorieHistorisch =
                    new Lo3Categorie<>(
                            overlijdenInhoud,
                            documentatie,
                            onderzoek,
                            historie,
                            new Lo3Herkomst(Lo3CategorieEnum.bepaalHistorischeCategorie(categorie), 0, 0));
            categorieInhoudLijst.add(categorieInhoudCategorieHistorisch);
        }

        return new Lo3Stapel<>(categorieInhoudLijst);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.bericht.model.lo3.parser.AbstractParserTest#getGegevensInOnderzoek()
     */
    @Override
    String getGegevensInOnderzoek() {
        return waardeElement8310;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.bericht.model.lo3.parser.AbstractParserTest#getCategorie()
     */
    @Override
    Lo3CategorieEnum getCategorie() {
        return categorie;
    }
}
