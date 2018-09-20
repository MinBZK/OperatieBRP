/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.groep.operationeel;

import javax.persistence.*;

import nl.bzk.copy.model.basis.AbstractGroep;
import nl.bzk.copy.model.groep.logisch.basis.PersoonBijhoudingsverantwoordelijkheidGroepBasis;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Verantwoordelijke;


/**
 * Implementatie voor groep bijhoudingsverantwoordelijkheid.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonBijhoudingsverantwoordelijkheidGroep extends AbstractGroep implements
        PersoonBijhoudingsverantwoordelijkheidGroepBasis
{

    /**
     * Standaard constructor die direct de waarde zet op basis van opgegeven groep.
     *
     * @param bijhoudingsverantwoordelijkheidGroepBasis
     *         de groep met daarin de te zetten waarde.
     */
    protected AbstractPersoonBijhoudingsverantwoordelijkheidGroep(
            final PersoonBijhoudingsverantwoordelijkheidGroepBasis bijhoudingsverantwoordelijkheidGroepBasis)
    {
        verantwoordelijke = bijhoudingsverantwoordelijkheidGroepBasis.getVerantwoordelijke();
    }

    /**
     * Standaard lege (protected) constructor.
     */
    protected AbstractPersoonBijhoudingsverantwoordelijkheidGroep() {
    }

    @Column(name = "verantwoordelijke")
    @Enumerated(value = EnumType.ORDINAL)
    private Verantwoordelijke verantwoordelijke;

    @Override
    public Verantwoordelijke getVerantwoordelijke() {
        return verantwoordelijke;
    }

    public void setVerantwoordelijke(final Verantwoordelijke verantwoordelijke) {
        this.verantwoordelijke = verantwoordelijke;
    }
}
