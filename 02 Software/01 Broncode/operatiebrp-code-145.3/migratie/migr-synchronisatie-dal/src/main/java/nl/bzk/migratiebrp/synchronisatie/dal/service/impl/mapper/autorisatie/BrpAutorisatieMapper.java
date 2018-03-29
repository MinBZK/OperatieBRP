/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelLo3Rubriek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAutorisatie;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpDienst;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpDienstbundel;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpDienstbundelLo3Rubriek;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpLeveringsautorisatie;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpLeveringsautorisatieBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpToegangLeveringsautorisatie;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstbundelInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpToegangLeveringsautorisatieInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.BrpMapperUtil;
import org.springframework.stereotype.Component;

/**
 * Mapper verantwoordelijk voor het mappen van een {@link ToegangLeveringsAutorisatie} op een {@link BrpAutorisatie}.
 */
@Component
public final class BrpAutorisatieMapper {

    /**
     * Mapped de autorisatie entiteit op de BRP autorisatie conversie model.
     * @param toegangen de toegang leveringsautorisatie entiteiten vanwaar moeten worden gemapped.
     * @return brpAutorisatie De autorisatie uit het conversiemodel.
     */
    public BrpAutorisatie mapNaarMigratie(final List<ToegangLeveringsAutorisatie> toegangen) {
        final BrpLeveringsautorisatieBuilder builder = new BrpLeveringsautorisatieBuilder();

        final List<BrpLeveringsautorisatie> leveringsautorisaties = new ArrayList<>();

        for (final BrpToegangLeveringsautorisatie toegang : mapToegangLeveringsautorisaties(toegangen)) {
            leveringsautorisaties.addAll(toegang.getLeveringsautorisaties());
        }

        builder.leveringsautorisatie(leveringsautorisaties);
        // Hier zou maar 1 unieke partij uit moeten komen
        final Set<BrpPartijCode> partijen = mapPartij(toegangen);
        if (partijen != null && partijen.size() == 1) {
            builder.partij(partijen.iterator().next());
        }

        return builder.build();
    }

    private List<BrpToegangLeveringsautorisatie> mapToegangLeveringsautorisaties(final List<ToegangLeveringsAutorisatie> toegangen) {

        final List<BrpToegangLeveringsautorisatie> brpToegangLeveringsautorisaties = new ArrayList<>();

        for (final ToegangLeveringsAutorisatie toegang : toegangen) {
            final BrpStapel<BrpToegangLeveringsautorisatieInhoud> toegangLeveringsautorisatieStapel;
            toegangLeveringsautorisatieStapel = new BrpToegangLeveringsautorisatieMapper().map(toegang.getToegangLeveringsautorisatieHistorieSet(), null);
            brpToegangLeveringsautorisaties.add(
                    new BrpToegangLeveringsautorisatie(
                            toegang.getAfleverpunt(),
                            BrpMapperUtil.mapDatum(toegang.getDatumIngang()),
                            BrpMapperUtil.mapDatum(toegang.getDatumEinde()),
                            new BrpPartijMapper().mapPartij(toegang.getGeautoriseerde().getPartij(), null),
                            toegang.getIndicatieGeblokkeerd(),
                            toegang.getNaderePopulatiebeperking(),
                            toegang.getOndertekenaar() != null ? new BrpPartijMapper().mapPartij(toegang.getOndertekenaar(), null) : null,
                            toegang.getTransporteur() != null ? new BrpPartijMapper().mapPartij(toegang.getTransporteur(), null) : null,
                            mapLeveringsautorisatie(toegang.getLeveringsautorisatie()),
                            toegangLeveringsautorisatieStapel));
        }
        return brpToegangLeveringsautorisaties;
    }

    private List<BrpLeveringsautorisatie> mapLeveringsautorisatie(final Leveringsautorisatie leveringsautorisatie) {
        final List<BrpDienstbundel> dienstbundels = new ArrayList<>();

        if (leveringsautorisatie.getDienstbundelSet() != null) {
            for (final Dienstbundel dienstbundel : leveringsautorisatie.getDienstbundelSet()) {
                final List<BrpDienst> diensten = new ArrayList<>();
                for (final Dienst dienst : dienstbundel.getDienstSet()) {
                    diensten.add(new BrpDienstMapper().mapDienst(dienst));
                }

                final List<BrpDienstbundelLo3Rubriek> lo3Rubrieken = new ArrayList<>();
                for (final DienstbundelLo3Rubriek lo3Rubriek : dienstbundel.getDienstbundelLo3RubriekSet()) {
                    lo3Rubrieken.add(new BrpDienstbundelLo3RubriekMapper().mapDienstbundelLo3Rubriek(lo3Rubriek));
                }

                // Dienstbundel
                final BrpStapel<BrpDienstbundelInhoud> dienstbundelStapel =
                        new BrpDienstbundelMapper().map(dienstbundel.getDienstbundelHistorieSet(), null);
                dienstbundels.add(new BrpDienstbundel(diensten, lo3Rubrieken, dienstbundelStapel));

            }
        }
        return Collections.singletonList(
                new BrpLeveringsautorisatie(
                        BrpMapperUtil.mapBrpStelselCode(leveringsautorisatie.getStelsel()),
                        leveringsautorisatie.getIndicatieModelautorisatie(),
                        new BrpLeveringsautorisatieMapper().map(leveringsautorisatie.getLeveringsautorisatieHistorieSet(), null),
                        dienstbundels));
    }

    private Set<BrpPartijCode> mapPartij(final List<ToegangLeveringsAutorisatie> toegangen) {
        final Set<BrpPartijCode> partijen = new HashSet<>();
        for (final ToegangLeveringsAutorisatie toegang : toegangen) {
            BrpPartijCode brpPartij = null;
            if (toegang.getGeautoriseerde() != null) {
                brpPartij = new BrpPartijCode(toegang.getGeautoriseerde().getPartij().getCode());
            }
            partijen.add(brpPartij);
        }

        return partijen;
    }

}
