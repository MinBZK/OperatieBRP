/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.objecttype.logisch.PersoonIndicatie;
import nl.bzk.brp.model.objecttype.logisch.basis.PersoonIndicatiesBasis;
import nl.bzk.brp.model.objecttype.operationeel.basis.AbstractPersoonIndicatieModel;


/**
 *
 */
@Entity
@Table(schema = "Kern", name = "PersIndicatie")
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public class PersoonIndicatieModel extends AbstractPersoonIndicatieModel implements PersoonIndicatie {

    /**
     * Copy constructor. Om een model object te construeren uit een web object.
     *
     * @param persInd Object type dat gekopieerd dient te worden.
     */
    public PersoonIndicatieModel(final PersoonIndicatiesBasis persInd) {
        super(persInd);
    }

    /**
     * Standaard (lege) constructor.
     */
    private PersoonIndicatieModel() {
    }
}
