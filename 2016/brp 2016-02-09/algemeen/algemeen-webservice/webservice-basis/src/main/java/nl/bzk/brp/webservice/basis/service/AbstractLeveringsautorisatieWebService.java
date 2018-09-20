/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.basis.service;

import javax.inject.Inject;
import nl.bzk.brp.business.stappen.BerichtContext;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.util.AutorisatieOffloadGegevens;
import nl.bzk.brp.webservice.business.service.AutorisatieException;
import nl.bzk.brp.webservice.business.service.LeveringsautorisatieService;
import nl.bzk.brp.webservice.business.stappen.BerichtVerwerkingsResultaat;

/**
 * Abstracte klasse die de autorisatie voor Leveringssgerelateerde webservices afhandelt.
 */
public abstract class AbstractLeveringsautorisatieWebService<T extends BerichtBericht, C extends BerichtContext, Y extends BerichtVerwerkingsResultaat>
    extends AbstractWebService<T,C,Y> {

    @Inject
    private LeveringsautorisatieService leveringsautorisatieService;

    @Override
    protected final void checkAutorisatie(final AutorisatieOffloadGegevens autenticatieOffloadGegevens, final T bericht) throws AutorisatieException {
        final int leveringautorisatieId = Integer.parseInt(bericht.getParameters().getLeveringsautorisatieID());
        leveringsautorisatieService.controleerAutorisatie(leveringautorisatieId,
                bericht.getStuurgegevens().getZendendePartijCode(), autenticatieOffloadGegevens);
    }
}
