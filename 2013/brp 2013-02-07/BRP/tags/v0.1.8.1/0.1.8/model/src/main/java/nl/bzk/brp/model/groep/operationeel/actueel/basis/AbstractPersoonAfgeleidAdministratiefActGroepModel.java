/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.operationeel.actueel.basis;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.groep.logisch.basis.PersoonAfgeleidAdministratiefGroepBasis;
import nl.bzk.brp.model.groep.operationeel.AbstractPersoonAfgeleidAdministratiefGroep;


/**
 * .
 *
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonAfgeleidAdministratiefActGroepModel extends
        AbstractPersoonAfgeleidAdministratiefGroep
{

    // Let op: Afgeleid Administratief groep HEEFT GEEN statushistorie
    // de vraag is of deze ook inOnderzoek kan stellen (de indicatie isIndoekzoek is de reden waarom andere groepen/
    // attributen in onderzoek gezet kan worden. Moet die dan zichzelf ook in onderzoek zetten?)

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractPersoonAfgeleidAdministratiefActGroepModel() {
        super();
    }

    /**
     * .
     *
     * @param persoonAfgeleidAdministratiefGroepBasis PersoonAfgeleidAdministratiefGroepBasis
     */
    protected AbstractPersoonAfgeleidAdministratiefActGroepModel(
            final PersoonAfgeleidAdministratiefGroepBasis persoonAfgeleidAdministratiefGroepBasis)
    {
        super(persoonAfgeleidAdministratiefGroepBasis);
    }

}
