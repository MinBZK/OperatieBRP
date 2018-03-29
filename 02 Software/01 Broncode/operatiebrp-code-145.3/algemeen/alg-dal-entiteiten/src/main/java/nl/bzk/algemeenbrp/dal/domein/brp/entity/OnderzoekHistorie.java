/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

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
import nl.bzk.algemeenbrp.dal.domein.brp.enums.StatusOnderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * The persistent class for the his_onderzoek database table.
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_onderzoek", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"onderzoek", "tsreg"}))
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

    @Column(name = "oms", length = 2_147_483_647)
    private String omschrijving;

    @Column(name = "status", nullable = false)
    private int statusOnderzoekId;

    // bi-directional many-to-one association to Onderzoek
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "onderzoek", nullable = false)
    private Onderzoek onderzoek;

    /**
     * JPA default constructor.
     */
    protected OnderzoekHistorie() {
    }

    /**
     * Maak een nieuwe onderzoek historie.
     * @param datumAanvang datum aanvang
     * @param statusOnderzoek status onderzoek
     * @param onderzoek onderzoek
     */
    public OnderzoekHistorie(final Integer datumAanvang, final StatusOnderzoek statusOnderzoek, final Onderzoek onderzoek) {
        setDatumAanvang(datumAanvang);
        setStatusOnderzoek(statusOnderzoek);
        setOnderzoek(onderzoek);
    }

    /**
     * Kopie constructor.
     * @param ander de te kopieren OnderzoekHistorie
     */
    public OnderzoekHistorie(final OnderzoekHistorie ander) {
        super(ander);
        datumAanvang = ander.datumAanvang;
        datumEinde = ander.datumEinde;
        omschrijving = ander.omschrijving;
        statusOnderzoekId = ander.statusOnderzoekId;
        onderzoek = ander.onderzoek;
    }

    @Override
    public Integer getId() {
        return convertLongNaarInteger(id);
    }

    /**
     * Zet de waarden voor id van OnderzoekHistorie.
     * @param id de nieuwe waarde voor id van OnderzoekHistorie
     */

    public void setId(final Integer id) {
        this.id = convertIntegerNaarLong(id);
    }

    /**
     * Geef de waarde van datum aanvang van OnderzoekHistorie.
     * @return de waarde van datum aanvang van OnderzoekHistorie
     */
    public Integer getDatumAanvang() {
        return datumAanvang;
    }

    /**
     * Zet de waarden voor datum aanvang van OnderzoekHistorie.
     * @param datumAanvang de nieuwe waarde voor datum aanvang van OnderzoekHistorie
     */
    public void setDatumAanvang(final Integer datumAanvang) {
        ValidationUtils.controleerOpNullWaarden("datumAanvang mag niet null zijn", datumAanvang);
        this.datumAanvang = datumAanvang;
    }

    /**
     * Geef de waarde van datum einde van OnderzoekHistorie.
     * @return de waarde van datum einde van OnderzoekHistorie
     */
    public Integer getDatumEinde() {
        return datumEinde;
    }

    /**
     * Zet de waarden voor datum einde van OnderzoekHistorie.
     * @param datumEinde de nieuwe waarde voor datum einde van OnderzoekHistorie
     */
    public void setDatumEinde(final Integer datumEinde) {
        this.datumEinde = datumEinde;
    }

    /**
     * Geef de waarde van omschrijving van OnderzoekHistorie.
     * @return de waarde van omschrijving van OnderzoekHistorie
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Zet de waarden voor omschrijving van OnderzoekHistorie.
     * @param omschrijving de nieuwe waarde voor omschrijving van OnderzoekHistorie
     */
    public void setOmschrijving(final String omschrijving) {
        this.omschrijving = omschrijving;
    }

    /**
     * Geef de waarde van status onderzoek van OnderzoekHistorie.
     * @return de waarde van status onderzoek van OnderzoekHistorie
     */
    public StatusOnderzoek getStatusOnderzoek() {
        return StatusOnderzoek.parseId(statusOnderzoekId);
    }

    /**
     * Zet de waarden voor status onderzoek van OnderzoekHistorie.
     * @param statusOnderzoek de nieuwe waarde voor status onderzoek van OnderzoekHistorie
     */
    public void setStatusOnderzoek(final StatusOnderzoek statusOnderzoek) {
        ValidationUtils.controleerOpNullWaarden("statusOnderzoek mag niet null zijn", statusOnderzoek);
        statusOnderzoekId = statusOnderzoek.getId();
    }

    /**
     * Geef de waarde van onderzoek van OnderzoekHistorie.
     * @return de waarde van onderzoek van OnderzoekHistorie
     */
    public Onderzoek getOnderzoek() {
        return onderzoek;
    }

    /**
     * Zet de waarden voor onderzoek van OnderzoekHistorie.
     * @param onderzoek de nieuwe waarde voor onderzoek van OnderzoekHistorie
     */
    public void setOnderzoek(final Onderzoek onderzoek) {
        ValidationUtils.controleerOpNullWaarden("onderzoek mag niet null zijn", onderzoek);
        this.onderzoek = onderzoek;
    }


    /**
     * Controleert of het meegegeven onderzoek inhoudelijk gelijk is aan dit onderzoek.
     * @param anderOnderzoek het onderzoek waarmee vergeleken gaat worden
     * @return true als het onderzoek inhoudelijk gelijk is aan dit onderzoek
     */
    public final boolean isInhoudelijkGelijk(final OnderzoekHistorie anderOnderzoek) {
        return new EqualsBuilder().append(datumAanvang, anderOnderzoek.datumAanvang).append(datumEinde, anderOnderzoek.datumEinde)
                .append(omschrijving, anderOnderzoek.omschrijving).append(statusOnderzoekId, anderOnderzoek.statusOnderzoekId).isEquals();
    }
}
