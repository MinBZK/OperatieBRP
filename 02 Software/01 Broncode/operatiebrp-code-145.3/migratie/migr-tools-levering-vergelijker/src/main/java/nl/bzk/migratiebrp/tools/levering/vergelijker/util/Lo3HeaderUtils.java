/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.levering.vergelijker.util;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Header;
import nl.bzk.migratiebrp.bericht.model.lo3.factory.Lo3BerichtFactory;
import org.apache.commons.lang3.StringUtils;

/**
 * Util klasse voor functies op de kop van een Lo3Bericht.
 */
public final class Lo3HeaderUtils {

    private static final Lo3BerichtFactory LO3_BERICHT_FACTORY = new Lo3BerichtFactory();

    /**
     * Niet instantieerbaar.
     */
    protected Lo3HeaderUtils() {

    }

    /**
     * Haal de headers uit een string-representatie van een LO3-bericht.
     * @param berichtAlsString Het bericht als string.
     * @return Array van header-waarden.
     */
    public static String[] haalHeadersUitBericht(final String berichtAlsString) {
        final Lo3Bericht lo3Bericht = LO3_BERICHT_FACTORY.getBericht(berichtAlsString);
        final Lo3Header header = new Lo3Header(lo3Bericht.getHeader().getHeaderVelden());

        try {
            return header.parseHeaders(berichtAlsString);
        } catch (final BerichtSyntaxException bse) {
            return new String[]{};
        }
    }

    /**
     * Vergelijkt de twee arrays met headers inhoudelijk en op lengte.
     * @param headersGbaBericht Headers van het GBA-V bericht.
     * @param headersBrpBericht Headers van het BRP bericht.
     * @return String met daarin de nummers van de afwijkend/ontbrekende koppen.
     */
    public static String vergelijkHeaders(final String[] headersGbaBericht, final String[] headersBrpBericht) {

        final List<Integer> afwijkendeKoppen = new ArrayList<>();

        if (headersGbaBericht.length == headersBrpBericht.length) {
            vergelijkHeaderLijsten(headersGbaBericht, headersBrpBericht, afwijkendeKoppen);
        } else {
            final String[] korteHeaderLijst = headersGbaBericht.length < headersBrpBericht.length ? headersGbaBericht : headersBrpBericht;
            final String[] langeHeaderLijst = headersGbaBericht.length < headersBrpBericht.length ? headersBrpBericht : headersGbaBericht;
            int index = vergelijkHeaderLijsten(langeHeaderLijst, korteHeaderLijst, afwijkendeKoppen);

            while (index < langeHeaderLijst.length) {
                afwijkendeKoppen.add(index);
                index++;
            }
        }

        return StringUtils.join(afwijkendeKoppen, ",");

    }

    private static int vergelijkHeaderLijsten(final String[] headersGbaBericht, final String[] headersBrpBericht, final List<Integer> afwijkendeKoppen) {
        int index = 1;
        for (final String huidigeHeader : headersBrpBericht) {
            if (!huidigeHeader.equals(headersGbaBericht[index - 1])) {
                afwijkendeKoppen.add(index);
            }
            index++;
        }
        return index;
    }

}
