/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.utils;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Deze implementatie van {@link FileSystemAccess} schrijft de file alleen als er nog geen file met dezelfde naam
 * bestaat in de {@link #sourceDirectory}.
 */
public class ExtensionPointFileSystemAccess extends JavaIoFileSystemAccess {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExtensionPointFileSystemAccess.class);

    private final String        sourceDirectory;

    public ExtensionPointFileSystemAccess(final String outputDirectory, final String sourceDirectory) {
        super(outputDirectory);
        this.sourceDirectory = sourceDirectory;
    }

    @Override
    public boolean exists(final String fileName) {
        StringBuilder pad = new StringBuilder();
        pad.append(sourceDirectory).append("/").append(fileName);
        File file = new File(pad.toString());
        return file.exists();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void generateFile(final String fileName, final CharSequence contents) {
        if (this.getOutputDirectory() == null) {
            throw new IllegalArgumentException("output directory pad is niet gezet.");
        }
        if (sourceDirectory == null) {
            throw new IllegalArgumentException("source directory pad is niet gezet.");
        }
        boolean fileExists = exists(fileName);
        if (fileExists) {
            LOGGER.info("File '{}' bestaat al in '{}' en wordt niet opnieuw gegenereerd.", fileName, sourceDirectory);
        } else {
            doGenerateFile(fileName, contents);
        }
    }
}
