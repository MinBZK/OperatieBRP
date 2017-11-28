/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.selectie.schrijver.gba;

import java.util.Collections;
import java.util.UUID;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.domain.internbericht.selectie.MaakSelectieResultaatTaak;
import nl.bzk.brp.domain.internbericht.verzendingmodel.AfnemerBericht;
import nl.bzk.brp.domain.internbericht.verzendingmodel.SynchronisatieBerichtGegevens;
import nl.bzk.brp.service.algemeen.PlaatsAfnemerBerichtService;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.algemeen.SynchronisatieBerichtGegevensFactory;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import nl.bzk.brp.service.cache.LeveringsAutorisatieCache;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Writer voor Geen resultaat bericht (Sv11) GBA Netwerk.
 */
@Component
public class SelectieGeenResultaatGbaBerichtWriter {


    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final PlaatsAfnemerBerichtService plaatsAfnemerBerichtService;
    private final LeveringsAutorisatieCache autorisatieCache;
    private final PartijService partijService;
    private final SynchronisatieBerichtGegevensFactory synchronisatieBerichtGegevensFactory;

    /**
     * Default constructor
     * @param plaatsAfnemerBerichtService de plaatsafnemerindicatie bericht service
     * @param autorisatieCache de autorisatie cache
     * @param partijService de partij service
     * @param synchronisatieBerichtGegevensFactory de synchronisatie bericht gegevens factory
     */
    @Inject
    public SelectieGeenResultaatGbaBerichtWriter(final PlaatsAfnemerBerichtService plaatsAfnemerBerichtService,
                                                 final LeveringsAutorisatieCache autorisatieCache,
                                                 final PartijService partijService,
                                                 final SynchronisatieBerichtGegevensFactory synchronisatieBerichtGegevensFactory) {
        this.autorisatieCache = autorisatieCache;
        this.partijService = partijService;
        this.plaatsAfnemerBerichtService = plaatsAfnemerBerichtService;
        this.synchronisatieBerichtGegevensFactory = synchronisatieBerichtGegevensFactory;
    }

    /**
     * Verstuur een Sv11 bericht naar de afnemer.
     * @param maakSelectieResultaatTaak De selectie resultaat taak waarvoor de sv11 wordt gemaakt.
     */
    @Transactional(transactionManager = "masterTransactionManager")
    public void verstuurSv11Bericht(final MaakSelectieResultaatTaak maakSelectieResultaatTaak) {

        Dienst dienst = autorisatieCache.geefDienst(maakSelectieResultaatTaak.getDienstId());
        ToegangLeveringsAutorisatie toegang = autorisatieCache.geefToegangLeveringsautorisatie(maakSelectieResultaatTaak.getToegangLeveringsAutorisatieId());
        Autorisatiebundel autorisatiebundel = new Autorisatiebundel(toegang, dienst);

        try {
            final AfnemerBericht sv11Bericht = new AfnemerBericht(maak(maakSelectieResultaatTaak, autorisatiebundel), toegang);
            plaatsAfnemerBerichtService.plaatsAfnemerberichten(Collections.singletonList(sv11Bericht));
            LOGGER.info("Sv11 verstuurd.");
        } catch (StapException e) {
            LOGGER.error("error on message", e);
        }
    }

    private SynchronisatieBerichtGegevens maak(final MaakSelectieResultaatTaak maakSelectieResultaatTaak, final Autorisatiebundel autorisatiebundel)
            throws StapException {
        // Maak een verwerkpersoon bericht aan om de algemene functionaliteit te kunnen gebruiken.
        return synchronisatieBerichtGegevensFactory
                .maak(maakVerwerkPersoonBericht(autorisatiebundel), autorisatiebundel, maakSelectieResultaatTaak.getPeilmomentMaterieelResultaat());
    }

    private VerwerkPersoonBericht maakVerwerkPersoonBericht(final Autorisatiebundel autorisatiebundel) {
        final Partij zendendePartij = partijService.geefBrpPartij();
        final Partij ontvangendePartij = autorisatiebundel.getPartij();
        //@formatter:off
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
            .metParameters()
                .metSoortSynchronisatie(SoortSynchronisatie.VOLLEDIG_BERICHT)
                .metDienst(autorisatiebundel.getDienst())
            .eindeParameters()
            .metTijdstipRegistratie(DatumUtil.nuAlsZonedDateTime())
            .metPartijCode(ontvangendePartij.getCode())
            .metStuurgegevens()
                .metReferentienummer(UUID.randomUUID().toString())
                .metTijdstipVerzending(DatumUtil.nuAlsZonedDateTime())
                .metZendendePartij(zendendePartij)
                .metZendendeSysteem(BasisBerichtGegevens.BRP_SYSTEEM)
                .metOntvangendePartij(ontvangendePartij)
            .eindeStuurgegevens()
        .build();
        //@formatter:on
        return new VerwerkPersoonBericht(basisBerichtGegevens, autorisatiebundel, Collections.emptyList());
    }
}
