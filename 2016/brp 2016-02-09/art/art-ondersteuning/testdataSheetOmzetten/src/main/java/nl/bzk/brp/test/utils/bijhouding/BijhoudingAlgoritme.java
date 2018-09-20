/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.utils.bijhouding;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import nl.bzk.brp.test.utils.POIUtil;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Voeg de groep 'bijhouding' toe nav de input van de drie groepen:
 * bijhoudingsaard, bijhoudingsgemeente en opschorting
 * Zie het commentaar in de klasse voor het onderliggende algoritme.
 */
public class BijhoudingAlgoritme {

    // Constantes mbt naamgeving ed
    private static final String SHEET_NAAM_PERS = "pers";
    private static final String SHEET_NAAM_BIJHOUDING = "his_persbijhouding";
    private static final String SHEET_NAAM_BIJHOUDINGSAARD = "his_persbijhaard";
    private static final String SHEET_NAAM_BIJHOUDINGSAARD2 = "his_persbijhverantwoordelijk";
    private static final String SHEET_NAAM_BIJHOUDINGSGEMEENTE = "his_persbijhgem";
    private static final String SHEET_NAAM_OPSCHORTING = "his_persopschorting";

    private static final String KOLOM_NAAM_ID = "id";
    private static final String KOLOM_NAAM_PERSOON = "pers";
    private static final String KOLOM_NAAM_DATUM_AANVANG_GELDIGHEID = "dataanvgel";
    private static final String KOLOM_NAAM_DATUM_EINDE_GELDIGHEID = "dateindegel";
    private static final String KOLOM_NAAM_TIJDSTIP_REGISTRATIE = "tsreg";
    private static final String KOLOM_NAAM_TIJDSTIP_VERVAL = "tsverval";
    private static final String KOLOM_NAAM_ACTIE_INHOUD = "actieinh";
    private static final String KOLOM_NAAM_ACTIE_VERVAL = "actieverval";
    private static final String KOLOM_NAAM_NADERE_AANDUIDING_VERVAL = "nadereaandverval";
    private static final String KOLOM_NAAM_ACTIE_AANPASSING_GELDIGHEID = "actieaanpgel";

    private static final String KOLOM_NAAM_BIJHOUDINGSGEMEENTE = "bijhgem";
    private static final String KOLOM_NAAM_BIJHOUDINGSPARTIJ = "bijhpartij";
    private static final String KOLOM_NAAM_BIJHOUDINGSAARD = "bijhaard";
    private static final String KOLOM_NAAM_BIJHOUDINGSAARD2 = "verantwoordelijke";
    private static final String KOLOM_NAAM_NADERE_BIJHOUDINGSAARD = "naderebijhaard";
    private static final String KOLOM_NAAM_INDICATIE_ONVERWERKT_DOCUMENT_AANWEZIG = "indonverwdocaanw";
    private static final String KOLOM_NAAM_REDEN_OPSCHORTING_BIJHOUDING = "rdnopschortingbijhouding";

    private static final int DEFAULT_BIJHOUDINGS_AARD = 1; // Ingezetene
    private static final int DEFAULT_BIJHOUDINGS_PARTIJ = 2; // Minister
    private static final int DEFAULT_NADERE_BIJHOUDINGSAARD = 1; // Actueel
    private static final String DEFAULT_INDICATIE_ONVERWERKT_DOCUMENT_AANWEZIG = "false"; // Nee

    private static final SortedMap<Integer, String> ROW_BIJHOUDING_DEFAULTS = new TreeMap<>();

    static {
        ROW_BIJHOUDING_DEFAULTS.put(0, "kern.his_persbijhouding"); // tabelnaam
        ROW_BIJHOUDING_DEFAULTS.put(1, "14"); // aantal kolommen
        ROW_BIJHOUDING_DEFAULTS.put(2, "id"); // kolomnaam
        ROW_BIJHOUDING_DEFAULTS.put(3, "pers"); // kolomnaam
        ROW_BIJHOUDING_DEFAULTS.put(4, "dataanvgel"); // kolomnaam
        ROW_BIJHOUDING_DEFAULTS.put(5, "dateindegel"); // kolomnaam
        ROW_BIJHOUDING_DEFAULTS.put(6, "tsreg"); // kolomnaam
        ROW_BIJHOUDING_DEFAULTS.put(7, "tsverval"); // kolomnaam
        ROW_BIJHOUDING_DEFAULTS.put(8, "actieinh"); // kolomnaam
        ROW_BIJHOUDING_DEFAULTS.put(9, "actieverval"); // kolomnaam
        ROW_BIJHOUDING_DEFAULTS.put(10, "nadereaandverval"); // kolomnaam
        ROW_BIJHOUDING_DEFAULTS.put(11, "actieaanpgel"); // kolomnaam
        ROW_BIJHOUDING_DEFAULTS.put(12, "bijhpartij"); // kolomnaam
        ROW_BIJHOUDING_DEFAULTS.put(13, "bijhaard"); // kolomnaam
        ROW_BIJHOUDING_DEFAULTS.put(14, "naderebijhaard"); // kolomnaam
        ROW_BIJHOUDING_DEFAULTS.put(15, "indonverwdocaanw"); // kolomnaam
    }

