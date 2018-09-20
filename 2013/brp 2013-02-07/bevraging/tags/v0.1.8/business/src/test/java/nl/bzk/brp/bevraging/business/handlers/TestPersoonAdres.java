/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import nl.bzk.brp.bevraging.domein.FunctieAdres;
import nl.bzk.brp.bevraging.domein.Partij;
import nl.bzk.brp.bevraging.domein.Plaats;
import nl.bzk.brp.bevraging.domein.SoortPartij;
import nl.bzk.brp.bevraging.domein.kern.PersoonAdres;


public class TestPersoonAdres implements PersoonAdres {

    private static final Partij PARTIJ = new Partij(SoortPartij.GEMEENTE);
    private static final Plaats PLAATS = new Plaats("Test");

    @Override
    public PersoonAdres getAdres() {
        return this;
    }

    @Override
    public Partij getGemeente() {
        return PARTIJ;
    }

    @Override
    public Integer getHuisNummer() {
        return 44;
    }

    @Override
    public String getNaamOpenbareRuimte() {
        return "Dam";
    }

    @Override
    public String getPostcode() {
        return "3063 NB";
    }

    @Override
    public Plaats getWoonplaats() {
        return PLAATS;
    }

    @Override
    public String getAfgekorteNaamOpenbareRuimte() {
        return "hoofdstr";
    }

    @Override
    public String getGemeenteDeel() {
        return "Scheveningen";
    }

    @Override
    public String getHuisletter() {
        return "A";
    }

    @Override
    public String getHuisnummertoevoeging() {
        // TODO Auto-generated method stub
        return "bis";
    }

    @Override
    public String getLocatieTOVAdres() {
        // TODO Auto-generated method stub
        return "boven";
    }

    @Override
    public FunctieAdres getSoort() {
        return FunctieAdres.WOONADRES;
    }

}
