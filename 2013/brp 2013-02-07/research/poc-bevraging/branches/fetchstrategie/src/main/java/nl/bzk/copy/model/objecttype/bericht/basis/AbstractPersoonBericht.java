/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.objecttype.bericht.basis;

import java.util.List;

import javax.validation.Valid;

import nl.bzk.copy.model.basis.AbstractObjectTypeBericht;
import nl.bzk.copy.model.groep.bericht.*;
import nl.bzk.copy.model.groep.logisch.*;
import nl.bzk.copy.model.objecttype.bericht.*;
import nl.bzk.copy.model.objecttype.logisch.PersoonIndicatie;
import nl.bzk.copy.model.objecttype.logisch.basis.PersoonBasis;
import nl.bzk.copy.model.objecttype.operationeel.statisch.SoortPersoon;
import nl.bzk.copy.model.validatie.MeldingCode;
import nl.bzk.copy.model.validatie.constraint.CollectieMetUniekeWaarden;


/**
 * De eerste laag implemenetatie van {@link}Persoon interface.
 * Deze class is een onderdeel van de model tree.
 * Deze implementatie wordt door de genrator gegenereerd.
 */
@SuppressWarnings("serial")
public abstract class AbstractPersoonBericht extends AbstractObjectTypeBericht implements PersoonBasis {

    private SoortPersoon soort;
    @Valid
    private PersoonIdentificatienummersGroepBericht identificatienummers;
    private PersoonGeslachtsaanduidingGroepBericht geslachtsaanduiding;
    private PersoonSamengesteldeNaamGroepBericht samengesteldeNaam;
    private PersoonAanschrijvingGroepBericht aanschrijving;
    @Valid
    private PersoonGeboorteGroepBericht geboorte;
    private PersoonBijhoudingsverantwoordelijkheidGroepBericht bijhoudingsverantwoordelijkheid;
    private PersoonOpschortingGroepBericht opschorting;
    private PersoonBijhoudingsgemeenteGroepBericht bijhoudingsgemeente;
    private PersoonInschrijvingGroepBericht inschrijving;
    @Valid
    private PersoonAfgeleidAdministratiefGroepBericht afgeleidAdministratief;
    private PersoonOverlijdenGroepBericht overlijden;
    private PersoonImmigratieGroepBericht immigratie;
    private PersoonEUVerkiezingenGroepBericht euVerkiezingen;
    private PersoonUitsluitingNLKiesrechtGroepBericht uitsluitingNLKiesrecht;
    private PersoonVerblijfsrechtGroepBericht verblijfsrecht;
    private PersoonskaartGroepBericht persoonskaart;

    private List<BetrokkenheidBericht> betrokkenheden;
    @Valid
    private List<PersoonAdresBericht> adressen;

    @CollectieMetUniekeWaarden(code = MeldingCode.INC001)
    private List<PersoonVoornaamBericht> persoonVoornaam;

    @CollectieMetUniekeWaarden(code = MeldingCode.INC002)
    private List<PersoonGeslachtsnaamcomponentBericht> geslachtsnaamcomponenten;

    @CollectieMetUniekeWaarden(code = MeldingCode.INC003)
    private List<PersoonNationaliteitBericht> nationaliteiten;

    @Override
    public SoortPersoon getSoort() {
        return soort;
    }

    @Override
    public PersoonIdentificatienummersGroepBericht getIdentificatienummers() {
        return identificatienummers;
    }

    @Override
    public PersoonGeslachtsaanduidingGroepBericht getGeslachtsaanduiding() {
        return geslachtsaanduiding;
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
    public PersoonBijhoudingsverantwoordelijkheidGroepBericht getBijhoudingsverantwoordelijkheid() {
        return bijhoudingsverantwoordelijkheid;
    }

    @Override
    public PersoonOpschortingGroepBericht getOpschorting() {
        return opschorting;
    }

    @Override
    public PersoonBijhoudingsgemeenteGroepBericht getBijhoudingsgemeente() {
        return bijhoudingsgemeente;
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
    public List<PersoonGeslachtsnaamcomponentBericht> getGeslachtsnaamcomponenten() {
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

    public void setIdentificatienummers(final PersoonIdentificatienummersGroepBericht identificatienummers) {
        this.identificatienummers = identificatienummers;
    }

    public void setGeslachtsaanduiding(final PersoonGeslachtsaanduidingGroepBericht geslachtsaanduiding) {
        this.geslachtsaanduiding = geslachtsaanduiding;
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

    public void setOverlijden(final PersoonOverlijdenGroepBericht overlijden) {
        this.overlijden = overlijden;
    }

    public void setBijhoudingsverantwoordelijkheid(
            final PersoonBijhoudingsverantwoordelijkheidGroepBericht bijhoudingsverantwoordelijkheid)
    {
        this.bijhoudingsverantwoordelijkheid = bijhoudingsverantwoordelijkheid;
    }

    public void setOpschorting(final PersoonOpschortingGroepBericht opschorting) {
        this.opschorting = opschorting;
    }

    public void setBijhoudingsgemeente(final PersoonBijhoudingsgemeenteGroepBericht bijhoudingsgemeente) {
        this.bijhoudingsgemeente = bijhoudingsgemeente;
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

    public void setGeslachtsnaamcomponenten(final List<PersoonGeslachtsnaamcomponentBericht> geslachtsnaamcomponenten) {
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
