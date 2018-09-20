/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.notificator.service;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.serialisatie.notificator.service.impl.IdentifierServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class IdentifierServiceTest {

    private static final String INTEGER_STRING_1 = "12345";
    private static final String INTEGER_STRING_2 = "23456";
    private static final String INTEGER_STRING_3 = "34567";
    private static final String INTEGER_STRING_4 = "45678";
    private static final String INTEGER_STRING_5 = "56789";

    private IdentifierService identifierService = new IdentifierServiceImpl();

    private final String fouteIntegerString1 = "abcde";

    @Test
    public void testConverteerLijstString() {
        final List<String> lijstString = new ArrayList<String>();
        lijstString.add(INTEGER_STRING_1);
        lijstString.add(INTEGER_STRING_2);
        lijstString.add(INTEGER_STRING_3);
        lijstString.add(INTEGER_STRING_4);
        lijstString.add(INTEGER_STRING_5);

        final List<Integer> lijstInteger = identifierService.converteerLijstString(lijstString);

        Assert.assertNotNull(lijstInteger);
        Assert.assertTrue(lijstInteger.contains(Integer.valueOf(INTEGER_STRING_1)));
        Assert.assertEquals(lijstString.size(), lijstInteger.size());
    }

    @Test(expected = NumberFormatException.class)
    public void testConverteerFoutieveLijstString() {
        final List<String> lijstString = new ArrayList<String>();
        lijstString.add(INTEGER_STRING_1);
        lijstString.add(INTEGER_STRING_2);
        lijstString.add(INTEGER_STRING_3);
        lijstString.add(fouteIntegerString1);

        identifierService.converteerLijstString(lijstString);
    }

}
