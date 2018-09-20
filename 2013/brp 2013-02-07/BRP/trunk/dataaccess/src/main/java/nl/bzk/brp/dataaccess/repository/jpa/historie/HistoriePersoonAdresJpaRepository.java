/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.historie;

import java.util.List;

import nl.bzk.brp.dataaccess.repository.historie.HistoriePersoonAdresRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.logisch.kern.basis.PersoonAdresStandaardGroepBasis;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAdresModel;
import nl.bzk.brp.model.operationeel.kern.PersoonAdresModel;
import nl.bzk.brp.model.operationeel.kern.PersoonAdresStandaardGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import org.springframework.stereotype.Repository;


/**
 * Repository voor de {@link PersoonAdresModel} class en standaard implementatie van de
 * {@link nl.bzk.brp.dataaccess.repository.historie.HistoriePersoonAdresRepository} class.
 */
@Repository
public class HistoriePersoonAdresJpaRepository extends
        AbstractGroepMaterieleHistorieRepository<PersoonAdresModel, PersoonAdresStandaardGroepBasis, HisPersoonAdresModel>
    implements HistoriePersoonAdresRepository
{

    @Override
    public List<HisPersoonAdresModel> haalHistorieGewijzigdeRecordsOp(final PersoonModel persoon,
        final DatumTijd tijdstipRegistratie)
    {

        // In principe is de check op datumTijdVerval overbodig, een D-laag record zal nooit dezelfde tijdstip
        // registratie hebben als de C-laag record. Check alsnog expliciet meegenomen.
        String query =
            "SELECT his FROM HisPersoonAdresModel his WHERE his.materieleHistorie.datumTijdRegistratie = :tijdstipRegistratie "
                + "AND his.persoonAdres.persoon = :persoon AND his.materieleHistorie.datumTijdVerval IS NULL";

        return getEntityManager().createQuery(query, HisPersoonAdresModel.class)
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
    protected HisPersoonAdresModel maakNieuwHistorieRecord(final PersoonAdresModel objectType,
        final PersoonAdresStandaardGroepBasis groep)
    {
        // dual gebruik, als groep is null, haal de groep uit de huidige ALaag
        if (groep == null) {
            return new HisPersoonAdresModel(objectType, objectType.getStandaard());
        } else {
            return new HisPersoonAdresModel(objectType, (PersoonAdresStandaardGroepModel) groep);
        }
    }

    @Override
    protected String padNaarALaagEntiteitInCLaagEntiteit() {
        return "persoonAdres";
    }

    @Override
    protected Class<HisPersoonAdresModel> getCLaagDomainClass() {
        return HisPersoonAdresModel.class;
    }

}
