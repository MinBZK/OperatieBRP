/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3;

import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3PersoonslijstFormatter;
import nl.bzk.migratiebrp.bericht.model.lo3.parser.Lo3PersoonslijstParser;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Test;

public class ParseFormatTest {

    private static final String LO3_PL_STRING =
            "00700"
                    + "011640110010817238743501200092995889450210004Mart0240005Vries03100081990010103200040599033000460300410001M6110001E8110004059981200071 A9102851000819900101861000819900102"
                    + "021720110010192829389501200099911223340210006Jannie0240004Smit03100081969010103200041901033000460300410001M6210008199001018110004059981200071 A9102851000819900101861000819900102"
                    + "031750110010172625463201200093827261340210008Mitchell0240005Vries03100081970010103200041900033000460300410001M6210008199001018110004059981200071 A9102851000819900101861000819900102"
                    + "070586810008199001017010001080100040001802001700000000000000000"
                    + "08106091000405990920008199001011010001W102000405991030008199001011110001.7210001G851000819900101861000819900102";

    @Test
    public void test() throws BerichtSyntaxException {

        final List<Lo3CategorieWaarde> parsedCategorieen = Lo3Inhoud.parseInhoud(LO3_PL_STRING);

        String formatted = Lo3Inhoud.formatInhoud(parsedCategorieen);

        Assert.assertEquals(LO3_PL_STRING, formatted);

        final Lo3Persoonslijst parsedLo3Persoonslijst = new Lo3PersoonslijstParser().parse(parsedCategorieen);

        final List<Lo3CategorieWaarde> formattedCategorieen = new Lo3PersoonslijstFormatter().format(parsedLo3Persoonslijst);

        // Deze test doet erg weinig door de equals override op Lo3CategorieWaarde
        Assert.assertEquals(parsedCategorieen, formattedCategorieen);

        formatted = Lo3Inhoud.formatInhoud(formattedCategorieen);
        Assert.assertEquals(LO3_PL_STRING, formatted);
    }

    @Test
    public void testOnderzoekEnRNI() {
        final Lo3CategorieWaarde onderzoekEnRni = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        onderzoekEnRni.addElement(Lo3ElementEnum.ELEMENT_8310, "010000");
        onderzoekEnRni.addElement(Lo3ElementEnum.ELEMENT_8320, "20120101");
        onderzoekEnRni.addElement(Lo3ElementEnum.ELEMENT_8330, "20120201");
        onderzoekEnRni.addElement(Lo3ElementEnum.ELEMENT_8810, "1999");
        onderzoekEnRni.addElement(Lo3ElementEnum.ELEMENT_8820, "Omschrijving");

        final List<Lo3CategorieWaarde> catWaarden = new ArrayList<>();
        catWaarden.add(onderzoekEnRni);

        final String formatted = Lo3Inhoud.formatInhoud(catWaarden);
        Assert.assertNotNull(formatted);
        Assert.assertEquals("00078010738310006010000832000820120101833000820120201881000419998820012Omschrijving", formatted);
    }

}
