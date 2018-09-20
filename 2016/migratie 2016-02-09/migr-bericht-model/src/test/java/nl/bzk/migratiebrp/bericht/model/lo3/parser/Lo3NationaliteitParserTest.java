/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.parser;

import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingBijzonderNederlandschap;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Test;

public class Lo3NationaliteitParserTest extends AbstractParserTest {

    private static final String WAARDE_ELEMENT_8310 = "040000";

    @Test
    public void testNationaliteitParser() {
        final Lo3Onderzoek onderzoek = maakOnderzoek();
        final Lo3Historie historie = maakHistorie();
        final Lo3Documentatie documentatie = maakDocumentatie();

        final Lo3NationaliteitInhoud categorieInhoud =
                new Lo3NationaliteitInhoud(
                    new Lo3NationaliteitCode(WAARDE_ELEMENT_0510, onderzoek),
                    new Lo3RedenNederlandschapCode(WAARDE_ELEMENT_6310,onderzoek),
                    new Lo3RedenNederlandschapCode(WAARDE_ELEMENT_6410,onderzoek),
                    new Lo3AanduidingBijzonderNederlandschap(WAARDE_ELEMENT_6510, onderzoek));
        final Lo3Categorie<Lo3NationaliteitInhoud> categorieInhoudCategorie =
                new Lo3Categorie<>(categorieInhoud, documentatie, onderzoek, historie, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 0, 0));

        final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorieInhoudLijst = new ArrayList<>();
        categorieInhoudLijst.add(categorieInhoudCategorie);
        final Lo3Stapel<Lo3NationaliteitInhoud> referentieInhoud = new Lo3Stapel<>(categorieInhoudLijst);

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();
        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_04, 1, 1);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0510, WAARDE_ELEMENT_0510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6310, WAARDE_ELEMENT_6310);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6410, WAARDE_ELEMENT_6410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6510, WAARDE_ELEMENT_6510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8210, WAARDE_ELEMENT_8210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8220, WAARDE_ELEMENT_8220);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8230, WAARDE_ELEMENT_8230);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8310, WAARDE_ELEMENT_8310);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDE_ELEMENT_8320);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8330, WAARDE_ELEMENT_8330);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8410, WAARDE_ELEMENT_8410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8510, WAARDE_ELEMENT_8510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8610, WAARDE_ELEMENT_8610);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8810, WAARDE_ELEMENT_8810);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8820, WAARDE_ELEMENT_8820);
        categorieen.add(categorie);

        final Lo3NationaliteitParser parser = new Lo3NationaliteitParser();
        final Lo3Stapel<Lo3NationaliteitInhoud> gegenereerdeInhoud = parser.parse(categorieen);

        final Lo3Categorie<Lo3NationaliteitInhoud> referentieElement = referentieInhoud.getLaatsteElement();
        final Lo3Categorie<Lo3NationaliteitInhoud> gegenereerdElement = gegenereerdeInhoud.getLaatsteElement();

        Assert.assertEquals(referentieElement.getInhoud(), gegenereerdElement.getInhoud());
        Assert.assertEquals(referentieElement.getHistorie(), gegenereerdElement.getHistorie());
        Assert.assertEquals(referentieElement.getDocumentatie(), gegenereerdElement.getDocumentatie());
    }

    @Test(expected = OnverwachteElementenExceptie.class)
    public void testNationaliteitsParserOnverwachtElement() {
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
        categorie.addElement(Lo3ElementEnum.ELEMENT_8810, WAARDE_ELEMENT_8810);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8820, WAARDE_ELEMENT_8820);

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();
        categorieen.add(categorie);

        final Lo3NationaliteitParser parser = new Lo3NationaliteitParser();
        parser.parse(categorieen);
    }

    @Test
    public void testNationaliteitsParserNullWaarden() {
        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(null, 1, 1);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0510, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6310, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6410, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6510, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8210, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8220, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8220, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8310, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8320, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8330, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8410, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8510, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8610, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8810, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8820, null);

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();
        categorieen.add(categorie);

        final Lo3NationaliteitParser parser = new Lo3NationaliteitParser();
        final Lo3Stapel<Lo3NationaliteitInhoud> parsedInhoud = parser.parse(categorieen);

        Assert.assertNotNull(parsedInhoud.getLaatsteElement().getInhoud());
        Assert.assertNotNull(parsedInhoud.getLaatsteElement().getHistorie());
        Assert.assertNotNull(parsedInhoud.getLaatsteElement().getDocumentatie());
        Assert.assertNotNull(parsedInhoud.getLaatsteElement().getLo3Herkomst());
        Assert.assertNull(parsedInhoud.getLaatsteElement().getOnderzoek());
    }

    @Test
    public void testNationaliteitsParserGeenWaarden() {
        final Lo3NationaliteitParser parser = new Lo3NationaliteitParser();
        final Lo3Stapel<Lo3NationaliteitInhoud> parsedInhoud = parser.parse(new ArrayList<Lo3CategorieWaarde>());
        Assert.assertNull(parsedInhoud);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.bericht.model.lo3.parser.AbstractParserTest#getGegevensInOnderzoek()
     */
    @Override
    String getGegevensInOnderzoek() {
        return WAARDE_ELEMENT_8310;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.bericht.model.lo3.parser.AbstractParserTest#getCategorie()
     */
    @Override
    Lo3CategorieEnum getCategorie() {
        return Lo3CategorieEnum.CATEGORIE_04;
    }
}
