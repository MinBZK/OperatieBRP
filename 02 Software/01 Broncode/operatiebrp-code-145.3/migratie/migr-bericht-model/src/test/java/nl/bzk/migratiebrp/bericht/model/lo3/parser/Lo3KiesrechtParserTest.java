/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.parser;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3KiesrechtInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingEuropeesKiesrecht;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingUitgeslotenKiesrecht;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Test;

public class Lo3KiesrechtParserTest extends AbstractParserTest {

    @Test
    public void testKiesrechtParserParser() {

        final Lo3KiesrechtParser parser = new Lo3KiesrechtParser();

        final Lo3Historie historie = new Lo3Historie(null, null, null);
        final List<Lo3Categorie<Lo3KiesrechtInhoud>> categorieInhoudLijst = new ArrayList<>();

        final Lo3KiesrechtInhoud gezagsverhoudingInhoud =
                new Lo3KiesrechtInhoud(
                        new Lo3AanduidingEuropeesKiesrecht(Integer.valueOf(WAARDE_ELEMENT_3110)),
                        maakDatum(WAARDE_ELEMENT_3120, null),
                        maakDatum(WAARDE_ELEMENT_3130, null),
                        new Lo3AanduidingUitgeslotenKiesrecht(WAARDE_ELEMENT_3810),
                        maakDatum(WAARDE_ELEMENT_3820, null));
        final Lo3Categorie<Lo3KiesrechtInhoud> categorieInhoudCategorie =
                new Lo3Categorie<>(gezagsverhoudingInhoud, null, null, historie, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_13, 0, 0));
        categorieInhoudLijst.add(categorieInhoudCategorie);

        final Lo3Stapel<Lo3KiesrechtInhoud> referentieInhoud = new Lo3Stapel<>(categorieInhoudLijst);
        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_13, 1, 1);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3110, WAARDE_ELEMENT_3110);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3120, WAARDE_ELEMENT_3120);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3130, WAARDE_ELEMENT_3130);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3810, WAARDE_ELEMENT_3810);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3820, WAARDE_ELEMENT_3820);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8210, WAARDE_ELEMENT_8210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8220, WAARDE_ELEMENT_8220);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8230, WAARDE_ELEMENT_8230);

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();
        categorieen.add(categorie);

        final Lo3Stapel<Lo3KiesrechtInhoud> gegenereerdeInhoud = parser.parse(categorieen);

        Assert.assertEquals(referentieInhoud.getLaatsteElement().getInhoud(), gegenereerdeInhoud.getLaatsteElement().getInhoud());
        Assert.assertEquals(referentieInhoud.getLaatsteElement().getHistorie(), gegenereerdeInhoud.getLaatsteElement().getHistorie());

    }

    @Test(expected = OnverwachteElementenExceptie.class)
    public void testKiesrechtParserParserOnverwachtElement() {
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

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();
        categorieen.add(categorie);

        final Lo3KiesrechtParser parser = new Lo3KiesrechtParser();
        parser.parse(categorieen);
    }

    @Test
    public void testKiesrechtParserParserNullWaarden() {
        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(null, 1, 1);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3110, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3120, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3130, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3810, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_3820, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8210, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8220, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8230, null);

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();
        categorieen.add(categorie);

        final Lo3KiesrechtParser parser = new Lo3KiesrechtParser();
        final Lo3Stapel<Lo3KiesrechtInhoud> parsedInhoud = parser.parse(categorieen);
        Assert.assertNotNull(parsedInhoud.getLaatsteElement().getInhoud());
        Assert.assertNotNull(parsedInhoud.getLaatsteElement().getHistorie());
        Assert.assertNotNull(parsedInhoud.getLaatsteElement().getDocumentatie());
        Assert.assertNotNull(parsedInhoud.getLaatsteElement().getLo3Herkomst());
        Assert.assertNull(parsedInhoud.getLaatsteElement().getOnderzoek());
    }

    @Test
    public void testKiesrechtParserParserGeenWaarden() {
        final Lo3KiesrechtParser parser = new Lo3KiesrechtParser();
        final Lo3Stapel<Lo3KiesrechtInhoud> parsedInhoud = parser.parse(new ArrayList<Lo3CategorieWaarde>());
        Assert.assertNull(parsedInhoud);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.bericht.model.lo3.parser.AbstractParserTest#getGegevensInOnderzoek()
     */
    @Override
    String getGegevensInOnderzoek() {
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.bericht.model.lo3.parser.AbstractParserTest#getCategorie()
     */
    @Override
    Lo3CategorieEnum getCategorie() {
        return Lo3CategorieEnum.CATEGORIE_13;
    }

}
