package nl.bzk.brp.model.objecttype.operationeel.basis;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.groep.logisch.basis.PersoonBijhoudingsgemeenteGroepBasis;
import nl.bzk.brp.model.groep.logisch.basis.PersoonBijhoudingsverantwoordelijkheidGroepBasis;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonAanschrijvingGroepModel;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonAfgeleidAdministratiefGroepModel;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonBijhoudingsgemeenteGroepModel;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonBijhoudingsverantwoordelijkheidGroepModel;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonEUVerkiezingenGroepModel;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonGeboorteGroepModel;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonGeslachtsaanduidingGroepModel;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonIdentificatienummersGroepModel;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonImmigratieGroepModel;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonInschrijvingGroepModel;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonOpschortingGroepModel;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonOverlijdenGroepModel;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonSamengesteldeNaamGroepModel;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonUitsluitingNLKiesrechtGroepModel;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonVerblijfsrechtGroepModel;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonskaartGroepModel;
import nl.bzk.brp.model.objecttype.logisch.basis.PersoonBasis;
import nl.bzk.brp.model.objecttype.operationeel.BetrokkenheidModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonAdresModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonGeslachtsnaamcomponentModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonIndicatieModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonNationaliteitModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonVoornaamModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortPersoon;
import nl.bzk.brp.model.objecttype.operationeel.statisch.StatusHistorie;


