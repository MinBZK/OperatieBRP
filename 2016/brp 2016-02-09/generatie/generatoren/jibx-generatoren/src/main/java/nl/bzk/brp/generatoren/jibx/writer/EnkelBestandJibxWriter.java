/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.jibx.writer;

import nl.bzk.brp.generatoren.algemeen.basis.Generator;
import nl.bzk.brp.generatoren.algemeen.basis.GeneratorExceptie;
import nl.bzk.brp.generatoren.algemeen.common.GeneratieUtil;
import nl.bzk.brp.generatoren.jibx.common.JibxBinding;
import nl.bzk.brp.generatoren.jibx.customchanges.HandmatigeWijziging;
import nl.bzk.brp.generatoren.jibx.model.Binding;
import nl.bzk.brp.generatoren.jibx.util.JibxGeneratieUtil;
import org.apache.commons.io.FileUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.JiBXException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactoryConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 * De JiBX Writer klasse schrijft een jibx binding weg naar file.
 * Dit gaat door middel van JiBX zelf: vanuit het Java model van de binding xml wordt
 * het gemarshalled.
 */
public class EnkelBestandJibxWriter implements JibxWriter {

    private static final Logger LOGGER   = LoggerFactory.getLogger(EnkelBestandJibxWriter.class);
    private static final int    TAB_SIZE = 4;

    private String generatieBasisFolder;
    private boolean overschrijf;
    private IMarshallingContext marshallingContext;

    /**
     * Maak een nieuwe JiBX writer aan.
     *
     * @param generatieBasisFolder basis folder voor generatie
     * @param overschrijf of het doelbestand overschreven moet worden
     */
    public EnkelBestandJibxWriter(final String generatieBasisFolder, final boolean overschrijf) {
        this.generatieBasisFolder = generatieBasisFolder;
        this.overschrijf = overschrijf;
        this.initialiseer();
    }

    /**
     * Initialiseer de marshalling context.
     */
    private void initialiseer() {
        try {
            IBindingFactory bindingFactory = BindingDirectory.getFactory(Binding.class);
            this.marshallingContext = bindingFactory.createMarshallingContext();
            this.marshallingContext.setIndent(TAB_SIZE);
        } catch (JiBXException e) {
            throw new GeneratorExceptie("JibxException tijdens het laden van de marshaller.", e);
        }
    }

    public IMarshallingContext getMarshallingContext() {
        return this.marshallingContext;
    }

    /** {@inheritDoc} */
    @Override
    public void marshallXmlEnSchrijfWeg(final JibxBinding jibxBinding,
            final Binding binding, final Generator generator)
    {
        GeneratieUtil.maakMapStructuurAan(generatieBasisFolder);

        OutputStream output = null;
        String bestandsNaam = generatieBasisFolder + "/" + JibxGeneratieUtil.getBestandsLocatieVoorBinding(jibxBinding);
        File bestand = new File(bestandsNaam);
        if (!bestand.exists() || overschrijf) {
            try {
                output = FileUtils.openOutputStream(bestand);
                this.getMarshallingContext().marshalDocument(binding, "UTF-8", null, output);
            } catch (Exception e) {
                String bericht = "Exception opgetreden tijdens het wegschrijven van een xml binding bestand.";
                LOGGER.error(bericht, e);
                throw new GeneratorExceptie(bericht, e);
            } finally {
                if (output != null) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        LOGGER.error("Probleem bij het schrijven binding bestand: " + bestandsNaam, e);
                    }
                }
            }

            // Na het closen van de stream verwerken we de handmatige wijzigingen en voeren voegen we wat comments met
            // versie nummers toe.
            OutputStream bestandStream = null;
            try {
                final Element rootElement = GeneratieUtil.bouwElementenUitXmlSnippet(new FileInputStream(bestand), true).get(0);
                Document document = new Document(rootElement);
                // Versie nummers in commentaar bovenaan het bestand:
                GeneratieUtil.voegVersieCommentaarToe(document, generator);
                // Handmatige wijzigingen:
                voerHandmatigeWijzigingenDoor(document, bestand.getName());

                // Schrijf de aangepaste binding weg:
                final XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
                bestandStream = new FileOutputStream(bestand);
                outputter.output(document, bestandStream);

            } catch (Exception e) {
                final String error = "Fout opgetreden bij toevoegen versie info en handmatige wijzigingen"
                        + " aan binding file.";
                LOGGER.error(error, e);
                throw new GeneratorExceptie(error, e);
            } finally {
                if (bestandStream != null) {
                    try {
                        bestandStream.close();
                    } catch (IOException e) {
                        LOGGER.error("Fout tijdens sluiten stream.", e);
                    }
                }
            }
        }
    }

    /**
     * Handmatige wijzigingen worden hier doorgevoerd indien die er zijn voor het bestand. Zie
     * {@link nl.bzk.brp.generatoren.jibx.customchanges} voor de huidige handmatige wijzigingen.
     *
     * @param document het binding bestand als DOM tree.
     * @param bestandsnaam naam van het bestand dat geschreven wordt.
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws XPathExpressionException
     * @throws XPathFactoryConfigurationException
     */
    private void voerHandmatigeWijzigingenDoor(final Document document, final String bestandsnaam)
            throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException,
            XPathExpressionException, XPathFactoryConfigurationException
    {
        Properties handmatigeWijzigingenProperties = new Properties();
        handmatigeWijzigingenProperties.load(
                EnkelBestandJibxWriter.class.getResourceAsStream("/handmatigewijzigingen.properties"));

        for (Object bestandMetHandmatigeWijzigingen : handmatigeWijzigingenProperties.keySet()) {
            final String bestandDatGewijzigdMoetWorden = (String) bestandMetHandmatigeWijzigingen;
            if (bestandDatGewijzigdMoetWorden.equals(bestandsnaam)) {
                final String[] handmatigeWijzingenClasses = ((String) handmatigeWijzigingenProperties.get(bestandDatGewijzigdMoetWorden)).split(",");
                for (final String handmatigeWijzigingClass: handmatigeWijzingenClasses) {
                    doeHandmatigeWijziging(document, handmatigeWijzigingClass.trim());
                }
            }
        }
    }

    private void doeHandmatigeWijziging(final Document document, final String handmatigeWijzingenClasses)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, XPathFactoryConfigurationException, XPathExpressionException {
        final Class handmatigeWijzigingClass = Class.forName(handmatigeWijzingenClasses);
        final HandmatigeWijziging handmatigeWijziging = (HandmatigeWijziging) handmatigeWijzigingClass.newInstance();
        handmatigeWijziging.voerUit(document);
    }
}
