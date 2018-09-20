/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.kern;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAanschrijvingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonBijhoudingsaardModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonBijhoudingsgemeenteModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonBijzondereVerblijfsrechtelijkePositieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonEUVerkiezingenModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeboorteModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsaanduidingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIdentificatienummersModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonImmigratieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonInschrijvingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonOpschortingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonOverlijdenModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonPersoonskaartModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonSamengesteldeNaamModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonUitsluitingNLKiesrechtModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVerblijfstitelModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/** HisVolledig klasse voor Persoon. */
@Entity
@Table(schema = "Kern", name = "Pers")
public class PersoonHisVolledig {

    @Id
    @JsonProperty
    private Integer iD;

    @Enumerated
    @Column(name = "Srt", updatable = false, insertable = false)
    @JsonProperty
    private SoortPersoon soort;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Set<HisPersoonIdentificatienummersModel> hisPersoonIdentificatienummersLijst;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Set<HisPersoonSamengesteldeNaamModel> hisPersoonSamengesteldeNaamLijst;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Set<HisPersoonGeboorteModel> hisPersoonGeboorteLijst;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Set<HisPersoonGeslachtsaanduidingModel> hisPersoonGeslachtsaanduidingLijst;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Set<HisPersoonInschrijvingModel> hisPersoonInschrijvingLijst;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Set<HisPersoonBijhoudingsaardModel> hisPersoonBijhoudingsaardLijst;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Set<HisPersoonBijhoudingsgemeenteModel> hisPersoonBijhoudingsgemeenteLijst;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Set<HisPersoonOpschortingModel> hisPersoonOpschortingLijst;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Set<HisPersoonOverlijdenModel> hisPersoonOverlijdenLijst;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Set<HisPersoonAanschrijvingModel> hisPersoonAanschrijvingLijst;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Set<HisPersoonImmigratieModel> hisPersoonImmigratieLijst;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Set<HisPersoonVerblijfstitelModel> hisPersoonVerblijfstitelLijst;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Set<HisPersoonBijzondereVerblijfsrechtelijkePositieModel>
        hisPersoonBijzondereVerblijfsrechtelijkePositieLijst;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Set<HisPersoonUitsluitingNLKiesrechtModel> hisPersoonUitsluitingNLKiesrechtLijst;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Set<HisPersoonEUVerkiezingenModel> hisPersoonEUVerkiezingenLijst;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Set<HisPersoonPersoonskaartModel> hisPersoonPersoonskaartLijst;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Set<PersoonVoornaamHisVolledig> voornamen;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Set<PersoonGeslachtsnaamcomponentHisVolledig> geslachtsnaamcomponenten;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    @JsonManagedReference
    private Set<PersoonNationaliteitHisVolledig> nationaliteiten;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    @JsonManagedReference
    private Set<PersoonAdresHisVolledig> adressen;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Set<PersoonIndicatieHisVolledig> indicaties;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Set<PersoonReisdocumentHisVolledig> reisdocumenten;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    @JsonManagedReference
    private Set<BetrokkenheidHisVolledig> betrokkenheden;

    /**
     * Retourneert ID van Persoon.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Soort van Persoon.
     *
     * @return Soort.
     */
    public SoortPersoon getSoort() {
        return soort;
    }

    /**
     * Retourneert lijst van historie van Identificatienummers van Persoon.
     *
     * @return lijst van historie voor groep Identificatienummers
     */
    public Set<HisPersoonIdentificatienummersModel> getHisPersoonIdentificatienummersLijst() {
        return hisPersoonIdentificatienummersLijst;
    }

    /**
     * Retourneert lijst van historie van Samengestelde naam van Persoon.
     *
     * @return lijst van historie voor groep Samengestelde naam
     */
    public Set<HisPersoonSamengesteldeNaamModel> getHisPersoonSamengesteldeNaamLijst() {
        return hisPersoonSamengesteldeNaamLijst;
    }

    /**
     * Retourneert lijst van historie van Geboorte van Persoon.
     *
     * @return lijst van historie voor groep Geboorte
     */
    public Set<HisPersoonGeboorteModel> getHisPersoonGeboorteLijst() {
        return hisPersoonGeboorteLijst;
    }

