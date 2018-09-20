/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.basis.PersoonBasis;
import nl.bzk.brp.model.operationeel.kern.BetrokkenheidModel;
import nl.bzk.brp.model.operationeel.kern.PersoonAanschrijvingGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonAdresModel;
import nl.bzk.brp.model.operationeel.kern.PersoonAfgeleidAdministratiefGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonBijhoudingsaardGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonBijhoudingsgemeenteGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonBijzondereVerblijfsrechtelijkePositieGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonEUVerkiezingenGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonGeboorteGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonGeslachtsaanduidingGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonGeslachtsnaamcomponentModel;
import nl.bzk.brp.model.operationeel.kern.PersoonIdentificatienummersGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonImmigratieGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonIndicatieModel;
import nl.bzk.brp.model.operationeel.kern.PersoonInschrijvingGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonNationaliteitModel;
import nl.bzk.brp.model.operationeel.kern.PersoonOpschortingGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonOverlijdenGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonPersoonskaartGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonReisdocumentModel;
import nl.bzk.brp.model.operationeel.kern.PersoonSamengesteldeNaamGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonUitsluitingNLKiesrechtGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonVerblijfstitelGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonVoornaamModel;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.hibernate.annotations.Type;


/**
 * Een persoon is een drager van rechten en plichten die een mens is.
 *
 * Buiten de BRP wordt ook wel de term 'Natuurlijk persoon' gebruikt.
 * In de BRP worden zowel personen ingeschreven die onder een College van Burgemeester en Wethouders vallen
 * ('ingezetenen'), als personen waarvoor de Minister verantwoordelijkheid geldt.
 *
 * 1. Binnen BRP gebruiken we het begrip Persoon, waar elders gebruikelijk is voor dit objecttype de naam
 * "Natuurlijk persoon" te gebruiken. Binnen de context van BRP hebben we het bij het hanteren van de term Persoon
 * echter nooit over niet-natuurlijke personen zoals rechtspersonen. Het gebruik van de term Persoon is verder dermate
 * gebruikelijk binnen de context van BRP, dat ervoor gekozen is deze naam te blijven hanteren. We spreken dus over
 * Persoon en niet over "Natuurlijk persoon".
 * 2. Voor "alternatieve realiteit" personen, en voor niet-ingeschrevenen, gebruiken we in het logisch & operationeel
 * model (maar dus NIET in de gegevensset) het construct 'persoon'. Om die reden zijn veel groepen niet verplicht, die
 * wellicht wel verplicht zouden zijn in geval van (alleen) ingeschrevenen.
 * RvdP 27 juni 2011
 *
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractPersoonModel extends AbstractDynamischObjectType implements PersoonBasis {

    @Id
    @SequenceGenerator(name = "PERSOON", sequenceName = "Kern.seq_Pers")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PERSOON")
    @JsonProperty
    private Integer                                                iD;

    @Enumerated
    @Column(name = "Srt")
    @JsonProperty
    private SoortPersoon                                           soort;

    @Embedded
    @JsonProperty
    private PersoonAfgeleidAdministratiefGroepModel                afgeleidAdministratief;

    @Embedded
    @JsonProperty
    private PersoonIdentificatienummersGroepModel                  identificatienummers;

    @Embedded
    @JsonProperty
    private PersoonSamengesteldeNaamGroepModel                     samengesteldeNaam;

    @Embedded
    @JsonProperty
    private PersoonGeboorteGroepModel                              geboorte;

    @Embedded
    @JsonProperty
    private PersoonGeslachtsaanduidingGroepModel                   geslachtsaanduiding;

    @Embedded
    @JsonProperty
    private PersoonInschrijvingGroepModel                          inschrijving;

    @Embedded
    @JsonProperty
    private PersoonBijhoudingsaardGroepModel                       bijhoudingsaard;

    @Embedded
    @JsonProperty
    private PersoonBijhoudingsgemeenteGroepModel                   bijhoudingsgemeente;

    @Embedded
    @JsonProperty
    private PersoonOpschortingGroepModel                           opschorting;

    @Embedded
    @JsonProperty
    private PersoonOverlijdenGroepModel                            overlijden;

    @Embedded
    @JsonProperty
    private PersoonAanschrijvingGroepModel                         aanschrijving;

    @Embedded
    @JsonProperty
    private PersoonImmigratieGroepModel                            immigratie;

    @Embedded
    @JsonProperty
    private PersoonVerblijfstitelGroepModel                        verblijfstitel;

    @Embedded
    @JsonProperty
    private PersoonBijzondereVerblijfsrechtelijkePositieGroepModel bijzondereVerblijfsrechtelijkePositie;

    @Embedded
    @JsonProperty
    private PersoonUitsluitingNLKiesrechtGroepModel                uitsluitingNLKiesrecht;

    @Embedded
    @JsonProperty
    private PersoonEUVerkiezingenGroepModel                        eUVerkiezingen;

    @Embedded
    @JsonProperty
    private PersoonPersoonskaartGroepModel                         persoonskaart;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "Pers")
    @JsonProperty
    @Sort(type = SortType.NATURAL)
    private Set<PersoonVoornaamModel>                              voornamen;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "Pers")
    @JsonProperty
    @Sort(type = SortType.NATURAL)
    private Set<PersoonGeslachtsnaamcomponentModel>                geslachtsnaamcomponenten;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "Pers")
    @JsonProperty
    @Sort(type = SortType.NATURAL)
    private Set<PersoonNationaliteitModel>                         nationaliteiten;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "Pers")
    @JsonProperty
    @Sort(type = SortType.NATURAL)
    private Set<PersoonAdresModel>                                 adressen;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "Pers")
    @JsonProperty
    @Sort(type = SortType.NATURAL)
    private Set<PersoonIndicatieModel>                             indicaties;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "Pers")
    @JsonProperty
    @Sort(type = SortType.NATURAL)
    private Set<PersoonReisdocumentModel>                          reisdocumenten;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "Pers")
    @JsonProperty
    @Sort(type = SortType.NATURAL)
    private Set<BetrokkenheidModel>                                betrokkenheden;

    @Type(type = "StatusHistorie")
    @Column(name = "AanschrStatusHis")
    @JsonProperty
    private StatusHistorie                                         aanschrijvingStatusHis;

    @Type(type = "StatusHistorie")
    @Column(name = "BijhaardStatusHis")
    @JsonProperty
    private StatusHistorie                                         bijhoudingsaardStatusHis;

    @Type(type = "StatusHistorie")
    @Column(name = "BijhgemStatusHis")
    @JsonProperty
    private StatusHistorie                                         bijhoudingsgemeenteStatusHis;

    @Type(type = "StatusHistorie")
    @Column(name = "BVPStatusHis")
    @JsonProperty
    private StatusHistorie                                         bijzondereVerblijfsrechtelijkePositieStatusHis;

    @Type(type = "StatusHistorie")
    @Column(name = "EUVerkiezingenStatusHis")
    @JsonProperty
    private StatusHistorie                                         eUVerkiezingenStatusHis;

    @Type(type = "StatusHistorie")
    @Column(name = "GeboorteStatusHis")
    @JsonProperty
    private StatusHistorie                                         geboorteStatusHis;

    @Type(type = "StatusHistorie")
    @Column(name = "GeslachtsaandStatusHis")
    @JsonProperty
    private StatusHistorie                                         geslachtsaanduidingStatusHis;

    @Type(type = "StatusHistorie")
    @Column(name = "IDsStatusHis")
    @JsonProperty
    private StatusHistorie                                         identificatienummersStatusHis;

    @Type(type = "StatusHistorie")
    @Column(name = "ImmigratieStatusHis")
    @JsonProperty
    private StatusHistorie                                         immigratieStatusHis;

    @Type(type = "StatusHistorie")
    @Column(name = "InschrStatusHis")
    @JsonProperty
    private StatusHistorie                                         inschrijvingStatusHis;

    @Type(type = "StatusHistorie")
    @Column(name = "OpschortingStatusHis")
    @JsonProperty
    private StatusHistorie                                         opschortingStatusHis;

    @Type(type = "StatusHistorie")
    @Column(name = "OverlijdenStatusHis")
    @JsonProperty
    private StatusHistorie                                         overlijdenStatusHis;

    @Type(type = "StatusHistorie")
    @Column(name = "PKStatusHis")
    @JsonProperty
    private StatusHistorie                                         persoonskaartStatusHis;

    @Type(type = "StatusHistorie")
    @Column(name = "SamengesteldeNaamStatusHis")
    @JsonProperty
    private StatusHistorie                                         samengesteldeNaamStatusHis;

    @Type(type = "StatusHistorie")
    @Column(name = "UitslNLKiesrStatusHis")
    @JsonProperty
    private StatusHistorie                                         uitsluitingNLKiesrechtStatusHis;

    @Type(type = "StatusHistorie")
    @Column(name = "VerblijfstitelStatusHis")
    @JsonProperty
    private StatusHistorie                                         verblijfstitelStatusHis;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractPersoonModel() {
        voornamen = new TreeSet<PersoonVoornaamModel>();
        geslachtsnaamcomponenten = new TreeSet<PersoonGeslachtsnaamcomponentModel>();
        nationaliteiten = new TreeSet<PersoonNationaliteitModel>();
        adressen = new TreeSet<PersoonAdresModel>();
        indicaties = new TreeSet<PersoonIndicatieModel>();
        reisdocumenten = new TreeSet<PersoonReisdocumentModel>();
        betrokkenheden = new TreeSet<BetrokkenheidModel>();
        this.aanschrijvingStatusHis = StatusHistorie.X;
        this.bijhoudingsaardStatusHis = StatusHistorie.X;
        this.bijhoudingsgemeenteStatusHis = StatusHistorie.X;
        this.bijzondereVerblijfsrechtelijkePositieStatusHis = StatusHistorie.X;
        this.eUVerkiezingenStatusHis = StatusHistorie.X;
        this.geboorteStatusHis = StatusHistorie.X;
        this.geslachtsaanduidingStatusHis = StatusHistorie.X;
        this.identificatienummersStatusHis = StatusHistorie.X;
        this.immigratieStatusHis = StatusHistorie.X;
        this.inschrijvingStatusHis = StatusHistorie.X;
        this.opschortingStatusHis = StatusHistorie.X;
        this.overlijdenStatusHis = StatusHistorie.X;
        this.persoonskaartStatusHis = StatusHistorie.X;
        this.samengesteldeNaamStatusHis = StatusHistorie.X;
        this.uitsluitingNLKiesrechtStatusHis = StatusHistorie.X;
        this.verblijfstitelStatusHis = StatusHistorie.X;

    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param soort soort van Persoon.
     */
    public AbstractPersoonModel(final SoortPersoon soort) {
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
            this.afgeleidAdministratief =
                new PersoonAfgeleidAdministratiefGroepModel(persoon.getAfgeleidAdministratief());
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
        if (persoon.getBijhoudingsaard() != null) {
            this.bijhoudingsaard = new PersoonBijhoudingsaardGroepModel(persoon.getBijhoudingsaard());
        }
        if (persoon.getBijhoudingsgemeente() != null) {
            this.bijhoudingsgemeente = new PersoonBijhoudingsgemeenteGroepModel(persoon.getBijhoudingsgemeente());
        }
        if (persoon.getOpschorting() != null) {
            this.opschorting = new PersoonOpschortingGroepModel(persoon.getOpschorting());
        }
        if (persoon.getOverlijden() != null) {
            this.overlijden = new PersoonOverlijdenGroepModel(persoon.getOverlijden());
        }
        if (persoon.getAanschrijving() != null) {
            this.aanschrijving = new PersoonAanschrijvingGroepModel(persoon.getAanschrijving());
        }
        if (persoon.getImmigratie() != null) {
            this.immigratie = new PersoonImmigratieGroepModel(persoon.getImmigratie());
        }
        if (persoon.getVerblijfstitel() != null) {
            this.verblijfstitel = new PersoonVerblijfstitelGroepModel(persoon.getVerblijfstitel());
        }
        if (persoon.getBijzondereVerblijfsrechtelijkePositie() != null) {
            this.bijzondereVerblijfsrechtelijkePositie =
                new PersoonBijzondereVerblijfsrechtelijkePositieGroepModel(
                        persoon.getBijzondereVerblijfsrechtelijkePositie());
        }
        if (persoon.getUitsluitingNLKiesrecht() != null) {
            this.uitsluitingNLKiesrecht =
                new PersoonUitsluitingNLKiesrechtGroepModel(persoon.getUitsluitingNLKiesrecht());
        }
        if (persoon.getEUVerkiezingen() != null) {
            this.eUVerkiezingen = new PersoonEUVerkiezingenGroepModel(persoon.getEUVerkiezingen());
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
    public PersoonBijhoudingsaardGroepModel getBijhoudingsaard() {
        return bijhoudingsaard;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonBijhoudingsgemeenteGroepModel getBijhoudingsgemeente() {
        return bijhoudingsgemeente;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonOpschortingGroepModel getOpschorting() {
        return opschorting;
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
    public PersoonAanschrijvingGroepModel getAanschrijving() {
        return aanschrijving;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonImmigratieGroepModel getImmigratie() {
        return immigratie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonVerblijfstitelGroepModel getVerblijfstitel() {
        return verblijfstitel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonBijzondereVerblijfsrechtelijkePositieGroepModel getBijzondereVerblijfsrechtelijkePositie() {
        return bijzondereVerblijfsrechtelijkePositie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonUitsluitingNLKiesrechtGroepModel getUitsluitingNLKiesrecht() {
        return uitsluitingNLKiesrecht;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonEUVerkiezingenGroepModel getEUVerkiezingen() {
        return eUVerkiezingen;
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
    public Set<PersoonVoornaamModel> getVoornamen() {
        return voornamen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<PersoonGeslachtsnaamcomponentModel> getGeslachtsnaamcomponenten() {
        return geslachtsnaamcomponenten;
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
     * Retourneert Aanschrijving StatusHis van Persoon.
     *
     * @return Aanschrijving StatusHis.
     */
    public StatusHistorie getAanschrijvingStatusHis() {
        return aanschrijvingStatusHis;
    }

    /**
     * Retourneert Bijhoudingsaard StatusHis van Persoon.
     *
     * @return Bijhoudingsaard StatusHis.
     */
    public StatusHistorie getBijhoudingsaardStatusHis() {
        return bijhoudingsaardStatusHis;
    }

    /**
     * Retourneert Bijhoudingsgemeente StatusHis van Persoon.
     *
     * @return Bijhoudingsgemeente StatusHis.
     */
    public StatusHistorie getBijhoudingsgemeenteStatusHis() {
        return bijhoudingsgemeenteStatusHis;
    }

    /**
     * Retourneert Bijzondere verblijfsrechtelijke positie StatusHis van Persoon.
     *
     * @return Bijzondere verblijfsrechtelijke positie StatusHis.
     */
    public StatusHistorie getBijzondereVerblijfsrechtelijkePositieStatusHis() {
        return bijzondereVerblijfsrechtelijkePositieStatusHis;
    }

    /**
     * Retourneert EU verkiezingen StatusHis van Persoon.
     *
     * @return EU verkiezingen StatusHis.
     */
    public StatusHistorie getEUVerkiezingenStatusHis() {
        return eUVerkiezingenStatusHis;
    }

    /**
     * Retourneert Geboorte StatusHis van Persoon.
     *
     * @return Geboorte StatusHis.
     */
    public StatusHistorie getGeboorteStatusHis() {
        return geboorteStatusHis;
    }

    /**
     * Retourneert Geslachtsaanduiding StatusHis van Persoon.
     *
     * @return Geslachtsaanduiding StatusHis.
     */
    public StatusHistorie getGeslachtsaanduidingStatusHis() {
        return geslachtsaanduidingStatusHis;
    }

    /**
     * Retourneert Identificatienummers StatusHis van Persoon.
     *
     * @return Identificatienummers StatusHis.
     */
    public StatusHistorie getIdentificatienummersStatusHis() {
        return identificatienummersStatusHis;
    }

    /**
     * Retourneert Immigratie StatusHis van Persoon.
     *
     * @return Immigratie StatusHis.
     */
    public StatusHistorie getImmigratieStatusHis() {
        return immigratieStatusHis;
    }

    /**
     * Retourneert Inschrijving StatusHis van Persoon.
     *
     * @return Inschrijving StatusHis.
     */
    public StatusHistorie getInschrijvingStatusHis() {
        return inschrijvingStatusHis;
    }

    /**
     * Retourneert Opschorting StatusHis van Persoon.
     *
     * @return Opschorting StatusHis.
     */
    public StatusHistorie getOpschortingStatusHis() {
        return opschortingStatusHis;
    }

    /**
     * Retourneert Overlijden StatusHis van Persoon.
     *
     * @return Overlijden StatusHis.
     */
    public StatusHistorie getOverlijdenStatusHis() {
        return overlijdenStatusHis;
    }

    /**
     * Retourneert Persoonskaart StatusHis van Persoon.
     *
     * @return Persoonskaart StatusHis.
     */
    public StatusHistorie getPersoonskaartStatusHis() {
        return persoonskaartStatusHis;
    }

    /**
     * Retourneert Samengestelde naam StatusHis van Persoon.
     *
     * @return Samengestelde naam StatusHis.
     */
    public StatusHistorie getSamengesteldeNaamStatusHis() {
        return samengesteldeNaamStatusHis;
    }

    /**
     * Retourneert Uitsluiting NL kiesrecht StatusHis van Persoon.
     *
     * @return Uitsluiting NL kiesrecht StatusHis.
     */
    public StatusHistorie getUitsluitingNLKiesrechtStatusHis() {
        return uitsluitingNLKiesrechtStatusHis;
    }

    /**
     * Retourneert Verblijfstitel StatusHis van Persoon.
     *
     * @return Verblijfstitel StatusHis.
     */
    public StatusHistorie getVerblijfstitelStatusHis() {
        return verblijfstitelStatusHis;
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
     * Zet Identificatienummers van Persoon. Zet tevens het bijbehorende status his veld op 'A' als het argument niet
     * null
     * is.
     *
     * @param identificatienummers Identificatienummers.
     */
    public void setIdentificatienummers(final PersoonIdentificatienummersGroepModel identificatienummers) {
        this.identificatienummers = identificatienummers;
        if (identificatienummers != null) {
            identificatienummersStatusHis = StatusHistorie.A;
        }

    }

    /**
     * Zet Samengestelde naam van Persoon. Zet tevens het bijbehorende status his veld op 'A' als het argument niet null
     * is.
     *
     * @param samengesteldeNaam Samengestelde naam.
     */
    public void setSamengesteldeNaam(final PersoonSamengesteldeNaamGroepModel samengesteldeNaam) {
        this.samengesteldeNaam = samengesteldeNaam;
        if (samengesteldeNaam != null) {
            samengesteldeNaamStatusHis = StatusHistorie.A;
        }

    }

    /**
     * Zet Geboorte van Persoon. Zet tevens het bijbehorende status his veld op 'A' als het argument niet null is.
     *
     * @param geboorte Geboorte.
     */
    public void setGeboorte(final PersoonGeboorteGroepModel geboorte) {
        this.geboorte = geboorte;
        if (geboorte != null) {
            geboorteStatusHis = StatusHistorie.A;
        }

    }

    /**
     * Zet Geslachtsaanduiding van Persoon. Zet tevens het bijbehorende status his veld op 'A' als het argument niet
     * null
     * is.
     *
     * @param geslachtsaanduiding Geslachtsaanduiding.
     */
    public void setGeslachtsaanduiding(final PersoonGeslachtsaanduidingGroepModel geslachtsaanduiding) {
        this.geslachtsaanduiding = geslachtsaanduiding;
        if (geslachtsaanduiding != null) {
            geslachtsaanduidingStatusHis = StatusHistorie.A;
        }

    }

    /**
     * Zet Inschrijving van Persoon. Zet tevens het bijbehorende status his veld op 'A' als het argument niet null is.
     *
     * @param inschrijving Inschrijving.
     */
    public void setInschrijving(final PersoonInschrijvingGroepModel inschrijving) {
        this.inschrijving = inschrijving;
        if (inschrijving != null) {
            inschrijvingStatusHis = StatusHistorie.A;
        }

    }

    /**
     * Zet Bijhoudingsaard van Persoon. Zet tevens het bijbehorende status his veld op 'A' als het argument niet null
     * is.
     *
     * @param bijhoudingsaard Bijhoudingsaard.
     */
    public void setBijhoudingsaard(final PersoonBijhoudingsaardGroepModel bijhoudingsaard) {
        this.bijhoudingsaard = bijhoudingsaard;
        if (bijhoudingsaard != null) {
            bijhoudingsaardStatusHis = StatusHistorie.A;
        }

    }

    /**
     * Zet Bijhoudingsgemeente van Persoon. Zet tevens het bijbehorende status his veld op 'A' als het argument niet
     * null
     * is.
     *
     * @param bijhoudingsgemeente Bijhoudingsgemeente.
     */
    public void setBijhoudingsgemeente(final PersoonBijhoudingsgemeenteGroepModel bijhoudingsgemeente) {
        this.bijhoudingsgemeente = bijhoudingsgemeente;
        if (bijhoudingsgemeente != null) {
            bijhoudingsgemeenteStatusHis = StatusHistorie.A;
        }

    }

    /**
     * Zet Opschorting van Persoon. Zet tevens het bijbehorende status his veld op 'A' als het argument niet null is.
     *
     * @param opschorting Opschorting.
     */
    public void setOpschorting(final PersoonOpschortingGroepModel opschorting) {
        this.opschorting = opschorting;
        if (opschorting != null) {
            opschortingStatusHis = StatusHistorie.A;
        }

    }

    /**
     * Zet Overlijden van Persoon. Zet tevens het bijbehorende status his veld op 'A' als het argument niet null is.
     *
     * @param overlijden Overlijden.
     */
    public void setOverlijden(final PersoonOverlijdenGroepModel overlijden) {
        this.overlijden = overlijden;
        if (overlijden != null) {
            overlijdenStatusHis = StatusHistorie.A;
        }

    }

    /**
     * Zet Aanschrijving van Persoon. Zet tevens het bijbehorende status his veld op 'A' als het argument niet null is.
     *
     * @param aanschrijving Aanschrijving.
     */
    public void setAanschrijving(final PersoonAanschrijvingGroepModel aanschrijving) {
        this.aanschrijving = aanschrijving;
        if (aanschrijving != null) {
            aanschrijvingStatusHis = StatusHistorie.A;
        }

    }

    /**
     * Zet Immigratie van Persoon. Zet tevens het bijbehorende status his veld op 'A' als het argument niet null is.
     *
     * @param immigratie Immigratie.
     */
    public void setImmigratie(final PersoonImmigratieGroepModel immigratie) {
        this.immigratie = immigratie;
        if (immigratie != null) {
            immigratieStatusHis = StatusHistorie.A;
        }

    }

    /**
     * Zet Verblijfstitel van Persoon. Zet tevens het bijbehorende status his veld op 'A' als het argument niet null is.
     *
     * @param verblijfstitel Verblijfstitel.
     */
    public void setVerblijfstitel(final PersoonVerblijfstitelGroepModel verblijfstitel) {
        this.verblijfstitel = verblijfstitel;
        if (verblijfstitel != null) {
            verblijfstitelStatusHis = StatusHistorie.A;
        }

    }

    /**
     * Zet Bijzondere verblijfsrechtelijke positie van Persoon. Zet tevens het bijbehorende status his veld op 'A' als
     * het
     * argument niet null is.
     *
     * @param bijzondereVerblijfsrechtelijkePositie Bijzondere verblijfsrechtelijke positie.
     */
    public void setBijzondereVerblijfsrechtelijkePositie(
            final PersoonBijzondereVerblijfsrechtelijkePositieGroepModel bijzondereVerblijfsrechtelijkePositie)
    {
        this.bijzondereVerblijfsrechtelijkePositie = bijzondereVerblijfsrechtelijkePositie;
        if (bijzondereVerblijfsrechtelijkePositie != null) {
            bijzondereVerblijfsrechtelijkePositieStatusHis = StatusHistorie.A;
        }

    }

    /**
     * Zet Uitsluiting NL kiesrecht van Persoon. Zet tevens het bijbehorende status his veld op 'A' als het argument
     * niet
     * null is.
     *
     * @param uitsluitingNLKiesrecht Uitsluiting NL kiesrecht.
     */
    public void setUitsluitingNLKiesrecht(final PersoonUitsluitingNLKiesrechtGroepModel uitsluitingNLKiesrecht) {
        this.uitsluitingNLKiesrecht = uitsluitingNLKiesrecht;
        if (uitsluitingNLKiesrecht != null) {
            uitsluitingNLKiesrechtStatusHis = StatusHistorie.A;
        }

    }

    /**
     * Zet EU verkiezingen van Persoon. Zet tevens het bijbehorende status his veld op 'A' als het argument niet null
     * is.
     *
     * @param eUVerkiezingen EU verkiezingen.
     */
    public void setEUVerkiezingen(final PersoonEUVerkiezingenGroepModel eUVerkiezingen) {
        this.eUVerkiezingen = eUVerkiezingen;
        if (eUVerkiezingen != null) {
            eUVerkiezingenStatusHis = StatusHistorie.A;
        }

    }

    /**
     * Zet Persoonskaart van Persoon. Zet tevens het bijbehorende status his veld op 'A' als het argument niet null is.
     *
     * @param persoonskaart Persoonskaart.
     */
    public void setPersoonskaart(final PersoonPersoonskaartGroepModel persoonskaart) {
        this.persoonskaart = persoonskaart;
        if (persoonskaart != null) {
            persoonskaartStatusHis = StatusHistorie.A;
        }

    }

    /**
     * Zet Aanschrijving StatusHis van Persoon.
     *
     * @param aanschrijvingStatusHis Aanschrijving StatusHis.
     */
    public void setAanschrijvingStatusHis(final StatusHistorie aanschrijvingStatusHis) {
        this.aanschrijvingStatusHis = aanschrijvingStatusHis;
    }

    /**
     * Zet Bijhoudingsaard StatusHis van Persoon.
     *
     * @param bijhoudingsaardStatusHis Bijhoudingsaard StatusHis.
     */
    public void setBijhoudingsaardStatusHis(final StatusHistorie bijhoudingsaardStatusHis) {
        this.bijhoudingsaardStatusHis = bijhoudingsaardStatusHis;
    }

    /**
     * Zet Bijhoudingsgemeente StatusHis van Persoon.
     *
     * @param bijhoudingsgemeenteStatusHis Bijhoudingsgemeente StatusHis.
     */
    public void setBijhoudingsgemeenteStatusHis(final StatusHistorie bijhoudingsgemeenteStatusHis) {
        this.bijhoudingsgemeenteStatusHis = bijhoudingsgemeenteStatusHis;
    }

    /**
     * Zet Bijzondere verblijfsrechtelijke positie StatusHis van Persoon.
     *
     * @param bijzondereVerblijfsrechtelijkePositieStatusHis Bijzondere verblijfsrechtelijke positie StatusHis.
     */
    public void setBijzondereVerblijfsrechtelijkePositieStatusHis(
            final StatusHistorie bijzondereVerblijfsrechtelijkePositieStatusHis)
    {
        this.bijzondereVerblijfsrechtelijkePositieStatusHis = bijzondereVerblijfsrechtelijkePositieStatusHis;
    }

    /**
     * Zet EU verkiezingen StatusHis van Persoon.
     *
     * @param eUVerkiezingenStatusHis EU verkiezingen StatusHis.
     */
    public void setEUVerkiezingenStatusHis(final StatusHistorie eUVerkiezingenStatusHis) {
        this.eUVerkiezingenStatusHis = eUVerkiezingenStatusHis;
    }

    /**
     * Zet Geboorte StatusHis van Persoon.
     *
     * @param geboorteStatusHis Geboorte StatusHis.
     */
    public void setGeboorteStatusHis(final StatusHistorie geboorteStatusHis) {
        this.geboorteStatusHis = geboorteStatusHis;
    }

    /**
     * Zet Geslachtsaanduiding StatusHis van Persoon.
     *
     * @param geslachtsaanduidingStatusHis Geslachtsaanduiding StatusHis.
     */
    public void setGeslachtsaanduidingStatusHis(final StatusHistorie geslachtsaanduidingStatusHis) {
        this.geslachtsaanduidingStatusHis = geslachtsaanduidingStatusHis;
    }

    /**
     * Zet Identificatienummers StatusHis van Persoon.
     *
     * @param identificatienummersStatusHis Identificatienummers StatusHis.
     */
    public void setIdentificatienummersStatusHis(final StatusHistorie identificatienummersStatusHis) {
        this.identificatienummersStatusHis = identificatienummersStatusHis;
    }

    /**
     * Zet Immigratie StatusHis van Persoon.
     *
     * @param immigratieStatusHis Immigratie StatusHis.
     */
    public void setImmigratieStatusHis(final StatusHistorie immigratieStatusHis) {
        this.immigratieStatusHis = immigratieStatusHis;
    }

    /**
     * Zet Inschrijving StatusHis van Persoon.
     *
     * @param inschrijvingStatusHis Inschrijving StatusHis.
     */
    public void setInschrijvingStatusHis(final StatusHistorie inschrijvingStatusHis) {
        this.inschrijvingStatusHis = inschrijvingStatusHis;
    }

    /**
     * Zet Opschorting StatusHis van Persoon.
     *
     * @param opschortingStatusHis Opschorting StatusHis.
     */
    public void setOpschortingStatusHis(final StatusHistorie opschortingStatusHis) {
        this.opschortingStatusHis = opschortingStatusHis;
    }

    /**
     * Zet Overlijden StatusHis van Persoon.
     *
     * @param overlijdenStatusHis Overlijden StatusHis.
     */
    public void setOverlijdenStatusHis(final StatusHistorie overlijdenStatusHis) {
        this.overlijdenStatusHis = overlijdenStatusHis;
    }

    /**
     * Zet Persoonskaart StatusHis van Persoon.
     *
     * @param persoonskaartStatusHis Persoonskaart StatusHis.
     */
    public void setPersoonskaartStatusHis(final StatusHistorie persoonskaartStatusHis) {
        this.persoonskaartStatusHis = persoonskaartStatusHis;
    }

    /**
     * Zet Samengestelde naam StatusHis van Persoon.
     *
     * @param samengesteldeNaamStatusHis Samengestelde naam StatusHis.
     */
    public void setSamengesteldeNaamStatusHis(final StatusHistorie samengesteldeNaamStatusHis) {
        this.samengesteldeNaamStatusHis = samengesteldeNaamStatusHis;
    }

    /**
     * Zet Uitsluiting NL kiesrecht StatusHis van Persoon.
     *
     * @param uitsluitingNLKiesrechtStatusHis Uitsluiting NL kiesrecht StatusHis.
     */
    public void setUitsluitingNLKiesrechtStatusHis(final StatusHistorie uitsluitingNLKiesrechtStatusHis) {
        this.uitsluitingNLKiesrechtStatusHis = uitsluitingNLKiesrechtStatusHis;
    }

    /**
     * Zet Verblijfstitel StatusHis van Persoon.
     *
     * @param verblijfstitelStatusHis Verblijfstitel StatusHis.
     */
    public void setVerblijfstitelStatusHis(final StatusHistorie verblijfstitelStatusHis) {
        this.verblijfstitelStatusHis = verblijfstitelStatusHis;
    }

}
