/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch;

import javax.validation.constraints.NotNull;

import nl.bzk.brp.model.gedeeld.SoortIndicatie;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/** Indicaties bij een persoon. */
public class PersoonIndicatie implements Comparable<PersoonIndicatie> {

    private static final int HASHCODE_NON_ZERO_ODD_NUMBER            = 13;
    private static final int HASHCODE_MULTIPLIER_NON_ZERO_ODD_NUMBER = 45;

    private Persoon persoon;

    @NotNull
    private SoortIndicatie soort;

    private Boolean waarde;

    public Persoon getPersoon() {
        return this.persoon;
    }

    public void setPersoon(final Persoon persoon) {
        this.persoon = persoon;
    }

    public SoortIndicatie getSoort() {
        return this.soort;
    }

    public void setSoort(final SoortIndicatie soort) {
        this.soort = soort;
    }

    public Boolean getWaarde() {
        return this.waarde;
    }

    public void setWaarde(final Boolean waarde) {
        this.waarde = waarde;
    }

    @Override
    public int compareTo(final PersoonIndicatie indicatie) {
        final int resultaat;
        if (indicatie == null) {
            resultaat = -1;
        } else {
            resultaat = soort.compareTo(indicatie.getSoort());
        }
        return resultaat;
    }

    @Override
    public boolean equals(final Object obj) {
        final boolean resultaat;

        if (obj == null) {
            resultaat = false;
        } else if (obj == this) {
            resultaat = true;
        } else if (obj.getClass() != getClass()) {
            resultaat = false;
        } else {
            PersoonIndicatie rhs = (PersoonIndicatie) obj;
            return new EqualsBuilder()
                .append(persoon, rhs.persoon)
                .append(soort, rhs.soort)
                .isEquals();
        }
        return resultaat;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(HASHCODE_NON_ZERO_ODD_NUMBER, HASHCODE_MULTIPLIER_NON_ZERO_ODD_NUMBER)
            .append(persoon).append(soort).toHashCode();
    }

}
