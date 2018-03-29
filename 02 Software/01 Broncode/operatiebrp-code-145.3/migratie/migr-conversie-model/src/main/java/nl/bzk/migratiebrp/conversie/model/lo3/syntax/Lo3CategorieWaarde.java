/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.syntax;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import nl.bzk.algemeenbrp.util.xml.annotation.Attribute;
import nl.bzk.algemeenbrp.util.xml.annotation.ElementMap;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Een categorie met zijn elementen. <b>Let op:</b> de equals() methode vergelijkt alleen op categorie, niet op de
 * inhoud van de elementen.
 */
public final class Lo3CategorieWaarde implements Serializable {

    /**
     * Default stapelnummer.
     */
    public static final int DEFAULT_STAPEL = -1;
    /**
     * Default voorkomenvolgnummer.
     */
    public static final int DEFAULT_VOORKOMEN = -1;

    private static final long serialVersionUID = 1L;
    private static final String ELEMENT_LABEL = "element";

    private final Lo3CategorieEnum categorie;
    private final int stapel;
    private final int voorkomen;

    private final Map<Lo3ElementEnum, String> elementen;

    /**
     * Maak een CategorieWaarde.
     * @param categorie categorie
     * @param stapel stapel voorkomen
     * @param voorkomen categorie voorkomen (binnen de stapel)
     * @param elementen elementen
     */
    public Lo3CategorieWaarde(
            @Attribute(name = "categorie") final Lo3CategorieEnum categorie,
            @Attribute(name = "stapel") final int stapel,
            @Attribute(name = "voorkomen") final int voorkomen,
            @ElementMap(name = "elementen", inline = true, entry = ELEMENT_LABEL, key = ELEMENT_LABEL, keyType = Lo3ElementEnum.class, attribute = true,
                    value = "waarde", valueType = String.class) final Map<Lo3ElementEnum, String> elementen) {
        this.categorie = categorie;
        this.stapel = stapel;
        this.voorkomen = voorkomen;

        if (elementen == null) {
            this.elementen = new EnumMap<>(Lo3ElementEnum.class);
        } else {
            this.elementen = elementen;
        }
    }

    /**
     * Maakt een CategorieWaarde.
     * @param categorie categorie
     * @param stapel stapel voorkomen
     * @param voorkomen categorie voorkomen (binnen de stapel)
     */
    public Lo3CategorieWaarde(final Lo3CategorieEnum categorie, final int stapel, final int voorkomen) {
        this.categorie = categorie;
        this.stapel = stapel;
        this.voorkomen = voorkomen;
        elementen = new EnumMap<>(Lo3ElementEnum.class);
    }

    /**
     * Voeg een element toe met de meegegeven waarde.
     * @param element het element type
     * @param waarde de waarde
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
     * @param element het element type
     * @return de waarde
     */
    public String getElement(final Lo3ElementEnum element) {
        return elementen.get(element);
    }

    /**
     * Geef de waarde van categorie.
     * @return the categorie
     */
    @Attribute(name = "categorie")
    public Lo3CategorieEnum getCategorie() {
        return categorie;
    }

    /**
     * Geef de waarde van stapel.
     * @return the stapel
     */
    @Attribute(name = "stapel")
    public int getStapel() {
        return stapel;
    }

    /**
     * Geeft de Lo3 herkomst terug.
     * @return de Lo3 herkomst.
     */
    public Lo3Herkomst getLo3Herkomst() {
        return new Lo3Herkomst(categorie, stapel, voorkomen);
    }

    /**
     * Geef de waarde van voorkomen.
     * @return the voorkomen
     */
    @Attribute(name = "voorkomen")
    public int getVoorkomen() {
        return voorkomen;
    }

    /**
     * Geef de empty.
     * @return true als deze categoriewaarde geen elementen bevat, anders false.
     */
    public boolean isEmpty() {
        return elementen.isEmpty();
    }

    /**
     * Geef de gevuld.
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
     * @return elementen
     */
    @ElementMap(name = "elementen", inline = true, entry = ELEMENT_LABEL, key = ELEMENT_LABEL, keyType = Lo3ElementEnum.class, attribute = true,
            value = "waarde", valueType = String.class)
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

        if (!(obj instanceof Lo3CategorieWaarde)) {
            return false;
        }
        final Lo3CategorieWaarde other = (Lo3CategorieWaarde) obj;
        return categorie == other.categorie;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("categorie", categorie).append("elementen", elementen).toString();
    }
}
