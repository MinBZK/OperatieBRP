/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.whitebox.vulling;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import nl.bzk.brp.whitebox.vulling.AdresLookup.AdresDTO;
import nl.bzk.brp.whitebox.vulling.model.Gemeente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SheetHandlerImplSimpleInsert extends AbstractSheetHandlerImpl {

    private final static String   QUOTE            = "'";

    private final static Logger   log              = LoggerFactory.getLogger(SheetHandlerImplSimpleInsert.class);

    private static final long     gemeente_factor  = 10 * 1000 * 1000;

    private static final long     ambtenaar_factor = 360;

    private final static String[] huisToev         = new String[] { "AA", "AB", "AC", "AD", "AE", "AF", "AG", "AH",
        "AI", "AJ", "AK", "AL", "AM", "AN", "AO", "AP", "AQ", "AR", "AS", "AT", "AU", "AV", "AW", "AX", "AY", "AZ",
        "BA", "BB", "BC", "BD", "BE", "BF", "BG", "BH", "BI", "BJ", "BK", "BL", "BM", "BN", "BO", "BP", "BQ", "BR",
        "BS", "BT", "BU", "BV", "BW", "BX", "BY", "BZ", "CA", "CB", "CC", "CD", "CE", "CF", "CG", "CH", "CI", "CJ",
        "CK", "CL", "CM", "CN", "CO", "CP", "CQ", "CR", "CS", "CT", "CU", "CV", "CW", "CX", "CY", "CZ", "DA", "DB",
        "DC", "DD", "DE", "DF", "DG", "DH", "DI", "DJ", "DK", "DL", "DM", "DN", "DO", "DP", "DQ", "DR", "DS", "DT",
        "DU", "DV", "DW", "DX", "DY", "DZ", "EA", "EB", "EC", "ED", "EE", "EF", "EG", "EH", "EI", "EJ", "EK", "EL",
        "EM", "EN", "EO", "EP", "EQ", "ER", "ES", "ET", "EU", "EV", "EW", "EX", "EY", "EZ", "FA", "FB", "FC", "FD",
        "FE", "FF", "FG", "FH", "FI", "FJ", "FK", "FL", "FM", "FN", "FO", "FP", "FQ", "FR", "FS", "FT", "FU", "FV",
        "FW", "FX", "FY", "FZ", "GA", "GB", "GC", "GD", "GE", "GF", "GG", "GH", "GI", "GJ", "GK", "GL", "GM", "GN",
        "GO", "GP", "GQ", "GR", "GS", "GT", "GU", "GV", "GW", "GX", "GY", "GZ", "HA", "HB", "HC", "HD", "HE", "HF",
        "HG", "HH", "HI", "HJ", "HK", "HL", "HM", "HN", "HO", "HP", "HQ", "HR", "HS", "HT", "HU", "HV", "HW", "HX",
        "HY", "HZ", "IA", "IB", "IC", "ID", "IE", "IF", "IG", "IH", "II", "IJ", "IK", "IL", "IM", "IN", "IO", "IP",
        "IQ", "IR", "IS", "IT", "IU", "IV", "IW", "IX", "IY", "IZ", "JA", "JB", "JC", "JD", "JE", "JF", "JG", "JH",
        "JI", "JJ", "JK", "JL", "JM", "JN", "JO", "JP", "JQ", "JR", "JS", "JT", "JU", "JV", "JW", "JX", "JY", "JZ",
        "KA", "KB", "KC", "KD", "KE", "KF", "KG", "KH", "KI", "KJ", "KK", "KL", "KM", "KN", "KO", "KP", "KQ", "KR",
        "KS", "KT", "KU", "KV", "KW", "KX", "KY", "KZ", "LA", "LB", "LC", "LD", "LE", "LF", "LG", "LH", "LI", "LJ",
        "LK", "LL", "LM", "LN", "LO", "LP", "LQ", "LR", "LS", "LT", "LU", "LV", "LW", "LX", "LY", "LZ", "MA", "MB",
        "MC", "MD", "ME", "MF", "MG", "MH", "MI", "MJ", "MK", "ML", "MM", "MN", "MO", "MP", "MQ", "MR", "MS", "MT",
        "MU", "MV", "MW", "MX", "MY", "MZ", "NA", "NB", "NC", "ND", "NE", "NF", "NG", "NH", "NI", "NJ", "NK", "NL",
        "NM", "NN", "NO", "NP", "NQ", "NR", "NS", "NT", "NU", "NV", "NW", "NX", "NY", "NZ", "OA", "OB", "OC", "OD",
        "OE", "OF", "OG", "OH", "OI", "OJ", "OK", "OL", "OM", "ON", "OO", "OP", "OQ", "OR", "OS", "OT", "OU", "OV",
        "OW", "OX", "OY", "OZ", "PA", "PB", "PC", "PD", "PE", "PF", "PG", "PH", "PI", "PJ", "PK", "PL", "PM", "PN",
        "PO", "PP", "PQ", "PR", "PS", "PT", "PU", "PV", "PW", "PX", "PY", "PZ", "QA", "QB", "QC", "QD", "QE", "QF",
        "QG", "QH", "QI", "QJ", "QK", "QL", "QM", "QN", "QO", "QP", "QQ", "QR", "QS", "QT", "QU", "QV", "QW", "QX",
        "QY", "QZ", "RA", "RB", "RC", "RD", "RE", "RF", "RG", "RH", "RI", "RJ", "RK", "RL", "RM", "RN", "RO", "RP",
        "RQ", "RR", "RS", "RT", "RU", "RV", "RW", "RX", "RY", "RZ", "SA", "SB", "SC", "SD", "SE", "SF", "SG", "SH",
        "SI", "SJ", "SK", "SL", "SM", "SN", "SO", "SP", "SQ", "SR", "SS", "ST", "SU", "SV", "SW", "SX", "SY", "SZ",
        "TA", "TB", "TC", "TD", "TE", "TF", "TG", "TH", "TI", "TJ", "TK", "TL", "TM", "TN", "TO", "TP", "TQ", "TR",
        "TS", "TT", "TU", "TV", "TW", "TX", "TY", "TZ", "UA", "UB", "UC", "UD", "UE", "UF", "UG", "UH", "UI", "UJ",
        "UK", "UL", "UM", "UN", "UO", "UP", "UQ", "UR", "US", "UT", "UU", "UV", "UW", "UX", "UY", "UZ", "VA", "VB",
        "VC", "VD", "VE", "VF", "VG", "VH", "VI", "VJ", "VK", "VL", "VM", "VN", "VO", "VP", "VQ", "VR", "VS", "VT",
        "VU", "VV", "VW", "VX", "VY", "VZ", "WA", "WB", "WC", "WD", "WE", "WF", "WG", "WH", "WI", "WJ", "WK", "WL",
        "WM", "WN", "WO", "WP", "WQ", "WR", "WS", "WT", "WU", "WV", "WW", "WX", "WY", "WZ", "XA", "XB", "XC", "XD",
        "XE", "XF", "XG", "XH", "XI", "XJ", "XK", "XL", "XM", "XN", "XO", "XP", "XQ", "XR", "XS", "XT", "XU", "XV",
        "XW", "XX", "XY", "XZ", "YA", "YB", "YC", "YD", "YE", "YF", "YG", "YH", "YI", "YJ", "YK", "YL", "YM", "YN",
        "YO", "YP", "YQ", "YR", "YS", "YT", "YU", "YV", "YW", "YX", "YY", "YZ", "ZA", "ZB", "ZC", "ZD", "ZE", "ZF",
        "ZG", "ZH", "ZI", "ZJ", "ZK", "ZL", "ZM", "ZN", "ZO", "ZP", "ZQ", "ZR", "ZS", "ZT", "ZU", "ZV", "ZW", "ZX",
        "ZY", "ZZ"                                };

    public SheetHandlerImplSimpleInsert(final int sheetNum) {
        super(sheetNum);
    }

    private final int START_ROW_NR           = 1;
    private final int TABLE_NAME_COL_NR      = 0;
    private final int AANTAL_KOLOMMEN_COL_NR = 1;

    @Override
    protected long printImpl(final Workbook workbook, final PrintWriter result) {

        long _count = 0;
        long gemeenteCount = 0;

        final Sheet sheet = workbook.getSheet(getSheetNum());

        int rowNum = 0;
        try {

            // Gemeente gemeente = Generator.gemeenten.iterator().next();
            for (final Gemeente gemeente : Generator.gemeenten) {

                log.debug("Generating sheet '" + sheet.getName() + "' voor gemeente '" + gemeente.naam + " "
                    + (Generator.gemeenten.indexOf(gemeente) + 1) + "/" + Generator.gemeenten.size());

                for (int ambtenaarCount = 0; ambtenaarCount < WhiteBoxFiller.ambtenarenCount; ambtenaarCount++) {

                    // log.debug("Generating sheet '"+sheet.getName()+"' voor gemeente '"
                    // + gemeente.naam + "' , ambtenaar=" + ambtenaarCount + "/"
                    // + Generator.AMBTENAAR_COUNT);

                    result.println("--------------------------------------------------");
                    result.println("--- Sheet: " + getSheetNum());
                    result.println("--- Gemeente: " + gemeente.naam);
                    result.println("--- Ambtenaar: " + ambtenaarCount);
                    result.println("--------------------------------------------------");

                    result.println();
                    result.println("begin;");
                    result.println();

                    for (rowNum = START_ROW_NR; rowNum < sheet.getRows(); rowNum++) {

                        final StringWriter stringWriter = new StringWriter();
                        final PrintWriter printWriter = new PrintWriter(stringWriter);

                        // log.debug("Sheet " + getSheetNum() +
                        // " "+sheet.getName()+", row " + rowNum);

                        final Cell[] row = sheet.getRow(rowNum);

                        if (row.length > 0 && row[0].getContents().trim().length() > 0) {
                            final String tableName = row[TABLE_NAME_COL_NR].getContents();

                            final int colCount = Integer.valueOf(row[AANTAL_KOLOMMEN_COL_NR].getContents());

                            final List<String> stringRow = new ArrayList<String>(row.length);

                            for (int i = 0; i < row.length; i++) {
                                stringRow.add(row[i].getContents());
                            }

                            handleRow(printWriter, gemeente, gemeenteCount, ambtenaarCount, stringRow, tableName,
                                    colCount, rowNum);

                        }

                        printWriter.flush();
                        stringWriter.flush();

                        final String line = stringWriter.toString();
                        result.println(line);
                        _count++;
                    }

                    result.println();
                    result.println("commit;");
                    result.println();

                }
                gemeenteCount++;
            }
        } catch (final Exception e) {
            log.error("Sheet=" + sheet.getName() + ", row=" + rowNum + ":" + e.getMessage());
            throw new RuntimeException(e);
        }

        return _count;
    }

    private static String getColName(final List<String> row, final int colNum) {
        return row.get(colNum + 2);
    }

    private static String getColValue(final List<String> row, final int colNum, final int colCount) {
        return row.get(colNum + 2 + colCount);
    }

    private static void setColValue(final List<String> row, final int colNum, final int colCount, final String value) {
        row.set(colNum + 2 + colCount, value);
    }

    private void handleRow(final PrintWriter printWriter, final Gemeente gemeente, final long gemeenteCount,
            final int ambtCount, final List<String> _row, final String tableName, final int colCount, final int rowNumber)
    {

        final List<String> row = changeRowForGemeente(gemeente, gemeenteCount, ambtCount, _row, colCount, tableName, rowNumber);

        printWriter.print("INSERT INTO " + tableName + " (");

        boolean first = true;
        for (int colNum = 0; colNum < colCount; colNum++) {
            if (first) {
                first = false;
            } else {
                printWriter.print(',');
            }
            printWriter.print(getColName(_row, colNum));
        }

        printWriter.print(") VALUES(");

        first = true;
        for (int colNum = 0; colNum < colCount; colNum++) {
            if (first) {
                first = false;
            } else {
                printWriter.print(',');
            }
            if (getColValue(row, colNum, colCount) != null && getColValue(row, colNum, colCount).trim().length() > 0) {
                printWriter.print(QUOTE + getColValue(row, colNum, colCount) + QUOTE);
            } else {
                printWriter.print("null");
            }
        }
        printWriter.print(");");
    }

    private final int              ID_COL               = 0;
    private final int              ID_HIS_COL           = 1;

    private final int              KERN_BETR_BETROKKENE = 3;

    private final int              PERS_ID_ANR          = 5;
    private final int              PERS_ID_BSN          = 6;

    private static volatile String lastAchternaam       = "Huisman";

    private static volatile String voornaamStore        = "Bas";

    private static long idcalc(final long value, final long gemeenteCount, final long ambtenaarCount) {

        final long result = value + (gemeenteCount * gemeente_factor) + (ambtenaarCount * ambtenaar_factor);

        // log.debug("gemeenteCount=" + gemeenteCount +", ambtCount=" +
        // ambtenaarCount + ", result=" + result);

        return result;
    }

    private List<String> changeRowForGemeente(final Gemeente gemeente, final long gemeenteCount,
            final int ambtenaarCount, final List<String> row, final int colCount, final String tabelNaam, final int rowNumber)
    {
        final List<String> clone = new ArrayList<String>(row);

        // ID
        setColValue(
                clone,
                ID_COL,
                colCount,
                ""
                    + idcalc(Long.valueOf(getColValue(row, ID_COL, colCount)).longValue(), gemeenteCount,
                            ambtenaarCount));

        // col 2 van his_*
        if (tabelNaam.contains("his_")) {
            setColValue(
                    clone,
                    ID_HIS_COL,
                    colCount,
                    ""
                        + idcalc(Long.valueOf(getColValue(row, ID_HIS_COL, colCount)).longValue(), gemeenteCount,
                                ambtenaarCount));

            for (int i = 0; i < colCount; i++) {
                if (getColName(row, i).startsWith("actie")) {

                    final String colValue = getColValue(row, i, colCount);

                    if (colValue != null && colValue.length() > 0) {

                        setColValue(
                                clone,
                                i,
                                colCount,
                                ""
                                    + idcalc(Long.valueOf(getColValue(row, i, colCount)).longValue(), gemeenteCount,
                                            ambtenaarCount));
                    }
                }
            }

        }

        if (tabelNaam.equalsIgnoreCase("kern.betr")) {
            setColValue(
                    clone,
                    KERN_BETR_BETROKKENE,
                    colCount,
                    ""
                        + idcalc(Long.valueOf(getColValue(row, KERN_BETR_BETROKKENE, colCount)).longValue(),
                                gemeenteCount, ambtenaarCount));
            setColValue(clone, 1, colCount,
                    "" + idcalc(Long.valueOf(getColValue(row, 1, colCount)).longValue(), gemeenteCount, ambtenaarCount));
        }

        if (tabelNaam.equalsIgnoreCase("kern.persadres")) {
            setColValue(clone, 1, colCount,
                    "" + idcalc(Long.valueOf(getColValue(row, 1, colCount)).longValue(), gemeenteCount, ambtenaarCount));
        }

        if (tabelNaam.equalsIgnoreCase("Kern.PersIndicatie")) {
            setColValue(clone, 1, colCount,
                    "" + idcalc(Long.valueOf(getColValue(row, 1, colCount)).longValue(), gemeenteCount, ambtenaarCount));
        }

        if (tabelNaam.equalsIgnoreCase("kern.his_persids")) {
            setColValue(clone, PERS_ID_BSN, colCount, "" + BSNGenerator.nextBSN());
            setColValue(clone, PERS_ID_ANR, colCount, "" + AnrGenerator.getNextAnr());
        }

        if (tabelNaam.equalsIgnoreCase("kern.his_perssamengesteldenaam")) {

            String geslacht = getColValue(row, 12, colCount);

            final String voornaam;

            if (row.size() >= 29 && "G".equalsIgnoreCase(row.get(28))) {
                voornaam = voornaamStore;
            } else if ("v".equalsIgnoreCase(geslacht)) {
                voornaam = VoornaamGenerator.volgendeNaam(true);
            } else if ("m".equalsIgnoreCase(geslacht)) {
                voornaam = VoornaamGenerator.volgendeNaam(false);
            } else {
                throw new RuntimeException("Geslacht moet m of v zijn in 'kern.his_perssamengesteldenaam', niet '"
                    + geslacht + "'");
            }

            setColValue(clone, 6, colCount, voornaam);

            if (row.size() >= 29 && "B".equalsIgnoreCase(row.get(28))) {
                voornaamStore = voornaam;
            }

            // achternaam

            final String achterNaam;
            if (row.size() >= 28 && "x".equalsIgnoreCase(row.get(27))) {
                achterNaam = lastAchternaam;
            } else {
                achterNaam = AchternamenGenerator.getNextNaam();
            }

            lastAchternaam = achterNaam;
            setColValue(clone, 9, colCount, achterNaam);
        }

        if (tabelNaam.equalsIgnoreCase("kern.his_persadres")) {

            final AdresDTO adresDTO =
                    AdresLookup.geefAdresDetails(gemeente.code, rowNumber-1);

            setColValue(clone, 11, colCount, "" + adresDTO.gemCode);
            setColValue(clone, 12, colCount, "" + adresDTO.woonplaatsCode);
            setColValue(clone, 13, colCount, "" + adresDTO.straatKort);
            setColValue(clone, 14, colCount, "" + adresDTO.straatLang);
            setColValue(clone, 15, colCount, "" + adresDTO.postcode);
            setColValue(clone, 16, colCount, "" + adresDTO.huisNr);
            setColValue(clone, 17, colCount, "" + huisToev[ambtenaarCount]);
        }

        if (tabelNaam.equalsIgnoreCase("kern.his_persbijhgem")) {
            final boolean doDis = getColValue(row, 3, colCount) == null || getColValue(row, 3, colCount).length() == 0;

            if (doDis) {
                setColValue(clone, 8, colCount, "" + gemeente.code);
            }
        }

        // log.debug("id=" + clone.get(ID_COL));

        return clone;
    }
}
