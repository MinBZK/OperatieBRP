/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.commands.util;

import javax.inject.Inject;

import nl.bzk.brp.bevraging.app.ContextParameterNames;
import nl.bzk.brp.dataaccess.repository.PersoonCacheRepository;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.PersoonCacheModel;
import nl.bzk.brp.serialisatie.persoon.PersoonHisVolledigSmileSerializer;
import nl.bzk.brp.serialisatie.persoon.PersoonHisVolledigStringSerializer;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Stap die een binary blob uit de database leest en afdrukt als JSON string.
 */
@Component
public class LeesBlobStap implements Command {
    public static final Logger LOGGER = LoggerFactory.getLogger(LeesBlobStap.class);

    @Inject
    private PersoonCacheRepository persoonCacheRepository;

    private PersoonHisVolledigSmileSerializer blobSerializer = new PersoonHisVolledigSmileSerializer();
    private PersoonHisVolledigStringSerializer jsonSerializer = new PersoonHisVolledigStringSerializer();

    @Override
    public boolean execute(final Context context) throws Exception {
        Integer id = (Integer) context.get(ContextParameterNames.PERSOON_ID);

        PersoonCacheModel cache = persoonCacheRepository.haalPersoonCacheOp(id);

        PersoonHisVolledigImpl persoon =
                blobSerializer.deserializeer(cache.getStandaard().getPersoonHistorieVolledigGegevens().getWaarde());

        LOGGER.info("Persoon met id [{}]:\n{}", id, new String(jsonSerializer.serializeer(persoon)));

        return PROCESSING_COMPLETE;
    }
}