    private Workbook testdataWorkbook;
    private FormulaEvaluator formulaEvaluator;

    public BijhoudingAlgoritme(final Workbook testdataWorkbook) {
        this.testdataWorkbook = testdataWorkbook;
        this.formulaEvaluator = this.testdataWorkbook.getCreationHelper().createFormulaEvaluator();
    }

    public void voegBijhoudingGroepToe() {
        // Verzamel de input van de 3 oude groepen
        BijhoudingOudeGroepen bijhoudingOudeGroepen = this.verzamelBijhoudingOudeGroepen();

        // Maak nieuwe his bijhouding records
        BijhoudingNieuweRecords bijhoudingNieuweRecords = this.maakNieuweBijhoudingRecords(bijhoudingOudeGroepen);

        // Vul de his records aan met 'modelmatige' vervallen records
        this.vulAanMetVervallenHistorie(bijhoudingNieuweRecords);

        // Zet de nieuwe records in de betreffende sheet en update de A-laag met de meest recente info
        zetNieuweRecordsInBijhoudingSheet(bijhoudingNieuweRecords);
    }

    // Loop voor alle personen de gemaakte materiele historie door en maak de modelmatig verwachte
    // vervallen records hierbij aan.
    private void vulAanMetVervallenHistorie(final BijhoudingNieuweRecords bijhoudingNieuweRecords) {
        int maxId = 0;
        for (HisPersoonBijhouding hisPersoonBijhouding : bijhoudingNieuweRecords.getAllePersoonBijhoudingen()) {
            if (hisPersoonBijhouding.getiD() > maxId) {
                maxId = hisPersoonBijhouding.getiD();
            }
        }

        int nextId = maxId + 1;
        for (Integer persoonId : bijhoudingNieuweRecords.getPersoonIds()) {
            for (HisPersoonBijhouding hisPersoonBijhouding : new ArrayList<>(bijhoudingNieuweRecords.getPersoonBijhoudingen(persoonId))) {
                if (hisPersoonBijhouding.getDatumEindeGeldigheid() != null) {
                    // Maak een nieuw vervallen record aan voor het moment dat dit record nog geen einde geldigheid had.
                    HisPersoonBijhouding vervallenBijhouding = new HisPersoonBijhouding();
                    vervallenBijhouding.setDatumAanvangGeldigheid(hisPersoonBijhouding.getDatumAanvangGeldigheid());
                    vervallenBijhouding.setDatumEindeGeldigheid(null);
                    vervallenBijhouding.setDatumTijdRegistratie(hisPersoonBijhouding.getDatumAanvangGeldigheid());
                    vervallenBijhouding.setDatumTijdVerval(hisPersoonBijhouding.getDatumEindeGeldigheid());
                    vervallenBijhouding.setActieInhoud(hisPersoonBijhouding.getActieInhoud());
                    vervallenBijhouding.setActieVerval(hisPersoonBijhouding.getActieAanpassingGeldigheid());

                    vervallenBijhouding.setiD(nextId);
                    nextId++;
                    vervallenBijhouding.setPersoon(hisPersoonBijhouding.getPersoon());
                    vervallenBijhouding.setBijhoudingsaard(hisPersoonBijhouding.getBijhoudingsaard());
                    vervallenBijhouding.setBijhoudingspartij(hisPersoonBijhouding.getBijhoudingspartij());
                    vervallenBijhouding.setNadereBijhoudingsaard(hisPersoonBijhouding.getNadereBijhoudingsaard());
                    vervallenBijhouding.setIndicatieOnverwerktDocumentAanwezig(hisPersoonBijhouding.getIndicatieOnverwerktDocumentAanwezig());

                    bijhoudingNieuweRecords.voegBijhoudingToe(vervallenBijhouding);
                }
            }
        }
    }

