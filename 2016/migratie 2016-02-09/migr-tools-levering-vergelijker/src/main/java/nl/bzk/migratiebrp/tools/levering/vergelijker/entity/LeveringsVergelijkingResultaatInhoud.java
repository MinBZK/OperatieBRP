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
@Table(name = "mig_leveringsvergelijking_resultaat_inhoud", schema = "public")
public class LeveringsVergelijkingResultaatInhoud {

    @Id
    @SequenceGenerator(name = "mig_leveringsvergelijking_resultaat_inhoud_id_generator",
            sequenceName = "public.mig_leveringsvergelijking_resultaat_inhoud_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mig_leveringsvergelijking_resultaat_inhoud_id_generator")
    @Column(nullable = false)
    private Integer id;

    @Column(name = "bijhouding_eref", nullable = false)
    private Long bijhoudingBerichtEref;

    @Column(name = "bijhouding_ber_id_gbav", nullable = false)
    private Long bijhoudingBerichtIdGbav;

    @Column(name = "bijhouding_ber_id_brp", nullable = false)
    private Long bijhoudingBerichtIdBrp;

    @Column(name = "afnemer_code", nullable = false, length = 7)
    private String afnemerCode;

    @Column(name = "berichtnummer", nullable = false, length = 4)
    private String berichtNummer;

    @Column(name = "levering_ber_id_gbav", nullable = false)
    private Long leveringBerichtIdGbav;

    @Column(name = "levering_ber_id_brp", nullable = false)
    private Long leveringBerichtIdBrp;

    @Column(name = "stapel", nullable = false)
    private Integer stapel;

    @Column(name = "voorkomen", nullable = false)
    private Integer voorkomen;

    @Column(name = "categorie", nullable = false)
    private Integer categorie;

    @Column(name = "rubriek", nullable = true, length = 8)
    private String rubriek;

    /**
     * Geef de waarde van id.
     *
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Zet de waarde van id.
     *
     * @param id
     *            id
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van bijhouding bericht eref.
     *
     * @return bijhouding bericht eref
     */
    public Long getBijhoudingBerichtEref() {
        return bijhoudingBerichtEref;
    }

    /**
     * Zet de waarde van bijhouding bericht eref.
     *
     * @param bijhoudingBerichtEref
     *            bijhouding bericht eref
     */
    public void setBijhoudingBerichtEref(final Long bijhoudingBerichtEref) {
        this.bijhoudingBerichtEref = bijhoudingBerichtEref;
    }

    /**
     * Geef de waarde van bijhouding bericht id gbav.
     *
     * @return bijhouding bericht id gbav
     */
    public Long getBijhoudingBerichtIdGbav() {
        return bijhoudingBerichtIdGbav;
    }

    /**
     * Zet de waarde van bijhouding bericht id gbav.
     *
     * @param bijhoudingBerichtIdGbav
     *            bijhouding bericht id gbav
     */
    public void setBijhoudingBerichtIdGbav(final Long bijhoudingBerichtIdGbav) {
        this.bijhoudingBerichtIdGbav = bijhoudingBerichtIdGbav;
    }

    /**
     * Geef de waarde van bijhouding bericht id brp.
     *
     * @return bijhouding bericht id brp
     */
    public Long getBijhoudingBerichtIdBrp() {
        return bijhoudingBerichtIdBrp;
    }

    /**
     * Zet de waarde van bijhouding bericht id brp.
     *
     * @param bijhoudingBerichtIdBrp
     *            bijhouding bericht id brp
     */
    public void setBijhoudingBerichtIdBrp(final Long bijhoudingBerichtIdBrp) {
        this.bijhoudingBerichtIdBrp = bijhoudingBerichtIdBrp;
    }

    /**
     * Geef de waarde van afnemer code.
     *
     * @return afnemer code
     */
    public String getAfnemerCode() {
        return afnemerCode;
    }

    /**
     * Zet de waarde van afnemer code.
     *
     * @param afnemerCode
     *            afnemer code
     */
    public void setAfnemerCode(final String afnemerCode) {
        this.afnemerCode = afnemerCode;
    }

    /**
     * Geef de waarde van levering bericht id gbav.
     *
     * @return levering bericht id gbav
     */
    public Long getLeveringBerichtIdGbav() {
        return leveringBerichtIdGbav;
    }

    /**
     * Zet de waarde van levering bericht id gbav.
     *
     * @param leveringBerichtIdGbav
     *            levering bericht id gbav
     */
    public void setLeveringBerichtIdGbav(final Long leveringBerichtIdGbav) {
        this.leveringBerichtIdGbav = leveringBerichtIdGbav;
    }

    /**
     * Geef de waarde van levering bericht id brp.
     *
     * @return levering bericht id brp
     */
    public Long getLeveringBerichtIdBrp() {
        return leveringBerichtIdBrp;
    }

    /**
     * Zet de waarde van levering bericht id brp.
     *
     * @param leveringBerichtIdBrp
     *            levering bericht id brp
     */
    public void setLeveringBerichtIdBrp(final Long leveringBerichtIdBrp) {
        this.leveringBerichtIdBrp = leveringBerichtIdBrp;
    }

    /**
     * Geef de waarde van stapel.
     *
     * @return stapel
     */
    public Integer getStapel() {
        return stapel;
    }

    /**
     * Zet de waarde van stapel.
     *
     * @param stapel
     *            stapel
     */
    public void setStapel(final Integer stapel) {
        this.stapel = stapel;
    }

    /**
     * Geef de waarde van voorkomen.
     *
     * @return voorkomen
     */
    public Integer getVoorkomen() {
        return voorkomen;
    }

    /**
     * Zet de waarde van voorkomen.
     *
     * @param voorkomen
     *            voorkomen
     */
    public void setVoorkomen(final Integer voorkomen) {
        this.voorkomen = voorkomen;
    }

    /**
     * Geef de waarde van categorie.
     *
     * @return categorie
     */
    public Integer getCategorie() {
        return categorie;
    }

    /**
     * Zet de waarde van categorie.
     *
     * @param categorie
     *            categorie
     */
    public void setCategorie(final Integer categorie) {
        this.categorie = categorie;
    }

    /**
     * Geef de waarde van rubriek.
     *
     * @return rubriek
     */
    public String getElement() {
        return rubriek;
    }

    /**
     * Zet de waarde van rubriek.
     *
     * @param rubriek
     *            rubriek
     */
    public void setElement(final String rubriek) {
        this.rubriek = rubriek;
    }

    /**
     * Geef de waarde van bericht nummer.
     *
     * @return bericht nummer
     */
    public String getBerichtNummer() {
        return berichtNummer;
    }

    /**
     * Zet de waarde van bericht nummer.
     *
     * @param berichtNummer
     *            bericht nummer
     */
    public void setBerichtNummer(final String berichtNummer) {
        this.berichtNummer = berichtNummer;
    }

}
