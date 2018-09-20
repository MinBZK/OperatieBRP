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
 * Deze class bevat de persoonsgegevens zoals gedefinieerd in de {@link nl.bzk.brp.bevraging.domein.kern.Persoon} interface.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "Pers", schema = "Kern")
@Access(AccessType.FIELD)
public class Persoon implements Serializable, nl.bzk.brp.bevraging.domein.kern.Persoon {

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

    @Override
    public Persoon getPersoon() {
        return this;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public SoortPersoon getSoort() {
        return soort;
    }

    @Override
    public Long getBurgerservicenummer() {
        return burgerservicenummer;
    }

    public void setBurgerservicenummer(final Long bsn) {
        this.burgerservicenummer = bsn;
    }

    @Override
    public Long getAdministratienummer() {
        return administratienummer;
    }

    public void setAdministratienummer(final Long administratienummer) {
        this.administratienummer = administratienummer;
    }

    @Override
    public String getGeslachtsnaam() {
        return geslachtsnaam;
    }

    public void setGeslachtsnaam(final String geslachtsnaam) {
        this.geslachtsnaam = geslachtsnaam;
    }

    @Override
    public String getVoornamen() {
        return voornamen;
    }

    public void setVoornamen(final String voornamen) {
        this.voornamen = voornamen;
    }

    @Override
    public String getVoorvoegsel() {
        return voorvoegsel;
    }

    @Override
    public String getScheidingsTeken() {
        return scheidingsTeken;
    }

    @Override
    public GeslachtsAanduiding getGeslachtsAanduiding() {
        return geslachtsAanduiding;
    }

    @Override
    public Integer getGeboorteDatum() {
        return geboorteDatum;
    }

    @Override
    public Partij getBijhoudingsGemeente() {
        return bijhoudingsGemeente;
    }

    public void setBijhoudingsGemeente(final Partij bijhoudingsGemeente) {
        this.bijhoudingsGemeente = bijhoudingsGemeente;
    }

    @Override
    public String getBuitenlandseGeboorteplaats() {
        return buitenlandseGeboorteplaats;
    }

    public void setBuitenlandseGeboorteplaats(final String buitenlandseGeboorteplaats) {
        this.buitenlandseGeboorteplaats = buitenlandseGeboorteplaats;
    }

    @Override
    public String getBuitenlandsePlaatsOverlijden() {
        return buitenlandsePlaatsOverlijden;
    }

    public void setBuitenlandsePlaatsOverlijden(final String buitenlandsePlaatsOverlijden) {
        this.buitenlandsePlaatsOverlijden = buitenlandsePlaatsOverlijden;
    }

    @Override
    public String getBuitenlandseRegioGeboorte() {
        return buitenlandseRegioGeboorte;
    }

    public void setBuitenlandseRegioGeboorte(final String buitenlandseRegioGeboorte) {
        this.buitenlandseRegioGeboorte = buitenlandseRegioGeboorte;
    }

    @Override
    public Partij getGemeenteGeboorte() {
        return gemeenteGeboorte;
    }

    public void setGemeenteGeboorte(final Partij gemeenteGeboorte) {
        this.gemeenteGeboorte = gemeenteGeboorte;
    }

    @Override
    public Land getLandGeboorte() {
        return landGeboorte;
    }

    public void setLandGeboorte(final Land landGeboorte) {
        this.landGeboorte = landGeboorte;
    }

    @Override
    public Set<PersoonAdres> getAdressen() {
        if (adressen == null) {
            return null;
        }
        return Collections.unmodifiableSet(adressen);
    }

    @Override
    public String getOmschrijvingGeboorteLocatie() {
        return omschrijvingGeboorteLocatie;
    }

    public void setOmschrijvingGeboorteLocatie(final String omschrijvingGeboorteLocatie) {
        this.omschrijvingGeboorteLocatie = omschrijvingGeboorteLocatie;
    }

    @Override
    public RedenOpschorting getRedenOpschortingBijhouding() {
        return redenOpschortingBijhouding;
    }

    public void setRedenOpschortingBijhouding(final RedenOpschorting redenOpschorting) {
        this.redenOpschortingBijhouding = redenOpschorting;
    }

    @Override
    public Plaats getWoonplaatsGeboorte() {
        return woonplaatsGeboorte;
    }

    public void setWoonplaatsGeboorte(final Plaats woonplaatsGeboorte) {
        this.woonplaatsGeboorte = woonplaatsGeboorte;
    }

    @Override
    public Boolean behandeldAlsNederlander() {
        return getIndicatie(SoortIndicatie.BEHANDELD_ALS_NEDERLANDER);
    }

    @Override
    public Boolean belemmeringVerstrekkingReisdocument() {
        return getIndicatie(SoortIndicatie.BELEMMERING_VERSTREKKING_REISDOCUMENT);
    }

    @Override
    public Boolean bezitBuitenlandsReisdocument() {
        return getIndicatie(SoortIndicatie.BEZIT_BUITENLANDS_REISDOCUMENT);
    }

    @Override
    public Boolean deelnameEUVerkiezingen() {
        return getIndicatie(SoortIndicatie.DEELNAME_EU_VERKIEZINGEN);
    }

    @Override
    public Boolean derdeHeeftGezag() {
        return getIndicatie(SoortIndicatie.DERDE_HEEFT_GEZAG);
    }

    @Override
    public Boolean gepriviligeerde() {
        return getIndicatie(SoortIndicatie.GEPRIVILEGIEERDE);
    }

    @Override
    public Boolean onderCuratele() {
        return getIndicatie(SoortIndicatie.ONDER_CURATELE);
    }

    @Override
    public Boolean uitsluitingNLKiesrecht() {
        return getIndicatie(SoortIndicatie.UITSLUITING_NL_KIESRECHT);
    }

    @Override
    public Boolean vastgesteldNietNederlander() {
        return getIndicatie(SoortIndicatie.VASTGESTELD_NIET_NEDERLANDER);
    }

    @Override
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

    @Override
    public final String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("id", id).append("soort", soort)
                .append("BSN", burgerservicenummer).append("geslachtsnaam", geslachtsnaam).toString();
    }

}
