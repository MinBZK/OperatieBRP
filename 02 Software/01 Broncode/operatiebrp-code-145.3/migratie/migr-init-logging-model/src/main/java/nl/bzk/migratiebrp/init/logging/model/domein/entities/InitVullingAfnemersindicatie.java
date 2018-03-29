/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.model.domein.entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Initiele vulling afnemersindicatie entity.
 */
@Entity
@Table(name = "initvullingresult_afnind", schema = "initvul")
public class InitVullingAfnemersindicatie {

    @Id
    @Column(name = "pl_id")
    private long plId;

    @Column(name = "a_nr")
    private String administratienummer;

    @Column(name = "bericht_resultaat", insertable = true, updatable = true, length = 200)
    private String berichtResultaat;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "stapelPk.plId")
    private final Set<InitVullingAfnemersindicatieStapel> stapels;

    /**
     * Default JPA constructor.
     */
    protected InitVullingAfnemersindicatie() {
        stapels = new HashSet<>();
    }

    /**
     * Maakt een InitVullingAfnemersindicatie object.
     * @param plId de pl id
     */
    public InitVullingAfnemersindicatie(final long plId) {
        setPlId(plId);
        stapels = new HashSet<>();
    }

    /**
     * Geef de waarde van pl id.
     * @return pl id
     */
    public long getPlId() {
        return plId;
    }

    /**
     * Zet de waarde van pl id.
     * @param plId pl id
     */
    public void setPlId(final long plId) {
        this.plId = plId;
    }

    /**
     * Geef de waarde van administratienummer.
     * @return the administratieNummer
     */
    public String getAdministratienummer() {
        return administratienummer;
    }

    /**
     * Zet de waarde van administratienummer.
     * @param administratieNummer the administratieNummer to set
     */
    public void setAdministratienummer(final String administratieNummer) {
        administratienummer = administratieNummer;
    }

    /**
     * Geef de waarde van bericht resultaat.
     * @return bericht resultaat
     */
    public String getBerichtResultaat() {
        return berichtResultaat;
    }

    /**
     * Zet de waarde van bericht resultaat.
     * @param berichtResultaat bericht resultaat
     */
    public void setBerichtResultaat(final String berichtResultaat) {
        this.berichtResultaat = berichtResultaat;
    }

    /**
     * Geef de regels van de afnemerindicatie.
     * @return InitVullingAfnemersindicatieregels
     */
    public Set<InitVullingAfnemersindicatieStapel> getStapels() {
        return stapels;
    }
}
