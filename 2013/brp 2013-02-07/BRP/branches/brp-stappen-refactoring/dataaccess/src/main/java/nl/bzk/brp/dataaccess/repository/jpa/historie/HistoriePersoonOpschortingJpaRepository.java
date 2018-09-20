/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.historie;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import nl.bzk.brp.dataaccess.repository.historie.HistoriePersoonOpschortingRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.logisch.kern.basis.PersoonOpschortingGroepBasis;
import nl.bzk.brp.model.operationeel.kern.HisPersoonOpschortingModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.operationeel.kern.PersoonOpschortingGroepModel;
import org.springframework.stereotype.Repository;


/**
 * Standaard repository implementatie van de {@link nl.bzk.brp.dataaccess.repository.historie.HistoriePersoonOpschortingRepository} class.
 */
@Repository
public class HistoriePersoonOpschortingJpaRepository extends
        AbstractGroepMaterieleHistorieRepository<PersoonModel, PersoonOpschortingGroepBasis, HisPersoonOpschortingModel>
        implements HistoriePersoonOpschortingRepository
{

    @PersistenceContext(unitName = "nl.bzk.brp")
    private EntityManager em;

    @Override
    public Datum haalOpActueleDatumOpschorting(final PersoonModel persoon) {
        StringBuilder query = new StringBuilder("SELECT max(hisOpschorting.materieleHistorie.datumAanvangGeldigheid) ");
        query.append("FROM HisPersoonOpschortingModel hisOpschorting ");
        query.append("WHERE hisOpschorting.materieleHistorie.datumTijdVerval is null ");
        query.append("AND hisOpschorting.persoon.id = :persoonId");

        Query tQuery = em.createQuery(query.toString());
        tQuery.setParameter("persoonId", persoon.getID());

        Datum datumOpschorting;
        try {
            datumOpschorting = (Datum) tQuery.getSingleResult();
        } catch (NoResultException e) {
            datumOpschorting = null;
        }

        return datumOpschorting;
    }

    @Override
    protected String padNaarALaagEntiteitInCLaagEntiteit() {
        return "persoon";
    }

    @Override
    protected Class<HisPersoonOpschortingModel> getCLaagDomainClass() {
        return HisPersoonOpschortingModel.class;
    }

    @Override
    protected HisPersoonOpschortingModel maakNieuwHistorieRecord(
            final PersoonModel objectType, final PersoonOpschortingGroepBasis groep)
    {
        // dual gebruik, als groep is null, haal de groep uit de huidige ALaag
        if (groep == null) {
            return new HisPersoonOpschortingModel(objectType, objectType.getOpschorting());
        } else {
            return new HisPersoonOpschortingModel(objectType, (PersoonOpschortingGroepModel) groep);
        }
    }

}
