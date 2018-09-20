/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.basis.AbstractDynamischObject;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.ComparatorFactory;
import nl.bzk.brp.model.basis.GesorteerdeSetOpVolgnummer;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.basis.VolgnummerComparator;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.PersoonBasis;
import nl.bzk.brp.model.operationeel.autaut.PersoonAfnemerindicatieModel;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

/**
 * Een persoon is een drager van rechten en plichten die een mens is.
 *
 * Buiten de BRP wordt ook wel de term 'Natuurlijk persoon' gebruikt. In de BRP worden zowel personen waarvan de
 * bijhouding valt onder afdeling I ('Ingezetenen') van de Wet BRP, als personen waarvoor de bijhouding onder afdeling
 * II ('Niet-ingezetenen') van de Wet BRP valt, ingeschreven.
 *
 * 1. Binnen BRP gebruiken we het begrip Persoon, waar elders gebruikelijk is voor dit objecttype de naam
 * "Natuurlijk persoon" te gebruiken. Binnen de context van BRP hebben we het bij het hanteren van de term Persoon
 * echter nooit over niet-natuurlijke personen zoals rechtspersonen. Het gebruik van de term Persoon is verder dermate
 * gebruikelijk binnen de context van BRP, dat ervoor gekozen is deze naam te blijven hanteren. We spreken dus over
 * Persoon en niet over "Natuurlijk persoon". 2. Voor gerelateerde personen, en voor niet-ingeschrevenen, gebruiken we
 * in het logisch & operationeel model (maar dus NIET in de gegevensset) het construct 'persoon'. Om die reden zijn veel
 * groepen niet verplicht, die wellicht wel verplicht zouden zijn in geval van (alleen) ingeschrevenen.
 *
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractPersoonModel extends AbstractDynamischObject implements PersoonBasis, ModelIdentificeerbaar<Integer> {

    @Transient
    @JsonProperty
    private Integer iD;

    @Embedded
    @AttributeOverride(name = SoortPersoonAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Srt"))
    @JsonProperty
    private SoortPersoonAttribuut soort;

    @Embedded
    @JsonProperty
    private PersoonAfgeleidAdministratiefGroepModel afgeleidAdministratief;

    @Embedded
    @JsonProperty
    private PersoonIdentificatienummersGroepModel identificatienummers;

    @Embedded
    @JsonProperty
    private PersoonSamengesteldeNaamGroepModel samengesteldeNaam;

    @Embedded
    @JsonProperty
    private PersoonGeboorteGroepModel geboorte;

    @Embedded
    @JsonProperty
    private PersoonGeslachtsaanduidingGroepModel geslachtsaanduiding;

    @Embedded
    @JsonProperty
    private PersoonInschrijvingGroepModel inschrijving;

    @Embedded
    @JsonProperty
    private PersoonNummerverwijzingGroepModel nummerverwijzing;

    @Embedded
    @JsonProperty
    private PersoonBijhoudingGroepModel bijhouding;

    @Embedded
    @JsonProperty
    private PersoonOverlijdenGroepModel overlijden;

    @Embedded
    @JsonProperty
    private PersoonNaamgebruikGroepModel naamgebruik;

    @Embedded
    @JsonProperty
    private PersoonMigratieGroepModel migratie;

    @Embedded
    @JsonProperty
    private PersoonVerblijfsrechtGroepModel verblijfsrecht;

    @Embedded
    @JsonProperty
    private PersoonUitsluitingKiesrechtGroepModel uitsluitingKiesrecht;

    @Embedded
    @JsonProperty
    private PersoonDeelnameEUVerkiezingenGroepModel deelnameEUVerkiezingen;

    @Embedded
    @JsonProperty
    private PersoonPersoonskaartGroepModel persoonskaart;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "Pers")
    @JsonProperty
    @JsonDeserialize(as = GesorteerdeSetOpVolgnummer.class)
    @Sort(type = SortType.COMPARATOR, comparator = VolgnummerComparator.class)
    private SortedSet<PersoonVoornaamModel> voornamen;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "Pers")
    @JsonProperty
    @JsonDeserialize(as = GesorteerdeSetOpVolgnummer.class)
    @Sort(type = SortType.COMPARATOR, comparator = VolgnummerComparator.class)
    private SortedSet<PersoonGeslachtsnaamcomponentModel> geslachtsnaamcomponenten;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "Geverifieerde")
    @JsonProperty
    private Set<PersoonVerificatieModel> verificaties;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "Pers")
    @JsonProperty
    private Set<PersoonNationaliteitModel> nationaliteiten;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "Pers")
    @JsonProperty
    private Set<PersoonAdresModel> adressen;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "Pers")
    @JsonProperty
    private Set<PersoonIndicatieModel> indicaties;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "Pers")
    @JsonProperty
    private Set<PersoonReisdocumentModel> reisdocumenten;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "Pers")
    @JsonProperty
    private Set<BetrokkenheidModel> betrokkenheden;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "Pers")
    @JsonProperty
    private Set<PersoonOnderzoekModel> onderzoeken;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "Pers")
    @JsonProperty
    private Set<PersoonVerstrekkingsbeperkingModel> verstrekkingsbeperkingen;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "Pers")
    @JsonProperty
    private Set<PersoonAfnemerindicatieModel> afnemerindicaties;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractPersoonModel() {
        voornamen = new TreeSet<PersoonVoornaamModel>(ComparatorFactory.getComparatorVoorPersoonVoornaam());
        geslachtsnaamcomponenten = new TreeSet<PersoonGeslachtsnaamcomponentModel>(ComparatorFactory.getComparatorVoorPersoonGeslachtsnaamcomponent());
        verificaties = new HashSet<PersoonVerificatieModel>();
        nationaliteiten = new HashSet<PersoonNationaliteitModel>();
        adressen = new HashSet<PersoonAdresModel>();
        indicaties = new HashSet<PersoonIndicatieModel>();
        reisdocumenten = new HashSet<PersoonReisdocumentModel>();
        betrokkenheden = new HashSet<BetrokkenheidModel>();
        onderzoeken = new HashSet<PersoonOnderzoekModel>();
        verstrekkingsbeperkingen = new HashSet<PersoonVerstrekkingsbeperkingModel>();
        afnemerindicaties = new HashSet<PersoonAfnemerindicatieModel>();

    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param soort soort van Persoon.
     */
    public AbstractPersoonModel(final SoortPersoonAttribuut soort) {
        this();
        this.soort = soort;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoon Te kopieren object type.
     */
    public AbstractPersoonModel(final Persoon persoon) {
        this();
        this.soort = persoon.getSoort();
        if (persoon.getAfgeleidAdministratief() != null) {
            this.afgeleidAdministratief = new PersoonAfgeleidAdministratiefGroepModel(persoon.getAfgeleidAdministratief());
        }
        if (persoon.getIdentificatienummers() != null) {
            this.identificatienummers = new PersoonIdentificatienummersGroepModel(persoon.getIdentificatienummers());
        }
        if (persoon.getSamengesteldeNaam() != null) {
            this.samengesteldeNaam = new PersoonSamengesteldeNaamGroepModel(persoon.getSamengesteldeNaam());
        }
        if (persoon.getGeboorte() != null) {
            this.geboorte = new PersoonGeboorteGroepModel(persoon.getGeboorte());
        }
        if (persoon.getGeslachtsaanduiding() != null) {
            this.geslachtsaanduiding = new PersoonGeslachtsaanduidingGroepModel(persoon.getGeslachtsaanduiding());
        }
        if (persoon.getInschrijving() != null) {
            this.inschrijving = new PersoonInschrijvingGroepModel(persoon.getInschrijving());
        }
        if (persoon.getNummerverwijzing() != null) {
            this.nummerverwijzing = new PersoonNummerverwijzingGroepModel(persoon.getNummerverwijzing());
        }
        if (persoon.getBijhouding() != null) {
            this.bijhouding = new PersoonBijhoudingGroepModel(persoon.getBijhouding());
        }
        if (persoon.getOverlijden() != null) {
            this.overlijden = new PersoonOverlijdenGroepModel(persoon.getOverlijden());
        }
        if (persoon.getNaamgebruik() != null) {
            this.naamgebruik = new PersoonNaamgebruikGroepModel(persoon.getNaamgebruik());
        }
        if (persoon.getMigratie() != null) {
            this.migratie = new PersoonMigratieGroepModel(persoon.getMigratie());
        }
        if (persoon.getVerblijfsrecht() != null) {
            this.verblijfsrecht = new PersoonVerblijfsrechtGroepModel(persoon.getVerblijfsrecht());
        }
        if (persoon.getUitsluitingKiesrecht() != null) {
            this.uitsluitingKiesrecht = new PersoonUitsluitingKiesrechtGroepModel(persoon.getUitsluitingKiesrecht());
        }
        if (persoon.getDeelnameEUVerkiezingen() != null) {
            this.deelnameEUVerkiezingen = new PersoonDeelnameEUVerkiezingenGroepModel(persoon.getDeelnameEUVerkiezingen());
        }
        if (persoon.getPersoonskaart() != null) {
            this.persoonskaart = new PersoonPersoonskaartGroepModel(persoon.getPersoonskaart());
        }

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
     * {@inheritDoc}
     */
    @Override
    public SoortPersoonAttribuut getSoort() {
        return soort;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonAfgeleidAdministratiefGroepModel getAfgeleidAdministratief() {
        return afgeleidAdministratief;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonIdentificatienummersGroepModel getIdentificatienummers() {
        return identificatienummers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonSamengesteldeNaamGroepModel getSamengesteldeNaam() {
        return samengesteldeNaam;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonGeboorteGroepModel getGeboorte() {
        return geboorte;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonGeslachtsaanduidingGroepModel getGeslachtsaanduiding() {
        return geslachtsaanduiding;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonInschrijvingGroepModel getInschrijving() {
        return inschrijving;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonNummerverwijzingGroepModel getNummerverwijzing() {
        return nummerverwijzing;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonBijhoudingGroepModel getBijhouding() {
        return bijhouding;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonOverlijdenGroepModel getOverlijden() {
        return overlijden;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonNaamgebruikGroepModel getNaamgebruik() {
        return naamgebruik;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonMigratieGroepModel getMigratie() {
        return migratie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonVerblijfsrechtGroepModel getVerblijfsrecht() {
        return verblijfsrecht;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonUitsluitingKiesrechtGroepModel getUitsluitingKiesrecht() {
        return uitsluitingKiesrecht;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonDeelnameEUVerkiezingenGroepModel getDeelnameEUVerkiezingen() {
        return deelnameEUVerkiezingen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonPersoonskaartGroepModel getPersoonskaart() {
        return persoonskaart;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SortedSet<PersoonVoornaamModel> getVoornamen() {
        return voornamen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SortedSet<PersoonGeslachtsnaamcomponentModel> getGeslachtsnaamcomponenten() {
        return geslachtsnaamcomponenten;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<PersoonVerificatieModel> getVerificaties() {
        return verificaties;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<PersoonNationaliteitModel> getNationaliteiten() {
        return nationaliteiten;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<PersoonAdresModel> getAdressen() {
        return adressen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<PersoonIndicatieModel> getIndicaties() {
        return indicaties;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<PersoonReisdocumentModel> getReisdocumenten() {
        return reisdocumenten;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<BetrokkenheidModel> getBetrokkenheden() {
        return betrokkenheden;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<PersoonOnderzoekModel> getOnderzoeken() {
        return onderzoeken;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<PersoonVerstrekkingsbeperkingModel> getVerstrekkingsbeperkingen() {
        return verstrekkingsbeperkingen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<PersoonAfnemerindicatieModel> getAfnemerindicaties() {
        return afnemerindicaties;
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
     * Zet Afgeleid administratief van Persoon.
     *
     * @param afgeleidAdministratief Afgeleid administratief.
     */
    public void setAfgeleidAdministratief(final PersoonAfgeleidAdministratiefGroepModel afgeleidAdministratief) {
        this.afgeleidAdministratief = afgeleidAdministratief;
    }

    /**
     * Zet Identificatienummers van Persoon.
     *
     * @param identificatienummers Identificatienummers.
     */
    public void setIdentificatienummers(final PersoonIdentificatienummersGroepModel identificatienummers) {
        this.identificatienummers = identificatienummers;
    }

    /**
     * Zet Samengestelde naam van Persoon.
     *
     * @param samengesteldeNaam Samengestelde naam.
     */
    public void setSamengesteldeNaam(final PersoonSamengesteldeNaamGroepModel samengesteldeNaam) {
        this.samengesteldeNaam = samengesteldeNaam;
    }

    /**
     * Zet Geboorte van Persoon.
     *
     * @param geboorte Geboorte.
     */
    public void setGeboorte(final PersoonGeboorteGroepModel geboorte) {
        this.geboorte = geboorte;
    }

    /**
     * Zet Geslachtsaanduiding van Persoon.
     *
     * @param geslachtsaanduiding Geslachtsaanduiding.
     */
    public void setGeslachtsaanduiding(final PersoonGeslachtsaanduidingGroepModel geslachtsaanduiding) {
        this.geslachtsaanduiding = geslachtsaanduiding;
    }

    /**
     * Zet Inschrijving van Persoon.
     *
     * @param inschrijving Inschrijving.
     */
    public void setInschrijving(final PersoonInschrijvingGroepModel inschrijving) {
        this.inschrijving = inschrijving;
    }

    /**
     * Zet Nummerverwijzing van Persoon.
     *
     * @param nummerverwijzing Nummerverwijzing.
     */
    public void setNummerverwijzing(final PersoonNummerverwijzingGroepModel nummerverwijzing) {
        this.nummerverwijzing = nummerverwijzing;
    }

    /**
     * Zet Bijhouding van Persoon.
     *
     * @param bijhouding Bijhouding.
     */
    public void setBijhouding(final PersoonBijhoudingGroepModel bijhouding) {
        this.bijhouding = bijhouding;
    }

    /**
     * Zet Overlijden van Persoon.
     *
     * @param overlijden Overlijden.
     */
    public void setOverlijden(final PersoonOverlijdenGroepModel overlijden) {
        this.overlijden = overlijden;
    }

    /**
     * Zet Naamgebruik van Persoon.
     *
     * @param naamgebruik Naamgebruik.
     */
    public void setNaamgebruik(final PersoonNaamgebruikGroepModel naamgebruik) {
        this.naamgebruik = naamgebruik;
    }

    /**
     * Zet Migratie van Persoon.
     *
     * @param migratie Migratie.
     */
    public void setMigratie(final PersoonMigratieGroepModel migratie) {
        this.migratie = migratie;
    }

    /**
     * Zet Verblijfsrecht van Persoon.
     *
     * @param verblijfsrecht Verblijfsrecht.
     */
    public void setVerblijfsrecht(final PersoonVerblijfsrechtGroepModel verblijfsrecht) {
        this.verblijfsrecht = verblijfsrecht;
    }

    /**
     * Zet Uitsluiting kiesrecht van Persoon.
     *
     * @param uitsluitingKiesrecht Uitsluiting kiesrecht.
     */
    public void setUitsluitingKiesrecht(final PersoonUitsluitingKiesrechtGroepModel uitsluitingKiesrecht) {
        this.uitsluitingKiesrecht = uitsluitingKiesrecht;
    }

    /**
     * Zet Deelname EU verkiezingen van Persoon.
     *
     * @param deelnameEUVerkiezingen Deelname EU verkiezingen.
     */
    public void setDeelnameEUVerkiezingen(final PersoonDeelnameEUVerkiezingenGroepModel deelnameEUVerkiezingen) {
        this.deelnameEUVerkiezingen = deelnameEUVerkiezingen;
    }

    /**
     * Zet Persoonskaart van Persoon.
     *
     * @param persoonskaart Persoonskaart.
     */
    public void setPersoonskaart(final PersoonPersoonskaartGroepModel persoonskaart) {
        this.persoonskaart = persoonskaart;
    }

    /**
     * Geeft alle groepen van de object met uitzondering van groepen die null zijn.
     *
     * @return Lijst met groepen.
     */
    public List<Groep> getGroepen() {
        final List<Groep> groepen = new ArrayList<>();
        if (afgeleidAdministratief != null) {
            groepen.add(afgeleidAdministratief);
        }
        if (identificatienummers != null) {
            groepen.add(identificatienummers);
        }
        if (samengesteldeNaam != null) {
            groepen.add(samengesteldeNaam);
        }
        if (geboorte != null) {
            groepen.add(geboorte);
        }
        if (geslachtsaanduiding != null) {
            groepen.add(geslachtsaanduiding);
        }
        if (inschrijving != null) {
            groepen.add(inschrijving);
        }
        if (nummerverwijzing != null) {
            groepen.add(nummerverwijzing);
        }
        if (bijhouding != null) {
            groepen.add(bijhouding);
        }
        if (overlijden != null) {
            groepen.add(overlijden);
        }
        if (naamgebruik != null) {
            groepen.add(naamgebruik);
        }
        if (migratie != null) {
            groepen.add(migratie);
        }
        if (verblijfsrecht != null) {
            groepen.add(verblijfsrecht);
        }
        if (uitsluitingKiesrecht != null) {
            groepen.add(uitsluitingKiesrecht);
        }
        if (deelnameEUVerkiezingen != null) {
            groepen.add(deelnameEUVerkiezingen);
        }
        if (persoonskaart != null) {
            groepen.add(persoonskaart);
        }
        return groepen;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van het object.
     */
    public List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (soort != null) {
            attributen.add(soort);
        }
        return attributen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transient
    public final PersoonIndicatieModel getIndicatieDerdeHeeftGezag() {
        PersoonIndicatieModel indicatieDerdeHeeftGezag = null;
        for (PersoonIndicatieModel persoonIndicatie : this.getIndicaties()) {
            if (persoonIndicatie.getSoort().getWaarde() == SoortIndicatie.INDICATIE_DERDE_HEEFT_GEZAG) {
                indicatieDerdeHeeftGezag = persoonIndicatie;
            }
        }
        return indicatieDerdeHeeftGezag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transient
    public final PersoonIndicatieModel getIndicatieOnderCuratele() {
        PersoonIndicatieModel indicatieOnderCuratele = null;
        for (PersoonIndicatieModel persoonIndicatie : this.getIndicaties()) {
            if (persoonIndicatie.getSoort().getWaarde() == SoortIndicatie.INDICATIE_ONDER_CURATELE) {
                indicatieOnderCuratele = persoonIndicatie;
            }
        }
        return indicatieOnderCuratele;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transient
    public final PersoonIndicatieModel getIndicatieVolledigeVerstrekkingsbeperking() {
        PersoonIndicatieModel indicatieVolledigeVerstrekkingsbeperking = null;
        for (PersoonIndicatieModel persoonIndicatie : this.getIndicaties()) {
            if (persoonIndicatie.getSoort().getWaarde() == SoortIndicatie.INDICATIE_VOLLEDIGE_VERSTREKKINGSBEPERKING) {
                indicatieVolledigeVerstrekkingsbeperking = persoonIndicatie;
            }
        }
        return indicatieVolledigeVerstrekkingsbeperking;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transient
    public final PersoonIndicatieModel getIndicatieVastgesteldNietNederlander() {
        PersoonIndicatieModel indicatieVastgesteldNietNederlander = null;
        for (PersoonIndicatieModel persoonIndicatie : this.getIndicaties()) {
            if (persoonIndicatie.getSoort().getWaarde() == SoortIndicatie.INDICATIE_VASTGESTELD_NIET_NEDERLANDER) {
                indicatieVastgesteldNietNederlander = persoonIndicatie;
            }
        }
        return indicatieVastgesteldNietNederlander;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transient
    public final PersoonIndicatieModel getIndicatieBehandeldAlsNederlander() {
        PersoonIndicatieModel indicatieBehandeldAlsNederlander = null;
        for (PersoonIndicatieModel persoonIndicatie : this.getIndicaties()) {
            if (persoonIndicatie.getSoort().getWaarde() == SoortIndicatie.INDICATIE_BEHANDELD_ALS_NEDERLANDER) {
                indicatieBehandeldAlsNederlander = persoonIndicatie;
            }
        }
        return indicatieBehandeldAlsNederlander;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transient
    public final PersoonIndicatieModel getIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument() {
        PersoonIndicatieModel indicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument = null;
        for (PersoonIndicatieModel persoonIndicatie : this.getIndicaties()) {
            if (persoonIndicatie.getSoort().getWaarde() == SoortIndicatie.INDICATIE_SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT) {
                indicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument = persoonIndicatie;
            }
        }
        return indicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transient
    public final PersoonIndicatieModel getIndicatieStaatloos() {
        PersoonIndicatieModel indicatieStaatloos = null;
        for (PersoonIndicatieModel persoonIndicatie : this.getIndicaties()) {
            if (persoonIndicatie.getSoort().getWaarde() == SoortIndicatie.INDICATIE_STAATLOOS) {
                indicatieStaatloos = persoonIndicatie;
            }
        }
        return indicatieStaatloos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transient
    public final PersoonIndicatieModel getIndicatieBijzondereVerblijfsrechtelijkePositie() {
        PersoonIndicatieModel indicatieBijzondereVerblijfsrechtelijkePositie = null;
        for (PersoonIndicatieModel persoonIndicatie : this.getIndicaties()) {
            if (persoonIndicatie.getSoort().getWaarde() == SoortIndicatie.INDICATIE_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE) {
                indicatieBijzondereVerblijfsrechtelijkePositie = persoonIndicatie;
            }
        }
        return indicatieBijzondereVerblijfsrechtelijkePositie;
    }

}
