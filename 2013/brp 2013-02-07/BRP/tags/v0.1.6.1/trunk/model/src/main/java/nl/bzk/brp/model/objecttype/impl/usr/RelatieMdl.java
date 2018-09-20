/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.impl.usr;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import nl.bzk.brp.model.objecttype.impl.gen.AbstractRelatieMdl;
import nl.bzk.brp.model.objecttype.interfaces.usr.Relatie;
import nl.bzk.brp.model.objecttype.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.statisch.SoortRelatie;

/**
 *
 */
//TODO: rename de entity naam terug naar de echte
@Entity
@Table(schema = "Kern", name = "Relatie")
@SuppressWarnings("serial")
public class RelatieMdl extends AbstractRelatieMdl implements Relatie {

    /**
     * Retourneert kind betrokkenheid in deze relatie.
     * @return Kind betrokkenheid.
     */
    @Transient
    public BetrokkenheidMdl getKindBetrokkenheid() {
        for (BetrokkenheidMdl betrokkenheidMdl : getBetrokkenheden()) {
            if (SoortBetrokkenheid.KIND == betrokkenheidMdl.getRol()) {
                return betrokkenheidMdl;
            }
        }
        return null;
    }

    /**
     * Retourneert ouder betrokkenheden in deze relatie.
     * @return Ouder betrokkenheden.
     */
    @Transient
    public Set<BetrokkenheidMdl> getOuderBetrokkenheden() {
        Set<BetrokkenheidMdl> ouderBetrokkenheden = new HashSet<BetrokkenheidMdl>();
        for (BetrokkenheidMdl betrokkenheidMdl : getBetrokkenheden()) {
            if (SoortBetrokkenheid.OUDER == betrokkenheidMdl.getRol()) {
                ouderBetrokkenheden.add(betrokkenheidMdl);
            }
        }
        return ouderBetrokkenheden;
    }

    /**
     * Retourneert partner betrokkenheden in deze relatie.
     * @return Partner betrokkenheden.
     */
    @Transient
    public BetrokkenheidMdl getPartnerBetrokkenheid() {
        for (BetrokkenheidMdl betrokkenheidMdl : getBetrokkenheden()) {
            if (SoortBetrokkenheid.PARTNER == betrokkenheidMdl.getRol()) {
                return betrokkenheidMdl;
            }
        }
        return null;
    }

    /**
     * Geeft aan of deze relatie een huwelijk is.
     * @return True indien huwelijk anders false.
     */
    public boolean isHuwelijk() {
        return SoortRelatie.HUWELIJK == getSoort();
    }

    /**
     * Geeft aan of deze relatie een geregistreerd partnerschap is.
     * @return True indien geregistreerd partnerschap anders false.
     */
    public boolean isGeregistreerdPartnerschap() {
        return SoortRelatie.GEREGISTREERD_PARTNERSCHAP == getSoort();
    }

    /**
     * Functie verseist voor Jibx maar verder niet gebruikt.
     * @param betr Betrokkenheid.
     */
    protected void voegBetrokkenheidToe(final BetrokkenheidMdl betr) { }

    /**
     * Functie verseist voor Jibx maar verder niet gebruikt.
     * @param betr Betrokkenheid.
     */
    protected void voegBetrokkenhedenToe(final Set<BetrokkenheidMdl> betr) { }
}
