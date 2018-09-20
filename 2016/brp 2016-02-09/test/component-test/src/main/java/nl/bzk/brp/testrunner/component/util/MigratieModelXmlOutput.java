/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.component.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.serialize.XmlEncoding;

/**
 * Util class waarbij het migratie BRP-model naar een XML-betand weg geschreven of gelezen kan worden.
 */
public final class MigratieModelXmlOutput {


    /**
     * Schrijft de meegegeven BRP migratie persoonslijst naar een XML bestand.
     *
     * @param brpPersoonslijst de persoonslijst die naar XML geschreven moet worden
     * @param xmlFile          het bestand waar de XML wordt weg geschreven
     * @throws IOException als het iets fout gaat tijdens het weg schrijven van de XML
     */
    public void schrijfXml(final BrpPersoonslijst brpPersoonslijst, final File xmlFile) throws IOException {
        final byte[] xmlData = brpPersoonslijst == null ? new byte[]{} : xmlSerializeObject(brpPersoonslijst);
        outputXml(xmlData, xmlFile);
    }

    /**
     * Leest een XML-bestand en decodeerd de XML naar het opgegeven clazz object.
     *
     * @param clazz   Class dat gevuld moet worden vanuit de XML
     * @param xmlFile het XML bestand
     * @param <T>     Type klasse dat moet worden terug gegeven
     * @return het gevulde clazz-Object
     * @throws IOException als het bestand niet gelezen kan worden
     */
    public static <T> T readXml(final Class<T> clazz, final File xmlFile) throws IOException {
        if (!xmlFile.exists()) {
            return null;
        }

        try (FileInputStream xmlFis = new FileInputStream(xmlFile)) {
            return XmlEncoding.decode(clazz, xmlFis);
        }
    }


    private byte[] xmlSerializeObject(final Object object) throws IOException {
        try (ByteArrayOutputStream boas = new ByteArrayOutputStream()) {
            XmlEncoding.encode(object, boas);
            return boas.toByteArray();
        }
    }

    private void outputXml(final byte[] xmlData, final File xmlFile) throws IOException {
        if (xmlFile != null) {
            final File parentFile = xmlFile.getParentFile();
            final boolean okToWrite = parentFile.exists() || parentFile.mkdirs();

            try (FileOutputStream xmlFos = new FileOutputStream(xmlFile)) {
                if (okToWrite) {
                    // write XML to file
                    xmlFos.write(xmlData);
                } else {
                    throw new IOException("Kan directory/bestand niet aanmaken");
                }
            }
        }
    }
}
