/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Access;
import javax.persistence.AccessType;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import nl.bzk.brp.model.PersistentRootObject;
import nl.bzk.brp.model.gedeeld.AdellijkeTitel;
import nl.bzk.brp.model.gedeeld.GeslachtsAanduiding;
import nl.bzk.brp.model.gedeeld.Land;
import nl.bzk.brp.model.gedeeld.Partij;
import nl.bzk.brp.model.gedeeld.Plaats;
import nl.bzk.brp.model.gedeeld.Predikaat;
import nl.bzk.brp.model.gedeeld.RedenOpschorting;
import nl.bzk.brp.model.gedeeld.SoortPersoon;
import nl.bzk.brp.model.gedeeld.Verantwoordelijke;
import nl.bzk.brp.model.gedeeld.WijzeGebruikGeslachtsnaam;
import nl.bzk.brp.model.operationeel.StatusHistorie;


/** Een actueel persoon in het operationeel model. */
@Entity
@Access(AccessType.FIELD)
@Table(name = "Pers", schema = "Kern")
public class PersistentPersoon implements PersistentRootObject {

    @Id
    @SequenceGenerator(name = "PERSOON", sequenceName = "Kern.seq_Pers")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSOON")
    private Long id;

    @Column(name = "BSN")
    private String burgerservicenummer;

    @Column(name = "anr")
    private String aNummer;

    @Column(name = "datgeboorte")
    private Integer datumGeboorte;

    @ManyToOne
    @JoinColumn(name = "GemGeboorte")
    private Partij gemeenteGeboorte;

    @ManyToOne
    @JoinColumn(name = "WplGeboorte")
    private Plaats woonplaatsGeboorte;

    @ManyToOne
    @JoinColumn(name = "LandGeboorte")
    private Land landGeboorte;

    @Column(name = "blgeboorteplaats")
    private String buitenlandGeboortePlaats;

    @Column(name = "blregiogeboorte")
    private String buitenlandGeboorteRegio;

    @Column(name = "omsgeboorteloc")
    private String omschrijvingGeboorteLocatie;

