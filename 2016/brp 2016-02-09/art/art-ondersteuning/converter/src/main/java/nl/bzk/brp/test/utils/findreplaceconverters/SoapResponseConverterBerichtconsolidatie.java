/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.utils.findreplaceconverters;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import nl.bzk.brp.test.utils.ConverterConfiguratie;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class SoapResponseConverterBerichtconsolidatie {

    public static void main(final String[] args) throws Exception {
        new SoapResponseConverterBerichtconsolidatie().startConversie();
    }

    private List<Map.Entry<String, String>> findReplaceValues;

    public SoapResponseConverterBerichtconsolidatie() throws Exception {
        findReplaceValues = ConverterConfiguratie.maakCustomPropertyMap(
                new File("D:/Werk/QSD/mGBA/Eclipse Workspace/art-utils-trunk/utils/converter/src/test/resources/Berichtconsolidatie/find-replace-response.properties"));
    }

    public void startConversie() throws Exception {
        String pad = "D:/Werk/QSD/mGBA/Eclipse Workspace/art-levering-synchronisatie-trunk/";
        Collection<File> inputFiles = FileUtils.listFiles(new File(pad), new String[] {"xml"}, true);
        for (File inputFile : inputFiles) {
            // Skip niet soapresponse files
            if (!inputFile.getName().toLowerCase().endsWith("soapresponse.xml")) {
                continue;
            } else {
                System.out.println("Now converting " + inputFile.getName() + " ...");
            }

            String newContents = this.pasFindReplaceToe(inputFile);

            FileUtils.write(inputFile, newContents);
        }
    }

    private String pasFindReplaceToe(final File inputFile) throws Exception {
        String inputContent = IOUtils.toString(new FileInputStream(inputFile));
        for (Entry<String, String> findReplaceEntry : findReplaceValues) {
            // Vervang de oude tekst door de nieuwe tekst.
            inputContent = inputContent.replaceAll(findReplaceEntry.getKey(), findReplaceEntry.getValue());
        }
        return inputContent;
    }

}
