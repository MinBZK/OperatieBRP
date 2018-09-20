/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.sync.AbstractSyncBerichtZonderGerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AutorisatieAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.xml.SyncXml;

/**
 * Afnemersindicaties antwoord bericht.
 */
public final class AutorisatieAntwoordBericht extends AbstractSyncBerichtZonderGerelateerdeInformatie {
    private static final long serialVersionUID = 1L;

    private final AutorisatieAntwoordType autorisatieAntwoordType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public AutorisatieAntwoordBericht() {
        this(new AutorisatieAntwoordType());
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een bericht te maken.
     *
     * @param autorisatieAntwoordType
     *            het autorisatieAntwoordType type
     */
    public AutorisatieAntwoordBericht(final AutorisatieAntwoordType autorisatieAntwoordType) {
        super("AutorisatieAntwoord");
        this.autorisatieAntwoordType = autorisatieAntwoordType;
    }

    /* ************************************************************************************************************* */

    /**
     * Geeft de status {@link StatusType} van het bericht terug.
     *
     * @return De status {@link StatusType} van het bericht.
     */
    public StatusType getStatus() {
        return autorisatieAntwoordType.getStatus();
    }

    /**
     * Zet de status {@link StatusType} op het bericht.
     *
     * @param status
     *            De te zetten status {@link StatusType}.
     */
    public void setStatus(final StatusType status) {
        autorisatieAntwoordType.setStatus(status);
    }

    /**
     * Geef de waarde van foutmelding.
     *
     * @return de foutmelding van het bericht terug
     */
    public String getFoutmelding() {
        return autorisatieAntwoordType.getFoutmelding();
    }

    /**
     * Zet de waarde van foutmelding.
     *
     * @param foutmelding
     *            De te zetten foutmelding
     */
    public void setFoutmelding(final String foutmelding) {
        autorisatieAntwoordType.setFoutmelding(foutmelding);
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return SyncXml.SINGLETON.elementToString(new ObjectFactory().createAutorisatieAntwoord(autorisatieAntwoordType));
    }

}
