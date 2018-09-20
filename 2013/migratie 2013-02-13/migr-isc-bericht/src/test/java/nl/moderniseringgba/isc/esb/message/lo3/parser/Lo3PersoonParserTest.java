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
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingNaamgebruikCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;

import org.junit.Test;

public class Lo3PersoonParserTest {

    private static final String WAARDE_ELEMENT_0110 = "1234567890";
    private static final String WAARDE_ELEMENT_0120 = "213454121";
    private static final String WAARDE_ELEMENT_0210 = "Johannes";
    private static final String WAARDE_ELEMENT_0220 = "B";
    private static final String WAARDE_ELEMENT_0230 = "van";
    private static final String WAARDE_ELEMENT_0240 = "Bijsterveld";
    private static final String WAARDE_ELEMENT_0310 = "19500615";
    private static final String WAARDE_ELEMENT_0320 = "1904";
    private static final String WAARDE_ELEMENT_0330 = "3010";
    private static final String WAARDE_ELEMENT_0410 = "M";
    private static final String WAARDE_ELEMENT_0510 = "3010";
    private static final String WAARDE_ELEMENT_2010 = "541231651";
    private static final String WAARDE_ELEMENT_2020 = "845145112";
    private static final String WAARDE_ELEMENT_6110 = "E";
    private static final String WAARDE_ELEMENT_8110 = "1904";
    private static final String WAARDE_ELEMENT_8120 = "A3542352";
    private static final String WAARDE_ELEMENT_8210 = "1920";
    private static final String WAARDE_ELEMENT_8220 = "20121219";
    private static final String WAARDE_ELEMENT_8230 = "123456";
    private static final String WAARDE_ELEMENT_8310 = "0320";
    private static final String WAARDE_ELEMENT_8320 = "20121101";
    private static final String WAARDE_ELEMENT_8330 = "20121201";
    private static final String WAARDE_ELEMENT_8410 = "0";
    private static final String WAARDE_ELEMENT_8510 = "20121214";
    private static final String WAARDE_ELEMENT_8610 = "20121219";

