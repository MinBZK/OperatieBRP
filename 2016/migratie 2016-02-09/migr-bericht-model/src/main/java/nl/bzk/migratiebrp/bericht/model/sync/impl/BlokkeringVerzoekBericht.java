/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.sync.AbstractSyncBerichtZonderGerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.sync.generated.BlokkeringVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.PersoonsaanduidingType;
import nl.bzk.migratiebrp.bericht.model.sync.xml.SyncXml;

/**
 * Blokkering bericht: aanbrengen persoonsaanduiding in BRP.
 */
public final class BlokkeringVerzoekBericht extends AbstractSyncBerichtZonderGerelateerdeInformatie {
    private static final long serialVersionUID = 1L;

    private final BlokkeringVerzoekType blokkeringVerzoekType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public BlokkeringVerzoekBericht() {
        this(new BlokkeringVerzoekType());
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een storebericht te maken.
     *
     * @param blokkeringVerzoekType
     *            het blokkeringVerzoek type
     */
    public BlokkeringVerzoekBericht(final BlokkeringVerzoekType blokkeringVerzoekType) {
        super("BlokkeringVerzoek");
        this.blokkeringVerzoekType = blokkeringVerzoekType;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return SyncXml.SINGLETON.elementToString(new ObjectFactory().createBlokkeringVerzoek(blokkeringVerzoekType));
    }

    /* ************************************************************************************************************* */

    /**
     * Geeft het A-nummer dat op het bericht staat.
     *
     * @return Het A-nummer dat op het bericht staat.
     */
    public String getANummer() {
        return blokkeringVerzoekType.getANummer();
    }

    /**
     * Zet het A-nummer op het bericht.
     *
     * @param aNummer
     *            Het te zetten A-nummer.
     */
    public void setANummer(final String aNummer) {
        blokkeringVerzoekType.setANummer(aNummer);
    }

    /**
     * Geef de waarde van persoonsaanduiding.
     *
     * @return de persoonsaanduiding
     */
    public PersoonsaanduidingType getPersoonsaanduiding() {
        return blokkeringVerzoekType.getPersoonsaanduiding();
    }

    /**
     * Zet de waarde van persoonsaanduiding.
     *
     * @param persoonsaanduiding
     *            de persoonsaanduiding
     */
    public void setPersoonsaanduiding(final PersoonsaanduidingType persoonsaanduiding) {
        blokkeringVerzoekType.setPersoonsaanduiding(persoonsaanduiding);
    }

    /**
     * Geeft het process ID dat op het bericht staat.
     *
     * @return Het process ID
     */
    public String getProcessId() {
        return blokkeringVerzoekType.getProcessId();
    }

    /**
     * Zet het proces ID op het bericht.
     *
     * @param processId
     *            Het te zetten proces ID.
     */
    public void setProcessId(final String processId) {
        blokkeringVerzoekType.setProcessId(processId);
    }

    /**
     * Geeft de gemeenteNaar die op het bericht staat.
     *
     * @return De gemeenteNaar
     */
    public String getGemeenteNaar() {
        return blokkeringVerzoekType.getGemeenteNaar();
    }

    /**
     * Zet de gemeenteNaar op het bericht.
     *
     * @param gemeenteNaar
     *            De te zetten gemeenteNaar.
     */
    public void setGemeenteNaar(final String gemeenteNaar) {
        blokkeringVerzoekType.setGemeenteNaar(gemeenteNaar);
    }

    /**
     * Geeft de gemeenteRegistratie die op het bericht staat.
     *
     * @return De gemeenteRegistratie
     */
    public String getGemeenteRegistratie() {
        return blokkeringVerzoekType.getGemeenteRegistratie();
    }

    /**
     * Zet de gemeenteRegistratie op het bericht.
     *
     * @param gemeenteRegistratie
     *            De te zetten gemeenteRegistratie.
     */
    public void setGemeenteRegistratie(final String gemeenteRegistratie) {
        blokkeringVerzoekType.setGemeenteRegistratie(gemeenteRegistratie);
    }
}
