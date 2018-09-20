/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.web.objecttype.impl.gen;

import java.util.Set;

import nl.bzk.brp.model.groep.interfaces.usr.PersoonEUVerkiezingenGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonImmigratieGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonOverlijdenGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonUitsluitingNLKiesrechtGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonVerblijfsrechtGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonskaartGroep;
import nl.bzk.brp.model.objecttype.interfaces.gen.PersoonBasis;
import nl.bzk.brp.model.objecttype.interfaces.usr.PersoonIndicatie;
import nl.bzk.brp.model.objecttype.interfaces.usr.PersoonVoornaam;
import nl.bzk.brp.model.objecttype.statisch.SoortPersoon;
import nl.bzk.brp.model.web.AbstractObjectTypeWeb;
import nl.bzk.brp.model.web.groep.impl.usr.PersoonAanschrijvingGroepWeb;
import nl.bzk.brp.model.web.groep.impl.usr.PersoonAfgeleidAdministratiefGroepWeb;
import nl.bzk.brp.model.web.groep.impl.usr.PersoonBijhoudingsGemeenteGroepWeb;
import nl.bzk.brp.model.web.groep.impl.usr.PersoonBijhoudingsVerantwoordelijkheidGroepWeb;
import nl.bzk.brp.model.web.groep.impl.usr.PersoonGeboorteGroepWeb;
import nl.bzk.brp.model.web.groep.impl.usr.PersoonGeslachtsAanduidingGroepWeb;
import nl.bzk.brp.model.web.groep.impl.usr.PersoonIdentificatieNummersGroepWeb;
import nl.bzk.brp.model.web.groep.impl.usr.PersoonInschrijvingGroepWeb;
import nl.bzk.brp.model.web.groep.impl.usr.PersoonOpschortingGroepWeb;
import nl.bzk.brp.model.web.groep.impl.usr.PersoonSamengesteldeNaamGroepWeb;
import nl.bzk.brp.model.web.objecttype.impl.usr.BetrokkenheidWeb;
import nl.bzk.brp.model.web.objecttype.impl.usr.PersoonAdresWeb;
import nl.bzk.brp.model.web.objecttype.impl.usr.PersoonGeslachtsnaamComponentWeb;
import nl.bzk.brp.model.web.objecttype.impl.usr.PersoonNationaliteitWeb;

/**
 * De eerste laag implemenetatie van {@link}Persoon interface.
 * Deze class is een onderdeel van de model tree.
 * Deze implementatie wordt door de genrator gegenereerd.
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractPersoonWeb extends AbstractObjectTypeWeb implements PersoonBasis {

    private SoortPersoon soort;
    private PersoonIdentificatieNummersGroepWeb identificatieNummers;
    private PersoonGeslachtsAanduidingGroepWeb geslachtsAanduiding;
    private PersoonSamengesteldeNaamGroepWeb samengesteldeNaam;
    private PersoonAanschrijvingGroepWeb aanschrijving;
    private PersoonGeboorteGroepWeb geboorte;
    private PersoonBijhoudingsVerantwoordelijkheidGroepWeb bijhoudingVerantwoordelijke;
    private PersoonOpschortingGroepWeb opschorting;
    private PersoonBijhoudingsGemeenteGroepWeb bijhoudenGemeente;
    private PersoonInschrijvingGroepWeb inschrijving;
    private PersoonAfgeleidAdministratiefGroepWeb afgeleidAdministratief;
    private Set<BetrokkenheidWeb> betrokkenheden;
    private Set<PersoonAdresWeb> adressen;
    private Set<PersoonVoornaam> persoonVoornaam;
    private Set<PersoonGeslachtsnaamComponentWeb> geslachtsnaamcomponenten;
    private Set<PersoonNationaliteitWeb> nationaliteiten;


    @Override
    public SoortPersoon getSoort() {
        return soort;
    }

    @Override
    public PersoonIdentificatieNummersGroepWeb getIdentificatieNummers() {
        return identificatieNummers;
    }

    @Override
    public PersoonGeslachtsAanduidingGroepWeb getGeslachtsAanduiding() {
        return geslachtsAanduiding;
    }

    @Override
    public PersoonSamengesteldeNaamGroepWeb getSamengesteldeNaam() {
        return samengesteldeNaam;
    }

    @Override
    public PersoonAanschrijvingGroepWeb getAanschrijving() {
        return aanschrijving;
    }

    @Override
    public PersoonGeboorteGroepWeb getGeboorte() {
        return geboorte;
    }

    @Override
    public PersoonOverlijdenGroep getOverlijden() {
        throw new UnsupportedOperationException("PersoonOverlijdenGroepWeb is nog niet gebouwd");
    }

    @Override
    public PersoonVerblijfsrechtGroep getVerblijfsrecht() {
        throw new UnsupportedOperationException("PersoonVerblijfsrechtGroepWeb is nog niet gebouwd");
    }

    @Override
    public PersoonUitsluitingNLKiesrechtGroep getUitsluitingNLKiesrecht() {
        throw new UnsupportedOperationException("PersoonUitsluitingNLKiesrechtGroepWeb is nog niet gebouwd");
    }

    @Override
    public PersoonEUVerkiezingenGroep getEUVerkiezingen() {
        throw new UnsupportedOperationException("PersoonEUVerkiezingenGroepWeb is nog niet gebouwd");
    }

    @Override
    public PersoonBijhoudingsVerantwoordelijkheidGroepWeb getBijhoudingVerantwoordelijke() {
        return bijhoudingVerantwoordelijke;
    }

    @Override
    public PersoonOpschortingGroepWeb getOpschorting() {
        return opschorting;
    }

    @Override
    public PersoonBijhoudingsGemeenteGroepWeb getBijhoudenGemeente() {
        return bijhoudenGemeente;
    }

    @Override
    public PersoonskaartGroep getPersoonsKaart() {
        throw new UnsupportedOperationException("PersoonskaartGroepWeb is nog niet gebouwd");
    }

    @Override
    public PersoonImmigratieGroep getImmigratie() {
        throw new UnsupportedOperationException("PersoonImmigratieGroepWeb is nog niet gebouwd");
    }

    @Override
    public PersoonInschrijvingGroepWeb getInschrijving() {
        return inschrijving;
    }

    @Override
    public PersoonAfgeleidAdministratiefGroepWeb getAfgeleidAdministratief() {
        return afgeleidAdministratief;
    }

    @Override
    public Set<BetrokkenheidWeb> getBetrokkenheden() {
        return betrokkenheden;
    }

    @Override
    public Set<PersoonAdresWeb> getAdressen() {
        return adressen;
    }

    @Override
    public Set<PersoonVoornaam> getPersoonVoornaam() {
        return persoonVoornaam;
    }

    @Override
    public Set<PersoonGeslachtsnaamComponentWeb> getGeslachtsnaamcomponenten() {
        return geslachtsnaamcomponenten;
    }

    @Override
    public Set<PersoonNationaliteitWeb> getNationaliteiten() {
        return nationaliteiten;
    }

    @Override
    public Set<? extends PersoonIndicatie> getIndicaties() {
        throw new UnsupportedOperationException();
    }

}
