/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.toegang.populatie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.levering.algemeen.service.PartijService;
import nl.bzk.brp.levering.business.toegang.leveringsautorisatie.ToegangLeveringsautorisatieService;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import org.springframework.stereotype.Service;


/**
 * De implementatie van de levering autorisatie service {@link LeveringinformatieService}.
 */
@Service
public class LeveringinformatieServiceImpl implements LeveringinformatieService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private PartijService partijService;

    @Inject
    private ToegangLeveringsautorisatieService toegangLeveringsautorisatieService;

    @Override
    public final Leveringinformatie geefLeveringinformatie(final int leveringsautorisatieID, final int partijCode, final SoortDienst soortDienst) {
        final ToegangLeveringsautorisatie la = geefToegangLeveringautorisatie(leveringsautorisatieID, partijCode);
        final Dienst dienstMetSoort = la.getLeveringsautorisatie().geefDienst(soortDienst);
        if (dienstMetSoort == null) {
            LOGGER.warn("De dienst kon niet gevonden worden voor het leveringsautorisatie met id: {} en soortdienst: {}",
                    leveringsautorisatieID, soortDienst);
        }
        return new Leveringinformatie(la, dienstMetSoort);
    }

    //TODO verplaatsen?
    @Override
    public ToegangLeveringsautorisatie geefToegangLeveringautorisatie(int leveringsautorisatieID, Integer partijCode) {
        if (!toegangLeveringsautorisatieService.bestaatLeveringautorisatie(leveringsautorisatieID)) {
            LOGGER.error("Leveringautorisatie met id {} kon niet gevonden worden", leveringsautorisatieID);
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.LEVERINGSAUTORISATIEID,
                leveringsautorisatieID, null);
        }

        if (!partijService.bestaatPartij(partijCode)) {
            LOGGER.error("De partij kon niet gevonden worden voor de code: {}", partijCode);
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.PARTIJCODE, partijCode,
                null);
        }

        final ToegangLeveringsautorisatie la = toegangLeveringsautorisatieService
            .geefToegangLeveringsautorisatieOpZonderControle(leveringsautorisatieID, partijCode);
        if (la == null) {
            throw new IllegalStateException(
                String.format("Toegangleveringsautorisatie kon niet gevonden worden voor de partij met code: %d en leveringautorisatieId "
                    + "met id: %d", partijCode, leveringsautorisatieID));
        }
        return la;
    }

    /**
     * @param soortDiensten
     * @return
     * @brp.bedrijfsregel R1261
     * @brp.bedrijfsregel BRLV0029
     * @brp.bedrijfsregel R2052
     * @brp.bedrijfsregel R2056
     * @brp.bedrijfsregel R1263
     * @brp.bedrijfsregel R1264
     * @brp.bedrijfsregel R1265
     */
    @Override
    @Regels({ Regel.R1261, Regel.BRLV0029, Regel.R2052, Regel.R2056, Regel.R1263, Regel.R1264, Regel.R1258 })
    public final List<Leveringinformatie> geefGeldigeLeveringinformaties(final SoortDienst... soortDiensten) {
        if (soortDiensten == null) {
            return Collections.emptyList();
        }
        final List<ToegangLeveringsautorisatie> leveringsautorisaties = toegangLeveringsautorisatieService.geefGeldigeLeveringsautorisaties();
        final List<Leveringinformatie> leveringAutorisaties = new ArrayList<>();
        final DatumAttribuut vandaag = DatumAttribuut.vandaag();
        for (final ToegangLeveringsautorisatie leveringsautorisatie : leveringsautorisaties) {
            final Iterable<Dienst> leveringsautorisatieDiensten = leveringsautorisatie.getLeveringsautorisatie().geefDiensten(soortDiensten);
            for (final Dienst dienst : leveringsautorisatieDiensten) {
                final Leveringinformatie leveringinformatie = new Leveringinformatie(leveringsautorisatie, dienst);
                if (leveringinformatie.isGeldigOp(vandaag)) {
                    leveringAutorisaties.add(leveringinformatie);
                }
            }
        }
        return leveringAutorisaties;
    }
}
