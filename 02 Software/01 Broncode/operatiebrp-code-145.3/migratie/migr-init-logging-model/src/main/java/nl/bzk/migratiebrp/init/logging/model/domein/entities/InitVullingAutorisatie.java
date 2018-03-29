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
public class InitVullingAutorisatie {

    @Id
    @Column(name = "autorisatie_id")
    private Long autorisatieId;

    @Column(name = "conversie_resultaat", insertable = true, updatable = true, length = 200)
    private String conversieResultaat;

    @Column(name = "conversie_melding", insertable = true, updatable = true, length = 2_147_483_647)
    private String conversieMelding;

    /**
     * Geef de waarde van autorisatie id.
     * @return autorisatie id
     */
    public Long getAutorisatieId() {
        return autorisatieId;
    }

    /**
     * Zet de waarde van autorisatie id.
     * @param autorisatieId autorisatie id
     */
    public void setAutorisatieId(final Long autorisatieId) {
        this.autorisatieId = autorisatieId;
    }

    /**
     * Geef de waarde van conversie resultaat.
     * @return conversie resultaat
     */
    public String getConversieResultaat() {
        return conversieResultaat;
    }

    /**
     * Zet de waarde van conversie resultaat.
     * @param conversieResultaat conversie resultaat
     */
    public void setConversieResultaat(final String conversieResultaat) {
        this.conversieResultaat = conversieResultaat;
    }

    /**
     * Geef de waarde van conversie melding.
     * @return conversie melding
     */
    public String getConversieMelding() {
        return conversieMelding;
    }

    /**
     * Zet de waarde van conversie melding.
     * @param conversieMelding conversie melding
     */
    public void setConversieMelding(final String conversieMelding) {
        this.conversieMelding = conversieMelding;
    }
}
