/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.historie;

import nl.bzk.brp.model.groep.operationeel.AbstractPersoonNationaliteitStandaardGroep;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonNationaliteitStandaardHisModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonNationaliteitModel;
import org.springframework.stereotype.Repository;

/**
 * JPA repository voor de tabel His_PersBijhVerantw.
 */
@Repository("persoonNationaliteitStandaardHistorieRepository")
public class PersoonNationaliteitStandaardHistorieRepository
        extends AbstractGroepHistorieRepository<PersoonNationaliteitModel,
        AbstractPersoonNationaliteitStandaardGroep, PersoonNationaliteitStandaardHisModel>
{
    @Override
    protected String padNaarALaagEntiteitInCLaagEntiteit() {
        return "persoonNationaliteit";
    }

    @Override
    protected Class<PersoonNationaliteitStandaardHisModel> getCLaagDomainClass() {
        return PersoonNationaliteitStandaardHisModel.class;
    }

    @Override
    protected PersoonNationaliteitStandaardHisModel maakNieuwHistorieRecord(final PersoonNationaliteitModel objectType,
        final AbstractPersoonNationaliteitStandaardGroep groep)
    {
        // dual gebruik, als groep is null, haal de groep uit de huidige ALaag
        if (groep == null) {
            return new PersoonNationaliteitStandaardHisModel(objectType.getGegevens(), objectType);
        } else {
            return new PersoonNationaliteitStandaardHisModel(groep, objectType);
        }
    }
}
