/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.autorisatie;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.algemeenbrp.util.xml.annotation.Root;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * This class represents an LO3 autorisatielijst.
 *
 * Deze class is immutable en threadsafe.
 */
@Root
public final class Lo3Autorisatie {

    private final Lo3Stapel<Lo3AutorisatieInhoud> autorisatieStapel;

    /**
     * Maak een Lo3 autorisatielijst.
     * @param autorisatieStapel de lijst met autorisatieStapel, mag null of leeg zijn
     * @throws NullPointerException als de stapel null zijn
     */
    public Lo3Autorisatie(
            @Element(name = "autorisatieStapel", type = Lo3Stapel.class) final Lo3Stapel<Lo3AutorisatieInhoud> autorisatieStapel) {
        this.autorisatieStapel = autorisatieStapel;
    }

    /**
     * Geef de waarde van autorisatie stapel.
     * @return de lijst met autorisatiestapels.
     */
    @Element(name = "autorisatieStapel", type = Lo3Stapel.class)
    public Lo3Stapel<Lo3AutorisatieInhoud> getAutorisatieStapel() {
        return autorisatieStapel;
    }

    /**
     * Geeft de afnemersindicatie terug voor deze Lo3 autorisatie.
     * @return de afnemersindicatie.
     */
    public String getAfnemersindicatie() {

        if (autorisatieStapel == null || autorisatieStapel.isEmpty()) {
            return null;
        } else {
            String afnemersIndicatie = null;
            for (final Lo3Categorie<Lo3AutorisatieInhoud> categorie : autorisatieStapel.getCategorieen()) {
                if (categorie.getInhoud() != null && categorie.getInhoud().getAfnemersindicatie() != null) {
                    afnemersIndicatie = categorie.getInhoud().getAfnemersindicatie();
                    break;
                }
            }
            return afnemersIndicatie;
        }
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Lo3Autorisatie)) {
            return false;
        }
        final Lo3Autorisatie castOther = (Lo3Autorisatie) other;
        return new EqualsBuilder().append(autorisatieStapel, castOther.autorisatieStapel).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(autorisatieStapel).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("autorisatieStapel", autorisatieStapel).toString();
    }
}
