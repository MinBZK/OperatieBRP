/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import nl.bzk.migratiebrp.bericht.model.sync.AbstractSyncBerichtZonderGerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.sync.generated.LeesAutorisatieRegisterVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.sync.xml.SyncXml;

/**
 * Lees autorisatie register verzoek.
 */
public final class LeesAutorisatieRegisterVerzoekBericht extends AbstractSyncBerichtZonderGerelateerdeInformatie {

    private static final long serialVersionUID = 1L;

    private final LeesAutorisatieRegisterVerzoekType leesAutorisatieRegisterVerzoekType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public LeesAutorisatieRegisterVerzoekBericht() {
        this(new LeesAutorisatieRegisterVerzoekType());
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een storebericht te maken.
     *
     * @param leesAutorisatieRegisterVerzoekType
     *            het jaxb element
     */
    public LeesAutorisatieRegisterVerzoekBericht(final LeesAutorisatieRegisterVerzoekType leesAutorisatieRegisterVerzoekType) {
        super("LeesAutorisatieRegisterVerzoek");
        this.leesAutorisatieRegisterVerzoekType = leesAutorisatieRegisterVerzoekType;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() {
        return SyncXml.SINGLETON.elementToString(new ObjectFactory().createLeesAutorisatieRegisterVerzoek(leesAutorisatieRegisterVerzoekType));
    }

}
