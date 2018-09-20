/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.kern;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import nl.bzk.brp.model.FormeleHistorieSet;
import nl.bzk.brp.model.FormeleHistorieSetImpl;
import nl.bzk.brp.model.MaterieleHistorieSet;
import nl.bzk.brp.model.MaterieleHistorieSetImpl;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.basis.ALaagAfleidbaar;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.GesorteerdeSetOpVolgnummer;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.basis.VolgnummerComparator;
import nl.bzk.brp.model.hisvolledig.impl.autaut.PersoonAfnemerindicatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.HisVolledigComparatorFactory;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledigBasis;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAfgeleidAdministratiefModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonBijhoudingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonDeelnameEUVerkiezingenModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeboorteModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsaanduidingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIdentificatienummersModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonInschrijvingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonMigratieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNaamgebruikModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNummerverwijzingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonOverlijdenModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonPersoonskaartModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonSamengesteldeNaamModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonUitsluitingKiesrechtModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVerblijfsrechtModel;
import nl.bzk.brp.model.operationeel.kern.PersoonAfgeleidAdministratiefGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonBijhoudingGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonDeelnameEUVerkiezingenGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonGeboorteGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonGeslachtsaanduidingGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonIdentificatienummersGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonInschrijvingGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonMigratieGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonNaamgebruikGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonNummerverwijzingGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonOverlijdenGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonPersoonskaartGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonSamengesteldeNaamGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonUitsluitingKiesrechtGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonVerblijfsrechtGroepModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.PredicateUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

