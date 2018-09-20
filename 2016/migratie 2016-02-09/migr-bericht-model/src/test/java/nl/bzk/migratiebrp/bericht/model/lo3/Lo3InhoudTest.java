/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.lo3.parser.Lo3PersoonslijstParser;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Test;

public class Lo3InhoudTest {

    /* Lg01 bericht waarbij geslachtsnaam (02.40) wel voorkomt in cat01 maar met berichtlengte 0. Dus een lege string. */
    private static final String LO3_PL =
            "00692011590110010817238743501200092995889450210004Mart024000003100081990010103200040599033000460300410001M6110001E811"
                    + "0004059981200071 A91028510008199001018610008199001020217201100101928293895012000999"
                    + "11223340210006Jannie0240004Smit031000819690101032"
                    + "00041901033000460300410001M6210008199001018110004059981200071 A91028510008199001018"
                    + "61000819900102031750110010172625463201200093827261340210008Mitchell0240005Vries0310"
                    + "0081970010103200041900033000460300410001M6210008199001018110004059981200071 A910285"
                    + "10008199001018610008199001020705568100081990010170100010801000118020017000000000000"
                    + "0000008106091000405990920008199001011010001W102000405991030008199001011110001.72100"
                    + "01G851000819900101861000819900102";

    /**
     * Test dat een lg01 bericht met daarin een element met veldlengte 0 resulteert in het ontbreken van het element als
     * resultaat van de {Lo3PersoonslijstParser}. Het resultaat van de
     * {@link nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud#parseInhoud(String)} dient een lege string terug te geven
     * aangezien dat gegeven nodig is voor zoekcriteria (bij bijvoorbeeld het plaatsen van afnemersindicaties).
     * 
     * @throws BerichtSyntaxException
     */
    @Test
    public void testLeegElement() throws BerichtSyntaxException {
        final List<Lo3CategorieWaarde> lo3Pl = Lo3Inhoud.parseInhoud(LO3_PL);
        assertNotNull(lo3Pl);
        final Lo3CategorieWaarde cat1 = leesCategorie1(lo3Pl, Lo3CategorieEnum.CATEGORIE_01);
        assertNotNull(cat1);
        final String geslachtsnaam1 = cat1.getElementen().get(Lo3ElementEnum.ELEMENT_0240);
        assertEquals("", geslachtsnaam1);

        final Lo3CategorieWaarde cat2 = leesCategorie1(lo3Pl, Lo3CategorieEnum.CATEGORIE_02);
        assertNotNull(cat2);
        final String geslachtsnaam2 = cat2.getElementen().get(Lo3ElementEnum.ELEMENT_0240);
        assertNotNull(geslachtsnaam2);

        final Lo3Persoonslijst lo3Persoonlijst = new Lo3PersoonslijstParser().parse(lo3Pl);
        assertNull(lo3Persoonlijst.getPersoonStapel().getLo3ActueelVoorkomen().getInhoud().getGeslachtsnaam());
    }

    /**
     * Test dat een lg01 bericht met daarin een categorie met een verkeerde veldlengte resulteert in een
     * BerichtSyntaxException. In dit geval: voornaam 'Jeroen' is 6 karakters lang, terwijl er een veldlengte van 8
     * karakters is gespecificeerd.
     * 
     * @throws BerichtSyntaxException
     */
    @Test(expected = BerichtSyntaxException.class)
    public void testOngeldigeCategorieVeldLengte() throws BerichtSyntaxException {
        final String lg =
                "00911011780110010531964598501200098200647130210008Jeroen0240006Kooper03100081966082103200040014033000460300410001M6110001V821000405188220008199409308230003PKA851000819920808861000819940930021550210011Greet Marga0240005Olijk03100081942083103200040013033000460300410001V621000819660821821000405188220008199409308230002PK851000819660821861000819940930031500210005Klaas0240006Kooper03100081938111803200040013033000460300410001M621000819660821821000405188220008199409308230002PK85100081966082186100081994093004086051000400016310003001821000405188220008199409308230002PK851000819660821861000819940930070776810008199409306910004051870100010801000400038020017201207011435010008710001P08235091000406260920008199806221010001W1030008199806221110010S vd Oyeln1115038Baron Schimmelpenninck van der Oyelaan11200021611600062252EB1170011Voorschoten11800160626010010016001119001606262000100160017210001T851000820110316861000820110317";

        Lo3Inhoud.parseInhoud(lg);
    }

    private Lo3CategorieWaarde leesCategorie1(final List<Lo3CategorieWaarde> lo3Pl, final Lo3CategorieEnum lo3CategorieEnum) {
        Lo3CategorieWaarde result = null;
        for (final Lo3CategorieWaarde lo3Categorie : lo3Pl) {
            if (lo3Categorie.getCategorie().equals(lo3CategorieEnum)) {
                result = lo3Categorie;
                break;
            }
        }
        return result;
    }

}
