/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.output;

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

import nl.bzk.migratiebrp.conversie.model.Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAfnemersindicaties;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAutorisatie;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.conversie.model.exceptions.PreconditieException;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Afnemersindicatie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Autorisatie;
import nl.bzk.migratiebrp.conversie.model.serialize.XmlEncoding;
import nl.bzk.migratiebrp.conversie.model.tussen.autorisatie.TussenAfnemersindicaties;
import nl.bzk.migratiebrp.conversie.model.tussen.autorisatie.TussenAutorisatie;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.test.common.resultaat.Foutmelding;
import nl.bzk.migratiebrp.test.common.resultaat.TestRapportage;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

/**
 * Test output helper.
 */
public final class TestOutput {

    private static final String AFNEMERSINDICATIE_XSL = "/AfnemersindicatieNaarHtml.xsl";
    private static final String AUTORISATIE_XSL = "/AutorisatieNaarHtml.xsl";
    private static final String FOUTMELDING_XSL = "/FoutmeldingNaarHtml.xsl";
    private static final String LOGGING_XSL = "/LoggingNaarHtml.xsl";
    private static final String CATEGORIEEN_XSL = "/CategorieenNaarHtml.xsl";
    private static final String PRECONDITIE_EXCEPTION_XSL = "/PreconditieExceptionNaarHtml.xsl";
    private static final String ONGELDIGE_PERSOONSLIJST_EXCEPTION_XSL = "/OngeldigePersoonslijstExceptionNaarHtml.xsl";
    private static final String TESTRAPPORT_XSL = "/TestRapportNaarHtml.xsl";
    private static final String FILE_WRITE_ERROR = "Kan directory/bestand niet aanmaken";

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final Transformer FOUTMELDING_TRANSFORMER;
    private static final Transformer LOGGING_TRANSFORMER;
    private static final Transformer CATEGORIEEN_TRANSFORMER;
    private static final Transformer RAPPORTAGE_TRANSFORMER;
    private static final Transformer PRECONDITIE_EXCEPTION_TRANSFORMER;
    private static final Transformer ONGELDIGE_PERSOONSLIJST_EXCEPTION_TRANSFORMER;

