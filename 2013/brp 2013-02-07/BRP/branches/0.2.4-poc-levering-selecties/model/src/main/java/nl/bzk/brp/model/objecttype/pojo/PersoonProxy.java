/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.pojo;

import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.groep.logisch.PersoonAanschrijvingGroep;
import nl.bzk.brp.model.groep.logisch.PersoonAfgeleidAdministratiefGroep;
import nl.bzk.brp.model.groep.logisch.PersoonBijhoudingsgemeenteGroep;
import nl.bzk.brp.model.groep.logisch.PersoonBijhoudingsverantwoordelijkheidGroep;
import nl.bzk.brp.model.groep.logisch.PersoonEUVerkiezingenGroep;
import nl.bzk.brp.model.groep.logisch.PersoonGeboorteGroep;
import nl.bzk.brp.model.groep.logisch.PersoonGeslachtsaanduidingGroep;
import nl.bzk.brp.model.groep.logisch.PersoonIdentificatienummersGroep;
import nl.bzk.brp.model.groep.logisch.PersoonImmigratieGroep;
import nl.bzk.brp.model.groep.logisch.PersoonInschrijvingGroep;
import nl.bzk.brp.model.groep.logisch.PersoonOpschortingGroep;
import nl.bzk.brp.model.groep.logisch.PersoonOverlijdenGroep;
import nl.bzk.brp.model.groep.logisch.PersoonSamengesteldeNaamGroep;
import nl.bzk.brp.model.groep.logisch.PersoonUitsluitingNLKiesrechtGroep;
import nl.bzk.brp.model.groep.logisch.PersoonVerblijfsrechtGroep;
import nl.bzk.brp.model.groep.logisch.PersoonskaartGroep;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonAanschrijvingHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonAdresHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonGeboorteHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonGeslachtsaanduidingHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonIdentificatienummersHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonOverlijdenHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonSamengesteldeNaamHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonVoornaamHisModel;
import nl.bzk.brp.model.objecttype.logisch.Betrokkenheid;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.logisch.PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.objecttype.logisch.PersoonIndicatie;
import nl.bzk.brp.model.objecttype.logisch.PersoonNationaliteit;
import nl.bzk.brp.model.objecttype.operationeel.PersoonAdresModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonVoornaamModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortPersoon;
import nl.bzk.brp.util.DatumUtil;

/**
 * Proxy voor persoon op peildatum.
 */
public class PersoonProxy implements Persoon {

    private final PersoonHisModel persoon;
    private final Datum peilDatum;

    public PersoonProxy(final PersoonHisModel persoon) {
        this(persoon, DatumUtil.vandaag());
    }

    public PersoonProxy(final PersoonHisModel persoon, final Datum peilDatum) {
        this.persoon = persoon;
        this.peilDatum = peilDatum;
    }


    @Override
    public SoortPersoon getSoort() {
        return persoon.getSoort();
    }

    @Override
    public PersoonGeslachtsaanduidingGroep getGeslachtsaanduiding() {
        PersoonGeslachtsaanduidingHisModel geslachtsaanduiding = Iterables.find(persoon.getGeslachtsaanduiding(),
                                                                                HistoriePredicates.geldigOp(peilDatum),
                                                                                null);

        return geslachtsaanduiding.getPersoon().getGeslachtsaanduiding();
    }

    @Override
    public PersoonGeboorteGroep getGeboorte() {
        PersoonGeboorteHisModel geboorte = Iterables.find(persoon.getGeboorte(),
                                                          HistoriePredicates.bekendOp(peilDatum),
                                                          null);

        return geboorte.getPersoon().getGeboorte();
    }

    @Override
    public Set<PersoonAdresModel> getAdressen() {
        Collection<PersoonAdresHisModel> hisAdressen =
                Collections2.filter(persoon.getAdressen(), HistoriePredicates.bekendOp(peilDatum));

        Set<PersoonAdresModel> adressen = new HashSet<PersoonAdresModel>(hisAdressen.size());
        for (PersoonAdresHisModel hisModel : hisAdressen) {
            adressen.add(new PersoonAdresModel(hisModel));
        }

        return adressen;
    }


    // TODO
    @Override
    public PersoonIdentificatienummersGroep getIdentificatienummers() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public PersoonSamengesteldeNaamGroep getSamengesteldeNaam() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public PersoonAanschrijvingGroep getAanschrijving() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public PersoonOverlijdenGroep getOverlijden() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Set<PersoonVoornaamModel> getPersoonVoornaam() {
        return Collections.emptySet();
    }

    @Override
    public PersoonOpschortingGroep getOpschorting() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public PersoonVerblijfsrechtGroep getVerblijfsrecht() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public PersoonBijhoudingsgemeenteGroep getBijhoudingsgemeente() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public PersoonUitsluitingNLKiesrechtGroep getUitsluitingNLKiesrecht() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public PersoonEUVerkiezingenGroep getEUVerkiezingen() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public PersoonBijhoudingsverantwoordelijkheidGroep getBijhoudingsverantwoordelijkheid() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public PersoonskaartGroep getPersoonsKaart() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public PersoonImmigratieGroep getImmigratie() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public PersoonInschrijvingGroep getInschrijving() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public PersoonAfgeleidAdministratiefGroep getAfgeleidAdministratief() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Collection<? extends Betrokkenheid> getBetrokkenheden() {
        return Collections.emptyList();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Collection<? extends PersoonGeslachtsnaamcomponent> getGeslachtsnaamcomponenten() {
        return Collections.emptyList();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Collection<? extends PersoonNationaliteit> getNationaliteiten() {
        return Collections.emptyList();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Collection<? extends PersoonIndicatie> getIndicaties() {
        return Collections.emptyList();  //To change body of implemented methods use File | Settings | File Templates.
    }
}
