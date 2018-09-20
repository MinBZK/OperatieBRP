/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.bericht.basis;

import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.groep.logisch.basis.PersoonBijhoudingsVerantwoordelijkheidGroepBasis;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Verantwoordelijke;

/**
 * Implementatie voor groep bijhoudingsverantwoordelijkheid.
 */
@SuppressWarnings("serial")
public abstract class AbstractPersoonBijhoudingsVerantwoordelijkheidGroepBericht extends AbstractGroepBericht
        implements PersoonBijhoudingsVerantwoordelijkheidGroepBasis
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
