/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import nl.bzk.brp.model.objecttype.statisch.StatusHistorie;

/**
 * Basis klasse voor alle groepen.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractGroep implements Groep, Onderzoekbaar {

    /**
     *
     */
    private static final long serialVersionUID = 1866357555380558972L;

    @Transient
    private boolean inOnderzoek;

    @Transient
    private StatusHistorie statusHistorie;

    @Override
    public boolean isInOnderzoek() {
        return inOnderzoek;
    }

    @Override
    public StatusHistorie getStatusHistorie() {
        return statusHistorie;
    }

}
