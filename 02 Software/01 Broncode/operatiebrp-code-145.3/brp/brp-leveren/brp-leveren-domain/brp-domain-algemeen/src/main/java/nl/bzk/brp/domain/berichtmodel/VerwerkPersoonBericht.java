/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.berichtmodel;

import java.util.LinkedList;
import java.util.List;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;

/**
 * VerwerkPersoonBericht.
 */
public final class VerwerkPersoonBericht extends Bericht {

    /**
     * Een leeg bericht.
     */
    public static final VerwerkPersoonBericht LEEG_BERICHT = new VerwerkPersoonBericht(null, null, null);

    private List<BijgehoudenPersoon> bijgehoudenPersonen;
    private Autorisatiebundel autorisatiebundel;

    /**
     * Constructor.
     * @param basisBerichtGegevens  de basis berichtgegevens
     * @param autorisatiebundel de autorisatie waarvoor het bericht gemaakt is
     * @param bijgehoudenPersonen lijst met personen in het bericht
     */
    public VerwerkPersoonBericht(final BasisBerichtGegevens basisBerichtGegevens,
                                 final Autorisatiebundel autorisatiebundel, final List<BijgehoudenPersoon> bijgehoudenPersonen) {
        super(basisBerichtGegevens);
        if (bijgehoudenPersonen == null) {
            this.bijgehoudenPersonen = new LinkedList<>();
        } else {
            this.bijgehoudenPersonen = bijgehoudenPersonen;
        }
        this.autorisatiebundel = autorisatiebundel;
    }

    public List<BijgehoudenPersoon> getBijgehoudenPersonen() {
        return bijgehoudenPersonen;
    }

    public Autorisatiebundel getAutorisatiebundel() {
        return autorisatiebundel;
    }

    public boolean isLeeg() {
        return bijgehoudenPersonen.isEmpty();
    }
}
