/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
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
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * Een persoon is een drager van rechten en plichten die een mens is.
 *
 * De populatie personen bestaat uit ingeschrevenen. Het betreft zowel ingezetenen
 * als niet-ingezetenen. Zie ook groep "Bijhoudingsverantwoordelijkheid" en objecttype
 * "Betrokkenheid".
 *
 * 1. Binnen BRP gebruiken we het begrip Persoon, waar elders gebruikelijk is voor
 * dit objecttype de naam "Natuurlijk persoon" te gebruiken. Binnen de context van
 * BRP hebben we het bij het hanteren van de term Persoon echter nooit over
 * niet-natuurlijke personen zoals rechtspersonen. Het gebruik van de term Persoon
 * is verder dermate gebruikelijk binnen de context van BRP, dat ervoor gekozen is
 * deze naam te blijven hanteren. We spreken dus over Persoon en niet over "Natuurlijk persoon".
 *
 * 2. Voor "alternatieve realiteit" personen, en voor niet-ingeschrevenen, gebruiken
 * we in het logisch & operationeel model (maar dus NIET in de gegevensset) het
 * construct 'persoon'. Om die reden zijn veel groepen niet verplicht, die wellicht
 * wel verplicht zouden zijn in geval van (alleen) ingeschrevenen.RvdP 27 juni 2011
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "Pers", schema = "Kern")
@Access(AccessType.FIELD)
public class Persoon implements Serializable {

