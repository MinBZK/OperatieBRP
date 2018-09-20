/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.commands;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import nl.bzk.brp.bevraging.app.ContextParameterNames;
import nl.bzk.brp.bevraging.dataaccess.BestandRepository;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.Filter;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.math3.stat.descriptive.AggregateSummaryStatistics;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Stap die rapporteert wat de resulaten zijn van voorgaande stappen.
 */
@Component
public class RapporteerStap implements Filter {
    private static final int MILLIS_IN_SECOND = 1000;
    private static final Logger LOGGER = LoggerFactory.getLogger(RapporteerStap.class);

    @Inject
    private BestandRepository bestandRepository;

    @Override
    public boolean execute(final Context context) throws Exception {
        return CONTINUE_PROCESSING;
    }

    @Override
    public boolean postprocess(final Context context, final Exception exception) {
        List<BevraagInfo> taskInfo = (List) context.get(ContextParameterNames.TASK_INFO_LIJST);
        if (taskInfo == null || taskInfo.isEmpty()) {
            LOGGER.warn("Geen data beschikbaar voor performance rapportage");
            return PROCESSING_COMPLETE;
        }

        List<String> report = new ArrayList<String>();

        this.parameterReport(report, context);
        this.databaseReport(report, context);
        this.percentileReport(report, context);
        this.resultReport(report, context);
        this.timingReport(report, context);
        this.commentReport(report, context);
        this.finishReport(report);

        String naam = context.get(ContextParameterNames.FILENAME) != null
                ? (String) context.get(ContextParameterNames.FILENAME)
                : (String) context.get(ContextParameterNames.SCENARIO);

        this.write(naam, report);

        for (String line : report) {
            LOGGER.info(line);
        }

        return CONTINUE_PROCESSING;

    }

    /**
     * Voeg sectie toe aan report.
     * @param report het rapport
     * @param context de context om data uit te lezen
     */
    private void parameterReport(final List<String> report, final Context context) {
        String scenario = context.get(ContextParameterNames.SCENARIO).toString().toUpperCase();
        // Settings section
        report.add("--[Parameters]----------------------------------------------------------------------------------");

        report.add(format(" %-40.40s %-20s", "Datum-tijd test run:", DateFormatUtils.format(new Date(),
                                                                                            "dd MMM yyyy HH:mm:ss")));
        report.add(format(" %-40.40s %-20s", "Scenario:", scenario));
        report.add(format(" %-40.40s %-20d", "Threads:", context.get(ContextParameterNames.AANTAL_THREADS)));
        report.add(format(" %-40.40s %-20d", "Aantal op te vragen persoonslijsten:",
                          context.get(ContextParameterNames
                                              .AANTAL_PERSOONSLIJSTEN)));
    }

    /**
     * Voeg sectie toe aan report.
     * @param report het rapport
     * @param context de context om data uit te lezen
     */
    private void databaseReport(final List<String> report, final Context context) {
        // database section
        Map<String, Object> dbInfo = (Map<String, Object>) context.get(ContextParameterNames.DATABASE_INFO);
        report.add(
                "\n--[Database]------------------------------------------------------------------------------------");
        report.add(format(" %-40.40s %-20s", "Database URL:", dbInfo.get("dataBaseUrl")));
        report.add(format(" %-40.40s %-20s", "Aantal personen in DB:", dbInfo.get("persoonModelCount")));
        report.add(format(" %-40.40s %-20s", "Aantal entities cached:",
                          ((List<String>) dbInfo.get("cachedEntities")).size()));
    }

    /**
     * Voeg sectie toe aan report.
     * @param report het rapport
     * @param context de context om data uit te lezen
     */
    private void percentileReport(final List<String> report, final Context context) {
        List<BevraagInfo> taskInfo = (List) context.get(ContextParameterNames.TASK_INFO_LIJST);
        if (taskInfo == null || taskInfo.isEmpty()) {
            return;
        }

        double[] values = new double[taskInfo.size()];
        for (int i = 0; i < taskInfo.size(); i++) {
            values[i] = (double) taskInfo.get(i).getTimeMillis();
        }

        Percentile percentile = new Percentile();
        percentile.setData(values);

        report.add(
                "\n--[Percentile]----------------------------------------------------------------------------------");
        report.add(format(" %-40.40s %10.0f ms", "Percentile 50:", percentile.evaluate(50)));
        report.add(format(" %-40.40s %10.0f ms", "Percentile 70:", percentile.evaluate(70)));
        report.add(format(" %-40.40s %10.0f ms", "Percentile 80:", percentile.evaluate(80)));
        report.add(format(" %-40.40s %10.0f ms", "Percentile 90:", percentile.evaluate(90)));
        report.add(format(" %-40.40s %10.0f ms", "Percentile 95:", percentile.evaluate(95)));
    }

