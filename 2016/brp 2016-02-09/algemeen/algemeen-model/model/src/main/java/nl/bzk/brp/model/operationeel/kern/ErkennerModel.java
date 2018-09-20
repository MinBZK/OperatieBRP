/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import nl.bzk.brp.model.logisch.kern.Erkenner;


/**
 * De betrokkenheid van een persoon in de rol van erkenner in een erkenning ongeboren vrucht.
 */
@Entity
@DiscriminatorValue(value = "4")
public class ErkennerModel extends AbstractErkennerModel implements Erkenner {

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected ErkennerModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param relatie relatie van Erkenner.
     * @param persoon persoon van Erkenner.
     */
    public ErkennerModel(final RelatieModel relatie, final PersoonModel persoon) {
        super(relatie, persoon);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param erkenner Te kopieren object type.
     * @param relatie  Bijbehorende Relatie.
     * @param persoon  Bijbehorende Persoon.
     */
    public ErkennerModel(final Erkenner erkenner, final RelatieModel relatie, final PersoonModel persoon) {
        super(erkenner, relatie, persoon);
    }

}
