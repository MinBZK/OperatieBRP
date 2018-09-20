/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.impl.gen;

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
import nl.bzk.brp.model.groep.impl.usr.PersoonAanschrijvingGroepMdl;
import nl.bzk.brp.model.groep.impl.usr.PersoonAfgeleidAdministratiefGroepMdl;
import nl.bzk.brp.model.groep.impl.usr.PersoonBijhoudingsGemeenteGroepMdl;
import nl.bzk.brp.model.groep.impl.usr.PersoonBijhoudingsVerantwoordelijkheidGroepMdl;
import nl.bzk.brp.model.groep.impl.usr.PersoonGeboorteGroepMdl;
import nl.bzk.brp.model.groep.impl.usr.PersoonGeslachtsAanduidingGroepMdl;
import nl.bzk.brp.model.groep.impl.usr.PersoonIdentificatieNummersGroepMdl;
import nl.bzk.brp.model.groep.impl.usr.PersoonInschrijvingGroepMdl;
import nl.bzk.brp.model.groep.impl.usr.PersoonOpschortingGroepMdl;
import nl.bzk.brp.model.groep.impl.usr.PersoonOverlijdenGroepMdl;
import nl.bzk.brp.model.groep.impl.usr.PersoonSamengesteldeNaamGroepMdl;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonEUVerkiezingenGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonImmigratieGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonOverlijdenGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonUitsluitingNLKiesrechtGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonVerblijfsrechtGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonskaartGroep;
import nl.bzk.brp.model.objecttype.impl.usr.BetrokkenheidMdl;
import nl.bzk.brp.model.objecttype.impl.usr.PersoonAdresMdl;
import nl.bzk.brp.model.objecttype.impl.usr.PersoonGeslachtsnaamComponentMdl;
import nl.bzk.brp.model.objecttype.impl.usr.PersoonIndicatieMdl;
import nl.bzk.brp.model.objecttype.impl.usr.PersoonNationaliteitMdl;
import nl.bzk.brp.model.objecttype.impl.usr.PersoonVoornaamMdl;
import nl.bzk.brp.model.objecttype.interfaces.gen.PersoonBasis;
import nl.bzk.brp.model.objecttype.statisch.SoortPersoon;


