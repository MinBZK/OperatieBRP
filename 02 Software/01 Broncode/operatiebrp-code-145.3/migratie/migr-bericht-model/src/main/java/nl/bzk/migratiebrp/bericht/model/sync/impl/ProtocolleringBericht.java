/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.sync.AbstractSyncBerichtZonderGerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ProtocolleringLijstType;
import nl.bzk.migratiebrp.bericht.model.sync.xml.SyncXml;

/**
 * Initiele vulling van protocollering data.
 */
public final class ProtocolleringBericht extends AbstractSyncBerichtZonderGerelateerdeInformatie {

    private static final long serialVersionUID = 1L;

    private final ProtocolleringLijstType protocolleringLijstType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public ProtocolleringBericht() {
        this(new ProtocolleringLijstType());
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een bericht te maken.
     * @param protocolleringLijstType protocollering lijst type
     */
    public ProtocolleringBericht(final ProtocolleringLijstType protocolleringLijstType) {
        super("Protocollering");
        this.protocolleringLijstType = protocolleringLijstType;
    }

    /* ************************************************************************************************************* */

    /**
     * Haalt lijst op van protocollering type.
     * @return Lijst van ProtocolleringType
     */
    public List<Protocollering> getProtocollering() {
        final List<Protocollering> resultaat = new ArrayList<>();
        protocolleringLijstType.getProtocollering().forEach(protocolleringType -> resultaat.add(new Protocollering(protocolleringType)));
        return resultaat;
    }

    /**
     * Voeg Protocollering toe aan bericht.
     * @param protocollering protocollering
     */
    public void addProtocollering(final Protocollering protocollering) {
        protocolleringLijstType.getProtocollering().add(protocollering.getProtocolleringType());
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() {
        return SyncXml.SINGLETON.elementToString(new ObjectFactory().createProtocollering(protocolleringLijstType));
    }
}
