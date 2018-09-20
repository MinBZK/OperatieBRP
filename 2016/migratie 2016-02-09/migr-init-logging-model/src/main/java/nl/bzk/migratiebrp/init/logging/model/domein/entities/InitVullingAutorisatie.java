/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.model.domein.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Initiele vulling autorisatie entity.
 */
@Entity
@Table(name = "initvullingresult_aut", schema = "initvul")
@SuppressWarnings("checkstyle:designforextension")
public class InitVullingAutorisatie {
    @Id
    @Column(name = "afnemer_code")
    private Integer afnemerCode;

    @Column(name = "conversie_resultaat", insertable = true, updatable = true, length = 200)
    private String conversieResultaat;

    @Column(insertable = true, updatable = true, length = 2147483647)
    private String foutmelding;

    /**
     * Geef de waarde van afnemer code.
     *
     * @return afnemer code
     */
    public Integer getAfnemerCode() {
        return afnemerCode;
    }

    /**
     * Zet de waarde van afnemer code.
     *
     * @param afnemerCode
     *            afnemer code
     */
    public void setAfnemerCode(final Integer afnemerCode) {
        this.afnemerCode = afnemerCode;
    }

    /**
     * Geef de waarde van conversie resultaat.
     *
     * @return conversie resultaat
     */
    public String getConversieResultaat() {
        return conversieResultaat;
    }

    /**
     * Zet de waarde van conversie resultaat.
     *
     * @param conversieResultaat
     *            conversie resultaat
     */
    public void setConversieResultaat(final String conversieResultaat) {
        this.conversieResultaat = conversieResultaat;
    }

    /**
     * Geef de waarde van foutmelding.
     *
     * @return foutmelding
     */
    public String getFoutmelding() {
        return foutmelding;
    }

    /**
     * Zet de waarde van foutmelding.
     *
     * @param foutmelding
     *            foutmelding
     */
    public void setFoutmelding(final String foutmelding) {
        this.foutmelding = foutmelding;
    }

}
