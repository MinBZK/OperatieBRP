/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;
import nl.bzk.migratiebrp.util.common.Kopieer;

/**
 * The persistent class for the pers database table.
 * <p/>
 * 
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "pers", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"id" }))
@SuppressWarnings("checkstyle:designforextension")
public class Persoon extends AbstractDeltaEntiteit implements DeltaRootEntiteit, Serializable {

    /**
     * Veldnaam van nadere bijhoudingsaard tbv verschil verwerking.
     */
    public static final String NADERE_BIJHOUDINGSAARD = "nadereBijhoudingsaard";
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
    public static final String STAPELS = "stapels";
    /**
     * Veldnaam van migratie historie set tbv bepalen infra wijziging.
     */
    public static final String PERSOONMIGRATIEHISTORIESET = "persoonMigratieHistorieSet";
    /**
     * Veldnaam van bijhouding historie set tbv bepalen infra wijziging.
     */
    public static final String PERSOONBIJHOUDINGHISTORIESET = "persoonBijhoudingHistorieSet";
    /**
     * Veldnaam van adres set tbv bepalen infra wijziging.
     */
    public static final String PERSOONADRESSET = "persoonAdresSet";
    /**
     * Veldnaam van migratie historie set tbv bepalen infra wijziging.
     */
    public static final String PERSOONINSCHRIJVINGHISTORIESET = "persoonInschrijvingHistorieSet";
    /**
     * Veldnaam van migratie historie set tbv bepalen infra wijziging.
     */
    public static final String TIJDSTIPLAATSTEWIJZIGING = "tijdstipLaatsteWijziging";
    /**
     * Veldnaam van inschrijving historie set tbv bepalen infra wijziging.
     */
    public static final String VERSIENUMMER = "versienummer";
    /**
     * Veldnaam van datum/tijd stempel tbv bepalen infra wijziging.
     */
    public static final String DATUMTIJDSTEMPEL = "datumtijdstempel";
    /**
     * Veldnaam van soort migratie tbv bepalen infra wijziging.
     */
    public static final String SOORTMIGRATIEID = "soortMigratieId";
    /**
     * Veldnaam van buitenlandsadres regel 1 tbv bepalen infra wijziging.
     */
    public static final String BUITENLANDSADRESREGEL1MIGRATIE = "buitenlandsAdresRegel1Migratie";
    /**
     * Veldnaam van buitenlandsadres regel 2 tbv bepalen infra wijziging.
     */
    public static final String BUITENLANDSADRESREGEL2MIGRATIE = "buitenlandsAdresRegel2Migratie";
    /**
     * Veldnaam van buitenlandsadres regel 3 tbv bepalen infra wijziging.
     */
    public static final String BUITENLANDSADRESREGEL3MIGRATIE = "buitenlandsAdresRegel3Migratie";
    /**
     * Veldnaam van buitenlandsadres regel 4 tbv bepalen infra wijziging.
     */
    public static final String BUITENLANDSADRESREGEL4MIGRATIE = "buitenlandsAdresRegel4Migratie";
    /**
     * Veldnaam van buitenlandsadres regel 5 tbv bepalen infra wijziging.
     */
    public static final String BUITENLANDSADRESREGEL5MIGRATIE = "buitenlandsAdresRegel5Migratie";
    /**
     * Veldnaam van buitenlandsadres regel 6 tbv bepalen infra wijziging.
     */
    public static final String BUITENLANDSADRESREGEL6MIGRATIE = "buitenlandsAdresRegel6Migratie";
    /**
     * Veldnaam van tijdstip laatste wijziging GBA tbv bepalen infra wijziging.
     */
    public static final String TIJDSTIPLAATSTEWIJZIGINGGBASYSTEMATIEK = "tijdstipLaatsteWijzigingGbaSystematiek";
    /**
     * Veldnaam van bijhoudingspartij tbv bepalen infra wijziging.
     */
    public static final String BIJHOUDINGSPARTIJ = "bijhoudingspartij";
    /**
     * Veldnaam van reden wijziging migratie tbv bepalen infra wijziging.
     */
    public static final String REDENWIJZIGINGMIGRATIE = "redenWijzigingMigratie";
    /**
     * Veldnaam van land/gebied migratie tbv bepalen infra wijziging.
     */
    public static final String LANDOFGEBIEDMIGRATIE = "landOfGebiedMigratie";
    /**
     * Veldnaam van aangever migratie tbv bepalen infra wijziging.
     */
    public static final String AANGEVERMIGRATIE = "aangeverMigratie";

    /**
     * Persoon attribuut wat gebruikt wordt in JPA annotaties.
     */
    protected static final String PERSOON_ATTRIBUUT = "persoon";
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "pers_id_generator", sequenceName = "kern.seq_pers", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pers_id_generator")
    @Column(nullable = false)
    private Long id;
    @Column(name = "srt", nullable = false)
    private short soortPersoonId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "admhnd")
    private AdministratieveHandeling administratieveHandeling;
    @Column(name = "Sorteervolgorde")
    private Short sorteervolgorde = 1;
    @Column(name = "anr")
    private Long administratienummer;
    @Column(name = "blplaatsgeboorte", length = 40)
    private String buitenlandsePlaatsGeboorte;
    @Column(name = "blplaatsoverlijden", length = 40)
    private String buitenlandsePlaatsOverlijden;
    @Column(name = "blregiogeboorte", length = 35)
    private String buitenlandseRegioGeboorte;
    @Column(name = "blregiooverlijden", length = 35)
    private String buitenlandseRegioOverlijden;
    @Column(name = "bsn", length = 9)
    private Integer burgerservicenummer;
    @Column(name = "dataanlaanpdeelneuverkiezing")
    private Integer datumAanleidingAanpassingDeelnameEuVerkiezing;
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
    @Column(name = "indonverwdocaanw")
    private Boolean indicatieOnverwerktDocumentAanwezig;
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
    private Short adellijkeTitelNaamgebruikId;
    @Column(length = 10)
    private String voorvoegsel;
    @Column(name = "voorvoegselnaamgebruik", length = 10)
    private String voorvoegselNaamgebruik;
    @Column(name = "srtmigratie")
    private Short soortMigratieId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "rdnwijzmigratie")
    private RedenWijzigingVerblijf redenWijzigingMigratie;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
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
    private Short adellijkeTitelId;
    @Column(name = "geslachtsaand")
    private Short geslachtsaanduidingId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "landgebiedgeboorte")
    private LandOfGebied landOfGebiedGeboorte;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "landgebiedmigratie")
    private LandOfGebied landOfGebiedMigratie;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "landgebiedoverlijden")
    private LandOfGebied landOfGebiedOverlijden;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "gempk")
    private Partij gemeentePersoonskaart;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "gemoverlijden")
    private Gemeente gemeenteOverlijden;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "gemgeboorte")
    private Gemeente gemeenteGeboorte;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "bijhpartij")
    private Partij bijhoudingspartij;
    @Column(name = "volgendeanr")
    private Long volgendeAdministratienummer;
    @Column(name = "vorigeanr")
    private Long vorigeAdministratienummer;
    @Column(name = "volgendebsn")
    private Integer volgendeBurgerservicenummer;
    @Column(name = "vorigebsn")
    private Integer vorigeBurgerservicenummer;
    @Column(name = "wplnaamgeboorte")
    private String woonplaatsGeboorte;
    @Column(name = "wplnaamoverlijden")
    private String woonplaatsOverlijden;
    @Column(name = "predicaat")
    private Short predicaatId;
    @Column(name = "predicaatnaamgebruik")
    private Short predicaatNaamgebruikId;
    @Column(name = "naderebijhaard")
    private Short nadereBijhoudingsaard;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "AandVerblijfsr")
    private Verblijfsrecht verblijfsrecht;
    @Column(name = "naamgebruik")
    private Short naamgebruikId;
    @Column(name = "bijhaard")
    private Short bijhoudingsaardId;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
    private Set<PersoonAdres> persoonAdresSet = new LinkedHashSet<>(0);
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
    private Set<PersoonGeslachtsnaamcomponent> persoonGeslachtsnaamcomponentSet = new LinkedHashSet<>(0);
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
    private Set<PersoonIndicatie> persoonIndicatieSet = new LinkedHashSet<>(0);
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
    private Set<PersoonNationaliteit> persoonNationaliteitSet = new LinkedHashSet<>(0);
    /**
     * Onderzoeken moeten EAGER worden geladen ivm uni-directionele koppeling van {@link GegevenInOnderzoek} naar
     * {@link DeltaEntiteit} door {@link GegevenInOnderzoekListener}. Dit wordt gedaan ivm @Any in de
     * {@link GegevenInOnderzoek}.
     */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
    private Set<PersoonOnderzoek> persoonOnderzoekSet = new LinkedHashSet<>(0);
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
    private Set<PersoonReisdocument> persoonReisdocumentSet = new LinkedHashSet<>(0);
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
    private Set<PersoonVerificatie> persoonVerificatieSet = new LinkedHashSet<>(0);
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
    private Set<PersoonVerstrekkingsbeperking> persoonVerstrekkingsbeperkingSet = new LinkedHashSet<>(0);
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
    private Set<PersoonVoornaam> persoonVoornaamSet = new LinkedHashSet<>(0);
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
    private Set<PersoonSamengesteldeNaamHistorie> persoonSamengesteldeNaamHistorieSet = new LinkedHashSet<>(0);
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
    private Set<PersoonNaamgebruikHistorie> persoonNaamgebruikHistorieSet = new LinkedHashSet<>(0);
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
    private Set<PersoonDeelnameEuVerkiezingenHistorie> persoonDeelnameEuVerkiezingenHistorieSet = new LinkedHashSet<>(0);
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
    private Set<PersoonGeboorteHistorie> persoonGeboorteHistorieSet = new LinkedHashSet<>(0);
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
    private Set<PersoonGeslachtsaanduidingHistorie> persoonGeslachtsaanduidingHistorieSet = new LinkedHashSet<>(0);
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
    private Set<PersoonIDHistorie> persoonIDHistorieSet = new LinkedHashSet<>(0);
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
    private Set<PersoonBijhoudingHistorie> persoonBijhoudingHistorieSet = new LinkedHashSet<>(0);
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
    private Set<PersoonMigratieHistorie> persoonMigratieHistorieSet = new LinkedHashSet<>(0);
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
    private Set<PersoonInschrijvingHistorie> persoonInschrijvingHistorieSet = new LinkedHashSet<>(0);
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
    private Set<PersoonNummerverwijzingHistorie> persoonNummerverwijzingHistorieSet = new LinkedHashSet<>(0);
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
    private Set<PersoonOverlijdenHistorie> persoonOverlijdenHistorieSet = new LinkedHashSet<>(0);
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
    private Set<PersoonPersoonskaartHistorie> persoonPersoonskaartHistorieSet = new LinkedHashSet<>(0);
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
    private Set<PersoonVerblijfsrechtHistorie> persoonVerblijfsrechtHistorieSet = new LinkedHashSet<>(0);
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
    private Set<PersoonUitsluitingKiesrechtHistorie> persoonUitsluitingKiesrechtHistorieSet = new LinkedHashSet<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
    private Set<PersoonAfgeleidAdministratiefHistorie> persoonAfgeleidAdministratiefHistorieSet = new LinkedHashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    private Set<Lo3Bericht> lo3Berichten = new LinkedHashSet<>(0);

    // bi-directional many-to-one association to Betrokkenheid
    /* Bij wijziging veld-naam ook de string constante daarvoor aanpassen */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
    private Set<Betrokkenheid> betrokkenheidSet = new LinkedHashSet<>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = PERSOON_ATTRIBUUT, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
    private Set<Stapel> stapels = new LinkedHashSet<>(0);

    /**
     * JPA default constructor.
     */
    protected Persoon() {
    }

    /**
     * Maakt een Persoon object.
     *
     * @param soortPersoon
     *            het soort persoon
     */
    public Persoon(final SoortPersoon soortPersoon) {
        setSoortPersoon(soortPersoon);
    }

    /**
     * Geef de Id waarde voor de entiteit. Intern is de Id een Long voor integratie met GegevenInOnderzoek. De waarde
     * wordt geconverteerd naar een Integer.
     *
     * @return de Id waarde.
     */
    public Integer getId() {
        return convertLongNaarInteger(id);
    }

    /**
     * Zet de Id waarde voor de entiteit. Intern wordt de Id waarde geconverteert naar een Long voor integratie met
     * GegevenInOnderzoek.
     *
     * @param id
     *            de Id waarde.
     */
    public void setId(final Integer id) {
        this.id = convertIntegerNaarLong(id);
    }

    /**
     * Geef de waarde van administratienummer.
     *
     * @return administratienummer
     */
    public Long getAdministratienummer() {
        return administratienummer;
    }

    /**
     * Zet de waarde van administratienummer.
     *
     * @param administratienummer
     *            administratienummer
     */
    public void setAdministratienummer(final Long administratienummer) {
        this.administratienummer = administratienummer;
    }

    /**
     * Geef de waarde van administratieve handeling.
     *
     * @return administratieve handeling
     */
    public AdministratieveHandeling getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    /**
     * Zet de waarde van administratieve handeling.
     *
     * @param administratieveHandeling
     *            administratieve handeling
     */
    public void setAdministratieveHandeling(final AdministratieveHandeling administratieveHandeling) {
        this.administratieveHandeling = administratieveHandeling;
    }

    /**
     * Geef de waarde van sorteervolgorde.
     *
     * @return sorteervolgorde
     */
    public Short getSorteervolgorde() {
        return sorteervolgorde;
    }

    /**
     * Zet de waarde van sorteervolgorde.
     *
     * @param sorteervolgorde
     *            sorteervolgorde
     */
    public void setSorteervolgorde(final Short sorteervolgorde) {
        this.sorteervolgorde = sorteervolgorde;
    }

    /**
     * Geef de waarde van buitenlands adres regel1 migratie.
     *
     * @return buitenlands adres regel1 migratie
     */
    public String getBuitenlandsAdresRegel1Migratie() {
        return buitenlandsAdresRegel1Migratie;
    }

    /**
     * Zet de waarde van buitenlands adres regel1 migratie.
     *
     * @param buitenlandsAdresRegel1Migratie
     *            buitenlands adres regel1 migratie
     */
    public void setBuitenlandsAdresRegel1Migratie(final String buitenlandsAdresRegel1Migratie) {
        Validatie.controleerOpLegeWaarden("buitenlandsAdresRegel1Migratie mag geen lege string zijn", buitenlandsAdresRegel1Migratie);
        this.buitenlandsAdresRegel1Migratie = buitenlandsAdresRegel1Migratie;
    }

    /**
     * Geef de waarde van buitenlands adres regel2 migratie.
     *
     * @return buitenlands adres regel2 migratie
     */
    public String getBuitenlandsAdresRegel2Migratie() {
        return buitenlandsAdresRegel2Migratie;
    }

    /**
     * Zet de waarde van buitenlands adres regel2 migratie.
     *
     * @param buitenlandsAdresRegel2Migratie
     *            buitenlands adres regel2 migratie
     */
    public void setBuitenlandsAdresRegel2Migratie(final String buitenlandsAdresRegel2Migratie) {
        Validatie.controleerOpLegeWaarden("buitenlandsAdresRegel2Migratie mag geen lege string zijn", buitenlandsAdresRegel2Migratie);
        this.buitenlandsAdresRegel2Migratie = buitenlandsAdresRegel2Migratie;
    }

    /**
     * Geef de waarde van buitenlands adres regel3 migratie.
     *
     * @return buitenlands adres regel3 migratie
     */
    public String getBuitenlandsAdresRegel3Migratie() {
        return buitenlandsAdresRegel3Migratie;
    }

    /**
     * Zet de waarde van buitenlands adres regel3 migratie.
     *
     * @param buitenlandsAdresRegel3Migratie
     *            buitenlands adres regel3 migratie
     */
    public void setBuitenlandsAdresRegel3Migratie(final String buitenlandsAdresRegel3Migratie) {
        Validatie.controleerOpLegeWaarden("buitenlandsAdresRegel3Migratie mag geen lege string zijn", buitenlandsAdresRegel3Migratie);
        this.buitenlandsAdresRegel3Migratie = buitenlandsAdresRegel3Migratie;
    }

    /**
     * Geef de waarde van buitenlands adres regel4 migratie.
     *
     * @return buitenlands adres regel4 migratie
     */
    public String getBuitenlandsAdresRegel4Migratie() {
        return buitenlandsAdresRegel4Migratie;
    }

    /**
     * Zet de waarde van buitenlands adres regel4 migratie.
     *
     * @param buitenlandsAdresRegel4Migratie
     *            buitenlands adres regel4 migratie
     */
    public void setBuitenlandsAdresRegel4Migratie(final String buitenlandsAdresRegel4Migratie) {
        Validatie.controleerOpLegeWaarden("buitenlandsAdresRegel4Migratie mag geen lege string zijn", buitenlandsAdresRegel4Migratie);
        this.buitenlandsAdresRegel4Migratie = buitenlandsAdresRegel4Migratie;
    }

    /**
     * Geef de waarde van buitenlands adres regel5 migratie.
     *
     * @return buitenlands adres regel5 migratie
     */
    public String getBuitenlandsAdresRegel5Migratie() {
        return buitenlandsAdresRegel5Migratie;
    }

    /**
     * Zet de waarde van buitenlands adres regel5 migratie.
     *
     * @param buitenlandsAdresRegel5Migratie
     *            buitenlands adres regel5 migratie
     */
    public void setBuitenlandsAdresRegel5Migratie(final String buitenlandsAdresRegel5Migratie) {
        Validatie.controleerOpLegeWaarden("buitenlandsAdresRegel5Migratie mag geen lege string zijn", buitenlandsAdresRegel5Migratie);
        this.buitenlandsAdresRegel5Migratie = buitenlandsAdresRegel5Migratie;
    }

    /**
     * Geef de waarde van buitenlands adres regel6 migratie.
     *
     * @return buitenlands adres regel6 migratie
     */
    public String getBuitenlandsAdresRegel6Migratie() {
        return buitenlandsAdresRegel6Migratie;
    }

    /**
     * Zet de waarde van buitenlands adres regel6 migratie.
     *
     * @param buitenlandsAdresRegel6Migratie
     *            buitenlands adres regel6 migratie
     */
    public void setBuitenlandsAdresRegel6Migratie(final String buitenlandsAdresRegel6Migratie) {
        Validatie.controleerOpLegeWaarden("buitenlandsAdresRegel6Migratie mag geen lege string zijn", buitenlandsAdresRegel6Migratie);
        this.buitenlandsAdresRegel6Migratie = buitenlandsAdresRegel6Migratie;
    }

    /**
     * Geef de waarde van buitenlandse plaats geboorte.
     *
     * @return buitenlandse plaats geboorte
     */
    public String getBuitenlandsePlaatsGeboorte() {
        return buitenlandsePlaatsGeboorte;
    }

    /**
     * Zet de waarde van buitenlandse plaats geboorte.
     *
     * @param buitenlandsePlaatsGeboorte
     *            buitenlandse plaats geboorte
     */
    public void setBuitenlandsePlaatsGeboorte(final String buitenlandsePlaatsGeboorte) {
        Validatie.controleerOpLegeWaarden("buitenlandsePlaatsGeboorte mag geen lege string zijn", buitenlandsePlaatsGeboorte);
        this.buitenlandsePlaatsGeboorte = buitenlandsePlaatsGeboorte;
    }

    /**
     * Geef de waarde van buitenlandse plaats overlijden.
     *
     * @return buitenlandse plaats overlijden
     */
    public String getBuitenlandsePlaatsOverlijden() {
        return buitenlandsePlaatsOverlijden;
    }

    /**
     * Zet de waarde van buitenlandse plaats overlijden.
     *
     * @param buitenlandsePlaatsOverlijden
     *            buitenlandse plaats overlijden
     */
    public void setBuitenlandsePlaatsOverlijden(final String buitenlandsePlaatsOverlijden) {
        Validatie.controleerOpLegeWaarden("buitenlandsePlaatsOverlijden mag geen lege string zijn", buitenlandsePlaatsOverlijden);
        this.buitenlandsePlaatsOverlijden = buitenlandsePlaatsOverlijden;
    }

    /**
     * Geef de waarde van buitenlandse regio geboorte.
     *
     * @return buitenlandse regio geboorte
     */
    public String getBuitenlandseRegioGeboorte() {
        return buitenlandseRegioGeboorte;
    }

    /**
     * Zet de waarde van buitenlandse regio geboorte.
     *
     * @param buitenlandseRegioGeboorte
     *            buitenlandse regio geboorte
     */
    public void setBuitenlandseRegioGeboorte(final String buitenlandseRegioGeboorte) {
        Validatie.controleerOpLegeWaarden("buitenlandseRegioGeboorte mag geen lege string zijn", buitenlandseRegioGeboorte);
        this.buitenlandseRegioGeboorte = buitenlandseRegioGeboorte;
    }

    /**
     * Geef de waarde van buitenlandse regio overlijden.
     *
     * @return buitenlandse regio overlijden
     */
    public String getBuitenlandseRegioOverlijden() {
        return buitenlandseRegioOverlijden;
    }

    /**
     * Zet de waarde van buitenlandse regio overlijden.
     *
     * @param buitenlandseRegioOverlijden
     *            buitenlandse regio overlijden
     */
    public void setBuitenlandseRegioOverlijden(final String buitenlandseRegioOverlijden) {
        Validatie.controleerOpLegeWaarden("buitenlandseRegioOverlijden mag geen lege string zijn", buitenlandseRegioOverlijden);
        this.buitenlandseRegioOverlijden = buitenlandseRegioOverlijden;
    }

    /**
     * Geef de waarde van burgerservicenummer.
     *
     * @return burgerservicenummer
     */
    public Integer getBurgerservicenummer() {
        return burgerservicenummer;
    }

    /**
     * Zet de waarde van burgerservicenummer.
     *
     * @param burgerservicenummer
     *            burgerservicenummer
     */
    public void setBurgerservicenummer(final Integer burgerservicenummer) {
        this.burgerservicenummer = burgerservicenummer;
    }

    /**
     * Geef de waarde van datum aanleiding aanpassing deelname eu verkiezing.
     *
     * @return datum aanleiding aanpassing deelname eu verkiezing
     */
    public Integer getDatumAanleidingAanpassingDeelnameEuVerkiezing() {
        return datumAanleidingAanpassingDeelnameEuVerkiezing;
    }

    /**
     * Zet de waarde van datum aanleiding aanpassing deelname eu verkiezingen.
     *
     * @param datumAanleidingAanpassingDeelnameEUVerkiezing
     *            datum aanleiding aanpassing deelname eu verkiezingen
     */
    public void setDatumAanleidingAanpassingDeelnameEuVerkiezingen(final Integer datumAanleidingAanpassingDeelnameEUVerkiezing) {
        datumAanleidingAanpassingDeelnameEuVerkiezing = datumAanleidingAanpassingDeelnameEUVerkiezing;
    }

    /**
     * Geef de waarde van datum aanvang verblijfsrecht.
     *
     * @return datum aanvang verblijfsrecht
     */
    public Integer getDatumAanvangVerblijfsrecht() {
        return datumAanvangVerblijfsrecht;
    }

    /**
     * Zet de waarde van datum aanvang verblijfsrecht.
     *
     * @param datumAanvangVerblijfsrecht
     *            datum aanvang verblijfsrecht
     */
    public void setDatumAanvangVerblijfsrecht(final Integer datumAanvangVerblijfsrecht) {
        this.datumAanvangVerblijfsrecht = datumAanvangVerblijfsrecht;
    }

    /**
     * Geeft de waarde van datumMededelingVerblijfsrecht.
     *
     * @return datumMededelingVerblijfsrecht
     */
    public Integer getDatumMededelingVerblijfsrecht() {
        return datumMededelingVerblijfsrecht;
    }

    /**
     * Zet de waarde van datumMededelingVerblijfsrecht.
     *
     * @param datumMededelingVerblijfsrecht
     *            datumMededelingVerblijfsrecht
     */
    public void setDatumMededelingVerblijfsrecht(final Integer datumMededelingVerblijfsrecht) {
        this.datumMededelingVerblijfsrecht = datumMededelingVerblijfsrecht;
    }

    /**
     * Geef de waarde van datum voorzien einde uitsluiting eu verkiezingen.
     *
     * @return datum voorzien einde uitsluiting eu verkiezingen
     */
    public Integer getDatumVoorzienEindeUitsluitingEuVerkiezingen() {
        return datumVoorzienEindeUitsluitingEuVerkiezingen;
    }

    /**
     * Zet de waarde van datum voorzien einde uitsluiting eu verkiezingen.
     *
     * @param datumEindeUitsluitingEUKiesrecht
     *            datum voorzien einde uitsluiting eu verkiezingen
     */
    public void setDatumVoorzienEindeUitsluitingEuVerkiezingen(final Integer datumEindeUitsluitingEUKiesrecht) {
        datumVoorzienEindeUitsluitingEuVerkiezingen = datumEindeUitsluitingEUKiesrecht;
    }

    /**
     * Geef de waarde van datum voorzien einde uitsluiting kiesrecht.
     *
     * @return datum voorzien einde uitsluiting kiesrecht
     */
    public Integer getDatumVoorzienEindeUitsluitingKiesrecht() {
        return datumVoorzienEindeUitsluitingKiesrecht;
    }

    /**
     * Zet de waarde van datum voorzien einde uitsluiting kiesrecht.
     *
     * @param datumEindeUitsluitingKiesrecht
     *            datum voorzien einde uitsluiting kiesrecht
     */
    public void setDatumVoorzienEindeUitsluitingKiesrecht(final Integer datumEindeUitsluitingKiesrecht) {
        datumVoorzienEindeUitsluitingKiesrecht = datumEindeUitsluitingKiesrecht;
    }

    /**
     * Geef de waarde van datum geboorte.
     *
     * @return datum geboorte
     */
    public Integer getDatumGeboorte() {
        return datumGeboorte;
    }

    /**
     * Zet de waarde van datum geboorte.
     *
     * @param datumGeboorte
     *            datum geboorte
     */
    public void setDatumGeboorte(final Integer datumGeboorte) {
        this.datumGeboorte = datumGeboorte;
    }

    /**
     * Geef de waarde van datum inschrijving.
     *
     * @return datum inschrijving
     */
    public Integer getDatumInschrijving() {
        return datumInschrijving;
    }

    /**
     * Zet de waarde van datum inschrijving.
     *
     * @param datumInschrijving
     *            datum inschrijving
     */
    public void setDatumInschrijving(final Integer datumInschrijving) {
        this.datumInschrijving = datumInschrijving;
    }

    /**
     * Geef de waarde van datum overlijden.
     *
     * @return datum overlijden
     */
    public Integer getDatumOverlijden() {
        return datumOverlijden;
    }

    /**
     * Zet de waarde van datum overlijden.
     *
     * @param datumOverlijden
     *            datum overlijden
     */
    public void setDatumOverlijden(final Integer datumOverlijden) {
        this.datumOverlijden = datumOverlijden;
    }

    /**
     * Geef de waarde van datum voorzien einde verblijfsrecht.
     *
     * @return datum voorzien einde verblijfsrecht
     */
    public Integer getDatumVoorzienEindeVerblijfsrecht() {
        return datumVoorzienEindeVerblijfsrecht;
    }

    /**
     * Zet de waarde van datum voorzien einde verblijfsrecht.
     *
     * @param datumVoorzienEindeVerblijfsrecht
     *            datum voorzien einde verblijfsrecht
     */
    public void setDatumVoorzienEindeVerblijfsrecht(final Integer datumVoorzienEindeVerblijfsrecht) {
        this.datumVoorzienEindeVerblijfsrecht = datumVoorzienEindeVerblijfsrecht;
    }

    /**
     * Geef de waarde van geslachtsnaamstam.
     *
     * @return geslachtsnaamstam
     */
    public String getGeslachtsnaamstam() {
        return geslachtsnaamstam;
    }

    /**
     * Zet de waarde van geslachtsnaamstam.
     *
     * @param geslachtsnaamstam
     *            geslachtsnaamstam
     */
    public void setGeslachtsnaamstam(final String geslachtsnaamstam) {
        Validatie.controleerOpLegeWaarden("geslachtsnaamstam mag geen lege string zijn", geslachtsnaamstam);
        this.geslachtsnaamstam = geslachtsnaamstam;
    }

    /**
     * Geef de waarde van geslachtsnaamstam naamgebruik.
     *
     * @return geslachtsnaamstam naamgebruik
     */
    public String getGeslachtsnaamstamNaamgebruik() {
        return geslachtsnaamstamNaamgebruik;
    }

    /**
     * Zet de waarde van geslachtsnaamstam naamgebruik.
     *
     * @param geslachtsnaamstamNaamgebruik
     *            geslachtsnaamstam naamgebruik
     */
    public void setGeslachtsnaamstamNaamgebruik(final String geslachtsnaamstamNaamgebruik) {
        Validatie.controleerOpLegeWaarden("geslachtsnaamstamNaamgebruik mag geen lege string zijn", geslachtsnaamstamNaamgebruik);
        this.geslachtsnaamstamNaamgebruik = geslachtsnaamstamNaamgebruik;
    }

    /**
     * Geef de waarde van indicatie naamgebruik afgeleid.
     *
     * @return indicatie naamgebruik afgeleid
     */
    public Boolean getIndicatieNaamgebruikAfgeleid() {
        return indicatieNaamgebruikAfgeleid;
    }

    /**
     * Zet de waarde van indicatie naamgebruik afgeleid.
     *
     * @param indicatieNaamgebruikAfgeleid
     *            indicatie naamgebruik afgeleid
     */
    public void setIndicatieNaamgebruikAfgeleid(final Boolean indicatieNaamgebruikAfgeleid) {
        this.indicatieNaamgebruikAfgeleid = indicatieNaamgebruikAfgeleid;
    }

    /**
     * Geef de waarde van indicatie afgeleid.
     *
     * @return indicatie afgeleid
     */
    public Boolean getIndicatieAfgeleid() {
        return indicatieAfgeleid;
    }

    /**
     * Zet de waarde van indicatie afgeleid.
     *
     * @param indicatieAfgeleid
     *            indicatie afgeleid
     */
    public void setIndicatieAfgeleid(final Boolean indicatieAfgeleid) {
        this.indicatieAfgeleid = indicatieAfgeleid;
    }

    /**
     * Geef de waarde van indicatie deelname eu verkiezingen.
     *
     * @return indicatie deelname eu verkiezingen
     */
    public Boolean getIndicatieDeelnameEuVerkiezingen() {
        return indicatieDeelnameEuVerkiezingen;
    }

    /**
     * Zet de waarde van indicatie deelname eu verkiezingen.
     *
     * @param indicatieDeelnameEuVerkiezingen
     *            indicatie deelname eu verkiezingen
     */
    public void setIndicatieDeelnameEuVerkiezingen(final Boolean indicatieDeelnameEuVerkiezingen) {
        this.indicatieDeelnameEuVerkiezingen = indicatieDeelnameEuVerkiezingen;
    }

    /**
     * Geef de waarde van indicatie namenreeks.
     *
     * @return indicatie namenreeks
     */
    public Boolean getIndicatieNamenreeks() {
        return indicatieNamenreeks;
    }

    /**
     * Zet de waarde van indicatie namenreeks.
     *
     * @param indicatieNamenreeks
     *            indicatie namenreeks
     */
    public void setIndicatieNamenreeks(final Boolean indicatieNamenreeks) {
        this.indicatieNamenreeks = indicatieNamenreeks;
    }

    /**
     * Geef de waarde van indicatie onverwerkt document aanwezig.
     *
     * @return indicatie onverwerkt document aanwezig
     */
    public Boolean getIndicatieOnverwerktDocumentAanwezig() {
        return indicatieOnverwerktDocumentAanwezig;
    }

    /**
     * Zet de waarde van indicatie onverwerkt document aanwezig.
     *
     * @param indicatieOnverwerktDocumentAanwezig
     *            indicatie onverwerkt document aanwezig
     */
    public void setIndicatieOnverwerktDocumentAanwezig(final Boolean indicatieOnverwerktDocumentAanwezig) {
        this.indicatieOnverwerktDocumentAanwezig = indicatieOnverwerktDocumentAanwezig;
    }

    /**
     * Geef de waarde van indicatie persoonskaart volledig geconverteerd.
     *
     * @return indicatie persoonskaart volledig geconverteerd
     */
    public Boolean getIndicatiePersoonskaartVolledigGeconverteerd() {
        return indicatiePersoonskaartVolledigGeconverteerd;
    }

    /**
     * Zet de waarde van indicatie persoonskaart volledig geconverteerd.
     *
     * @param indicatiePersoonskaartVolledigGeconverteerd
     *            indicatie persoonskaart volledig geconverteerd
     */
    public void setIndicatiePersoonskaartVolledigGeconverteerd(final Boolean indicatiePersoonskaartVolledigGeconverteerd) {
        this.indicatiePersoonskaartVolledigGeconverteerd = indicatiePersoonskaartVolledigGeconverteerd;
    }

    /**
     * Geef de waarde van indicatie uitsluiting kiesrecht.
     *
     * @return indicatie uitsluiting kiesrecht
     */
    public Boolean getIndicatieUitsluitingKiesrecht() {
        return indicatieUitsluitingKiesrecht;
    }

    /**
     * Zet de waarde van indicatie uitsluiting kiesrecht.
     *
     * @param indicatieUitsluitingKiesrecht
     *            indicatie uitsluiting kiesrecht
     */
    public void setIndicatieUitsluitingKiesrecht(final Boolean indicatieUitsluitingKiesrecht) {
        this.indicatieUitsluitingKiesrecht = indicatieUitsluitingKiesrecht;
    }

    /**
     * Geef de waarde van omschrijving geboortelocatie.
     *
     * @return omschrijving geboortelocatie
     */
    public String getOmschrijvingGeboortelocatie() {
        return omschrijvingGeboortelocatie;
    }

    /**
     * Zet de waarde van omschrijving geboortelocatie.
     *
     * @param omschrijvingGeboortelocatie
     *            omschrijving geboortelocatie
     */
    public void setOmschrijvingGeboortelocatie(final String omschrijvingGeboortelocatie) {
        Validatie.controleerOpLegeWaarden("omschrijvingGeboortelocatie mag geen lege string zijn", omschrijvingGeboortelocatie);
        this.omschrijvingGeboortelocatie = omschrijvingGeboortelocatie;
    }

    /**
     * Geef de waarde van omschrijving locatie overlijden.
     *
     * @return omschrijving locatie overlijden
     */
    public String getOmschrijvingLocatieOverlijden() {
        return omschrijvingLocatieOverlijden;
    }

    /**
     * Zet de waarde van omschrijving locatie overlijden.
     *
     * @param omschrijvingLocatieOverlijden
     *            omschrijving locatie overlijden
     */
    public void setOmschrijvingLocatieOverlijden(final String omschrijvingLocatieOverlijden) {
        Validatie.controleerOpLegeWaarden("omschrijvingLocatieOverlijden mag geen lege string zijn", omschrijvingLocatieOverlijden);
        this.omschrijvingLocatieOverlijden = omschrijvingLocatieOverlijden;
    }

    /**
     * Geef de waarde van scheidingsteken.
     *
     * @return scheidingsteken
     */
    public Character getScheidingsteken() {
        return scheidingsteken;
    }

    /**
     * Zet de waarde van scheidingsteken.
     *
     * @param scheidingsteken
     *            scheidingsteken
     */
    public void setScheidingsteken(final Character scheidingsteken) {
        this.scheidingsteken = scheidingsteken;
    }

    /**
     * Geef de waarde van scheidingsteken naamgebruik.
     *
     * @return scheidingsteken naamgebruik
     */
    public Character getScheidingstekenNaamgebruik() {
        return scheidingstekenNaamgebruik;
    }

    /**
     * Zet de waarde van scheidingsteken naamgebruik.
     *
     * @param scheidingstekenNaamgebruik
     *            scheidingsteken naamgebruik
     */
    public void setScheidingstekenNaamgebruik(final Character scheidingstekenNaamgebruik) {
        this.scheidingstekenNaamgebruik = scheidingstekenNaamgebruik;
    }

    /**
     * Geef de waarde van tijdstip laatste wijziging.
     *
     * @return tijdstip laatste wijziging
     */
    public Timestamp getTijdstipLaatsteWijziging() {
        return Kopieer.timestamp(tijdstipLaatsteWijziging);
    }

    /**
     * Zet de waarde van tijdstip laatste wijziging.
     *
     * @param tijdstipLaatsteWijziging
     *            tijdstip laatste wijziging
     */
    public void setTijdstipLaatsteWijziging(final Timestamp tijdstipLaatsteWijziging) {
        this.tijdstipLaatsteWijziging = Kopieer.timestamp(tijdstipLaatsteWijziging);
    }

    /**
     * Geef de waarde van tijdstip laatste wijziging gba systematiek.
     *
     * @return tijdstip laatste wijziging gba systematiek
     */
    public Timestamp getTijdstipLaatsteWijzigingGbaSystematiek() {
        return Kopieer.timestamp(tijdstipLaatsteWijzigingGbaSystematiek);
    }

    /**
     * Zet de waarde van tijdstip laatste wijziging gba systematiek.
     *
     * @param tijdstipLaatsteWijzigingGbaSystematiek
     *            tijdstip laatste wijziging gba systematiek
     */
    public void setTijdstipLaatsteWijzigingGbaSystematiek(final Timestamp tijdstipLaatsteWijzigingGbaSystematiek) {
        this.tijdstipLaatsteWijzigingGbaSystematiek = Kopieer.timestamp(tijdstipLaatsteWijzigingGbaSystematiek);
    }

    /**
     * Geef de waarde van versienummer.
     *
     * @return versienummer
     */
    public Long getVersienummer() {
        return versienummer;
    }

    /**
     * Zet de waarde van versienummer.
     *
     * @param versienummer
     *            versienummer
     */
    public void setVersienummer(final Long versienummer) {
        this.versienummer = versienummer;
    }

    /**
     * Geef de waarde van datumtijdstempel.
     *
     * @return datumtijdstempel
     */
    public Timestamp getDatumtijdstempel() {
        return Kopieer.timestamp(datumtijdstempel);
    }

    /**
     * Zet de waarde van datumtijdstempel.
     *
     * @param datumtijdstempel
     *            datumtijdstempel
     */
    public void setDatumtijdstempel(final Timestamp datumtijdstempel) {
        this.datumtijdstempel = Kopieer.timestamp(datumtijdstempel);
    }

    /**
     * Geef de waarde van voornamen.
     *
     * @return voornamen
     */
    public String getVoornamen() {
        return voornamen;
    }

    /**
     * Zet de waarde van voornamen.
     *
     * @param voornamen
     *            voornamen
     */
    public void setVoornamen(final String voornamen) {
        Validatie.controleerOpLegeWaarden("voornamen mag geen lege string zijn", voornamen);
        this.voornamen = voornamen;
    }

    /**
     * Geef de waarde van voornamen naamgebruik.
     *
     * @return voornamen naamgebruik
     */
    public String getVoornamenNaamgebruik() {
        return voornamenNaamgebruik;
    }

    /**
     * Zet de waarde van voornamen naamgebruik.
     *
     * @param voornamenNaamgebruik
     *            voornamen naamgebruik
     */
    public void setVoornamenNaamgebruik(final String voornamenNaamgebruik) {
        Validatie.controleerOpLegeWaarden("voornamenNaamgebruik mag geen lege string zijn", voornamenNaamgebruik);
        this.voornamenNaamgebruik = voornamenNaamgebruik;
    }

    /**
     * Geef de waarde van adellijke titel naamgebruik.
     *
     * @return adellijke titel naamgebruik
     */
    public AdellijkeTitel getAdellijkeTitelNaamgebruik() {
        return AdellijkeTitel.parseId(adellijkeTitelNaamgebruikId);
    }

    /**
     * Zet de waarde van adellijke titel naamgebruik.
     *
     * @param naamgebruikTitel
     *            adellijke titel naamgebruik
     */
    public void setAdellijkeTitelNaamgebruik(final AdellijkeTitel naamgebruikTitel) {
        if (naamgebruikTitel == null) {
            adellijkeTitelNaamgebruikId = null;
        } else {
            adellijkeTitelNaamgebruikId = naamgebruikTitel.getId();
        }
    }

    /**
     * Geef de waarde van soort migratie.
     *
     * @return soort migratie
     */
    public SoortMigratie getSoortMigratie() {
        return SoortMigratie.parseId(soortMigratieId);
    }

    /**
     * Zet de waarde van soort migratie.
     *
     * @param soortMigratie
     *            soort migratie
     */
    public void setSoortMigratie(final SoortMigratie soortMigratie) {
        if (soortMigratie == null) {
            soortMigratieId = null;
        } else {
            soortMigratieId = soortMigratie.getId();
        }
    }

    /**
     * Geef de waarde van reden wijziging migratie.
     *
     * @return reden wijziging migratie
     */
    public RedenWijzigingVerblijf getRedenWijzigingMigratie() {
        return redenWijzigingMigratie;
    }

    /**
     * Zet de waarde van reden wijziging migratie.
     *
     * @param redenWijzigingMigratie
     *            reden wijziging migratie
     */
    public void setRedenWijzigingMigratie(final RedenWijzigingVerblijf redenWijzigingMigratie) {
        this.redenWijzigingMigratie = redenWijzigingMigratie;
    }

    /**
     * Geef de waarde van aangever migratie.
     *
     * @return aangever migratie
     */
    public Aangever getAangeverMigratie() {
        return aangeverMigratie;
    }

    /**
     * Zet de waarde van aangever migratie.
     *
     * @param aangeverMigratie
     *            aangever migratie
     */
    public void setAangeverMigratie(final Aangever aangeverMigratie) {
        this.aangeverMigratie = aangeverMigratie;
    }

    /**
     * Geef de waarde van voorvoegsel.
     *
     * @return voorvoegsel
     */
    public String getVoorvoegsel() {
        return voorvoegsel;
    }

    /**
     * Zet de waarde van voorvoegsel.
     *
     * @param voorvoegsel
     *            voorvoegsel
     */
    public void setVoorvoegsel(final String voorvoegsel) {
        Validatie.controleerOpLegeWaarden("voorvoegsel mag geen lege string zijn", voorvoegsel);
        this.voorvoegsel = voorvoegsel;
    }

    /**
     * Geef de waarde van voorvoegsel naamgebruik.
     *
     * @return voorvoegsel naamgebruik
     */
    public String getVoorvoegselNaamgebruik() {
        return voorvoegselNaamgebruik;
    }

    /**
     * Zet de waarde van voorvoegsel naamgebruik.
     *
     * @param voorvoegselNaamgebruik
     *            voorvoegsel naamgebruik
     */
    public void setVoorvoegselNaamgebruik(final String voorvoegselNaamgebruik) {
        Validatie.controleerOpLegeWaarden("voorvoegselNaamgebruik mag geen lege string zijn", voorvoegselNaamgebruik);
        this.voorvoegselNaamgebruik = voorvoegselNaamgebruik;
    }

    /**
     * Geef de waarde van geslachtsaanduiding.
     *
     * @return geslachtsaanduiding
     */
    public Geslachtsaanduiding getGeslachtsaanduiding() {
        return Geslachtsaanduiding.parseId(geslachtsaanduidingId);
    }

    /**
     * Zet de waarde van geslachtsaanduiding.
     *
     * @param geslachtsaanduiding
     *            geslachtsaanduiding
     */
    public void setGeslachtsaanduiding(final Geslachtsaanduiding geslachtsaanduiding) {
        if (geslachtsaanduiding == null) {
            geslachtsaanduidingId = null;
        } else {
            geslachtsaanduidingId = geslachtsaanduiding.getId();
        }
    }

    /**
     * Geef de waarde van land of gebied geboorte.
     *
     * @return land of gebied geboorte
     */
    public LandOfGebied getLandOfGebiedGeboorte() {
        return landOfGebiedGeboorte;
    }

    /**
     * Zet de waarde van land of gebied geboorte.
     *
     * @param landOfGebiedGeboorte
     *            land of gebied geboorte
     */
    public void setLandOfGebiedGeboorte(final LandOfGebied landOfGebiedGeboorte) {
        this.landOfGebiedGeboorte = landOfGebiedGeboorte;
    }

    /**
     * Geef de waarde van land of gebied migratie.
     *
     * @return land of gebied migratie
     */
    public LandOfGebied getLandOfGebiedMigratie() {
        return landOfGebiedMigratie;
    }

    /**
     * Zet de waarde van land of gebied migratie.
     *
     * @param landOfGebiedMigratie
     *            land of gebied migratie
     */
    public void setLandOfGebiedMigratie(final LandOfGebied landOfGebiedMigratie) {
        this.landOfGebiedMigratie = landOfGebiedMigratie;
    }

    /**
     * Geef de waarde van land of gebied overlijden.
     *
     * @return land of gebied overlijden
     */
    public LandOfGebied getLandOfGebiedOverlijden() {
        return landOfGebiedOverlijden;
    }

    /**
     * Zet de waarde van land of gebied overlijden.
     *
     * @param landOfGebiedOverlijden
     *            land of gebied overlijden
     */
    public void setLandOfGebiedOverlijden(final LandOfGebied landOfGebiedOverlijden) {
        this.landOfGebiedOverlijden = landOfGebiedOverlijden;
    }

    /**
     * Geef de waarde van gemeente persoonskaart.
     *
     * @return gemeente persoonskaart
     */
    public Partij getGemeentePersoonskaart() {
        return gemeentePersoonskaart;
    }

    /**
     * Zet de waarde van gemeente persoonskaart.
     *
     * @param gemeentePersoonskaart
     *            gemeente persoonskaart
     */
    public void setGemeentePersoonskaart(final Partij gemeentePersoonskaart) {
        this.gemeentePersoonskaart = gemeentePersoonskaart;
    }

    /**
     * Geef de waarde van gemeente overlijden.
     *
     * @return gemeente overlijden
     */
    public Gemeente getGemeenteOverlijden() {
        return gemeenteOverlijden;
    }

    /**
     * Zet de waarde van gemeente overlijden.
     *
     * @param gemeenteOverlijden
     *            gemeente overlijden
     */
    public void setGemeenteOverlijden(final Gemeente gemeenteOverlijden) {
        this.gemeenteOverlijden = gemeenteOverlijden;
    }

    /**
     * Geef de waarde van gemeente geboorte.
     *
     * @return gemeente geboorte
     */
    public Gemeente getGemeenteGeboorte() {
        return gemeenteGeboorte;
    }

    /**
     * Zet de waarde van gemeente geboorte.
     *
     * @param gemeenteGeboorte
     *            gemeente geboorte
     */
    public void setGemeenteGeboorte(final Gemeente gemeenteGeboorte) {
        this.gemeenteGeboorte = gemeenteGeboorte;
    }

    /**
     * Geef de waarde van bijhoudingspartij.
     *
     * @return bijhoudingspartij
     */
    public Partij getBijhoudingspartij() {
        return bijhoudingspartij;
    }

    /**
     * Zet de waarde van bijhoudingspartij.
     *
     * @param bijhoudingspartij
     *            bijhoudingspartij
     */
    public void setBijhoudingspartij(final Partij bijhoudingspartij) {
        this.bijhoudingspartij = bijhoudingspartij;
    }

    /**
     * Geef de waarde van volgende administratienummer.
     *
     * @return volgende administratienummer
     */
    public Long getVolgendeAdministratienummer() {
        return volgendeAdministratienummer;
    }

    /**
     * Zet de waarde van volgende administratienummer.
     *
     * @param volgendeAdministratienummer
     *            volgende administratienummer
     */
    public void setVolgendeAdministratienummer(final Long volgendeAdministratienummer) {
        this.volgendeAdministratienummer = volgendeAdministratienummer;
    }

    /**
     * Geef de waarde van vorige administratienummer.
     *
     * @return vorige administratienummer
     */
    public Long getVorigeAdministratienummer() {
        return vorigeAdministratienummer;
    }

    /**
     * Zet de waarde van vorige administratienummer.
     *
     * @param vorigeAdministratienummer
     *            vorige administratienummer
     */
    public void setVorigeAdministratienummer(final Long vorigeAdministratienummer) {
        this.vorigeAdministratienummer = vorigeAdministratienummer;
    }

    /**
     * Geef de waarde van volgende burgerservicenummer.
     *
     * @return volgende burgerservicenummer
     */
    public Integer getVolgendeBurgerservicenummer() {
        return volgendeBurgerservicenummer;
    }

    /**
     * Zet de waarde van volgende burgerservicenummer.
     *
     * @param volgendeBurgerservicenummer
     *            volgende burgerservicenummer
     */
    public void setVolgendeBurgerservicenummer(final Integer volgendeBurgerservicenummer) {
        this.volgendeBurgerservicenummer = volgendeBurgerservicenummer;
    }

    /**
     * Geef de waarde van vorige burgerservicenummer.
     *
     * @return vorige burgerservicenummer
     */
    public Integer getVorigeBurgerservicenummer() {
        return vorigeBurgerservicenummer;
    }

    /**
     * Zet de waarde van vorige burgerservicenummer.
     *
     * @param vorigeBurgerservicenummer
     *            vorige burgerservicenummer
     */
    public void setVorigeBurgerservicenummer(final Integer vorigeBurgerservicenummer) {
        this.vorigeBurgerservicenummer = vorigeBurgerservicenummer;
    }

    /**
     * Geef de waarde van woonplaats geboorte.
     *
     * @return woonplaats geboorte
     */
    public String getWoonplaatsGeboorte() {
        return woonplaatsGeboorte;
    }

    /**
     * Zet de waarde van woonplaats geboorte.
     *
     * @param woonplaatsGeboorte
     *            woonplaats geboorte
     */
    public void setWoonplaatsGeboorte(final String woonplaatsGeboorte) {
        this.woonplaatsGeboorte = woonplaatsGeboorte;
    }

    /**
     * Geef de waarde van woonplaats overlijden.
     *
     * @return woonplaats overlijden
     */
    public String getWoonplaatsOverlijden() {
        return woonplaatsOverlijden;
    }

    /**
     * Zet de waarde van woonplaats overlijden.
     *
     * @param woonplaatsOverlijden
     *            woonplaats overlijden
     */
    public void setWoonplaatsOverlijden(final String woonplaatsOverlijden) {
        this.woonplaatsOverlijden = woonplaatsOverlijden;
    }

    /**
     * Geef de waarde van predicaat.
     *
     * @return predicaat
     */
    public Predicaat getPredicaat() {
        return Predicaat.parseId(predicaatId);
    }

    /**
     * Zet de waarde van predicaat.
     *
     * @param predicaat
     *            predicaat
     */
    public void setPredicaat(final Predicaat predicaat) {
        if (predicaat == null) {
            predicaatId = null;
        } else {
            predicaatId = predicaat.getId();
        }
    }

    /**
     * Geef de waarde van predicaat naamgebruik.
     *
     * @return predicaat naamgebruik
     */
    public Predicaat getPredicaatNaamgebruik() {
        return Predicaat.parseId(predicaatNaamgebruikId);
    }

    /**
     * Zet de waarde van predicaat naamgebruik.
     *
     * @param predicaatNaamgebruik
     *            predicaat naamgebruik
     */
    public void setPredicaatNaamgebruik(final Predicaat predicaatNaamgebruik) {
        if (predicaatNaamgebruik == null) {
            predicaatNaamgebruikId = null;
        } else {
            predicaatNaamgebruikId = predicaatNaamgebruik.getId();
        }
    }

    /**
     * Geef de waarde van nadere bijhoudingsaard.
     *
     * @return nadere bijhoudingsaard
     */
    public NadereBijhoudingsaard getNadereBijhoudingsaard() {
        return NadereBijhoudingsaard.parseId(nadereBijhoudingsaard);
    }

    /**
     * Zet de waarde van nadere bijhoudingsaard.
     *
     * @param redenOpschorting
     *            nadere bijhoudingsaard
     */
    public void setNadereBijhoudingsaard(final NadereBijhoudingsaard redenOpschorting) {
        if (redenOpschorting == null) {
            nadereBijhoudingsaard = null;
        } else {
            nadereBijhoudingsaard = redenOpschorting.getId();
        }
    }

    /**
     * Geef de waarde van soort persoon.
     *
     * @return soort persoon
     */
    public SoortPersoon getSoortPersoon() {
        return SoortPersoon.parseId(soortPersoonId);
    }

    /**
     * Zet de waarde van soort persoon.
     *
     * @param soortPersoon
     *            soort persoon
     */
    public void setSoortPersoon(final SoortPersoon soortPersoon) {
        ValidationUtils.controleerOpNullWaarden("soortPersoon mag niet null zijn", soortPersoon);
        soortPersoonId = soortPersoon.getId();
    }

    /**
     * Geef de waarde van verblijfsrecht.
     *
     * @return verblijfsrecht
     */
    public Verblijfsrecht getVerblijfsrecht() {
        return verblijfsrecht;
    }

    /**
     * Zet de waarde van verblijfsrecht.
     *
     * @param verblijfsrecht
     *            verblijfsrecht
     */
    public void setVerblijfsrecht(final Verblijfsrecht verblijfsrecht) {
        this.verblijfsrecht = verblijfsrecht;
    }

    /**
     * Geef de waarde van naamgebruik.
     *
     * @return naamgebruik
     */
    public Naamgebruik getNaamgebruik() {
        return Naamgebruik.parseId(naamgebruikId);
    }

    /**
     * Zet de waarde van naamgebruik.
     *
     * @param naamgebruik
     *            naamgebruik
     */
    public void setNaamgebruik(final Naamgebruik naamgebruik) {
        if (naamgebruik == null) {
            naamgebruikId = null;
        } else {
            naamgebruikId = naamgebruik.getId();
        }
    }

    /**
     * Geef de waarde van persoon adres set.
     *
     * @return persoon adres set
     */
    public Set<PersoonAdres> getPersoonAdresSet() {
        return persoonAdresSet;
    }

    /**
     * Toevoegen van een persoon adres.
     *
     * @param persoonAdres
     *            persoon adres
     */
    public final void addPersoonAdres(final PersoonAdres persoonAdres) {
        persoonAdres.setPersoon(this);
        persoonAdresSet.add(persoonAdres);
    }

    /**
     * Geef de waarde van persoon geslachtsnaamcomponent set.
     *
     * @return persoon geslachtsnaamcomponent set
     */
    public Set<PersoonGeslachtsnaamcomponent> getPersoonGeslachtsnaamcomponentSet() {
        return persoonGeslachtsnaamcomponentSet;
    }

    /**
     * Toevoegen van een persoon geslachtsnaamcomponent.
     *
     * @param persoonGeslachtsnaamcomponent
     *            persoon geslachtsnaamcomponent
     */
    public final void addPersoonGeslachtsnaamcomponent(final PersoonGeslachtsnaamcomponent persoonGeslachtsnaamcomponent) {
        persoonGeslachtsnaamcomponent.setPersoon(this);
        persoonGeslachtsnaamcomponentSet.add(persoonGeslachtsnaamcomponent);
    }

    /**
     * Geef de waarde van persoon indicatie set.
     *
     * @return persoon indicatie set
     */
    public Set<PersoonIndicatie> getPersoonIndicatieSet() {
        return persoonIndicatieSet;
    }

    /**
     * Toevoegen van een persoon indicatie.
     *
     * @param persoonIndicatie
     *            persoon indicatie
     */
    public final void addPersoonIndicatie(final PersoonIndicatie persoonIndicatie) {
        persoonIndicatie.setPersoon(this);
        persoonIndicatieSet.add(persoonIndicatie);
    }

    /**
     * Geef de waarde van persoon nationaliteit set.
     *
     * @return persoon nationaliteit set
     */
    public Set<PersoonNationaliteit> getPersoonNationaliteitSet() {
        return persoonNationaliteitSet;
    }

    /**
     * Toevoegen van een persoon nationaliteit.
     *
     * @param persoonNationaliteit
     *            persoon nationaliteit
     */
    public final void addPersoonNationaliteit(final PersoonNationaliteit persoonNationaliteit) {
        persoonNationaliteit.setPersoon(this);
        persoonNationaliteitSet.add(persoonNationaliteit);
    }

    /**
     * Geef de waarde van persoon onderzoek set.
     *
     * @return persoon onderzoek set
     */
    public Set<PersoonOnderzoek> getPersoonOnderzoekSet() {
        return persoonOnderzoekSet;
    }

    /**
     * Toevoegen van een persoon onderzoek.
     *
     * @param persoonOnderzoek
     *            persoon onderzoek
     */
    public final void addPersoonOnderzoek(final PersoonOnderzoek persoonOnderzoek) {
        persoonOnderzoek.setPersoon(this);
        persoonOnderzoekSet.add(persoonOnderzoek);
    }

    /**
     * Verwijderen van een persoon onderzoek.
     *
     * @param persoonOnderzoek
     *            persoon onderzoek
     * @return true, if successful
     */
    public final boolean removePersoonOnderzoek(final PersoonOnderzoek persoonOnderzoek) {
        return persoonOnderzoekSet.remove(persoonOnderzoek);
    }

    /**
     * Geef de waarde van persoon reisdocument set.
     *
     * @return persoon reisdocument set
     */
    public Set<PersoonReisdocument> getPersoonReisdocumentSet() {
        return persoonReisdocumentSet;
    }

    /**
     * Toevoegen van een persoon reisdocument.
     *
     * @param persoonReisdocument
     *            persoon reisdocument
     */
    public final void addPersoonReisdocument(final PersoonReisdocument persoonReisdocument) {
        persoonReisdocument.setPersoon(this);
        persoonReisdocumentSet.add(persoonReisdocument);
    }

    /**
     * Geef de waarde van persoon verificatie set.
     *
     * @return persoon verificatie set
     */
    public Set<PersoonVerificatie> getPersoonVerificatieSet() {
        return persoonVerificatieSet;
    }

    /**
     * Toevoegen van een persoon verificatie.
     *
     * @param persoonVerificatie
     *            persoon verificatie
     */
    public final void addPersoonVerificatie(final PersoonVerificatie persoonVerificatie) {
        persoonVerificatie.setPersoon(this);
        persoonVerificatieSet.add(persoonVerificatie);
    }

    /**
     * Geef de waarde van persoon verstrekkingsbeperking set.
     *
     * @return persoon verstrekkingsbeperking set
     */
    public Set<PersoonVerstrekkingsbeperking> getPersoonVerstrekkingsbeperkingSet() {
        return persoonVerstrekkingsbeperkingSet;
    }

    /**
     * Geef de waarde van persoon voornaam set.
     *
     * @return persoon voornaam set
     */
    public Set<PersoonVoornaam> getPersoonVoornaamSet() {
        return persoonVoornaamSet;
    }

    /**
     * Toevoegen van een persoon voornaam.
     *
     * @param persoonVoornaam
     *            persoon voornaam
     */
    public final void addPersoonVoornaam(final PersoonVoornaam persoonVoornaam) {
        persoonVoornaam.setPersoon(this);
        persoonVoornaamSet.add(persoonVoornaam);
    }

    /**
     * Geef de waarde van adellijke titel.
     *
     * @return adellijke titel
     */
    public AdellijkeTitel getAdellijkeTitel() {
        return AdellijkeTitel.parseId(adellijkeTitelId);
    }

    /**
     * Zet de waarde van adellijke titel.
     *
     * @param titel
     *            adellijke titel
     */
    public void setAdellijkeTitel(final AdellijkeTitel titel) {
        if (titel == null) {
            adellijkeTitelId = null;
        } else {
            adellijkeTitelId = titel.getId();
        }
    }

    /**
     * Geef de waarde van bijhoudingsaard.
     *
     * @return bijhoudingsaard
     */
    public Bijhoudingsaard getBijhoudingsaard() {
        if (bijhoudingsaardId == null) {
            return null;
        }
        return Bijhoudingsaard.parseId(bijhoudingsaardId);
    }

    /**
     * Zet de waarde van bijhoudingsaard.
     *
     * @param bijhoudingsaard
     *            bijhoudingsaard
     */
    public void setBijhoudingsaard(final Bijhoudingsaard bijhoudingsaard) {
        if (bijhoudingsaard == null) {
            bijhoudingsaardId = null;
        } else {
            bijhoudingsaardId = bijhoudingsaard.getId();
        }
    }

    /**
     * Toevoegen van een persoon afgeleid administratief historie.
     *
     * @param persoonAfgeleidAdministratiefHistorie
     *            persoon afgeleid administratief historie
     */
    public final void addPersoonAfgeleidAdministratiefHistorie(final PersoonAfgeleidAdministratiefHistorie persoonAfgeleidAdministratiefHistorie) {
        persoonAfgeleidAdministratiefHistorie.setPersoon(this);
        persoonAfgeleidAdministratiefHistorieSet.add(persoonAfgeleidAdministratiefHistorie);
    }

    /**
     * Geef de waarde van persoon afgeleid administratief historie set.
     *
     * @return persoon afgeleid administratief historie set
     */
    public Set<PersoonAfgeleidAdministratiefHistorie> getPersoonAfgeleidAdministratiefHistorieSet() {
        return persoonAfgeleidAdministratiefHistorieSet;
    }

    /**
     * Geef de waarde van persoon samengestelde naam historie set.
     *
     * @return persoon samengestelde naam historie set
     */
    public Set<PersoonSamengesteldeNaamHistorie> getPersoonSamengesteldeNaamHistorieSet() {
        return persoonSamengesteldeNaamHistorieSet;
    }

    /**
     * Toevoegen van een persoon samengestelde naam historie.
     *
     * @param persoonSamengesteldeNaamHistorie
     *            persoon samengestelde naam historie
     */
    public final void addPersoonSamengesteldeNaamHistorie(final PersoonSamengesteldeNaamHistorie persoonSamengesteldeNaamHistorie) {
        persoonSamengesteldeNaamHistorie.setPersoon(this);
        persoonSamengesteldeNaamHistorieSet.add(persoonSamengesteldeNaamHistorie);
    }

    /**
     * Geef de waarde van persoon naamgebruik historie set.
     *
     * @return persoon naamgebruik historie set
     */
    public Set<PersoonNaamgebruikHistorie> getPersoonNaamgebruikHistorieSet() {
        return persoonNaamgebruikHistorieSet;
    }

    /**
     * Toevoegen van een persoon naamgebruik historie.
     *
     * @param persoonNaamgebruikHistorie
     *            persoon naamgebruik historie
     */
    public final void addPersoonNaamgebruikHistorie(final PersoonNaamgebruikHistorie persoonNaamgebruikHistorie) {
        persoonNaamgebruikHistorie.setPersoon(this);
        persoonNaamgebruikHistorieSet.add(persoonNaamgebruikHistorie);
    }

    /**
     * Geef de waarde van persoon deelname eu verkiezingen historie set.
     *
     * @return persoon deelname eu verkiezingen historie set
     */
    public Set<PersoonDeelnameEuVerkiezingenHistorie> getPersoonDeelnameEuVerkiezingenHistorieSet() {
        return persoonDeelnameEuVerkiezingenHistorieSet;
    }

    /**
     * Toevoegen van een persoon deelname eu verkiezingen historie.
     *
     * @param persoonDeelnameEuVerkiezingenHistorie
     *            persoon deelname eu verkiezingen historie
     */
    public final void addPersoonDeelnameEuVerkiezingenHistorie(final PersoonDeelnameEuVerkiezingenHistorie persoonDeelnameEuVerkiezingenHistorie) {
        persoonDeelnameEuVerkiezingenHistorie.setPersoon(this);
        persoonDeelnameEuVerkiezingenHistorieSet.add(persoonDeelnameEuVerkiezingenHistorie);
    }

    /**
     * Geef de waarde van persoon geboorte historie set.
     *
     * @return persoon geboorte historie set
     */
    public Set<PersoonGeboorteHistorie> getPersoonGeboorteHistorieSet() {
        return persoonGeboorteHistorieSet;
    }

    /**
     * Toevoegen van een persoon geboorte historie.
     *
     * @param persoonGeboorteHistorie
     *            persoon geboorte historie
     */
    public final void addPersoonGeboorteHistorie(final PersoonGeboorteHistorie persoonGeboorteHistorie) {
        persoonGeboorteHistorie.setPersoon(this);
        persoonGeboorteHistorieSet.add(persoonGeboorteHistorie);
    }

    /**
     * Geef de waarde van persoon geslachtsaanduiding historie set.
     *
     * @return persoon geslachtsaanduiding historie set
     */
    public Set<PersoonGeslachtsaanduidingHistorie> getPersoonGeslachtsaanduidingHistorieSet() {
        return persoonGeslachtsaanduidingHistorieSet;
    }

    /**
     * Toevoegen van een persoon geslachtsaanduiding historie.
     *
     * @param persoonGeslachtsaanduidingHistorie
     *            persoon geslachtsaanduiding historie
     */
    public final void addPersoonGeslachtsaanduidingHistorie(final PersoonGeslachtsaanduidingHistorie persoonGeslachtsaanduidingHistorie) {
        persoonGeslachtsaanduidingHistorie.setPersoon(this);
        persoonGeslachtsaanduidingHistorieSet.add(persoonGeslachtsaanduidingHistorie);
    }

    /**
     * Geef de waarde van persoon id historie set.
     *
     * @return persoon id historie set
     */
    public Set<PersoonIDHistorie> getPersoonIDHistorieSet() {
        return persoonIDHistorieSet;
    }

    /**
     * Toevoegen van een persoon id historie.
     *
     * @param persoonIDHistorie
     *            persoon id historie
     */
    public final void addPersoonIDHistorie(final PersoonIDHistorie persoonIDHistorie) {
        persoonIDHistorie.setPersoon(this);
        persoonIDHistorieSet.add(persoonIDHistorie);
    }

    /**
     * Geef de waarde van persoon verblijfsrecht historie set.
     *
     * @return persoon verblijfsrecht historie set
     */
    public Set<PersoonVerblijfsrechtHistorie> getPersoonVerblijfsrechtHistorieSet() {
        return persoonVerblijfsrechtHistorieSet;
    }

    /**
     * Toevoegen van een persoon verblijfsrecht historie.
     *
     * @param persoonVerblijfsrechtHistorie
     *            persoon verblijfsrecht historie
     */
    public final void addPersoonVerblijfsrechtHistorie(final PersoonVerblijfsrechtHistorie persoonVerblijfsrechtHistorie) {
        persoonVerblijfsrechtHistorie.setPersoon(this);
        persoonVerblijfsrechtHistorieSet.add(persoonVerblijfsrechtHistorie);
    }

    /**
     * Geef de waarde van persoon bijhouding historie set.
     *
     * @return persoon bijhouding historie set
     */
    public Set<PersoonBijhoudingHistorie> getPersoonBijhoudingHistorieSet() {
        return persoonBijhoudingHistorieSet;
    }

    /**
     * Toevoegen van een persoon bijhouding historie.
     *
     * @param persoonBijhoudingHistorie
     *            persoon bijhouding historie
     */
    public final void addPersoonBijhoudingHistorie(final PersoonBijhoudingHistorie persoonBijhoudingHistorie) {
        persoonBijhoudingHistorie.setPersoon(this);
        persoonBijhoudingHistorieSet.add(persoonBijhoudingHistorie);
    }

    /**
     * Geef de waarde van persoon migratie historie set.
     *
     * @return persoon migratie historie set
     */
    public Set<PersoonMigratieHistorie> getPersoonMigratieHistorieSet() {
        return persoonMigratieHistorieSet;
    }

    /**
     * Toevoegen van een persoon migratie historie.
     *
     * @param persoonMigratieHistorie
     *            persoon migratie historie
     */
    public final void addPersoonMigratieHistorie(final PersoonMigratieHistorie persoonMigratieHistorie) {
        persoonMigratieHistorie.setPersoon(this);
        persoonMigratieHistorieSet.add(persoonMigratieHistorie);
    }

    /**
     * Geef de waarde van persoon inschrijving historie set.
     *
     * @return persoon inschrijving historie set
     */
    public Set<PersoonInschrijvingHistorie> getPersoonInschrijvingHistorieSet() {
        return persoonInschrijvingHistorieSet;
    }

    /**
     * Toevoegen van een persoon inschrijving historie.
     *
     * @param persoonInschrijvingHistorie
     *            persoon inschrijving historie
     */
    public final void addPersoonInschrijvingHistorie(final PersoonInschrijvingHistorie persoonInschrijvingHistorie) {
        persoonInschrijvingHistorie.setPersoon(this);
        persoonInschrijvingHistorieSet.add(persoonInschrijvingHistorie);
    }

    /**
     * Geef de waarde van persoon nummerverwijzing historie set.
     *
     * @return persoon nummerverwijzing historie set
     */
    public Set<PersoonNummerverwijzingHistorie> getPersoonNummerverwijzingHistorieSet() {
        return persoonNummerverwijzingHistorieSet;
    }

    /**
     * Toevoegen van een persoon nummerverwijzing historie.
     *
     * @param persoonNummerverwijzingHistorie
     *            persoon nummerverwijzing historie
     */
    public final void addPersoonNummerverwijzingHistorie(final PersoonNummerverwijzingHistorie persoonNummerverwijzingHistorie) {
        persoonNummerverwijzingHistorie.setPersoon(this);
        persoonNummerverwijzingHistorieSet.add(persoonNummerverwijzingHistorie);
    }

    /**
     * Geef de waarde van persoon overlijden historie set.
     *
     * @return persoon overlijden historie set
     */
    public Set<PersoonOverlijdenHistorie> getPersoonOverlijdenHistorieSet() {
        return persoonOverlijdenHistorieSet;
    }

    /**
     * Toevoegen van een persoon overlijden historie.
     *
     * @param persoonOverlijdenHistorie
     *            persoon overlijden historie
     */
    public final void addPersoonOverlijdenHistorie(final PersoonOverlijdenHistorie persoonOverlijdenHistorie) {
        persoonOverlijdenHistorie.setPersoon(this);
        persoonOverlijdenHistorieSet.add(persoonOverlijdenHistorie);
    }

    /**
     * Geef de waarde van persoon persoonskaart historie set.
     *
     * @return persoon persoonskaart historie set
     */
    public Set<PersoonPersoonskaartHistorie> getPersoonPersoonskaartHistorieSet() {
        return persoonPersoonskaartHistorieSet;
    }

    /**
     * Toevoegen van een persoon persoonskaart historie.
     *
     * @param persoonPersoonskaartHistorie
     *            persoon persoonskaart historie
     */
    public final void addPersoonPersoonskaartHistorie(final PersoonPersoonskaartHistorie persoonPersoonskaartHistorie) {
        persoonPersoonskaartHistorie.setPersoon(this);
        persoonPersoonskaartHistorieSet.add(persoonPersoonskaartHistorie);
    }

    /**
     * Geef de waarde van persoon uitsluiting kiesrecht historie set.
     *
     * @return persoon uitsluiting kiesrecht historie set
     */
    public Set<PersoonUitsluitingKiesrechtHistorie> getPersoonUitsluitingKiesrechtHistorieSet() {
        return persoonUitsluitingKiesrechtHistorieSet;
    }

    /**
     * Toevoegen van een persoon uitsluiting kiesrecht historie.
     *
     * @param persoonUitsluitingKiesrechtHistorie
     *            persoon uitsluiting kiesrecht historie
     */
    public final void addPersoonUitsluitingKiesrechtHistorie(final PersoonUitsluitingKiesrechtHistorie persoonUitsluitingKiesrechtHistorie) {
        persoonUitsluitingKiesrechtHistorie.setPersoon(this);
        persoonUitsluitingKiesrechtHistorieSet.add(persoonUitsluitingKiesrechtHistorie);
    }

    /**
     * Geef de waarde van lo3 berichten.
     *
     * @return lo3 berichten
     */
    public Set<Lo3Bericht> getLo3Berichten() {
        return lo3Berichten;
    }

    /**
     * Toevoegen van een lo3 bericht.
     *
     * @param lo3Bericht
     *            lo3 bericht
     */
    public final void addLo3Bericht(final Lo3Bericht lo3Bericht) {
        lo3Bericht.setPersoon(this);
        lo3Berichten.add(lo3Bericht);
    }

    /**
     * Geef de waarde van betrokkenheid set.
     *
     * @return betrokkenheid set
     */
    public Set<Betrokkenheid> getBetrokkenheidSet() {
        return betrokkenheidSet;
    }

    /**
     * Toevoegen van een betrokkenheid.
     *
     * @param betrokkenheid
     *            betrokkenheid
     */
    public final void addBetrokkenheid(final Betrokkenheid betrokkenheid) {
        betrokkenheid.setPersoon(this);
        betrokkenheidSet.add(betrokkenheid);
    }

    /**
     * Verwijderen van een betrokkenheid.
     *
     * @param betrokkenheid
     *            betrokkenheid
     * @return true, if successful
     */
    public final boolean removeBetrokkenheid(final Betrokkenheid betrokkenheid) {
        // Persoon hard op null zetten, anders wordt de DB-object niet verwijderd door Hibernate.
        betrokkenheid.setPersoon(null);
        return betrokkenheidSet.remove(betrokkenheid);
    }

    /**
     * Geef de waarde van relaties.
     *
     * @return relaties
     */
    public Set<Relatie> getRelaties() {
        final Set<Relatie> result = new LinkedHashSet<>();
        for (final Betrokkenheid ikBetrokkenheid : betrokkenheidSet) {
            result.add(ikBetrokkenheid.getRelatie());
        }
        return result;
    }

    /**
     * Geef de waarde van stapels.
     *
     * @return stapels
     */
    public Set<Stapel> getStapels() {
        return stapels;
    }

    /**
     * Voegt de stapel toe aan deze persoon.
     *
     * @param stapel
     *            de (IST) stapel, mag niet null zijn
     */
    public final void addStapel(final Stapel stapel) {
        stapel.setPersoon(this);
        stapels.add(stapel);
    }

    /**
     * Verwijderd de stapel voor deze persoon.
     *
     * @param stapel
     *            de stapel die verwijderd moet worden
     * @return true als het lukt de stapel te verwijderen anders false
     */
    public final boolean removeStapel(final Stapel stapel) {
        return stapels.remove(stapel);
    }

    @Override
    public Map<String, Collection<? extends FormeleHistorie>> verzamelHistorie() {
        final Map<String, Collection<? extends FormeleHistorie>> result = new HashMap<>();

        result.put("persoonGeboorteHistorieSet", Collections.unmodifiableSet(persoonGeboorteHistorieSet));
        result.put("persoonSamengesteldeNaamHistorieSet", Collections.unmodifiableSet(persoonSamengesteldeNaamHistorieSet));
        result.put("persoonGeslachtsaanduidingHistorieSet", Collections.unmodifiableSet(persoonGeslachtsaanduidingHistorieSet));
        result.put("persoonIDHistorieSet", Collections.unmodifiableSet(persoonIDHistorieSet));
        return result;
    }
}
