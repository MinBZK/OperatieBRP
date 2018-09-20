/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 *
 */
package nl.bzk.brp.preview.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


/**
 * Deze klasse bevat de statistieken die zijn bijgehouden voor de berichten.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Statistieken implements Serializable {

    /** De Constante serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** De aantal prevalidaties geboorte. */
    private int               aantalPrevalidatiesGeboorte;

    /** De aantal prevalidaties verhuis. */
    private int               aantalPrevalidatiesVerhuis;

    /** De aantal prevalidaties huwelijk. */
    private int               aantalPrevalidatiesHuwelijk;

    /** De aantal prevalidaties adrescorrectie. */
    private int               aantalPrevalidatiesAdrescorrectie;

    /** De aantal geboorte berichten. */
    private int               aantalGeboorteBerichten;

    /** De aantal verhuis berichten. */
    private int               aantalVerhuisBerichten;

    /** De aantal huwelijk berichten. */
    private int               aantalHuwelijkBerichten;

    /** De aantal adrescorrectie berichten. */
    private int               aantalAdrescorrectieBerichten;

    /**
     * Instantieert een nieuwe statistieken.
     */
    public Statistieken() {
    }

    /**
     * Haalt een aantal verhuis berichten op.
     *
     * @return aantal verhuis berichten
     */
    public int getAantalVerhuisBerichten() {
        return aantalVerhuisBerichten;
    }

    /**
     * Instellen van aantal verhuis berichten.
     *
     * @param aantalVerhuisBerichten de nieuwe aantal verhuis berichten
     */
    public void setAantalVerhuisBerichten(final int aantalVerhuisBerichten) {
        this.aantalVerhuisBerichten = aantalVerhuisBerichten;
    }

    /**
     * Haalt een aantal geboorte berichten op.
     *
     * @return aantal geboorte berichten
     */
    public int getAantalGeboorteBerichten() {
        return aantalGeboorteBerichten;
    }

    /**
     * Instellen van aantal geboorte berichten.
     *
     * @param aantalGeboorteBerichten de nieuwe aantal geboorte berichten
     */
    public void setAantalGeboorteBerichten(final int aantalGeboorteBerichten) {
        this.aantalGeboorteBerichten = aantalGeboorteBerichten;
    }

    /**
     * Haalt een aantal prevalidaties geboorte op.
     *
     * @return aantal prevalidaties geboorte
     */
    public int getAantalPrevalidatiesGeboorte() {
        return aantalPrevalidatiesGeboorte;
    }

    /**
     * Instellen van aantal prevalidaties.
     *
     * @param aantalPrevalidatiesGeboorteParam de nieuwe aantal prevalidaties
     */
    public void setAantalPrevalidaties(final int aantalPrevalidatiesGeboorteParam) {
        aantalPrevalidatiesGeboorte = aantalPrevalidatiesGeboorteParam;
    }

    /**
     * Haalt een aantal huwelijk berichten op.
     *
     * @return aantal huwelijk berichten
     */
    public int getAantalHuwelijkBerichten() {
        return aantalHuwelijkBerichten;
    }

    /**
     * Instellen van aantal huwelijk berichten.
     *
     * @param aantalHuwelijkBerichten de nieuwe aantal huwelijk berichten
     */
    public void setAantalHuwelijkBerichten(final int aantalHuwelijkBerichten) {
        this.aantalHuwelijkBerichten = aantalHuwelijkBerichten;
    }

    /**
     * Haalt een aantal prevalidaties verhuis op.
     *
     * @return aantal prevalidaties verhuis
     */
    public int getAantalPrevalidatiesVerhuis() {
        return aantalPrevalidatiesVerhuis;
    }

    /**
     * Instellen van aantal prevalidaties verhuis.
     *
     * @param aantalPrevalidatiesVerhuis de nieuwe aantal prevalidaties verhuis
     */
    public void setAantalPrevalidatiesVerhuis(final int aantalPrevalidatiesVerhuis) {
        this.aantalPrevalidatiesVerhuis = aantalPrevalidatiesVerhuis;
    }

    /**
     * Haalt een aantal prevalidaties huwelijk op.
     *
     * @return aantal prevalidaties huwelijk
     */
    public int getAantalPrevalidatiesHuwelijk() {
        return aantalPrevalidatiesHuwelijk;
    }

    /**
     * Instellen van aantal prevalidaties huwelijk.
     *
     * @param aantalPrevalidatiesHuwelijk de nieuwe aantal prevalidaties huwelijk
     */
    public void setAantalPrevalidatiesHuwelijk(final int aantalPrevalidatiesHuwelijk) {
        this.aantalPrevalidatiesHuwelijk = aantalPrevalidatiesHuwelijk;
    }

    /**
     * Haalt een aantal adrescorrectie berichten op.
     *
     * @return aantal adrescorrectie berichten
     */
    public int getAantalAdrescorrectieBerichten() {
        return aantalAdrescorrectieBerichten;
    }

    /**
     * Instellen van aantal adrescorrectie berichten.
     *
     * @param aantalAdrescorrectieBerichten de nieuwe aantal adrescorrectie berichten
     */
    public void setAantalAdrescorrectieBerichten(final int aantalAdrescorrectieBerichten) {
        this.aantalAdrescorrectieBerichten = aantalAdrescorrectieBerichten;
    }

    /**
     * Haalt een aantal prevalidaties adrescorrectie op.
     *
     * @return aantal prevalidaties adrescorrectie
     */
    public int getAantalPrevalidatiesAdrescorrectie() {
        return aantalPrevalidatiesAdrescorrectie;
    }

    /**
     * Instellen van aantal prevalidaties adrescorrectie.
     *
     * @param aantalPrevalidatiesAdrescorrectie de nieuwe aantal prevalidaties adrescorrectie
     */
    public void setAantalPrevalidatiesAdrescorrectie(final int aantalPrevalidatiesAdrescorrectie) {
        this.aantalPrevalidatiesAdrescorrectie = aantalPrevalidatiesAdrescorrectie;
    }
}