/**
 * De eerste laag implemenetatie van {@link}Persoon interface.
 * Deze class is een onderdeel van de model tree.
 * Deze implementatie wordt door de genrator gegenereerd.
 *
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonModel extends AbstractDynamischObjectType implements PersoonBasis {

    @Id
    @SequenceGenerator(name = "PERSOON", sequenceName = "Kern.seq_Pers")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSOON")
    private Long id;

    @Column(name = "Srt")
    @Enumerated
    private SoortPersoon soort;

    @Embedded
    private PersoonIdentificatienummersGroepModel identificatienummersGroep;

    @Embedded
    private PersoonGeslachtsaanduidingGroepModel geslachtsaanduidingGroep;

    @Embedded
    @NotNull
    private PersoonSamengesteldeNaamGroepModel samengesteldeNaamGroep;

    @Embedded
    private PersoonAanschrijvingGroepModel aanschrijvingGroep;

    @Embedded
    private PersoonGeboorteGroepModel geboorteGroep;

    @Embedded
    private PersoonOverlijdenGroepModel overlijdenGroep;

    @Embedded
    private PersoonVerblijfsrechtGroepModel verblijfsrechtGroep;

    @Embedded
    private PersoonUitsluitingNLKiesrechtGroepModel uitsluitingNlKiesrechtGroep;

    @Embedded
    private PersoonEUVerkiezingenGroepModel euVerkiezingenGroep;

    @Embedded
    private PersoonBijhoudingsverantwoordelijkheidGroepModel bijhoudingsverantwoordelijkheidGroep;

    @Embedded
    private PersoonOpschortingGroepModel opschortingGroep;

    @Embedded
    private PersoonBijhoudingsgemeenteGroepModel bijhoudingsgemeenteGroep;

    @Embedded
    private PersoonPersoonskaartGroepModel persoonskaartGroep;

    @Embedded
    private PersoonImmigratieGroepModel immigratieGroep;

    @Embedded
    private PersoonInschrijvingGroepModel inschrijvingGroep;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "TijdstipLaatsteWijz"))
    private DatumTijd tijdstipLaatsteWijziging;

    @Column(name = "IndGegevensInOnderzoek")
    @Type(type = "JaNee")
    private JaNee indicatieGegevensInOnderzoek;

    @Column(name = "IDsStatusHis")
    @Enumerated(value = EnumType.STRING)
    @NotNull
    private StatusHistorie identificatienummersStatusHis = StatusHistorie.X;

    @Column(name = "GeslachtsaandStatusHis")
    @Enumerated(value = EnumType.STRING)
    @NotNull
    private StatusHistorie geslachtsaanduidingStatusHis = StatusHistorie.X;

    @Column(name = "SamengesteldeNaamStatusHis")
    @Enumerated(value = EnumType.STRING)
    @NotNull
    private StatusHistorie samengesteldeNaamStatusHis = StatusHistorie.X;

    @Column(name = "AanschrStatusHis")
    @Enumerated(value = EnumType.STRING)
    @NotNull
    private StatusHistorie aanschrijvingStatusHis = StatusHistorie.X;

    @Column(name = "GeboorteStatusHis")
    @Enumerated(value = EnumType.STRING)
    @NotNull
    private StatusHistorie geboorteStatusHis = StatusHistorie.X;

    @Column(name = "OverlijdenStatusHis")
    @Enumerated(value = EnumType.STRING)
    @NotNull
    private StatusHistorie overlijdenStatusHis = StatusHistorie.X;

    @Column(name = "VerblijfsrStatusHis")
    @Enumerated(value = EnumType.STRING)
    @NotNull
    private StatusHistorie verblijfsrechtStatusHis = StatusHistorie.X;

    @Column(name = "UitslNLKiesrStatusHis")
    @Enumerated(value = EnumType.STRING)
    @NotNull
    private StatusHistorie uitsluitingNlKiesrechtStatusHis = StatusHistorie.X;

    @Column(name = "EUVerkiezingenStatusHis")
    @Enumerated(value = EnumType.STRING)
    @NotNull
    private StatusHistorie euVerkiezingenStatusHis = StatusHistorie.X;

    @Column(name = "BijhverantwoordelijkheidStat")
    @Enumerated(value = EnumType.STRING)
    @NotNull
    private StatusHistorie bijhoudingsverantwoordelijkheidStatusHis = StatusHistorie.X;

    @Column(name = "OpschortingStatusHis")
    @Enumerated(value = EnumType.STRING)
    @NotNull
    private StatusHistorie opschortingStatusHis = StatusHistorie.X;

    @Column(name = "BijhgemStatusHis")
    @Enumerated(value = EnumType.STRING)
    @NotNull
    private StatusHistorie bijhoudingsgemeenteStatusHis = StatusHistorie.X;

    @Column(name = "PKStatusHis")
    @Enumerated(value = EnumType.STRING)
    @NotNull
    private StatusHistorie persoonskaartStatusHis = StatusHistorie.X;

    @Column(name = "ImmigratieStatusHis")
    @Enumerated(value = EnumType.STRING)
    @NotNull
    private StatusHistorie immigratieStatusHis = StatusHistorie.X;

    @Column(name = "InschrStatusHis")
    @Enumerated(value = EnumType.STRING)
    @NotNull
    private StatusHistorie inschrijvingStatusHis = StatusHistorie.X;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "Pers")
    private Set<PersoonVoornaamModel> voornamen = new HashSet<PersoonVoornaamModel>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "Pers")
    private Set<PersoonGeslachtsnaamcomponentModel> geslachtsnaamcomponenten = new HashSet<PersoonGeslachtsnaamcomponentModel>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "Pers")
    private Set<PersoonNationaliteitModel> nationaliteiten = new HashSet<PersoonNationaliteitModel>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "Pers")
    private Set<PersoonAdresModel> adressen = new HashSet<PersoonAdresModel>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "Pers")
    private Set<PersoonIndicatieModel> indicaties = new HashSet<PersoonIndicatieModel>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "Pers")
    private Set<PersoonReisdocumentModel> reisdocumenten = new HashSet<PersoonReisdocumentModel>();

    @OneToMany(cascade = { CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.REMOVE },
               fetch = FetchType.EAGER)
    @JoinColumn(name = "Pers")
    private Set<BetrokkenheidModel> betrokkenheden = new HashSet<BetrokkenheidModel>();

    /**
     * Copy constructor.
     *
     * @param persoon Object type dat gekopieerd dient te worden.
     */
    protected AbstractPersoonModel(final PersoonBasis persoon) {
        super(persoon);
        soort = persoon.getSoort();
        tijdstipLaatsteWijziging = persoon.getTijdstipLaatsteWijziging();
        indicatieGegevensInOnderzoek = persoon.getIndicatieGegevensInOnderzoek();
        if (persoon.getIdentificatienummersGroep() != null) {
            identificatienummersGroep = new PersoonIdentificatienummersGroepModel(persoon.getIdentificatienummersGroep());
            identificatienummersStatusHis = StatusHistorie.A;
        }
        if (persoon.getGeslachtsaanduidingGroep() != null) {
            geslachtsaanduidingGroep = new PersoonGeslachtsaanduidingGroepModel(persoon.getGeslachtsaanduidingGroep());
            geslachtsaanduidingStatusHis = StatusHistorie.A;
        }
        if (persoon.getSamengesteldeNaamGroep() != null) {
            samengesteldeNaamGroep = new PersoonSamengesteldeNaamGroepModel(persoon.getSamengesteldeNaamGroep());
            samengesteldeNaamStatusHis = StatusHistorie.A;
        }
        if (persoon.getAanschrijvingGroep() != null) {
            aanschrijvingGroep = new PersoonAanschrijvingGroepModel(persoon.getAanschrijvingGroep());
            aanschrijvingStatusHis = StatusHistorie.A;
        }
        if (persoon.getGeboorteGroep() != null) {
            geboorteGroep = new PersoonGeboorteGroepModel(persoon.getGeboorteGroep());
            geboorteStatusHis = StatusHistorie.A;
        }
        if (persoon.getOverlijdenGroep() != null) {
            overlijdenGroep = new PersoonOverlijdenGroepModel(persoon.getOverlijdenGroep());
            overlijdenStatusHis = StatusHistorie.A;
        }
        if (persoon.getVerblijfsrechtGroep() != null) {
            verblijfsrechtGroep = new PersoonVerblijfsrechtGroepModel(persoon.getVerblijfsrechtGroep());
            verblijfsrechtStatusHis = StatusHistorie.A;
        }
        if (persoon.getUitsluitingNLKiesrechtGroep() != null) {
            uitsluitingNlKiesrechtGroep = new PersoonUitsluitingNLKiesrechtGroepModel(persoon.getUitsluitingNLKiesrechtGroep());
            uitsluitingNlKiesrechtStatusHis = StatusHistorie.A;
        }
        if (persoon.getEUVerkiezingenGroep() != null) {
            euVerkiezingenGroep = new PersoonEUVerkiezingenGroepModel(persoon.getEUVerkiezingenGroep());
            euVerkiezingenStatusHis = StatusHistorie.A;
        }
        if (persoon.getBijhoudingsverantwoordelijkheidGroep() != null) {
            bijhoudingsverantwoordelijkheidGroep = new PersoonBijhoudingsverantwoordelijkheidGroepModel(persoon.getBijhoudingsverantwoordelijkheidGroep());
            bijhoudingsverantwoordelijkheidStatusHis = StatusHistorie.A;
        }
        if (persoon.getOpschortingGroep() != null) {
            opschortingGroep = new PersoonOpschortingGroepModel(persoon.getOpschortingGroep());
            opschortingStatusHis = StatusHistorie.A;
        }
        if (persoon.getBijhoudingsgemeenteGroep() != null) {
            bijhoudingsgemeenteGroep = new PersoonBijhoudingsgemeenteGroepModel(persoon.getBijhoudingsgemeenteGroep());
            bijhoudingsgemeenteStatusHis = StatusHistorie.A;
        }
        if (persoon.getPersoonskaartGroep() != null) {
            persoonskaartGroep = new PersoonPersoonskaartGroepModel(persoon.getPersoonskaartGroep());
            persoonskaartStatusHis = StatusHistorie.A;
        }
        if (persoon.getImmigratieGroep() != null) {
            immigratieGroep = new PersoonImmigratieGroepModel(persoon.getImmigratieGroep());
            immigratieStatusHis = StatusHistorie.A;
        }
        if (persoon.getInschrijvingGroep() != null) {
            inschrijvingGroep = new PersoonInschrijvingGroepModel(persoon.getInschrijvingGroep());
            inschrijvingStatusHis = StatusHistorie.A;
        }
    }

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractPersoonModel() {
    }

    /**
     * Vervang een lijst van groepen met nieuwe data.
     *
     * @param groepen de lijst
     */
    public void vervangGroepen(final Groep ... groepen) {
        for (Groep groep: groepen) {
            if (groep == null) {
                continue;
	        } else if (groep instanceof PersoonIdentificatienummersGroepBasis) {
	            identificatienummersGroep =
	                new PersoonIdentificatienummersGroepModel(
	                    (PersoonIdentificatienummersGroepBasis) groep);
	            identificatienummersStatusHis = StatusHistorie.A;
	        } else if (groep instanceof PersoonGeslachtsaanduidingGroepBasis) {
	            geslachtsaanduidingGroep =
	                new PersoonGeslachtsaanduidingGroepModel(
	                    (PersoonGeslachtsaanduidingGroepBasis) groep);
	            geslachtsaanduidingStatusHis = StatusHistorie.A;
	        } else if (groep instanceof PersoonSamengesteldeNaamGroepBasis) {
	            samengesteldeNaamGroep =
	                new PersoonSamengesteldeNaamGroepModel(
	                    (PersoonSamengesteldeNaamGroepBasis) groep);
	            samengesteldeNaamStatusHis = StatusHistorie.A;
	        } else if (groep instanceof PersoonAanschrijvingGroepBasis) {
	            aanschrijvingGroep =
	                new PersoonAanschrijvingGroepModel(
	                    (PersoonAanschrijvingGroepBasis) groep);
	            aanschrijvingStatusHis = StatusHistorie.A;
	        } else if (groep instanceof PersoonGeboorteGroepBasis) {
	            geboorteGroep =
	                new PersoonGeboorteGroepModel(
	                    (PersoonGeboorteGroepBasis) groep);
	            geboorteStatusHis = StatusHistorie.A;
	        } else if (groep instanceof PersoonOverlijdenGroepBasis) {
	            overlijdenGroep =
	                new PersoonOverlijdenGroepModel(
	                    (PersoonOverlijdenGroepBasis) groep);
	            overlijdenStatusHis = StatusHistorie.A;
	        } else if (groep instanceof PersoonVerblijfsrechtGroepBasis) {
	            verblijfsrechtGroep =
	                new PersoonVerblijfsrechtGroepModel(
	                    (PersoonVerblijfsrechtGroepBasis) groep);
	            verblijfsrechtStatusHis = StatusHistorie.A;
	        } else if (groep instanceof PersoonUitsluitingNLKiesrechtGroepBasis) {
	            uitsluitingNlKiesrechtGroep =
	                new PersoonUitsluitingNLKiesrechtGroepModel(
	                    (PersoonUitsluitingNLKiesrechtGroepBasis) groep);
	            uitsluitingNlKiesrechtStatusHis = StatusHistorie.A;
	        } else if (groep instanceof PersoonEUVerkiezingenGroepBasis) {
	            euVerkiezingenGroep =
	                new PersoonEUVerkiezingenGroepModel(
	                    (PersoonEUVerkiezingenGroepBasis) groep);
	            euVerkiezingenStatusHis = StatusHistorie.A;
	        } else if (groep instanceof PersoonBijhoudingsverantwoordelijkheidGroepBasis) {
	            bijhoudingsverantwoordelijkheidGroep =
	                new PersoonBijhoudingsverantwoordelijkheidGroepModel(
	                    (PersoonBijhoudingsverantwoordelijkheidGroepBasis) groep);
	            bijhoudingsverantwoordelijkheidStatusHis = StatusHistorie.A;
	        } else if (groep instanceof PersoonOpschortingGroepBasis) {
	            opschortingGroep =
	                new PersoonOpschortingGroepModel(
	                    (PersoonOpschortingGroepBasis) groep);
	            opschortingStatusHis = StatusHistorie.A;
	        } else if (groep instanceof PersoonBijhoudingsgemeenteGroepBasis) {
	            bijhoudingsgemeenteGroep =
	                new PersoonBijhoudingsgemeenteGroepModel(
	                    (PersoonBijhoudingsgemeenteGroepBasis) groep);
	            bijhoudingsgemeenteStatusHis = StatusHistorie.A;
	        } else if (groep instanceof PersoonPersoonskaartGroepBasis) {
	            persoonskaartGroep =
	                new PersoonPersoonskaartGroepModel(
	                    (PersoonPersoonskaartGroepBasis) groep);
	            persoonskaartStatusHis = StatusHistorie.A;
	        } else if (groep instanceof PersoonImmigratieGroepBasis) {
	            immigratieGroep =
	                new PersoonImmigratieGroepModel(
	                    (PersoonImmigratieGroepBasis) groep);
	            immigratieStatusHis = StatusHistorie.A;
	        } else if (groep instanceof PersoonInschrijvingGroepBasis) {
	            inschrijvingGroep =
	                new PersoonInschrijvingGroepModel(
	                    (PersoonInschrijvingGroepBasis) groep);
	            inschrijvingStatusHis = StatusHistorie.A;
            }
        }
    }

    public Long getId() {
        return id;
    }

    @Override
    public SoortPersoon getSoort() {
        return soort;
    }

    @Override
    public PersoonIdentificatienummersGroepModel getIdentificatienummersGroep() {
        return identificatienummersGroep;
    }

    @Override
    public PersoonGeslachtsaanduidingGroepModel getGeslachtsaanduidingGroep() {
        return geslachtsaanduidingGroep;
    }

    @Override
    public PersoonSamengesteldeNaamGroepModel getSamengesteldeNaamGroep() {
        return samengesteldeNaamGroep;
    }

    @Override
    public PersoonAanschrijvingGroepModel getAanschrijvingGroep() {
        return aanschrijvingGroep;
    }

    @Override
    public PersoonGeboorteGroepModel getGeboorteGroep() {
        return geboorteGroep;
    }

    @Override
    public PersoonOverlijdenGroepModel getOverlijdenGroep() {
        return overlijdenGroep;
    }

    @Override
    public PersoonVerblijfsrechtGroepModel getVerblijfsrechtGroep() {
        return verblijfsrechtGroep;
    }

    @Override
    public PersoonUitsluitingNLKiesrechtGroepModel getUitsluitingNLKiesrechtGroep() {
        return uitsluitingNlKiesrechtGroep;
    }

    @Override
    public PersoonEUVerkiezingenGroepModel getEUVerkiezingenGroep() {
        return euVerkiezingenGroep;
    }

    @Override
    public PersoonBijhoudingsverantwoordelijkheidGroepModel getBijhoudingsverantwoordelijkheidGroep() {
        return bijhoudingsverantwoordelijkheidGroep;
    }

    @Override
    public PersoonOpschortingGroepModel getOpschortingGroep() {
        return opschortingGroep;
    }

    @Override
    public PersoonBijhoudingsgemeenteGroepModel getBijhoudingsgemeenteGroep() {
        return bijhoudingsgemeenteGroep;
    }

    @Override
    public PersoonPersoonskaartGroepModel getPersoonskaartGroep() {
        return persoonskaartGroep;
    }

    @Override
    public PersoonImmigratieGroepModel getImmigratieGroep() {
        return immigratieGroep;
    }

    @Override
    public PersoonInschrijvingGroepModel getInschrijvingGroep() {
        return inschrijvingGroep;
    }

    @Override
    public DatumTijd getTijdstipLaatsteWijziging() {
        return tijdstipLaatsteWijziging;
    }

    @Override
    public JaNee getIndicatieGegevensInOnderzoek() {
        return indicatieGegevensInOnderzoek;
    }

    @Override
    public Set<PersoonVoornaamModel> getVoornamen() {
        return voornamen;
    }

    @Override
    public Set<PersoonGeslachtsnaamcomponentModel> getGeslachtsnaamcomponenten() {
        return geslachtsnaamcomponenten;
    }

    @Override
    public Set<PersoonNationaliteitModel> getNationaliteiten() {
        return nationaliteiten;
    }

    @Override
    public Set<PersoonAdresModel> getAdressen() {
        return adressen;
    }

    @Override
    public Set<PersoonIndicatieModel> getIndicaties() {
        return indicaties;
    }

    @Override
    public Set<PersoonReisdocumentModel> getReisdocumenten() {
        return reisdocumenten;
    }

    @Override
    public Set<BetrokkenheidModel> getBetrokkenheden() {
        return betrokkenheden;
    }

    protected void setSoort(final SoortPersoon soort) {
        this.soort = soort;
    }

    protected void setIdentificatienummers(final PersoonIdentificatienummersGroepModel identificatienummers) {
        this.identificatienummers = identificatienummers;
    }

    protected void setGeslachtsaanduiding(final PersoonGeslachtsaanduidingGroepModel geslachtsaanduiding) {
        this.geslachtsaanduiding = geslachtsaanduiding;
    }

    protected void setSamengesteldeNaam(final PersoonSamengesteldeNaamGroepModel samengesteldeNaam) {
        this.samengesteldeNaam = samengesteldeNaam;
    }

    protected void setAanschrijving(final PersoonAanschrijvingGroepModel aanschrijving) {
        this.aanschrijving = aanschrijving;
    }

    protected void setGeboorte(final PersoonGeboorteGroepModel geboorte) {
        this.geboorte = geboorte;
    }

    protected void setOpschorting(final PersoonOpschortingGroepModel opschorting) {
        this.opschorting = opschorting;
    }

    protected void setBijhoudenGemeente(final PersoonBijhoudingsgemeenteGroepModel bijhoudenGemeente) {
        this.bijhoudenGemeente = bijhoudenGemeente;
    }

    protected void setOverlijden(final PersoonOverlijdenGroepModel overlijden) {
        this.overlijden = overlijden;
    }

    protected void setInschrijving(final PersoonInschrijvingGroepModel inschrijving) {
        this.inschrijving = inschrijving;
    }

    protected void setBijhoudingVerantwoordelijke(
            final PersoonBijhoudingsverantwoordelijkheidGroepModel bijhoudingVerantwoordelijke)
    {
        this.bijhoudingVerantwoordelijke = bijhoudingVerantwoordelijke;
    }

    protected void setAfgeleidAdministratief(final PersoonAfgeleidAdministratiefGroepModel afgeleidAdministratief) {
        this.afgeleidAdministratief = afgeleidAdministratief;
    }

    protected void setBetrokkenheden(final Set<BetrokkenheidModel> betrokkenheden) {
        this.betrokkenheden = betrokkenheden;
    }

    protected void setAdressen(final Set<PersoonAdresModel> adressen) {
        this.adressen = adressen;
    }

    protected void setPersoonVoornaam(final Set<PersoonVoornaamModel> voornamen) {
        this.voornamen = voornamen;
    }

    protected void setGeslachtsnaamcomponenten(final Set<PersoonGeslachtsnaamcomponentModel> geslachtsnaamcomponenten) {
        this.geslachtsnaamcomponenten = geslachtsnaamcomponenten;
    }

    protected void setNationaliteiten(final Set<PersoonNationaliteitModel> nationaliteiten) {
        this.nationaliteiten = nationaliteiten;
    }

    protected void setIndicaties(final Set<PersoonIndicatieModel> indicaties) {
        this.indicaties = indicaties;
    }

}
