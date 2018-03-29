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
 * Initiele vulling protocollering entity.
 */
@Entity
@Table(name = "initvullingresult_protocollering", schema = "initvul")
public class InitVullingProtocollering {
    @Id
    @Column(name = "activiteit_id")
    private long activiteitId;

    @Column(name = "conversie_resultaat", insertable = true, updatable = true, length = 200)
    private String conversieResultaat;

    @Column(insertable = true, updatable = true, length = 2_147_483_647)
    private String foutmelding;

    /**
     * Geef activiteit id.
     * @return activiteit id
     */
    public long getActiviteitId() {
        return activiteitId;
    }

    /**
     * Zet activiteit id.
     * @param activiteitId id
     */
    public void setActiviteitId(final long activiteitId) {
        this.activiteitId = activiteitId;
    }

    /**
     * Geef conversie resultaat.
     * @return conversie resultaat
     */
    public String getConversieResultaat() {
        return conversieResultaat;
    }

    /**
     * Zet conversie resultaat.
     * @param conversieResultaat conversie resultaat
     */
    public void setConversieResultaat(final String conversieResultaat) {
        this.conversieResultaat = conversieResultaat;
    }

    /**
     * Geef foutmelding.
     * @return foutmelding
     */
    public String getFoutmelding() {
        return foutmelding;
    }

    /**
     * Zet foutmelding.
     * @param foutmelding foutmelding
     */
    public void setFoutmelding(final String foutmelding) {
        this.foutmelding = foutmelding;
    }
}
