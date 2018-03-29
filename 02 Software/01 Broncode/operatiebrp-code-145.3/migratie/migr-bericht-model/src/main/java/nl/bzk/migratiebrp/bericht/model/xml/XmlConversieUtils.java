/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.xml;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Xml conversie utilities.
 */
public final class XmlConversieUtils {

    private static final int AANTAL_POSITIES_NANO_SECONDEN = 9;

    /**
     * Private constructor.
     */
    private XmlConversieUtils() {
        // Niet instantieerbaar.
    }

    /**
     * Converteer xml naar date.
     * @param xml xml
     * @return date
     */
    public static Date converteerXmlNaarDate(final XMLGregorianCalendar xml) {
        return xml == null ? null : xml.toGregorianCalendar().getTime();
    }

    /**
     * Converteer xml naar date.
     * @param cal cal
     * @return localdatetime
     */
    public static LocalDateTime converteerXmlNaarLocalDateTime(final XMLGregorianCalendar cal) {
        if (cal == null) {
            return null;
        }

        return LocalDateTime.of(cal.getYear(), cal.getMonth(), cal.getDay(), cal.getHour(), cal.getMinute(), cal.getSecond(),
                cal.getFractionalSecond().movePointRight(AANTAL_POSITIES_NANO_SECONDEN).intValue());
    }

    /**
     * Converteer date naar xml.
     * @param date date
     * @return XML gregorian calendar
     */
    public static XMLGregorianCalendar converteerDateNaarXml(final Date date) {
        if (date == null) {
            return null;
        }
        final GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
        } catch (final DatatypeConfigurationException e) {
            throw new IllegalArgumentException("Kon XML Datatype factory niet ophalen", e);
        }
    }

    /**
     * Converteer date naar xml.
     * @param date date
     * @return XML gregorian calendar
     */
    public static XMLGregorianCalendar converteerDateNaarXml(final LocalDateTime date) {
        XMLGregorianCalendar result = null;
        if (date != null) {
            try {
                result = DatatypeFactory.newInstance().newXMLGregorianCalendar();
                result.setYear(date.getYear());
                result.setMonth(date.getMonth().getValue());
                result.setDay(date.getDayOfMonth());
                result.setHour(date.getHour());
                result.setMinute(date.getMinute());
                result.setSecond(date.getSecond());
                result.setFractionalSecond(BigDecimal.valueOf(date.getNano(), AANTAL_POSITIES_NANO_SECONDEN));
            } catch (final DatatypeConfigurationException e) {
                throw new IllegalArgumentException("Kon XML Datatype factory niet ophalen", e);
            }
        }
        return result;
    }

}
