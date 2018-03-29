/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaVoorwaardeOnvertaalbaarExceptie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaVoorwaardeRegel;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.GelijkEenOperator;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.OngelijkAlleOperator;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.OngelijkEenOperator;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.Operator;

/**
 * Een GBA voorwaarde regel vertaald een voorwaarde regel naar een BRP expressie.
 */
public abstract class AbstractGbaVoorwaardeRegel implements GbaVoorwaardeRegel {

    private final int volgorde;
    private final String regexPatroon;

    /**
     * Maakt een nieuw voorwaarderegel aan.
     * @param volgorde volgorde waarin voorwaarderegel moet worden uitgevoerd
     * @param regexPatroon reguliere expressie waaraan voorwaarde regel moet voldoen
     */
    public AbstractGbaVoorwaardeRegel(final int volgorde, final String regexPatroon) {
        this.volgorde = volgorde;
        this.regexPatroon = regexPatroon;
    }

    @Override
    public final int volgorde() {
        return volgorde;
    }

    @Override
    public final boolean filter(final String voorwaarde) {
        return voorwaarde.matches(regexPatroon);
    }

    Operator bepaalOperator(final String s) throws GbaVoorwaardeOnvertaalbaarExceptie {
        final Operator operator;
        switch (s) {
            case "OGAA":
                operator = new OngelijkAlleOperator();
                break;
            case "OGA1":
                operator = new OngelijkEenOperator();
                break;
            case "GA1":
                operator = new GelijkEenOperator();
                break;
            default:
                throw new GbaVoorwaardeOnvertaalbaarExceptie("Operator wordt niet ondersteund");
        }
        return operator;
    }
}
