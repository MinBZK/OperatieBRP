/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.utils;

public enum SoortBevraging {

    DETAILS_PERSOON("details-persoon", "1a-template-bevraging-details-persoon-response.xml"),
    KANDIDAAT_VADER("kandidaat-vader", "1b-template-bevraging-kandidaat-vader-response.xml"),
    PERSONEN_OP_ADRES("personen-op-adres", "1c-template-bevraging-personen-op-adres-response.xml"),
    ZOEK_PERSOON("tsoek-persoon", "");

    private String mapnaam;
    private String templateNaam;

    private SoortBevraging(final String mapnaam, final String templateNaam) {
        this.mapnaam = mapnaam;
        this.templateNaam = templateNaam;
    }

    public String getMapnaam() {
        return mapnaam;
    }

    public String getTemplateNaam() {
        return templateNaam;
    }

}
