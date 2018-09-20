/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.bericht.basis;

import java.util.List;

import javax.validation.Valid;

import nl.bzk.brp.model.basis.AbstractObjectTypeBericht;
import nl.bzk.brp.model.groep.bericht.PersoonAanschrijvingGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonAfgeleidAdministratiefGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonBijhoudingsGemeenteGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonBijhoudingsVerantwoordelijkheidGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonEUVerkiezingenGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonGeslachtsAanduidingGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonIdentificatieNummersGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonImmigratieGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonInschrijvingGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonOpschortingGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonOverlijdenGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonSamengesteldeNaamGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonUitsluitingNLKiesrechtGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonVerblijfsrechtGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonskaartGroepBericht;
import nl.bzk.brp.model.groep.logisch.PersoonEUVerkiezingenGroep;
import nl.bzk.brp.model.groep.logisch.PersoonImmigratieGroep;
import nl.bzk.brp.model.groep.logisch.PersoonUitsluitingNLKiesrechtGroep;
import nl.bzk.brp.model.groep.logisch.PersoonVerblijfsrechtGroep;
import nl.bzk.brp.model.groep.logisch.PersoonskaartGroep;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonAdresBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonGeslachtsnaamComponentBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonNationaliteitBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonVoornaamBericht;
import nl.bzk.brp.model.objecttype.logisch.PersoonIndicatie;
import nl.bzk.brp.model.objecttype.logisch.basis.PersoonBasis;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortPersoon;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.constraint.CollectieMetUniekeWaarden;