    static {
        final TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            final Class<?> clazz = TestOutput.class;
            FOUTMELDING_TRANSFORMER = transformerFactory.newTransformer(new StreamSource(clazz.getResourceAsStream(FOUTMELDING_XSL)));
            LOGGING_TRANSFORMER = transformerFactory.newTransformer(new StreamSource(clazz.getResourceAsStream(LOGGING_XSL)));
            CATEGORIEEN_TRANSFORMER = transformerFactory.newTransformer(new StreamSource(clazz.getResourceAsStream(CATEGORIEEN_XSL)));
            RAPPORTAGE_TRANSFORMER = transformerFactory.newTransformer(new StreamSource(clazz.getResourceAsStream(TESTRAPPORT_XSL)));
            PRECONDITIE_EXCEPTION_TRANSFORMER = transformerFactory.newTransformer(new StreamSource(clazz.getResourceAsStream(PRECONDITIE_EXCEPTION_XSL)));
            ONGELDIGE_PERSOONSLIJST_EXCEPTION_TRANSFORMER =
                    transformerFactory.newTransformer(new StreamSource(clazz.getResourceAsStream(ONGELDIGE_PERSOONSLIJST_EXCEPTION_XSL)));
        } catch (final TransformerConfigurationException e) {
            throw new TestOutputException("Kan XSLT transformatie (rapportage) niet initialiseren.", e);
        }
    }

    private static final Transformer AFNEMERSINDICATIE_TRANSFORMER;
    private static final Transformer AUTORISATIE_TRANSFORMER;
    private static final Transformer MODEL_TRANSFORMER;
    static {
        final TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {

            // Deze XSL gebruikt imports
            final Class<?> clazz = TestOutput.class;
            final URIResolver resolver = new URIResolver() {

                @Override
                public Source resolve(final String href, final String base) throws TransformerException {
                    return new StreamSource(clazz.getResourceAsStream("/" + href));
                }
            };
            transformerFactory.setURIResolver(resolver);
            AUTORISATIE_TRANSFORMER = transformerFactory.newTransformer(new StreamSource(clazz.getResourceAsStream(AUTORISATIE_XSL)));
            MODEL_TRANSFORMER = transformerFactory.newTransformer(new StreamSource(clazz.getResourceAsStream("/ConversieModelNaarHtml.xsl")));
            AFNEMERSINDICATIE_TRANSFORMER = transformerFactory.newTransformer(new StreamSource(clazz.getResourceAsStream(AFNEMERSINDICATIE_XSL)));

        } catch (final TransformerConfigurationException e) {
            e.printStackTrace();
            throw new TestOutputException("Kan XSLT transformatie (afnemersindicatie,autorisatie,model) niet initialiseren.", e);
        }
    }

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
    public static void outputXmlEnHtml(final Object object, final File xmlFile, final File htmlFile) {
        final byte[] xmlData = object == null ? new byte[] {} : TestOutput.xmlSerializeObject(object);
        TestOutput.outputXml(xmlData, xmlFile);
        TestOutput.outputHtml(xmlData, object, htmlFile);
    }

    private static void outputHtml(final byte[] xmlData, final Object object, final File htmlFile) {
        final Transformer transformer;
        if (object instanceof Persoonslijst) {
            transformer = MODEL_TRANSFORMER;
        } else if (object instanceof TestRapportage) {
            transformer = RAPPORTAGE_TRANSFORMER;
        } else if (object instanceof Foutmelding) {
            transformer = FOUTMELDING_TRANSFORMER;
        } else if (object instanceof Logging) {
            transformer = LOGGING_TRANSFORMER;
        } else if (object instanceof Lo3CategorieWaarden) {
            transformer = CATEGORIEEN_TRANSFORMER;
        } else if (object instanceof Lo3Autorisatie || object instanceof TussenAutorisatie || object instanceof BrpAutorisatie) {
            transformer = AUTORISATIE_TRANSFORMER;
        } else if (object instanceof Lo3Afnemersindicatie || object instanceof TussenAfnemersindicaties || object instanceof BrpAfnemersindicaties) {
            transformer = AFNEMERSINDICATIE_TRANSFORMER;
        } else if (object instanceof PreconditieException) {
            transformer = PRECONDITIE_EXCEPTION_TRANSFORMER;
        } else if (object instanceof OngeldigePersoonslijstException) {
            transformer = ONGELDIGE_PERSOONSLIJST_EXCEPTION_TRANSFORMER;
        } else {
            transformer = null;
        }

        if (transformer != null && htmlFile != null) {
            final File parentFile = htmlFile.getParentFile();
            final boolean okToWrite = parentFile.exists() || parentFile.mkdirs();

            // @formatter:off - formatter zet opening-{ niet op juiste plaats
            try (final FileOutputStream htmlFos = new FileOutputStream(htmlFile);
                    final ByteArrayInputStream xmlDataBais = new ByteArrayInputStream(xmlData))
                    {
                // @formatter:on

                if (okToWrite) {
                    transformer.transform(new StreamSource(xmlDataBais), new StreamResult(htmlFos));
                } else {
                    throw new IOException(FILE_WRITE_ERROR);
                }
            } catch (final Exception e) {
                throw new TestOutputException("Kan XML niet transformeren naar HTML file.", e);
            }
        }
    }

    private static void outputXml(final byte[] xmlData, final File xmlFile) {
        if (xmlFile != null) {
            final File parentFile = xmlFile.getParentFile();
            final boolean okToWrite = parentFile.exists() || parentFile.mkdirs();

            try (FileOutputStream xmlFos = new FileOutputStream(xmlFile)) {
                if (okToWrite) {
                    // write XML to file
                    xmlFos.write(xmlData);
                } else {
                    throw new IOException(FILE_WRITE_ERROR);
                }
            } catch (final Exception e) {
                LOG.error("Exception: >>" + e.getMessage() + "<<");
                throw new TestOutputException("Kan XML file niet schrijven.", e);
            }
        }
    }

    private static byte[] xmlSerializeObject(final Object object) {
        try (ByteArrayOutputStream boas = new ByteArrayOutputStream()) {
            XmlEncoding.encode(object, boas);
            return boas.toByteArray();
        } catch (final Exception e) {
            System.err.println("Exception: >>" + e.getMessage() + "<<");
            throw new TestOutputException("Kan object (class=" + object.getClass().getName() + " niet serializeren.", e);
        }
    }

    /**
     * Leest een XML-bestand en decodeerd de XML naar het opgegeven clazz object.
     *
     * @param clazz
     *            Class dat gevuld moet worden vanuit de XML
     * @param xmlFile
     *            het XML bestand
     * @param <T>
     *            Type klasse dat moet worden terug gegeven
     * @return het gevulde clazz-Object
     */
    public static <T> T readXml(final Class<T> clazz, final File xmlFile) {
        LOG.debug("readXml(clazz={},xmlFile={})", clazz.getName(), xmlFile.getPath());
        if (!xmlFile.exists()) {
            return null;
        }

        try (FileInputStream xmlFis = new FileInputStream(xmlFile)) {
            return XmlEncoding.decode(clazz, xmlFis);
        } catch (final Exception e) {
            System.err.println("Exception: >>" + e.getMessage() + "<<");
            throw new TestOutputException("Fout bij lezen en decoderen XML uit file.", e);
        }
    }
}
