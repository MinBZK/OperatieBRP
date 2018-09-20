/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.dataaccess.repository.jpa.historie;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import nl.bzk.copy.dataaccess.repository.historie.PersoonOpschortingHistorieRepository;
import nl.bzk.copy.dataaccess.repository.jpa.historie.AbstractGroepHistorieRepository;
import nl.bzk.copy.model.attribuuttype.Datum;
import nl.bzk.copy.model.groep.operationeel.AbstractPersoonOpschortingGroep;
import nl.bzk.copy.model.groep.operationeel.historisch.PersoonOpschortingHisModel;
import nl.bzk.copy.model.objecttype.operationeel.PersoonModel;
import org.springframework.stereotype.Repository;


/**
 * Standaard repository implementatie van de {@link nl.bzk.copy.dataaccess.repository.historie.PersoonOpschortingHistorieRepository} class.
 */
@Repository
public class PersoonOpschortingHistorieJpaRepository extends
    AbstractGroepHistorieRepository<PersoonModel, AbstractPersoonOpschortingGroep, PersoonOpschortingHisModel>
    implements PersoonOpschortingHistorieRepository
{

    @PersistenceContext(unitName = "nl.bzk.copy")
    private EntityManager em;

    @Override
    public Datum haalOpActueleDatumOpschorting(final PersoonModel persoon) {
        StringBuilder query = new StringBuilder("SELECT max(hisOpschorting.historie.datumAanvangGeldigheid) ");
        query.append("FROM PersoonOpschortingHisModel hisOpschorting ");
        query.append("WHERE hisOpschorting.historie.datumTijdVerval is null ");
        query.append("AND hisOpschorting.persoon.id = :persoonId");

        Query tQuery = em.createQuery(query.toString());
        tQuery.setParameter("persoonId", persoon.getId());

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
    protected Class<PersoonOpschortingHisModel> getCLaagDomainClass() {
        return PersoonOpschortingHisModel.class;
    }

    @Override
    protected PersoonOpschortingHisModel maakNieuwHistorieRecord(
        final PersoonModel objectType, final AbstractPersoonOpschortingGroep groep)
    {
        // dual gebruik, als groep is null, haal de groep uit de huidige ALaag
        if (groep == null) {
            return new PersoonOpschortingHisModel(objectType.getOpschorting(), objectType);
        } else {
            return new PersoonOpschortingHisModel(groep, objectType);
        }
    }

}
