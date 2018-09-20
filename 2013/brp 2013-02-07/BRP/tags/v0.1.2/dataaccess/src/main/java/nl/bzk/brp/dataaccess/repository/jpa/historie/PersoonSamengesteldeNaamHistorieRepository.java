/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.historie;

import java.util.Date;

import nl.bzk.brp.model.operationeel.kern.HisPersoonSamengesteldeNaam;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoon;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * JPA repository voor de tabel His_PersSamengesteldeNaam.
 */
@Repository
@Qualifier("persoonSamengesteldeNaamHistorieRepository")
public class PersoonSamengesteldeNaamHistorieRepository
        extends AbstractGroepHistorieRepository<HisPersoonSamengesteldeNaam>
{

    @Override
    protected HisPersoonSamengesteldeNaam maakNieuwHistorieRecord(final PersistentPersoon aLaagObject,
                                                                  final Integer datumAanvangGeldigheid,
                                                                  final Integer datumEindeGeldigheid,
                                                                  final Date registratieTijd)
    {
        final HisPersoonSamengesteldeNaam hisSamengesteldeNaam = new HisPersoonSamengesteldeNaam();
        hisSamengesteldeNaam.setAdellijkeTitel(aLaagObject.getAdellijkeTitel());
        hisSamengesteldeNaam.setGeslachtsNaam(aLaagObject.getGeslachtsNaam());
        hisSamengesteldeNaam.setIndAlgoritmischAfgeleid(aLaagObject.getIndAlgoritmischAfgeleid());
        hisSamengesteldeNaam.setIndNreeksAlsGeslnaam(aLaagObject.getIndReeksAlsGeslachtnaam());
        hisSamengesteldeNaam.setPersoon(aLaagObject);
        hisSamengesteldeNaam.setPredikaat(aLaagObject.getPredikaat());
        hisSamengesteldeNaam.setScheidingsTeken(aLaagObject.getScheidingsTeken());
        hisSamengesteldeNaam.setVoornamen(aLaagObject.getVoornaam());
        hisSamengesteldeNaam.setVoorvoegsel(aLaagObject.getVoorvoegsel());
        hisSamengesteldeNaam.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        hisSamengesteldeNaam.setDatumTijdRegistratie(registratieTijd);
        return hisSamengesteldeNaam;
    }

    @Override
    protected String padNaarPersoonEntiteitInCLaagEntiteit() {
        return "persoon";
    }

    @Override
    protected Class<HisPersoonSamengesteldeNaam> getCLaagDomainClass() {
        return HisPersoonSamengesteldeNaam.class;
    }
}