    @SequenceGenerator(name = "PERSOON_SEQUENCE_GENERATOR", sequenceName = "Kern.seq_Pers")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSOON_SEQUENCE_GENERATOR")
    private Long                                  id;
    @Column(name = "srt")
    private SoortPersoon                          soort;
    @Column(name = "bsn")
    private Long                                  burgerservicenummer;
    @Column(name = "anr")
    private Long                                  administratienummer;
    @Column(name = "voornamen")
    private String                                voornamen;
    @Column(name = "voorvoegsel")
    private String                                voorvoegsel;
    @Column(name = "scheidingsteken")
    private String                                scheidingsTeken;
    @Column(name = "geslnaam")
    private String                                geslachtsnaam;
    @Column(name = "geslachtsaand")
    private GeslachtsAanduiding                   geslachtsAanduiding;
    @Column(name = "datgeboorte")
    private Integer                               geboorteDatum;
    @ManyToOne
    @JoinColumn(name = "GemGeboorte")
    private Partij                                gemeenteGeboorte;
    @ManyToOne
    @JoinColumn(name = "LandGeboorte")
    private Land                                  landGeboorte;
    @ManyToOne
    @JoinColumn(name = "Bijhgem")
    private Partij                                bijhoudingsGemeente;
    @Column(name = "BLGeboorteplaats")
    private String                                buitenlandseGeboorteplaats;
    @Column(name = "BLRegioGeboorte")
    private String                                buitenlandseRegioGeboorte;
    @Column(name = "BLPlaatsOverlijden")
    private String                                buitenlandsePlaatsOverlijden;
    @Column(name = "OmsGeboorteLoc")
    private String                                omschrijvingGeboorteLocatie;
    @Column(name = "RdnOpschortingBijhouding")
    private RedenOpschorting                      redenOpschortingBijhouding;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "Pers")
    private Set<PersoonAdres>                     adressen;
    @ManyToOne
    @JoinColumn(name = "WplGeboorte")
    private Plaats                                woonplaatsGeboorte;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "Pers")
    @MapKey(name = "soort")
    private Map<SoortIndicatie, PersoonIndicatie> indicaties;

    /**
     * No-arg constructor voor JPA.
     */
    protected Persoon() {
    }

    /**
     * Constructor voor nieuwe instanties.
     *
     * @param soortPersoon het soort persoon dat wordt gecreeerd.
     */
    public Persoon(final SoortPersoon soortPersoon) {
        this.soort = soortPersoon;
    }

    public Long getId() {
        return id;
    }

    public SoortPersoon getSoort() {
        return soort;
    }

    public Long getBurgerservicenummer() {
        return burgerservicenummer;
    }

    public void setBurgerservicenummer(final Long bsn) {
        this.burgerservicenummer = bsn;
    }

    public Long getAdministratienummer() {
        return administratienummer;
    }

    public void setAdministratienummer(final Long administratienummer) {
        this.administratienummer = administratienummer;
    }

    public String getGeslachtsnaam() {
        return geslachtsnaam;
    }

    public void setGeslachtsnaam(final String geslachtsnaam) {
        this.geslachtsnaam = geslachtsnaam;
    }

    public String getVoornamen() {
        return voornamen;
    }

    public String getVoorvoegsel() {
        return voorvoegsel;
    }

    public String getScheidingsTeken() {
        return scheidingsTeken;
    }

    public GeslachtsAanduiding getGeslachtsAanduiding() {
        return geslachtsAanduiding;
    }

    public Integer getGeboorteDatum() {
        return geboorteDatum;
    }

    public Partij getBijhoudingsGemeente() {
        return bijhoudingsGemeente;
    }

    public void setBijhoudingsGemeente(final Partij bijhoudingsGemeente) {
        this.bijhoudingsGemeente = bijhoudingsGemeente;
    }

    public String getBuitenlandseGeboorteplaats() {
        return buitenlandseGeboorteplaats;
    }

    public void setBuitenlandseGeboorteplaats(final String buitenlandseGeboorteplaats) {
        this.buitenlandseGeboorteplaats = buitenlandseGeboorteplaats;
    }

    public String getBuitenlandsePlaatsOverlijden() {
        return buitenlandsePlaatsOverlijden;
    }

    public void setBuitenlandsePlaatsOverlijden(final String buitenlandsePlaatsOverlijden) {
        this.buitenlandsePlaatsOverlijden = buitenlandsePlaatsOverlijden;
    }

    public String getBuitenlandseRegioGeboorte() {
        return buitenlandseRegioGeboorte;
    }

    public void setBuitenlandseRegioGeboorte(final String buitenlandseRegioGeboorte) {
        this.buitenlandseRegioGeboorte = buitenlandseRegioGeboorte;
    }

    public Partij getGemeenteGeboorte() {
        return gemeenteGeboorte;
    }

    public void setGemeenteGeboorte(final Partij gemeenteGeboorte) {
        this.gemeenteGeboorte = gemeenteGeboorte;
    }

    public Land getLandGeboorte() {
        return landGeboorte;
    }

    public void setLandGeboorte(final Land landGeboorte) {
        this.landGeboorte = landGeboorte;
    }

    /**
     * Geeft de verzameling adressen van deze {@link Persoon} als een unmodifiable {@link Set}.
     *
     * @return de verzameling adressen van deze {@link Persoon} als een unmodifiable {@link Set}.
     */
    public Set<PersoonAdres> getAdressen() {
        if (adressen == null) {
            return null;
        }
        return Collections.unmodifiableSet(adressen);
    }

    public String getOmschrijvingGeboorteLocatie() {
        return omschrijvingGeboorteLocatie;
    }

    public void setOmschrijvingGeboorteLocatie(final String omschrijvingGeboorteLocatie) {
        this.omschrijvingGeboorteLocatie = omschrijvingGeboorteLocatie;
    }

    public RedenOpschorting getRedenOpschortingBijhouding() {
        return redenOpschortingBijhouding;
    }

    public void setRedenOpschortingBijhouding(final RedenOpschorting redenOpschorting) {
        this.redenOpschortingBijhouding = redenOpschorting;
    }

    public Plaats getWoonplaatsGeboorte() {
        return woonplaatsGeboorte;
    }

    public void setWoonplaatsGeboorte(final Plaats woonplaatsGeboorte) {
        this.woonplaatsGeboorte = woonplaatsGeboorte;
    }

    /**
     * Indicator die aangeeft dat de persoon wordt behandeld als Nederlander.
     *
     * @return {@link Boolean#TRUE} als dit waar is, {@link Boolean#TRUE} als dit niet waar is, en <code>null</code> als
     *         dit niet van toepassing is.
     */
    public Boolean behandeldAlsNederlander() {
        return getIndicatie(SoortIndicatie.BEHANDELD_ALS_NEDERLANDER);
    }

    /**
     * Indicator die aangeeft dat aan de persoon geen reisdocument verstrekt mag worden.
     *
     * @return {@link Boolean#TRUE} als dit waar is, {@link Boolean#TRUE} als dit niet waar is, en <code>null</code> als
     *         dit niet van toepassing is.
     */
    public Boolean belemmeringVerstrekkingReisdocument() {
        return getIndicatie(SoortIndicatie.BELEMMERING_VERSTREKKING_REISDOCUMENT);
    }

    /**
     * Indicator die aangeeft dat de ingeschrevene beschikt over één of meer buitenlandse reisdocumenten of is
     * bijgeschreven in een buitenlands reisdocument.
     *
     * @return {@link Boolean#TRUE} als dit waar is, {@link Boolean#TRUE} als dit niet waar is, en <code>null</code> als
     *         dit niet van toepassing is.
     */
    public Boolean bezitBuitenlandsReisdocument() {
        return getIndicatie(SoortIndicatie.BEZIT_BUITENLANDS_REISDOCUMENT);
    }

    /**
     * Indicator die aangeeft of de persoon deel kan nemen aan de verkiezing van de leden van het Europees parlement,
     * zoals beschreven in afdeling V van de Kieswet.
     *
     * @return {@link Boolean#TRUE} als dit waar is, {@link Boolean#TRUE} als dit niet waar is, en <code>null</code> als
     *         dit niet van toepassing is.
     */
    public Boolean deelnameEUVerkiezingen() {
        return getIndicatie(SoortIndicatie.DEELNAME_EU_VERKIEZINGEN);
    }

    /**
     * Indicator die aangeeft dat een derde het gezag over de minderjarige persoon heeft.
     *
     * @return {@link Boolean#TRUE} als dit waar is, {@link Boolean#TRUE} als dit niet waar is, en <code>null</code> als
     *         dit niet van toepassing is.
     */
    public Boolean derdeHeeftGezag() {
        return getIndicatie(SoortIndicatie.DERDE_HEEFT_GEZAG);
    }

    /**
     * Indicator die aangeeft dat de betrokken persoon een geprivilegieerde status heeft.
     *
     * @return {@link Boolean#TRUE} als dit waar is, {@link Boolean#TRUE} als dit niet waar is, en <code>null</code> als
     *         dit niet van toepassing is.
     */
    public Boolean gepriviligeerde() {
        return getIndicatie(SoortIndicatie.GEPRIVILEGIEERDE);
    }

    /**
     * Indicator die aangeeft dat de persoon onder curatele staat.
     *
     * @return {@link Boolean#TRUE} als dit waar is, {@link Boolean#TRUE} als dit niet waar is, en <code>null</code> als
     *         dit niet van toepassing is.
     */
    public Boolean onderCuratele() {
        return getIndicatie(SoortIndicatie.ONDER_CURATELE);
    }

    /**
     * Indicatie dat de betrokkene uitgesloten is van het actieve kiesrecht voor de verkiezingen zoals bedoeld in de
     * afdelingen II, III en IV van de Kieswet.
     *
     * @return {@link Boolean#TRUE} als dit waar is, {@link Boolean#TRUE} als dit niet waar is, en <code>null</code> als
     *         dit niet van toepassing is.
     */
    public Boolean uitsluitingNLKiesrecht() {
        return getIndicatie(SoortIndicatie.UITSLUITING_NL_KIESRECHT);
    }

    /**
     * Indicator die aangeeft dat vastgesteld is dat de persoon geen Nederlander is.
     *
     * @return {@link Boolean#TRUE} als dit waar is, {@link Boolean#TRUE} als dit niet waar is, en <code>null</code> als
     *         dit niet van toepassing is.
     */
    public Boolean vastgesteldNietNederlander() {
        return getIndicatie(SoortIndicatie.VASTGESTELD_NIET_NEDERLANDER);
    }

    /**
     * Indicator die aangeeft dat de persoon heeft gekozen voor beperkte verstrekking van zijn/haar gegevens aan derden.
     *
     * @return {@link Boolean#TRUE} als dit waar is, {@link Boolean#TRUE} als dit niet waar is, en <code>null</code> als
     *         dit niet van toepassing is.
     */
    public Boolean verstrekkingsBeperking() {
        return getIndicatie(SoortIndicatie.VERSTREKKINGSBEPERKING);
    }

    /**
     * Generieke method om uit de {@link Map} van indicatoren &eacute;&eacute;n specifieke indicator te selecteren.
     *
     * @param soortIndicatie een enumerator die aangeeft welke indicator gevraagd wordt.
     * @return een {@link Boolean} die aangeeft of de gevonden indicator waar, niet waar of onbekend is.
     */
    private Boolean getIndicatie(final SoortIndicatie soortIndicatie) {
        if (indicaties != null) {
            PersoonIndicatie indicatie = indicaties.get(soortIndicatie);
            if (indicatie != null) {
                return indicatie.getWaarde();
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("id", id).append("soort", soort)
                .append("BSN", burgerservicenummer).append("geslachtsnaam", geslachtsnaam).toString();
    }
}
