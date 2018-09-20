/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tools;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.BurgerzakenModule;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Genereert een excel rapport voor de leveranciers met de bedrijfsregels die actief zijn in een bepaalde versie van
 * de BRP. De versie staat in de pom.xml.
 *
 * TODO Let op: Dit is momenteel nog knutselwerk, omdat het even snel midden in een sprint is gebouwd.
 * TODO De output is naar wens van Erwin, de code moet nog netter worden gemaakt. Misschien ooit integreren in
 * TODO distributie project om het gelijk na een release te laten draaien en als documentatie op te leveren.
 */
public class RapportGenerator {

    private static Logger LOG = LoggerFactory.getLogger(RapportGenerator.class);
    // Style voor koppen.
    private CellStyle headerStyle;

    public static void main(String[] args) throws ConfigurationException, IOException {
        new RapportGenerator().run();
    }

    private void run() throws IOException {
        FileOutputStream fileOut = null;
        Workbook wb = null;
        try {
            fileOut = new FileOutputStream("BRP-Bijhouding-regels.xls");
            wb = initialiseerWorkBook();

            initStyles(wb);

            //Algemene regels:
            definieerAlgemeneRegels(wb.createSheet("Algemene regels"));

            //Regels Per actie/handeling
            definieerRegelsPerActieAdmhnd(wb.createSheet("Regels per handeling en actie"));

            //Regels per actie (ongeacht handeling)
            definieerRegelsPerActie(wb.createSheet("Regels per actie (ongeacht handeling)"));

        } catch (ConfigurationException | IOException e) {
            LOG.error(e.getMessage());
        } finally {
            if (wb != null) {
                wb.write(fileOut);
                fileOut.close();
            }
        }
    }

    private void initStyles(final Workbook wb) {
        // Font voor headers:
        Font font = wb.createFont();
        font.setFontHeightInPoints((short) 14);
        font.setFontName("Arial");
        font.setBoldweight((short) 20);
        headerStyle = wb.createCellStyle();
        headerStyle.setFont(font);
    }

    private SoortAdmHandelingSoortActie maakKey(final SoortAdministratieveHandeling soortAdmHnd, final SoortActie soortActie) {
        return new SoortAdmHandelingSoortActie(soortAdmHnd, soortActie);
    }

    private void definieerAlgemeneRegels(final Sheet sheet) throws ConfigurationException {
        final Map<String, List<Object>> regelsAlleBerichten = leesRegelsAlleBerichten();

        final Row row = sheet.createRow(0);
        final Cell cell = row.createCell(0);
        cell.setCellValue("Soort bericht");
        cell.setCellStyle(headerStyle);

        final Cell cellRegel = row.createCell(1);
        cellRegel.setCellValue("Regel");
        cellRegel.setCellStyle(headerStyle);

        final Cell cellRegelOms = row.createCell(2);
        cellRegelOms.setCellValue("Regel omschrijving");
        cellRegelOms.setCellStyle(headerStyle);

        final Row row1 = sheet.createRow(1);
        final Cell cell1 = row1.createCell(0);
        cell1.setCellValue("Algemeen (Alle berichten)");

        //Rapporteer eerst de algemene
        final List<Object> algemeneRegels = regelsAlleBerichten.get("*");
        int rijnummer = 2;
        for (Object algemeneRegel : algemeneRegels) {
            final Row regelRij = sheet.createRow(rijnummer);
            final Cell regelCel = regelRij.createCell(1);
            regelCel.setCellValue((String) algemeneRegel);
            final Cell regelOmsCel = regelRij.createCell(2);
            regelOmsCel.setCellValue(Regel.valueOf((String) algemeneRegel).getOmschrijving());
            rijnummer++;
        }


        for (String key : regelsAlleBerichten.keySet()) {
            if (key.equals("*")) {
                //Hebben we al gehad.
                continue;
            } else {
                final SoortBericht soortBericht = SoortBericht.valueOf(key);
                final List<Object> regelsVoorSoortBericht = regelsAlleBerichten.get(key);
                final Row regelRij = sheet.createRow(rijnummer);
                final Cell regelCel = regelRij.createCell(0);
                regelCel.setCellValue(soortBericht.getNaam());
                rijnummer++;
                for (Object regelVoorSoortBericht : regelsVoorSoortBericht) {
                    final Row regelRijSb = sheet.createRow(rijnummer);
                    final Cell regelCelSb = regelRijSb.createCell(1);
                    regelCelSb.setCellValue((String) regelVoorSoortBericht);
                    final Cell regelOmsCel = regelRijSb.createCell(2);
                    regelOmsCel.setCellValue(Regel.valueOf((String) regelVoorSoortBericht).getOmschrijving());
                    rijnummer++;
                }
            }
        }

        // Even auto sizen:
        for (Row rijtje : sheet) {
            sheet.autoSizeColumn(rijtje.getRowNum());
        }
    }

