/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie;

import java.util.List;

import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.ist.Stapel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Converteerder.
 */
public interface Converteerder {

    /**
     * Converteer de BRP gegevens naar LO3 gegevens.
     *
     * @param persoon persoon
     * @param istStapels ist stapels
     * @param administratieveHandeling administratieveHandeling
     * @param conversieCache cache
     * @return LO3 categorieen
     */
    List<Lo3CategorieWaarde> converteer(
        PersoonHisVolledig persoon,
        List<Stapel> istStapels,
        AdministratieveHandelingModel administratieveHandeling,
        ConversieCache conversieCache);

}
