/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.lezer.job;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.service.algemeen.autorisatie.AutorisatieException;
import nl.bzk.brp.service.algemeen.autorisatie.AutorisatieUtil;
import nl.bzk.brp.service.algemeen.autorisatie.LeveringsautorisatieService;
import org.springframework.stereotype.Service;

/**
 * Implementatie van {@link SelectieAutorisatieService}.
 */
@Service
final class SelectieAutorisatieServiceImpl implements SelectieAutorisatieService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private LeveringsautorisatieService leveringsautorisatieService;

    private SelectieAutorisatieServiceImpl() {
    }

    @Bedrijfsregel(Regel.R2057)
    @Bedrijfsregel(Regel.R2574)
    @Override
    public boolean isAutorisatieGeldig(final ToegangLeveringsAutorisatie toegang, final Dienst dienst) {
        if (prevalideer(toegang, dienst)) {
            return false;
        }

        final Leveringsautorisatie leveringsautorisatie = toegang.getLeveringsautorisatie();
        final PartijRol geautoriseerde = toegang.getGeautoriseerde();
        try {
            AutorisatieUtil.assertDienstNietGeblokkeerd(dienst);
            AutorisatieUtil.assertDienstbundelNietGeblokkeerd(dienst.getDienstbundel());
            AutorisatieUtil.assertToegangLeveringsAutorisatieNietGeblokkeerd(toegang);
            AutorisatieUtil.assertLeveringsautorisieNietGeblokkeerd(leveringsautorisatie);
            AutorisatieUtil.assertDienstGeldig(dienst);
            AutorisatieUtil.assertDienstbundelGeldig(dienst.getDienstbundel());
            AutorisatieUtil.assertToegangLeveringsAutorisatieGeldig(toegang);
            AutorisatieUtil.assertLeveringsautorisieGeldig(leveringsautorisatie);
            AutorisatieUtil.assertPartijRolGeldig(geautoriseerde);
            AutorisatieUtil.assertPartijUitPartijRolGeldig(geautoriseerde);
            AutorisatieUtil.assertStelselCorrect(leveringsautorisatie, geautoriseerde.getPartij(), leveringsautorisatie.getStelsel() == Stelsel.BRP);
        } catch (AutorisatieException e) {
            LOGGER.info("Autorisatiefout voor toegang leveringsautorisatie {} en dienst {}: " + e, toegang.getId(), dienst.getId());
            return false;
        }
        return true;
    }

    private boolean prevalideer(ToegangLeveringsAutorisatie toegang, Dienst dienst) {
        if (toegang == null || dienst == null || dienst.getSoortDienst() != SoortDienst.SELECTIE) {
            //dit kan voorkomen als autorisatie gewijzigd is na inplannen
            LOGGER.warn("Autorisatie gewijzigd, niet meer geldig voor selectie");
            return true;
        }
        //valideer of dienst etc ook correct zijn geladen in cache. Hier zit bijvoorbeeeld cache expressie controle in
        if (!leveringsautorisatieService.bestaatDienst(dienst.getId())) {
            LOGGER.warn(String.format("dienst [%d] kan niet gevonden worden in cache", dienst.getId()));
            return true;
        }
        return false;
    }
}
