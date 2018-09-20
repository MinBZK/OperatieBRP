/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.objecttype.operationeel;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.copy.model.objecttype.logisch.Betrokkenheid;
import nl.bzk.copy.model.objecttype.logisch.basis.BetrokkenheidBasis;
import nl.bzk.copy.model.objecttype.operationeel.basis.AbstractBetrokkenheidModel;

/**
 *
 */
// TODO: rename de entity naam terug naar de echte
@Entity
@Table(schema = "Kern", name = "Betr")
@SuppressWarnings("serial")
public class BetrokkenheidModel extends AbstractBetrokkenheidModel implements Betrokkenheid {

    /**
     * Copy constructor. Om een model object te construeren uit een web object.
     *
     * @param betr    Object type dat gekopieerd dient te worden.
     * @param pers    Betrokkene.
     * @param relatie de relatie
     */
    public BetrokkenheidModel(final BetrokkenheidBasis betr, final PersoonModel pers, final RelatieModel relatie) {
        super(betr, pers, relatie);
    }

    /**
     * default cons.
     */
    public BetrokkenheidModel() {
    }

}
