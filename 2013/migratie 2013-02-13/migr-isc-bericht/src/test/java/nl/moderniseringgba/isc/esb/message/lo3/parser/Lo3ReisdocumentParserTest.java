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
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3ReisdocumentInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingBezitBuitenlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingInhoudingVermissingNederlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AutoriteitVanAfgifteNederlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LengteHouder;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Signalering;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;

import org.junit.Test;

public class Lo3ReisdocumentParserTest {

    private static final String WAARDE_ELEMENT_0410 = "M";
    private static final String WAARDE_ELEMENT_3510 = "Paspoort";
    private static final String WAARDE_ELEMENT_3520 = "NUP8DFRT3";
    private static final String WAARDE_ELEMENT_3530 = "20121001";
    private static final String WAARDE_ELEMENT_3540 = "GEM";
    private static final String WAARDE_ELEMENT_3550 = "20131231";
    private static final String WAARDE_ELEMENT_3560 = "20121101";
    private static final String WAARDE_ELEMENT_3570 = "N";
    private static final String WAARDE_ELEMENT_3580 = "12";
    private static final String WAARDE_ELEMENT_3610 = "5";
    private static final String WAARDE_ELEMENT_3710 = "0";
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
    public void testReisdocumentParser() {

        final Lo3ReisdocumentParser parser = new Lo3ReisdocumentParser();

        final Lo3Historie historie =
                new Lo3Historie(new Lo3IndicatieOnjuist(WAARDE_ELEMENT_8410), new Lo3Datum(
                        Integer.valueOf(WAARDE_ELEMENT_8510)), new Lo3Datum(Integer.valueOf(WAARDE_ELEMENT_8610)));
        final List<Lo3Categorie<Lo3ReisdocumentInhoud>> categorieInhoudLijst =
                new ArrayList<Lo3Categorie<Lo3ReisdocumentInhoud>>();

        final Lo3ReisdocumentInhoud reisdocumentInhoud =
                new Lo3ReisdocumentInhoud(new Lo3SoortNederlandsReisdocument(WAARDE_ELEMENT_3510),
                        WAARDE_ELEMENT_3520, new Lo3Datum(Integer.valueOf(WAARDE_ELEMENT_3530)),
                        new Lo3AutoriteitVanAfgifteNederlandsReisdocument(WAARDE_ELEMENT_3540), new Lo3Datum(
                                Integer.valueOf(WAARDE_ELEMENT_3550)), new Lo3Datum(
                                Integer.valueOf(WAARDE_ELEMENT_3560)),
                        new Lo3AanduidingInhoudingVermissingNederlandsReisdocument(WAARDE_ELEMENT_3570),
                        new Lo3LengteHouder(Integer.valueOf(WAARDE_ELEMENT_3580)), new Lo3Signalering(
                                Integer.valueOf(WAARDE_ELEMENT_3610)), new Lo3AanduidingBezitBuitenlandsReisdocument(
                                Integer.valueOf(WAARDE_ELEMENT_3710)));

        final Lo3Categorie<Lo3ReisdocumentInhoud> categorieInhoudCategorie =
                new Lo3Categorie<Lo3ReisdocumentInhoud>(reisdocumentInhoud, null, historie, new Lo3Herkomst(
                        Lo3CategorieEnum.CATEGORIE_12, 0, 0));

        categorieInhoudLijst.add(categorieInhoudCategorie);

        final Lo3Stapel<Lo3ReisdocumentInhoud> referentieInhoud =
                new Lo3Stapel<Lo3ReisdocumentInhoud>(categorieInhoudLijst);

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<Lo3CategorieWaarde>();

        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_12, 1, 1);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3510, WAARDE_ELEMENT_3510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3520, WAARDE_ELEMENT_3520);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3530, WAARDE_ELEMENT_3530);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3540, WAARDE_ELEMENT_3540);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3550, WAARDE_ELEMENT_3550);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3560, WAARDE_ELEMENT_3560);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3570, WAARDE_ELEMENT_3570);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3580, WAARDE_ELEMENT_3580);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3610, WAARDE_ELEMENT_3610);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3710, WAARDE_ELEMENT_3710);
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

        final Lo3Stapel<Lo3ReisdocumentInhoud> gegenereerdeInhoud = parser.parse(categorieen);

        Assert.assertEquals(referentieInhoud.getMeestRecenteElement().getInhoud(), gegenereerdeInhoud
                .getMeestRecenteElement().getInhoud());
        Assert.assertEquals(referentieInhoud.getMeestRecenteElement().getHistorie(), gegenereerdeInhoud
                .getMeestRecenteElement().getHistorie());

    }

    @Test(expected = OnverwachteElementenExceptie.class)
    public void testReisdocumentParserOnverwachtElement() {

        final Lo3ReisdocumentParser parser = new Lo3ReisdocumentParser();

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<Lo3CategorieWaarde>();

        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_12, 1, 1);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0410, WAARDE_ELEMENT_0410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3510, WAARDE_ELEMENT_3510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3520, WAARDE_ELEMENT_3520);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3530, WAARDE_ELEMENT_3530);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3540, WAARDE_ELEMENT_3540);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3550, WAARDE_ELEMENT_3550);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3560, WAARDE_ELEMENT_3560);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3570, WAARDE_ELEMENT_3570);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3580, WAARDE_ELEMENT_3580);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3610, WAARDE_ELEMENT_3610);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3710, WAARDE_ELEMENT_3710);
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
    public void testReisdocumentParserNullWaarden() {

        final Lo3ReisdocumentParser parser = new Lo3ReisdocumentParser();

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<Lo3CategorieWaarde>();

        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(null, 1, 1);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3510, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3520, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3530, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3540, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3550, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3560, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3570, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3580, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3610, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3710, null);
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
    public void testReisdocumentParserGeenWaarden() {

        final Lo3ReisdocumentParser parser = new Lo3ReisdocumentParser();

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<Lo3CategorieWaarde>();

        parser.parse(categorieen);
    }

}
