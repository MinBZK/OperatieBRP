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
import nl.bzk.copy.model.groep.logisch.basis.PersoonGeslachtsnaamcomponentStandaardGroepBasis;
import nl.bzk.copy.model.groep.operationeel.AbstractPersoonGeslachtsnaamcomponentStandaardGroep;


/**
 * Implementatie standaard groep object type Persoon Geslachtsnaam component.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonGeslachtsnaamcomponentStandaardActGroepModel extends
        AbstractPersoonGeslachtsnaamcomponentStandaardGroep implements Onderzoekbaar
{

    @Transient
    private boolean inOnderzoek;

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractPersoonGeslachtsnaamcomponentStandaardActGroepModel() {
        super();
    }

    /**
     * .
     *
     * @param persoonGeslachtsnaamcomponentStandaardGroepBasis
     *         PersoonGeslachtsnaamcomponentStandaardGroepBasis
     */
    protected AbstractPersoonGeslachtsnaamcomponentStandaardActGroepModel(
            final PersoonGeslachtsnaamcomponentStandaardGroepBasis persoonGeslachtsnaamcomponentStandaardGroepBasis)
    {
        super(persoonGeslachtsnaamcomponentStandaardGroepBasis);
    }

    @Override
    public boolean isInOnderzoek() {
        return inOnderzoek;
    }

}
