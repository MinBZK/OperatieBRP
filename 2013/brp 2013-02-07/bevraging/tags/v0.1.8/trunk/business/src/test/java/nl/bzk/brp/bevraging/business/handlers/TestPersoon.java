/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import java.util.HashSet;
import java.util.Set;

import nl.bzk.brp.bevraging.domein.GeslachtsAanduiding;
import nl.bzk.brp.bevraging.domein.Land;
import nl.bzk.brp.bevraging.domein.Partij;
import nl.bzk.brp.bevraging.domein.Plaats;
import nl.bzk.brp.bevraging.domein.RedenOpschorting;
import nl.bzk.brp.bevraging.domein.SoortPartij;
import nl.bzk.brp.bevraging.domein.SoortPersoon;
import nl.bzk.brp.bevraging.domein.kern.Persoon;
import nl.bzk.brp.bevraging.domein.kern.PersoonAdres;


public class TestPersoon implements Persoon {

    private static final Integer               DATUM    = 11112011;
    private static final Partij                PARTIJ_1 = new Partij(SoortPartij.AANGEWEZEN_BESTUURSORGAAN);
    private static final Partij                PARTIJ_2 = new Partij(SoortPartij.DUMMY);
    private static final Land                  LAND     = new Land("Neverland");
    private static final HashSet<PersoonAdres> ADRESSEN;
    private static final Plaats                PLAATS   = new Plaats("Babylon");

    static {
        ADRESSEN = new HashSet<PersoonAdres>();
        ADRESSEN.add(new TestPersoonAdres());
    }

    @Override
    public Persoon getPersoon() {
        return this;
    }

    @Override
    public Long getId() {
        return 2L;
    }

    @Override
    public SoortPersoon getSoort() {
        return SoortPersoon.DUMMY;
    }

    @Override
    public Long getBurgerservicenummer() {
        return 77L;
    }

    @Override
    public Long getAdministratienummer() {
        return 44L;
    }

    @Override
    public String getGeslachtsnaam() {
        return "Vis";
    }

    @Override
    public String getVoornamen() {
        return "Ab";
    }

    @Override
    public String getVoorvoegsel() {
        return "de";
    }

    @Override
    public String getScheidingsTeken() {
        return ",";
    }

    @Override
    public GeslachtsAanduiding getGeslachtsAanduiding() {
        return GeslachtsAanduiding.DUMMY;
    }

    @Override
    public Integer getDatumGeboorte() {
        return DATUM;
    }

    @Override
    public Partij getBijhoudingsGemeente() {
        return PARTIJ_1;
    }

    @Override
    public String getBuitenlandseGeboorteplaats() {
        return "Bethlehem";
    }

    @Override
    public String getBuitenlandsePlaatsOverlijden() {
        return "Napels";
    }

    @Override
    public String getBuitenlandseRegioGeboorte() {
        return "Jordaanvalei";
    }

    @Override
    public Partij getGemeenteGeboorte() {
        return PARTIJ_2;
    }

    @Override
    public Land getLandGeboorte() {
        return LAND;
    }

    @Override
    public Set<PersoonAdres> getAdressen() {
        return ADRESSEN;
    }

    @Override
    public String getOmschrijvingGeboorteLocatie() {
        return "Stal";
    }

    @Override
    public RedenOpschorting getRedenOpschortingBijhouding() {
        return RedenOpschorting.DUMMY;
    }

    @Override
    public Plaats getWoonplaatsGeboorte() {
        return PLAATS;
    }

    @Override
    public Boolean behandeldAlsNederlander() {
        return true;
    }

    @Override
    public Boolean belemmeringVerstrekkingReisdocument() {
        return true;
    }

    @Override
    public Boolean bezitBuitenlandsReisdocument() {
        return true;
    }

    @Override
    public Boolean deelnameEUVerkiezingen() {
        return true;
    }

    @Override
    public Boolean derdeHeeftGezag() {
        return true;
    }

    @Override
    public Boolean gepriviligeerde() {
        return true;
    }

    @Override
    public Boolean onderCuratele() {
        return true;
    }

    @Override
    public Boolean uitsluitingNLKiesrecht() {
        return true;
    }

    @Override
    public Boolean vastgesteldNietNederlander() {
        return true;
    }

    @Override
    public Boolean verstrekkingsBeperking() {
        return true;
    }

}
