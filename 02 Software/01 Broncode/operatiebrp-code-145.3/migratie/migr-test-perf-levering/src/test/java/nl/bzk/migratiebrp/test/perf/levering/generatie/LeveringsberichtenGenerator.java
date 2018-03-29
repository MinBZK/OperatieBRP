/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.perf.levering.generatie;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.commons.io.IOUtils;

public class LeveringsberichtenGenerator {

    private static final Integer AANTAL_LEVERINGSBERICHTEN = 10000;

    private static final String VULLING_1K =
            "00000000Gv01000000000000000007472459051000000000001716011850110010747245905101200090691579590210004Ab"
                    + "el0240006Jansen03100081926042503200040518033000460300410001V6110001E82100040518822000819260"
                    + "4258230012Geboorteakte851000819260425861000819260426021950110010980353984001200099246537110"
                    + "210003Jan0240010Langenberg03100081912020103200040518033000460300410001M62100081926042582100"
                    + "0405188220008192604258230012Geboorteakte851000819260425861000819260426032040110010529383637"
                    + "301200098426646950210011Mette-marit0240011Qualisaters03100081924022303200040518033000460300"
                    + "410001M621000819260425821000405188220008192604258230012Geboorteakte851000819260425861000819"
                    + "26042604096051000400016310003001821000405188220008192604258230012Geboorteakte85100081926042"
                    + "5861000819260426051770110010240587387001200093446485520210009Xantipper0240001V0310008197104"
                    + "2203200040518033000460300410001M06100081922061406200040518063000460301510001H85100081926042"
                    + "5861000819260426070776810008192604256910004051870100010801000400018020017192604250000000008"
                    + "710001P081270";

    public static void main(final String[] args) {
        final File baseDir = new File("D:\\workspaces\\mGBA\\isc-code\\migr-perf-levering\\test\\levering");

        if (args == null || args.length == 0) {
            // Standaard generatie.
            maakLeveringsBerichtenZip(new File(baseDir + "-" + AANTAL_LEVERINGSBERICHTEN, "1k"), 1, AANTAL_LEVERINGSBERICHTEN);
            maakLeveringsBerichtenZip(new File(baseDir + "-" + AANTAL_LEVERINGSBERICHTEN, "2k"), 2, AANTAL_LEVERINGSBERICHTEN);
            maakLeveringsBerichtenZip(new File(baseDir + "-" + AANTAL_LEVERINGSBERICHTEN, "5k"), 5, AANTAL_LEVERINGSBERICHTEN);
            maakLeveringsBerichtenZip(new File(baseDir + "-" + AANTAL_LEVERINGSBERICHTEN, "10k"), 10, AANTAL_LEVERINGSBERICHTEN);
            maakLeveringsBerichtenZipGemengdeGrootte(
                    new File(baseDir + "-" + AANTAL_LEVERINGSBERICHTEN, "gemengd"),
                    AANTAL_LEVERINGSBERICHTEN);
        } else {
            if (args.length == 1) {
                // Afwijkende grootte.
                final Integer grootte = Integer.valueOf(args[0]);
                maakLeveringsBerichtenZip(
                        new File(baseDir + "-" + AANTAL_LEVERINGSBERICHTEN, grootte + "k"),
                        grootte,
                        AANTAL_LEVERINGSBERICHTEN);
            } else if (args.length == 2) {
                final Integer grootte = Integer.valueOf(args[0]);
                final Integer aantal = Integer.valueOf(args[1]);
                maakLeveringsBerichtenZip(new File(baseDir + "-" + AANTAL_LEVERINGSBERICHTEN, grootte + "k"), grootte, aantal);
            } else {
                System.out.println("Ongeldig aantal parameters meegegeven. Aanroep: LeveringsberichtenGenerator "
                        + "<grootte in kb> <aantal leveringsberichten>");
                System.exit(1);
            }
        }

    }

    private static void maakLeveringsBerichtenZipGemengdeGrootte(final File directory, final int aantal) {
        System.out.println("Start generatie " + aantal + " leveringsberichten van gemengde/random grootte (<10kb).");
        directory.mkdirs();

        try (
                OutputStream os = new BufferedOutputStream(new FileOutputStream(new File(directory, "berichten.zip")))) {
            final ZipOutputStream zip = new ZipOutputStream(os);
            zip.setLevel(9);

            final long start = System.currentTimeMillis();
            for (int aantalGegenereerd = 1; aantalGegenereerd <= aantal; aantalGegenereerd++) {

                final int grootte = new Double(Math.random() * 10).intValue();

                final String filename = String.format("%08d-uit-levering-bericht" + aantalGegenereerd + ".txt", aantalGegenereerd);

                final StringBuilder vulling = new StringBuilder();
                for (int aantalKVulling = 1; aantalKVulling <= grootte; aantalKVulling++) {
                    vulling.append(VULLING_1K);
                }

                final ZipEntry entry = new ZipEntry(filename);
                zip.putNextEntry(entry);
                IOUtils.write(vulling, zip);
            }

            final long end = System.currentTimeMillis();
            final long duration = end - start;

            final double speed = aantal * 1000 / duration;
            System.out.println("Generatie: " + speed + " leveringsberichten/s (" + duration / 1000 + " s)");

            zip.finish();

        } catch (final IOException e) {
            System.out.println("Probleem tijdens generatie: " + e.getMessage());
        }

        System.out.println("Einde generatie " + aantal + " leveringsberichten van gemengde/random grootte (<10kb).");
    }

    private static void maakLeveringsBerichtenZip(final File directory, final int grootte, final int aantal) {
        System.out.println("Start generatie " + aantal + " leveringsberichten van " + grootte + "kb.");
        directory.mkdirs();

        try (
                OutputStream os = new BufferedOutputStream(new FileOutputStream(new File(directory, "berichten.zip")))) {
            final ZipOutputStream zip = new ZipOutputStream(os);
            zip.setLevel(9);

            final long start = System.currentTimeMillis();
            for (int aantalGegenereerd = 1; aantalGegenereerd <= aantal; aantalGegenereerd++) {

                final String filename = String.format("%08d-uit-levering.txt", aantalGegenereerd);

                final StringBuilder vulling = new StringBuilder();
                for (int aantalKVulling = 1; aantalKVulling <= grootte; aantalKVulling++) {
                    vulling.append(VULLING_1K);
                }

                final ZipEntry entry = new ZipEntry(filename);
                zip.putNextEntry(entry);
                IOUtils.write(vulling, zip);
            }

            final long end = System.currentTimeMillis();
            final long duration = end - start;

            final double speed = aantal * 1000 / duration;
            System.out.println("Generatie: " + speed + " leveringsberichten/s (" + duration / 1000 + " s)");

            zip.finish();

        } catch (final IOException e) {
            System.out.println("Probleem tijdens generatie: " + e.getMessage());
        }

        System.out.println("Einde generatie " + aantal + " leveringsberichten van " + grootte + "kb.");
    }

}
