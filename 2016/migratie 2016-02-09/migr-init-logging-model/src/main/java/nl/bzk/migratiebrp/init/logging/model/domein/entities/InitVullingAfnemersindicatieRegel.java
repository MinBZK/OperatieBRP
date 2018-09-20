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
 * Initiele vulling afnemersindicatieregel entity.
 */
@Entity
@Table(name = "initvullingresult_afnind_regel", schema = "initvul")
@SuppressWarnings("checkstyle:designforextension")
public class InitVullingAfnemersindicatieRegel implements Serializable {

    /**
     * default version
     */
    private static final long serialVersionUID = 659440868265165363L;

    @EmbeddedId
    private InitVullingAfnemersindicatieRegelPk regelPk;

    @Column(name = "afnemer_code")
    private Long afnemerCode;

    @Column(name = "geldigheid_start_datum")
    private Long geldigheidStartDatum;

    /**
     * Default JPA constructor.
     */
    protected InitVullingAfnemersindicatieRegel() {
    }

    /**
     * Maakt een InitVullingAfnemersindicatieRegel object.
     *
     * @param regelPk
     *            de gecombineerde sleutel
     */
    public InitVullingAfnemersindicatieRegel(final InitVullingAfnemersindicatieRegelPk regelPk) {
        setRegelPk(regelPk);
    }

    /**
     * Geef de waarde van afnemer code.
     *
     * @return the afnemerCode
     */
    public Long getAfnemerCode() {
        return afnemerCode;
    }

    /**
     * Zet de waarde van afnemer code.
     *
     * @param afnemerCode
     *            the afnemerCode to set
     */
    public void setAfnemerCode(final Long afnemerCode) {
        this.afnemerCode = afnemerCode;
    }

    /**
     * Geef de waarde van geldigheid start datum.
     *
     * @return the geldigheidStartDatum
     */
    public Long getGeldigheidStartDatum() {
        return geldigheidStartDatum;
    }

    /**
     * Zet de waarde van geldigheid start datum.
     *
     * @param geldigheidStartDatum
     *            the geldigheidStartDatum to set
     */
    public void setGeldigheidStartDatum(final Long geldigheidStartDatum) {
        this.geldigheidStartDatum = geldigheidStartDatum;
    }

    /**
     * Geef de waarde van regel pk.
     *
     * @return the regelPk
     */
    public InitVullingAfnemersindicatieRegelPk getRegelPk() {
        return regelPk;
    }

    /**
     * Zet de waarde van regel pk.
     *
     * @param regelPk
     *            the regelPk to set
     */
    public void setRegelPk(final InitVullingAfnemersindicatieRegelPk regelPk) {
        ValidationUtils.controleerOpNullWaarden("embedded PK mag niet null zijn", regelPk);
        this.regelPk = regelPk;
    }
}
