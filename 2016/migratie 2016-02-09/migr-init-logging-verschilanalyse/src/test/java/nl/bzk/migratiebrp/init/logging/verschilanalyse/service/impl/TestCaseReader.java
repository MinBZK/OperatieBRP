/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.verschilanalyse.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public class TestCaseReader extends PathMatchingResourcePatternResolver {

    private final String testcase;
    private final Resource dataFolder;

    public TestCaseReader(final Resource dataFolder, final String testcase) {
        this.dataFolder = dataFolder;
        this.testcase = testcase.substring(0, testcase.lastIndexOf("."));
    }

    /**
     * Geef de waarde van input lo3.
     *
     * @return input lo3
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public String getInputLo3() throws IOException {
        final Set<Resource> files = doFindMatchingFileSystemResources(dataFolder.getFile(), testcase + ".txt");
        String lg01 = readFile(files.iterator().next().getFile());
        if (lg01.substring(0, 12).equalsIgnoreCase("00000000lg01")) {
            lg01 = lg01.substring(49);
        }
        return lg01;
    }

    /**
     * Geef de waarde van input brp lo3 file.
     *
     * @return input brp lo3 file
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public String getInputBrpLo3File() throws IOException {
        final Set<Resource> files = doFindMatchingFileSystemResources(dataFolder.getFile(), testcase + ".xml");
        return readFile(files.iterator().next().getFile());
    }

    /**
     * Geef de waarde van expected fingerprints.
     *
     * @return expected fingerprints
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public Set<String> getExpectedFingerprints() throws IOException {
        final Set<Resource> files = doFindMatchingFileSystemResources(dataFolder.getFile(), testcase + "-expected.txt");
        return readFileAsSet(files.iterator().next().getFile());
    }

    private String readFile(final File file) throws IOException {
        final StringBuilder result = new StringBuilder();
        try (
            BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            while (line != null) {
                result.append(line);
                line = br.readLine();
            }
        }
        return result.toString();
    }

    private Set<String> readFileAsSet(final File file) throws IOException {
        final Set<String> result = new HashSet<>();
        try (
            BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            while (line != null) {
                if (!line.isEmpty()) {
                    result.add(line);
                }
                line = br.readLine();
            }
        }
        return result;
    }
}
