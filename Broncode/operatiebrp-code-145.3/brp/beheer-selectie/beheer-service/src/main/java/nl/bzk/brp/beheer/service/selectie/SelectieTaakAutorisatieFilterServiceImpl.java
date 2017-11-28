/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.service.selectie;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.Optional;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import org.springframework.stereotype.Service;

/**
 * Implementatie van {@link SelectieTaakAutorisatieFilterService}.
 */
@Service
final class SelectieTaakAutorisatieFilterServiceImpl implements SelectieTaakAutorisatieFilterService {

    @Override
    public Collection<SelectieTaakDTO> filter(Collection<SelectieTaakDTO> taken) {
        Collection<SelectieTaakDTO> geautoriseerdeTaken = Lists.newArrayList();
        for (SelectieTaakDTO taak : taken) {
            int peildatum = DatumUtil.vanDatumNaarInteger(Optional.ofNullable(taak.getBerekendeSelectieDatum()).orElse(taak.getDatumPlanning()));
            Dienst dienst = taak.getDienst();
            ToegangLeveringsAutorisatie toegangLeveringsautorisatie = taak.getToegangLeveringsautorisatie();
            PartijRol partijRol = toegangLeveringsautorisatie.getGeautoriseerde();
            Partij partij = partijRol.getPartij();
            boolean geldig = AutAutUtil.isGeldigOp(peildatum, dienst.getDatumIngang(), dienst.getDatumEinde());
            geldig = geldig && isGeldig(peildatum, dienst);
            geldig = geldig && isGeldig(peildatum, dienst.getDienstbundel());
            geldig = geldig && isGeldig(peildatum, dienst.getDienstbundel().getLeveringsautorisatie());
            geldig = geldig && isGeldig(peildatum, toegangLeveringsautorisatie);
            geldig = geldig && isGeldig(peildatum, partijRol);
            geldig = geldig && isGeldig(peildatum, partij);
            if (geldig) {
                geautoriseerdeTaken.add(taak);
            }
        }
        return geautoriseerdeTaken;
    }

    private static boolean isGeldig(int peildatum, Dienst dienst) {
        boolean geldig = dienst.getDienstbundel().getLeveringsautorisatie().isActueelEnGeldig();
        geldig = geldig && AutAutUtil.isGeldigEnNietGeblokkeerd(dienst.getDienstbundel().getLeveringsautorisatie(), peildatum);
        return geldig;
    }

    private static boolean isGeldig(int peildatum, Dienstbundel dienstbundel) {
        boolean geldig = dienstbundel.isActueelEnGeldig();
        geldig = geldig && AutAutUtil.isGeldigEnNietGeblokkeerd(dienstbundel, peildatum);
        return geldig;
    }

    private static boolean isGeldig(int peildatum, Leveringsautorisatie leveringsautorisatie) {
        boolean geldig = leveringsautorisatie.isActueelEnGeldig();
        geldig = geldig && AutAutUtil.isGeldigEnNietGeblokkeerd(leveringsautorisatie, peildatum);
        return geldig;
    }

    private static boolean isGeldig(int peildatum, ToegangLeveringsAutorisatie toegangLeveringsAutorisatie) {
        boolean geldig = toegangLeveringsAutorisatie.isActueelEnGeldig();
        geldig = geldig && AutAutUtil.isGeldigEnNietGeblokkeerd(toegangLeveringsAutorisatie, peildatum);
        return geldig;
    }

    private static boolean isGeldig(int peildatum, PartijRol partijRol) {
        boolean geldig = partijRol.isActueelEnGeldig();
        geldig = geldig && AutAutUtil.isGeldigOp(peildatum, partijRol.getDatumIngang(), partijRol.getDatumEinde());
        return geldig;
    }

    private static boolean isGeldig(int peildatum, Partij partij) {
        boolean geldig = partij.isActueelEnGeldig();
        geldig = geldig && AutAutUtil.isGeldigOp(peildatum, partij.getDatumIngang(), partij.getDatumEinde());
        return geldig;
    }
}
