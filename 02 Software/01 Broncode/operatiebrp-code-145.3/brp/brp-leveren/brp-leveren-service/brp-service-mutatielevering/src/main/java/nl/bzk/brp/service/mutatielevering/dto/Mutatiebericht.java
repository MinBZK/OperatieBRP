/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering.dto;

import java.util.Set;
import nl.bzk.brp.domain.internbericht.verzendingmodel.SynchronisatieBerichtGegevens;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;

/**
 * DTO dat alle gegevens om enkel uitgaand bericht te versturen.
 */
public final class Mutatiebericht {

    private final Mutatielevering mutatielevering;
    private final Set<Persoonslijst> personenInBericht;
    private final String inhoudelijkBericht;
    private final SynchronisatieBerichtGegevens stuurgegevensBericht;

    /**
     * Constructor.
     * @param mutatielevering de mutatielevering
     * @param personenInBericht de personen die in het bericht zijn opgenomen (subset van mutatiehandeling.personen)
     * @param inhoudelijkBericht het inhoudelijke bericht (brpXML / Lo3)
     * @param stuurgegevensBericht het stuurgegevens bericht (json)
     */
    public Mutatiebericht(final Mutatielevering mutatielevering, final Set<Persoonslijst> personenInBericht,
                          final String inhoudelijkBericht, final SynchronisatieBerichtGegevens stuurgegevensBericht) {
        this.mutatielevering = mutatielevering;
        this.personenInBericht = personenInBericht;
        this.inhoudelijkBericht = inhoudelijkBericht;
        this.stuurgegevensBericht = stuurgegevensBericht;
    }

    public Mutatielevering getMutatielevering() {
        return mutatielevering;
    }

    public Set<Persoonslijst> getPersonenInBericht() {
        return personenInBericht;
    }

    public String getInhoudelijkBericht() {
        return inhoudelijkBericht;
    }

    public SynchronisatieBerichtGegevens getStuurgegevensBericht() {
        return stuurgegevensBericht;
    }
}
