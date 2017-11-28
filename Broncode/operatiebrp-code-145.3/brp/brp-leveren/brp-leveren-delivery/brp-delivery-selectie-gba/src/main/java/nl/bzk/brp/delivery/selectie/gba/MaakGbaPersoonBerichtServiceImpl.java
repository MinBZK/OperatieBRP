/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.selectie.gba;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Blokkering;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;
import nl.bzk.brp.delivery.selectie.gba.berichten.Ag11BerichtFactory;
import nl.bzk.brp.delivery.selectie.gba.berichten.Sf01BerichtFactory;
import nl.bzk.brp.delivery.selectie.gba.berichten.Sv01BerichtFactory;
import nl.bzk.brp.delivery.selectie.gba.berichten.Sv11BerichtFactory;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.service.algemeen.MaakPersoonBerichtService;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.dalapi.BlokkeringRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Service voor het genereren van een Sv01 bericht op basis van een VerwerkPersoonBericht.
 */
@Service(value = "maakGbaPersoonBerichtService")
public class MaakGbaPersoonBerichtServiceImpl implements MaakPersoonBerichtService {

    private Ag11BerichtFactory ag11BerichtFactory;
    private BlokkeringRepository blokkeringRepository;
    private Sf01BerichtFactory sf01BerichtFactory;
    private Sv01BerichtFactory sv01BerichtFactory;
    private Sv11BerichtFactory sv11BerichtFactory;

    /**
     * Constructor.
     * @param ag11BerichtFactory Ag11 bericht factory
     * @param sf01BerichtFactory Sf01 bericht factory
     * @param sv01BerichtFactory Sv01 bericht factory
     * @param sv11BerichtFactory Sv11 bericht factory
     * @param blokkeringRepository blokkering repository
     */
    @Inject
    public MaakGbaPersoonBerichtServiceImpl(final Ag11BerichtFactory ag11BerichtFactory, final BlokkeringRepository blokkeringRepository,
                                            final Sf01BerichtFactory sf01BerichtFactory, final Sv01BerichtFactory sv01BerichtFactory,
                                            final Sv11BerichtFactory sv11BerichtFactory) {
        this.ag11BerichtFactory = ag11BerichtFactory;
        this.blokkeringRepository = blokkeringRepository;
        this.sf01BerichtFactory = sf01BerichtFactory;
        this.sv01BerichtFactory = sv01BerichtFactory;
        this.sv11BerichtFactory = sv11BerichtFactory;
    }

    @Override
    public String maakPersoonBericht(final VerwerkPersoonBericht verwerkPersoonBericht) throws StapException {
        final BijgehoudenPersoon persoon =
                verwerkPersoonBericht.getBijgehoudenPersonen().stream().findFirst()
                        .orElse(null);

        if (persoon == null) {
            return sv11BerichtFactory.maakSv11Bericht();
        }

        if (SoortSelectie.SELECTIE_MET_PLAATSING_AFNEMERINDICATIE.getId() == verwerkPersoonBericht.getAutorisatiebundel().getDienst().getSoortSelectie()) {
            Dienst
                    spontaanDienst =
                    AutAutUtil.zoekDienst(verwerkPersoonBericht.getAutorisatiebundel().getLeveringsautorisatie(),
                            SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE);
            return ag11BerichtFactory.maakAg11Bericht(spontaanDienst, persoon.getPersoonslijst());
        }

        final String resultaatBericht;

        // Zoek blokkering op basis van a-nummer op.
        Blokkering blokkering = blokkeringRepository.getBlokkeringOpANummer(persoon.getPersoonslijst().<String>getActueleAttribuutWaarde(
                ElementHelper.getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER))
                .orElseThrow(() -> new IllegalStateException("A Nummer mag niet leeg zijn")));
        // Indien geblokkeerd wordt een Sf01 verstuurd.
        if (blokkering != null) {
            resultaatBericht = sf01BerichtFactory.maakSf01Bericht(persoon.getPersoonslijst(), blokkering.getRegistratieGemeente());
        } else {

            final ToegangLeveringsAutorisatie toegang = verwerkPersoonBericht.getAutorisatiebundel().getToegangLeveringsautorisatie();
            final Dienst dienstSelectie = AutAutUtil.zoekDienst(toegang.getLeveringsautorisatie(), SoortDienst.SELECTIE);
            Assert.notNull(dienstSelectie, "Dienst selectie kon niet gevonden worden in de toegangleveringsautorisatie.");

            resultaatBericht = sv01BerichtFactory.maakSv01Bericht(dienstSelectie, persoon.getPersoonslijst());
        }
        return resultaatBericht;
    }
}
