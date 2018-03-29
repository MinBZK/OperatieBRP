/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.schrijvers;

import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;
import difflib.myers.Equalizer;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Handler, die verantwoordelijk is voor het maken van een Unified Diff, van twee bestanden.
 */
final class DiffHandler {

    private static final String LEADING_SPATIES = "^\\s +";
    private Equalizer equalizer = (o, t1) -> ((String) o).replaceAll(LEADING_SPATIES, "").equals(((String) t1).replaceAll(LEADING_SPATIES, ""));

    /**
     * Maakt een diff van twee bestanden. Bij het bepalen van het verschil wordt rekening gehouden met de volgende
     * voorwaardes:
     * <p>
     * 1. Leading whitespace wordt genegeerd bij het bepalen van het verschil op een regel 2. Indien de originele regel
     * (expected) de combinatie >*< of "*" bevat, wordt dit resultaat weggelaten. Het algoritme neemt hier aan dat het
     * om een wildcard gaat.
     * <p>
     * Indien er verschillen worden geconstateerd, wordt de diff opgeslagen.
     *
     * @param verwacht het verwachte bestand
     * @param werkelijk het actuele resultaat
     * @param output de locatie van de diff-file, t.o.v. van outputDir
     * @param filterWildcards boolean
     * @throws IOException on file error
     */
    void schrijfDiff(final Path verwacht, final Path werkelijk, final Path output, final boolean filterWildcards) throws IOException {
        final List<String> aFile = Files.readAllLines(verwacht);
        final List<String> bFile = Files.readAllLines(werkelijk);

        final Patch<String> patch = DiffUtils.diff(aFile, bFile, equalizer);

        if (filterWildcards) {
            final List<Delta<String>> geFilteredeLijstMetDeltas = new ArrayList<>();
            for (Delta<String> delta : patch.getDeltas()) {
                if (!Delta.TYPE.CHANGE.equals(delta.getType()) || !deltaBevatWildCards(delta.getOriginal().getLines())) {
                    geFilteredeLijstMetDeltas.add(delta);
                }

            }
            patch.getDeltas().clear();
            patch.getDeltas().addAll(geFilteredeLijstMetDeltas);
        }

        if (!patch.getDeltas().isEmpty()) {
            final List<String> unifiedDiff = DiffUtils.generateUnifiedDiff("expected", "verkregen", aFile, patch, 3);
            final StringBuilder content = new StringBuilder();
            unifiedDiff.forEach(line -> content.append(line).append("\n"));
            schrijfFile(output, content.toString());
        }

    }

    private boolean deltaBevatWildCards(final List<String> lines) {
        for (String line : lines) {
            if (line.length() > 0) {
                if (Pattern.matches(".*[>\"]\\*[<\"].*", line)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void schrijfFile(final Path path, final String content) throws IOException {
        Files.write(path, content.getBytes(Charset.defaultCharset()));
    }
}
