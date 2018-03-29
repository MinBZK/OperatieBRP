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
import nl.bzk.algemeenbrp.util.xml.exception.DecodeException;
import nl.bzk.algemeenbrp.util.xml.exception.EncodeException;
import nl.bzk.algemeenbrp.util.xml.exception.XmlException;
import nl.bzk.algemeenbrp.util.xml.model.ConfigurationHelper;
import nl.bzk.algemeenbrp.util.xml.model.XmlObject;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActieBron;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDocumentInhoud;
import org.w3c.dom.Element;

/**
 *
 */
public final class BrpActieBronObjectEncoder extends AbstractBrpObjectEncoder implements XmlObject<BrpActieBron> {

    private static final String DOCUMENT = "document";

    @Override
    public void encode(final Context context, final Class<?> clazzFromParent, final String nameFromParent, final BrpActieBron value, final Writer writer)
            throws XmlException {
        try {
            writeTag(writer, false, nameFromParent);

            // Rechtsgrond
            final XmlObject<BrpString> rechtsgrondConfig = ConfigurationHelper.getConfiguration(context).getXmlObjectFor(BrpString.class);
            rechtsgrondConfig.encode(context, BrpString.class, "rechtsgrond", value.getRechtsgrondOmschrijving(), writer);

            writeDocument(writer, value, context);

            writeTag(writer, true, nameFromParent);
        } catch (final IOException e) {
            throw new EncodeException(context.getElementStack(), e);
        }
    }

    private void writeDocument(final Writer writer, final BrpActieBron bron, final Context context)
            throws IOException, XmlException {
        if (!bron.getDocumentStapel().isEmpty()) {
            writeTag(writer, false, DOCUMENT);
            final BrpDocumentInhoud document = bron.getDocumentStapel().get(0).getInhoud();
            writeDocumentSoort(writer, document, context);
            writeBrpString(writer, document.getAktenummer(), "aktenummer", context);
            writeBrpString(writer, document.getOmschrijving(), "omschrijving", context);
            writePartijCode(writer, document.getPartijCode(), context);
            writeTag(writer, true, DOCUMENT);
        }
    }

    private void writeDocumentSoort(final Writer writer, final BrpDocumentInhoud document, final Context context)
            throws XmlException {
        final XmlObject<BrpSoortDocumentCode> rechtsgrondConfig =
                ConfigurationHelper.getConfiguration(context).getXmlObjectFor(BrpSoortDocumentCode.class);
        rechtsgrondConfig.encode(context, BrpSoortDocumentCode.class, "soort", document.getSoortDocumentCode(), writer);
    }

    private String createNode(final String element, final String waarde) {
        final StringBuffer result = new StringBuffer("<");
        result.append("><waarde>");
        result.append("</waarde></");
        result.append(">");
        return result.toString();

    }

    @Override
    public BrpActieBron decode(final Context context, final Element element) throws ConfigurationException, DecodeException {
        throw new UnsupportedOperationException("BrpActieBronObject.decode niet ondersteund");
    }
}
