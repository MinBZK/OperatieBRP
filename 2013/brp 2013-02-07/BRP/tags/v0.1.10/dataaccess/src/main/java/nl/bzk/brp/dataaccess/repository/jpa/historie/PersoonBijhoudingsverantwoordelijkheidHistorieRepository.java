/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.historie;

import nl.bzk.brp.model.groep.operationeel.AbstractPersoonBijhoudingsverantwoordelijkheidGroep;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonBijhoudingsverantwoordelijkheidHisModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import org.springframework.stereotype.Repository;

/**
 * JPA repository voor de tabel His_PersBijhVerantw.
 */
@Repository("persoonBijhoudingsverantwoordelijkheidHistorieRepository")
public class PersoonBijhoudingsverantwoordelijkheidHistorieRepository
    extends AbstractGroepHistorieRepository<PersoonModel, AbstractPersoonBijhoudingsverantwoordelijkheidGroep,
        PersoonBijhoudingsverantwoordelijkheidHisModel>
{

    @Override
    protected String padNaarALaagEntiteitInCLaagEntiteit() {
        return "persoon";
    }

    @Override
    protected Class<PersoonBijhoudingsverantwoordelijkheidHisModel> getCLaagDomainClass() {
        return PersoonBijhoudingsverantwoordelijkheidHisModel.class;
    }

    @Override
    protected PersoonBijhoudingsverantwoordelijkheidHisModel maakNieuwHistorieRecord(final PersoonModel objectType,
        final AbstractPersoonBijhoudingsverantwoordelijkheidGroep groep)
    {
        // dual gebruik, als groep is null, haal de groep uit de huidige ALaag
        if (groep == null) {
            return new PersoonBijhoudingsverantwoordelijkheidHisModel(objectType.getBijhoudingsverantwoordelijkheid(),
                    objectType);
        } else {
            return new PersoonBijhoudingsverantwoordelijkheidHisModel(groep, objectType);
        }
    }
}
