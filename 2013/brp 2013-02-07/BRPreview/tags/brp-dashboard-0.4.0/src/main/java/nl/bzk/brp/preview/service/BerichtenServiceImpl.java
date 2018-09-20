/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 *
 */
package nl.bzk.brp.preview.service;

import java.util.Calendar;

import nl.bzk.brp.preview.dataaccess.BerichtenDao;
import nl.bzk.brp.preview.model.Bericht;
import nl.bzk.brp.preview.model.BerichtRequest;
import nl.bzk.brp.preview.model.BerichtenResponse;
import nl.bzk.brp.preview.model.DashboardSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Implementatie van de berichtenservice die verantwoordelijk is voor het ophalen en opslaan van de berichten van de BRP
 * die getoond moeten worden op het scherm.
 */
@Service
public class BerichtenServiceImpl implements BerichtenService {

    @Autowired
    private DashboardSettings settings;

    @Autowired
    private BerichtenDao      berichtenDao;

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.preview.service.BerichtenService#opslaan(nl.bzk.brp.preview.model.Bericht)
     */
    @Override
    public void opslaan(final BerichtRequest inkomendBericht) {

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

        berichtenDao.opslaan(bericht);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.preview.service.BerichtenService#getBerichtenResponse()
     */
    @Override
    public BerichtenResponse getBerichtenResponse() {
        BerichtenResponse response = new BerichtenResponse();

        response.setAantalBerichtenVolledig(settings.getAantalBerichtenVolledig());
        response.setPagina(DashboardSettings.DEFAULT_PAGINA);
        response.setMaximumAantalBerichtenPerResponse(settings.getMaximumAantalBerichtenPerResponse());
        response.setBerichten(berichtenDao.getAlleBerichten());
        return response;
    }

    @Override
    public BerichtenResponse getBerichtenResponseVanaf(final Calendar vanaf) {
        BerichtenResponse response = new BerichtenResponse();

        response.setAantalBerichtenVolledig(settings.getAantalBerichtenVolledig());
        response.setPagina(DashboardSettings.DEFAULT_PAGINA);
        response.setMaximumAantalBerichtenPerResponse(settings.getMaximumAantalBerichtenPerResponse());
        response.setBerichten(berichtenDao.getBerichtenVanaf(vanaf));
        return response;
    }

}
