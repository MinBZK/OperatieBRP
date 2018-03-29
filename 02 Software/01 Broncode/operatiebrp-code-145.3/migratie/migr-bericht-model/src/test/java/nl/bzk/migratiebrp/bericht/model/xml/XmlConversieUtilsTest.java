/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.xml;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Date;
import javax.xml.datatype.XMLGregorianCalendar;
import org.junit.Assert;
import org.junit.Test;


public class XmlConversieUtilsTest {

    @Test
    public void testLocalDateTime() {
        // Maximale precisie
        final LocalDateTime original = LocalDateTime.of(2016, Month.AUGUST, 4, 12, 30, 7, 123456789);
        System.out.println("LocalDateTime: " + original);

        final XMLGregorianCalendar xml = XmlConversieUtils.converteerDateNaarXml(original);
        System.out.println("XML: " + xml);

        final LocalDateTime check = XmlConversieUtils.converteerXmlNaarLocalDateTime(xml);
        System.out.println("Check: " + check);

        Assert.assertEquals(original, check);
    }

    @Test
    public void testDate() {
        // Maximale precisie
        final Date original = new Date(1);
        System.out.println("Date: " + original);

        final XMLGregorianCalendar xml = XmlConversieUtils.converteerDateNaarXml(original);
        System.out.println("XML: " + xml);

        final Date check = XmlConversieUtils.converteerXmlNaarDate(xml);
        System.out.println("Check: " + check);

        Assert.assertEquals(original, check);
        Assert.assertEquals(original.getTime(), check.getTime());
    }

}
