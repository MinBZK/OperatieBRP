/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.historie;

import nl.bzk.brp.model.groep.operationeel.historisch.PersoonGeslachtsnaamcomponentHisModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonGeslachtsnaamcomponentModel;
import org.springframework.stereotype.Repository;


/**
 * Repository voor de {@link nl.bzk.brp.model.objecttype.operationeel.PersoonGeslachtsnaamcomponentModel} class.
 */
@Repository("persoonGeslachtsnaamcomponentHistorieRepository")
public class PersoonGeslachtsnaamcomponentHistorieJpaRepository
    extends AbstractGroepHistorieRepository<PersoonGeslachtsnaamcomponentModel, PersoonGeslachtsnaamcomponentHisModel>
{

    @Override
    protected PersoonGeslachtsnaamcomponentHisModel maakNieuwHistorieRecord(
            final PersoonGeslachtsnaamcomponentModel objectType)
    {
        return new PersoonGeslachtsnaamcomponentHisModel(objectType.getGegevens(), objectType);
    }

    @Override
    protected String padNaarALaagEntiteitInCLaagEntiteit() {
        return "persoonGeslachtsnaamcomponent";
    }

    @Override
    protected Class<PersoonGeslachtsnaamcomponentHisModel> getCLaagDomainClass() {
        return PersoonGeslachtsnaamcomponentHisModel.class;
    }

}
