/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonPropertyOrder;


/**
 * De klasse BerichtenResponse vormt de basis voor de JSON response waarin de instellingen voor het dashboard en de
 * berichten die getoond moeten worden zitten.
 */
@JsonPropertyOrder(value = { "pagina", "maximumAantalBerichtenPerResponse", "aantalBerichtenVolledig", "berichten" })
@JsonIgnoreProperties(ignoreUnknown = true)
public class BerichtenResponse {

    /** De pagina (nummer) die wordt teruggegeven (voor het kunnen navigeren tussen meldingen). */
    private int           pagina = DashboardSettings.DEFAULT_PAGINA;

    /** Het aantal berichten per response. */
    private int           maximumAantalBerichtenPerResponse;

    /** Het aantal berichten per pagina. */
    private int           maximumAantalBerichtenPerPagina = DashboardSettings.MAX_AANTAL_BERICHTEN_TONEN;

    /** Het aantal berichten dat volledig getoond wordt. */
    private int           aantalBerichtenVolledig;

    /** De berichten. */
    private List<Bericht> berichten;

    private Statistieken statistieken;

    public int getPagina() {
        return pagina;
    }

    public void setPagina(final int pagina) {
        this.pagina = pagina;
    }


    /**
     * Geef de maximum aantal berichten per response.
     *
     * @return de maximum aantal berichten per response
     */
    public int getMaximumAantalBerichtenPerResponse() {
        return maximumAantalBerichtenPerResponse;
    }

    public void setMaximumAantalBerichtenPerResponse(final int aantalPerPagina) {
        maximumAantalBerichtenPerResponse = aantalPerPagina;
    }

    /**
     * Haal de berichten op.
     *
     * @return the berichten
     */
    public List<Bericht> getBerichten() {
        return berichten;
    }

    /**
     * Zet de berichten.
     *
     * @param berichten the new berichten
     */
    public void setBerichten(final List<Bericht> berichten) {
        this.berichten = new ArrayList<Bericht>(berichten);
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
    public void setAantalBerichtenVolledig(final int aantalBerichtenVolledig) {
        this.aantalBerichtenVolledig = aantalBerichtenVolledig;
    }

    /**
     * Geeft het aantal berichten dat wordt teruggegeven.
     *
     * @return het aantal berichten
     */
    public int getAantalBerichten() {
        if (berichten == null) {
            return 0;
        }
        return berichten.size();
    }

    /**
     * Geef de maximum aantal berichten per pagina.
     *
     * @return de maximum aantal berichten per pagina
     */
    public int getMaximumAantalBerichtenPerPagina() {
        return maximumAantalBerichtenPerPagina;
    }

    /**
     * Zet de maximum aantal berichten per pagina.
     *
     * @param maximumAantalBerichtenPerPagina de new maximum aantal berichten per pagina
     */
    public void setMaximumAantalBerichtenPerPagina(final int maximumAantalBerichtenPerPagina) {
        this.maximumAantalBerichtenPerPagina = maximumAantalBerichtenPerPagina;
    }

    public Statistieken getStatistieken() {
        return statistieken;
    }

    public void setStatistieken(final Statistieken berichtStatistieken) {
        statistieken = berichtStatistieken;
    }

}
