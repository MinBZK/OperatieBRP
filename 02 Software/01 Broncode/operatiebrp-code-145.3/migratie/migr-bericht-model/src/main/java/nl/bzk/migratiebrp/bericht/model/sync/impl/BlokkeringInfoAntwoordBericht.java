/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.sync.AbstractSyncBerichtZonderGerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.sync.generated.BlokkeringInfoAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.PersoonsaanduidingType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.xml.SyncXml;

/**
 * Blokkering info antwoord.
 */
public final class BlokkeringInfoAntwoordBericht extends AbstractSyncBerichtZonderGerelateerdeInformatie {
    private static final long serialVersionUID = 1L;

    private final BlokkeringInfoAntwoordType blokkeringInfoAntwoordType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public BlokkeringInfoAntwoordBericht() {
        this(new BlokkeringInfoAntwoordType());
        blokkeringInfoAntwoordType.setStatus(StatusType.OK);
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een storebericht te maken.
     * @param blokkeringInfoAntwoordType het blokkeringInfoAntwoord type
     */
    public BlokkeringInfoAntwoordBericht(final BlokkeringInfoAntwoordType blokkeringInfoAntwoordType) {
        super("BlokkeringInfoAntwoord");
        this.blokkeringInfoAntwoordType = blokkeringInfoAntwoordType;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return SyncXml.SINGLETON.elementToString(new ObjectFactory().createBlokkeringInfoAntwoord(blokkeringInfoAntwoordType));
    }

    /* ************************************************************************************************************* */

    /**
     * Geeft de persoonsaanduiding {@link PersoonsaanduidingType} op het bericht terug.
     * @return De persoonsaanduiding {@link PersoonsaanduidingType} op het bericht.
     */
    public PersoonsaanduidingType getPersoonsaanduiding() {
        return blokkeringInfoAntwoordType.getPersoonsaanduiding();
    }

    /**
     * Zet de persoonsaanduiding {@link PersoonsaanduidingType} op het bericht.
     * @param persoonsaanduiding De te zetten persoonsaanduiding {@link PersoonsaanduidingType} op het bericht.
     */
    public void setPersoonsaanduiding(final PersoonsaanduidingType persoonsaanduiding) {
        blokkeringInfoAntwoordType.setPersoonsaanduiding(persoonsaanduiding);
    }

    /**
     * Geeft het proces ID van het bericht terug.
     * @return Het proces ID van het bericht.
     */
    public String getProcessId() {
        return blokkeringInfoAntwoordType.getProcessId();
    }

    /**
     * Zet het proces ID op het bericht.
     * @param processId Het te zetten proces ID.
     */
    public void setProcessId(final String processId) {
        blokkeringInfoAntwoordType.setProcessId(processId);
    }

    /**
     * Geeft het gemeente naar van het bericht terug.
     * @return Het gemeente naar van het bericht.
     */
    public String getGemeenteNaar() {
        return blokkeringInfoAntwoordType.getGemeenteNaar();
    }

    /**
     * Zet het gemeente naar op het bericht.
     * @param gemeenteNaar Het te zetten gemeente naar
     */
    public void setGemeenteNaar(final String gemeenteNaar) {
        blokkeringInfoAntwoordType.setGemeenteNaar(gemeenteNaar);
    }

    /**
     * Geeft de status {@link StatusType} van het bericht terug.
     * @return De status {@link StatusType} van het bericht.
     */
    public StatusType getStatus() {
        return blokkeringInfoAntwoordType.getStatus();
    }

    /**
     * Zet de status {@link StatusType} op het bericht.
     * @param status De te zetten status {@link StatusType}.
     */
    public void setStatus(final StatusType status) {
        blokkeringInfoAntwoordType.setStatus(status);
    }

}
