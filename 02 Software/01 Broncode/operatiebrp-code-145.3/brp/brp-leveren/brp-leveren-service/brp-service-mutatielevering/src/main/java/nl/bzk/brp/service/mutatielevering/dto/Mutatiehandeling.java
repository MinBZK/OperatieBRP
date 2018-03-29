/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering.dto;

import com.google.common.collect.ImmutableMap;
import java.util.Collection;
import java.util.Map;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;

/**
 * Dit object bevat de handeling met bijhouden personen.
 */
public final class Mutatiehandeling {

    private final long administratieveHandelingId;
    private final Map<Long, Persoonslijst> persoonsgegevensMap;


    /**
     * Constructor.
     * @param administratieveHandelingId de administratieveHandelingId
     * @param persoonsLijstMap de bijgehouden personen.
     */
    public Mutatiehandeling(final long administratieveHandelingId, final Map<Long, Persoonslijst> persoonsLijstMap) {
        this.administratieveHandelingId = administratieveHandelingId;
        this.persoonsgegevensMap = ImmutableMap.copyOf(persoonsLijstMap);
    }

    public long getAdministratieveHandelingId() {
        return administratieveHandelingId;
    }

    public Map<Long, Persoonslijst> getPersoonsgegevensMap() {
        return persoonsgegevensMap;
    }

    public Collection<Persoonslijst> getPersonen() {
        return persoonsgegevensMap.values();
    }
}
