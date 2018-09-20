/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.taglet.bedrijfsregel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.List;

import nl.bzk.brp.taglet.model.Referentie;


/**
 * Utility om bedrijfsregels te exporteren naar een CSV bestand.
 */
public final class BedrijfsregelExportUtils {

    private static final String EXPORT_FILENAME = "bedrijfsregel-export.csv";
    private static final String CSV_SEPARATOR   = ",";

    /**
     * Constructor private gemaakt.
     */
    private BedrijfsregelExportUtils() {
    }

    /**
     * Verwijder het huidige export bestand.
     */
    public static void reset() {
        File file = new File(EXPORT_FILENAME);
        if (file.exists()) {
            boolean isDeleted = file.delete();
            if (!isDeleted) {
                throw new RuntimeException("het lukt niet om het oude export bestand te verwijderen: "
                    + EXPORT_FILENAME);
            }
        }
        appendHeader();
    }

    /**
     * Voeg een header toe met kolomnamen.
     */
    private static void appendHeader() {
        PrintWriter out = getPrintWriter();
        out.println(getCsvHeader());
        out.flush();
        out.close();
    }

    /**
     * Voeg voor elke bedrijfsregel een datarecord toe.
     *
     * @param ruleRefs bedrijfsregels
     */
    public static void append(final List<Referentie> ruleRefs) {
        PrintWriter out = getPrintWriter();
        export(out, ruleRefs);
        out.flush();
        out.close();
    }

    /**
     * @return een PrintWriter naar het export bestand
     */
    private static PrintWriter getPrintWriter() {
        try {
            return new PrintWriter(new FileOutputStream(new File(EXPORT_FILENAME), true));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Voeg alle bedrijfsregels toe aan het export bestand.
     *
     * @param out het export bestand
     * @param ruleRefs de toe te voegen bedrijsregels
     */
    private static void export(final PrintWriter out, final List<Referentie> ruleRefs) {
        for (Referentie ruleRef : ruleRefs) {
            out.println(convertToCsvRecord(ruleRef));
        }
    }

    /**
     * @return CSV string met de kolomnamen.
     */
    private static String getCsvHeader() {
        StringBuilder result = new StringBuilder();
        result.append("Rule");
        result.append(CSV_SEPARATOR);
        result.append("Package");
        result.append(CSV_SEPARATOR);
        result.append("Type");
        result.append(CSV_SEPARATOR);
        result.append("Member");
        result.append(CSV_SEPARATOR);
        result.append("Line");
        result.append(CSV_SEPARATOR);
        result.append("Column");
        return result.toString();
    }

    /**
     * Converteer een bedrijfsregel naar CSV formaat.
     *
     * @param ruleRef de bedrijfsregel
     * @return de bedrijfsregel al CSV
     */
    private static String convertToCsvRecord(final Referentie ruleRef) {
        StringBuilder result = new StringBuilder();
        result.append(ruleRef.getReferentie());
        result.append(CSV_SEPARATOR);
        result.append(ruleRef.getPackageName());
        result.append(CSV_SEPARATOR);
        result.append(ruleRef.getTypeName());
        result.append(CSV_SEPARATOR);
        result.append(ruleRef.getMemberName());
        result.append(CSV_SEPARATOR);
        result.append(ruleRef.getLine());
        result.append(CSV_SEPARATOR);
        result.append(ruleRef.getColumn());
        return result.toString();
    }

}
