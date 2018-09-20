/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import nl.bzk.migratiebrp.bericht.model.sync.AbstractSyncBerichtZonderGerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.sync.generated.LeesGemeenteRegisterVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.sync.xml.SyncXml;

/**
 * Lees gemeente register verzoek.
 */
public final class LeesGemeenteRegisterVerzoekBericht extends AbstractSyncBerichtZonderGerelateerdeInformatie {
    private static final long serialVersionUID = 1L;

    private final LeesGemeenteRegisterVerzoekType leesGemeenteRegisterVerzoekType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public LeesGemeenteRegisterVerzoekBericht() {
        this(new LeesGemeenteRegisterVerzoekType());
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een storebericht te maken.
     *
     * @param leesGemeenteRegisterVerzoekType
     *            het jaxb element
     */
    public LeesGemeenteRegisterVerzoekBericht(final LeesGemeenteRegisterVerzoekType leesGemeenteRegisterVerzoekType) {
        super("LeesGemeenteRegisterVerzoek");
        this.leesGemeenteRegisterVerzoekType = leesGemeenteRegisterVerzoekType;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() {
        return SyncXml.SINGLETON.elementToString(new ObjectFactory().createLeesGemeenteRegisterVerzoek(leesGemeenteRegisterVerzoekType));
    }

}
