/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.dto.proxies;

import java.util.HashSet;
import java.util.Set;

import nl.bzk.brp.bevraging.domein.GeslachtsAanduiding;
import nl.bzk.brp.bevraging.domein.Land;
import nl.bzk.brp.bevraging.domein.Partij;
import nl.bzk.brp.bevraging.domein.Plaats;
import nl.bzk.brp.bevraging.domein.RedenOpschorting;
import nl.bzk.brp.bevraging.domein.SoortPersoon;
import nl.bzk.brp.bevraging.domein.kern.Persoon;
import nl.bzk.brp.bevraging.domein.kern.PersoonAdres;


/**
 * Proxy die alleen gegevenselementen doorgeeft als deze in het abonnement zitten.
 */
public class PersoonProxy extends AbstractAutorisatieProxy implements Persoon {

    private static final String VELD_PREFIX = Persoon.class.getSimpleName().toLowerCase() + ".";

    private final Persoon       persoon;

    private Set<PersoonAdres>   adressenProxies;

    /**
     * Constructor.
     *
     * @param persoon de persoon waarvoor dit object een proxy moet zijn
     * @param toegestaneVelden de java namen (zonder hoofdletters) van de velden die doorgegeven mogen worden
     */
    public PersoonProxy(final Persoon persoon, final Set<String> toegestaneVelden) {
        super(toegestaneVelden);
        this.persoon = persoon;
        initialiseerAdressenProxies(toegestaneVelden);
    }

    /**
     * Creeert voor elk adres van de persoon een proxy en voegt die toe aan de verzameling met proxies.
     * Deze proxies zullen worden teruggegeven door de getAdressen methode in plaats van de adresssen zelf.
     *
     * @param toegestaneVelden de java namen (zonder hoofdletters) van de velden die doorgegeven mogen worden
     */
    private void initialiseerAdressenProxies(final Set<String> toegestaneVelden) {
        if (persoon.getAdressen() != null) {
            adressenProxies = new HashSet<PersoonAdres>();
            for (PersoonAdres adres : persoon.getAdressen()) {
                adressenProxies.add(new PersoonAdresProxy(adres, toegestaneVelden));
            }
        }
    }

    /**
     * @param persoon de persoon zelf of de proxy ervan
     * @return de persoon zelf
     */
    public static Persoon getPersoon(final Persoon persoon) {
        Persoon resultaat;
        if (persoon instanceof PersoonProxy) {
            resultaat = ((PersoonProxy) persoon).persoon;
        } else {
            resultaat = persoon;
        }
        return resultaat;
    }

    @Override
    public Long getId() {
        return filter(persoon.getId(), VELD_PREFIX + "Id");
    }

    @Override
    public SoortPersoon getSoort() {
        return filter(persoon.getSoort(), VELD_PREFIX + "Soort");
    }

    @Override
    public String getBurgerservicenummer() {
        return filter(persoon.getBurgerservicenummer(), VELD_PREFIX + "Burgerservicenummer");
    }

    @Override
    public String getAdministratienummer() {
        return filter(persoon.getAdministratienummer(), VELD_PREFIX + "Administratienummer");
    }

    @Override
    public String getGeslachtsnaam() {
        return filter(persoon.getGeslachtsnaam(), VELD_PREFIX + "Geslachtsnaam");
    }

    @Override
    public String getVoornamen() {
        return filter(persoon.getVoornamen(), VELD_PREFIX + "Voornamen");
    }

    @Override
    public String getVoorvoegsel() {
        return filter(persoon.getVoorvoegsel(), VELD_PREFIX + "Voorvoegsel");
    }

    @Override
    public String getScheidingsTeken() {
        return filter(persoon.getScheidingsTeken(), VELD_PREFIX + "ScheidingsTeken");
    }

    @Override
    public GeslachtsAanduiding getGeslachtsAanduiding() {
        return filter(persoon.getGeslachtsAanduiding(), VELD_PREFIX + "GeslachtsAanduiding");
    }

    @Override
    public Integer getDatumGeboorte() {
        return filter(persoon.getDatumGeboorte(), VELD_PREFIX + "DatumGeboorte");
    }

