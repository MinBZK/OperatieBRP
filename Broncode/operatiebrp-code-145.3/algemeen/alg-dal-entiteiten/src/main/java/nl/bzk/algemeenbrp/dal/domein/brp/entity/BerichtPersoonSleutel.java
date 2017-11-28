/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Dit is de samengestelde sleutel van {@link BerichtPersoon}.
 */
public class BerichtPersoonSleutel implements Serializable {

    private static final long serialVersionUID = 7668494881258070841L;

    private Long bericht;

    private Long persoon;

    /**
     * JPA default constructor.
     */
    protected BerichtPersoonSleutel() {
    }

    /**
     * Maakt een nieuw paar van bericht/persoon.
     * @param bericht berichtId
     * @param persoon persoon
     */
    public BerichtPersoonSleutel(Long bericht, Long persoon) {
        this.bericht = bericht;
        this.persoon = persoon;
    }

    /**
     * Geeft de waarde van bericht.
     * @return bericht
     */
    public Long getBericht() {
        return bericht;
    }

     /**
     * Geeft de waarde van persoon.
     * @return persoon
     */
    public Long getPersoon() {
        return persoon;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj != null && this.getClass() == obj.getClass()) {
            final BerichtPersoonSleutel other = (BerichtPersoonSleutel) obj;
            return new EqualsBuilder().append(bericht, other.bericht)
                    .append(persoon, other.persoon).isEquals();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(bericht).append(persoon).toHashCode();
    }
}
