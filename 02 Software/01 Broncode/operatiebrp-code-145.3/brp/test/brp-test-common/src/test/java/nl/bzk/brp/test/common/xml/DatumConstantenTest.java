/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.common.xml;

import nl.bzk.brp.test.common.dsl.DatumConstanten;
import org.junit.Test;

/**
 * DatumConstantenTest.
 */
public class DatumConstantenTest {

    @Test
    public void testVandaag() {
        System.out.println(DatumConstanten.getDate("eergisteren"));
        System.out.println(DatumConstanten.getBasicIsoDateString("eergisteren"));

        System.out.println(DatumConstanten.getDate("gisteren"));
        System.out.println(DatumConstanten.getBasicIsoDateString("gisteren"));

        System.out.println(DatumConstanten.getDate("vandaag"));
        System.out.println(DatumConstanten.getBasicIsoDateString("vandaag"));

        System.out.println(DatumConstanten.getDate("morgen"));
        System.out.println(DatumConstanten.getBasicIsoDateString("morgen"));

        System.out.println(DatumConstanten.getDate("overmorgen"));
        System.out.println(DatumConstanten.getBasicIsoDateString("overmorgen"));
    }

    @Test
    public void testParseDate() {
        System.out.println(DatumConstanten.getDate("20010506"));
    }

}
