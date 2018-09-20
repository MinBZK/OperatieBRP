/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.performance.profiel2.builder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 */
public class Writers {

    final Writer pers;
    final Writer admhnd;
    final Writer actie;
    final Writer persadres;
    final Writer his_persadres;
    final Writer his_persafgeleidadministrati;
    final Writer persafnemerindicatie;
    final Writer his_persafnemerindicatie;
    final Writer relatie;
    final Writer his_relatie;
    final Writer betr;
    final Writer his_betr;

    Map<String, File> writerMap = new HashMap<>();

    public Writers() throws IOException {

        pers = newWriter("pers");
        admhnd = newWriter("admhnd");
        actie = newWriter("actie");
        persadres = newWriter("persadres");
        his_persadres = newWriter("his_persadres");
        his_persafgeleidadministrati = newWriter("his_persafgeleidadministrati");
        persafnemerindicatie = newWriter("persafnemerindicatie");
        his_persafnemerindicatie = newWriter("his_persafnemerindicatie");
        relatie = newWriter("relatie");
        his_relatie = newWriter("his_relatie");
        betr = newWriter("betr");
        his_betr = newWriter("his_betr");
    }

    private Writer newWriter(String naam) throws IOException {
        final File tempFile = File.createTempFile(naam, ".sql");
        tempFile.deleteOnExit();
        writerMap.put(naam, tempFile);
        return new BufferedWriter(new FileWriter(tempFile));
    }

    public void close() throws IOException {
        pers.close();
        admhnd.close();
        actie.close();
        persadres.close();
        his_persadres.close();
        his_persafgeleidadministrati.close();
        persafnemerindicatie.close();
        his_persafnemerindicatie.close();
        relatie.close();
        his_relatie.close();
        betr.close();
        his_betr.close();
    }


    public static void mergeWriters(Writers ... writers) throws IOException {


        final ForkJoinPool pool = new ForkJoinPool();
        final ForkJoinTask taak = pool.submit(new Merger(writers));
        taak.join();
    }

    private static class Merger extends RecursiveTask {

        final String keys [] = {"pers", "admhnd", "actie", "persadres", "his_persadres", "his_persafgeleidadministrati",
            "persafnemerindicatie", "his_persafnemerindicatie", "relatie", "his_relatie", "betr", "his_betr"};

        private final Writers [] writers;

        private Merger(final Writers[] writers) {
            this.writers = writers;
        }

        @Override
        protected Object compute() {
            final List<ForkJoinTask> verversCacheList = new LinkedList<>();

            for (String key : keys) {
                verversCacheList.add(new MergeTask(writers, key).fork());
            }
            for (final ForkJoinTask taak : verversCacheList) {
                taak.join();
            }
            return null;
        }
    }

    private static class MergeTask extends RecursiveTask<File> {

        private final Writers [] writers;
        private final String key;

        private MergeTask(final Writers[] writers, final String key) {
            this.writers = writers;
            this.key = key;
        }


        @Override
        protected File compute() {
            final List<File> fileList = new LinkedList<>();
            for (Writers writer : writers) {
                fileList.add(writer.writerMap.get(key));
            }
            File tempFile = null;
            try {
                tempFile = File.createTempFile(key, ".sql");
                mergeFiles(fileList.toArray(new File[fileList.size()]), tempFile);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }

            for (File file : fileList) {
                file.delete();
            }
            return tempFile;
        }
    }

    public static void mergeFiles(File[] files, File mergedFile) {

        FileWriter fstream = null;
        BufferedWriter out = null;
        try {
            fstream = new FileWriter(mergedFile, true);
            out = new BufferedWriter(fstream);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        for (File f : files) {
            System.out.println("merging: " + f.getName());
            try (BufferedReader in = new BufferedReader(new FileReader(f))){
                String aLine;
                while ((aLine = in.readLine()) != null) {
                    out.write(aLine);
                    //out.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
