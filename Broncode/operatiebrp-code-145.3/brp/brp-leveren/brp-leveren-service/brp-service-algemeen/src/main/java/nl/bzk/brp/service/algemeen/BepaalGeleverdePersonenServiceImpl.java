/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.protocollering.domain.algemeen.LeveringPersoon;
import org.springframework.stereotype.Service;

/**
 * Implementatie van {@link BepaalGeleverdePersonenService}.
 */
@Service
class BepaalGeleverdePersonenServiceImpl implements BepaalGeleverdePersonenService {

    @Override
    public Resultaat bepaal(final SoortDienst soortDienst, final int leveringsautorisatieId, final List<Persoonslijst> persoonslijstList,
                            final Integer datumAanvangMaterielePeriode) {
        final List<Long> geleverdePersoonIds = Lists.newArrayList();
        final Map<Long, ZonedDateTime> persoonIdTijdstipLaatsteWijzigingMap = Maps.newHashMap();
        final Map<Long, Integer> persoonIdDampMap = Maps.newHashMap();
        for (final Persoonslijst persoonslijst : persoonslijstList) {
            final Long id = persoonslijst.getId();
            final ZonedDateTime date = persoonslijst.getNuNuBeeld().bepaalTijdstipLaatsteWijziging();
            geleverdePersoonIds.add(id);
            persoonIdTijdstipLaatsteWijzigingMap.put(id, date);
            if (isDienstVanToepassing(soortDienst) && datumAanvangMaterielePeriode == null) {
                final Integer damp = persoonslijst.getDatumAanvangMaterielePeriodeVanAfnemerindicatie(leveringsautorisatieId);
                if (damp != null) {
                    persoonIdDampMap.put(id, damp);
                }
            }
        }

        final boolean heeftAlleenUniekeDamp = new HashSet<>(persoonIdDampMap.values()).size() == 1;
        final List<LeveringPersoon> leveringPersonen = Lists.newArrayList();
        for (final Long persoonId : geleverdePersoonIds) {
            leveringPersonen.add(new LeveringPersoon(persoonId, persoonIdTijdstipLaatsteWijzigingMap.get(persoonId),
                    !heeftAlleenUniekeDamp ? persoonIdDampMap.get(persoonId) : null));
        }

        Integer damp = null;
        if (heeftAlleenUniekeDamp) {
            damp = persoonIdDampMap.values().iterator().next();
        } else if (datumAanvangMaterielePeriode != null) {
            damp = datumAanvangMaterielePeriode;
        }

        return new Resultaat(damp, leveringPersonen, geleverdePersoonIds);
    }

    private boolean isDienstVanToepassing(final SoortDienst soortDienst) {
        return soortDienst == SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE
                || soortDienst == SoortDienst.SYNCHRONISATIE_PERSOON
                || soortDienst == SoortDienst.PLAATSING_AFNEMERINDICATIE;
    }
}
