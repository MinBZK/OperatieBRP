/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bijhouding;

import nl.bzk.brp.model.bericht.ber.BerichtParametersGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.bericht.kern.HandelingNotificatieBijhoudingsplanBericht;


/**
 * Klasse voor Notificeer Bijhoudingsplan Bericht.
 */
public final class NotificeerBijhoudingsplanBericht {

    private BerichtStuurgegevensGroepBericht stuurgegevens;

    private BerichtParametersGroepBericht parameters;

    private HandelingNotificatieBijhoudingsplanBericht administratieveHandelingPlan;

    public BerichtStuurgegevensGroepBericht getStuurgegevens() {
        return stuurgegevens;
    }

    public void setStuurgegevens(final BerichtStuurgegevensGroepBericht stuurgegevens) {
        this.stuurgegevens = stuurgegevens;
    }

    public BerichtParametersGroepBericht getParameters() {
        return parameters;
    }

    public void setParameters(final BerichtParametersGroepBericht parameters) {
        this.parameters = parameters;
    }

    public HandelingNotificatieBijhoudingsplanBericht getAdministratieveHandelingPlan() {
        return administratieveHandelingPlan;
    }

    public void setAdministratieveHandelingPlan(final HandelingNotificatieBijhoudingsplanBericht administratieveHandelingPlan) {
        this.administratieveHandelingPlan = administratieveHandelingPlan;
    }
}
