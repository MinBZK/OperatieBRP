/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.objecttype.impl.gen;

import nl.bzk.brp.model.groep.interfaces.usr.PersoonAanschrijvingGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonAfgeleidAdministratiefGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonBijhoudingsGemeenteGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonBijhoudingsVerantwoordelijkheidGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonEUVerkiezingenGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonGeboorteGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonGeslachtsAanduidingGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonIdentificatieNummersGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonImmigratieGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonInschrijvingGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonOpschortingGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonOverlijdenGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonSamengesteldeNaamGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonUitsluitingNLKiesrechtGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonVerblijfsrechtGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonskaartGroep;
import nl.bzk.brp.model.objecttype.interfaces.gen.PersoonBasis;
import nl.bzk.brp.model.objecttype.statisch.SoortPersoon;
import nl.bzk.brp.web.AbstractObjectTypeWeb;

/**
 * De eerste laag implemenetatie van {@link}Persoon interface.
 * Deze class is een onderdeel van de model tree.
 * Deze implementatie wordt door de genrator gegenereerd.
 *
 */
public abstract class AbstractPersoonWeb extends AbstractObjectTypeWeb implements PersoonBasis {

    private SoortPersoon soort;
    private PersoonIdentificatieNummersGroep identificatieNummers;
    private PersoonGeslachtsAanduidingGroep geslachtsAanduiding;
    private PersoonSamengesteldeNaamGroep samengesteldeNaam;
    private PersoonAanschrijvingGroep aanschrijving;
    private PersoonGeboorteGroep geboorte;
    private PersoonOverlijdenGroep overlijden;
    private PersoonVerblijfsrechtGroep verblijfsrecht;
    private PersoonUitsluitingNLKiesrechtGroep uitsluitingNLKiesrecht;
    private PersoonEUVerkiezingenGroep eUVerkiezingenVerkiezingen;
    private PersoonBijhoudingsVerantwoordelijkheidGroep bijhoudingVerantwoordelijke;
    private PersoonOpschortingGroep opschorting;
    private PersoonBijhoudingsGemeenteGroep bijhoudenGemeente;
    private PersoonskaartGroep persoonsKaart;
    private PersoonImmigratieGroep immigratie;
    private PersoonInschrijvingGroep inschrijving;
    private PersoonAfgeleidAdministratiefGroep afgeleidAdministratief;


    @Override
    public SoortPersoon getSoort() {
        return soort;
    }

    @Override
    public PersoonIdentificatieNummersGroep getIdentificatieNummers() {
        return identificatieNummers;
    }

    @Override
    public PersoonGeslachtsAanduidingGroep getGeslachtsAanduiding() {
        return geslachtsAanduiding;
    }

    @Override
    public PersoonSamengesteldeNaamGroep getSamengesteldeNaam() {
        return samengesteldeNaam;
    }

    @Override
    public PersoonAanschrijvingGroep getAanschrijving() {
        return aanschrijving;
    }

    @Override
    public PersoonGeboorteGroep getGeboorte() {
        return geboorte;
    }

    @Override
    public PersoonOverlijdenGroep getOverlijden() {
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
        return eUVerkiezingenVerkiezingen;
    }

    @Override
    public PersoonBijhoudingsVerantwoordelijkheidGroep getBijhoudingVerantwoordelijke() {
        return bijhoudingVerantwoordelijke;
    }

    @Override
    public PersoonOpschortingGroep getOpschorting() {
        return opschorting;
    }

    @Override
    public PersoonBijhoudingsGemeenteGroep getBijhoudenGemeente() {
        return bijhoudenGemeente;
    }

    @Override
    public PersoonskaartGroep getPersoonsKaart() {
        return persoonsKaart;
    }

    @Override
    public PersoonImmigratieGroep getImmigratie() {
        return immigratie;
    }

    @Override
    public PersoonInschrijvingGroep getInschrijving() {
        return inschrijving;
    }

    @Override
    public PersoonAfgeleidAdministratiefGroep getAfgeleidAdministratief() {
        return afgeleidAdministratief;
    }

}
