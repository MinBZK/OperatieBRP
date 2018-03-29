/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.processors.encoder;

import java.io.IOException;
import java.io.Writer;
import nl.bzk.algemeenbrp.util.xml.context.Context;
import nl.bzk.algemeenbrp.util.xml.exception.ConfigurationException;
import nl.bzk.algemeenbrp.util.xml.exception.EncodeException;
import nl.bzk.algemeenbrp.util.xml.exception.XmlException;
import nl.bzk.algemeenbrp.util.xml.model.ConfigurationHelper;
import nl.bzk.algemeenbrp.util.xml.model.XmlObject;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Abstract class met gedeelde encoding functionaliteit.
 */
public abstract class AbstractBrpObjectEncoder {

    private static final String ELEMENT_PARTIJ_CODE = "partijCode";

    /**
     * writes a tag.
     * @param writer Writer
     * @param isEnd boolean
     * @param name name of element
     * @param attributes array of attributes
     * @throws IOException on error
     */
    final void writeTag(final Writer writer, final boolean isEnd, final String name, final Pair<String, String>... attributes) throws IOException {
        if (isEnd) {
            writer.write("</");
        } else {
            writer.write("<");
        }
        writer.write(name);
        for (Pair<String, String> attribute : attributes) {
            writer.write(attribute.toString(" %1$s=\"%2$s\""));
        }
        writer.write(">");
    }

    /**
     * writes a empty tag.
     * @param writer Writer
     * @param name name of element
     * @throws IOException on error
     */
    final void writeEmptyTag(final Writer writer, final String name) throws IOException {
        writer.write("<" + name + "/>");
    }

    /**
     * write PartijCode.
     * @param writer Writer
     * @param partijCode BrpPartijCode
     * @param context Context
     * @throws ConfigurationException on error
     * @throws EncodeException on error
     * @throws IOException on error
     */
    final void writePartijCode(final Writer writer, final BrpPartijCode partijCode, final Context context)
            throws ConfigurationException, EncodeException, IOException {
        writeTag(writer, false, ELEMENT_PARTIJ_CODE);
        writer.write("<waarde>" + partijCode.getWaarde() + "</waarde>");
        writeTag(writer, true, ELEMENT_PARTIJ_CODE);
    }

    /**
     * write BrpString.
     * @param writer Writer
     * @param waarde BrpString
     * @param nodeNaam elementNaam
     * @param context context
     * @throws EncodeException on error
     * @throws ConfigurationException on error
     */
    final void writeBrpString(final Writer writer, final BrpString waarde, final String nodeNaam, final Context context)
            throws XmlException {
        if (waarde != null) {
            final XmlObject<BrpString> rechtsgrondConfig = ConfigurationHelper.getConfiguration(context).getXmlObjectFor(BrpString.class);
            rechtsgrondConfig.encode(context, BrpString.class, nodeNaam, waarde, writer);
        }
    }

}
