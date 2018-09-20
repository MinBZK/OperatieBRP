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

import nl.bzk.brp.model.groep.operationeel.AbstractPersoonEUVerkiezingenGroep;
import nl.bzk.brp.model.groep.operationeel.historisch.basis.AbstractPersoonEUVerkiezingenHisModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;


/**
 * User implementatie van BetrokkenheidOuderschapHisMdl.
 *
 */
@Entity
@Table(schema = "kern", name = "his_perseuverkiezingen")
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public class PersoonEUVerkiezingenHisModel extends AbstractPersoonEUVerkiezingenHisModel {

    /**
     * Default constructor tbv hibernate.
     */
    @SuppressWarnings("unused")
    private PersoonEUVerkiezingenHisModel() {
        super();
    }

    /**
     * .
     *
     * @param persoonEUVerkiezingenGroep PersoonEUVerkiezingenGroepBasis
     * @param persoonModel .
     */
    public PersoonEUVerkiezingenHisModel(
            final AbstractPersoonEUVerkiezingenGroep persoonEUVerkiezingenGroep,
            final PersoonModel persoonModel)
    {
        super(persoonEUVerkiezingenGroep, persoonModel);
    }

}
