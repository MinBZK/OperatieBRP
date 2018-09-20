/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.persoon.impl;

import java.util.Arrays;
import javax.inject.Inject;

import nl.bzk.brp.blobifier.repository.alleenlezen.LeesPersoonCacheRepository;
import nl.bzk.brp.blobifier.service.BlobifierService;
import nl.bzk.brp.model.logisch.kern.PersoonCache;
import nl.bzk.brp.model.operationeel.kern.PersoonCacheModel;
import nl.bzk.brp.serialisatie.persoon.PersoonSerialiseerder;
import nl.bzk.brp.vergrendeling.SleutelVergrendelaar;
import nl.bzk.brp.vergrendeling.VergrendelFout;
import org.perf4j.StopWatch;
import org.perf4j.slf4j.Slf4JStopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service die op basis van een gegeven persoonsleutel de persoon omzet naar een blob en opslaat in de persoon cache.
 */
@Service
@Transactional
public class PersoonSerialiseerderImpl implements PersoonSerialiseerder {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersoonSerialiseerderImpl.class);

    @Inject
    private LeesPersoonCacheRepository leesPersoonCacheRepository;

    @Inject
    private BlobifierService blobifierService;

    @Inject
    private SleutelVergrendelaar sleutelVergrendelaar;

    @Value("${persoonserialisatienotificatie.overschijfbestaandecaches}")
    private boolean overschrijfBestaandeCaches;


    /**
     * Bepaalt op basis van of de cache al bestaat en of deze overschreven moet worden of de cache gemaakt moet worden.
     *
     * @param persoonSleutel De persoon id.
     * @param persoonCache   De persoon cache uit de database.
     * @return Boolean true als de cache (opnieuw) gemaakt moet worden, anders false.
     */
    private boolean bepaalOfCacheGemaaktOfOverschrevenMoetWorden(final Integer persoonSleutel,
                                                                 final PersoonCache persoonCache)
    {
        final boolean maakNieuweCache;
        if (persoonCache != null
                && persoonCache.getStandaard().getPersoonHistorieVolledigGegevens() != null
                && persoonCache.getStandaard().getPersoonHistorieVolledigGegevens().heeftWaarde())
        {
            if (overschrijfBestaandeCaches) {
                LOGGER.debug(
                        "Er bestond reeds een blob in de cache, deze wordt vervangen. "
                                + "De checksum van de oude cache is {}, versie {}",
                        persoonCache.getStandaard().getPersoonHistorieVolledigChecksum(),
                        persoonCache.getStandaard().getVersienummer());
                maakNieuweCache = true;
            } else {
                LOGGER.debug("Er bestond reeds een blob in de cache voor persoon met sleutel {}, "
                                + "deze wordt overgeslagen.",
                        persoonSleutel);
                maakNieuweCache = false;
            }
        } else {
            maakNieuweCache = true;
        }
        return maakNieuweCache;
    }

    /**
     * Maakt een persooncache in de database.
     *
     * @param persoonSleutel De persoon id.
     */
    private void maakPersoonCache(final Integer persoonSleutel) {
        blobifierService.blobify(persoonSleutel, false);
    }

    @Override
    public final void serialiseerPersoon(final Integer persoonSleutel) throws VergrendelFout {
        final StopWatch stopWatch = new Slf4JStopWatch();

        if (null == persoonSleutel) {
            throw new IllegalArgumentException("persoon sleutel moet ingevuld zijn. Er is nu niets te serialiseren.");
        }
        LOGGER.debug("Vergrendel de sleutel en haal persoon op met sleutel {}", persoonSleutel);
        sleutelVergrendelaar.vergrendel(Arrays.asList(persoonSleutel),
                null,
                SleutelVergrendelaar.VergrendelMode.EXCLUSIEF);

        stopWatch.lap("vergrendelen");

        //Check of blob al bestaat
        final PersoonCacheModel persoonCache = leesPersoonCacheRepository.haalPersoonCacheOp(persoonSleutel);
        final boolean maakNieuweCache = bepaalOfCacheGemaaktOfOverschrevenMoetWorden(persoonSleutel, persoonCache);

        if (maakNieuweCache) {
            maakPersoonCache(persoonSleutel);

            stopWatch.stop("blobben");
        } else {
            stopWatch.stop("nietblobben");
        }

    }


}
