/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.model.domein.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;

/**
 * Initiele vulling afnemersindicatiestapel entity.
 */
@Entity
@Table(name = "initvullingresult_afnind_stapel", schema = "initvul")
@SuppressWarnings("checkstyle:designforextension")
public class InitVullingAfnemersindicatieStapel implements Serializable {

    /**
     * default version
     */
    private static final long serialVersionUID = 659440868265165363L;

    @EmbeddedId
    private InitVullingAfnemersindicatieStapelPk stapelPk;

    @Column(name = "conversie_resultaat", insertable = true, updatable = true, length = 200)
    private String conversieResultaat;

    /**
     * Default JPA constructor.
     */
    protected InitVullingAfnemersindicatieStapel() {
    }

    /**
     * Maakt een InitVullingAfnemersindicatieRegel object.
     *
     * @param stapelPk
     *            de gecombineerde sleutel
     */
    public InitVullingAfnemersindicatieStapel(final InitVullingAfnemersindicatieStapelPk stapelPk) {
        setStapelPk(stapelPk);
    }

    /**
     * Geef de waarde van regel pk.
     *
     * @return the regelPk
     */
    public InitVullingAfnemersindicatieStapelPk getStapelPk() {
        return stapelPk;
    }

    /**
     * Zet de waarde van stapel pk.
     *
     * @param stapelPk
     *            the stapelPk to set
     */
    public void setStapelPk(final InitVullingAfnemersindicatieStapelPk stapelPk) {
        ValidationUtils.controleerOpNullWaarden("embedded PK mag niet null zijn", stapelPk);
        this.stapelPk = stapelPk;
    }

    /**
     * Geef de waarde van het conversieResultaat.
     *
     * @return conversieResultaat
     */
    public String getConversieResultaat() {
        return conversieResultaat;
    }

    /**
     * Zet de waarde van het conversieResultaat.
     *
     * @param conversieResultaat
     *            Het conversieResultaat
     */
    public void setConversieResultaat(final String conversieResultaat) {
        this.conversieResultaat = conversieResultaat;
    }
}
