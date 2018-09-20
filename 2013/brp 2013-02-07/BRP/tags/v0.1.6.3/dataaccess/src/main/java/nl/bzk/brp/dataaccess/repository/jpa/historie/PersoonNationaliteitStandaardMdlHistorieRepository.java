/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.historie;

import nl.bzk.brp.model.groep.operationeel.historisch.PersoonNationaliteitHisModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonNationaliteitModel;
import org.springframework.stereotype.Repository;

/**
 * JPA repository voor de tabel His_PersBijhVerantw.
 */
@Repository("persoonNationaliteitStandaardMdlHistorieRepository")
public class PersoonNationaliteitStandaardMdlHistorieRepository
        extends AbstractGroepHistorieRepository<PersoonNationaliteitModel, PersoonNationaliteitHisModel>
{

    @Override
    protected PersoonNationaliteitHisModel maakNieuwHistorieRecord(final PersoonNationaliteitModel objectType) {
        return new PersoonNationaliteitHisModel(objectType.getGegevens(),
            objectType);
    }

    @Override
    protected String padNaarALaagEntiteitInCLaagEntiteit() {
        return "persoonNationaliteit";
    }

    @Override
    protected Class<PersoonNationaliteitHisModel> getCLaagDomainClass() {
        return PersoonNationaliteitHisModel.class;
    }
}
