/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.junit.Assert;
import org.junit.Test;

public class MaakConfiguratie {

    private static final Logger LOG = LoggerFactory.getLogger();

    // Kopie uit TestBericht
    private static final Pattern FILE_NAME_PATTERN = Pattern.compile(
            "([0-9]*)-([0-9]*)?(IN|UIT)([0-9]*)?-([A-Za-z_]*)(-.*)?(\\..*)?",
            Pattern.CASE_INSENSITIVE);

    @Test
    public void run() {
        // maakConfig("regressie-voisc", false);
        maakConfigs("test", false);
    }

    private void maakConfigs(final String base, final boolean write) {
        final File baseDirectory = new File(base);
        Assert.assertTrue(baseDirectory.isDirectory());
        for (final String child : baseDirectory.list()) {
            final File childDirectory = new File(baseDirectory, child);
            if (childDirectory.isDirectory()) {
                maakConfig(base + File.separator + child, write);
            }
        }
    }

    private void maakConfig(final String base, final boolean write) {

        final Set<String> configuratie = new TreeSet<>();
        addConfiguratie(configuratie, new File(base));

        final Properties properties = new Properties();
        for (final String regel : configuratie) {
            properties.setProperty(regel, "true");
        }

        if (write) {
            final File propFile = new File(new File(base), "testcase.properties");
            try (OutputStream os = new FileOutputStream(propFile)) {
                properties.store(os, null);
                LOG.info("Written config to : " + propFile.getAbsolutePath());
            } catch (final IOException e) {
                LOG.error("Error on writing config", e);
                // Ignore
            }
        }
    }

    private void addConfiguratie(final Set<String> configuratie, final File fileOrDirectory) {
        if (fileOrDirectory.isFile()) {
            final Matcher matcher = FILE_NAME_PATTERN.matcher(fileOrDirectory.getName());
            if (matcher.matches()) {
                configuratie.add(matcher.group(5).toLowerCase());
            }
        } else {
            for (final File child : fileOrDirectory.listFiles()) {
                addConfiguratie(configuratie, child);
            }
        }
    }
}
