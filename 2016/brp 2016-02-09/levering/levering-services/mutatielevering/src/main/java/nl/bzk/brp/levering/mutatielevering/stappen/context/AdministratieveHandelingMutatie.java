/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.context;

import nl.bzk.brp.model.basis.BrpObject;

/**
 * Dit is de klasse die het onderwerp is voor de administratieve handeling verwerking stappen.
 */
public class AdministratieveHandelingMutatie implements BrpObject {

    private final Long administratieveHandelingId;

    /**
     * Construeerd een AdministratieveHandelingMutatie met alle gegevens die nodig zijn om de stappen verwerking te starten.
     *
     * @param administratieveHandelingId Het id van de administratieve handeling.
     */
    public AdministratieveHandelingMutatie(
        final Long administratieveHandelingId)
    {
        this.administratieveHandelingId = administratieveHandelingId;
    }

    /**
     * Geeft de administratieve handeling id.
     *
     * @return Het id van de administratieve handeling.
     */
    public final Long getAdministratieveHandelingId() {
        return administratieveHandelingId;
    }

}