/**
 * De eerste laag implemenetatie van {@link}Persoon interface.
 * Deze class is een onderdeel van de model tree.
 * Deze implementatie wordt door de genrator gegenereerd.
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractPersoonBericht extends AbstractObjectTypeBericht implements PersoonBasis {

    private SoortPersoon                                   soort;
    @Valid
    private PersoonIdentificatieNummersGroepBericht identificatieNummers;
    private PersoonGeslachtsAanduidingGroepBericht geslachtsAanduiding;
    private PersoonSamengesteldeNaamGroepBericht samengesteldeNaam;
    private PersoonAanschrijvingGroepBericht aanschrijving;
    @Valid
    private PersoonGeboorteGroepBericht geboorte;
    private PersoonBijhoudingsVerantwoordelijkheidGroepBericht bijhoudingVerantwoordelijke;
    private PersoonOpschortingGroepBericht opschorting;
    private PersoonBijhoudingsGemeenteGroepBericht bijhoudenGemeente;
    private PersoonInschrijvingGroepBericht inschrijving;
    @Valid
    private PersoonAfgeleidAdministratiefGroepBericht afgeleidAdministratief;
    private PersoonOverlijdenGroepBericht overlijden;
    private PersoonImmigratieGroepBericht immigratie;
    private PersoonEUVerkiezingenGroepBericht euVerkiezingen;
    private PersoonUitsluitingNLKiesrechtGroepBericht uitsluitingNLKiesrecht;
    private PersoonVerblijfsrechtGroepBericht   verblijfsrecht;
    private PersoonskaartGroepBericht   persoonskaart;

    private List<BetrokkenheidBericht>                          betrokkenheden;
    @Valid
    private List<PersoonAdresBericht>                           adressen;

    @CollectieMetUniekeWaarden(code = MeldingCode.INC001)
    private List<PersoonVoornaamBericht>                        persoonVoornaam;

    @CollectieMetUniekeWaarden(code = MeldingCode.INC002)
    private List<PersoonGeslachtsnaamComponentBericht>          geslachtsnaamcomponenten;

    @CollectieMetUniekeWaarden(code = MeldingCode.INC003)
    private List<PersoonNationaliteitBericht>                   nationaliteiten;

    @Override
    public SoortPersoon getSoort() {
        return soort;
    }

    @Override
    public PersoonIdentificatieNummersGroepBericht getIdentificatieNummers() {
        return identificatieNummers;
    }

    @Override
    public PersoonGeslachtsAanduidingGroepBericht getGeslachtsAanduiding() {
        return geslachtsAanduiding;
    }

    @Override
    public PersoonSamengesteldeNaamGroepBericht getSamengesteldeNaam() {
        return samengesteldeNaam;
    }

    @Override
    public PersoonAanschrijvingGroepBericht getAanschrijving() {
        return aanschrijving;
    }

    @Override
    public PersoonGeboorteGroepBericht getGeboorte() {
        return geboorte;
    }

    @Override
    public PersoonOverlijdenGroepBericht getOverlijden() {
        return overlijden;
    }

    @Override
    public PersoonVerblijfsrechtGroep getVerblijfsrecht() {
        return verblijfsrecht;
    }

    @Override
    public PersoonUitsluitingNLKiesrechtGroep getUitsluitingNLKiesrecht() {
        return uitsluitingNLKiesrecht;
    }

    @Override
    public PersoonEUVerkiezingenGroep getEUVerkiezingen() {
        return euVerkiezingen;
    }

    @Override
    public PersoonBijhoudingsVerantwoordelijkheidGroepBericht getBijhoudingVerantwoordelijke() {
        return bijhoudingVerantwoordelijke;
    }

    @Override
    public PersoonOpschortingGroepBericht getOpschorting() {
        return opschorting;
    }

    @Override
    public PersoonBijhoudingsGemeenteGroepBericht getBijhoudenGemeente() {
        return bijhoudenGemeente;
    }

    @Override
    public PersoonskaartGroep getPersoonsKaart() {
        return persoonskaart;
    }

    @Override
    public PersoonImmigratieGroep getImmigratie() {
        return immigratie;
    }

    @Override
    public PersoonInschrijvingGroepBericht getInschrijving() {
        return inschrijving;
    }

    @Override
    public PersoonAfgeleidAdministratiefGroepBericht getAfgeleidAdministratief() {
        return afgeleidAdministratief;
    }

    @Override
    public List<BetrokkenheidBericht> getBetrokkenheden() {
        return betrokkenheden;
    }

    @Override
    public List<PersoonAdresBericht> getAdressen() {
        return adressen;
    }

    @Override
    public List<PersoonVoornaamBericht> getPersoonVoornaam() {
        return persoonVoornaam;
    }

    @Override
    public List<PersoonGeslachtsnaamComponentBericht> getGeslachtsnaamcomponenten() {
        return geslachtsnaamcomponenten;
    }

    @Override
    public List<PersoonNationaliteitBericht> getNationaliteiten() {
        return nationaliteiten;
    }

    @Override
    public List<? extends PersoonIndicatie> getIndicaties() {
        throw new UnsupportedOperationException();
    }

    public void setSoort(final SoortPersoon soort) {
        this.soort = soort;
    }

    public void setIdentificatieNummers(final PersoonIdentificatieNummersGroepBericht identificatieNummers) {
        this.identificatieNummers = identificatieNummers;
    }

    public void setGeslachtsAanduiding(final PersoonGeslachtsAanduidingGroepBericht geslachtsAanduiding) {
        this.geslachtsAanduiding = geslachtsAanduiding;
    }

    public void setSamengesteldeNaam(final PersoonSamengesteldeNaamGroepBericht samengesteldeNaam) {
        this.samengesteldeNaam = samengesteldeNaam;
    }

    public void setAanschrijving(final PersoonAanschrijvingGroepBericht aanschrijving) {
        this.aanschrijving = aanschrijving;
    }

    public void setGeboorte(final PersoonGeboorteGroepBericht geboorte) {
        this.geboorte = geboorte;
    }

    public void setBijhoudingVerantwoordelijke(
            final PersoonBijhoudingsVerantwoordelijkheidGroepBericht bijhoudingVerantwoordelijke)
    {
        this.bijhoudingVerantwoordelijke = bijhoudingVerantwoordelijke;
    }

    public void setOpschorting(final PersoonOpschortingGroepBericht opschorting) {
        this.opschorting = opschorting;
    }

    public void setBijhoudenGemeente(final PersoonBijhoudingsGemeenteGroepBericht bijhoudenGemeente) {
        this.bijhoudenGemeente = bijhoudenGemeente;
    }

    public void setInschrijving(final PersoonInschrijvingGroepBericht inschrijving) {
        this.inschrijving = inschrijving;
    }

    public void setAfgeleidAdministratief(final PersoonAfgeleidAdministratiefGroepBericht afgeleidAdministratief) {
        this.afgeleidAdministratief = afgeleidAdministratief;
    }

    public void setBetrokkenheden(final List<BetrokkenheidBericht> betrokkenheden) {
        this.betrokkenheden = betrokkenheden;
    }

    public void setAdressen(final List<PersoonAdresBericht> adressen) {
        this.adressen = adressen;
    }

    public void setPersoonVoornaam(final List<PersoonVoornaamBericht> persoonVoornaam) {
        this.persoonVoornaam = persoonVoornaam;
    }

    public void setGeslachtsnaamcomponenten(final List<PersoonGeslachtsnaamComponentBericht> geslachtsnaamcomponenten) {
        this.geslachtsnaamcomponenten = geslachtsnaamcomponenten;
    }

    public void setNationaliteiten(final List<PersoonNationaliteitBericht> nationaliteiten) {
        this.nationaliteiten = nationaliteiten;
    }

    public PersoonEUVerkiezingenGroepBericht getEuVerkiezingen() {
        return euVerkiezingen;
    }

    public void setEuVerkiezingen(final PersoonEUVerkiezingenGroepBericht euVerkiezingen) {
        this.euVerkiezingen = euVerkiezingen;
    }

    public void setUitsluitingNLKiesrecht(final PersoonUitsluitingNLKiesrechtGroepBericht uitsluitingNLKiesrecht) {
        this.uitsluitingNLKiesrecht = uitsluitingNLKiesrecht;
    }

    public void setVerblijfsrecht(final PersoonVerblijfsrechtGroepBericht verblijfsrecht) {
        this.verblijfsrecht = verblijfsrecht;
    }

    public void setImmigratie(final PersoonImmigratieGroepBericht immigratie) {
        this.immigratie = immigratie;
    }
}
