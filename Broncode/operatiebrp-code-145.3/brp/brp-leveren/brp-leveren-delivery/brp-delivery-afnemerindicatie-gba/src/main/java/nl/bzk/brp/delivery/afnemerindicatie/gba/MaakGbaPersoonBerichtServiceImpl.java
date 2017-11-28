/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.afnemerindicatie.gba;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.delivery.afnemerindicatie.gba.berichten.Ag01BerichtFactory;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.service.algemeen.MaakPersoonBerichtService;
import nl.bzk.brp.service.algemeen.StapException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Service voor het genereren van een Ag01 bericht op basis van een VerwerkPersoonBericht.
 */
@Service
public class MaakGbaPersoonBerichtServiceImpl implements MaakPersoonBerichtService {

    private Ag01BerichtFactory ag01BerichtFactory;

    /**
     * Constructor.
     * @param ag01BerichtFactory Ag01 bericht factory
     */
    @Inject
    public MaakGbaPersoonBerichtServiceImpl(final Ag01BerichtFactory ag01BerichtFactory) {
        this.ag01BerichtFactory = ag01BerichtFactory;
    }

    @Override
    public String maakPersoonBericht(final VerwerkPersoonBericht verwerkPersoonBericht) throws StapException {
        final BijgehoudenPersoon persoon =
                verwerkPersoonBericht.getBijgehoudenPersonen().stream().findFirst()
                        .orElseThrow(() -> new IllegalStateException("Geen bijgehouden persoon gevonden."));

        final ToegangLeveringsAutorisatie toegang = verwerkPersoonBericht.getAutorisatiebundel().getToegangLeveringsautorisatie();
        final Dienst dienstSpontaan = AutAutUtil.zoekDienst(toegang.getLeveringsautorisatie(), SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE);
        Assert.notNull(dienstSpontaan, "Dienst spontaan kon niet gevonden worden in de toegangleveringsautorisatie.");

        return ag01BerichtFactory.maakAg01Bericht(dienstSpontaan, persoon.getPersoonslijst());
    }
}
