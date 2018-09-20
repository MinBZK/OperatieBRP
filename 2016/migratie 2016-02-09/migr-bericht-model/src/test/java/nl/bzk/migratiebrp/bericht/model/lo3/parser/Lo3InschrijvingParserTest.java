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
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGeheimCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatiePKVolledigGeconverteerdCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Test;

public class Lo3InschrijvingParserTest extends AbstractParserTest {

    @Test
    public void testInschrijvingParser() {
        final Lo3Historie historie = Lo3Historie.NULL_HISTORIE;
        final List<Lo3Categorie<Lo3InschrijvingInhoud>> categorieInhoudLijst = new ArrayList<>();
        final Lo3InschrijvingInhoud inschrijvingInhoud =
                new Lo3InschrijvingInhoud(
                    maakDatum(WAARDE_ELEMENT_6620, null),
                    maakDatum(WAARDE_ELEMENT_6710, null),
                    new Lo3RedenOpschortingBijhoudingCode(WAARDE_ELEMENT_6720),
                    maakDatum(WAARDE_ELEMENT_6810, null),
                    new Lo3GemeenteCode(WAARDE_ELEMENT_6910),
                    new Lo3IndicatieGeheimCode(Integer.valueOf(WAARDE_ELEMENT_7010)),
                    maakDatum(WAARDE_ELEMENT_7110, null),
                    maakLo3String(WAARDE_ELEMENT_7120, null),
                    maakLo3Integer(WAARDE_ELEMENT_8010, null),
                    new Lo3Datumtijdstempel(Long.valueOf(WAARDE_ELEMENT_8020)),
                    new Lo3IndicatiePKVolledigGeconverteerdCode(WAARDE_ELEMENT_8710));

        final Lo3Categorie<Lo3InschrijvingInhoud> categorieInhoudCategorie =
                new Lo3Categorie<>(inschrijvingInhoud, null, null, historie, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0));

        categorieInhoudLijst.add(categorieInhoudCategorie);

        final Lo3Stapel<Lo3InschrijvingInhoud> referentieInhoud = new Lo3Stapel<>(categorieInhoudLijst);

        final Lo3CategorieWaarde categorie = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_07, 1, 1);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6620, WAARDE_ELEMENT_6620);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6710, WAARDE_ELEMENT_6710);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6720, WAARDE_ELEMENT_6720);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6810, WAARDE_ELEMENT_6810);
        categorie.addElement(Lo3ElementEnum.ELEMENT_6910, WAARDE_ELEMENT_6910);
        categorie.addElement(Lo3ElementEnum.ELEMENT_7010, WAARDE_ELEMENT_7010);
        categorie.addElement(Lo3ElementEnum.ELEMENT_7110, WAARDE_ELEMENT_7110);
        categorie.addElement(Lo3ElementEnum.ELEMENT_7120, WAARDE_ELEMENT_7120);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8010, WAARDE_ELEMENT_8010);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8020, WAARDE_ELEMENT_8020);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8710, WAARDE_ELEMENT_8710);

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();
        categorieen.add(categorie);

        final Lo3InschrijvingParser parser = new Lo3InschrijvingParser();
        final Lo3Stapel<Lo3InschrijvingInhoud> gegenereerdeInhoud = parser.parse(categorieen);

        Assert.assertEquals(referentieInhoud.getLaatsteElement().getInhoud(), gegenereerdeInhoud.getLaatsteElement().getInhoud());
        Assert.assertEquals(referentieInhoud.getLaatsteElement().getHistorie(), gegenereerdeInhoud.getLaatsteElement().getHistorie());

    }

    @Test(expected = OnverwachteElementenExceptie.class)
    public void testInschrijvingParserOnverwachtElement() {
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

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();
        categorieen.add(categorie);

        final Lo3InschrijvingParser parser = new Lo3InschrijvingParser();
        parser.parse(categorieen);
    }

    @Test
    public void testInschrijvingParserNullWaarden() {
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

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();
        categorieen.add(categorie);

        final Lo3InschrijvingParser parser = new Lo3InschrijvingParser();
        final Lo3Stapel<Lo3InschrijvingInhoud> parsedInhoud = parser.parse(categorieen);
        Assert.assertNotNull(parsedInhoud.getLaatsteElement().getInhoud());
        Assert.assertNotNull(parsedInhoud.getLaatsteElement().getHistorie());
        Assert.assertNotNull(parsedInhoud.getLaatsteElement().getDocumentatie());
        Assert.assertNotNull(parsedInhoud.getLaatsteElement().getLo3Herkomst());
        Assert.assertNull(parsedInhoud.getLaatsteElement().getOnderzoek());
    }

    @Test
    public void testInschrijvingParserGeenWaarden() {
        final Lo3InschrijvingParser parser = new Lo3InschrijvingParser();
        final Lo3Stapel<Lo3InschrijvingInhoud> parsedInhoud = parser.parse(new ArrayList<Lo3CategorieWaarde>());
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
        return Lo3CategorieEnum.CATEGORIE_07;
    }
}
