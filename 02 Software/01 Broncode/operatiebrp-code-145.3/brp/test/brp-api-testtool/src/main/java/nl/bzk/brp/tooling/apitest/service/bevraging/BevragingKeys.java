/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.bevraging;

/**
 * Constanten keys voor geef details persoon en zoek persoon.
 */
final class BevragingKeys {

    /**
     * bsn param.
     */
    static final String BSN          = "bsn";
    /**
     * dienstId param.
     */
    static final String DIENST_ID    = "dienstId";
    /**
     * rolNaam param.
     */
    static final String ROL_NAAM     = "rolNaam";
    /**
     * historievorm param.
     */
    static final String HISTORIEVORM = "historievorm";

    /**
     * scopingElementen param.
     */
    static final String SCOPING_ELEMENTEN = "scopingElementen";

    /**
     * anr param.
     */
    static final String ANR                                       = "anr";
    /**
     * verantwoording param.
     */
    static final String VERANTWOORDING                            = "verantwoording";
    /**
     * peilmomentFormeelResultaat param.
     */
    static final String GEEF_DETAILS_PEILMOMENT_FORMEEL_RESULTAAT = "peilmomentFormeelResultaat";

    /**
     * peilmomentMaterieelResultaat param.
     */
    static final String GEEF_DETAILS_PEILMOMENT_MATERIEEL_RESULTAAT = "peilmomentMaterieelResultaat";

    /**
     * zoekbereik parameter voor zoek persoon.
     */
    static final String ZOEK_PERSOON_ZOEKBEREIK = "zoekbereik";

    /**
     * peilmomentMaterieel param.
     */
    static final String ZOEK_PERSOON_PEILMOMENT_MATERIEEL = "peilmomentMaterieel";

    /**
     * bevraging met objectSleutel.
     */
    static final String OBJECT_SLEUTEL = "objectSleutel";

    private BevragingKeys() {
    }
}
