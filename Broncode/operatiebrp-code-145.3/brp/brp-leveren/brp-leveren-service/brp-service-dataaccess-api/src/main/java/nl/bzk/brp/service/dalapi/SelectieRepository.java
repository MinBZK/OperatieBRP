/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.dalapi;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Selectierun;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Selectietaak;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SelectietaakStatus;

/**
 * DAL tbv Selectie op master
 */
public interface SelectieRepository {

    /**
     * Geeft een lijst {@link Selectietaak} objecten, welke vandaag ingepland
     * zijn met status {@link SelectietaakStatus#UITVOERBAAR}.
     * @return een lijst
     */
    List<Selectietaak> getTakenGeplandVoorVandaag();

    /**
     * @param selectietaakId selectietaakId
     * @return Selectietaak
     */
    Selectietaak haalSelectietaakOp(int selectietaakId);

    /**
     * Persist de staat van de selectierun met de bijbehorende selectietaken.
     * @param selectierun de {@link Selectierun}
     */
    void slaSelectieOp(Selectierun selectierun);

    /**
     * @param selectierun de {@link Selectierun}
     */
    void werkSelectieBij(Selectierun selectierun);

    /**
     * Update de selectietaak.
     *
     * @param selectietaak de selectietaak.
     */
    void slaSelectietaakOp(Selectietaak selectietaak);

    /**
     * Geeft een lijst van {@link Selectietaak}en met status {@link SelectietaakStatus#TE_PROTOCOLLEREN}
     * @return een lijst selectietaken
     */
    List<Selectietaak> getSelectietakenMetStatusTeProtocolleren();


}
