/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.service.datatoegang;

import java.util.function.Consumer;
import java.util.function.Function;
import javax.persistence.EntityManager;

/**
 * Een verzoek obv {@link EntityManager}
 */
public interface EntityManagerVerzoek {

    /**
     * Een verzoek zonder commit.
     *
     * @param entityManagerConsumer de consumer die gebruik maakt van de entitymanager
     * @return een verzoekresultaat
     */
    void voerUit(Consumer<EntityManager> entityManagerConsumer);

    /**
     *  Een verzoek zonder commit.
     *
     * @param function de consumer die gebruik maakt van de entitymanager
     * @return een verzoekresultaat
     */
    <T> T voerUitEnReturn(Function<EntityManager, T> function);

    /**
     * Een transactioneel verzoek.
     *
     * @param entityManagerConsumer de consumer die gebruik maakt van de entitymanager
     * @return een verzoekresultaat
     */
    void voerUitTransactioneel(Consumer<EntityManager> entityManagerConsumer);

}
