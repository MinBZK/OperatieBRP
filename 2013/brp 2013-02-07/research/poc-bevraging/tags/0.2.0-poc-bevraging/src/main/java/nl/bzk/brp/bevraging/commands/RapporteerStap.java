/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.commands;

import static java.lang.String.format;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import nl.bzk.brp.bevraging.app.ContextParameterNames;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

/**
 *
 */
@Component
public class RapporteerStap implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(RapporteerStap.class);
    private String folder;

    public RapporteerStap() {
        this.setFolder(System.getProperty("app.home"));
    }

    public void setFolder(String path) {
        this.folder = path + "/report";

        if (!verifyFolder(this.folder)) {
            this.folder = System.getProperty("java.io.tmpdir");
            LOGGER.warn("Rapport folder '{}/report' is niet schrijfbaar, reset naar java.io.tmpdir: '{}'", path,
                        this.folder);
        }
    }

    private boolean verifyFolder(String folder) {
        try {
            File file = ResourceUtils.getFile(folder);
            return file.exists() && file.canWrite();
        } catch (FileNotFoundException e) {
            LOGGER.error("Kan rapport folder '{}' niet schrijven", folder, e);
            return false;
        }

    }

    @Override
    public boolean execute(final Context context) throws Exception {
        List<BevraagInfo> taskInfo = (List) context.get(ContextParameterNames.TASK_INFO_LIJST);
        if (taskInfo == null || taskInfo.isEmpty()) {
            LOGGER.warn("Geen data beschikbaar voor performance rapportage");
            return true;
        }

        List<String> report = new ArrayList<String>();

        this.parameterReport(report, context);
        this.databaseReport(report, context);
        this.percentileReport(report, context);
        this.resultReport(report, context);
        this.commentReport(report, context);

        this.write(context, report);

        for (String line : report) {
            LOGGER.info(line);
        }

        return false;
    }

    private void parameterReport(List<String> report, Context context) {
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

    private void databaseReport(List<String> report, Context context) {
        // database section
        Map<String, Object> dbInfo = (Map<String, Object>) context.get(ContextParameterNames.DATABASE_INFO);
        report.add("\n--[Database]------------------------------------------------------------------------------------");
        report.add(format(" %-40.40s %-20s", "Database URL:", dbInfo.get("dataBaseUrl")));
        report.add(format(" %-40.40s %-20s", "Aantal personen in DB:", dbInfo.get("persoonModelCount")));
        report.add(format(" %-40.40s %-20s", "Aantal entities cached:", ((List<String>)dbInfo.get("cachedEntities")).size()));
    }

    private void percentileReport(List<String> report, Context context) {
        List<BevraagInfo> taskInfo = (List) context.get(ContextParameterNames.TASK_INFO_LIJST);
        if (taskInfo == null || taskInfo.isEmpty()) {
            return;
        }

        double[] values = new double[taskInfo.size()];
        for(int i=0; i< taskInfo.size(); i++) {
            values[i] = (double) taskInfo.get(i).getTimeMillis();
        }

        Percentile percentile = new Percentile();
        percentile.setData(values);

        report.add("\n--[Percentile]----------------------------------------------------------------------------------");
        report.add(format(" %-40.40s %10.0f ms", "Percentile 50:", percentile.evaluate(50)));
        report.add(format(" %-40.40s %10.0f ms", "Percentile 70:", percentile.evaluate(70)));
        report.add(format(" %-40.40s %10.0f ms", "Percentile 80:", percentile.evaluate(80)));
        report.add(format(" %-40.40s %10.0f ms", "Percentile 90:", percentile.evaluate(90)));
        report.add(format(" %-40.40s %10.0f ms", "Percentile 95:", percentile.evaluate(95)));
    }

    private void resultReport(List<String> report, Context context) {
        List<BevraagInfo> taskInfo = (List) context.get(ContextParameterNames.TASK_INFO_LIJST);
        if (taskInfo == null || taskInfo.isEmpty()) {
            return;
        }

        SummaryStatistics allStats = new SummaryStatistics();
        SummaryStatistics okStats = new SummaryStatistics();
        SummaryStatistics failStats = new SummaryStatistics();

        for (BevraagInfo info : taskInfo) {
            allStats.addValue(info.getTimeMillis());
            if (info.getResult().equals("OK")) {
                okStats.addValue(info.getTimeMillis());
            }
            if (info.getResult().equals("FAIL")) {
                failStats.addValue(info.getTimeMillis());
            }
        }

        context.put(ContextParameterNames.AVERAGE_PL_TIME, Math.round(allStats.getMean()));

        // execution section
        report.add("\n--[Resultaat]-----------------------------------------------------------------------------------");
        report.add(format(" %-40.40s %-20d", "Aantal geselecteerde BSNs", ((List)context.get(ContextParameterNames.BSNLIJST)).size()) );

        report.add("\n");
        report.add(format(" %-40.40s %10s %10s %10s", "", "TOTAAL", "OK", "FAIL"));
        report.add(format(" %-40.40s %10s %10s %10s #", "Aantal opgevraagde BSNs (PL's):",
                          allStats.getN(), okStats.getN(), failStats.getN()
                         ));

        report.add(format(" %-40.40s %10d %10d %10d ms", "Gemiddelde duur ophalen van BSN:", Math.round(allStats.getMean()), Math.round(okStats.getMean()), Math.round(failStats.getMean())));
        report.add(format(" %-40.40s %10d %10d %10d /sec", "Gemiddelde snelheid ophalen van BSN:", Math.round(
                1 / allStats.getMean() * 1000 * (Integer) context.get(ContextParameterNames.AANTAL_THREADS)),
                          Math.round(
                                  1 / okStats.getMean() * 1000 * (Integer) context.get(ContextParameterNames.AANTAL_THREADS)), Math.round(
                1 / failStats.getMean() * 1000 * (Integer) context.get(ContextParameterNames.AANTAL_THREADS))));
        report.add("\n");

        report.add(format(" %-40.40s %10.0f %10.0f %10.0f ms", "Snelste tijd ophalen van PL:", allStats.getMin(), okStats.getMin(), failStats.getMin()));
        report.add(format(" %-40.40s %10.0f %10.0f %10.0f ms", "Langzaamste tijd ophalen van PL:", allStats.getMax(), okStats.getMax(), failStats.getMax()));
        report.add(format(" %-40.40s %10.3f %10.3f %10.3f", "Standaarddeviatie ophalen van PL:", allStats.getStandardDeviation(), okStats.getStandardDeviation(), failStats.getStandardDeviation()));
    }


    private void commentReport(List<String> report, Context context) {
        if(context.get(ContextParameterNames.COMMENT) != null) {
            report.add("\n--[Opmerking]-----------------------------------------------------------------------------------");
            report.add((String)context.get(ContextParameterNames.COMMENT));
        }

        // finish
        report.add("------------------------------------------------------------------------------------------------\n");
    }

    private void write(Context context, List<String> lines) throws IOException {

        String scenario = context.get(ContextParameterNames.FILENAME) != null ? (String)context.get(ContextParameterNames.FILENAME) : (String)context.get(ContextParameterNames.SCENARIO);
        File reportDir = ResourceUtils.getFile(this.folder);
        String datumTijdStempel = DateFormatUtils.format(new Date(), "yyyyMMdd-HHmm");
        File report = new File(reportDir, format("%s-%s.txt", scenario, datumTijdStempel));
        report.createNewFile();

        FileOutputStream fos = new FileOutputStream(report);
        OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");

        for (String line : lines) {
            out.write(line);
            out.write("\n");
        }

        out.close();
    }
}
