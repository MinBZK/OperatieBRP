/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.datataal.leveringautorisatie;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;

/**
 * Abstracte basis voor Toegangautorisaties.
 */
public abstract class AbstractToegangautorisatieDsl {

    //= partijNaam
    private final String geautoriseerde;
    private final String ondertekenaar;
    private final String transporteur;
    private final Integer datingang;
    private final Integer dateinde;
    private final Boolean indblok;

    public AbstractToegangautorisatieDsl(final DslSectie sectie) {
        geautoriseerde = sectie.geefStringValue("geautoriseerde");
        transporteur = sectie.geefStringValue("transporteur");
        dateinde = sectie.geefDatumInt("dateinde");
        ondertekenaar = sectie.geefStringValue("ondertekenaar");
        datingang = sectie.geefDatumIntOfDefault("datingang", DatumAttribuut.gisteren());
        indblok = sectie.geefBooleanValue("indblok");
    }

    public abstract int getId();

    public String getGeautoriseerde() {
        return geautoriseerde;
    }

    public String getOndertekenaar() {
        return ondertekenaar;
    }

    public String getTransporteur() {
        return transporteur;
    }

    public Integer getDatingang() {
        return datingang;
    }

    public Integer getDateinde() {
        return dateinde;
    }

    public Boolean isIndblok() {
        return indblok;
    }
}
