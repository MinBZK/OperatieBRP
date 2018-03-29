/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.VerstrekkingsbeperkingService;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtParameters;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtPersoonInformatie;
import nl.bzk.brp.service.maakbericht.bepaling.StatischePersoongegevens;
import nl.bzk.brp.service.maakbericht.bepaling.StatischePersoongegevensBepalingService;
import nl.bzk.brp.service.maakbericht.filterstappen.MaakBerichtStap;
import nl.bzk.brp.service.maakbericht.filterstappen.Stappenlijst;
import org.springframework.stereotype.Service;


/**
 * Implementatie van {@link VerwerkPersoonBerichtFactory}. Voert alle maakbericht stappen uit.
 */
@Service
final class VerwerkPersoonBerichtFactoryImpl implements VerwerkPersoonBerichtFactory {

    @Inject
    private Stappenlijst stappenlijst;

    @Inject
    private BijgehoudenPersoonFactory bijgehoudenPersoonFactory;

    @Inject
    private StatischePersoongegevensBepalingService statischePersoonGegevensBepalingService;

    @Inject
    private VerstrekkingsbeperkingService verstrekkingsbeperkingService;

    private VerwerkPersoonBerichtFactoryImpl() {
    }

    @Override
    public List<VerwerkPersoonBericht> maakBerichten(final MaakBerichtParameters maakBerichtParameters) {
        //bepaal statische gegevens voor de bijgehouden personen
        final Map<Persoonslijst, StatischePersoongegevens> statischePersoonGegevensMap = new HashMap<>();
        for (Persoonslijst persoonslijst : maakBerichtParameters.getBijgehoudenPersonen()) {
            final StatischePersoongegevens statischePersoongegevens = statischePersoonGegevensBepalingService.bepaal(persoonslijst,
                    maakBerichtParameters.getAdministratieveHandeling() != null);
            statischePersoonGegevensMap.put(persoonslijst, statischePersoongegevens);
        }
        //maak berichten voor elke persoon voor alle autorisatiebundels
        final Map<Autorisatiebundel, List<BijgehoudenPersoon>> bijgehoudenPersoonListMap = new HashMap<>();
        for (Autorisatiebundel autorisatiebundel : maakBerichtParameters.getAutorisatiebundels()) {
            final List<Berichtgegevens> berichtgegevensLijst = new ArrayList<>();
            for (Persoonslijst persoonslijst : maakBerichtParameters.getBijgehoudenPersonen()) {
                final Map<Persoonslijst, MaakBerichtPersoonInformatie> maakBerichtPersoonMap = maakBerichtParameters.getMaakBerichtPersoonMap()
                        .get(autorisatiebundel);
                if (maakBerichtPersoonMap == null || maakBerichtPersoonMap.get(persoonslijst) == null) {
                    //helemaal geen personen in deze set
                    continue;
                }
                final MaakBerichtPersoonInformatie maakBerichtPersoon = maakBerichtPersoonMap.get(persoonslijst);
                final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, persoonslijst, maakBerichtPersoon, autorisatiebundel,
                        statischePersoonGegevensMap.get(persoonslijst));
                berichtgegevens
                        .setMutatieberichtMetMeldingVerstrekkingsbeperking(isMutatieberichtMetMeldingVerstrekkingsbeperking(autorisatiebundel, persoonslijst));
                berichtgegevensLijst.add(berichtgegevens);
                for (final MaakBerichtStap stap : stappenlijst.getStappen()) {
                    stap.execute(berichtgegevens);
                }
            }
            final List<BijgehoudenPersoon> bijgehoudenPersoonList = bijgehoudenPersoonFactory.maakBijgehoudenPersonen(berichtgegevensLijst);
            bijgehoudenPersoonListMap.put(autorisatiebundel, bijgehoudenPersoonList);

        }
        //zet om in berichten
        return maakBerichtParameters.getBijgehoudenPersoonBerichtDecorator().build(bijgehoudenPersoonListMap);
    }

    private boolean isMutatieberichtMetMeldingVerstrekkingsbeperking(final Autorisatiebundel autorisatiebundel, final Persoonslijst persoonslijst) {
        if (autorisatiebundel.getDienst().getSoortDienst() == SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE) {
            final Partij partij = autorisatiebundel.getToegangLeveringsautorisatie().getGeautoriseerde().getPartij();
            return verstrekkingsbeperkingService.heeftGeldigeVerstrekkingsbeperking(persoonslijst.getNuNuBeeld(), partij);
        }
        return false;
    }

}
