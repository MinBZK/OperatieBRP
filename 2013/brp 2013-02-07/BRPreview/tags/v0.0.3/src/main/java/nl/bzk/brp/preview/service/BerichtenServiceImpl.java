/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 *
 */
package nl.bzk.brp.preview.service;

import nl.bzk.brp.preview.dataaccess.BerichtenDao;
import nl.bzk.brp.preview.model.Bericht;
import nl.bzk.brp.preview.model.BerichtenResponse;
import nl.bzk.brp.preview.model.DashboardSettings;
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

    private static Logger     logger = LoggerFactory.getLogger(BerichtenServiceImpl.class);

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
    public int opslaan(final Bericht inkomendBericht) {
        // TODO Auto-generated method stub
        logger.debug("Opslaan van bericht {1}", inkomendBericht);
        return SUCCESS;
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

}
