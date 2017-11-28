/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.format;

import java.util.List;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.levering.lo3.util.PersoonUtil;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Header;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Basis implementatie voor een lo3 mutatiebericht.
 */
public abstract class AbstractGvFormatter extends AbstractFormatter {

    private static final Lo3Header HEADER = new Lo3Header(Lo3HeaderVeld.RANDOM_KEY, Lo3HeaderVeld.BERICHTNUMMER, Lo3HeaderVeld.A_NUMMER);

    /**
     * Geef het GvXX bericht type.
     * @return bericht type.
     */
    protected abstract String getBerichtType();

    /**
     * Format de GvXX header.
     * @param persoon persoon (ongefilterd)
     * @param categorieen categorieen (ongefiltered)
     * @return header
     */
    @Override
    protected final String formatHeader(final Persoonslijst persoon, final List<Lo3CategorieWaarde> categorieen) {
        final String anummer = PersoonUtil.getAnummer(persoon);

        final String[] headers = new String[]{null, getBerichtType(), anummer};
        return HEADER.formatHeaders(headers);
    }
}
