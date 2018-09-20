/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.blokkering.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;
import nl.bzk.migratiebrp.util.common.Kopieer;

/**
 * The persistent class for the betr database table.
 *
 */
@Entity
@Table(name = "blokkering", schema = "migblok")
@SuppressWarnings({"checkstyle:designforextension", "multiplestringliterals" })
public class Blokkering implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "BLOK_ID_GENERATOR", sequenceName = "migblok.seq_Blokkering", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BLOK_ID_GENERATOR")
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "ANr", nullable = false, length = 7)
    private Long aNummer;

    @Column(name = "RdnBlokkering", nullable = false)
    private Short redenBlokkeringId;

    @Column(name = "ProcessInstantieID", nullable = true)
    private Long processId;

    @Column(name = "LO3GemVestiging", nullable = true, length = 4)
    private String gemeenteCodeNaar;

    @Column(name = "LO3GemReg", nullable = true, length = 4)
    private String registratieGemeente;

    @Column(name = "tsreg", nullable = true, length = 4)
    private Timestamp tijdstip;

    /**
     * Maak een nieuwe blokkering.
     */
    public Blokkering() {
    }

    /**
     * Maakt een blokkering object aan.
     *
     * @param aNummer
     *            Het aNummer van de blokkering.
     * @param processId
     *            Het ID van het proces dat de blokkering aangeeft.
     * @param gemeenteCodeNaar
     *            De gemeente waarnaartoe de PL is verhuisd.
     * @param registratieGemeente
     *            De gemeente die de blokkering registreert.
     * @param redenBlokkering
     *            the reden blokkering
     * @param tijdstip
     *            Het tijdstip van blokkeren.
     */
    public Blokkering(
        final Long aNummer,
        final Long processId,
        final String gemeenteCodeNaar,
        final String registratieGemeente,
        final RedenBlokkering redenBlokkering,
        final Timestamp tijdstip)
    {
        if (tijdstip == null) {
            throw new NullPointerException("tijdstipVerwerking mag niet null zijn");
        }
        ValidationUtils.controleerOpNullWaarden("aNummer mag niet null zijn", aNummer);
        ValidationUtils.controleerOpNullWaarden("reden blokkering mag niet null zijn", redenBlokkering);
        this.aNummer = aNummer;
        this.gemeenteCodeNaar = gemeenteCodeNaar;
        redenBlokkeringId = redenBlokkering.getId();
        this.processId = processId;
        this.registratieGemeente = registratieGemeente;
        this.tijdstip = Kopieer.timestamp(tijdstip);
    }

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
     * Geef de waarde van a-nummer.
     *
     * @return anummer
     */
    public Long getaNummer() {
        return aNummer;
    }

    /**
     * Zet de waarde van a-nummer.
     *
     * @param aNummer
     *            a-nummer
     */
    public void setaNummer(final Long aNummer) {
        ValidationUtils.controleerOpNullWaarden("aNummer mag niet null zijn", aNummer);
        this.aNummer = aNummer;
    }

    /**
     * Geef de waarde van reden blokkering.
     *
     * @return reden blokkering
     */
    public RedenBlokkering getRedenBlokkering() {
        return RedenBlokkering.parseId(redenBlokkeringId);
    }

    /**
     * Zet de waarde van reden blokkering.
     *
     * @param redenBlokkering
     *            reden blokkering
     */
    public void setRedenBlokkering(final RedenBlokkering redenBlokkering) {
        redenBlokkeringId = redenBlokkering.getId();
    }

    /**
     * Geef de waarde van process id.
     *
     * @return process id
     */
    public Long getProcessId() {
        return processId;
    }

    /**
     * Zet de waarde van process id.
     *
     * @param processId
     *            process id
     */
    public void setProcessId(final Long processId) {
        this.processId = processId;
    }

    /**
     * Geef de waarde van gemeente code naar.
     *
     * @return gemeente code naar
     */
    public String getGemeenteCodeNaar() {
        return gemeenteCodeNaar;
    }

    /**
     * Zet de waarde van gemeente code naar.
     *
     * @param gemeenteCodeNaar
     *            gemeente code naar
     */
    public void setGemeenteCodeNaar(final String gemeenteCodeNaar) {
        this.gemeenteCodeNaar = gemeenteCodeNaar;
    }

    /**
     * Geef de waarde van registratie gemeente.
     *
     * @return registratie gemeente
     */
    public String getRegistratieGemeente() {
        return registratieGemeente;
    }

    /**
     * Zet de waarde van registratie gemeente.
     *
     * @param registratieGemeente
     *            registratie gemeente
     */
    public void setRegistratieGemeente(final String registratieGemeente) {
        this.registratieGemeente = registratieGemeente;
    }

    /**
     * Geef de waarde van tijdstip.
     *
     * @return tijdstip
     */
    public Timestamp getTijdstip() {
        return Kopieer.timestamp(tijdstip);
    }

    /**
     * Zet de waarde van tijdstip.
     *
     * @param tijdstip
     *            tijdstip
     */
    public void setTijdstip(final Timestamp tijdstip) {
        this.tijdstip = Kopieer.timestamp(tijdstip);
    }

    /**
     * Maakt een BerichtLog obv de huidige datum en tijd.
     *
     * @param aNummer
     *            Het aNummer van de blokkering.
     * @param processId
     *            Het ID van het proces dat de blokkering aangeeft.
     * @param gemeenteCodeNaar
     *            De gemeente waarnaartoe de PL is verhuisd.
     * @param registratieGemeente
     *            De gemeente die de blokkering registreert.
     * @param redenBlokkering
     *            the reden blokkering
     * @return een nieuw BerichtLog object
     */
    public static Blokkering newInstance(
        final Long aNummer,
        final Long processId,
        final String gemeenteCodeNaar,
        final String registratieGemeente,
        final RedenBlokkering redenBlokkering)
    {
        final Blokkering result =
                new Blokkering(aNummer, processId, gemeenteCodeNaar, registratieGemeente, redenBlokkering, new Timestamp(System.currentTimeMillis()));
        return result;
    }

}
