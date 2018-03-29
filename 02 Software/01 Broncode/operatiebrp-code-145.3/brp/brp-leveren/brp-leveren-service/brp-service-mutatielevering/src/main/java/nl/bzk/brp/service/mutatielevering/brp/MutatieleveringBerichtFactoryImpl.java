/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering.brp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Populatie;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.maakbericht.VerwerkPersoonBerichtFactory;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtParameters;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtPersoonInformatie;
import nl.bzk.brp.service.mutatielevering.algemeen.SoortSynchronisatieBepaler;
import nl.bzk.brp.service.mutatielevering.dto.Mutatiehandeling;
import nl.bzk.brp.service.mutatielevering.dto.Mutatielevering;
import org.springframework.stereotype.Component;

/**
 * Maakt de berichten voor mutatielevering.
 */
@Component
final class MutatieleveringBerichtFactoryImpl implements MutatieleveringBerichtFactory {

    @Inject
    private VerwerkPersoonBerichtFactory stappenlijstUitvoerService;
    @Inject
    private SoortSynchronisatieBepaler soortSynchronisatieBepaler;
    @Inject
    private VerwerkPersoonBerichtBuilderService verwerkPersoonBerichtBuildService;

    private MutatieleveringBerichtFactoryImpl() {

    }

    @Override
    public List<VerwerkPersoonBericht> apply(final List<Mutatielevering> leveringList,
                                             final Mutatiehandeling mutatiehandeling) {
        //maak parameter object
        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        maakBerichtParameters.setAdministratieveHandelingId(mutatiehandeling.getAdministratieveHandelingId());
        final Map<Autorisatiebundel, Map<Persoonslijst, MaakBerichtPersoonInformatie>> maakBerichtPersoonMap = new HashMap<>();
        maakBerichtParameters.setMaakBerichtPersoonMap(maakBerichtPersoonMap);
        //alle autorisatiebundels voor dit verzoek
        final List<Autorisatiebundel> autorisatiebundels = new ArrayList<>(leveringList.size());
        //maak lijst van alle bijgehouden personen
        final List<Persoonslijst> bijgehoudenPersonen = new ArrayList<>();
        for (Persoonslijst mutatiehandelingPersoon : mutatiehandeling.getPersonen()) {
            bijgehoudenPersonen.add(mutatiehandelingPersoon.getNuNuBeeld());
        }
        //de map met populatie voor personen per persoon. Let op. Personen die geleverd worden. Dit hoeven niet alle bijgehouden personen te zijn.
        final Map<Autorisatiebundel, Map<Persoonslijst, Populatie>> teLeverenPersonenMapVoorAutorisatie = new HashMap<>();
        for (Mutatielevering mutatielevering : leveringList) {
            if (Stelsel.BRP != mutatielevering.getStelsel()) {
                continue;
            }
            autorisatiebundels.add(mutatielevering.getAutorisatiebundel());
            final Map<Persoonslijst, MaakBerichtPersoonInformatie> maakBerichtPersonen = maakMaakBerichtPersonen(mutatielevering);
            maakBerichtPersoonMap.put(mutatielevering.getAutorisatiebundel(), maakBerichtPersonen);
            teLeverenPersonenMapVoorAutorisatie.put(mutatielevering.getAutorisatiebundel(), mutatielevering.getTeLeverenPersonenMap());
        }
        maakBerichtParameters.setAutorisatiebundels(autorisatiebundels);
        maakBerichtParameters.setBijgehoudenPersonen(bijgehoudenPersonen);
        //zet callback method voor verrijken berichten
        maakBerichtParameters.setBijgehoudenPersoonBerichtDecorator(
                bijgehoudenPersoonList -> verwerkPersoonBerichtBuildService.maakBerichten(mutatiehandeling.getAdministratieveHandelingId(),
                        bijgehoudenPersoonList,
                        teLeverenPersonenMapVoorAutorisatie));
        //maak berichten
        return stappenlijstUitvoerService.maakBerichten(maakBerichtParameters);
    }

    private Map<Persoonslijst, MaakBerichtPersoonInformatie> maakMaakBerichtPersonen(final Mutatielevering mutatielevering) {
        final Map<Persoonslijst, MaakBerichtPersoonInformatie> maakBerichtPersonen = new HashMap<>();
        for (final Map.Entry<Persoonslijst, Populatie> entry : mutatielevering.getTeLeverenPersonenMap().entrySet()) {
            final Persoonslijst persoonslijst = entry.getKey();
            final AdministratieveHandeling administratieveHandeling = persoonslijst.getAdministratieveHandeling();
            final SoortAdministratieveHandeling soortHandeling = administratieveHandeling.getSoort();
            final SoortSynchronisatie soortSynchronisatie = soortSynchronisatieBepaler
                    .bepaalSoortSynchronisatie(entry.getValue(), mutatielevering.getAutorisatiebundel().getSoortDienst(), soortHandeling);
            final Integer datumAanvangMaterielePeriode = bepaalDatumAanvangMaterielePeriode(mutatielevering.getAutorisatiebundel(), persoonslijst);
            final MaakBerichtPersoonInformatie maakBerichtPersoon = new MaakBerichtPersoonInformatie(soortSynchronisatie);
            maakBerichtPersoon.setDatumAanvangmaterielePeriode(datumAanvangMaterielePeriode);
            maakBerichtPersonen.put(persoonslijst, maakBerichtPersoon);
        }
        return maakBerichtPersonen;
    }

    private Integer bepaalDatumAanvangMaterielePeriode(final Autorisatiebundel autorisatiebundel,
                                                       final Persoonslijst persoonslijst) {
        Integer datumAanvangMaterielePeriode = null;
        if (autorisatiebundel.getSoortDienst() == SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE) {
            datumAanvangMaterielePeriode = persoonslijst
                    .getDatumAanvangMaterielePeriodeVanAfnemerindicatie(autorisatiebundel.getToegangLeveringsautorisatie()
                            .getLeveringsautorisatie().getId());
        }
        return datumAanvangMaterielePeriode;
    }
}
