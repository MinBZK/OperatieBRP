/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3.parser;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingBijzonderNederlandschap;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;

import org.junit.Test;

public class Lo3NationaliteitParserTest {

    private static final String WAARDE_ELEMENT_0410 = "M";
    private static final String WAARDE_ELEMENT_0510 = "3010";
    private static final String WAARDE_ELEMENT_6310 = "007";
    private static final String WAARDE_ELEMENT_6410 = "110";
    private static final String WAARDE_ELEMENT_6510 = "A";
    private static final String WAARDE_ELEMENT_8210 = "1920";
    private static final String WAARDE_ELEMENT_8220 = "20121219";
    private static final String WAARDE_ELEMENT_8230 = "123456";
    private static final String WAARDE_ELEMENT_8410 = "0";
    private static final String WAARDE_ELEMENT_8510 = "20121214";
    private static final String WAARDE_ELEMENT_8610 = "20121219";

    @Test
    public void testNationaliteitParser() {

        final Lo3NationaliteitParser parser = new Lo3NationaliteitParser();

        final Lo3Historie historie =
                new Lo3Historie(new Lo3IndicatieOnjuist("0"), new Lo3Datum(20121214), new Lo3Datum(20121219));
        final List<Lo3Categorie<Lo3CategorieInhoud>> categorieInhoudLijst =
                new ArrayList<Lo3Categorie<Lo3CategorieInhoud>>();

        final Lo3CategorieInhoud categorieInhoud =
                new Lo3NationaliteitInhoud(new Lo3NationaliteitCode(WAARDE_ELEMENT_0510),
                        new Lo3RedenNederlandschapCode(WAARDE_ELEMENT_6310), new Lo3RedenNederlandschapCode(
                                WAARDE_ELEMENT_6410), new Lo3AanduidingBijzonderNederlandschap(WAARDE_ELEMENT_6510));

        final Lo3Categorie<Lo3CategorieInhoud> categorieInhoudCategorie =
                new Lo3Categorie<Lo3CategorieInhoud>(categorieInhoud, null, historie, new Lo3Herkomst(
                        Lo3CategorieEnum.CATEGORIE_04, 0, 0));

        categorieInhoudLijst.add(categorieInhoudCategorie);

        final Lo3Stapel<Lo3CategorieInhoud> referentieInhoud =
                new Lo3Stapel<Lo3CategorieInhoud>(categorieInhoudLijst);

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<Lo3CategorieWaarde>();

        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_04, 1, 1);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0510, WAARDE_ELEMENT_0510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6310, WAARDE_ELEMENT_6310);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6410, WAARDE_ELEMENT_6410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6510, WAARDE_ELEMENT_6510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8210, WAARDE_ELEMENT_8210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8220, WAARDE_ELEMENT_8220);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8220, WAARDE_ELEMENT_8230);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8410, WAARDE_ELEMENT_8410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8510, WAARDE_ELEMENT_8510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8610, WAARDE_ELEMENT_8610);

        categorieen.add(categorie);

        final Lo3Stapel<Lo3NationaliteitInhoud> gegenereerdeInhoud = parser.parse(categorieen);

        Assert.assertEquals(referentieInhoud.getMeestRecenteElement().getInhoud(), gegenereerdeInhoud
                .getMeestRecenteElement().getInhoud());
        Assert.assertEquals(referentieInhoud.getMeestRecenteElement().getHistorie(), gegenereerdeInhoud
                .getMeestRecenteElement().getHistorie());

    }

    @Test(expected = OnverwachteElementenExceptie.class)
    public void testNationaliteitsParserOnverwachtElement() {

        final Lo3NationaliteitParser parser = new Lo3NationaliteitParser();

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<Lo3CategorieWaarde>();

        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_04, 1, 1);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0410, WAARDE_ELEMENT_0410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0510, WAARDE_ELEMENT_0510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6310, WAARDE_ELEMENT_6310);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6410, WAARDE_ELEMENT_6410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6510, WAARDE_ELEMENT_6510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8210, WAARDE_ELEMENT_8210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8220, WAARDE_ELEMENT_8220);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8220, WAARDE_ELEMENT_8230);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8410, WAARDE_ELEMENT_8410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8510, WAARDE_ELEMENT_8510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8610, WAARDE_ELEMENT_8610);

        categorieen.add(categorie);

        try {
            parser.parse(categorieen);
        } catch (final OnverwachteElementenExceptie exceptie) {
            throw exceptie;
        }
    }

    @Test
    public void testNationaliteitsParserNullWaarden() {

        final Lo3NationaliteitParser parser = new Lo3NationaliteitParser();

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<Lo3CategorieWaarde>();

        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(null, 1, 1);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0510, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6310, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6410, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6510, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8210, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8220, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8220, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8410, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8510, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8610, null);

        categorieen.add(categorie);

        parser.parse(categorieen);
    }

    @Test
    public void testNationaliteitsParserGeenWaarden() {

        final Lo3NationaliteitParser parser = new Lo3NationaliteitParser();

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<Lo3CategorieWaarde>();

        parser.parse(categorieen);
    }

}
