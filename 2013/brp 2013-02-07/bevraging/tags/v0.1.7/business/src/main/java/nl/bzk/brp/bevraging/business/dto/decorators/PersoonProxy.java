/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.dto.decorators;

import java.util.Collections;
import java.util.Set;

import nl.bzk.brp.bevraging.domein.GeslachtsAanduiding;
import nl.bzk.brp.bevraging.domein.Land;
import nl.bzk.brp.bevraging.domein.Partij;
import nl.bzk.brp.bevraging.domein.Persoon;
import nl.bzk.brp.bevraging.domein.PersoonAdres;
import nl.bzk.brp.bevraging.domein.Plaats;
import nl.bzk.brp.bevraging.domein.RedenOpschorting;
import nl.bzk.brp.bevraging.domein.SoortPersoon;


/**
 * Proxy die alleen gegevenselementen doorgeeft als deze in het abonnement zitten.
 */
public class PersoonProxy implements nl.bzk.brp.bevraging.domein.kern.Persoon {

    private Persoon persoon;

    /**
     * Constructor.
     *
     * @param persoon de persoon zoals die bekend is in de database
     */
    public PersoonProxy(final Persoon persoon) {
        this.persoon = persoon;
    }

    @Override
    public Persoon getPersoon() {
        return persoon;
    }

    @Override
    public Long getId() {
        return persoon.getId();
    }

    @Override
    public SoortPersoon getSoort() {
        return persoon.getSoort();
    }

    @Override
    public Long getBurgerservicenummer() {
        return persoon.getBurgerservicenummer();
    }

    @Override
    public Long getAdministratienummer() {
        return persoon.getAdministratienummer();
    }

    @Override
    public String getGeslachtsnaam() {
        return persoon.getGeslachtsnaam();
    }

    @Override
    public String getVoornamen() {
        return persoon.getVoornamen();
    }

    @Override
    public String getVoorvoegsel() {
        return persoon.getVoorvoegsel();
    }

    @Override
    public String getScheidingsTeken() {
        return persoon.getScheidingsTeken();
    }

    @Override
    public GeslachtsAanduiding getGeslachtsAanduiding() {
        return persoon.getGeslachtsAanduiding();
    }

    @Override
    public Integer getGeboorteDatum() {
        return persoon.getGeboorteDatum();
    }

    @Override
    public Partij getBijhoudingsGemeente() {
        return persoon.getBijhoudingsGemeente();
    }

    @Override
    public String getBuitenlandseGeboorteplaats() {
        return persoon.getBuitenlandseGeboorteplaats();
    }

    @Override
    public String getBuitenlandsePlaatsOverlijden() {
        return persoon.getBuitenlandsePlaatsOverlijden();
    }

    @Override
    public String getBuitenlandseRegioGeboorte() {
        return persoon.getBuitenlandseRegioGeboorte();
    }

    @Override
    public Partij getGemeenteGeboorte() {
        return persoon.getGemeenteGeboorte();
    }

    @Override
    public Land getLandGeboorte() {
        return persoon.getLandGeboorte();
    }

    @Override
    public Set<PersoonAdres> getAdressen() {
        if (persoon.getAdressen() == null) {
            return null;
        }
        return Collections.unmodifiableSet(persoon.getAdressen());
    }

    @Override
    public String getOmschrijvingGeboorteLocatie() {
        return persoon.getOmschrijvingGeboorteLocatie();
    }

    @Override
    public RedenOpschorting getRedenOpschortingBijhouding() {
        return persoon.getRedenOpschortingBijhouding();
    }

    @Override
    public Plaats getWoonplaatsGeboorte() {
        return persoon.getWoonplaatsGeboorte();
    }

    @Override
    public Boolean behandeldAlsNederlander() {
        return persoon.behandeldAlsNederlander();
    }

    @Override
    public Boolean belemmeringVerstrekkingReisdocument() {
        return persoon.belemmeringVerstrekkingReisdocument();
    }

    @Override
    public Boolean bezitBuitenlandsReisdocument() {
        return persoon.bezitBuitenlandsReisdocument();
    }

    @Override
    public Boolean deelnameEUVerkiezingen() {
        return persoon.deelnameEUVerkiezingen();
    }

    @Override
    public Boolean derdeHeeftGezag() {
        return persoon.derdeHeeftGezag();
    }

    @Override
    public Boolean gepriviligeerde() {
        return persoon.gepriviligeerde();
    }

    @Override
    public Boolean onderCuratele() {
        return persoon.onderCuratele();
    }

    @Override
    public Boolean uitsluitingNLKiesrecht() {
        return persoon.uitsluitingNLKiesrecht();
    }

    @Override
    public Boolean vastgesteldNietNederlander() {
        return persoon.vastgesteldNietNederlander();
    }

    @Override
    public Boolean verstrekkingsBeperking() {
        return persoon.verstrekkingsBeperking();
    }

    @Override
    public final String toString() {
        return persoon.toString();
    }

}
