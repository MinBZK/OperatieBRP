/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingNaamgebruikCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Test;

public class Lo3PersoonParserTest extends AbstractParserTest {

    private static final String WAARDE_ELEMENT_8310 = "010000";

    @Test
    public void testPersoonParser() {
        final Lo3Onderzoek onderzoek = maakOnderzoek();
        final Lo3Historie historie = maakHistorie();
        final Lo3Documentatie documentatie = maakDocumentatie();

        final Lo3PersoonInhoud persoonInhoud =
                new Lo3PersoonInhoud(
                        maakLo3String(WAARDE_ELEMENT_0110, null),
                        maakLo3String(WAARDE_ELEMENT_0120, onderzoek),
                        maakLo3String(WAARDE_ELEMENT_0210, onderzoek),
                        new Lo3AdellijkeTitelPredikaatCode(WAARDE_ELEMENT_0220, onderzoek),
                        maakLo3String(WAARDE_ELEMENT_0230, onderzoek),
                        maakLo3String(WAARDE_ELEMENT_0240, onderzoek),
                        maakDatum(WAARDE_ELEMENT_0310, onderzoek),
                        new Lo3GemeenteCode(WAARDE_ELEMENT_0320, onderzoek),
                        new Lo3LandCode(WAARDE_ELEMENT_0330, onderzoek),
                        new Lo3Geslachtsaanduiding(WAARDE_ELEMENT_0410, onderzoek),
                        maakLo3String(WAARDE_ELEMENT_2010, null),
                        maakLo3String(WAARDE_ELEMENT_2020, null),
                        new Lo3AanduidingNaamgebruikCode(WAARDE_ELEMENT_6110, onderzoek));

        final Lo3Categorie<Lo3PersoonInhoud> categorieInhoudCategorie =
                new Lo3Categorie<>(persoonInhoud, documentatie, onderzoek, historie, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0));

        final List<Lo3Categorie<Lo3PersoonInhoud>> categorieInhoudLijst = new ArrayList<>();
        categorieInhoudLijst.add(categorieInhoudCategorie);

        final Lo3Stapel<Lo3PersoonInhoud> referentieInhoud = new Lo3Stapel<>(categorieInhoudLijst);

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
        categorie.addElement(Lo3ElementEnum.ELEMENT_8810, WAARDE_ELEMENT_8810);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8820, WAARDE_ELEMENT_8820);

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();
        categorieen.add(categorie);

        final Lo3PersoonParser parser = new Lo3PersoonParser();
        final Lo3Stapel<Lo3PersoonInhoud> gegenereerdeInhoud = parser.parse(categorieen);

        final Lo3Categorie<Lo3PersoonInhoud> referentieElement = referentieInhoud.getLaatsteElement();
        final Lo3Categorie<Lo3PersoonInhoud> gegenereerdElement = gegenereerdeInhoud.getLaatsteElement();

        Assert.assertEquals(referentieElement.getInhoud(), gegenereerdElement.getInhoud());
        Assert.assertEquals(referentieElement.getHistorie(), gegenereerdElement.getHistorie());
        Assert.assertEquals(referentieElement.getDocumentatie(), gegenereerdElement.getDocumentatie());
    }

    @Test(expected = OnverwachteElementenExceptie.class)
    public void testPersoonParserOnverwachtElement() {
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

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();
        categorieen.add(categorie);

        final Lo3PersoonParser parser = new Lo3PersoonParser();
        parser.parse(categorieen);
    }

    @Test
    public void testPersoonParserNullWaarden() {
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
        categorie.addElement(Lo3ElementEnum.ELEMENT_8810, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8820, null);

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();
        categorieen.add(categorie);

        final Lo3PersoonParser parser = new Lo3PersoonParser();
        final Lo3Stapel<Lo3PersoonInhoud> parsedInhoud = parser.parse(categorieen);

        Assert.assertNotNull(parsedInhoud.getLaatsteElement().getInhoud());
        Assert.assertNotNull(parsedInhoud.getLaatsteElement().getHistorie());
        Assert.assertNotNull(parsedInhoud.getLaatsteElement().getDocumentatie());
        Assert.assertNotNull(parsedInhoud.getLaatsteElement().getLo3Herkomst());
        Assert.assertNull(parsedInhoud.getLaatsteElement().getOnderzoek());
    }

    @Test
    public void testPersoonParserGeenWaarden() {
        final Lo3PersoonParser parser = new Lo3PersoonParser();
        final Lo3Stapel<Lo3PersoonInhoud> parsedInhoud = parser.parse(new ArrayList<>());
        Assert.assertNull(parsedInhoud);

    }

    @Test
    public void testOnterechtGevuldeDatumOpneming() {
        final Lo3CategorieWaarde categorieActueel = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 1, 1);
        categorieActueel.addElement(Lo3ElementEnum.ELEMENT_0110, WAARDE_ELEMENT_0110);
        categorieActueel.addElement(Lo3ElementEnum.ELEMENT_0120, WAARDE_ELEMENT_0120);
        categorieActueel.addElement(Lo3ElementEnum.ELEMENT_0210, WAARDE_ELEMENT_0210);
        categorieActueel.addElement(Lo3ElementEnum.ELEMENT_0220, WAARDE_ELEMENT_0220);
        categorieActueel.addElement(Lo3ElementEnum.ELEMENT_0230, WAARDE_ELEMENT_0230);
        categorieActueel.addElement(Lo3ElementEnum.ELEMENT_0240, WAARDE_ELEMENT_0240);
        categorieActueel.addElement(Lo3ElementEnum.ELEMENT_0310, WAARDE_ELEMENT_0310);
        categorieActueel.addElement(Lo3ElementEnum.ELEMENT_0320, WAARDE_ELEMENT_0320);
        categorieActueel.addElement(Lo3ElementEnum.ELEMENT_0330, WAARDE_ELEMENT_0330);
        categorieActueel.addElement(Lo3ElementEnum.ELEMENT_0410, WAARDE_ELEMENT_0410);
        categorieActueel.addElement(Lo3ElementEnum.ELEMENT_8110, WAARDE_ELEMENT_8110);
        categorieActueel.addElement(Lo3ElementEnum.ELEMENT_8120, WAARDE_ELEMENT_8120);
        categorieActueel.addElement(Lo3ElementEnum.ELEMENT_8510, WAARDE_ELEMENT_8510);
        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_51, 1, 1);
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

        final List<Lo3CategorieWaarde> categorieen = Arrays.asList(categorieActueel, categorie);

        final Lo3PersoonParser parser = new Lo3PersoonParser();
        final Lo3Stapel<Lo3PersoonInhoud> gegenereerdeInhoud = parser.parse(categorieen);
        Assert.assertEquals("Moeten twee categorieen zijn", 2, gegenereerdeInhoud.getCategorieen().size());
        Assert.assertEquals("Er moet een null historie zijn", new Lo3Historie(null, null, null), gegenereerdeInhoud.getCategorieen().get(1).getHistorie());
        Assert.assertNotNull("Documentatie moet gevuld zijn", gegenereerdeInhoud.getCategorieen().get(1).getDocumentatie());
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
        return Lo3CategorieEnum.CATEGORIE_01;
    }
}
