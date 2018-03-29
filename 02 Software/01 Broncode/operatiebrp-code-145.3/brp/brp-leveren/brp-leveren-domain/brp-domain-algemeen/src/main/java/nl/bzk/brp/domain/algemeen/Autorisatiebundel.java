/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.algemeen;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;

/**
 * Autorisatiebundel combineert een ToegangLeveringsAutorisatie en een dienst.
 */
public final class Autorisatiebundel {

    private final ToegangLeveringsAutorisatie toegangLeveringsautorisatie;
    private final Dienst dienst;

    /**
     * Constructor.
     *
     * @param toegangLeveringsautorisatie de toegangleveringsautorisatie
     * @param dienst                      de dienst
     */
    public Autorisatiebundel(final ToegangLeveringsAutorisatie toegangLeveringsautorisatie, final Dienst dienst) {
        this.toegangLeveringsautorisatie = toegangLeveringsautorisatie;
        this.dienst = dienst;
    }

    /**
     * @return de toegangleveringsautorisatie
     */
    public ToegangLeveringsAutorisatie getToegangLeveringsautorisatie() {
        return toegangLeveringsautorisatie;
    }

    /**
     * @return de leveringsautorisatie
     */
    public Leveringsautorisatie getLeveringsautorisatie() {
        return toegangLeveringsautorisatie.getLeveringsautorisatie();
    }

    /**
     * @return het leveringsautorisatie id
     */
    public int getLeveringsautorisatieId() {
        return getLeveringsautorisatie().getId();
    }

    /**
     * @return de dienst waarvoor geleverd wordt
     */
    public Dienst getDienst() {
        return dienst;
    }

    /**
     * @return het soort dienst, afgeleid van dienst.
     */
    public SoortDienst getSoortDienst() {
        return dienst.getSoortDienst();
    }

    /**
     * @return de geautoriseerde partij
     */
    public Partij getPartij() {
        return toegangLeveringsautorisatie.getGeautoriseerde().getPartij();
    }

    /**
     * @return de rol van de geautoriseerde partij
     */
    public Rol getRol() {
        return toegangLeveringsautorisatie.getGeautoriseerde().getRol();
    }


}
