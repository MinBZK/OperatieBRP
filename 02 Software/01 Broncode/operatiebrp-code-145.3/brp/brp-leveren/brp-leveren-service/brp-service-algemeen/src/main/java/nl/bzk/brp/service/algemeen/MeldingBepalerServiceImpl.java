/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen;

import com.google.common.collect.Lists;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.algemeen.Populatie;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.springframework.stereotype.Service;

/**
 * MeldingBepalerServiceImpl. Maakt voor een gegeven persoon een lijst met specifieke meldingen,
 * waarvoor geldt dat het {@link SoortMelding} een waarschuwing is.
 */
@Bedrijfsregel(Regel.R1315)
@Bedrijfsregel(Regel.R1316)
@Bedrijfsregel(Regel.R1340)
@Bedrijfsregel(Regel.R2586)
@Service
public final class MeldingBepalerServiceImpl implements MeldingBepalerService {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final String PERSOON_HEEFT_MELDING = "Persoon met id [{}] heeft melding: {} - {}";

    @Inject
    private VerstrekkingsbeperkingService verstrekkingsbeperkingService;

    private MeldingBepalerServiceImpl() {
    }

    @Override
    public List<Melding> geefWaarschuwingen(final List<BijgehoudenPersoon> bijgehoudenPersoonList) {
        final List<Melding> meldingList = Lists.newLinkedList();
        bijgehoudenPersoonList.forEach(
                persoon -> controleerVerstrekkingbeperkingOpPersoon(persoon.getPersoonslijst(), persoon.getCommunicatieId(), meldingList));
        return meldingList;
    }

    @Override
    public List<Melding> geefWaarschuwingen(BijgehoudenPersoon bijgehoudenPersoon) {
        final List<Melding> meldingList = Lists.newLinkedList();
        controleerVerstrekkingbeperkingOpPersoon(bijgehoudenPersoon.getPersoonslijst(), bijgehoudenPersoon.getCommunicatieId(), meldingList);
        return meldingList;
    }

    @Override
    public List<Melding> geefWaarschuwingen(final BijgehoudenPersoon bijgehoudenPersoon, final Populatie populatie, final Autorisatiebundel autorisatiebundel) {
        final List<Melding> meldingList = Lists.newLinkedList();
        final SoortDienst soortDienst = autorisatiebundel.getDienst().getSoortDienst();
        if (isDienstMutatielevering(soortDienst)) {
            if (SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE == soortDienst && (Populatie.VERLAAT == populatie
                    || Populatie.BUITEN == populatie)) {
                //De geleverde persoon valt niet meer binnen de doelgroep van de leveringsautorisatie
                final Melding melding = new Melding(Regel.R1315);
                melding.setReferentieID(Integer.toString(bijgehoudenPersoon.getCommunicatieId()));
                meldingList.add(melding);
            } else if (Populatie.VERLAAT == populatie) {
                //De geleverde persoon heeft de doelbindingspopulatie verlaten. Mutatielevering voor deze persoonslijst is gestopt
                final Melding melding = new Melding(Regel.R1316);
                melding.setReferentieID(Integer.toString(bijgehoudenPersoon.getCommunicatieId()));
                meldingList.add(melding);
                LOGGER.debug(PERSOON_HEEFT_MELDING, bijgehoudenPersoon.getPersoonslijst().getId(), Regel.R1316, Regel.R1316.getMelding());
            }
            //Voegt indien nodig een melding toe dat mutatielevering is gestopt, omdat de betreffende persoon een verstrekkingsbeperking heeft.
            final boolean heeftGeldigeVerstrekkingsbeperking = verstrekkingsbeperkingService.heeftGeldigeVerstrekkingsbeperking(
                    bijgehoudenPersoon.getPersoonslijst(), autorisatiebundel.getToegangLeveringsautorisatie().getGeautoriseerde().getPartij());
            if (heeftGeldigeVerstrekkingsbeperking) {
                final Melding melding = new Melding(Regel.R2586);
                melding.setReferentieID(Integer.toString(bijgehoudenPersoon.getCommunicatieId()));
                meldingList.add(melding);
                LOGGER.debug(PERSOON_HEEFT_MELDING, bijgehoudenPersoon.getPersoonslijst().getId(), Regel.R2586, Regel.R2586.getMelding());
            } else {
                controleerVerstrekkingbeperkingOpPersoon(bijgehoudenPersoon.getPersoonslijst(), bijgehoudenPersoon.getCommunicatieId(), meldingList);
            }
        }

        return meldingList;
    }


    private static List<Melding> controleerVerstrekkingbeperkingOpPersoon(final Persoonslijst persoonslijst, final Integer communicatieId,
                                                                          List<Melding> meldingList) {
        if (persoonslijst.heeftIndicatieVolledigeVerstrekkingsbeperking() || !persoonslijst.getVerstrekkingsbeperkingen().isEmpty()) {
            final Melding melding = new Melding(Regel.R1340);
            melding.setReferentieID(communicatieId != null ? Integer.toString(communicatieId) : null);
            meldingList.add(melding);
            LOGGER.debug(PERSOON_HEEFT_MELDING, persoonslijst.getId(), Regel.R1340, Regel.R1340.getMelding());
        }
        return meldingList;
    }

    private static boolean isDienstMutatielevering(final SoortDienst soortDienst) {
        return SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE == soortDienst
                || SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING == soortDienst
                || SoortDienst.ATTENDERING == soortDienst;
    }
}
