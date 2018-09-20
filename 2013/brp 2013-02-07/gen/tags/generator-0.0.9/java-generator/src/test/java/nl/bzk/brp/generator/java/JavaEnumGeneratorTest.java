/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generator.java;

import java.io.StringWriter;

import nl.bzk.brp.generator.GenerationReport;
import nl.bzk.brp.generator.integration.JavaEnumGeneratorIntegrationTest;
import nl.bzk.brp.metaregister.dataaccess.ObjectTypeDao;
import nl.bzk.brp.metaregister.model.ObjectType;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class JavaEnumGeneratorTest extends AbstractBmrTest {

    private static final Logger logger      = LoggerFactory.getLogger(JavaEnumGeneratorIntegrationTest.class);

    @Autowired(required = true)
    private JavaEnumGenerator   generator;
    
    @Autowired
    private ObjectTypeDao       enumDao;
    
    private GenerationReport report = new GenerationReport();

    @Before
    public void setUp() throws Exception {
        prepareGeneratedSourcesDirectory("enumeraties");
        generator.setGeneratedSourcesPath("enumeraties");
    }


    @Test
    public void genereerEnum() {
        StringWriter writer = new StringWriter();
        ObjectType enumeratie = enumDao.getBySyncId(1973);
        generator.genereerElement(writer, report , enumeratie);
        Assert.assertEquals(getVerwachtResultaat("Geslachtsaanduiding.jav"), toUnixString(writer));
    }


}
