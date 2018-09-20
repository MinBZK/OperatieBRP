/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Klasse voor de DemoMode functionaliteit.
 */
@Component
public final class DemoMode {

    private final String defaultPlLocation;

    private final boolean demoMode;

    /**
     * Constructor. Inject de parameters via Spring uit de config file.
     * 
     * @param defaultPlLocation
     *            String
     * @param demoMode
     *            boolean
     */
    @Autowired
    public DemoMode(@Value("${defaultpl.location}") final String defaultPlLocation, @Value("${demo.mode}") final boolean demoMode) {
        this.demoMode = demoMode;
        if (demoMode) {
            this.defaultPlLocation = new File(defaultPlLocation).getAbsolutePath();
        } else {
            this.defaultPlLocation = null;
        }

    }

    /**
     * Zet een map in elkaar welke op de ModelAndView wordt gezet. Map bevat demoMode, defaultPlen en FilenameUtils.
     * 
     * @return model Map<String, Object>
     */
    public Map<String, Object> getDemoModel() {
        final Map<String, Object> model = new HashMap<>();

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
     * @throws IOException
     *             als de file niet gelezen kan worden
     */
    public byte[] getDemoUploadFile(final String filename) throws IOException {
        if (filename != null) {
            final File file = new File(filename);
            if (FilenameUtils.separatorsToUnix(file.getCanonicalPath()).startsWith(FilenameUtils.separatorsToUnix(defaultPlLocation))) {
                return FileUtils.readFileToByteArray(file);
            }
        }
        return null;
    }

    /**
     * Geef de waarde van default p len.
     *
     * @return De lijst met PL bestanden in de directory.
     */
    private List<File> getDefaultPLen() {
        final List<File> result = new ArrayList<>();

        final File dir = new File(defaultPlLocation);
        if (dir.isDirectory()) {
            final Collection<File> files = FileUtils.listFiles(dir, OndersteundeFormatenEnum.getBestandExtensies(), true);

            for (final File file : files) {
                result.add(file);
            }

            Collections.sort(result);
        }

        return result;
    }

    /**
     * Geeft aan of de GGO-viewer in demo mode draait of niet.
     * 
     * @return true als de GGO-viewer in demo mode draait.
     */
    public boolean isDemoMode() {
        return demoMode;
    }
}
