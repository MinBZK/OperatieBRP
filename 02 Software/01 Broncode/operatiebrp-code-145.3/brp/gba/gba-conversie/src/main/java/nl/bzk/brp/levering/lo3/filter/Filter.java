/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.filter;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.levering.lo3.conversie.IdentificatienummerMutatie;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Lo3 Filter.
 */
public interface Filter {

    /**
     * Filter.
     * @param persoon persoon
     * @param istStapels ist stapels
     * @param administratieveHandeling administratieveHandeling
     * @param identificatienummerMutatieResultaat identificatienummer mutatie resultaat
     * @param categorieen categorien
     * @param lo3Filterrubrieken te filteren rubrieken
     * @return gefilterde categorieen
     */
    List<Lo3CategorieWaarde> filter(
            Persoonslijst persoon,
            List<Stapel> istStapels,
            AdministratieveHandeling administratieveHandeling,
            IdentificatienummerMutatie identificatienummerMutatieResultaat,
            final List<Lo3CategorieWaarde> categorieen,
            final List<String> lo3Filterrubrieken);

    /**
     * Bepaal op basis van de gefilterde rubrieken of de levering geskipped moet worden (omdat er geen rubrieken over zijn na het filteren).
     * @param persoon persoon
     * @param categorieen gefilterde categorieen
     * @return false, als de levering geskipped moet worden, anders true
     */
    boolean leveringUitvoeren(final Persoonslijst persoon, final List<Lo3CategorieWaarde> categorieen);

}
