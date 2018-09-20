/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.historie;

import nl.bzk.brp.model.groep.operationeel.AbstractPersoonGeslachtsaanduidingGroep;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonGeslachtsaanduidingHisModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import org.springframework.stereotype.Repository;

/**
 * JPA repository voor de tabel His_PersGeslachtsAand.
 */
@Repository("persoonGeslachtsaanduidingHistorieRepository")
public class PersoonGeslachtsaanduidingHistorieRepository
    extends AbstractGroepHistorieRepository<PersoonModel, AbstractPersoonGeslachtsaanduidingGroep,
        PersoonGeslachtsaanduidingHisModel>
{

    @Override
    protected String padNaarALaagEntiteitInCLaagEntiteit() {
        return "persoon";
    }

    @Override
    protected Class<PersoonGeslachtsaanduidingHisModel> getCLaagDomainClass() {
        return PersoonGeslachtsaanduidingHisModel.class;
    }

    @Override
    protected PersoonGeslachtsaanduidingHisModel maakNieuwHistorieRecord(final PersoonModel objectType,
        final AbstractPersoonGeslachtsaanduidingGroep groep)
    {
        // dual gebruik, als groep is null, haal de groep uit de huidige ALaag
        if (groep == null) {
            return new PersoonGeslachtsaanduidingHisModel(objectType.getGeslachtsaanduiding(), objectType);
        } else {
            return new PersoonGeslachtsaanduidingHisModel(groep, objectType);
        }
    }
}
