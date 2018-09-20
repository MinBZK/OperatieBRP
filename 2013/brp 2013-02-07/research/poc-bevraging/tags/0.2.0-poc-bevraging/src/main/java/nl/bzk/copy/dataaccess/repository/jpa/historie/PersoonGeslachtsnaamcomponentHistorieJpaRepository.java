/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.dataaccess.repository.jpa.historie;

import nl.bzk.copy.dataaccess.repository.jpa.historie.AbstractGroepHistorieRepository;
import nl.bzk.copy.model.groep.operationeel.AbstractPersoonGeslachtsnaamcomponentStandaardGroep;
import nl.bzk.copy.model.groep.operationeel.historisch.PersoonGeslachtsnaamcomponentHisModel;
import nl.bzk.copy.model.objecttype.operationeel.PersoonGeslachtsnaamcomponentModel;
import org.springframework.stereotype.Repository;


/**
 * Repository voor de {@link nl.bzk.copy.model.objecttype.operationeel.PersoonGeslachtsnaamcomponentModel} class.
 */
@Repository("persoonGeslachtsnaamcomponentHistorieRepository")
public class PersoonGeslachtsnaamcomponentHistorieJpaRepository
    extends AbstractGroepHistorieRepository<PersoonGeslachtsnaamcomponentModel,
    AbstractPersoonGeslachtsnaamcomponentStandaardGroep, PersoonGeslachtsnaamcomponentHisModel>
{

    @Override
    protected String padNaarALaagEntiteitInCLaagEntiteit() {
        return "persoonGeslachtsnaamcomponent";
    }

    @Override
    protected Class<PersoonGeslachtsnaamcomponentHisModel> getCLaagDomainClass() {
        return PersoonGeslachtsnaamcomponentHisModel.class;
    }

    @Override
    protected PersoonGeslachtsnaamcomponentHisModel maakNieuwHistorieRecord(
        final PersoonGeslachtsnaamcomponentModel objectType,
        final AbstractPersoonGeslachtsnaamcomponentStandaardGroep groep)
    {
        // dual gebruik, als groep is null, haal de groep uit de huidige ALaag
        if (groep == null) {
            return new PersoonGeslachtsnaamcomponentHisModel(objectType.getGegevens(), objectType);
        } else {
            return new PersoonGeslachtsnaamcomponentHisModel(groep, objectType);
        }
    }

}