    private void zetNieuweRecordsInBijhoudingSheet(final BijhoudingNieuweRecords bijhoudingNieuweRecords) {
        Sheet sheet = testdataWorkbook.getSheet(SHEET_NAAM_BIJHOUDING);
        Sheet persSheet = testdataWorkbook.getSheet(SHEET_NAAM_PERS);

        // Legen bestaande data.
        Iterator<Row> rowIter = sheet.rowIterator();
        // Skip headers
        rowIter.next();
        while (rowIter.hasNext()) {
            Row row = rowIter.next();
            Iterator<Cell> cellIter = row.cellIterator();
            while (cellIter.hasNext()) {
                Cell cell = cellIter.next();
                cell.setCellValue((String) null);
                cell.setCellType(Cell.CELL_TYPE_BLANK);
                row.removeCell(cell);
            }
        }

        int rowNummer = 1;
        for (Integer persoonId : bijhoudingNieuweRecords.getPersoonIds()) {
            HisPersoonBijhouding meestRecenteRecord = null;
            for (HisPersoonBijhouding bijhouding : bijhoudingNieuweRecords.getPersoonBijhoudingen(persoonId)) {
                if (meestRecenteRecord == null) {
                    meestRecenteRecord = bijhouding;
                }
                sheet.createRow(rowNummer);
                Row row = sheet.getRow(rowNummer);
                vulDefaultsVoorBijhouding(row);
                row.getCell(16).setCellValue(bijhouding.getiD());
                row.getCell(17).setCellValue(bijhouding.getPersoon());
                row.getCell(18).setCellValue(bijhouding.getDatumAanvangGeldigheid());
                if (bijhouding.getDatumEindeGeldigheid() != null) { row.getCell(19).setCellValue(bijhouding.getDatumEindeGeldigheid()); }
                row.getCell(20).setCellValue(bijhouding.getDatumTijdRegistratie());
                if (bijhouding.getDatumTijdVerval() != null) { row.getCell(21).setCellValue(bijhouding.getDatumTijdVerval()); }
                if (bijhouding.getActieInhoud() != null) { row.getCell(22).setCellValue(bijhouding.getActieInhoud()); }
                if (bijhouding.getActieVerval() != null) { row.getCell(23).setCellValue(bijhouding.getActieVerval()); }
                if (bijhouding.getNadereAanduidingVerval() != null) { row.getCell(24).setCellValue(bijhouding.getNadereAanduidingVerval()); }
                if (bijhouding.getActieAanpassingGeldigheid() != null) { row.getCell(25).setCellValue(bijhouding.getActieAanpassingGeldigheid()); }
                if (bijhouding.getBijhoudingspartij() != null) { row.getCell(26).setCellValue(bijhouding.getBijhoudingspartij()); }
                if (bijhouding.getBijhoudingsaard() != null) { row.getCell(27).setCellValue(bijhouding.getBijhoudingsaard()); }
                if (bijhouding.getNadereBijhoudingsaard() != null) { row.getCell(28).setCellValue(bijhouding.getNadereBijhoudingsaard()); }
                if (bijhouding.getIndicatieOnverwerktDocumentAanwezig() != null) { row.getCell(29).setCellValue(bijhouding.getIndicatieOnverwerktDocumentAanwezig()); }

                rowNummer++;
            }

            // Kopieer de meest recente info uit de his naar de a-laag (pers sheet).
            Integer rowIndex = getPersRowNumber(persSheet, persoonId);
            Integer aardIndex = POIUtil.getColumnIndex(persSheet, KOLOM_NAAM_BIJHOUDINGSAARD);
            if (aardIndex == null) {
                aardIndex = POIUtil.getColumnIndex(persSheet, KOLOM_NAAM_BIJHOUDINGSAARD2);
            }
            Integer gemIndex = POIUtil.getColumnIndex(persSheet, KOLOM_NAAM_BIJHOUDINGSPARTIJ);
            Integer docIndex = POIUtil.getColumnIndex(persSheet, KOLOM_NAAM_INDICATIE_ONVERWERKT_DOCUMENT_AANWEZIG);
            Integer nadereIndex = POIUtil.getColumnIndex(persSheet, KOLOM_NAAM_NADERE_BIJHOUDINGSAARD);

            Cell bijhoudingsaardKolomCell = persSheet.getRow(rowIndex).getCell(aardIndex);
            if (bijhoudingsaardKolomCell != null) {
                bijhoudingsaardKolomCell.setCellValue((String) null);
                bijhoudingsaardKolomCell.setCellType(Cell.CELL_TYPE_BLANK);
                persSheet.getRow(rowIndex).removeCell(bijhoudingsaardKolomCell);
            }
            bijhoudingsaardKolomCell = persSheet.getRow(rowIndex).createCell(aardIndex);
            bijhoudingsaardKolomCell.setCellValue(meestRecenteRecord.getBijhoudingsaard());

            Cell bijhoudingsgemKolomCell = persSheet.getRow(rowIndex).getCell(gemIndex);
            if (bijhoudingsgemKolomCell != null) {
                bijhoudingsgemKolomCell.setCellValue((String) null);
                bijhoudingsgemKolomCell.setCellType(Cell.CELL_TYPE_BLANK);
                persSheet.getRow(rowIndex).removeCell(bijhoudingsgemKolomCell);
            }
            bijhoudingsgemKolomCell = persSheet.getRow(rowIndex).createCell(gemIndex);
            bijhoudingsgemKolomCell.setCellValue(meestRecenteRecord.getBijhoudingspartij());

            Cell bijhoudingsdocKolomCell = persSheet.getRow(rowIndex).getCell(docIndex);
            if (bijhoudingsdocKolomCell != null) {
                bijhoudingsdocKolomCell.setCellValue((String) null);
                bijhoudingsdocKolomCell.setCellType(Cell.CELL_TYPE_BLANK);
                persSheet.getRow(rowIndex).removeCell(bijhoudingsdocKolomCell);
            }
            bijhoudingsdocKolomCell = persSheet.getRow(rowIndex).createCell(docIndex);
            bijhoudingsdocKolomCell.setCellValue(meestRecenteRecord.getIndicatieOnverwerktDocumentAanwezig());

            Cell bijhoudingsnadereKolomCell = persSheet.getRow(rowIndex).getCell(nadereIndex);
            if (bijhoudingsnadereKolomCell != null) {
                bijhoudingsnadereKolomCell.setCellValue((String) null);
                bijhoudingsnadereKolomCell.setCellType(Cell.CELL_TYPE_BLANK);
                persSheet.getRow(rowIndex).removeCell(bijhoudingsnadereKolomCell);
            }
            bijhoudingsnadereKolomCell = persSheet.getRow(rowIndex).createCell(nadereIndex);
            bijhoudingsnadereKolomCell.setCellValue(meestRecenteRecord.getNadereBijhoudingsaard());
        }
    }

