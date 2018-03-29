/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.koppelvlak;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import javax.xml.transform.stream.StreamSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class SchemaValidatorTest {

    private final Path voorbeeldbericht;

    public SchemaValidatorTest(final Path voorbeeldbericht) {
        this.voorbeeldbericht = voorbeeldbericht;
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Iterable<Path> data() throws IOException {
        final Path dir = Paths.get("src/main/resources/voorbeeldberichten");
        return Files.list(dir).collect(Collectors.toList());
    }

    @Test
    public void test() throws IOException {

        final File file = voorbeeldbericht.toFile();
        final SchemaValidator schemaValidator;
        final String fileName = file.getName();
        if (fileName.startsWith("bhg_")) {
            schemaValidator = SchemaValidator.BIJHOUDING_SCHEMA_BESTAND;
        } else if (fileName.startsWith("stv_")) {
            schemaValidator = SchemaValidator.STUF_SCHEMA_BESTAND;
        } else if (fileName.startsWith("vrb_")) {
            schemaValidator = SchemaValidator.VRIJBERICHT_SCHEMA_BESTAND;
        } else if (fileName.startsWith("lvg_bvg")) {
            schemaValidator = SchemaValidator.BEVRAGING_SCHEMA;
        } else if (
                fileName.startsWith("lvg_synPlaatsingAfnemerindicatie")
                        || fileName.startsWith("lvg_synVerwijderingAfnemerindicatie")) {
            schemaValidator = SchemaValidator.AFNMERINDICATIE_SCHEMA_BESTAND;
        } else if (fileName.startsWith("lvg_syn")) {
            schemaValidator = SchemaValidator.SYNCHRONISATIE_SCHEMA;
        } else if (fileName.startsWith("lvg_sel")) {
            schemaValidator = SchemaValidator.SELECTIE_SCHEMA;
        } else if (fileName.startsWith("isc_")) {
            schemaValidator = SchemaValidator.ISC_SCHEMA;
        } else {
            throw new RuntimeException("Kan bestand niet valideren: " + file);
        }

        final StreamSource streamSource = new StreamSource(file);
        schemaValidator.valideer(streamSource);
    }

}
