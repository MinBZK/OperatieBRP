/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import nl.bzk.brp.bijhouding.bericht.annotation.XmlTransient;

/**
 * Bevat state voor elke implementatie van {@link Element}, zoals het optionele {@link BijhoudingVerzoekBericht} waar
 * een element deel vanuit kan maken.
 */
public abstract class AbstractElement implements Element {

    @XmlTransient
    private BijhoudingVerzoekBericht verzoekBericht;

    @Override
    public final BijhoudingVerzoekBericht getVerzoekBericht() {
        return verzoekBericht;
    }

    @Override
    public final void setVerzoekBericht(final BijhoudingVerzoekBericht verzoekBericht) {
        this.verzoekBericht = verzoekBericht;
    }
}
