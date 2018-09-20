/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

import nl.gba.gbav.impl.util.configuration.ServiceLocatorSpringImpl;
import nl.gba.gbav.util.configuration.ServiceLocator;
import nl.ictu.spg.system.PLTester;

/**
 * Main voor het omzetten van lelijk bestanden naar mooi bestanden.
 */
public final class LelijkToMooi {

    private static final String MOOI_FILE_EXTENSIE = "_mooi";
    static {
        // Nodig voor de PLData builder.
        try {
            final String id = System.getProperty("gbav.deployment.id", "vergelijk");
            final String context = System.getProperty("gbav.deployment.context", "classpath:deploymentContext.xml");
            ServiceLocator.initialize(new ServiceLocatorSpringImpl(context, id));
        } catch (final IllegalStateException e) {
            e.printStackTrace();
            System.out.println("Iets fout gegaan bij hetg laden van de gbav context.");
        }
    }

    private LelijkToMooi() {
        super();
    }

    /**
     * Main voor het omzetten van lelijk bestanden naar mooi bestanden.
     * 
     * @param args
     *            Argumenten voor de main.
     */
    public static void main(final String[] args) {
        String dir = null;
        if (args != null && args.length > 0) {
            dir = args[0];
        }
        if (dir == null || dir.isEmpty()) {
            System.out.println("Directory met de bestanden die eindigen op _lelijk is LEEG! script wordt gestopt");
            System.exit(-1);
        }
        System.out.println("Directory met de bestanden die eindigen op _lelijk: " + dir);
        final String fileExt = "_lelijk";
        final File fileDir = new File(dir);
        for (final File child : fileDir.listFiles()) {
            verwerkBestand(dir, fileExt, child);
        }
    }

    private static void verwerkBestand(final String dir, final String fileExt, final File child) {
        if (child.getName().contains(fileExt) && !child.getName().contains(MOOI_FILE_EXTENSIE)) {
            System.out.println("File: " + child.getName());
            String inhoud = null;
            try {
                inhoud = readFileAsString(child);
            } catch (final IOException e) {
                e.printStackTrace();
            }
            System.out.println("inhoud = " + inhoud);
            final String mooi = PLTester.lelijkToMooiPL(inhoud);
            final String mooiDir = dir + child.getName() + MOOI_FILE_EXTENSIE;
            final File mooiFile = new File(mooiDir);
            if (!mooiFile.exists()) {
                try {
                    mooiFile.createNewFile();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                final OutputStream outStream = new FileOutputStream(mooiFile);
                final BufferedOutputStream buffOut = new BufferedOutputStream(outStream);
                buffOut.write(mooi.getBytes());
                buffOut.close();
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String readFileAsString(final File file) throws java.io.IOException {
        final BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = "";
        String results = "";
        while ((line = reader.readLine()) != null) {
            results += line;
        }
        reader.close();
        return results;
    }
}
