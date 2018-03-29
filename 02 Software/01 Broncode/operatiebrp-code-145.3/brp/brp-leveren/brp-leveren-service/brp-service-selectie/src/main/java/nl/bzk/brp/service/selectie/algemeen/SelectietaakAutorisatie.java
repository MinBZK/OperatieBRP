/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.algemeen;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Selectietaak;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;

/**
 * Valueholder voor een {@link Selectietaak} en een {@link ToegangLeveringsAutorisatie}
 */
public class SelectietaakAutorisatie {

    private Selectietaak selectietaak;
    private ToegangLeveringsAutorisatie toegangLeveringsAutorisatie;

    /**
     * Constructor.
     *
     * @param selectietaak de {@link Selectietaak}
     * @param toegangLeveringsAutorisatie de {@link ToegangLeveringsAutorisatie}
     */
    public SelectietaakAutorisatie(final Selectietaak selectietaak, final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie) {
        this.selectietaak = selectietaak;
        this.toegangLeveringsAutorisatie = toegangLeveringsAutorisatie;
    }

    public Selectietaak getSelectietaak() {
        return selectietaak;
    }

    public ToegangLeveringsAutorisatie getToegangLeveringsAutorisatie() {
        return toegangLeveringsAutorisatie;
    }
}
