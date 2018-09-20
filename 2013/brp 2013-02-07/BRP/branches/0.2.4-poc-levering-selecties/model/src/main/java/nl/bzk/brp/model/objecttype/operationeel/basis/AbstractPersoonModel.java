/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.basis;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.groep.logisch.basis.*;
import nl.bzk.brp.model.groep.operationeel.actueel.*;
import nl.bzk.brp.model.objecttype.logisch.basis.PersoonBasis;
import nl.bzk.brp.model.objecttype.operationeel.*;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortPersoon;
import nl.bzk.brp.model.objecttype.operationeel.statisch.StatusHistorie;


/**
 * De eerste laag implemenetatie van {@link}Persoon interface.
 * Deze class is een onderdeel van de model tree.
 * Deze implementatie wordt door de genrator gegenereerd.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonModel extends AbstractDynamischObjectType implements PersoonBasis {

    @Id
    @SequenceGenerator(name = "seq_Pers", sequenceName = "Kern.seq_Pers")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_Pers")
    private Integer                                          id;

    @Column(name = "srt")
    @Enumerated
    @NotNull
    private SoortPersoon                                     soort;

    @Column(name = "geboortestatushis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie                                   geboorteStatusHis;

    @Column(name = "overlijdenstatushis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie                                   overlijdenStatusHis;

    @Column(name = "samengesteldenaamstatushis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie                                   samengesteldeNaamStatusHis;

    @Column(name = "aanschrstatushis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie                                   aanschrijvingStatusHis;

    @Column(name = "idsstatushis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie                                   identificatienrsStatusHis;

    @Column(name = "geslachtsaandstatusHis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie                                   geslachtsaanduidingStatusHis;

    @Column(name = "verblijfsrstatushis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie                                   verblijfsrechtStatusHis;

    @Column(name = "uitslnlkiesrstatushis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie                                   uitsluitingNLKiesrechtStatusHis;

    @Column(name = "euverkiezingenstatushis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie                                   eUVerkiezingenStatusHis;

    @Column(name = "bijhverantwoordelijkheidstat")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie                                   bijhoudingsverantwoordelijkheidStatusHis;

    @Column(name = "opschortingstatushis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie                                   opschortingStatusHis;

    @Column(name = "bijhgemstatushis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie                                   bijhoudingsgemeenteStatusHis;

    @Column(name = "pkstatushis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie                                   persoonskaartStatusHis;

    @Column(name = "immigratiestatushis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie                                   immigratieStatusHis;

    @Column(name = "inschrstatushis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie                                   inschrijvingStatusHis;

    @Embedded
    private PersoonIdentificatienummersGroepModel            identificatienummers;

    @Embedded
    private PersoonGeslachtsaanduidingGroepModel             geslachtsaanduiding;

    @Embedded
    private PersoonSamengesteldeNaamGroepModel               samengesteldeNaam;

    @Embedded
    private PersoonAanschrijvingGroepModel                   aanschrijving;

    @Embedded
    private PersoonGeboorteGroepModel                        geboorte;

    @Embedded
    private PersoonOpschortingGroepModel                     opschorting;

    @Embedded
    private PersoonBijhoudingsgemeenteGroepModel             bijhoudenGemeente;

    @Embedded
    private PersoonOverlijdenGroepModel                      overlijden;

    @Embedded
    private PersoonInschrijvingGroepModel                    inschrijving;

    @Embedded
    private PersoonVerblijfsrechtGroepModel                  verblijfsrecht;

    @Embedded
    private PersoonskaartGroepModel                          persoonskaart;

    @Embedded
    private PersoonImmigratieGroepModel                      immigratie;

    @Embedded
    private PersoonEUVerkiezingenGroepModel                  euVerkiezingen;

    @Embedded
    private PersoonBijhoudingsverantwoordelijkheidGroepModel bijhoudingVerantwoordelijke;

    @Embedded
    private PersoonUitsluitingNLKiesrechtGroepModel          uitsluitingNLKiesrecht;

    @Embedded
    private PersoonAfgeleidAdministratiefGroepModel          afgeleidAdministratief;

    @OneToMany(cascade = { CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.REMOVE },
               fetch = FetchType.LAZY)
    @JoinColumn(name = "pers")
    @OrderBy("id")
    private Set<BetrokkenheidModel>                          betrokkenheden;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "Pers")
    @OrderBy("gegevens.woonplaats, gegevens.naamOpenbareRuimte, gegevens.huisnummer, gegevens.huisletter, gegevens.huisnummertoevoeging")
    private Set<PersoonAdresModel>                           adressen;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "Pers")
    @OrderBy("volgnummer")
    private Set<PersoonVoornaamModel>                        persoonVoornaam;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "Pers")
    @OrderBy("volgnummer")
    private Set<PersoonGeslachtsnaamcomponentModel>          geslachtsnaamcomponenten;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "Pers")
    private Set<PersoonNationaliteitModel>                   nationaliteiten;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "Pers")
    @OrderBy("gegevens.soort")
    private Set<PersoonIndicatieModel>                       indicaties;

    /**
     * Copy constructor. Om een model object te construeren uit een web object.
     *
     * @param persoon Object type dat gekopieerd dient te worden.
     */
    protected AbstractPersoonModel(final PersoonBasis persoon) {
        super(persoon);
        initLegeSets();
        initLegeStatusHistorie();

        soort = persoon.getSoort();

        if (persoon.getIdentificatienummers() != null) {
            identificatienummers = new PersoonIdentificatienummersGroepModel(persoon.getIdentificatienummers());
            identificatienrsStatusHis = StatusHistorie.A;
        }
        if (persoon.getGeslachtsaanduiding() != null) {
            geslachtsaanduiding = new PersoonGeslachtsaanduidingGroepModel(persoon.getGeslachtsaanduiding());
            geslachtsaanduidingStatusHis = StatusHistorie.A;
        }
        if (persoon.getSamengesteldeNaam() != null) {
            samengesteldeNaam = new PersoonSamengesteldeNaamGroepModel(persoon.getSamengesteldeNaam());
            samengesteldeNaamStatusHis = StatusHistorie.A;
        }
        if (persoon.getAanschrijving() != null) {
            aanschrijving = new PersoonAanschrijvingGroepModel(persoon.getAanschrijving());
            aanschrijvingStatusHis = StatusHistorie.A;
        }
        if (persoon.getGeboorte() != null) {
            geboorte = new PersoonGeboorteGroepModel(persoon.getGeboorte());
            geboorteStatusHis = StatusHistorie.A;
        }
        if (persoon.getBijhoudingsgemeente() != null) {
            bijhoudenGemeente = new PersoonBijhoudingsgemeenteGroepModel(persoon.getBijhoudingsgemeente());
            bijhoudingsgemeenteStatusHis = StatusHistorie.A;
        }
        if (persoon.getBijhoudingsverantwoordelijkheid() != null) {
            bijhoudingVerantwoordelijke =
                new PersoonBijhoudingsverantwoordelijkheidGroepModel(persoon.getBijhoudingsverantwoordelijkheid());
            bijhoudingsverantwoordelijkheidStatusHis = StatusHistorie.A;
        }
        if (persoon.getAfgeleidAdministratief() != null) {
            afgeleidAdministratief = new PersoonAfgeleidAdministratiefGroepModel(persoon.getAfgeleidAdministratief());
        }
        if (persoon.getInschrijving() != null) {
            inschrijving = new PersoonInschrijvingGroepModel(persoon.getInschrijving());
            inschrijvingStatusHis = StatusHistorie.A;
        }
        if (persoon.getOpschorting() != null) {
            opschorting = new PersoonOpschortingGroepModel(persoon.getOpschorting());
            opschortingStatusHis = StatusHistorie.A;
        }
        if (persoon.getOverlijden() != null) {
            overlijden = new PersoonOverlijdenGroepModel(persoon.getOverlijden());
            overlijdenStatusHis = StatusHistorie.A;
        }
        if (persoon.getVerblijfsrecht() != null) {
            verblijfsrecht = new PersoonVerblijfsrechtGroepModel(persoon.getVerblijfsrecht());
            verblijfsrechtStatusHis = StatusHistorie.A;
        }
        if (persoon.getPersoonsKaart() != null) {
            persoonskaart = new PersoonskaartGroepModel(persoon.getPersoonsKaart());
            persoonskaartStatusHis = StatusHistorie.A;
        }
        if (persoon.getImmigratie() != null) {
            immigratie = new PersoonImmigratieGroepModel(persoon.getImmigratie());
            immigratieStatusHis = StatusHistorie.A;
        }
        if (persoon.getEUVerkiezingen() != null) {
            euVerkiezingen = new PersoonEUVerkiezingenGroepModel(persoon.getEUVerkiezingen());
            eUVerkiezingenStatusHis = StatusHistorie.A;
        }
        if (persoon.getUitsluitingNLKiesrecht() != null) {
            uitsluitingNLKiesrecht = new PersoonUitsluitingNLKiesrechtGroepModel(persoon.getUitsluitingNLKiesrecht());
            uitsluitingNLKiesrechtStatusHis = StatusHistorie.A;
        }

    }

    /** Default constructor. Vereist voor Hibernate. */
    protected AbstractPersoonModel() {
        initLegeSets();
    }

    /** initieer alle sttaushistories op waarde X. */
    private void initLegeStatusHistorie() {
        geboorteStatusHis = StatusHistorie.X;
        overlijdenStatusHis = StatusHistorie.X;
        samengesteldeNaamStatusHis = StatusHistorie.X;
        aanschrijvingStatusHis = StatusHistorie.X;
        identificatienrsStatusHis = StatusHistorie.X;
        geslachtsaanduidingStatusHis = StatusHistorie.X;
        verblijfsrechtStatusHis = StatusHistorie.X;
        uitsluitingNLKiesrechtStatusHis = StatusHistorie.X;
        eUVerkiezingenStatusHis = StatusHistorie.X;
        bijhoudingsverantwoordelijkheidStatusHis = StatusHistorie.X;
        opschortingStatusHis = StatusHistorie.X;
        bijhoudingsgemeenteStatusHis = StatusHistorie.X;
        persoonskaartStatusHis = StatusHistorie.X;
        immigratieStatusHis = StatusHistorie.X;
        inschrijvingStatusHis = StatusHistorie.X;
    }

    /** initieer de sets zodat we geen null pointers krijgen. */
    private void initLegeSets() {
        betrokkenheden = new HashSet<BetrokkenheidModel>();
        adressen = new HashSet<PersoonAdresModel>();
        persoonVoornaam = new HashSet<PersoonVoornaamModel>();
        geslachtsnaamcomponenten = new HashSet<PersoonGeslachtsnaamcomponentModel>();
        nationaliteiten = new HashSet<PersoonNationaliteitModel>();
        indicaties = new HashSet<PersoonIndicatieModel>();
    }

    /**
     * Vervang een lijst van groepen met nieuwe data.
     *
     * @param groepen de lijst
     */
    public void vervangGroepen(final Groep... groepen) {
        for (Groep groep : groepen) {
            if (groep instanceof PersoonBijhoudingsgemeenteGroepBasis) {
                bijhoudenGemeente =
                    new PersoonBijhoudingsgemeenteGroepModel((PersoonBijhoudingsgemeenteGroepBasis) groep);
                // TODO: wat als deze null is, of wat als dit een 'verval' groep => dan moet de status nders worden.
                bijhoudingsgemeenteStatusHis = StatusHistorie.A;
            } else if (groep instanceof PersoonBijhoudingsverantwoordelijkheidGroepBasis) {
                bijhoudingVerantwoordelijke =
                    new PersoonBijhoudingsverantwoordelijkheidGroepModel(
                            (PersoonBijhoudingsverantwoordelijkheidGroepBasis) groep);
                // TODO: wat als deze null is, of wat als dit een 'verval' groep => dan moet de status nders worden.
                bijhoudingsverantwoordelijkheidStatusHis = StatusHistorie.A;
            } else if (groep instanceof PersoonAanschrijvingGroepBasis) {
                aanschrijving = new PersoonAanschrijvingGroepModel((PersoonAanschrijvingGroepBasis) groep);
                // TODO: wat als deze null is, of wat als dit een 'verval' groep => dan moet de status nders worden.
                aanschrijvingStatusHis = StatusHistorie.A;
            } else if (groep instanceof PersoonOverlijdenGroepBasis) {
                overlijden = new PersoonOverlijdenGroepModel((PersoonOverlijdenGroepBasis) groep);
                overlijdenStatusHis = StatusHistorie.A;
            } else if (groep instanceof PersoonOpschortingGroepBasis) {
                opschorting = new PersoonOpschortingGroepModel((PersoonOpschortingGroepBasis) groep);
                opschortingStatusHis = StatusHistorie.A;
            } else {
                throw new UnsupportedOperationException("Vervang groepen voor de groep ["
                    + groep.getClass().getSimpleName() + "] moet nog toegevoegd worden.");
            }
            // etc ... TODO andere groepen ook implementeren.
        }
    }

    public Integer getId() {
        return id;
    }

    @Override
    public SoortPersoon getSoort() {
        return soort;
    }

    @Override
    public PersoonIdentificatienummersGroepModel getIdentificatienummers() {
        return identificatienummers;
    }

    @Override
    public PersoonGeslachtsaanduidingGroepModel getGeslachtsaanduiding() {
        return geslachtsaanduiding;
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
    public PersoonBijhoudingsverantwoordelijkheidGroepModel getBijhoudingsverantwoordelijkheid() {
        return bijhoudingVerantwoordelijke;
    }

    @Override
    public PersoonOpschortingGroepModel getOpschorting() {
        return opschorting;
    }

    @Override
    public PersoonBijhoudingsgemeenteGroepModel getBijhoudingsgemeente() {
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
    public PersoonOverlijdenGroepModel getOverlijden() {
        return overlijden;
    }

    @Override
    public PersoonVerblijfsrechtGroepModel getVerblijfsrecht() {
        return verblijfsrecht;
    }

    @Override
    public PersoonUitsluitingNLKiesrechtGroepModel getUitsluitingNLKiesrecht() {
        return uitsluitingNLKiesrecht;
    }

    @Override
    public PersoonEUVerkiezingenGroepModel getEUVerkiezingen() {
        return euVerkiezingen;
    }

    @Override
    public PersoonskaartGroepModel getPersoonsKaart() {
        return persoonskaart;
    }

    @Override
    public PersoonImmigratieGroepModel getImmigratie() {
        return immigratie;
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
    public Set<PersoonGeslachtsnaamcomponentModel> getGeslachtsnaamcomponenten() {
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

    protected void setPersoonVoornaam(final Set<PersoonVoornaamModel> persoonVoornaam) {
        this.persoonVoornaam = persoonVoornaam;
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
