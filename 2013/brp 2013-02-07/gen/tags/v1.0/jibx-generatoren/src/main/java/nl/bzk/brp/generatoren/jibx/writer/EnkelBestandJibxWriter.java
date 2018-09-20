/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.jibx.writer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import nl.bzk.brp.generatoren.algemeen.basis.Generator;
import nl.bzk.brp.generatoren.algemeen.basis.GeneratorExceptie;
import nl.bzk.brp.generatoren.algemeen.common.GeneratieUtil;
import nl.bzk.brp.generatoren.jibx.common.JibxBinding;
import nl.bzk.brp.generatoren.jibx.model.Binding;
import nl.bzk.brp.generatoren.jibx.util.JibxGeneratieUtil;

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
        String bestandsNaam = generatieBasisFolder + "/" + JibxGeneratieUtil.getBestandsnaamVoorBinding(jibxBinding);
        File bestand = new File(bestandsNaam);
        if (!bestand.exists() || overschrijf) {
            try {
                output = new FileOutputStream(bestand);
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

            // Na het closen van de stream voegen we nog wat comments met versie nummers toe.
            try {
                voegVersieCommentaarToe(bestand, generator);
            } catch (IOException e) {
                final String error = "Fout opgetreden bij toevoegen versie info aan binding file.";
                LOGGER.error(error, e);
                throw new GeneratorExceptie(error, e);
            }
        }
    }

    /**
     * Voeg het commentaar over de versies toe aan de gegenereerde binding file.
     *
     * @param bestand het binding bestand
     * @param generator de generator
     * @throws IOException indien er iets mis gaat in de io
     */
    private void voegVersieCommentaarToe(final File bestand, final Generator generator) throws IOException {
        final Element rootElement = GeneratieUtil.bouwElementenUitXmlSnippet(new FileInputStream(bestand), true).get(0);
        Document document = new Document(rootElement);
        GeneratieUtil.voegVersieCommentaarToe(document, generator);

        final XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        outputter.output(document, new FileOutputStream(bestand));
    }

}