    @Column(name = "geboortestatushis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie geboorteStatusHis;

    // afgeleide gegevens overlijden.
    @Column(name = "datoverlijden")
    private Integer datumOverlijden;

    @ManyToOne
    @JoinColumn(name = "gemoverlijden")
    private Partij gemeenteOverljden;

    @ManyToOne
    @JoinColumn(name = "wploverlijden")
    private Plaats woonplaatsOverlijden;

    @Column(name = "blplaatsoverlijden")
    private String buitenlandOverlijdenPlaats;

    @Column(name = "blregiooverlijden")
    private String buitenlandOverlijdenRegio;

    @ManyToOne
    @JoinColumn(name = "landoverlijden")
    private Land landOverlijden;

    @Column(name = "omslocoverlijden")
    private String omschrijvingOverlijdenLocatie;

    @Column(name = "overlijdenstatushis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie overlijdenStatusHis;


    @Column(name = "geslachtsaand")
    @Enumerated
    private GeslachtsAanduiding geslachtsAanduiding;

    // afgeleide gegevens voor samengesteldenaam.
    @Column(name = "predikaat")
    @Enumerated
    private Predikaat predikaat;

    @Column(name = "voorvoegsel")
    private String voorvoegsel;

    @Column(name = "scheidingsteken")
    private String scheidingsTeken;

    @Column(name = "adellijketitel")
    @Enumerated
    private AdellijkeTitel adellijkeTitel;

    @Column(name = "geslnaam")
    private String geslachtsNaam;

    @Column(name = "indnreeksalsgeslnaam")
    private Boolean indReeksAlsGeslachtnaam;

    @Column(name = "indalgoritmischafgeleid")
    private Boolean indAlgoritmischAfgeleid;

    @Column(name = "voornamen")
    private String voornaam;

    @Column(name = "samengesteldenaamstatushis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie samengesteldeNaamStatusHis;

    // afgeleide gegevens voor aanschrijving.
    @Column(name = "gebrgeslnaamegp")
    private WijzeGebruikGeslachtsnaam aanschrijvingWijzeGebruikGeslachtsnaam;

    @Column(name = "indaanschrmetadellijketitels")
    private Boolean indAanschrijvingMetAdellijkeTitels;

    @Column(name = "indaanschralgoritmischafgele")
    private Boolean indAanschrijvingAlgoritmischAfgeleid;

    @Column(name = "predikaataanschr")
    @Enumerated
    private Predikaat aanschrijvingPredikaat;

    @Column(name = "voornamenaanschr")
    private String aanschrijvingVoornamen;

    @Column(name = "voorvoegselaanschr")
    private String aanschrijvingVoorvoegsel;

    @Column(name = "scheidingstekenaanschr")
    private String aanschrijvingScheidingteken;

    @Column(name = "geslnaamaanschr")
    private String aanschrijvingGeslachtnaam;

    @Column(name = "aanschrstatushis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie aanschrijvingStatusHis;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pers")
    private Set<PersistentPersoonVoornaam> persoonVoornamen =
        new HashSet<PersistentPersoonVoornaam>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pers")
    private Set<PersistentPersoonGeslachtsnaamcomponent> persoonGeslachtsnaamcomponenten =
        new HashSet<PersistentPersoonGeslachtsnaamcomponent>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pers")
    private Set<PersistentPersoonAdres> adressen = new HashSet<PersistentPersoonAdres>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pers")
    private Set<PersistentPersoonIndicatie> persoonIndicaties =
        new HashSet<PersistentPersoonIndicatie>();
    @Column(name = "srt")
    @Enumerated
    private SoortPersoon soortPersoon;

    @Column(name = "idsstatushis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie statushistorie;

    @Column(name = "geslachtsaandstatusHis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie geslachtsaanduidingStatusHis;

    // afgeleide groep voor verblijfsrecht
    @Column(name = "verblijfsr")
    private Long    verblijfsrechtId;
    @Column(name = "dataanvverblijfsr")
    private Integer verblijfsrechtDatumAanvang;
    @Column(name = "datvoorzeindeverblijfsr")
    private Integer verblijfsrechtDatumVoorzienEinde;
    @Column(name = "dataanvaaneenslverblijfsr")
    private Integer verblijfsrechtDatumAanvangAaneensluitend;

    @Column(name = "verblijfsrstatushis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie verblijfsrechtStatusHis;

    @Column(name = "induitslnlkiesr")
    private Boolean uitsluitingNLKiesrechtIndicatie;
    @Column(name = "dateindeuitslnlkiesr")
    private Integer uitsluitingNLKiesrechtDatumEinde;

    @Column(name = "uitslnlkiesrstatushis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie uitsluitingNLKiesrechtStatusHis;

    @Column(name = "inddeelneuverkiezingen")
    private Boolean eUVerkiezingenIndicatie;
    @Column(name = "dataanlaanpdeelneuverkiezing")
    private Integer eUVerkiezingenDatumAanvang;
    @Column(name = "dateindeuitsleukiesr")
    private Integer eUVerkiezingenDatumEinde;

    @Column(name = "euverkiezingenstatushis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie eUVerkiezingenStatusHis;

    @Column(name = "verantwoordelijke")
    @Enumerated(value = EnumType.ORDINAL)
    private Verantwoordelijke verantwoordelijke;

    @Column(name = "bijhverantwoordelijkheidstat")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie bijhoudingsverantwoordelijkheidStatusHis;

    @Column(name = "rdnopschortingbijhouding")
    @Enumerated(value = EnumType.ORDINAL)
    private RedenOpschorting redenOpschortingBijhouding;

    @Column(name = "opschortingstatushis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie opschortingStatusHis;

    @ManyToOne
    @JoinColumn(name = "bijhgem")
    private Partij         bijhoudingsGemeente;
    @Column(name = "datinschringem")
    private Integer        bijhoudingsGemeenteDatumInschrijving;
    @Column(name = "indonverwdocaanw")
    private Boolean        bijhoudingsGemeenteIndicatieOnverwerktDocumentAanwezig;
    @Column(name = "bijhgemstatushis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie bijhoudingsgemeenteStatusHis;

    @ManyToOne
    @JoinColumn(name = "gempk")
    private Partij         persoonskaartGemeente;
    @Column(name = "indpkvollediggeconv")
    private Boolean        persoonskaartIsVolledigGeconverteerd;
    @Column(name = "pkstatushis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie persoonskaartStatusHis;

    @ManyToOne
    @JoinColumn(name = "landvanwaargevestigd")
    private Land           immigratieLandHerkomst;
    @Column(name = "datvestiginginnederland")
    private Integer        immigratieVestigingDatum;
    @Column(name = "immigratiestatushis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie immigratieStatusHis;

    @Column(name = "datinschr")
    private Integer        inschrijvingDatum;
    @Column(name = "versienr")
    private Long           inschrijvingVersienummer;
    @Column(name = "vorigepers")
    private Long           inschrijvingVorigPersooonId;
    @Column(name = "volgendepers")
    private Long           inschrijvingVolgendPersooonId;
    @Column(name = "inschrstatushis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie inschrijvingStatusHis;

    @Column(name = "tijdstiplaatstewijz")
    @Temporal(TemporalType.TIMESTAMP)
    private Date laatstGewijzigd;

    @Column(name = "indgegevensinonderzoek")
    private Boolean indGegevensInOnderzoek;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "pers")
    private Set<PersistentPersoonNationaliteit> nationaliteiten = new HashSet<PersistentPersoonNationaliteit>();

    @OneToMany(cascade = { CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.REMOVE }, fetch = FetchType.LAZY)
    @JoinColumn(name = "betrokkene")
    private Set<PersistentBetrokkenheid> betrokkenheden;


    /** No-args constructor, vereist voor JPA. */
    public PersistentPersoon() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getBurgerservicenummer() {
        return burgerservicenummer;
    }

    public void setBurgerservicenummer(final String burgerservicenummer) {
        this.burgerservicenummer = burgerservicenummer;
    }

    public Set<PersistentPersoonVoornaam> getPersoonVoornamen() {
        return persoonVoornamen;
    }

    public void setPersoonVoornamen(final Set<PersistentPersoonVoornaam> persoonVoornamen) {
        this.persoonVoornamen = persoonVoornamen;
    }

    /**
     * Voeg persoon voornaam toe aan de persoonVoornamen set.
     *
     * @param persoonVoornaam de {@link PersistentPersoonVoornaam}
     */
    public void voegPersoonVoornaamToe(final PersistentPersoonVoornaam persoonVoornaam) {
        persoonVoornamen.add(persoonVoornaam);
    }

    public Set<PersistentPersoonGeslachtsnaamcomponent> getPersoonGeslachtsnaamcomponenten() {
        return persoonGeslachtsnaamcomponenten;
    }

    public void setPersoonGeslachtsnaamcomponenten(
        final Set<PersistentPersoonGeslachtsnaamcomponent> persoonGeslachtsnaamcomponenten)
    {
        this.persoonGeslachtsnaamcomponenten = persoonGeslachtsnaamcomponenten;
    }

    /**
     * Voeg geslachtsnaamcomponent toe aan de geslachtsnaamcomponenten set.
     *
     * @param geslachtsnaamcomponent de {@link PersistentPersoonGeslachtsnaamcomponent}
     */
    public void voegPersoonGeslachtsnaamcomponentenToe(
        final PersistentPersoonGeslachtsnaamcomponent geslachtsnaamcomponent)
    {
        persoonGeslachtsnaamcomponenten.add(geslachtsnaamcomponent);
    }

    public Set<PersistentPersoonAdres> getAdressen() {
        return adressen;
    }

    public void setAdressen(final Set<PersistentPersoonAdres> adressen) {
        this.adressen = adressen;
    }

    public Partij getBijhoudingsGemeente() {
        return bijhoudingsGemeente;
    }

    public void setBijhoudingsGemeente(final Partij bijhoudingsGemeente) {
        this.bijhoudingsGemeente = bijhoudingsGemeente;
    }

    public String getANummer() {
        return aNummer;
    }

    public void setANummer(final String anr) {
        aNummer = anr;
    }

    public Integer getDatumGeboorte() {
        return datumGeboorte;
    }

    public void setDatumGeboorte(final Integer datumGeboorte) {
        this.datumGeboorte = datumGeboorte;
    }

    public Partij getGemeenteGeboorte() {
        return gemeenteGeboorte;
    }

    public void setGemeenteGeboorte(final Partij gemeenteGeboorte) {
        this.gemeenteGeboorte = gemeenteGeboorte;
    }

    public Plaats getWoonplaatsGeboorte() {
        return woonplaatsGeboorte;
    }

    public void setWoonplaatsGeboorte(final Plaats woonplaatsGeboorte) {
        this.woonplaatsGeboorte = woonplaatsGeboorte;
    }

    public Land getLandGeboorte() {
        return landGeboorte;
    }

    public void setLandGeboorte(final Land landGeboorte) {
        this.landGeboorte = landGeboorte;
    }

    public GeslachtsAanduiding getGeslachtsAanduiding() {
        return geslachtsAanduiding;
    }

    public void setGeslachtsAanduiding(final GeslachtsAanduiding geslachtsAanduiding) {
        this.geslachtsAanduiding = geslachtsAanduiding;
    }

    public String getGeslachtsNaam() {
        return geslachtsNaam;
    }

    public void setGeslachtsNaam(final String geslachtsNaam) {
        this.geslachtsNaam = geslachtsNaam;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(final String voornaam) {
        this.voornaam = voornaam;
    }

    public SoortPersoon getSoortPersoon() {
        return soortPersoon;
    }

    public void setSoortPersoon(final SoortPersoon soortPersoon) {
        this.soortPersoon = soortPersoon;
    }

    public StatusHistorie getStatushistorie() {
        return statushistorie;
    }

    public void setStatushistorie(final StatusHistorie statushistorie) {
        this.statushistorie = statushistorie;
    }

    public StatusHistorie getGeslachtsaanduidingStatusHis() {
        return geslachtsaanduidingStatusHis;
    }

    public void setGeslachtsaanduidingStatusHis(final StatusHistorie geslachtsaanduidingStatusHis) {
        this.geslachtsaanduidingStatusHis = geslachtsaanduidingStatusHis;
    }

    public StatusHistorie getSamengesteldeNaamStatusHis() {
        return samengesteldeNaamStatusHis;
    }

    public void setSamengesteldeNaamStatusHis(final StatusHistorie samengesteldeNaamStatusHis) {
        this.samengesteldeNaamStatusHis = samengesteldeNaamStatusHis;
    }

    public StatusHistorie getAanschrijvingStatusHis() {
        return aanschrijvingStatusHis;
    }

    public void setAanschrijvingStatusHis(final StatusHistorie aanschrijvingStatusHis) {
        this.aanschrijvingStatusHis = aanschrijvingStatusHis;
    }

    public StatusHistorie getGeboorteStatusHis() {
        return geboorteStatusHis;
    }

    public void setGeboorteStatusHis(final StatusHistorie geboorteStatusHis) {
        this.geboorteStatusHis = geboorteStatusHis;
    }

    public StatusHistorie getOverlijdenStatusHis() {
        return overlijdenStatusHis;
    }

    public void setOverlijdenStatusHis(final StatusHistorie overlijdenStatusHis) {
        this.overlijdenStatusHis = overlijdenStatusHis;
    }

    public StatusHistorie getVerblijfsrechtStatusHis() {
        return verblijfsrechtStatusHis;
    }

    public void setVerblijfsrechtStatusHis(final StatusHistorie verblijfsrechtStatusHis) {
        this.verblijfsrechtStatusHis = verblijfsrechtStatusHis;
    }

    public StatusHistorie getUitsluitingNLKiesrechtStatusHis() {
        return uitsluitingNLKiesrechtStatusHis;
    }

    public void setUitsluitingNLKiesrechtStatusHis(final StatusHistorie uitsluitingNLKiesrechtStatusHis) {
        this.uitsluitingNLKiesrechtStatusHis = uitsluitingNLKiesrechtStatusHis;
    }

    public Set<PersistentPersoonIndicatie> getPersoonIndicaties() {
        return persoonIndicaties;
    }

    public void setPersoonIndicaties(final Set<PersistentPersoonIndicatie> persoonIndicaties) {
        this.persoonIndicaties = persoonIndicaties;
    }

    public StatusHistorie getEUVerkiezingenStatusHis() {
        return eUVerkiezingenStatusHis;
    }

    public void setEUVerkiezingenStatusHis(final StatusHistorie euVerkiezingenStatusHis) {
        eUVerkiezingenStatusHis = euVerkiezingenStatusHis;
    }

    public StatusHistorie getBijhoudingsverantwoordelijkheidStatusHis() {
        return bijhoudingsverantwoordelijkheidStatusHis;
    }

    public void setBijhoudingsverantwoordelijkheidStatusHis(
        final StatusHistorie bijhoudingsverantwoordelijkheidStatusHis)
    {
        this.bijhoudingsverantwoordelijkheidStatusHis = bijhoudingsverantwoordelijkheidStatusHis;
    }

    public StatusHistorie getOpschortingStatusHis() {
        return opschortingStatusHis;
    }

    public void setOpschortingStatusHis(final StatusHistorie opschortingStatusHis) {
        this.opschortingStatusHis = opschortingStatusHis;
    }

    public StatusHistorie getBijhoudingsgemeenteStatusHis() {
        return bijhoudingsgemeenteStatusHis;
    }

    public void setBijhoudingsgemeenteStatusHis(final StatusHistorie bijhoudingsgemeenteStatusHis) {
        this.bijhoudingsgemeenteStatusHis = bijhoudingsgemeenteStatusHis;
    }

    public StatusHistorie getPersoonskaartStatusHis() {
        return persoonskaartStatusHis;
    }

    public void setPersoonskaartStatusHis(final StatusHistorie persoonskaartStatusHis) {
        this.persoonskaartStatusHis = persoonskaartStatusHis;
    }

    public StatusHistorie getImmigratieStatusHis() {
        return immigratieStatusHis;
    }

    public void setImmigratieStatusHis(final StatusHistorie immigratieStatusHis) {
        this.immigratieStatusHis = immigratieStatusHis;
    }

    public StatusHistorie getInschrijvingStatusHis() {
        return inschrijvingStatusHis;
    }

    public void setInschrijvingStatusHis(final StatusHistorie inschrijvingStatusHis) {
        this.inschrijvingStatusHis = inschrijvingStatusHis;
    }

    /** @return the predikaat */
    public Predikaat getPredikaat() {
        return predikaat;
    }

    /** @param predikaat the predikaat to set */
    public void setPredikaat(final Predikaat predikaat) {
        this.predikaat = predikaat;
    }

    /** @return the voorvoegsel */
    public String getVoorvoegsel() {
        return voorvoegsel;
    }

    /** @param voorvoegsel the voorvoegsel to set */
    public void setVoorvoegsel(final String voorvoegsel) {
        this.voorvoegsel = voorvoegsel;
    }

    /** @return the scheidingsTeken */
    public String getScheidingsTeken() {
        return scheidingsTeken;
    }

    /** @param scheidingsTeken the scheidingsTeken to set */
    public void setScheidingsTeken(final String scheidingsTeken) {
        this.scheidingsTeken = scheidingsTeken;
    }

    /** @return the adellijkeTitel */
    public AdellijkeTitel getAdellijkeTitel() {
        return adellijkeTitel;
    }

    /** @param adellijkeTitel the adellijkeTitel to set */
    public void setAdellijkeTitel(final AdellijkeTitel adellijkeTitel) {
        this.adellijkeTitel = adellijkeTitel;
    }

    /** @return the indReeksAlsGeslachtnaam */
    public Boolean getIndReeksAlsGeslachtnaam() {
        return indReeksAlsGeslachtnaam;
    }

    /** @param indReeksAlsGeslachtnaam the indReeksAlsGeslachtnaam to set */
    public void setIndReeksAlsGeslachtnaam(final Boolean indReeksAlsGeslachtnaam) {
        this.indReeksAlsGeslachtnaam = indReeksAlsGeslachtnaam;
    }

    /** @return the indAlgoritmIsAfgeleid */
    public Boolean getIndAlgoritmischAfgeleid() {
        return indAlgoritmischAfgeleid;
    }

    /** @param indAlgoritmIsAfgeleid the indAlgoritmIsAfgeleid to set */
    public void setIndAlgoritmischAfgeleid(final Boolean indAlgoritmIsAfgeleid) {
        this.indAlgoritmischAfgeleid = indAlgoritmIsAfgeleid;
    }

    /** @return the buitenlandGeboorteRegio */
    public String getBuitenlandGeboorteRegio() {
        return buitenlandGeboorteRegio;
    }

    /** @param buitenlandGeboorteRegio the buitenlandGeboorteRegio to set */
    public void setBuitenlandGeboorteRegio(final String buitenlandGeboorteRegio) {
        this.buitenlandGeboorteRegio = buitenlandGeboorteRegio;
    }

    /** @return the omschrijvingGeboorteLocatie */
    public String getOmschrijvingGeboorteLocatie() {
        return omschrijvingGeboorteLocatie;
    }

    /** @param omschrijvingGeboorteLocatie the omschrijvingGeboorteLocatie to set */
    public void setOmschrijvingGeboorteLocatie(final String omschrijvingGeboorteLocatie) {
        this.omschrijvingGeboorteLocatie = omschrijvingGeboorteLocatie;
    }

    /** @return the buitenlandGeboortePlaats */
    public String getBuitenlandGeboortePlaats() {
        return buitenlandGeboortePlaats;
    }

    /** @param buitenlandGeboortePlaats the buitenlandGeboortePlaats to set */
    public void setBuitenlandGeboortePlaats(final String buitenlandGeboortePlaats) {
        this.buitenlandGeboortePlaats = buitenlandGeboortePlaats;
    }

    /** @return the datumOverlijden */
    public Integer getDatumOverlijden() {
        return datumOverlijden;
    }

    /** @param datumOverlijden the datumOverlijden to set */
    public void setDatumOverlijden(final Integer datumOverlijden) {
        this.datumOverlijden = datumOverlijden;
    }

    /** @return the gemeenteOverljden */
    public Partij getGemeenteOverljden() {
        return gemeenteOverljden;
    }

    /** @param gemeenteOverljden the gemeenteOverljden to set */
    public void setGemeenteOverljden(final Partij gemeenteOverljden) {
        this.gemeenteOverljden = gemeenteOverljden;
    }

    /** @return the woonplaatsOverlijden */
    public Plaats getWoonplaatsOverlijden() {
        return woonplaatsOverlijden;
    }

    /** @param woonplaatsOverlijden the woonplaatsOverlijden to set */
    public void setWoonplaatsOverlijden(final Plaats woonplaatsOverlijden) {
        this.woonplaatsOverlijden = woonplaatsOverlijden;
    }

    /** @return the buitenlandOverlijdenPlaats */
    public String getBuitenlandOverlijdenPlaats() {
        return buitenlandOverlijdenPlaats;
    }

    /** @param buitenlandOverlijdenPlaats the buitenlandOverlijdenPlaats to set */
    public void setBuitenlandOverlijdenPlaats(final String buitenlandOverlijdenPlaats) {
        this.buitenlandOverlijdenPlaats = buitenlandOverlijdenPlaats;
    }

    /** @return the buitenlandOverlijdenRegio */
    public String getBuitenlandOverlijdenRegio() {
        return buitenlandOverlijdenRegio;
    }

    /** @param buitenlandOverlijdenRegio the buitenlandOverlijdenRegio to set */
    public void setBuitenlandOverlijdenRegio(final String buitenlandOverlijdenRegio) {
        this.buitenlandOverlijdenRegio = buitenlandOverlijdenRegio;
    }

    /** @return the landOverlijden */
    public Land getLandOverlijden() {
        return landOverlijden;
    }

    /** @param landOverlijden the landOverlijden to set */
    public void setLandOverlijden(final Land landOverlijden) {
        this.landOverlijden = landOverlijden;
    }

    /** @return the omschrijvingOverlijdenLocatie */
    public String getOmschrijvingOverlijdenLocatie() {
        return omschrijvingOverlijdenLocatie;
    }

    /** @param omschrijvingOverlijdenLocatie the omschrijvingOverlijdenLocatie to set */
    public void setOmschrijvingOverlijdenLocatie(final String omschrijvingOverlijdenLocatie) {
        this.omschrijvingOverlijdenLocatie = omschrijvingOverlijdenLocatie;
    }

    /** @return the indAanschrijvingMetAdellijkeTitels */
    public Boolean getIndAanschrijvingMetAdellijkeTitels() {
        return indAanschrijvingMetAdellijkeTitels;
    }

    /** @param indAanschrijvingMetAdellijkeTitels the indAanschrijvingMetAdellijkeTitels to set */
    public void setIndAanschrijvingMetAdellijkeTitels(final Boolean indAanschrijvingMetAdellijkeTitels) {
        this.indAanschrijvingMetAdellijkeTitels = indAanschrijvingMetAdellijkeTitels;
    }

    /** @return the indAanschrijvingAlgoritmischAfgeleid */
    public Boolean getIndAanschrijvingAlgoritmischAfgeleid() {
        return indAanschrijvingAlgoritmischAfgeleid;
    }

    /** @param indAanschrijvingAlgoritmischAfgeleid the indAanschrijvingAlgoritmischAfgeleid to set */
    public void setIndAanschrijvingAlgoritmischAfgeleid(final Boolean indAanschrijvingAlgoritmischAfgeleid) {
        this.indAanschrijvingAlgoritmischAfgeleid = indAanschrijvingAlgoritmischAfgeleid;
    }

    /** @return the aanschrijvingPredikaat */
    public Predikaat getAanschrijvingPredikaat() {
        return aanschrijvingPredikaat;
    }

    /** @param aanschrijvingPredikaat the aanschrijvingPredikaat to set */
    public void setAanschrijvingPredikaat(final Predikaat aanschrijvingPredikaat) {
        this.aanschrijvingPredikaat = aanschrijvingPredikaat;
    }

    /** @return the aanschrijvingVoornamen */
    public String getAanschrijvingVoornamen() {
        return aanschrijvingVoornamen;
    }

    /** @param aanschrijvingVoornamen the aanschrijvingVoornamen to set */
    public void setAanschrijvingVoornamen(final String aanschrijvingVoornamen) {
        this.aanschrijvingVoornamen = aanschrijvingVoornamen;
    }

    /** @return the aanschrijvinVoorvoegsel */
    public String getAanschrijvingVoorvoegsel() {
        return aanschrijvingVoorvoegsel;
    }

    /** @param aanschrijvinVoorvoegsel the aanschrijvinVoorvoegsel to set */
    public void setAanschrijvingVoorvoegsel(final String aanschrijvinVoorvoegsel) {
        this.aanschrijvingVoorvoegsel = aanschrijvinVoorvoegsel;
    }

    /** @return the aanschrijvingScheidingteken */
    public String getAanschrijvingScheidingteken() {
        return aanschrijvingScheidingteken;
    }

    /** @param aanschrijvingScheidingteken the aanschrijvingScheidingteken to set */
    public void setAanschrijvingScheidingteken(final String aanschrijvingScheidingteken) {
        this.aanschrijvingScheidingteken = aanschrijvingScheidingteken;
    }

    /** @return the aanschrijvingGeslachtnaam */
    public String getAanschrijvingGeslachtnaam() {
        return aanschrijvingGeslachtnaam;
    }

    /** @param aanschrijvingGeslachtnaam the aanschrijvingGeslachtnaam to set */
    public void setAanschrijvingGeslachtnaam(final String aanschrijvingGeslachtnaam) {
        this.aanschrijvingGeslachtnaam = aanschrijvingGeslachtnaam;
    }

    /** @return the verblijfsrechtId */
    public Long getVerblijfsrechtId() {
        return verblijfsrechtId;
    }

    /** @param verblijfsrechtId the verblijfsrechtId to set */
    public void setVerblijfsrechtId(final Long verblijfsrechtId) {
        this.verblijfsrechtId = verblijfsrechtId;
    }

    /** @return the verblijfsrechtDatumAanvang */
    public Integer getVerblijfsrechtDatumAanvang() {
        return verblijfsrechtDatumAanvang;
    }

    /** @param verblijfsrechtDatumAanvang the verblijfsrechtDatumAanvang to set */
    public void setVerblijfsrechtDatumAanvang(final Integer verblijfsrechtDatumAanvang) {
        this.verblijfsrechtDatumAanvang = verblijfsrechtDatumAanvang;
    }

    /** @return the verblijfsrechtDatumVoorzienEinde */
    public Integer getVerblijfsrechtDatumVoorzienEinde() {
        return verblijfsrechtDatumVoorzienEinde;
    }

    /** @param verblijfsrechtDatumVoorzienEinde the verblijfsrechtDatumVoorzienEinde to set */
    public void setVerblijfsrechtDatumVoorzienEinde(final Integer verblijfsrechtDatumVoorzienEinde) {
        this.verblijfsrechtDatumVoorzienEinde = verblijfsrechtDatumVoorzienEinde;
    }

    /** @return the verblijfsrechtDatumAanvangAaneensluitend */
    public Integer getVerblijfsrechtDatumAanvangAaneensluitend() {
        return verblijfsrechtDatumAanvangAaneensluitend;
    }

    /** @param verblijfsrechtDatumAanvangAaneensluitend the verblijfsrechtDatumAanvangAaneensluitend to set */
    public void setVerblijfsrechtDatumAanvangAaneensluitend(final Integer verblijfsrechtDatumAanvangAaneensluitend) {
        this.verblijfsrechtDatumAanvangAaneensluitend = verblijfsrechtDatumAanvangAaneensluitend;
    }

    /** @return the uitsluitingNLKiesrechtIndicatie */
    public Boolean getUitsluitingNLKiesrechtIndicatie() {
        return uitsluitingNLKiesrechtIndicatie;
    }

    /** @param uitsluitingNLKiesrechtIndicatie the uitsluitingNLKiesrechtIndicatie to set */
    public void setUitsluitingNLKiesrechtIndicatie(final Boolean uitsluitingNLKiesrechtIndicatie) {
        this.uitsluitingNLKiesrechtIndicatie = uitsluitingNLKiesrechtIndicatie;
    }

    /** @return the uitsluitingNLKiesrechtDatumEinde */
    public Integer getUitsluitingNLKiesrechtDatumEinde() {
        return uitsluitingNLKiesrechtDatumEinde;
    }

    /** @param uitsluitingNLKiesrechtDatumEinde the uitsluitingNLKiesrechtDatumEinde to set */
    public void setUitsluitingNLKiesrechtDatumEinde(final Integer uitsluitingNLKiesrechtDatumEinde) {
        this.uitsluitingNLKiesrechtDatumEinde = uitsluitingNLKiesrechtDatumEinde;
    }

    /** @return the eUVerkiezingenIndicatie */
    public Boolean geteUVerkiezingenIndicatie() {
        return eUVerkiezingenIndicatie;
    }

    /** @param eUVerkiezingenIndicatie the eUVerkiezingenIndicatie to set */
    public void seteUVerkiezingenIndicatie(final Boolean eUVerkiezingenIndicatie) {
        this.eUVerkiezingenIndicatie = eUVerkiezingenIndicatie;
    }

    /** @return the eUVerkiezingenDatumAanvang */
    public Integer geteUVerkiezingenDatumAanvang() {
        return eUVerkiezingenDatumAanvang;
    }

    /** @param eUVerkiezingenDatumAanvang the eUVerkiezingenDatumAanvang to set */
    public void seteUVerkiezingenDatumAanvang(final Integer eUVerkiezingenDatumAanvang) {
        this.eUVerkiezingenDatumAanvang = eUVerkiezingenDatumAanvang;
    }

    /** @return the eUVerkiezingenDatumEinde */
    public Integer geteUVerkiezingenDatumEinde() {
        return eUVerkiezingenDatumEinde;
    }

    /** @param eUVerkiezingenDatumEinde the eUVerkiezingenDatumEinde to set */
    public void seteUVerkiezingenDatumEinde(final Integer eUVerkiezingenDatumEinde) {
        this.eUVerkiezingenDatumEinde = eUVerkiezingenDatumEinde;
    }

    /** @return the bijhoudingsGemeenteDatumInschrijving */
    public Integer getBijhoudingsGemeenteDatumInschrijving() {
        return bijhoudingsGemeenteDatumInschrijving;
    }

    /** @param bijhoudingsGemeenteDatumInschrijving the bijhoudingsGemeenteDatumInschrijving to set */
    public void setBijhoudingsGemeenteDatumInschrijving(final Integer bijhoudingsGemeenteDatumInschrijving) {
        this.bijhoudingsGemeenteDatumInschrijving = bijhoudingsGemeenteDatumInschrijving;
    }

    /** @return the bijhoudingsGemeenteIndicatieOnverwerktDocumentAanwezig */
    public Boolean getBijhoudingsGemeenteIndicatieOnverwerktDocumentAanwezig() {
        return bijhoudingsGemeenteIndicatieOnverwerktDocumentAanwezig;
    }

    /**
     * @param bijhoudingsGemeenteIndicatieOnverwerktDocumentAanwezig the
     * bijhoudingsGemeenteIndicatieOnverwerktDocumentAanwezig
     * to set
     */
    public void setBijhoudingsGemeenteIndicatieOnverwerktDocumentAanwezig(
        final Boolean bijhoudingsGemeenteIndicatieOnverwerktDocumentAanwezig)
    {
        this.bijhoudingsGemeenteIndicatieOnverwerktDocumentAanwezig =
            bijhoudingsGemeenteIndicatieOnverwerktDocumentAanwezig;
    }

    /** @return the persoonskaartGemeente */
    public Partij getPersoonskaartGemeente() {
        return persoonskaartGemeente;
    }

    /** @param persoonskaartGemeente the persoonskaartGemeente to set */
    public void setPersoonskaartGemeente(final Partij persoonskaartGemeente) {
        this.persoonskaartGemeente = persoonskaartGemeente;
    }

    /** @return the persoonskaartIsVolledigGeconverteerd */
    public Boolean getPersoonskaartIsVolledigGeconverteerd() {
        return persoonskaartIsVolledigGeconverteerd;
    }

    /** @param persoonskaartIsVolledigGeconverteerd the persoonskaartIsVolledigGeconverteerd to set */
    public void setPersoonskaartIsVolledigGeconverteerd(final Boolean persoonskaartIsVolledigGeconverteerd) {
        this.persoonskaartIsVolledigGeconverteerd = persoonskaartIsVolledigGeconverteerd;
    }

    /** @return the immigratieLandHerkomst */
    public Land getImmigratieLandHerkomst() {
        return immigratieLandHerkomst;
    }

    /** @param immigratieLandHerkomst the immigratieLandHerkomst to set */
    public void setImmigratieLandHerkomst(final Land immigratieLandHerkomst) {
        this.immigratieLandHerkomst = immigratieLandHerkomst;
    }

    /** @return the immigratieVestigingDatum */
    public Integer getImmigratieVestigingDatum() {
        return immigratieVestigingDatum;
    }

    /** @param immigratieVestigingDatum the immigratieVestigingDatum to set */
    public void setImmigratieVestigingDatum(final Integer immigratieVestigingDatum) {
        this.immigratieVestigingDatum = immigratieVestigingDatum;
    }

    /** @return the inschrijvingDatum */
    public Integer getInschrijvingDatum() {
        return inschrijvingDatum;
    }

    /** @param inschrijvingDatum the inschrijvingDatum to set */
    public void setInschrijvingDatum(final Integer inschrijvingDatum) {
        this.inschrijvingDatum = inschrijvingDatum;
    }

    /** @return the inschrijvingVersienummer */
    public Long getInschrijvingVersienummer() {
        return inschrijvingVersienummer;
    }

    /** @param inschrijvingVersienummer the inschrijvingVersienummer to set */
    public void setInschrijvingVersienummer(final Long inschrijvingVersienummer) {
        this.inschrijvingVersienummer = inschrijvingVersienummer;
    }

    /** @return the inschrijvingVorigPersooonId */
    public Long getInschrijvingVorigPersooonId() {
        return inschrijvingVorigPersooonId;
    }

    /** @param inschrijvingVorigPersooonId the inschrijvingVorigPersooonId to set */
    public void setInschrijvingVorigPersooonId(final Long inschrijvingVorigPersooonId) {
        this.inschrijvingVorigPersooonId = inschrijvingVorigPersooonId;
    }

    /** @return the inschrijvingVolgendPersooonId */
    public Long getInschrijvingVolgendPersooonId() {
        return inschrijvingVolgendPersooonId;
    }

    /** @param inschrijvingVolgendPersooonId the inschrijvingVolgendPersooonId to set */
    public void setInschrijvingVolgendPersooonId(final Long inschrijvingVolgendPersooonId) {
        this.inschrijvingVolgendPersooonId = inschrijvingVolgendPersooonId;
    }

    /** @return the verantwoordelijke */
    public Verantwoordelijke getVerantwoordelijke() {
        return verantwoordelijke;
    }

    /** @param verantwoordelijke the verantwoordelijke to set */
    public void setVerantwoordelijke(final Verantwoordelijke verantwoordelijke) {
        this.verantwoordelijke = verantwoordelijke;
    }

    /** @return the redenOpschortingBijhouding */
    public RedenOpschorting getRedenOpschortingBijhouding() {
        return redenOpschortingBijhouding;
    }

    /** @param redenOpschortingBijhouding the redenOpschortingBijhouding to set */
    public void setRedenOpschortingBijhouding(final RedenOpschorting redenOpschortingBijhouding) {
        this.redenOpschortingBijhouding = redenOpschortingBijhouding;
    }

    /** @return the aanschrijvingWijzeGebruikGeslachtsnaam */
    public WijzeGebruikGeslachtsnaam getAanschrijvingWijzeGebruikGeslachtsnaam() {
        return aanschrijvingWijzeGebruikGeslachtsnaam;
    }

    /** @param aanschrijvingWijzeGebruikGeslachtsnaam the aanschrijvingWijzeGebruikGeslachtsnaam to set */
    public void setAanschrijvingWijzeGebruikGeslachtsnaam(
        final WijzeGebruikGeslachtsnaam aanschrijvingWijzeGebruikGeslachtsnaam)
    {
        this.aanschrijvingWijzeGebruikGeslachtsnaam = aanschrijvingWijzeGebruikGeslachtsnaam;
    }

    public Set<PersistentPersoonNationaliteit> getNationaliteiten() {
        return nationaliteiten;
    }

    public void setNationaliteiten(final Set<PersistentPersoonNationaliteit> nationaliteiten) {
        this.nationaliteiten = nationaliteiten;
    }

    /**
     * Voegt de opgegeven nationaliteit toe aan de set van nationaliteiten. Indien de set (nog) niet geinitialiseerd
     * is, wordt dit eerst gedaan door een lege set aan te maken en dan de nationaliteit toe te voegen.
     *
     * @param nationaliteit de nationaliteit die toegevoegd dient te worden.
     */
    public void voegNationaliteitToe(final PersistentPersoonNationaliteit nationaliteit) {
        if (nationaliteiten == null) {
            nationaliteiten = new HashSet<PersistentPersoonNationaliteit>();
        }
        nationaliteiten.add(nationaliteit);
    }

    /** @return the laatstGewijzigd */
    public Date getLaatstGewijzigd() {
        if (laatstGewijzigd == null) {
            return null;
        } else {
            return (Date) laatstGewijzigd.clone();
        }
    }

    /** @param laatstGewijzigd the laatstGewijzigd to set */
    public void setLaatstGewijzigd(final Date laatstGewijzigd) {
        if (laatstGewijzigd == null) {
            this.laatstGewijzigd = null;
        } else {
            this.laatstGewijzigd = (Date) laatstGewijzigd.clone();
        }
    }

    /** @return the indGegevensInOnderzoek */
    public Boolean getIndGegevensInOnderzoek() {
        return indGegevensInOnderzoek;
    }

    /** @param indGegevensInOnderzoek the indGegevensInOnderzoek to set */
    public void setIndGegevensInOnderzoek(final Boolean indGegevensInOnderzoek) {
        this.indGegevensInOnderzoek = indGegevensInOnderzoek;
    }

    public Set<PersistentBetrokkenheid> getBetrokkenheden() {
        return betrokkenheden;
    }

    public void setBetrokkenheden(final Set<PersistentBetrokkenheid> betrokkenheden) {
        this.betrokkenheden = betrokkenheden;
    }
}
