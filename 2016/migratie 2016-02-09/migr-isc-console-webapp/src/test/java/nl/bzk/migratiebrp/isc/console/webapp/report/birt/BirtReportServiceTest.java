/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.webapp.report.birt;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import nl.bzk.migratiebrp.isc.console.webapp.report.ReportException;
import nl.bzk.migratiebrp.isc.console.webapp.report.config.DefaultReportConfig;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

public class BirtReportServiceTest {
    private final BirtReportService subject = new BirtReportService(new DefaultReportConfig());

    @Test
    public void test() throws Exception {
        final Map<String, Object> params = new HashMap<>();
        params.put("testParam", "testParamWaarde");
        final String filename = subject.genereerReport("test", params);
        Assert.assertNotNull(filename);
        final File file = new File(filename);
        Assert.assertTrue(file.exists());
        final String report = IOUtils.toString(new FileInputStream(file));
        System.out.println(report);
    }

    @Test
    public void testRealReport() throws Exception {
        final Map<String, Object> params = new HashMap<>();
        params.put("datumVan", "18-10-2015");
        params.put("datumTot", "18-11-2015");
        final String filename = subject.genereerReport("mig-fouten-overzicht", params);
        System.out.println(filename);
    }

    @Test(expected = ReportException.class)
    public void testRunReportExceptie() throws Exception {
        subject.genereerReport("mig-fouten-overzicht", null);
    }

    @Test(expected = ReportException.class)
    public void testOngeldigReportExceptie() throws Exception {
        subject.genereerReport("mig-ongeldig-rapport-overzicht", null);
    }

}
