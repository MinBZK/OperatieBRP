/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Embeddable;
import nl.bzk.brp.model.logisch.kern.OnderzoekAfgeleidAdministratiefGroep;


/**
 *
 *
 */
@Embeddable
public class OnderzoekAfgeleidAdministratiefGroepModel extends AbstractOnderzoekAfgeleidAdministratiefGroepModel
    implements OnderzoekAfgeleidAdministratiefGroep
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected OnderzoekAfgeleidAdministratiefGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param administratieveHandeling administratieveHandeling van Afgeleid administratief.
     */
    public OnderzoekAfgeleidAdministratiefGroepModel(final AdministratieveHandelingModel administratieveHandeling) {
        super(administratieveHandeling);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param onderzoekAfgeleidAdministratiefGroep
     *         te kopieren groep.
     */
    public OnderzoekAfgeleidAdministratiefGroepModel(
        final OnderzoekAfgeleidAdministratiefGroep onderzoekAfgeleidAdministratiefGroep)
    {

    }

}
