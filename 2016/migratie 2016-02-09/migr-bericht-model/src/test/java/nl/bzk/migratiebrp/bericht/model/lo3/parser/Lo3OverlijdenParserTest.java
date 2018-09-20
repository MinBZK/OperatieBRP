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
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OverlijdenInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Test;

public class Lo3OverlijdenParserTest extends AbstractParserTest {

    private static final String WAARDE_ELEMENT_8310 = "060000";

    @Test
    public void testOverlijdenParser() {
        final Lo3Onderzoek onderzoek = maakOnderzoek();
        final Lo3Historie historie = maakHistorie();
        final Lo3Documentatie documentatie = maakDocumentatie();

        // referentie
        final List<Lo3Categorie<Lo3OverlijdenInhoud>> categorieInhoudLijst = new ArrayList<>();
        final Lo3OverlijdenInhoud overlijdenInhoud =
                new Lo3OverlijdenInhoud(maakDatum(WAARDE_ELEMENT_0810, onderzoek), new Lo3GemeenteCode(WAARDE_ELEMENT_0820, onderzoek), new Lo3LandCode(
                    WAARDE_ELEMENT_0830,
                    onderzoek));
        final Lo3Categorie<Lo3OverlijdenInhoud> categorieInhoudCategorie =
                new Lo3Categorie<>(overlijdenInhoud, documentatie, onderzoek, historie, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_06, 0, 0));
        categorieInhoudLijst.add(categorieInhoudCategorie);
        final Lo3Stapel<Lo3OverlijdenInhoud> referentieInhoud = new Lo3Stapel<>(categorieInhoudLijst);

        // parsed
        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_06, 1, 1);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0810, WAARDE_ELEMENT_0810);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0820, WAARDE_ELEMENT_0820);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0830, WAARDE_ELEMENT_0830);
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
        categorie.addElement(Lo3ElementEnum.ELEMENT_8810, WAARDE_ELEMENT_8810);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8820, WAARDE_ELEMENT_8820);

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();
        categorieen.add(categorie);

        final Lo3OverlijdenParser parser = new Lo3OverlijdenParser();
        final Lo3Stapel<Lo3OverlijdenInhoud> gegenereerdeInhoud = parser.parse(categorieen);

        final Lo3Categorie<Lo3OverlijdenInhoud> referentieElement = referentieInhoud.getLaatsteElement();
        final Lo3Categorie<Lo3OverlijdenInhoud> gegenereerdElement = gegenereerdeInhoud.getLaatsteElement();

        Assert.assertEquals(referentieElement.getInhoud(), gegenereerdElement.getInhoud());
        Assert.assertEquals(referentieElement.getHistorie(), gegenereerdElement.getHistorie());
        Assert.assertEquals(referentieElement.getDocumentatie(), gegenereerdElement.getDocumentatie());
    }

    @Test(expected = OnverwachteElementenExceptie.class)
    public void testOverlijdenParserOnverwachtElement() {
        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_06, 1, 1);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0410, WAARDE_ELEMENT_0410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0810, WAARDE_ELEMENT_0810);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0820, WAARDE_ELEMENT_0820);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0830, WAARDE_ELEMENT_0830);
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
        categorie.addElement(Lo3ElementEnum.ELEMENT_8810, WAARDE_ELEMENT_8810);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8820, WAARDE_ELEMENT_8820);

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();
        categorieen.add(categorie);

        final Lo3OverlijdenParser parser = new Lo3OverlijdenParser();
        parser.parse(categorieen);
    }

    @Test
    public void testOverlijdenParserNullWaarden() {
        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(null, 1, 1);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0810, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0820, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0830, null);
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
        categorie.addElement(Lo3ElementEnum.ELEMENT_8810, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8820, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8810, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8820, null);

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();
        categorieen.add(categorie);

        final Lo3OverlijdenParser parser = new Lo3OverlijdenParser();
        final Lo3Stapel<Lo3OverlijdenInhoud> parsedInhoud = parser.parse(categorieen);

        Assert.assertNotNull(parsedInhoud.getLaatsteElement().getInhoud());
        Assert.assertNotNull(parsedInhoud.getLaatsteElement().getHistorie());
        Assert.assertNotNull(parsedInhoud.getLaatsteElement().getDocumentatie());
        Assert.assertNotNull(parsedInhoud.getLaatsteElement().getLo3Herkomst());
        Assert.assertNull(parsedInhoud.getLaatsteElement().getOnderzoek());
    }

    @Test
    public void testOverlijdenParserGeenWaarden() {
        final Lo3OverlijdenParser parser = new Lo3OverlijdenParser();
        final Lo3Stapel<Lo3OverlijdenInhoud> parsedInhoud = parser.parse(new ArrayList<Lo3CategorieWaarde>());
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
        return Lo3CategorieEnum.CATEGORIE_06;
    }

}
