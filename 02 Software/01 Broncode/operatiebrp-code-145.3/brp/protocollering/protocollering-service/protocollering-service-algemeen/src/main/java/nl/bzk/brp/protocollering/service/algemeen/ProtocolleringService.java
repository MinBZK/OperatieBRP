/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.protocollering.service.algemeen;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsaantekening;
import nl.bzk.brp.protocollering.domain.algemeen.LeveringPersoon;
import nl.bzk.brp.protocollering.domain.algemeen.ProtocolleringOpdracht;

/**
 * De interface voor de service die de protocollering verwerkt.
 */
public interface ProtocolleringService {

    /**
     * Protocolleer de opdracht.
     *
     * @param protocolleringOpdracht protocolleringOpdracht.
     * @return id van de {@link Leveringsaantekening}
     */
    Leveringsaantekening protocolleer(final ProtocolleringOpdracht protocolleringOpdracht);

    /**
     * Protocolleert (en persisteert) enkel de gegeven {@link LeveringPersoon}en bij de gegeven {@link Leveringsaantekening}.
     *
     * @param leveringsaantekening de {@link Leveringsaantekening}
     * @param persoonList een lijst met protocolleren personen
     */
    void protocolleerPersonenBijLeveringaantekening(final Leveringsaantekening leveringsaantekening, final List<LeveringPersoon> persoonList);

}
