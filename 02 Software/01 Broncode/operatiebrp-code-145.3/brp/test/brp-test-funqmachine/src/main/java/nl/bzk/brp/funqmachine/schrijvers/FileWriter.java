/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.schrijvers;

import java.io.IOException;
import java.util.Iterator;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.funqmachine.jbehave.context.ScenarioResult;
import nl.bzk.brp.funqmachine.jbehave.context.StepResult;
import nl.bzk.brp.funqmachine.processors.xml.XmlException;
import nl.bzk.brp.funqmachine.processors.xml.XmlUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

/**
 * Schrijft de {@link StepResult} weg.
 */
@Component
public final class FileWriter implements DisposableBean {
    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final String SLASH = "/";
    private static final String MINUS = "-";
    private static final String FILE_ERROR_RESPONSE_END = "-response.txt";
    private static final String FILE_RESPONSE_END = "-response.xml";
    private static final String FILE_EXPECTED_END = "-expected.xml";
    private static final String FILE_ERROR_EXPECTED_END = "-expected.txt";
    private static final String REQUEST_SQL = "-request.sql";
    private FileHandler handler;

    /**
     * Constructor.
     * @throws IOException on error
     */
    public FileWriter() throws IOException {
        handler = new FileHandler();
        handler.prepareDirectories();
    }

    /**
     * @param result ScenarioResult
     * @param withDiff boolean
     */
    public void write(final ScenarioResult result, final boolean withDiff) {
        final Iterator<StepResult> stepResults = result.iterator();
        final int[] index = {0};
        stepResults.forEachRemaining(stepResult -> {
            try {
                schrijfBestanden(result, stepResult, index, withDiff);
            } catch (final IOException | XmlException e) {
                LOGGER.error("Fout tijdens wegschrijven Step resultaten", e);
            }
        });
    }

    private void schrijfBestanden(final ScenarioResult result, final StepResult r, final int[] idx, final boolean withDiff) throws IOException, XmlException {
        schrijfResponse(result, r, idx);
        schrijfExpected(result, r, idx, withDiff);
        schrijfRequest(result, r, idx);
        idx[0]++;
    }

    private void schrijfRequest(final ScenarioResult result, final StepResult r, final int[] idx) throws IOException, XmlException {
        if (r.getRequest() != null && r.getRequest().length() > 0) {
            if (r.getSoort().equals(StepResult.Soort.DATA)) {
                handler.schrijfFile(result.getPath() + SLASH + String.valueOf(r.getSoort()) + MINUS + idx[0] + REQUEST_SQL, r.getRequest());
            } else if (r.getSoort().equals(StepResult.Soort.ERROR)) {
                handler.schrijfFile(result.getPath() + SLASH + String.valueOf(r.getSoort()) + MINUS + idx[0] + REQUEST_SQL, r.getRequest());
            } else {
                handler.schrijfFile(
                        result.getPath() + SLASH + String.valueOf(r.getSoort()) + MINUS + idx[0] + "-request.xml", XmlUtils.prettify(r.getRequest()));
            }

        }
    }

    private void schrijfExpected(final ScenarioResult result, final StepResult r, final int[] idx,
                                 final boolean withDiff) throws IOException {
        if (r.getExpected() != null && r.getExpected().length() > 0 && !r.getSoort().equals(StepResult.Soort.BLOB)) {
            final String expectedFileEnd;
            final String responseEnd;
            if (r.getSoort().equals(StepResult.Soort.ERROR)) {
                responseEnd = FILE_ERROR_RESPONSE_END;
                expectedFileEnd = FILE_ERROR_EXPECTED_END;

            } else {
                responseEnd = FILE_RESPONSE_END;
                expectedFileEnd = FILE_EXPECTED_END;
            }
            handler.schrijfFile(
                    result.getPath() + SLASH + String.valueOf(r.getSoort()) + MINUS + idx[0] + expectedFileEnd, r.getExpected());

            if (withDiff) {
                handler.schrijfDiff(
                        result.getPath()
                                + SLASH
                                + String.valueOf(r.getSoort())
                                + MINUS
                                + idx[0]
                                + expectedFileEnd,
                        result.getPath() + SLASH + String.valueOf(r.getSoort()) + MINUS + idx[0] + responseEnd,
                        result.getPath() + SLASH + String.valueOf(r.getSoort()) + MINUS + idx[0] + "-verschil.diff");
            }

        }
    }

    private void schrijfResponse(final ScenarioResult result, final StepResult r, final int[] idx)
            throws IOException, XmlException {
        if (r.getResponse() != null && r.getResponse().length() > 0) {
            if (r.getSoort().equals(StepResult.Soort.BRP_PL)) {
                handler.schrijfFile(
                        result.getPath()
                                + SLASH
                                + String.valueOf(r.getSoort())
                                + MINUS
                                + String.valueOf(r.getAnummer())
                                + FILE_RESPONSE_END,
                        XmlUtils.prettify(r.getResponse()));
            } else if (r.getSoort().equals(StepResult.Soort.BLOB)) {
                handler.schrijfFile(result.getPath() + SLASH + String.valueOf(r.getId()) + "-persoon.blob.json", r.getResponse());
            } else if (r.getSoort().equals(StepResult.Soort.JSON)) {
                handler.schrijfFile(result.getPath() + SLASH + String.valueOf(r.getId()) + MINUS + idx[0] + "-.json", r.getResponse());
            } else if (r.getSoort().equals(StepResult.Soort.ERROR)) {
                handler.schrijfFile(result.getPath() + SLASH + String.valueOf(r.getSoort()) + MINUS + idx[0] + "-response.txt", r.getResponse());
            } else {
                handler.schrijfFile(
                        result.getPath() + SLASH + String.valueOf(r.getSoort()) + MINUS + idx[0] + FILE_RESPONSE_END, XmlUtils.prettify(r.getResponse()));
            }

        }
    }

    /**
     * @param result scenarioResult
     */
    public void write(final ScenarioResult result) {
        write(result, false);
    }

    @Override
    public void destroy() throws Exception {
        // Doet niets verder
    }
}