    private Workbook initialiseerWorkBook() throws IOException {
        return new HSSFWorkbook();
    }

    private Map<String, List<Object>> leesRegelsAlleBerichten() throws ConfigurationException {
        final Map<String, List<Object>> regelsAlleBerichten = new HashMap<>();
        final PropertiesConfiguration regelsVoorBericht =
                new PropertiesConfiguration(RapportGenerator.class.getResource("/regels-voor-bericht.properties"));
        final PropertiesConfiguration regelsNaBericht =
                new PropertiesConfiguration(RapportGenerator.class.getResource("/regels-na-bericht.properties"));

        final PropertiesConfiguration regelsVoorActieAdmhnd =
                new PropertiesConfiguration(RapportGenerator.class.getResource("/regels-voor-admhnd-actie.properties"));
        final PropertiesConfiguration regelsNaActieAdmhnd =
                new PropertiesConfiguration(RapportGenerator.class.getResource("/regels-na-admhnd-actie.properties"));

        //Voor bericht
        final Iterator<String> keysVoorBericht = regelsVoorBericht.getKeys();
        while (keysVoorBericht.hasNext()) {
            final String next = keysVoorBericht.next();
            regelsAlleBerichten.put(next, regelsVoorBericht.getList(next));
        }

        // Na bericht
        final Iterator<String> keysNaBericht = regelsNaBericht.getKeys();
        while (keysNaBericht.hasNext()) {
            final String next = keysNaBericht.next();
            final List<Object> list = regelsNaBericht.getList(next);
            if (regelsAlleBerichten.get(next) != null) {
                regelsAlleBerichten.get(next).addAll(list);
            } else {
                regelsAlleBerichten.put(next, list);
            }
        }

        // Voor actie/admhnd die algemeen is, dat wil zeggen key = *.*
        final List<Object> listVoor = regelsVoorActieAdmhnd.getList("*.*");
        if (listVoor != null) {
            regelsAlleBerichten.get("*").addAll(listVoor);
        }

        // Na actie/admhnd die algemeen is, dat wil zeggen key = *.*
        final List<Object> listNa = regelsNaActieAdmhnd.getList("*.*");
        if (listNa != null) {
            regelsAlleBerichten.get("*").addAll(listNa);
        }
        return regelsAlleBerichten;
    }