    /**
     * Voeg sectie toe aan report.
     * @param report het rapport
     * @param context de context om data uit te lezen
     */
    private void resultReport(final List<String> report, final Context context) {
        List<BevraagInfo> taskInfo = (List) context.get(ContextParameterNames.TASK_INFO_LIJST);
        if (taskInfo == null || taskInfo.isEmpty()) {
            return;
        }

        AggregateSummaryStatistics allStats = new AggregateSummaryStatistics();
        SummaryStatistics okStats = allStats.createContributingStatistics();
        SummaryStatistics failStats = allStats.createContributingStatistics();

        for (BevraagInfo info : taskInfo) {
            if (info.getResult().equals("OK")) {
                okStats.addValue(info.getTimeMillis());
            }
            if (info.getResult().equals("FAIL")) {
                failStats.addValue(info.getTimeMillis());
            }
        }

        // execution section
        report.add(
                "\n--[Resultaat]-----------------------------------------------------------------------------------");

        if (context.get(ContextParameterNames.BSNLIJST) != null) {
            report.add(format(" %-40.40s %-20d", "Aantal geselecteerde BSNs",
                          ((List) context.get(ContextParameterNames.BSNLIJST)).size()));
        }

        report.add("\n");
        report.add(format(" %-40.40s %10s %10s %10s", "", "TOTAAL", "OK", "FAIL"));
        report.add(format(" %-40.40s %10s %10s %10s #", "Aantal opgevraagde BSNs (PL's):",
                          allStats.getN(), okStats.getN(), failStats.getN()));

        report.add(format(" %-40.40s %10d %10d %10d ms", "Gemiddelde duur ophalen van BSN:",
                          Math.round(allStats.getMean()), Math.round(okStats.getMean()),
                          Math.round(failStats.getMean())));
        report.add(format(" %-40.40s %10d %10d %10d /sec", "Gemiddelde snelheid ophalen van BSN:",
                          Math.round(1 / allStats.getMean()
                                 * MILLIS_IN_SECOND * (Integer) context.get(ContextParameterNames.AANTAL_THREADS)),
                          Math.round(1 / okStats.getMean()
                                 * MILLIS_IN_SECOND * (Integer) context.get(ContextParameterNames.AANTAL_THREADS)),
                          Math.round(1 / failStats.getMean()
                                 * MILLIS_IN_SECOND * (Integer) context.get(ContextParameterNames.AANTAL_THREADS))));
        report.add("\n");

        report.add(format(" %-40.40s %10.0f %10.0f %10.0f ms", "Snelste tijd ophalen van PL:", allStats.getMin(),
                          okStats.getMin(), failStats.getMin()));
        report.add(format(" %-40.40s %10.0f %10.0f %10.0f ms", "Langzaamste tijd ophalen van PL:", allStats.getMax(),
                          okStats.getMax(), failStats.getMax()));
        report.add(format(" %-40.40s %10.3f %10.3f %10.3f", "Standaarddeviatie ophalen van PL:",
                          allStats.getStandardDeviation(), okStats.getStandardDeviation(),
                          failStats.getStandardDeviation()));
    }


    /**
     * Voeg sectie toe aan report.
     * @param report het rapport
     * @param context de context om data uit te lezen
     */
    private void commentReport(final List<String> report, final Context context) {
        if (context.get(ContextParameterNames.COMMENT) != null) {
            report.add(
                "\n--[Opmerking]-----------------------------------------------------------------------------------");
            report.add((String) context.get(ContextParameterNames.COMMENT));
        }
    }

    /**
     * Voeg sectie toe aan report.
     * @param report het rapport
     * @param context de context om data uit te lezen
     */
    private void timingReport(final List<String> report, final Context context) {
        if (context.get(ContextParameterNames.TIMING) != null) {
            report.add(
                "\n--[Timing]--------------------------------------------------------------------------------------");
            report.add((String) context.get(ContextParameterNames.TIMING));
        }
    }

    /**
     * Voeg de laatste regels toe aan het rapport.
     * @param report het rapport
     */
    private void finishReport(final List<String> report) {
        // finish
        report.add(
                "------------------------------------------------------------------------------------------------\n");
    }

    /**
     * Schrijf het rapport.
     * @param naam naam van uitvoer
     * @param lines de regels om in het rapport te schrijven
     */
    private void write(final String naam, final List<String> lines) {
        bestandRepository.schrijfRegels(naam, lines);
    }
}
