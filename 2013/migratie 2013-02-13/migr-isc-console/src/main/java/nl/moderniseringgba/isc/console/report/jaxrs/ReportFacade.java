/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.console.report.jaxrs;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import nl.moderniseringgba.isc.console.report.ReportException;
import nl.moderniseringgba.isc.console.report.ReportService;
import nl.moderniseringgba.isc.console.report.birt.BirtReportService;
import nl.moderniseringgba.isc.console.report.config.DefaultReportConfig;
import nl.moderniseringgba.isc.console.report.config.ReportConfig;

/**
 * Report facade.
 */
@Path("report")
public final class ReportFacade {

    private ReportConfig reportConfig = new DefaultReportConfig();
    private ReportService reportService = new BirtReportService(reportConfig);

    public void setReportConfig(final ReportConfig reportConfig) {
        this.reportConfig = reportConfig;
    }

    public void setReportService(final ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * Execute a report and return the HTML.
     * 
     * @param fileName
     *            report
     * @param request
     *            http request
     * @return response
     */
    @GET
    @Path("view/{fileName}")
    @Produces("text/html")
    public Response viewReportHtml(
            @PathParam("fileName") final String fileName,
            @Context final HttpServletRequest request) {

        // HttpServletRequest is niet generified
        try {
            @SuppressWarnings("unchecked")
            final String reportFilename = reportService.genereerReport(fileName, request.getParameterMap());
            final File reportFile = new File(reportFilename);
            return Response.ok(reportFile).type("text/html").build();
        } catch (final ReportException e) {
            return Response.serverError().build();
        }
    }

    /**
     * Return an image used in a report.
     * 
     * @param fileName
     *            image
     * @param request
     *            http request
     * @return response
     */
    @GET
    @Path("view/images/{fileName}")
    public Response getImage(@PathParam("fileName") final String fileName, @Context final HttpServletRequest request) {
        final File imageFile = new File(reportConfig.getImagesDirectory(), fileName);
        if (!imageFile.exists()) {
            return Response.serverError().build();
        }
        return Response.ok(imageFile).build();
    }

}
