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
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortVerbintenis;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Test;

public class Lo3HuwelijkOfGpParserTest extends AbstractParserTest {

    private static final String WAARDE_ELEMENT_8310 = "050000";

    @Test
    public void testHuwelijkOfGpParser() {

        final Lo3Historie historie = maakHistorie();
        final Lo3Onderzoek onderzoek = maakOnderzoek();
        final Lo3Documentatie documentatie = maakDocumentatie();

        final Lo3HuwelijkOfGpInhoud huwelijkOfGpInhoud =
                new Lo3HuwelijkOfGpInhoud(
                    maakLo3Long(WAARDE_ELEMENT_0110, onderzoek),
                    maakLo3Integer(WAARDE_ELEMENT_0120, onderzoek),
                    maakLo3String(WAARDE_ELEMENT_0210, onderzoek),
                    new Lo3AdellijkeTitelPredikaatCode(WAARDE_ELEMENT_0220, onderzoek),
                    maakLo3String(WAARDE_ELEMENT_0230, onderzoek),
                    maakLo3String(WAARDE_ELEMENT_0240, onderzoek),
                    maakDatum(WAARDE_ELEMENT_0310, onderzoek),
                    new Lo3GemeenteCode(WAARDE_ELEMENT_0320, onderzoek),
                    new Lo3LandCode(WAARDE_ELEMENT_0330, onderzoek),
                    new Lo3Geslachtsaanduiding(WAARDE_ELEMENT_0410, onderzoek),
                    maakDatum(WAARDE_ELEMENT_0610, onderzoek),
                    new Lo3GemeenteCode(WAARDE_ELEMENT_0620, onderzoek),
                    new Lo3LandCode(WAARDE_ELEMENT_0630, onderzoek),
                    maakDatum(WAARDE_ELEMENT_0710, onderzoek),
                    new Lo3GemeenteCode(WAARDE_ELEMENT_0720, onderzoek),
                    new Lo3LandCode(WAARDE_ELEMENT_0730, onderzoek),
                    new Lo3RedenOntbindingHuwelijkOfGpCode(WAARDE_ELEMENT_0740, onderzoek),
                    new Lo3SoortVerbintenis(WAARDE_ELEMENT_1510, onderzoek));

        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> huwelijkOfGpInhoudCategorie =
                new Lo3Categorie<>(huwelijkOfGpInhoud, documentatie, onderzoek, historie, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_05, 0, 0));

        final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> huwelijkOfGpInhoudLijst = new ArrayList<>();
        huwelijkOfGpInhoudLijst.add(huwelijkOfGpInhoudCategorie);
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> referentieInhoud = new Lo3Stapel<>(huwelijkOfGpInhoudLijst);

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
        categorie.addElement(Lo3ElementEnum.ELEMENT_8330, WAARDE_ELEMENT_8330);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8410, WAARDE_ELEMENT_8410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8510, WAARDE_ELEMENT_8510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8610, WAARDE_ELEMENT_8610);

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();
        categorieen.add(categorie);

        final Lo3HuwelijkOfGpParser parser = new Lo3HuwelijkOfGpParser();
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> gegenereerdeInhoud = parser.parse(categorieen);

        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> referentieElement = referentieInhoud.getLaatsteElement();
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> gegenereerdElement = gegenereerdeInhoud.getLaatsteElement();

        Assert.assertEquals(referentieElement.getInhoud(), gegenereerdElement.getInhoud());
        Assert.assertEquals(referentieElement.getHistorie(), gegenereerdElement.getHistorie());
        Assert.assertEquals(referentieElement.getDocumentatie(), gegenereerdElement.getDocumentatie());
    }

    @Test(expected = OnverwachteElementenExceptie.class)
    public void testHuwelijkOfGpParserOnverwachtElement() {
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

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();
        categorieen.add(categorie);

        final Lo3HuwelijkOfGpParser parser = new Lo3HuwelijkOfGpParser();
        parser.parse(categorieen);
    }

    @Test
    public void testHuwelijkOfGpParserNullWaarden() {
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

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();
        categorieen.add(categorie);

        final Lo3HuwelijkOfGpParser parser = new Lo3HuwelijkOfGpParser();
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> parsedInhoud = parser.parse(categorieen);

        Assert.assertNotNull(parsedInhoud.getLaatsteElement().getInhoud());
        Assert.assertNotNull(parsedInhoud.getLaatsteElement().getHistorie());
        Assert.assertNotNull(parsedInhoud.getLaatsteElement().getDocumentatie());
        Assert.assertNotNull(parsedInhoud.getLaatsteElement().getLo3Herkomst());
        Assert.assertNull(parsedInhoud.getLaatsteElement().getOnderzoek());
    }

    @Test
    public void testHuwelijkOfGpParserGeenWaarden() {
        final Lo3HuwelijkOfGpParser parser = new Lo3HuwelijkOfGpParser();
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> parsedInhoud = parser.parse(new ArrayList<Lo3CategorieWaarde>());
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
        return Lo3CategorieEnum.CATEGORIE_05;
    }
}
