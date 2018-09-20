/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * Xml utils.
 */
public final class XmlUtils {
    /**
     * Instantieert een nieuwe Xml utils.
     */
    private XmlUtils() {
    }

    private static final Logger LOGGER = Logger.getLogger(XmlUtils.class.getName());
    // this class is for supporting that we can use jaxB to convert to xml, so we can print it out.

    /**
     * Convert to xml internal.
     *
     * @param o o
     * @return byte array output stream
     * @throws JAXBException jAXB exception
     * @throws UnsupportedEncodingException unsupported encoding exception
     */
    private static ByteArrayOutputStream convertToXmlInternal(final Object o) throws JAXBException,
            UnsupportedEncodingException
    {
        JAXBContext context = JAXBContext.newInstance(o.getClass());
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        m.marshal(o, baos);
        return baos;
    }

    /**
     * Close stream.
     *
     * @param baos baos
     */
    private static void closeStream(final ByteArrayOutputStream baos) {
        if (baos != null) {
            try {
                baos.flush();
                baos.close();
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Convert to xml.
     *
     * @param o o
     * @return string
     */
    public static String convertToXml(final Object o) {
        String xml = null;

        if (null == o) {
            return null;
        } else {
            ByteArrayOutputStream baos = null;
            try {
                baos = convertToXmlInternal(o);
                xml = baos.toString("UTF-8");
            } catch (UnsupportedEncodingException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            } catch (JAXBException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            } finally {
                closeStream(baos);
                return xml;
            }
        }
    }

}