    private int getPersRowNumber(final Sheet persSheet, final Integer persoonId) {
        int rowNumber = 0;
        Iterator<Row> rowIter = persSheet.rowIterator();
        int persIdKolomIndex = POIUtil.getColumnIndex(persSheet, "id");
        // Skip kolom headers.
        rowIter.next();
        while (rowIter.hasNext()) {
            rowNumber++;
            Row row = rowIter.next();
            Cell cell = row.getCell(persIdKolomIndex);
            if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                if ((int) cell.getNumericCellValue() == persoonId) {
                    break;
                }
            } else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
                if ((int) formulaEvaluator.evaluate(cell).getNumberValue() == persoonId) {
                    break;
                }
            }
        }
        return rowNumber;
    }

    // Maakt tevens de benodigde cellen aan.
    private void vulDefaultsVoorBijhouding(final Row row) {
        for (int i = 0; i <= 29; i++) {
            row.createCell(i);
        }
        for (Map.Entry<Integer, String> defaultEntry : ROW_BIJHOUDING_DEFAULTS.entrySet()) {
            row.getCell(defaultEntry.getKey()).setCellValue(defaultEntry.getValue());
        }
    }

    private BijhoudingNieuweRecords maakNieuweBijhoudingRecords(final BijhoudingOudeGroepen bijhoudingOudeGroepen) {
        BijhoudingNieuweRecords bijhoudingNieuweRecords = new BijhoudingNieuweRecords();

        Integer nextId = null;
        // Voor elke persoon.
        for (Integer persoonId : bijhoudingOudeGroepen.getPersoonIds()) {

/* NB: 2014-02-13: Alle handmatige acties weer uit, personen aangepast

            // Handmatige skip van 'probleemgevallen' (crossover data).
            if (persoonId == 21030 || persoonId == 7041 || persoonId == 17041 || persoonId == 14039) {
                continue;
            }

            // Custom fix voor gat in bijhoudingsgemeente historie.
            if (("" + persoonId).endsWith("39")) {
                for (HisPersoonBijhoudingsgemeente eenGem : bijhoudingOudeGroepen.getPersoonBijhoudingsgemeenten(persoonId)) {
                    if (eenGem.getDatumEindeGeldigheid() != null && eenGem.getDatumEindeGeldigheid().equals(20110901)) {
                        eenGem.setDatumEindeGeldigheid(20111201);
                    }
                }
            }

            // Custom fix: haal de opschorting naar voren tot na de laatste bijh. gemeente change, zodat het weer 'sense' maakt
            if (("" + persoonId).endsWith("41") || persoonId == 6039 || persoonId == 16039) {
                bijhoudingOudeGroepen.getPersoonOpschortingen(persoonId).iterator().next().setDatumAanvangGeldigheid(
                        bijhoudingOudeGroepen.getPersoonBijhoudingsgemeenten(persoonId).iterator().next().getDatumAanvangGeldigheid() + 5);
            }
*/

            // Skip iedereen behalve de interessante personen voor de laatste conversie.
            if (persoonId != 21030 && persoonId != 7041 && persoonId != 17041 && persoonId != 14039) {
                continue;
            }

            System.out.println("Conversie start voor persoon: " + persoonId);

            // Zoek de oudste datum aanvang van een bijhoudingsaard of gemeente
            Integer oudsteDAGOverall = 20150101;
            Integer oudsteDAGAard = 20150101;
            Integer oudsteDAGGem = 20150101;
            boolean oudsteDagVanAard = true;
            HisPersoonBijhoudingsaard oudsteAard = null;
            HisPersoonBijhoudingsgemeente oudsteGem = null;
            for (HisPersoonBijhoudingsaard bijhoudingsaard : bijhoudingOudeGroepen.getPersoonBijhoudingsaarden(persoonId)) {
                if (bijhoudingsaard.getDatumAanvangGeldigheid() < oudsteDAGOverall) {
                    oudsteDAGOverall = bijhoudingsaard.getDatumAanvangGeldigheid();
                }
                if (bijhoudingsaard.getDatumAanvangGeldigheid() < oudsteDAGAard) {
                    oudsteDAGAard = bijhoudingsaard.getDatumAanvangGeldigheid();
                    oudsteAard = bijhoudingsaard;
                }
            }
            for (HisPersoonBijhoudingsgemeente bijhoudingsgemeente : bijhoudingOudeGroepen.getPersoonBijhoudingsgemeenten(persoonId)) {
                if (bijhoudingsgemeente.getDatumAanvangGeldigheid() < oudsteDAGOverall) {
                    oudsteDAGOverall = bijhoudingsgemeente.getDatumAanvangGeldigheid();
                    oudsteDagVanAard = false;
                }
                if (bijhoudingsgemeente.getDatumAanvangGeldigheid() < oudsteDAGGem) {
                    oudsteDAGGem = bijhoudingsgemeente.getDatumAanvangGeldigheid();
                    oudsteGem = bijhoudingsgemeente;
                }
            }

            // Trek de oudste DAG gelijk + bepaal start next id (indien nog niet bekend)
            if (oudsteDagVanAard) {
                if (nextId == null) nextId = oudsteAard.getiD();
                if (oudsteGem != null) {
                    oudsteGem.setDatumAanvangGeldigheid(oudsteDAGOverall);
                } else {
                    System.out.println("Geen gem gevonden voor persoon: " + persoonId);
                }
            } else {
                if (nextId == null) nextId = oudsteGem.getiD();
                if (oudsteAard != null) {
                    oudsteAard.setDatumAanvangGeldigheid(oudsteDAGOverall);
                } else {
                    System.out.println("Geen aard gevonden voor persoon: " + persoonId);
                }
            }

            List<HisPersoonRecord> periodeGrenzen = berekenPeriodeGrenzen(bijhoudingOudeGroepen, persoonId);

            // Zolang er nog volgende DEG's zijn: Maak record aan van huidige dag tot eerstvolgende deg
            for (int periodeIndex = 0; periodeIndex < periodeGrenzen.size(); periodeIndex++) {
                HisPersoonRecord periodeGrens = periodeGrenzen.get(periodeIndex);

                // Skip alle bijhoudingsaard records, die hebben zelfde aanvang geldigheid als oudste gem en
                // er zijn nooit 'nieuwere' bijhoudingsaarden.
                if (periodeGrens instanceof HisPersoonBijhoudingsaard && bijhoudingOudeGroepen.getPersoonBijhoudingsgemeenten(persoonId).size() > 0) {
                    continue;
                }

                System.out.println(periodeGrens.getClass().getSimpleName() + ": " + periodeGrens.getDatumAanvangGeldigheid() + " - " + periodeGrens.getDatumEindeGeldigheid());

                HisPersoonBijhoudingsaard aard = prikHisRecord(bijhoudingOudeGroepen.getPersoonBijhoudingsaarden(persoonId), periodeGrens.getDatumAanvangGeldigheid());
                HisPersoonBijhoudingsgemeente gem = prikHisRecord(bijhoudingOudeGroepen.getPersoonBijhoudingsgemeenten(persoonId), periodeGrens.getDatumAanvangGeldigheid());
                HisPersoonOpschorting opschorting = prikHisRecord(bijhoudingOudeGroepen.getPersoonOpschortingen(persoonId), periodeGrens.getDatumAanvangGeldigheid());

                HisPersoonBijhouding bijhouding = new HisPersoonBijhouding();
                bijhouding.setiD(nextId);
                nextId++;
                bijhouding.setPersoon(persoonId);
                bijhouding.setDatumAanvangGeldigheid(periodeGrens.getDatumAanvangGeldigheid());
                if (periodeIndex < periodeGrenzen.size() - 1) {
                    // Gebruik eigen datum einde geldigheid indien aanwezig, anders aanvang geldigheid van volgende record
                    if (periodeGrens.getDatumEindeGeldigheid() == null || periodeGrens.getDatumEindeGeldigheid() == 0) {
                        bijhouding.setDatumEindeGeldigheid(periodeGrenzen.get(periodeIndex + 1).getDatumAanvangGeldigheid());
                    } else {
                        bijhouding.setDatumEindeGeldigheid(periodeGrens.getDatumEindeGeldigheid());
                        // Ook een dummy nodig als de datum einde van huidige niet overeen komt met datum aanvang van een volgende.
                        if (!periodeGrens.getDatumEindeGeldigheid().equals(periodeGrenzen.get(periodeIndex + 1).getDatumAanvangGeldigheid())) {
                            HisPersoonRecord dummyHisRecord = new HisPersoonRecord() { };
                            dummyHisRecord.setDatumAanvangGeldigheid(periodeGrens.getDatumEindeGeldigheid());
                            dummyHisRecord.setActieInhoud(periodeGrens.getActieAanpassingGeldigheid());
                            dummyHisRecord.setActieAanpassingGeldigheid(periodeGrenzen.get(periodeIndex + 1).getActieInhoud());
                            periodeGrenzen.add(periodeIndex + 1, dummyHisRecord);
                        }
                    }
                } else if (periodeGrens.getDatumEindeGeldigheid() != null && periodeGrens.getDatumEindeGeldigheid() != 0) {
                    bijhouding.setDatumEindeGeldigheid(periodeGrenzen.get(periodeIndex).getDatumEindeGeldigheid());
                    // Het laatste record in de periode grenzen lijst heeft een einde geleigheid.
                    // Dat betekent dat we ook nog een laatste record nodig hebben van die einde geldigheid tot 'oneindig'.
                    // Daarom maken we een extra 'dummy' grens record aan, die hiervoor zal zorgen in de laatste for-loop-doorloping.
                    // Uiteraard zonder einde geldigheid, zodat deze cyclus doorbroken wordt. :)
                    HisPersoonRecord dummyHisRecord = new HisPersoonRecord() { };
                    dummyHisRecord.setDatumAanvangGeldigheid(periodeGrens.getDatumEindeGeldigheid());
                    dummyHisRecord.setActieInhoud(periodeGrens.getActieAanpassingGeldigheid());
                    periodeGrenzen.add(dummyHisRecord);
                }


                // Neem als tsreg als default de datum aanvang geldigheid of als de datum einde geldigheid niet leeg is neem je die.
                if (bijhouding.getDatumEindeGeldigheid() != null) {
                    bijhouding.setDatumTijdRegistratie(bijhouding.getDatumEindeGeldigheid());
                } else {
                    bijhouding.setDatumTijdRegistratie(bijhouding.getDatumAanvangGeldigheid());
                }
                // Neem als bron record voor de actie inhoud het prik-record met dezelfde datum aanvang geldigheid,
                // met als prio: aard, gem, opschorting.
                if (aard.getDatumAanvangGeldigheid().equals(periodeGrens.getDatumAanvangGeldigheid())) {
                    bijhouding.setActieInhoud(aard.getActieInhoud());
                } else if (gem.getDatumAanvangGeldigheid().equals(periodeGrens.getDatumAanvangGeldigheid())) {
                    bijhouding.setActieInhoud(gem.getActieInhoud());
                } else if (opschorting != null && opschorting.getDatumAanvangGeldigheid().equals(periodeGrens.getDatumAanvangGeldigheid())) {
                    bijhouding.setActieInhoud(opschorting.getActieInhoud());
                } else {
                    // Mag alleen als we de dummy te pakken hebben.
                    if (periodeGrens.getClass().getSimpleName().equals("")) {
                        bijhouding.setActieInhoud(periodeGrens.getActieInhoud());
                    } else {
                        throw new IllegalStateException("Geen aard, gem of opschorting met aanvang geldigheid gelijk aan periode grens.");
                    }
                }

                // Als we een einde geldigheid hebben, moet er ook een actie aanpassing geldigheid komen
                if (bijhouding.getDatumEindeGeldigheid() != null) {
                    // Neem als actie aanpassing geldigheid de a-a-g van het bronrecord: prio gem dan opschorting
                    if (gem.getDatumAanvangGeldigheid().equals(periodeGrens.getDatumAanvangGeldigheid())) {
                        // Als de gem geen einde geldigheid heeft, dan nemen we het opschorting record met wel einde geldigheid.
                        if (gem.getDatumEindeGeldigheid() == null || gem.getDatumEindeGeldigheid() == 0) {
                            for (HisPersoonOpschorting eenOpschorting : bijhoudingOudeGroepen.getPersoonOpschortingen(persoonId)) {
                                if (eenOpschorting.getDatumEindeGeldigheid() != null && eenOpschorting.getDatumEindeGeldigheid() > 0) {
                                    bijhouding.setActieAanpassingGeldigheid(eenOpschorting.getActieInhoud());
                                    break;
                                }
                            }
                        } else {
                            bijhouding.setActieAanpassingGeldigheid(gem.getActieAanpassingGeldigheid());
                        }
                    } else if (opschorting != null && opschorting.getDatumAanvangGeldigheid().equals(periodeGrens.getDatumAanvangGeldigheid())) {
                        bijhouding.setActieAanpassingGeldigheid(opschorting.getActieAanpassingGeldigheid());
                    } else {
                        // Mag alleen als we de dummy te pakken hebben.
                        if (periodeGrens.getClass().getSimpleName().equals("")) {
                            bijhouding.setActieAanpassingGeldigheid(periodeGrens.getActieAanpassingGeldigheid());
                        } else {
                            throw new IllegalStateException("Geen gem of opschorting met aanvang geldigheid gelijk aan periode grens.");
                        }
                    }
                }

                if (aard != null) {
                    bijhouding.setBijhoudingsaard(aard.getBijhoudingsaard());
                } else {
                    bijhouding.setBijhoudingsaard(DEFAULT_BIJHOUDINGS_AARD);
                }

                if (gem != null) {
                    bijhouding.setBijhoudingspartij(gem.getBijhoudingsgemeente());
                    bijhouding.setIndicatieOnverwerktDocumentAanwezig(gem.getIndicatieOnverwerktDocumentAanwezig());
                } else {
                    bijhouding.setBijhoudingspartij(DEFAULT_BIJHOUDINGS_PARTIJ);
                    bijhouding.setIndicatieOnverwerktDocumentAanwezig(DEFAULT_INDICATIE_ONVERWERKT_DOCUMENT_AANWEZIG);
                }

                if (opschorting != null) {
                    int nadereBijhaard;
                    int redenOpschorting = opschorting.getRedenOpschortingBijhouding();
                    if (redenOpschorting == 1) {
                        nadereBijhaard = 4;
                    } else if (redenOpschorting == 2) {
                        nadereBijhaard = 6;
                    } else if (redenOpschorting == 3) {
                        nadereBijhaard = 7;
                    } else {
                        throw new IllegalArgumentException("Onconverteerbare reden opschorting: " + redenOpschorting);
                    }
                    bijhouding.setNadereBijhoudingsaard(nadereBijhaard);
                } else {
                    bijhouding.setNadereBijhoudingsaard(DEFAULT_NADERE_BIJHOUDINGSAARD);
                }

                bijhoudingNieuweRecords.voegBijhoudingToe(bijhouding);
            }
        }

        return bijhoudingNieuweRecords;
    }

    private <T extends HisPersoonRecord> T prikHisRecord(final SortedSet<T> records, final int periodeGrens) {
        T record = null;
        // De records zijn gesorteerd op DAG (aflopend), dus het is degene die als eerste voldoet
        // en kleiner is dan de betreffende DEG (indien aanwezig).
        for (T eenRecord : records) {
            if (eenRecord.getDatumAanvangGeldigheid() <= periodeGrens) {
                if (eenRecord.getDatumEindeGeldigheid() == null || eenRecord.getDatumEindeGeldigheid() == 0
                        // Op datum einde geldigheid is het record zelf niet meer geldig, vandaar kleinder dan.
                        || (eenRecord.getDatumEindeGeldigheid() != null && periodeGrens < eenRecord.getDatumEindeGeldigheid())) {
                    record = eenRecord;
                }
                // Sowieso breaken, als datum einde niet klopte, is er dus geen match.
                break;
            }
        }
        return record;
    }

    // Zet de periode grenzen op een rijtje van oud naar nieuw
    private List<HisPersoonRecord> berekenPeriodeGrenzen(final BijhoudingOudeGroepen bijhoudingOudeGroepen, final Integer persoonId) {
        // Sorteren door in sorted set te gooien, maar dan andersom gesorteerd, namelijk oud naar nieuw.
        SortedSet<HisPersoonRecord> grenzen = new TreeSet<>(new Comparator<HisPersoonRecord>() {
            @Override
            public int compare(final HisPersoonRecord o1, final HisPersoonRecord o2) {
                return -1 * o1.compareTo(o2);
            }
        });
        // Speciale uitzondering: als er maar 1 bijhoudingsaard en 1 bijhoudingsgemeente record is, die ook dezelfde geldigheid hebben,
        // dan hoeven we alleen maar de bijhoudingsgemeente mee te nemen
        if (bijhoudingOudeGroepen.getPersoonBijhoudingsaarden(persoonId).size() == 1
                && bijhoudingOudeGroepen.getPersoonBijhoudingsgemeenten(persoonId).size() == 1
                && bijhoudingOudeGroepen.getPersoonBijhoudingsaarden(persoonId).iterator().next().getDatumAanvangGeldigheid().equals(bijhoudingOudeGroepen.getPersoonBijhoudingsgemeenten(persoonId).iterator().next().getDatumAanvangGeldigheid())
                && bijhoudingOudeGroepen.getPersoonBijhoudingsaarden(persoonId).iterator().next().getDatumEindeGeldigheid().equals(bijhoudingOudeGroepen.getPersoonBijhoudingsgemeenten(persoonId).iterator().next().getDatumEindeGeldigheid())) {
            grenzen.addAll(bijhoudingOudeGroepen.getPersoonBijhoudingsgemeenten(persoonId));
            grenzen.addAll(bijhoudingOudeGroepen.getPersoonOpschortingen(persoonId));
        } else {
            // Eigenlijk kun je gewoon altijd de aard records negeren.
            grenzen.addAll(bijhoudingOudeGroepen.getPersoonBijhoudingsgemeenten(persoonId));
            grenzen.addAll(bijhoudingOudeGroepen.getPersoonOpschortingen(persoonId));
//            grenzen.addAll(bijhoudingOudeGroepen.getPersoonRecords(persoonId));
        }
        // Maak er nu een lijst van.
        return new ArrayList<>(grenzen);
    }

    private BijhoudingOudeGroepen verzamelBijhoudingOudeGroepen() {
        BijhoudingOudeGroepen bijhoudingOudeGroepen = new BijhoudingOudeGroepen();

        Sheet sheet = testdataWorkbook.getSheet(SHEET_NAAM_BIJHOUDINGSAARD);
        if (sheet == null) {
            sheet = testdataWorkbook.getSheet(SHEET_NAAM_BIJHOUDINGSAARD2);
        }
        Iterator<Row> iter = sheet.rowIterator();
        // Sla de kolom headers over.
        iter.next();
        while (iter.hasNext()) {
            Row row = iter.next();
            HisPersoonBijhoudingsaard bijhoudingsaard = new HisPersoonBijhoudingsaard();
            this.vulHisPersoonVelden(sheet, row, bijhoudingsaard);
            bijhoudingsaard.setBijhoudingsaard(getValueAsInt(sheet, row, KOLOM_NAAM_BIJHOUDINGSAARD));
            bijhoudingOudeGroepen.voegBijhoudingsaardToe(bijhoudingsaard);
        }

        sheet = testdataWorkbook.getSheet(SHEET_NAAM_BIJHOUDINGSGEMEENTE);
        iter = sheet.rowIterator();
        // Sla de kolom headers over.
        iter.next();
        while (iter.hasNext()) {
            Row row = iter.next();
            HisPersoonBijhoudingsgemeente bijhoudingsgemeente = new HisPersoonBijhoudingsgemeente();
            this.vulHisPersoonVelden(sheet, row, bijhoudingsgemeente);
            bijhoudingsgemeente.setBijhoudingsgemeente(getValueAsInt(sheet, row, KOLOM_NAAM_BIJHOUDINGSGEMEENTE));
            bijhoudingsgemeente.setIndicatieOnverwerktDocumentAanwezig(getValueAsString(sheet, row, KOLOM_NAAM_INDICATIE_ONVERWERKT_DOCUMENT_AANWEZIG));
            // Veld niet vullen, vervalt.
            //bijhoudingsgemeente.setDatumInschrijvingInGemeente(getValueAsInt(sheet, row, KOLOM_NAAM_);
            bijhoudingOudeGroepen.voegBijhoudinggemeenteToe(bijhoudingsgemeente);
        }

        sheet = testdataWorkbook.getSheet(SHEET_NAAM_OPSCHORTING);
        iter = sheet.rowIterator();
        // Sla de kolom headers over.
        iter.next();
        while (iter.hasNext()) {
            Row row = iter.next();
            HisPersoonOpschorting opschorting = new HisPersoonOpschorting();
            this.vulHisPersoonVelden(sheet, row, opschorting);
            opschorting.setRedenOpschortingBijhouding(getValueAsInt(sheet, row, KOLOM_NAAM_REDEN_OPSCHORTING_BIJHOUDING));
            bijhoudingOudeGroepen.voegOpschortingToe(opschorting);
        }

        return bijhoudingOudeGroepen;
    }

    private void vulHisPersoonVelden(final Sheet sheet, final Row row, final HisPersoonRecord hisPersoonRecord) {
        hisPersoonRecord.setiD(getValueAsInt(sheet, row, KOLOM_NAAM_ID));
        hisPersoonRecord.setPersoon(getValueAsInt(sheet, row, KOLOM_NAAM_PERSOON));
        hisPersoonRecord.setDatumAanvangGeldigheid(getValueAsInt(sheet, row, KOLOM_NAAM_DATUM_AANVANG_GELDIGHEID));
        hisPersoonRecord.setDatumEindeGeldigheid(getValueAsInt(sheet, row, KOLOM_NAAM_DATUM_EINDE_GELDIGHEID));
        hisPersoonRecord.setDatumTijdRegistratie(getValueAsInt(sheet, row, KOLOM_NAAM_TIJDSTIP_REGISTRATIE));
        hisPersoonRecord.setDatumTijdVerval(getValueAsInt(sheet, row, KOLOM_NAAM_TIJDSTIP_VERVAL));
        hisPersoonRecord.setActieInhoud(getValueAsInt(sheet, row, KOLOM_NAAM_ACTIE_INHOUD));
        hisPersoonRecord.setActieVerval(getValueAsInt(sheet, row, KOLOM_NAAM_ACTIE_VERVAL));
        hisPersoonRecord.setActieAanpassingGeldigheid(getValueAsInt(sheet, row, KOLOM_NAAM_ACTIE_AANPASSING_GELDIGHEID));
    }

    private Integer getValueAsInt(final Sheet sheet, final Row row, final String kolomNaam) {
        Cell cell = row.getCell(POIUtil.getColumnIndex(sheet, kolomNaam, true));
        if (cell == null) return null;
        if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
            CellValue cellValue = this.formulaEvaluator.evaluate(cell);
            String stringValue = cellValue.getStringValue();
            if (stringValue != null) {
                if (stringValue.trim().equals("")) {
                    return null;
                } else {
                    return Integer.parseInt(stringValue);
                }
            } else {
                return (int) cellValue.getNumberValue();
            }
        } else {
            if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                String stringValue = cell.getStringCellValue();
                if (stringValue.trim().equals("")) {
                    return null;
                } else {
                    return Integer.parseInt(stringValue);
                }
            } else {
                return (int) cell.getNumericCellValue();
            }
        }
    }

    private String getValueAsString(final Sheet sheet, final Row row, final String kolomNaam) {
        Cell cell = row.getCell(POIUtil.getColumnIndex(sheet, kolomNaam, true));
        if (cell == null) return null;
        if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
            CellValue cellValue = this.formulaEvaluator.evaluate(cell);
            return cellValue.getStringValue();
        } else {
            return cell.getStringCellValue();
        }
    }

}
