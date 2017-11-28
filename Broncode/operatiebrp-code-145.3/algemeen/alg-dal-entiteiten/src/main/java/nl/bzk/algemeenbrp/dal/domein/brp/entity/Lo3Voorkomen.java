/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

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
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;
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
@Table(name = "lo3voorkomen", schema = "verconv",
        uniqueConstraints = @UniqueConstraint(columnNames = {"lo3ber", "lo3categorie", "lo3stapelvolgnr", "lo3voorkomenvolgnr"}))
public class Lo3Voorkomen extends AbstractEntiteit implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "lo3voorkomen_id_generator", sequenceName = "verconv.seq_lo3voorkomen", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lo3voorkomen_id_generator")
    @Column(updatable = false)
    private Long id;

    @OneToOne(optional = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
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
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "voorkomen", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Set<Lo3Melding> meldingen = new LinkedHashSet<>(0);

    // bi-directional many-to-one association to Lo3Bericht
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "lo3ber", nullable = false)
    private Lo3Bericht bericht;

    /**
     * JPA default constructor.
     */
    protected Lo3Voorkomen() {}

    /**
     * Maak een nieuwe lo3 voorkomen.
     *
     * @param bericht bericht
     * @param categorie categorie
     */
    public Lo3Voorkomen(final Lo3Bericht bericht, final String categorie) {
        setBericht(bericht);
        setCategorie(categorie);
    }

    /**
     * Maak een nieuwe lo3 voorkomen.
     *
     * @param bericht bericht
     * @param categorie categorie
     * @param stapelvolgnummer stapelvolgnummer
     * @param voorkomenvolgnummer voorkomenvolgnummer
     */
    public Lo3Voorkomen(final Lo3Bericht bericht, final String categorie, final Integer stapelvolgnummer, final Integer voorkomenvolgnummer) {
        this(bericht, categorie);
        this.stapelvolgnummer = stapelvolgnummer;
        this.voorkomenvolgnummer = voorkomenvolgnummer;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.kern.entity.DeltaEntiteit#getId()
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van Lo3Voorkomen.
     *
     * @param id de nieuwe waarde voor id van Lo3Voorkomen
     */
    protected void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van actie van Lo3Voorkomen.
     *
     * @return de waarde van actie van Lo3Voorkomen
     */
    public BRPActie getActie() {
        return actie;
    }

    /**
     * Zet de waarden voor actie van Lo3Voorkomen.
     *
     * @param actie de nieuwe waarde voor actie van Lo3Voorkomen
     */
    public void setActie(final BRPActie actie) {
        this.actie = actie;
        if (actie != null) {
            actie.setLo3Voorkomen(this);
        }
    }

    /**
     * Geef de waarde van categorie van Lo3Voorkomen.
     *
     * @return de waarde van categorie van Lo3Voorkomen
     */
    public String getCategorie() {
        return categorie;
    }

    /**
     * Zet de waarden voor categorie van Lo3Voorkomen.
     *
     * @param categorie de nieuwe waarde voor categorie van Lo3Voorkomen
     */
    public void setCategorie(final String categorie) {
        ValidationUtils.controleerOpNullWaarden("categorie mag niet null zijn", categorie);
        ValidationUtils.controleerOpLegeWaarden("categorie mag geen lege string zijn", categorie);
        this.categorie = categorie;
    }

    /**
     * Geef de waarde van conversie sortering van Lo3Voorkomen.
     *
     * @return de waarde van conversie sortering van Lo3Voorkomen
     */
    public Integer getConversieSortering() {
        return conversieSortering;
    }

    /**
     * Zet de waarden voor conversie sortering van Lo3Voorkomen.
     *
     * @param conversieSortering de nieuwe waarde voor conversie sortering van Lo3Voorkomen
     */
    public void setConversieSortering(final Integer conversieSortering) {
        this.conversieSortering = conversieSortering;
    }

    /**
     * Geef de waarde van stapelvolgnummer van Lo3Voorkomen.
     *
     * @return de waarde van stapelvolgnummer van Lo3Voorkomen
     */
    public Integer getStapelvolgnummer() {
        return stapelvolgnummer;
    }

    /**
     * Zet de waarden voor stapelvolgnummer van Lo3Voorkomen.
     *
     * @param stapelvolgnummer de nieuwe waarde voor stapelvolgnummer van Lo3Voorkomen
     */
    public void setStapelvolgnummer(final Integer stapelvolgnummer) {
        this.stapelvolgnummer = stapelvolgnummer;
    }

    /**
     * Geef de waarde van voorkomenvolgnummer van Lo3Voorkomen.
     *
     * @return de waarde van voorkomenvolgnummer van Lo3Voorkomen
     */
    public Integer getVoorkomenvolgnummer() {
        return voorkomenvolgnummer;
    }

    /**
     * Zet de waarden voor voorkomenvolgnummer van Lo3Voorkomen.
     *
     * @param voorkomenvolgnummer de nieuwe waarde voor voorkomenvolgnummer van Lo3Voorkomen
     */
    public void setVoorkomenvolgnummer(final Integer voorkomenvolgnummer) {
        this.voorkomenvolgnummer = voorkomenvolgnummer;
    }

    /**
     * Geef de waarde van meldingen van Lo3Voorkomen.
     *
     * @return de waarde van meldingen van Lo3Voorkomen
     */
    public Set<Lo3Melding> getMeldingen() {
        return meldingen;
    }

    /**
     * Zet de waarden voor meldingen van Lo3Voorkomen.
     *
     * @param meldingen de nieuwe waarde voor meldingen van Lo3Voorkomen
     */
    public void setMeldingen(final Set<Lo3Melding> meldingen) {
        this.meldingen = meldingen;
    }

    /**
     * Maakt een bi-directionele relatie tussen melding en voorkomen.
     *
     * @param melding melding
     */
    public final void addMelding(final Lo3Melding melding) {
        melding.setVoorkomen(this);
        meldingen.add(melding);
    }

    /**
     * Geef de waarde van bericht van Lo3Voorkomen.
     *
     * @return de waarde van bericht van Lo3Voorkomen
     */
    public Lo3Bericht getBericht() {
        return bericht;
    }

    /**
     * Zet de waarden voor bericht van Lo3Voorkomen.
     *
     * @param bericht de nieuwe waarde voor bericht van Lo3Voorkomen
     */
    public void setBericht(final Lo3Bericht bericht) {
        ValidationUtils.controleerOpNullWaarden("bericht mag niet null zijn", bericht);
        this.bericht = bericht;
    }

    /**
     * Update.
     *
     * @param lo3Voorkomen lo3 voorkomen
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
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }
        final Lo3Voorkomen castOther = (Lo3Voorkomen) other;
        return new EqualsBuilder().append(categorie, castOther.categorie).append(stapelvolgnummer, castOther.stapelvolgnummer)
                .append(voorkomenvolgnummer, castOther.voorkomenvolgnummer).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(categorie).append(stapelvolgnummer).append(voorkomenvolgnummer).toHashCode();
    }

    @Override
    public final String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("id", id).append("categorie", categorie)
                .append("stapelvolgnummer", stapelvolgnummer).append("voorkomenvolgnummer", voorkomenvolgnummer).toString();
    }
}
