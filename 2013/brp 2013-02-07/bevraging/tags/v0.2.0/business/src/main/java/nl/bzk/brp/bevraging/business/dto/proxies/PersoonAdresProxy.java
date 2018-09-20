/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.dto.proxies;

import java.util.Set;

import nl.bzk.brp.bevraging.domein.FunctieAdres;
import nl.bzk.brp.bevraging.domein.Partij;
import nl.bzk.brp.bevraging.domein.Plaats;
import nl.bzk.brp.bevraging.domein.kern.PersoonAdres;


/**
 * Proxy voor PersoonAdres die alleen gegevenselementen doorgeeft als deze in het abonnement zitten.
 */
public class PersoonAdresProxy extends AbstractAutorisatieProxy implements PersoonAdres {

    private static final String VELD_PREFIX = PersoonAdres.class.getSimpleName().toLowerCase() + ".";

    private final PersoonAdres adres;

    /**
     * Constructor.
     *
     * @param persoonAdres de persoonAdres waarvoor dit object een proxy moet zijn
     * @param toegestaneVelden de java namen (zonder hoofdletters) van de velden die doorgegeven mogen worden
     */
    public PersoonAdresProxy(final PersoonAdres persoonAdres, final Set<String> toegestaneVelden) {
        super(toegestaneVelden);
        adres = persoonAdres;
    }

    /**
     * @return het adres waarvoor dit object een proxy is
     */
    public PersoonAdres getAdres() {
        return adres;
    }

    @Override
    public Partij getGemeente() {
        return filter(adres.getGemeente(), VELD_PREFIX + "Gemeente");
    }

    @Override
    public Integer getHuisNummer() {
        return filter(adres.getHuisNummer(), VELD_PREFIX + "HuisNummer");
    }

    @Override
    public String getNaamOpenbareRuimte() {
        return filter(adres.getNaamOpenbareRuimte(), VELD_PREFIX + "NaamOpenbareRuimte");
    }

    @Override
    public String getPostcode() {
        return filter(adres.getPostcode(), VELD_PREFIX + "Postcode");
    }

    @Override
    public Plaats getWoonplaats() {
        return filter(adres.getWoonplaats(), VELD_PREFIX + "Woonplaats");
    }

    @Override
    public String getAfgekorteNaamOpenbareRuimte() {
        return filter(adres.getAfgekorteNaamOpenbareRuimte(), VELD_PREFIX + "AfgekorteNaamOpenbareRuimte");
    }

    @Override
    public String getGemeenteDeel() {
        return filter(adres.getGemeenteDeel(), VELD_PREFIX + "GemeenteDeel");
    }

    @Override
    public String getHuisletter() {
        return filter(adres.getHuisletter(), VELD_PREFIX + "Huisletter");
    }

    @Override
    public String getHuisnummertoevoeging() {
        return filter(adres.getHuisnummertoevoeging(), VELD_PREFIX + "Huisnummertoevoeging");
    }

    @Override
    public String getLocatieTOVAdres() {
        return filter(adres.getLocatieTOVAdres(), VELD_PREFIX + "LocatieTOVAdres");
    }

    @Override
    public FunctieAdres getSoort() {
        return filter(adres.getSoort(), VELD_PREFIX + "Soort");
    }

    @Override
    public final String toString() {
        return adres.toString();
    }

}
