/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.AbstractDelegatePersoonNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteit;

/**
 * Deze decorator class voegt bijhoudingsfunctionaliteit toe aan de {@link PersoonNationaliteit} entiteit.
 */
public final class BijhoudingPersoonNationaliteit extends AbstractDelegatePersoonNationaliteit implements BijhoudingEntiteit {
    private static final long serialVersionUID = 1;

    /**
     * Initialiseert AbstractDelegateNationaliteit.
     * @param delegate de delegate waar de getter en setter methodes naar moeten delegeren, mag niet null zijn
     */
    private BijhoudingPersoonNationaliteit(final PersoonNationaliteit delegate) {
        super(delegate);
    }

    /**
     * Maakt een nieuwe {@link BijhoudingPersoonNationaliteit}.
     * @param delegate de nationaliteit die moet worden uigebreid met bijhoudingsfunctionaliteit
     * @return een nationaliteit met bijhoudingsfunctionaliteit
     */
    public static BijhoudingPersoonNationaliteit decorate(final PersoonNationaliteit delegate) {
        if (delegate == null) {
            return null;
        }
        return new BijhoudingPersoonNationaliteit(delegate);
    }


}
