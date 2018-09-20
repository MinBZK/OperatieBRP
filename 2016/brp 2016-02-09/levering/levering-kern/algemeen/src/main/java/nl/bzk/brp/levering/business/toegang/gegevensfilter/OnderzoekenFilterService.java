/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.toegang.gegevensfilter;

import java.util.List;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;

/**
 * Interface voor filtering van onderzoeken.
 */
public interface OnderzoekenFilterService {

    /**
     * Filter de onderzoek gegevens naar ontbrekende gegevens.
     *
     * @param persoonHisVolledigs de personen die gefilterd moeten worden
     */
    void filterOnderzoekGegevensNaarOntbrekendeGegevens(final List<PersoonHisVolledig> persoonHisVolledigs);
}
