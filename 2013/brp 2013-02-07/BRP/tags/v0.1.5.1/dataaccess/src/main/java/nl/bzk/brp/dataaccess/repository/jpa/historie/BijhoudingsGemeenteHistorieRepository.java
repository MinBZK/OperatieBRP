/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.historie;

import java.util.Date;

import nl.bzk.brp.model.operationeel.kern.HisPersoonBijhoudingsGemeente;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoon;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;


/**
 * JPA repository voor de tabel his_persbijhgem.
 */
@Repository
@Qualifier("bijhoudingsGemeenteHistorieRepository")
public class BijhoudingsGemeenteHistorieRepository extends
        AbstractGroepHistorieRepository<HisPersoonBijhoudingsGemeente>
{

    @Override
    protected HisPersoonBijhoudingsGemeente maakNieuwHistorieRecord(final PersistentPersoon aLaagObject,
            final Integer datumAanvangGeldigheid, final Integer datumEindeGeldigheid, final Date tijdstipRegistratie)
    {
        HisPersoonBijhoudingsGemeente hisPersoonBijhoudingsGemeente = new HisPersoonBijhoudingsGemeente();
        hisPersoonBijhoudingsGemeente.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        hisPersoonBijhoudingsGemeente.setDatumEindeGeldigheid(datumEindeGeldigheid);
        hisPersoonBijhoudingsGemeente.setDatumTijdRegistratie(tijdstipRegistratie);
        hisPersoonBijhoudingsGemeente.setPersoon(aLaagObject);
        hisPersoonBijhoudingsGemeente.setDatumInschrijvingInGemeente(aLaagObject
                .getBijhoudingsGemeenteDatumInschrijving());
        hisPersoonBijhoudingsGemeente.setBijhoudingsGemeente(aLaagObject.getBijhoudingsGemeente());
        hisPersoonBijhoudingsGemeente.setIndOnverwDocAanw(aLaagObject
                .getBijhoudingsGemeenteIndicatieOnverwerktDocumentAanwezig());
        return hisPersoonBijhoudingsGemeente;
    }

    @Override
    protected String padNaarPersoonEntiteitInCLaagEntiteit() {
        return "persoon";
    }

    @Override
    protected Class<HisPersoonBijhoudingsGemeente> getCLaagDomainClass() {
        return HisPersoonBijhoudingsGemeente.class;
    }

}
