/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.console.report.birt;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.console.report.config.DefaultReportConfig;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class BirtReportServiceTest {
    private final BirtReportService subject = new BirtReportService(new DefaultReportConfig());

    @Test
    public void test() throws Exception {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("testParam", "testParamWaarde");
        final String filename = subject.genereerReport("test", params);
        Assert.assertNotNull(filename);
        final File file = new File(filename);
        Assert.assertTrue(file.exists());
        // final String report = IOUtils.toString(new FileInputStream(file));
        // System.out.println(report);
    }

    @Test
    @Ignore
    public void testRealReport() throws Exception {
        final String filename = subject.genereerReport("mig-fouten-overzicht", null);
        System.out.println(filename);
    }

}
