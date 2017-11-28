/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.annotation.IndicatieActueelEnGeldig;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AdellijkeTitel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Naamgebruik;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMigratie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;
import nl.bzk.algemeenbrp.util.common.DatumUtil;

/**
 * The persistent class for the pers database table.
 * <p/>
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "pers", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))
@NamedQueries({@NamedQuery(name = "Persoon.isPersoonGeblokkeerd", query = "select count(*) > 0 from Blokkering b where b.aNummer = :anummer"),
        @NamedQuery(name = "Persoon.zoekGerelateerdePseudoPersonen", query = "select p from Persoon p where (p.burgerservicenummer = :burgerservicenummer or p"
                + ".administratienummer = :administratienummer) and p.soortPersoonId = :soortPersoonId"),
        @NamedQuery(name = "Persoon.zoekPersonenMetDezelfdeBsn", query = "select p from Persoon p where p.burgerservicenummer = :burgerservicenummer and "
                + "not(nadereBijhoudingsaardId in (:nadereBijhoudingsaardFoutId,:nadereBijhoudingsaardGewistId)) and p.soortPersoonId = :soortPersoonId"),
        @NamedQuery(name = "Persoon.zoekPersonenMetHetzelfdeAdministratienummer", query = "select p from Persoon p where "
                + "p.administratienummer = :administratienummer and not(nadereBijhoudingsaardId in (:nadereBijhoudingsaardFoutId,:nadereBijhoudingsaardGewistId"
                + ")) and p.soortPersoonId = :soortPersoonId")})
public class Persoon extends AbstractEntiteit implements Afleidbaar, RootEntiteit, Serializable {

    /**
     * Veldnaam van nadere bijhoudingsaard tbv verschil verwerking.
     */
    public static final String NADERE_BIJHOUDINGSAARD = "nadereBijhoudingsaardId";
    /**
     * Veldnaam van naamgebrui historie set tbv verschil verwerking.
     */
    public static final String PERSOON_NAAMGEBRUIK_HISTORIE_SET = "persoonNaamgebruikHistorieSet";
    /**
     * Veldnaam van betrokkenheidset tbv verschil verwerking.
     */
    public static final String PERSOON_REISDOCUMENT_SET = "persoonReisdocumentSet";
    /**
     * Veldnaam van betrokkenheidset tbv verschil verwerking.
     */
    public static final String BETROKKENHEID_SET = "betrokkenheidSet";
    /**
     * Veldnaam van stapels tbv verschil verwerking.
     */
    public static final String STAPELS_SET = "stapels";
    /**
     * Veldnaam van migratie historie set tbv bepalen infra wijziging.
     */
    public static final String PERSOON_MIGRATIE_HISTORIE_SET = "persoonMigratieHistorieSet";
    /**
     * Veldnaam van bijhouding historie set tbv bepalen infra wijziging.
     */
    public static final String PERSOON_BIJHOUDING_HISTORIE_SET = "persoonBijhoudingHistorieSet";
    /**
     * Veldnaam van adres set tbv bepalen infra wijziging.
     */
    public static final String PERSOON_ADRES_SET = "persoonAdresSet";
    /**
     * Veldnaam van migratie historie set tbv bepalen infra wijziging.
     */
    public static final String PERSOON_INSCHRIJVING_HISTORIE_SET = "persoonInschrijvingHistorieSet";
    /**
     * Veldnaam van migratie historie set tbv bepalen infra wijziging.
     */
    public static final String TIJDSTIP_LAATSTE_WIJZIGING = "tijdstipLaatsteWijziging";
    /**
     * Veldnaam van inschrijving historie set tbv bepalen infra wijziging.
     */
    public static final String VERSIE_NUMMER = "versienummer";
    /**
     * Veldnaam van datum/tijd stempel tbv bepalen infra wijziging.
     */
    public static final String DATUM_TIJD_STEMPEL = "datumtijdstempel";
    /**
     * Veldnaam van soort migratie tbv bepalen infra wijziging.
     */
    public static final String SOORT_MIGRATIE_ID = "soortMigratieId";
    /**
     * Veldnaam van buitenlandsadres regel 1 tbv bepalen infra wijziging.
     */
    public static final String BUITENLANDS_ADRES_REGEL1_MIGRATIE = "buitenlandsAdresRegel1Migratie";
    /**
     * Veldnaam van buitenlandsadres regel 2 tbv bepalen infra wijziging.
     */
    public static final String BUITENLANDS_ADRES_REGEL2_MIGRATIE = "buitenlandsAdresRegel2Migratie";
    /**
     * Veldnaam van buitenlandsadres regel 3 tbv bepalen infra wijziging.
     */
    public static final String BUITENLANDS_ADRES_REGEL3_MIGRATIE = "buitenlandsAdresRegel3Migratie";
    /**
     * Veldnaam van buitenlandsadres regel 4 tbv bepalen infra wijziging.
     */
    public static final String BUITENLANDS_ADRES_REGEL4_MIGRATIE = "buitenlandsAdresRegel4Migratie";
    /**
     * Veldnaam van buitenlandsadres regel 5 tbv bepalen infra wijziging.
     */
    public static final String BUITENLANDS_ADRES_REGEL5_MIGRATIE = "buitenlandsAdresRegel5Migratie";
    /**
     * Veldnaam van buitenlandsadres regel 6 tbv bepalen infra wijziging.
     */
    public static final String BUITENLANDS_ADRES_REGEL6_MIGRATIE = "buitenlandsAdresRegel6Migratie";
    /**
     * Veldnaam van tijdstip laatste wijziging GBA tbv bepalen infra wijziging.
     */
    public static final String TIJDSTIP_LAATSTE_WIJZIGING_GBA_SYSTEMATIEK = "tijdstipLaatsteWijzigingGbaSystematiek";
    /**
     * Veldnaam van bijhoudingspartij tbv bepalen infra wijziging.
     */
    public static final String BIJHOUDINGS_PARTIJ = "bijhoudingspartij";
    /**
     * Veldnaam van reden wijziging migratie tbv bepalen infra wijziging.
     */
    public static final String REDEN_WIJZIGING_MIGRATIE = "redenWijzigingMigratie";
    /**
     * Veldnaam van land/gebied migratie tbv bepalen infra wijziging.
     */
    public static final String LAND_OF_GEBIED_MIGRATIE = "landOfGebiedMigratie";
    /**
     * Veldnaam van aangever migratie tbv bepalen infra wijziging.
     */
    public static final String AANGEVER_MIGRATIE = "aangeverMigratie";

    /**
     * Persoon attribuut wat gebruikt wordt in JPA annotaties.
     */
    private static final String PERSOON_ATTRIBUUT = "persoon";
    private static final long serialVersionUID = 1L;
    private static final int BSN_LENGTE = 9;
    private static final int ANUMMER_LENGTE = 10;

    @Id
    @SequenceGenerator(name = "pers_id_generator", sequenceName = "kern.seq_pers", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pers_id_generator")
    @Column(nullable = false)
    private Long id;
    @Version
    @Column(name = "lockversie")
    private Long lockVersie;
    @Column(name = "srt", nullable = false)
    private int soortPersoonId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "admhnd")
    private AdministratieveHandeling administratieveHandeling;
    @Column(name = "sorteervolgorde", nullable = false)
    private Short sorteervolgorde = 1;
    @Column(name = "anr", length = ANUMMER_LENGTE)
    private String administratienummer;
    @Column(name = "blplaatsgeboorte", length = 40)
    private String buitenlandsePlaatsGeboorte;
    @Column(name = "blplaatsoverlijden", length = 40)
    private String buitenlandsePlaatsOverlijden;
    @Column(name = "blregiogeboorte", length = 35)
    private String buitenlandseRegioGeboorte;
    @Column(name = "blregiooverlijden", length = 35)
    private String buitenlandseRegioOverlijden;
    @Column(name = "bsn", length = BSN_LENGTE)
    private String burgerservicenummer;
    @Column(name = "dataanlaanpdeelneuverkiezing")
    private Integer datumAanleidingAanpassingDeelnameEuVerkiezingen;
    @Column(name = "dataanvverblijfsr")
    private Integer datumAanvangVerblijfsrecht;
    @Column(name = "datmededelingverblijfsr")
    private Integer datumMededelingVerblijfsrecht;
    @Column(name = "datvoorzeindeuitsleuverkiezi")
    private Integer datumVoorzienEindeUitsluitingEuVerkiezingen;
    @Column(name = "datvoorzeindeuitslkiesr")
    private Integer datumVoorzienEindeUitsluitingKiesrecht;
    @Column(name = "datgeboorte")
    private Integer datumGeboorte;
    @Column(name = "datinschr")
    private Integer datumInschrijving;
    @Column(name = "datoverlijden")
    private Integer datumOverlijden;
    @Column(name = "datvoorzeindeverblijfsr")
    private Integer datumVoorzienEindeVerblijfsrecht;
    @Column(name = "geslnaamstam", length = 200)
    private String geslachtsnaamstam;
    @Column(name = "geslnaamstamnaamgebruik", length = 200)
    private String geslachtsnaamstamNaamgebruik;
    @Column(name = "indnaamgebruikafgeleid")
    private Boolean indicatieNaamgebruikAfgeleid;
    @Column(name = "indafgeleid")
    private Boolean indicatieAfgeleid;
    @Column(name = "inddeelneuverkiezingen")
    private Boolean indicatieDeelnameEuVerkiezingen;
    @Column(name = "indnreeks")
    private Boolean indicatieNamenreeks;
    @Column(name = "indpkvollediggeconv")
    private Boolean indicatiePersoonskaartVolledigGeconverteerd;
    @Column(name = "induitslkiesr")
    private Boolean indicatieUitsluitingKiesrecht;
    @Column(name = "omslocgeboorte", length = 40)
    private String omschrijvingGeboortelocatie;
    @Column(name = "omslocoverlijden", length = 40)
    private String omschrijvingLocatieOverlijden;
    @Column(length = 1)
    private Character scheidingsteken;
    @Column(name = "scheidingstekennaamgebruik", length = 1)
    private Character scheidingstekenNaamgebruik;
    @Column(name = "tslaatstewijz")
    private Timestamp tijdstipLaatsteWijziging;
    @Column(name = "tslaatstewijzgbasystematiek")
    private Timestamp tijdstipLaatsteWijzigingGbaSystematiek;
    @Column(name = "versienr")
    private Long versienummer;
    @Column(name = "dattijdstempel")
    private Timestamp datumtijdstempel;
    @Column(length = 200)
    private String voornamen;
    @Column(name = "voornamennaamgebruik", length = 200)
    private String voornamenNaamgebruik;
    @Column(name = "adellijketitelnaamgebruik")
    private Integer adellijkeTitelNaamgebruikId;
    @Column(length = ANUMMER_LENGTE)
    private String voorvoegsel;
    @Column(name = "voorvoegselnaamgebruik", length = ANUMMER_LENGTE)
    private String voorvoegselNaamgebruik;
    @Column(name = "srtmigratie")
    private Integer soortMigratieId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rdnwijzmigratie")
    private RedenWijzigingVerblijf redenWijzigingMigratie;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aangmigratie")
    private Aangever aangeverMigratie;
    @Column(name = "bladresregel1migratie")
    private String buitenlandsAdresRegel1Migratie;
    @Column(name = "bladresregel2migratie")
    private String buitenlandsAdresRegel2Migratie;
    @Column(name = "bladresregel3migratie")
    private String buitenlandsAdresRegel3Migratie;
    @Column(name = "bladresregel4migratie")
    private String buitenlandsAdresRegel4Migratie;
    @Column(name = "bladresregel5migratie")
    private String buitenlandsAdresRegel5Migratie;
    @Column(name = "bladresregel6migratie")
    private String buitenlandsAdresRegel6Migratie;
    @Column(name = "adellijketitel")
    private Integer adellijkeTitelId;
    @Column(name = "geslachtsaand")
    private Integer geslachtsaanduidingId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "landgebiedgeboorte")
    private LandOfGebied landOfGebiedGeboorte;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "landgebiedmigratie")
    private LandOfGebied landOfGebiedMigratie;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "landgebiedoverlijden")
    private LandOfGebied landOfGebiedOverlijden;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gempk")
    private Partij gemeentePersoonskaart;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gemoverlijden")
    private Gemeente gemeenteOverlijden;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gemgeboorte")
    private Gemeente gemeenteGeboorte;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {})
    @JoinColumn(name = "bijhpartij")
    private Partij bijhoudingspartij;
    @Column(name = "volgendeanr", length = ANUMMER_LENGTE)
    private String volgendeAdministratienummer;
    @Column(name = "vorigeanr", length = ANUMMER_LENGTE)
    private String vorigeAdministratienummer;
    @Column(name = "volgendebsn", length = BSN_LENGTE)
    private String volgendeBurgerservicenummer;
    @Column(name = "vorigebsn", length = BSN_LENGTE)
    private String vorigeBurgerservicenummer;
    @Column(name = "wplnaamgeboorte")
    private String woonplaatsnaamGeboorte;
    @Column(name = "wplnaamoverlijden")
    private String woonplaatsnaamOverlijden;
    @Column(name = "predicaat")
    private Integer predicaatId;
    @Column(name = "predicaatnaamgebruik")
    private Integer predicaatNaamgebruikId;
    @Column(name = "naderebijhaard")
    private Integer nadereBijhoudingsaardId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AandVerblijfsr")
    private Verblijfsrecht verblijfsrecht;
    @Column(name = "naamgebruik")
    private Integer naamgebruikId;
    @Column(name = "bijhaard")
    private Integer bijhoudingsaardId;
    @Column(name = "indagafgeleidadministratief", nullable = false)
    private boolean isActueelVoorAfgeleidadministratief;
    @Column(name = "indagids", nullable = false)
    private boolean isActueelVoorIds;
    @Column(name = "indagsamengesteldenaam", nullable = false)
    private boolean isActueelVoorSamengesteldenaam;
    @Column(name = "indaggeboorte", nullable = false)
    private boolean isActueelVoorGeboorte;
    @Column(name = "indaggeslachtsaand", nullable = false)
    private boolean isActueelVoorGeslachtsaanduiding;
    @Column(name = "indaginschr", nullable = false)
    private boolean isActueelVoorInschrijving;
    @Column(name = "indagnrverwijzing", nullable = false)
    private boolean isActueelVoorVerwijzing;
    @Column(name = "indagbijhouding", nullable = false)
    private boolean isActueelVoorBijhouding;
    @Column(name = "indagoverlijden", nullable = false)
    private boolean isActueelVoorOverlijden;
    @Column(name = "indagnaamgebruik", nullable = false)
    private boolean isActueelVoorNaamgebruik;
    @Column(name = "indagmigratie", nullable = false)
    private boolean isActueelVoorMigratie;
    @Column(name = "indagverblijfsr", nullable = false)
    private boolean isActueelVoorVerblijfsrecht;
    @Column(name = "indaguitslkiesr", nullable = false)
    private boolean isActueelVoorUitsluitingkiesrecht;
    @Column(name = "indagdeelneuverkiezingen", nullable = false)
    private boolean isActueelVoorDeelnameEuVerkiezingen;
    @Column(name = "indagpk", nullable = false)
    private boolean isActueelVoorPersoonskaart;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private final Set<PersoonAdres> persoonAdresSet = new LinkedHashSet<>(0);
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private final Set<PersoonGeslachtsnaamcomponent> persoonGeslachtsnaamcomponentSet = new LinkedHashSet<>(0);
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private final Set<PersoonIndicatie> persoonIndicatieSet = new LinkedHashSet<>(0);
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private final Set<PersoonNationaliteit> persoonNationaliteitSet = new LinkedHashSet<>(0);
    /**
     * Onderzoeken moeten EAGER worden geladen ivm uni-directionele koppeling van
     * {@link GegevenInOnderzoek} naar {@link Entiteit} door {@link GegevenInOnderzoekListener}. Dit
     * wordt gedaan ivm @Any in de {@link GegevenInOnderzoek}.
     */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private final Set<Onderzoek> onderzoeken = new LinkedHashSet<>(0);
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private final Set<PersoonReisdocument> persoonReisdocumentSet = new LinkedHashSet<>(0);
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private final Set<PersoonVerificatie> persoonVerificatieSet = new LinkedHashSet<>(0);
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private final Set<PersoonVerstrekkingsbeperking> persoonVerstrekkingsbeperkingSet = new LinkedHashSet<>(0);
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private final Set<PersoonVoornaam> persoonVoornaamSet = new LinkedHashSet<>(0);
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private final Set<PersoonBuitenlandsPersoonsnummer> persoonBuitenlandsPersoonsnummerSet = new LinkedHashSet<>(0);

    @IndicatieActueelEnGeldig(naam = "isActueelVoorSamengesteldenaam")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private final Set<PersoonSamengesteldeNaamHistorie> persoonSamengesteldeNaamHistorieSet = new LinkedHashSet<>(0);
    @IndicatieActueelEnGeldig(naam = "isActueelVoorNaamgebruik")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private final Set<PersoonNaamgebruikHistorie> persoonNaamgebruikHistorieSet = new LinkedHashSet<>(0);
    @IndicatieActueelEnGeldig(naam = "isActueelVoorDeelnameEuVerkiezingen")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private final Set<PersoonDeelnameEuVerkiezingenHistorie> persoonDeelnameEuVerkiezingenHistorieSet = new LinkedHashSet<>(0);
    @IndicatieActueelEnGeldig(naam = "isActueelVoorGeboorte")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private final Set<PersoonGeboorteHistorie> persoonGeboorteHistorieSet = new LinkedHashSet<>(0);
    @IndicatieActueelEnGeldig(naam = "isActueelVoorGeslachtsaanduiding")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private final Set<PersoonGeslachtsaanduidingHistorie> persoonGeslachtsaanduidingHistorieSet = new LinkedHashSet<>(0);
    @IndicatieActueelEnGeldig(naam = "isActueelVoorIds")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private final Set<PersoonIDHistorie> persoonIDHistorieSet = new LinkedHashSet<>(0);
    @IndicatieActueelEnGeldig(naam = "isActueelVoorBijhouding")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private final Set<PersoonBijhoudingHistorie> persoonBijhoudingHistorieSet = new LinkedHashSet<>(0);
    @IndicatieActueelEnGeldig(naam = "isActueelVoorMigratie")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private final Set<PersoonMigratieHistorie> persoonMigratieHistorieSet = new LinkedHashSet<>(0);
    @IndicatieActueelEnGeldig(naam = "isActueelVoorInschrijving")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private final Set<PersoonInschrijvingHistorie> persoonInschrijvingHistorieSet = new LinkedHashSet<>(0);
    @IndicatieActueelEnGeldig(naam = "isActueelVoorVerwijzing")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private final Set<PersoonNummerverwijzingHistorie> persoonNummerverwijzingHistorieSet = new LinkedHashSet<>(0);
    @IndicatieActueelEnGeldig(naam = "isActueelVoorOverlijden")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private final Set<PersoonOverlijdenHistorie> persoonOverlijdenHistorieSet = new LinkedHashSet<>(0);
    @IndicatieActueelEnGeldig(naam = "isActueelVoorPersoonskaart")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private final Set<PersoonPersoonskaartHistorie> persoonPersoonskaartHistorieSet = new LinkedHashSet<>(0);
    @IndicatieActueelEnGeldig(naam = "isActueelVoorVerblijfsrecht")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private final Set<PersoonVerblijfsrechtHistorie> persoonVerblijfsrechtHistorieSet = new LinkedHashSet<>(0);
    @IndicatieActueelEnGeldig(naam = "isActueelVoorUitsluitingkiesrecht")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private final Set<PersoonUitsluitingKiesrechtHistorie> persoonUitsluitingKiesrechtHistorieSet = new LinkedHashSet<>();
    @IndicatieActueelEnGeldig(naam = "isActueelVoorAfgeleidadministratief")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private final Set<PersoonAfgeleidAdministratiefHistorie> persoonAfgeleidAdministratiefHistorieSet = new LinkedHashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private final Set<Lo3Bericht> lo3Berichten = new LinkedHashSet<>(0);

    // bi-directional many-to-one association to Betrokkenheid
    /* Bij wijziging veld-naam ook de string constante daarvoor aanpassen */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private final Set<Betrokkenheid> betrokkenheidSet = new LinkedHashSet<>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private final Set<Stapel> stapels = new LinkedHashSet<>(0);

    /**
     * JPA default constructor.
     */
    protected Persoon() {
    }

    /**
     * Maakt een Persoon object.
     * @param soortPersoon het soort persoon
     */
    public Persoon(final SoortPersoon soortPersoon) {
        setSoortPersoon(soortPersoon);
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
     * Zet de waarden voor id van Persoon.
     * @param id de nieuwe waarde voor id van Persoon
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de optimistic locking versie (wordt automatisch bijgewerkt door Hibernate).
     * @return optimistic locking versie
     */
    public Long getLockVersie() {
        return lockVersie;
    }

    /**
     * Geef de waarde van administratienummer van Persoon.
     * @return de waarde van administratienummer van Persoon
     */
    public String getAdministratienummer() {
        return administratienummer;
    }

    /**
     * Zet de waarden voor administratienummer van Persoon.
     * @param administratienummer de nieuwe waarde voor administratienummer van Persoon
     */
    public void setAdministratienummer(final String administratienummer) {
        if (administratienummer != null) {
            ValidationUtils.controleerOpLengte("administratienummer moet een lengte van 10 hebben", administratienummer, ANUMMER_LENGTE);
        }
        this.administratienummer = administratienummer;
    }

    /**
     * Geef de waarde van administratieve handeling van Persoon.
     * @return de waarde van administratieve handeling van Persoon
     */
    public AdministratieveHandeling getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    /**
     * Zet de waarden voor administratieve handeling van Persoon.
     * @param administratieveHandeling de nieuwe waarde voor administratieve handeling van Persoon
     */
    public void setAdministratieveHandeling(final AdministratieveHandeling administratieveHandeling) {
        this.administratieveHandeling = administratieveHandeling;
    }

    /**
     * Geef de waarde van sorteervolgorde van Persoon.
     * @return de waarde van sorteervolgorde van Persoon
     */
    public Short getSorteervolgorde() {
        return sorteervolgorde;
    }

    /**
     * Zet de waarden voor sorteervolgorde van Persoon.
     * @param sorteervolgorde de nieuwe waarde voor sorteervolgorde van Persoon
     */
    public void setSorteervolgorde(final Short sorteervolgorde) {
        this.sorteervolgorde = sorteervolgorde;
    }

    /**
     * Geef de waarde van buitenlands adres regel1 migratie van Persoon.
     * @return de waarde van buitenlands adres regel1 migratie van Persoon
     */
    public String getBuitenlandsAdresRegel1Migratie() {
        return buitenlandsAdresRegel1Migratie;
    }

    /**
     * Zet de waarden voor buitenlands adres regel1 migratie van Persoon.
     * @param buitenlandsAdresRegel1Migratie de nieuwe waarde voor buitenlands adres regel1 migratie van Persoon
     */
    public void setBuitenlandsAdresRegel1Migratie(final String buitenlandsAdresRegel1Migratie) {
        ValidationUtils.controleerOpLegeWaarden("buitenlandsAdresRegel1Migratie mag geen lege string zijn", buitenlandsAdresRegel1Migratie);
        this.buitenlandsAdresRegel1Migratie = buitenlandsAdresRegel1Migratie;
    }

    /**
     * Geef de waarde van buitenlands adres regel2 migratie van Persoon.
     * @return de waarde van buitenlands adres regel2 migratie van Persoon
     */
    public String getBuitenlandsAdresRegel2Migratie() {
        return buitenlandsAdresRegel2Migratie;
    }

    /**
     * Zet de waarden voor buitenlands adres regel2 migratie van Persoon.
     * @param buitenlandsAdresRegel2Migratie de nieuwe waarde voor buitenlands adres regel2 migratie van Persoon
     */
    public void setBuitenlandsAdresRegel2Migratie(final String buitenlandsAdresRegel2Migratie) {
        ValidationUtils.controleerOpLegeWaarden("buitenlandsAdresRegel2Migratie mag geen lege string zijn", buitenlandsAdresRegel2Migratie);
        this.buitenlandsAdresRegel2Migratie = buitenlandsAdresRegel2Migratie;
    }

    /**
     * Geef de waarde van buitenlands adres regel3 migratie van Persoon.
     * @return de waarde van buitenlands adres regel3 migratie van Persoon
     */
    public String getBuitenlandsAdresRegel3Migratie() {
        return buitenlandsAdresRegel3Migratie;
    }

    /**
     * Zet de waarden voor buitenlands adres regel3 migratie van Persoon.
     * @param buitenlandsAdresRegel3Migratie de nieuwe waarde voor buitenlands adres regel3 migratie van Persoon
     */
    public void setBuitenlandsAdresRegel3Migratie(final String buitenlandsAdresRegel3Migratie) {
        ValidationUtils.controleerOpLegeWaarden("buitenlandsAdresRegel3Migratie mag geen lege string zijn", buitenlandsAdresRegel3Migratie);
        this.buitenlandsAdresRegel3Migratie = buitenlandsAdresRegel3Migratie;
    }

    /**
     * Geef de waarde van buitenlands adres regel4 migratie van Persoon.
     * @return de waarde van buitenlands adres regel4 migratie van Persoon
     */
    public String getBuitenlandsAdresRegel4Migratie() {
        return buitenlandsAdresRegel4Migratie;
    }

    /**
     * Zet de waarden voor buitenlands adres regel4 migratie van Persoon.
     * @param buitenlandsAdresRegel4Migratie de nieuwe waarde voor buitenlands adres regel4 migratie van Persoon
     */
    public void setBuitenlandsAdresRegel4Migratie(final String buitenlandsAdresRegel4Migratie) {
        ValidationUtils.controleerOpLegeWaarden("buitenlandsAdresRegel4Migratie mag geen lege string zijn", buitenlandsAdresRegel4Migratie);
        this.buitenlandsAdresRegel4Migratie = buitenlandsAdresRegel4Migratie;
    }

    /**
     * Geef de waarde van buitenlands adres regel5 migratie van Persoon.
     * @return de waarde van buitenlands adres regel5 migratie van Persoon
     */
    public String getBuitenlandsAdresRegel5Migratie() {
        return buitenlandsAdresRegel5Migratie;
    }

    /**
     * Zet de waarden voor buitenlands adres regel5 migratie van Persoon.
     * @param buitenlandsAdresRegel5Migratie de nieuwe waarde voor buitenlands adres regel5 migratie van Persoon
     */
    public void setBuitenlandsAdresRegel5Migratie(final String buitenlandsAdresRegel5Migratie) {
        ValidationUtils.controleerOpLegeWaarden("buitenlandsAdresRegel5Migratie mag geen lege string zijn", buitenlandsAdresRegel5Migratie);
        this.buitenlandsAdresRegel5Migratie = buitenlandsAdresRegel5Migratie;
    }

    /**
     * Geef de waarde van buitenlands adres regel6 migratie van Persoon.
     * @return de waarde van buitenlands adres regel6 migratie van Persoon
     */
    public String getBuitenlandsAdresRegel6Migratie() {
        return buitenlandsAdresRegel6Migratie;
    }

    /**
     * Zet de waarden voor buitenlands adres regel6 migratie van Persoon.
     * @param buitenlandsAdresRegel6Migratie de nieuwe waarde voor buitenlands adres regel6 migratie van Persoon
     */
    public void setBuitenlandsAdresRegel6Migratie(final String buitenlandsAdresRegel6Migratie) {
        ValidationUtils.controleerOpLegeWaarden("buitenlandsAdresRegel6Migratie mag geen lege string zijn", buitenlandsAdresRegel6Migratie);
        this.buitenlandsAdresRegel6Migratie = buitenlandsAdresRegel6Migratie;
    }

    /**
     * Geef de waarde van buitenlandse plaats geboorte van Persoon.
     * @return de waarde van buitenlandse plaats geboorte van Persoon
     */
    public String getBuitenlandsePlaatsGeboorte() {
        return buitenlandsePlaatsGeboorte;
    }

    /**
     * Zet de waarden voor buitenlandse plaats geboorte van Persoon.
     * @param buitenlandsePlaatsGeboorte de nieuwe waarde voor buitenlandse plaats geboorte van Persoon
     */
    public void setBuitenlandsePlaatsGeboorte(final String buitenlandsePlaatsGeboorte) {
        ValidationUtils.controleerOpLegeWaarden("buitenlandsePlaatsGeboorte mag geen lege string zijn", buitenlandsePlaatsGeboorte);
        this.buitenlandsePlaatsGeboorte = buitenlandsePlaatsGeboorte;
    }

    /**
     * Geef de waarde van buitenlandse plaats overlijden van Persoon.
     * @return de waarde van buitenlandse plaats overlijden van Persoon
     */
    public String getBuitenlandsePlaatsOverlijden() {
        return buitenlandsePlaatsOverlijden;
    }

    /**
     * Zet de waarden voor buitenlandse plaats overlijden van Persoon.
     * @param buitenlandsePlaatsOverlijden de nieuwe waarde voor buitenlandse plaats overlijden van Persoon
     */
    public void setBuitenlandsePlaatsOverlijden(final String buitenlandsePlaatsOverlijden) {
        ValidationUtils.controleerOpLegeWaarden("buitenlandsePlaatsOverlijden mag geen lege string zijn", buitenlandsePlaatsOverlijden);
        this.buitenlandsePlaatsOverlijden = buitenlandsePlaatsOverlijden;
    }

    /**
     * Geef de waarde van buitenlandse regio geboorte van Persoon.
     * @return de waarde van buitenlandse regio geboorte van Persoon
     */
    public String getBuitenlandseRegioGeboorte() {
        return buitenlandseRegioGeboorte;
    }

    /**
     * Zet de waarden voor buitenlandse regio geboorte van Persoon.
     * @param buitenlandseRegioGeboorte de nieuwe waarde voor buitenlandse regio geboorte van Persoon
     */
    public void setBuitenlandseRegioGeboorte(final String buitenlandseRegioGeboorte) {
        ValidationUtils.controleerOpLegeWaarden("buitenlandseRegioGeboorte mag geen lege string zijn", buitenlandseRegioGeboorte);
        this.buitenlandseRegioGeboorte = buitenlandseRegioGeboorte;
    }

    /**
     * Geef de waarde van buitenlandse regio overlijden van Persoon.
     * @return de waarde van buitenlandse regio overlijden van Persoon
     */
    public String getBuitenlandseRegioOverlijden() {
        return buitenlandseRegioOverlijden;
    }

    /**
     * Zet de waarden voor buitenlandse regio overlijden van Persoon.
     * @param buitenlandseRegioOverlijden de nieuwe waarde voor buitenlandse regio overlijden van Persoon
     */
    public void setBuitenlandseRegioOverlijden(final String buitenlandseRegioOverlijden) {
        ValidationUtils.controleerOpLegeWaarden("buitenlandseRegioOverlijden mag geen lege string zijn", buitenlandseRegioOverlijden);
        this.buitenlandseRegioOverlijden = buitenlandseRegioOverlijden;
    }

    /**
     * Geef de waarde van burgerservicenummer van Persoon.
     * @return de waarde van burgerservicenummer van Persoon
     */
    public String getBurgerservicenummer() {
        return burgerservicenummer;
    }

    /**
     * Zet de waarden voor burgerservicenummer van Persoon.
     * @param burgerservicenummer de nieuwe waarde voor burgerservicenummer van Persoon
     */
    public void setBurgerservicenummer(final String burgerservicenummer) {
        if (burgerservicenummer != null) {
            ValidationUtils.controleerOpLengte("burgerservicenummer moet een lengte van 9 hebben", burgerservicenummer, BSN_LENGTE);
        }
        this.burgerservicenummer = burgerservicenummer;
    }

    /**
     * Geef de waarde van datum aanleiding aanpassing deelname eu verkiezing van Persoon.
     * @return de waarde van datum aanleiding aanpassing deelname eu verkiezing van Persoon
     */
    public Integer getDatumAanleidingAanpassingDeelnameEuVerkiezing() {
        return datumAanleidingAanpassingDeelnameEuVerkiezingen;
    }

    /**
     * Zet de waarden voor datum aanleiding aanpassing deelname eu verkiezingen van Persoon.
     * @param datumAanleidingAanpassingDeelnameEUVerkiezing de nieuwe waarde voor datum aanleiding aanpassing deelname eu verkiezingen van Persoon
     */
    public void setDatumAanleidingAanpassingDeelnameEuVerkiezingen(final Integer datumAanleidingAanpassingDeelnameEUVerkiezing) {
        datumAanleidingAanpassingDeelnameEuVerkiezingen = datumAanleidingAanpassingDeelnameEUVerkiezing;
    }

    /**
     * Geef de waarde van datum aanvang verblijfsrecht van Persoon.
     * @return de waarde van datum aanvang verblijfsrecht van Persoon
     */
    public Integer getDatumAanvangVerblijfsrecht() {
        return datumAanvangVerblijfsrecht;
    }

    /**
     * Zet de waarden voor datum aanvang verblijfsrecht van Persoon.
     * @param datumAanvangVerblijfsrecht de nieuwe waarde voor datum aanvang verblijfsrecht van Persoon
     */
    public void setDatumAanvangVerblijfsrecht(final Integer datumAanvangVerblijfsrecht) {
        this.datumAanvangVerblijfsrecht = datumAanvangVerblijfsrecht;
    }

    /**
     * Geef de waarde van datum mededeling verblijfsrecht van Persoon.
     * @return de waarde van datum mededeling verblijfsrecht van Persoon
     */
    public Integer getDatumMededelingVerblijfsrecht() {
        return datumMededelingVerblijfsrecht;
    }

    /**
     * Zet de waarden voor datum mededeling verblijfsrecht van Persoon.
     * @param datumMededelingVerblijfsrecht de nieuwe waarde voor datum mededeling verblijfsrecht van Persoon
     */
    public void setDatumMededelingVerblijfsrecht(final Integer datumMededelingVerblijfsrecht) {
        this.datumMededelingVerblijfsrecht = datumMededelingVerblijfsrecht;
    }

    /**
     * Geef de waarde van datum voorzien einde uitsluiting eu verkiezingen van Persoon.
     * @return de waarde van datum voorzien einde uitsluiting eu verkiezingen van Persoon
     */
    public Integer getDatumVoorzienEindeUitsluitingEuVerkiezingen() {
        return datumVoorzienEindeUitsluitingEuVerkiezingen;
    }

    /**
     * Zet de waarden voor datum voorzien einde uitsluiting eu verkiezingen van Persoon.
     * @param datumEindeUitsluitingEUKiesrecht de nieuwe waarde voor datum voorzien einde uitsluiting eu verkiezingen van Persoon
     */
    public void setDatumVoorzienEindeUitsluitingEuVerkiezingen(final Integer datumEindeUitsluitingEUKiesrecht) {
        datumVoorzienEindeUitsluitingEuVerkiezingen = datumEindeUitsluitingEUKiesrecht;
    }

    /**
     * Geef de waarde van datum voorzien einde uitsluiting kiesrecht van Persoon.
     * @return de waarde van datum voorzien einde uitsluiting kiesrecht van Persoon
     */
    public Integer getDatumVoorzienEindeUitsluitingKiesrecht() {
        return datumVoorzienEindeUitsluitingKiesrecht;
    }

    /**
     * Zet de waarden voor datum voorzien einde uitsluiting kiesrecht van Persoon.
     * @param datumEindeUitsluitingKiesrecht de nieuwe waarde voor datum voorzien einde uitsluiting kiesrecht van Persoon
     */
    public void setDatumVoorzienEindeUitsluitingKiesrecht(final Integer datumEindeUitsluitingKiesrecht) {
        datumVoorzienEindeUitsluitingKiesrecht = datumEindeUitsluitingKiesrecht;
    }

    /**
     * Geef de waarde van datum geboorte van Persoon.
     * @return de waarde van datum geboorte van Persoon
     */
    public Integer getDatumGeboorte() {
        return datumGeboorte;
    }

    /**
     * Zet de waarden voor datum geboorte van Persoon.
     * @param datumGeboorte de nieuwe waarde voor datum geboorte van Persoon
     */
    public void setDatumGeboorte(final Integer datumGeboorte) {
        this.datumGeboorte = datumGeboorte;
    }

    /**
     * Geef de waarde van datum inschrijving van Persoon.
     * @return de waarde van datum inschrijving van Persoon
     */
    public Integer getDatumInschrijving() {
        return datumInschrijving;
    }

    /**
     * Zet de waarden voor datum inschrijving van Persoon.
     * @param datumInschrijving de nieuwe waarde voor datum inschrijving van Persoon
     */
    public void setDatumInschrijving(final Integer datumInschrijving) {
        this.datumInschrijving = datumInschrijving;
    }

    /**
     * Geef de waarde van datum overlijden van Persoon.
     * @return de waarde van datum overlijden van Persoon
     */
    public Integer getDatumOverlijden() {
        return datumOverlijden;
    }

    /**
     * Zet de waarden voor datum overlijden van Persoon.
     * @param datumOverlijden de nieuwe waarde voor datum overlijden van Persoon
     */
    public void setDatumOverlijden(final Integer datumOverlijden) {
        this.datumOverlijden = datumOverlijden;
    }

    /**
     * Geef de waarde van datum voorzien einde verblijfsrecht van Persoon.
     * @return de waarde van datum voorzien einde verblijfsrecht van Persoon
     */
    public Integer getDatumVoorzienEindeVerblijfsrecht() {
        return datumVoorzienEindeVerblijfsrecht;
    }

    /**
     * Zet de waarden voor datum voorzien einde verblijfsrecht van Persoon.
     * @param datumVoorzienEindeVerblijfsrecht de nieuwe waarde voor datum voorzien einde verblijfsrecht van Persoon
     */
    public void setDatumVoorzienEindeVerblijfsrecht(final Integer datumVoorzienEindeVerblijfsrecht) {
        this.datumVoorzienEindeVerblijfsrecht = datumVoorzienEindeVerblijfsrecht;
    }

    /**
     * Geef de waarde van geslachtsnaamstam van Persoon.
     * @return de waarde van geslachtsnaamstam van Persoon
     */
    public String getGeslachtsnaamstam() {
        return geslachtsnaamstam;
    }

    /**
     * Zet de waarden voor geslachtsnaamstam van Persoon.
     * @param geslachtsnaamstam de nieuwe waarde voor geslachtsnaamstam van Persoon
     */
    public void setGeslachtsnaamstam(final String geslachtsnaamstam) {
        ValidationUtils.controleerOpLegeWaarden("geslachtsnaamstam mag geen lege string zijn", geslachtsnaamstam);
        this.geslachtsnaamstam = geslachtsnaamstam;
    }

    /**
     * Geef de waarde van geslachtsnaamstam naamgebruik van Persoon.
     * @return de waarde van geslachtsnaamstam naamgebruik van Persoon
     */
    public String getGeslachtsnaamstamNaamgebruik() {
        return geslachtsnaamstamNaamgebruik;
    }

    /**
     * Zet de waarden voor geslachtsnaamstam naamgebruik van Persoon.
     * @param geslachtsnaamstamNaamgebruik de nieuwe waarde voor geslachtsnaamstam naamgebruik van Persoon
     */
    public void setGeslachtsnaamstamNaamgebruik(final String geslachtsnaamstamNaamgebruik) {
        ValidationUtils.controleerOpLegeWaarden("geslachtsnaamstamNaamgebruik mag geen lege string zijn", geslachtsnaamstamNaamgebruik);
        this.geslachtsnaamstamNaamgebruik = geslachtsnaamstamNaamgebruik;
    }

    /**
     * Geef de waarde van indicatie naamgebruik afgeleid van Persoon.
     * @return de waarde van indicatie naamgebruik afgeleid van Persoon
     */
    public Boolean getIndicatieNaamgebruikAfgeleid() {
        return indicatieNaamgebruikAfgeleid;
    }

    /**
     * Zet de waarden voor indicatie naamgebruik afgeleid van Persoon.
     * @param indicatieNaamgebruikAfgeleid de nieuwe waarde voor indicatie naamgebruik afgeleid van Persoon
     */
    public void setIndicatieNaamgebruikAfgeleid(final Boolean indicatieNaamgebruikAfgeleid) {
        this.indicatieNaamgebruikAfgeleid = indicatieNaamgebruikAfgeleid;
    }

    /**
     * Geef de waarde van indicatie afgeleid van Persoon.
     * @return de waarde van indicatie afgeleid van Persoon
     */
    public Boolean getIndicatieAfgeleid() {
        return indicatieAfgeleid;
    }

    /**
     * Zet de waarden voor indicatie afgeleid van Persoon.
     * @param indicatieAfgeleid de nieuwe waarde voor indicatie afgeleid van Persoon
     */
    public void setIndicatieAfgeleid(final Boolean indicatieAfgeleid) {
        this.indicatieAfgeleid = indicatieAfgeleid;
    }

    /**
     * Geef de waarde van indicatie deelname eu verkiezingen van Persoon.
     * @return de waarde van indicatie deelname eu verkiezingen van Persoon
     */
    public Boolean getIndicatieDeelnameEuVerkiezingen() {
        return indicatieDeelnameEuVerkiezingen;
    }

    /**
     * Zet de waarden voor indicatie deelname eu verkiezingen van Persoon.
     * @param indicatieDeelnameEuVerkiezingen de nieuwe waarde voor indicatie deelname eu verkiezingen van Persoon
     */
    public void setIndicatieDeelnameEuVerkiezingen(final Boolean indicatieDeelnameEuVerkiezingen) {
        this.indicatieDeelnameEuVerkiezingen = indicatieDeelnameEuVerkiezingen;
    }

    /**
     * Geef de waarde van indicatie namenreeks van Persoon.
     * @return de waarde van indicatie namenreeks van Persoon
     */
    public Boolean getIndicatieNamenreeks() {
        return indicatieNamenreeks;
    }

    /**
     * Zet de waarden voor indicatie namenreeks van Persoon.
     * @param indicatieNamenreeks de nieuwe waarde voor indicatie namenreeks van Persoon
     */
    public void setIndicatieNamenreeks(final Boolean indicatieNamenreeks) {
        this.indicatieNamenreeks = indicatieNamenreeks;
    }

    /**
     * Geef de waarde van indicatie persoonskaart volledig geconverteerd van Persoon.
     * @return de waarde van indicatie persoonskaart volledig geconverteerd van Persoon
     */
    public Boolean getIndicatiePersoonskaartVolledigGeconverteerd() {
        return indicatiePersoonskaartVolledigGeconverteerd;
    }

    /**
     * Zet de waarden voor indicatie persoonskaart volledig geconverteerd van Persoon.
     * @param indicatiePersoonskaartVolledigGeconverteerd de nieuwe waarde voor indicatie persoonskaart volledig geconverteerd van Persoon
     */
    public void setIndicatiePersoonskaartVolledigGeconverteerd(final Boolean indicatiePersoonskaartVolledigGeconverteerd) {
        this.indicatiePersoonskaartVolledigGeconverteerd = indicatiePersoonskaartVolledigGeconverteerd;
    }

    /**
     * Geef de waarde van indicatie uitsluiting kiesrecht van Persoon.
     * @return de waarde van indicatie uitsluiting kiesrecht van Persoon
     */
    public Boolean getIndicatieUitsluitingKiesrecht() {
        return indicatieUitsluitingKiesrecht;
    }

    /**
     * Zet de waarden voor indicatie uitsluiting kiesrecht van Persoon.
     * @param indicatieUitsluitingKiesrecht de nieuwe waarde voor indicatie uitsluiting kiesrecht van Persoon
     */
    public void setIndicatieUitsluitingKiesrecht(final Boolean indicatieUitsluitingKiesrecht) {
        this.indicatieUitsluitingKiesrecht = indicatieUitsluitingKiesrecht;
    }

    /**
     * Geef de waarde van omschrijving geboortelocatie van Persoon.
     * @return de waarde van omschrijving geboortelocatie van Persoon
     */
    public String getOmschrijvingGeboortelocatie() {
        return omschrijvingGeboortelocatie;
    }

    /**
     * Zet de waarden voor omschrijving geboortelocatie van Persoon.
     * @param omschrijvingGeboortelocatie de nieuwe waarde voor omschrijving geboortelocatie van Persoon
     */
    public void setOmschrijvingGeboortelocatie(final String omschrijvingGeboortelocatie) {
        ValidationUtils.controleerOpLegeWaarden("omschrijvingGeboortelocatie mag geen lege string zijn", omschrijvingGeboortelocatie);
        this.omschrijvingGeboortelocatie = omschrijvingGeboortelocatie;
    }

    /**
     * Geef de waarde van omschrijving locatie overlijden van Persoon.
     * @return de waarde van omschrijving locatie overlijden van Persoon
     */
    public String getOmschrijvingLocatieOverlijden() {
        return omschrijvingLocatieOverlijden;
    }

    /**
     * Zet de waarden voor omschrijving locatie overlijden van Persoon.
     * @param omschrijvingLocatieOverlijden de nieuwe waarde voor omschrijving locatie overlijden van Persoon
     */
    public void setOmschrijvingLocatieOverlijden(final String omschrijvingLocatieOverlijden) {
        ValidationUtils.controleerOpLegeWaarden("omschrijvingLocatieOverlijden mag geen lege string zijn", omschrijvingLocatieOverlijden);
        this.omschrijvingLocatieOverlijden = omschrijvingLocatieOverlijden;
    }

    /**
     * Geef de waarde van scheidingsteken van Persoon.
     * @return de waarde van scheidingsteken van Persoon
     */
    public Character getScheidingsteken() {
        return scheidingsteken;
    }

    /**
     * Zet de waarden voor scheidingsteken van Persoon.
     * @param scheidingsteken de nieuwe waarde voor scheidingsteken van Persoon
     */
    public void setScheidingsteken(final Character scheidingsteken) {
        this.scheidingsteken = scheidingsteken;
    }

    /**
     * Geef de waarde van scheidingsteken naamgebruik van Persoon.
     * @return de waarde van scheidingsteken naamgebruik van Persoon
     */
    public Character getScheidingstekenNaamgebruik() {
        return scheidingstekenNaamgebruik;
    }

    /**
     * Zet de waarden voor scheidingsteken naamgebruik van Persoon.
     * @param scheidingstekenNaamgebruik de nieuwe waarde voor scheidingsteken naamgebruik van Persoon
     */
    public void setScheidingstekenNaamgebruik(final Character scheidingstekenNaamgebruik) {
        this.scheidingstekenNaamgebruik = scheidingstekenNaamgebruik;
    }

    /**
     * Geef de waarde van tijdstip laatste wijziging van Persoon.
     * @return de waarde van tijdstip laatste wijziging van Persoon
     */
    public Timestamp getTijdstipLaatsteWijziging() {
        return Entiteit.timestamp(tijdstipLaatsteWijziging);
    }

    /**
     * Zet de waarden voor tijdstip laatste wijziging van Persoon.
     * @param tijdstipLaatsteWijziging de nieuwe waarde voor tijdstip laatste wijziging van Persoon
     */
    public void setTijdstipLaatsteWijziging(final Timestamp tijdstipLaatsteWijziging) {
        this.tijdstipLaatsteWijziging = Entiteit.timestamp(tijdstipLaatsteWijziging);
    }

    /**
     * Geef de waarde van tijdstip laatste wijziging gba systematiek van Persoon.
     * @return de waarde van tijdstip laatste wijziging gba systematiek van Persoon
     */
    public Timestamp getTijdstipLaatsteWijzigingGbaSystematiek() {
        return Entiteit.timestamp(tijdstipLaatsteWijzigingGbaSystematiek);
    }

    /**
     * Zet de waarden voor tijdstip laatste wijziging gba systematiek van Persoon.
     * @param tijdstipLaatsteWijzigingGbaSystematiek de nieuwe waarde voor tijdstip laatste wijziging gba systematiek van Persoon
     */
    public void setTijdstipLaatsteWijzigingGbaSystematiek(final Timestamp tijdstipLaatsteWijzigingGbaSystematiek) {
        this.tijdstipLaatsteWijzigingGbaSystematiek = Entiteit.timestamp(tijdstipLaatsteWijzigingGbaSystematiek);
    }

    /**
     * Geef de waarde van versienummer van Persoon.
     * @return de waarde van versienummer van Persoon
     */
    public Long getVersienummer() {
        return versienummer;
    }

    /**
     * Zet de waarden voor versienummer van Persoon.
     * @param versienummer de nieuwe waarde voor versienummer van Persoon
     */
    public void setVersienummer(final Long versienummer) {
        this.versienummer = versienummer;
    }

    /**
     * Geef de waarde van datumtijdstempel van Persoon.
     * @return de waarde van datumtijdstempel van Persoon
     */
    public Timestamp getDatumtijdstempel() {
        return Entiteit.timestamp(datumtijdstempel);
    }

    /**
     * Zet de waarden voor datumtijdstempel van Persoon.
     * @param datumtijdstempel de nieuwe waarde voor datumtijdstempel van Persoon
     */
    public void setDatumtijdstempel(final Timestamp datumtijdstempel) {
        this.datumtijdstempel = Entiteit.timestamp(datumtijdstempel);
    }

    /**
     * Geef de waarde van voornamen van Persoon.
     * @return de waarde van voornamen van Persoon
     */
    public String getVoornamen() {
        return voornamen;
    }

    /**
     * Zet de waarden voor voornamen van Persoon.
     * @param voornamen de nieuwe waarde voor voornamen van Persoon
     */
    public void setVoornamen(final String voornamen) {
        ValidationUtils.controleerOpLegeWaarden("voornamen mag geen lege string zijn", voornamen);
        this.voornamen = voornamen;
    }

    /**
     * Geef de waarde van voornamen naamgebruik van Persoon.
     * @return de waarde van voornamen naamgebruik van Persoon
     */
    public String getVoornamenNaamgebruik() {
        return voornamenNaamgebruik;
    }

    /**
     * Zet de waarden voor voornamen naamgebruik van Persoon.
     * @param voornamenNaamgebruik de nieuwe waarde voor voornamen naamgebruik van Persoon
     */
    public void setVoornamenNaamgebruik(final String voornamenNaamgebruik) {
        ValidationUtils.controleerOpLegeWaarden("voornamenNaamgebruik mag geen lege string zijn", voornamenNaamgebruik);
        this.voornamenNaamgebruik = voornamenNaamgebruik;
    }

    /**
     * Geef de waarde van adellijke titel naamgebruik van Persoon.
     * @return de waarde van adellijke titel naamgebruik van Persoon
     */
    public AdellijkeTitel getAdellijkeTitelNaamgebruik() {
        return AdellijkeTitel.parseId(adellijkeTitelNaamgebruikId);
    }

    /**
     * Zet de waarden voor adellijke titel naamgebruik van Persoon.
     * @param naamgebruikTitel de nieuwe waarde voor adellijke titel naamgebruik van Persoon
     */
    public void setAdellijkeTitelNaamgebruik(final AdellijkeTitel naamgebruikTitel) {
        if (naamgebruikTitel == null) {
            adellijkeTitelNaamgebruikId = null;
        } else {
            adellijkeTitelNaamgebruikId = naamgebruikTitel.getId();
        }
    }

    /**
     * Geef de waarde van soort migratie van Persoon.
     * @return de waarde van soort migratie van Persoon
     */
    public SoortMigratie getSoortMigratie() {
        return SoortMigratie.parseId(soortMigratieId);
    }

    /**
     * Zet de waarden voor soort migratie van Persoon.
     * @param soortMigratie de nieuwe waarde voor soort migratie van Persoon
     */
    public void setSoortMigratie(final SoortMigratie soortMigratie) {
        if (soortMigratie == null) {
            soortMigratieId = null;
        } else {
            soortMigratieId = soortMigratie.getId();
        }
    }

    /**
     * Geef de waarde van reden wijziging migratie van Persoon.
     * @return de waarde van reden wijziging migratie van Persoon
     */
    public RedenWijzigingVerblijf getRedenWijzigingMigratie() {
        return redenWijzigingMigratie;
    }

    /**
     * Zet de waarden voor reden wijziging migratie van Persoon.
     * @param redenWijzigingMigratie de nieuwe waarde voor reden wijziging migratie van Persoon
     */
    public void setRedenWijzigingMigratie(final RedenWijzigingVerblijf redenWijzigingMigratie) {
        this.redenWijzigingMigratie = redenWijzigingMigratie;
    }

    /**
     * Geef de waarde van aangever migratie van Persoon.
     * @return de waarde van aangever migratie van Persoon
     */
    public Aangever getAangeverMigratie() {
        return aangeverMigratie;
    }

    /**
     * Zet de waarden voor aangever migratie van Persoon.
     * @param aangeverMigratie de nieuwe waarde voor aangever migratie van Persoon
     */
    public void setAangeverMigratie(final Aangever aangeverMigratie) {
        this.aangeverMigratie = aangeverMigratie;
    }

    /**
     * Geef de waarde van voorvoegsel van Persoon.
     * @return de waarde van voorvoegsel van Persoon
     */
    public String getVoorvoegsel() {
        return voorvoegsel;
    }

    /**
     * Zet de waarden voor voorvoegsel van Persoon.
     * @param voorvoegsel de nieuwe waarde voor voorvoegsel van Persoon
     */
    public void setVoorvoegsel(final String voorvoegsel) {
        ValidationUtils.controleerOpLegeWaarden("voorvoegsel mag geen lege string zijn", voorvoegsel);
        this.voorvoegsel = voorvoegsel;
    }

    /**
     * Geef de waarde van voorvoegsel naamgebruik van Persoon.
     * @return de waarde van voorvoegsel naamgebruik van Persoon
     */
    public String getVoorvoegselNaamgebruik() {
        return voorvoegselNaamgebruik;
    }

    /**
     * Zet de waarden voor voorvoegsel naamgebruik van Persoon.
     * @param voorvoegselNaamgebruik de nieuwe waarde voor voorvoegsel naamgebruik van Persoon
     */
    public void setVoorvoegselNaamgebruik(final String voorvoegselNaamgebruik) {
        ValidationUtils.controleerOpLegeWaarden("voorvoegselNaamgebruik mag geen lege string zijn", voorvoegselNaamgebruik);
        this.voorvoegselNaamgebruik = voorvoegselNaamgebruik;
    }

    /**
     * Geef de waarde van geslachtsaanduiding van Persoon.
     * @return de waarde van geslachtsaanduiding van Persoon
     */
    public Geslachtsaanduiding getGeslachtsaanduiding() {
        return Geslachtsaanduiding.parseId(geslachtsaanduidingId);
    }

    /**
     * Zet de waarden voor geslachtsaanduiding van Persoon.
     * @param geslachtsaanduiding de nieuwe waarde voor geslachtsaanduiding van Persoon
     */
    public void setGeslachtsaanduiding(final Geslachtsaanduiding geslachtsaanduiding) {
        if (geslachtsaanduiding == null) {
            geslachtsaanduidingId = null;
        } else {
            geslachtsaanduidingId = geslachtsaanduiding.getId();
        }
    }

    /**
     * Geef de waarde van land of gebied geboorte van Persoon.
     * @return de waarde van land of gebied geboorte van Persoon
     */
    public LandOfGebied getLandOfGebiedGeboorte() {
        return landOfGebiedGeboorte;
    }

    /**
     * Zet de waarden voor land of gebied geboorte van Persoon.
     * @param landOfGebiedGeboorte de nieuwe waarde voor land of gebied geboorte van Persoon
     */
    public void setLandOfGebiedGeboorte(final LandOfGebied landOfGebiedGeboorte) {
        this.landOfGebiedGeboorte = landOfGebiedGeboorte;
    }

    /**
     * Geef de waarde van land of gebied migratie van Persoon.
     * @return de waarde van land of gebied migratie van Persoon
     */
    public LandOfGebied getLandOfGebiedMigratie() {
        return landOfGebiedMigratie;
    }

    /**
     * Zet de waarden voor land of gebied migratie van Persoon.
     * @param landOfGebiedMigratie de nieuwe waarde voor land of gebied migratie van Persoon
     */
    public void setLandOfGebiedMigratie(final LandOfGebied landOfGebiedMigratie) {
        this.landOfGebiedMigratie = landOfGebiedMigratie;
    }

    /**
     * Geef de waarde van land of gebied overlijden van Persoon.
     * @return de waarde van land of gebied overlijden van Persoon
     */
    public LandOfGebied getLandOfGebiedOverlijden() {
        return landOfGebiedOverlijden;
    }

    /**
     * Zet de waarden voor land of gebied overlijden van Persoon.
     * @param landOfGebiedOverlijden de nieuwe waarde voor land of gebied overlijden van Persoon
     */
    public void setLandOfGebiedOverlijden(final LandOfGebied landOfGebiedOverlijden) {
        this.landOfGebiedOverlijden = landOfGebiedOverlijden;
    }

    /**
     * Geef de waarde van gemeente persoonskaart van Persoon.
     * @return de waarde van gemeente persoonskaart van Persoon
     */
    public Partij getGemeentePersoonskaart() {
        return gemeentePersoonskaart;
    }

    /**
     * Zet de waarden voor gemeente persoonskaart van Persoon.
     * @param gemeentePersoonskaart de nieuwe waarde voor gemeente persoonskaart van Persoon
     */
    public void setGemeentePersoonskaart(final Partij gemeentePersoonskaart) {
        this.gemeentePersoonskaart = gemeentePersoonskaart;
    }

    /**
     * Geef de waarde van gemeente overlijden van Persoon.
     * @return de waarde van gemeente overlijden van Persoon
     */
    public Gemeente getGemeenteOverlijden() {
        return gemeenteOverlijden;
    }

    /**
     * Zet de waarden voor gemeente overlijden van Persoon.
     * @param gemeenteOverlijden de nieuwe waarde voor gemeente overlijden van Persoon
     */
    public void setGemeenteOverlijden(final Gemeente gemeenteOverlijden) {
        this.gemeenteOverlijden = gemeenteOverlijden;
    }

    /**
     * Geef de waarde van gemeente geboorte van Persoon.
     * @return de waarde van gemeente geboorte van Persoon
     */
    public Gemeente getGemeenteGeboorte() {
        return gemeenteGeboorte;
    }

    /**
     * Zet de waarden voor gemeente geboorte van Persoon.
     * @param gemeenteGeboorte de nieuwe waarde voor gemeente geboorte van Persoon
     */
    public void setGemeenteGeboorte(final Gemeente gemeenteGeboorte) {
        this.gemeenteGeboorte = gemeenteGeboorte;
    }

    /**
     * Geef de waarde van bijhoudingspartij van Persoon.
     * @return de waarde van bijhoudingspartij van Persoon
     */
    public Partij getBijhoudingspartij() {
        return bijhoudingspartij;
    }

    /**
     * Zet de waarden voor bijhoudingspartij van Persoon.
     * @param bijhoudingspartij de nieuwe waarde voor bijhoudingspartij van Persoon
     */
    public void setBijhoudingspartij(final Partij bijhoudingspartij) {
        this.bijhoudingspartij = bijhoudingspartij;
    }

    /**
     * Geef de waarde van volgende administratienummer van Persoon.
     * @return de waarde van volgende administratienummer van Persoon
     */
    public String getVolgendeAdministratienummer() {
        return volgendeAdministratienummer;
    }

    /**
     * Zet de waarden voor volgende administratienummer van Persoon.
     * @param volgendeAdministratienummer de nieuwe waarde voor volgende administratienummer van Persoon
     */
    public void setVolgendeAdministratienummer(final String volgendeAdministratienummer) {
        if (volgendeAdministratienummer != null) {
            ValidationUtils.controleerOpLengte("volgendeAdministratienummer moet een lengte van 10 hebben", volgendeAdministratienummer, ANUMMER_LENGTE);
        }
        this.volgendeAdministratienummer = volgendeAdministratienummer;
    }

    /**
     * Geef de waarde van vorige administratienummer van Persoon.
     * @return de waarde van vorige administratienummer van Persoon
     */
    public String getVorigeAdministratienummer() {
        return vorigeAdministratienummer;
    }

    /**
     * Zet de waarden voor vorige administratienummer van Persoon.
     * @param vorigeAdministratienummer de nieuwe waarde voor vorige administratienummer van Persoon
     */
    public void setVorigeAdministratienummer(final String vorigeAdministratienummer) {
        if (vorigeAdministratienummer != null) {
            ValidationUtils.controleerOpLengte("vorigeAdministratienummer moet een lengte van 10 hebben", vorigeAdministratienummer, ANUMMER_LENGTE);
        }
        this.vorigeAdministratienummer = vorigeAdministratienummer;
    }

    /**
     * Geef de waarde van volgende burgerservicenummer van Persoon.
     * @return de waarde van volgende burgerservicenummer van Persoon
     */
    public String getVolgendeBurgerservicenummer() {
        return volgendeBurgerservicenummer;
    }

    /**
     * Zet de waarden voor volgende burgerservicenummer van Persoon.
     * @param volgendeBurgerservicenummer de nieuwe waarde voor volgende burgerservicenummer van Persoon
     */
    public void setVolgendeBurgerservicenummer(final String volgendeBurgerservicenummer) {
        if (volgendeBurgerservicenummer != null) {
            ValidationUtils.controleerOpLengte("volgendeBurgerservicenummer moet een lengte van 9 hebben", volgendeBurgerservicenummer, BSN_LENGTE);
        }
        this.volgendeBurgerservicenummer = volgendeBurgerservicenummer;
    }

    /**
     * Geef de waarde van vorige burgerservicenummer van Persoon.
     * @return de waarde van vorige burgerservicenummer van Persoon
     */
    public String getVorigeBurgerservicenummer() {
        return vorigeBurgerservicenummer;
    }

    /**
     * Zet de waarden voor vorige burgerservicenummer van Persoon.
     * @param vorigeBurgerservicenummer de nieuwe waarde voor vorige burgerservicenummer van Persoon
     */
    public void setVorigeBurgerservicenummer(final String vorigeBurgerservicenummer) {
        if (vorigeBurgerservicenummer != null) {
            ValidationUtils.controleerOpLengte("vorigeBurgerservicenummer moet een lengte van 9 hebben", vorigeBurgerservicenummer, BSN_LENGTE);
        }
        this.vorigeBurgerservicenummer = vorigeBurgerservicenummer;
    }

    /**
     * Geef de waarde van woonplaats geboorte van Persoon.
     * @return de waarde van woonplaats geboorte van Persoon
     */
    public String getWoonplaatsGeboorte() {
        return woonplaatsnaamGeboorte;
    }

    /**
     * Zet de waarden voor woonplaats geboorte van Persoon.
     * @param woonplaatsGeboorte de nieuwe waarde voor woonplaats geboorte van Persoon
     */
    public void setWoonplaatsGeboorte(final String woonplaatsGeboorte) {
        woonplaatsnaamGeboorte = woonplaatsGeboorte;
    }

    /**
     * Geef de waarde van woonplaats overlijden van Persoon.
     * @return de waarde van woonplaats overlijden van Persoon
     */
    public String getWoonplaatsOverlijden() {
        return woonplaatsnaamOverlijden;
    }

    /**
     * Zet de waarden voor woonplaats overlijden van Persoon.
     * @param woonplaatsOverlijden de nieuwe waarde voor woonplaats overlijden van Persoon
     */
    public void setWoonplaatsOverlijden(final String woonplaatsOverlijden) {
        woonplaatsnaamOverlijden = woonplaatsOverlijden;
    }

    /**
     * Geef de waarde van predicaat van Persoon.
     * @return de waarde van predicaat van Persoon
     */
    public Predicaat getPredicaat() {
        return Predicaat.parseId(predicaatId);
    }

    /**
     * Zet de waarden voor predicaat van Persoon.
     * @param predicaat de nieuwe waarde voor predicaat van Persoon
     */
    public void setPredicaat(final Predicaat predicaat) {
        if (predicaat == null) {
            predicaatId = null;
        } else {
            predicaatId = predicaat.getId();
        }
    }

    /**
     * Geef de waarde van predicaat naamgebruik van Persoon.
     * @return de waarde van predicaat naamgebruik van Persoon
     */
    public Predicaat getPredicaatNaamgebruik() {
        return Predicaat.parseId(predicaatNaamgebruikId);
    }

    /**
     * Zet de waarden voor predicaat naamgebruik van Persoon.
     * @param predicaatNaamgebruik de nieuwe waarde voor predicaat naamgebruik van Persoon
     */
    public void setPredicaatNaamgebruik(final Predicaat predicaatNaamgebruik) {
        if (predicaatNaamgebruik == null) {
            predicaatNaamgebruikId = null;
        } else {
            predicaatNaamgebruikId = predicaatNaamgebruik.getId();
        }
    }

    /**
     * Geef de waarde van nadere bijhoudingsaard van Persoon.
     * @return de waarde van nadere bijhoudingsaard van Persoon
     */
    public NadereBijhoudingsaard getNadereBijhoudingsaard() {
        return NadereBijhoudingsaard.parseId(nadereBijhoudingsaardId);
    }

    /**
     * Zet de waarden voor nadere bijhoudingsaard van Persoon.
     * @param redenOpschorting de nieuwe waarde voor nadere bijhoudingsaard van Persoon
     */
    public void setNadereBijhoudingsaard(final NadereBijhoudingsaard redenOpschorting) {
        if (redenOpschorting == null) {
            nadereBijhoudingsaardId = null;
        } else {
            nadereBijhoudingsaardId = redenOpschorting.getId();
        }
    }

    /**
     * Geef de waarde van soort persoon van Persoon.
     * @return de waarde van soort persoon van Persoon
     */
    public SoortPersoon getSoortPersoon() {
        return SoortPersoon.parseId(soortPersoonId);
    }

    /**
     * Zet de waarden voor soort persoon van Persoon.
     * @param soortPersoon de nieuwe waarde voor soort persoon van Persoon
     */
    public void setSoortPersoon(final SoortPersoon soortPersoon) {
        ValidationUtils.controleerOpNullWaarden("soortPersoon mag niet null zijn", soortPersoon);
        soortPersoonId = soortPersoon.getId();
    }

    public boolean isPersoonIngeschrevene() {
        return SoortPersoon.INGESCHREVENE.equals(getSoortPersoon());
    }

    /**
     * Geef de waarde van verblijfsrecht van Persoon.
     * @return de waarde van verblijfsrecht van Persoon
     */
    public Verblijfsrecht getVerblijfsrecht() {
        return verblijfsrecht;
    }

    /**
     * Zet de waarden voor verblijfsrecht van Persoon.
     * @param verblijfsrecht de nieuwe waarde voor verblijfsrecht van Persoon
     */
    public void setVerblijfsrecht(final Verblijfsrecht verblijfsrecht) {
        this.verblijfsrecht = verblijfsrecht;
    }

    /**
     * Geef de waarde van naamgebruik van Persoon.
     * @return de waarde van naamgebruik van Persoon
     */
    public Naamgebruik getNaamgebruik() {
        return Naamgebruik.parseId(naamgebruikId);
    }

    /**
     * Zet de waarden voor naamgebruik van Persoon.
     * @param naamgebruik de nieuwe waarde voor naamgebruik van Persoon
     */
    public void setNaamgebruik(final Naamgebruik naamgebruik) {
        if (naamgebruik == null) {
            naamgebruikId = null;
        } else {
            naamgebruikId = naamgebruik.getId();
        }
    }

    /**
     * Geef de waarde van persoon adres set van Persoon.
     * @return de waarde van persoon adres set van Persoon
     */
    public Set<PersoonAdres> getPersoonAdresSet() {
        return persoonAdresSet;
    }

    /**
     * Toevoegen van een persoon adres.
     * @param persoonAdres persoon adres
     */
    public void addPersoonAdres(final PersoonAdres persoonAdres) {
        persoonAdres.setPersoon(this);
        persoonAdresSet.add(persoonAdres);
    }

    /**
     * Geef de waarde van persoon geslachtsnaamcomponent set van Persoon.
     * @return de waarde van persoon geslachtsnaamcomponent set van Persoon
     */
    public Set<PersoonGeslachtsnaamcomponent> getPersoonGeslachtsnaamcomponentSet() {
        return persoonGeslachtsnaamcomponentSet;
    }

    /**
     * Toevoegen van een persoon geslachtsnaamcomponent.
     * @param persoonGeslachtsnaamcomponent persoon geslachtsnaamcomponent
     */
    public void addPersoonGeslachtsnaamcomponent(final PersoonGeslachtsnaamcomponent persoonGeslachtsnaamcomponent) {
        persoonGeslachtsnaamcomponent.setPersoon(this);
        persoonGeslachtsnaamcomponentSet.add(persoonGeslachtsnaamcomponent);
    }

    /**
     * Geef de waarde van persoon indicatie set van Persoon.
     * @return de waarde van persoon indicatie set van Persoon
     */
    public Set<PersoonIndicatie> getPersoonIndicatieSet() {
        return persoonIndicatieSet;
    }

    /**
     * Toevoegen van een persoon indicatie.
     * @param persoonIndicatie persoon indicatie
     */
    public void addPersoonIndicatie(final PersoonIndicatie persoonIndicatie) {
        persoonIndicatie.setPersoon(this);
        persoonIndicatieSet.add(persoonIndicatie);
    }

    /**
     * Geef de waarde van persoon nationaliteit set van Persoon.
     * @return de waarde van persoon nationaliteit set van Persoon
     */
    public Set<PersoonNationaliteit> getPersoonNationaliteitSet() {
        return persoonNationaliteitSet;
    }

    /**
     * Toevoegen van een persoon nationaliteit.
     * @param persoonNationaliteit persoon nationaliteit
     */
    public void addPersoonNationaliteit(final PersoonNationaliteit persoonNationaliteit) {
        persoonNationaliteit.setPersoon(this);
        persoonNationaliteitSet.add(persoonNationaliteit);
    }

    /**
     * Geef de onderzoeken van deze persoon.
     * @return een lijst van {@link Onderzoek}
     */
    public Set<Onderzoek> getOnderzoeken() {
        return onderzoeken;
    }

    /**
     * Toevoegen van een onderzoek.
     * @param onderzoek onderzoek
     */
    public void addOnderzoek(final Onderzoek onderzoek) {
        onderzoek.setPersoon(this);
        onderzoeken.add(onderzoek);
    }

    /**
     * Verwijderen van een onderzoek.
     * @param onderzoek onderzoek
     * @return true, if successful
     */
    public boolean removeOnderzoek(final Onderzoek onderzoek) {
        return onderzoeken.remove(onderzoek);
    }

    /**
     * Geef de waarde van persoon reisdocument set van Persoon.
     * @return de waarde van persoon reisdocument set van Persoon
     */
    public Set<PersoonReisdocument> getPersoonReisdocumentSet() {
        return persoonReisdocumentSet;
    }

    /**
     * Toevoegen van een persoon reisdocument.
     * @param persoonReisdocument persoon reisdocument
     */
    public void addPersoonReisdocument(final PersoonReisdocument persoonReisdocument) {
        persoonReisdocument.setPersoon(this);
        persoonReisdocumentSet.add(persoonReisdocument);
    }

    /**
     * Geef de waarde van persoon verificatie set van Persoon.
     * @return de waarde van persoon verificatie set van Persoon
     */
    public Set<PersoonVerificatie> getPersoonVerificatieSet() {
        return persoonVerificatieSet;
    }

    /**
     * Toevoegen van een persoon verificatie.
     * @param persoonVerificatie persoon verificatie
     */
    public void addPersoonVerificatie(final PersoonVerificatie persoonVerificatie) {
        persoonVerificatie.setPersoon(this);
        persoonVerificatieSet.add(persoonVerificatie);
    }

    /**
     * Toevoegen van een persoon verstrekkingsbeperking.
     * @param persoonVerstrekkingsbeperking persoon verstrekkingsbeperking
     */
    public void addPersoonVerstrekkingsbeperking(final PersoonVerstrekkingsbeperking persoonVerstrekkingsbeperking) {
        persoonVerstrekkingsbeperking.setPersoon(this);
        persoonVerstrekkingsbeperkingSet.add(persoonVerstrekkingsbeperking);
    }

    /**
     * Geef de waarde van persoon verstrekkingsbeperking set van Persoon.
     * @return de waarde van persoon verstrekkingsbeperking set van Persoon
     */
    public Set<PersoonVerstrekkingsbeperking> getPersoonVerstrekkingsbeperkingSet() {
        return persoonVerstrekkingsbeperkingSet;
    }

    /**
     * Geef de waarde van persoon voornaam set van Persoon.
     * @return de waarde van persoon voornaam set van Persoon
     */
    public Set<PersoonVoornaam> getPersoonVoornaamSet() {
        return persoonVoornaamSet;
    }

    /**
     * Geeft een specifieke voornaam uit de set terug.
     * indien deze niet bestaat null.
     * @param volgnummer volgnummer
     * @return de waarde van persoon voornaam set van Persoon
     */
    public PersoonVoornaam getPersoonVoornaam(int volgnummer) {
        for (PersoonVoornaam persoonVoornaam : persoonVoornaamSet) {
            if(persoonVoornaam.getVolgnummer() == volgnummer){
                return persoonVoornaam;
            }
        }
        return null;
    }


    /**
     * Toevoegen van een persoon voornaam.
     * @param persoonVoornaam persoon voornaam
     */
    public void addPersoonVoornaam(final PersoonVoornaam persoonVoornaam) {
        persoonVoornaam.setPersoon(this);
        persoonVoornaamSet.add(persoonVoornaam);
    }

    /**
     * Geef de waarde van persoon buitenlands persoonsnummer set van Persoon.
     * @return de waarde van persoon buitenlands persoonsnummer set van Persoon
     */
    public Set<PersoonBuitenlandsPersoonsnummer> getPersoonBuitenlandsPersoonsnummerSet() {
        return persoonBuitenlandsPersoonsnummerSet;
    }

    /**
     * Toevoegen van een persoon buitenlands persoonsnummer.
     * @param persoonBuitenlandsPersoonsnummer persoon buitenlands persoonsnummer
     */
    public void addPersoonBuitenlandsPersoonsnummer(final PersoonBuitenlandsPersoonsnummer persoonBuitenlandsPersoonsnummer) {
        persoonBuitenlandsPersoonsnummer.setPersoon(this);
        persoonBuitenlandsPersoonsnummerSet.add(persoonBuitenlandsPersoonsnummer);
    }

    /**
     * Geef de waarde van adellijke titel van Persoon.
     * @return de waarde van adellijke titel van Persoon
     */
    public AdellijkeTitel getAdellijkeTitel() {
        return AdellijkeTitel.parseId(adellijkeTitelId);
    }

    /**
     * Zet de waarden voor adellijke titel van Persoon.
     * @param titel de nieuwe waarde voor adellijke titel van Persoon
     */
    public void setAdellijkeTitel(final AdellijkeTitel titel) {
        if (titel == null) {
            adellijkeTitelId = null;
        } else {
            adellijkeTitelId = titel.getId();
        }
    }

    /**
     * Geef de waarde van bijhoudingsaard van Persoon.
     * @return de waarde van bijhoudingsaard van Persoon
     */
    public Bijhoudingsaard getBijhoudingsaard() {
        if (bijhoudingsaardId == null) {
            return null;
        }
        return Bijhoudingsaard.parseId(bijhoudingsaardId);
    }

    /**
     * Zet de waarden voor bijhoudingsaard van Persoon.
     * @param bijhoudingsaard de nieuwe waarde voor bijhoudingsaard van Persoon
     */
    public void setBijhoudingsaard(final Bijhoudingsaard bijhoudingsaard) {
        if (bijhoudingsaard == null) {
            bijhoudingsaardId = null;
        } else {
            bijhoudingsaardId = bijhoudingsaard.getId();
        }
    }

    /**
     * Geef de waarde van isActueelVoorAfgeleidadministratief.
     * @return isActueelVoorAfgeleidadministratief
     */
    public boolean isActueelVoorAfgeleidadministratief() {
        return isActueelVoorAfgeleidadministratief;
    }

    /**
     * Zet de waarde van isActueelVoorAfgeleidadministratief.
     * @param actueelVoorAfgeleidadministratief isActueelVoorAfgeleidadministratief
     */
    public void setActueelVoorAfgeleidadministratief(final boolean actueelVoorAfgeleidadministratief) {
        isActueelVoorAfgeleidadministratief = actueelVoorAfgeleidadministratief;
    }

    /**
     * Geef de waarde van isActueelVoorIds.
     * @return isActueelVoorIds
     */
    public boolean isActueelVoorIds() {
        return isActueelVoorIds;
    }

    /**
     * Zet de waarde van isActueelVoorIds.
     * @param actueelVoorIds isActueelVoorIds
     */
    public void setActueelVoorIds(final boolean actueelVoorIds) {
        isActueelVoorIds = actueelVoorIds;
    }

    /**
     * Geef de waarde van isActueelVoorSamengesteldenaam.
     * @return isActueelVoorSamengesteldenaam
     */
    public boolean isActueelVoorSamengesteldenaam() {
        return isActueelVoorSamengesteldenaam;
    }

    /**
     * Zet de waarde van isActueelVoorSamengesteldenaam.
     * @param actueelVoorSamengesteldenaam isActueelVoorSamengesteldenaam
     */
    public void setActueelVoorSamengesteldenaam(final boolean actueelVoorSamengesteldenaam) {
        isActueelVoorSamengesteldenaam = actueelVoorSamengesteldenaam;
    }

    /**
     * Geef de waarde van isActueelVoorGeboorte.
     * @return isActueelVoorGeboorte
     */
    public boolean isActueelVoorGeboorte() {
        return isActueelVoorGeboorte;
    }

    /**
     * Zet de waarde van isActueelVoorGeboorte.
     * @param actueelVoorGeboorte isActueelVoorGeboorte
     */
    public void setActueelVoorGeboorte(final boolean actueelVoorGeboorte) {
        isActueelVoorGeboorte = actueelVoorGeboorte;
    }

    /**
     * Geef de waarde van isActueelVoorGeslachtsaanduiding.
     * @return isActueelVoorGeslachtsaanduiding
     */
    public boolean isActueelVoorGeslachtsaanduiding() {
        return isActueelVoorGeslachtsaanduiding;
    }

    /**
     * Zet de waarde van isActueelVoorGeslachtsaanduiding.
     * @param actueelVoorGeslachtsaanduiding isActueelVoorGeslachtsaanduiding
     */
    public void setActueelVoorGeslachtsaanduiding(final boolean actueelVoorGeslachtsaanduiding) {
        isActueelVoorGeslachtsaanduiding = actueelVoorGeslachtsaanduiding;
    }

    /**
     * Geef de waarde van isActueelVoorInschrijving.
     * @return isActueelVoorInschrijving
     */
    public boolean isActueelVoorInschrijving() {
        return isActueelVoorInschrijving;
    }

    /**
     * Zet de waarde van isActueelVoorInschrijving.
     * @param actueelVoorInschrijving isActueelVoorInschrijving
     */
    public void setActueelVoorInschrijving(final boolean actueelVoorInschrijving) {
        isActueelVoorInschrijving = actueelVoorInschrijving;
    }

    /**
     * Geef de waarde van isActueelVoorVerwijzing.
     * @return isActueelVoorVerwijzing
     */
    public boolean isActueelVoorVerwijzing() {
        return isActueelVoorVerwijzing;
    }

    /**
     * Zet de waarde van isActueelVoorVerwijzing.
     * @param actueelVoorVerwijzing isActueelVoorVerwijzing
     */
    public void setActueelVoorVerwijzing(final boolean actueelVoorVerwijzing) {
        isActueelVoorVerwijzing = actueelVoorVerwijzing;
    }

    /**
     * Geef de waarde van isActueelVoorBijhouding.
     * @return isActueelVoorBijhouding
     */
    public boolean isActueelVoorBijhouding() {
        return isActueelVoorBijhouding;
    }

    /**
     * Zet de waarde van isActueelVoorBijhouding.
     * @param actueelVoorBijhouding isActueelVoorBijhouding
     */
    public void setActueelVoorBijhouding(final boolean actueelVoorBijhouding) {
        isActueelVoorBijhouding = actueelVoorBijhouding;
    }

    /**
     * Geef de waarde van isActueelVoorOverlijden.
     * @return isActueelVoorOverlijden
     */
    public boolean isActueelVoorOverlijden() {
        return isActueelVoorOverlijden;
    }

    /**
     * Zet de waarde van isActueelVoorOverlijden.
     * @param actueelVoorOverlijden isActueelVoorOverlijden
     */
    public void setActueelVoorOverlijden(final boolean actueelVoorOverlijden) {
        isActueelVoorOverlijden = actueelVoorOverlijden;
    }

    /**
     * Geef de waarde van isActueelVoorNaamgebruik.
     * @return isActueelVoorNaamgebruik
     */
    public boolean isActueelVoorNaamgebruik() {
        return isActueelVoorNaamgebruik;
    }

    /**
     * Zet de waarde van isActueelVoorNaamgebruik.
     * @param actueelVoorNaamgebruik isActueelVoorNaamgebruik
     */
    public void setActueelVoorNaamgebruik(final boolean actueelVoorNaamgebruik) {
        isActueelVoorNaamgebruik = actueelVoorNaamgebruik;
    }

    /**
     * Geef de waarde van isActueelVoorMigratie.
     * @return isActueelVoorMigratie
     */
    public boolean isActueelVoorMigratie() {
        return isActueelVoorMigratie;
    }

    /**
     * Zet de waarde van isActueelVoorMigratie.
     * @param actueelVoorMigratie isActueelVoorMigratie
     */
    public void setActueelVoorMigratie(final boolean actueelVoorMigratie) {
        isActueelVoorMigratie = actueelVoorMigratie;
    }

    /**
     * Geef de waarde van isActueelVoorVerblijfsrecht.
     * @return isActueelVoorVerblijfsrecht
     */
    public boolean isActueelVoorVerblijfsrecht() {
        return isActueelVoorVerblijfsrecht;
    }

    /**
     * Zet de waarde van isActueelVoorVerblijfsrecht.
     * @param actueelVoorVerblijfsrecht isActueelVoorVerblijfsrecht
     */
    public void setActueelVoorVerblijfsrecht(final boolean actueelVoorVerblijfsrecht) {
        isActueelVoorVerblijfsrecht = actueelVoorVerblijfsrecht;
    }

    /**
     * Geef de waarde van isActueelVoorUitsluitingkiesrecht.
     * @return isActueelVoorUitsluitingkiesrecht
     */
    public boolean isActueelVoorUitsluitingkiesrecht() {
        return isActueelVoorUitsluitingkiesrecht;
    }

    /**
     * Zet de waarde van isActueelVoorUitsluitingkiesrecht.
     * @param actueelVoorUitsluitingkiesrecht isActueelVoorUitsluitingkiesrecht
     */
    public void setActueelVoorUitsluitingkiesrecht(final boolean actueelVoorUitsluitingkiesrecht) {
        isActueelVoorUitsluitingkiesrecht = actueelVoorUitsluitingkiesrecht;
    }

    /**
     * Geef de waarde van isActueelVoorDeelnameEuVerkiezingen.
     * @return isActueelVoorDeelnameEuVerkiezingen
     */
    public boolean isActueelVoorDeelnameEuVerkiezingen() {
        return isActueelVoorDeelnameEuVerkiezingen;
    }

    /**
     * Zet de waarde van isActueelVoorDeelnameEuVerkiezingen.
     * @param actueelVoorDeelnameEuVerkiezingen isActueelVoorDeelnameEuVerkiezingen
     */
    public void setActueelVoorDeelnameEuVerkiezingen(final boolean actueelVoorDeelnameEuVerkiezingen) {
        isActueelVoorDeelnameEuVerkiezingen = actueelVoorDeelnameEuVerkiezingen;
    }

    /**
     * Geef de waarde van isActueelVoorPersoonskaart.
     * @return isActueelVoorPersoonskaart
     */
    public boolean isActueelVoorPersoonskaart() {
        return isActueelVoorPersoonskaart;
    }

    /**
     * Zet de waarde van isActueelVoorPersoonskaart.
     * @param actueelVoorPersoonskaart isActueelVoorPersoonskaart
     */
    public void setActueelVoorPersoonskaart(final boolean actueelVoorPersoonskaart) {
        isActueelVoorPersoonskaart = actueelVoorPersoonskaart;
    }

    /**
     * Toevoegen van een persoon afgeleid administratief historie.
     * @param persoonAfgeleidAdministratiefHistorie persoon afgeleid administratief historie
     */
    public void addPersoonAfgeleidAdministratiefHistorie(final PersoonAfgeleidAdministratiefHistorie persoonAfgeleidAdministratiefHistorie) {
        persoonAfgeleidAdministratiefHistorie.setPersoon(this);
        persoonAfgeleidAdministratiefHistorieSet.add(persoonAfgeleidAdministratiefHistorie);
    }

    /**
     * Geef de waarde van persoon afgeleid administratief historie set van Persoon.
     * @return de waarde van persoon afgeleid administratief historie set van Persoon
     */
    public Set<PersoonAfgeleidAdministratiefHistorie> getPersoonAfgeleidAdministratiefHistorieSet() {
        return persoonAfgeleidAdministratiefHistorieSet;
    }

    /**
     * Geef de waarde van persoon samengestelde naam historie set van Persoon.
     * @return de waarde van persoon samengestelde naam historie set van Persoon
     */
    public Set<PersoonSamengesteldeNaamHistorie> getPersoonSamengesteldeNaamHistorieSet() {
        return persoonSamengesteldeNaamHistorieSet;
    }

    /**
     * Toevoegen van een persoon samengestelde naam historie.
     * @param persoonSamengesteldeNaamHistorie persoon samengestelde naam historie
     */
    public void addPersoonSamengesteldeNaamHistorie(final PersoonSamengesteldeNaamHistorie persoonSamengesteldeNaamHistorie) {
        persoonSamengesteldeNaamHistorie.setPersoon(this);
        persoonSamengesteldeNaamHistorieSet.add(persoonSamengesteldeNaamHistorie);
    }

    /**
     * Geef de waarde van persoon naamgebruik historie set van Persoon.
     * @return de waarde van persoon naamgebruik historie set van Persoon
     */
    public Set<PersoonNaamgebruikHistorie> getPersoonNaamgebruikHistorieSet() {
        return persoonNaamgebruikHistorieSet;
    }

    /**
     * Toevoegen van een persoon naamgebruik historie.
     * @param persoonNaamgebruikHistorie persoon naamgebruik historie
     */
    public void addPersoonNaamgebruikHistorie(final PersoonNaamgebruikHistorie persoonNaamgebruikHistorie) {
        persoonNaamgebruikHistorie.setPersoon(this);
        persoonNaamgebruikHistorieSet.add(persoonNaamgebruikHistorie);
    }

    /**
     * Geef de waarde van persoon deelname eu verkiezingen historie set van Persoon.
     * @return de waarde van persoon deelname eu verkiezingen historie set van Persoon
     */
    public Set<PersoonDeelnameEuVerkiezingenHistorie> getPersoonDeelnameEuVerkiezingenHistorieSet() {
        return persoonDeelnameEuVerkiezingenHistorieSet;
    }

    /**
     * Toevoegen van een persoon deelname eu verkiezingen historie.
     * @param persoonDeelnameEuVerkiezingenHistorie persoon deelname eu verkiezingen historie
     */
    public void addPersoonDeelnameEuVerkiezingenHistorie(final PersoonDeelnameEuVerkiezingenHistorie persoonDeelnameEuVerkiezingenHistorie) {
        persoonDeelnameEuVerkiezingenHistorie.setPersoon(this);
        persoonDeelnameEuVerkiezingenHistorieSet.add(persoonDeelnameEuVerkiezingenHistorie);
    }

    /**
     * Geef de waarde van persoon geboorte historie set van Persoon.
     * @return de waarde van persoon geboorte historie set van Persoon
     */
    public Set<PersoonGeboorteHistorie> getPersoonGeboorteHistorieSet() {
        return persoonGeboorteHistorieSet;
    }

    /**
     * Toevoegen van een persoon geboorte historie.
     * @param persoonGeboorteHistorie persoon geboorte historie
     */
    public void addPersoonGeboorteHistorie(final PersoonGeboorteHistorie persoonGeboorteHistorie) {
        persoonGeboorteHistorie.setPersoon(this);
        persoonGeboorteHistorieSet.add(persoonGeboorteHistorie);
    }

    /**
     * Geef de waarde van persoon geslachtsaanduiding historie set van Persoon.
     * @return de waarde van persoon geslachtsaanduiding historie set van Persoon
     */
    public Set<PersoonGeslachtsaanduidingHistorie> getPersoonGeslachtsaanduidingHistorieSet() {
        return persoonGeslachtsaanduidingHistorieSet;
    }

    /**
     * Toevoegen van een persoon geslachtsaanduiding historie.
     * @param persoonGeslachtsaanduidingHistorie persoon geslachtsaanduiding historie
     */
    public void addPersoonGeslachtsaanduidingHistorie(final PersoonGeslachtsaanduidingHistorie persoonGeslachtsaanduidingHistorie) {
        persoonGeslachtsaanduidingHistorie.setPersoon(this);
        persoonGeslachtsaanduidingHistorieSet.add(persoonGeslachtsaanduidingHistorie);
    }

    /**
     * Geef de waarde van persoon id historie set van Persoon.
     * @return de waarde van persoon id historie set van Persoon
     */
    public Set<PersoonIDHistorie> getPersoonIDHistorieSet() {
        return persoonIDHistorieSet;
    }

    /**
     * Toevoegen van een persoon id historie.
     * @param persoonIDHistorie persoon id historie
     */
    public void addPersoonIDHistorie(final PersoonIDHistorie persoonIDHistorie) {
        persoonIDHistorie.setPersoon(this);
        persoonIDHistorieSet.add(persoonIDHistorie);
    }

    /**
     * Geef de waarde van persoon verblijfsrecht historie set van Persoon.
     * @return de waarde van persoon verblijfsrecht historie set van Persoon
     */
    public Set<PersoonVerblijfsrechtHistorie> getPersoonVerblijfsrechtHistorieSet() {
        return persoonVerblijfsrechtHistorieSet;
    }

    /**
     * Toevoegen van een persoon verblijfsrecht historie.
     * @param persoonVerblijfsrechtHistorie persoon verblijfsrecht historie
     */
    public void addPersoonVerblijfsrechtHistorie(final PersoonVerblijfsrechtHistorie persoonVerblijfsrechtHistorie) {
        persoonVerblijfsrechtHistorie.setPersoon(this);
        persoonVerblijfsrechtHistorieSet.add(persoonVerblijfsrechtHistorie);
    }

    /**
     * Geef de waarde van persoon bijhouding historie set van Persoon.
     * @return de waarde van persoon bijhouding historie set van Persoon
     */
    public Set<PersoonBijhoudingHistorie> getPersoonBijhoudingHistorieSet() {
        return persoonBijhoudingHistorieSet;
    }

    /**
     * Toevoegen van een persoon bijhouding historie.
     * @param persoonBijhoudingHistorie persoon bijhouding historie
     */
    public void addPersoonBijhoudingHistorie(final PersoonBijhoudingHistorie persoonBijhoudingHistorie) {
        persoonBijhoudingHistorie.setPersoon(this);
        persoonBijhoudingHistorieSet.add(persoonBijhoudingHistorie);
    }

    /**
     * Geef de waarde van persoon migratie historie set van Persoon.
     * @return de waarde van persoon migratie historie set van Persoon
     */
    public Set<PersoonMigratieHistorie> getPersoonMigratieHistorieSet() {
        return persoonMigratieHistorieSet;
    }

    /**
     * Toevoegen van een persoon migratie historie.
     * @param persoonMigratieHistorie persoon migratie historie
     */
    public void addPersoonMigratieHistorie(final PersoonMigratieHistorie persoonMigratieHistorie) {
        persoonMigratieHistorie.setPersoon(this);
        persoonMigratieHistorieSet.add(persoonMigratieHistorie);
    }

    /**
     * Geef de waarde van persoon inschrijving historie set van Persoon.
     * @return de waarde van persoon inschrijving historie set van Persoon
     */
    public Set<PersoonInschrijvingHistorie> getPersoonInschrijvingHistorieSet() {
        return persoonInschrijvingHistorieSet;
    }

    /**
     * Toevoegen van een persoon inschrijving historie.
     * @param persoonInschrijvingHistorie persoon inschrijving historie
     */
    public void addPersoonInschrijvingHistorie(final PersoonInschrijvingHistorie persoonInschrijvingHistorie) {
        persoonInschrijvingHistorie.setPersoon(this);
        persoonInschrijvingHistorieSet.add(persoonInschrijvingHistorie);
    }

    /**
     * Geef de waarde van persoon nummerverwijzing historie set van Persoon.
     * @return de waarde van persoon nummerverwijzing historie set van Persoon
     */
    public Set<PersoonNummerverwijzingHistorie> getPersoonNummerverwijzingHistorieSet() {
        return persoonNummerverwijzingHistorieSet;
    }

    /**
     * Toevoegen van een persoon nummerverwijzing historie.
     * @param persoonNummerverwijzingHistorie persoon nummerverwijzing historie
     */
    public void addPersoonNummerverwijzingHistorie(final PersoonNummerverwijzingHistorie persoonNummerverwijzingHistorie) {
        persoonNummerverwijzingHistorie.setPersoon(this);
        persoonNummerverwijzingHistorieSet.add(persoonNummerverwijzingHistorie);
    }

    /**
     * Geef de waarde van persoon overlijden historie set van Persoon.
     * @return de waarde van persoon overlijden historie set van Persoon
     */
    public Set<PersoonOverlijdenHistorie> getPersoonOverlijdenHistorieSet() {
        return persoonOverlijdenHistorieSet;
    }

    /**
     * Toevoegen van een persoon overlijden historie.
     * @param persoonOverlijdenHistorie persoon overlijden historie
     */
    public void addPersoonOverlijdenHistorie(final PersoonOverlijdenHistorie persoonOverlijdenHistorie) {
        persoonOverlijdenHistorie.setPersoon(this);
        persoonOverlijdenHistorieSet.add(persoonOverlijdenHistorie);
    }

    /**
     * Geef de waarde van persoon persoonskaart historie set van Persoon.
     * @return de waarde van persoon persoonskaart historie set van Persoon
     */
    public Set<PersoonPersoonskaartHistorie> getPersoonPersoonskaartHistorieSet() {
        return persoonPersoonskaartHistorieSet;
    }

    /**
     * Toevoegen van een persoon persoonskaart historie.
     * @param persoonPersoonskaartHistorie persoon persoonskaart historie
     */
    public void addPersoonPersoonskaartHistorie(final PersoonPersoonskaartHistorie persoonPersoonskaartHistorie) {
        persoonPersoonskaartHistorie.setPersoon(this);
        persoonPersoonskaartHistorieSet.add(persoonPersoonskaartHistorie);
    }

    /**
     * Geef de waarde van persoon uitsluiting kiesrecht historie set van Persoon.
     * @return de waarde van persoon uitsluiting kiesrecht historie set van Persoon
     */
    public Set<PersoonUitsluitingKiesrechtHistorie> getPersoonUitsluitingKiesrechtHistorieSet() {
        return persoonUitsluitingKiesrechtHistorieSet;
    }

    /**
     * Toevoegen van een persoon uitsluiting kiesrecht historie.
     * @param persoonUitsluitingKiesrechtHistorie persoon uitsluiting kiesrecht historie
     */
    public void addPersoonUitsluitingKiesrechtHistorie(final PersoonUitsluitingKiesrechtHistorie persoonUitsluitingKiesrechtHistorie) {
        persoonUitsluitingKiesrechtHistorie.setPersoon(this);
        persoonUitsluitingKiesrechtHistorieSet.add(persoonUitsluitingKiesrechtHistorie);
    }

    /**
     * Geef de waarde van lo3 berichten van Persoon.
     * @return de waarde van lo3 berichten van Persoon
     */
    public Set<Lo3Bericht> getLo3Berichten() {
        return lo3Berichten;
    }

    /**
     * Toevoegen van een lo3 bericht.
     * @param lo3Bericht lo3 bericht
     */
    public void addLo3Bericht(final Lo3Bericht lo3Bericht) {
        lo3Bericht.setPersoon(this);
        lo3Berichten.add(lo3Bericht);
    }

    /**
     * Geef de waarde van betrokkenheid set van Persoon.
     * @return de waarde van betrokkenheid set van Persoon
     */
    public Set<Betrokkenheid> getBetrokkenheidSet() {
        return betrokkenheidSet;
    }

    /**
     * Geef de betrokkenheden terug van {@link Persoon} voor de juiste {@link SoortBetrokkenheid}.
     * @param soortBetrokkenheid het soort betrokkenheid waarop gefilterd moet worden, null als er geen filtering toegepast moet worden
     * @return de lijst van betrokkenheden
     */
    public Set<Betrokkenheid> getBetrokkenheidSet(final SoortBetrokkenheid soortBetrokkenheid) {
        return betrokkenheidSet.stream()
                .filter(ikBetrokkenheid -> soortBetrokkenheid == null || soortBetrokkenheid.equals(ikBetrokkenheid.getSoortBetrokkenheid()))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * Geef de actuele betrokkenheden terug van {@link Persoon} voor de juiste {@link SoortBetrokkenheid}.
     * @param soortBetrokkenheid het soort betrokkenheid waarop gefilterd moet worden, null als er geen filtering toegepast moet worden
     * @return de lijst van actuele betrokkenheden
     */
    public Set<Betrokkenheid> getActueleBetrokkenheidSet(final SoortBetrokkenheid soortBetrokkenheid) {
        return Betrokkenheid.getActueleBetrokkenheden(getBetrokkenheidSet(soortBetrokkenheid));
    }

    /**
     * Toevoegen van een betrokkenheid.
     * @param betrokkenheid betrokkenheid
     */
    public void addBetrokkenheid(final Betrokkenheid betrokkenheid) {
        betrokkenheid.setPersoon(this);
        betrokkenheidSet.add(betrokkenheid);
    }

    /**
     * Verwijderen van een betrokkenheid.
     * @param betrokkenheid betrokkenheid
     * @return true, if successful
     */
    public boolean removeBetrokkenheid(final Betrokkenheid betrokkenheid) {
        // Persoon hard op null zetten, anders wordt de DB-object niet verwijderd door Hibernate.
        betrokkenheid.setPersoon(null);
        return betrokkenheidSet.remove(betrokkenheid);
    }

    /**
     * Geef van deze persoon de actuele ouders. Dit betekent dat deze persoon een actuele kind betrokkenheid moet hebben met eem familierechtelijke betrekking
     * met daaraan 1 of meerdere ouder betrokkenheden. Deze ouder betrokkenheden worden dan geretourneerd.
     * @return de actuele ouders of een lege lijst als deze niet gevonden worden
     */
    public Set<Betrokkenheid> getActueleOuders() {
        Set<Betrokkenheid> results = new LinkedHashSet<>();
        for (final Betrokkenheid ikAlsKindBetrokkenheid : getActueleBetrokkenheidSet(SoortBetrokkenheid.KIND)) {
            results.addAll(ikAlsKindBetrokkenheid.getRelatie().getActueleBetrokkenheidSet(SoortBetrokkenheid.OUDER));
        }
        return results;
    }

    /**
     * Geef van deze persoon de ouders op peildatum.
     * Dit betekent dat deze persoon een kind betrokkenheid moet hebben met eem familierechtelijke betrekking
     * met daaraan 1 of meerdere ouder betrokkenheden welke op peildatum een actuele {@link BetrokkenheidOuderHistorie} heeft.
     * Deze ouder betrokkenheden worden dan geretourneerd.
     * @param peildatum
     * @return de ouders op peildatum of een lege lijst als deze niet gevonden worden
     */
    public Set<Persoon> getOuders(int peildatum) {
        Set<Persoon> results = new LinkedHashSet<>();
        for (final Betrokkenheid ikAlsKindBetrokkenheid : getActueleBetrokkenheidSet(SoortBetrokkenheid.KIND)) {
            final Set<Betrokkenheid> ouderBetrokkenheidSet = ikAlsKindBetrokkenheid.getRelatie().getActueleBetrokkenheidSet(SoortBetrokkenheid.OUDER);
            for (Betrokkenheid ouderBetrokkenheid : ouderBetrokkenheidSet){
                if(MaterieleHistorie.getGeldigVoorkomenOpPeildatum(ouderBetrokkenheid.getBetrokkenheidOuderHistorieSet(),peildatum)!=null){
                    results.add(ouderBetrokkenheid.getPersoon());
                }
            }
        }
        return results;
    }

    /**
     * Geef van deze persoon de actuele kinderen. Dit betekent dat deze persoon actuele ouder betrokkenheden moet hebben met eem familierechtelijke betrekking
     * met daaraan een kind betrokkenheid. Deze kind betrokkenheden (van verschillende relaties) worden dan geretourneerd.
     * @return de actuele kinderen of een lege lijst als deze niet gevonden worden
     */
    public Set<Betrokkenheid> getActueleKinderen() {
        Set<Betrokkenheid> results = new LinkedHashSet<>();
        for (final Betrokkenheid ikAlsOuderBetrokkenheid : getActueleBetrokkenheidSet(SoortBetrokkenheid.OUDER)) {
            results.addAll(ikAlsOuderBetrokkenheid.getRelatie().getActueleBetrokkenheidSet(SoortBetrokkenheid.KIND));
        }
        return results;
    }

    /**
     * Geef van deze persoon de kinderen op pijlDatum.
     * Dit betekent dat deze persoon op pijldatum een actuele ouder betrokkenheden moet hebben met eem familierechtelijke betrekking
     * met daaraan een kind betrokkenheid. Deze kind betrokkenheden (van verschillende relaties) worden dan geretourneerd.
     * @param peildatum datum waarop bepaald word of ouderouderschap een actueel historisch voorkomen heeft.
     * @return de actuele kinderen of een lege lijst als deze niet gevonden worden
     */
    public Set<Betrokkenheid> getKinderen(final int peildatum) {
        Set<Betrokkenheid> results = new LinkedHashSet<>();
        for (final Betrokkenheid ikAlsOuderBetrokkenheid : getActueleBetrokkenheidSet(SoortBetrokkenheid.OUDER)) {
            if(MaterieleHistorie.getGeldigVoorkomenOpPeildatum(ikAlsOuderBetrokkenheid.getBetrokkenheidOuderHistorieSet(),peildatum)!=null) {
                results.addAll(ikAlsOuderBetrokkenheid.getRelatie().getActueleBetrokkenheidSet(SoortBetrokkenheid.KIND));
            }
        }
        return results;
    }

    /**
     * Geef van deze persoon de actuele partners. Dit betekent dat deze persoon actuele partner betrokkenheden moet hebben met een HGP
     * met daaraan een actuele partner betrokkenheid. Deze partner betrokkenheden (van verschillende relaties) worden dan geretourneerd.
     * @return de actuele partners of een lege lijst als deze niet gevonden worden
     */
    public Set<Betrokkenheid> getActuelePartners() {
        Set<Betrokkenheid> results = new LinkedHashSet<>();
        for (final Betrokkenheid ikAlsPartnerBetrokkenheid : getActueleBetrokkenheidSet(SoortBetrokkenheid.PARTNER)) {
            for (final Betrokkenheid andereBetrokkenheid : ikAlsPartnerBetrokkenheid.getRelatie().getActueleBetrokkenheidSet(SoortBetrokkenheid.PARTNER)) {
                if (ikAlsPartnerBetrokkenheid != andereBetrokkenheid) {
                    results.add(andereBetrokkenheid);
                }
            }
        }
        return results;
    }

    /**
     * Geef de waarde van relaties van Persoon.
     * @return de waarde van relaties van Persoon
     */
    public Set<Relatie> getRelaties() {
        return getRelaties(null);
    }

    /**
     * Geef de relaties terug van {@link Persoon} waarbij Persoon.
     * @param soortBetrokkenheid het soort betrokkenheid waarop gefilterd moet worden, null als er geen filtering toegepast moet worden
     * @return de lijst van relaties
     */
    public Set<Relatie> getRelaties(final SoortBetrokkenheid soortBetrokkenheid) {
        return betrokkenheidSet.stream()
                .filter(ikBetrokkenheid -> soortBetrokkenheid == null || soortBetrokkenheid.equals(ikBetrokkenheid.getSoortBetrokkenheid()))
                .map(Betrokkenheid::getRelatie).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * Geef de waarde van stapels van Persoon.
     * @return de waarde van stapels van Persoon
     */
    public Set<Stapel> getStapels() {
        return stapels;
    }

    /**
     * Voegt de stapel toe aan deze persoon.
     * @param stapel de (IST) stapel, mag niet null zijn
     */
    public void addStapel(final Stapel stapel) {
        stapel.setPersoon(this);
        stapels.add(stapel);
    }

    /**
     * Verwijderd de stapel voor deze persoon.
     * @param stapel de stapel die verwijderd moet worden
     * @return true als het lukt de stapel te verwijderen anders false
     */
    public boolean removeStapel(final Stapel stapel) {
        return stapels.remove(stapel);
    }

    @Override
    public Map<String, Collection<FormeleHistorie>> verzamelHistorie() {
        final Map<String, Collection<FormeleHistorie>> result = new HashMap<>();

        result.put("persoonGeboorteHistorieSet", Collections.unmodifiableSet(persoonGeboorteHistorieSet));
        result.put("persoonSamengesteldeNaamHistorieSet", Collections.unmodifiableSet(persoonSamengesteldeNaamHistorieSet));
        result.put("persoonGeslachtsaanduidingHistorieSet", Collections.unmodifiableSet(persoonGeslachtsaanduidingHistorieSet));
        result.put("persoonIDHistorieSet", Collections.unmodifiableSet(persoonIDHistorieSet));
        return result;
    }

    /**
     * Deze methode leidt de groepen af waarvan de inhoud is gebaseerd op andere groepen voor
     * ingeschreven personen.
     * @param actie de verantwoording voor de afgeleide gegevens
     * @param datumAanvangGeldigheid de datum aanvang geldigheid voor de afgeleide gegevens
     * @param isExplicietGewijzigd the is expliciet gewijzigd
     */
    public void leidAf(final BRPActie actie, final int datumAanvangGeldigheid, final boolean isExplicietGewijzigd) {
        leidAf(actie, datumAanvangGeldigheid, isExplicietGewijzigd, null);
    }

    /**
     * Deze methode leidt de groepen af waarvan de inhoud is gebaseerd op andere groepen voor
     * ingeschreven personen.
     * @param actie de verantwoording voor de afgeleide gegevens
     * @param datumAanvangGeldigheid de datum aanvang geldigheid voor de afgeleide gegevens
     * @param isExplicietGewijzigd the is expliciet gewijzigd
     * @param explicieteIndicatieNamenReeks zet indicatieNamenReeks expliciet of null om hem af te leiden van vorige voorkomen.
     */
    @Bedrijfsregel(Regel.R1811)
    public void leidAf(final BRPActie actie, final int datumAanvangGeldigheid, final boolean isExplicietGewijzigd,
                       final Boolean explicieteIndicatieNamenReeks) {
        if (SoortPersoon.INGESCHREVENE.equals(getSoortPersoon()) && !getPersoonGeslachtsnaamcomponentSet().isEmpty()) {
            final Set<PersoonGeslachtsnaamcomponentHistorie> persoonGeslachtsnaamcomponentHistorieSet =
                    getPersoonGeslachtsnaamcomponentSet().iterator().next().getPersoonGeslachtsnaamcomponentHistorieSet();
            final PersoonGeslachtsnaamcomponentHistorie actueelGeslachtsnaamcomponentVoorkomen =
                    FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoonGeslachtsnaamcomponentHistorieSet);
            if (actueelGeslachtsnaamcomponentVoorkomen != null) {
                final PersoonSamengesteldeNaamHistorie actueelSamengesteldeNaamVoorkomen =
                        FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(getPersoonSamengesteldeNaamHistorieSet());

                final Boolean indicatieNamenReeks = explicieteIndicatieNamenReeks != null ? explicieteIndicatieNamenReeks
                        : actueelSamengesteldeNaamVoorkomen != null &&
                                actueelSamengesteldeNaamVoorkomen.getIndicatieNamenreeks();
                final PersoonSamengesteldeNaamHistorie nieuweSamengesteldeNaamVoorkomen =
                        new PersoonSamengesteldeNaamHistorie(this, actueelGeslachtsnaamcomponentVoorkomen.getStam(), true,
                                indicatieNamenReeks);

                // optionele velden
                nieuweSamengesteldeNaamVoorkomen.setScheidingsteken(actueelGeslachtsnaamcomponentVoorkomen.getScheidingsteken());
                nieuweSamengesteldeNaamVoorkomen.setVoorvoegsel(actueelGeslachtsnaamcomponentVoorkomen.getVoorvoegsel());
                nieuweSamengesteldeNaamVoorkomen.setAdellijkeTitel(actueelGeslachtsnaamcomponentVoorkomen.getAdellijkeTitel());
                nieuweSamengesteldeNaamVoorkomen.setPredicaat(actueelGeslachtsnaamcomponentVoorkomen.getPredicaat());
                nieuweSamengesteldeNaamVoorkomen.setVoornamen(bepaalActueleVoornamen());

                // verantwoording
                nieuweSamengesteldeNaamVoorkomen.setDatumTijdRegistratie(actie.getDatumTijdRegistratie());
                nieuweSamengesteldeNaamVoorkomen.setActieInhoud(actie);
                nieuweSamengesteldeNaamVoorkomen.setDatumAanvangGeldigheid(datumAanvangGeldigheid);

                if (!nieuweSamengesteldeNaamVoorkomen.isInhoudelijkGelijkAan(actueelSamengesteldeNaamVoorkomen)) {
                    MaterieleHistorie.voegNieuweActueleToe(nieuweSamengesteldeNaamVoorkomen, getPersoonSamengesteldeNaamHistorieSet());
                    final Persoon partner = getActuelePartner();
                    leidtNaamgebruikPartnerAf(actie, partner, isExplicietGewijzigd);
                    leidtNaamgebruikAf(actie, partner, isExplicietGewijzigd);
                }
            }
        }
    }

    private void leidtNaamgebruikPartnerAf(final BRPActie actie, final Persoon partner, final boolean isExplicietGewijzigd) {
        if (partner != null) {
            partner.leidtNaamgebruikAf(actie, isExplicietGewijzigd);
        }
    }

    private String bepaalActueleVoornamen() {
        final StringBuilder result = new StringBuilder();
        final List<PersoonVoornaam> persoonVoornaamList = new ArrayList<>(getPersoonVoornaamSet());
        Collections.sort(persoonVoornaamList, PersoonVoornaam.COMPARATOR);
        for (final PersoonVoornaam persoonVoornaam : persoonVoornaamList) {
            final PersoonVoornaamHistorie actueleVoornaam =
                    FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoonVoornaam.getPersoonVoornaamHistorieSet());
            if (actueleVoornaam != null) {
                if (result.length() != 0) {
                    result.append(' ');
                }
                result.append(actueleVoornaam.getNaam());
            }
        }
        if (result.length() == 0) {
            return null;
        } else {
            return result.toString();
        }
    }

    /**
     * Leidt het naamgebruik af op basis van het huidige (actuele) naamgebruik. Als de indicatie
     * naamgebruik afgeleid false is, dan zal er niet worden afgeleid.
     * @param actie de actie die tot de afleiding heeft geleid.
     * @param isExplicietGewijzigd the is expliciet gewijzigd*
     */
    public void leidtNaamgebruikAf(final BRPActie actie, final boolean isExplicietGewijzigd) {
        leidtNaamgebruikAf(actie, getActuelePartner(), isExplicietGewijzigd);
    }

    private void leidtNaamgebruikAf(final BRPActie actie, final Persoon partner, final boolean isExplicietGewijzigd) {
        final PersoonNaamgebruikHistorie actueleNaamgebruikHistorie =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(getPersoonNaamgebruikHistorieSet());
        if (actueleNaamgebruikHistorie != null && actueleNaamgebruikHistorie.getIndicatieNaamgebruikAfgeleid()) {
            leidtNaamgebruikAf(actueleNaamgebruikHistorie.getNaamgebruik(), actie, partner, isExplicietGewijzigd);
        }
    }

    /**
     * Leidt naamgebruik af.
     * @param naamgebruik the naamgebruik
     * @param actie the actie
     * @param isExplicietGewijzigd the is expliciet gewijzigd
     */
    @Bedrijfsregel(Regel.R1683)
    public void leidtNaamgebruikAf(final Naamgebruik naamgebruik, final BRPActie actie, final boolean isExplicietGewijzigd) {
        leidtNaamgebruikAf(naamgebruik, actie, getActuelePartner(), isExplicietGewijzigd);
    }

    private void leidtNaamgebruikAf(final Naamgebruik naamgebruik, final BRPActie actie, final Persoon partner, final boolean isExplicietGewijzigd) {
        final PersoonSamengesteldeNaamHistorie samengesteldeNaamHistorie =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(getPersoonSamengesteldeNaamHistorieSet());
        final PersoonSamengesteldeNaamHistorie partnerSamengesteldeNaamHistorie;

        if (!Naamgebruik.EIGEN.equals(naamgebruik) && partner != null) {
            partnerSamengesteldeNaamHistorie =
                    FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(partner.getPersoonSamengesteldeNaamHistorieSet());
        } else {
            partnerSamengesteldeNaamHistorie = null;
        }

        final PersoonNaamgebruikHistorie actueelHistorie = FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(getPersoonNaamgebruikHistorieSet());
        final PersoonNaamgebruikHistorie historie = maakHistorieOpBasisVanNaamgebruik(naamgebruik, samengesteldeNaamHistorie, partnerSamengesteldeNaamHistorie);

        historie.setVoornamenNaamgebruik(samengesteldeNaamHistorie.getVoornamen());
        historie.setPredicaat(samengesteldeNaamHistorie.getPredicaat());
        historie.setAdellijkeTitel(samengesteldeNaamHistorie.getAdellijkeTitel());

        historie.setActieInhoud(actie);
        historie.setDatumTijdRegistratie(actie.getDatumTijdRegistratie());

        if (isExplicietGewijzigd || !historie.isInhoudelijkGelijkAan(actueelHistorie)) {
            FormeleHistorie.voegToe(historie, getPersoonNaamgebruikHistorieSet());
        }
    }

    private PersoonNaamgebruikHistorie maakHistorieOpBasisVanNaamgebruik(final Naamgebruik naamgebruik,
                                                                         final PersoonSamengesteldeNaamHistorie samengesteldeNaamHistorie,
                                                                         final PersoonSamengesteldeNaamHistorie partnerSamengesteldeNaamHistorie) {
        final String naamgebruikVoorvoegsel = samengesteldeNaamHistorie.getVoorvoegsel() == null ? "" : samengesteldeNaamHistorie.getVoorvoegsel();
        final String naamgebruikScheidingsteken =
                samengesteldeNaamHistorie.getScheidingsteken() == null ? "" : samengesteldeNaamHistorie.getScheidingsteken().toString();

        final String naamgebruikGeslachtsnaamstam = samengesteldeNaamHistorie.getGeslachtsnaamstam();

        final String partnerVoorvoegsel;
        final String partnerScheidingsteken;
        final String partnerGeslachtsnaam;

        if (partnerSamengesteldeNaamHistorie != null) {
            partnerVoorvoegsel = partnerSamengesteldeNaamHistorie.getVoorvoegsel() == null ? "" : partnerSamengesteldeNaamHistorie.getVoorvoegsel();
            partnerScheidingsteken =
                    partnerSamengesteldeNaamHistorie.getScheidingsteken() == null ? "" : partnerSamengesteldeNaamHistorie.getScheidingsteken().toString();
            partnerGeslachtsnaam = partnerSamengesteldeNaamHistorie.getGeslachtsnaamstam();
        } else {
            partnerVoorvoegsel = "";
            partnerScheidingsteken = "";
            partnerGeslachtsnaam = "";
        }

        final String geslachtsnaam;
        final String koppelteken = "-";

        switch (naamgebruik) {
            case PARTNER:
                geslachtsnaam = partnerGeslachtsnaam;
                break;
            case PARTNER_NA_EIGEN:
                geslachtsnaam = naamgebruikGeslachtsnaamstam + koppelteken + partnerVoorvoegsel + partnerScheidingsteken + partnerGeslachtsnaam;
                break;
            case PARTNER_VOOR_EIGEN:
                geslachtsnaam = partnerGeslachtsnaam + koppelteken + naamgebruikVoorvoegsel + naamgebruikScheidingsteken + naamgebruikGeslachtsnaamstam;
                break;
            default:
                // EIGEN
                geslachtsnaam = naamgebruikGeslachtsnaamstam;
        }

        final PersoonNaamgebruikHistorie historie = new PersoonNaamgebruikHistorie(this, geslachtsnaam, true, naamgebruik);
        vulVoorvoegselEnScheidingsteken(historie, naamgebruik, naamgebruikVoorvoegsel, naamgebruikScheidingsteken, partnerVoorvoegsel, partnerScheidingsteken);
        return historie;
    }

    private void vulVoorvoegselEnScheidingsteken(final PersoonNaamgebruikHistorie historie, final Naamgebruik naamgebruik, final String naamgebruikVoorvoegsel,
                                                 final String naamgebruikScheidingsteken, final String partnerVoorvoegsel,
                                                 final String partnerScheidingsteken) {
        switch (naamgebruik) {
            case PARTNER:
            case PARTNER_VOOR_EIGEN:
                historie.setVoorvoegselNaamgebruik(partnerVoorvoegsel.isEmpty() ? null : partnerVoorvoegsel);
                historie.setScheidingstekenNaamgebruik(partnerScheidingsteken.isEmpty() ? null : partnerScheidingsteken.charAt(0));
                break;
            case PARTNER_NA_EIGEN:
            case EIGEN:
            default:
                historie.setVoorvoegselNaamgebruik(naamgebruikVoorvoegsel.isEmpty() ? null : naamgebruikVoorvoegsel);
                historie.setScheidingstekenNaamgebruik(naamgebruikScheidingsteken.isEmpty() ? null : naamgebruikScheidingsteken.charAt(0));
        }
    }

    /**
     * Geeft de actuele (ex-)partner terug van deze persoon, of null als er geen huwelijk/geregistreerd partnerschap is gevonden.
     * @return de actuele (ex-)partner of null
     */
    public Persoon getActuelePartner() {
        final Set<Relatie> relaties = getRelaties(SoortBetrokkenheid.PARTNER);
        final Relatie actueleOfExPartnerRelatie = Entiteit.convertToPojo(Relatie.getActueleOfMeestRecentBeeindigdeRelatie(relaties));

        final Persoon persoon;
        if (actueleOfExPartnerRelatie != null) {
            final Betrokkenheid betrokkenheid = actueleOfExPartnerRelatie.getAndereBetrokkenheid(this);
            persoon = betrokkenheid.getPersoon();
        } else {
            persoon = null;
        }
        return persoon;
    }

    /**
     * Geeft alle actuele voorkomens van de materiele groepen van een Persoon.
     * @return een set van actuele voorkomens van de materiele groepen voor deze persoon
     */
    public Set<MaterieleHistorie> getNietVervallenMaterieleGroepen() {
        final Set<MaterieleHistorie> results = new LinkedHashSet<>();
        for (final PersoonAdres adres : getPersoonAdresSet()) {
            results.addAll(FormeleHistorieZonderVerantwoording.getNietVervallenVoorkomens(adres.getPersoonAdresHistorieSet()));
        }
        results.addAll(FormeleHistorieZonderVerantwoording.getNietVervallenVoorkomens(getPersoonBijhoudingHistorieSet()));
        results.addAll(FormeleHistorieZonderVerantwoording.getNietVervallenVoorkomens(getPersoonGeslachtsaanduidingHistorieSet()));
        for (final PersoonGeslachtsnaamcomponent p : getPersoonGeslachtsnaamcomponentSet()) {
            results.addAll(FormeleHistorieZonderVerantwoording.getNietVervallenVoorkomens(p.getPersoonGeslachtsnaamcomponentHistorieSet()));
        }
        results.addAll(FormeleHistorieZonderVerantwoording.getNietVervallenVoorkomens(getPersoonIDHistorieSet()));
        for (final PersoonIndicatie p : getPersoonIndicatieSet()) {
            if (p.getSoortIndicatie().isMaterieleHistorieVanToepassing()) {
                results.addAll(FormeleHistorieZonderVerantwoording.getNietVervallenVoorkomens(p.getPersoonIndicatieHistorieSet()));
            }
        }
        results.addAll(FormeleHistorieZonderVerantwoording.getNietVervallenVoorkomens(getPersoonMigratieHistorieSet()));
        for (final PersoonNationaliteit p : getPersoonNationaliteitSet()) {
            results.addAll(FormeleHistorieZonderVerantwoording.getNietVervallenVoorkomens(p.getPersoonNationaliteitHistorieSet()));
        }
        results.addAll(FormeleHistorieZonderVerantwoording.getNietVervallenVoorkomens(getPersoonNummerverwijzingHistorieSet()));
        results.addAll(FormeleHistorieZonderVerantwoording.getNietVervallenVoorkomens(getPersoonSamengesteldeNaamHistorieSet()));
        for (final PersoonVoornaam p : getPersoonVoornaamSet()) {
            results.addAll(FormeleHistorieZonderVerantwoording.getNietVervallenVoorkomens(p.getPersoonVoornaamHistorieSet()));
        }
        for (final Betrokkenheid ouderBetrokkenheid : getActueleBetrokkenheidSet(SoortBetrokkenheid.OUDER)) {
            results.addAll(FormeleHistorieZonderVerantwoording.getNietVervallenVoorkomens(ouderBetrokkenheid.getBetrokkenheidOuderHistorieSet()));
            results.addAll(FormeleHistorieZonderVerantwoording.getNietVervallenVoorkomens(ouderBetrokkenheid.getBetrokkenheidOuderlijkGezagHistorieSet()));
        }
        for (final Betrokkenheid kindBetrokkenheid : getActueleBetrokkenheidSet(SoortBetrokkenheid.KIND)) {
            for (final Betrokkenheid ouderBetrokkenheid : kindBetrokkenheid.getRelatie().getActueleBetrokkenheidSet(SoortBetrokkenheid.OUDER)) {
                results.addAll(FormeleHistorieZonderVerantwoording.getNietVervallenVoorkomens(ouderBetrokkenheid.getBetrokkenheidOuderHistorieSet()));
                results.addAll(FormeleHistorieZonderVerantwoording.getNietVervallenVoorkomens(ouderBetrokkenheid.getBetrokkenheidOuderlijkGezagHistorieSet()));
            }
        }
        return results;
    }

    /**
     * Berekent de leeftijd van deze persoon voor de gegeven peildatum op de soepele manier (onbekende en deels onbekende datums zijn toegestaan).
     * @param peildatum de datum waarop de leeftijd bepaald moet worden
     * @return leeftijd de leeftijd op het gegeven peilmoment
     * @throws IllegalStateException wanneer deze persoon geen actuele geboorte groep heeft
     */
    public int bepaalLeeftijd(final int peildatum) {
        final PersoonGeboorteHistorie geboorteHistorie = FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(getPersoonGeboorteHistorieSet());
        if (geboorteHistorie != null) {
            return DatumUtil.bepaalJarenTussenDatumsSoepel(geboorteHistorie.getDatumGeboorte(), peildatum).intValue();
        } else {
            throw new IllegalStateException("Deze persoon heeft geen actuele geboorte groep en dus kan de leeftijd niet worden berekent.");
        }
    }


    /**
     * Controleert of deze {@link Persoon} verwant is met de meegegeven Persoon.
     * Twee Persoonen zijn verwant (op een zekere peildatum) als ze (op die peildatum):
     * - een Familierechtelijke betrekking hebben of
     * - beide betrokken zijn in verschillende Familierechtelijke betrekkingen waarbij in beide eenzelfde derde Persoon is betrokken.
     * Als een van de personen null is, dan is er geen verwantschap.
     * @param persoonA een {@link Persoon}
     * @param persoonB een {@link Persoon}
     * @return true indien verwant, false in andere gevallen.
     */
    public static boolean bestaatVerwantschap(final Persoon persoonA, final Persoon persoonB) {
        if (persoonA == null || persoonB == null || Objects.equals(persoonA.getId(), persoonB.getId())) {
            return false;
        } else {
            return bestaatVerwantschap(persoonB, persoonA.getActueleKinderen()) || bestaatVerwantschap(persoonB, persoonA.getActueleOuders())
                    || bestaatVerwantschapMetTweedeGraadsFamilie(persoonA, persoonB);
        }
    }

    private static boolean bestaatVerwantschapMetTweedeGraadsFamilie(final Persoon persoonA, final Persoon persoonB) {
        final boolean isVerwant;
        // Verwant met grootouders?
        final Set<Betrokkenheid> grootouders = new HashSet<>();
        persoonA.getActueleOuders().forEach(ouder -> grootouders.addAll(ouder.getPersoon().getActueleOuders()));

        if (!bestaatVerwantschap(persoonB, grootouders)) {
            // Verwant met kleinkinderen?
            final Set<Betrokkenheid> kleinkinderen = new HashSet<>();
            persoonA.getActueleKinderen().forEach(kind -> kleinkinderen.addAll(kind.getPersoon().getActueleKinderen()));

            if (!bestaatVerwantschap(persoonB, kleinkinderen)) {
                // Verwant met broers/zussen?
                final Set<Betrokkenheid> broersZussen = new HashSet<>();
                persoonA.getActueleOuders().forEach(ouder -> broersZussen.addAll(ouder.getPersoon().getActueleKinderen()));

                isVerwant = bestaatVerwantschap(persoonB, broersZussen);
            } else {
                isVerwant = true;
            }
        } else {
            isVerwant = true;
        }

        return isVerwant;
    }

    private static boolean bestaatVerwantschap(final Persoon ander, final Set<Betrokkenheid> mogelijkeVerwantschapKandidaten) {
        return mogelijkeVerwantschapKandidaten.stream().map(Betrokkenheid::getPersoon).anyMatch(kind -> Objects.equals(kind.getId(), ander.getId()));
    }
}
