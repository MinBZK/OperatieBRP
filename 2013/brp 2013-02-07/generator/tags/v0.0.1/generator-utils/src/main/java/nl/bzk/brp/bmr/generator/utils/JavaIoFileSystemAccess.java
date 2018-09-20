/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;


/**
 * Implementatie van {@link FileSystemAccess} die files schrijft met {@link File}.
 */
public class JavaIoFileSystemAccess implements FileSystemAccess {

    private final String outputDirectory;

    public JavaIoFileSystemAccess(final String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    /**
     * {@inheritDoc}
     *
     * Deze versie checkt geen files en zegt daarom altijd dat de file niet bestaat.
     */
    @Override
    public boolean exists(final String fileName) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void generateFile(final String bestandsNaam, final CharSequence inhoud) {
        if (outputDirectory == null) {
            throw new IllegalArgumentException("directory pad is niet gezet.");
        }
        doGenerateFile(bestandsNaam, inhoud);
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    /**
     * @param bestandsNaam
     * @param inhoud
     */
    protected void doGenerateFile(final String bestandsNaam, final CharSequence inhoud) {
        String pad = outputDirectory + "/" + bestandsNaam;
        File bestand = new File(pad);
        bestand.getParentFile().mkdirs();
        try {
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(bestand), "UTF-8");
            writer.append(inhoud);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
