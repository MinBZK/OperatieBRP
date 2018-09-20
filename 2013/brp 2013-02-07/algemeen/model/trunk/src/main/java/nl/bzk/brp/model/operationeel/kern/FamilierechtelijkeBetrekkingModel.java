/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import nl.bzk.brp.model.logisch.kern.FamilierechtelijkeBetrekking;
import nl.bzk.brp.model.operationeel.kern.basis.AbstractFamilierechtelijkeBetrekkingModel;


/**
 * De familierechtelijke betrekking tussen het Kind enerzijds, en zijn/haar ouders anderzijds.
 *
 * De familierechtelijke betrekking "is" van het Kind. Adoptie, erkenning, dan wel het terugdraaien van een adoptie of
 * erkenning heeft g��n invloed op de familierechtelijke betrekking zelf: het blijft ��n en dezelfde Relatie.
 *
 *
 *
 */
@Entity
@DiscriminatorValue(value = "3")
public class FamilierechtelijkeBetrekkingModel extends AbstractFamilierechtelijkeBetrekkingModel implements
    FamilierechtelijkeBetrekking
{

    /** Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft. */
    public FamilierechtelijkeBetrekkingModel() {
        super();
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param familierechtelijkeBetrekking Te kopieren object type.
     */
    public FamilierechtelijkeBetrekkingModel(final FamilierechtelijkeBetrekking familierechtelijkeBetrekking) {
        super(familierechtelijkeBetrekking);
    }

    /**
     * Retourneert kind betrokkenheid in deze relatie.
     * @return Kind betrokkenheid.
     */
    @Transient
    public KindModel getKindBetrokkenheid() {
        for (BetrokkenheidModel betrokkenheid : getBetrokkenheden()) {
            if (betrokkenheid instanceof KindModel) {
                return (KindModel) betrokkenheid;
            }
        }
        return null;
    }

    /**
     * Retourneert ouder betrokkenheden in deze relatie.
     * @return Ouder betrokkenheden.
     */
    @Transient
    public Set<BetrokkenheidModel> getOuderBetrokkenheden() {
        final TreeSet<BetrokkenheidModel> gesorteerdeSet = new TreeSet<BetrokkenheidModel>(
                new Comparator<BetrokkenheidModel>() {
                    @Override
                    public int compare(final BetrokkenheidModel betr1, final BetrokkenheidModel betr2) {
                        if (betr1.getID() == null || betr2.getID() == null) {
                            return betr1.getPersoon().getID().compareTo(betr2.getPersoon().getID());
                        } else {
                            return betr1.getID().compareTo(betr2.getID());
                        }
                    }
                });
        for (BetrokkenheidModel betrokkenheid : getBetrokkenheden()) {
            if (betrokkenheid instanceof OuderModel) {
                gesorteerdeSet.add(betrokkenheid);
            }
        }
        return gesorteerdeSet;
    }

    /**
     * Functie verseist voor Jibx maar verder niet gebruikt.
     * @param betr Betrokkenheid.
     */
    protected void voegBetrokkenhedenToe(final Set<BetrokkenheidModel> betr) { }

    /**
     * Functie verseist voor Jibx maar verder niet gebruikt.
     * @param betr KindModel.
     */
    protected void voegKindBetrokkenheidToe(final KindModel betr) { }

}
