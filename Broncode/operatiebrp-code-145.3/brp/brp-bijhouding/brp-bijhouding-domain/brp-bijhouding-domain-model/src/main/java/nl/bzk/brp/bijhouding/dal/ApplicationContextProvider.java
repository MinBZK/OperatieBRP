/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.dal;

import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutelService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Helper class voor het verkrijgen van de application context van brp-bijhouding-dal.
 */
public final class ApplicationContextProvider implements ApplicationContextAware {

    private static final ContextHolder contextHolder = new ContextHolder();

    /**
     * returns spring context bean DynamischeStamtabelRepository.
     * @return DynamischeStamtabelRepository
     */
    public static DynamischeStamtabelRepository getDynamischeStamtabelRepository() {
        return contextHolder.context.getBean(DynamischeStamtabelRepository.class);
    }

    /**
     * returns spring context bean PersoonRepository.
     * @return PersoonRepository
     */
    public static PersoonRepository getPersoonRepository() {
        return contextHolder.context.getBean(PersoonRepository.class);
    }

    /**
     * returns spring context bean PersoonCacheRepository.
     * @return PersoonCacheRepository
     */
    public static PersoonCacheRepository getPersoonCacheRepository() {
        return contextHolder.context.getBean(PersoonCacheRepository.class);
    }

    /**
     * returns spring context bean RelatieRepository.
     * @return RelatieRepository
     */
    public static RelatieRepository getRelatieRepository() {
        return contextHolder.context.getBean(RelatieRepository.class);
    }

    /**
     * returns spring context bean BetrokkenheidRepository.
     * @return BetrokkenheidRepository
     */
    public static BetrokkenheidRepository getBetrokkenheidRepository() {
        return contextHolder.context.getBean(BetrokkenheidRepository.class);
    }

    /**
     * returns spring context bean OnderzoekRepository.
     * @return OnderzoekRepository
     */
    public static OnderzoekRepository getOnderzoekRepository() {
        return contextHolder.context.getBean(OnderzoekRepository.class);
    }

    /**
     * returns spring context bean ObjectSleutelService.
     * @return ObjectSleutelService
     */
    public static ObjectSleutelService getObjectSleutelService() {
        return contextHolder.context.getBean(ObjectSleutelService.class);
    }

    /**
     * Set de application context.
     * @param applicationContext de application context
     * @throws BeansException wanneer de application context niet goed gelezen kan worden
     */
    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) {
        contextHolder.context = applicationContext;
    }

    /**
     * Class wat als context holder dient om een de applicatie context static te kunnen benaderen.
     */
    private static class ContextHolder {
        private ApplicationContext context;
    }
}