    /**
     * Retourneert lijst van historie van Geslachtsaanduiding van Persoon.
     *
     * @return lijst van historie voor groep Geslachtsaanduiding
     */
    public Set<HisPersoonGeslachtsaanduidingModel> getHisPersoonGeslachtsaanduidingLijst() {
        return hisPersoonGeslachtsaanduidingLijst;
    }

    /**
     * Retourneert lijst van historie van Inschrijving van Persoon.
     *
     * @return lijst van historie voor groep Inschrijving
     */
    public Set<HisPersoonInschrijvingModel> getHisPersoonInschrijvingLijst() {
        return hisPersoonInschrijvingLijst;
    }

    /**
     * Retourneert lijst van historie van Bijhoudingsaard van Persoon.
     *
     * @return lijst van historie voor groep Bijhoudingsaard
     */
    public Set<HisPersoonBijhoudingsaardModel> getHisPersoonBijhoudingsaardLijst() {
        return hisPersoonBijhoudingsaardLijst;
    }

    /**
     * Retourneert lijst van historie van Bijhoudingsgemeente van Persoon.
     *
     * @return lijst van historie voor groep Bijhoudingsgemeente
     */
    public Set<HisPersoonBijhoudingsgemeenteModel> getHisPersoonBijhoudingsgemeenteLijst() {
        return hisPersoonBijhoudingsgemeenteLijst;
    }

    /**
     * Retourneert lijst van historie van Opschorting van Persoon.
     *
     * @return lijst van historie voor groep Opschorting
     */
    public Set<HisPersoonOpschortingModel> getHisPersoonOpschortingLijst() {
        return hisPersoonOpschortingLijst;
    }

    /**
     * Retourneert lijst van historie van Overlijden van Persoon.
     *
     * @return lijst van historie voor groep Overlijden
     */
    public Set<HisPersoonOverlijdenModel> getHisPersoonOverlijdenLijst() {
        return hisPersoonOverlijdenLijst;
    }

    /**
     * Retourneert lijst van historie van Aanschrijving van Persoon.
     *
     * @return lijst van historie voor groep Aanschrijving
     */
    public Set<HisPersoonAanschrijvingModel> getHisPersoonAanschrijvingLijst() {
        return hisPersoonAanschrijvingLijst;
    }

    /**
     * Retourneert lijst van historie van Immigratie van Persoon.
     *
     * @return lijst van historie voor groep Immigratie
     */
    public Set<HisPersoonImmigratieModel> getHisPersoonImmigratieLijst() {
        return hisPersoonImmigratieLijst;
    }

    /**
     * Retourneert lijst van historie van Verblijfstitel van Persoon.
     *
     * @return lijst van historie voor groep Verblijfstitel
     */
    public Set<HisPersoonVerblijfstitelModel> getHisPersoonVerblijfstitelLijst() {
        return hisPersoonVerblijfstitelLijst;
    }

    /**
     * Retourneert lijst van historie van Bijzondere verblijfsrechtelijke positie van Persoon.
     *
     * @return lijst van historie voor groep Bijzondere verblijfsrechtelijke positie
     */
    public Set<HisPersoonBijzondereVerblijfsrechtelijkePositieModel>
    getHisPersoonBijzondereVerblijfsrechtelijkePositieLijst() {
        return hisPersoonBijzondereVerblijfsrechtelijkePositieLijst;
    }

    /**
     * Retourneert lijst van historie van Uitsluiting NL kiesrecht van Persoon.
     *
     * @return lijst van historie voor groep Uitsluiting NL kiesrecht
     */
    public Set<HisPersoonUitsluitingNLKiesrechtModel> getHisPersoonUitsluitingNLKiesrechtLijst() {
        return hisPersoonUitsluitingNLKiesrechtLijst;
    }

    /**
     * Retourneert lijst van historie van EU verkiezingen van Persoon.
     *
     * @return lijst van historie voor groep EU verkiezingen
     */
    public Set<HisPersoonEUVerkiezingenModel> getHisPersoonEUVerkiezingenLijst() {
        return hisPersoonEUVerkiezingenLijst;
    }

