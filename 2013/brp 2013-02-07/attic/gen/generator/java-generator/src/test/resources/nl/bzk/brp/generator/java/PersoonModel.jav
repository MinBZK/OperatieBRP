package nl.bzk.brp.model.objecttype.operationeel.basis;

import java.util.HashSet;
import java.util.Set;

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
import javax.validation.constraints.NotNull;

import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.groep.logisch.PersoonEUVerkiezingenGroep;
import nl.bzk.brp.model.groep.logisch.PersoonImmigratieGroep;
import nl.bzk.brp.model.groep.logisch.PersoonOverlijdenGroep;
import nl.bzk.brp.model.groep.logisch.PersoonUitsluitingNLKiesrechtGroep;
import nl.bzk.brp.model.groep.logisch.PersoonVerblijfsrechtGroep;
import nl.bzk.brp.model.groep.logisch.PersoonskaartGroep;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonAanschrijvingGroepModel;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonAfgeleidAdministratiefGroepModel;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonBijhoudingsGemeenteGroepModel;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonBijhoudingsVerantwoordelijkheidGroepModel;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonGeboorteGroepModel;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonGeslachtsAanduidingGroepModel;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonIdentificatieNummersGroepModel;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonInschrijvingGroepModel;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonOpschortingGroepModel;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonOverlijdenGroepModel;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonSamengesteldeNaamGroepModel;
import nl.bzk.brp.model.objecttype.logisch.basis.PersoonBasis;
import nl.bzk.brp.model.objecttype.operationeel.BetrokkenheidModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonAdresModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonGeslachtsnaamComponentModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonIndicatieModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonNationaliteitModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonVoornaamModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortPersoon;


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
    @SequenceGenerator(name = "seq_Pers", sequenceName = "Kern.seq_Pers")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Pers")
    private Long                                           id;

    @Column(name = "srt")
    @Enumerated
    private SoortPersoon                                   soort;

    @Embedded
    private PersoonIdentificatieNummersGroepModel identificatieNummers;

    @Embedded
    private PersoonGeslachtsAanduidingGroepModel geslachtsAanduiding;

    @Embedded
    private PersoonSamengesteldeNaamGroepModel samengesteldeNaam;

    @Embedded
    private PersoonAanschrijvingGroepModel aanschrijving;

    @Embedded
    private PersoonGeboorteGroepModel geboorte;

    @Embedded
    private PersoonOpschortingGroepModel opschorting;

    @Embedded
    private PersoonBijhoudingsGemeenteGroepModel bijhoudenGemeente;

    @Embedded
    private PersoonOverlijdenGroepModel overlijden;

    @Embedded
    private PersoonInschrijvingGroepModel inschrijving;

    @Embedded
    private PersoonBijhoudingsVerantwoordelijkheidGroepModel bijhoudingVerantwoordelijke;

    @OneToMany(cascade = { CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.REMOVE },
               fetch = FetchType.EAGER)
    @JoinColumn(name = "betrokkene")
    private Set<BetrokkenheidModel>                          betrokkenheden;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "Pers")
    private Set<PersoonAdresModel>                           adressen;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "Pers")
    private Set<PersoonVoornaamModel>                        persoonVoornaam;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "Pers")
    private Set<PersoonGeslachtsnaamComponentModel>          geslachtsnaamcomponenten;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "Pers")
    private Set<PersoonNationaliteitModel>                   nationaliteiten;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "Pers")
    private Set<PersoonIndicatieModel>                       indicaties;

    /**
     * Copy constructor. Om een model object te construeren uit een web object.
     *
     * @param persoon Object type dat gekopieerd dient te worden.
     */
    protected AbstractPersoonModel(final PersoonBasis persoon) {
        super(persoon);
        soort = persoon.getSoort();
        if (persoon.getIdentificatieNummers() != null) {
            identificatieNummers = new PersoonIdentificatieNummersGroepModel(persoon.getIdentificatieNummers());
        }
        if (persoon.getGeslachtsAanduiding() != null) {
            geslachtsAanduiding = new PersoonGeslachtsAanduidingGroepModel(persoon.getGeslachtsAanduiding());
        }
        if (persoon.getSamengesteldeNaam() != null) {
            samengesteldeNaam = new PersoonSamengesteldeNaamGroepModel(persoon.getSamengesteldeNaam());
        }
        if (persoon.getAanschrijving() != null) {
            aanschrijving = new PersoonAanschrijvingGroepModel(persoon.getAanschrijving());
        }
        if (persoon.getGeboorte() != null) {
            geboorte = new PersoonGeboorteGroepModel(persoon.getGeboorte());
        }
        if (persoon.getBijhoudenGemeente() != null) {
            bijhoudenGemeente = new PersoonBijhoudingsGemeenteGroepModel(persoon.getBijhoudenGemeente());
        }
        if (persoon.getBijhoudingVerantwoordelijke() != null) {
            bijhoudingVerantwoordelijke =
                new PersoonBijhoudingsVerantwoordelijkheidGroepModel(persoon.getBijhoudingVerantwoordelijke());
        }
        if (persoon.getAfgeleidAdministratief() != null) {
            afgeleidAdministratief = new PersoonAfgeleidAdministratiefGroepModel(persoon.getAfgeleidAdministratief());
        }
        if (persoon.getInschrijving() != null) {
            inschrijving = new PersoonInschrijvingGroepModel(persoon.getInschrijving());
        }
        if (persoon.getOpschorting() != null) {
            opschorting = new PersoonOpschortingGroepModel(persoon.getOpschorting());
        }

        // Geslachtsnaam componenten
        geslachtsnaamcomponenten = new HashSet<PersoonGeslachtsnaamComponentModel>();

        // Nationaliteiten
        nationaliteiten = new HashSet<PersoonNationaliteitModel>();

        // Voornamen
        persoonVoornaam = new HashSet<PersoonVoornaamModel>();

        // Adressen
        adressen = new HashSet<PersoonAdresModel>();
    }

    /**
     * Default constructor. Vereist voor Hibernate.
     */
    protected AbstractPersoonModel() {
    }

    public Long getId() {
        return id;
    }

    @Override
    public SoortPersoon getSoort() {
        return soort;
    }

    @Override
    public PersoonIdentificatieNummersGroepModel getIdentificatieNummers() {
        return identificatieNummers;
    }

    @Override
    public PersoonGeslachtsAanduidingGroepModel getGeslachtsAanduiding() {
        return geslachtsAanduiding;
    }

    @Override
    public PersoonSamengesteldeNaamGroepModel getSamengesteldeNaam() {
        return samengesteldeNaam;
    }

    @Override
    public PersoonAanschrijvingGroepModel getAanschrijving() {
        return aanschrijving;
    }

    @Override
    public PersoonGeboorteGroepModel getGeboorte() {
        return geboorte;
    }

    @Override
    public PersoonBijhoudingsVerantwoordelijkheidGroepModel getBijhoudingVerantwoordelijke() {
        return bijhoudingVerantwoordelijke;
    }

    @Override
    public PersoonOpschortingGroepModel getOpschorting() {
        return opschorting;
    }

    @Override
    public PersoonBijhoudingsGemeenteGroepModel getBijhoudenGemeente() {
        return bijhoudenGemeente;
    }

    @Override
    public PersoonInschrijvingGroepModel getInschrijving() {
        return inschrijving;
    }

    @Override
    public PersoonAfgeleidAdministratiefGroepModel getAfgeleidAdministratief() {
        return afgeleidAdministratief;
    }

    @Override
    public PersoonOverlijdenGroep getOverlijden() {
        return overlijden;
    }

    @Override
    public PersoonVerblijfsrechtGroep getVerblijfsrecht() {
        return null;
    }

    @Override
    public PersoonUitsluitingNLKiesrechtGroep getUitsluitingNLKiesrecht() {
        return null;
    }

    @Override
    public PersoonEUVerkiezingenGroep getEUVerkiezingen() {
        return null;
    }

    @Override
    public PersoonskaartGroep getPersoonsKaart() {
        return null;
    }

    @Override
    public PersoonImmigratieGroep getImmigratie() {
        return null;
    }

    @Override
    public Set<BetrokkenheidModel> getBetrokkenheden() {
        return betrokkenheden;
    }

    @Override
    public Set<PersoonAdresModel> getAdressen() {
        return adressen;
    }

    @Override
    public Set<PersoonVoornaamModel> getPersoonVoornaam() {
        return persoonVoornaam;
    }

    @Override
    public Set<PersoonGeslachtsnaamComponentModel> getGeslachtsnaamcomponenten() {
        return geslachtsnaamcomponenten;
    }

    @Override
    public Set<PersoonNationaliteitModel> getNationaliteiten() {
        return nationaliteiten;
    }

    @Override
    public Set<PersoonIndicatieModel> getIndicaties() {
        return indicaties;
    }

    protected void setSoort(final SoortPersoon soort) {
        this.soort = soort;
    }

    protected void setIdentificatieNummers(final PersoonIdentificatieNummersGroepModel identificatieNummers) {
        this.identificatieNummers = identificatieNummers;
    }

    protected void setGeslachtsAanduiding(final PersoonGeslachtsAanduidingGroepModel geslachtsAanduiding) {
        this.geslachtsAanduiding = geslachtsAanduiding;
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

    protected void setBijhoudenGemeente(final PersoonBijhoudingsGemeenteGroepModel bijhoudenGemeente) {
        this.bijhoudenGemeente = bijhoudenGemeente;
    }

    protected void setOverlijden(final PersoonOverlijdenGroepModel overlijden) {
        this.overlijden = overlijden;
    }

    protected void setInschrijving(final PersoonInschrijvingGroepModel inschrijving) {
        this.inschrijving = inschrijving;
    }

    protected void setBijhoudingVerantwoordelijke(
            final PersoonBijhoudingsVerantwoordelijkheidGroepModel bijhoudingVerantwoordelijke)
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

    protected void setPersoonVoornaam(final Set<PersoonVoornaamModel> persoonVoornaam) {
        this.persoonVoornaam = persoonVoornaam;
    }

    protected void setGeslachtsnaamcomponenten(final Set<PersoonGeslachtsnaamComponentModel> geslachtsnaamcomponenten) {
        this.geslachtsnaamcomponenten = geslachtsnaamcomponenten;
    }

    protected void setNationaliteiten(final Set<PersoonNationaliteitModel> nationaliteiten) {
        this.nationaliteiten = nationaliteiten;
    }

    protected void setIndicaties(final Set<PersoonIndicatieModel> indicaties) {
        this.indicaties = indicaties;
    }
}
