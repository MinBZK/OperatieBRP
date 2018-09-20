/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nl.bzk.brp.model.PersistentRootObject;
import nl.bzk.brp.model.gedeeld.GeslachtsAanduiding;
import nl.bzk.brp.model.gedeeld.Land;
import nl.bzk.brp.model.gedeeld.Partij;
import nl.bzk.brp.model.gedeeld.Plaats;
import nl.bzk.brp.model.gedeeld.SoortPersoon;
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

    @Column(name = "geslachtsaand")
    @Enumerated
    private GeslachtsAanduiding geslachtsAanduiding;

    @Column(name = "geslnaam")
    private String geslachtsNaam;

    @Column(name = "voornamen")
    private String voornaam;

    @ManyToOne
    @JoinColumn(name = "bijhgem")
    private Partij bijhoudingsGemeente;

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
    private Set<PersistentPersoonAdres> adressen;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pers")
    private Set<PersistentPersoonIndicatie> persoonIndicaties =
        new HashSet<PersistentPersoonIndicatie>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pers")
    private Set<HisPersoonGeboorte>            hisPersoonGeboorte            =
        new HashSet<HisPersoonGeboorte>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pers")
    private Set<HisPersoonGeslachtsaanduiding> hisPersoonGeslachtsaanduiding =
        new HashSet<HisPersoonGeslachtsaanduiding>();

    @Column(name = "srt")
    @Enumerated
    private SoortPersoon soortPersoon;

    @Column(name = "idsstatushis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie statushistorie;

    @Column(name = "geslachtsaandstatusHis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie geslachtsaanduidingStatusHis;

    @Column(name = "samengesteldenaamstatushis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie samengesteldeNaamStatusHis;

    @Column(name = "aanschrstatushis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie aanschrijvingStatusHis;

    @Column(name = "geboortestatushis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie geboorteStatusHis;

    @Column(name = "overlijdenstatushis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie overlijdenStatusHis;

    @Column(name = "verblijfsrstatushis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie verblijfsrechtStatusHis;

    @Column(name = "uitslnlkiesrstatushis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie uitsluitingNLKiesrechtStatusHis;

    @Column(name = "euverkiezingenstatushis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie eUVerkiezingenStatusHis;

    @Column(name = "bijhverantwoordelijkheidstat")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie bijhoudingsverantwoordelijkheidStatusHis;

    @Column(name = "opschortingstatushis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie opschortingStatusHis;

    @Column(name = "bijhgemstatushis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie bijhoudingsgemeenteStatusHis;

    @Column(name = "pkstatushis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie persoonskaartStatusHis;

    @Column(name = "immigratiestatushis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie immigratieStatusHis;

    @Column(name = "inschrstatushis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie inschrijvingStatusHis;

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

    public Set<HisPersoonGeboorte> getHisPersoonGeboorte() {
        return hisPersoonGeboorte;
    }

    public void setHisPersoonGeboorte(final Set<HisPersoonGeboorte> hisPersoonGeboorte) {
        this.hisPersoonGeboorte = hisPersoonGeboorte;
    }

    /**
     * Voeg historie persoon geboorte toe aan de hisPersoonGeboorte set.
     *
     * @param persoonGeboorte de {@link HisPersoonGeboorte}
     */
    public void voegHisPersoonGeboorteToe(final HisPersoonGeboorte persoonGeboorte) {
        hisPersoonGeboorte.add(persoonGeboorte);
    }

    public Set<HisPersoonGeslachtsaanduiding> getHisPersoonGeslachtsaanduiding() {
        return hisPersoonGeslachtsaanduiding;
    }

    public void setHisPersoonGeslachtsaanduiding(
        final Set<HisPersoonGeslachtsaanduiding> hisPersoonGeslachtsaanduiding)
    {
        this.hisPersoonGeslachtsaanduiding = hisPersoonGeslachtsaanduiding;
    }

    /**
     * Voeg historie persoon geslachtsaanduiding toe aan de hisPersoonGeslachtsaanduiding set.
     *
     * @param persoonGeslachtsaanduiding de {@link HisPersoonGeslachtsaanduiding}
     */
    public void voegToeHisPersoonGeslachtsaanduiding(final HisPersoonGeslachtsaanduiding persoonGeslachtsaanduiding) {
        hisPersoonGeslachtsaanduiding.add(persoonGeslachtsaanduiding);
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

    public void setANummer(final String aNummer) {
        this.aNummer = aNummer;
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

}
