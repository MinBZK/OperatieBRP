/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert de inhoud van de BRP indicatie 'Ouder'.
 * 
 * Deze class is immutable en threadsafe.
 * 
 */
public final class BrpOuderInhoud extends AbstractBrpGroepInhoud {

    @Element(name = "indicatieOuder", required = false)
    private final BrpBoolean indicatieOuder;

    @Element(name = "indicatieOuderUitWieKindIsGeboren", required = false)
    private final BrpBoolean indicatieOuderUitWieKindIsGeboren;

    /**
     * Maakt een BrpOuderInhoud object.
     * 
     * @param indicatieOuder
     *            true als de indicatie bestaat, anders null, mag niet false zijn
     * @param indicatieOuderUitWieKindIsGeboren
     *            true als de indicatie bestaat, anders null, mag niet false zijn
     * @throws IllegalArgumentException
     *             als heeftIndicatie false is
     */
    public BrpOuderInhoud(@Element(name = "indicatieOuder", required = false) final BrpBoolean indicatieOuder, @Element(
            name = "indicatieOuderUitWieKindIsGeboren", required = false) final BrpBoolean indicatieOuderUitWieKindIsGeboren)
    {
        this.indicatieOuder = indicatieOuder;
        this.indicatieOuderUitWieKindIsGeboren = indicatieOuderUitWieKindIsGeboren;
    }

    /**
     * Geef de waarde van indicatie ouder.
     *
     * @return true als er sprake is van een indicatie, anders null
     */
    public BrpBoolean getIndicatieOuder() {
        return indicatieOuder;
    }

    /**
     * Geef de waarde van indicatie adresgevende ouder.
     *
     * @return true als er sprake is van een indicatie, anders null
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
        return !Validatie.isAttribuutGevuld(indicatieOuder);
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
        return new EqualsBuilder().append(indicatieOuder, castOther.indicatieOuder)
                                  .append(indicatieOuderUitWieKindIsGeboren, castOther.indicatieOuderUitWieKindIsGeboren)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(indicatieOuder).append(indicatieOuderUitWieKindIsGeboren).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                                                                          .append("indicatieOuder", indicatieOuder)
                                                                          .append("indicatieOuderUitWieKindIsGeboren", indicatieOuderUitWieKindIsGeboren)
                                                                          .toString();
    }
}
