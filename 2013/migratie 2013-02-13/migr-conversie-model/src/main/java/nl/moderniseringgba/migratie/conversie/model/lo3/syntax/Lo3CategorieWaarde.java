/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.lo3.syntax;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Een categorie met zijn elementen. <b>Let op:</b> de equals() methode vergelijkt alleen op categorie, niet op de
 * inhoud van de elementen.
 */
public final class Lo3CategorieWaarde implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Lo3CategorieEnum categorie;
    private final int stapel;
    private final int voorkomen;

    private final Map<Lo3ElementEnum, String> elementen;

    /**
     * Maakt een CategorieWaarde.
     * 
     * @param categorie
     *            categorie
     */
    public Lo3CategorieWaarde(final Lo3CategorieEnum categorie, final int stapel, final int voorkomen) {
        this.categorie = categorie;
        this.stapel = stapel;
        this.voorkomen = voorkomen;
        this.elementen = new HashMap<Lo3ElementEnum, String>();
    }

    /**
     * Voeg een element toe met de meegegeven waarde.
     * 
     * @param element
     *            het element type
     * @param waarde
     *            de waarde
     */
    public void addElement(final Lo3ElementEnum element, final String waarde) {
        if (waarde == null) {
            this.elementen.remove(element);
        } else {
            this.elementen.put(element, waarde);
        }
    }

    /**
     * Geef de waarde van een specifiek element.
     * 
     * @param element
     *            het element type
     * @return de waarde
     */
    public String getElement(final Lo3ElementEnum element) {
        return this.elementen.get(element);
    }

    /**
     * @return the categorie
     */
    public Lo3CategorieEnum getCategorie() {
        return this.categorie;
    }

    /**
     * @return the stapel
     */
    public int getStapel() {
        return stapel;
    }

    public Lo3Herkomst getLo3Herkomst() {
        return new Lo3Herkomst(categorie, stapel, voorkomen);
    }

    /**
     * @return the voorkomen
     */
    public int getVoorkomen() {
        return voorkomen;
    }

    /**
     * @return true als deze categoriewaarde geen elementen bevat, anders false.
     */
    public boolean isEmpty() {
        return this.elementen.isEmpty();
    }

    /**
     * @return true als deze categorie minimaal 1 element bevat met een gevulde waarde.
     */
    public boolean isGevuld() {
        for (final String value : elementen.values()) {
            if (value != null && !"".equals(value)) {
                return true;
            }
        }

        return false;
    }

    public Map<Lo3ElementEnum, String> getElementen() {
        return this.elementen;
    }

    /**
     * Afwijkende equals en hashCode: elementen wordt genegeerd
     */
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.categorie == null) ? 0 : this.categorie.hashCode());
        return result;
    }

    /**
     * Afwijkende equals en hashCode: elementen wordt genegeerd
     */
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Lo3CategorieWaarde)) {
            return false;
        }
        final Lo3CategorieWaarde other = (Lo3CategorieWaarde) obj;
        if (this.categorie != other.categorie) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("categorie", this.categorie)
                .append("elementen", this.elementen).toString();
    }

}
