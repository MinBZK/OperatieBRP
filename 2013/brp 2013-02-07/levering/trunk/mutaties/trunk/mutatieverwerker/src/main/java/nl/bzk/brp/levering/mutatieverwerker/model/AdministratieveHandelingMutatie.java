/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatieverwerker.model;

import java.util.Collection;
import java.util.Collections;

import nl.bzk.brp.model.basis.Vergrendelbaar;

/**
 * Dit is de klasse die het onderwerp is voor de administratieve handeling verwerking stappen.
 */
public class AdministratieveHandelingMutatie implements Vergrendelbaar {

    private Long administratieveHandelingId;

    private Collection<String> betrokkenPersonenBsns;

    /**
     * Construeerd een AdministratieveHandelingMutatie met alle gegevens die nodig zijn om de stappen verwerking
     * te starten.
     *
     * @param administratieveHandelingId Het id van de administratieve handeling.
     * @param betrokkenPersonenBsns      De lijst van bsn's van de betrokken personen.
     */
    public AdministratieveHandelingMutatie(
            final Long administratieveHandelingId,
            final Collection<String> betrokkenPersonenBsns)
    {
        this.administratieveHandelingId = administratieveHandelingId;
        this.betrokkenPersonenBsns = betrokkenPersonenBsns;
    }

    public Long getAdministratieveHandelingId() {
        return administratieveHandelingId;
    }

    @Override
    public Collection<String> getReadBsnLocks() {
        return Collections.emptyList();
    }

    @Override
    public Collection<String> getWriteBsnLocks() {
        return betrokkenPersonenBsns;
    }
}
