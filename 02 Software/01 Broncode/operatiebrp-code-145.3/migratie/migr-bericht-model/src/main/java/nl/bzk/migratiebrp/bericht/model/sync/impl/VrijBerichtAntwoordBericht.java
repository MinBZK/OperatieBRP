/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.sync.AbstractSyncBerichtZonderGerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.VrijBerichtAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.xml.SyncXml;

/**
 * Antwoord op ad hoc zoek vraag.
 */
public class VrijBerichtAntwoordBericht extends AbstractSyncBerichtZonderGerelateerdeInformatie {
    private static final long serialVersionUID = 1L;

    private final VrijBerichtAntwoordType vrijBerichtAntwoordType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public VrijBerichtAntwoordBericht() {
        this(new VrijBerichtAntwoordType());
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een storebericht te maken.
     * @param vrijBerichtAntwoordType het vrijBerichtAntwoord type
     */
    public VrijBerichtAntwoordBericht(final VrijBerichtAntwoordType vrijBerichtAntwoordType) {
        super("vrijBerichtAntwoord");
        this.vrijBerichtAntwoordType = vrijBerichtAntwoordType;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return SyncXml.SINGLETON.elementToString(new ObjectFactory().createVrijBerichtAntwoord(vrijBerichtAntwoordType));
    }

    /* ************************************************************************************************************* */

    /**
     * Geef de status.
     * @return de status
     */
    public String getStatus() {
        return vrijBerichtAntwoordType.getStatus().value();
    }

    /**
     * Zet de status.
     * @param geslaagd Indicatie of vrijbericht succesvol is
     */
    public void setStatus(boolean geslaagd) {
        if (geslaagd) {
            vrijBerichtAntwoordType.setStatus(StatusType.OK);
        } else {
            vrijBerichtAntwoordType.setStatus(StatusType.FOUT);
        }
    }

    /**
     * Geef het referentienummer van dit vrijbericht.
     * @return het referentienummer
     */
    public String getReferentienummer() {
        return vrijBerichtAntwoordType.getReferentienummer();
    }

    /**
     * Zet het referentienummer voor dit bericht.
     * @param referentienummer referentienummer voor dit bericht
     */
    public void setReferentienummer(String referentienummer) {
        vrijBerichtAntwoordType.setReferentienummer(referentienummer);
    }
}
