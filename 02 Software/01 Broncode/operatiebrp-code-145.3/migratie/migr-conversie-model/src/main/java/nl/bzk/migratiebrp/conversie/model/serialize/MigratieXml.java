/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.serialize;

import java.io.Reader;
import java.io.Writer;
import nl.bzk.algemeenbrp.util.xml.Xml;
import nl.bzk.algemeenbrp.util.xml.exception.ConfigurationException;
import nl.bzk.algemeenbrp.util.xml.exception.DecodeException;
import nl.bzk.algemeenbrp.util.xml.exception.EncodeException;
import nl.bzk.algemeenbrp.util.xml.exception.XmlException;
import nl.bzk.algemeenbrp.util.xml.model.CompositeObject;
import nl.bzk.algemeenbrp.util.xml.model.Configuration;
import nl.bzk.algemeenbrp.util.xml.model.XmlObject;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;

/**
 * Xml (met specifieke migratie configuratie).
 */
public final class MigratieXml {

    private static final Configuration XML_CONFIGURATIE;

    static {
        try {
            XML_CONFIGURATIE = new Configuration();
            final XmlObject<BrpActie> brpActieConfiguratie = XML_CONFIGURATIE.getXmlObjectFor(BrpActie.class);
            if (brpActieConfiguratie instanceof CompositeObject<?>) {
                XML_CONFIGURATIE.registerXmlObjectFor(BrpActie.class, new BrpActieObject((CompositeObject<BrpActie>) brpActieConfiguratie));
            }
        } catch (final ConfigurationException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private MigratieXml() {
        // Niet instantieerbaar
    }

    /**
     * Encodeer een gegeven object.
     * @param object object
     * @param writer writer om XML naar te schrijven
     * @param <T> type van het object
     * @throws ConfigurationException bij configuratie problemen (annoties op de klassen)
     * @throws EncodeException bij encodeer problemen
     */
    public static <T> void encode(final T object, final Writer writer) throws XmlException {
        Xml.encode(XML_CONFIGURATIE, object, writer);
    }

    /**
     * Decodeer een object.
     * @param clazz te decoderen object
     * @param reader reader om XML van de lezen
     * @param <T> type van het object
     * @return het gedecodeerde object
     * @throws ConfigurationException bij configuratie problemen (annoties op de klassen)
     * @throws DecodeException bij decodeer problemen
     */
    public static <T> T decode(final Class<T> clazz, final Reader reader) throws XmlException {
        return Xml.decode(XML_CONFIGURATIE, clazz, reader);
    }
}
