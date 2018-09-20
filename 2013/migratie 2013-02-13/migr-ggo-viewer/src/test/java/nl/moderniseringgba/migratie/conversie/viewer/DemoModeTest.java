/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.viewer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import javax.inject.Inject;

import org.apache.commons.io.FilenameUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Tests only the demo functionality. Uses the Spring runner to inject the Value annotations on the ViewerController
 * from the property file.
 * 
 * TODO: add testcases to test demoMode and defaultPlLocation. But so far it's hard to change the properties for test
 * other then changing them in de file itself.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-viewer-beans.xml" })
public class DemoModeTest {

    private boolean demoModeProp;
    private String defaultPlLocationProp;

    @Inject
    private DemoMode demoMode;

    @Before
    public void loadProps() throws IOException {
        // check against configured properties
        final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("ggo-viewer.properties");
        final Properties ggoViewerProperties = new Properties();
        ggoViewerProperties.load(inputStream);
        demoModeProp = new Boolean(ggoViewerProperties.getProperty("demo.mode"));
        defaultPlLocationProp = ggoViewerProperties.getProperty("defaultpl.location");
    }

    @Test
    public void testGetDemoModel() {
        final Map<String, Object> demoModel = demoMode.getDemoModel();
        assertEquals("DemoMode should be same as configured in properties", demoModeProp, demoModel.get("demoMode"));
        if (demoModeProp) {
            assertEquals("FilenameUtils", FilenameUtils.class, demoModel.get("FilenameUtils"));
            assertTrue("Should have defaultPlen", demoModel.containsKey("defaultPlen"));
        }
    }

}
