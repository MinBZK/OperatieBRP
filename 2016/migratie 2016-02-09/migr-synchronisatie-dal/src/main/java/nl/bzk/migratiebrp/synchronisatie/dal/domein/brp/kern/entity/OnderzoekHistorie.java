/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;

/**
 * The persistent class for the his_onderzoek database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_onderzoek", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"onderzoek", "tsreg" }))
@SuppressWarnings("checkstyle:designforextension")
public class OnderzoekHistorie extends AbstractFormeleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_onderzoek_id_generator", sequenceName = "kern.seq_his_onderzoek", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_onderzoek_id_generator")
    @Column(nullable = false)
    /**
     * Het veld zou Integer moeten zijn maar is Long i.v.m. de link naar GegevenInOnderzoek.
     */
    private Long id;

    @Column(name = "dataanv", nullable = false)
    private Integer datumAanvang;

    @Column(name = "dateinde")
    private Integer datumEinde;

    @Column(name = "oms", length = 2147483647)
    private String omschrijving;

    @Column(name = "status", nullable = false)
    private short statusOnderzoekId;

    @Column(name = "verwachteafhandeldat", nullable = false)
    private Integer verwachteAfhandelDatum;

    // bi-directional many-to-one association to Onderzoek
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "onderzoek", nullable = false)
    private Onderzoek onderzoek;

    /**
     * JPA default constructor.
     */
    protected OnderzoekHistorie() {
    }

    /**
     * Maak een nieuwe onderzoek historie.
     *
     * @param datumAanvang
     *            datum aanvang
     * @param statusOnderzoek
     *            status onderzoek
     * @param verwachteAfhandelDatum
     *            verwachte afhandel datum
     * @param onderzoek
     *            onderzoek
     */
    public OnderzoekHistorie(
        final Integer datumAanvang,
        final StatusOnderzoek statusOnderzoek,
        final Integer verwachteAfhandelDatum,
        final Onderzoek onderzoek)
    {
        setDatumAanvang(datumAanvang);
        setStatusOnderzoek(statusOnderzoek);
        setVerwachteAfhandelDatum(verwachteAfhandelDatum);
        setOnderzoek(onderzoek);
    }

    /**
     * Geef de Id waarde voor de entiteit. Intern is de Id een Long voor integratie met GegevenInOnderzoek. De waarde
     * wordt geconverteerd naar een Integer.
     *
     * @return de Id waarde.
     */
    public Integer getId() {
        return convertLongNaarInteger(id);
    }

    /**
     * Zet de Id waarde voor de entiteit. Intern wordt de Id waarde geconverteert naar een Long voor integratie met
     * GegevenInOnderzoek.
     *
     * @param id
     *            de Id waarde.
     */

    public void setId(final Integer id) {
        this.id = convertIntegerNaarLong(id);
    }

    /**
     * Geef de waarde van datum aanvang.
     *
     * @return datum aanvang
     */
    public Integer getDatumAanvang() {
        return datumAanvang;
    }

    /**
     * Zet de waarde van datum aanvang.
     *
     * @param datumAanvang
     *            datum aanvang
     */
    public void setDatumAanvang(final Integer datumAanvang) {
        ValidationUtils.controleerOpNullWaarden("datumAanvang mag niet null zijn", datumAanvang);
        this.datumAanvang = datumAanvang;
    }

    /**
     * Geef de waarde van datum einde.
     *
     * @return datum einde
     */
    public Integer getDatumEinde() {
        return datumEinde;
    }

    /**
     * Zet de waarde van datum einde.
     *
     * @param datumEinde
     *            datum einde
     */
    public void setDatumEinde(final Integer datumEinde) {
        this.datumEinde = datumEinde;
    }

    /**
     * Geef de waarde van omschrijving.
     *
     * @return omschrijving
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Zet de waarde van omschrijving.
     *
     * @param omschrijving
     *            omschrijving
     */
    public void setOmschrijving(final String omschrijving) {
        this.omschrijving = omschrijving;
    }

    /**
     * Geef de waarde van status onderzoek.
     *
     * @return status onderzoek
     */
    public StatusOnderzoek getStatusOnderzoek() {
        return StatusOnderzoek.parseId(statusOnderzoekId);
    }

    /**
     * Zet de waarde van status onderzoek.
     *
     * @param statusOnderzoek
     *            status onderzoek
     */
    public void setStatusOnderzoek(final StatusOnderzoek statusOnderzoek) {
        ValidationUtils.controleerOpNullWaarden("statusOnderzoek mag niet null zijn", statusOnderzoek);
        statusOnderzoekId = statusOnderzoek.getId();
    }

    /**
     * Geef de waarde van verwachte afhandel datum.
     *
     * @return verwachte afhandel datum
     */
    public Integer getVerwachteAfhandelDatum() {
        return verwachteAfhandelDatum;
    }

    /**
     * Zet de waarde van verwachte afhandel datum.
     *
     * @param verwachteAfhandelDatum
     *            verwachte afhandel datum
     */
    public void setVerwachteAfhandelDatum(final Integer verwachteAfhandelDatum) {
        ValidationUtils.controleerOpNullWaarden("verwachteAfhandelDatum mag niet null zijn", verwachteAfhandelDatum);
        this.verwachteAfhandelDatum = verwachteAfhandelDatum;
    }

    /**
     * Geef de waarde van onderzoek.
     *
     * @return onderzoek
     */
    public Onderzoek getOnderzoek() {
        return onderzoek;
    }

    /**
     * Zet de waarde van onderzoek.
     *
     * @param onderzoek
     *            onderzoek
     */
    public void setOnderzoek(final Onderzoek onderzoek) {
        ValidationUtils.controleerOpNullWaarden("onderzoek mag niet null zijn", onderzoek);
        this.onderzoek = onderzoek;
    }

}
