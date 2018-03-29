/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.transformeer;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilGroep;

/**
 * Interface voor transformatie regels.
 */
public interface Transformatie {
    /**
     * Bepaal of deze transformatie van toepassing is voor de gegeven VerschilGroep.
     * @param verschillen de groep verschillen
     * @return true als deze transformatie van toepassing is op de verschillen
     */
    boolean accept(final VerschilGroep verschillen);

    /**
     * Voer de transformatie uit op de gegeven VerschilGroep.
     * @param verschillen de groep verschillen
     * @param actieVervalTbvLeveringMuts de actie die gebruikt kan worden voor het vullen van actieVervalTbvLeveringMuts in historie rijen
     * @param deltaBepalingContext de delta context
     * @return de nieuwe groep verschillen die de gegeven VerschilGroep vervangt.
     */
    VerschilGroep execute(final VerschilGroep verschillen, final BRPActie actieVervalTbvLeveringMuts, final DeltaBepalingContext deltaBepalingContext);

    /**
     * Geef de code voor deze transformatie voor logging.
     * @return de logging code voor deze transformatie
     */
    DeltaWijziging getDeltaWijziging();
}
