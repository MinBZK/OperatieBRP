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
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3KiesrechtInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingEuropeesKiesrecht;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingUitgeslotenKiesrecht;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;

import org.junit.Test;

public class Lo3KiesrechtParserTest {

    private static final String WAARDE_ELEMENT_0410 = "M";
    private static final String WAARDE_ELEMENT_3110 = "1";
    private static final String WAARDE_ELEMENT_3120 = "20120105";
    private static final String WAARDE_ELEMENT_3130 = "20121231";
    private static final String WAARDE_ELEMENT_3810 = "N";
    private static final String WAARDE_ELEMENT_3820 = "20121201";
    private static final String WAARDE_ELEMENT_8210 = "1920";
    private static final String WAARDE_ELEMENT_8220 = "20121219";
    private static final String WAARDE_ELEMENT_8230 = "123456";

    @Test
    public void testKiesrechtParserParser() {

        final Lo3KiesrechtParser parser = new Lo3KiesrechtParser();

        final Lo3Historie historie = Lo3Historie.NULL_HISTORIE;
        final List<Lo3Categorie<Lo3KiesrechtInhoud>> categorieInhoudLijst =
                new ArrayList<Lo3Categorie<Lo3KiesrechtInhoud>>();

        final Lo3KiesrechtInhoud gezagsverhoudingInhoud =
                new Lo3KiesrechtInhoud(new Lo3AanduidingEuropeesKiesrecht(Integer.valueOf(WAARDE_ELEMENT_3110)),
                        new Lo3Datum(Integer.valueOf(WAARDE_ELEMENT_3120)), new Lo3Datum(
                                Integer.valueOf(WAARDE_ELEMENT_3130)), new Lo3AanduidingUitgeslotenKiesrecht(
                                WAARDE_ELEMENT_3810), new Lo3Datum(Integer.valueOf(WAARDE_ELEMENT_3820)));

        final Lo3Categorie<Lo3KiesrechtInhoud> categorieInhoudCategorie =
                new Lo3Categorie<Lo3KiesrechtInhoud>(gezagsverhoudingInhoud, null, historie, new Lo3Herkomst(
                        Lo3CategorieEnum.CATEGORIE_13, 0, 0));

        categorieInhoudLijst.add(categorieInhoudCategorie);

        final Lo3Stapel<Lo3KiesrechtInhoud> referentieInhoud =
                new Lo3Stapel<Lo3KiesrechtInhoud>(categorieInhoudLijst);

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<Lo3CategorieWaarde>();

        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_13, 1, 1);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3110, WAARDE_ELEMENT_3110);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3120, WAARDE_ELEMENT_3120);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3130, WAARDE_ELEMENT_3130);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3810, WAARDE_ELEMENT_3810);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3820, WAARDE_ELEMENT_3820);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8210, WAARDE_ELEMENT_8210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8220, WAARDE_ELEMENT_8220);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8230, WAARDE_ELEMENT_8230);

        categorieen.add(categorie);

        final Lo3Stapel<Lo3KiesrechtInhoud> gegenereerdeInhoud = parser.parse(categorieen);

        Assert.assertEquals(referentieInhoud.getMeestRecenteElement().getInhoud(), gegenereerdeInhoud
                .getMeestRecenteElement().getInhoud());
        Assert.assertEquals(referentieInhoud.getMeestRecenteElement().getHistorie(), gegenereerdeInhoud
                .getMeestRecenteElement().getHistorie());

    }

    @Test(expected = OnverwachteElementenExceptie.class)
    public void testKiesrechtParserParserOnverwachtElement() {

        final Lo3KiesrechtParser parser = new Lo3KiesrechtParser();

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<Lo3CategorieWaarde>();

        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_13, 1, 1);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0410, WAARDE_ELEMENT_0410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3110, WAARDE_ELEMENT_3110);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3120, WAARDE_ELEMENT_3120);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3130, WAARDE_ELEMENT_3130);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3810, WAARDE_ELEMENT_3810);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3820, WAARDE_ELEMENT_3820);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8210, WAARDE_ELEMENT_8210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8220, WAARDE_ELEMENT_8220);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8230, WAARDE_ELEMENT_8230);

        categorieen.add(categorie);

        try {
            parser.parse(categorieen);
        } catch (final OnverwachteElementenExceptie exceptie) {
            throw exceptie;
        }
    }

    @Test
    public void testKiesrechtParserParserNullWaarden() {

        final Lo3KiesrechtParser parser = new Lo3KiesrechtParser();

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<Lo3CategorieWaarde>();

        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(null, 1, 1);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3110, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3120, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3130, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3810, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3820, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8210, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8220, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8230, null);

        categorieen.add(categorie);

        parser.parse(categorieen);
    }

    @Test
    public void testKiesrechtParserParserGeenWaarden() {

        final Lo3KiesrechtParser parser = new Lo3KiesrechtParser();

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<Lo3CategorieWaarde>();

        parser.parse(categorieen);
    }

}
