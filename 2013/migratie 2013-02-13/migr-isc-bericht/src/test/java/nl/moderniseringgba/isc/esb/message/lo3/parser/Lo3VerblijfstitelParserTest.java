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
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerblijfstitelInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingVerblijfstitelCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;

import org.junit.Test;

public class Lo3VerblijfstitelParserTest {

    private static final String WAARDE_ELEMENT_0410 = "M";
    private static final String WAARDE_ELEMENT_3910 = "20121213";
    private static final String WAARDE_ELEMENT_3920 = "1904";
    private static final String WAARDE_ELEMENT_3930 = "3010";
    private static final String WAARDE_ELEMENT_8310 = "0320";
    private static final String WAARDE_ELEMENT_8320 = "20121101";
    private static final String WAARDE_ELEMENT_8330 = "20121201";
    private static final String WAARDE_ELEMENT_8410 = "0";
    private static final String WAARDE_ELEMENT_8510 = "20121214";
    private static final String WAARDE_ELEMENT_8610 = "20121219";

    @Test
    public void testVerblijfstitelParser() {

        final Lo3VerblijfstitelParser parser = new Lo3VerblijfstitelParser();

        final Lo3Historie historie =
                new Lo3Historie(new Lo3IndicatieOnjuist(WAARDE_ELEMENT_8410), new Lo3Datum(
                        Integer.valueOf(WAARDE_ELEMENT_8510)), new Lo3Datum(Integer.valueOf(WAARDE_ELEMENT_8610)));
        final List<Lo3Categorie<Lo3VerblijfstitelInhoud>> categorieInhoudLijst =
                new ArrayList<Lo3Categorie<Lo3VerblijfstitelInhoud>>();

        final Lo3VerblijfstitelInhoud verblijfstitelInhoud =
                new Lo3VerblijfstitelInhoud(new Lo3AanduidingVerblijfstitelCode(WAARDE_ELEMENT_3910), new Lo3Datum(
                        Integer.valueOf(WAARDE_ELEMENT_3920)), new Lo3Datum(Integer.valueOf(WAARDE_ELEMENT_3930)));

        final Lo3Categorie<Lo3VerblijfstitelInhoud> categorieInhoudCategorie =
                new Lo3Categorie<Lo3VerblijfstitelInhoud>(verblijfstitelInhoud, null, historie, new Lo3Herkomst(
                        Lo3CategorieEnum.CATEGORIE_10, 0, 0));

        categorieInhoudLijst.add(categorieInhoudCategorie);

        final Lo3Stapel<Lo3VerblijfstitelInhoud> referentieInhoud =
                new Lo3Stapel<Lo3VerblijfstitelInhoud>(categorieInhoudLijst);

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<Lo3CategorieWaarde>();

        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_10, 1, 1);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3910, WAARDE_ELEMENT_3910);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3920, WAARDE_ELEMENT_3920);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3930, WAARDE_ELEMENT_3930);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8310, WAARDE_ELEMENT_8310);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDE_ELEMENT_8320);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8330, WAARDE_ELEMENT_8330);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8410, WAARDE_ELEMENT_8410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8510, WAARDE_ELEMENT_8510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8610, WAARDE_ELEMENT_8610);

        categorieen.add(categorie);

        final Lo3Stapel<Lo3VerblijfstitelInhoud> gegenereerdeInhoud = parser.parse(categorieen);

        Assert.assertEquals(referentieInhoud.getMeestRecenteElement().getInhoud(), gegenereerdeInhoud
                .getMeestRecenteElement().getInhoud());
        Assert.assertEquals(referentieInhoud.getMeestRecenteElement().getHistorie(), gegenereerdeInhoud
                .getMeestRecenteElement().getHistorie());

    }

    @Test(expected = OnverwachteElementenExceptie.class)
    public void testVerblijfstitelParserOnverwachtElement() {

        final Lo3VerblijfstitelParser parser = new Lo3VerblijfstitelParser();

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<Lo3CategorieWaarde>();

        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_10, 1, 1);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0410, WAARDE_ELEMENT_0410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3910, WAARDE_ELEMENT_3910);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3920, WAARDE_ELEMENT_3920);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3930, WAARDE_ELEMENT_3930);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8310, WAARDE_ELEMENT_8310);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDE_ELEMENT_8320);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8330, WAARDE_ELEMENT_8330);
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
    public void testVerblijfstitelParserNullWaarden() {

        final Lo3VerblijfstitelParser parser = new Lo3VerblijfstitelParser();

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<Lo3CategorieWaarde>();

        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(null, 1, 1);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3910, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3920, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3930, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8310, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8320, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8330, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8410, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8510, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8610, null);

        categorieen.add(categorie);

        parser.parse(categorieen);
    }

    @Test
    public void testVerblijfstitelParserGeenWaarden() {

        final Lo3VerblijfstitelParser parser = new Lo3VerblijfstitelParser();

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<Lo3CategorieWaarde>();

        parser.parse(categorieen);
    }

}
