/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.autorisatie;

import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Afnemers indicatie inhoud.
 * 
 * Deze class is immutable en threadsafe.
 */
public final class Lo3AfnemersindicatieInhoud implements Lo3CategorieInhoud {

    /*
     * 40.10 Afnemersindicatie
     */
    @Element(name = "afnemersindicatie", required = false)
    private final Integer afnemersindicatie;

    /**
     * Default constructor (alles null).
     */
    public Lo3AfnemersindicatieInhoud() {
        this(null);
    }

    /**
     * Maakt een Lo3AfnemersindicatieInhoud.
     * 
     * @param afnemersindicatie
     *            afnemers indicatie
     */
    public Lo3AfnemersindicatieInhoud(@Element(name = "afnemersindicatie", required = false) final Integer afnemersindicatie) {
        super();
        this.afnemersindicatie = afnemersindicatie;
    }

    /**
     * Geef de waarde van afnemersindicatie.
     *
     * @return the afnemersindicatie
     */
    public Integer getAfnemersindicatie() {
        return afnemersindicatie;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Lo3AfnemersindicatieInhoud)) {
            return false;
        }
        final Lo3AfnemersindicatieInhoud castOther = (Lo3AfnemersindicatieInhoud) other;
        return new EqualsBuilder().append(getAfnemersindicatie(), castOther.getAfnemersindicatie()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getAfnemersindicatie()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("afnemersindicatie", getAfnemersindicatie()).toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.conversie.model.lo3.Lo3CategorieInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        return afnemersindicatie == null;
    }

}
