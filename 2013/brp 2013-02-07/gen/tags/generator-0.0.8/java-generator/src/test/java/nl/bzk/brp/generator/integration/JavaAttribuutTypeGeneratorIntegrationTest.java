/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 * 
 */
package nl.bzk.brp.generator.integration;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import nl.bzk.brp.generator.GenerationPackageNames;
import nl.bzk.brp.generator.GenerationReport;
import nl.bzk.brp.generator.java.AbstractBmrTest;
import nl.bzk.brp.generator.java.DataTypeTranslator;
import nl.bzk.brp.generator.java.JavaAttribuutTypeBasisGenerator;
import nl.bzk.brp.generator.java.JavaAttribuutTypeGenerator;
import nl.bzk.brp.generator.java.domein.AttributeType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 
 *
 */
public class JavaAttribuutTypeGeneratorIntegrationTest extends AbstractBmrTest {

    private static final Logger logger = LoggerFactory.getLogger(JavaAttribuutTypeGeneratorIntegrationTest.class);

    @Autowired
    private JavaAttribuutTypeGenerator generator;

    @Autowired
    private JavaAttribuutTypeBasisGenerator generatorBasis;

    @Before
    public void setUp() throws Exception {
        generatorBasis.setBasePackage(GenerationPackageNames.ATTRIBUUTTYPE_BASIS_PACKAGE);
        generatorBasis.setGeneratedSourcesPath(prepareGeneratedSourcesDirectory(GenerationPackageNames.ATTRIBUUTTYPE_BASIS_PACKAGE.getSubPackage()));

        generator.setBasePackage(GenerationPackageNames.ATTRIBUUTTYPE_PACKAGE);
        generator.setGeneratedSourcesPath(prepareGeneratedSourcesDirectory(GenerationPackageNames.ATTRIBUUTTYPE_PACKAGE.getSubPackage()));

    }

    @Test
    public void shouldGenerateStatischAttribuutType() {
        AttributeType type = new AttributeType("Postcode");
        type.setPackageName(GenerationPackageNames.ATTRIBUUTTYPE_BASIS_PACKAGE.getPackage());
        type.setBaseType(DataTypeTranslator.getJavaClass("numerieke code"));
        type.setStatisch(true);
        String sourceCode = generatorBasis.genereerAttribuutTypeBasis(type);
        System.out.println(sourceCode);
        assertNotNull(sourceCode);
        assertTrue("Geen variabele className gevonden", sourceCode.indexOf("<className>") == -1);
        assertTrue("package naam komt niet voor", sourceCode.indexOf(GenerationPackageNames.ATTRIBUUTTYPE_BASIS_PACKAGE.getPackage()) != -1);
        assertTrue("Class moet afgeleid zijn van AbstractStatischAttribuutType",
                sourceCode.indexOf("AbstractStatischAttribuutType") > 0);
    }

    @Test
    public void shouldGenerateGegevensAttribuutType() {
        AttributeType type = new AttributeType("LengteInCm");
        type.setPackageName(GenerationPackageNames.ATTRIBUUTTYPE_PACKAGE.getPackage());
        type.setBaseType(DataTypeTranslator.getJavaClass("decimaal getal"));
        type.setStatisch(false);
        String sourceCode = generator.genereerAttribuutType(type);

        System.out.println(sourceCode);
        assertNotNull(sourceCode);
        assertTrue("Geen variabele className gevonden", sourceCode.indexOf("<className>") == -1);
        assertTrue("package naam komt niet voor", sourceCode.indexOf(GenerationPackageNames.ATTRIBUUTTYPE_PACKAGE.getPackage()) != -1);
        assertTrue("Class moet afgeleid zijn van LengteInCmBasis",
                sourceCode.indexOf("LengteInCmBasis") > 0);
    }

    @Test
    public void shouldGetAllAttribuutTypes() {
        GenerationReport raportBasis = generatorBasis.genereer();
        Assert.assertTrue(raportBasis.getErrors().size() == 0);
        GenerationReport raport = generator.genereer();
        Assert.assertTrue(raport.getErrors().size() == 0);
    }

}