    /**
     * Retourneert lijst van historie van Persoonskaart van Persoon.
     *
     * @return lijst van historie voor groep Persoonskaart
     */
    public Set<HisPersoonPersoonskaartModel> getHisPersoonPersoonskaartLijst() {
        return hisPersoonPersoonskaartLijst;
    }

    /**
     * Retourneert lijst van Persoon \ Voornaam.
     *
     * @return lijst van Persoon \ Voornaam
     */
    public Set<PersoonVoornaamHisVolledig> getVoornamen() {
        return voornamen;
    }

    /**
     * Retourneert lijst van Persoon \ Geslachtsnaamcomponent.
     *
     * @return lijst van Persoon \ Geslachtsnaamcomponent
     */
    public Set<PersoonGeslachtsnaamcomponentHisVolledig> getGeslachtsnaamcomponenten() {
        return geslachtsnaamcomponenten;
    }

    /**
     * Retourneert lijst van Persoon \ Nationaliteit.
     *
     * @return lijst van Persoon \ Nationaliteit
     */
    public Set<PersoonNationaliteitHisVolledig> getNationaliteiten() {
        return nationaliteiten;
    }

    /**
     * Retourneert lijst van Persoon \ Adres.
     *
     * @return lijst van Persoon \ Adres
     */
    public Set<PersoonAdresHisVolledig> getAdressen() {
        return adressen;
    }

    /**
     * Retourneert lijst van Persoon \ Indicatie.
     *
     * @return lijst van Persoon \ Indicatie
     */
    public Set<PersoonIndicatieHisVolledig> getIndicaties() {
        return indicaties;
    }

    /**
     * Retourneert lijst van Persoon \ Reisdocument.
     *
     * @return lijst van Persoon \ Reisdocument
     */
    public Set<PersoonReisdocumentHisVolledig> getReisdocumenten() {
        return reisdocumenten;
    }

    /**
     * Retourneert lijst van Betrokkenheid.
     *
     * @return lijst van Betrokkenheid
     */
    public Set<BetrokkenheidHisVolledig> getBetrokkenheden() {
        return betrokkenheden;
    }

    /**
     * Zet lijst van historie van Identificatienummers van Persoon.
     *
     * @param hisPersoonIdentificatienummersLijst lijst van historie voor groep Identificatienummers
     */
    public void setHisPersoonIdentificatienummersLijst(
        final Set<HisPersoonIdentificatienummersModel> hisPersoonIdentificatienummersLijst)
    {
        this.hisPersoonIdentificatienummersLijst = hisPersoonIdentificatienummersLijst;
    }

    /**
     * Zet lijst van historie van Samengestelde naam van Persoon.
     *
     * @param hisPersoonSamengesteldeNaamLijst lijst van historie voor groep Samengestelde naam
     */
    public void setHisPersoonSamengesteldeNaamLijst(
        final Set<HisPersoonSamengesteldeNaamModel> hisPersoonSamengesteldeNaamLijst)
    {
        this.hisPersoonSamengesteldeNaamLijst = hisPersoonSamengesteldeNaamLijst;
    }

    /**
     * Zet lijst van historie van Geboorte van Persoon.
     *
     * @param hisPersoonGeboorteLijst lijst van historie voor groep Geboorte
     */
    public void setHisPersoonGeboorteLijst(final Set<HisPersoonGeboorteModel> hisPersoonGeboorteLijst) {
        this.hisPersoonGeboorteLijst = hisPersoonGeboorteLijst;
    }

    /**
     * Zet lijst van historie van Geslachtsaanduiding van Persoon.
     *
     * @param hisPersoonGeslachtsaanduidingLijst lijst van historie voor groep Geslachtsaanduiding
     */
    public void setHisPersoonGeslachtsaanduidingLijst(
        final Set<HisPersoonGeslachtsaanduidingModel> hisPersoonGeslachtsaanduidingLijst)
    {
        this.hisPersoonGeslachtsaanduidingLijst = hisPersoonGeslachtsaanduidingLijst;
    }

