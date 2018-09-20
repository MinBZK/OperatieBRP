/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.operationeel;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.groep.logisch.basis.PersoonBijhoudingsVerantwoordelijkheidGroepBasis;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Verantwoordelijke;


/**
 * Implementatie voor groep bijhoudingsverantwoordelijkheid.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonBijhoudingsVerantwoordelijkheidGroep extends AbstractGroep implements
        PersoonBijhoudingsVerantwoordelijkheidGroepBasis
{

    protected AbstractPersoonBijhoudingsVerantwoordelijkheidGroep(
            final PersoonBijhoudingsVerantwoordelijkheidGroepBasis bijhoudingsVerantwoordelijkheidGroepBasis)
    {
        verantwoordelijke = bijhoudingsVerantwoordelijkheidGroepBasis.getVerantwoordelijke();
    }

    protected AbstractPersoonBijhoudingsVerantwoordelijkheidGroep() {
    }

    @Column(name = "verantwoordelijke")
    @Enumerated(value = EnumType.ORDINAL)
    private Verantwoordelijke verantwoordelijke;

    @Override
    public Verantwoordelijke getVerantwoordelijke() {
        return verantwoordelijke;
    }
}
