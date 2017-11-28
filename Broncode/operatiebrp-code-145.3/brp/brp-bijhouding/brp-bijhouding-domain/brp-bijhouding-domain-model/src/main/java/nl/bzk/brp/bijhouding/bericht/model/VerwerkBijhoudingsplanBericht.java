/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;

/**
 * Interface voor het notificatie bericht.
 */
@XmlElement("bhg_sysVerwerkBijhoudingsplan")
public interface VerwerkBijhoudingsplanBericht extends BmrGroep {

    /**
     * Geef de waarde van stuurgegevens.
     *
     * @return stuurgegevens
     */
    StuurgegevensElement getStuurgegevens();

    /**
     * Geef de waarde van administratieveHandelingPlan.
     *
     * @return administratieveHandelingPlan
     */
    AdministratieveHandelingPlanElement getAdministratieveHandelingPlan();
}
