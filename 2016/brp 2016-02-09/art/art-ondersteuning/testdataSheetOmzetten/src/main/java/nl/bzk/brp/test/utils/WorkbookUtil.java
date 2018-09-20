/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 *
 * @author jk getted from
 *         http://jxls.cvs.sourceforge.net/jxls/jxls/src/java/org/
 *         jxls/util/Util.java?revision=1.8&view=markup by Leonid Vysochyn and
 *         modified (adding styles copying) modified by Philipp Löpmeier
 *         (replacing deprecated classes and methods, using generic types)
 */
public final class WorkbookUtil {

	/**
	 * DEFAULT CONSTRUCTOR.
	 */
	private WorkbookUtil() {
	}

	/**
	 * @param newSheet
	 *            the sheet to create from the copy.
	 * @param sheet
	 *            the sheet to copy.
	 */
	public static void copySheets(HSSFSheet newSheet, HSSFSheet sheet) {
		copySheets(newSheet, sheet, true);
	}

	/**
	 * @param newSheet
	 *            the sheet to create from the copy.
	 * @param sheet
	 *            the sheet to copy.
	 * @param copyStyle
	 *            true copy the style.
	 */
	public static void copySheets(HSSFSheet newSheet, HSSFSheet sheet,
			boolean copyStyle) {
		int maxColumnNum = 0;
		Map<Integer, HSSFCellStyle> styleMap = (copyStyle) ? new HashMap<Integer, HSSFCellStyle>()
				: null;
		for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
			HSSFRow srcRow = sheet.getRow(i);
			HSSFRow destRow = newSheet.createRow(i);
			if (srcRow != null) {
				WorkbookUtil
						.copyRow(sheet, newSheet, srcRow, destRow, styleMap);
				if (srcRow.getLastCellNum() > maxColumnNum) {
					maxColumnNum = srcRow.getLastCellNum();
				}
			}
		}
		for (int i = 0; i <= maxColumnNum; i++) {
			newSheet.setColumnWidth(i, sheet.getColumnWidth(i));
		}
	}

	/**
	 * @param srcSheet
	 *            the sheet to copy.
	 * @param destSheet
	 *            the sheet to create.
	 * @param srcRow
	 *            the row to copy.
	 * @param destRow
	 *            the row to create.
	 * @param styleMap
	 *            -
	 */
	public static void copyRow(HSSFSheet srcSheet, HSSFSheet destSheet,
			HSSFRow srcRow, HSSFRow destRow,
			Map<Integer, HSSFCellStyle> styleMap) {
		// manage a list of merged zone in order to not insert two times a
		// merged zone
		Set<CellRangeAddressWrapper> mergedRegions = new TreeSet<CellRangeAddressWrapper>();
		destRow.setHeight(srcRow.getHeight());
		// pour chaque row
		for (int j = srcRow.getFirstCellNum(); j <= srcRow.getLastCellNum(); j++) {
			HSSFCell oldCell = srcRow.getCell(j); // ancienne cell
			HSSFCell newCell = destRow.getCell(j); // new cell
			if (oldCell != null) {
				if (newCell == null) {
					newCell = destRow.createCell(j);
				}
				// copy chaque cell
				copyCell(oldCell, newCell, styleMap);
				// copy les informations de fusion entre les cellules
				// System.out.println("row num: " + srcRow.getRowNum() +
				// " , col: " + (short)oldCell.getColumnIndex());
				CellRangeAddress mergedRegion = getMergedRegion(srcSheet,
						srcRow.getRowNum(), (short) oldCell.getColumnIndex());

				if (mergedRegion != null) {
					// System.out.println("Selected merged region: " +
					// mergedRegion.toString());
					CellRangeAddress newMergedRegion = new CellRangeAddress(
							mergedRegion.getFirstRow(),
							mergedRegion.getLastRow(),
							mergedRegion.getFirstColumn(),
							mergedRegion.getLastColumn());
					// System.out.println("New merged region: " +
					// newMergedRegion.toString());
					CellRangeAddressWrapper wrapper = new CellRangeAddressWrapper(
							newMergedRegion);
					if (isNewMergedRegion(wrapper, mergedRegions)) {
						mergedRegions.add(wrapper);
						destSheet.addMergedRegion(wrapper.range);
					}
				}
			}
		}

	}

	/**
	 * @param oldCell
	 * @param newCell
	 * @param styleMap
	 */
	public static void copyCell(HSSFCell oldCell, HSSFCell newCell,
			Map<Integer, HSSFCellStyle> styleMap) {
		if (styleMap != null) {
			if (oldCell.getSheet().getWorkbook() == newCell.getSheet()
					.getWorkbook()) {
				newCell.setCellStyle(oldCell.getCellStyle());
			} else {
				int stHashCode = oldCell.getCellStyle().hashCode();
				HSSFCellStyle newCellStyle = styleMap.get(stHashCode);
				if (newCellStyle == null) {
					newCellStyle = newCell.getSheet().getWorkbook()
							.createCellStyle();
					newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
					styleMap.put(stHashCode, newCellStyle);
				}
				newCell.setCellStyle(newCellStyle);
			}
		}
		switch (oldCell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			newCell.setCellValue(oldCell.getStringCellValue());
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			newCell.setCellValue(oldCell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			newCell.setCellType(HSSFCell.CELL_TYPE_BLANK);
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			newCell.setCellValue(oldCell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_ERROR:
			newCell.setCellErrorValue(oldCell.getErrorCellValue());
			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			newCell.setCellFormula(oldCell.getCellFormula());
			break;
		default:
			break;
		}

	}

	/**
	 * Récupère les informations de fusion des cellules dans la sheet source
	 * pour les appliquer à la sheet destination... Récupère toutes les zones
	 * merged dans la sheet source et regarde pour chacune d'elle si elle se
	 * trouve dans la current row que nous traitons. Si oui, retourne l'objet
	 * CellRangeAddress.
	 *
	 * @param sheet
	 *            the sheet containing the data.
	 * @param rowNum
	 *            the num of the row to copy.
	 * @param cellNum
	 *            the num of the cell to copy.
	 * @return the CellRangeAddress created.
	 */
	public static CellRangeAddress getMergedRegion(HSSFSheet sheet, int rowNum,
			short cellNum) {
		for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
			CellRangeAddress merged = sheet.getMergedRegion(i);
			if (merged.isInRange(rowNum, cellNum)) {
				return merged;
			}
		}
		return null;
	}

	/**
	 * Check that the merged region has been created in the destination sheet.
	 *
	 * @param newMergedRegion
	 *            the merged region to copy or not in the destination sheet.
	 * @param mergedRegions
	 *            the list containing all the merged region.
	 * @return true if the merged region is already in the list or not.
	 */
	private static boolean isNewMergedRegion(
			CellRangeAddressWrapper newMergedRegion,
			Set<CellRangeAddressWrapper> mergedRegions) {
		return !mergedRegions.contains(newMergedRegion);
	}

}