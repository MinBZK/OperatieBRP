/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * The persistent class for the lo3voorkomen database table.
 * 
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "lo3voorkomen", schema = "verconv", uniqueConstraints = @UniqueConstraint(columnNames = {"lo3ber",
                                                                                                       "lo3categorie",
                                                                                                       "lo3stapelvolgnr",
                                                                                                       "lo3voorkomenvolgnr" }))
@SuppressWarnings("checkstyle:designforextension")
public class Lo3Voorkomen extends AbstractDeltaEntiteit implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "lo3voorkomen_id_generator", sequenceName = "verconv.seq_lo3voorkomen", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lo3voorkomen_id_generator")
    @Column(updatable = false)
    private Long id;

    @OneToOne(optional = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "actie", unique = true)
    private BRPActie actie;

    @Column(name = "lo3categorie", nullable = false)
    private String categorie;

    @Column(name = "lo3conversiesortering")
    private Integer conversieSortering;

    @Column(name = "lo3stapelvolgnr")
    private Integer stapelvolgnummer;

    @Column(name = "lo3voorkomenvolgnr")
    private Integer voorkomenvolgnummer;

    // bi-directional many-to-one association to Lo3Melding
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "voorkomen", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
    private Set<Lo3Melding> meldingen = new LinkedHashSet<>(0);

    // bi-directional many-to-one association to Lo3Bericht
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "lo3ber", nullable = false)
    private Lo3Bericht bericht;

    /**
     * JPA default constructor.
     */
    protected Lo3Voorkomen() {
    }

    /**
     * Maak een nieuwe lo3 voorkomen.
     *
     * @param bericht
     *            bericht
     * @param categorie
     *            categorie
     */
    public Lo3Voorkomen(final Lo3Bericht bericht, final String categorie) {
        setBericht(bericht);
        setCategorie(categorie);
    }

    /**
     * Maak een nieuwe lo3 voorkomen.
     *
     * @param bericht
     *            bericht
     * @param categorie
     *            categorie
     * @param stapelvolgnummer
     *            stapelvolgnummer
     * @param voorkomenvolgnummer
     *            voorkomenvolgnummer
     */
    public Lo3Voorkomen(final Lo3Bericht bericht, final String categorie, final Integer stapelvolgnummer, final Integer voorkomenvolgnummer) {
        this(bericht, categorie);
        this.stapelvolgnummer = stapelvolgnummer;
        this.voorkomenvolgnummer = voorkomenvolgnummer;
    }

    /**
     * Geef de waarde van id.
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Zet de waarde van id.
     *
     * @param id
     *            id
     */
    protected void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van actie.
     *
     * @return actie
     */
    public BRPActie getActie() {
        return actie;
    }

    /**
     * Zet de waarde van actie.
     *
     * @param actie
     *            actie
     */
    public void setActie(final BRPActie actie) {
        this.actie = actie;
        if (actie != null) {
            actie.setLo3Voorkomen(this);
        }
    }

    /**
     * Geef de waarde van categorie.
     *
     * @return categorie
     */
    public String getCategorie() {
        return categorie;
    }

    /**
     * Zet de waarde van categorie.
     *
     * @param categorie
     *            categorie
     */
    public void setCategorie(final String categorie) {
        ValidationUtils.controleerOpNullWaarden("categorie mag niet null zijn", categorie);
        Validatie.controleerOpLegeWaarden("categorie mag geen lege string zijn", categorie);
        this.categorie = categorie;
    }

    /**
     * Geef de waarde van conversie sortering.
     *
     * @return conversie sortering
     */
    public Integer getConversieSortering() {
        return conversieSortering;
    }

    /**
     * Zet de waarde van conversie sortering.
     *
     * @param conversieSortering
     *            conversie sortering
     */
    public void setConversieSortering(final Integer conversieSortering) {
        this.conversieSortering = conversieSortering;
    }

    /**
     * Geef de waarde van stapelvolgnummer.
     *
     * @return stapelvolgnummer
     */
    public Integer getStapelvolgnummer() {
        return stapelvolgnummer;
    }

    /**
     * Zet de waarde van stapelvolgnummer.
     *
     * @param stapelvolgnummer
     *            stapelvolgnummer
     */
    public void setStapelvolgnummer(final Integer stapelvolgnummer) {
        this.stapelvolgnummer = stapelvolgnummer;
    }

    /**
     * Geef de waarde van voorkomenvolgnummer.
     *
     * @return voorkomenvolgnummer
     */
    public Integer getVoorkomenvolgnummer() {
        return voorkomenvolgnummer;
    }

    /**
     * Zet de waarde van voorkomenvolgnummer.
     *
     * @param voorkomenvolgnummer
     *            voorkomenvolgnummer
     */
    public void setVoorkomenvolgnummer(final Integer voorkomenvolgnummer) {
        this.voorkomenvolgnummer = voorkomenvolgnummer;
    }

    /**
     * Geef de waarde van meldingen.
     *
     * @return meldingen
     */
    public Set<Lo3Melding> getMeldingen() {
        return meldingen;
    }

    /**
     * Zet de waarde van meldingen.
     *
     * @param meldingen
     *            meldingen
     */
    public void setMeldingen(final Set<Lo3Melding> meldingen) {
        this.meldingen = meldingen;
    }

    /**
     * Maakt een bi-directionele relatie tussen melding en voorkomen.
     * 
     * @param melding
     *            melding
     */
    public final void addMelding(final Lo3Melding melding) {
        melding.setVoorkomen(this);
        meldingen.add(melding);
    }

    /**
     * Geef de waarde van bericht.
     *
     * @return bericht
     */
    public Lo3Bericht getBericht() {
        return bericht;
    }

    /**
     * Zet de waarde van bericht.
     *
     * @param bericht
     *            bericht
     */
    public void setBericht(final Lo3Bericht bericht) {
        ValidationUtils.controleerOpNullWaarden("bericht mag niet null zijn", bericht);
        this.bericht = bericht;
    }

    /**
     * Update.
     *
     * @param lo3Voorkomen
     *            lo3 voorkomen
     */
    public void update(final Lo3Voorkomen lo3Voorkomen) {
        categorie = lo3Voorkomen.getCategorie();
        stapelvolgnummer = lo3Voorkomen.getStapelvolgnummer();
        voorkomenvolgnummer = lo3Voorkomen.getVoorkomenvolgnummer();
        conversieSortering = lo3Voorkomen.getConversieSortering();
        meldingen = lo3Voorkomen.getMeldingen();
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Lo3Voorkomen)) {
            return false;
        }
        final Lo3Voorkomen castOther = (Lo3Voorkomen) other;
        return new EqualsBuilder().append(categorie, castOther.categorie)
                                  .append(stapelvolgnummer, castOther.stapelvolgnummer)
                                  .append(voorkomenvolgnummer, castOther.voorkomenvolgnummer)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(categorie).append(stapelvolgnummer).append(voorkomenvolgnummer).toHashCode();
    }

    @Override
    public final String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                                                                          .append("id", id)
                                                                          .append("categorie", categorie)
                                                                          .append("stapelvolgnummer", stapelvolgnummer)
                                                                          .append("voorkomenvolgnummer", voorkomenvolgnummer)
                                                                          .toString();
    }
}
