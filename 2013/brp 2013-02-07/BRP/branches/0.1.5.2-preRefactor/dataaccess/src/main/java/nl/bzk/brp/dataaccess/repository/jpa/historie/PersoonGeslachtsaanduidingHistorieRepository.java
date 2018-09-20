/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.historie;

import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsaanduiding;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoon;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * JPA repository voor de tabel His_PersGeslachtsAand.
 */
@Repository
@Qualifier("persoonGeslachtsaanduidingHistorieRepository")
public class PersoonGeslachtsaanduidingHistorieRepository
        extends AbstractGroepHistorieRepository<HisPersoonGeslachtsaanduiding>
{

    @Override
    protected HisPersoonGeslachtsaanduiding maakNieuwHistorieRecord(final PersistentPersoon ppersoon) {
        final HisPersoonGeslachtsaanduiding hisPersoonGeslachtsaanduiding = new HisPersoonGeslachtsaanduiding();
        hisPersoonGeslachtsaanduiding.setPersoon(ppersoon);
        hisPersoonGeslachtsaanduiding.setGeslachtsAanduiding(ppersoon.getGeslachtsAanduiding());
        return hisPersoonGeslachtsaanduiding;
    }

    @Override
    protected String padNaarPersoonEntiteitInCLaagEntiteit() {
        return "persoon";
    }

    @Override
    protected Class<HisPersoonGeslachtsaanduiding> getCLaagDomainClass() {
        return HisPersoonGeslachtsaanduiding.class;
    }
}
