/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.console.report.jaxrs;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import nl.moderniseringgba.isc.console.report.ReportException;
import nl.moderniseringgba.isc.console.report.ReportService;
import nl.moderniseringgba.isc.console.report.config.DefaultReportConfig;
import nl.moderniseringgba.isc.console.report.config.ReportConfig;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ReportFacadeTest {

    private final ReportFacade subject = new ReportFacade();
    private final ReportConfig reportConfig = new DefaultReportConfig();
    private ReportService reportService;

    @Before
    public void setup() {
        reportService = Mockito.mock(ReportService.class);
        subject.setReportService(reportService);
        subject.setReportConfig(reportConfig);
    }

    @Test
    public void testViewReportHtmlOk() throws Exception {
        final File reportFile = File.createTempFile("test", null, reportConfig.getImagesDirectory());
        final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        final Map<String, Object> parameterMap = new HashMap<String, Object>();
        Mockito.when(request.getParameterMap()).thenReturn(parameterMap);

        Mockito.when(reportService.genereerReport("bla", parameterMap)).thenReturn(reportFile.getPath());

        final Response result = subject.viewReportHtml("bla", request);
        Assert.assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
        Assert.assertEquals(reportFile, result.getEntity());
    }

    @Test
    public void testViewReportHtmlNok() throws Exception {
        final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        final Map<String, Object> parameterMap = new HashMap<String, Object>();
        Mockito.when(request.getParameterMap()).thenReturn(parameterMap);

        Mockito.when(reportService.genereerReport("bla", parameterMap)).thenThrow(new ReportException("Test"));

        final Response result = subject.viewReportHtml("bla", request);
        Assert.assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), result.getStatus());
    }

    @Test
    public void testGetImageOk() throws Exception {
        final File imageFile = File.createTempFile("test", null, reportConfig.getImagesDirectory());

        final Response result = subject.getImage(imageFile.getName(), null);
        Assert.assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
        Assert.assertEquals(imageFile, result.getEntity());
    }

    @Test
    public void testGetImageNok() throws Exception {
        final File imageFile = File.createTempFile("test", null, reportConfig.getImagesDirectory());
        imageFile.delete();

        final Response result = subject.getImage(imageFile.getName(), null);
        Assert.assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), result.getStatus());
    }
}
