/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.perf.isc.generatie;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lg01Bericht;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

public class Lo3PersoonslijstGeneratorTest {
    private static final Lo3PersoonslijstGenerator PL_GENERATOR = new Lo3PersoonslijstGenerator();

    @Test
    public void test() throws Exception {
        final Lo3Persoonslijst pl = PL_GENERATOR.genereer();
        System.out.println(pl);
        final Lg01Bericht lg01 = new Lg01Bericht();
        lg01.setLo3Persoonslijst(pl);
        lg01.setHeader(Lo3HeaderVeld.A_NUMMER, String.valueOf(pl.getActueelAdministratienummer()));
        lg01.setHeader(Lo3HeaderVeld.DATUM_TIJD, "20130712132012000");

        System.out.println(lg01);
        System.out.println(lg01.format());
        Assert.assertTrue(lg01 instanceof Lg01Bericht);
    }

    // @org.junit.Ignore
    @Test
    public void maakPlSyncBerichten() {
        final File baseDir = new File("D:\\mGBA\\integratie-test-cases\\uc202");

        // maakLg01Berichten(new File(baseDir, "1a"), 1);
        // maakLg01Berichten(new File(baseDir, "1b"), 1);
        // maakLg01Berichten(new File(baseDir, "1c"), 1);
        // maakLg01Berichten(new File(baseDir, "1d"), 1);
        // maakLg01Berichten(new File(baseDir, "1e"), 1);
        // maakLg01Berichten(new File(baseDir, "10a"), 10);
        // maakLg01Berichten(new File(baseDir, "10b"), 10);
        // maakLg01Berichten(new File(baseDir, "10c"), 10);
        // maakLg01Berichten(new File(baseDir, "10d"), 10);
        // maakLg01Berichten(new File(baseDir, "10e"), 10);
        // maakLg01Berichten(new File(baseDir, "25a"), 25);
        // maakLg01Berichten(new File(baseDir, "25b"), 25);
        // maakLg01Berichten(new File(baseDir, "25c"), 25);
        // maakLg01Berichten(new File(baseDir, "25d"), 25);
        // maakLg01Berichten(new File(baseDir, "25e"), 25);
        // maakLg01Berichten(new File(baseDir, "50a"), 50);
        // maakLg01Berichten(new File(baseDir, "50b"), 50);
        // maakLg01Berichten(new File(baseDir, "50c"), 50);
        // maakLg01Berichten(new File(baseDir, "50d"), 50);
        // maakLg01Berichten(new File(baseDir, "50e"), 50);
        maakLg01Berichten(new File(baseDir, "500a"), 500);
        maakLg01Berichten(new File(baseDir, "500b"), 500);
        maakLg01Berichten(new File(baseDir, "500c"), 500);
        maakLg01Berichten(new File(baseDir, "500d"), 500);
        maakLg01Berichten(new File(baseDir, "500e"), 500);
        // maakLg01BerichtZip(new File(baseDir, "100"), 100);
        // maakLg01BerichtZip(new File(baseDir, "250"), 250);
        // maakLg01BerichtZip(new File(baseDir, "500"), 500);
        // maakLg01BerichtZip(new File(baseDir, "1k"), 1_000);
        // maakLg01BerichtZip(new File(baseDir, "2k5"), 2_500);
        // maakLg01BerichtZip(new File(baseDir, "5k"), 5_000);
        // maakLg01BerichtZip(new File(baseDir, "10k"), 10_000);
        // maakLg01BerichtZip(new File(baseDir, "25k"), 25_000);
        // maakLg01BerichtZip(new File(baseDir, "50k"), 50_000);
        // maakPlSyncBerichtZip(new File(baseDir, "100k"), 100_000);
        // maakPlSyncBerichtZip(new File(baseDir, "250k"), 250_000);
        // maakPlSyncBerichtZip(new File(baseDir, "500k"), 500_000);
        // maakPlSyncBerichtZip(new File(baseDir, "1m"), 1_000_000);
        // maakPlSyncBerichtZip(new File(baseDir, "2m"), 2_000_000);
        // maakPlSyncBerichtZip(new File(baseDir, "5m"), 5_000_000);
        // maakPlSyncBerichtZip(new File(baseDir, "10m"), 10_000_000);
        // maakPlSyncBerichtZip(new File(baseDir, "20m"), 20_000_000);
        // maakPlSyncBerichtZip(new File(baseDir, "50m"), 50_000_000);
    }

