/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import java.util.List;
import java.util.stream.Collectors;

import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.sync.AbstractSyncBerichtZonderGerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ProtocolleringAntwoordLijstType;
import nl.bzk.migratiebrp.bericht.model.sync.xml.SyncXml;

/**
 * Antwoord bericht voor initiele vulling protocollering.
 */
public final class ProtocolleringAntwoordBericht extends AbstractSyncBerichtZonderGerelateerdeInformatie {

    private static final long serialVersionUID = 1L;

    private final ProtocolleringAntwoordLijstType protocolleringAntwoordLijstType;

    /**
     * Default constructor.
     */
    public ProtocolleringAntwoordBericht() {
        this(new ProtocolleringAntwoordLijstType());
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een bericht te maken.
     * @param protocolleringAntwoordLijstType protocolleringantwoord type
     */
    public ProtocolleringAntwoordBericht(final ProtocolleringAntwoordLijstType protocolleringAntwoordLijstType) {
        super("ProtocolleringAntwoord");
        this.protocolleringAntwoordLijstType = protocolleringAntwoordLijstType;
    }

    /* ************************************************************************************************************* */

    /**
     * Haal lijst op van protocollering antwoord.
     * @return lijst met protocolleringAntwoorden
     */
    public List<ProtocolleringAntwoord> getProtocolleringAntwoord() {
        return protocolleringAntwoordLijstType.getProtocolleringAntwoord().stream().map(ProtocolleringAntwoord::new).collect(Collectors.toList());
    }

    /**
     * Voeg ProtocolleringAntwoord toe aan bericht.
     * @param protocolleringAntwoord protocolleringAntwoord
     */
    public void addProtocolleringAntwoord(final ProtocolleringAntwoord protocolleringAntwoord) {
        protocolleringAntwoordLijstType.getProtocolleringAntwoord().add(protocolleringAntwoord.getProtocolleringAntwoordType());
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return SyncXml.SINGLETON.elementToString(new ObjectFactory().createProtocolleringAntwoord(protocolleringAntwoordLijstType));
    }
}
