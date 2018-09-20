/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.operationeel.historisch;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.groep.operationeel.AbstractPersoonSamengesteldeNaamGroep;
import nl.bzk.brp.model.groep.operationeel.historisch.basis.AbstractPersoonSamengesteldeNaamHisModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;


/** User implementatie van PersoonSamengesteldeNaamHisMdl. */
@Entity
@Table(schema = "kern", name = "his_perssamengesteldenaam")
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public class PersoonSamengesteldeNaamHisModel extends AbstractPersoonSamengesteldeNaamHisModel {

    /** Default constructor tbv hibernate. */
    @SuppressWarnings("unused")
    private PersoonSamengesteldeNaamHisModel() {
        super();
    }

    /**
     * .
     *
     * @param persoonSamengesteldeNaamGroep .
     * @param persoonModel .
     */
    public PersoonSamengesteldeNaamHisModel(
        final AbstractPersoonSamengesteldeNaamGroep persoonSamengesteldeNaamGroep,
        final PersoonModel persoonModel)
    {
        super(persoonSamengesteldeNaamGroep, persoonModel);
    }

    @Override
    public PersoonSamengesteldeNaamHisModel kopieer() {
        return new PersoonSamengesteldeNaamHisModel(this, getPersoon());
    }
}
