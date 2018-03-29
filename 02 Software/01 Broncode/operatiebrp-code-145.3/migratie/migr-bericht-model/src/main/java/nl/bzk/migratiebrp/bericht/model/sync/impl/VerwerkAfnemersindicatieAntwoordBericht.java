/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import nl.bzk.migratiebrp.bericht.model.sync.AbstractSyncBerichtZonderGerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AfnemersindicatieFoutcodeType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.VerwerkAfnemersindicatieAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.xml.SyncXml;

/**
 * Verwijder afnemersindicatie antwoord.
 */
public final class VerwerkAfnemersindicatieAntwoordBericht extends AbstractSyncBerichtZonderGerelateerdeInformatie {
    private static final long serialVersionUID = 1L;

    private final VerwerkAfnemersindicatieAntwoordType verwerkAfnemersindicatieAntwoordType;

    /**
     * Default constructor (status=Ok).
     */
    public VerwerkAfnemersindicatieAntwoordBericht() {
        this(new VerwerkAfnemersindicatieAntwoordType());
        verwerkAfnemersindicatieAntwoordType.setStatus(StatusType.OK);
    }

    /**
     * JAXB constructor.
     * @param verwerkAfnemersindicatieAntwoordType Het verwerk afnemersindicatie antwoord type {@link VerwerkAfnemersindicatieAntwoordType}
     */
    public VerwerkAfnemersindicatieAntwoordBericht(final VerwerkAfnemersindicatieAntwoordType verwerkAfnemersindicatieAntwoordType) {
        super("VerwerkAfnemersindicatieAntwoord");
        this.verwerkAfnemersindicatieAntwoordType = verwerkAfnemersindicatieAntwoordType;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() {
        return SyncXml.SINGLETON.elementToString(new ObjectFactory().createVerwerkAfnemersindicatieAntwoord(verwerkAfnemersindicatieAntwoordType));
    }

    /* ************************************************************************************************************* */

    /**
     * Geef de waarde van status.
     * @return De status {@link StatusType} op het bericht.
     */
    public StatusType getStatus() {
        return verwerkAfnemersindicatieAntwoordType.getStatus();
    }

    /**
     * Zet de waarde van status.
     * @param status status
     */
    public void setStatus(final StatusType status) {
        if (status == null) {
            throw new NullPointerException("Status mag niet null zijn.");
        }
        verwerkAfnemersindicatieAntwoordType.setStatus(status);
    }

    /**
     * Geef de waarde van foutcode.
     * @return De foutcode {@link AfnemersindicatieFoutcodeType} op het bericht.
     */
    public AfnemersindicatieFoutcodeType getFoutcode() {
        return verwerkAfnemersindicatieAntwoordType.getFoutcode();
    }

    /**
     * Zet de waarde van foutcode.
     * @param foutcode fout code
     */
    public void setFoutcode(final AfnemersindicatieFoutcodeType foutcode) {
        if (foutcode == null) {
            throw new NullPointerException("Foutcode mag niet null zijn.");
        }
        verwerkAfnemersindicatieAntwoordType.setFoutcode(foutcode);
    }
}