    /**
     * Zet lijst van historie van Inschrijving van Persoon.
     *
     * @param hisPersoonInschrijvingLijst lijst van historie voor groep Inschrijving
     */
    public void setHisPersoonInschrijvingLijst(final Set<HisPersoonInschrijvingModel> hisPersoonInschrijvingLijst) {
        this.hisPersoonInschrijvingLijst = hisPersoonInschrijvingLijst;
    }

    /**
     * Zet lijst van historie van Bijhoudingsaard van Persoon.
     *
     * @param hisPersoonBijhoudingsaardLijst lijst van historie voor groep Bijhoudingsaard
     */
    public void setHisPersoonBijhoudingsaardLijst(
        final Set<HisPersoonBijhoudingsaardModel> hisPersoonBijhoudingsaardLijst)
    {
        this.hisPersoonBijhoudingsaardLijst = hisPersoonBijhoudingsaardLijst;
    }

    /**
     * Zet lijst van historie van Bijhoudingsgemeente van Persoon.
     *
     * @param hisPersoonBijhoudingsgemeenteLijst lijst van historie voor groep Bijhoudingsgemeente
     */
    public void setHisPersoonBijhoudingsgemeenteLijst(
        final Set<HisPersoonBijhoudingsgemeenteModel> hisPersoonBijhoudingsgemeenteLijst)
    {
        this.hisPersoonBijhoudingsgemeenteLijst = hisPersoonBijhoudingsgemeenteLijst;
    }

    /**
     * Zet lijst van historie van Opschorting van Persoon.
     *
     * @param hisPersoonOpschortingLijst lijst van historie voor groep Opschorting
     */
    public void setHisPersoonOpschortingLijst(final Set<HisPersoonOpschortingModel> hisPersoonOpschortingLijst) {
        this.hisPersoonOpschortingLijst = hisPersoonOpschortingLijst;
    }

    /**
     * Zet lijst van historie van Overlijden van Persoon.
     *
     * @param hisPersoonOverlijdenLijst lijst van historie voor groep Overlijden
     */
    public void setHisPersoonOverlijdenLijst(final Set<HisPersoonOverlijdenModel> hisPersoonOverlijdenLijst) {
        this.hisPersoonOverlijdenLijst = hisPersoonOverlijdenLijst;
    }

    /**
     * Zet lijst van historie van Aanschrijving van Persoon.
     *
     * @param hisPersoonAanschrijvingLijst lijst van historie voor groep Aanschrijving
     */
    public void setHisPersoonAanschrijvingLijst(final Set<HisPersoonAanschrijvingModel> hisPersoonAanschrijvingLijst) {
        this.hisPersoonAanschrijvingLijst = hisPersoonAanschrijvingLijst;
    }

    /**
     * Zet lijst van historie van Immigratie van Persoon.
     *
     * @param hisPersoonImmigratieLijst lijst van historie voor groep Immigratie
     */
    public void setHisPersoonImmigratieLijst(final Set<HisPersoonImmigratieModel> hisPersoonImmigratieLijst) {
        this.hisPersoonImmigratieLijst = hisPersoonImmigratieLijst;
    }

    /**
     * Zet lijst van historie van Verblijfstitel van Persoon.
     *
     * @param hisPersoonVerblijfstitelLijst lijst van historie voor groep Verblijfstitel
     */
    public void
    setHisPersoonVerblijfstitelLijst(final Set<HisPersoonVerblijfstitelModel> hisPersoonVerblijfstitelLijst) {
        this.hisPersoonVerblijfstitelLijst = hisPersoonVerblijfstitelLijst;
    }

    /**
     * Zet lijst van historie van Bijzondere verblijfsrechtelijke positie van Persoon.
     *
     * @param hisPersoonBijzondereVerblijfsrechtelijkePositieLijst lijst van historie voor groep Bijzondere
     * verblijfsrechtelijke
     * positie
     */
    public void
    setHisPersoonBijzondereVerblijfsrechtelijkePositieLijst(
        final Set<HisPersoonBijzondereVerblijfsrechtelijkePositieModel>
            hisPersoonBijzondereVerblijfsrechtelijkePositieLijst)
    {
        this.hisPersoonBijzondereVerblijfsrechtelijkePositieLijst =
            hisPersoonBijzondereVerblijfsrechtelijkePositieLijst;
    }

