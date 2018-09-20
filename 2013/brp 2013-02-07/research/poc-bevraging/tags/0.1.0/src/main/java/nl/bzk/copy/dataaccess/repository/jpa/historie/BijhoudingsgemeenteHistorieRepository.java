/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.dataaccess.repository.jpa.historie;

import nl.bzk.copy.dataaccess.repository.jpa.historie.AbstractGroepHistorieRepository;
import nl.bzk.copy.model.groep.operationeel.AbstractPersoonBijhoudingsgemeenteGroep;
import nl.bzk.copy.model.groep.operationeel.historisch.PersoonBijhoudingsgemeenteHisModel;
import nl.bzk.copy.model.objecttype.operationeel.PersoonModel;
import org.springframework.stereotype.Repository;


/**
 * JPA repository voor de tabel his_persbijhgem.
 */
@Repository("bijhoudingsgemeenteHistorieRepository")
public class BijhoudingsgemeenteHistorieRepository extends
    AbstractGroepHistorieRepository<PersoonModel, AbstractPersoonBijhoudingsgemeenteGroep,
    PersoonBijhoudingsgemeenteHisModel>
{

    @Override
    protected String padNaarALaagEntiteitInCLaagEntiteit() {
        return "persoon";
    }

    @Override
    protected Class<PersoonBijhoudingsgemeenteHisModel> getCLaagDomainClass() {
        return PersoonBijhoudingsgemeenteHisModel.class;
    }

    @Override
    protected PersoonBijhoudingsgemeenteHisModel maakNieuwHistorieRecord(final PersoonModel objectType,
            final AbstractPersoonBijhoudingsgemeenteGroep groep)
    {
        // dual gebruik, als groep is null, haal de groep uit de huidige ALaag
        if (groep == null) {
            return new PersoonBijhoudingsgemeenteHisModel(objectType.getBijhoudingsgemeente(), objectType);
        } else {
            return new PersoonBijhoudingsgemeenteHisModel(groep, objectType);
        }
    }
}
