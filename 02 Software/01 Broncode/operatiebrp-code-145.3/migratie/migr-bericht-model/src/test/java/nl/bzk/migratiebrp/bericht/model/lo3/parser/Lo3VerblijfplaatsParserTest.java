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
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingHuisnummer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AangifteAdreshouding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3FunctieAdres;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Huisnummer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieDocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Test;

public class Lo3VerblijfplaatsParserTest extends AbstractParserTest {

    private static final String WAARDE_ELEMENT_8310 = "080000";

    @Test
    public void testVerblijfplaatsParser() {
        final Lo3Historie historie = maakHistorie();
        final Lo3Documentatie documentatie = maakDocumentatie();
        final Lo3Onderzoek onderzoek = maakOnderzoek();

        final List<Lo3Categorie<Lo3VerblijfplaatsInhoud>> categorieInhoudLijst = new ArrayList<>();
        final Lo3VerblijfplaatsInhoud verblijfplaatsInhoud =
                new Lo3VerblijfplaatsInhoud(
                        new Lo3GemeenteCode(WAARDE_ELEMENT_0910, onderzoek),
                        maakDatum(WAARDE_ELEMENT_0920, onderzoek),
                        new Lo3FunctieAdres(WAARDE_ELEMENT_1010, onderzoek),
                        maakLo3String(WAARDE_ELEMENT_1020, onderzoek),
                        maakDatum(WAARDE_ELEMENT_1030, onderzoek),
                        maakLo3String(WAARDE_ELEMENT_1110, onderzoek),
                        maakLo3String(WAARDE_ELEMENT_1115, onderzoek),
                        new Lo3Huisnummer(WAARDE_ELEMENT_1120, onderzoek),
                        maakLo3Character(WAARDE_ELEMENT_1130, onderzoek),
                        maakLo3String(WAARDE_ELEMENT_1140, onderzoek),
                        new Lo3AanduidingHuisnummer(WAARDE_ELEMENT_1150, onderzoek),
                        maakLo3String(WAARDE_ELEMENT_1160, onderzoek),
                        maakLo3String(WAARDE_ELEMENT_1170, onderzoek),
                        maakLo3String(WAARDE_ELEMENT_1180, onderzoek),
                        maakLo3String(WAARDE_ELEMENT_1190, onderzoek),
                        maakLo3String(WAARDE_ELEMENT_1210, onderzoek),
                        new Lo3LandCode(WAARDE_ELEMENT_1310, onderzoek),
                        maakDatum(WAARDE_ELEMENT_1320, onderzoek),
                        maakLo3String(WAARDE_ELEMENT_1330, onderzoek),
                        maakLo3String(WAARDE_ELEMENT_1340, onderzoek),
                        maakLo3String(WAARDE_ELEMENT_1350, onderzoek),
                        new Lo3LandCode(WAARDE_ELEMENT_1410, onderzoek),
                        maakDatum(WAARDE_ELEMENT_1420, onderzoek),
                        new Lo3AangifteAdreshouding(WAARDE_ELEMENT_7210),
                        new Lo3IndicatieDocument(WAARDE_ELEMENT_7510, null));

        final Lo3Categorie<Lo3VerblijfplaatsInhoud> categorieInhoudCategorie =
                new Lo3Categorie<>(verblijfplaatsInhoud, documentatie, onderzoek, historie, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_08, 0, 0));