/**
 * De eerste laag implemenetatie van {@link}Persoon interface.
 * Deze class is een onderdeel van de model tree.
 * Deze implementatie wordt door de genrator gegenereerd.
 *
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonMdl extends AbstractDynamischObjectType implements PersoonBasis {

    @Id
    @SequenceGenerator(name = "seq_Pers", sequenceName = "Kern.seq_Pers")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Pers")
    private Long                                           id;

    @Column(name = "srt")
    @Enumerated
    @NotNull
    private SoortPersoon                                   soort;

    @Embedded
    @NotNull
    private PersoonIdentificatieNummersGroepMdl            identificatieNummers;

    @Embedded
    @NotNull
    private PersoonGeslachtsAanduidingGroepMdl             geslachtsAanduiding;

    @Embedded
    @NotNull
    private PersoonSamengesteldeNaamGroepMdl               samengesteldeNaam;

    @Embedded
    @NotNull
    private PersoonAanschrijvingGroepMdl                   aanschrijving;

    @Embedded
    @NotNull
    private PersoonGeboorteGroepMdl                        geboorte;

    @Embedded
    @NotNull
    private PersoonOpschortingGroepMdl                     opschorting;

    @Embedded
    @NotNull
    private PersoonBijhoudingsGemeenteGroepMdl             bijhoudenGemeente;

    @Embedded
    @NotNull
    private PersoonOverlijdenGroepMdl                      overlijden;

    @Embedded
    @NotNull
    private PersoonInschrijvingGroepMdl                    inschrijving;

    @Embedded
    @NotNull
    private PersoonBijhoudingsVerantwoordelijkheidGroepMdl bijhoudingVerantwoordelijke;

    @Embedded
    @NotNull
    private PersoonAfgeleidAdministratiefGroepMdl          afgeleidAdministratief;

    @OneToMany(cascade = { CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.REMOVE }, fetch = FetchType.EAGER)
    @JoinColumn(name = "betrokkene")
    private Set<BetrokkenheidMdl>                         betrokkenheden;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "Pers")
    private Set<PersoonAdresMdl>                          adressen;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "Pers")
    private Set<PersoonVoornaamMdl>                       persoonVoornaam;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "Pers")
    private Set<PersoonGeslachtsnaamComponentMdl>         geslachtsnaamcomponenten;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "Pers")
    private Set<PersoonNationaliteitMdl>                  nationaliteiten;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "Pers")
    private Set<PersoonIndicatieMdl>                      indicaties;

    public Long getId() {
        return id;
    }

    @Override
    public SoortPersoon getSoort() {
        return soort;
    }

    @Override
    public PersoonIdentificatieNummersGroepMdl getIdentificatieNummers() {
        return identificatieNummers;
    }

    @Override
    public PersoonGeslachtsAanduidingGroepMdl getGeslachtsAanduiding() {
        return geslachtsAanduiding;
    }

    @Override
    public PersoonSamengesteldeNaamGroepMdl getSamengesteldeNaam() {
        return samengesteldeNaam;
    }

    @Override
    public PersoonAanschrijvingGroepMdl getAanschrijving() {
        return aanschrijving;
    }

    @Override
    public PersoonGeboorteGroepMdl getGeboorte() {
        return geboorte;
    }

    @Override
    public PersoonBijhoudingsVerantwoordelijkheidGroepMdl getBijhoudingVerantwoordelijke() {
        return bijhoudingVerantwoordelijke;
    }

    @Override
    public PersoonOpschortingGroepMdl getOpschorting() {
        return opschorting;
    }

    @Override
    public PersoonBijhoudingsGemeenteGroepMdl getBijhoudenGemeente() {
        return bijhoudenGemeente;
    }

    @Override
    public PersoonInschrijvingGroepMdl getInschrijving() {
        return inschrijving;
    }

    @Override
    public PersoonAfgeleidAdministratiefGroepMdl getAfgeleidAdministratief() {
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
    public Set<BetrokkenheidMdl> getBetrokkenheden() {
        return betrokkenheden;
    }

    @Override
    public Set<PersoonAdresMdl> getAdressen() {
        return adressen;
    }

    @Override
    public Set<PersoonVoornaamMdl> getPersoonVoornaam() {
        return persoonVoornaam;
    }

    @Override
    public Set<PersoonGeslachtsnaamComponentMdl> getGeslachtsnaamcomponenten() {
        return geslachtsnaamcomponenten;
    }

    @Override
    public Set<PersoonNationaliteitMdl> getNationaliteiten() {
        return nationaliteiten;
    }

    @Override
    public Set<PersoonIndicatieMdl> getIndicaties() {
        return indicaties;
    }

    protected void setSoort(final SoortPersoon soort) {
        this.soort = soort;
    }

    protected void setIdentificatieNummers(final PersoonIdentificatieNummersGroepMdl identificatieNummers) {
        this.identificatieNummers = identificatieNummers;
    }

    protected void setGeslachtsAanduiding(final PersoonGeslachtsAanduidingGroepMdl geslachtsAanduiding) {
        this.geslachtsAanduiding = geslachtsAanduiding;
    }

    protected void setSamengesteldeNaam(final PersoonSamengesteldeNaamGroepMdl samengesteldeNaam) {
        this.samengesteldeNaam = samengesteldeNaam;
    }

    protected void setAanschrijving(final PersoonAanschrijvingGroepMdl aanschrijving) {
        this.aanschrijving = aanschrijving;
    }

    protected void setGeboorte(final PersoonGeboorteGroepMdl geboorte) {
        this.geboorte = geboorte;
    }

    protected void setOpschorting(final PersoonOpschortingGroepMdl opschorting) {
        this.opschorting = opschorting;
    }

    protected void setBijhoudenGemeente(final PersoonBijhoudingsGemeenteGroepMdl bijhoudenGemeente) {
        this.bijhoudenGemeente = bijhoudenGemeente;
    }

    protected void setOverlijden(final PersoonOverlijdenGroepMdl overlijden) {
        this.overlijden = overlijden;
    }

    protected void setInschrijving(final PersoonInschrijvingGroepMdl inschrijving) {
        this.inschrijving = inschrijving;
    }

    protected void setBijhoudingVerantwoordelijke(final PersoonBijhoudingsVerantwoordelijkheidGroepMdl bijhoudingVerantwoordelijke) {
        this.bijhoudingVerantwoordelijke = bijhoudingVerantwoordelijke;
    }

    protected void setAfgeleidAdministratief(final PersoonAfgeleidAdministratiefGroepMdl afgeleidAdministratief) {
        this.afgeleidAdministratief = afgeleidAdministratief;
    }

    protected void setBetrokkenheden(final Set<BetrokkenheidMdl> betrokkenheden) {
        this.betrokkenheden = betrokkenheden;
    }

    protected void setAdressen(final Set<PersoonAdresMdl> adressen) {
        this.adressen = adressen;
    }

    protected void setPersoonVoornaam(final Set<PersoonVoornaamMdl> persoonVoornaam) {
        this.persoonVoornaam = persoonVoornaam;
    }

    protected void setGeslachtsnaamcomponenten(final Set<PersoonGeslachtsnaamComponentMdl> geslachtsnaamcomponenten) {
        this.geslachtsnaamcomponenten = geslachtsnaamcomponenten;
    }

    protected void setNationaliteiten(final Set<PersoonNationaliteitMdl> nationaliteiten) {
        this.nationaliteiten = nationaliteiten;
    }

    protected void setIndicaties(final Set<PersoonIndicatieMdl> indicaties) {
        this.indicaties = indicaties;
    }
}
