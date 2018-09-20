/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 *
 */
package nl.bzk.brp.preview.model;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;


/**
 * Deze klasse bevat de instellingen die door het dashboard worden gebruikt. Deze instellingen kunnen worden gewijzigd
 * gedurende de demo en moeten 'on the fly' zichtbaar worden.
 */
@Component
@ManagedResource(
                 description = "Deze klasse wordt gebruikt om de instelligen van het dashboard scherm dynamisch aan te kunnen passen.",
                 objectName = "dashboardSettings")
public class DashboardSettings {

    /** De constante MAX_AANTAL_BERICHTEN. */
    private static final int MAX_AANTAL_BERICHTEN               = 5;

    /** De constante DEFAULTPAGINA. */
    public static final int  DEFAULT_PAGINA                     = 1;

    /** Het maximaal aantal berichten per response. */
    private int              maximaalAantalBerichtenPerResponse = MAX_AANTAL_BERICHTEN;

    /** Het aantal berichten dat volledig getoond wordt op het dashboard. */
    private int              aantalBerichtenVolledig = 1;

    public int getMaximumAantalBerichtenPerResponse() {
        return maximaalAantalBerichtenPerResponse;
    }

    @ManagedAttribute(defaultValue = "10")
    public void setMaximumAantalBerichtenPerResponse(final int aantalBerichtenPerResponse) {
        maximaalAantalBerichtenPerResponse = aantalBerichtenPerResponse;
    }

    public int getAantalBerichtenVolledig() {
        return aantalBerichtenVolledig;
    }

    @ManagedAttribute
    public void setAantalBerichtenVolledig(final int aantalBerichtenVolledig) {
        this.aantalBerichtenVolledig = aantalBerichtenVolledig;
    }

}
