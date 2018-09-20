/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import junit.framework.Assert;
import nl.ictu.spg.common.util.conversion.GBACharacterSet;
import nl.moderniseringgba.isc.esb.message.BerichtSyntaxException;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;

import org.junit.Test;

public class Lo3InhoudDiakrietenTest {

    @Test
    public void test() throws BerichtSyntaxException, UnsupportedEncodingException {
        final String voornaam = "Hélène";

        final Lo3CategorieWaarde lo3Categorie = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        lo3Categorie.addElement(Lo3ElementEnum.ELEMENT_0210, voornaam);

        final String lo3 = Lo3Inhoud.formatInhoud(Collections.singletonList(lo3Categorie));

        Assert.assertTrue(GBACharacterSet.isValidTeletex(lo3));

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

        System.out.println("Inhoud: " + inhoud);
        System.out.println("Lo3: " + lo3);

        Assert.assertEquals(inhoud, lo3);

        final List<Lo3CategorieWaarde> parsed = Lo3Inhoud.parseInhoud(lo3);
        Assert.assertEquals(lo3Categorie, parsed.get(0));

        final byte[] utf8Bytes = lo3.getBytes("UTF-8");
        Assert.assertEquals(27, utf8Bytes.length);

        for (final byte b : utf8Bytes) {
            System.out.println("BYte: " + Byte.toString(b) + " (0x" + Integer.toHexString(b) + ") ->  " + ((char) b));
        }

        final byte[] expected =
                new byte[] { '0', '0', '0', '2', '0', '0', '1', '0', '1', '5', '0', '2', '1', '0', '0', '0', '8',
                        'H', (byte) 0xc3, (byte) 0x82, 'e', 'l', (byte) 0xc3, (byte) 0x81, 'e', 'n', 'e' };

        // Het unicode teken 0+00C2 wordt in UTF-8 geencode als 0xc3 0x82

        Assert.assertTrue(Arrays.equals(expected, utf8Bytes));

        final String stringTerug = new String(utf8Bytes, "UTF-8");
        Assert.assertEquals(inhoud, stringTerug);

        Assert.assertTrue(GBACharacterSet.isValidTeletex(stringTerug));

        final List<Lo3CategorieWaarde> terug = Lo3Inhoud.parseInhoud(stringTerug);
        Assert.assertEquals(1, terug.size());

        final String voornaamTerug = terug.get(0).getElement(Lo3ElementEnum.ELEMENT_0210);
        Assert.assertEquals(voornaam, voornaamTerug);
    }
}
