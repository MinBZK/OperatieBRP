/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.utils;

import org.apache.poi.ss.util.CellRangeAddress;

public class CellRangeAddressWrapper implements
		Comparable<CellRangeAddressWrapper> {

	public CellRangeAddress range;

	/**
	 * @param theRange
	 *            the CellRangeAddress object to wrap.
	 */
	public CellRangeAddressWrapper(CellRangeAddress theRange) {
		this.range = theRange;
	}

	/**
	 * @param o
	 *            the object to compare.
	 * @return -1 the current instance is prior to the object in parameter, 0:
	 *         equal, 1: after...
	 */
	@Override
	public int compareTo(CellRangeAddressWrapper o) {

		if (range.getFirstColumn() < o.range.getFirstColumn()
				&& range.getFirstRow() < o.range.getFirstRow()) {
			return -1;
		} else if (range.getFirstColumn() == o.range.getFirstColumn()
				&& range.getFirstRow() == o.range.getFirstRow()) {
			return 0;
		} else {
			return 1;
		}

	}

}