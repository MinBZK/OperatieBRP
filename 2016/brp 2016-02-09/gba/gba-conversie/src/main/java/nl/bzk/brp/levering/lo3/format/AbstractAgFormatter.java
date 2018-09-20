/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.format;

import java.util.List;

import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Header;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Basis implementatie voor een lo3 vulbericht.
 */
public abstract class AbstractAgFormatter extends AbstractFormatter {

    private static final Lo3Header HEADER =
            new Lo3Header(Lo3HeaderVeld.RANDOM_KEY, Lo3HeaderVeld.BERICHTNUMMER, Lo3HeaderVeld.STATUS, Lo3HeaderVeld.DATUM);

    /**
     * Geef het AgXX bericht type.
     *
     * @return bericht type.
     */
    protected abstract String getBerichtType();

    /**
     * Format de AgXX header.
     *
     * @param persoon persoon (ongefilterd)
     * @param categorieen categorieen (ongefiltered)
     * @return header
     */
    @Override
    protected final String formatHeader(final PersoonHisVolledig persoon, final List<Lo3CategorieWaarde> categorieen) {
        String status = FormatterUtil.geefElementWaarde(categorieen, Lo3CategorieEnum.CATEGORIE_07, Lo3ElementEnum.ELEMENT_6720);

        final String datum;
        if ("E".equals(status) || "M".equals(status) || "O".equals(status) || "R".equals(status)) {
            datum = FormatterUtil.geefElementWaarde(categorieen, Lo3CategorieEnum.CATEGORIE_07, Lo3ElementEnum.ELEMENT_6710);
        } else {
            status = "A";
            datum = "";
        }
        final String[] headers = new String[] {null, getBerichtType(), status, datum };
        return HEADER.formatHeaders(headers);
    }
}
