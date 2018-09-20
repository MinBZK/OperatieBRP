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
import nl.bzk.brp.model.groep.logisch.basis.PersoonOpschortingGroepBasis;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenOpschorting;


/**
 * .
 *
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonOpschortingGroep extends AbstractGroep implements PersoonOpschortingGroepBasis {

    @Column(name = "rdnOpschortingBijhouding")
    @Enumerated(value = EnumType.ORDINAL)
    private RedenOpschorting redenOpschorting;

    @Override
    public RedenOpschorting getRedenOpschorting() {
        return redenOpschorting;
    }

    /**
     * .
     * @param groep PersoonOpschortingGroepBasis
     */
    protected AbstractPersoonOpschortingGroep(final PersoonOpschortingGroepBasis groep) {
        super(groep);
        redenOpschorting = groep.getRedenOpschorting();
    }

    /**
     * .
     */
    protected AbstractPersoonOpschortingGroep() {
    }
}
