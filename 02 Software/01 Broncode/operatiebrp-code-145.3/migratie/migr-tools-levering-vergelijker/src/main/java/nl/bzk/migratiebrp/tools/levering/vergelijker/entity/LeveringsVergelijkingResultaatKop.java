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
@Table(name = "mig_leveringsvergelijking_resultaat_kop", schema = "public")
public class LeveringsVergelijkingResultaatKop {

    @Id
    @SequenceGenerator(name = "mig_leveringsvergelijking_resultaat_kop_id_generator",
            sequenceName = "public.mig_leveringsvergelijking_resultaat_kop_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mig_leveringsvergelijking_resultaat_kop_id_generator")
    @Column(nullable = false)
    private Integer id;

    @Column(name = "bijhouding_eref", nullable = false)
    private Long bijhoudingBerichtEref;

    @Column(name = "bijhouding_ber_id_gbav", nullable = false)
    private Long bijhoudingBerichtIdGbav;

    @Column(name = "bijhouding_ber_id_brp", nullable = false)
    private Long bijhoudingBerichtIdBrp;

    @Column(name = "afnemer_code", nullable = false, length = 10)
    private String afnemerCode;

    @Column(name = "berichtnummer", nullable = false, length = 4)
    private String berichtNummer;

    @Column(name = "levering_ber_id_gbav", nullable = false)
    private Long leveringBerichtIdGbav;

    @Column(name = "levering_ber_id_brp", nullable = false)
    private Long leveringBerichtIdBrp;

    @Column(name = "afwijkingen", nullable = false)
    private String afwijkingen;

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
     * Geef de waarde van bijhouding bericht eref.
     * @return bijhouding bericht eref
     */
    public Long getBijhoudingBerichtEref() {
        return bijhoudingBerichtEref;
    }

    /**
     * Zet de waarde van bijhouding bericht eref.
     * @param bijhoudingBerichtEref bijhouding bericht eref
     */
    public void setBijhoudingBerichtEref(final Long bijhoudingBerichtEref) {
        this.bijhoudingBerichtEref = bijhoudingBerichtEref;
    }

    /**
     * Geef de waarde van bijhouding bericht id gbav.
     * @return bijhouding bericht id gbav
     */
    public Long getBijhoudingBerichtIdGbav() {
        return bijhoudingBerichtIdGbav;
    }

    /**
     * Zet de waarde van bijhouding bericht id gbav.
     * @param bijhoudingBerichtIdGbav bijhouding bericht id gbav
     */
    public void setBijhoudingBerichtIdGbav(final Long bijhoudingBerichtIdGbav) {
        this.bijhoudingBerichtIdGbav = bijhoudingBerichtIdGbav;
    }

    /**
     * Geef de waarde van bijhouding bericht id brp.
     * @return bijhouding bericht id brp
     */
    public Long getBijhoudingBerichtIdBrp() {
        return bijhoudingBerichtIdBrp;
    }

    /**
     * Zet de waarde van bijhouding bericht id brp.
     * @param bijhoudingBerichtIdBrp bijhouding bericht id brp
     */
    public void setBijhoudingBerichtIdBrp(final Long bijhoudingBerichtIdBrp) {
        this.bijhoudingBerichtIdBrp = bijhoudingBerichtIdBrp;
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
     * Geef de waarde van levering bericht id gbav.
     * @return levering bericht id gbav
     */
    public Long getLeveringBerichtIdGbav() {
        return leveringBerichtIdGbav;
    }

    /**
     * Zet de waarde van levering bericht id gbav.
     * @param leveringBerichtIdGbav levering bericht id gbav
     */
    public void setLeveringBerichtIdGbav(final Long leveringBerichtIdGbav) {
        this.leveringBerichtIdGbav = leveringBerichtIdGbav;
    }

    /**
     * Geef de waarde van levering bericht id brp.
     * @return levering bericht id brp
     */
    public Long getLeveringBerichtIdBrp() {
        return leveringBerichtIdBrp;
    }

    /**
     * Zet de waarde van levering bericht id brp.
     * @param leveringBerichtIdBrp levering bericht id brp
     */
    public void setLeveringBerichtIdBrp(final Long leveringBerichtIdBrp) {
        this.leveringBerichtIdBrp = leveringBerichtIdBrp;
    }

    /**
     * Geef de waarde van afwijkingen.
     * @return afwijkingen
     */
    public String getAfwijkingen() {
        return afwijkingen;
    }

    /**
     * Zet de waarde van afwijkingen.
     * @param afwijkingen afwijkingen
     */
    public void setAfwijkingen(final String afwijkingen) {
        this.afwijkingen = afwijkingen;
    }

    /**
     * Geef de waarde van bericht nummer.
     * @return bericht nummer
     */
    public String getBerichtNummer() {
        return berichtNummer;
    }

    /**
     * Zet de waarde van bericht nummer.
     * @param berichtNummer bericht nummer
     */
    public void setBerichtNummer(final String berichtNummer) {
        this.berichtNummer = berichtNummer;
    }

}
