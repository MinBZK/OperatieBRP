/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import nl.gba.gbav.impl.util.configuration.ServiceLocatorSpringImpl;
import nl.gba.gbav.lo3.PLData;
import nl.gba.gbav.lo3.util.PLBuilderFactory;
import nl.gba.gbav.util.configuration.ServiceLocator;
import nl.ictu.spg.AbstractTest;
import nl.ictu.spg.common.util.transaction.TransactionAssistant;
import nl.ictu.spg.domain.lo3.util.LO3LelijkParser;
import nl.ictu.spg.domain.lo3.util.LO3Meta;
import nl.ictu.spg.domain.pl.util.PLAssembler;
import nl.ictu.spg.system.BerichtTester;
import nl.ictu.spg.system.IDVTester;
import nl.ictu.spg.system.PLTester;
import nl.ictu.spg.system.SystemTestConstant;

/**
 * Main voor het omzetten van lelijk bestanden in de gbav database.
 */
public final class LoadLelijkInGbavDB extends AbstractTest {

    private static final int ANUMMER_ELEMENT = 110;
    private static final int LG01_KOP_LENGTE = 49;

    static {
        // Nodig voor de PLData builder.
        try {
            final String id = System.getProperty("gbav.deployment.id", "unit-test");
            final String context =
                    System.getProperty("gbav.deployment.context", "classpath:config/deploymentContext.xml");
            ServiceLocator.initialize(new ServiceLocatorSpringImpl(context, id));
        } catch (final IllegalStateException e) {
            e.printStackTrace();
        }
    }

    private LoadLelijkInGbavDB() {
        super();
    }

    /**
     * Main voor het omzetten van lelijk bestanden in de gbav database.
     * 
     * @param args
     *            Argumenten.
     * @throws IOException
     *             Als er een fout optreedt.
     */
    public static void main(final String[] args) throws IOException {
        String dir = null;
        if (args != null && args.length > 0) {
            dir = args[0];
        }
        if (dir == null || dir.isEmpty()) {
            System.out.println("Directory met de bestanden die eindigen op _lelijk is LEEG! script wordt gestopt");
            System.exit(-1);
        }
        System.out.println("Directory met de bestanden die eindigen op _lelijk: " + dir);
        final String fileExt = "lelijk";
        final File fileDir = new File(dir);
        for (final File child : fileDir.listFiles()) {
            verwerkBestand(dir, fileExt, child);
        }
    }

    private static void verwerkBestand(final String dir, final String fileExt, final File child) {
        if (child.getName().contains(fileExt) && !child.getName().contains("mooi")) {
            System.out.println("naam: " + child.getName());
            String inhoud = null;
            try {
                inhoud = readFileAsString(child, false);
            } catch (final IOException e) {
                e.printStackTrace();
            }

            final PLData pl = PLBuilderFactory.getPLDataBuilder().create();
            final PLAssembler assembler = new PLAssembler();
            assembler.setUseTeletex(true);
            assembler.startOfTraversal(pl);
            final LO3LelijkParser parser = new LO3LelijkParser();
            parser.parse(inhoud, assembler);

            final String anr = pl.getCategorie(1).getRubriek(ANUMMER_ELEMENT).toString();
            if (inhoud.contains("Lg01") || inhoud.contains("La01")) {
                // Strip kop 49 chars
                inhoud = inhoud.substring(LG01_KOP_LENGTE, inhoud.length());
            }
            try {
                String mooi = PLTester.lelijkToMooiPL(inhoud);
                mooi =
                        "00000000Lg01\n" + "20130101000000000" + anr + "0000000000\n" + "_" + mooi.length() + "\n"
                                + mooi;

                final File mooiFile = new File(dir + child.getName() + "_mooi");
                if (!mooiFile.exists()) {
                    mooiFile.createNewFile();
                }
                final OutputStream outStream = new FileOutputStream(mooiFile);
                final BufferedOutputStream buffOut = new BufferedOutputStream(outStream);
                buffOut.write(mooi.getBytes());
                buffOut.close();

                final LoadLelijkInGbavDB load = new LoadLelijkInGbavDB();
                load.requiresPL(dir, mooiFile.getName());
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String readFileAsString(final File file, final boolean enter) throws java.io.IOException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
        String line = "";
        String results = "";
        while ((line = reader.readLine()) != null) {
            results += line;
            if (enter) {
                results += "\n";
            }
        }
        reader.close();
        return results;
    }

    @Override
    public long requiresPL(final String baseName, final String plName) {
        final PLTester pl = new PLTester();
        long act = -1;
        if (pl.readMooiFormaatFile(new File(baseName + plName))) {
            /*
             * Een La01 bericht heeft geen A-nummer in de kop zodat pl geen A-nummer heeft. Zet deze expliciet en maak
             * er een Lg01 bericht van want anders werkt pl.diff() niet.
             */
            if (PLTester.LA01.equals(pl.getBerichtNummer())) {
                pl.setANumber(pl.getRubriek(LO3Meta.GS_PERSOON, 0, 0, LO3Meta.RUBRIEK_A_NR));
                pl.setBerichtNummer(PLTester.LG01);
            }
            // Zoek en diff de PL
            if (!SystemTestConstant.CHECK_OK.equals(pl.diff())) {
                // Als de PL bestaat, deactiveer hem dan
                // pl.deactivate();
                // Creeer IDV processor
                final IDVTester updater = new IDVTester();
                // updater.setSleepMillies(0);
                // updater.setRuns(1);
                txAssistant = (TransactionAssistant) ServiceLocator.getInstance().getService("TXAssistant");
                txAssistant.invokeWithinNewTx(updater, "cleanupActivities");
                txAssistant.invokeWithinNewTx(updater, "cleanupMessagesToProcess");
                final BerichtTester bt = new BerichtTester(1, pl.getRubriek(8, 0, 0, 910) + "010", baseName, plName);
                bt.setPl(pl);
                act = bt.createActivity();
                // Verwerk bericht
                txAssistant.invokeWithinNewTx(updater, "runOnce");
                System.out.println("Aantal verwerkte berichten: " + updater.processedTotal);
            } else {
                System.out.println("PL is up to date");
            }
        } else {
            System.out.println("Failure to load PL: " + plName);
        }
        return act;
    }
}
