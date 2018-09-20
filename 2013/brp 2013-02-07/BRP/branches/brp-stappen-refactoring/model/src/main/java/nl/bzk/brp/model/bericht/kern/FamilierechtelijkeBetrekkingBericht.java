/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import java.util.HashSet;
import java.util.Set;

import nl.bzk.brp.model.bericht.kern.basis.AbstractFamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.logisch.kern.FamilierechtelijkeBetrekking;


/**
 * De familierechtelijke betrekking tussen het Kind enerzijds, en zijn/haar ouders anderzijds.
 * <p/>
 * De familierechtelijke betrekking "is" van het Kind. Adoptie, erkenning, dan wel het terugdraaien van een adoptie of
 * erkenning heeft g��n invloed op de familierechtelijke betrekking zelf: het blijft ��n en dezelfde Relatie.
 * <p/>
 * <p/>
 * Generator: nl.bzk.brp.generatoren.java.BerichtModelGenerator.
 * Metaregister versie: 1.3.8.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2012-12-10 16:16:22.
 * Gegenereerd op: Mon Dec 10 16:17:11 CET 2012.
 */
public class FamilierechtelijkeBetrekkingBericht extends AbstractFamilierechtelijkeBetrekkingBericht implements
    FamilierechtelijkeBetrekking
{

    /** Constructor die het discriminator attribuut zet of doorgeeft. */
    public FamilierechtelijkeBetrekkingBericht() {
        super();
    }

    public Set<OuderBericht> getOuderBetrokkenheden() {
        final Set<OuderBericht> ouderBetr = new HashSet<OuderBericht>();
        if (getBetrokkenheden() != null) {
            for (BetrokkenheidBericht betrokkenheidBericht : getBetrokkenheden()) {
                if (betrokkenheidBericht instanceof OuderBericht) {
                    ouderBetr.add((OuderBericht) betrokkenheidBericht);
                }
            }
        }
        return ouderBetr;
    }

    public KindBericht getKindBetrokkenheid() {
        if (getBetrokkenheden() != null) {
            for (BetrokkenheidBericht betrokkenheidBericht : getBetrokkenheden()) {
                if (betrokkenheidBericht instanceof KindBericht) {
                    return (KindBericht) betrokkenheidBericht;
                }
            }
        }
        return null;
    }

}
