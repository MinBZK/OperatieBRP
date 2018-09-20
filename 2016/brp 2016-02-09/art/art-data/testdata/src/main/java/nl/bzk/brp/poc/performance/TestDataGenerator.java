/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.performance;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

/**
 */
public final class TestDataGenerator {

    private static final int    AANTAL_TESTSETS              = 8;
    private static final int    AANTAL_LEVERINGSAUTORISATIES = 100;
    private static final String OUTPUT_FILE_FORMAT           = System.getProperty("user.dir")
        + "/art/art-data/testdata/target/POC_performance_testdata_%s.sql";

    private TestDataGenerator() {
        //
    }

    /**
     * @param args args.
     * @throws IOException IOException
     */
    public static void main(final String[] args) throws IOException {

        final InputStream resource = TestDataGenerator.class.getResourceAsStream("/performance-poc/performance-poc-testdata.sql");
        final List<String> lines = IOUtils.readLines(resource);
        final String input = StringUtils.join(lines.toArray(), System.lineSeparator());

        int totaalAantalLeveringsautorisaties = AANTAL_LEVERINGSAUTORISATIES;
        for (int j = 0; j < AANTAL_TESTSETS; j++) {
            final StringBuilder builder = new StringBuilder();
            builder.append("truncate kern.partij cascade;\n");
            builder.append("truncate autaut.levsautorisatie cascade;\n");

            builder.append(String.format("INSERT INTO kern.partij (id,srt,code,naam,datingang,dateinde,indverstrbeperkingmogelijk) VALUES('%s','7',"
                + "'199903', 'BRP PARTIJ', '19540101', null,'false');%n", totaalAantalLeveringsautorisaties + 1));
            //builder.append("delete FROM kern.pers;\n");
            for (int i = 0; i < totaalAantalLeveringsautorisaties; i++) {
                appendLeveringsautorisatie(builder, i, input);
            }

            System.out.println("output file: " + OUTPUT_FILE_FORMAT);
            try (final FileWriter output = new FileWriter(String.format(OUTPUT_FILE_FORMAT, totaalAantalLeveringsautorisaties))) {
                IOUtils.write(builder.toString(), output);
                totaalAantalLeveringsautorisaties *= 2;
            }

        }
    }

    private static void appendLeveringsautorisatie(final StringBuilder builder, final int i, final String input) {
        String script = input.replace("{leveringsautorisatieId}", String.valueOf(i));
        script = script.replace("{leveringsautorisatieNaam}", "testabo" + i);
        script = script.replace("{populatieBeperking}", "WAAR");
        script = script.replace("{authenticatiemiddelId}", String.valueOf(i));
        script = script.replace("{partijId}", String.valueOf(i));
        script = script.replace("{certificaatId}", String.valueOf(i));
        script = script.replace("{afleverwijzeId}", String.valueOf(i));
        script = script.replace("{toegangLeveringsautorisatieId}", String.valueOf(i));
        script = script.replace("{dienstId}", String.valueOf(i));
        script = script.replace("{certificaatSignature}", String.valueOf(i));
        builder.append(script);
    }
}
