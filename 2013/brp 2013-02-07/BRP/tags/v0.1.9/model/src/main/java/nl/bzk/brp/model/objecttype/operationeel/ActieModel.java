/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel;

import java.util.List;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.logisch.basis.ActieBasis;
import nl.bzk.brp.model.objecttype.operationeel.basis.AbstractActieModel;

/**
 *
 */
@Entity
@Table(schema = "kern", name = "actie")
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public class ActieModel extends AbstractActieModel implements Actie {

    /**
     * Copy constructor. Om een model object te construeren uit een web object.
     *
     * @param actie Object type dat gekopieerd dient te worden.
     */
    public ActieModel(final ActieBasis actie) {
        super(actie);
    }

    /** Standaard (lege) constructor. */
    protected ActieModel() {
    }

    @Override
    public List<RootObject> getRootObjecten() {
        throw new UnsupportedOperationException("Model Actie kent geen rootobject functionaliteit");
    }
}
