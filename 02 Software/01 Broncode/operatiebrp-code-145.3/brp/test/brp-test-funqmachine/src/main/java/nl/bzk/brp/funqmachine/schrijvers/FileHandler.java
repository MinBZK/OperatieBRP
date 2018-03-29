/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.schrijvers;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Doet alle bestandsafhandeling voor de ARTs.
 */
public final class FileHandler {
    private Path baseDir;
    private Path baseOutputDir;

    /**
     * Constructor. Deze class gaat er van uit dat deze gedraait wordt binnen maven/IDE en dus een target folder heeft.
     */
    public FileHandler() {
        baseDir = FileSystems.getDefault().getPath("./target");
        baseOutputDir = baseDir.resolve("funqmachine");
    }

    /**
     * Zorgt ervoor dat directories waarvan de aanwezigheid wordt aangenomen ook daadwerkelijk zijn gecreeerd.
     */
    void prepareDirectories() throws IOException {
        final Path reportsDir = baseDir.resolve("surefire-reports");
        Files.createDirectories(reportsDir);
    }

    /**
     * Geeft het {@link Path} terug van het meegegeven filename die in de outputDir moet komen.
     * @param filename een bestandsnaam
     * @return een {@link Path} van het meegegeven filename
     */
    public Path geefOutputFile(final String filename) {
        return baseOutputDir.resolve(filename);
    }

    /**
     * Schrijf de content naar een bestand. De file is relatief t.o.v. outputdir. Bijvoorbeeld, {@code report.sql} of
     * {@code ./data/request/een-request.xml}.
     * @param filename bestands naam
     * @param content bestand inhoud
     * @throws IOException bij file probleem
     */
    void schrijfFile(final String filename, final String content) throws IOException {
        final Path outputFile = geefOutputFile(filename);

        final Path outputDir = outputFile.getParent();
        if (!outputDir.toFile().exists()) {
            Files.createDirectories(outputDir);
        }
        schrijfContent(outputFile, content);
    }

    private void schrijfContent(final Path path, final String content) throws IOException {
        Files.write(path, (content == null ? "" : content).getBytes(Charset.defaultCharset()));
    }

    /**
     * Maakt een diff van twee bestanden. Bij het bepalen van het verschil wordt rekening gehouden met de volgende
     * voorwaardes:
     * <p>
     * 1. Leading whitespace wordt genegeerd bij het bepalen van het verschil op een regel 2. Indien de originele regel
     * (expected) de combinatie >*< of "*" bevat, wordt dit resultaat weggelaten. Het algoritme neemt hier aan dat het
     * om een wildcard gaat.
     * <p>
     * Indien er verschillen worden geconstateerd, wordt de diff opgeslagen.
     * @param verwacht het verwachte bestand
     * @param werkelijk het actuele resultaat
     * @param filename de locatie van de diff-file, t.o.v. van outputDir
     * @throws IOException bij bestands fout
     */
    void schrijfDiff(final String verwacht, final String werkelijk, final String filename) throws IOException {
        final DiffHandler diffHandler = new DiffHandler();
        diffHandler.schrijfDiff(geefOutputFile(verwacht), geefOutputFile(werkelijk), geefOutputFile(filename), false);
    }
}
