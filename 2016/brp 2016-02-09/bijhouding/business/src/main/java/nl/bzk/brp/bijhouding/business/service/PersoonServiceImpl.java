/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.service;

import java.util.List;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Commentaar.
 */
@Service
public class PersoonServiceImpl implements PersoonService {

    @Override
    public boolean isPersoonBerichtIdentiekAlsPersoonModel(final PersoonBericht persoonBericht,
                                                           final PersoonModel persoonModel)
    {
        // gaan even vanuit dat identificeerbareObjecten == null;
        // dus persoon is gerefereerd via Technische sleutel en niet refID
        final boolean isIdentiek;

        // check eerst dat beide objecten niet null.
        if (null == persoonBericht || null == persoonModel) {
            isIdentiek = false;
        } else if (StringUtils.isNotBlank(persoonBericht.getObjectSleutel())) {
            isIdentiek = persoonBericht.getObjectSleutel().trim().equals(
                    maakTechnischeSleutelVanPersoonModel(persoonModel));
        } else {
            // kijk of deze persoon een identificatieNummers.bsn heeft en vergelijk met die van de model
            // zelfde ==> persoon is identiek (Let op: eigen interpretatie !!)
            if (persoonBericht.getIdentificatienummers() != null) {
                if (persoonBericht.getIdentificatienummers().getBurgerservicenummer() != null) {
                    if (null != persoonModel.getIdentificatienummers().getBurgerservicenummer()) {
                        isIdentiek = persoonBericht.getIdentificatienummers().getBurgerservicenummer().equals(
                                persoonModel.getIdentificatienummers().getBurgerservicenummer());
                    } else {
                        // persoonModel heeft geen BSN
                        isIdentiek = false;
                    }
                } else if (null != persoonModel.getIdentificatienummers()
                        && null != persoonModel.getIdentificatienummers().getAdministratienummer())
                {
                    if (null != persoonModel.getIdentificatienummers().getAdministratienummer()) {
                        isIdentiek = persoonBericht.getIdentificatienummers().getAdministratienummer().equals(
                                persoonModel.getIdentificatienummers().getAdministratienummer());
                    } else {
                        // persoonModel heeft geen A-nummer, dus anders: Let op eigen interpretatie
                        isIdentiek = false;
                    }
                } else {
                    // hmm: geen anummer, geen bsn nummer en toch identificatienummers groep ?
                    isIdentiek = false;
                }
            } else {
                // nu moeten we echt gaan vergelijken: geslachtsAanduiding, geboorteDatum, geslachtsnaam etc ....
                isIdentiek = false;
            }
        }
        return isIdentiek;
    }

    @Override
    public String maakTechnischeSleutelVanPersoonModel(final PersoonModel persoonModel) {
        if (persoonModel.getSoort().getWaarde() == SoortPersoon.INGESCHREVENE) {
            // moet een bsn nummer hebben.
            // moet dit ook niet leading zero's krijgen ???
            return persoonModel.getIdentificatienummers().getBurgerservicenummer().getWaarde().toString();
        } else {
            // niet ingeschreven, dus voorlopig geef de database ID terug.
            return "db" + persoonModel.getID();
        }
    }

    @Override
    public boolean isIdentiekPersoon(final PersoonBericht persoon1, final PersoonBericht persoon2) {
        boolean isIdentiek = false;
        if (null != persoon1 && null != persoon2) {
            // vergelijk of de ene persoon refereert naar de ander
            // en/of ze hebben de zelfde technische sleutels.
            if (StringUtils.isNotBlank(persoon1.getReferentieID())) {
                isIdentiek = StringUtils.equals(persoon1.getReferentieID(), persoon2.getCommunicatieID());
            } else if (StringUtils.isNotBlank(persoon2.getReferentieID())) {
                isIdentiek = StringUtils.equals(persoon2.getReferentieID(), persoon1.getCommunicatieID());
            } else if (persoon1.getObjectSleutelDatabaseID() != null
                       && persoon2.getObjectSleutelDatabaseID() != null)
            {
                isIdentiek = persoon1.getObjectSleutelDatabaseID().equals(persoon2.getObjectSleutelDatabaseID());
            }
            // alle andere gevallen is het anders.
        }
        return isIdentiek;
    }

    @Override
    public boolean isPersoonNevenActieIdentiekHoofdPersoon(
            final PersoonBericht nevenActiePersoon, final List<PersoonBericht> hoofdPersonen)
    {
        boolean isIdentiek = false;
        if (null != nevenActiePersoon && CollectionUtils.isNotEmpty(hoofdPersonen)) {
            for (PersoonBericht hoofdPersoon : hoofdPersonen) {
                if (isIdentiekPersoon(nevenActiePersoon, hoofdPersoon)) {
                    isIdentiek = true;
                    break;
                }
            }
        }
        return isIdentiek;
    }
}
