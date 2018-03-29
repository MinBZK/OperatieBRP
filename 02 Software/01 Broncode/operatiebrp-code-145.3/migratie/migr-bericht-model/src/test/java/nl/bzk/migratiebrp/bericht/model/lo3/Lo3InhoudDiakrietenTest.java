/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.util.gbav.GBACharacterSet;
import org.junit.Test;

public class Lo3InhoudDiakrietenTest {

    @Test
    public void test() throws BerichtSyntaxException, UnsupportedEncodingException {
        final String voornaam = "Hélène";

        final Lo3CategorieWaarde lo3Categorie = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        lo3Categorie.addElement(Lo3ElementEnum.ELEMENT_0210, voornaam);

        final String lo3 = Lo3Inhoud.formatInhoud(Collections.singletonList(lo3Categorie));

        Assert.assertTrue(GBACharacterSet.isValideTeletex(lo3));

        //
        final String berichtLengte = "00020"; // 5 lang
        final String categorie = "01"; // 2 lang
        final String categorieLengte = "015"; // 3 lang
        final String element = "0210"; // 4 lang
        final String elementLengte = "008"; // 3 lang, inhoud 8 lang 6 tekens waarvan 2 met 2 bytes
        final String elementWaarde = "H\u00c2el\u00c1ene";

        // é (LATIN SMALL LETTER E WITH ACUTE) wordt geencode als 0xc2 0x65 in teletex
        // è (LATIN SMALL LETTER E WITH GRAVE) wordt geencode als 0xc1 0x65 in teletex

        //
        final String inhoud = berichtLengte + categorie + categorieLengte + element + elementLengte + elementWaarde;

        Assert.assertEquals(inhoud, lo3);

        final List<Lo3CategorieWaarde> parsed = Lo3Inhoud.parseInhoud(lo3);
        Assert.assertEquals(lo3Categorie, parsed.get(0));

        final byte[] utf8Bytes = lo3.getBytes("UTF-8");
        Assert.assertEquals(27, utf8Bytes.length);

        final byte[] expected =
                new byte[]{'0',
                        '0',
                        '0',
                        '2',
                        '0',
                        '0',
                        '1',
                        '0',
                        '1',
                        '5',
                        '0',
                        '2',
                        '1',
                        '0',
                        '0',
                        '0',
                        '8',
                        'H',
                        (byte) 0xc3,
                        (byte) 0x82,
                        'e',
                        'l',
                        (byte) 0xc3,
                        (byte) 0x81,
                        'e',
                        'n',
                        'e'};

        // Het unicode teken 0+00C2 wordt in UTF-8 geencode als 0xc3 0x82

        Assert.assertTrue(Arrays.equals(expected, utf8Bytes));

        final String stringTerug = new String(utf8Bytes, "UTF-8");
        Assert.assertEquals(inhoud, stringTerug);

        Assert.assertTrue(GBACharacterSet.isValideTeletex(stringTerug));

        final List<Lo3CategorieWaarde> terug = Lo3Inhoud.parseInhoud(stringTerug);
        Assert.assertEquals(1, terug.size());

        final String voornaamTerug = terug.get(0).getElement(Lo3ElementEnum.ELEMENT_0210);
        Assert.assertEquals(voornaam, voornaamTerug);
    }

    @Test
    public void testTeletex() {
        Assert.assertTrue(GBACharacterSet.isValideTeletex("Lindstr\u00c8om"));
        Assert.assertFalse(GBACharacterSet.isValideTeletex("Lindström"));
    }

    @Test
    public void testUnicode() {
        Assert.assertTrue(GBACharacterSet.isValideUnicode("Lindström"));
    }
}
