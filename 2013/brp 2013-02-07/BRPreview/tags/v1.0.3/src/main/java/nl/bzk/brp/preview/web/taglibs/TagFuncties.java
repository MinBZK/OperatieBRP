/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.web.taglibs;


public class TagFuncties {

    public static String formatteerDatum(final String decimalen) {
        String resultaat;
        if (decimalen == null || decimalen.length() == 0) {
            resultaat = null;
        } else {
            int datum = Integer.parseInt(decimalen);
            int dag = datum % 100;
            int maand = (datum - dag) / 100 % 100;
            int jaar = datum / 10000;
            resultaat = String.format("%02d-%02d-%04d", dag, maand, jaar);
        }
        return resultaat;
    }

}
