/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.sync.AbstractSyncBerichtZonderGerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.sync.generated.BlokkeringInfoVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.sync.xml.SyncXml;

/**
 * Opvragen blokkering info bericht: opvragen status van de persoonsaanduiding in BRP.
 */
public final class BlokkeringInfoVerzoekBericht extends AbstractSyncBerichtZonderGerelateerdeInformatie {
    private static final long serialVersionUID = 1L;

    private final BlokkeringInfoVerzoekType blokkeringInfoVerzoekType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public BlokkeringInfoVerzoekBericht() {
        this(new BlokkeringInfoVerzoekType());
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een storebericht te maken.
     *
     * @param blokkeringInfoVerzoekType
     *            het blokkeringInfoVerzoek type
     */
    public BlokkeringInfoVerzoekBericht(final BlokkeringInfoVerzoekType blokkeringInfoVerzoekType) {
        super("BlokkeringInfoVerzoek");
        this.blokkeringInfoVerzoekType = blokkeringInfoVerzoekType;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return SyncXml.SINGLETON.elementToString(new ObjectFactory().createBlokkeringInfoVerzoek(blokkeringInfoVerzoekType));
    }

    /* ************************************************************************************************************* */

    /**
     * Geeft het A-nummer dat op het bericht staat.
     *
     * @return Het A-nummer dat op het bericht staat.
     */
    public String getANummer() {
        return blokkeringInfoVerzoekType.getANummer();
    }

    /**
     * Zet het A-nummer op het bericht.
     *
     * @param aNummer
     *            Het te zetten A-nummer.
     */
    public void setANummer(final String aNummer) {
        blokkeringInfoVerzoekType.setANummer(aNummer);
    }

}
