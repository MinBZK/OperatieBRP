/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.writer;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.bzk.brp.generatoren.algemeen.basis.GeneratorExceptie;
import nl.bzk.brp.generatoren.java.model.JavaResource;
import nl.bzk.brp.generatoren.java.util.JavaGeneratorConstante;

/**
 * Class voor het wegschrijven van Java source code resources op basis van een 'Platform Specifiek Model' (een Java model in dit geval).
 */
public final class ResourceWriter {

    private static final Logger LOG = LoggerFactory.getLogger(ResourceWriter.class);

    protected static final String  PAD_SEPARATOR           = System.getProperty("file.separator");
    private static final String ENCODING = "UTF-8";

	private final String generatieBasisFolder;
	private final boolean overschrijf;

	  /**
     * Constructor die de basis map zet waarin de resources geschreven dienen te worden.
     *
     * @param generatieBasisFolder de basis folder waarin de resources geschreven worden.
     * @param overschrijf          boolean die aangeeft of een weg te schrijven bestand eventueel overschreven moet
     *                             worden indien het al bestaat of niet.
     */
	public ResourceWriter(String generatieBasisFolder, boolean overschrijf) {
		this.generatieBasisFolder = generatieBasisFolder;
		this.overschrijf = overschrijf;
	}


    /**
     * Schrijft de opgegeven Java resource objecten naar de bijbehorende bestanden.
     *
     * @param javaResources de Java resource-objecten die weggeschreven dienen te worden.
     */
	public void schrijfWeg(List<JavaResource> javaResources) {
		  for (JavaResource javaResource : javaResources) {
	            final File bestand = bouwBronBestand(javaResource);
	            String contents = javaResource.getContents();
	            contents = contents == null ? "" : contents.replaceAll("\r\n|\r|\n",
	                    JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde());

	            if (!bestand.exists() || overschrijf) {
	                try {
	                    FileUtils.deleteQuietly(bestand);
	                    FileUtils.writeStringToFile(bestand, contents, ENCODING);
	                } catch (IOException e) {
	                    String bericht = "IOException opgetreden tijdens het wegschrijven van een java bestand.";
	                    LOG.error(bericht, e);
	                    throw new GeneratorExceptie(bericht, e);
	                }
	            }
	        }
	}

    /**
     * Bouwt een nieuw bestand voor het opgegeven Java broncode object, waarbij het gehele pad wordt opgegeven. De op
     * de writer geconfigureerde basis map voor generatie zal hier als basis worden gebruikt, waarna verder het package
     * en de naam van het bestand van invloed zijn op de bestandsnaam.
     *
     * @param javaBroncodeObject het Java broncode object waarvoor een bestand gemaakt dient te worden.
     * @return een bestand met een voor het Java broncode object specifieke bestandslocatie.
     */
    protected File bouwBronBestand(final JavaResource javaResource) {
        StringBuilder bestandNaam = new StringBuilder();
        bestandNaam.append(generatieBasisFolder);
        bestandNaam.append(PAD_SEPARATOR);
        if (javaResource.getFolder() != null) {
            bestandNaam.append(javaResource.getFolder().replaceAll("\\|/", PAD_SEPARATOR));
            bestandNaam.append(PAD_SEPARATOR);
        }
        bestandNaam.append(javaResource.getNaam());
        return new File(bestandNaam.toString());
    }

}
