/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import java.util.List;
import java.util.Set;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.sync.AbstractSyncBerichtZonderGerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AfnemersindicatiesAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.xml.SyncXml;
import nl.bzk.migratiebrp.conversie.model.logging.LogRegel;

/**
 * Afnemersindicaties antwoord bericht.
 */
public final class AfnemersindicatiesAntwoordBericht extends AbstractSyncBerichtZonderGerelateerdeInformatie {
    private static final long serialVersionUID = 1L;

    private final AfnemersindicatiesAntwoordType afnemersindicatiesAntwoordType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public AfnemersindicatiesAntwoordBericht() {
        this(new AfnemersindicatiesAntwoordType());
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een bericht te maken.
     *
     * @param afnemersindicatiesType
     *            het afnemersindicatiesType type
     */
    public AfnemersindicatiesAntwoordBericht(final AfnemersindicatiesAntwoordType afnemersindicatiesType) {
        super("AfnemersindicatiesAntwoord");
        afnemersindicatiesAntwoordType = afnemersindicatiesType;
    }

    /* ************************************************************************************************************* */

    /**
     * Geeft de status {@link StatusType} van het bericht terug.
     *
     * @return De status {@link StatusType} van het bericht.
     */
    public StatusType getStatus() {
        return afnemersindicatiesAntwoordType.getStatus();
    }

    /**
     * Zet de status {@link StatusType} op het bericht.
     *
     * @param status
     *            De te zetten status {@link StatusType}.
     */
    public void setStatus(final StatusType status) {
        afnemersindicatiesAntwoordType.setStatus(status);
    }

    /**
     * Geef de waarde van foutmelding.
     *
     * @return de foutmelding van het bericht terug
     */
    public String getFoutmelding() {
        return afnemersindicatiesAntwoordType.getFoutmelding();
    }

    /**
     * Zet de waarde van foutmelding.
     *
     * @param foutmelding
     *            De te zetten foutmelding
     */
    public void setFoutmelding(final String foutmelding) {
        afnemersindicatiesAntwoordType.setFoutmelding(foutmelding);
    }

    /**
     * Geeft de logregels op het bericht terug.
     *
     * @return De logregels op het bericht.
     */
    public List<LogRegel> getLogging() {
        return asLogRegelList(afnemersindicatiesAntwoordType.getLogging());
    }

    /**
     * Zet de logregels op het bericht.
     *
     * @param logRegels
     *            De te zetten logregels.
     */
    public void setLogging(final Set<LogRegel> logRegels) {
        afnemersindicatiesAntwoordType.setLogging(asLogRegelType(logRegels));

    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return SyncXml.SINGLETON.elementToString(new ObjectFactory().createAfnemersindicatiesAntwoord(afnemersindicatiesAntwoordType));
    }
}
