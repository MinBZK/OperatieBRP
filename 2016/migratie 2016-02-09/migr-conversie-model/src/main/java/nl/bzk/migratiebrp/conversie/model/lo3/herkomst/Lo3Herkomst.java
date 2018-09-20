/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.herkomst;

import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * De herkomst vanuit een lo3 bericht wordt vastgelegd door de categorie, stapelnummer en categorievoorkomen.
 */
public final class Lo3Herkomst implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final int ACTUEEL_VOORKOMEN = 0;

    @Element(name = "categorie", required = false)
    private final Lo3CategorieEnum categorie;
    @Element(name = "stapel")
    private final int stapel;
    @Element(name = "voorkomen")
    private final int voorkomen;
    // Het veld 'conversieSortering' wordt bewust niet meegenomen in equals() en hashCode()
    private final Integer conversieSortering;

    /**
     * Constructor.
     *
     * @param categorie
     *            categorie
     * @param stapel
     *            stapel
     * @param voorkomen
     *            voorkomen
     */
    public Lo3Herkomst(
        @Element(name = "categorie", required = false) final Lo3CategorieEnum categorie,
        @Element(name = "stapel") final int stapel,
        @Element(name = "voorkomen") final int voorkomen)
    {
        super();
        this.categorie = categorie;
        this.stapel = stapel;
        this.voorkomen = voorkomen;
        conversieSortering = null;
    }

    /**
     * Constructor.
     *
     * @param categorie
     *            categorie
     * @param stapel
     *            stapel
     * @param voorkomen
     *            voorkomen
     * @param conversieSortering
     *            conversieSortering
     */
    public Lo3Herkomst(final Lo3CategorieEnum categorie, final int stapel, final int voorkomen, final Integer conversieSortering) {
        super();
        this.categorie = categorie;
        this.stapel = stapel;
        this.voorkomen = voorkomen;
        this.conversieSortering = conversieSortering;
    }

    /**
     * Geef de waarde van categorie.
     *
     * @return categorie
     */
    public Lo3CategorieEnum getCategorie() {
        return categorie;
    }

    /**
     * Geef de waarde van stapel.
     *
     * @return stapel
     */
    public int getStapel() {
        return stapel;
    }

    /**
     * Geef de waarde van voorkomen.
     *
     * @return voorkomen
     */
    public int getVoorkomen() {
        return voorkomen;
    }

    /**
     * Geef de waarde van conversie sortering.
     *
     * @return conversie sortering
     */
    public Integer getConversieSortering() {
        return conversieSortering;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Lo3Herkomst)) {
            return false;
        }
        final Lo3Herkomst castOther = (Lo3Herkomst) other;
        // Het veld 'conversieSortering' wordt bewust niet meegenomen in equals() en hashCode()
        return new EqualsBuilder().append(categorie, castOther.categorie)
                                  .append(stapel, castOther.stapel)
                                  .append(voorkomen, castOther.voorkomen)
                                  .isEquals();
    }

    /**
     * zelfde als equals, maar dan alleen op categorie en stapelnummer. Van beide objecten die vergeleken worden, worden
     * de categorieen naar hun actuele categorienummer vertaald (dwz cat54 wordt cat04).
     *
     * @param other
     *            andere object waarmee vergeleken wordt
     * @return true als other van het type Lo3Herkomst is en categorie en stapel gelijk zijn
     */
    public boolean equalsCategorieStapelOnly(final Object other) {
        boolean result = false;
        if (this == other) {
            result = true;
        }
        if (other instanceof Lo3Herkomst) {
            final Lo3Herkomst castOther = (Lo3Herkomst) other;
            final Lo3CategorieEnum actueleCat = Lo3CategorieEnum.bepaalActueleCategorie(categorie);
            final Lo3CategorieEnum actueleOtherCat = Lo3CategorieEnum.bepaalActueleCategorie(castOther.categorie);

            result = new EqualsBuilder().append(actueleCat, actueleOtherCat).append(stapel, castOther.stapel).isEquals();
        }

        return result;
    }

    @Override
    public int hashCode() {
        // Het veld 'conversieSortering' wordt bewust niet meegenomen in equals() en hashCode()
        return new HashCodeBuilder().append(categorie).append(stapel).append(voorkomen).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                                                                          .append("categorie", categorie)
                                                                          .append("stapel", stapel)
                                                                          .append("voorkomen", voorkomen)
                                                                          .toString();
    }

    /**
     * Geef de lo3 actueel voorkomen.
     *
     * @return lo3 actueel voorkomen
     */
    public boolean isLo3ActueelVoorkomen() {
        return voorkomen == ACTUEEL_VOORKOMEN;
    }
}
