/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.verwerker;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.DeltaRootEntiteit;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VergelijkerResultaat;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.util.DeltaRootEntiteitModus;

/**
 * Verwerker van de gevonden verschillen tussen de bestaande en de nieuwe versie van een {@link DeltaRootEntiteit}. Deze
 * class is stateful en moet voor elke {@link DeltaRootEntiteit} opnieuw worden geconstrueerd.
 */
public final class DeltaRootEntiteitVerschilVerwerker extends AbstractDeltaVerschilVerwerker {

    /**
     * Maakt een verschil verwerker en zet de modus waar deze verwerker in werkt.
     * 
     * @param modus
     *            een {@link DeltaRootEntiteitModus} waarmee aangegeven wordt voor welke {@link DeltaRootEntiteit} deze
     *            verwerker werkt.
     */
    public DeltaRootEntiteitVerschilVerwerker(final DeltaRootEntiteitModus modus) {
        super(modus);
    }

    /**
     * Verwerk de wijzigingen op de bestaande versie van de {@link DeltaRootEntiteit}.
     *
     * @param verschillen
     *            de lijst met verschillen
     * @param deltaRootEntiteit
     *            de bestaande entiteit waar de verschillen in wordt verwerkt
     * @param administratieveHandeling
     *            de administratieve handeling die aan deze wijzigingen is gekoppeld.
     */
    public void verwerkWijzigingen(
        final VergelijkerResultaat verschillen,
        final DeltaRootEntiteit deltaRootEntiteit,
        final AdministratieveHandeling administratieveHandeling)
    {
        try {
            verwerkWijzigingen(verschillen, deltaRootEntiteit, null, null, administratieveHandeling);
        } catch (final ReflectiveOperationException e) {
            throw new IllegalStateException(e);
        }
    }
}
