/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.DynamischeStamtabel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The persistent class for the partij database table.
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "partij", schema = "kern")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@SuppressWarnings("checkstyle:designforextension")
public class Partij extends AbstractDeltaEntiteit implements Serializable, DynamischeStamtabel {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "partij_id_generator", sequenceName = "kern.seq_partij", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partij_id_generator")
    @Column(nullable = false, insertable = true, updatable = false)
    private Short id;

    @Column(nullable = false, insertable = true, updatable = true, length = 80, unique = true)
    private String naam;

    // uni-directional many-to-one association to SoortPartij
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "srt")
    private SoortPartij soortPartij;

    @Column(name = "code", nullable = false, insertable = true, updatable = false, unique = true)
    private int code;

    @Column(name = "datingang", insertable = true, updatable = true)
    private Integer datumIngang;

    @Column(name = "dateinde", insertable = true, updatable = false)
    private Integer datumEinde;

    @Column(insertable = true, updatable = true, length = 40)
    private String oin;

    @Column(name = "indverstrbeperkingmogelijk", nullable = true)
    private Boolean indicatieVerstrekkingsbeperkingMogelijk;

    @Column(name = "indautofiat")
    private Boolean indicatieAutomatischFiatteren;

    @Column(name = "datovergangnaarbrp")
    private Integer datumOvergangNaarBrp;

    @OneToMany(mappedBy = "partij", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private Set<PartijHistorie> hisPartijen = new LinkedHashSet<>(0);

    @OneToMany(mappedBy = "partij", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private Set<PartijBijhoudingHistorie> partijBijhoudingHistorieSet = new LinkedHashSet<>(0);

    /**
     * JPA default constructor.
     */
    protected Partij() {
    }

    /**
     * Maak een nieuwe partij.
     *
     * @param naam naam
     * @param code code
     */
    public Partij(final String naam, final int code) {
        setNaam(naam);
        this.code = code;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.DynamischeStamtabel#getId()
     */
    @Override
    public Short getId() {
        return id;
    }

    /**
     * Zet de waarde van id.
     *
     * @param id id
     */
    public void setId(final Short id) {
        this.id = id;
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
     * @param naam naam
     */
    public void setNaam(final String naam) {
        ValidationUtils.controleerOpNullWaarden("naam mag niet null zijn", naam);
        Validatie.controleerOpLegeWaarden("naam mag geen lege string zijn", naam);
        this.naam = naam;
    }

    /**
     * Geef de waarde van soort partij.
     *
     * @return soort partij
     */
    public SoortPartij getSoortPartij() {
        return soortPartij;
    }

    /**
     * Zet de waarde van soort partij.
     *
     * @param soortPartij soort partij
     */
    public void setSoortPartij(final SoortPartij soortPartij) {
        this.soortPartij = soortPartij;
    }

    /**
     * Geef de waarde van code.
     *
     * @return code uit GBA tabel 59.
     */
    public int getCode() {
        return code;
    }

    /**
     * Zet de waarde van code.
     *
     * @param code code
     */
    public void setCode(final int code) {
        this.code = code;
    }

    /**
     * Geef de waarde van datum ingang.
     *
     * @return datum ingang
     */
    public Integer getDatumIngang() {
        return datumIngang;
    }

    /**
     * Zet de waarde van datum ingang.
     *
     * @param datumIngang datum ingang
     */
    public void setDatumIngang(final Integer datumIngang) {
        this.datumIngang = datumIngang;
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
     * @param datumEinde datum einde
     */
    public void setDatumEinde(final Integer datumEinde) {
        this.datumEinde = datumEinde;
    }

    /**
     * Geef de waarde van oin.
     *
     * @return oin
     */
    public String getOin() {
        return oin;
    }

    /**
     * Zet de waarde van oin.
     *
     * @param oin oin
     */
    public void setOin(final String oin) {
        this.oin = oin;
    }

    /**
     * Controleert op indicatie verstrekkingsbeperking mogelijk.
     *
     * @return boolean
     */
    public Boolean isIndicatieVerstrekkingsbeperkingMogelijk() {
        return indicatieVerstrekkingsbeperkingMogelijk;
    }

    /**
     * Zet de waarde van indicatie verstrekkingsbeperking mogelijk.
     *
     * @param indicatieVerstrekkingsbeperkingMogelijk indicatie verstrekkingsbeperking mogelijk
     */
    public void setIndicatieVerstrekkingsbeperkingMogelijk(final Boolean indicatieVerstrekkingsbeperkingMogelijk) {
        this.indicatieVerstrekkingsbeperkingMogelijk = indicatieVerstrekkingsbeperkingMogelijk;
    }

    /**
     * Geef de waarde van indicatieAutomatischFiatteren.
     *
     * @return indicatieAutomatischFiatteren
     */
    public Boolean getIndicatieAutomatischFiatteren() {
        return indicatieAutomatischFiatteren;
    }

    /**
     * Zet de waarde van indicatieAutomatischFiatteren.
     *
     * @param indicatieAutomatischFiatteren indicatieAutomatischFiatteren
     */
    public void setIndicatieAutomatischFiatteren(final Boolean indicatieAutomatischFiatteren) {
        this.indicatieAutomatischFiatteren = indicatieAutomatischFiatteren;
    }

    /**
     * Geef de waarde van datumOvergangNaarBrp.
     *
     * @return datumOvergangNaarBrp
     */
    public Integer getDatumOvergangNaarBrp() {
        return datumOvergangNaarBrp;
    }

    /**
     * Zet de waarde van datumOvergangNaarBrp.
     *
     * @param datumOvergangNaarBrp datumOvergangNaarBrp
     */
    public void setDatumOvergangNaarBrp(final Integer datumOvergangNaarBrp) {
        this.datumOvergangNaarBrp = datumOvergangNaarBrp;
    }

    /**
     * Geef de waarde van his partijen.
     *
     * @return his partijen
     */
    public Set<PartijHistorie> getHisPartijen() {
        return hisPartijen;
    }

    /**
     * Toevoegen van een his partij.
     *
     * @param hisPartij his partij
     */
    public void addHisPartij(final PartijHistorie hisPartij) {
        hisPartij.setPartij(this);
        hisPartijen.add(hisPartij);
    }

    /**
     * Geef de waarde van his partij bijhoudingen.
     *
     * @return hisPartijBijhoudingen
     */
    public Set<PartijBijhoudingHistorie> getPartijBijhoudingHistorieSet() {
        return partijBijhoudingHistorieSet;
    }

    /**
     * Toevoegen van een his partij.
     *
     * @param partijBijhoudingHistorie partijBijhoudingHistorie
     */
    public void addPartijBijhoudingHistorie(final PartijBijhoudingHistorie partijBijhoudingHistorie) {
        partijBijhoudingHistorie.setPartij(this);
        partijBijhoudingHistorieSet.add(partijBijhoudingHistorie);
    }

    /**
     * Als de naam van twee partijen gelijk zijn dan worden ze als inhoudelijk gelijk beschouwd.
     *
     * @param anderePartij de andere partij waaarme vergeleken wordt
     * @return true als de naam van deze partij gelijk is aan de andere partij, anders false
     */
    @SuppressWarnings("checkstyle:returncount")
    public boolean isInhoudelijkGelijkAan(final Partij anderePartij) {
        if (this == anderePartij) {
            return true;
        }
        if (anderePartij == null) {
            return false;
        }
        if (getNaam() == null) {
            if (anderePartij.getNaam() != null) {
                return false;
            }
        } else if (!getNaam().equals(anderePartij.getNaam())) {
            return false;
        }
        return true;
    }

    @Override
    public final String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("id", id).toString();
    }
}
