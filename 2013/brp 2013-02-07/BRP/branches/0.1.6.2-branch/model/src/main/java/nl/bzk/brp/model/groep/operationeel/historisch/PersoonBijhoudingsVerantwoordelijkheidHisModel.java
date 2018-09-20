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

import nl.bzk.brp.model.groep.operationeel.AbstractPersoonBijhoudingsVerantwoordelijkheidGroep;
import nl.bzk.brp.model.groep.operationeel.historisch.basis.AbstractPersoonBijhoudingsVerantwoordelijkheidHisModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;


/**
 * User implementatie van PersoonBijhoudingsVerantwoordelijkheidHisMdl.
 *
 */
@Entity
@Table(schema = "kern", name = "his_persbijhverantwoordelijk")
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public class PersoonBijhoudingsVerantwoordelijkheidHisModel extends AbstractPersoonBijhoudingsVerantwoordelijkheidHisModel {

    /**
     * Standaard constructor.
     *
     * @param persoonBijhoudingsVerantwoordelijkheidGroep de groep met daarin de bijhoudingsverantwoordelijke
     * @param persoonModel de persoon waartoe de groep behoord
     */
    public PersoonBijhoudingsVerantwoordelijkheidHisModel(
            final AbstractPersoonBijhoudingsVerantwoordelijkheidGroep persoonBijhoudingsVerantwoordelijkheidGroep,
            final PersoonModel persoonModel)
    {
        super(persoonBijhoudingsVerantwoordelijkheidGroep, persoonModel);
    }

    protected PersoonBijhoudingsVerantwoordelijkheidHisModel() {
    }

    @Override
    public PersoonBijhoudingsVerantwoordelijkheidHisModel kopieer() {
        return new PersoonBijhoudingsVerantwoordelijkheidHisModel(this, getPersoon());
    }
}
