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
import java.util.List;

import nl.bzk.brp.preview.dataaccess.BerichtenDao;
import nl.bzk.brp.preview.model.AbstractBerichtRequest;
import nl.bzk.brp.preview.model.Bericht;
import nl.bzk.brp.preview.model.BerichtenResponse;
import nl.bzk.brp.preview.model.DashboardSettings;
import nl.bzk.brp.preview.model.Statistieken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Implementatie van de berichtenservice die verantwoordelijk is voor het ophalen en opslaan van de berichten van de BRP
 * die getoond moeten worden op het scherm.
 */
@Service
public class BerichtenServiceImpl implements BerichtenService {

    /** De logger. */
    private static Logger     logger = LoggerFactory.getLogger(BerichtenServiceImpl.class);

    /** De settings. */
    @Autowired
    private DashboardSettings settings;

    /** De berichten dao. */
    @Autowired
    private BerichtenDao      berichtenDao;

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.preview.service.BerichtenService#opslaan(nl.bzk.brp.preview.model.Bericht)
     */
    @Override
    public void opslaan(final AbstractBerichtRequest inkomendBericht) {

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
    public BerichtenResponse getBerichten() {
        BerichtenResponse response = new BerichtenResponse();

        response.setAantalBerichtenVolledig(settings.getAantalBerichtenVolledig());
        response.setPagina(DashboardSettings.DEFAULT_PAGINA);
        response.setMaximumAantalBerichtenPerResponse(settings.getMaximumAantalBerichtenPerResponse());

        List<Bericht> alleBerichten = berichtenDao.getAlleBerichten();
        if (alleBerichten.size() > 0 && alleBerichten.size() > settings.getMaximumAantalBerichtenPerResponse()) {
            response.setBerichten(alleBerichten.subList(0, settings.getMaximumAantalBerichtenPerResponse() - 1));
        } else {
            response.setBerichten(alleBerichten);
        }

        // TODO statistieken invullen
        response.setStatistieken(new Statistieken());
        return response;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.preview.service.BerichtenService#getBerichtenResponseVanaf(java.util.Calendar)
     */
    @Override
    public BerichtenResponse getBerichtenResponseVanaf(final Calendar vanaf) {
        BerichtenResponse response = createBerichtResponseWithSettings();
        List<Bericht> alleBerichten = berichtenDao.getBerichtenVanaf(vanaf);
        logger.debug("Gebruik settings object voor beperken lijst in service:" + settings.toString());
        if (alleBerichten.size() > 0 && alleBerichten.size() > settings.getMaximumAantalBerichtenPerResponse()) {
            response.setBerichten(alleBerichten.subList(0, settings.getMaximumAantalBerichtenPerResponse()));
        } else {
            response.setBerichten(alleBerichten);
        }

        // TODO statistieken invullen
        response.setStatistieken(new Statistieken());
        return response;
    }

    /**
     * Creates the bericht response with settings.
     *
     * @return de berichten response
     */
    private BerichtenResponse createBerichtResponseWithSettings() {
        BerichtenResponse response = new BerichtenResponse();

        response.setAantalBerichtenVolledig(settings.getAantalBerichtenVolledig());
        response.setPagina(DashboardSettings.DEFAULT_PAGINA);
        response.setMaximumAantalBerichtenPerResponse(settings.getMaximumAantalBerichtenPerResponse() - 1);
        response.setMaximumAantalBerichtenPerPagina(settings.getMaximaalAantalBerichtenTonen());

        return response;
    }

    @Override
    public void setMaximumAantalBerichtenTonen(final int maximum) {
        settings.setMaximumAantalBerichtenTonen(maximum);
        logger.debug("settings gewijzigd voor maximaal aantal berichten in:"
            + settings.getMaximaalAantalBerichtenTonen());
        logger.debug("settings object bij wijzigen:" + settings.toString());
    }

    @Override
    public BerichtenResponse getBerichtenVoorPartij(final String partij) {
        BerichtenResponse response = createBerichtResponseWithSettings();
        logger.debug("Berichten opvragen voor partij:" + partij);
        List<Bericht> alleBerichten = berichtenDao.getBerichtenVoorPartij(partij);
        logger.debug("Gebruik settings object voor beperken lijst in service:" + settings.toString());
        if (alleBerichten.size() > 0 && alleBerichten.size() > settings.getMaximumAantalBerichtenPerResponse()) {
            response.setBerichten(alleBerichten.subList(0, settings.getMaximumAantalBerichtenPerResponse()));
        } else {
            response.setBerichten(alleBerichten);
        }

        // TODO statistieken invullen
        response.setStatistieken(new Statistieken());
        return response;
    }

    @Override
    public BerichtenResponse getBerichtenVoorPartijVanaf(final String partij, final Calendar vanaf) {
        BerichtenResponse response = createBerichtResponseWithSettings();
        logger.debug("Berichten opvragen voor partij:" + partij);
        List<Bericht> alleBerichten = berichtenDao.getBerichtenVoorPartijVanaf(partij, vanaf);
        logger.debug("Gebruik settings object voor beperken lijst in service:" + settings.toString());
        if (alleBerichten.size() > 0 && alleBerichten.size() > settings.getMaximumAantalBerichtenPerResponse()) {
            response.setBerichten(alleBerichten.subList(0, settings.getMaximumAantalBerichtenPerResponse()));
        } else {
            response.setBerichten(alleBerichten);
        }

        // TODO statistieken invullen
        response.setStatistieken(new Statistieken());
        return response;
    }

    @Override
    public BerichtenResponse getBerichtenVoorBsn(final String bsn) {

        BerichtenResponse response = createBerichtResponseWithSettings();

        List<Bericht> berichten;
        try {
            berichten = berichtenDao.getBerichtenOpBsn(Integer.parseInt(bsn));
        } catch (NumberFormatException e) {
            logger.warn("bsn is geen integer [{}]", bsn);
            berichten = null;
        }

        response.setBerichten(berichten);

        return response;
    }

}
