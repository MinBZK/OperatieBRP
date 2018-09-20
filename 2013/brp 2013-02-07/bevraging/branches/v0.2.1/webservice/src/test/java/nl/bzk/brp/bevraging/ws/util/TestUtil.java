/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.ws.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;


public final class TestUtil {

    private TestUtil() {
    }

    /**
     * Vertaal een gewoon {@link java.util.Date} object naar een {@link javax.xml.datatype.XMLGregorianCalendar} object,
     * dat geschikt is voor een XML document.
     *
     * @param date Het {@link java.util.Date} object dat vertaald moet worden.
     * @return Het {@link javax.xml.datatype.XMLGregorianCalendar} object dat het resultaat is van de vertaling.
     */
    public static XMLGregorianCalendar toXMLGregorianCalendar(final Date date) {
        XMLGregorianCalendar xmlCalendar = null;
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            try {
                xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar((GregorianCalendar) calendar);
            } catch (DatatypeConfigurationException e) {
                throw new RuntimeException(e);
            }
        }
        return xmlCalendar;
    }
}