    @Override
    public Partij getBijhoudingsGemeente() {
        return filter(persoon.getBijhoudingsGemeente(), VELD_PREFIX + "BijhoudingsGemeente");
    }

    @Override
    public String getBuitenlandseGeboorteplaats() {
        return filter(persoon.getBuitenlandseGeboorteplaats(), VELD_PREFIX + "BuitenlandseGeboorteplaats");
    }

    @Override
    public String getBuitenlandsePlaatsOverlijden() {
        return filter(persoon.getBuitenlandsePlaatsOverlijden(), VELD_PREFIX + "BuitenlandsePlaatsOverlijden");
    }

    @Override
    public String getBuitenlandseRegioGeboorte() {
        return filter(persoon.getBuitenlandseRegioGeboorte(), VELD_PREFIX + "BuitenlandseRegioGeboorte");
    }

    @Override
    public Partij getGemeenteGeboorte() {
        return filter(persoon.getGemeenteGeboorte(), VELD_PREFIX + "GemeenteGeboorte");
    }

    @Override
    public Land getLandGeboorte() {
        return filter(persoon.getLandGeboorte(), VELD_PREFIX + "LandGeboorte");
    }

    @Override
    public Set<PersoonAdres> getAdressen() {
        return adressenProxies;
    }

    @Override
    public String getOmschrijvingGeboorteLocatie() {
        return filter(persoon.getOmschrijvingGeboorteLocatie(), VELD_PREFIX + "OmschrijvingGeboorteLocatie");
    }

    /**
     * {@inheritDoc}
     *
     * @brp.bedrijfsregel BRAU0047
     */
    @Override
    public RedenOpschorting getRedenOpschortingBijhouding() {
        return persoon.getRedenOpschortingBijhouding();
    }

    @Override
    public Plaats getWoonplaatsGeboorte() {
        return filter(persoon.getWoonplaatsGeboorte(), VELD_PREFIX + "WoonplaatsGeboorte");
    }

    @Override
    public Boolean behandeldAlsNederlander() {
        return filter(persoon.behandeldAlsNederlander(), VELD_PREFIX + "behandeldAlsNederlander");
    }

    @Override
    public Boolean belemmeringVerstrekkingReisdocument() {
        return filter(persoon.belemmeringVerstrekkingReisdocument(), VELD_PREFIX
            + "belemmeringVerstrekkingReisdocument");
    }

    @Override
    public Boolean bezitBuitenlandsReisdocument() {
        return filter(persoon.bezitBuitenlandsReisdocument(), VELD_PREFIX + "bezitBuitenlandsReisdocument");
    }

    @Override
    public Boolean deelnameEUVerkiezingen() {
        return filter(persoon.deelnameEUVerkiezingen(), VELD_PREFIX + "deelnameEUVerkiezingen");
    }

    @Override
    public Boolean derdeHeeftGezag() {
        return filter(persoon.derdeHeeftGezag(), VELD_PREFIX + "derdeHeeftGezag");
    }

    @Override
    public Boolean gepriviligeerde() {
        return filter(persoon.gepriviligeerde(), VELD_PREFIX + "gepriviligeerde");
    }

    @Override
    public Boolean onderCuratele() {
        return filter(persoon.onderCuratele(), VELD_PREFIX + "onderCuratele");
    }

    @Override
    public Boolean uitsluitingNLKiesrecht() {
        return filter(persoon.uitsluitingNLKiesrecht(), VELD_PREFIX + "uitsluitingNLKiesrecht");
    }

    @Override
    public Boolean vastgesteldNietNederlander() {
        return filter(persoon.vastgesteldNietNederlander(), VELD_PREFIX + "vastgesteldNietNederlander");
    }

    /**
     * {@inheritDoc}
     *
     * @brp.bedrijfsregel BRAU0047, FTPE0003
     */
    @Override
    public Boolean verstrekkingsBeperking() {
        return persoon.verstrekkingsBeperking();
    }

    @Override
    public final String toString() {
        return persoon.toString();
    }

}
