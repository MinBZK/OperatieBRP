/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.levering.vergelijker.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Deze class is gegenereerd o.b.v. het datamodel en bevat daarom geen javadoc, daarnaast mogen entities en de methoden
 * van entities niet final zijn.
 */
@Entity
@Table(name = "mig_leveringsvergelijking_berichtcorrelatie_gbav", schema = "public")
public class LeveringsVergelijkingBerichtCorrelatieGbav {

    @Id
    @SequenceGenerator(name = "mig_leveringsvergelijking_berichtcorrelatie_gbav_id_generator",
            sequenceName = "public.mig_leveringsvergelijking_berichtcorrelatie_gbav_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mig_leveringsvergelijking_berichtcorrelatie_gbav_id_generator")
    @Column(nullable = false)
    private Integer id;

    @Column(name = "bijhouding_ber_id", nullable = false)
    private Long bijhoudingBerichtId;

    @Column(name = "afnemer_code", nullable = false, length = 10)
    private String afnemerCode;

    @Column(name = "levering_ber_id", nullable = false)
    private Long leveringBerichtId;

    /**
     * Geef de waarde van id.
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Zet de waarde van id.
     * @param id id
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van bijhouding bericht id.
     * @return bijhouding bericht id
     */
    public Long getBijhoudingBerichtId() {
        return bijhoudingBerichtId;
    }

    /**
     * Zet de waarde van bijhouding bericht id.
     * @param bijhoudingBerichtId bijhouding bericht id
     */
    public void setBijhoudingBerichtId(final Long bijhoudingBerichtId) {
        this.bijhoudingBerichtId = bijhoudingBerichtId;
    }

    /**
     * Geef de waarde van afnemer code.
     * @return afnemer code
     */
    public String getAfnemerCode() {
        return afnemerCode;
    }

    /**
     * Zet de waarde van afnemer code.
     * @param afnemerCode afnemer code
     */
    public void setAfnemerCode(final String afnemerCode) {
        this.afnemerCode = afnemerCode;
    }

    /**
     * Geef de waarde van levering bericht id.
     * @return levering bericht id
     */
    public Long getLeveringBerichtId() {
        return leveringBerichtId;
    }

    /**
     * Zet de waarde van levering bericht id.
     * @param leveringBerichtId levering bericht id
     */
    public void setLeveringBerichtId(final Long leveringBerichtId) {
        this.leveringBerichtId = leveringBerichtId;
    }

}
