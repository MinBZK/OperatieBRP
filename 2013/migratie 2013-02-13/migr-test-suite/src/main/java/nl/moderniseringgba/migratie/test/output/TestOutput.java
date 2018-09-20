/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import nl.moderniseringgba.migratie.conversie.model.Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratiePersoonslijst;
import nl.moderniseringgba.migratie.conversie.proces.logging.Logging;
import nl.moderniseringgba.migratie.conversie.serialize.PersoonslijstDecoder;
import nl.moderniseringgba.migratie.conversie.serialize.PersoonslijstEncoder;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;
import nl.moderniseringgba.migratie.test.resultaat.Foutmelding;
import nl.moderniseringgba.migratie.test.resultaat.TestRapportage;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 * Test output helper.
 */
// CHECKSTYLE:OFF - Class fan out complexity
public final class TestOutput {
    // CHECKSTYLE:ON

    private static final String FOUTMELDING_XSL = "/FoutmeldingNaarHtml.xsl";
    private static final String LOGGING_XSL = "/LoggingNaarHtml.xsl";
    private static final String TESTRAPPORT_XSL = "/TestRapportNaarHtml.xsl";

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final Transformer RAPPORTAGE_TRANSFORMER;
    private static final Transformer FOUTMELDING_TRANSFORMER;
    private static final Transformer LOGGING_TRANSFORMER;
    static {
        final TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            RAPPORTAGE_TRANSFORMER =
                    transformerFactory.newTransformer(new StreamSource(TestOutput.class
                            .getResourceAsStream(TESTRAPPORT_XSL)));
            FOUTMELDING_TRANSFORMER =
                    transformerFactory.newTransformer(new StreamSource(TestOutput.class
                            .getResourceAsStream(FOUTMELDING_XSL)));
            LOGGING_TRANSFORMER =
                    transformerFactory.newTransformer(new StreamSource(TestOutput.class
                            .getResourceAsStream(LOGGING_XSL)));
        } catch (final TransformerConfigurationException e) {
            throw new RuntimeException("Kan XSLT transformatie (rapportage) niet initialiseren.", e);
        }
    }

    private static final Transformer MODEL_TRANSFORMER;
    static {
        final TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {

            // Deze XSL gebruikt imports
            final URIResolver resolver = new URIResolver() {

                @Override
                public Source resolve(final String href, final String base) throws TransformerException {
                    return new StreamSource(TestOutput.class.getResourceAsStream("/" + href));
                }
            };
            transformerFactory.setURIResolver(resolver);

            MODEL_TRANSFORMER =
                    transformerFactory.newTransformer(new StreamSource(TestOutput.class
                            .getResourceAsStream("/ConversieModelNaarHtml.xsl")));
        } catch (final TransformerConfigurationException e) {
            throw new RuntimeException("Kan XSLT transformatie (model) niet initialiseren.", e);
        }
    }

    private static final Serializer SERIALIZER = new Persister();

    private TestOutput() {
    }

    /**
     * Output XML en HTML voor een object.
     * 
     * @param object
     *            object
     * @param xmlFile
     *            xml file
     * @param htmlFile
     *            html file
     */
    // CHECKSTYLE:OFF - Complexity
    public static void outputXmlEnHtml(final Object object, final File xmlFile, final File htmlFile) {
        // CHECKSTYLE:ON
        final ByteArrayOutputStream boas = new ByteArrayOutputStream();

        try {
            // Uitzondering voor persoonslijst
            if (object instanceof Persoonslijst) {
                final Persoonslijst pl = (Persoonslijst) object; // PersoonslijstSorter.sort((Persoonslijst) object);

                PersoonslijstEncoder.encodePersoonslijst(pl, boas);
            } else {
                SERIALIZER.write(object, boas);
            }

            // CHECKSTYLE:OFF de serializer methode gooit een Exception
        } catch (final Exception e) {
            // CHECKSTYLE:ON
            throw new RuntimeException("Kan object (class=" + object.getClass().getName() + " niet serializeren.", e);
        } finally {
            try {
                boas.close();
            } catch (final IOException e) {
                // Ignore, can't do anything anyway
                LOG.debug("Fout tijdens sluiten stream (schrijven PL)", e);
            }
        }

        final byte[] xmlData = boas.toByteArray();

        if (xmlFile != null) {
            xmlFile.getParentFile().mkdirs();
            // write XML to file
            FileOutputStream xmlFos = null;
            try {
                xmlFos = new FileOutputStream(xmlFile);
                xmlFos.write(xmlData);
                // CHECKSTYLE:OFF
            } catch (final Exception e) {
                // CHECKSTYLE:ON
                throw new RuntimeException("Kan XML file niet schrijven.", e);
            } finally {
                if (xmlFos != null) {
                    try {
                        xmlFos.close();
                    } catch (final IOException e) {
                        // Ignore, can't do anything anyway
                        LOG.debug("Fout tijdens sluiten stream (schrijven XML)", e);

                    }
                }
            }
        }

        final Transformer transformer;
        if (object instanceof Persoonslijst) {
            transformer = MODEL_TRANSFORMER;
        } else if (object instanceof TestRapportage) {
            transformer = RAPPORTAGE_TRANSFORMER;
        } else if (object instanceof Foutmelding) {
            transformer = FOUTMELDING_TRANSFORMER;
        } else if (object instanceof Logging) {
            transformer = LOGGING_TRANSFORMER;
        } else {
            transformer = null;
        }

        if (transformer != null && htmlFile != null) {
            htmlFile.getParentFile().mkdirs();
            FileOutputStream htmlFos = null;
            try {
                htmlFos = new FileOutputStream(htmlFile);
                transformer.transform(new StreamSource(new ByteArrayInputStream(xmlData)), new StreamResult(htmlFos));
                // CHECKSTYLE:OFF
            } catch (final Exception e) {
                // CHECKSTYLE:ON

                throw new RuntimeException("Kan XML niet transformeren naar HTML file.", e);
            } finally {
                if (htmlFos != null) {
                    try {
                        htmlFos.close();
                    } catch (final IOException e) {
                        // Ignore, can't do anything anyway
                        LOG.debug("Fout tijdens sluiten stream (schrijven HTML)", e);

                    }
                }
            }
        }
    }

    // CHECKSTYLE:OFF
    @SuppressWarnings("unchecked")
    public static <T> T readXml(final Class<T> clazz, final File xmlFile) {
        // CHECKSTYLE:ON
        LOG.debug("readXml(clazz={},xmlFile={})", clazz.getName(), xmlFile.getPath());
        if (!xmlFile.exists()) {
            return null;
        }

        try {
            // Uitzondering voor persoonslijst
            if (BrpPersoonslijst.class.isAssignableFrom(clazz)) {
                return (T) PersoonslijstDecoder.decodeBrpPersoonslijst(new FileInputStream(xmlFile));
            }

            if (MigratiePersoonslijst.class.isAssignableFrom(clazz)) {
                return (T) PersoonslijstDecoder.decodeMigratiePersoonslijst(new FileInputStream(xmlFile));
            }

            if (Lo3Persoonslijst.class.isAssignableFrom(clazz)) {
                return (T) PersoonslijstDecoder.decodeLo3Persoonslijst(new FileInputStream(xmlFile));
            }

            return SERIALIZER.read(clazz, xmlFile);
            // CHECKSTYLE:OFF
        } catch (final Exception e) {
            // CHECKSTYLE:ON
            throw new RuntimeException("Fout bij lezen en decoderen XML uit file.", e);
        }

    }
}
