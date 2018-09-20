/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.historie;

import nl.bzk.brp.model.groep.operationeel.historisch.PersoonGeslachtsnaamComponentHisModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonGeslachtsnaamComponentModel;
import org.springframework.stereotype.Repository;


/**
 * Repository voor de {@link PersoonGeslachtsnaamComponentModel} class.
 */
@Repository("persoonGeslachtsnaamcomponentHistorieRepository")
public class PersoonGeslachtsnaamcomponentHistorieJpaRepository
    extends AbstractGroepHistorieRepository<PersoonGeslachtsnaamComponentModel, PersoonGeslachtsnaamComponentHisModel>
{

    @Override
    protected PersoonGeslachtsnaamComponentHisModel maakNieuwHistorieRecord(
            final PersoonGeslachtsnaamComponentModel objectType)
    {
        return new PersoonGeslachtsnaamComponentHisModel(objectType.getGegevens(), objectType);
    }

    @Override
    protected String padNaarALaagEntiteitInCLaagEntiteit() {
        return "persoonGeslachtsnaamcomponent";
    }

    @Override
    protected Class<PersoonGeslachtsnaamComponentHisModel> getCLaagDomainClass() {
        return PersoonGeslachtsnaamComponentHisModel.class;
    }

}
