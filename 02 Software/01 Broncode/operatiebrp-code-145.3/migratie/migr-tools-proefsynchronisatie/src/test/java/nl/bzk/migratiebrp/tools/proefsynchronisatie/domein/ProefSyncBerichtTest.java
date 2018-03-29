/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.proefsynchronisatie.domein;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import org.junit.Test;

public class ProefSyncBerichtTest {

    private static final String AFZENDER = "059901";
    private static final Timestamp BERICHT_DATUM = new Timestamp(System.currentTimeMillis());
    private static final String
            LG01_BERICHT =
            "00000000Lg01201301221619285991010101025000000000000765011970110010101010102501200096947984590210013Peter Richard0220002JH0230007van "
                    + "den0240006Heuvel03100081990011003200040599033000460300410001M6110001E8110004059981200071 "
                    +
                    "A1284851000819900110861000819900112021800110010101010129101200095859039060210006Hennie0240012Meerdervoort03100081949020203200041900033000460300410001V6210008199001108110004059981200071 A1284851000819900110861000819900112031870110010101010132701200094647322190210005Harry0230007van den0240006Heuvel03100081951010103200041901033000460300410001M6210008199001108110004059981200071 A128485100081990011086100081990011207058681000819900110701000108010004000180200171990011000000000008118091000405990920008199001101010001W102000419011030008199001101110013Viooltjeslaan7210001I851000819900110861000819900112";

    @Test
    public void testGetterEnSetters() {
        final ProefSynchronisatieBericht proefSynchronisatieBericht = new ProefSynchronisatieBericht();
        proefSynchronisatieBericht.setBerichtDatum(BERICHT_DATUM);
        proefSynchronisatieBericht.setBericht(LG01_BERICHT);
        proefSynchronisatieBericht.setAfzender(AFZENDER);

        assertEquals(BERICHT_DATUM, proefSynchronisatieBericht.getBerichtDatum());
        assertEquals(LG01_BERICHT, proefSynchronisatieBericht.getBericht());
        assertEquals(AFZENDER, proefSynchronisatieBericht.getAfzender());
    }
}
