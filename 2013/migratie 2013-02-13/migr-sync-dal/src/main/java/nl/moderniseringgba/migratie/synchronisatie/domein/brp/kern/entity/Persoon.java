/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/*
 * CHECKSTYLE:OFF Deze class is gegenereerd o.b.v. het BRP datamodel en bevat daarom geen javadoc, daarnaast mogen
 * entities en de methoden van entities niet final zijn.
 */
package nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.BinaireRelatie;

/**
 * The persistent class for the pers database table.
 * 
 */
@Entity
@Table(name = "pers", schema = "kern")
public class Persoon implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "PERS_ID_GENERATOR", sequenceName = "KERN.SEQ_PERS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERS_ID_GENERATOR")
    @Column(nullable = false)
    private Long id;

    /**
     * De foreign key 'Soort persoon' is een verwijzing naar een statisch stamgegeven van de BRP. Aangezien statische
     * stamgegevens in de BRP alleen releasematig worden beheerd gaan we hier pragmatisch mee om en mappen we dit naar
     * een Java enumeratie, aangezien dit de leesbaarheid van de code ten goede komt.
     * 
     * @see #setSoortPersoon(SoortPersoon) 
     */
    @Column(name = "srt", nullable = false)
    private Integer soortPersoonId;

    @Enumerated(EnumType.STRING)
    @Column(name = "aanschrstatushis", nullable = false, length = 1)
    private HistorieStatus aanschrijvingStatusHistorie = HistorieStatus.X;

    @Column(name = "anr", length = 10)
    private BigDecimal administratienummer;

    @Enumerated(EnumType.STRING)
    @Column(name = "bijhgemstatushis", nullable = false, length = 1)
    private HistorieStatus bijhoudingsgemeenteStatusHistorie = HistorieStatus.X;

    @Enumerated(EnumType.STRING)
    @Column(name = "bijhverantwoordelijkheidstat", nullable = false, length = 1)
    private HistorieStatus bijhoudingsverantwoordelijkheidStatusHistorie = HistorieStatus.X;

    @Column(name = "blgeboorteplaats", length = 40)
    private String buitenlandseGeboorteplaats;

    @Column(name = "blplaatsoverlijden", length = 40)
    private String buitenlandsePlaatsOverlijden;

    @Column(name = "blregiogeboorte", length = 35)
    private String buitenlandseRegioGeboorte;

    @Column(name = "blregiooverlijden", length = 35)
    private String buitenlandseRegioOverlijden;

    @Column(name = "bsn", length = 9)
    private BigDecimal burgerservicenummer;

    @Column(name = "dataanlaanpdeelneuverkiezing", precision = 8)
    private BigDecimal datumAanleidingAanpassingDeelnameEUVerkiezing;

    @Column(name = "dataanvaaneenslverblijfsr", precision = 8)
    private BigDecimal datumAanvangAaneensluitendVerblijfsrecht;

    @Column(name = "dataanvverblijfsr", precision = 8)
    private BigDecimal datumAanvangVerblijfsrecht;

    @Column(name = "dateindeuitsleukiesr", precision = 8)
    private BigDecimal datumEindeUitsluitingEUKiesrecht;

    @Column(name = "dateindeuitslnlkiesr", precision = 8)
    private BigDecimal datumEindeUitsluitingNLKiesrecht;

    @Column(name = "datgeboorte", precision = 8)
    private BigDecimal datumGeboorte;

    @Column(name = "datinschr", precision = 8)
    private BigDecimal datumInschrijving;

    @Column(name = "datinschringem", precision = 8)
    private BigDecimal datumInschrijvingInGemeente;

    @Column(name = "datoverlijden", precision = 8)
    private BigDecimal datumOverlijden;

    @Column(name = "datvestiginginnederland", precision = 8)
    private BigDecimal datumVestigingInNederland;

    @Column(name = "datvoorzeindeverblijfsr", precision = 8)
    private BigDecimal datumVoorzienEindeVerblijfsrecht;

    @Enumerated(EnumType.STRING)
    @Column(name = "euverkiezingenstatushis", nullable = false, length = 1)
    private HistorieStatus euVerkiezingenStatusHistorie = HistorieStatus.X;

    @Enumerated(EnumType.STRING)
    @Column(name = "geboortestatushis", nullable = false, length = 1)
    private HistorieStatus geboorteStatusHistorie = HistorieStatus.X;

    @Enumerated(EnumType.STRING)
    @Column(name = "geslachtsaandstatushis", nullable = false, length = 1)
    private HistorieStatus geslachtsaanduidingStatusHistorie = HistorieStatus.X;

    @Column(name = "geslnaam", length = 200)
    private String geslachtsnaam;

    @Column(name = "geslnaamaanschr", length = 200)
    private String geslachtsnaamAanschrijving;

    @Enumerated(EnumType.STRING)
    @Column(name = "idsstatushis", nullable = false, length = 1)
    private HistorieStatus identificatienummersStatusHistorie = HistorieStatus.X;

    @Enumerated(EnumType.STRING)
    @Column(name = "immigratiestatushis", nullable = false, length = 1)
    private HistorieStatus immigratieStatusHistorie = HistorieStatus.X;

    @Column(name = "indaanschralgoritmischafgele")
    private Boolean indicatieAanschrijvingAlgoritmischAfgeleid;

    @Column(name = "indaanschrmetadellijketitels")
    private Boolean indicatieAanschrijvenMetAdellijkeTitelsEnOfPredikaten;

    @Column(name = "indalgoritmischafgeleid")
    private Boolean indicatieAlgoritmischAfgeleid;

    @Column(name = "inddeelneuverkiezingen")
    private Boolean indicatieDeelnameEUVerkiezingen;

    @Column(name = "indgegevensinonderzoek")
    private Boolean indicatieGegevensInOnderzoek;

    @Column(name = "indnreeksalsgeslnaam")
    private Boolean indicatieNamenreeksAlsGeslachtsnaam;

    @Column(name = "indonverwdocaanw")
    private Boolean indicatieOnverwerktDocumentAanwezig;

    @Column(name = "indpkvollediggeconv")
    private Boolean indicatiePersoonskaartVolledigGeconverteerd;

    @Column(name = "induitslnlkiesr")
    private Boolean indicatieUitsluitingNLKiesrecht;

    @Enumerated(EnumType.STRING)
    @Column(name = "inschrstatushis", nullable = false, length = 1)
    private HistorieStatus inschrijvingStatusHistorie = HistorieStatus.X;

    @Column(name = "omsgeboorteloc", length = 40)
    private String omschrijvingGeboortelocatie;

    @Column(name = "omslocoverlijden", length = 40)
    private String omschrijvingLocatieOverlijden;

    @Enumerated(EnumType.STRING)
    @Column(name = "opschortingstatushis", nullable = false, length = 1)
    private HistorieStatus opschortingStatusHistorie = HistorieStatus.X;

    @Enumerated(EnumType.STRING)
    @Column(name = "overlijdenstatushis", nullable = false, length = 1)
    private HistorieStatus overlijdenStatusHistorie = HistorieStatus.X;

    @Enumerated(EnumType.STRING)
    @Column(name = "pkstatushis", nullable = false, length = 1)
    private HistorieStatus persoonskaartStatusHistorie = HistorieStatus.X;

    @Enumerated(EnumType.STRING)
    @Column(name = "samengesteldenaamstatushis", nullable = false, length = 1)
    private HistorieStatus samengesteldeNaamStatusHistorie = HistorieStatus.X;

    @Column(length = 1)
    private Character scheidingsteken;

    @Column(name = "scheidingstekenaanschr", length = 1)
    private Character scheidingstekenAanschrijving;

    @Column(name = "tijdstiplaatstewijz")
    private Timestamp tijdstipLaatsteWijziging;

    @Enumerated(EnumType.STRING)
    @Column(name = "uitslnlkiesrstatushis", nullable = false, length = 1)
    private HistorieStatus uitsluitingNLKiesrecht = HistorieStatus.X;

    @Enumerated(EnumType.STRING)
    @Column(name = "verblijfsrstatushis", nullable = false, length = 1)
    private HistorieStatus verblijfsrechtStatusHistorie = HistorieStatus.X;

    @Column(name = "versienr")
    private Long versienummer;

    @Column(length = 200)
    private String voornamen;

    @Column(name = "voornamenaanschr", length = 200)
    private String voornamenAanschrijving;

    @Column(length = 10)
    private String voorvoegsel;

    @Column(name = "voorvoegselaanschr", length = 10)
    private String voorvoegselAanschrijving;

    // bi-directional many-to-one association to Betrokkenheid
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoon", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private final Set<Betrokkenheid> betrokkenheidSet = new LinkedHashSet<Betrokkenheid>(0);

    // bi-directional many-to-one association to MultiRealiteitRegel
    @OneToMany(mappedBy = "multiRealiteitPersoon", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private final Set<MultiRealiteitRegel> multiRealiteitRegelMultiRealiteitPersoonSet =
            new LinkedHashSet<MultiRealiteitRegel>(0);

    // bi-directional many-to-one association to MultiRealiteitRegel
    @OneToMany(mappedBy = "persoon", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private final Set<MultiRealiteitRegel> multiRealiteitRegelSet = new LinkedHashSet<MultiRealiteitRegel>(0);

    // bi-directional many-to-one association to MultiRealiteitRegel
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "geldigVoorPersoon", cascade = { CascadeType.PERSIST,
            CascadeType.MERGE })
    private final Set<MultiRealiteitRegel> multiRealiteitRegelGeldigVoorPersoonSet =
            new LinkedHashSet<MultiRealiteitRegel>(0);

    @Column(name = "adellijketitel")
    private Integer adellijkeTitelId;

    @Column(name = "geslachtsaand")
    private Integer geslachtsaanduidingId;

    // bi-directional many-to-one association to Land
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "landgeboorte")
    private Land landGeboorte;

    // bi-directional many-to-one association to Land
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "landvanwaargevestigd")
    private Land landVanWaarGevestigd;

    // bi-directional many-to-one association to Land
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "landoverlijden")
    private Land landOverlijden;

    // bi-directional many-to-one association to Partij
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "gempk")
    private Partij gemeentePersoonskaart;

    // bi-directional many-to-one association to Partij
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "gemoverlijden")
    private Partij gemeenteOverlijden;

    // bi-directional many-to-one association to Partij
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "gemgeboorte")
    private Partij gemeenteGeboorte;

    // bi-directional many-to-one association to Partij
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "bijhgem")
    private Partij bijhoudingsgemeente;

    // bi-directional many-to-one association to Persoon
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "volgendepers")
    private Persoon volgendePersoon;

    // bi-directional many-to-one association to Persoon
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "vorigepers")
    private Persoon vorigePersoon;

    // bi-directional many-to-one association to Plaats
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "wplgeboorte")
    private Plaats woonplaatsGeboorte;

    // bi-directional many-to-one association to Plaats
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "wploverlijden")
    private Plaats woonplaatsOverlijden;

    @Column(name = "predikaat")
    private Integer predikaatId;

    @Column(name = "predikaataanschr")
    private Integer predikaatAanschrijvingId;

    @Column(name = "rdnopschortingbijhouding")
    private Integer redenOpschortingBijhoudingId;

    @Column(name = "verantwoordelijke")
    private Integer verantwoordelijkeId;

    // bi-directional many-to-one association to Verblijfsrecht
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "verblijfsr")
    private Verblijfsrecht verblijfsrecht;

    @Column(name = "gebrgeslnaamegp")
    private Integer wijzeGebruikGeslachtsnaamId;

    // bi-directional many-to-one association to PersoonAdres
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoon", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REMOVE })
    private final Set<PersoonAdres> persoonAdresSet = new LinkedHashSet<PersoonAdres>(0);

    // bi-directional many-to-one association to PersoonGeslachtsnaamcomponent
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoon", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REMOVE })
    private final Set<PersoonGeslachtsnaamcomponent> persoonGeslachtsnaamcomponentSet =
            new LinkedHashSet<PersoonGeslachtsnaamcomponent>(0);

    // bi-directional many-to-one association to PersoonIndicatie
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoon", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REMOVE })
    private final Set<PersoonIndicatie> persoonIndicatieSet = new LinkedHashSet<PersoonIndicatie>(0);

    // bi-directional many-to-one association to PersoonNationaliteit
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoon", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REMOVE })
    private final Set<PersoonNationaliteit> persoonNationaliteitSet = new LinkedHashSet<PersoonNationaliteit>(0);

    // bi-directional many-to-one association to PersoonReisdocument
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoon", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REMOVE })
    private final Set<PersoonReisdocument> persoonReisdocumentSet = new LinkedHashSet<PersoonReisdocument>(0);

    // bi-directional many-to-one association to PersoonVerificatie
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoon", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REMOVE })
    private final Set<PersoonVerificatie> persoonVerificatieSet = new LinkedHashSet<PersoonVerificatie>(0);

    // bi-directional many-to-one association to PersoonVoornaam
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoon", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REMOVE })
    private final Set<PersoonVoornaam> persoonVoornaamSet = new LinkedHashSet<PersoonVoornaam>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoon", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REMOVE })
    private final Set<PersoonSamengesteldeNaamHistorie> persoonSamengesteldeNaamHistorieSet =
            new LinkedHashSet<PersoonSamengesteldeNaamHistorie>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoon", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REMOVE })
    private final Set<PersoonAanschrijvingHistorie> persoonAanschrijvingHistorieSet =
            new LinkedHashSet<PersoonAanschrijvingHistorie>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoon", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REMOVE })
    private final Set<PersoonEUVerkiezingenHistorie> persoonEUVerkiezingenHistorieSet =
            new LinkedHashSet<PersoonEUVerkiezingenHistorie>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoon", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REMOVE })
    private final Set<PersoonGeboorteHistorie> persoonGeboorteHistorieSet =
            new LinkedHashSet<PersoonGeboorteHistorie>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoon", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REMOVE })
    private final Set<PersoonGeslachtsaanduidingHistorie> persoonGeslachtsaanduidingHistorieSet =
            new LinkedHashSet<PersoonGeslachtsaanduidingHistorie>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoon", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REMOVE })
    private final Set<PersoonIDHistorie> persoonIDHistorieSet = new LinkedHashSet<PersoonIDHistorie>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoon", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REMOVE })
    private final Set<PersoonBijhoudingsgemeenteHistorie> persoonBijhoudingsgemeenteHistorieSet =
            new LinkedHashSet<PersoonBijhoudingsgemeenteHistorie>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoon", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REMOVE })
    private final Set<PersoonImmigratieHistorie> persoonImmigratieHistorieSet =
            new LinkedHashSet<PersoonImmigratieHistorie>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoon", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REMOVE })
    private final Set<PersoonInschrijvingHistorie> persoonInschrijvingHistorieSet =
            new LinkedHashSet<PersoonInschrijvingHistorie>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoon", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REMOVE })
    private final Set<PersoonOpschortingHistorie> persoonOpschortingHistorieSet =
            new LinkedHashSet<PersoonOpschortingHistorie>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoon", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REMOVE })
    private final Set<PersoonOverlijdenHistorie> persoonOverlijdenHistorieSet =
            new LinkedHashSet<PersoonOverlijdenHistorie>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoon", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REMOVE })
    private final Set<PersoonPersoonskaartHistorie> persoonPersoonskaartHistorieSet =
            new LinkedHashSet<PersoonPersoonskaartHistorie>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoon", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REMOVE })
    private final Set<PersoonBijhoudingsverantwoordelijkheidHistorie> persoonBijhoudingsverantwoordelijkheidHistorieSet =
            new LinkedHashSet<PersoonBijhoudingsverantwoordelijkheidHistorie>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoon", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REMOVE })
    private final Set<PersoonVerblijfsrechtHistorie> persoonVerblijfsrechtHistorieSet =
            new LinkedHashSet<PersoonVerblijfsrechtHistorie>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoon", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REMOVE })
    private final Set<PersoonUitsluitingNLKiesrechtHistorie> persoonUitsluitingNLKiesrechtHistorieSet =
            new LinkedHashSet<PersoonUitsluitingNLKiesrechtHistorie>();

    /**
     * 
     */
    public Persoon() {
    }

    /**
     * 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * 
     */
    public BigDecimal getAdministratienummer() {
        return administratienummer;
    }

    /**
     * 
     */
    public void setAdministratienummer(final BigDecimal administratienummer) {
        this.administratienummer = administratienummer;
    }

    /**
     * 
     */
    public HistorieStatus getAanschrijvingStatusHistorie() {
        return aanschrijvingStatusHistorie;
    }

    /**
     * 
     */
    public void setAanschrijvingStatusHistorie(final HistorieStatus aanschrijvingStatusHistorie) {
        this.aanschrijvingStatusHistorie = aanschrijvingStatusHistorie;
    }

    /**
     * 
     */
    public HistorieStatus getEuVerkiezingenStatusHistorie() {
        return euVerkiezingenStatusHistorie;
    }

    /**
     * 
     */
    public void setEuVerkiezingenStatusHistorie(final HistorieStatus euVerkiezingenStatusHistorie) {
        this.euVerkiezingenStatusHistorie = euVerkiezingenStatusHistorie;
    }

    /**
     * 
     */
    public HistorieStatus getGeboorteStatusHistorie() {
        return geboorteStatusHistorie;
    }

    /**
     * 
     */
    public void setGeboorteStatusHistorie(final HistorieStatus geboorteStatusHistorie) {
        this.geboorteStatusHistorie = geboorteStatusHistorie;
    }

    /**
     * 
     */
    public HistorieStatus getGeslachtsaanduidingStatusHistorie() {
        return geslachtsaanduidingStatusHistorie;
    }

    /**
     * 
     */
    public void setGeslachtsaanduidingStatusHistorie(final HistorieStatus geslachtsaanduidingStatusHistorie) {
        this.geslachtsaanduidingStatusHistorie = geslachtsaanduidingStatusHistorie;
    }

    /**
     * 
     */
    public HistorieStatus getIdentificatienummersStatusHistorie() {
        return identificatienummersStatusHistorie;
    }

    /**
     * 
     */
    public void setIdentificatienummersStatusHistorie(final HistorieStatus identificatienummersStatusHistorie) {
        this.identificatienummersStatusHistorie = identificatienummersStatusHistorie;
    }

    /**
     * 
     */
    public HistorieStatus getImmigratieStatusHistorie() {
        return immigratieStatusHistorie;
    }

    /**
     * 
     */
    public void setImmigratieStatusHistorie(final HistorieStatus immigratieStatusHistorie) {
        this.immigratieStatusHistorie = immigratieStatusHistorie;
    }

    /**
     * 
     */
    public HistorieStatus getInschrijvingStatusHistorie() {
        return inschrijvingStatusHistorie;
    }

    /**
     * 
     */
    public void setInschrijvingStatusHistorie(final HistorieStatus inschrijvingStatusHistorie) {
        this.inschrijvingStatusHistorie = inschrijvingStatusHistorie;
    }

    /**
     * 
     */
    public HistorieStatus getOpschortingStatusHistorie() {
        return opschortingStatusHistorie;
    }

    /**
     * 
     */
    public void setOpschortingStatusHistorie(final HistorieStatus opschortingStatusHistorie) {
        this.opschortingStatusHistorie = opschortingStatusHistorie;
    }

    /**
     * 
     */
    public HistorieStatus getOverlijdenStatusHistorie() {
        return overlijdenStatusHistorie;
    }

    /**
     * 
     */
    public void setOverlijdenStatusHistorie(final HistorieStatus overlijdenStatusHistorie) {
        this.overlijdenStatusHistorie = overlijdenStatusHistorie;
    }

    /**
     * 
     */
    public HistorieStatus getPersoonskaartStatusHistorie() {
        return persoonskaartStatusHistorie;
    }

    /**
     * 
     */
    public void setPersoonskaartStatusHistorie(final HistorieStatus persoonskaartStatusHistorie) {
        this.persoonskaartStatusHistorie = persoonskaartStatusHistorie;
    }

    /**
     * 
     */
    public HistorieStatus getSamengesteldeNaamStatusHistorie() {
        return samengesteldeNaamStatusHistorie;
    }

    /**
     * 
     */
    public void setSamengesteldeNaamStatusHistorie(final HistorieStatus samengesteldeNaamStatusHistorie) {
        this.samengesteldeNaamStatusHistorie = samengesteldeNaamStatusHistorie;
    }

    /**
     * 
     */
    public HistorieStatus getVerblijfsrechtStatusHistorie() {
        return verblijfsrechtStatusHistorie;
    }

    /**
     * 
     */
    public void setVerblijfsrechtStatusHistorie(final HistorieStatus verblijfsrechtStatusHistorie) {
        this.verblijfsrechtStatusHistorie = verblijfsrechtStatusHistorie;
    }

    /**
     * 
     */
    public HistorieStatus getBijhoudingsgemeenteStatusHistorie() {
        return bijhoudingsgemeenteStatusHistorie;
    }

    /**
     * 
     */
    public void setBijhoudingsgemeenteStatusHistorie(final HistorieStatus bijhoudingsgemeenteStatusHistorie) {
        this.bijhoudingsgemeenteStatusHistorie = bijhoudingsgemeenteStatusHistorie;
    }

    /**
     * 
     */
    public HistorieStatus getBijhoudingsverantwoordelijkheidStatusHistorie() {
        return bijhoudingsverantwoordelijkheidStatusHistorie;
    }

    /**
     * 
     */
    public void setBijhoudingsverantwoordelijkheidStatusHistorie(
            final HistorieStatus bijhoudingsverantwoordelijkheidStatusHistorie) {
        this.bijhoudingsverantwoordelijkheidStatusHistorie = bijhoudingsverantwoordelijkheidStatusHistorie;
    }

    /**
     * 
     */
    public String getBuitenlandseGeboorteplaats() {
        return buitenlandseGeboorteplaats;
    }

    /**
     * 
     */
    public void setBuitenlandseGeboorteplaats(final String buitenlandseGeboorteplaats) {
        this.buitenlandseGeboorteplaats = buitenlandseGeboorteplaats;
    }

    /**
     * 
     */
    public String getBuitenlandsePlaatsOverlijden() {
        return buitenlandsePlaatsOverlijden;
    }

    /**
     * 
     */
    public void setBuitenlandsePlaatsOverlijden(final String buitenlandsePlaatsOverlijden) {
        this.buitenlandsePlaatsOverlijden = buitenlandsePlaatsOverlijden;
    }

    /**
     * 
     */
    public String getBuitenlandseRegioGeboorte() {
        return buitenlandseRegioGeboorte;
    }

    /**
     * 
     */
    public void setBuitenlandseRegioGeboorte(final String buitenlandseRegioGeboorte) {
        this.buitenlandseRegioGeboorte = buitenlandseRegioGeboorte;
    }

    /**
     * 
     */
    public String getBuitenlandseRegioOverlijden() {
        return buitenlandseRegioOverlijden;
    }

    /**
     * 
     */
    public void setBuitenlandseRegioOverlijden(final String buitenlandseRegioOverlijden) {
        this.buitenlandseRegioOverlijden = buitenlandseRegioOverlijden;
    }

    /**
     * 
     */
    public BigDecimal getBurgerservicenummer() {
        return burgerservicenummer;
    }

    /**
     * 
     */
    public void setBurgerservicenummer(final BigDecimal burgerservicenummer) {
        this.burgerservicenummer = burgerservicenummer;
    }

    /**
     * 
     */
    public BigDecimal getDatumAanleidingAanpassingDeelnameEUVerkiezing() {
        return datumAanleidingAanpassingDeelnameEUVerkiezing;
    }

    /**
     * 
     */
    public void setDatumAanleidingAanpassingDeelnameEUVerkiezing(
            final BigDecimal datumAanleidingAanpassingDeelnameEUVerkiezing) {
        this.datumAanleidingAanpassingDeelnameEUVerkiezing = datumAanleidingAanpassingDeelnameEUVerkiezing;
    }

    /**
     * 
     */
    public BigDecimal getDatumAanvangAaneensluitendVerblijfsrecht() {
        return datumAanvangAaneensluitendVerblijfsrecht;
    }

    /**
     * 
     */
    public void
            setDatumAanvangAaneensluitendVerblijfsrecht(final BigDecimal datumAanvangAaneensluitendVerblijfsrecht) {
        this.datumAanvangAaneensluitendVerblijfsrecht = datumAanvangAaneensluitendVerblijfsrecht;
    }

    /**
     * 
     */
    public BigDecimal getDatumAanvangVerblijfsrecht() {
        return datumAanvangVerblijfsrecht;
    }

    /**
     * 
     */
    public void setDatumAanvangVerblijfsrecht(final BigDecimal datumAanvangVerblijfsrecht) {
        this.datumAanvangVerblijfsrecht = datumAanvangVerblijfsrecht;
    }

    /**
     * 
     */
    public BigDecimal getDatumEindeUitsluitingEUKiesrecht() {
        return datumEindeUitsluitingEUKiesrecht;
    }

    /**
     * 
     */
    public void setDatumEindeUitsluitingEUKiesrecht(final BigDecimal datumEindeUitsluitingEUKiesrecht) {
        this.datumEindeUitsluitingEUKiesrecht = datumEindeUitsluitingEUKiesrecht;
    }

    /**
     * 
     */
    public BigDecimal getDatumEindeUitsluitingNLKiesrecht() {
        return datumEindeUitsluitingNLKiesrecht;
    }

    /**
     * 
     */
    public void setDatumEindeUitsluitingNLKiesrecht(final BigDecimal datumEindeUitsluitingNLKiesrecht) {
        this.datumEindeUitsluitingNLKiesrecht = datumEindeUitsluitingNLKiesrecht;
    }

    /**
     * 
     */
    public BigDecimal getDatumGeboorte() {
        return datumGeboorte;
    }

    /**
     * 
     */
    public void setDatumGeboorte(final BigDecimal datumGeboorte) {
        this.datumGeboorte = datumGeboorte;
    }

    /**
     * 
     */
    public BigDecimal getDatumInschrijving() {
        return datumInschrijving;
    }

    /**
     * 
     */
    public void setDatumInschrijving(final BigDecimal datumInschrijving) {
        this.datumInschrijving = datumInschrijving;
    }

    /**
     * 
     */
    public BigDecimal getDatumInschrijvingInGemeente() {
        return datumInschrijvingInGemeente;
    }

    /**
     * 
     */
    public void setDatumInschrijvingInGemeente(final BigDecimal datumInschrijvingInGemeente) {
        this.datumInschrijvingInGemeente = datumInschrijvingInGemeente;
    }

    /**
     * 
     */
    public BigDecimal getDatumOverlijden() {
        return datumOverlijden;
    }

    /**
     * 
     */
    public void setDatumOverlijden(final BigDecimal datumOverlijden) {
        this.datumOverlijden = datumOverlijden;
    }

    /**
     * 
     */
    public BigDecimal getDatumVestigingInNederland() {
        return datumVestigingInNederland;
    }

    /**
     * 
     */
    public void setDatumVestigingInNederland(final BigDecimal datumVestigingInNederland) {
        this.datumVestigingInNederland = datumVestigingInNederland;
    }

    /**
     * 
     */
    public BigDecimal getDatumVoorzienEindeVerblijfsrecht() {
        return datumVoorzienEindeVerblijfsrecht;
    }

    /**
     * 
     */
    public void setDatumVoorzienEindeVerblijfsrecht(final BigDecimal datumVoorzienEindeVerblijfsrecht) {
        this.datumVoorzienEindeVerblijfsrecht = datumVoorzienEindeVerblijfsrecht;
    }

    /**
     * 
     */
    public String getGeslachtsnaam() {
        return geslachtsnaam;
    }

    /**
     * 
     */
    public void setGeslachtsnaam(final String geslachtsnaam) {
        this.geslachtsnaam = geslachtsnaam;
    }

    /**
     * 
     */
    public String getGeslachtsnaamAanschrijving() {
        return geslachtsnaamAanschrijving;
    }

    /**
     * 
     */
    public void setGeslachtsnaamAanschrijving(final String geslachtsnaamAanschrijving) {
        this.geslachtsnaamAanschrijving = geslachtsnaamAanschrijving;
    }

    /**
     * 
     */
    public Boolean getIndicatieAanschrijvingAlgoritmischAfgeleid() {
        return indicatieAanschrijvingAlgoritmischAfgeleid;
    }

    /**
     * 
     */
    public void
            setIndicatieAanschrijvingAlgoritmischAfgeleid(final Boolean indicatieAanschrijvingAlgoritmischAfgeleid) {
        this.indicatieAanschrijvingAlgoritmischAfgeleid = indicatieAanschrijvingAlgoritmischAfgeleid;
    }

    /**
     * 
     */
    public Boolean getIndicatieAanschrijvenMetAdellijkeTitelsEnOfPredikaten() {
        return indicatieAanschrijvenMetAdellijkeTitelsEnOfPredikaten;
    }

    /**
     * 
     */
    public void setIndicatieAanschrijvenMetAdellijkeTitelsEnOfPredikaten(
            final Boolean indicatieAanschrijvenMetAdellijkeTitelsEnOfPredikaten) {
        this.indicatieAanschrijvenMetAdellijkeTitelsEnOfPredikaten =
                indicatieAanschrijvenMetAdellijkeTitelsEnOfPredikaten;
    }

    /**
     * 
     */
    public Boolean getIndicatieAlgoritmischAfgeleid() {
        return indicatieAlgoritmischAfgeleid;
    }

    /**
     * 
     */
    public void setIndicatieAlgoritmischAfgeleid(final Boolean indicatieAlgoritmischAfgeleid) {
        this.indicatieAlgoritmischAfgeleid = indicatieAlgoritmischAfgeleid;
    }

    /**
     * 
     */
    public Boolean getIndicatieDeelnameEUVerkiezingen() {
        return indicatieDeelnameEUVerkiezingen;
    }

    /**
     * 
     */
    public void setIndicatieDeelnameEUVerkiezingen(final Boolean indicatieDeelnameEUVerkiezingen) {
        this.indicatieDeelnameEUVerkiezingen = indicatieDeelnameEUVerkiezingen;
    }

    /**
     * 
     */
    public Boolean getIndicatieGegevensInOnderzoek() {
        return indicatieGegevensInOnderzoek;
    }

    /**
     * 
     */
    public void setIndicatieGegevensInOnderzoek(final Boolean indicatieGegevensInOnderzoek) {
        this.indicatieGegevensInOnderzoek = indicatieGegevensInOnderzoek;
    }

    /**
     * 
     */
    public Boolean getIndicatieNamenreeksAlsGeslachtsnaam() {
        return indicatieNamenreeksAlsGeslachtsnaam;
    }

    /**
     * 
     */
    public void setIndicatieNamenreeksAlsGeslachtsnaam(final Boolean indicatieNamenreeksAlsGeslachtsnaam) {
        this.indicatieNamenreeksAlsGeslachtsnaam = indicatieNamenreeksAlsGeslachtsnaam;
    }

    /**
     * 
     */
    public Boolean getIndicatieOnverwerktDocumentAanwezig() {
        return indicatieOnverwerktDocumentAanwezig;
    }

    /**
     * 
     */
    public void setIndicatieOnverwerktDocumentAanwezig(final Boolean indicatieOnverwerktDocumentAanwezig) {
        this.indicatieOnverwerktDocumentAanwezig = indicatieOnverwerktDocumentAanwezig;
    }

    /**
     * 
     */
    public Boolean getIndicatiePersoonskaartVolledigGeconverteerd() {
        return indicatiePersoonskaartVolledigGeconverteerd;
    }

    /**
     * 
     */
    public void setIndicatiePersoonskaartVolledigGeconverteerd(
            final Boolean indicatiePersoonskaartVolledigGeconverteerd) {
        this.indicatiePersoonskaartVolledigGeconverteerd = indicatiePersoonskaartVolledigGeconverteerd;
    }

    /**
     * 
     */
    public Boolean getIndicatieUitsluitingNLKiesrecht() {
        return indicatieUitsluitingNLKiesrecht;
    }

    /**
     * 
     */
    public void setIndicatieUitsluitingNLKiesrecht(final Boolean indicatieUitsluitingNLKiesrecht) {
        this.indicatieUitsluitingNLKiesrecht = indicatieUitsluitingNLKiesrecht;
    }

    /**
     * 
     */
    public String getOmschrijvingGeboortelocatie() {
        return omschrijvingGeboortelocatie;
    }

    /**
     * 
     */
    public void setOmschrijvingGeboortelocatie(final String omschrijvingGeboortelocatie) {
        this.omschrijvingGeboortelocatie = omschrijvingGeboortelocatie;
    }

    /**
     * 
     */
    public String getOmschrijvingLocatieOverlijden() {
        return omschrijvingLocatieOverlijden;
    }

    /**
     * 
     */
    public void setOmschrijvingLocatieOverlijden(final String omschrijvingLocatieOverlijden) {
        this.omschrijvingLocatieOverlijden = omschrijvingLocatieOverlijden;
    }

    /**
     * 
     */
    public Character getScheidingsteken() {
        return scheidingsteken;
    }

    /**
     * 
     */
    public void setScheidingsteken(final Character scheidingsteken) {
        this.scheidingsteken = scheidingsteken;
    }

    /**
     * 
     */
    public Character getScheidingstekenAanschrijving() {
        return scheidingstekenAanschrijving;
    }

    /**
     * 
     */
    public void setScheidingstekenAanschrijving(final Character scheidingstekenAanschrijving) {
        this.scheidingstekenAanschrijving = scheidingstekenAanschrijving;
    }

    /**
     * 
     */
    public Timestamp getTijdstipLaatsteWijziging() {
        return tijdstipLaatsteWijziging;
    }

    /**
     * 
     */
    public void setTijdstipLaatsteWijziging(final Timestamp tijdstipLaatsteWijziging) {
        this.tijdstipLaatsteWijziging = tijdstipLaatsteWijziging;
    }

    /**
     * 
     */
    public Long getVersienummer() {
        return versienummer;
    }

    /**
     * 
     */
    public void setVersienummer(final Long versienummer) {
        this.versienummer = versienummer;
    }

    /**
     * 
     */
    public String getVoornamen() {
        return voornamen;
    }

    /**
     * 
     */
    public void setVoornamen(final String voornamen) {
        this.voornamen = voornamen;
    }

    /**
     * 
     */
    public String getVoornamenAanschrijving() {
        return voornamenAanschrijving;
    }

    /**
     * 
     */
    public void setVoornamenAanschrijving(final String voornamenAanschrijving) {
        this.voornamenAanschrijving = voornamenAanschrijving;
    }

    /**
     * 
     */
    public String getVoorvoegsel() {
        return voorvoegsel;
    }

    /**
     * 
     */
    public void setVoorvoegsel(final String voorvoegsel) {
        this.voorvoegsel = voorvoegsel;
    }

    /**
     * 
     */
    public String getVoorvoegselAanschrijving() {
        return voorvoegselAanschrijving;
    }

    /**
     * 
     */
    public void setVoorvoegselAanschrijving(final String voorvoegselAanschrijving) {
        this.voorvoegselAanschrijving = voorvoegselAanschrijving;
    }

    /**
     * 
     */
    public Set<Betrokkenheid> getBetrokkenheidSet() {
        return betrokkenheidSet;
    }

    /**
     * @return alle betrokkenheden van deze persoon waarvan het soort betrokkenheid KIND is
     */
    public Set<Betrokkenheid> getKindBetrokkenheidSet() {
        final Set<Betrokkenheid> result = new LinkedHashSet<Betrokkenheid>();
        for (final Betrokkenheid betrokkenheid : getBetrokkenheidSet()) {
            if (SoortBetrokkenheid.KIND.equals(betrokkenheid.getSoortBetrokkenheid())) {
                result.add(betrokkenheid);
            }
        }
        return result;
    }

    /**
     * 
     */
    public void addBetrokkenheid(final Betrokkenheid betrokkenheid) {
        betrokkenheid.setPersoon(this);
        betrokkenheidSet.add(betrokkenheid);
    }

    /**
     * 
     */
    public boolean removeBetrokkenheid(final Betrokkenheid betrokkenheid) {
        return betrokkenheidSet.remove(betrokkenheid);
    }

    /**
     * 
     */
    public Set<MultiRealiteitRegel> getMultiRealiteitRegelMultiRealiteitPersoonSet() {
        return multiRealiteitRegelMultiRealiteitPersoonSet;
    }

    /**
     * 
     */
    public void addMultiRealiteitRegelMultiRealiteitPersoon(
            final MultiRealiteitRegel multiRealiteitRegelMultiRealiteitPersoon) {
        multiRealiteitRegelMultiRealiteitPersoon.setPersoon(this);
        multiRealiteitRegelMultiRealiteitPersoonSet.add(multiRealiteitRegelMultiRealiteitPersoon);
    }

    /**
     * 
     */
    public Set<MultiRealiteitRegel> getMultiRealiteitRegelSet() {
        return multiRealiteitRegelSet;
    }

    /**
     * 
     */
    public void addMultiRealiteitRegel(final MultiRealiteitRegel multiRealiteitRegel) {
        multiRealiteitRegel.setPersoon(this);
        multiRealiteitRegelSet.add(multiRealiteitRegel);
    }

    /**
     * 
     */
    public Set<MultiRealiteitRegel> getMultiRealiteitRegelGeldigVoorPersoonSet() {
        return multiRealiteitRegelGeldigVoorPersoonSet;
    }

    /**
     * 
     */
    public void
            addMultiRealiteitRegelGeldigVoorPersoon(final MultiRealiteitRegel multiRealiteitRegelGeldigVoorPersoon) {
        multiRealiteitRegelGeldigVoorPersoon.setGeldigVoorPersoon(this);
        multiRealiteitRegelGeldigVoorPersoonSet.add(multiRealiteitRegelGeldigVoorPersoon);
    }

    /**
     * 
     */
    public boolean removeMultiRealiteitRegelGeldigVoorPersoon(
            final MultiRealiteitRegel multiRealiteitRegelGeldigVoorPersoon) {
        return multiRealiteitRegelGeldigVoorPersoonSet.remove(multiRealiteitRegelGeldigVoorPersoon);
    }

    /**
     * 
     */
    public Geslachtsaanduiding getGeslachtsaanduiding() {
        return Geslachtsaanduiding.parseId(geslachtsaanduidingId);
    }

    /**
     * 
     */
    public void setGeslachtsaanduiding(final Geslachtsaanduiding geslachtsaanduiding) {
        if (geslachtsaanduiding == null) {
            geslachtsaanduidingId = null;
        } else {
            geslachtsaanduidingId = geslachtsaanduiding.getId();
        }
    }

    /**
     * 
     */
    public Land getLandGeboorte() {
        return landGeboorte;
    }

    /**
     * 
     */
    public void setLandGeboorte(final Land landGeboorte) {
        this.landGeboorte = landGeboorte;
    }

    /**
     * 
     */
    public Land getLandVanWaarGevestigd() {
        return landVanWaarGevestigd;
    }

    /**
     * 
     */
    public void setLandVanWaarGevestigd(final Land landVanWaarGevestigd) {
        this.landVanWaarGevestigd = landVanWaarGevestigd;
    }

    /**
     * 
     */
    public Land getLandOverlijden() {
        return landOverlijden;
    }

    /**
     * 
     */
    public void setLandOverlijden(final Land landOverlijden) {
        this.landOverlijden = landOverlijden;
    }

    /**
     * 
     */
    public Partij getGemeentePersoonskaart() {
        return gemeentePersoonskaart;
    }

    /**
     * 
     */
    public void setGemeentePersoonskaart(final Partij gemeentePersoonskaart) {
        this.gemeentePersoonskaart = gemeentePersoonskaart;
    }

    /**
     * 
     */
    public Partij getGemeenteOverlijden() {
        return gemeenteOverlijden;
    }

    /**
     * 
     */
    public void setGemeenteOverlijden(final Partij gemeenteOverlijden) {
        this.gemeenteOverlijden = gemeenteOverlijden;
    }

    /**
     * 
     */
    public Partij getGemeenteGeboorte() {
        return gemeenteGeboorte;
    }

    /**
     * 
     */
    public void setGemeenteGeboorte(final Partij gemeenteGeboorte) {
        this.gemeenteGeboorte = gemeenteGeboorte;
    }

    /**
     * 
     */
    public Partij getBijhoudingsgemeente() {
        return bijhoudingsgemeente;
    }

    /**
     * 
     */
    public void setBijhoudingsgemeente(final Partij bijhoudingsgemeente) {
        this.bijhoudingsgemeente = bijhoudingsgemeente;
    }

    /**
     * 
     */
    public Persoon getVolgendePersoon() {
        return volgendePersoon;
    }

    /**
     * 
     */
    public void setVolgendePersoon(final Persoon volgendePersoon) {
        this.volgendePersoon = volgendePersoon;
    }

    /**
     * 
     */
    public Persoon getVorigePersoon() {
        return vorigePersoon;
    }

    /**
     * 
     */
    public void setVorigePersoon(final Persoon vorigePersoon) {
        this.vorigePersoon = vorigePersoon;
    }

    /**
     * 
     */
    public Plaats getWoonplaatsGeboorte() {
        return woonplaatsGeboorte;
    }

    /**
     * 
     */
    public void setWoonplaatsGeboorte(final Plaats woonplaatsGeboorte) {
        this.woonplaatsGeboorte = woonplaatsGeboorte;
    }

    /**
     * 
     */
    public Plaats getWoonplaatsOverlijden() {
        return woonplaatsOverlijden;
    }

    /**
     * 
     */
    public void setWoonplaatsOverlijden(final Plaats woonplaatsOverlijden) {
        this.woonplaatsOverlijden = woonplaatsOverlijden;
    }

    /**
     * 
     */
    public Predikaat getPredikaat() {
        return Predikaat.parseId(predikaatId);
    }

    /**
     * 
     */
    public void setPredikaat(final Predikaat predikaat) {
        if (predikaat == null) {
            predikaatId = null;
        } else {
            predikaatId = predikaat.getId();
        }
    }

    /**
     * 
     */
    public Predikaat getPredikaatAanschrijving() {
        return Predikaat.parseId(predikaatAanschrijvingId);
    }

    /**
     * 
     */
    public void setPredikaatAanschrijving(final Predikaat predikaatAanschrijving) {
        if (predikaatAanschrijving == null) {
            predikaatAanschrijvingId = null;
        } else {
            predikaatAanschrijvingId = predikaatAanschrijving.getId();
        }
    }

    /**
     * 
     */
    public RedenOpschorting getRedenOpschortingBijhouding() {
        return RedenOpschorting.parseId(redenOpschortingBijhoudingId);
    }

    /**
     * 
     */
    public void setRedenOpschortingBijhouding(final RedenOpschorting redenOpschorting) {
        if (redenOpschorting == null) {
            redenOpschortingBijhoudingId = null;
        } else {
            redenOpschortingBijhoudingId = redenOpschorting.getId();
        }
    }

    /**
     * 
     */
    public SoortPersoon getSoortPersoon() {
        return SoortPersoon.parseId(soortPersoonId);
    }

    /**
     * 
     */
    public void setSoortPersoon(final SoortPersoon soortPersoon) {
        if (soortPersoon == null) {
            soortPersoonId = null;
        } else {
            soortPersoonId = soortPersoon.getId();
        }
    }

    /**
     * 
     */
    public Integer getSoortPersoonId() {
        return soortPersoonId;
    }

    /**
     * 
     */
    public Verantwoordelijke getVerantwoordelijke() {
        return Verantwoordelijke.parseId(verantwoordelijkeId);
    }

    /**
     * 
     */
    public void setVerantwoordelijke(final Verantwoordelijke verantwoordelijke) {
        if (verantwoordelijke == null) {
            verantwoordelijkeId = null;
        } else {
            verantwoordelijkeId = verantwoordelijke.getId();
        }
    }

    /**
     * 
     */
    public Verblijfsrecht getVerblijfsrecht() {
        return verblijfsrecht;
    }

    /**
     * 
     */
    public void setVerblijfsrecht(final Verblijfsrecht verblijfsrecht) {
        this.verblijfsrecht = verblijfsrecht;
    }

    /**
     * 
     */
    public WijzeGebruikGeslachtsnaam getWijzeGebruikGeslachtsnaam() {
        return WijzeGebruikGeslachtsnaam.parseId(wijzeGebruikGeslachtsnaamId);
    }

    /**
     * 
     */
    public void setWijzeGebruikGeslachtsnaam(final WijzeGebruikGeslachtsnaam wijzeGebruikGeslachtsnaam) {
        if (wijzeGebruikGeslachtsnaam == null) {
            wijzeGebruikGeslachtsnaamId = null;
        } else {
            wijzeGebruikGeslachtsnaamId = wijzeGebruikGeslachtsnaam.getId();
        }
    }

    /**
     * 
     */
    public Set<PersoonAdres> getPersoonAdresSet() {
        return persoonAdresSet;
    }

    /**
     * 
     */
    public void addPersoonAdres(final PersoonAdres persoonAdres) {
        persoonAdres.setPersoon(this);
        persoonAdresSet.add(persoonAdres);
    }

    /**
     * 
     */
    public boolean removePersoonAdres(final PersoonAdres persoonAdres) {
        return persoonAdresSet.remove(persoonAdres);
    }

    /**
     * 
     */
    public Set<PersoonGeslachtsnaamcomponent> getPersoonGeslachtsnaamcomponentSet() {
        return persoonGeslachtsnaamcomponentSet;
    }

    /**
     * 
     */
    public void addPersoonGeslachtsnaamcomponent(final PersoonGeslachtsnaamcomponent persoonGeslachtsnaamcomponent) {
        persoonGeslachtsnaamcomponent.setPersoon(this);
        persoonGeslachtsnaamcomponentSet.add(persoonGeslachtsnaamcomponent);
    }

    /**
     * 
     */
    public boolean removePersoonGeslachtsnaamcomponent(
            final PersoonGeslachtsnaamcomponent persoonGeslachtsnaamcomponent) {
        return persoonGeslachtsnaamcomponentSet.remove(persoonGeslachtsnaamcomponent);
    }

    /**
     * 
     */
    public Set<PersoonIndicatie> getPersoonIndicatieSet() {
        return persoonIndicatieSet;
    }

    /**
     * 
     */
    public void addPersoonIndicatie(final PersoonIndicatie persoonIndicatie) {
        persoonIndicatie.setPersoon(this);
        persoonIndicatieSet.add(persoonIndicatie);
    }

    /**
     * 
     */
    public boolean removePersoonIndicatie(final PersoonIndicatie persoonIndicatie) {
        return persoonIndicatieSet.remove(persoonIndicatie);
    }

    /**
     * 
     */
    public Set<PersoonNationaliteit> getPersoonNationaliteitSet() {
        return persoonNationaliteitSet;
    }

    /**
     * 
     */
    public void addPersoonNationaliteit(final PersoonNationaliteit persoonNationaliteit) {
        persoonNationaliteit.setPersoon(this);
        persoonNationaliteitSet.add(persoonNationaliteit);
    }

    /**
     * 
     */
    public boolean removePersoonNationaliteit(final PersoonNationaliteit persoonNationaliteit) {
        return persoonNationaliteitSet.remove(persoonNationaliteit);
    }

    /**
     * 
     */
    public Set<PersoonReisdocument> getPersoonReisdocumentSet() {
        return persoonReisdocumentSet;
    }

    /**
     * 
     */
    public void addPersoonReisdocument(final PersoonReisdocument persoonReisdocument) {
        persoonReisdocument.setPersoon(this);
        persoonReisdocumentSet.add(persoonReisdocument);
    }

    /**
     * 
     */
    public boolean removePersoonReisdocument(final PersoonReisdocument persoonReisdocument) {
        return persoonReisdocumentSet.remove(persoonReisdocument);
    }

    /**
     * 
     */
    public Set<PersoonVerificatie> getPersoonVerificatieSet() {
        return persoonVerificatieSet;
    }

    /**
     * 
     */
    public void addPersoonVerificatie(final PersoonVerificatie persoonVerificatie) {
        persoonVerificatie.setPersoon(this);
        persoonVerificatieSet.add(persoonVerificatie);
    }

    /**
     * 
     */
    public boolean removePersoonVerificatie(final PersoonVerificatie persoonVerificatie) {
        return persoonVerificatieSet.remove(persoonVerificatie);
    }

    /**
     * 
     */
    public Set<PersoonVoornaam> getPersoonVoornaamSet() {
        return persoonVoornaamSet;
    }

    /**
     * 
     */
    public void addPersoonVoornaam(final PersoonVoornaam persoonVoornaam) {
        persoonVoornaam.setPersoon(this);
        persoonVoornaamSet.add(persoonVoornaam);
    }

    /**
     * 
     */
    public boolean removePersoonVoornaam(final PersoonVoornaam persoonVoornaam) {
        return persoonVoornaamSet.remove(persoonVoornaam);
    }

    /**
     * 
     */
    public HistorieStatus getUitsluitingNLKiesrecht() {
        return uitsluitingNLKiesrecht;
    }

    /**
     * 
     */
    public void setUitsluitingNLKiesrecht(final HistorieStatus uitsluitingNLKiesrecht) {
        this.uitsluitingNLKiesrecht = uitsluitingNLKiesrecht;
    }

    /**
     * 
     */
    public AdellijkeTitel getAdellijkeTitel() {
        return AdellijkeTitel.parseId(adellijkeTitelId);
    }

    /**
     * 
     */
    public void setAdellijkeTitel(final AdellijkeTitel titel) {
        if (titel == null) {
            adellijkeTitelId = null;
        } else {
            adellijkeTitelId = titel.getId();
        }
    }

    /**
     * 
     */
    public Integer getAdellijkeTitelId() {
        return adellijkeTitelId;
    }

    /**
     * 
     */
    public void setAdellijkeTitelId(final Integer adellijkeTitelId) {
        this.adellijkeTitelId = adellijkeTitelId;
    }

    /**
     * 
     */
    public Set<PersoonSamengesteldeNaamHistorie> getPersoonSamengesteldeNaamHistorieSet() {
        return persoonSamengesteldeNaamHistorieSet;
    }

    /**
     * 
     */
    public void addPersoonSamengesteldeNaamHistorie(
            final PersoonSamengesteldeNaamHistorie persoonSamengesteldeNaamHistorie) {
        persoonSamengesteldeNaamHistorie.setPersoon(this);
        persoonSamengesteldeNaamHistorieSet.add(persoonSamengesteldeNaamHistorie);
    }

    /**
     * 
     */
    public boolean removePersoonSamengesteldeNaamHistorie(
            final PersoonSamengesteldeNaamHistorie persoonSamengesteldeNaamHistorie) {
        return persoonSamengesteldeNaamHistorieSet.remove(persoonSamengesteldeNaamHistorie);
    }

    /**
     * 
     */
    public Set<PersoonAanschrijvingHistorie> getPersoonAanschrijvingHistorieSet() {
        return persoonAanschrijvingHistorieSet;
    }

    /**
     * 
     */
    public void addPersoonAanschrijvingHistorie(final PersoonAanschrijvingHistorie persoonAanschrijvingHistorie) {
        persoonAanschrijvingHistorie.setPersoon(this);
        persoonAanschrijvingHistorieSet.add(persoonAanschrijvingHistorie);
    }

    /**
     * 
     */
    public boolean
            removePersoonAanschrijvingHistorie(final PersoonAanschrijvingHistorie persoonAanschrijvingHistorie) {
        return persoonAanschrijvingHistorieSet.remove(persoonAanschrijvingHistorie);
    }

    /**
     * 
     */
    public Set<PersoonEUVerkiezingenHistorie> getPersoonEUVerkiezingenHistorieSet() {
        return persoonEUVerkiezingenHistorieSet;
    }

    /**
     * 
     */
    public void addPersoonEUVerkiezingenHistorie(final PersoonEUVerkiezingenHistorie persoonEUVerkiezingenHistorie) {
        persoonEUVerkiezingenHistorie.setPersoon(this);
        persoonEUVerkiezingenHistorieSet.add(persoonEUVerkiezingenHistorie);
    }

    /**
     * 
     */
    public boolean removePersoonEUVerkiezingenHistorie(
            final PersoonEUVerkiezingenHistorie persoonEUVerkiezingenHistorie) {
        return persoonEUVerkiezingenHistorieSet.remove(persoonEUVerkiezingenHistorie);
    }

    /**
     * 
     */
    public Set<PersoonGeboorteHistorie> getPersoonGeboorteHistorieSet() {
        return persoonGeboorteHistorieSet;
    }

    /**
     * 
     */
    public void addPersoonGeboorteHistorie(final PersoonGeboorteHistorie persoonGeboorteHistorie) {
        persoonGeboorteHistorie.setPersoon(this);
        persoonGeboorteHistorieSet.add(persoonGeboorteHistorie);
    }

    /**
     * 
     */
    public boolean removePersoonGeboorteHistorie(final PersoonGeboorteHistorie persoonGeboorteHistorie) {
        return persoonGeboorteHistorieSet.remove(persoonGeboorteHistorie);
    }

    /**
     * 
     */
    public Set<PersoonGeslachtsaanduidingHistorie> getPersoonGeslachtsaanduidingHistorieSet() {
        return persoonGeslachtsaanduidingHistorieSet;
    }

    /**
     * 
     */
    public void addPersoonGeslachtsaanduidingHistorie(
            final PersoonGeslachtsaanduidingHistorie persoonGeslachtsaanduidingHistorie) {
        persoonGeslachtsaanduidingHistorie.setPersoon(this);
        persoonGeslachtsaanduidingHistorieSet.add(persoonGeslachtsaanduidingHistorie);
    }

    /**
     * 
     */
    public boolean removePersoonGeslachtsaanduidingHistorie(
            final PersoonGeslachtsaanduidingHistorie persoonGeslachtsaanduidingHistorie) {
        return persoonGeslachtsaanduidingHistorieSet.remove(persoonGeslachtsaanduidingHistorie);
    }

    /**
     * 
     */
    public Set<PersoonIDHistorie> getPersoonIDHistorieSet() {
        return persoonIDHistorieSet;
    }

    /**
     * 
     */
    public void addPersoonIDHistorie(final PersoonIDHistorie persoonIDHistorie) {
        persoonIDHistorie.setPersoon(this);
        persoonIDHistorieSet.add(persoonIDHistorie);
    }

    /**
     * 
     */
    public boolean removePersoonIDHistorie(final PersoonIDHistorie persoonIDHistorie) {
        return persoonIDHistorieSet.remove(persoonIDHistorie);
    }

    /**
     * 
     */
    public Set<PersoonVerblijfsrechtHistorie> getPersoonVerblijfsrechtHistorieSet() {
        return persoonVerblijfsrechtHistorieSet;
    }

    /**
     * 
     */
    public void addPersoonVerblijfsrechtHistorie(final PersoonVerblijfsrechtHistorie persoonVerblijfsrechtHistorie) {
        persoonVerblijfsrechtHistorie.setPersoon(this);
        persoonVerblijfsrechtHistorieSet.add(persoonVerblijfsrechtHistorie);
    }

    /**
     * 
     */
    public boolean removePersoonVerblijfsrechtHistorie(
            final PersoonVerblijfsrechtHistorie persoonVerblijfsrechtHistorie) {
        return persoonVerblijfsrechtHistorieSet.remove(persoonVerblijfsrechtHistorie);
    }

    /**
     * 
     */
    public Set<PersoonBijhoudingsgemeenteHistorie> getPersoonBijhoudingsgemeenteHistorieSet() {
        return persoonBijhoudingsgemeenteHistorieSet;
    }

    /**
     * 
     */
    public void addPersoonBijhoudingsgemeenteHistorie(
            final PersoonBijhoudingsgemeenteHistorie persoonBijhoudingsgemeenteHistorie) {
        persoonBijhoudingsgemeenteHistorie.setPersoon(this);
        persoonBijhoudingsgemeenteHistorieSet.add(persoonBijhoudingsgemeenteHistorie);
    }

    /**
     * 
     */
    public boolean removePersoonBijhoudingsgemeenteHistorie(
            final PersoonBijhoudingsgemeenteHistorie persoonBijhoudingsgemeenteHistorie) {
        return persoonBijhoudingsgemeenteHistorieSet.remove(persoonBijhoudingsgemeenteHistorie);
    }

    /**
     * 
     */
    public Set<PersoonImmigratieHistorie> getPersoonImmigratieHistorieSet() {
        return persoonImmigratieHistorieSet;
    }

    /**
     * 
     */
    public void addPersoonImmigratieHistorie(final PersoonImmigratieHistorie persoonImmigratieHistorie) {
        persoonImmigratieHistorie.setPersoon(this);
        persoonImmigratieHistorieSet.add(persoonImmigratieHistorie);
    }

    /**
     * 
     */
    public boolean removePersoonImmigratieHistorie(final PersoonImmigratieHistorie persoonImmigratieHistorie) {
        return persoonImmigratieHistorieSet.remove(persoonImmigratieHistorie);
    }

    /**
     * 
     */
    public Set<PersoonInschrijvingHistorie> getPersoonInschrijvingHistorieSet() {
        return persoonInschrijvingHistorieSet;
    }

    /**
     * 
     */
    public void addPersoonInschrijvingHistorie(final PersoonInschrijvingHistorie persoonInschrijvingHistorie) {
        persoonInschrijvingHistorie.setPersoon(this);
        persoonInschrijvingHistorieSet.add(persoonInschrijvingHistorie);
    }

    /**
     * 
     */
    public boolean removePersoonInschrijvingHistorie(final PersoonInschrijvingHistorie persoonInschrijvingHistorie) {
        return persoonInschrijvingHistorieSet.remove(persoonInschrijvingHistorie);
    }

    /**
     * 
     */
    public Set<PersoonOpschortingHistorie> getPersoonOpschortingHistorieSet() {
        return persoonOpschortingHistorieSet;
    }

    /**
     * 
     */
    public void addPersoonOpschortingHistorie(final PersoonOpschortingHistorie persoonOpschortingHistorie) {
        persoonOpschortingHistorie.setPersoon(this);
        persoonOpschortingHistorieSet.add(persoonOpschortingHistorie);
    }

    /**
     * 
     */
    public boolean removePersoonOpschortingHistorie(final PersoonOpschortingHistorie persoonOpschortingHistorie) {
        return persoonOpschortingHistorieSet.remove(persoonOpschortingHistorie);
    }

    /**
     * 
     */
    public Set<PersoonOverlijdenHistorie> getPersoonOverlijdenHistorieSet() {
        return persoonOverlijdenHistorieSet;
    }

    /**
     * 
     */
    public void addPersoonOverlijdenHistorie(final PersoonOverlijdenHistorie persoonOverlijdenHistorie) {
        persoonOverlijdenHistorie.setPersoon(this);
        persoonOverlijdenHistorieSet.add(persoonOverlijdenHistorie);
    }

    /**
     * 
     */
    public boolean removePersoonOverlijdenHistorie(final PersoonOverlijdenHistorie persoonOverlijdenHistorie) {
        return persoonOverlijdenHistorieSet.remove(persoonOverlijdenHistorie);
    }

    /**
     * 
     */
    public Set<PersoonPersoonskaartHistorie> getPersoonPersoonskaartHistorieSet() {
        return persoonPersoonskaartHistorieSet;
    }

    /**
     * 
     */
    public void addPersoonPersoonskaartHistorie(final PersoonPersoonskaartHistorie persoonPersoonskaartHistorie) {
        persoonPersoonskaartHistorie.setPersoon(this);
        persoonPersoonskaartHistorieSet.add(persoonPersoonskaartHistorie);
    }

    /**
     * 
     */
    public boolean
            removePersoonPersoonskaartHistorie(final PersoonPersoonskaartHistorie persoonPersoonskaartHistorie) {
        return persoonPersoonskaartHistorieSet.remove(persoonPersoonskaartHistorie);
    }

    /**
     * 
     */
    public Set<PersoonBijhoudingsverantwoordelijkheidHistorie> getPersoonBijhoudingsverantwoordelijkheidHistorieSet() {
        return persoonBijhoudingsverantwoordelijkheidHistorieSet;
    }

    /**
     * 
     */
    public void addPersoonBijhoudingsverantwoordelijkheidHistorie(
            final PersoonBijhoudingsverantwoordelijkheidHistorie persoonBijhoudingsverantwoordelijkheidHistorie) {
        persoonBijhoudingsverantwoordelijkheidHistorie.setPersoon(this);
        persoonBijhoudingsverantwoordelijkheidHistorieSet.add(persoonBijhoudingsverantwoordelijkheidHistorie);
    }

    /**
     * 
     */
    public boolean removePersoonBijhoudingsverantwoordelijkheidHistorie(
            final PersoonBijhoudingsverantwoordelijkheidHistorie persoonBijhoudingsverantwoordelijkheidHistorie) {
        return persoonBijhoudingsverantwoordelijkheidHistorieSet
                .remove(persoonBijhoudingsverantwoordelijkheidHistorie);
    }

    /**
     * 
     */
    public Set<PersoonUitsluitingNLKiesrechtHistorie> getPersoonUitsluitingNLKiesrechtHistorieSet() {
        return persoonUitsluitingNLKiesrechtHistorieSet;
    }

    /**
     * 
     */
    public void addPersoonUitsluitingNLKiesrechtHistorie(
            final PersoonUitsluitingNLKiesrechtHistorie persoonUitsluitingNLKiesrechtHistorie) {
        persoonUitsluitingNLKiesrechtHistorie.setPersoon(this);
        persoonUitsluitingNLKiesrechtHistorieSet.add(persoonUitsluitingNLKiesrechtHistorie);
    }

    /**
     * 
     */
    public boolean removePersoonUitsluitingNLKiesrechtHistorie(
            final PersoonUitsluitingNLKiesrechtHistorie persoonUitsluitingNLKiesrechtHistorie) {
        return persoonUitsluitingNLKiesrechtHistorieSet.remove(persoonUitsluitingNLKiesrechtHistorie);
    }

    /**
     * 
     */
    public Set<Betrokkenheid> getAlleGerelateerdeBetrokkenheden() {
        final Set<Betrokkenheid> result = new LinkedHashSet<Betrokkenheid>();
        for (final Betrokkenheid ikBetrokkenheid : betrokkenheidSet) {
            result.addAll(ikBetrokkenheid.getAndereBetrokkenhedenVanRelatie());
        }
        return result;
    }

    /**
     * 
     */
    public Set<Relatie> getRelaties() {
        final Set<Relatie> result = new LinkedHashSet<Relatie>();
        for (final Betrokkenheid ikBetrokkenheid : betrokkenheidSet) {
            result.add(ikBetrokkenheid.getRelatie());
        }
        return result;
    }

    /**
     * Geeft de lijst van betrokkenheden - van het meegegeven soort - met relaties waarvan het soort overeenkomt met één
     * van de meegegeven relatie soorten.
     * 
     * @param soortIkBetrokkenheid
     *            de soort ik-betrokkenheid van deze persoon
     * @param relatieSoorten
     *            de toegestande relatie soort
     * @return de lijst met betrokkenheden die aan de criteria voldoet
     */
    private Set<Betrokkenheid> getBetrokkenhedenVoorSoortRelatie(
            final SoortBetrokkenheid soortIkBetrokkenheid,
            final SoortRelatie... relatieSoorten) {
        final Set<Betrokkenheid> result = new LinkedHashSet<Betrokkenheid>();
        for (final Betrokkenheid ikBetrokkenheid : betrokkenheidSet) {
            if (!ikBetrokkenheid.getSoortBetrokkenheid().equals(soortIkBetrokkenheid)) {
                continue;
            }
            for (final SoortRelatie soortRelatie : relatieSoorten) {
                if (soortRelatie.equals(ikBetrokkenheid.getRelatie().getSoortRelatie())) {
                    result.add(ikBetrokkenheid);
                }
            }
        }
        return result;
    }

    /**
     * 
     */
    public Set<BinaireRelatie> getBinaireRelatiesVoorHuwelijkOfGp() {
        final Set<Betrokkenheid> huwelijkOfGpBetrokkenheden =
                getBetrokkenhedenVoorSoortRelatie(SoortBetrokkenheid.PARTNER,
                        SoortRelatie.GEREGISTREERD_PARTNERSCHAP, SoortRelatie.HUWELIJK);
        final Set<BinaireRelatie> result = new HashSet<BinaireRelatie>();
        for (final Betrokkenheid huwelijkOfGpBetrokkenheid : huwelijkOfGpBetrokkenheden) {
            for (final Betrokkenheid andereBetrokkenheid : huwelijkOfGpBetrokkenheid
                    .getAndereBetrokkenhedenVanRelatie()) {
                result.add(new BinaireRelatie(huwelijkOfGpBetrokkenheid, andereBetrokkenheid));
            }
        }
        return result;
    }

    /**
     * 
     */
    public Set<BinaireRelatie> getBinaireRelatiesVoorKind() {
        final Set<Betrokkenheid> kindBetrokkenheden =
                getBetrokkenhedenVoorSoortRelatie(SoortBetrokkenheid.KIND, SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final Set<BinaireRelatie> result = new HashSet<BinaireRelatie>();
        for (final Betrokkenheid kindBetrokkenheid : kindBetrokkenheden) {
            for (final Betrokkenheid andereBetrokkenheid : kindBetrokkenheid.getAndereBetrokkenhedenVanRelatie()) {
                if (SoortBetrokkenheid.OUDER.equals(andereBetrokkenheid.getSoortBetrokkenheid())) {
                    result.add(new BinaireRelatie(kindBetrokkenheid, andereBetrokkenheid));
                }
            }
        }
        return result;
    }

    /**
     * 
     */
    public Set<BinaireRelatie> getBinaireRelatiesVoorOuder() {
        final Set<Betrokkenheid> ouderBetrokkenheden =
                getBetrokkenhedenVoorSoortRelatie(SoortBetrokkenheid.OUDER,
                        SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final Set<BinaireRelatie> result = new HashSet<BinaireRelatie>();
        for (final Betrokkenheid ouderBetrokkenheid : ouderBetrokkenheden) {
            for (final Betrokkenheid andereBetrokkenheid : ouderBetrokkenheid.getAndereBetrokkenhedenVanRelatie()) {
                if (SoortBetrokkenheid.KIND.equals(andereBetrokkenheid.getSoortBetrokkenheid())) {
                    result.add(new BinaireRelatie(ouderBetrokkenheid, andereBetrokkenheid));
                }
            }
        }
        return result;
    }

    /**
     * Zoekt in de lijst van betrokkenen naar een match met de meegegeven betrokkenheid, hierbij wordt gekeken naar de
     * a-nummers van de betrokkenen en het soort betrokkenheid en het soort relatie. Daarnaast wordt de historie van de
     * relatie en betrokkenheid met elkaar vergeleken. Als een match wordt gevonden dan wordt deze geretourneerd.
     * <p/>
     * In de vergelijking worden twee verschillen genegeerd bij het vergelijken van de historie van relaties:
     * <ol>
     * <li>TODO</li>
     * <li>TODO</li>
     * </ol>
     * 
     * @param andereBinaireRelatie
     *            de andere binaire relatie waarmee een match moet worden gevonden, mag niet null zijn
     * @return een binaire relatie van deze persoon die een match vormt met de andere binaire relatie
     * @throws NullPointerException
     *             als andereBetrokkenheid null is
     */
    public BinaireRelatie getGelijkeBinaireRelatieVoorHuwelijkOfGp(final BinaireRelatie andereBinaireRelatie) {
        if (andereBinaireRelatie == null) {
            throw new NullPointerException("andereBinaireRelatie mag niet null zijn");
        }
        return getGelijkeBinaireRelatie(getBinaireRelatiesVoorHuwelijkOfGp(), andereBinaireRelatie, true);
    }

    /**
     * 
     * @param andereBinaireKindRelatie
     *            de andere binaire kind relatie waarmee vergeleken moet worden
     * @param controleerBetrokkenheden
     *            true als de betrokkenheid en betrokkenheid-historie in de vergelijking moet worden meegenomen, anders
     *            false
     * @return de gelijke binaireKindRelatie van deze persoon of null als deze niet bestaat
     */
    public BinaireRelatie getGelijkeBinaireRelatieVoorKind(
            final BinaireRelatie andereBinaireKindRelatie,
            final boolean controleerBetrokkenheden) {
        if (andereBinaireKindRelatie == null) {
            throw new NullPointerException("andereBinaireKindRelatie mag niet null zijn");
        }
        return getGelijkeBinaireRelatie(getBinaireRelatiesVoorKind(), andereBinaireKindRelatie,
                controleerBetrokkenheden);
    }

    /**
     * 
     * @param andereBinaireOuderRelatie
     *            de andere binaire ouder relatie waarmee vergeleken moet worden
     * @return de gelijke binaireOuderRelatie van deze persoon of null als deze niet bestaat
     */
    public BinaireRelatie getGelijkeBinaireRelatieVoorOuder(final BinaireRelatie andereBinaireOuderRelatie) {
        if (andereBinaireOuderRelatie == null) {
            throw new NullPointerException("andereBinaireOuderRelatie mag niet null zijn");
        }
        return getGelijkeBinaireRelatie(getBinaireRelatiesVoorOuder(), andereBinaireOuderRelatie, false);
    }

    private BinaireRelatie getGelijkeBinaireRelatie(
            final Set<BinaireRelatie> binaireRelaties,
            final BinaireRelatie andereBinaireRelatie,
            final boolean controleerBetrokkenheid) {
        BinaireRelatie result = null;
        for (final BinaireRelatie binaireRelatie : binaireRelaties) {
            if (binaireRelatie.getIkBetrokkenheid().isInhoudelijkGelijkAan(andereBinaireRelatie.getIkBetrokkenheid(),
                    true, controleerBetrokkenheid)
                    && binaireRelatie.getAndereBetrokkenheid().isInhoudelijkGelijkAan(
                            andereBinaireRelatie.getAndereBetrokkenheid(), false, controleerBetrokkenheid)) {
                result = binaireRelatie;
                break;
            }
        }
        return result;
    }

    /**
     * Verwijderd alle onetomany associaties van deze persoon en retourneerd de lijst met entities die nu verwijderd
     * kunnen worden.
     * 
     * @return de lijst met entiteiten die afhankelijk zijn van deze persoon en nu verwijders zijn als associatie
     */
    public Set<Object> verwijderAlleRelatiesAfhankelijkVanPersoon() {
        final Set<Object> result = new HashSet<Object>();
        for (final PersoonAdres persoonAdres : persoonAdresSet) {
            removePersoonAdres(persoonAdres);
            result.add(persoonAdres);
        }
        for (final PersoonGeslachtsnaamcomponent persoonGeslachtsnaamcomponent : persoonGeslachtsnaamcomponentSet) {
            removePersoonGeslachtsnaamcomponent(persoonGeslachtsnaamcomponent);
            result.add(persoonGeslachtsnaamcomponent);
        }
        for (final PersoonIndicatie persoonIndicatie : persoonIndicatieSet) {
            removePersoonIndicatie(persoonIndicatie);
            result.add(persoonIndicatie);
        }
        for (final PersoonNationaliteit persoonNationaliteit : persoonNationaliteitSet) {
            removePersoonNationaliteit(persoonNationaliteit);
            result.add(persoonNationaliteit);
        }
        for (final PersoonReisdocument persoonReisdocument : persoonReisdocumentSet) {
            removePersoonReisdocument(persoonReisdocument);
            result.add(persoonReisdocument);
        }
        for (final PersoonVerificatie persoonVerificatie : persoonVerificatieSet) {
            removePersoonVerificatie(persoonVerificatie);
            result.add(persoonVerificatie);
        }
        for (final PersoonVoornaam persoonVoornaam : persoonVoornaamSet) {
            removePersoonVoornaam(persoonVoornaam);
            result.add(persoonVoornaam);
        }
        for (final PersoonSamengesteldeNaamHistorie persoonSamengesteldeNaamHistorie : persoonSamengesteldeNaamHistorieSet) {
            removePersoonSamengesteldeNaamHistorie(persoonSamengesteldeNaamHistorie);
            result.add(persoonSamengesteldeNaamHistorie);
        }
        for (final PersoonAanschrijvingHistorie persoonAanschrijvingHistorie : persoonAanschrijvingHistorieSet) {
            removePersoonAanschrijvingHistorie(persoonAanschrijvingHistorie);
            result.add(persoonAanschrijvingHistorie);
        }
        for (final PersoonEUVerkiezingenHistorie persoonEUVerkiezingenHistorie : persoonEUVerkiezingenHistorieSet) {
            removePersoonEUVerkiezingenHistorie(persoonEUVerkiezingenHistorie);
            result.add(persoonEUVerkiezingenHistorie);
        }
        for (final PersoonGeboorteHistorie persoonGeboorteHistorie : persoonGeboorteHistorieSet) {
            removePersoonGeboorteHistorie(persoonGeboorteHistorie);
            result.add(persoonGeboorteHistorie);
        }
        for (final PersoonGeslachtsaanduidingHistorie persoonGeslachtsaanduidingHistorie : persoonGeslachtsaanduidingHistorieSet) {
            removePersoonGeslachtsaanduidingHistorie(persoonGeslachtsaanduidingHistorie);
            result.add(persoonGeslachtsaanduidingHistorie);
        }
        for (final PersoonIDHistorie persoonIDHistorie : persoonIDHistorieSet) {
            removePersoonIDHistorie(persoonIDHistorie);
            result.add(persoonIDHistorie);
        }
        for (final PersoonBijhoudingsgemeenteHistorie persoonBijhoudingsgemeenteHistorie : persoonBijhoudingsgemeenteHistorieSet) {
            removePersoonBijhoudingsgemeenteHistorie(persoonBijhoudingsgemeenteHistorie);
            result.add(persoonBijhoudingsgemeenteHistorie);
        }
        for (final PersoonImmigratieHistorie persoonImmigratieHistorie : persoonImmigratieHistorieSet) {
            removePersoonImmigratieHistorie(persoonImmigratieHistorie);
            result.add(persoonImmigratieHistorie);
        }
        for (final PersoonInschrijvingHistorie persoonInschrijvingHistorie : persoonInschrijvingHistorieSet) {
            removePersoonInschrijvingHistorie(persoonInschrijvingHistorie);
            result.add(persoonInschrijvingHistorie);
        }
        for (final PersoonOpschortingHistorie persoonOpschortingHistorie : persoonOpschortingHistorieSet) {
            removePersoonOpschortingHistorie(persoonOpschortingHistorie);
            result.add(persoonOpschortingHistorie);
        }
        for (final PersoonOverlijdenHistorie persoonOverlijdenHistorie : persoonOverlijdenHistorieSet) {
            removePersoonOverlijdenHistorie(persoonOverlijdenHistorie);
            result.add(persoonOverlijdenHistorie);
        }
        for (final PersoonPersoonskaartHistorie persoonPersoonskaartHistorie : persoonPersoonskaartHistorieSet) {
            removePersoonPersoonskaartHistorie(persoonPersoonskaartHistorie);
            result.add(persoonPersoonskaartHistorie);
        }
        for (final PersoonBijhoudingsverantwoordelijkheidHistorie persoonBijhoudingsverantwoordelijkheidHistorie : persoonBijhoudingsverantwoordelijkheidHistorieSet) {
            removePersoonBijhoudingsverantwoordelijkheidHistorie(persoonBijhoudingsverantwoordelijkheidHistorie);
            result.add(persoonBijhoudingsverantwoordelijkheidHistorie);
        }
        for (final PersoonVerblijfsrechtHistorie persoonVerblijfsrechtHistorie : persoonVerblijfsrechtHistorieSet) {
            removePersoonVerblijfsrechtHistorie(persoonVerblijfsrechtHistorie);
            result.add(persoonVerblijfsrechtHistorie);
        }
        for (final PersoonUitsluitingNLKiesrechtHistorie persoonUitsluitingNLKiesrechtHistorie : persoonUitsluitingNLKiesrechtHistorieSet) {
            removePersoonUitsluitingNLKiesrechtHistorie(persoonUitsluitingNLKiesrechtHistorie);
            result.add(persoonUitsluitingNLKiesrechtHistorie);
        }
        return result;
    }

    /**
     * 
     */
    public void ontkenAlleRelatiesVanuitBetrokkenen() {
        for (final BinaireRelatie binairHuwelijkOfGp : getBinaireRelatiesVoorHuwelijkOfGp()) {
            MultiRealiteitRegel.maakMultiRealiteitRegel(binairHuwelijkOfGp.inverse());
        }
        for (final BinaireRelatie binairKindRelatie : getBinaireRelatiesVoorKind()) {
            MultiRealiteitRegel.maakMultiRealiteitRegel(binairKindRelatie.inverse());
        }
        for (final BinaireRelatie binairOuderRelatie : getBinaireRelatiesVoorOuder()) {
            MultiRealiteitRegel.maakMultiRealiteitRegel(binairOuderRelatie.inverse());
        }
    }
}
