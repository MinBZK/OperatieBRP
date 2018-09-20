/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nl.bzk.brp.bevraging.domein.aut.AutorisatieBesluit;
import nl.bzk.brp.bevraging.domein.aut.DoelBinding;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * Een Partij is een voor de BRP relevant Bestuursorgaan, of een onderdeel
 * daarvan, die met een bepaalde doelstelling is aangesloten op de BRP.
 *
 * Dit betreft onder andere gemeenten, maar ook aangewezen bestuursorganen en afnemers.
 */
@Entity
@Table(name = "Partij", schema = "Kern")
@Access(AccessType.FIELD)
public class Partij implements Serializable {

    @SequenceGenerator(name = "PARTIJ_SEQUENCE_GENERATOR", sequenceName = "Kern.seq_Partij")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PARTIJ_SEQUENCE_GENERATOR")
    private Long             id;

    @Column(name = "Naam")
    private String           naam;

    @Column(name = "Srt")
    private SoortPartij      soort;

    @ManyToOne
    @JoinColumn(name = "VoortzettendeGem")
    private Partij           voortzettendeGemeente;

    @Column(name = "GemCode")
    private Integer          gemeenteCode;

    @OneToMany(mappedBy = "geautoriseerde", fetch = FetchType.EAGER)
    private Set<DoelBinding> doelBindingen = new HashSet<DoelBinding>();

    /**
     * No-arg constructor voor JPA.
     */
    protected Partij() {
    }

    /**
     * Constructor voor nieuwe instanties.
     *
     * @param soort
     *            de {@link SoortPartij} is verplicht.
     */
    public Partij(final SoortPartij soort) {
        this.soort = soort;
    }

    public Long getId() {
        return id;
    }

    public Integer getGemeenteCode() {
        return gemeenteCode;
    }

    public void setGemeenteCode(final Integer gemeenteCode) {
        this.gemeenteCode = gemeenteCode;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(final String naam) {
        this.naam = naam;
    }

    public SoortPartij getSoort() {
        return soort;
    }

    public void setSoort(final SoortPartij soort) {
        this.soort = soort;
    }

    public Partij getVoortzettendeGemeente() {
        return voortzettendeGemeente;
    }

    public void setVoortzettendeGemeente(final Partij voortzettendeGemeente) {
        this.voortzettendeGemeente = voortzettendeGemeente;
    }

    public Set<DoelBinding> getDoelBindingen() {
        return Collections.unmodifiableSet(doelBindingen);
    }

    /**
     * Creëer een nieuwe doelbinding voor deze partij en voeg deze toe aan de verzameling doelbindingen.
     *
     * @param autorisatieBesluit het autorisatiebesluit dat aan deze partij wordt gebonden.
     * @return de doelbinding die gecreëerd wordt.
     */
    public DoelBinding createDoelbinding(final AutorisatieBesluit autorisatieBesluit) {
        if (autorisatieBesluit == null) {
            throw new IllegalArgumentException("autorisatieBesluit is een verplichte paramter");
        }
        DoelBinding result = new DoelBinding(autorisatieBesluit, this);
        doelBindingen.add(result);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }
}
