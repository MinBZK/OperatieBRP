/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

    @SequenceGenerator(name = "PARTIJ_SEQUENCE_GENERATOR", sequenceName = "kern.seq_partij")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PARTIJ_SEQUENCE_GENERATOR")
    private Long                     id;

    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "Naam")
    private String                   naam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "srt")
    private SoortPartij              soort;

    @ManyToOne
    @JoinColumn(name = "VoortzettendeGem")
    private Partij                   voortzettendeGemeente;

    @Column(name = "GemCode")
    private Integer                  gemeenteCode;

    @OneToMany(mappedBy = "geautoriseerde", fetch = FetchType.EAGER)
    private final Set<DoelBinding>   doelBindingen        = new HashSet<DoelBinding>();

    // Hosing: toegevoegd
    @Column(name = "DatAanv", precision = 8, scale = 0)
    private Integer                  dataanv;

    // Hosing: toegevoegd
    @Column(name = "DatEinde", precision = 8, scale = 0)
    private Integer                  dateinde;

    // Hosing: toegevoegd
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Sector")
    private Sector                   sector;

    // Hosing: toegevoegd
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "partij", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AuthenticatieMiddel> authenticatieMiddels = new HashSet<AuthenticatieMiddel>(0);

    // Hosing: toegevoegd
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "partij", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Partijrol>           partijrols           = new HashSet<Partijrol>(0);

    // Hosing: toegevoegd
    @Column(name = "PartijStatusHis", nullable = false, length = 1)
    private String                   partijStatushHis;

    // Hosing: toegevoegd
    @Column(name = "GemStatusHis", nullable = false, length = 1)
    private String                   gemStatusHis;

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

    public void setId(final Long id) {
        this.id = id;
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

    public Integer getDataanv() {
        return dataanv;
    }

    public void setDataanv(final Integer dataanv) {
        this.dataanv = dataanv;
    }

    public Integer getDateinde() {
        return dateinde;
    }

    public void setDateinde(final Integer dateinde) {
        this.dateinde = dateinde;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(final Sector sector) {
        this.sector = sector;
    }

    public Set<AuthenticatieMiddel> getAuthenticatieMiddels() {
        return authenticatieMiddels;
    }

    public void setAuthenticatieMiddels(final Set<AuthenticatieMiddel> authenticatieMiddels) {
        this.authenticatieMiddels = authenticatieMiddels;
    }

    public Set<Partijrol> getPartijrols() {
        return partijrols;
    }

    public void setPartijrols(final Set<Partijrol> partijrols) {
        this.partijrols = partijrols;
    }

    public String getPartijStatushHis() {
        return partijStatushHis;
    }

    public void setPartijStatushHis(final String partijStatushHis) {
        this.partijStatushHis = partijStatushHis;
    }

    public String getGemStatusHis() {
        return gemStatusHis;
    }

    public void setGemStatusHis(final String gemStatusHis) {
        this.gemStatusHis = gemStatusHis;
    }

    /**
     * Creeer een nieuwe doelbinding voor deze partij en voeg deze toe aan de verzameling doelbindingen.
     *
     * @param autorisatieBesluit het autorisatiebesluit dat aan deze partij wordt gebonden.
     * @return de doelbinding die gecreeerd wordt.
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
