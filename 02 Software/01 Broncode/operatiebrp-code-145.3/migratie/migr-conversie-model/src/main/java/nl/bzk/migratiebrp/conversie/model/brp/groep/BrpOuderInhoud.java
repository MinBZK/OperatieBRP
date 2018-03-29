/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Deze class representeert de inhoud van de BRP indicatie 'Ouder'.
 *
 * Deze class is immutable en threadsafe.
 */
public final class BrpOuderInhoud extends AbstractBrpGroepInhoud {

    @Element(name = "indicatieOuderUitWieKindIsGeboren", required = false)
    private final BrpBoolean indicatieOuderUitWieKindIsGeboren;

    private boolean isLeeg;

    /**
     * Maakt een BrpOuderInhoud object.
     */
    public BrpOuderInhoud() {
        this(null);
    }

    /**
     * Maakt een BrpOuderInhoud object.
     * @param indicatieOuderUitWieKindIsGeboren true als de indicatie bestaat, anders null, mag niet false zijn
     * @throws IllegalArgumentException als heeftIndicatie false is
     */
    public BrpOuderInhoud(@Element(name = "indicatieOuderUitWieKindIsGeboren", required = false) final BrpBoolean indicatieOuderUitWieKindIsGeboren) {
        isLeeg = false;
        this.indicatieOuderUitWieKindIsGeboren = indicatieOuderUitWieKindIsGeboren;
    }

    /**
     * Geef de waarde van indicatie ouder uit wie kind is geboren van BrpOuderInhoud.
     * @return de waarde van indicatie ouder uit wie kind is geboren van BrpOuderInhoud
     */
    public BrpBoolean getIndicatieOuderUitWieKindIsGeboren() {
        return indicatieOuderUitWieKindIsGeboren;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpGroepInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        return isLeeg;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpOuderInhoud)) {
            return false;
        }
        final BrpOuderInhoud castOther = (BrpOuderInhoud) other;
        return new EqualsBuilder().append(indicatieOuderUitWieKindIsGeboren, castOther.indicatieOuderUitWieKindIsGeboren).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(indicatieOuderUitWieKindIsGeboren).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("indicatieOuderUitWieKindIsGeboren", indicatieOuderUitWieKindIsGeboren)
                .toString();
    }

    /**
     * Maakt een expliciet lege inhoud.
     * @return een lege inhoud
     */
    public static BrpOuderInhoud maakLegeInhoud() {
        final BrpOuderInhoud result = new BrpOuderInhoud(null);
        result.isLeeg = true;
        return result;
    }
}