        categorieInhoudLijst.add(categorieInhoudCategorie);

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> referentieInhoud = new Lo3Stapel<>(categorieInhoudLijst);

        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_08, 1, 1);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0910, WAARDE_ELEMENT_0910);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0920, WAARDE_ELEMENT_0920);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1010, WAARDE_ELEMENT_1010);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1020, WAARDE_ELEMENT_1020);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1030, WAARDE_ELEMENT_1030);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1110, WAARDE_ELEMENT_1110);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1115, WAARDE_ELEMENT_1115);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1120, WAARDE_ELEMENT_1120);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1130, WAARDE_ELEMENT_1130);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1140, WAARDE_ELEMENT_1140);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1150, WAARDE_ELEMENT_1150);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1160, WAARDE_ELEMENT_1160);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1170, WAARDE_ELEMENT_1170);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1180, WAARDE_ELEMENT_1180);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1190, WAARDE_ELEMENT_1190);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1210, WAARDE_ELEMENT_1210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1310, WAARDE_ELEMENT_1310);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1320, WAARDE_ELEMENT_1320);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1330, WAARDE_ELEMENT_1330);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1340, WAARDE_ELEMENT_1340);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1350, WAARDE_ELEMENT_1350);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1410, WAARDE_ELEMENT_1410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1420, WAARDE_ELEMENT_1420);
        categorie.addElement(Lo3ElementEnum.ELEMENT_7210, WAARDE_ELEMENT_7210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_7510, WAARDE_ELEMENT_7510);
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

        final Lo3VerblijfplaatsParser parser = new Lo3VerblijfplaatsParser();
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> gegenereerdeInhoud = parser.parse(categorieen);

        final Lo3Categorie<Lo3VerblijfplaatsInhoud> referentieElement = referentieInhoud.getLaatsteElement();
        final Lo3Categorie<Lo3VerblijfplaatsInhoud> gegenereerdElement = gegenereerdeInhoud.getLaatsteElement();

        Assert.assertEquals(referentieElement.getInhoud(), gegenereerdElement.getInhoud());
        Assert.assertEquals(referentieElement.getHistorie(), gegenereerdElement.getHistorie());
        Assert.assertEquals(referentieElement.getDocumentatie(), gegenereerdElement.getDocumentatie());

    }

    @Test(expected = OnverwachteElementenExceptie.class)
    public void testVerblijfplaatsParserOnverwachtElement() {
        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_08, 1, 1);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0410, WAARDE_ELEMENT_0410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0910, WAARDE_ELEMENT_0910);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0920, WAARDE_ELEMENT_0920);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1010, WAARDE_ELEMENT_1010);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1020, WAARDE_ELEMENT_1020);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1030, WAARDE_ELEMENT_1030);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1110, WAARDE_ELEMENT_1110);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1115, WAARDE_ELEMENT_1115);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1120, WAARDE_ELEMENT_1120);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1130, WAARDE_ELEMENT_1130);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1140, WAARDE_ELEMENT_1140);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1150, WAARDE_ELEMENT_1150);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1160, WAARDE_ELEMENT_1160);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1170, WAARDE_ELEMENT_1170);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1180, WAARDE_ELEMENT_1180);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1190, WAARDE_ELEMENT_1190);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1210, WAARDE_ELEMENT_1210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1310, WAARDE_ELEMENT_1310);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1320, WAARDE_ELEMENT_1320);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1330, WAARDE_ELEMENT_1330);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1340, WAARDE_ELEMENT_1340);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1350, WAARDE_ELEMENT_1350);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1410, WAARDE_ELEMENT_1410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1420, WAARDE_ELEMENT_1420);
        categorie.addElement(Lo3ElementEnum.ELEMENT_7210, WAARDE_ELEMENT_7210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_7510, WAARDE_ELEMENT_7510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8310, WAARDE_ELEMENT_8310);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDE_ELEMENT_8320);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8330, WAARDE_ELEMENT_8330);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8410, WAARDE_ELEMENT_8410);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8510, WAARDE_ELEMENT_8510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8610, WAARDE_ELEMENT_8610);

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();
        categorieen.add(categorie);

        final Lo3VerblijfplaatsParser parser = new Lo3VerblijfplaatsParser();
        parser.parse(categorieen);
    }

    @Test
    public void testVerblijfplaatsParserNullWaarden() {
        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(null, 1, 1);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0910, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_0920, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1010, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1020, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1030, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1110, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1115, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1120, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1130, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1140, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1150, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1160, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1170, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1180, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1190, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1210, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1310, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1320, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1330, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1340, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1350, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1410, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_1420, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_7210, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_7510, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8310, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8320, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8330, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8410, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8510, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8610, null);

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();
        categorieen.add(categorie);

        final Lo3VerblijfplaatsParser parser = new Lo3VerblijfplaatsParser();
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> parsedInhoud = parser.parse(categorieen);

        Assert.assertNotNull(parsedInhoud.getLaatsteElement().getInhoud());
        Assert.assertNotNull(parsedInhoud.getLaatsteElement().getHistorie());
        Assert.assertNotNull(parsedInhoud.getLaatsteElement().getDocumentatie());
        Assert.assertNotNull(parsedInhoud.getLaatsteElement().getLo3Herkomst());
        Assert.assertNull(parsedInhoud.getLaatsteElement().getOnderzoek());
    }

    @Test
    public void testVerblijfplaatsParserGeenWaarden() {
        final Lo3VerblijfplaatsParser parser = new Lo3VerblijfplaatsParser();
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> parsedInhoud = parser.parse(new ArrayList<Lo3CategorieWaarde>());
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
        return Lo3CategorieEnum.CATEGORIE_08;
    }
}
