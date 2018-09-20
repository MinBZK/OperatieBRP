/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.blokkering.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the betr database table.
 * 
 */
@Entity
@Table(name = "mig_blokkering", schema = "blokkering")
/*
 * CHECKSTYLE:OFF Deze class is gegenereerd o.b.v. het BRP datamodel en bevat daarom geen javadoc, daarnaast mogen
 * entities en de methoden van entities niet final zijn.
 */
public class Blokkering implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "BLOK_ID_GENERATOR", sequenceName = "blokkering.mig_blokkering_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BLOK_ID_GENERATOR")
    @Column(nullable = false)
    private Integer id;

    @Column(name = "aNummer", nullable = false, length = 7)
    private String aNummer;

    @Enumerated(EnumType.STRING)
    @Column(name = "persoonsAanduiding", nullable = true, length = 31)
    private String persoonsAanduiding;

    @Column(name = "process_id", nullable = true)
    private Long processId;

    @Column(name = "gemeentecode_naar", nullable = true, length = 4)
    private String gemeenteCodeNaar;

    @Column(name = "registratieGemeente", nullable = true, length = 4)
    private String registratieGemeente;

    @Column(name = "tijdstip", nullable = true, length = 4)
    private Timestamp tijdstip;

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
     * @param persoonsAanduiding
     *            De persoonsaanduiding.
     * @param tijdstip
     *            Het tijdstip van blokkeren.
     */
    public Blokkering(
            final String aNummer,
            final Long processId,
            final String gemeenteCodeNaar,
            final String registratieGemeente,
            final String persoonsAanduiding,
            final Timestamp tijdstip) {
        if (aNummer == null) {
            throw new NullPointerException("Administratienummer mag niet null zijn");
        }
        if (tijdstip == null) {
            throw new NullPointerException("tijdstipVerwerking mag niet null zijn");
        }
        this.aNummer = aNummer;
        this.gemeenteCodeNaar = gemeenteCodeNaar;
        this.persoonsAanduiding = persoonsAanduiding;
        this.processId = processId;
        this.registratieGemeente = registratieGemeente;
        this.tijdstip = tijdstip;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getaNummer() {
        return aNummer;
    }

    public void setaNummer(final String aNummer) {
        this.aNummer = aNummer;
    }

    public String getPersoonsAanduiding() {
        return persoonsAanduiding;
    }

    public void setPersoonsAanduiding(final String persoonsAanduiding) {
        this.persoonsAanduiding = persoonsAanduiding;
    }

    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(final Long processId) {
        this.processId = processId;
    }

    public String getGemeenteCodeNaar() {
        return gemeenteCodeNaar;
    }

    public void setGemeenteCodeNaar(final String gemeenteCodeNaar) {
        this.gemeenteCodeNaar = gemeenteCodeNaar;
    }

    public String getRegistratieGemeente() {
        return registratieGemeente;
    }

    public void setRegistratieGemeente(final String registratieGemeente) {
        this.registratieGemeente = registratieGemeente;
    }

    public Timestamp getTijdstip() {
        return tijdstip;
    }

    public void setTijdstip(final Timestamp tijdstip) {
        this.tijdstip = tijdstip;
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
     * @param persoonsAanduiding
     *            De persoonsaanduiding.
     * @return een nieuw BerichtLog object
     */
    public static Blokkering newInstance(
            final String aNummer,
            final Long processId,
            final String gemeenteCodeNaar,
            final String registratieGemeente,
            final String persoonsAanduiding) {
        final Blokkering result =
                new Blokkering(aNummer, processId, gemeenteCodeNaar, registratieGemeente, persoonsAanduiding,
                        new Timestamp(System.currentTimeMillis()));
        return result;
    }

}
