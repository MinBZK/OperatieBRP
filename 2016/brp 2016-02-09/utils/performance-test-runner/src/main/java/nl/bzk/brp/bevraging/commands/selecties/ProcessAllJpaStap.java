/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.commands.selecties;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.google.common.base.Optional;
import nl.bzk.brp.bevraging.app.ContextParameterNames;
import nl.bzk.brp.bevraging.commands.BevraagInfo;
import nl.bzk.brp.bevraging.dataaccess.PersoonLeveringService;
import nl.bzk.brp.bevraging.dataaccess.jpa.PersoonCacheJpaRepository;
import nl.bzk.brp.model.operationeel.kern.PersoonCacheModel;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class ProcessAllJpaStap implements Command {
    final Integer PAGE_SIZE = 200;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessAllJpaStap.class);

    @Inject
    private PersoonCacheJpaRepository cacheJpaRepository;

    @Inject
    private PersoonLeveringService leveringService;

    @Override
    public boolean execute(final Context context) throws Exception {
        long numberOfCaches = cacheJpaRepository.count();
        long pageCount = numberOfCaches / PAGE_SIZE + (numberOfCaches % PAGE_SIZE != 0 ? 1 : 0);

        LOGGER.info("{} blobs in {} paginas", numberOfCaches, pageCount);

        List<BevraagInfo> resultaten = new ArrayList<BevraagInfo>();

        try {
            for (int page = 0; page <= pageCount; page++) {
                LOGGER.debug("pagina: {}", page);
                Pageable pageable = new PageRequest(page, PAGE_SIZE);

                Page<PersoonCacheModel> caches = cacheJpaRepository.findAll(pageable);

                for (PersoonCacheModel model : caches) {
                    long startTimeMillis = System.currentTimeMillis();

                    Optional<Boolean> geleverd = leveringService.leverPersoon(model.getStandaard().getPersoonHistorieVolledigGegevens().getWaarde());

                    long duration = System.currentTimeMillis() - startTimeMillis;

                    if (geleverd.isPresent()) {
                        resultaten.add(new BevraagInfo("", ("OK"), duration));
                    } else {
                        resultaten.add(new BevraagInfo("", ("FAIL"), duration));
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Exception", e);
        }

        context.put(ContextParameterNames.TASK_INFO_LIJST, resultaten);

        return CONTINUE_PROCESSING;
    }
}
