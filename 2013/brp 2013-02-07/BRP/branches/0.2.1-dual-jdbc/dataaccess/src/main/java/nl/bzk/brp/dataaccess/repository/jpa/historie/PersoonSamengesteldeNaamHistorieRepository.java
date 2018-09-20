/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.historie;

import nl.bzk.brp.model.groep.operationeel.AbstractPersoonSamengesteldeNaamGroep;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonSamengesteldeNaamHisModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * JPA repository voor de tabel His_PersSamengesteldeNaam.
 */
@Repository
@Qualifier("persoonSamengesteldeNaamHistorieRepository")
public class PersoonSamengesteldeNaamHistorieRepository
    extends AbstractGroepHistorieRepository<PersoonModel, AbstractPersoonSamengesteldeNaamGroep,
        PersoonSamengesteldeNaamHisModel>
{

    @Override
    protected String padNaarALaagEntiteitInCLaagEntiteit() {
        return "persoon";
    }

    @Override
    protected Class<PersoonSamengesteldeNaamHisModel> getCLaagDomainClass() {
        return PersoonSamengesteldeNaamHisModel.class;
    }

    @Override
    protected PersoonSamengesteldeNaamHisModel maakNieuwHistorieRecord(
            final PersoonModel objectType, final AbstractPersoonSamengesteldeNaamGroep groep)
    {
        // dual gebruik, als groep is null, haal de groep uit de huidige ALaag
        if (groep == null) {
            return new PersoonSamengesteldeNaamHisModel(objectType.getSamengesteldeNaam(), objectType);
        } else {
            return new PersoonSamengesteldeNaamHisModel(groep, objectType);
        }
    }
}
