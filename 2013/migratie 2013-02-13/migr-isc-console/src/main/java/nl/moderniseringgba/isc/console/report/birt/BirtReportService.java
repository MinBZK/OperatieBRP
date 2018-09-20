/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.console.report.birt;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import nl.moderniseringgba.isc.console.report.ReportException;
import nl.moderniseringgba.isc.console.report.ReportService;
import nl.moderniseringgba.isc.console.report.config.ReportConfig;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.HTMLServerImageHandler;
import org.eclipse.birt.report.engine.api.IGetParameterDefinitionTask;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.IScalarParameterDefn;
import org.eclipse.birt.report.model.api.IResourceLocator;
import org.eclipse.birt.report.model.api.ModuleHandle;

/**
 * BIRT implementatie van rapportage.
 */
public final class BirtReportService implements ReportService {
    private static final String BASE_RESOURCE_LOCATION = "/reports/";
    private final ReportConfig config;
    private final IReportEngine engine;

    /**
     * Constructor.
     * 
     * @param config
     *            configuratie
     */
    public BirtReportService(final ReportConfig config) {
        this.config = config;

        final EngineConfig engineConfig = new EngineConfig();
        engineConfig.setResourceLocator(new ResourceLocator());

        try {
            Platform.startup(engineConfig);
            final IReportEngineFactory factory =
                    (IReportEngineFactory) Platform
                            .createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
            engine = factory.createReportEngine(engineConfig);
        } catch (final BirtException e) {
            throw new RuntimeException("Could not start BIRT.", e);
        }
    }

    @Override
    public String genereerReport(final String reportName, final Map<String, Object> parameters) throws ReportException {
        // Load report design
        final IReportRunnable reportDesign;
        try {
            reportDesign = engine.openReportDesign(getReport(reportName));
        } catch (final EngineException e) {
            throw new ReportException("Could not load report design", e);
        }

        // Run and render task
        final IRunAndRenderTask runAndRenderReportTask = engine.createRunAndRenderTask(reportDesign);
        setParameters(runAndRenderReportTask, reportDesign, parameters);

        // Determine directories and filenames
        final File reportFile;
        try {
            reportFile = File.createTempFile(reportName, ".html", config.getReportDirectory());
        } catch (final IOException e1) {
            throw new ReportException("Could not create temporary report file.");
        }
        final String reportFilename = reportFile.getAbsolutePath();
        final String imageDirectory = config.getImagesDirectory().getAbsolutePath();

        // Setup rendering to HTML
        final HTMLRenderOption htmlOptions = new HTMLRenderOption();

        htmlOptions.setOutputFileName(reportFilename);
        htmlOptions.setImageDirectory(imageDirectory);
        htmlOptions.setImageHandler(new HTMLServerImageHandler());
        htmlOptions.setBaseImageURL("images/");
        htmlOptions.setHtmlPagination(false);

        runAndRenderReportTask.setRenderOption(htmlOptions);

        // run and render report
        try {
            runAndRenderReportTask.run();
        } catch (final EngineException e) {
            throw new ReportException("Could not execute report", e);
        }
        runAndRenderReportTask.close();

        return reportFilename;
    }

    private void setParameters(
            final IRunAndRenderTask runAndRenderReportTask,
            final IReportRunnable reportDesign,
            final Map<String, Object> parameters) throws ReportException {

        // Set parameters (on run and render task)
        if (parameters != null) {
            final IGetParameterDefinitionTask parameterDefinitions =
                    engine.createGetParameterDefinitionTask(reportDesign);
            @SuppressWarnings("unchecked")
            final Iterator<IScalarParameterDefn> parameterDefinitionIterator =
                    parameterDefinitions.getParameterDefns(true).iterator();

            final Map<String, Object> parameterValues = new HashMap<String, Object>();
            while (parameterDefinitionIterator.hasNext()) {
                final IScalarParameterDefn parameterDefition = parameterDefinitionIterator.next();
                final String parameterName = parameterDefition.getName();
                if (parameters.containsKey(parameterName)) {
                    parameterValues.put(parameterName,
                            BirtReportParameters.convertTo(parameterDefition, parameters.get(parameterName)));
                }
            }

            runAndRenderReportTask.setParameterValues(parameterValues);
        }
    }

    private InputStream getReport(final String reportName) {
        final String reportResource = BASE_RESOURCE_LOCATION + reportName + ".rptdesign";
        return BirtReportService.class.getResourceAsStream(reportResource);
    }

    /**
     * Resource locatie that retrieves libraries from the same place as reports.
     */
    private final class ResourceLocator implements IResourceLocator {

        @Override
        public URL findResource(final ModuleHandle moduleHandle, final String fileName, final int type) {
            if (type == LIBRARY) {
                return BirtReportService.class.getResource(BASE_RESOURCE_LOCATION + fileName);
            }

            return null;
        }

        @Override
        @SuppressWarnings("rawtypes")
        public URL findResource(
                final ModuleHandle moduleHandle,
                final String fileName,
                final int type,
                final Map appContext) {
            return findResource(moduleHandle, fileName, type);
        }

    }
}