/**
 * HisVolledig klasse voor Persoon.
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractPersoonHisVolledigImpl implements HisVolledigImpl, PersoonHisVolledigBasis, ALaagAfleidbaar, ElementIdentificeerbaar {

    @Transient
    @JsonProperty
    private Integer iD;

    @Embedded
    @AttributeOverride(name = SoortPersoonAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Srt"))
    @JsonProperty
    private SoortPersoonAttribuut soort;

    @Embedded
    private PersoonAfgeleidAdministratiefGroepModel afgeleidAdministratief;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<HisPersoonAfgeleidAdministratiefModel> hisPersoonAfgeleidAdministratiefLijst;

    @Transient
    private FormeleHistorieSet<HisPersoonAfgeleidAdministratiefModel> persoonAfgeleidAdministratiefHistorie;

    @Embedded
    private PersoonIdentificatienummersGroepModel identificatienummers;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<HisPersoonIdentificatienummersModel> hisPersoonIdentificatienummersLijst;

    @Transient
    private MaterieleHistorieSet<HisPersoonIdentificatienummersModel> persoonIdentificatienummersHistorie;

    @Embedded
    private PersoonSamengesteldeNaamGroepModel samengesteldeNaam;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<HisPersoonSamengesteldeNaamModel> hisPersoonSamengesteldeNaamLijst;

    @Transient
    private MaterieleHistorieSet<HisPersoonSamengesteldeNaamModel> persoonSamengesteldeNaamHistorie;

    @Embedded
    private PersoonGeboorteGroepModel geboorte;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<HisPersoonGeboorteModel> hisPersoonGeboorteLijst;

    @Transient
    private FormeleHistorieSet<HisPersoonGeboorteModel> persoonGeboorteHistorie;

    @Embedded
    private PersoonGeslachtsaanduidingGroepModel geslachtsaanduiding;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<HisPersoonGeslachtsaanduidingModel> hisPersoonGeslachtsaanduidingLijst;

    @Transient
    private MaterieleHistorieSet<HisPersoonGeslachtsaanduidingModel> persoonGeslachtsaanduidingHistorie;

    @Embedded
    private PersoonInschrijvingGroepModel inschrijving;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<HisPersoonInschrijvingModel> hisPersoonInschrijvingLijst;

    @Transient
    private FormeleHistorieSet<HisPersoonInschrijvingModel> persoonInschrijvingHistorie;

    @Embedded
    private PersoonNummerverwijzingGroepModel nummerverwijzing;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<HisPersoonNummerverwijzingModel> hisPersoonNummerverwijzingLijst;

    @Transient
    private MaterieleHistorieSet<HisPersoonNummerverwijzingModel> persoonNummerverwijzingHistorie;

    @Embedded
    private PersoonBijhoudingGroepModel bijhouding;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<HisPersoonBijhoudingModel> hisPersoonBijhoudingLijst;

    @Transient
    private MaterieleHistorieSet<HisPersoonBijhoudingModel> persoonBijhoudingHistorie;

    @Embedded
    private PersoonOverlijdenGroepModel overlijden;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<HisPersoonOverlijdenModel> hisPersoonOverlijdenLijst;

    @Transient
    private FormeleHistorieSet<HisPersoonOverlijdenModel> persoonOverlijdenHistorie;

    @Embedded
    private PersoonNaamgebruikGroepModel naamgebruik;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<HisPersoonNaamgebruikModel> hisPersoonNaamgebruikLijst;

    @Transient
    private FormeleHistorieSet<HisPersoonNaamgebruikModel> persoonNaamgebruikHistorie;

    @Embedded
    private PersoonMigratieGroepModel migratie;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<HisPersoonMigratieModel> hisPersoonMigratieLijst;

    @Transient
    private MaterieleHistorieSet<HisPersoonMigratieModel> persoonMigratieHistorie;

    @Embedded
    private PersoonVerblijfsrechtGroepModel verblijfsrecht;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<HisPersoonVerblijfsrechtModel> hisPersoonVerblijfsrechtLijst;

    @Transient
    private FormeleHistorieSet<HisPersoonVerblijfsrechtModel> persoonVerblijfsrechtHistorie;

    @Embedded
    private PersoonUitsluitingKiesrechtGroepModel uitsluitingKiesrecht;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<HisPersoonUitsluitingKiesrechtModel> hisPersoonUitsluitingKiesrechtLijst;

    @Transient
    private FormeleHistorieSet<HisPersoonUitsluitingKiesrechtModel> persoonUitsluitingKiesrechtHistorie;

    @Embedded
    private PersoonDeelnameEUVerkiezingenGroepModel deelnameEUVerkiezingen;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<HisPersoonDeelnameEUVerkiezingenModel> hisPersoonDeelnameEUVerkiezingenLijst;

    @Transient
    private FormeleHistorieSet<HisPersoonDeelnameEUVerkiezingenModel> persoonDeelnameEUVerkiezingenHistorie;

    @Embedded
    private PersoonPersoonskaartGroepModel persoonskaart;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<HisPersoonPersoonskaartModel> hisPersoonPersoonskaartLijst;

    @Transient
    private FormeleHistorieSet<HisPersoonPersoonskaartModel> persoonPersoonskaartHistorie;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    @JsonDeserialize(as = GesorteerdeSetOpVolgnummer.class)
    @Sort(type = SortType.COMPARATOR, comparator = VolgnummerComparator.class)
    private SortedSet<PersoonVoornaamHisVolledigImpl> voornamen;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    @JsonDeserialize(as = GesorteerdeSetOpVolgnummer.class)
    @Sort(type = SortType.COMPARATOR, comparator = VolgnummerComparator.class)
    private SortedSet<PersoonGeslachtsnaamcomponentHisVolledigImpl> geslachtsnaamcomponenten;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "geverifieerde", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<PersoonVerificatieHisVolledigImpl> verificaties;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<PersoonNationaliteitHisVolledigImpl> nationaliteiten;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<PersoonAdresHisVolledigImpl> adressen;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<PersoonReisdocumentHisVolledigImpl> reisdocumenten;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    private Set<BetrokkenheidHisVolledigImpl> betrokkenheden;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<PersoonOnderzoekHisVolledigImpl> onderzoeken;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<PersoonVerstrekkingsbeperkingHisVolledigImpl> verstrekkingsbeperkingen;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoon", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    // Handmatige wijziging: niet serialisere @JsonProperty
    @JsonManagedReference
    private Set<PersoonAfnemerindicatieHisVolledigImpl> afnemerindicaties;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "persoon")
    @JsonProperty
    private PersoonIndicatieDerdeHeeftGezagHisVolledigImpl indicatieDerdeHeeftGezag;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "persoon")
    @JsonProperty
    private PersoonIndicatieOnderCurateleHisVolledigImpl indicatieOnderCuratele;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "persoon")
    @JsonProperty
    private PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImpl indicatieVolledigeVerstrekkingsbeperking;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "persoon")
    @JsonProperty
    private PersoonIndicatieVastgesteldNietNederlanderHisVolledigImpl indicatieVastgesteldNietNederlander;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "persoon")
    @JsonProperty
    private PersoonIndicatieBehandeldAlsNederlanderHisVolledigImpl indicatieBehandeldAlsNederlander;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "persoon")
    @JsonProperty
    private PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImpl indicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "persoon")
    @JsonProperty
    private PersoonIndicatieStaatloosHisVolledigImpl indicatieStaatloos;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "persoon")
    @JsonProperty
    private PersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledigImpl indicatieBijzondereVerblijfsrechtelijkePositie;

    /**
     * Default contructor voor JPA.
     *
     */
    protected AbstractPersoonHisVolledigImpl() {
        voornamen = new TreeSet<PersoonVoornaamHisVolledigImpl>(HisVolledigComparatorFactory.getComparatorVoorPersoonVoornaam());
        geslachtsnaamcomponenten =
                new TreeSet<PersoonGeslachtsnaamcomponentHisVolledigImpl>(HisVolledigComparatorFactory.getComparatorVoorPersoonGeslachtsnaamcomponent());
        verificaties = new HashSet<PersoonVerificatieHisVolledigImpl>();
        nationaliteiten = new HashSet<PersoonNationaliteitHisVolledigImpl>();
        adressen = new HashSet<PersoonAdresHisVolledigImpl>();
        reisdocumenten = new HashSet<PersoonReisdocumentHisVolledigImpl>();
        betrokkenheden = new HashSet<BetrokkenheidHisVolledigImpl>();
        onderzoeken = new HashSet<PersoonOnderzoekHisVolledigImpl>();
        verstrekkingsbeperkingen = new HashSet<PersoonVerstrekkingsbeperkingHisVolledigImpl>();
        afnemerindicaties = new HashSet<PersoonAfnemerindicatieHisVolledigImpl>();

    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param soort soort van Persoon.
     */
    public AbstractPersoonHisVolledigImpl(final SoortPersoonAttribuut soort) {
        this();
        this.soort = soort;

    }

    /**
     * Retourneert ID van Persoon.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "PERSOON", sequenceName = "Kern.seq_Pers")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PERSOON")
    @Access(value = AccessType.PROPERTY)
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Soort van Persoon.
     *
     * @return Soort.
     */
    public SoortPersoonAttribuut getSoort() {
        return soort;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SortedSet<PersoonVoornaamHisVolledigImpl> getVoornamen() {
        return voornamen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SortedSet<PersoonGeslachtsnaamcomponentHisVolledigImpl> getGeslachtsnaamcomponenten() {
        return geslachtsnaamcomponenten;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<PersoonVerificatieHisVolledigImpl> getVerificaties() {
        return verificaties;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<PersoonNationaliteitHisVolledigImpl> getNationaliteiten() {
        return nationaliteiten;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<PersoonAdresHisVolledigImpl> getAdressen() {
        return adressen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<PersoonReisdocumentHisVolledigImpl> getReisdocumenten() {
        return reisdocumenten;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<BetrokkenheidHisVolledigImpl> getBetrokkenheden() {
        return betrokkenheden;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<PersoonOnderzoekHisVolledigImpl> getOnderzoeken() {
        return onderzoeken;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<PersoonVerstrekkingsbeperkingHisVolledigImpl> getVerstrekkingsbeperkingen() {
        return verstrekkingsbeperkingen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<PersoonAfnemerindicatieHisVolledigImpl> getAfnemerindicaties() {
        return afnemerindicaties;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonIndicatieDerdeHeeftGezagHisVolledigImpl getIndicatieDerdeHeeftGezag() {
        return indicatieDerdeHeeftGezag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonIndicatieOnderCurateleHisVolledigImpl getIndicatieOnderCuratele() {
        return indicatieOnderCuratele;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImpl getIndicatieVolledigeVerstrekkingsbeperking() {
        return indicatieVolledigeVerstrekkingsbeperking;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonIndicatieVastgesteldNietNederlanderHisVolledigImpl getIndicatieVastgesteldNietNederlander() {
        return indicatieVastgesteldNietNederlander;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonIndicatieBehandeldAlsNederlanderHisVolledigImpl getIndicatieBehandeldAlsNederlander() {
        return indicatieBehandeldAlsNederlander;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImpl getIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument()
    {
        return indicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonIndicatieStaatloosHisVolledigImpl getIndicatieStaatloos() {
        return indicatieStaatloos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledigImpl getIndicatieBijzondereVerblijfsrechtelijkePositie() {
        return indicatieBijzondereVerblijfsrechtelijkePositie;
    }

    /**
     * Setter is verplicht voor JPA, omdat de Id annotatie op de getter zit. We maken de functie private voor de
     * zekerheid.
     *
     * @param id Id
     */
    private void setID(final Integer id) {
        this.iD = id;
    }

    /**
     * Zet lijst van Persoon \ Voornaam.
     *
     * @param voornamen lijst van Persoon \ Voornaam
     */
    public void setVoornamen(final SortedSet<PersoonVoornaamHisVolledigImpl> voornamen) {
        this.voornamen = voornamen;
    }

    /**
     * Zet lijst van Persoon \ Geslachtsnaamcomponent.
     *
     * @param geslachtsnaamcomponenten lijst van Persoon \ Geslachtsnaamcomponent
     */
    public void setGeslachtsnaamcomponenten(final SortedSet<PersoonGeslachtsnaamcomponentHisVolledigImpl> geslachtsnaamcomponenten) {
        this.geslachtsnaamcomponenten = geslachtsnaamcomponenten;
    }

    /**
     * Zet lijst van Persoon \ Verificatie.
     *
     * @param verificaties lijst van Persoon \ Verificatie
     */
    public void setVerificaties(final Set<PersoonVerificatieHisVolledigImpl> verificaties) {
        this.verificaties = verificaties;
    }

    /**
     * Zet lijst van Persoon \ Nationaliteit.
     *
     * @param nationaliteiten lijst van Persoon \ Nationaliteit
     */
    public void setNationaliteiten(final Set<PersoonNationaliteitHisVolledigImpl> nationaliteiten) {
        this.nationaliteiten = nationaliteiten;
    }

    /**
     * Zet lijst van Persoon \ Adres.
     *
     * @param adressen lijst van Persoon \ Adres
     */
    public void setAdressen(final Set<PersoonAdresHisVolledigImpl> adressen) {
        this.adressen = adressen;
    }

    /**
     * Zet lijst van Persoon \ Reisdocument.
     *
     * @param reisdocumenten lijst van Persoon \ Reisdocument
     */
    public void setReisdocumenten(final Set<PersoonReisdocumentHisVolledigImpl> reisdocumenten) {
        this.reisdocumenten = reisdocumenten;
    }

    /**
     * Zet lijst van Betrokkenheid.
     *
     * @param betrokkenheden lijst van Betrokkenheid
     */
    public void setBetrokkenheden(final Set<BetrokkenheidHisVolledigImpl> betrokkenheden) {
        this.betrokkenheden = betrokkenheden;
    }

    /**
     * Zet lijst van Persoon \ Onderzoek.
     *
     * @param onderzoeken lijst van Persoon \ Onderzoek
     */
    public void setOnderzoeken(final Set<PersoonOnderzoekHisVolledigImpl> onderzoeken) {
        this.onderzoeken = onderzoeken;
    }

    /**
     * Zet lijst van Persoon \ Verstrekkingsbeperking.
     *
     * @param verstrekkingsbeperkingen lijst van Persoon \ Verstrekkingsbeperking
     */
    public void setVerstrekkingsbeperkingen(final Set<PersoonVerstrekkingsbeperkingHisVolledigImpl> verstrekkingsbeperkingen) {
        this.verstrekkingsbeperkingen = verstrekkingsbeperkingen;
    }

    /**
     * Zet lijst van Persoon \ Afnemerindicatie.
     *
     * @param afnemerindicaties lijst van Persoon \ Afnemerindicatie
     */
    public void setAfnemerindicaties(final Set<PersoonAfnemerindicatieHisVolledigImpl> afnemerindicaties) {
        this.afnemerindicaties = afnemerindicaties;
    }

    /**
     * Zet de indicatie his volledig voor subtype Derde heeft gezag?. Let op: alleen gebruiken als er nog geen his
     * volledig van dit subtype is! Voor het toevoegen van historie aan een bestaande indicatie his volledig, gebruik de
     * indicatie his volledig getter + getHistorie + voegToe.
     *
     * @param indicatieDerdeHeeftGezag indicatie
     */
    public void setIndicatieDerdeHeeftGezag(final PersoonIndicatieDerdeHeeftGezagHisVolledigImpl indicatieDerdeHeeftGezag) {
        this.indicatieDerdeHeeftGezag = indicatieDerdeHeeftGezag;
    }

    /**
     * Zet de indicatie his volledig voor subtype Onder curatele?. Let op: alleen gebruiken als er nog geen his volledig
     * van dit subtype is! Voor het toevoegen van historie aan een bestaande indicatie his volledig, gebruik de
     * indicatie his volledig getter + getHistorie + voegToe.
     *
     * @param indicatieOnderCuratele indicatie
     */
    public void setIndicatieOnderCuratele(final PersoonIndicatieOnderCurateleHisVolledigImpl indicatieOnderCuratele) {
        this.indicatieOnderCuratele = indicatieOnderCuratele;
    }

    /**
     * Zet de indicatie his volledig voor subtype Volledige verstrekkingsbeperking?. Let op: alleen gebruiken als er nog
     * geen his volledig van dit subtype is! Voor het toevoegen van historie aan een bestaande indicatie his volledig,
     * gebruik de indicatie his volledig getter + getHistorie + voegToe.
     *
     * @param indicatieVolledigeVerstrekkingsbeperking indicatie
     */
    public void setIndicatieVolledigeVerstrekkingsbeperking(
        final PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImpl indicatieVolledigeVerstrekkingsbeperking)
    {
        this.indicatieVolledigeVerstrekkingsbeperking = indicatieVolledigeVerstrekkingsbeperking;
    }

    /**
     * Zet de indicatie his volledig voor subtype Vastgesteld niet Nederlander?. Let op: alleen gebruiken als er nog
     * geen his volledig van dit subtype is! Voor het toevoegen van historie aan een bestaande indicatie his volledig,
     * gebruik de indicatie his volledig getter + getHistorie + voegToe.
     *
     * @param indicatieVastgesteldNietNederlander indicatie
     */
    public void setIndicatieVastgesteldNietNederlander(final PersoonIndicatieVastgesteldNietNederlanderHisVolledigImpl indicatieVastgesteldNietNederlander)
    {
        this.indicatieVastgesteldNietNederlander = indicatieVastgesteldNietNederlander;
    }

    /**
     * Zet de indicatie his volledig voor subtype Behandeld als Nederlander?. Let op: alleen gebruiken als er nog geen
     * his volledig van dit subtype is! Voor het toevoegen van historie aan een bestaande indicatie his volledig,
     * gebruik de indicatie his volledig getter + getHistorie + voegToe.
     *
     * @param indicatieBehandeldAlsNederlander indicatie
     */
    public void setIndicatieBehandeldAlsNederlander(final PersoonIndicatieBehandeldAlsNederlanderHisVolledigImpl indicatieBehandeldAlsNederlander) {
        this.indicatieBehandeldAlsNederlander = indicatieBehandeldAlsNederlander;
    }

    /**
     * Zet de indicatie his volledig voor subtype Signalering met betrekking tot verstrekken reisdocument?. Let op:
     * alleen gebruiken als er nog geen his volledig van dit subtype is! Voor het toevoegen van historie aan een
     * bestaande indicatie his volledig, gebruik de indicatie his volledig getter + getHistorie + voegToe.
     *
     * @param indicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument indicatie
     */
    public void setIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument(
        final PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImpl indicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument)
    {
        this.indicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument = indicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument;
    }

    /**
     * Zet de indicatie his volledig voor subtype Staatloos?. Let op: alleen gebruiken als er nog geen his volledig van
     * dit subtype is! Voor het toevoegen van historie aan een bestaande indicatie his volledig, gebruik de indicatie
     * his volledig getter + getHistorie + voegToe.
     *
     * @param indicatieStaatloos indicatie
     */
    public void setIndicatieStaatloos(final PersoonIndicatieStaatloosHisVolledigImpl indicatieStaatloos) {
        this.indicatieStaatloos = indicatieStaatloos;
    }

    /**
     * Zet de indicatie his volledig voor subtype Bijzondere verblijfsrechtelijke positie?. Let op: alleen gebruiken als
     * er nog geen his volledig van dit subtype is! Voor het toevoegen van historie aan een bestaande indicatie his
     * volledig, gebruik de indicatie his volledig getter + getHistorie + voegToe.
     *
     * @param indicatieBijzondereVerblijfsrechtelijkePositie indicatie
     */
    public void setIndicatieBijzondereVerblijfsrechtelijkePositie(
        final PersoonIndicatieBijzondereVerblijfsrechtelijkePositieHisVolledigImpl indicatieBijzondereVerblijfsrechtelijkePositie)
    {
        this.indicatieBijzondereVerblijfsrechtelijkePositie = indicatieBijzondereVerblijfsrechtelijkePositie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void leidALaagAf() {
        final HisPersoonAfgeleidAdministratiefModel actueelAfgeleidAdministratief = getPersoonAfgeleidAdministratiefHistorie().getActueleRecord();
        if (actueelAfgeleidAdministratief != null) {
            this.afgeleidAdministratief =
                    new PersoonAfgeleidAdministratiefGroepModel(
                        actueelAfgeleidAdministratief.getAdministratieveHandeling(),
                        actueelAfgeleidAdministratief.getTijdstipLaatsteWijziging(),
                        actueelAfgeleidAdministratief.getSorteervolgorde(),
                        actueelAfgeleidAdministratief.getIndicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig(),
                        actueelAfgeleidAdministratief.getTijdstipLaatsteWijzigingGBASystematiek());
        } else {
            this.afgeleidAdministratief = null;
        }
        final HisPersoonIdentificatienummersModel actueelIdentificatienummers = getPersoonIdentificatienummersHistorie().getActueleRecord();
        if (actueelIdentificatienummers != null) {
            this.identificatienummers =
                    new PersoonIdentificatienummersGroepModel(
                        actueelIdentificatienummers.getBurgerservicenummer(),
                        actueelIdentificatienummers.getAdministratienummer());
        } else {
            this.identificatienummers = null;
        }
        final HisPersoonSamengesteldeNaamModel actueelSamengesteldeNaam = getPersoonSamengesteldeNaamHistorie().getActueleRecord();
        if (actueelSamengesteldeNaam != null) {
            this.samengesteldeNaam =
                    new PersoonSamengesteldeNaamGroepModel(
                        actueelSamengesteldeNaam.getIndicatieAfgeleid(),
                        actueelSamengesteldeNaam.getIndicatieNamenreeks(),
                        actueelSamengesteldeNaam.getPredicaat(),
                        actueelSamengesteldeNaam.getVoornamen(),
                        actueelSamengesteldeNaam.getAdellijkeTitel(),
                        actueelSamengesteldeNaam.getVoorvoegsel(),
                        actueelSamengesteldeNaam.getScheidingsteken(),
                        actueelSamengesteldeNaam.getGeslachtsnaamstam());
        } else {
            this.samengesteldeNaam = null;
        }
        final HisPersoonGeboorteModel actueelGeboorte = getPersoonGeboorteHistorie().getActueleRecord();
        if (actueelGeboorte != null) {
            this.geboorte =
                    new PersoonGeboorteGroepModel(
                        actueelGeboorte.getDatumGeboorte(),
                        actueelGeboorte.getGemeenteGeboorte(),
                        actueelGeboorte.getWoonplaatsnaamGeboorte(),
                        actueelGeboorte.getBuitenlandsePlaatsGeboorte(),
                        actueelGeboorte.getBuitenlandseRegioGeboorte(),
                        actueelGeboorte.getOmschrijvingLocatieGeboorte(),
                        actueelGeboorte.getLandGebiedGeboorte());
        } else {
            this.geboorte = null;
        }
        final HisPersoonGeslachtsaanduidingModel actueelGeslachtsaanduiding = getPersoonGeslachtsaanduidingHistorie().getActueleRecord();
        if (actueelGeslachtsaanduiding != null) {
            this.geslachtsaanduiding = new PersoonGeslachtsaanduidingGroepModel(actueelGeslachtsaanduiding.getGeslachtsaanduiding());
        } else {
            this.geslachtsaanduiding = null;
        }
        final HisPersoonInschrijvingModel actueelInschrijving = getPersoonInschrijvingHistorie().getActueleRecord();
        if (actueelInschrijving != null) {
            this.inschrijving =
                    new PersoonInschrijvingGroepModel(
                        actueelInschrijving.getDatumInschrijving(),
                        actueelInschrijving.getVersienummer(),
                        actueelInschrijving.getDatumtijdstempel());
        } else {
            this.inschrijving = null;
        }
        final HisPersoonNummerverwijzingModel actueelNummerverwijzing = getPersoonNummerverwijzingHistorie().getActueleRecord();
        if (actueelNummerverwijzing != null) {
            this.nummerverwijzing =
                    new PersoonNummerverwijzingGroepModel(
                        actueelNummerverwijzing.getVorigeBurgerservicenummer(),
                        actueelNummerverwijzing.getVolgendeBurgerservicenummer(),
                        actueelNummerverwijzing.getVorigeAdministratienummer(),
                        actueelNummerverwijzing.getVolgendeAdministratienummer());
        } else {
            this.nummerverwijzing = null;
        }
        final HisPersoonBijhoudingModel actueelBijhouding = getPersoonBijhoudingHistorie().getActueleRecord();
        if (actueelBijhouding != null) {
            this.bijhouding =
                    new PersoonBijhoudingGroepModel(
                        actueelBijhouding.getBijhoudingspartij(),
                        actueelBijhouding.getBijhoudingsaard(),
                        actueelBijhouding.getNadereBijhoudingsaard(),
                        actueelBijhouding.getIndicatieOnverwerktDocumentAanwezig());
        } else {
            this.bijhouding = null;
        }
        final HisPersoonOverlijdenModel actueelOverlijden = getPersoonOverlijdenHistorie().getActueleRecord();
        if (actueelOverlijden != null) {
            this.overlijden =
                    new PersoonOverlijdenGroepModel(
                        actueelOverlijden.getDatumOverlijden(),
                        actueelOverlijden.getGemeenteOverlijden(),
                        actueelOverlijden.getWoonplaatsnaamOverlijden(),
                        actueelOverlijden.getBuitenlandsePlaatsOverlijden(),
                        actueelOverlijden.getBuitenlandseRegioOverlijden(),
                        actueelOverlijden.getOmschrijvingLocatieOverlijden(),
                        actueelOverlijden.getLandGebiedOverlijden());
        } else {
            this.overlijden = null;
        }
        final HisPersoonNaamgebruikModel actueelNaamgebruik = getPersoonNaamgebruikHistorie().getActueleRecord();
        if (actueelNaamgebruik != null) {
            this.naamgebruik =
                    new PersoonNaamgebruikGroepModel(
                        actueelNaamgebruik.getNaamgebruik(),
                        actueelNaamgebruik.getIndicatieNaamgebruikAfgeleid(),
                        actueelNaamgebruik.getPredicaatNaamgebruik(),
                        actueelNaamgebruik.getVoornamenNaamgebruik(),
                        actueelNaamgebruik.getAdellijkeTitelNaamgebruik(),
                        actueelNaamgebruik.getVoorvoegselNaamgebruik(),
                        actueelNaamgebruik.getScheidingstekenNaamgebruik(),
                        actueelNaamgebruik.getGeslachtsnaamstamNaamgebruik());
        } else {
            this.naamgebruik = null;
        }
        final HisPersoonMigratieModel actueelMigratie = getPersoonMigratieHistorie().getActueleRecord();
        if (actueelMigratie != null) {
            this.migratie =
                    new PersoonMigratieGroepModel(
                        actueelMigratie.getSoortMigratie(),
                        actueelMigratie.getRedenWijzigingMigratie(),
                        actueelMigratie.getAangeverMigratie(),
                        actueelMigratie.getLandGebiedMigratie(),
                        actueelMigratie.getBuitenlandsAdresRegel1Migratie(),
                        actueelMigratie.getBuitenlandsAdresRegel2Migratie(),
                        actueelMigratie.getBuitenlandsAdresRegel3Migratie(),
                        actueelMigratie.getBuitenlandsAdresRegel4Migratie(),
                        actueelMigratie.getBuitenlandsAdresRegel5Migratie(),
                        actueelMigratie.getBuitenlandsAdresRegel6Migratie());
        } else {
            this.migratie = null;
        }
        final HisPersoonVerblijfsrechtModel actueelVerblijfsrecht = getPersoonVerblijfsrechtHistorie().getActueleRecord();
        if (actueelVerblijfsrecht != null) {
            this.verblijfsrecht =
                    new PersoonVerblijfsrechtGroepModel(
                        actueelVerblijfsrecht.getAanduidingVerblijfsrecht(),
                        actueelVerblijfsrecht.getDatumAanvangVerblijfsrecht(),
                        actueelVerblijfsrecht.getDatumMededelingVerblijfsrecht(),
                        actueelVerblijfsrecht.getDatumVoorzienEindeVerblijfsrecht());
        } else {
            this.verblijfsrecht = null;
        }
        final HisPersoonUitsluitingKiesrechtModel actueelUitsluitingKiesrecht = getPersoonUitsluitingKiesrechtHistorie().getActueleRecord();
        if (actueelUitsluitingKiesrecht != null) {
            this.uitsluitingKiesrecht =
                    new PersoonUitsluitingKiesrechtGroepModel(
                        actueelUitsluitingKiesrecht.getIndicatieUitsluitingKiesrecht(),
                        actueelUitsluitingKiesrecht.getDatumVoorzienEindeUitsluitingKiesrecht());
        } else {
            this.uitsluitingKiesrecht = null;
        }
        final HisPersoonDeelnameEUVerkiezingenModel actueelDeelnameEUVerkiezingen = getPersoonDeelnameEUVerkiezingenHistorie().getActueleRecord();
        if (actueelDeelnameEUVerkiezingen != null) {
            this.deelnameEUVerkiezingen =
                    new PersoonDeelnameEUVerkiezingenGroepModel(
                        actueelDeelnameEUVerkiezingen.getIndicatieDeelnameEUVerkiezingen(),
                        actueelDeelnameEUVerkiezingen.getDatumAanleidingAanpassingDeelnameEUVerkiezingen(),
                        actueelDeelnameEUVerkiezingen.getDatumVoorzienEindeUitsluitingEUVerkiezingen());
        } else {
            this.deelnameEUVerkiezingen = null;
        }
        final HisPersoonPersoonskaartModel actueelPersoonskaart = getPersoonPersoonskaartHistorie().getActueleRecord();
        if (actueelPersoonskaart != null) {
            this.persoonskaart =
                    new PersoonPersoonskaartGroepModel(
                        actueelPersoonskaart.getGemeentePersoonskaart(),
                        actueelPersoonskaart.getIndicatiePersoonskaartVolledigGeconverteerd());
        } else {
            this.persoonskaart = null;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FormeleHistorieSet<HisPersoonAfgeleidAdministratiefModel> getPersoonAfgeleidAdministratiefHistorie() {
        if (hisPersoonAfgeleidAdministratiefLijst == null) {
            hisPersoonAfgeleidAdministratiefLijst = new HashSet<>();
        }
        if (persoonAfgeleidAdministratiefHistorie == null) {
            persoonAfgeleidAdministratiefHistorie =
                    new FormeleHistorieSetImpl<HisPersoonAfgeleidAdministratiefModel>(hisPersoonAfgeleidAdministratiefLijst);
        }
        return persoonAfgeleidAdministratiefHistorie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MaterieleHistorieSet<HisPersoonIdentificatienummersModel> getPersoonIdentificatienummersHistorie() {
        if (hisPersoonIdentificatienummersLijst == null) {
            hisPersoonIdentificatienummersLijst = new HashSet<>();
        }
        if (persoonIdentificatienummersHistorie == null) {
            persoonIdentificatienummersHistorie = new MaterieleHistorieSetImpl<HisPersoonIdentificatienummersModel>(hisPersoonIdentificatienummersLijst);
        }
        return persoonIdentificatienummersHistorie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MaterieleHistorieSet<HisPersoonSamengesteldeNaamModel> getPersoonSamengesteldeNaamHistorie() {
        if (hisPersoonSamengesteldeNaamLijst == null) {
            hisPersoonSamengesteldeNaamLijst = new HashSet<>();
        }
        if (persoonSamengesteldeNaamHistorie == null) {
            persoonSamengesteldeNaamHistorie = new MaterieleHistorieSetImpl<HisPersoonSamengesteldeNaamModel>(hisPersoonSamengesteldeNaamLijst);
        }
        return persoonSamengesteldeNaamHistorie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FormeleHistorieSet<HisPersoonGeboorteModel> getPersoonGeboorteHistorie() {
        if (hisPersoonGeboorteLijst == null) {
            hisPersoonGeboorteLijst = new HashSet<>();
        }
        if (persoonGeboorteHistorie == null) {
            persoonGeboorteHistorie = new FormeleHistorieSetImpl<HisPersoonGeboorteModel>(hisPersoonGeboorteLijst);
        }
        return persoonGeboorteHistorie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MaterieleHistorieSet<HisPersoonGeslachtsaanduidingModel> getPersoonGeslachtsaanduidingHistorie() {
        if (hisPersoonGeslachtsaanduidingLijst == null) {
            hisPersoonGeslachtsaanduidingLijst = new HashSet<>();
        }
        if (persoonGeslachtsaanduidingHistorie == null) {
            persoonGeslachtsaanduidingHistorie = new MaterieleHistorieSetImpl<HisPersoonGeslachtsaanduidingModel>(hisPersoonGeslachtsaanduidingLijst);
        }
        return persoonGeslachtsaanduidingHistorie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FormeleHistorieSet<HisPersoonInschrijvingModel> getPersoonInschrijvingHistorie() {
        if (hisPersoonInschrijvingLijst == null) {
            hisPersoonInschrijvingLijst = new HashSet<>();
        }
        if (persoonInschrijvingHistorie == null) {
            persoonInschrijvingHistorie = new FormeleHistorieSetImpl<HisPersoonInschrijvingModel>(hisPersoonInschrijvingLijst);
        }
        return persoonInschrijvingHistorie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MaterieleHistorieSet<HisPersoonNummerverwijzingModel> getPersoonNummerverwijzingHistorie() {
        if (hisPersoonNummerverwijzingLijst == null) {
            hisPersoonNummerverwijzingLijst = new HashSet<>();
        }
        if (persoonNummerverwijzingHistorie == null) {
            persoonNummerverwijzingHistorie = new MaterieleHistorieSetImpl<HisPersoonNummerverwijzingModel>(hisPersoonNummerverwijzingLijst);
        }
        return persoonNummerverwijzingHistorie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MaterieleHistorieSet<HisPersoonBijhoudingModel> getPersoonBijhoudingHistorie() {
        if (hisPersoonBijhoudingLijst == null) {
            hisPersoonBijhoudingLijst = new HashSet<>();
        }
        if (persoonBijhoudingHistorie == null) {
            persoonBijhoudingHistorie = new MaterieleHistorieSetImpl<HisPersoonBijhoudingModel>(hisPersoonBijhoudingLijst);
        }
        return persoonBijhoudingHistorie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FormeleHistorieSet<HisPersoonOverlijdenModel> getPersoonOverlijdenHistorie() {
        if (hisPersoonOverlijdenLijst == null) {
            hisPersoonOverlijdenLijst = new HashSet<>();
        }
        if (persoonOverlijdenHistorie == null) {
            persoonOverlijdenHistorie = new FormeleHistorieSetImpl<HisPersoonOverlijdenModel>(hisPersoonOverlijdenLijst);
        }
        return persoonOverlijdenHistorie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FormeleHistorieSet<HisPersoonNaamgebruikModel> getPersoonNaamgebruikHistorie() {
        if (hisPersoonNaamgebruikLijst == null) {
            hisPersoonNaamgebruikLijst = new HashSet<>();
        }
        if (persoonNaamgebruikHistorie == null) {
            persoonNaamgebruikHistorie = new FormeleHistorieSetImpl<HisPersoonNaamgebruikModel>(hisPersoonNaamgebruikLijst);
        }
        return persoonNaamgebruikHistorie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MaterieleHistorieSet<HisPersoonMigratieModel> getPersoonMigratieHistorie() {
        if (hisPersoonMigratieLijst == null) {
            hisPersoonMigratieLijst = new HashSet<>();
        }
        if (persoonMigratieHistorie == null) {
            persoonMigratieHistorie = new MaterieleHistorieSetImpl<HisPersoonMigratieModel>(hisPersoonMigratieLijst);
        }
        return persoonMigratieHistorie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FormeleHistorieSet<HisPersoonVerblijfsrechtModel> getPersoonVerblijfsrechtHistorie() {
        if (hisPersoonVerblijfsrechtLijst == null) {
            hisPersoonVerblijfsrechtLijst = new HashSet<>();
        }
        if (persoonVerblijfsrechtHistorie == null) {
            persoonVerblijfsrechtHistorie = new FormeleHistorieSetImpl<HisPersoonVerblijfsrechtModel>(hisPersoonVerblijfsrechtLijst);
        }
        return persoonVerblijfsrechtHistorie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FormeleHistorieSet<HisPersoonUitsluitingKiesrechtModel> getPersoonUitsluitingKiesrechtHistorie() {
        if (hisPersoonUitsluitingKiesrechtLijst == null) {
            hisPersoonUitsluitingKiesrechtLijst = new HashSet<>();
        }
        if (persoonUitsluitingKiesrechtHistorie == null) {
            persoonUitsluitingKiesrechtHistorie = new FormeleHistorieSetImpl<HisPersoonUitsluitingKiesrechtModel>(hisPersoonUitsluitingKiesrechtLijst);
        }
        return persoonUitsluitingKiesrechtHistorie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FormeleHistorieSet<HisPersoonDeelnameEUVerkiezingenModel> getPersoonDeelnameEUVerkiezingenHistorie() {
        if (hisPersoonDeelnameEUVerkiezingenLijst == null) {
            hisPersoonDeelnameEUVerkiezingenLijst = new HashSet<>();
        }
        if (persoonDeelnameEUVerkiezingenHistorie == null) {
            persoonDeelnameEUVerkiezingenHistorie =
                    new FormeleHistorieSetImpl<HisPersoonDeelnameEUVerkiezingenModel>(hisPersoonDeelnameEUVerkiezingenLijst);
        }
        return persoonDeelnameEUVerkiezingenHistorie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FormeleHistorieSet<HisPersoonPersoonskaartModel> getPersoonPersoonskaartHistorie() {
        if (hisPersoonPersoonskaartLijst == null) {
            hisPersoonPersoonskaartLijst = new HashSet<>();
        }
        if (persoonPersoonskaartHistorie == null) {
            persoonPersoonskaartHistorie = new FormeleHistorieSetImpl<HisPersoonPersoonskaartModel>(hisPersoonPersoonskaartLijst);
        }
        return persoonPersoonskaartHistorie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings(value = "unchecked")
    public Set<? extends PersoonIndicatieHisVolledigImpl> getIndicaties() {
        return Collections.unmodifiableSet(new HashSet<PersoonIndicatieHisVolledigImpl>(CollectionUtils.select(Arrays.asList(
            getIndicatieDerdeHeeftGezag(),
            getIndicatieOnderCuratele(),
            getIndicatieVolledigeVerstrekkingsbeperking(),
            getIndicatieVastgesteldNietNederlander(),
            getIndicatieBehandeldAlsNederlander(),
            getIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument(),
            getIndicatieStaatloos(),
            getIndicatieBijzondereVerblijfsrechtelijkePositie()), PredicateUtils.notNullPredicate())));
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON;
    }

}
