/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.sync.AbstractSyncBerichtZonderGerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.sync.generated.BlokkeringAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.PersoonsaanduidingType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.xml.SyncXml;

/**
 * Blokkering antwoord.
 */
public final class BlokkeringAntwoordBericht extends AbstractSyncBerichtZonderGerelateerdeInformatie {
    private static final long serialVersionUID = 1L;

    private final BlokkeringAntwoordType blokkeringAntwoordType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public BlokkeringAntwoordBericht() {
        this(new BlokkeringAntwoordType());
        blokkeringAntwoordType.setStatus(StatusType.OK);
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een storebericht te maken.
     * @param blokkeringAntwoordType het blokkeringAntwoord type
     */
    public BlokkeringAntwoordBericht(final BlokkeringAntwoordType blokkeringAntwoordType) {
        super("BlokkeringAntwoord");
        this.blokkeringAntwoordType = blokkeringAntwoordType;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return SyncXml.SINGLETON.elementToString(new ObjectFactory().createBlokkeringAntwoord(blokkeringAntwoordType));
    }

    /* ************************************************************************************************************* */

    /**
     * Geeft de status {@link StatusType} van het bericht terug.
     * @return De status {@link StatusType} van het bericht.
     */
    public StatusType getStatus() {
        return blokkeringAntwoordType.getStatus();
    }

    /**
     * Zet de status {@link StatusType} op het bericht.
     * @param status De te zetten status {@link StatusType}.
     */
    public void setStatus(final StatusType status) {
        blokkeringAntwoordType.setStatus(status);
    }

    /**
     * Geeft de persoonsaanduiding op het bericht terug.
     * @return De persoonsaanduiding op het bericht.
     */
    public PersoonsaanduidingType getPersoonsaanduiding() {
        return blokkeringAntwoordType.getPersoonsaanduiding();
    }

    /**
     * Zet de persoonsaanduiding op het bericht.
     * @param persoonsaanduiding De te zetten persoonsaanduiding.
     */
    public void setToelichting(final PersoonsaanduidingType persoonsaanduiding) {
        blokkeringAntwoordType.setPersoonsaanduiding(persoonsaanduiding);
    }

    /**
     * Geeft het process id op het bericht terug.
     * @return Het process id op het bericht.
     */
    public String getProcessId() {
        return blokkeringAntwoordType.getProcessId();
    }

    /**
     * Zet het process id op het bericht.
     * @param processId Het te zetten processId.
     */
    public void setProcessId(final String processId) {
        blokkeringAntwoordType.setProcessId(processId);
    }

    /**
     * Geeft de gemeente naar op het bericht terug.
     * @return De gemeente naar op het bericht.
     */
    public String getGemeenteNaar() {
        return blokkeringAntwoordType.getGemeenteNaar();
    }

    /**
     * Zet de gemeente naar op het bericht.
     * @param gemeenteNaar De te zetten gemeente naar.
     */
    public void setGemeenteNAar(final String gemeenteNaar) {
        blokkeringAntwoordType.setGemeenteNaar(gemeenteNaar);
    }

}
