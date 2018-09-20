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
import nl.bzk.brp.generator.GenerationPackageNames;
import nl.bzk.brp.generator.java.AbstractBmrTest;
import nl.bzk.brp.generator.java.JavaEnumGenerator;
import nl.bzk.brp.generator.java.domein.GenerationReport;
import nl.bzk.brp.metaregister.dataaccess.ObjectTypeDao;
import nl.bzk.brp.metaregister.model.Laag;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 *
 */
public class JavaEnumGeneratorIntegrationTest extends AbstractBmrTest {

    private static final Logger logger = LoggerFactory.getLogger(JavaEnumGeneratorIntegrationTest.class);

    
    @Autowired(required=true)
    private JavaEnumGenerator generator;
    

    
    @Before
    public void setUp() throws Exception {
     
        String path = System.getProperty("user.dir");
        System.out.println("Current working directory : " + path);
        FileUtils.forceMkdir(new File(FilenameUtils.concat(FilenameUtils.concat(path,"target/generated-sources"), "enumeraties")));
        generator.setGeneratedSourcesPath(FilenameUtils.concat(FilenameUtils.concat(path,"target/generated-sources"), "enumeraties"));
        generator.setTemplateGroupName("enumeraties");
    }

    /**
     * Test method for {@link nl.bzk.brp.generator.java.JavaEnumGenerator#genereerAttribuutType(java.lang.String, java.lang.String, java.lang.String, boolean)}.
     */
    @Test
    public void shouldGenerateLogicalModel() {
/*        Laag.LOGISCH.set();*/
        GenerationReport result = generator.genereer();
        
        Assert.assertEquals(0, result.getErrors().size());
        logger.info("Gegenereerde classes: " + StringUtils.join(result.getGeneratedObjects().keySet().toArray(),", "));

    }
    

    

}
