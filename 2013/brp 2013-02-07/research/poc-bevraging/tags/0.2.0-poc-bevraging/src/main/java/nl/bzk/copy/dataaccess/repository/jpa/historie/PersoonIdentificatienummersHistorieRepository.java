/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.dataaccess.repository.jpa.historie;

import nl.bzk.copy.dataaccess.repository.jpa.historie.AbstractGroepHistorieRepository;
import nl.bzk.copy.model.groep.operationeel.AbstractPersoonIdentificatienummersGroep;
import nl.bzk.copy.model.groep.operationeel.historisch.PersoonIdentificatienummersHisModel;
import nl.bzk.copy.model.objecttype.operationeel.PersoonModel;
import org.springframework.stereotype.Repository;

/**
 * JPA repository voor de tabel His_PersIds.
 */
@Repository("persoonIdentificatienummersHistorieRepository")
public class PersoonIdentificatienummersHistorieRepository
    extends AbstractGroepHistorieRepository<PersoonModel, AbstractPersoonIdentificatienummersGroep,
        PersoonIdentificatienummersHisModel>
{

    @Override
    protected String padNaarALaagEntiteitInCLaagEntiteit() {
        return "persoon";
    }

    @Override
    protected Class<PersoonIdentificatienummersHisModel> getCLaagDomainClass() {
        return PersoonIdentificatienummersHisModel.class;
    }

    @Override
    protected PersoonIdentificatienummersHisModel maakNieuwHistorieRecord(final PersoonModel objectType,
        final AbstractPersoonIdentificatienummersGroep groep)
    {
        // dual gebruik, als groep is null, haal de groep uit de huidige ALaag
        if (groep == null) {
            return new PersoonIdentificatienummersHisModel(objectType.getIdentificatienummers(), objectType);
        } else {
            return new PersoonIdentificatienummersHisModel(groep, objectType);
        }
    }
}
