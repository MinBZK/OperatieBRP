/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test;

import static java.lang.String.format;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ignore
@RunWith(BlockJUnit4ClassRunner.class)
public class PropertyFilterTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyFilterTest.class);

    private final Map<String, Properties> propertyFiles = new HashMap<>();

    @Before
    public void setUp() throws IOException {

        final List<String> results = new ArrayList<>();
        // create new filename filter
        final FilenameFilter propertyFilenameFilter = new FilenameFilter() {

            @Override
            public boolean accept(final File dir, final String name) {
                if (name.lastIndexOf('.') > 0) {
                    // get last index for '.' char
                    final int lastIndex = name.lastIndexOf('.');

                    // get extension
                    final String str = name.substring(lastIndex);

                    // match path name extension
                    if (str.equals(".properties")) {
                        return true;
                    }
                }
                return false;
            }
        };
        final File[] files = new File("./target/test-classes").listFiles(propertyFilenameFilter);

        if (files != null) {
            for (final File file : files) {
                if (file.isFile()) {
                    results.add(file.getName());
                }
            }
        }

        for (final String fileName : results) {
            final InputStream in =
                    getClass().getClassLoader().getResourceAsStream(fileName);
            final Properties brp = new Properties();
            brp.load(in);
            propertyFiles.put(fileName, brp);
            LOGGER.info("Property file {} ingeladen met {} properties", fileName, brp.stringPropertyNames().size());
        }
    }

    @Test
    public void testFilteringCorrect() {
        boolean filterSucces = true;
        int files = 0;
        final Map<String, String> errorReport = new HashMap<>();
        for (final Map.Entry<String, Properties> propertyFile : propertyFiles.entrySet()) {
            boolean illegalTokenFound = false;
            final Properties file = propertyFile.getValue();
            for (final Object key : file.keySet()) {
                final String value = file.getProperty((String) key);
                // $ is toegestaan mits er default waarde wordt opgegeven via :
                if (value.contains("$") && !value.contains(":")) {
                    illegalTokenFound = true;
                    LOGGER.error("FOUT: {} {}={}", propertyFile.getKey(), key, value);
                    errorReport.put(propertyFile.getKey(), (String) key);
                }
            }
            if (illegalTokenFound) {
                filterSucces = false;
            }
            files++;
        }

        Assert.assertTrue(
                "Minimaal 1 file moet gefiltered zijn! Test werkt met maven classpath, dus niet in IDEA.",
                files > 0);
        String gevondenFouten = "======== OVERZICHT VAN FILTER FOUTEN =======\nbestandsnaam\t\t\t\t\tproperty naam\n";
        for (final Map.Entry<String, String> fout : errorReport.entrySet()) {
            gevondenFouten += format("%s:\t\t%s\n", fout.getKey(), fout.getValue());
        }
        if (errorReport.size() > 0) {
            LOGGER.error(gevondenFouten);
        }
        Assert.assertTrue(gevondenFouten, errorReport.size() == 0);
        Assert.assertTrue(filterSucces);
    }

    protected class Property implements Comparable<Property> {

        private final String name;
        private final String value;
        private final String filename;

        public Property(final String naam, final String waarde, final String bestandsnaam) {
            name = naam;
            value = waarde;
            filename = bestandsnaam;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }

        public String getFilename() {
            return filename;
        }

        @Override
        public int compareTo(final Property property) {
            if (property.getName().equalsIgnoreCase(this.getName())) {
                return property.getFilename().compareTo(this.getFilename());
            } else {
                return property.getName().compareTo(this.getName());
            }

        }
    }

    @Test
    public void testCheckReport() {

        final List<Property> overzicht = new ArrayList<>();
        for (final Map.Entry<String, Properties> propertyFile : propertyFiles.entrySet()) {
            final Properties file = propertyFile.getValue();
            for (final Object key : file.keySet()) {
                final String value = file.getProperty((String) key);
                overzicht.add(new Property((String) key, value, propertyFile.getKey()));
            }
        }

        Collections.sort(overzicht);
        for (final Property property : overzicht) {
            LOGGER.info(format("%-80.80s = %-80.80s;\t\t%-40s", property.getName(), property.getValue(), property.getFilename()));
        }
    }
}
