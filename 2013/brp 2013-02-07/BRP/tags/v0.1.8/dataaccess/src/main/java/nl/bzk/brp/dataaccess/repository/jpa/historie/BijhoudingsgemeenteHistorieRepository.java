/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.historie;

import nl.bzk.brp.model.groep.operationeel.historisch.PersoonBijhoudingsgemeenteHisModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import org.springframework.stereotype.Repository;


/**
 * JPA repository voor de tabel his_persbijhgem.
 */
@Repository("bijhoudingsgemeenteHistorieRepository")
public class BijhoudingsgemeenteHistorieRepository extends
        AbstractGroepHistorieRepository<PersoonModel, PersoonBijhoudingsgemeenteHisModel>
{

    @Override
    protected PersoonBijhoudingsgemeenteHisModel maakNieuwHistorieRecord(final PersoonModel objectType) {
        return new PersoonBijhoudingsgemeenteHisModel(objectType.getBijhoudingsgemeente(), objectType);
    }

    @Override
    protected String padNaarALaagEntiteitInCLaagEntiteit() {
        return "persoon";
    }

    @Override
    protected Class<PersoonBijhoudingsgemeenteHisModel> getCLaagDomainClass() {
        return PersoonBijhoudingsgemeenteHisModel.class;
    }
}
