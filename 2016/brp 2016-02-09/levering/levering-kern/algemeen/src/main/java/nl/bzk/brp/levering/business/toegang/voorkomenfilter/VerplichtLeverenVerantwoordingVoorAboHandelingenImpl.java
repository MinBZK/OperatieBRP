/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.toegang.voorkomenfilter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.brp.levering.algemeen.service.PartijService;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rol;
import nl.bzk.brp.model.basis.FormeelVerantwoordbaar;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.basis.MaterieelVerantwoordbaar;
import nl.bzk.brp.model.basis.VerantwoordingsEntiteit;
import nl.bzk.brp.model.hisvolledig.kern.AdministratieveHandelingHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import org.springframework.stereotype.Service;

/**
 * Implementatie van VerplichtLeverenVerantwoordingVoorAboHandelingen.
 */
@Service
public class VerplichtLeverenVerantwoordingVoorAboHandelingenImpl implements VerplichtLeverenVerantwoordingVoorAboHandelingen {

    @Inject
    private PartijService partijService;

    @Override
    public final void voerRegelUit(final List<HistorieEntiteit> totaleLijstVanHisElementenOpPersoonsLijst,
        final List<AdministratieveHandelingHisVolledig> administratieveHandelingen)
    {

        final Set<Long> relevanteHandelingen = bepaalABOHandelingen(administratieveHandelingen);
        if (relevanteHandelingen.isEmpty()) {
            return;
        }
        final VerantwoordingsEntiteit[] entiteiten = new VerantwoordingsEntiteit[3];
        for (final HistorieEntiteit historieEntiteit : totaleLijstVanHisElementenOpPersoonsLijst) {

            if (historieEntiteit instanceof MaterieelVerantwoordbaar) {
                final MaterieelVerantwoordbaar verantwoordbaar = (MaterieelVerantwoordbaar) historieEntiteit;
                entiteiten[0] = verantwoordbaar.getVerantwoordingInhoud();
                entiteiten[1] = verantwoordbaar.getVerantwoordingVerval();
                entiteiten[2] = verantwoordbaar.getVerantwoordingAanpassingGeldigheid();
            } else if (historieEntiteit instanceof FormeelVerantwoordbaar) {
                final FormeelVerantwoordbaar verantwoordbaar = (FormeelVerantwoordbaar) historieEntiteit;
                entiteiten[0] = verantwoordbaar.getVerantwoordingInhoud();
                entiteiten[1] = verantwoordbaar.getVerantwoordingVerval();
                entiteiten[2] = null;
            }

            for (final VerantwoordingsEntiteit verantwoordingsEntiteit : entiteiten) {
                if (verantwoordingsEntiteit instanceof ActieModel) {
                    final ActieModel actieModel = (ActieModel) verantwoordingsEntiteit;
                    if (relevanteHandelingen.contains(actieModel.getAdministratieveHandeling().getID())) {
                        actieModel.setMagGeleverdWorden(true);
                        actieModel.zetAlleAttributenOpMagGeleverdWorden();
                    }
                }
            }
        }
    }


    private Set<Long> bepaalABOHandelingen(final List<AdministratieveHandelingHisVolledig> alleHandelingen) {
        final Set<Long> relevanteHandelingen = new HashSet<>();
        for (final AdministratieveHandelingHisVolledig administratieveHandelingHisVolledig : alleHandelingen) {
            final Integer partijCode = administratieveHandelingHisVolledig.getPartij().getWaarde().getCode().getWaarde();
            final Partij partij = partijService.vindPartijOpCode(partijCode);
            final Set<Rol> rollen = partij.getRollen();
            if (rollen.contains(Rol.BIJHOUDINGSORGAAN_MINISTER)) {
                relevanteHandelingen.add(administratieveHandelingHisVolledig.getID());
            }
        }
        return relevanteHandelingen;
    }
}
