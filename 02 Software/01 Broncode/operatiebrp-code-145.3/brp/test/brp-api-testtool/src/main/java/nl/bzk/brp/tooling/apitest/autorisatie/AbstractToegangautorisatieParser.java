/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.autorisatie;


import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.test.common.dsl.DslSectie;

/**
 * Abstracte basis voor Toegangautorisaties.
 */
abstract class AbstractToegangautorisatieParser {

    //= partijNaam
    private final String  geautoriseerde;
    private final String  ondertekenaar;
    private final String  transporteur;
    private final Integer datingang;
    private final Integer dateinde;
    private final Boolean indblok;
    private final Boolean indag;
    private final Integer rol;

    public AbstractToegangautorisatieParser(final DslSectie sectie) {
        geautoriseerde = sectie.geefStringValue("geautoriseerde").orElse(null);
        transporteur = sectie.geefStringValue("transporteur").orElse(null);
        dateinde = sectie.geefDatumInt("dateinde").orElse(null);
        ondertekenaar = sectie.geefStringValue("ondertekenaar").orElse(null);
        datingang = sectie.geefDatumInt("datingang").orElse(DatumUtil.gisteren());
        indblok = sectie.geefBooleanValue("indblok").orElse(null);
        indag = sectie.geefBooleanValue("indag").orElse(null);
        rol = sectie.geefInteger("rol").orElse(1);
    }

    abstract int getId();

    String getGeautoriseerde() {
        return geautoriseerde;
    }

    String getOndertekenaar() {
        return ondertekenaar;
    }

    String getTransporteur() {
        return transporteur;
    }

    Integer getDatingang() {
        return datingang;
    }

    Integer getDateinde() {
        return dateinde;
    }

    Boolean isIndblok() {
        return indblok;
    }

    Boolean isIndag() {
        return indag;
    }

    Integer getRol() {
        return rol;
    }
}
