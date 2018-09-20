/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.register;

import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Autorisatie uit het autorisatie register (immutable, thread-safe).
 */
public final class Autorisatie implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String partijCode;
    private final int toegangLeveringsautorisatieId;
    private final Integer plaatsenAfnemersindicatieDienstId;
    private final Integer verwijderenAfnemersindicatieDienstId;
    private final Integer bevragenPersoonDienstId;
    private final Integer bevragenAdresDienstId;

    /**
     * Constructor.
     * @param partijCode partijCode
     * @param toegangLeveringsautorisatieId toegang Leverings authorisatie id
     * @param plaatsenAfnemersindicatieDienstId plaatsen afneemrs indicatie ID
     * @param verwijderenAfnemersindicatieDienstId verwijderen Afnemers indicatie dienst ID
     * @param bevragenPersoonDienstId bevragen persoons ID
     * @param bevragenAdresDienstId bevragen Adres Dienst ID
     */
    public Autorisatie(
        final String partijCode,
        final int toegangLeveringsautorisatieId,
        final Integer plaatsenAfnemersindicatieDienstId,
        final Integer verwijderenAfnemersindicatieDienstId,
        final Integer bevragenPersoonDienstId,
        final Integer bevragenAdresDienstId)
    {
        super();
        this.partijCode = partijCode;
        this.toegangLeveringsautorisatieId = toegangLeveringsautorisatieId;
        this.plaatsenAfnemersindicatieDienstId = plaatsenAfnemersindicatieDienstId;
        this.verwijderenAfnemersindicatieDienstId = verwijderenAfnemersindicatieDienstId;
        this.bevragenPersoonDienstId = bevragenPersoonDienstId;
        this.bevragenAdresDienstId = bevragenAdresDienstId;
    }

    public String getPartijCode() {
        return partijCode;
    }

    public int getToegangLeveringsautorisatieId() {
        return toegangLeveringsautorisatieId;
    }

    public Integer getPlaatsenAfnemersindicatieDienstId() {
        return plaatsenAfnemersindicatieDienstId;
    }

    public Integer getVerwijderenAfnemersindicatieDienstId() {
        return verwijderenAfnemersindicatieDienstId;
    }

    public Integer getBevragenPersoonDienstId() {
        return bevragenPersoonDienstId;
    }

    public Integer getBevragenAdresDienstId() {
        return bevragenAdresDienstId;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Autorisatie)) {
            return false;
        }
        final Autorisatie castOther = (Autorisatie) other;
        return new EqualsBuilder().append(partijCode, castOther.partijCode)
                                  .append(toegangLeveringsautorisatieId, castOther.toegangLeveringsautorisatieId)
                                  .append(plaatsenAfnemersindicatieDienstId, castOther.plaatsenAfnemersindicatieDienstId)
                                  .append(verwijderenAfnemersindicatieDienstId, castOther.verwijderenAfnemersindicatieDienstId)
                                  .append(bevragenPersoonDienstId, castOther.bevragenPersoonDienstId)
                                  .append(bevragenAdresDienstId, castOther.bevragenAdresDienstId)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(partijCode)
                                    .append(toegangLeveringsautorisatieId)
                                    .append(plaatsenAfnemersindicatieDienstId)
                                    .append(verwijderenAfnemersindicatieDienstId)
                                    .append(bevragenPersoonDienstId)
                                    .append(bevragenAdresDienstId)
                                    .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                                                                          .append("partijCode", partijCode)
                                                                          .append("toegangLeveringsautorisatieId", toegangLeveringsautorisatieId)
                                                                          .append("plaatsenAfnemersindicatieDienstId", plaatsenAfnemersindicatieDienstId)
                                                                          .append(
                                                                              "verwijderenAfnemersindicatieDienstId",
                                                                              verwijderenAfnemersindicatieDienstId)
                                                                          .append("bevragenPersoonDienstId", bevragenPersoonDienstId)
                                                                          .append("bevragenAdresDienstId", bevragenAdresDienstId)
                                                                          .toString();
    }

}