    @Test
    public void testPersoonParser() {

        final Lo3PersoonParser parser = new Lo3PersoonParser();

        final Lo3Historie historie =
                new Lo3Historie(new Lo3IndicatieOnjuist(WAARDE_ELEMENT_8410), new Lo3Datum(
                        Integer.valueOf(WAARDE_ELEMENT_8510)), new Lo3Datum(Integer.valueOf(WAARDE_ELEMENT_8610)));
        final List<Lo3Categorie<Lo3PersoonInhoud>> categorieInhoudLijst =
                new ArrayList<Lo3Categorie<Lo3PersoonInhoud>>();

        final Lo3PersoonInhoud persoonInhoud =
                new Lo3PersoonInhoud(Long.valueOf(WAARDE_ELEMENT_0110), Long.valueOf(WAARDE_ELEMENT_0120),
                        WAARDE_ELEMENT_0210, new Lo3AdellijkeTitelPredikaatCode(WAARDE_ELEMENT_0220),
                        WAARDE_ELEMENT_0230, WAARDE_ELEMENT_0240, new Lo3Datum(Integer.valueOf(WAARDE_ELEMENT_0310)),
                        new Lo3GemeenteCode(WAARDE_ELEMENT_0320), new Lo3LandCode(WAARDE_ELEMENT_0330),
                        new Lo3Geslachtsaanduiding(WAARDE_ELEMENT_0410), new Lo3AanduidingNaamgebruikCode(
                                WAARDE_ELEMENT_6110), Long.valueOf(WAARDE_ELEMENT_2010),
                        Long.valueOf(WAARDE_ELEMENT_2020));

        final Lo3Categorie<Lo3PersoonInhoud> categorieInhoudCategorie =
                new Lo3Categorie<Lo3PersoonInhoud>(persoonInhoud, null, historie, new Lo3Herkomst(
                        Lo3CategorieEnum.CATEGORIE_01, 0, 0));

        categorieInhoudLijst.add(categorieInhoudCategorie);

        final Lo3Stapel<Lo3PersoonInhoud> referentieInhoud = new Lo3Stapel<Lo3PersoonInhoud>(categorieInhoudLijst);

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<Lo3CategorieWaarde>();

        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 1, 1);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0110, WAARDE_ELEMENT_0110);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0120, WAARDE_ELEMENT_0120);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0210, WAARDE_ELEMENT_0210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0220, WAARDE_ELEMENT_0220);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0230, WAARDE_ELEMENT_0230);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0240, WAARDE_ELEMENT_0240);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0310, WAARDE_ELEMENT_0310);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0320, WAARDE_ELEMENT_0320);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0330, WAARDE_ELEMENT_0330);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0410, WAARDE_ELEMENT_0410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_2010, WAARDE_ELEMENT_2010);
        categorie.addElement(Lo3ElementEnum.ELEMENT_2020, WAARDE_ELEMENT_2020);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6110, WAARDE_ELEMENT_6110);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8110, WAARDE_ELEMENT_8110);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8120, WAARDE_ELEMENT_8120);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8210, WAARDE_ELEMENT_8210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8220, WAARDE_ELEMENT_8220);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8230, WAARDE_ELEMENT_8230);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8310, WAARDE_ELEMENT_8310);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDE_ELEMENT_8320);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8330, WAARDE_ELEMENT_8330);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8410, WAARDE_ELEMENT_8410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8510, WAARDE_ELEMENT_8510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8610, WAARDE_ELEMENT_8610);

        categorieen.add(categorie);

        final Lo3Stapel<Lo3PersoonInhoud> gegenereerdeInhoud = parser.parse(categorieen);

        Assert.assertEquals(referentieInhoud.getMeestRecenteElement().getInhoud(), gegenereerdeInhoud
                .getMeestRecenteElement().getInhoud());
        Assert.assertEquals(referentieInhoud.getMeestRecenteElement().getHistorie(), gegenereerdeInhoud
                .getMeestRecenteElement().getHistorie());

    }

    @Test(expected = OnverwachteElementenExceptie.class)
    public void testPersoonParserOnverwachtElement() {

        final Lo3PersoonParser parser = new Lo3PersoonParser();

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<Lo3CategorieWaarde>();

        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 1, 1);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0110, WAARDE_ELEMENT_0110);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0120, WAARDE_ELEMENT_0120);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0210, WAARDE_ELEMENT_0210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0220, WAARDE_ELEMENT_0220);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0230, WAARDE_ELEMENT_0230);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0240, WAARDE_ELEMENT_0240);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0310, WAARDE_ELEMENT_0310);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0320, WAARDE_ELEMENT_0320);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0330, WAARDE_ELEMENT_0330);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0410, WAARDE_ELEMENT_0410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0510, WAARDE_ELEMENT_0510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_2010, WAARDE_ELEMENT_2010);
        categorie.addElement(Lo3ElementEnum.ELEMENT_2020, WAARDE_ELEMENT_2020);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6110, WAARDE_ELEMENT_6110);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8110, WAARDE_ELEMENT_8110);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8120, WAARDE_ELEMENT_8120);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8210, WAARDE_ELEMENT_8210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8220, WAARDE_ELEMENT_8220);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8230, WAARDE_ELEMENT_8230);
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
    public void testPersoonParserNullWaarden() {

        final Lo3PersoonParser parser = new Lo3PersoonParser();

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<Lo3CategorieWaarde>();

        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(null, 1, 1);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0110, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0120, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0210, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0220, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0230, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0240, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0310, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0320, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0330, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0410, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_2010, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_2020, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6110, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8110, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8120, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8210, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8220, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8230, null);
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
    public void testPersoonParserGeenWaarden() {

        final Lo3PersoonParser parser = new Lo3PersoonParser();

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<Lo3CategorieWaarde>();

        parser.parse(categorieen);
    }

}
