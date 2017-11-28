/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.RedenBlokkering;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * The persistent class for the betr database table.
 */
@Entity
@Table(name = "blokkering", schema = "migblok")
public class Blokkering implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final int ANUMMER_LENGTE = 10;

    @Id
    @SequenceGenerator(name = "blokkering_id_generator", sequenceName = "migblok.seq_blokkering", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "blokkering_id_generator")
    @Column(nullable = false, updatable = false)
    private Integer id;

    @Column(name = "anr", nullable = false, length = 10)
    private String aNummer;

    @Column(name = "rdnblokkering", nullable = false)
    private Integer redenBlokkeringId;

    @Column(name = "processinstantieid", nullable = true)
    private Long processId;

    @Column(name = "lo3gemvestiging", nullable = true, length = 6)
    private String gemeenteCodeNaar;

    @Column(name = "lo3gemreg", nullable = true, length = 6)
    private String registratieGemeente;

    @Column(name = "tsreg", nullable = true, length = 4)
    private Timestamp tijdstip;

    /**
     * JPA no-args constructor.
     */
    Blokkering() {
    }

    /**
     * Maakt een blokkering object aan.
     * @param aNummer Het aNummer van de blokkering.
     * @param tijdstip Het tijdstip van blokkeren.
     */
    public Blokkering(final String aNummer, final Timestamp tijdstip) {
        setaNummer(aNummer);
        setTijdstip(tijdstip);
    }

    /**
     * Geef de waarde van id van Blokkering.
     * @return de waarde van id van Blokkering
     */
    public Integer getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van Blokkering.
     * @param id de nieuwe waarde voor id van Blokkering
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van a-nummer.
     * @return anummer
     */
    public String getaNummer() {
        return aNummer;
    }

    /**
     * Zet de waarde van a-nummer.
     * @param aNummer a-nummer
     */
    public void setaNummer(final String aNummer) {
        ValidationUtils.controleerOpNullWaarden("aNummer mag niet null zijn", aNummer);
        ValidationUtils.controleerOpLengte("aNummer moet een lengte van 10 hebben", aNummer, ANUMMER_LENGTE);
        this.aNummer = aNummer;
    }

    /**
     * Geef de waarde van reden blokkering van Blokkering.
     * @return de waarde van reden blokkering van Blokkering
     */
    public RedenBlokkering getRedenBlokkering() {
        return RedenBlokkering.parseId(redenBlokkeringId);
    }

    /**
     * Zet de waarden voor reden blokkering van Blokkering.
     * @param redenBlokkering de nieuwe waarde voor reden blokkering van Blokkering
     */
    public void setRedenBlokkering(final RedenBlokkering redenBlokkering) {
        redenBlokkeringId = redenBlokkering == null ? null : redenBlokkering.getId();
    }

    /**
     * Geef de waarde van process id van Blokkering.
     * @return de waarde van process id van Blokkering
     */
    public Long getProcessId() {
        return processId;
    }

    /**
     * Zet de waarden voor process id van Blokkering.
     * @param processId de nieuwe waarde voor process id van Blokkering
     */
    public void setProcessId(final Long processId) {
        this.processId = processId;
    }

    /**
     * Geef de waarde van gemeente code naar van Blokkering.
     * @return de waarde van gemeente code naar van Blokkering
     */
    public String getGemeenteCodeNaar() {
        return gemeenteCodeNaar;
    }

    /**
     * Zet de waarden voor gemeente code naar van Blokkering.
     * @param gemeenteCodeNaar de nieuwe waarde voor gemeente code naar van Blokkering
     */
    public void setGemeenteCodeNaar(final String gemeenteCodeNaar) {
        this.gemeenteCodeNaar = gemeenteCodeNaar;
    }

    /**
     * Geef de waarde van registratie gemeente van Blokkering.
     * @return de waarde van registratie gemeente van Blokkering
     */
    public String getRegistratieGemeente() {
        return registratieGemeente;
    }

    /**
     * Zet de waarden voor registratie gemeente van Blokkering.
     * @param registratieGemeente de nieuwe waarde voor registratie gemeente van Blokkering
     */
    public void setRegistratieGemeente(final String registratieGemeente) {
        this.registratieGemeente = registratieGemeente;
    }

    /**
     * Geef de waarde van tijdstip van Blokkering.
     * @return de waarde van tijdstip van Blokkering
     */
    public Timestamp getTijdstip() {
        return Entiteit.timestamp(tijdstip);
    }

    /**
     * Zet de waarden voor tijdstip van Blokkering.
     * @param tijdstip de nieuwe waarde voor tijdstip van Blokkering
     */
    public void setTijdstip(final Timestamp tijdstip) {
        ValidationUtils.controleerOpNullWaarden("tijdstip mag niet null zijn", tijdstip);
        this.tijdstip = Entiteit.timestamp(tijdstip);
    }
}