    private void definieerRegelsPerActieAdmhnd(final Sheet sheet) throws ConfigurationException {
        final Row row = sheet.createRow(0);
        final Cell cellModule = row.createCell(0);
        cellModule.setCellValue("Module");
        cellModule.setCellStyle(headerStyle);
        final Cell cellHandeling = row.createCell(1);
        cellHandeling.setCellValue("Administratieve handeling");
        cellHandeling.setCellStyle(headerStyle);
        final Cell cellActie = row.createCell(2);
        cellActie.setCellValue("Soort Actie");
        cellActie.setCellStyle(headerStyle);
        final Cell cellRegel = row.createCell(3);
        cellRegel.setCellValue("Regel code");
        cellRegel.setCellStyle(headerStyle);
        final Cell cellRegelOms = row.createCell(4);
        cellRegelOms.setCellValue("Regel omschrijving");
        cellRegelOms.setCellStyle(headerStyle);


        final PropertiesConfiguration regelsVoorActieAdmhnd =
                new PropertiesConfiguration(RapportGenerator.class.getResource("/regels-voor-admhnd-actie.properties"));
        final PropertiesConfiguration regelsNaActieAdmhnd =
                new PropertiesConfiguration(RapportGenerator.class.getResource("/regels-na-admhnd-actie.properties"));

        Map<BurgerzakenModule, Map<SoortAdmHandelingSoortActie, List<Object>>> config = new HashMap<>();

        for (SoortAdministratieveHandeling soortAdmHnd : SoortAdministratieveHandeling.values()) {
            if (soortAdmHnd == SoortAdministratieveHandeling.DUMMY) {
                continue;
            }
            for (SoortActie soortActie : SoortActie.values()) {
                if (soortActie == SoortActie.DUMMY) {
                    continue;
                }
                final SoortAdmHandelingSoortActie key = maakKey(soortAdmHnd, soortActie);
                final List<Object> totaalListVoorDezeSoortAdmHndActie = new ArrayList<>();
                final List<Object> voorList = regelsVoorActieAdmhnd.getList(key.toString());
                final List<Object> naList = regelsNaActieAdmhnd.getList(key.toString());
                totaalListVoorDezeSoortAdmHndActie.addAll(voorList);
                totaalListVoorDezeSoortAdmHndActie.addAll(naList);

                Map<SoortAdmHandelingSoortActie, List<Object>> regelListMapModule = config.get(soortAdmHnd.getModule());
                if (regelListMapModule == null) {
                    regelListMapModule = new HashMap<>();
                }
                regelListMapModule.put(key, totaalListVoorDezeSoortAdmHndActie);
                config.put(soortAdmHnd.getModule(), regelListMapModule);
            }
        }

        //Begin met de output:
        int rijnummer = 1;
        final Set<BurgerzakenModule> burgerzakenModules = config.keySet();
        for (BurgerzakenModule burgerzakenModule : burgerzakenModules) {
            final Row moduleRij = sheet.createRow(rijnummer);
            final Cell moduleCel = moduleRij.createCell(0);
            moduleCel.setCellValue(burgerzakenModule.getNaam());
            rijnummer++;

            final Map<SoortAdmHandelingSoortActie, List<Object>> regelsPerAdmhndActie = config.get(burgerzakenModule);
            if (regelsPerAdmhndActie.size() > 0) {
                for (SoortAdmHandelingSoortActie admhndActieKey : regelsPerAdmhndActie.keySet()) {
                    //Handeling rij
                    final Row handelingRij = sheet.createRow(rijnummer);
                    final Cell handelingCel = handelingRij.createCell(1);
                    handelingCel.setCellValue(admhndActieKey.soortAdministratieveHandeling.getNaam());
                    rijnummer++;

                    //Actie rij
                    final Row actieRij = sheet.createRow(rijnummer);
                    final Cell actieCel = actieRij.createCell(2);
                    actieCel.setCellValue(admhndActieKey.soortActie.getNaam());
                    rijnummer++;

                    final List<Object> regels = regelsPerAdmhndActie.get(admhndActieKey);
                    if (regels.isEmpty()) {
                        sheet.removeRow(sheet.getRow(--rijnummer));
                        sheet.removeRow(sheet.getRow(--rijnummer));
                    } else {
                        for (Object regel : regels) {
                            final Row regelRij = sheet.createRow(rijnummer);
                            final Cell regelCel = regelRij.createCell(3);
                            regelCel.setCellValue((String) regel);

                            //Omschrijving van de regel erbij:
                            final Cell regelOmsCel = regelRij.createCell(4);
                            regelOmsCel.setCellValue(Regel.valueOf((String) regel).getOmschrijving());
                            rijnummer++;
                        }
                    }
                }
            }
        }

        // Even auto sizen:
        for (Row rijtje : sheet) {
            sheet.autoSizeColumn(rijtje.getRowNum());
        }
    }

