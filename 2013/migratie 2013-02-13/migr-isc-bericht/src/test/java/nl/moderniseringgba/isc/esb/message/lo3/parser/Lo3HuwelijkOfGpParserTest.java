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
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3SoortVerbintenis;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;

import org.junit.Test;

public class Lo3HuwelijkOfGpParserTest {

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
    private static final String WAARDE_ELEMENT_0610 = "20120427";
    private static final String WAARDE_ELEMENT_0620 = "1903";
    private static final String WAARDE_ELEMENT_0630 = "3010";
    private static final String WAARDE_ELEMENT_0710 = "20121001";
    private static final String WAARDE_ELEMENT_0720 = "1904";
    private static final String WAARDE_ELEMENT_0730 = "3010";
    private static final String WAARDE_ELEMENT_0740 = "Scheiding";
    private static final String WAARDE_ELEMENT_1510 = "Huwelijk";
    private static final String WAARDE_ELEMENT_8110 = "1904";
    private static final String WAARDE_ELEMENT_8120 = "A124543";
    private static final String WAARDE_ELEMENT_8210 = "1920";
    private static final String WAARDE_ELEMENT_8220 = "20121219";
    private static final String WAARDE_ELEMENT_8230 = "123456";
    private static final String WAARDE_ELEMENT_8310 = "0740";
    private static final String WAARDE_ELEMENT_8320 = "20121101";
    private static final String WAARDE_ELEMENT_8330 = "20121201";
    private static final String WAARDE_ELEMENT_8410 = "0";
    private static final String WAARDE_ELEMENT_8510 = "20121214";
    private static final String WAARDE_ELEMENT_8610 = "20121219";

