/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.syntax;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementMap;

/**
 * Een categorie met zijn elementen. <b>Let op:</b> de equals() methode vergelijkt alleen op categorie, niet op de
 * inhoud van de elementen.
 */
public final class Lo3CategorieWaarde implements Serializable {

    /** Default stapelnummer. */
    public static final int DEFAULT_STAPEL = -1;
    /** Default voorkomenvolgnummer. */
    public static final int DEFAULT_VOORKOMEN = -1;

    private static final long serialVersionUID = 1L;
    private static final String ELEMENT_LABEL = "element";

    private final Lo3CategorieEnum categorie;
    private final int stapel;
    private final int voorkomen;

    private final Map<Lo3ElementEnum, String> elementen;

    /**
     * Maak een CategorieWaarde.
     *
     * @param categorie
     *            categorie
     * @param stapel
     *            stapel voorkomen
     * @param voorkomen
     *            categorie voorkomen (binnen de stapel)
     * @param elementen
     *            elementen
     */
    public Lo3CategorieWaarde(@Attribute(name = "categorie", required = false) final Lo3CategorieEnum categorie, @Attribute(name = "stapel",
            required = false) final int stapel, @Attribute(name = "voorkomen", required = false) final int voorkomen, @ElementMap(inline = true,
            entry = ELEMENT_LABEL, key = ELEMENT_LABEL, attribute = true, value = "waarde", required = false) final Map<Lo3ElementEnum, String> elementen)
    {
        this.categorie = categorie;
        this.stapel = stapel;
        this.voorkomen = voorkomen;

        if (elementen == null) {
            this.elementen = new HashMap<>();
        } else {
            this.elementen = elementen;
        }
    }

    /**
     * Maakt een CategorieWaarde.
     *
     * @param categorie
     *            categorie
     * @param stapel
     *            stapel voorkomen
     * @param voorkomen
     *            categorie voorkomen (binnen de stapel)
     */
    public Lo3CategorieWaarde(final Lo3CategorieEnum categorie, final int stapel, final int voorkomen) {
        this.categorie = categorie;
        this.stapel = stapel;
        this.voorkomen = voorkomen;
        elementen = new HashMap<>();
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
            elementen.remove(element);
        } else {
            elementen.put(element, waarde);
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
        return elementen.get(element);
    }

    /**
     * Geef de waarde van categorie.
     *
     * @return the categorie
     */
    @Attribute(name = "categorie", required = false)
    public Lo3CategorieEnum getCategorie() {
        return categorie;
    }

    /**
     * Geef de waarde van stapel.
     *
     * @return the stapel
     */
    @Attribute(name = "stapel", required = false)
    public int getStapel() {
        return stapel;
    }

    /**
     * Geeft de Lo3 herkomst terug.
     *
     * @return de Lo3 herkomst.
     */
    public Lo3Herkomst getLo3Herkomst() {
        return new Lo3Herkomst(categorie, stapel, voorkomen);
    }

    /**
     * Geef de waarde van voorkomen.
     *
     * @return the voorkomen
     */
    @Attribute(name = "voorkomen", required = false)
    public int getVoorkomen() {
        return voorkomen;
    }

    /**
     * Geef de empty.
     *
     * @return true als deze categoriewaarde geen elementen bevat, anders false.
     */
    public boolean isEmpty() {
        return elementen.isEmpty();
    }

    /**
     * Geef de gevuld.
     *
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

    /**
     * Geef de waarde van elementen.
     *
     * @return elementen
     */
    @ElementMap(inline = true, entry = ELEMENT_LABEL, key = ELEMENT_LABEL, attribute = true, value = "waarde", required = false)
    public Map<Lo3ElementEnum, String> getElementen() {
        return elementen;
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
        result = prime * result + (categorie == null ? 0 : categorie.hashCode());
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
        if (categorie != other.categorie) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("categorie", categorie).append("elementen", elementen).toString();
    }

}
