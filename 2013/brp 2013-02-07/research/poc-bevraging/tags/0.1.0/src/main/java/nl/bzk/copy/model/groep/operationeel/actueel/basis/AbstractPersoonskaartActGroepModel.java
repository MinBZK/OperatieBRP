/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.groep.operationeel.actueel.basis;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import nl.bzk.copy.model.basis.Onderzoekbaar;
import nl.bzk.copy.model.groep.logisch.basis.PersoonskaartGroepBasis;
import nl.bzk.copy.model.groep.operationeel.AbstractPersoonskaartGroep;


/**
 * Implementatie voor groep persoon inschrijving.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonskaartActGroepModel extends AbstractPersoonskaartGroep implements
        Onderzoekbaar
{
    @Transient
    private boolean inOnderzoek;

    /**
     * Copy constructor voor groep.
     *
     * @param groep De te kopieren groep.
     */
    protected AbstractPersoonskaartActGroepModel(final PersoonskaartGroepBasis groep) {
        super(groep);
    }

    /**
     * .
     */
    protected AbstractPersoonskaartActGroepModel() {
        super();
    }

    @Override
    public boolean isInOnderzoek() {
        return inOnderzoek;
    }

}
