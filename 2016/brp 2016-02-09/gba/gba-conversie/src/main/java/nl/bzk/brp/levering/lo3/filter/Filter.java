/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.filter;

import java.util.List;

import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.ist.Stapel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Lo3 Filter.
 */
public interface Filter {

    /**
     * Filter.
     *
     * @param persoon persoon
     * @param istStapels ist stapels
     * @param administratieveHandeling administratieveHandeling
     * @param categorieen categorien
     * @param lo3Filterrubrieken te filteren rubrieken
     * @return gefilterde categorieen
     */
    List<Lo3CategorieWaarde> filter(
        PersoonHisVolledig persoon,
        List<Stapel> istStapels,
        AdministratieveHandelingModel administratieveHandeling,
        final List<Lo3CategorieWaarde> categorieen,
        final List<String> lo3Filterrubrieken);

    /**
     * Bepaal op basis van de gefilterde rubrieken of de levering geskipped moet worden (omdat er geen rubrieken over
     * zijn na het filteren).
     *
     * @param persoon persoon
     * @param categorieen gefilterde categorieen
     * @return false, als de levering geskipped moet worden, anders true
     */
    boolean leveringUitvoeren(final PersoonHisVolledig persoon, final List<Lo3CategorieWaarde> categorieen);

}
