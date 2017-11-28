/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.afnemerindicatie;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.MeldingBepalerService;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import nl.bzk.brp.service.maakbericht.VerwerkPersoonBerichtFactory;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtParameters;
import org.springframework.stereotype.Component;

/**
 * Maakt het persoonbericht voor onderhoud afnemerindicatie.
 */
@Component
final class OnderhoudAfnemerindicatiePersoonBerichtFactoryImpl implements OnderhoudAfnemerindicatiePersoonBerichtFactory {

    @Inject
    private VerwerkPersoonBerichtFactory stappenlijstUitvoerService;
    @Inject
    private PartijService partijService;
    @Inject
    private MeldingBepalerService meldingBepalerService;

    private OnderhoudAfnemerindicatiePersoonBerichtFactoryImpl() {
    }

    @Override
    public VerwerkPersoonBericht maakBericht(final Persoonslijst persoonslijst, final Autorisatiebundel autorisatiebundel,
                                             final Integer datumAanvangMaterielePeriode, final ZonedDateTime tijdstipRegistratie,
                                             final SoortAdministratieveHandeling soortAdministratieveHandeling, final String berichtReferentie) {
        final MaakBerichtParameters maakBerichtParameters = MaakBerichtParameters.getInstance(
                autorisatiebundel,
                bijgehoudenPersoonMap -> {
                    final List<BijgehoudenPersoon> bijgehoudenPersoonList = bijgehoudenPersoonMap.get(autorisatiebundel);
                    final VerwerkPersoonBericht
                            verwerkPersoonBericht =
                            bijgehoudenPersoonList.stream().findFirst()
                                    .map(b -> maakBerichtBuilder(b, autorisatiebundel, tijdstipRegistratie, soortAdministratieveHandeling))
                                    .orElse(VerwerkPersoonBericht.LEEG_BERICHT);
                    return Collections.singletonList(verwerkPersoonBericht);
                },
                datumAanvangMaterielePeriode,
                persoonslijst,
                SoortSynchronisatie.VOLLEDIG_BERICHT, null);
        return stappenlijstUitvoerService.maakBerichten(maakBerichtParameters).stream().findFirst()
                .orElseThrow(() -> new NullPointerException("bevat altijd een verwerk persoon bericht voor onderhoud afnemerindicatie"));
    }

    private VerwerkPersoonBericht maakBerichtBuilder(final BijgehoudenPersoon bijgehoudenPersoon,
                                                     final Autorisatiebundel autorisatiebundel, final ZonedDateTime tijdstipRegistratie,
                                                     final SoortAdministratieveHandeling soortAdministratieveHandeling) {
        final Partij zendendePartij = partijService.geefBrpPartij();
        final Partij ontvangendePartij = autorisatiebundel.getPartij();
        //@formatter:off
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
                        .metMeldingen(meldingBepalerService.geefWaarschuwingen(bijgehoudenPersoon))
                        .metParameters()
                            .metSoortSynchronisatie(SoortSynchronisatie.VOLLEDIG_BERICHT)
                            .metDienst(autorisatiebundel.getDienst())
                        .eindeParameters()
                        .metTijdstipRegistratie(tijdstipRegistratie)
                        .metSoortNaam(soortAdministratieveHandeling.getNaam())
                        .metCategorieNaam(soortAdministratieveHandeling.getCategorie().getNaam())
                        .metPartijCode(ontvangendePartij.getCode())
                        .metStuurgegevens()
                            .metReferentienummer(UUID.randomUUID().toString())
                            .metTijdstipVerzending(DatumUtil.nuAlsZonedDateTime())
                            .metZendendePartij(zendendePartij)
                            .metZendendeSysteem(BasisBerichtGegevens.BRP_SYSTEEM)
                            .metOntvangendePartij(ontvangendePartij)
                        .eindeStuurgegevens().build();
        //@formatter:on
        return new VerwerkPersoonBericht(basisBerichtGegevens, autorisatiebundel, Collections.singletonList(bijgehoudenPersoon));
    }
}
