/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.jbpm;

import java.util.Map;

import nl.bzk.migratiebrp.bericht.model.Bericht;

/**
 * JBPM Service.
 */
public interface JbpmService {

    /**
     * Start een JBPM proces.
     * @param procesNaam proces naam
     * @param berichtId bericht id
     * @return process instance id
     */
    long startProces(final String procesNaam, final Long berichtId);

    /**
     * Start een foutmelding JBPM proces.
     * @param bericht bericht
     * @param berichtId bericht id
     * @param originator originator
     * @param recipient recipient
     * @param fout fout code
     * @param foutmelding fout melding
     * @param indicatieBeheerder indicatie beheerder
     * @param geenPfVoorL3o indicatie pf pad onderdrukken voor Lo3 bericht
     * @return process instance id
     */
    long startFoutmeldingProces(
            final Bericht bericht,
            final Long berichtId,
            final String originator,
            final String recipient,
            final String fout,
            final String foutmelding,
            final boolean indicatieBeheerder,
            final boolean geenPfVoorL3o);

    /**
     * Vervolg een JBPM proces.
     * @param processInstanceId process instance id
     * @param tokenId token id
     * @param nodeId node id
     * @param attributes attributen
     * @return true als het proces vervolgd kon worden (token stond in de juiste node)
     */
    boolean vervolgProces(long processInstanceId, long tokenId, long nodeId, Map<String, Object> attributes);

    /**
     * Start een herhaal JBPM proces.
     * @param bericht bericht
     * @param berichtId bericht id
     * @param originator originator
     * @param recipient recipient
     * @param fout fout code
     * @param foutmelding fout melding
     * @param indicatieBeheerder indicatie beheerder
     * @return process instance id
     */
    long startHerhaalProces(
            Bericht bericht,
            Long berichtId,
            String originator,
            String recipient,
            String fout,
            String foutmelding,
            boolean indicatieBeheerder);

}
