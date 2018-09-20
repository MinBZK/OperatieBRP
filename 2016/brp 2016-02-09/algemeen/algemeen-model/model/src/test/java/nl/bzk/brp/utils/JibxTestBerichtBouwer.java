/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.utils;

import java.io.InputStream;
import java.io.StringWriter;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;


public final class JibxTestBerichtBouwer {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private JibxTestBerichtBouwer() {
    }

    public static String bouwXmlString(final Object jibxGebindObject) {
        try {
            IBindingFactory bfact = BindingDirectory.getFactory(jibxGebindObject.getClass());

            IMarshallingContext mctx = bfact.createMarshallingContext();
            mctx.setIndent(1);
            StringWriter stringWriter = new StringWriter();
            mctx.setOutput(stringWriter);
            mctx.marshalDocument(jibxGebindObject);

            return stringWriter.toString();
        } catch (JiBXException e) {
            LOGGER.error("JiBX fout opgetreden.", e);
        }
        return null;
    }

    public static Object leesXmlString(final InputStream xmlInputStrem, final Class clazz) {
        try {
            IBindingFactory bfact = BindingDirectory.getFactory(clazz);

            IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
            return uctx.unmarshalDocument(xmlInputStrem, null);
        } catch (JiBXException e) {
            LOGGER.error("JiBX fout opgetreden.", e);
        }
        return null;
    }

}
