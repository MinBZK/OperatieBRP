/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.lo3vermooier;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;

/**
 * Lo3 bericht vermooier.
 */
public final class LO3BerichtVermooier {

    private LO3BerichtVermooier() {
        // Niet instantieerbaar
    }

    private static String readFile(final String filename) throws IOException {
        final File file = new File(filename);
        final StringBuilder tekst = new StringBuilder();

        if (file.exists() && !file.isDirectory()) {
            final Path path = Paths.get(filename);
            final List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            for (int i = 0; i < lines.size(); i++) {
                tekst.append(lines.get(i));
            }
            return tekst.toString();
        }
        System.err.println("Bestand bestaat niet of is een directory.");
        return null;
    }

    /**
     * Main.
     * @param args argumenten
     * @throws IOException bij lees fouten
     * @throws BerichtSyntaxException bij bericht syntax fouten
     */
    public static void main(final String[] args) throws IOException, BerichtSyntaxException {
        final String bestandsnaam;
        if (args != null && args.length == 1) {
            bestandsnaam = args[0];
        } else {
            System.out.println("Geen bericht opgegeven, default naar bericht.txt");
            bestandsnaam = "bericht.txt";
        }

        final String tekst;
        final File file = new File(bestandsnaam);
        if (file.exists()) {
            tekst = readFile(bestandsnaam);
        } else {
            tekst = bestandsnaam;
        }

        if (tekst != null) {
            new Lo3BerichtPrinter(tekst).print();
        }
    }
}
