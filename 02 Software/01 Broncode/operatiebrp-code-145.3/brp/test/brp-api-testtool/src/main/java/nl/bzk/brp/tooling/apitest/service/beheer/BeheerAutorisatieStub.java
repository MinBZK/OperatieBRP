/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.beheer;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.beheer.service.dal.DienstRepository;
import nl.bzk.brp.test.common.DienstSleutel;
import nl.bzk.brp.test.common.dsl.Ref;
import nl.bzk.brp.tooling.apitest.autorisatie.AutorisatieData;
import nl.bzk.brp.tooling.apitest.service.dataaccess.AutorisatieStubService;
import org.springframework.stereotype.Component;

/**
 * Stub voor de {@link DienstRepository}.
 */
@Component
final class BeheerAutorisatieStub implements DienstRepository, AutorisatieStubService {
    private List<Dienst> diensten;
    private AutorisatieData autorisatieData;

    @Override
    public Collection<Dienst> getSelectieDienstenBinnenPeriode(Integer beginDatum, Integer eindDatum) {
        return diensten.stream().filter(d -> {
            boolean geldig = d.getEersteSelectieDatum() != null && d.getEersteSelectieDatum() < eindDatum;
            geldig = geldig && !Boolean.TRUE.equals(d.getIndicatieGeblokkeerd());
            geldig = geldig && d.getSoortDienst().equals(SoortDienst.SELECTIE);
            return geldig;
        }).collect(Collectors.toList());
    }

    @Override
    public Dienst findDienstById(Integer id) {
        return diensten.stream().filter(d -> d.getId().equals(id)).findFirst().orElseThrow(() -> new RuntimeException("Dienst niet gevonden."));
    }

    @Override
    public void setData(AutorisatieData autorisatieData) {
        this.autorisatieData = autorisatieData;
        for (Ref<Dienst> dienstRef : autorisatieData.getDienstRefs()) {
            for (Ref<ToegangLeveringsAutorisatie> toegangLeveringsAutorisatieRef : autorisatieData.getToegangRefs()) {
                if (dienstRef.getRef().equals(toegangLeveringsAutorisatieRef.getRef())) {
                    dienstRef.getValue().getDienstbundel().getLeveringsautorisatie().getToegangLeveringsautorisatieSet()
                            .add(toegangLeveringsAutorisatieRef.getValue());
                }
            }
        }
        this.diensten = autorisatieData.getDienstEntities();
    }

    @Override
    public Leveringsautorisatie getLeveringsautorisatie(String leveringsautorisatieNaam) {
        return null;
    }

    @Override
    public Dienst getDienstUitLeveringsautorisatie(Leveringsautorisatie leveringsAutorisatie, int dienstId) {
        return null;
    }

    @Override
    public Dienst getDienst(DienstSleutel dienstSleutel) {
        final ToegangLeveringsAutorisatie toegangleveringsautorisatie = getToegangleveringsautorisatie(dienstSleutel);
        return autorisatieData.getDienstRefs().stream()
                .filter(dienstRef -> dienstRef.getRef().equals(dienstSleutel.getDienstRef()))
                .filter(dienstRef -> dienstRef.getValue().getDienstbundel().getLeveringsautorisatie() == toegangleveringsautorisatie.getLeveringsautorisatie())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Dienst niet gevonden voor dienstSleutel: " + dienstSleutel))
                .getValue();
    }

    @Override
    public ToegangLeveringsAutorisatie getToegangleveringsautorisatie(DienstSleutel dienstSleutel) {
        return autorisatieData.getToegangRefs().stream()
                .filter(toegangLeveringsAutorisatieRef -> toegangLeveringsAutorisatieRef.getRef().equals(dienstSleutel.getToegangRef()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Toegangleveringsautorisatie niet gevonden voor dienstSleutel: " + dienstSleutel))
                .getValue();
    }

    ToegangLeveringsAutorisatie getToegangLeveringsautorisatieById(Integer id) {
        return autorisatieData.getToegangLeveringsautorisatieEntities().stream().filter(tla -> tla.getId().equals(id)).findFirst()
                .orElseThrow(() -> new RuntimeException("Toegang leveringsautorisatie niet gevonden."));
    }
}
