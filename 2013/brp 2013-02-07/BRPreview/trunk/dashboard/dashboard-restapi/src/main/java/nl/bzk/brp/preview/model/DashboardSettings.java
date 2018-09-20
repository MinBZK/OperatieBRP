/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;


/**
 * Deze klasse bevat de instellingen die door het dashboard worden gebruikt. Deze instellingen kunnen worden gewijzigd
 * gedurende de demo en moeten 'on the fly' zichtbaar worden.
 */
@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
@ManagedResource(
        description = "Deze klasse wordt gebruikt om de instelligen van het dashboard scherm dynamisch aan te kunnen passen.",
        objectName = "dashboardSettings")
public class DashboardSettings {

    /**
     * De constante MAX_AANTAL_BERICHTEN.
     */
    public static final int MAX_AANTAL_BERICHTEN_PER_RESPONSE = 100;

    /**
     * De constante MAX_AANTAL_BERICHTEN.
     */
    public static final int MAX_AANTAL_BERICHTEN_TONEN = 7;
    /**
     * De constante DEFAULTPAGINA.
     */
    public static final int DEFAULT_PAGINA             = 1;

    /**
     * De constante DEFAULT_BERICHTEN_VOLLEDIG.
     */
    public static final int DEFAULT_BERICHTEN_VOLLEDIG = 3;

    /** De Constante DEFAULT_RECENTE_BSNS. */
    public static final int DEFAULT_RECENTE_BSNS = 100;

    /**
     * Het maximaal aantal berichten per response.
     */
    private int maximaalAantalBerichtenPerResponse = MAX_AANTAL_BERICHTEN_PER_RESPONSE;

    /**
     * Het maximaal aantal berichten op het scherm.
     */
    private int maximaalAantalBerichtenTonen;

    /**
     * Het aantal berichten dat volledig getoond wordt op het dashboard.
     */
    private int aantalBerichtenVolledig = DEFAULT_BERICHTEN_VOLLEDIG;

    /**
     * Het aantal berichten dat volledig getoond wordt op het dashboard.
     */
    private int aantalRecenteBsnsVoorAutocompletion = DEFAULT_RECENTE_BSNS;

    /**
     * Instantieert nieuwe dashboard settings.
     */
    public DashboardSettings() {
        maximaalAantalBerichtenTonen = MAX_AANTAL_BERICHTEN_TONEN;
    }

    /**
     * Haalt een maximum aantal berichten per response op.
     *
     * @return maximum aantal berichten per response
     */
    public int getMaximumAantalBerichtenPerResponse() {
        return maximaalAantalBerichtenPerResponse;
    }

    /**
     * Zet de maximum aantal berichten per response.
     *
     * @param aantalBerichtenPerResponse de new maximum aantal berichten per response
     */
    @ManagedAttribute(defaultValue = "10")
    public void setMaximumAantalBerichtenPerResponse(final int aantalBerichtenPerResponse) {
        maximaalAantalBerichtenPerResponse = aantalBerichtenPerResponse;
    }

    /**
     * Geef de aantal berichten volledig.
     *
     * @return de aantal berichten volledig
     */
    public int getAantalBerichtenVolledig() {
        return aantalBerichtenVolledig;
    }

    /**
     * Zet de aantal berichten volledig.
     *
     * @param aantalBerichtenVolledig de new aantal berichten volledig
     */
    @ManagedAttribute
    public void setAantalBerichtenVolledig(final int aantalBerichtenVolledig) {
        this.aantalBerichtenVolledig = aantalBerichtenVolledig;
    }

    /**
     * Geef de maximaal aantal berichten tonen.
     *
     * @return de maximaal aantal berichten tonen
     */
    public int getMaximaalAantalBerichtenTonen() {
        return maximaalAantalBerichtenTonen;
    }

    /**
     * Zet de maximum aantal berichten tonen.
     *
     * @param aantalBerichtenTonenOpScherm de new maximum aantal berichten tonen
     */
    @ManagedAttribute(defaultValue = "5")
    public void setMaximumAantalBerichtenTonen(final int aantalBerichtenTonenOpScherm) {
        maximaalAantalBerichtenTonen = aantalBerichtenTonenOpScherm;

    }

    /**
     * Haalt een aantal recente bsns voor autocompletion op.
     *
     * @return aantal recente bsns voor autocompletion
     */
    public int getAantalRecenteBsnsVoorAutocompletion() {
        return aantalRecenteBsnsVoorAutocompletion;
    }

    /**
     * Instellen van aantal recente bsns voor autocompletion.
     *
     * @param aantalRecenteBsnsVoorAutocompletionParam de nieuwe aantal recente bsns voor autocompletion
     */
    public void setAantalRecenteBsnsVoorAutocompletion(final int aantalRecenteBsnsVoorAutocompletionParam) {
        aantalRecenteBsnsVoorAutocompletion = aantalRecenteBsnsVoorAutocompletionParam;
    }

}
