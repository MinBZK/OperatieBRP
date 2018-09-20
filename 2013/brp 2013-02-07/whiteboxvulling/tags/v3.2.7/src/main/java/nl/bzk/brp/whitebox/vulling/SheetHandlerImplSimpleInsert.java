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

    // offset 10 * 1000 * 1000 -> 20 * 1000 * 1000
    private static final long     gemeente_factor  = 20 * 1000 * 1000;

    // offset 360 -> 720
    private static final long     ambtenaar_factor = 720;

    private final static String[] huisToev         = new String[] { "AAA", "AAB", "AAC", "AAD", "AAE", "AAF", "AAG",
        "AAH", "AAI", "AAJ", "AAK", "AAL", "AAM", "AAN", "AAO", "AAP", "AAQ", "AAR", "AAS", "AAT", "AAU", "AAV", "AAW",
        "AAX", "AAY", "AAZ", "ABA", "ABB", "ABC", "ABD", "ABE", "ABF", "ABG", "ABH", "ABI", "ABJ", "ABK", "ABL", "ABM",
        "ABN", "ABO", "ABP", "ABQ", "ABR", "ABS", "ABT", "ABU", "ABV", "ABW", "ABX", "ABY", "ABZ", "ACA", "ACB", "ACC",
        "ACD", "ACE", "ACF", "ACG", "ACH", "ACI", "ACJ", "ACK", "ACL", "ACM", "ACN", "ACO", "ACP", "ACQ", "ACR", "ACS",
        "ACT", "ACU", "ACV", "ACW", "ACX", "ACY", "ACZ", "ADA", "ADB", "ADC", "ADD", "ADE", "ADF", "ADG", "ADH", "ADI",
        "ADJ", "ADK", "ADL", "ADM", "ADN", "ADO", "ADP", "ADQ", "ADR", "ADS", "ADT", "ADU", "ADV", "ADW", "ADX", "ADY",
        "ADZ", "AEA", "AEB", "AEC", "AED", "AEE", "AEF", "AEG", "AEH", "AEI", "AEJ", "AEK", "AEL", "AEM", "AEN", "AEO",
        "AEP", "AEQ", "AER", "AES", "AET", "AEU", "AEV", "AEW", "AEX", "AEY", "AEZ", "AFA", "AFB", "AFC", "AFD", "AFE",
        "AFF", "AFG", "AFH", "AFI", "AFJ", "AFK", "AFL", "AFM", "AFN", "AFO", "AFP", "AFQ", "AFR", "AFS", "AFT", "AFU",
        "AFV", "AFW", "AFX", "AFY", "AFZ", "AGA", "AGB", "AGC", "AGD", "AGE", "AGF", "AGG", "AGH", "AGI", "AGJ", "AGK",
        "AGL", "AGM", "AGN", "AGO", "AGP", "AGQ", "AGR", "AGS", "AGT", "AGU", "AGV", "AGW", "AGX", "AGY", "AGZ", "AHA",
        "AHB", "AHC", "AHD", "AHE", "AHF", "AHG", "AHH", "AHI", "AHJ", "AHK", "AHL", "AHM", "AHN", "AHO", "AHP", "AHQ",
        "AHR", "AHS", "AHT", "AHU", "AHV", "AHW", "AHX", "AHY", "AHZ", "AIA", "AIB", "AIC", "AID", "AIE", "AIF", "AIG",
        "AIH", "AII", "AIJ", "AIK", "AIL", "AIM", "AIN", "AIO", "AIP", "AIQ", "AIR", "AIS", "AIT", "AIU", "AIV", "AIW",
        "AIX", "AIY", "AIZ", "AJA", "AJB", "AJC", "AJD", "AJE", "AJF", "AJG", "AJH", "AJI", "AJJ", "AJK", "AJL", "AJM",
        "AJN", "AJO", "AJP", "AJQ", "AJR", "AJS", "AJT", "AJU", "AJV", "AJW", "AJX", "AJY", "AJZ", "AKA", "AKB", "AKC",
        "AKD", "AKE", "AKF", "AKG", "AKH", "AKI", "AKJ", "AKK", "AKL", "AKM", "AKN", "AKO", "AKP", "AKQ", "AKR", "AKS",
        "AKT", "AKU", "AKV", "AKW", "AKX", "AKY", "AKZ", "ALA", "ALB", "ALC", "ALD", "ALE", "ALF", "ALG", "ALH", "ALI",
        "ALJ", "ALK", "ALL", "ALM", "ALN", "ALO", "ALP", "ALQ", "ALR", "ALS", "ALT", "ALU", "ALV", "ALW", "ALX", "ALY",
        "ALZ", "AMA", "AMB", "AMC", "AMD", "AME", "AMF", "AMG", "AMH", "AMI", "AMJ", "AMK", "AML", "AMM", "AMN", "AMO",
        "AMP", "AMQ", "AMR", "AMS", "AMT", "AMU", "AMV", "AMW", "AMX", "AMY", "AMZ", "ANA", "ANB", "ANC", "AND", "ANE",
        "ANF", "ANG", "ANH", "ANI", "ANJ", "ANK", "ANL", "ANM", "ANN", "ANO", "ANP", "ANQ", "ANR", "ANS", "ANT", "ANU",
        "ANV", "ANW", "ANX", "ANY", "ANZ", "AOA", "AOB", "AOC", "AOD", "AOE", "AOF", "AOG", "AOH", "AOI", "AOJ", "AOK",
        "AOL", "AOM", "AON", "AOO", "AOP", "AOQ", "AOR", "AOS", "AOT", "AOU", "AOV", "AOW", "AOX", "AOY", "AOZ", "APA",
        "APB", "APC", "APD", "APE", "APF", "APG", "APH", "API", "APJ", "APK", "APL", "APM", "APN", "APO", "APP", "APQ",
        "APR", "APS", "APT", "APU", "APV", "APW", "APX", "APY", "APZ", "AQA", "AQB", "AQC", "AQD", "AQE", "AQF", "AQG",
        "AQH", "AQI", "AQJ", "AQK", "AQL", "AQM", "AQN", "AQO", "AQP", "AQQ", "AQR", "AQS", "AQT", "AQU", "AQV", "AQW",
        "AQX", "AQY", "AQZ", "ARA", "ARB", "ARC", "ARD", "ARE", "ARF", "ARG", "ARH", "ARI", "ARJ", "ARK", "ARL", "ARM",
        "ARN", "ARO", "ARP", "ARQ", "ARR", "ARS", "ART", "ARU", "ARV", "ARW", "ARX", "ARY", "ARZ", "ASA", "ASB", "ASC",
        "ASD", "ASE", "ASF", "ASG", "ASH", "ASI", "ASJ", "ASK", "ASL", "ASM", "ASN", "ASO", "ASP", "ASQ", "ASR", "ASS",
        "AST", "ASU", "ASV", "ASW", "ASX", "ASY", "ASZ", "ATA", "ATB", "ATC", "ATD", "ATE", "ATF", "ATG", "ATH", "ATI",
        "ATJ", "ATK", "ATL", "ATM", "ATN", "ATO", "ATP", "ATQ", "ATR", "ATS", "ATT", "ATU", "ATV", "ATW", "ATX", "ATY",
        "ATZ", "AUA", "AUB", "AUC", "AUD", "AUE", "AUF", "AUG", "AUH", "AUI", "AUJ", "AUK", "AUL", "AUM", "AUN", "AUO",
        "AUP", "AUQ", "AUR", "AUS", "AUT", "AUU", "AUV", "AUW", "AUX", "AUY", "AUZ", "AVA", "AVB", "AVC", "AVD", "AVE",
        "AVF", "AVG", "AVH", "AVI", "AVJ", "AVK", "AVL", "AVM", "AVN", "AVO", "AVP", "AVQ", "AVR", "AVS", "AVT", "AVU",
        "AVV", "AVW", "AVX", "AVY", "AVZ", "AWA", "AWB", "AWC", "AWD", "AWE", "AWF", "AWG", "AWH", "AWI", "AWJ", "AWK",
        "AWL", "AWM", "AWN", "AWO", "AWP", "AWQ", "AWR", "AWS", "AWT", "AWU", "AWV", "AWW", "AWX", "AWY", "AWZ", "AXA",
        "AXB", "AXC", "AXD", "AXE", "AXF", "AXG", "AXH", "AXI", "AXJ", "AXK", "AXL", "AXM", "AXN", "AXO", "AXP", "AXQ",
        "AXR", "AXS", "AXT", "AXU", "AXV", "AXW", "AXX", "AXY", "AXZ", "AYA", "AYB", "AYC", "AYD", "AYE", "AYF", "AYG",
        "AYH", "AYI", "AYJ", "AYK", "AYL", "AYM", "AYN", "AYO", "AYP", "AYQ", "AYR", "AYS", "AYT", "AYU", "AYV", "AYW",
        "AYX", "AYY", "AYZ", "AZA", "AZB", "AZC", "AZD", "AZE", "AZF", "AZG", "AZH", "AZI", "AZJ", "AZK", "AZL", "AZM",
        "AZN", "AZO", "AZP", "AZQ", "AZR", "AZS", "AZT", "AZU", "AZV", "AZW", "AZX", "AZY", "AZZ", "BAA", "BAB", "BAC",
        "BAD", "BAE", "BAF", "BAG", "BAH", "BAI", "BAJ", "BAK", "BAL", "BAM", "BAN", "BAO", "BAP", "BAQ", "BAR", "BAS",
        "BAT", "BAU", "BAV", "BAW", "BAX", "BAY", "BAZ", "BBA", "BBB", "BBC", "BBD", "BBE", "BBF", "BBG", "BBH", "BBI",
        "BBJ", "BBK", "BBL", "BBM", "BBN", "BBO", "BBP", "BBQ", "BBR", "BBS", "BBT", "BBU", "BBV", "BBW", "BBX", "BBY",
        "BBZ", "BCA", "BCB", "BCC", "BCD", "BCE", "BCF", "BCG", "BCH", "BCI", "BCJ", "BCK", "BCL", "BCM", "BCN", "BCO",
        "BCP", "BCQ", "BCR", "BCS", "BCT", "BCU", "BCV", "BCW", "BCX", "BCY", "BCZ", "BDA", "BDB", "BDC", "BDD", "BDE",
        "BDF", "BDG", "BDH", "BDI", "BDJ", "BDK", "BDL", "BDM", "BDN", "BDO", "BDP", "BDQ", "BDR", "BDS", "BDT", "BDU",
        "BDV", "BDW", "BDX", "BDY", "BDZ", "BEA", "BEB", "BEC", "BED", "BEE", "BEF", "BEG", "BEH", "BEI", "BEJ", "BEK",
        "BEL", "BEM", "BEN", "BEO", "BEP", "BEQ", "BER", "BES", "BET", "BEU", "BEV", "BEW", "BEX", "BEY", "BEZ", "BFA",
        "BFB", "BFC", "BFD", "BFE", "BFF", "BFG", "BFH", "BFI", "BFJ", "BFK", "BFL", "BFM", "BFN", "BFO", "BFP", "BFQ",
        "BFR", "BFS", "BFT", "BFU", "BFV", "BFW", "BFX", "BFY", "BFZ", "BGA", "BGB", "BGC", "BGD", "BGE", "BGF", "BGG",
        "BGH", "BGI", "BGJ", "BGK", "BGL", "BGM", "BGN", "BGO", "BGP", "BGQ", "BGR", "BGS", "BGT", "BGU", "BGV", "BGW",
        "BGX", "BGY", "BGZ", "BHA", "BHB", "BHC", "BHD", "BHE", "BHF", "BHG", "BHH", "BHI", "BHJ", "BHK", "BHL", "BHM",
        "BHN", "BHO", "BHP", "BHQ", "BHR", "BHS", "BHT", "BHU", "BHV", "BHW", "BHX", "BHY", "BHZ", "BIA", "BIB", "BIC",
        "BID", "BIE", "BIF", "BIG", "BIH", "BII", "BIJ", "BIK", "BIL", "BIM", "BIN", "BIO", "BIP", "BIQ", "BIR", "BIS",
        "BIT", "BIU", "BIV", "BIW", "BIX", "BIY", "BIZ", "BJA", "BJB", "BJC", "BJD", "BJE", "BJF", "BJG", "BJH", "BJI",
        "BJJ", "BJK", "BJL", "BJM", "BJN", "BJO", "BJP", "BJQ", "BJR", "BJS", "BJT", "BJU", "BJV", "BJW", "BJX", "BJY",
        "BJZ", "BKA", "BKB", "BKC", "BKD", "BKE", "BKF", "BKG", "BKH", "BKI", "BKJ", "BKK", "BKL", "BKM", "BKN", "BKO",
        "BKP", "BKQ", "BKR", "BKS", "BKT", "BKU", "BKV", "BKW", "BKX", "BKY", "BKZ", "BLA", "BLB", "BLC", "BLD", "BLE",
        "BLF", "BLG", "BLH", "BLI", "BLJ", "BLK", "BLL", "BLM", "BLN", "BLO", "BLP", "BLQ", "BLR", "BLS", "BLT", "BLU",
        "BLV", "BLW", "BLX", "BLY", "BLZ", "BMA", "BMB", "BMC", "BMD", "BME", "BMF", "BMG", "BMH", "BMI", "BMJ", "BMK",
        "BML", "BMM", "BMN", "BMO", "BMP", "BMQ", "BMR", "BMS", "BMT", "BMU", "BMV", "BMW", "BMX", "BMY", "BMZ", "BNA",
        "BNB", "BNC", "BND", "BNE", "BNF", "BNG", "BNH", "BNI", "BNJ", "BNK", "BNL", "BNM", "BNN", "BNO", "BNP", "BNQ",
        "BNR", "BNS", "BNT", "BNU", "BNV", "BNW", "BNX", "BNY", "BNZ", "BOA", "BOB", "BOC", "BOD", "BOE", "BOF", "BOG",
        "BOH", "BOI", "BOJ", "BOK", "BOL", "BOM", "BON", "BOO", "BOP", "BOQ", "BOR", "BOS", "BOT", "BOU", "BOV", "BOW",
        "BOX", "BOY", "BOZ", "BPA", "BPB", "BPC", "BPD", "BPE", "BPF", "BPG", "BPH", "BPI", "BPJ", "BPK", "BPL", "BPM",
        "BPN", "BPO", "BPP", "BPQ", "BPR", "BPS", "BPT", "BPU", "BPV", "BPW", "BPX", "BPY", "BPZ", "BQA", "BQB", "BQC",
        "BQD", "BQE", "BQF", "BQG", "BQH", "BQI", "BQJ", "BQK", "BQL", "BQM", "BQN", "BQO", "BQP", "BQQ", "BQR", "BQS",
        "BQT", "BQU", "BQV", "BQW", "BQX", "BQY", "BQZ", "BRA", "BRB", "BRC", "BRD", "BRE", "BRF", "BRG", "BRH", "BRI",
        "BRJ", "BRK", "BRL", "BRM", "BRN", "BRO", "BRP", "BRQ", "BRR", "BRS", "BRT", "BRU", "BRV", "BRW", "BRX", "BRY",
        "BRZ", "BSA", "BSB", "BSC", "BSD", "BSE", "BSF", "BSG", "BSH", "BSI", "BSJ", "BSK", "BSL", "BSM", "BSN", "BSO",
        "BSP", "BSQ", "BSR", "BSS", "BST", "BSU", "BSV", "BSW", "BSX", "BSY", "BSZ", "BTA", "BTB", "BTC", "BTD", "BTE",
        "BTF", "BTG", "BTH", "BTI", "BTJ", "BTK", "BTL", "BTM", "BTN", "BTO", "BTP", "BTQ", "BTR", "BTS", "BTT", "BTU",
        "BTV", "BTW", "BTX", "BTY", "BTZ", "BUA", "BUB", "BUC", "BUD", "BUE", "BUF", "BUG", "BUH", "BUI", "BUJ", "BUK",
        "BUL", "BUM", "BUN", "BUO", "BUP", "BUQ", "BUR", "BUS", "BUT", "BUU", "BUV", "BUW", "BUX", "BUY", "BUZ", "BVA",
        "BVB", "BVC", "BVD", "BVE", "BVF", "BVG", "BVH", "BVI", "BVJ", "BVK", "BVL", "BVM", "BVN", "BVO", "BVP", "BVQ",
        "BVR", "BVS", "BVT", "BVU", "BVV", "BVW", "BVX", "BVY", "BVZ", "BWA", "BWB", "BWC", "BWD", "BWE", "BWF", "BWG",
        "BWH", "BWI", "BWJ", "BWK", "BWL", "BWM", "BWN", "BWO", "BWP", "BWQ", "BWR", "BWS", "BWT", "BWU", "BWV", "BWW",
        "BWX", "BWY", "BWZ", "BXA", "BXB", "BXC", "BXD", "BXE", "BXF", "BXG", "BXH", "BXI", "BXJ", "BXK", "BXL", "BXM",
        "BXN", "BXO", "BXP", "BXQ", "BXR", "BXS", "BXT", "BXU", "BXV", "BXW", "BXX", "BXY", "BXZ", "BYA", "BYB", "BYC",
        "BYD", "BYE", "BYF", "BYG", "BYH", "BYI", "BYJ", "BYK", "BYL", "BYM", "BYN", "BYO", "BYP", "BYQ", "BYR", "BYS",
        "BYT", "BYU", "BYV", "BYW", "BYX", "BYY", "BYZ", "BZA", "BZB", "BZC", "BZD", "BZE", "BZF", "BZG", "BZH", "BZI",
        "BZJ", "BZK", "BZL", "BZM", "BZN", "BZO", "BZP", "BZQ", "BZR", "BZS", "BZT", "BZU", "BZV", "BZW", "BZX", "BZY",
        "BZZ"                                     };

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

                    // Create first line of COPY statement
                    final Cell[] rowCopy = sheet.getRow(START_ROW_NR);
                    final int colCountCopy = Integer.valueOf(rowCopy[AANTAL_KOLOMMEN_COL_NR].getContents());
                    final String tableNameCopy = rowCopy[TABLE_NAME_COL_NR].getContents();
                    final List<String> stringRowCopy = new ArrayList<String>(colCountCopy);

                    for (int i = 0; i < rowCopy.length; i++) {
                        stringRowCopy.add(rowCopy[i].getContents());
                    }

                    StringBuilder sbCopy = new StringBuilder("COPY ");
                    sbCopy.append(tableNameCopy + " (");

                    boolean first = true;
                    for (int i = 0; i < colCountCopy; i++) {
                        if (first) {
                            first = false;
                        } else {
                            sbCopy.append(", ");
                        }
                        sbCopy.append(getColName(stringRowCopy, i));
                    }
                    sbCopy.append(" ) FROM stdin;");
                    result.println(sbCopy.toString());

                    for (rowNum = START_ROW_NR; rowNum < sheet.getRows(); rowNum++) {

                        final StringWriter stringWriter = new StringWriter();
                        final PrintWriter printWriter = new PrintWriter(stringWriter);
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

                            printWriter.flush();
                            stringWriter.flush();

                            final String line = stringWriter.toString();
                            result.println(line);
                        }
                        _count++;
                    }

                    result.println("\\.");
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
            final int ambtCount, final List<String> _row, final String tableName, final int colCount,
            final int rowNumber)
    {

        final List<String> row =
            changeRowForGemeente(gemeente, gemeenteCount, ambtCount, _row, colCount, tableName, rowNumber);

        boolean first = true;
        for (int colNum = 0; colNum < colCount; colNum++) {

            if (first) {
                first = false;
            } else {
                printWriter.print("\t");
            }
            if (getColValue(row, colNum, colCount) != null && getColValue(row, colNum, colCount).trim().length() > 0) {
                printWriter.print(getColValue(row, colNum, colCount));
            } else {
                printWriter.print("\\N");
            }
        }
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
            final int ambtenaarCount, final List<String> row, final int colCount, final String tabelNaam,
            final int rowNumber)
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

            final AdresDTO adresDTO = AdresLookup.geefAdresDetails(gemeente.code, rowNumber - 1);

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
