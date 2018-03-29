/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Collection;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

/**
 * LineRemover.
 */
public class LineRemover {

    @Test
    public void test() throws IOException, URISyntaxException {
        URL dirURL = LineRemover.class.getClassLoader().getResource("");
        String s = Paths.get("D:\\operatiebrp-code\\brp\\test\\brp-api-test\\src\\test\\resources").toString();
        Collection<File> files = FileUtils.listFiles(
            new File(s),
            new SuffixFileFilter("xml"),
            DirectoryFileFilter.DIRECTORY
        );

        for (File file : files) {
            System.out.println(file.getName());
            process(file);
        }

    }

    private void process(File inputFile) throws IOException {
        File tempFile = new File(inputFile.getName() + ".xml");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String lineToRemove = "brp:ontvangendeSysteem";
        String matcher = "brp:stuurgegevens";
        String stop = "/brp:stuurgegevens";

        String currentLine;
        boolean match = false;
        boolean active = false;
        while ((currentLine = reader.readLine()) != null) {
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();


            if (trimmedLine.contains(matcher)) {
                active = true;
            }
            if (active && trimmedLine.contains(stop)) {
                active = false;
            }

            if (active && trimmedLine.contains(lineToRemove)) {
                match = true;
                active = false;
                continue;
            }


            if (StringUtils.isNotBlank(currentLine)) {
                writer.write(currentLine + System.getProperty("line.separator"));
            }
        }
        writer.close();
        reader.close();
        if (!match) {
            return;
        }
        //Delete the original file
        if (!inputFile.delete()) {
            System.out.println("Could not delete file");
            return;
        }


        boolean successful = tempFile.renameTo(inputFile);
        System.out.println(successful);
    }


}
