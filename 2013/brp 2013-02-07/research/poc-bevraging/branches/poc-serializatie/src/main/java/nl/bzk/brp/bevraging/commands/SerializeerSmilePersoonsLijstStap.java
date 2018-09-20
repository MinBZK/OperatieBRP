/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import javax.inject.Inject;

import nl.bzk.brp.bevraging.app.ContextParameterNames;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.blob.PlBlobOpslagplaats;
import nl.bzk.brp.dataaccess.repository.jpa.serialization.JacksonSmileBlobSerializer;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.pojo.PersoonHisModel;
import org.apache.commons.chain.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Stap die PersoonHisModel instanties (de)serializeert.
 */
@Component
public class SerializeerSmilePersoonsLijstStap extends AbstractAsynchroonStap {

    private static final Logger LOGGER = LoggerFactory.getLogger(SerializeerSmilePersoonsLijstStap.class);

    @Inject
    private PlBlobOpslagplaats blobOpslagplaats;

    @Inject
    private PersoonRepository persoonRepository;

    @Override
    public boolean doExecute(final Context context) throws Exception {
        blobOpslagplaats.setBlobSerializer(new JacksonSmileBlobSerializer());

        List<Integer> bsns = (List) context.get(ContextParameterNames.BSNLIJST);
        List<Future<BevraagInfo>> futures = new ArrayList<Future<BevraagInfo>>(bsns.size());
        for (Integer bsn : bsns) {
            LOGGER.info("ophalen persoon met BSN '{}'", bsn);
            PersoonModel persoon = persoonRepository.findByBurgerservicenummer(new Burgerservicenummer(bsn.toString()));
            futures.add(this.taskExecutor.submit(new LeesBlob(persoon.getId())));
        }

        List<BevraagInfo> results = new ArrayList<BevraagInfo>(bsns.size());
        for (Future<BevraagInfo> future : futures) {
            BevraagInfo info = future.get();
            results.add(info);
            LOGGER.info("Serializeer cyclus ID '{}' in {} ms", info.getTaskName(), info.getTimeMillis());
        }

        // opslaan resultaten
        context.put(ContextParameterNames.TASK_INFO_LIJST, results);

        return false;
    }

    /**
     * Lees blob.
     */
    class LeesBlob implements Callable<BevraagInfo> {
        private Integer id;

        /**
         * Constructor.
         * @param id de ID van de blob om te lezen.
         */
        public LeesBlob(final Integer id) {
            this.id = id;
        }

        @Override
        public BevraagInfo call() {
            long startTimeMillis = System.currentTimeMillis();
            boolean success = true;

            try {
                LOGGER.info("lezen hisModel: id = {}", this.id);
                PersoonHisModel model = blobOpslagplaats.leesPlBlob(id);
                LOGGER.info("heb hisModel: {}", model.getId());
            } catch (Exception e) {
                LOGGER.error("fout tijdens serializatie", e);
                success = false;
            }

            long duration = System.currentTimeMillis() - startTimeMillis;
            return new BevraagInfo(id.toString(), (success ? "OK" : "FAIL"), duration);
        }
    }
}
