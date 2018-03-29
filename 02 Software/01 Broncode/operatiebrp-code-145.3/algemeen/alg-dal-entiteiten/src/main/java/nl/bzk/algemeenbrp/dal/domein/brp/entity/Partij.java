/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Cacheable;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.algemeenbrp.dal.domein.brp.annotation.IndicatieActueelEnGeldig;
import nl.bzk.algemeenbrp.dal.domein.brp.annotation.NegerenVoorALaagAfleiding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.util.Geldigheid;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NamedQuery;

/**
 * The persistent class for the partij database table.
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = Partij.PARTIJ_LABEL, schema = "kern")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Access(AccessType.FIELD)
@NamedQuery(name = "Partij" + Constanten.ZOEK_ALLES_VOOR_CACHE,
        query = "select p from Partij p left join fetch p.hisPartijen left join fetch p.partijBijhoudingHistorieSet left join fetch p.partijRolSet pr "
                + "left join fetch pr.partijRolHistorieSet")
public class Partij extends AbstractEntiteit implements Afleidbaar, Serializable, DynamischeStamtabel, Geldigheid {
    /**
     * Vaste waarde: de partijcode van de Minister (199901).
     */
    public static final String PARTIJCODE_MINISTER = "199901";

    /**
     * Vaste waarde: de partijcode van BRP Voorziening (199903).
     */
    public static final String PARTIJ_CODE_BRP = "199903";

    /**
     * Database tabel naam.
     */
    static final String PARTIJ_LABEL = "partij";

    private static final EnumSet<Rol>
            BIJHOUDER_ROLLEN =
            EnumSet.of(Rol.BIJHOUDINGSORGAAN_COLLEGE, Rol.BIJHOUDINGSORGAAN_MINISTER, Rol.BIJHOUDINGSVOORSTELORGAAN);

    private static final long serialVersionUID = 1L;
    private static final int CODE_LENGTE = 6;

    @Id
    @SequenceGenerator(name = "partij_id_generator", sequenceName = "kern.seq_partij", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partij_id_generator")
    @Column(nullable = false, updatable = false)
    @Access(AccessType.PROPERTY)
    private Short id;

    @Column(nullable = false, length = 80, unique = true)
    private String naam;

    // uni-directional many-to-one association to SoortPartij
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "srt")
    private SoortPartij soortPartij;

    @Column(name = "code", nullable = false, updatable = false, unique = true, length = 6)
    private String code;

    @Column(name = "datingang")
    private Integer datumIngang;

    @Column(name = "dateinde", updatable = false)
    private Integer datumEinde;

    @Column(length = 40)
    private String oin;

    @Column(name = "indverstrbeperkingmogelijk")
    private Boolean indicatieVerstrekkingsbeperkingMogelijk;

    @Column(name = "indautofiat")
    private Boolean indicatieAutomatischFiatteren;

    @Column(name = "datovergangnaarbrp")
    private Integer datumOvergangNaarBrp;

    @Column(name = "indag", nullable = false)
    private boolean isActueelEnGeldig;

    @Column(name = "indagbijhouding", nullable = false)
    private boolean isActueelEnGeldigVoorBijhouding;

    // uni-directional many-to-one association to Partij
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ondertekenaarvrijber")
    private Partij ondertekenaarVrijBericht;

    // uni-directional many-to-one association to Partij
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transporteurvrijber")
    private Partij transporteurVrijBericht;

    @Column(name = "datingangvrijber")
    private Integer datumIngangVrijBericht;

    @Column(name = "dateindevrijber")
    private Integer datumEindeVrijBericht;

    @Column(name = "afleverpuntvrijber")
    private String afleverpuntVrijBericht;

    @Column(name = "indblokvrijber")
    private Boolean isVrijBerichtGeblokkeerd;

    @Column(name = "indagvrijber", nullable = false)
    private boolean isActueelEnGeldigVoorVrijBericht;

    @IndicatieActueelEnGeldig(naam = "isActueelEnGeldig")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = PARTIJ_LABEL, cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<PartijHistorie> hisPartijen = new LinkedHashSet<>(0);

    @IndicatieActueelEnGeldig(naam = "isActueelEnGeldigVoorBijhouding")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = PARTIJ_LABEL, cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<PartijBijhoudingHistorie> partijBijhoudingHistorieSet = new LinkedHashSet<>(0);

    @IndicatieActueelEnGeldig(naam = "isActueelEnGeldigVoorVrijBericht")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = PARTIJ_LABEL, cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<PartijVrijBerichtHistorie> partijVrijBerichtHistorieSet = new LinkedHashSet<>(0);

    @OneToMany(fetch = FetchType.EAGER, mappedBy = PARTIJ_LABEL, cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<PartijRol> partijRolSet = new LinkedHashSet<>(0);

    @NegerenVoorALaagAfleiding
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PARTIJ_LABEL)
    private final Set<Gemeente> gemeenteSet = new LinkedHashSet<>(0);

    /**
     * JPA default constructor.
     */
    protected Partij() {
    }

    /**
     * Maak een nieuwe partij.
     * @param naam naam
     * @param code code
     */
    public Partij(final String naam, final String code) {
        setNaam(naam);
        setCode(code);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.DynamischeStamtabel#getId()
     */
    @Override
    public Short getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van Partij.
     * @param id de nieuwe waarde voor id van Partij
     */
    public void setId(final Short id) {
        this.id = id;
    }

    /**
     * Geef de waarde van naam van Partij.
     * @return de waarde van naam van Partij
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Zet de waarden voor naam van Partij.
     * @param naam de nieuwe waarde voor naam van Partij
     */
    public void setNaam(final String naam) {
        ValidationUtils.controleerOpNullWaarden("naam mag niet null zijn", naam);
        ValidationUtils.controleerOpLegeWaarden("naam mag geen lege string zijn", naam);
        this.naam = naam;
    }

    /**
     * Geef de waarde van soort partij van Partij.
     * @return de waarde van soort partij van Partij
     */
    public SoortPartij getSoortPartij() {
        return soortPartij;
    }

    /**
     * Zet de waarden voor soort partij van Partij.
     * @param soortPartij de nieuwe waarde voor soort partij van Partij
     */
    public void setSoortPartij(final SoortPartij soortPartij) {
        this.soortPartij = soortPartij;
    }

    /**
     * Geef de waarde van code van Partij.
     * @return de waarde van code van Partij
     */
    public String getCode() {
        return code;
    }

    /**
     * Zet de waarden voor code van Partij.
     * @param code de nieuwe waarde voor code van Partij
     */
    public void setCode(final String code) {
        ValidationUtils.controleerOpNullWaarden("code mag niet null zijn", code);
        ValidationUtils.controleerOpLengte("code moet een lengte van 6 hebben", code, CODE_LENGTE);
        this.code = code;
    }

    /**
     * Geef de waarde van datum ingang van Partij.
     * @return de waarde van datum ingang van Partij
     */
    public Integer getDatumIngang() {
        return datumIngang;
    }

    /**
     * Zet de waarden voor datum ingang van Partij.
     * @param datumIngang de nieuwe waarde voor datum ingang van Partij
     */
    public void setDatumIngang(final Integer datumIngang) {
        this.datumIngang = datumIngang;
    }

    /**
     * Geef de waarde van datum einde van Partij.
     * @return de waarde van datum einde van Partij
     */
    public Integer getDatumEinde() {
        return datumEinde;
    }

    /**
     * Zet de waarden voor datum einde van Partij.
     * @param datumEinde de nieuwe waarde voor datum einde van Partij
     */
    public void setDatumEinde(final Integer datumEinde) {
        this.datumEinde = datumEinde;
    }

    /**
     * Geef de waarde van oin van Partij.
     * @return de waarde van oin van Partij
     */
    public String getOin() {
        return oin;
    }

    /**
     * Zet de waarden voor oin van Partij.
     * @param oin de nieuwe waarde voor oin van Partij
     */
    public void setOin(final String oin) {
        this.oin = oin;
    }

    /**
     * Controleert op indicatie verstrekkingsbeperking mogelijk.
     * @return boolean
     */
    public Boolean isIndicatieVerstrekkingsbeperkingMogelijk() {
        return indicatieVerstrekkingsbeperkingMogelijk;
    }

    /**
     * Zet de waarden voor indicatie verstrekkingsbeperking mogelijk van Partij.
     * @param indicatieVerstrekkingsbeperkingMogelijk de nieuwe waarde voor indicatie verstrekkingsbeperking mogelijk van Partij
     */
    public void setIndicatieVerstrekkingsbeperkingMogelijk(final Boolean indicatieVerstrekkingsbeperkingMogelijk) {
        this.indicatieVerstrekkingsbeperkingMogelijk = indicatieVerstrekkingsbeperkingMogelijk;
    }

    /**
     * Geef de waarde van indicatie automatisch fiatteren van Partij.
     * @return de waarde van indicatie automatisch fiatteren van Partij
     */
    public Boolean getIndicatieAutomatischFiatteren() {
        return indicatieAutomatischFiatteren;
    }

    /**
     * Zet de waarden voor indicatie automatisch fiatteren van Partij.
     * @param indicatieAutomatischFiatteren de nieuwe waarde voor indicatie automatisch fiatteren van Partij
     */
    public void setIndicatieAutomatischFiatteren(final Boolean indicatieAutomatischFiatteren) {
        this.indicatieAutomatischFiatteren = indicatieAutomatischFiatteren;
    }

    /**
     * Geef de waarde van datum overgang naar brp van Partij.
     * @return de waarde van datum overgang naar brp van Partij
     */
    public Integer getDatumOvergangNaarBrp() {
        return datumOvergangNaarBrp;
    }

    /**
     * Zet de waarden voor datum overgang naar brp van Partij.
     * @param datumOvergangNaarBrp de nieuwe waarde voor datum overgang naar brp van Partij
     */
    public void setDatumOvergangNaarBrp(final Integer datumOvergangNaarBrp) {
        this.datumOvergangNaarBrp = datumOvergangNaarBrp;
    }

    /**
     * Geef de waarde van isActueelEnGeldig.
     * @return isActueelEnGeldig
     */
    public boolean isActueelEnGeldig() {
        return isActueelEnGeldig;
    }

    /**
     * Zet de waarde van isActueelEnGeldig.
     * @param actueelEnGeldig isActueelEnGeldig
     */
    public void setActueelEnGeldig(final boolean actueelEnGeldig) {
        isActueelEnGeldig = actueelEnGeldig;
    }

    /**
     * Geef de waarde van isActueelEnGeldigVoorBijhouding.
     * @return isActueelEnGeldigVoorBijhouding
     */
    public boolean isActueelEnGeldigVoorBijhouding() {
        return isActueelEnGeldigVoorBijhouding;
    }

    /**
     * Zet de waarde van isActueelEnGeldig.
     * @param actueelEnGeldigVoorBijhouding isActueelEnGeldigVoorBijhouding
     */
    public void setActueelEnGeldigVoorBijhouding(final boolean actueelEnGeldigVoorBijhouding) {
        isActueelEnGeldigVoorBijhouding = actueelEnGeldigVoorBijhouding;
    }

    /**
     * Geef de ondertekenaar van het vrije bericht
     * @return een partij
     */
    public Partij getOndertekenaarVrijBericht() {
        return ondertekenaarVrijBericht;
    }

    /**
     * Zet de ondertekenaar van het vrije bericht
     * @param ondertekenaarVrijBericht een partij
     */
    public void setOndertekenaarVrijBericht(final Partij ondertekenaarVrijBericht) {
        this.ondertekenaarVrijBericht = ondertekenaarVrijBericht;
    }

    /**
     * Geef de transporteur van het vrije bericht
     * @return een partij
     */
    public Partij getTransporteurVrijBericht() {
        return transporteurVrijBericht;
    }

    /**
     * Zet de transporteur van het vrije bericht
     * @param transporteurVrijBericht een partij
     */
    public void setTransporteurVrijBericht(final Partij transporteurVrijBericht) {
        this.transporteurVrijBericht = transporteurVrijBericht;
    }

    /**
     * Geef de datum ingang voor het versturen van het vrije bericht
     * @return een datum
     */
    public Integer getDatumIngangVrijBericht() {
        return datumIngangVrijBericht;
    }

    /**
     * Zet de datumIngang voor het versturen van een vrij bericht.
     * @param datumIngangVrijBericht een datum
     */
    public void setDatumIngangVrijBericht(final Integer datumIngangVrijBericht) {
        this.datumIngangVrijBericht = datumIngangVrijBericht;
    }

    /**
     * Geef de datum einde voor het versturen van het vrije bericht
     * @return een datum
     */
    public Integer getDatumEindeVrijBericht() {
        return datumEindeVrijBericht;
    }

    /**
     * Zet de datumEinde voor het versturen van een vrij bericht
     * @param datumEindeVrijBericht een datum
     */
    public void setDatumEindeVrijBericht(final Integer datumEindeVrijBericht) {
        this.datumEindeVrijBericht = datumEindeVrijBericht;
    }

    /**
     * Geef het afleverpunt voor het versturen van het vrije bericht
     * @return een datum
     */
    public String getAfleverpuntVrijBericht() {
        return afleverpuntVrijBericht;
    }

    /**
     * Zet het afleverpunt voor het versturen van een vrij bericht
     * @param afleverpuntVrijBericht een afleverpunt (URI)
     */
    public void setAfleverpuntVrijBericht(final String afleverpuntVrijBericht) {
        this.afleverpuntVrijBericht = afleverpuntVrijBericht;
    }

    /**
     * Geeft indicatie of het versturen van een vrij bericht geblokkeerd is.
     * @return een boolean indicatie
     */
    public Boolean isVrijBerichtGeblokkeerd() {
        return isVrijBerichtGeblokkeerd;
    }

    /**
     * Zet de indicatie of het versturen van een vrij bericht geblokkeerd is.
     * @param isVrijBerichtGeblokkeerd een boolean indicatie
     */
    public void setVrijBerichtGeblokkeerd(final Boolean isVrijBerichtGeblokkeerd) {
        if (Boolean.FALSE.equals(isVrijBerichtGeblokkeerd)) {
            throw new IllegalArgumentException("Is vrij bericht geblokkeerd moet null of TRUE zijn");
        }
        this.isVrijBerichtGeblokkeerd = isVrijBerichtGeblokkeerd;
    }

    /**
     * Geeft de actueel/geldig indicatie voor de A-laag mbt een vrij bericht.
     * @return een boolean indicatie
     */
    public boolean isActueelEnGeldigVoorVrijBericht() {
        return isActueelEnGeldigVoorVrijBericht;
    }

    /**
     * Zet de actueel/geldig indicatie voor de A-laag mbt een vrij bericht.
     * @param actueelEnGeldigVoorVrijBericht een boolean indicatie
     */
    public void setActueelEnGeldigVoorVrijBericht(final boolean actueelEnGeldigVoorVrijBericht) {
        isActueelEnGeldigVoorVrijBericht = actueelEnGeldigVoorVrijBericht;
    }

    /**
     * Geef de waarde van his partijen van Partij.
     * @return de waarde van his partijen van Partij
     */
    public Set<PartijHistorie> getHisPartijen() {
        return hisPartijen;
    }

    /**
     * Toevoegen van een his partij.
     * @param hisPartij his partij
     */
    public void addHisPartij(final PartijHistorie hisPartij) {
        hisPartij.setPartij(this);
        hisPartijen.add(hisPartij);
    }

    /**
     * Geef de waarde van partij bijhouding historie set van Partij.
     * @return de waarde van partij bijhouding historie set van Partij
     */
    public Set<PartijBijhoudingHistorie> getPartijBijhoudingHistorieSet() {
        return partijBijhoudingHistorieSet;
    }

    /**
     * Toevoegen van een his partij.
     * @param partijBijhoudingHistorie partijBijhoudingHistorie
     */
    public void addPartijBijhoudingHistorie(final PartijBijhoudingHistorie partijBijhoudingHistorie) {
        partijBijhoudingHistorie.setPartij(this);
        partijBijhoudingHistorieSet.add(partijBijhoudingHistorie);
    }

    /**
     * Geef de waarde van partij vrij bericht historie set van Partij.
     * @return de waarde van partij vrij bericht historie set van Partij
     */
    public Set<PartijVrijBerichtHistorie> getPartijVrijBerichtHistorieSet() {
        return partijVrijBerichtHistorieSet;
    }

    /**
     * Toevoegen van een his partij vrij bericht.
     * @param partijVrijBerichtHistorie partijVrijBerichtHistorie
     */
    public void addPartijBijhoudingHistorie(final PartijVrijBerichtHistorie partijVrijBerichtHistorie) {
        partijVrijBerichtHistorie.setPartij(this);
        partijVrijBerichtHistorieSet.add(partijVrijBerichtHistorie);
    }

    /**
     * Geef de waarde van partij rol set van Partij.
     * @return de waarde van partij rol set van Partij
     */
    public Set<PartijRol> getPartijRolSet() {
        return partijRolSet;
    }

    /**
     * Geef de rollen die een partij heeft.
     * @return rollen
     */
    public Set<Rol> getRollen() {
        return getPartijRolSet().stream().map(PartijRol::getRol).collect(Collectors.toCollection(() -> EnumSet.noneOf(Rol.class)));
    }

    /**
     * Geef die rollen van de partij die nu geldig zijn.
     * @return rollen
     */
    public Set<Rol> getActueleRollen() {
        return getPartijRolSet().stream()
                                .filter(PartijRol::isGeldig)
                                .map(PartijRol::getRol)
                                .collect(Collectors.toCollection(() -> EnumSet.noneOf(Rol.class)));
    }

    /**
     * Toevoegen van een partij rol.
     * @param partijRol partijRol
     */
    public void addPartijRol(final PartijRol partijRol) {
        partijRol.setPartij(this);
        partijRolSet.add(partijRol);
    }

    /**
     * geeft een lijst van Gemeenten behorend bij deze partij
     * @return gemeenten behorend bij deze partij
     */
    public Set<Gemeente> getGemeenten() {
        return gemeenteSet;
    }

    /**
     * Als de code van twee partijen gelijk zijn dan worden ze als inhoudelijk gelijk beschouwd.
     * @param anderePartij de andere partij waaarme vergeleken wordt
     * @return true als de code van deze partij gelijk is aan de andere partij, anders false
     */
    public boolean isInhoudelijkGelijkAan(final Partij anderePartij) {
        return this == anderePartij || (anderePartij != null && getCode().equals(anderePartij.getCode()));
    }

    @Override
    public final String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("id", id).append("code", code)
                .append("naam", naam).toString();
    }

    /**
     * Geeft true als deze partij de rol van bijhouder heeft, anders false.
     * @return true voor bijhouder, anders false
     */
    public final boolean isBijhouder() {
        return !Collections.disjoint(getRollen(), BIJHOUDER_ROLLEN);
    }
}
