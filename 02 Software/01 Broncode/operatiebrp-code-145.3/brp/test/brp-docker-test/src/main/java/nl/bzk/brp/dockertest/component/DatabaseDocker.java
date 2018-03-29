/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

import nl.bzk.brp.dockertest.service.datatoegang.EntityManagerVerzoek;
import nl.bzk.brp.dockertest.service.datatoegang.TemplateVerzoek;
import nl.bzk.brp.dockertest.service.gbasync.PersoonUitExcelHelper;
import org.springframework.context.support.AbstractRefreshableConfigApplicationContext;
import org.springframework.core.io.Resource;

/**
 * Interface voor database dockers.
 */
public interface DatabaseDocker extends Docker {

    /**
     * @return een {@link EntityManagerVerzoek}
     */
    EntityManagerVerzoek entityManagerVerzoek();

    AbstractRefreshableConfigApplicationContext getApplicationContext();

    /**
     * @return een {@link TemplateVerzoek}
     */
    TemplateVerzoek template();

    /**
     * Optional interface voor het supporten van excelbestanden met persoonsgegevens.
     */
    interface ExcelSupport extends DatabaseDocker {

        /**
         * Voert een initiele vulling uit met eventueel een aantal synchronisaties (mutaties)
         * @param resources lijst met resources
         */
        default void converteerExcel(Resource... resources) {
            getPersoonUitExcelHelper().converteerExcel(resources);
        }

        /**
         * Voer een synchronisatie uit (mutatie)
         * @param resources lijst met resources
         */
        default void synchroniseer(Resource... resources) {
            getPersoonUitExcelHelper().sync(resources);
        }

        /**
         * @return help klasse
         */
        PersoonUitExcelHelper getPersoonUitExcelHelper();
    }

    /**
     * Laad een databasedump ahv een .backup bestand.
     * @param database database naam
     * @param bestand .backup bestand
     * @param logischeNaam {@link DockerNaam}
     */
    void laadDump(final String database, final String bestand, final DockerNaam logischeNaam);
}
