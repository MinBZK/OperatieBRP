/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.historie;

import java.util.List;

import nl.bzk.brp.dataaccess.repository.historie.PersoonAdresHistorieRepository;
import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.groep.operationeel.AbstractPersoonAdresStandaardGroep;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonAdresHisModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonAdresModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import org.springframework.stereotype.Repository;


/**
 * Repository voor de {@link PersoonAdresModel} class en standaard implementatie van de
 * {@link nl.bzk.brp.dataaccess.repository.historie.PersoonAdresHistorieRepository} class.
 */
@Repository
public class PersoonAdresHistorieJpaRepository extends
        AbstractGroepHistorieRepository<PersoonAdresModel, AbstractPersoonAdresStandaardGroep, PersoonAdresHisModel>
        implements PersoonAdresHistorieRepository
{

    @Override
    public List<PersoonAdresHisModel> haalHistorieGewijzigdeRecordsOp(final PersoonModel persoon,
            final DatumTijd tijdstipRegistratie)
    {

        // In principe is de check op datumTijdVerval overbodig, een D-laag record zal nooit dezelfde tijdstip
        // registratie hebben als de C-laag record. Check alsnog expliciet meegenomen.
        String query =
            "SELECT his FROM PersoonAdresHisModel his WHERE his.historie.datumTijdRegistratie = :tijdstipRegistratie "
                + "AND his.persoonAdres.persoon = :persoon AND his.historie.datumTijdVerval IS NULL";

        return getEntityManager().createQuery(query, PersoonAdresHisModel.class)
                .setParameter("tijdstipRegistratie", tijdstipRegistratie).setParameter("persoon", persoon)
                .getResultList();
    }

    /**
     * .
     *
     * @param objectType .
     * @param groep .
     * @return .
     */
    @Override
    protected PersoonAdresHisModel maakNieuwHistorieRecord(final PersoonAdresModel objectType,
            final AbstractPersoonAdresStandaardGroep groep)
    {
        // dual gebruik, als groep is null, haal de groep uit de huidige ALaag
        if (groep == null) {
            return new PersoonAdresHisModel(objectType.getGegevens(), objectType);
        } else {
            return new PersoonAdresHisModel(groep, objectType);
        }
    }

    @Override
    protected String padNaarALaagEntiteitInCLaagEntiteit() {
        return "persoonAdres";
    }

    @Override
    protected Class<PersoonAdresHisModel> getCLaagDomainClass() {
        return PersoonAdresHisModel.class;
    }

}
