/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.historie;

import nl.bzk.brp.model.groep.operationeel.historisch.PersoonBijhoudingsverantwoordelijkheidHisModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import org.springframework.stereotype.Repository;

/**
 * JPA repository voor de tabel His_PersBijhVerantw.
 */
@Repository("persoonBijhoudingsverantwoordelijkheidHistorieRepository")
public class PersoonBijhoudingsverantwoordelijkheidHistorieRepository
        extends AbstractGroepHistorieRepository<PersoonModel, PersoonBijhoudingsverantwoordelijkheidHisModel>
{

    @Override
    protected PersoonBijhoudingsverantwoordelijkheidHisModel maakNieuwHistorieRecord(final PersoonModel objectType) {
        return new PersoonBijhoudingsverantwoordelijkheidHisModel(objectType.getBijhoudingsverantwoordelijkheid(),
            objectType);
    }

    @Override
    protected String padNaarALaagEntiteitInCLaagEntiteit() {
        return "persoon";
    }

    @Override
    protected Class<PersoonBijhoudingsverantwoordelijkheidHisModel> getCLaagDomainClass() {
        return PersoonBijhoudingsverantwoordelijkheidHisModel.class;
    }
}
