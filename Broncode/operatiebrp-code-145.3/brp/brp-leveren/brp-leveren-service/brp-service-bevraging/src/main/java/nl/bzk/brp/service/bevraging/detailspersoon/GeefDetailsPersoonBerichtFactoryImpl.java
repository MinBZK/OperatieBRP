/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.detailspersoon;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.HistorieVorm;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Verantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.DatumFormatterUtil;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.BerichtVerwerkingsResultaat;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.request.DatumService;
import nl.bzk.brp.service.algemeen.MeldingBepalerService;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import nl.bzk.brp.service.bevraging.algemeen.BevragingVerzoek;
import nl.bzk.brp.service.maakbericht.VerwerkPersoonBerichtFactory;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtHistorieFilterInformatie;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtParameters;
import org.springframework.stereotype.Component;

/**
 * Factory voor Geef Details Persoon bericht.
 */
@Bedrijfsregel(Regel.R2401)
@Component
final class GeefDetailsPersoonBerichtFactoryImpl implements GeefDetailsPersoonBerichtFactory {

    @Inject
    private VerwerkPersoonBerichtFactory verwerkPersoonBerichtFactory;
    @Inject
    private DatumService datumService;
    @Inject
    private PartijService partijService;
    @Inject
    private MeldingBepalerService meldingBepalerService;

    private GeefDetailsPersoonBerichtFactoryImpl() {

    }

    @Override
    public VerwerkPersoonBericht maakGeefDetailsPersoonBericht(final Persoonslijst persoonslijst,
                                                               final Autorisatiebundel autorisatiebundel,
                                                               final GeefDetailsPersoonVerzoek bevragingVerzoek,
                                                               final Set<AttribuutElement> scopingElementen) throws StapMeldingException {
        boolean verantwoordingLeveren = true;
        MaakBerichtHistorieFilterInformatie historieFilterInformatie = null;
        final GeefDetailsPersoonVerzoek.HistorieFilterParameters historieFilterParameters
                = bevragingVerzoek.getParameters().getHistorieFilterParameters();
        if (historieFilterParameters != null) {
            verantwoordingLeveren = !Verantwoording.GEEN.getNaam().equals(historieFilterParameters.getVerantwoording());
            historieFilterInformatie =
                    new MaakBerichtHistorieFilterInformatie(bepaalHistorievorm(historieFilterParameters),
                            maakMaterieelPeilmoment(historieFilterParameters), maakFormeelPeilmoment(historieFilterParameters));
        }

        final MaakBerichtParameters maakBerichtParameters = MaakBerichtParameters.getInstance(autorisatiebundel,
                bijgehoudenPersoonMap -> {
                    final List<BijgehoudenPersoon> bijgehoudenPersoonList = bijgehoudenPersoonMap.get(autorisatiebundel);
                    final VerwerkPersoonBericht
                            verwerkPersoonBericht =
                            bijgehoudenPersoonList.stream().findFirst()
                                    .map(b -> maakVerwerkPersoonBericht(b, autorisatiebundel, bevragingVerzoek))
                                    .orElse(VerwerkPersoonBericht.LEEG_BERICHT);
                    return Collections.singletonList(verwerkPersoonBericht);
                },
                null,
                persoonslijst,
                SoortSynchronisatie.VOLLEDIG_BERICHT,
                historieFilterInformatie);
        maakBerichtParameters.setVerantwoordingLeveren(verantwoordingLeveren);
        maakBerichtParameters.setGevraagdeElementen(scopingElementen);

        final VerwerkPersoonBericht verwerkPersoonBericht = verwerkPersoonBerichtFactory.maakBerichten(maakBerichtParameters).stream().findFirst()
                .orElseThrow(() -> new NullPointerException("bevat altijd een verwerk persoon bericht in geef details"));

        if (verwerkPersoonBericht.isLeeg()) {
            throw new StapMeldingException(Regel.R1403);
        }

        return verwerkPersoonBericht;
    }

    private VerwerkPersoonBericht maakVerwerkPersoonBericht(final BijgehoudenPersoon bijgehoudenPersoon,
                                                            final Autorisatiebundel autorisatiebundel,
                                                            final BevragingVerzoek bevragingVerzoek) {

        //@formatter:off
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
            .metMeldingen(meldingBepalerService.geefWaarschuwingen(bijgehoudenPersoon))
            .metStuurgegevens()
                .metReferentienummer(UUID.randomUUID().toString())
                .metCrossReferentienummer(bevragingVerzoek.getStuurgegevens().getReferentieNummer())
                .metTijdstipVerzending(DatumUtil.nuAlsZonedDateTime())
                .metZendendePartij(partijService.geefBrpPartij())
                .metZendendeSysteem(BasisBerichtGegevens.BRP_SYSTEEM)
            .eindeStuurgegevens()
            .metResultaat(
                BerichtVerwerkingsResultaat.builder()
                    .metVerwerking(VerwerkingsResultaat.GESLAAGD)
                    .metHoogsteMeldingsniveau(SoortMelding.GEEN.getNaam())
                    .build()
                )
        .build();
        //@formatter:on
        return new VerwerkPersoonBericht(basisBerichtGegevens, autorisatiebundel, Collections.singletonList(bijgehoudenPersoon));
    }

    private HistorieVorm bepaalHistorievorm(final GeefDetailsPersoonVerzoek.HistorieFilterParameters historieFilterParameters) {
        if (historieFilterParameters == null || historieFilterParameters.getHistorieVorm() == null) {
            return HistorieVorm.GEEN;
        }
        return historieFilterParameters.getHistorieVorm();
    }

    private ZonedDateTime maakFormeelPeilmoment(final GeefDetailsPersoonVerzoek.HistorieFilterParameters historieFilterParameters) {
        final ZonedDateTime formeelPeilmoment;
        if (historieFilterParameters == null || historieFilterParameters.getPeilMomentFormeelResultaat() == null) {
            formeelPeilmoment = DatumUtil.nuAlsZonedDateTime();
        } else {
            formeelPeilmoment = DatumFormatterUtil.vanXsdDatumTijdNaarZonedDateTime(historieFilterParameters.getPeilMomentFormeelResultaat());
        }
        return formeelPeilmoment;
    }

    private int maakMaterieelPeilmoment(final GeefDetailsPersoonVerzoek.HistorieFilterParameters historieFilterParameters)
            throws StapMeldingException {
        final int datum;
        if (historieFilterParameters == null || historieFilterParameters.getPeilMomentMaterieelResultaat() == null) {
            datum = BrpNu.get().alsIntegerDatumNederland();
        } else {
            final LocalDate localDate = datumService.parseDate(historieFilterParameters.getPeilMomentMaterieelResultaat());
            datum = DatumFormatterUtil.vanLocalDateNaarInteger(localDate);
        }
        return datum;
    }
}
