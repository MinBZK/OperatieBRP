/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.model.domein.entities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import nl.bzk.migratiebrp.conversie.model.logging.LogRegel;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

/**
 * Initiele vulling afnemersindicatie entity.
 */
@Entity
@Table(name = "initvullingresult_afnind", schema = "initvul")
@SuppressWarnings("checkstyle:designforextension")
public class InitVullingAfnemersindicatie {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Id
    @Column(name = "pl_id")
    private long plId;

    @Column(name = "a_nr")
    private Long administratienummer;

    @Column(name = "conversie_resultaat", insertable = true, updatable = true, length = 200)
    private String conversieResultaat;

    @Column(name = "foutmelding", insertable = true, updatable = true)
    private String foutmelding;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "stapelPk.plId")
    private Set<InitVullingAfnemersindicatieStapel> stapels;

    /**
     * Default JPA constructor.
     */
    protected InitVullingAfnemersindicatie() {
        stapels = new HashSet<>();
    }

    /**
     * Maakt een InitVullingAfnemersindicatie object.
     *
     * @param plId
     *            de pl id
     */
    public InitVullingAfnemersindicatie(final long plId) {
        setPlId(plId);
        stapels = new HashSet<>();
    }

    /**
     * Geef de waarde van pl id.
     *
     * @return pl id
     */
    public long getPlId() {
        return plId;
    }

    /**
     * Zet de waarde van pl id.
     *
     * @param plId
     *            pl id
     */
    public void setPlId(final long plId) {
        this.plId = plId;
    }

    /**
     * Geef de waarde van administratienummer.
     *
     * @return the administratieNummer
     */
    public Long getAdministratienummer() {
        return administratienummer;
    }

    /**
     * Zet de waarde van administratienummer.
     *
     * @param administratieNummer
     *            the administratieNummer to set
     */
    public void setAdministratienummer(final Long administratieNummer) {
        administratienummer = administratieNummer;
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

    /**
     * Geef de regels van de afnemerindicatie.
     *
     * @return InitVullingAfnemersindicatieregels
     */
    public Set<InitVullingAfnemersindicatieStapel> getStapels() {
        return stapels;
    }

    /**
     * verwerkt het resultaat.
     * @param logging lijst met log regels
     */
    public void verwerkResultaat(final List<LogRegel> logging) {
        LOG.debug("Verwerken van de logging, logging aanwezig {}", logging != null);
        LOG.debug("Aantal stapels aanwezig: {}", getStapels().size());

        final Map<Short, InitVullingAfnemersindicatieStapel> stapelMap = new HashMap<>();

        for (final InitVullingAfnemersindicatieStapel stapel : getStapels()) {
            stapel.setConversieResultaat("VERWERKT");
            stapelMap.put(stapel.getStapelPk().getStapelNr(), stapel);
        }
        if (logging != null) {
            LOG.debug("Aantal regels logging: {}", logging.size());
            for (final LogRegel logRegel : logging) {
                final InitVullingAfnemersindicatieStapel stapel = stapelMap.get((short) logRegel.getLo3Herkomst().getStapel());
                LOG.debug("Stapel gevonden: {}", stapel != null);
                stapel.setConversieResultaat(logRegel.getSoortMeldingCode().toString());
                LOG.debug("Conversieresultaat gezet op {}", logRegel.getSoortMeldingCode().toString());
            }
        }

    }
}
