/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.operationeel.actueel.basis;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import nl.bzk.brp.model.basis.Onderzoekbaar;
import nl.bzk.brp.model.groep.logisch.basis.PersoonImmigratieGroepBasis;
import nl.bzk.brp.model.groep.operationeel.AbstractPersoonImmigratieGroep;


/**
 * Implementatie voor groep persoon inschrijving.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonImmigratieActGroepModel extends AbstractPersoonImmigratieGroep implements
        Onderzoekbaar
{
    @Transient
    private boolean inOnderzoek;

    /**
     * Copy constructor voor groep.
     *
     * @param groep De te kopieren groep.
     */
    protected AbstractPersoonImmigratieActGroepModel(final PersoonImmigratieGroepBasis groep) {
        super(groep);
    }

    /**
     * .
     */
    protected AbstractPersoonImmigratieActGroepModel() {
        super();
    }

    @Override
    public boolean isInOnderzoek() {
        return inOnderzoek;
    }

}
