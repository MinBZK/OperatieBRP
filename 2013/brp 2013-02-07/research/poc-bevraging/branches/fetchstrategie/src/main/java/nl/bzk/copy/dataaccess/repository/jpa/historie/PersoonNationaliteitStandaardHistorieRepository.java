/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.dataaccess.repository.jpa.historie;

import nl.bzk.copy.dataaccess.repository.jpa.historie.AbstractGroepHistorieRepository;
import nl.bzk.copy.model.groep.operationeel.AbstractPersoonNationaliteitStandaardGroep;
import nl.bzk.copy.model.groep.operationeel.historisch.PersoonNationaliteitHisModel;
import nl.bzk.copy.model.objecttype.operationeel.PersoonNationaliteitModel;
import org.springframework.stereotype.Repository;

/**
 * JPA repository voor de tabel His_PersBijhVerantw.
 */
@Repository("persoonNationaliteitStandaardHistorieRepository")
public class PersoonNationaliteitStandaardHistorieRepository
        extends AbstractGroepHistorieRepository<PersoonNationaliteitModel,
        AbstractPersoonNationaliteitStandaardGroep, PersoonNationaliteitHisModel>
{
    @Override
    protected String padNaarALaagEntiteitInCLaagEntiteit() {
        return "persoonNationaliteit";
    }

    @Override
    protected Class<PersoonNationaliteitHisModel> getCLaagDomainClass() {
        return PersoonNationaliteitHisModel.class;
    }

    @Override
    protected PersoonNationaliteitHisModel maakNieuwHistorieRecord(final PersoonNationaliteitModel objectType,
        final AbstractPersoonNationaliteitStandaardGroep groep)
    {
        // dual gebruik, als groep is null, haal de groep uit de huidige ALaag
        if (groep == null) {
            return new PersoonNationaliteitHisModel(objectType.getGegevens(), objectType);
        } else {
            return new PersoonNationaliteitHisModel(groep, objectType);
        }
    }
}
