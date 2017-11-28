/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.format;

import java.util.List;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Header;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.springframework.stereotype.Component;

/**
 * Uitgaand Sv11 bericht: vul bericht na plaatsen afnemersindicatie.
 */
@Component
public final class Sv11Formatter extends AbstractFormatter {

    private static final Lo3Header HEADER = new Lo3Header(Lo3HeaderVeld.RANDOM_KEY, Lo3HeaderVeld.BERICHTNUMMER);

    @Override
    protected String formatHeader(final Persoonslijst persoon, final List<Lo3CategorieWaarde> categorieen) {
        final String[] headers = new String[]{null, "Sv11"};
        return HEADER.formatHeaders(headers);
    }
}
