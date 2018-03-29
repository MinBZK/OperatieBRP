/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.dto;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.brp.domain.internbericht.verzendingmodel.SynchronisatieBerichtGegevens;

/**
 * Representatie van een bericht dat normaal gesproken op de JMS-afnemerqueue belandt.
 */
public final class OntvangenBericht {

    private final String                        uitgaandeXmlBericht;
    private final SynchronisatieBerichtGegevens metaGegevens;
    private final ToegangLeveringsAutorisatie   toegangLeveringsautorisatie;

    /**
     * Constructor.
     *
     * @param uitgaandeXmlBericht         XML van bericht
     * @param metaGegevens                de meta gegevens
     * @param toegangLeveringsautorisatie de autorisatie
     */
    public OntvangenBericht(final String uitgaandeXmlBericht, final SynchronisatieBerichtGegevens metaGegevens,
        final ToegangLeveringsAutorisatie toegangLeveringsautorisatie)
    {
        this.uitgaandeXmlBericht = uitgaandeXmlBericht;
        this.metaGegevens = metaGegevens;
        this.toegangLeveringsautorisatie = toegangLeveringsautorisatie;
    }

    /**
     * @return XML van bericht
     */
    public String getUitgaandeXmlBericht() {
        return uitgaandeXmlBericht;
    }

    /**
     * @return meta gegevens
     */
    public SynchronisatieBerichtGegevens getMetaGegevens() {
        return metaGegevens;
    }

    /**
     * @return de autorisatie
     */
    public ToegangLeveringsAutorisatie getToegangLeveringsautorisatie() {
        return toegangLeveringsautorisatie;
    }


}
