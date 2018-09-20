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
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingHuisnummer;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AangifteAdreshouding;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3FunctieAdres;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Huisnummer;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieDocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;

import org.junit.Test;

public class Lo3VerblijfplaatsParserTest {

    private static final String WAARDE_ELEMENT_0410 = "M";
    private static final String WAARDE_ELEMENT_0910 = "1904";
    private static final String WAARDE_ELEMENT_0920 = "19500615";
    private static final String WAARDE_ELEMENT_1010 = "W";
    private static final String WAARDE_ELEMENT_1020 = "1";
    private static final String WAARDE_ELEMENT_1030 = "20120501";
    private static final String WAARDE_ELEMENT_1110 = "Beekstraat";
    private static final String WAARDE_ELEMENT_1115 = "De flat";
    private static final String WAARDE_ELEMENT_1120 = "13";
    private static final String WAARDE_ELEMENT_1130 = "F";
    private static final String WAARDE_ELEMENT_1140 = "bis";
    private static final String WAARDE_ELEMENT_1150 = "4E ETAGE";
    private static final String WAARDE_ELEMENT_1160 = "1342 BX";
    private static final String WAARDE_ELEMENT_1170 = "Den Haag";
    private static final String WAARDE_ELEMENT_1180 = "H";
    private static final String WAARDE_ELEMENT_1190 = "L";
    private static final String WAARDE_ELEMENT_1210 = "De flat tegenover het grasveld van het malieveld";
    private static final String WAARDE_ELEMENT_1310 = "5012";
    private static final String WAARDE_ELEMENT_1320 = "20120701";
    private static final String WAARDE_ELEMENT_1330 = "365 First Avenue, New York";
    private static final String WAARDE_ELEMENT_1340 = "3650 First Avenue, New York";
    private static final String WAARDE_ELEMENT_1350 = "36500 First Avenue, New York";
    private static final String WAARDE_ELEMENT_1410 = "1010";
    private static final String WAARDE_ELEMENT_1420 = "20121201";
    private static final String WAARDE_ELEMENT_7210 = "BZM-aanmelding";
    private static final String WAARDE_ELEMENT_7510 = "0";
    private static final String WAARDE_ELEMENT_8310 = "0320";
    private static final String WAARDE_ELEMENT_8320 = "20121101";
    private static final String WAARDE_ELEMENT_8330 = "20121201";
    private static final String WAARDE_ELEMENT_8410 = "0";
    private static final String WAARDE_ELEMENT_8510 = "20121214";
    private static final String WAARDE_ELEMENT_8610 = "20121219";

