/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.algemeen;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Selectierun;

/**
 * Valueobject van de selectierun
 */
public class Selectie {

    private Selectierun selectierun;
    private List<SelectietaakAutorisatie> selectietaakAutorisatieList;

    /**
     * Constructor.
     * @param selectierun de {@link Selectierun selectierun}
     * @param selectietaakAutorisatieList de lijst van {@link SelectietaakAutorisatie selectietaak autorisaties}
     */
    public Selectie(final Selectierun selectierun,
                    final List<SelectietaakAutorisatie> selectietaakAutorisatieList) {
        this.selectierun = selectierun;
        this.selectietaakAutorisatieList = selectietaakAutorisatieList;
    }

    public Selectierun getSelectierun() {
        return selectierun;
    }

    public List<SelectietaakAutorisatie> getSelectietaakAutorisatieList() {
        return selectietaakAutorisatieList;
    }

    /**
     * Geeft aan of de selectie uitvoerbaar is of niet.
     * @return true als uitvoerbaar, anders false
     */
    public boolean isUitvoerbaar() {
        return !selectierun.getSelectieTaken().isEmpty() && !(selectietaakAutorisatieList == null || selectietaakAutorisatieList.isEmpty());
    }
}
