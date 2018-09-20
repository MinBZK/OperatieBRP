/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.xsd.writer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import nl.bzk.brp.generatoren.algemeen.basis.GeneratorExceptie;
import nl.bzk.brp.generatoren.algemeen.common.GeneratieUtil;
import nl.bzk.brp.generatoren.xsd.AbstractXsdGenerator;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/** De writer klasse voor XSD bestanden. */
@Component
public class XsdWriter {

    private static final Logger LOGGER = LoggerFactory.getLogger(XsdWriter.class);

    /**
     * Schrijf een XSD bestand naar de betreffende folder, met de betreffende naam
     * en bestaande uit het betreffende root element (moet dus 'schema' zijn).
     *
     * @param generatieBasisFolder de basis folder waar het bestand geschreven moet worden
     * @param overschrijf of het doelbestand overschreven moet worden
     * @param naam de naam van het bestand (zonder extensie)
     * @param document het XML document van een XSD
     */
    public void writeXsd(final String generatieBasisFolder, final boolean overschrijf,
            final String naam, final Document document)
    {
        GeneratieUtil.maakMapStructuurAan(generatieBasisFolder);

        //TODO: Bouw een XSD validatie in op dit punt?

        String bestandsNaam = generatieBasisFolder + "/" + naam + ".xsd";
        File bestand = new File(bestandsNaam);
        if (!bestand.exists() || overschrijf) {
            OutputStream bestandStream = null;
            try {
                XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());

                // Hier zetten we de default XSD namespace op alle elementen die nog geen andere namespace hebben.
                zetXsdNamespace(document.getRootElement());

                bestandStream = new FileOutputStream(bestand);
                outputter.output(document, bestandStream);
            } catch (Exception e) {
                String bericht = "Exception opgetreden tijdens het wegschrijven van een xsd bestand.";
                LOGGER.error(bericht, e);
                throw new GeneratorExceptie(bericht, e);
            } finally {
                if (bestandStream != null) {
                    try {
                        bestandStream.close();
                    } catch (IOException e) {
                        LOGGER.error("Fout bij het sluiten van het bestand: " + bestandsNaam);
                    }
                }
            }
        }
    }

    /**
     * Zet het default XSD namespace op alle elementen die nog geen default namespace hebben.
     * Dit, om te voorkomen dat alle code overal hier zelf rekening mee moet houden.
     * Deze methode wordt recursief aangeroepen voor alle kind-elementen.
     *
     * @param element het element waar de namespace op toegevoegd moet worden, inclusief kids
     */
    private void zetXsdNamespace(final Element element) {
        // Als dit element nog geen default namespace heeft, zat dan de XSD namespace.
        if (element.getNamespace().getURI().equals("")) {
            element.setNamespace(AbstractXsdGenerator.XSD_NAMESPACE);
        }
        // Doe hetzelfde voor al zijn kinderen (recursief).
        for (Element child : element.getChildren()) {
            zetXsdNamespace(child);
        }
    }

}
