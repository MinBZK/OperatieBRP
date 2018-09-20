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


/**
 * Deze klasse bevat de statistieken die zijn bijgehouden voor de berichten.
 *
 */
public class Statistieken implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private int               aantalPrevalidatiesGeboorte;
    private int               aantalPrevalidatiesVerhuis;
    private int               aantalPrevalidatiesHuwelijk;
    private int               aantalPrevalidatiesAdrescorrectie;
    private int               aantalGeboorteBerichten;
    private int               aantalVerhuisBerichten;
    private int               aantalHuwelijkBerichten;
    private int               aantalAdrescorrectieBerichten;

    public Statistieken() {
    }

    public int getAantalVerhuisBerichten() {
        return aantalVerhuisBerichten;
    }

    public void setAantalVerhuisBerichten(final int aantalVerhuisBerichten) {
        this.aantalVerhuisBerichten = aantalVerhuisBerichten;
    }

    public int getAantalGeboorteBerichten() {
        return aantalGeboorteBerichten;
    }

    public void setAantalGeboorteBerichten(final int aantalGeboorteBerichten) {
        this.aantalGeboorteBerichten = aantalGeboorteBerichten;
    }

    public int getAantalPrevalidatiesGeboorte() {
        return aantalPrevalidatiesGeboorte;
    }

    public void setAantalPrevalidaties(final int aantalPrevalidatiesGeboorte) {
        this.aantalPrevalidatiesGeboorte = aantalPrevalidatiesGeboorte;
    }

    public int getAantalHuwelijkBerichten() {
        return aantalHuwelijkBerichten;
    }

    public void setAantalHuwelijkBerichten(int aantalHuwelijkBerichten) {
        this.aantalHuwelijkBerichten = aantalHuwelijkBerichten;
    }

    public int getAantalPrevalidatiesVerhuis() {
        return aantalPrevalidatiesVerhuis;
    }

    public void setAantalPrevalidatiesVerhuis(int aantalPrevalidatiesVerhuis) {
        this.aantalPrevalidatiesVerhuis = aantalPrevalidatiesVerhuis;
    }

    public int getAantalPrevalidatiesHuwelijk() {
        return aantalPrevalidatiesHuwelijk;
    }

    public void setAantalPrevalidatiesHuwelijk(int aantalPrevalidatiesHuwelijk) {
        this.aantalPrevalidatiesHuwelijk = aantalPrevalidatiesHuwelijk;
    }
    public int getAantalAdrescorrectieBerichten() {
        return aantalAdrescorrectieBerichten;
    }

    public void setAantalAdrescorrectieBerichten(int aantalAdrescorrectieBerichten) {
        this.aantalAdrescorrectieBerichten = aantalAdrescorrectieBerichten;
    }

    public int getAantalPrevalidatiesAdrescorrectie() {
        return aantalPrevalidatiesAdrescorrectie;
    }

    public void setAantalPrevalidatiesAdrescorrectie(int aantalPrevalidatiesAdrescorrectie) {
        this.aantalPrevalidatiesAdrescorrectie = aantalPrevalidatiesAdrescorrectie;
    }
}