    /**
     * Zet lijst van historie van Uitsluiting NL kiesrecht van Persoon.
     *
     * @param hisPersoonUitsluitingNLKiesrechtLijst lijst van historie voor groep Uitsluiting NL kiesrecht
     */
    public void setHisPersoonUitsluitingNLKiesrechtLijst(
        final Set<HisPersoonUitsluitingNLKiesrechtModel> hisPersoonUitsluitingNLKiesrechtLijst)
    {
        this.hisPersoonUitsluitingNLKiesrechtLijst = hisPersoonUitsluitingNLKiesrechtLijst;
    }

    /**
     * Zet lijst van historie van EU verkiezingen van Persoon.
     *
     * @param hisPersoonEUVerkiezingenLijst lijst van historie voor groep EU verkiezingen
     */
    public void
    setHisPersoonEUVerkiezingenLijst(final Set<HisPersoonEUVerkiezingenModel> hisPersoonEUVerkiezingenLijst) {
        this.hisPersoonEUVerkiezingenLijst = hisPersoonEUVerkiezingenLijst;
    }

    /**
     * Zet lijst van historie van Persoonskaart van Persoon.
     *
     * @param hisPersoonPersoonskaartLijst lijst van historie voor groep Persoonskaart
     */
    public void setHisPersoonPersoonskaartLijst(final Set<HisPersoonPersoonskaartModel> hisPersoonPersoonskaartLijst) {
        this.hisPersoonPersoonskaartLijst = hisPersoonPersoonskaartLijst;
    }

    /**
     * Zet lijst van Persoon \ Voornaam.
     *
     * @param voornamen lijst van Persoon \ Voornaam
     */
    public void setVoornamen(final Set<PersoonVoornaamHisVolledig> voornamen) {
        this.voornamen = voornamen;
    }

    /**
     * Zet lijst van Persoon \ Geslachtsnaamcomponent.
     *
     * @param geslachtsnaamcomponenten lijst van Persoon \ Geslachtsnaamcomponent
     */
    public void
    setGeslachtsnaamcomponenten(final Set<PersoonGeslachtsnaamcomponentHisVolledig> geslachtsnaamcomponenten) {
        this.geslachtsnaamcomponenten = geslachtsnaamcomponenten;
    }

    /**
     * Zet lijst van Persoon \ Nationaliteit.
     *
     * @param nationaliteiten lijst van Persoon \ Nationaliteit
     */
    public void setNationaliteiten(final Set<PersoonNationaliteitHisVolledig> nationaliteiten) {
        this.nationaliteiten = nationaliteiten;
    }

    /**
     * Zet lijst van Persoon \ Adres.
     *
     * @param adressen lijst van Persoon \ Adres
     */
    public void setAdressen(final Set<PersoonAdresHisVolledig> adressen) {
        this.adressen = adressen;
    }

    /**
     * Zet lijst van Persoon \ Indicatie.
     *
     * @param indicaties lijst van Persoon \ Indicatie
     */
    public void setIndicaties(final Set<PersoonIndicatieHisVolledig> indicaties) {
        this.indicaties = indicaties;
    }

    /**
     * Zet lijst van Persoon \ Reisdocument.
     *
     * @param reisdocumenten lijst van Persoon \ Reisdocument
     */
    public void setReisdocumenten(final Set<PersoonReisdocumentHisVolledig> reisdocumenten) {
        this.reisdocumenten = reisdocumenten;
    }

    /**
     * Zet lijst van Betrokkenheid.
     *
     * @param betrokkenheden lijst van Betrokkenheid
     */
    public void setBetrokkenheden(final Set<BetrokkenheidHisVolledig> betrokkenheden) {
        this.betrokkenheden = betrokkenheden;
    }

}
