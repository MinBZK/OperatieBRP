/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import nl.bzk.migratiebrp.bericht.model.sync.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ProtocolleringAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;

/**
 * Antwoord bericht voor initiele vulling protocollering.
 */
public class ProtocolleringAntwoord {

    private static final ObjectFactory OBJECT_FACTORY = new ObjectFactory();
    private final ProtocolleringAntwoordType protocolleringAntwoordType;

    /**
     * Constructor.
     * @param activiteitId activiteitId
     */
    public ProtocolleringAntwoord(final long activiteitId) {
        this(OBJECT_FACTORY.createProtocolleringAntwoordType());
        setActiviteitId(activiteitId);
    }

    /**
     * Contructor met bestaand protocolleringAntwoordType.
     * @param protocolleringAntwoordType bestaand protocolleringAntwoordType
     */
    public ProtocolleringAntwoord(final ProtocolleringAntwoordType protocolleringAntwoordType) {
        this.protocolleringAntwoordType = protocolleringAntwoordType;
    }

    /**
     * Geef activiteit id.
     * @return activiteit id
     */
    public final long getActiviteitId() {
        return protocolleringAntwoordType.getActiviteitId();
    }

    /**
     * Zet activiteit id.
     * @param id activiteit id
     */
    public final void setActiviteitId(final long id) {
        protocolleringAntwoordType.setActiviteitId(id);
    }

    /**
     * Geeft de status {@link StatusType} van het bericht terug.
     * @return De status {@link StatusType} van het bericht.
     */
    public final StatusType getStatus() {
        return protocolleringAntwoordType.getStatus();
    }

    /**
     * Zet de status {@link StatusType} op het bericht.
     * @param status De te zetten status {@link StatusType}.
     */
    public final void setStatus(final StatusType status) {
        protocolleringAntwoordType.setStatus(status);
    }

    /**
     * Geef de waarde van foutmelding.
     * @return de foutmelding van het bericht terug
     */
    public final String getFoutmelding() {
        return protocolleringAntwoordType.getFoutmelding();
    }

    /**
     * Zet de waarde van foutmelding.
     * @param foutmelding De te zetten foutmelding
     */
    public final void setFoutmelding(final String foutmelding) {
        protocolleringAntwoordType.setFoutmelding(foutmelding);
    }

    /**
     * Geef protocolleringAntwoordType.
     * @return protocolleringAntwoordType protocolleringAntwoordType
     */
    public final ProtocolleringAntwoordType getProtocolleringAntwoordType() {
        return protocolleringAntwoordType;
    }
}
