/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.format;

import java.util.List;

import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Uitgaand bericht 'formatter'.
 */
public interface Formatter {

    /**
     * Maak de platte tekst voor dit uitgaand bericht.
     *
     * @param persoon persoon (ongefilterd)
     * @param categorieen categorieen (ongefilterd)
     * @param categorieenGefilterd categorieen (gefilterd)
     * @return platte tekst
     */
    String maakPlatteTekst(final PersoonHisVolledig persoon, List<Lo3CategorieWaarde> categorieen, List<Lo3CategorieWaarde> categorieenGefilterd);
}
