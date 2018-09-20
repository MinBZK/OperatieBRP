/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.basis.service;

import javax.inject.Inject;
import nl.bzk.brp.business.stappen.BerichtContext;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.util.AutorisatieOffloadGegevens;
import nl.bzk.brp.webservice.business.service.AutorisatieException;
import nl.bzk.brp.webservice.business.service.BijhoudingsautorisatieService;
import nl.bzk.brp.webservice.business.stappen.BerichtVerwerkingsResultaat;

/**
 * Abstracte klasse die de autorisatie voor Bijhoudingsgerelateerde webservices afhandelt.
 */
public abstract class AbstractBijhoudingsautorisatieWebService<T extends BerichtBericht, C extends BerichtContext, Y extends BerichtVerwerkingsResultaat>
    extends AbstractWebService<T,C,Y> {

    @Inject
    private BijhoudingsautorisatieService bijhoudingsautorisatieService;

    @Override
    protected final void checkAutorisatie(final AutorisatieOffloadGegevens autorisatieOffloadGegevens, final T bericht) throws AutorisatieException {
        final AdministratieveHandelingBericht administratieveHandeling = bericht.getAdministratieveHandeling();
        SoortAdministratieveHandeling soortAdministratieveHandeling = null;
        if (administratieveHandeling != null) {
            soortAdministratieveHandeling = administratieveHandeling.getSoort().getWaarde();
        }
        bijhoudingsautorisatieService.controleerAutorisatie(soortAdministratieveHandeling, bericht.getStuurgegevens()
            .getZendendePartijCode(), autorisatieOffloadGegevens);
    }
}
