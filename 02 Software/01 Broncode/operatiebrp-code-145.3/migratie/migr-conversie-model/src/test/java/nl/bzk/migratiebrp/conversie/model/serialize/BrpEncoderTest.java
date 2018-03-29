/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.serialize;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Iterator;
import nl.bzk.algemeenbrp.util.xml.Xml;
import nl.bzk.algemeenbrp.util.xml.context.Context;
import nl.bzk.algemeenbrp.util.xml.exception.ConfigurationException;
import nl.bzk.algemeenbrp.util.xml.exception.DecodeException;
import nl.bzk.algemeenbrp.util.xml.exception.EncodeException;
import nl.bzk.algemeenbrp.util.xml.exception.XmlException;
import nl.bzk.algemeenbrp.util.xml.model.AbstractChild;
import nl.bzk.algemeenbrp.util.xml.model.AttributeChild;
import nl.bzk.algemeenbrp.util.xml.model.Child;
import nl.bzk.algemeenbrp.util.xml.model.CompositeObject;
import nl.bzk.algemeenbrp.util.xml.model.Configuration;
import nl.bzk.algemeenbrp.util.xml.model.ConfigurationHelper;
import nl.bzk.algemeenbrp.util.xml.model.XmlObject;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActieBron;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVoornaamInhoud;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class BrpEncoderTest {

    private Configuration xmlConfiguratie;

    @Test
    public void test() throws XmlException, IOException {

        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        final BrpGroep<BrpVoornaamInhoud> voornaam =
                BrpStapelHelper.groep(BrpStapelHelper.voornaam("Jaap", 1), BrpStapelHelper.his(20000101), BrpStapelHelper.act(1, 20000101));

        final BrpStapel<BrpVoornaamInhoud> voornaamStapel = BrpStapelHelper.stapel(voornaam);
        builder.voornaamStapel(voornaamStapel);
        final BrpPersoonslijst persoonslijst = builder.build();

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (final Writer writer = new OutputStreamWriter(baos)) {
            configure();
            Xml.encode(xmlConfiguratie, persoonslijst, writer);
        }

        final byte[] data = baos.toByteArray();
        final String xml = new String(data, Charset.defaultCharset());
        System.out.println("Xml: " + xml);
    }

    @Before
    public void configure() throws ConfigurationException {
        xmlConfiguratie = new Configuration();

        // BRP ACTIE (id als sterretje)
        final XmlObject<BrpActie> brpActieConfiguratie = xmlConfiguratie.getXmlObjectFor(BrpActie.class);

        if (brpActieConfiguratie instanceof CompositeObject<?>) {
            // Verwijder ID
            final Iterator<Child<?>> childIterator = ((CompositeObject<?>) brpActieConfiguratie).getChildren().iterator();

            IdSterretjeAttribute newAttribute = null;
            while (childIterator.hasNext()) {
                final Child<?> attribute = childIterator.next();
                if ("id".equals(attribute.getName()) && attribute instanceof AttributeChild<?>) {
                    childIterator.remove();
                    newAttribute = new IdSterretjeAttribute((AttributeChild<Long>) attribute);
                }
            }

            // Toevoegen ID
            if (newAttribute != null) {
                ((CompositeObject<?>) brpActieConfiguratie).getChildren().add(newAttribute);
            }
        }

        // BRP ACTIE BRON (geen inhoud behalve rechtsgrond en inhoud van eerste record van document stapel)
        final XmlObject<BrpActieBron> brpActieBronConfiguratie = xmlConfiguratie.getXmlObjectFor(BrpActieBron.class);

        if (brpActieBronConfiguratie instanceof CompositeObject<?>) {
            xmlConfiguratie.registerXmlObjectFor(BrpActieBron.class, new BrpActieBronObject());
        }
    }

    public class IdSterretjeAttribute extends AbstractChild<Long> implements Child<Long> {

        public IdSterretjeAttribute(final AttributeChild<Long> base) {
            super(base.getAccessor(), base.getName());
        }

        @Override
        public void encode(final Context context, final Long value, final Writer writer) throws ConfigurationException, EncodeException {
            try {
                writer.append(" ");
                writer.append(getName());
                writer.append("=\"*\"");
            } catch (final IOException e) {
                throw new EncodeException(context.getElementStack(), e);
            }
        }

        @Override
        public boolean isAttribute() {
            return true;
        }

        @Override
        public Long decode(final Context context, final Node node, final Long previousValue) throws ConfigurationException, DecodeException {
            throw new UnsupportedOperationException("BrpActieBronObject.decode niet ondersteund");
        }

        @Override
        public boolean canDecode(final Node node) {
            return false;
        }

    }

    public class BrpActieBronObject implements XmlObject<BrpActieBron> {
        @Override
        public void encode(
                final Context context,
                final Class<?> clazzFromParent,
                final String nameFromParent,
                final BrpActieBron value,
                final Writer writer) throws XmlException {
            try {
                writer.write("<");
                writer.write(nameFromParent);
                writer.write(">");

                // Rechtsgrond
                final XmlObject<BrpString> rechtsgrondConfig = ConfigurationHelper.getConfiguration(context).getXmlObjectFor(BrpString.class);
                rechtsgrondConfig.encode(context, BrpString.class, "rechtsgrond", value.getRechtsgrondOmschrijving(), writer);

                // Document
                final XmlObject<BrpDocumentInhoud> documentConfig = ConfigurationHelper.getConfiguration(context).getXmlObjectFor(BrpDocumentInhoud.class);
                documentConfig.encode(context, BrpDocumentInhoud.class, "document", value.getDocumentStapel().get(0).getInhoud(), writer);

                writer.write("</");
                writer.write(nameFromParent);
                writer.write(">");
            } catch (final IOException e) {
                throw new EncodeException(context.getElementStack(), e);
            }
        }

        @Override
        public BrpActieBron decode(final Context context, final Element element) throws ConfigurationException, DecodeException {
            throw new UnsupportedOperationException("BrpActieBronObject.decode niet ondersteund");
        }

    }
}
