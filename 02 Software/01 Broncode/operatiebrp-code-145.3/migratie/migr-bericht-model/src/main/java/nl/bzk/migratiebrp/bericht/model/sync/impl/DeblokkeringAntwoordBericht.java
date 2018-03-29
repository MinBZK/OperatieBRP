/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.sync.AbstractSyncBerichtZonderGerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.sync.generated.DeblokkeringAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.xml.SyncXml;

/**
 * Deblokkering bericht antwoord.
 */
public final class DeblokkeringAntwoordBericht extends AbstractSyncBerichtZonderGerelateerdeInformatie {

    private static final long serialVersionUID = 1L;

    private final DeblokkeringAntwoordType deblokkeringAntwoordType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public DeblokkeringAntwoordBericht() {
        this(new DeblokkeringAntwoordType());
        deblokkeringAntwoordType.setStatus(StatusType.OK);
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een storebericht te maken.
     * @param deblokkeringAntwoordType het deblokkeringAntwoord type
     */
    public DeblokkeringAntwoordBericht(final DeblokkeringAntwoordType deblokkeringAntwoordType) {
        super("DeblokkeringAntwoord");
        this.deblokkeringAntwoordType = deblokkeringAntwoordType;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return SyncXml.SINGLETON.elementToString(new ObjectFactory().createDeblokkeringAntwoord(deblokkeringAntwoordType));
    }

    /* ************************************************************************************************************* */

    /**
     * Geeft de status {@link StatusType} van het bericht terug.
     * @return De status {@link StatusType} van het bericht.
     */
    public StatusType getStatus() {
        return deblokkeringAntwoordType.getStatus();
    }

    /**
     * Zet de status {@link StatusType} op het bericht.
     * @param status De te zetten status {@link StatusType}.
     */
    public void setStatus(final StatusType status) {
        deblokkeringAntwoordType.setStatus(status);
    }

}
