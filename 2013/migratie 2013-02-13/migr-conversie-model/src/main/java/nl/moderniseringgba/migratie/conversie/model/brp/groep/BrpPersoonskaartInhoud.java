/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.groep;

import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;

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
    private final BrpGemeenteCode gemeentePKCode;
    @Element(name = "indicatiePKVolledigGeconverteerd", required = false)
    private final Boolean indicatiePKVolledigGeconverteerd;

    /**
     * Maakt een BrpPersoonskaartInhoud.
     * 
     * @param gemeentePKCode
     *            gemeente PK
     * @param indPKVolledigGeconverteerd
     *            PK volledig geconverteerd
     */
    public BrpPersoonskaartInhoud(
            @Element(name = "gemeentePKCode", required = false) final BrpGemeenteCode gemeentePKCode,
            @Element(name = "indicatiePKVolledigGeconverteerd", required = false) final Boolean indPKVolledigGeconverteerd) {
        this.gemeentePKCode = gemeentePKCode;
        this.indicatiePKVolledigGeconverteerd = indPKVolledigGeconverteerd;
    }

    @Override
    public boolean isLeeg() {
        return gemeentePKCode == null && indicatiePKVolledigGeconverteerd == null;
    }

    /**
     * @return the gemeentePKCode
     */
    public BrpGemeenteCode getGemeentePKCode() {
        return gemeentePKCode;
    }

    /**
     * @return the indicatiePKVolledigGeconverteerd
     */
    public Boolean getIndicatiePKVolledigGeconverteerd() {
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
                .append(indicatiePKVolledigGeconverteerd, castOther.indicatiePKVolledigGeconverteerd).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(gemeentePKCode).append(indicatiePKVolledigGeconverteerd).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("gemeentePKCode", gemeentePKCode)
                .append("indicatiePKVolledigGeconverteerd", indicatiePKVolledigGeconverteerd).toString();
    }

}