    @Test
    public void testVerblijfplaatsParser() {

        final Lo3VerblijfplaatsParser parser = new Lo3VerblijfplaatsParser();

        final Lo3Historie historie =
                new Lo3Historie(new Lo3IndicatieOnjuist(WAARDE_ELEMENT_8410), new Lo3Datum(
                        Integer.valueOf(WAARDE_ELEMENT_8510)), new Lo3Datum(Integer.valueOf(WAARDE_ELEMENT_8610)));
        final List<Lo3Categorie<Lo3VerblijfplaatsInhoud>> categorieInhoudLijst =
                new ArrayList<Lo3Categorie<Lo3VerblijfplaatsInhoud>>();

        final Lo3VerblijfplaatsInhoud verblijfplaatsInhoud =
                new Lo3VerblijfplaatsInhoud(new Lo3GemeenteCode(WAARDE_ELEMENT_0910), new Lo3Datum(
                        Integer.valueOf(WAARDE_ELEMENT_0920)), new Lo3FunctieAdres(WAARDE_ELEMENT_1010),
                        WAARDE_ELEMENT_1020, new Lo3Datum(Integer.valueOf(WAARDE_ELEMENT_1030)), WAARDE_ELEMENT_1110,
                        WAARDE_ELEMENT_1115, new Lo3Huisnummer(Integer.valueOf(WAARDE_ELEMENT_1120)),
                        WAARDE_ELEMENT_1130.charAt(0), WAARDE_ELEMENT_1140, new Lo3AanduidingHuisnummer(
                                WAARDE_ELEMENT_1150), WAARDE_ELEMENT_1160, WAARDE_ELEMENT_1170, WAARDE_ELEMENT_1180,
                        WAARDE_ELEMENT_1190, WAARDE_ELEMENT_1210, new Lo3LandCode(WAARDE_ELEMENT_1310), new Lo3Datum(
                                Integer.valueOf(WAARDE_ELEMENT_1320)), WAARDE_ELEMENT_1330, WAARDE_ELEMENT_1340,
                        WAARDE_ELEMENT_1350, new Lo3LandCode(WAARDE_ELEMENT_1410), new Lo3Datum(
                                Integer.valueOf(WAARDE_ELEMENT_1420)), new Lo3AangifteAdreshouding(
                                WAARDE_ELEMENT_7210), new Lo3IndicatieDocument(Integer.valueOf(WAARDE_ELEMENT_7510)));

        final Lo3Categorie<Lo3VerblijfplaatsInhoud> categorieInhoudCategorie =
                new Lo3Categorie<Lo3VerblijfplaatsInhoud>(verblijfplaatsInhoud, null, historie, new Lo3Herkomst(
                        Lo3CategorieEnum.CATEGORIE_08, 0, 0));

        categorieInhoudLijst.add(categorieInhoudCategorie);

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> referentieInhoud =
                new Lo3Stapel<Lo3VerblijfplaatsInhoud>(categorieInhoudLijst);

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<Lo3CategorieWaarde>();

        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_08, 1, 1);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0910, WAARDE_ELEMENT_0910);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0920, WAARDE_ELEMENT_0920);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1010, WAARDE_ELEMENT_1010);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1020, WAARDE_ELEMENT_1020);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1030, WAARDE_ELEMENT_1030);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1110, WAARDE_ELEMENT_1110);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1115, WAARDE_ELEMENT_1115);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1120, WAARDE_ELEMENT_1120);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1130, WAARDE_ELEMENT_1130);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1140, WAARDE_ELEMENT_1140);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1150, WAARDE_ELEMENT_1150);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1160, WAARDE_ELEMENT_1160);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1170, WAARDE_ELEMENT_1170);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1180, WAARDE_ELEMENT_1180);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1190, WAARDE_ELEMENT_1190);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1210, WAARDE_ELEMENT_1210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1310, WAARDE_ELEMENT_1310);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1320, WAARDE_ELEMENT_1320);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1330, WAARDE_ELEMENT_1330);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1340, WAARDE_ELEMENT_1340);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1350, WAARDE_ELEMENT_1350);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1410, WAARDE_ELEMENT_1410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1420, WAARDE_ELEMENT_1420);
        categorie.addElement(Lo3ElementEnum.ELEMENT_7210, WAARDE_ELEMENT_7210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_7510, WAARDE_ELEMENT_7510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8310, WAARDE_ELEMENT_8310);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDE_ELEMENT_8320);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8330, WAARDE_ELEMENT_8330);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8410, WAARDE_ELEMENT_8410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8510, WAARDE_ELEMENT_8510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8610, WAARDE_ELEMENT_8610);

        categorieen.add(categorie);

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> gegenereerdeInhoud = parser.parse(categorieen);

        Assert.assertEquals(referentieInhoud.getMeestRecenteElement().getInhoud(), gegenereerdeInhoud
                .getMeestRecenteElement().getInhoud());
        Assert.assertEquals(referentieInhoud.getMeestRecenteElement().getHistorie(), gegenereerdeInhoud
                .getMeestRecenteElement().getHistorie());

    }

    @Test(expected = OnverwachteElementenExceptie.class)
    public void testVerblijfplaatsParserOnverwachtElement() {

        final Lo3VerblijfplaatsParser parser = new Lo3VerblijfplaatsParser();

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<Lo3CategorieWaarde>();

        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_08, 1, 1);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0410, WAARDE_ELEMENT_0410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0910, WAARDE_ELEMENT_0910);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0920, WAARDE_ELEMENT_0920);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1010, WAARDE_ELEMENT_1010);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1020, WAARDE_ELEMENT_1020);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1030, WAARDE_ELEMENT_1030);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1110, WAARDE_ELEMENT_1110);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1115, WAARDE_ELEMENT_1115);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1120, WAARDE_ELEMENT_1120);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1130, WAARDE_ELEMENT_1130);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1140, WAARDE_ELEMENT_1140);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1150, WAARDE_ELEMENT_1150);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1160, WAARDE_ELEMENT_1160);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1170, WAARDE_ELEMENT_1170);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1180, WAARDE_ELEMENT_1180);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1190, WAARDE_ELEMENT_1190);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1210, WAARDE_ELEMENT_1210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1310, WAARDE_ELEMENT_1310);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1320, WAARDE_ELEMENT_1320);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1330, WAARDE_ELEMENT_1330);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1340, WAARDE_ELEMENT_1340);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1350, WAARDE_ELEMENT_1350);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1410, WAARDE_ELEMENT_1410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1420, WAARDE_ELEMENT_1420);
        categorie.addElement(Lo3ElementEnum.ELEMENT_7210, WAARDE_ELEMENT_7210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_7510, WAARDE_ELEMENT_7510);
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
    public void testVerblijfplaatsParserNullWaarden() {

        final Lo3VerblijfplaatsParser parser = new Lo3VerblijfplaatsParser();

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<Lo3CategorieWaarde>();

        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(null, 1, 1);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0910, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0920, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1010, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1020, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1030, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1110, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1115, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1120, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1130, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1140, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1150, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1160, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1170, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1180, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1190, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1210, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1310, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1320, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1330, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1340, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1350, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1410, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1420, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_7210, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_7510, null);
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
    public void testVerblijfplaatsParserGeenWaarden() {

        final Lo3VerblijfplaatsParser parser = new Lo3VerblijfplaatsParser();

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<Lo3CategorieWaarde>();

        parser.parse(categorieen);
    }

}
