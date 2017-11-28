/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.protocollering.domain.algemeen;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * LeveringPersoon.
 */
@JsonAutoDetect
public final class LeveringPersoon {

    private Long persoonId;
    private ZonedDateTime tijdstipLaatsteWijziging;
    private Integer datumAanvangMaterielePeriode;

    /**
     * json default constructor.
     */
    public LeveringPersoon() {
        //public constructor v json deserialisatie
    }

    /**
     * @param persoonId persoonId
     * @param tijdstipLaatsteWijziging tijdstip laatste wijziging
     */
    public LeveringPersoon(final Long persoonId, final ZonedDateTime tijdstipLaatsteWijziging) {
        this.persoonId = persoonId;
        this.tijdstipLaatsteWijziging = tijdstipLaatsteWijziging;
    }

    /**
     * @param persoonId persoonId
     * @param tijdstipLaatsteWijziging tijdstip laatste wijziging
     * @param datumAanvangMaterielePeriode datum aanvang materiele periode
     */
    public LeveringPersoon(final Long persoonId, final ZonedDateTime tijdstipLaatsteWijziging, final Integer datumAanvangMaterielePeriode) {
        this.persoonId = persoonId;
        this.tijdstipLaatsteWijziging = tijdstipLaatsteWijziging;
        this.datumAanvangMaterielePeriode = datumAanvangMaterielePeriode;
    }

    /**
     * @return persoonId
     */
    public Long getPersoonId() {
        return persoonId;
    }

    /**
     * @return tijdstipLaatsteWijziging
     */
    public ZonedDateTime getTijdstipLaatsteWijziging() {
        return tijdstipLaatsteWijziging;
    }

    /**
     * @return datumAanvangMaterielePeriode
     */
    public Integer getDatumAanvangMaterielePeriode() {
        return datumAanvangMaterielePeriode;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LeveringPersoon that = (LeveringPersoon) o;
        return Objects.equals(persoonId, that.persoonId)
                && Objects.equals(tijdstipLaatsteWijziging, that.tijdstipLaatsteWijziging);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persoonId, tijdstipLaatsteWijziging);
    }
}
