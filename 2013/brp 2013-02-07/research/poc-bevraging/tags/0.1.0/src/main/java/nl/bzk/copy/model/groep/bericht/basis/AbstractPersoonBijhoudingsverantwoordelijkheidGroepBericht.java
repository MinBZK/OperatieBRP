/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.groep.bericht.basis;

import nl.bzk.copy.model.basis.AbstractGroepBericht;
import nl.bzk.copy.model.groep.logisch.basis.PersoonBijhoudingsverantwoordelijkheidGroepBasis;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Verantwoordelijke;

/**
 * Implementatie voor groep bijhoudingsverantwoordelijkheid.
 */
@SuppressWarnings("serial")
public abstract class AbstractPersoonBijhoudingsverantwoordelijkheidGroepBericht extends AbstractGroepBericht
        implements PersoonBijhoudingsverantwoordelijkheidGroepBasis
{

    private Verantwoordelijke verantwoordelijke;

    @Override
    public Verantwoordelijke getVerantwoordelijke() {
        return verantwoordelijke;
    }

    public void setVerantwoordelijke(final Verantwoordelijke verantwoordelijke) {
        this.verantwoordelijke = verantwoordelijke;
    }
}
