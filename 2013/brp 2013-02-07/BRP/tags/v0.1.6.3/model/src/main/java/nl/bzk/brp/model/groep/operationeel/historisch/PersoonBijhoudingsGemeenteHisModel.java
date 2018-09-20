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

import nl.bzk.brp.model.groep.operationeel.AbstractPersoonBijhoudingsGemeenteGroep;
import nl.bzk.brp.model.groep.operationeel.historisch.basis.AbstractPersoonBijhoudingsGemeenteHisModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;


/** User implementatie van PersoonBijhoudingsGemeenteHisMdl. */
@Entity
@Table(schema = "kern", name = "his_persbijhgem")
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public class PersoonBijhoudingsGemeenteHisModel extends AbstractPersoonBijhoudingsGemeenteHisModel {

    public PersoonBijhoudingsGemeenteHisModel() {
    }

    /**
     * Standaard constructor.
     *
     * @param persoonBijhoudingsGemeenteGroep bijhoudingsgemeente groep
     * @param persoon persoon waartoe groep behoord
     */
    public PersoonBijhoudingsGemeenteHisModel(
        final AbstractPersoonBijhoudingsGemeenteGroep persoonBijhoudingsGemeenteGroep,
        final PersoonModel persoon)
    {
        super(persoonBijhoudingsGemeenteGroep, persoon);
    }

    @Override
    public PersoonBijhoudingsGemeenteHisModel kopieer() {
        return new PersoonBijhoudingsGemeenteHisModel(this, getPersoon());
    }
}