    private void definieerRegelsPerActie(final Sheet sheet) throws ConfigurationException {
        final Row row = sheet.createRow(0);
        final Cell cellActie = row.createCell(0);
        cellActie.setCellValue("Soort Actie");
        cellActie.setCellStyle(headerStyle);
        final Cell cellRegel = row.createCell(1);
        cellRegel.setCellValue("Regel");
        cellRegel.setCellStyle(headerStyle);
        final Cell cellRegelOms = row.createCell(2);
        cellRegelOms.setCellValue("Regel omschrijving");
        cellRegelOms.setCellStyle(headerStyle);


        final PropertiesConfiguration regelsVoorActieAdmhnd =
                new PropertiesConfiguration(RapportGenerator.class.getResource("/regels-voor-admhnd-actie.properties"));
        final PropertiesConfiguration regelsNaActieAdmhnd =
                new PropertiesConfiguration(RapportGenerator.class.getResource("/regels-na-admhnd-actie.properties"));

        int rijnummer = 1;
        for (SoortActie soortActie : SoortActie.values()) {
            List<Object> regelsVoorActie = new ArrayList<>();
            regelsVoorActie.addAll(regelsVoorActieAdmhnd.getList("*." + soortActie.name()));
            regelsVoorActie.addAll(regelsNaActieAdmhnd.getList("*." + soortActie.name()));

            if (!regelsVoorActie.isEmpty()) {
                final Row rijActieNaam = sheet.createRow(rijnummer);
                final Cell celActieNaam = rijActieNaam.createCell(0);
                celActieNaam.setCellValue(soortActie.getNaam());
                rijnummer++;

                for (Object regel : regelsVoorActie) {
                    final Row regelRij = sheet.createRow(rijnummer);
                    final Cell regelCel = regelRij.createCell(1);
                    regelCel.setCellValue((String) regel);
                    final Cell regelOmsCel = regelRij.createCell(2);
                    regelOmsCel.setCellValue(Regel.valueOf((String) regel).getOmschrijving());
                    rijnummer++;
                }
            }
        }

        // Even auto sizen:
        for (Row rijtje : sheet) {
            sheet.autoSizeColumn(rijtje.getRowNum());
        }
    }

    private static final class SoortAdmHandelingSoortActie {
        private static final int HASH_CODE_BASE = 31;
        private SoortAdministratieveHandeling soortAdministratieveHandeling;
        private SoortActie                    soortActie;

        /**
         * Constructor.
         *
         * @param soortAdministratieveHandeling soort administratieve handeling
         * @param soortActie soort actie
         */
        private SoortAdmHandelingSoortActie(
            final SoortAdministratieveHandeling soortAdministratieveHandeling,
            final SoortActie soortActie)
        {
            this.soortAdministratieveHandeling = soortAdministratieveHandeling;
            this.soortActie = soortActie;
        }

        @Override
        public boolean equals(final Object o) {
            boolean resultaat;
            if (this == o) {
                resultaat = true;
            } else if (!(o instanceof SoortAdmHandelingSoortActie)) {
                resultaat = false;
            } else {
                final SoortAdmHandelingSoortActie that = (SoortAdmHandelingSoortActie) o;

                resultaat = this.soortAdministratieveHandeling == that.soortAdministratieveHandeling
                    && this.soortActie == that.soortActie;
            }
            return resultaat;
        }

        @Override
        public int hashCode() {
            int result = soortAdministratieveHandeling.hashCode();
            result = HASH_CODE_BASE * result + soortActie.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return soortAdministratieveHandeling.name() + "." + soortActie.name();
        }
    }

}
