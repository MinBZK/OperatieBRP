/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import nl.bzk.brp.model.logisch.kern.Naamgever;
import nl.bzk.brp.model.operationeel.kern.basis.AbstractNaamgeverModel;


/**
 * De betrokkenheid in de rol van Naamgever.
 *
 *
 *
 */
@Entity
@DiscriminatorValue(value = "6")
public class NaamgeverModel extends AbstractNaamgeverModel implements Naamgever {

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected NaamgeverModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param relatie relatie van Naamgever.
     * @param persoon persoon van Naamgever.
     */
    public NaamgeverModel(final RelatieModel relatie, final PersoonModel persoon) {
        super(relatie, persoon);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param naamgever Te kopieren object type.
     * @param relatie Bijbehorende Relatie.
     * @param persoon Bijbehorende Persoon.
     */
    public NaamgeverModel(final Naamgever naamgever, final RelatieModel relatie, final PersoonModel persoon) {
        super(naamgever, relatie, persoon);
    }

}
