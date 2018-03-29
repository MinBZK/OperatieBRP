/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.junit.Test;

public class DemoModeTest {

    @Test
    public void testGetDemoModelDemoMode() {
        final boolean isDemoMode = true;
        final String path = FilenameUtils.separatorsToUnix(new File("src/test/resources/").getAbsolutePath());
        final DemoMode demoMode = new DemoMode(path, isDemoMode);
        assertEquals(isDemoMode, demoMode.isDemoMode());

        final Map<String, Object> demoModel = demoMode.getDemoModel();
        assertEquals(isDemoMode, demoModel.get("demoMode"));
        assertEquals("FilenameUtils", FilenameUtils.class, demoModel.get("FilenameUtils"));
        assertTrue(((List<?>) demoModel.get("defaultPlen")).size() > 0);
    }

    @Test
    public void testGetDemoModelNonDemoMode() {
        final boolean isDemoMode = false;
        final DemoMode demoMode = new DemoMode(null, isDemoMode);
        assertEquals(isDemoMode, demoMode.isDemoMode());

        final Map<String, Object> demoModel = demoMode.getDemoModel();
        assertEquals(isDemoMode, demoModel.get("demoMode"));
        assertNull(demoModel.get("defaultPlen"));
    }

    @Test
    public void testGetDemoUploadFile() throws IOException {
        final String defaultPlLocation = FilenameUtils.separatorsToUnix(new File("src/test/resources/").getAbsolutePath());
        final DemoMode demoMode = new DemoMode(defaultPlLocation, true);

        final byte[] uploadFile = demoMode.getDemoUploadFile("src/test/resources/Omzetting.txt");
        assertNotNull(uploadFile);
        assertTrue(uploadFile.length > 0);
    }

    @Test
    public void testGetDemoUploadFileNull() throws IOException {
        final String defaultPlLocation = FilenameUtils.separatorsToUnix(new File("src/test/resources/").getAbsolutePath());
        final DemoMode demoMode = new DemoMode(defaultPlLocation, true);

        assertNull(demoMode.getDemoUploadFile(null));
    }
}
