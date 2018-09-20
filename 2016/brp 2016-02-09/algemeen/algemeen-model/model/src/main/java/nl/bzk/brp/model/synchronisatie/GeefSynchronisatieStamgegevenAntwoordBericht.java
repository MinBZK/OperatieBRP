/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.synchronisatie;

import java.util.List;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.bericht.ber.AntwoordBericht;
import nl.bzk.brp.model.basis.SynchroniseerbaarStamgegeven;


/**
 * Antwoord bericht voor synchronisatie stamgegevens.
 */
public class GeefSynchronisatieStamgegevenAntwoordBericht extends AntwoordBericht {

    private List<SynchroniseerbaarStamgegeven> synchronisatieStamgegevens;

    /**
     * Standaard constructor die de soort zet van het bericht.
     */
    public GeefSynchronisatieStamgegevenAntwoordBericht() {
        super(SoortBericht.LVG_SYN_GEEF_SYNCHRONISATIE_STAMGEGEVEN_R);
    }

    public List<SynchroniseerbaarStamgegeven> getSynchronisatieStamgegevens() {
        return synchronisatieStamgegevens;
    }

    public void setSynchronisatieStamgegevens(final List<SynchroniseerbaarStamgegeven> synchronisatieStamgegevens) {
        this.synchronisatieStamgegevens = synchronisatieStamgegevens;
    }
}
