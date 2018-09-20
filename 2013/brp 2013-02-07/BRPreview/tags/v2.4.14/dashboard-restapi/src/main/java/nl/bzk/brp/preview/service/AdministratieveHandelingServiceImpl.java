/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.service;

import nl.bzk.brp.preview.dataaccess.AdministratieveHandelingDao;
import nl.bzk.brp.preview.dataaccess.BerichtenDao;
import nl.bzk.brp.preview.model.AbstractBerichtRequest;
import nl.bzk.brp.preview.model.Bericht;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * TODO: Add documentation
 */
@Service
public class AdministratieveHandelingServiceImpl implements AdministratieveHandelingService {

    public final static String JMS_MESSAGE_ACTION_ID = "actieId";
    /** De berichten dao. */
    @Autowired
    private BerichtenDao berichtenDao;

    private AdministratieveHandelingDao ahDao;

    @Override
    public Bericht maakBericht(final Long handelingId) {

        AbstractBerichtRequest inkomendBericht = ahDao.haalOp(handelingId);

        inkomendBericht.valideer();

        Bericht bericht = new Bericht();

        String berichtTekst = inkomendBericht.creeerBerichtTekst();

        bericht.setBurgerservicenummers(inkomendBericht.creeerBsnLijst());

        bericht.setBericht(berichtTekst);

        bericht.setBerichtDetails(inkomendBericht.creeerDetailsTekst());

        bericht.setBurgerZakenModule(inkomendBericht.getKenmerken().getBurgerZakenModuleNaam());

        bericht.setPartij(inkomendBericht.getKenmerken().getVerzendendePartij().getNaam());

        if (inkomendBericht.getMeldingen() != null) {
            bericht.setAantalMeldingen(inkomendBericht.getMeldingen().size());
        }

        bericht.setSoortBijhouding(inkomendBericht.getSoortBijhouding());

        bericht.setVerzondenOp(inkomendBericht.getVerwerking().getVerwerkingsmoment());

        bericht.setPrevalidatie(inkomendBericht.getKenmerken().isPrevalidatie());

        return bericht;
    }

    @Override
    public void opslaan(final Bericht bericht) {
        berichtenDao.opslaan(bericht);
    }
}
