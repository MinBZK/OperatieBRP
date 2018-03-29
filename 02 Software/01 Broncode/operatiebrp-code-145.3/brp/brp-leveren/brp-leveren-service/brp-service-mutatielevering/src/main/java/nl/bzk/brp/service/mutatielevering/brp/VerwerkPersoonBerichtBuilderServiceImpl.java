/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering.brp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.algemeen.Populatie;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.MeldingBepalerService;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import org.springframework.stereotype.Service;

/**
 * VerwerkPersoonBerichtBuildServiceImpl.
 */
@Service
public final class VerwerkPersoonBerichtBuilderServiceImpl implements VerwerkPersoonBerichtBuilderService {

    @Inject
    private PartijService partijService;
    @Inject
    private MeldingBepalerService meldingBepalerService;

    private VerwerkPersoonBerichtBuilderServiceImpl() {
    }

    @Override
    public List<VerwerkPersoonBericht> maakBerichten(final long administratieveHandeling,
                                                     final Map<Autorisatiebundel, List<BijgehoudenPersoon>> bijgehoudenPersonen,
                                                     final Map<Autorisatiebundel, Map<Persoonslijst, Populatie>> teLeverenPersonenMap) {
        final List<VerwerkPersoonBericht> leverBerichten = new ArrayList<>();

        for (Map.Entry<Autorisatiebundel, List<BijgehoudenPersoon>> autorisatiebundelListEntry : bijgehoudenPersonen.entrySet()) {
            final Autorisatiebundel autorisatiebundel = autorisatiebundelListEntry.getKey();
            //maak berichten, splits volledig en mutatieberichten
            final List<BijgehoudenPersoon> volledigBerichtPersonen = new ArrayList<>();
            final List<Melding> meldingenVolledigBericht = new ArrayList<>();
            final List<BijgehoudenPersoon> mutatieBerichtPersonen = new ArrayList<>();
            final List<Melding> meldingenMutatieBericht = new ArrayList<>();

            final Partij zendendePartij = partijService.geefBrpPartij();
            for (final BijgehoudenPersoon bijgehoudenPersoon : autorisatiebundelListEntry.getValue()) {
                final Populatie populatie = teLeverenPersonenMap.get(autorisatiebundel).get(bijgehoudenPersoon.getPersoonslijst());
                voegBijgehoudenPersoonToe(bijgehoudenPersoon, populatie, autorisatiebundel, volledigBerichtPersonen, meldingenVolledigBericht,
                        mutatieBerichtPersonen, meldingenMutatieBericht);
            }

            if (!volledigBerichtPersonen.isEmpty()) {
                final AdministratieveHandeling administratieveHandeling1 = volledigBerichtPersonen.get(0).getPersoonslijst().getAdministratieveHandeling();
                final BasisBerichtGegevens.Builder builder = maakBasisBerichtBuilder(administratieveHandeling1, autorisatiebundel.getPartij(),
                        zendendePartij,
                        autorisatiebundel.getDienst(),
                        SoortSynchronisatie.VOLLEDIG_BERICHT,
                        autorisatiebundel.getLeveringsautorisatie().getIndicatieAliasSoortAdministratieveHandelingLeveren());
                final BasisBerichtGegevens bericht = builder
                        .metMeldingen(meldingenVolledigBericht)
                        .build();
                final VerwerkPersoonBericht verwerkPersoonBericht = new VerwerkPersoonBericht(bericht, autorisatiebundel, volledigBerichtPersonen);
                leverBerichten.add(verwerkPersoonBericht);
            }

            if (!mutatieBerichtPersonen.isEmpty()) {
                final AdministratieveHandeling administratieveHandeling1 = mutatieBerichtPersonen.get(0).getPersoonslijst().getAdministratieveHandeling();

                final BasisBerichtGegevens.Builder builder = maakBasisBerichtBuilder(administratieveHandeling1, autorisatiebundel.getPartij(),
                        zendendePartij,
                        autorisatiebundel.getDienst(),
                        SoortSynchronisatie.MUTATIE_BERICHT,
                        autorisatiebundel.getLeveringsautorisatie().getIndicatieAliasSoortAdministratieveHandelingLeveren());
                final VerwerkPersoonBericht verwerkPersoonBericht = new VerwerkPersoonBericht(builder.metMeldingen(meldingenMutatieBericht).build(),
                        autorisatiebundel, mutatieBerichtPersonen);

                if (!verwerkPersoonBericht.isLeeg()) {
                    leverBerichten.add(verwerkPersoonBericht);
                }
            }
        }
        return leverBerichten;
    }

    private void voegBijgehoudenPersoonToe(final BijgehoudenPersoon bijgehoudenPersoon, final Populatie populatie, final Autorisatiebundel autorisatiebundel,
                                           final List<BijgehoudenPersoon> volledigBerichtPersonen, final List<Melding> meldingenVolledigBericht,
                                           final List<BijgehoudenPersoon> mutatieBerichtPersonen, final List<Melding> meldingenMutatieBericht) {
        if (bijgehoudenPersoon.isVolledigBericht()) {
            final List<Melding> meldingen = meldingBepalerService.geefWaarschuwingen(bijgehoudenPersoon, populatie, autorisatiebundel);
            if (!meldingen.isEmpty()) {
                meldingenVolledigBericht.addAll(meldingen);
            }
            volledigBerichtPersonen.add(bijgehoudenPersoon);
        } else {
            final List<Melding> meldingen = meldingBepalerService.geefWaarschuwingen(bijgehoudenPersoon, populatie, autorisatiebundel);
            if (!meldingen.isEmpty()) {
                meldingenMutatieBericht.addAll(meldingen);
            }
            mutatieBerichtPersonen.add(bijgehoudenPersoon);
        }
    }

    private BasisBerichtGegevens.Builder maakBasisBerichtBuilder(final AdministratieveHandeling administratieveHandeling, final Partij ontvangendePartij,
                                                                 final Partij zendendePartij, final Dienst dienst,
                                                                 final SoortSynchronisatie soortSynchronisatie, final Boolean aliasLeveren) {

        final SoortAdministratieveHandeling soort = administratieveHandeling.getSoort();
        final String soortNaam = Boolean.TRUE.equals(aliasLeveren) && soort.getAlias() != null ?
                soort.getAlias().getNaam() : soort.getNaam();
        //@formatter:off
        return BasisBerichtGegevens.builder()
            .metParameters()
                .metSoortSynchronisatie(soortSynchronisatie)
                .metDienst(dienst)
            .eindeParameters()
            .metTijdstipRegistratie(administratieveHandeling.getTijdstipRegistratie())
            .metPartijCode(administratieveHandeling.getPartijCode())
                .metSoortNaam(soortNaam)
                .metCategorieNaam(soort.getCategorie().getNaam())
            .metAdministratieveHandelingId(administratieveHandeling.getId())
            .metStuurgegevens()
                .metReferentienummer(UUID.randomUUID().toString())
                .metTijdstipVerzending(DatumUtil.nuAlsZonedDateTime())
                .metZendendePartij(zendendePartij)
                .metZendendeSysteem(BasisBerichtGegevens.BRP_SYSTEEM)
                .metOntvangendePartij(ontvangendePartij)
            .eindeStuurgegevens();
        //@formatter:on
    }
}
