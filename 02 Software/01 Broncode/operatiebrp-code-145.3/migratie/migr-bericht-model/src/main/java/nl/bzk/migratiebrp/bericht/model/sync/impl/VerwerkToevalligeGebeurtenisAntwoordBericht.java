/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import java.util.List;
import java.util.Set;

import nl.bzk.migratiebrp.bericht.model.sync.AbstractSyncBerichtZonderGerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.sync.generated.FoutredenType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.VerwerkToevalligeGebeurtenisAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.xml.SyncXml;
import nl.bzk.migratiebrp.conversie.model.logging.LogRegel;

/**
 * Verwerk toevallige gebeurtenis antwoord.
 */
public final class VerwerkToevalligeGebeurtenisAntwoordBericht extends AbstractSyncBerichtZonderGerelateerdeInformatie {
    private static final long serialVersionUID = 1L;

    private final VerwerkToevalligeGebeurtenisAntwoordType verwerkToevalligeGebeurtenisAntwoordType;

    /**
     * Default constructor (status=Ok).
     */
    public VerwerkToevalligeGebeurtenisAntwoordBericht() {
        this(new VerwerkToevalligeGebeurtenisAntwoordType());
        verwerkToevalligeGebeurtenisAntwoordType.setStatus(StatusType.OK);
    }

    /**
     * JAXB constructor.
     * @param verwerkToevalligeGebeurtenisAntwoordType Het verwerk toevallige gebeurtenis antwoord type {@link VerwerkToevalligeGebeurtenisAntwoordType}
     */
    public VerwerkToevalligeGebeurtenisAntwoordBericht(final VerwerkToevalligeGebeurtenisAntwoordType verwerkToevalligeGebeurtenisAntwoordType) {
        super("VerwerkToevalligeGebeurtenisAntwoord");
        this.verwerkToevalligeGebeurtenisAntwoordType = verwerkToevalligeGebeurtenisAntwoordType;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() {
        return SyncXml.SINGLETON.elementToString(new ObjectFactory().createVerwerkToevalligeGebeurtenisAntwoord(verwerkToevalligeGebeurtenisAntwoordType));
    }

    /* ************************************************************************************************************* */

    /**
     * Geef de waarde van status.
     * @return De status {@link StatusType} op het bericht.
     */
    public StatusType getStatus() {
        return verwerkToevalligeGebeurtenisAntwoordType.getStatus();
    }

    /**
     * Zet de waarde van status.
     * @param status status
     */
    public void setStatus(final StatusType status) {
        if (status == null) {
            throw new NullPointerException("Status mag niet null zijn.");
        }
        verwerkToevalligeGebeurtenisAntwoordType.setStatus(status);
    }

    /**
     * Geef de waarde van foutreden.
     * @return De foutreden {@link FoutredenType} op het bericht.
     */
    public FoutredenType getFoutreden() {
        return verwerkToevalligeGebeurtenisAntwoordType.getFoutreden();
    }

    /**
     * Zet de waarde van foutreden.
     * @param foutreden De te zetten foutreden.
     */
    public void setFoutreden(final FoutredenType foutreden) {
        verwerkToevalligeGebeurtenisAntwoordType.setFoutreden(foutreden);
    }

    /**
     * Geeft de administratie handeling id.
     * @return administratie handeling id
     */
    public Long getAdministratieveHandelingId() {
        return verwerkToevalligeGebeurtenisAntwoordType.getAdministratieveHandelingId();
    }

    /**
     * Zet de administratieve handeling id.
     * @param administratieveHandelingId de te zetten administratieve handeling id
     */
    public void setAdministratieveHandelingId(final Long administratieveHandelingId) {
        verwerkToevalligeGebeurtenisAntwoordType.setAdministratieveHandelingId(administratieveHandelingId);
    }

    /**
     * Geef de waarde van bijhoudings Gemeente.
     * @return De bijhoudingsgemeente
     */
    public String getBijhoudingGemeenteCode() {
        return verwerkToevalligeGebeurtenisAntwoordType.getBijhoudingGemeenteCode();
    }

    /**
     * Zet de waarde van gemeente van bijhouding.
     * @param gemeentecode De code van de actuele gemeente van bijhouding.
     */
    public void setBijhoudingGemeenteCode(final String gemeentecode) {
        verwerkToevalligeGebeurtenisAntwoordType.setBijhoudingGemeenteCode(gemeentecode);
    }

    /**
     * Geeft de logregels op het bericht terug.
     * @return De logregels op het bericht.
     */
    public List<LogRegel> getLogging() {
        return asLogRegelList(verwerkToevalligeGebeurtenisAntwoordType.getLogging());
    }

    /**
     * Zet de logregels op het bericht.
     * @param logRegels De te zetten logregels.
     */
    public void setLogging(final Set<LogRegel> logRegels) {
        verwerkToevalligeGebeurtenisAntwoordType.setLogging(asLogRegelType(logRegels));
    }

}
