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
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfstitelInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingVerblijfstitelCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Test;

public class Lo3VerblijfstitelParserTest extends AbstractParserTest {

    private static final String WAARDE_ELEMENT_8310 = "100000";

    @Test
    public void testVerblijfstitelParser() {
        final Lo3Onderzoek onderzoek = maakOnderzoek();
        final Lo3Historie historie = maakHistorie();
        final Lo3Documentatie documentatie = maakDocumentatie();

        // referentie
        final List<Lo3Categorie<Lo3VerblijfstitelInhoud>> categorieInhoudLijst = new ArrayList<>();
        final Lo3VerblijfstitelInhoud verblijfstitelInhoud =
                new Lo3VerblijfstitelInhoud(
                        new Lo3AanduidingVerblijfstitelCode(WAARDE_ELEMENT_3910, onderzoek),
                        maakDatum(WAARDE_ELEMENT_3920, onderzoek),
                        maakDatum(WAARDE_ELEMENT_3930, onderzoek));
        final Lo3Categorie<Lo3VerblijfstitelInhoud> categorieInhoudCategorie =
                new Lo3Categorie<>(verblijfstitelInhoud, documentatie, onderzoek, historie, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_10, 0, 0));
        categorieInhoudLijst.add(categorieInhoudCategorie);

        // parsed
        final Lo3Stapel<Lo3VerblijfstitelInhoud> referentieInhoud = new Lo3Stapel<>(categorieInhoudLijst);

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

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();
        categorieen.add(categorie);

        final Lo3VerblijfstitelParser parser = new Lo3VerblijfstitelParser();
        final Lo3Stapel<Lo3VerblijfstitelInhoud> gegenereerdeInhoud = parser.parse(categorieen);

        final Lo3Categorie<Lo3VerblijfstitelInhoud> referentieElement = referentieInhoud.getLaatsteElement();
        final Lo3Categorie<Lo3VerblijfstitelInhoud> gegenereerdElement = gegenereerdeInhoud.getLaatsteElement();

        Assert.assertEquals(referentieElement.getInhoud(), gegenereerdElement.getInhoud());
        Assert.assertEquals(referentieElement.getHistorie(), gegenereerdElement.getHistorie());
        Assert.assertEquals(referentieElement.getDocumentatie(), gegenereerdElement.getDocumentatie());
    }

    @Test(expected = OnverwachteElementenExceptie.class)
    public void testVerblijfstitelParserOnverwachtElement() {
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

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();
        categorieen.add(categorie);

        final Lo3VerblijfstitelParser parser = new Lo3VerblijfstitelParser();
        parser.parse(categorieen);
    }

    @Test
    public void testVerblijfstitelParserNullWaarden() {
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

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();
        categorieen.add(categorie);

        final Lo3VerblijfstitelParser parser = new Lo3VerblijfstitelParser();
        final Lo3Stapel<Lo3VerblijfstitelInhoud> parsedInhoud = parser.parse(categorieen);

        Assert.assertNotNull(parsedInhoud.getLaatsteElement().getInhoud());
        Assert.assertNotNull(parsedInhoud.getLaatsteElement().getHistorie());
        Assert.assertNotNull(parsedInhoud.getLaatsteElement().getDocumentatie());
        Assert.assertNotNull(parsedInhoud.getLaatsteElement().getLo3Herkomst());
        Assert.assertNull(parsedInhoud.getLaatsteElement().getOnderzoek());
    }

    @Test
    public void testVerblijfstitelParserGeenWaarden() {
        final Lo3VerblijfstitelParser parser = new Lo3VerblijfstitelParser();
        final Lo3Stapel<Lo3VerblijfstitelInhoud> parsedInhoud = parser.parse(new ArrayList<Lo3CategorieWaarde>());
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
        return Lo3CategorieEnum.CATEGORIE_10;
    }

}
