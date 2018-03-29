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
 * Dit is de samengestelde sleutel voor {@link LeveringsaantekeningPersoon}.
 */
public class LeveringsaantekeningPersoonSleutel implements Serializable {

    private static final long serialVersionUID = 3114028299706856845L;

    private Long leveringsaantekening;

    private Long persoon;

    /**
     * JPA default constructor.
     */
    protected LeveringsaantekeningPersoonSleutel() {
    }

    /**
     * Maak een nieuw paar van leveringsaantekening/persoon.
     * @param leveringsaantekening leveringsaantekening, mag niet null zijn
     * @param persoon persoon, mag niet null zijn
     */
    public LeveringsaantekeningPersoonSleutel(Long leveringsaantekening, Long persoon) {
        this.leveringsaantekening = leveringsaantekening;
        this.persoon = persoon;
    }

    /**
     * Geef de waarde van leveringsaantekeningId.
     * @return leveringsaantekeningId
     */
    public Long getLeveringsaantekening() {
        return leveringsaantekening;
    }

    /**
     * Geef de waarde van persoon.
     * @return persoon
     */
    public Long getPersoon() {
        return persoon;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj != null && this.getClass() == obj.getClass()) {
            final LeveringsaantekeningPersoonSleutel other = (LeveringsaantekeningPersoonSleutel) obj;
            return new EqualsBuilder().append(leveringsaantekening, other.leveringsaantekening)
                    .append(persoon, other.persoon).isEquals();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(leveringsaantekening).append(persoon).toHashCode();
    }
}
