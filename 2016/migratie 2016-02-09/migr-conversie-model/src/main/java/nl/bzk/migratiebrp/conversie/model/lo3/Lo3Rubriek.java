/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3;

import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3GroepEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3RubriekVolgnummerEnum;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze klasse staat geen wijzigingen toe op de referenties die deze klasse vasthoudt.
 */
public final class Lo3Rubriek {

    private final Lo3CategorieEnum categorie;
    private final Lo3GroepEnum groep;
    private final Lo3RubriekVolgnummerEnum rubriekVolgNummer;

    /**
     * Maakt een Lo3Categorie object.
     * 
     * @param categorie
     *            de LO3 categorie
     * @param groep
     *            de LO3 groep
     * @param rubriekVolgNummer
     *            het LO3 rubriekvolgnummer
     * @throws NullPointerException
     *             als een van de parameters leeg is
     */
    public Lo3Rubriek(
        @Element(name = "categorie") final Lo3CategorieEnum categorie,
        @Element(name = "groep", required = false) final Lo3GroepEnum groep,
        @Element(name = "rubriekVolgNummer", required = false) final Lo3RubriekVolgnummerEnum rubriekVolgNummer)
    {
        if (categorie == null) {
            throw new NullPointerException("categorie mag niet null zijn");
        }
        if (groep == null) {
            throw new NullPointerException("groep mag niet null zijn");
        }
        if (rubriekVolgNummer == null) {
            throw new NullPointerException("rubriek volgnummer mag niet null zijn");
        }
        this.categorie = categorie;
        this.groep = groep;
        this.rubriekVolgNummer = rubriekVolgNummer;
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
     * Geef de waarde van groep.
     *
     * @return groep
     */
    public Lo3GroepEnum getGroep() {
        return groep;
    }

    /**
     * Geef de waarde van rubriek volg nummer.
     *
     * @return rubriek volg nummer
     */
    public Lo3RubriekVolgnummerEnum getRubriekVolgNummer() {
        return rubriekVolgNummer;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Lo3Rubriek)) {
            return false;
        }
        final Lo3Rubriek castOther = (Lo3Rubriek) other;
        return new EqualsBuilder().append(categorie, castOther.categorie)
                                  .append(groep, castOther.groep)
                                  .append(rubriekVolgNummer, castOther.rubriekVolgNummer)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(categorie).append(groep).append(rubriekVolgNummer).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                                                                          .append("categorie", categorie)
                                                                          .append("groep", groep)
                                                                          .append("rubriekVolgNummer", rubriekVolgNummer)
                                                                          .toString();
    }
}
