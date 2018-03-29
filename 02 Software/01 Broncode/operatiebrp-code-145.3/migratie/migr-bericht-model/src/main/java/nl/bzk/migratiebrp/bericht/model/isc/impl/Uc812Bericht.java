/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.isc.impl;

import java.util.Collections;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.GerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.isc.AbstractIscBericht;
import nl.bzk.migratiebrp.bericht.model.isc.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.isc.generated.Uc812Type;
import nl.bzk.migratiebrp.bericht.model.isc.xml.IscXml;

/**
 * Uc812 bericht.
 */
public final class Uc812Bericht extends AbstractIscBericht {
    private static final long serialVersionUID = 1L;

    private final Uc812Type uc812Type;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public Uc812Bericht() {
        uc812Type = new Uc812Type();
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een bericht te maken.
     * @param uc812Type het uc812Type type
     */
    public Uc812Bericht(final Uc812Type uc812Type) {
        this.uc812Type = uc812Type;
    }

    /* ************************************************************************************************************* */

    @Override
    public String getBerichtType() {
        return "Uc812";
    }

    /* ************************************************************************************************************* */

    /**
     * Geeft de bulk synchronisatievraag.
     * @return De bulk synchronisatievraag.
     */
    public String getBulkSynchronisatievraag() {
        return uc812Type.getBulkSynchronisatievraag();
    }

    /**
     * Zet de bulk synchronisatievraag.
     * @param bulkSynchronisatievraag De te zetten bulk synchronisatievraag
     */
    public void setBulkSynchronisatievraag(final String bulkSynchronisatievraag) {
        uc812Type.setBulkSynchronisatievraag(bulkSynchronisatievraag);
    }

    /* ************************************************************************************************************* */

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.bericht.model.Bericht#getGerelateerdeInformatie()
     */
    @Override
    public GerelateerdeInformatie getGerelateerdeInformatie() {
        return new GerelateerdeInformatie(null, Collections.<String>emptyList(), Collections.<String>emptyList());
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return IscXml.SINGLETON.elementToString(new ObjectFactory().createUc812(uc812Type));
    }

}
