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
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3ReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingInhoudingVermissingNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AutoriteitVanAfgifteNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Signalering;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Test;

public class Lo3ReisdocumentParserTest extends AbstractParserTest {

    private static final String WAARDE_ELEMENT_8310 = "120000";

    @Test
    public void testReisdocumentParser() {
        final Lo3Onderzoek onderzoek = maakOnderzoek();
        final Lo3Historie historie = maakHistorie();
        final Lo3Documentatie documentatie = maakDocumentatie();

        // referentie
        final List<Lo3Categorie<Lo3ReisdocumentInhoud>> categorieInhoudLijst = new ArrayList<>();
        final Lo3ReisdocumentInhoud reisdocumentInhoud =
                new Lo3ReisdocumentInhoud(
                        new Lo3SoortNederlandsReisdocument(WAARDE_ELEMENT_3510, onderzoek),
                        new Lo3String(WAARDE_ELEMENT_3520, onderzoek),
                        new Lo3Datum(WAARDE_ELEMENT_3530, onderzoek),
                        new Lo3AutoriteitVanAfgifteNederlandsReisdocument(WAARDE_ELEMENT_3540, onderzoek),
                        new Lo3Datum(WAARDE_ELEMENT_3550, onderzoek),
                        new Lo3Datum(WAARDE_ELEMENT_3560, onderzoek),
                        new Lo3AanduidingInhoudingVermissingNederlandsReisdocument(WAARDE_ELEMENT_3570, onderzoek),
                        new Lo3Signalering(WAARDE_ELEMENT_3610, onderzoek));
        final Lo3Categorie<Lo3ReisdocumentInhoud> categorieInhoudCategorie =
                new Lo3Categorie<>(reisdocumentInhoud, documentatie, onderzoek, historie, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_12, 0, 0));
        categorieInhoudLijst.add(categorieInhoudCategorie);

        // parsed
        final Lo3Stapel<Lo3ReisdocumentInhoud> referentieInhoud = new Lo3Stapel<>(categorieInhoudLijst);
        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();
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
        categorie.addElement(Lo3ElementEnum.ELEMENT_8210, WAARDE_ELEMENT_8210);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8220, WAARDE_ELEMENT_8220);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8230, WAARDE_ELEMENT_8230);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8310, WAARDE_ELEMENT_8310);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDE_ELEMENT_8320);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8330, WAARDE_ELEMENT_8330);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8510, WAARDE_ELEMENT_8510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8610, WAARDE_ELEMENT_8610);
        categorieen.add(categorie);

        final Lo3ReisdocumentParser parser = new Lo3ReisdocumentParser();
        final Lo3Stapel<Lo3ReisdocumentInhoud> gegenereerdeInhoud = parser.parse(categorieen);

        final Lo3Categorie<Lo3ReisdocumentInhoud> referentieElement = referentieInhoud.getLaatsteElement();
        final Lo3Categorie<Lo3ReisdocumentInhoud> gegenereerdElement = gegenereerdeInhoud.getLaatsteElement();

        Assert.assertEquals(referentieElement.getInhoud(), gegenereerdElement.getInhoud());
        Assert.assertEquals(referentieElement.getHistorie(), gegenereerdElement.getHistorie());
        Assert.assertEquals(referentieElement.getDocumentatie(), gegenereerdElement.getDocumentatie());

    }

    @Test(expected = OnverwachteElementenExceptie.class)
    public void testReisdocumentParserOnverwachtElement() {

        final Lo3ReisdocumentParser parser = new Lo3ReisdocumentParser();

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();

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
        categorie.addElement(Lo3ElementEnum.ELEMENT_8510, WAARDE_ELEMENT_8510);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8610, WAARDE_ELEMENT_8610);

        categorieen.add(categorie);

        parser.parse(categorieen);
    }

    @Test
    public void testReisdocumentParserNullWaarden() {

        final Lo3ReisdocumentParser parser = new Lo3ReisdocumentParser();

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();

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
        categorie.addElement(Lo3ElementEnum.ELEMENT_8510, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8610, null);

        categorieen.add(categorie);

        parser.parse(categorieen);
    }

    @Test
    public void testReisdocumentParserGeenWaarden() {

        final Lo3ReisdocumentParser parser = new Lo3ReisdocumentParser();

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();

        parser.parse(categorieen);
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
        return Lo3CategorieEnum.CATEGORIE_12;
    }
}
