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
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerwijzingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGeheimCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Test;

public class Lo3VerwijzingParserTest extends AbstractParserTest {
    private static final String WAARDE_ELEMENT_8310 = "210000";

    @Test
    public void testVerwijzingParser() {
        final Lo3Onderzoek onderzoek = maakOnderzoek();
        final Lo3Historie historie = maakHistorie();
        final Lo3Documentatie documentatie = maakDocumentatie();
        final Lo3VerwijzingInhoud verwijzingInhoud =
                new Lo3VerwijzingInhoud(
                    maakLo3Long(WAARDE_ELEMENT_0110, null),
                    maakLo3Integer(WAARDE_ELEMENT_0120, onderzoek),
                    maakLo3String(WAARDE_ELEMENT_0210, onderzoek),
                    new Lo3AdellijkeTitelPredikaatCode(WAARDE_ELEMENT_0220, onderzoek),
                    maakLo3String(WAARDE_ELEMENT_0230, onderzoek),
                    maakLo3String(WAARDE_ELEMENT_0240, onderzoek),
                    maakDatum(WAARDE_ELEMENT_0310, onderzoek),
                    new Lo3GemeenteCode(WAARDE_ELEMENT_0320, onderzoek),
                    new Lo3LandCode(WAARDE_ELEMENT_0330, onderzoek),
                    new Lo3GemeenteCode(WAARDE_ELEMENT_0910, onderzoek),
                    maakDatum(WAARDE_ELEMENT_0920, onderzoek),
                    new Lo3IndicatieGeheimCode(Integer.valueOf(WAARDE_ELEMENT_7010)));
        final Lo3Categorie<Lo3VerwijzingInhoud> categorieInhoudCategorie =
                new Lo3Categorie<>(verwijzingInhoud, documentatie, onderzoek, historie, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_21, 0, 0));
        final List<Lo3Categorie<Lo3VerwijzingInhoud>> categorieInhoudLijst = new ArrayList<>();
        categorieInhoudLijst.add(categorieInhoudCategorie);

        final Lo3Stapel<Lo3VerwijzingInhoud> referentieInhoud = new Lo3Stapel<>(categorieInhoudLijst);
        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_21, 1, 1);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0110, WAARDE_ELEMENT_0110);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0120, WAARDE_ELEMENT_0120);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0210, WAARDE_ELEMENT_0210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0220, WAARDE_ELEMENT_0220);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0230, WAARDE_ELEMENT_0230);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0240, WAARDE_ELEMENT_0240);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0310, WAARDE_ELEMENT_0310);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0320, WAARDE_ELEMENT_0320);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0330, WAARDE_ELEMENT_0330);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0910, WAARDE_ELEMENT_0910);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0920, WAARDE_ELEMENT_0920);
        categorie.addElement(Lo3ElementEnum.ELEMENT_7010, WAARDE_ELEMENT_7010);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8310, WAARDE_ELEMENT_8310);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDE_ELEMENT_8320);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8330, WAARDE_ELEMENT_8330);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8410, WAARDE_ELEMENT_8410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8510, WAARDE_ELEMENT_8510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8610, WAARDE_ELEMENT_8610);
        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();
        categorieen.add(categorie);

        final Lo3VerwijzingParser parser = new Lo3VerwijzingParser();
        final Lo3Stapel<Lo3VerwijzingInhoud> gegenereerdeInhoud = parser.parse(categorieen);

        final Lo3Categorie<Lo3VerwijzingInhoud> referentieElement = referentieInhoud.getLaatsteElement();
        final Lo3Categorie<Lo3VerwijzingInhoud> gegenereerdElement = gegenereerdeInhoud.getLaatsteElement();

        Assert.assertEquals(referentieElement.getInhoud(), gegenereerdElement.getInhoud());
        Assert.assertEquals(referentieElement.getHistorie(), gegenereerdElement.getHistorie());
        Assert.assertEquals(referentieElement.getDocumentatie(), gegenereerdElement.getDocumentatie());

    }

    @Test(expected = OnverwachteElementenExceptie.class)
    public void testInschrijvingParserOnverwachtElement() {
        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_21, 1, 1);
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

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();
        categorieen.add(categorie);

        final Lo3VerwijzingParser parser = new Lo3VerwijzingParser();
        parser.parse(categorieen);
    }

    @Test
    public void testInschrijvingParserNullWaarden() {
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
        categorie.addElement(Lo3ElementEnum.ELEMENT_0910, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0920, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_7010, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8310, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8320, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8330, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8410, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8510, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8610, null);

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();
        categorieen.add(categorie);

        final Lo3VerwijzingParser parser = new Lo3VerwijzingParser();
        final Lo3Stapel<Lo3VerwijzingInhoud> parsedInhoud = parser.parse(categorieen);
        Assert.assertNotNull(parsedInhoud.getLaatsteElement().getInhoud());
        Assert.assertNotNull(parsedInhoud.getLaatsteElement().getHistorie());
        Assert.assertNotNull(parsedInhoud.getLaatsteElement().getDocumentatie());
        Assert.assertNotNull(parsedInhoud.getLaatsteElement().getLo3Herkomst());
        Assert.assertNull(parsedInhoud.getLaatsteElement().getOnderzoek());
    }

    @Test
    public void testInschrijvingParserGeenWaarden() {
        final Lo3VerwijzingParser parser = new Lo3VerwijzingParser();
        final Lo3Stapel<Lo3VerwijzingInhoud> parsedInhoud = parser.parse(new ArrayList<Lo3CategorieWaarde>());
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
        return Lo3CategorieEnum.CATEGORIE_21;
    }
}
