/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import java.io.Serializable;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.DynamischeStamtabel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The persistent class for the gem database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "gem", schema = "kern")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@SuppressWarnings("checkstyle:designforextension")
public class Gemeente extends AbstractDeltaEntiteit implements Serializable, DynamischeStamtabel {
    private static final long serialVersionUID = 1L;
    private static final String NAAM_MAG_NIET_NULL_ZIJN = "naam mag niet null zijn";
    private static final String NAAM_MAG_GEEN_LEGE_STRING_ZIJN = "naam mag geen lege string zijn";
    private static final String PARTIJ_MAG_NIET_NULL_ZIJN = "partij mag niet null zijn";

    @Id
    @Column(nullable = false, insertable = false, updatable = false)
    private Short id;

    @Column(nullable = false, insertable = false, updatable = false, unique = true)
    private short code;

    @Column(name = "dataanvgel", insertable = false, updatable = false)
    private Integer datumAanvangGeldigheid;

    @Column(name = "dateindegel", insertable = false, updatable = false)
    private Integer datumEindeGeldigheid;

    @Column(nullable = false, insertable = false, updatable = false)
    private String naam;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "partij", insertable = false, updatable = false, nullable = false)
    private Partij partij;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "voortzettendegem")
    private Gemeente voortzettendeGemeente;

    /**
     * JPA default constructor.
     */
    protected Gemeente() {
    }

    /**
     * Maak een nieuwe gemeente.
     *
     * @param id
     *            id
     * @param naam
     *            naam
     * @param code
     *            code
     * @param partij
     *            partij
     */
    public Gemeente(final Short id, final String naam, final short code, final Partij partij) {
        this.id = id;
        ValidationUtils.controleerOpNullWaarden(NAAM_MAG_NIET_NULL_ZIJN, naam);
        Validatie.controleerOpLegeWaarden(NAAM_MAG_GEEN_LEGE_STRING_ZIJN, naam);
        this.naam = naam;
        this.code = code;
        ValidationUtils.controleerOpNullWaarden(PARTIJ_MAG_NIET_NULL_ZIJN, partij);
        this.partij = partij;
    }

    @Override
    public Short getId() {
        return id;
    }

    /**
     * Zet de waarde van id.
     *
     * @param id
     *            id
     */
    public void setId(final Short id) {
        this.id = id;
    }

    /**
     * Geef de waarde van code.
     *
     * @return code
     */
    public short getCode() {
        return code;
    }

    /**
     * Zet de waarde van code.
     *
     * @param code
     *            code
     */
    public void setCode(final short code) {
        this.code = code;
    }

    /**
     * Geef de waarde van naam.
     *
     * @return naam
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Zet de waarde van naam.
     *
     * @param naam
     *            naam
     */
    public void setNaam(final String naam) {
        ValidationUtils.controleerOpNullWaarden(NAAM_MAG_NIET_NULL_ZIJN, naam);
        Validatie.controleerOpLegeWaarden(NAAM_MAG_GEEN_LEGE_STRING_ZIJN, naam);
        this.naam = naam;
    }

    /**
     * Geef de waarde van partij.
     *
     * @return partij
     */
    public Partij getPartij() {
        return partij;
    }

    /**
     * Zet de waarde van partij.
     *
     * @param partij
     *            partij
     */
    public void setPartij(final Partij partij) {
        ValidationUtils.controleerOpNullWaarden(PARTIJ_MAG_NIET_NULL_ZIJN, partij);
        this.partij = partij;
    }

    /**
     * Geef de waarde van datum aanvang geldigheid.
     *
     * @return datum aanvang geldigheid
     */
    public Integer getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Zet de waarde van datum aanvang geldigheid.
     *
     * @param datumAanvangGeldigheid
     *            datum aanvang geldigheid
     */
    public void setDatumAanvangGeldigheid(final Integer datumAanvangGeldigheid) {
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
    }

    /**
     * Geef de waarde van datum einde geldigheid.
     *
     * @return datum einde geldigheid
     */
    public Integer getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    /**
     * Zet de waarde van datum einde geldigheid.
     *
     * @param datumEindeGeldigheid
     *            datum einde geldigheid
     */
    public void setDatumEindeGeldigheid(final Integer datumEindeGeldigheid) {
        this.datumEindeGeldigheid = datumEindeGeldigheid;
    }

    /**
     * Geef de waarde van voortzettende gemeente.
     *
     * @return voortzettende gemeente
     */
    public Gemeente getVoortzettendeGemeente() {
        return voortzettendeGemeente;
    }

    /**
     * Zet de waarde van voortzettende gemeente.
     *
     * @param voortzettendeGemeente
     *            voortzettende gemeente
     */
    public void setVoortzettendeGemeente(final Gemeente voortzettendeGemeente) {
        this.voortzettendeGemeente = voortzettendeGemeente;
    }

    /**
     * Als de naam van twee partijen gelijk zijn dan worden ze als inhoudelijk gelijk beschouwd.
     *
     * @param andereGemeente
     *            de andere gemeente waaarme vergeleken wordt
     * @return true als de code van deze gemeente gelijk is aan de andere gemeente, anders false
     */
    public boolean isInhoudelijkGelijkAan(final Gemeente andereGemeente) {
        if (this == andereGemeente) {
            return true;
        }
        if (andereGemeente == null) {
            return false;
        }
        return getCode() == andereGemeente.getCode();
    }
}
