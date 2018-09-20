/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.viewer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Klasse voor de DemoMode functionaliteit.
 */
@Component
public class DemoMode {
    @Value("${defaultpl.location}")
    private String defaultPlLocation;

    @Value("${demo.mode}")
    private boolean demoMode;

    /**
     * Zet een map in elkaar welke op de ModelAndView wordt gezet. Map bevat demoMode, defaultPlen en FilenameUtils.
     * 
     * @return model Map<String, Object>
     */
    public final Map<String, Object> getDemoModel() {
        final Map<String, Object> model = new HashMap<String, Object>();

        model.put("demoMode", demoMode);

        if (demoMode) {
            model.put("defaultPlen", getDefaultPLen());
            model.put("FilenameUtils", FilenameUtils.class);
        }
        return model;
    }

    /**
     * Zoekt en controleert aan de hand van de filename de echte file op en retourneert deze als byte array.
     * 
     * @param filename
     *            String
     * @return file byte[]
     */
    public final byte[] getDemoUploadFile(final String filename) {
        try {
            if (filename != null && defaultPlLocation != null) {
                final File file = new File(filename);
                if (FilenameUtils.separatorsToUnix(file.getAbsolutePath()).startsWith(defaultPlLocation)) {
                    return FileUtils.readFileToByteArray(file);
                }
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * @return De lijst met PL bestanden in de directory.
     */
    private List<File> getDefaultPLen() {
        final List<File> result = new ArrayList<File>();

        try {
            final File dir = new File(defaultPlLocation);
            if (dir.isDirectory()) {
                final Collection<File> files = FileUtils.listFiles(dir, new String[] { "xls", "GBA", "txt" }, true);

                for (final File file : files) {
                    result.add(file);
                }
            }
        } catch (final IllegalArgumentException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
