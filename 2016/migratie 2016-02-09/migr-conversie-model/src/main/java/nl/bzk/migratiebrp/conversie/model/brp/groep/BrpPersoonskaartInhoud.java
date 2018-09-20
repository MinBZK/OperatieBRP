/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert de inhoud van de groep BRP Persoonskaart.
 * 
 * Deze class is immutable en threadsafe.
 * 
 */
public final class BrpPersoonskaartInhoud extends AbstractBrpGroepInhoud {
    @Element(name = "gemeentePKCode", required = false)
    private final BrpPartijCode gemeentePKCode;
    @Element(name = "indicatiePKVolledigGeconverteerd", required = false)
    private final BrpBoolean indicatiePKVolledigGeconverteerd;

    /**
     * Maakt een BrpPersoonskaartInhoud.
     * 
     * @param gemeentePKCode
     *            gemeente PK
     * @param indPKVolledigGeconverteerd
     *            PK volledig geconverteerd
     */
    public BrpPersoonskaartInhoud(@Element(name = "gemeentePKCode", required = false) final BrpPartijCode gemeentePKCode, @Element(
            name = "indicatiePKVolledigGeconverteerd", required = false) final BrpBoolean indPKVolledigGeconverteerd)
    {
        this.gemeentePKCode = gemeentePKCode;
        indicatiePKVolledigGeconverteerd = indPKVolledigGeconverteerd;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpGroepInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        return !Validatie.isEenParameterGevuld(gemeentePKCode, indicatiePKVolledigGeconverteerd);
    }

    /**
     * Geef de waarde van gemeente pk code.
     *
     * @return the gemeentePKCode
     */
    public BrpPartijCode getGemeentePKCode() {
        return gemeentePKCode;
    }

    /**
     * Geef de waarde van indicatie pk volledig geconverteerd.
     *
     * @return the indicatiePKVolledigGeconverteerd
     */
    public BrpBoolean getIndicatiePKVolledigGeconverteerd() {
        return indicatiePKVolledigGeconverteerd;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpPersoonskaartInhoud)) {
            return false;
        }
        final BrpPersoonskaartInhoud castOther = (BrpPersoonskaartInhoud) other;
        return new EqualsBuilder().append(gemeentePKCode, castOther.gemeentePKCode)
                                  .append(indicatiePKVolledigGeconverteerd, castOther.indicatiePKVolledigGeconverteerd)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(gemeentePKCode).append(indicatiePKVolledigGeconverteerd).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                                                                          .append("gemeentePKCode", gemeentePKCode)
                                                                          .append("indicatiePKVolledigGeconverteerd", indicatiePKVolledigGeconverteerd)
                                                                          .toString();
    }

}
