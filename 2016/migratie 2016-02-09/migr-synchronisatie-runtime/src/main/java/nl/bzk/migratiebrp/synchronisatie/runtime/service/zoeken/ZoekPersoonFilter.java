/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.zoeken;

import java.util.List;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;

/**
 * Filter voor additionele zoek criteria.
 */
public interface ZoekPersoonFilter {

    /**
     * Filter de gegeven personen op de additionele zoekcriteria.
     *
     * Indien meer dan 1 persoon voldoet aan de additionele zoekcriteria stopt de verwerking. Er zullen dus ten hoogste
     * 2 gevonden personen worden teruggegeven.
     *
     * @param personen
     *            personen
     * @param additioneleZoekCriteria
     *            additionele zoekcriteria
     * @return lijst van gevonden personen
     * @throws BerichtSyntaxException
     *             bij parse fouten in de additionele zoek criteria
     */
    List<GevondenPersoon> filter(List<Persoon> personen, String additioneleZoekCriteria) throws BerichtSyntaxException;

}
