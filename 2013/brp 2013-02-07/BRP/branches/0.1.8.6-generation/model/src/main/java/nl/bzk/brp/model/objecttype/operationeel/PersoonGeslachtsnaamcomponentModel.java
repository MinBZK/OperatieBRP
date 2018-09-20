/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.objecttype.logisch.PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.objecttype.logisch.basis.PersoonGeslachtsnaamcomponentBasis;
import nl.bzk.brp.model.objecttype.operationeel.basis.AbstractPersoonGeslachtsnaamcomponentModel;


/**
 *
 */
@Entity
@Table(schema = "Kern", name = "PersGeslnaamcomp")
@SuppressWarnings("serial")
public class PersoonGeslachtsnaamcomponentModel extends AbstractPersoonGeslachtsnaamcomponentModel implements
        PersoonGeslachtsnaamcomponent
{

    /**
     * Copy constructor. Om een model object te construeren uit een web object.
     *
     * @param geslComp Object type dat gekopieerd dient te worden.
     * @param pers Persoon
     */
    public PersoonGeslachtsnaamcomponentModel(final PersoonGeslachtsnaamcomponentBasis geslComp, final PersoonModel pers)
    {
        super(geslComp, pers);
    }

    /**
     * Standaard (lege) constructor.
     */
    protected PersoonGeslachtsnaamcomponentModel() {
    }
}
