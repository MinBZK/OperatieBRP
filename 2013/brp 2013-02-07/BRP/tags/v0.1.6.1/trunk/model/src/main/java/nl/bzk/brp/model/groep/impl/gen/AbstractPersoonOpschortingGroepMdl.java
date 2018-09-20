/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.impl.gen;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.groep.interfaces.gen.PersoonOpschortingGroepBasis;
import nl.bzk.brp.model.objecttype.statisch.RedenOpschorting;
import nl.bzk.brp.model.objecttype.statisch.StatusHistorie;


/**
 * .
 *
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonOpschortingGroepMdl extends AbstractGroep implements PersoonOpschortingGroepBasis {

    @Column(name = "rdnOpschortingBijhouding")
    @Enumerated(value = EnumType.STRING)
    private RedenOpschorting redenOpschorting;

    @Transient
    private StatusHistorie   statusHistorie;

    @Override
    public RedenOpschorting getRedenOpschorting() {
        return redenOpschorting;
    }

    @Override
    public StatusHistorie getStatusHistorie() {
        return statusHistorie;
    }

}
