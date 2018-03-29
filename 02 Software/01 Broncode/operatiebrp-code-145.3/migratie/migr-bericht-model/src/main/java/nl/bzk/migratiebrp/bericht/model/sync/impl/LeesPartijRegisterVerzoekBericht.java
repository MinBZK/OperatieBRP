/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import nl.bzk.migratiebrp.bericht.model.sync.AbstractSyncBerichtZonderGerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.sync.generated.LeesPartijRegisterVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.sync.xml.SyncXml;

/**
 * Lees gemeente register verzoek.
 */
public final class LeesPartijRegisterVerzoekBericht extends AbstractSyncBerichtZonderGerelateerdeInformatie {
    private static final long serialVersionUID = 1L;

    private final LeesPartijRegisterVerzoekType leesPartijRegisterVerzoekType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public LeesPartijRegisterVerzoekBericht() {
        this(new LeesPartijRegisterVerzoekType());
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een storebericht te maken.
     * @param leesPartijRegisterVerzoekType het jaxb element
     */
    public LeesPartijRegisterVerzoekBericht(final LeesPartijRegisterVerzoekType leesPartijRegisterVerzoekType) {
        super("LeesPartijRegisterVerzoek");
        this.leesPartijRegisterVerzoekType = leesPartijRegisterVerzoekType;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() {
        return SyncXml.SINGLETON.elementToString(new ObjectFactory().createLeesPartijRegisterVerzoek(leesPartijRegisterVerzoekType));
    }

}
