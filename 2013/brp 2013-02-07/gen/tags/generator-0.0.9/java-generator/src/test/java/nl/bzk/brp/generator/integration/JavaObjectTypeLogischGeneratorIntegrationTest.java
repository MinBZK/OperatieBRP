/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 * 
 */
package nl.bzk.brp.generator.integration;

import java.io.File;

import junit.framework.Assert;
import nl.bzk.brp.generator.GenerationReport;
import nl.bzk.brp.generator.java.AbstractBmrTest;
import nl.bzk.brp.generator.java.JavaEnumGenerator;
import nl.bzk.brp.generator.java.JavaObjectTypeLogischBasisGenerator;
import nl.bzk.brp.generator.java.JavaObjectTypeLogischGenerator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


/**
 *
 */
public class JavaObjectTypeLogischGeneratorIntegrationTest extends AbstractBmrTest {

    
    @Autowired(required=true)
    private JavaObjectTypeLogischGenerator generator;
    
    @Before
    public void setUp() throws Exception {
        String path = System.getProperty("user.dir");
        FileUtils.forceMkdir(new File(FilenameUtils.concat(FilenameUtils.concat(path,"target/generated-sources"), "objecttypes")));   
        generator.setGeneratedSourcesPath(FilenameUtils.concat(FilenameUtils.concat(path,"target/generated-sources"), "objecttypes"));     
        
    }
  

    
    /**
     * Test method for {@link nl.bzk.brp.generator.java.JavaEnumGenerator#genereerAttribuutType(java.lang.String, java.lang.String, java.lang.String, boolean)}.
     */
    @Test
    public void shouldGenerateAllObjectTypes() {
        generator.setOverwritable(true); //Voor de unit test wel altijd overschrijven om te verifieren dat er geen fouten zijn
        GenerationReport result = generator.genereer();
        
        Assert.assertEquals(0, result.getErrors().size());
        

    }

    

}
