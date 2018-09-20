/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.domein;

import java.util.Map;
import java.util.Set;
import javax.persistence.*;

import nl.bzk.brp.bevraging.domein.*;

/**
 * Persoon model class tbv POC bijhouding.
 */
@SuppressWarnings("serial") @Entity @Table(name = "Pers", schema = "Kern") @Access(AccessType.FIELD)
public class PocPersoon {

    @SequenceGenerator(name = "PERSOON_SEQUENCE_GENERATOR", sequenceName = "Kern.seq_Pers")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSOON_SEQUENCE_GENERATOR")
    private Long                                  id;
    @Column(name = "srt")
    private SoortPersoon                          soort;
    @Column(name = "bsn")
    protected Long                                burgerservicenummer;
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
    private Integer                               datumGeboorte;
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
    private Set<PocPersoonAdres>                  adressen;
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
    public PocPersoon() {
    }

    /**
     * Constructor voor nieuwe instanties.
     *
     * @param soortPersoon het soort persoon dat wordt gecreeerd.
     */
    public PocPersoon(final SoortPersoon soortPersoon) {
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

    public void setBurgerservicenummer(final Long burgerservicenummer) {
        this.burgerservicenummer = burgerservicenummer;
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

    public String getGeslachtsnaam() {
        return geslachtsnaam;
    }

    public GeslachtsAanduiding getGeslachtsAanduiding() {
        return geslachtsAanduiding;
    }

    public Integer getDatumGeboorte() {
        return datumGeboorte;
    }

    public Partij getGemeenteGeboorte() {
        return gemeenteGeboorte;
    }

    public Land getLandGeboorte() {
        return landGeboorte;
    }

    public Partij getBijhoudingsGemeente() {
        return bijhoudingsGemeente;
    }

    public String getBuitenlandseGeboorteplaats() {
        return buitenlandseGeboorteplaats;
    }

    public String getBuitenlandseRegioGeboorte() {
        return buitenlandseRegioGeboorte;
    }

    public String getBuitenlandsePlaatsOverlijden() {
        return buitenlandsePlaatsOverlijden;
    }

    public String getOmschrijvingGeboorteLocatie() {
        return omschrijvingGeboorteLocatie;
    }

    public RedenOpschorting getRedenOpschortingBijhouding() {
        return redenOpschortingBijhouding;
    }

    public Set<PocPersoonAdres> getAdressen() {
        return adressen;
    }

    public void setAdressen(final Set<PocPersoonAdres> adressen) {
        this.adressen = adressen;
    }

    public Plaats getWoonplaatsGeboorte() {
        return woonplaatsGeboorte;
    }

    public Map<SoortIndicatie, PersoonIndicatie> getIndicaties() {
        return indicaties;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setSoort(final SoortPersoon soort) {
        this.soort = soort;
    }

    public void setVoornamen(final String voornamen) {
        this.voornamen = voornamen;
    }

    public void setVoorvoegsel(final String voorvoegsel) {
        this.voorvoegsel = voorvoegsel;
    }

    public void setScheidingsTeken(final String scheidingsTeken) {
        this.scheidingsTeken = scheidingsTeken;
    }

    public void setGeslachtsnaam(final String geslachtsnaam) {
        this.geslachtsnaam = geslachtsnaam;
    }

    public void setGeslachtsAanduiding(final GeslachtsAanduiding geslachtsAanduiding) {
        this.geslachtsAanduiding = geslachtsAanduiding;
    }

    public void setDatumGeboorte(final Integer datumGeboorte) {
        this.datumGeboorte = datumGeboorte;
    }

    public void setGemeenteGeboorte(final Partij gemeenteGeboorte) {
        this.gemeenteGeboorte = gemeenteGeboorte;
    }

    public void setLandGeboorte(final Land landGeboorte) {
        this.landGeboorte = landGeboorte;
    }

    public void setBijhoudingsGemeente(final Partij bijhoudingsGemeente) {
        this.bijhoudingsGemeente = bijhoudingsGemeente;
    }

    public void setBuitenlandseGeboorteplaats(final String buitenlandseGeboorteplaats) {
        this.buitenlandseGeboorteplaats = buitenlandseGeboorteplaats;
    }

    public void setBuitenlandseRegioGeboorte(final String buitenlandseRegioGeboorte) {
        this.buitenlandseRegioGeboorte = buitenlandseRegioGeboorte;
    }

    public void setBuitenlandsePlaatsOverlijden(final String buitenlandsePlaatsOverlijden) {
        this.buitenlandsePlaatsOverlijden = buitenlandsePlaatsOverlijden;
    }

    public void setOmschrijvingGeboorteLocatie(final String omschrijvingGeboorteLocatie) {
        this.omschrijvingGeboorteLocatie = omschrijvingGeboorteLocatie;
    }

    public void setRedenOpschortingBijhouding(final RedenOpschorting redenOpschortingBijhouding) {
        this.redenOpschortingBijhouding = redenOpschortingBijhouding;
    }

    public void setWoonplaatsGeboorte(final Plaats woonplaatsGeboorte) {
        this.woonplaatsGeboorte = woonplaatsGeboorte;
    }

    public void setIndicaties(final Map<SoortIndicatie, PersoonIndicatie> indicaties) {
        this.indicaties = indicaties;
    }
}
