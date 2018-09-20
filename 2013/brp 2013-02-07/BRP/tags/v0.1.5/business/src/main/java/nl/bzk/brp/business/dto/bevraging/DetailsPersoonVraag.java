/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto.bevraging;

/**
 * DTO object voor de vraag om persoon details op te halen.
 */
public class DetailsPersoonVraag extends AbstractVraag {

    private final Integer peilmomentMaterieel = null;
    private final Integer peilmomentFormeel = null;

    public Integer getPeilmomentMaterieel() {
        return peilmomentMaterieel;
    }

    public Integer getPeilmomentFormeel() {
        return peilmomentFormeel;
    }
}
