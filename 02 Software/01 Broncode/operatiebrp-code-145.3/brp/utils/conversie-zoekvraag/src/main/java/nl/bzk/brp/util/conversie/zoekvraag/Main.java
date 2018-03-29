/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.conversie.zoekvraag;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.util.conversie.zoekvraag.xml.In0Root;
import nl.bzk.brp.util.conversie.zoekvraag.xml.InElement;
import nl.bzk.brp.util.conversie.zoekvraag.xml.Masker;
import nl.bzk.brp.util.conversie.zoekvraag.xml.ParameterItem;
import nl.bzk.brp.util.conversie.zoekvraag.xml.Parameters;
import org.apache.commons.io.IOUtils;
import org.springframework.util.Assert;

/**
 */
public class Main {


    private static final Logger LOGGER = LoggerFactory.getLogger();

    public static void main(String[] args) {

        LOGGER.info("Start conversie");
        Assert.isTrue(args.length > 0, "1 argument verwacht");

        final File inputFile = new File(args[0]);
        Assert.isTrue(inputFile.exists(), "Input bestand bestaat niet: " + args[0]);
        LOGGER.info("Input bestand: {}", inputFile.getAbsolutePath());

        final File outputFile = new File(inputFile.getParent(), inputFile.getName() + ".csv");
        LOGGER.info("Output bestand: {}", outputFile.getAbsolutePath());

        final In0Root root;
        try {
            root = unmarshall(inputFile);
        } catch (JAXBException | IOException e) {
            throw new IllegalStateException("Kan bestand niet mappen op java structuur", e);
        }

        try (FileOutputStream fos = new FileOutputStream(outputFile);
             OutputStreamWriter ow = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
             PrintWriter pw = new PrintWriter(ow)) {
            new CSVWriter(pw).toCsv(root);
        } catch (IOException e) {
            throw new IllegalStateException("Probleem bij wegschrijven CSV", e);
        }

        LOGGER.info("Klaar");
    }

    private static In0Root unmarshall(final File inputFile) throws JAXBException, IOException {
        final JAXBContext jaxbContext = JAXBContext.newInstance(In0Root.class, InElement.class, Masker.class, Parameters.class, ParameterItem.class);
        final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (In0Root) unmarshaller.unmarshal(maakXmlFile(inputFile));
    }

    private static File maakXmlFile(final File inputFile) throws IOException {
        final File fileMetRoot = new File(inputFile.getParentFile(), inputFile.getName() + ".root.xml");
        try (InputStream fileReader = new FileInputStream(inputFile);
             OutputStream fileWriter = new FileOutputStream(fileMetRoot)
        ) {
            fileWriter.write("<root>".getBytes(StandardCharsets.UTF_8));
            IOUtils.copy(fileReader, fileWriter);
            fileWriter.write("</root>".getBytes(StandardCharsets.UTF_8));
        }
        return fileMetRoot;
    }

}
