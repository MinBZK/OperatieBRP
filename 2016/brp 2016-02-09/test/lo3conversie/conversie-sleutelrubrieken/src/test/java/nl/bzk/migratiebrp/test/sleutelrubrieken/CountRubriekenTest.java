/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.sleutelrubrieken;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class CountRubriekenTest {

    private static final Pattern RUBRIEK_PATTERN = Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{2}");

    @Test
    public void test() throws IOException {
        final Map<String, Integer> result = new TreeMap<>();

        final File inputFile = new File("D:\\mGBA\\autorisatie\\actueel\\spontaan - alleen actueel.txt");
        for (final String line : Files.readAllLines(inputFile.toPath(), Charset.defaultCharset())) {
            final Matcher matcher = RUBRIEK_PATTERN.matcher(line);
            while (matcher.find()) {
                addOne(result, matcher.group());
            }
        }

        for (final Map.Entry<String, Integer> entry : result.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    private void addOne(final Map<String, Integer> result, final String group) {
        if (!result.containsKey(group)) {
            result.put(group, 0);
        }

        result.put(group, result.get(group) + 1);
    }
}
