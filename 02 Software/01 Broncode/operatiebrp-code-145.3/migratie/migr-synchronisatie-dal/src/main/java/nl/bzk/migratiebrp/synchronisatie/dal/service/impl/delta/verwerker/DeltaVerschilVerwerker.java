/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.verwerker;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RootEntiteit;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VergelijkerResultaat;

/**
 * Interface voor de verschillende verwerkers m.b.t. persoonsgegevens. Onderzoek en Logging implementeren deze interface
 * niet.
 */
public interface DeltaVerschilVerwerker {

    /**
     * Verwerk de wijzigingen op {@link RootEntiteit}.
     * @param verschillen de lijst met verschillen
     * @param bestaandEntiteit de bestaande entiteit {@link RootEntiteit} waar de verschillen in wordt verwerkt
     * @param administratieveHandeling de administratieve handeling die aan deze wijzigingen is gekoppeld.
     */
    void verwerkWijzigingen(
            final VergelijkerResultaat verschillen,
            final RootEntiteit bestaandEntiteit,
            final AdministratieveHandeling administratieveHandeling);
}