    @Test
    public void testHuwelijkOfGpParser() {

        final Lo3HuwelijkOfGpParser parser = new Lo3HuwelijkOfGpParser();

        final Lo3Historie historie =
                new Lo3Historie(new Lo3IndicatieOnjuist(WAARDE_ELEMENT_8410), new Lo3Datum(
                        Integer.valueOf(WAARDE_ELEMENT_8510)), new Lo3Datum(Integer.valueOf(WAARDE_ELEMENT_8610)));
        final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> huwelijkOfGpInhoudLijst =
                new ArrayList<Lo3Categorie<Lo3HuwelijkOfGpInhoud>>();

        final Lo3HuwelijkOfGpInhoud huwelijkOfGpInhoud =
                new Lo3HuwelijkOfGpInhoud(Long.valueOf(WAARDE_ELEMENT_0110), Long.valueOf(WAARDE_ELEMENT_0120),
                        WAARDE_ELEMENT_0210, new Lo3AdellijkeTitelPredikaatCode(WAARDE_ELEMENT_0220),
                        WAARDE_ELEMENT_0230, WAARDE_ELEMENT_0240, new Lo3Datum(Integer.valueOf(WAARDE_ELEMENT_0310)),
                        new Lo3GemeenteCode(WAARDE_ELEMENT_0320), new Lo3LandCode(WAARDE_ELEMENT_0330),
                        new Lo3Geslachtsaanduiding(WAARDE_ELEMENT_0410), new Lo3Datum(
                                Integer.valueOf(WAARDE_ELEMENT_0610)), new Lo3GemeenteCode(WAARDE_ELEMENT_0620),
                        new Lo3LandCode(WAARDE_ELEMENT_0630), new Lo3Datum(Integer.valueOf(WAARDE_ELEMENT_0710)),
                        new Lo3GemeenteCode(WAARDE_ELEMENT_0720), new Lo3LandCode(WAARDE_ELEMENT_0730),
                        new Lo3RedenOntbindingHuwelijkOfGpCode(WAARDE_ELEMENT_0740), new Lo3SoortVerbintenis(
                                WAARDE_ELEMENT_1510));

        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> huwelijkOfGpInhoudCategorie =
                new Lo3Categorie<Lo3HuwelijkOfGpInhoud>(huwelijkOfGpInhoud, null, historie, new Lo3Herkomst(
                        Lo3CategorieEnum.CATEGORIE_05, 0, 0));

        huwelijkOfGpInhoudLijst.add(huwelijkOfGpInhoudCategorie);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> referentieInhoud =
                new Lo3Stapel<Lo3HuwelijkOfGpInhoud>(huwelijkOfGpInhoudLijst);

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<Lo3CategorieWaarde>();

        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_05, 1, 1);
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
        categorie.addElement(Lo3ElementEnum.ELEMENT_0610, WAARDE_ELEMENT_0610);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0620, WAARDE_ELEMENT_0620);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0630, WAARDE_ELEMENT_0630);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0710, WAARDE_ELEMENT_0710);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0720, WAARDE_ELEMENT_0720);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0730, WAARDE_ELEMENT_0730);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0740, WAARDE_ELEMENT_0740);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1510, WAARDE_ELEMENT_1510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8110, WAARDE_ELEMENT_8110);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8120, WAARDE_ELEMENT_8120);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8210, WAARDE_ELEMENT_8210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8220, WAARDE_ELEMENT_8220);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8230, WAARDE_ELEMENT_8230);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8310, WAARDE_ELEMENT_8310);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDE_ELEMENT_8320);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8410, WAARDE_ELEMENT_8410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8510, WAARDE_ELEMENT_8510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8610, WAARDE_ELEMENT_8610);

        categorieen.add(categorie);

        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> gegenereerdeInhoud = parser.parse(categorieen);

        Assert.assertEquals(referentieInhoud.getMeestRecenteElement().getInhoud(), gegenereerdeInhoud
                .getMeestRecenteElement().getInhoud());
        Assert.assertEquals(referentieInhoud.getMeestRecenteElement().getHistorie(), gegenereerdeInhoud
                .getMeestRecenteElement().getHistorie());

    }

    @Test(expected = OnverwachteElementenExceptie.class)
    public void testHuwelijkOfGpParserOnverwachtElement() {

        final Lo3HuwelijkOfGpParser parser = new Lo3HuwelijkOfGpParser();

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<Lo3CategorieWaarde>();

        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_05, 1, 1);
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
        categorie.addElement(Lo3ElementEnum.ELEMENT_0610, WAARDE_ELEMENT_0610);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0620, WAARDE_ELEMENT_0620);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0630, WAARDE_ELEMENT_0630);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0710, WAARDE_ELEMENT_0710);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0720, WAARDE_ELEMENT_0720);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0730, WAARDE_ELEMENT_0730);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0740, WAARDE_ELEMENT_0740);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1510, WAARDE_ELEMENT_1510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8110, WAARDE_ELEMENT_8110);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8120, WAARDE_ELEMENT_8120);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8210, WAARDE_ELEMENT_8210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8220, WAARDE_ELEMENT_8220);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8230, WAARDE_ELEMENT_8230);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8310, WAARDE_ELEMENT_8310);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDE_ELEMENT_8320);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8330, WAARDE_ELEMENT_8330);

        categorieen.add(categorie);

        try {
            parser.parse(categorieen);
        } catch (final OnverwachteElementenExceptie exceptie) {
            throw exceptie;
        }
    }

    @Test
    public void testHuwelijkOfGpParserNullWaarden() {

        final Lo3HuwelijkOfGpParser parser = new Lo3HuwelijkOfGpParser();

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
        categorie.addElement(Lo3ElementEnum.ELEMENT_0610, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0620, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0630, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0710, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0720, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0730, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0740, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1510, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8110, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8120, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8210, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8220, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8230, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8310, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8320, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8330, null);
        categorieen.add(categorie);

        parser.parse(categorieen);
    }

    @Test
    public void testHuwelijkOfGpParserGeenWaarden() {

        final Lo3HuwelijkOfGpParser parser = new Lo3HuwelijkOfGpParser();

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<Lo3CategorieWaarde>();

        parser.parse(categorieen);
    }

}
