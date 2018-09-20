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

import nl.bzk.brp.model.objecttype.impl.gen.AbstractPersoonMdl;
import nl.bzk.brp.model.objecttype.interfaces.usr.Persoon;
import nl.bzk.brp.model.objecttype.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.statisch.SoortIndicatie;

/**
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(schema = "Kern", name = "Pers")
public class PersoonMdl extends AbstractPersoonMdl implements Persoon {

    /**
     * Retourneert true indien deze persoon betrokkenheden heeft.
     * @return True indien betrokkenheden aanwezig.
     */
    public boolean heeftBetrokkenheden() {
        return getBetrokkenheden() != null
               && !getBetrokkenheden().isEmpty();
    }

    /**
     * Retourneert true indien deze persoon voornamen heeft.
     * @return True indien voornamen aanwezig.
     */
    public boolean heeftVoornamen() {
        return getPersoonVoornaam() != null
               && !getPersoonVoornaam().isEmpty();
    }

    /**
     * Retourneert true indien deze persoon geslachtsnaam componenten heeft.
     * @return True indien geslachtsnaam componenten aanwezig.
     */
    public boolean heeftGeslachtsnaamComponenten() {
        return getGeslachtsnaamcomponenten() != null
               && !getGeslachtsnaamcomponenten().isEmpty();
    }

    /**
     * Retourneert true indien deze persoon nationaliteiten heeft.
     * @return True indien nationaliteiten aanwezig.
     */
    public boolean heeftNationaliteiten() {
        return getNationaliteiten() != null
               && !getNationaliteiten().isEmpty();
    }

    /**
     * Retourneert kind betrokkenheid van deze persoon.
     * @return Kind betrokkenheid.
     */
    public BetrokkenheidMdl getKindBetrokkenHeid() {
        for (BetrokkenheidMdl betrokkenheidMdl : getBetrokkenheden()) {
            if (SoortBetrokkenheid.KIND == betrokkenheidMdl.getRol()) {
                return betrokkenheidMdl;
            }
        }
        return null;
    }

    /**
     * Retourneert ouder betrokkenheden van deze persoon.
     * @return Ouder betrokkenheden.
     */
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
     * Retourneert partner betrokkenheden van deze persoon.
     * @return Partner betrokkenheden.
     */
    public Set<BetrokkenheidMdl> getPartnerBetrokkenHeden() {
        Set<BetrokkenheidMdl> partnerBetrokkenheden = new HashSet<BetrokkenheidMdl>();
        for (BetrokkenheidMdl betrokkenheidMdl : getBetrokkenheden()) {
            if (SoortBetrokkenheid.PARTNER == betrokkenheidMdl.getRol()) {
                partnerBetrokkenheden.add(betrokkenheidMdl);
            }
        }
        return partnerBetrokkenheden;
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

    /**
     * Retourneert de indicatie verstrekkings beperking indien aanwezig.
     * @return Indicatie.
     */
    protected PersoonIndicatieMdl getIndicatieVerstrekkingsbeperking() {
        if (getIndicaties() != null) {
            for (PersoonIndicatieMdl ind : getIndicaties()) {
                if (SoortIndicatie.VERSTREKKINGSBEPERKING == ind.getSoort()) {
                    return ind;
                }
            }
        }
        return null;
    }

    /**
     * Functie verseist voor Jibx maar verder niet gebruikt.
     * @param ind Indicatie.
     */
    protected void voegPersoonIndicatieToe(final PersoonIndicatieMdl ind) { }

    /**
     * Retourneert of persoon een verstrekkingsbeperking heeft.
     * @return true of false.
     */
    protected boolean heeftVerstrekkingsBeperking() {
        return getIndicatieVerstrekkingsbeperking() != null;
    }

}
