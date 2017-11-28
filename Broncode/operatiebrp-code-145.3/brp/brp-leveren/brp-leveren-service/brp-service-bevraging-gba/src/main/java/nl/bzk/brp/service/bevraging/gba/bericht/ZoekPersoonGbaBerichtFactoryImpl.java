/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.gba.bericht;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.BerichtVerwerkingsResultaat;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.levering.lo3.bericht.BerichtFactory;
import nl.bzk.brp.levering.lo3.builder.Xa01Builder;
import nl.bzk.brp.service.bevraging.algemeen.BevragingVerzoek;
import nl.bzk.brp.service.bevraging.gba.adres.AdresvraagVerzoek;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.ZoekPersoonBerichtFactory;
import nl.bzk.brp.service.bevraging.zoekpersoonopadres.ZoekPersoonOpAdresVraag;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * Factory voor zoek persoon (gba).
 */
@Primary
@Component("zoekPersoonGbaBerichtFactoryImpl")
public final class ZoekPersoonGbaBerichtFactoryImpl implements ZoekPersoonBerichtFactory {

    private final BerichtFactory berichtFactory;

    /**
     * Constructor.
     * @param berichtFactory bericht factory
     */
    @Inject
    public ZoekPersoonGbaBerichtFactoryImpl(final BerichtFactory berichtFactory) {
        this.berichtFactory = berichtFactory;
    }

    /**
     * Maak de inhoud voor het antwoord bericht (Ha01, Hf01, Xa01 of Xf01)
     */
    @Override
    public VerwerkPersoonBericht maakZoekPersoonBericht(final List<Persoonslijst> persoonslijsten,
                                                        final Autorisatiebundel autorisatiebundel,
                                                        final BevragingVerzoek bevragingVerzoek,
                                                        final Integer peilmomentMaterieel) {
        // @formatter:off
        return new VerwerkPersoonBericht(
            metResultaat(persoonslijsten, bevragingVerzoek)
            .metStuurgegevens()
                .metReferentienummer(UUID.randomUUID().toString())
                .metCrossReferentienummer(bevragingVerzoek.getStuurgegevens().getReferentieNummer())
                .metTijdstipVerzending(DatumUtil.nuAlsZonedDateTime())
            .eindeStuurgegevens()
            .build(),
            autorisatiebundel,
            maakBijgehoudenPersonen(filterPersonen(persoonslijsten, bevragingVerzoek))
        );
        // @formatter:on
    }

    private BasisBerichtGegevens.Builder metResultaat(final List<Persoonslijst> persoonslijsten, final BevragingVerzoek verzoek) {
        return bepaalMelding(persoonslijsten, verzoek).map(this::metFoutiefResultaat).orElseGet(this::metGeslaagdResultaat);
    }

    private BasisBerichtGegevens.Builder metGeslaagdResultaat() {
        return BasisBerichtGegevens.builder()
                .metResultaat(BerichtVerwerkingsResultaat.builder()
                        .metVerwerking(VerwerkingsResultaat.GESLAAGD)
                        .metHoogsteMeldingsniveau(SoortMelding.GEEN.getNaam())
                        .build());
    }

    private BasisBerichtGegevens.Builder metFoutiefResultaat(final Melding melding) {
        return BasisBerichtGegevens.builder()
                .metMeldingen(Collections.singletonList(melding))
                .metResultaat(BerichtVerwerkingsResultaat.builder()
                        .metVerwerking(VerwerkingsResultaat.FOUTIEF)
                        .metHoogsteMeldingsniveau(SoortMelding.FOUT.getNaam())
                        .build());
    }

    private Optional<Melding> bepaalMelding(final List<Persoonslijst> persoonslijsten, final BevragingVerzoek verzoek) {
        Optional<Melding> melding = Optional.empty();
        if (verzoek instanceof ZoekPersoonOpAdresVraag) {
            List<Persoonslijst> gefilterdePersonen = filterPersonen(persoonslijsten, verzoek);
            if (!persoonslijsten.isEmpty() && gefilterdePersonen.isEmpty()) {
                melding = Optional.of(new Melding(Regel.R1640));
            } else if (verzoek instanceof AdresvraagVerzoek && isXa01TeLang(gefilterdePersonen, (AdresvraagVerzoek) verzoek)) {
                melding = Optional.of(new Melding(Regel.R2240));
            }
        }
        return melding;
    }

    private List<Persoonslijst> filterPersonen(final List<Persoonslijst> persoonslijsten, final BevragingVerzoek bevragingVerzoek) {
        if (bevragingVerzoek instanceof ZoekPersoonOpAdresVraag) {
            return persoonslijsten.stream()
                    .filter(this::isActueel)
                    .collect(Collectors.toList());
        } else {
            return persoonslijsten;
        }
    }

    private List<BijgehoudenPersoon> maakBijgehoudenPersonen(final List<Persoonslijst> persoonslijsten) {
        return persoonslijsten.stream()
                .map(persoonslijst -> new BijgehoudenPersoon.Builder(persoonslijst, null).build())
                .collect(Collectors.toList());
    }

    private boolean isActueel(final Persoonslijst persoonslijst) {
        return persoonslijst.<String>getActueleAttribuutWaarde(ElementHelper.getAttribuutElement(Element.PERSOON_BIJHOUDING_NADEREBIJHOUDINGSAARDCODE))
                .map(NadereBijhoudingsaard::parseCode)
                .map(NadereBijhoudingsaard.ACTUEEL::equals)
                .orElse(false);
    }

    private boolean isXa01TeLang(final List<Persoonslijst> persoonslijsten, final AdresvraagVerzoek verzoek) {
        return new Xa01Builder(berichtFactory).isTooLong(persoonslijsten, verzoek.getGevraagdeRubrieken());
    }
}
