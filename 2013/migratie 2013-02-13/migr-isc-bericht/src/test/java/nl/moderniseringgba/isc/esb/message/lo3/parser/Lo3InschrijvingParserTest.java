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
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieGeheimCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatiePKVolledigGeconverteerdCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;

import org.junit.Test;

public class Lo3InschrijvingParserTest {

    private static final String WAARDE_ELEMENT_0410 = "M";
    private static final String WAARDE_ELEMENT_6620 = "20121212";
    private static final String WAARDE_ELEMENT_6710 = "20121212";
    private static final String WAARDE_ELEMENT_6720 = "M";
    private static final String WAARDE_ELEMENT_6810 = "19500615";
    private static final String WAARDE_ELEMENT_6910 = "1905";
    private static final String WAARDE_ELEMENT_7010 = "0";
    private static final String WAARDE_ELEMENT_8010 = "1";
    private static final String WAARDE_ELEMENT_8020 = "20121219000000";
    private static final String WAARDE_ELEMENT_8710 = "N";

    @Test
    public void testInschrijvingParser() {

        final Lo3InschrijvingParser parser = new Lo3InschrijvingParser();

        final Lo3Historie historie = Lo3Historie.NULL_HISTORIE;
        final List<Lo3Categorie<Lo3InschrijvingInhoud>> categorieInhoudLijst =
                new ArrayList<Lo3Categorie<Lo3InschrijvingInhoud>>();

        final Lo3InschrijvingInhoud inschrijvingInhoud =
                new Lo3InschrijvingInhoud(new Lo3Datum(Integer.valueOf(WAARDE_ELEMENT_6620)), new Lo3Datum(
                        Integer.valueOf(WAARDE_ELEMENT_6710)), new Lo3RedenOpschortingBijhoudingCode(
                        WAARDE_ELEMENT_6720), new Lo3Datum(Integer.valueOf(WAARDE_ELEMENT_6810)),
                        new Lo3GemeenteCode(WAARDE_ELEMENT_6910), new Lo3IndicatieGeheimCode(
                                Integer.valueOf(WAARDE_ELEMENT_7010)), Integer.valueOf(WAARDE_ELEMENT_8010),
                        new Lo3Datumtijdstempel(Long.valueOf(WAARDE_ELEMENT_8020)),
                        new Lo3IndicatiePKVolledigGeconverteerdCode(WAARDE_ELEMENT_8710));

        final Lo3Categorie<Lo3InschrijvingInhoud> categorieInhoudCategorie =
                new Lo3Categorie<Lo3InschrijvingInhoud>(inschrijvingInhoud, null, historie, new Lo3Herkomst(
                        Lo3CategorieEnum.CATEGORIE_07, 0, 0));

        categorieInhoudLijst.add(categorieInhoudCategorie);

        final Lo3Stapel<Lo3InschrijvingInhoud> referentieInhoud =
                new Lo3Stapel<Lo3InschrijvingInhoud>(categorieInhoudLijst);

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<Lo3CategorieWaarde>();

        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_07, 1, 1);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6620, WAARDE_ELEMENT_6620);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6710, WAARDE_ELEMENT_6710);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6720, WAARDE_ELEMENT_6720);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6810, WAARDE_ELEMENT_6810);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6910, WAARDE_ELEMENT_6910);
        categorie.addElement(Lo3ElementEnum.ELEMENT_7010, WAARDE_ELEMENT_7010);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8010, WAARDE_ELEMENT_8010);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8020, WAARDE_ELEMENT_8020);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8710, WAARDE_ELEMENT_8710);

        categorieen.add(categorie);

        final Lo3Stapel<Lo3InschrijvingInhoud> gegenereerdeInhoud = parser.parse(categorieen);

        Assert.assertEquals(referentieInhoud.getMeestRecenteElement().getInhoud(), gegenereerdeInhoud
                .getMeestRecenteElement().getInhoud());
        Assert.assertEquals(referentieInhoud.getMeestRecenteElement().getHistorie(), gegenereerdeInhoud
                .getMeestRecenteElement().getHistorie());

    }

    @Test(expected = OnverwachteElementenExceptie.class)
    public void testInschrijvingParserOnverwachtElement() {

        final Lo3InschrijvingParser parser = new Lo3InschrijvingParser();

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<Lo3CategorieWaarde>();

        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_07, 1, 1);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0410, WAARDE_ELEMENT_0410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6620, WAARDE_ELEMENT_6620);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6710, WAARDE_ELEMENT_6710);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6720, WAARDE_ELEMENT_6720);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6810, WAARDE_ELEMENT_6810);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6910, WAARDE_ELEMENT_6910);
        categorie.addElement(Lo3ElementEnum.ELEMENT_7010, WAARDE_ELEMENT_7010);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8010, WAARDE_ELEMENT_8010);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8020, WAARDE_ELEMENT_8020);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8710, WAARDE_ELEMENT_8710);

        categorieen.add(categorie);

        try {
            parser.parse(categorieen);
        } catch (final OnverwachteElementenExceptie exceptie) {
            throw exceptie;
        }
    }

    @Test
    public void testInschrijvingParserNullWaarden() {

        final Lo3InschrijvingParser parser = new Lo3InschrijvingParser();

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<Lo3CategorieWaarde>();

        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(null, 1, 1);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6620, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6710, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6720, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6810, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6910, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_7010, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8010, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8020, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8710, null);

        categorieen.add(categorie);

        parser.parse(categorieen);
    }

    @Test
    public void testInschrijvingParserGeenWaarden() {

        final Lo3InschrijvingParser parser = new Lo3InschrijvingParser();

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<Lo3CategorieWaarde>();

        parser.parse(categorieen);
    }

}