    private void maakLg01Berichten(final File directory, final int aantal) {
        System.out.println("Start generatie " + aantal + " pl-en.");
        directory.mkdirs();

        try {
            final long start = System.currentTimeMillis();
            for (int i = 1; i <= aantal; i++) {
                final Lo3Persoonslijst pl = PL_GENERATOR.genereer();

                final Lg01Bericht lg01 = new Lg01Bericht();
                lg01.setLo3Persoonslijst(pl);
                lg01.setHeader(Lo3HeaderVeld.A_NUMMER, String.valueOf(pl.getActueelAdministratienummer()));
                lg01.setHeader(Lo3HeaderVeld.DATUM_TIJD, "20130712132012000");

                final String filename = String.format("%05d-uit-mailbox-pl-%10d.txt", i, pl.getActueelAdministratienummer());
                final String propertiesFilename = filename + ".properties";

                try (
                        FileOutputStream fos = new FileOutputStream(new File(directory, filename))) {
                    IOUtils.write(lg01.format(), fos);
                }
                try (
                        FileWriter writer = new FileWriter(new File(directory, propertiesFilename))) {
                    writer.write("lo3Gemeente=");
                    writer.write(pl.getVerblijfplaatsStapel().getLaatsteElement().getInhoud().getGemeenteInschrijving().getWaarde());
                    writer.write("\nbrpGemeente=3000200");

                }
            }
            final long end = System.currentTimeMillis();
            final long duration = end - start;

            final double speed = aantal * 1000 / duration;
            System.out.println("Generatie: " + speed + " pl/s + (" + duration / 1000 + " s)");

        } catch (final IOException e) {
            System.out.println("Probleem tijdens generatie: " + e.getMessage());
        }

        System.out.println("Einde generatie " + aantal + " pl-en.");
    }

    private void maakLg01BerichtZip(final File directory, final int aantal) {
        System.out.println("Start generatie " + aantal + " pl-en.");
        directory.mkdirs();

        try (
                OutputStream os = new BufferedOutputStream(new FileOutputStream(new File(directory, "berichten.zip")))) {
            final ZipOutputStream zip = new ZipOutputStream(os);
            zip.setLevel(9);

            final long start = System.currentTimeMillis();
            for (int i = 1; i <= aantal; i++) {
                if (i % 100000 == 0) {
                    System.out.println("Pl " + i);
                }
                final Lo3Persoonslijst pl = PL_GENERATOR.genereer();

                final Lg01Bericht lg01 = new Lg01Bericht();
                lg01.setLo3Persoonslijst(pl);
                lg01.setHeader(Lo3HeaderVeld.A_NUMMER, String.valueOf(pl.getActueelAdministratienummer()));
                lg01.setHeader(Lo3HeaderVeld.DATUM_TIJD, "20130712132012000");

                final String filename = String.format("%05d-uit-voisc-pl-%10d.txt", i, pl.getActueelAdministratienummer());

                final ZipEntry entry = new ZipEntry(filename);
                zip.putNextEntry(entry);
                IOUtils.write(lg01.format(), zip);
            }
            final long end = System.currentTimeMillis();
            final long duration = end - start;

            final double speed = aantal * 1000 / duration;
            System.out.println("Generatie: " + speed + " pl/s + (" + duration / 1000 + " s)");

            zip.finish();

        } catch (final IOException e) {
            System.out.println("Probleem tijdens generatie: " + e.getMessage());
        }

        System.out.println("Einde generatie " + aantal + " pl-en.");
    }
}
