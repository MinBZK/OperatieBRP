/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.format;

import java.util.List;

import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Abstracte formatter.
 */
public abstract class AbstractFormatter implements Formatter {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public final String maakPlatteTekst(
        final PersoonHisVolledig persoon,
        final List<Lo3CategorieWaarde> categorieen,
        final List<Lo3CategorieWaarde> categorieenGefilterd)
    {
        return formatHeader(persoon, categorieen) + formatInhoud(categorieenGefilterd);
    }

    /**
     * Format de header van het Lo3 bericht.
     *
     * @param persoon persoon (ongefilterd)
     * @param categorieen categorieen (ongefiltered)
     * @return header
     */
    protected abstract String formatHeader(final PersoonHisVolledig persoon, final List<Lo3CategorieWaarde> categorieen);

    /**
     * Format de inhoud van het Lo3 bericht.
     *
     * @param categorieen categorien (gefilterd)
     * @return inhoud
     */
    protected final String formatInhoud(final List<Lo3CategorieWaarde> categorieen) {
        LOGGER.debug("Format inhoud: {} ", categorieen);
        return Lo3Inhoud.formatInhoud(categorieen);
    }
}
