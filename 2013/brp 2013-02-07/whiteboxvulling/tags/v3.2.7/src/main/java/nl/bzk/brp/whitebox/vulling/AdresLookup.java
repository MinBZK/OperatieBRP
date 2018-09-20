/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.whitebox.vulling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdresLookup {

    private final static Logger   log              = LoggerFactory.getLogger(AdresLookup.class);

    static Map<Long, ArrayList<AdresDTO>> map = new HashMap<Long, ArrayList<AdresDTO>>();

    public static void initialize(final Sheet sheet) {
	for (int i = 2; i < sheet.getRows(); i++) {
	    final Cell[] row = sheet.getRow(i);

	    final long voorGemCode = Long.parseLong(row[9].getContents());

	    if (!map.containsKey(voorGemCode)) {
		map.put(voorGemCode, new ArrayList<AdresDTO>());
	    }

	    final ArrayList<AdresDTO> adresList = map.get(voorGemCode);

	    final AdresDTO adresDTO = new AdresDTO();
	    adresDTO.gemCode = row[2].getContents();
	    adresDTO.woonplaatsCode = row[3].getContents();
	    adresDTO.straatKort = row[4].getContents();
	    adresDTO.straatLang = row[5].getContents();
	    adresDTO.postcode = row[6].getContents();
	    adresDTO.huisNr = row[7].getContents();

	    adresList.add(adresDTO);
	}
    }

    public static class AdresDTO {

	public String gemCode, straatKort, straatLang, huisNr, postcode, woonplaatsCode;


	@Override
	public String toString() {
	    return "gemCode="+gemCode+", straatKort="+straatKort+", straatLang="+straatLang+", huisNr="+huisNr+", postcode="+postcode+"";
	}
    }

    public static AdresDTO geefAdresDetails(final long voorGemeenteCode, final int addressId) {
	return map.get(voorGemeenteCode).get(addressId);
    }

}
