/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.filters;

import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.logging.FunctioneleMelding;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.momentview.autaut.PersoonAfnemerindicatieView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.autaut.PersoonAfnemerindicatie;
import nl.bzk.brp.model.logisch.kern.AdministratieveHandeling;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * Filter dat bepaalt of de levering door mag gaan: Levering kan alleen als een afnemer werkelijk een indicatie heeft *en* de genoemde dienst.
 */
@Component
@Order(10)
public class PersoonHeeftAfnemerIndicatieFilter implements LeverenPersoonFilter {

    private static final Logger LOGGER                            = LoggerFactory.getLogger();
    private static final String LOG_DATUM_EINDE_VOLGEN_VERSTREKEN =
        "Datum einde volgen is verstreken voor afnemer indicatie met id {} van afnemer {} op"
            + " persoon {} met leveringsautorisatie {} (datum peilmoment: {}, datum einde "
            + "volgen: {}).";

    @Override
    public final boolean magLeverenDoorgaan(final PersoonHisVolledig persoon, final Populatie populatie,
        final Leveringinformatie leveringAutorisatie,
        final AdministratieveHandelingModel administratieveHandeling)
    {
        boolean resultaat = true;

        if (SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE == leveringAutorisatie
            .getSoortDienst())
        {
            final Long afnemerIndicatieId =
                vindAfnemerIndicatieOpPersoon(administratieveHandeling, persoon, leveringAutorisatie);

            if (afnemerIndicatieId == null) {
                LOGGER.debug(
                    "Persoon {} zal niet geleverd worden voor dienst {} daar er geen (geldige) afnemerindicatie is.",
                    persoon.getID(), leveringAutorisatie.getDienst().getID());
                resultaat = false;
            } else if (populatie == Populatie.BUITEN) {
                logPersoonValtBuitenPopulatie(leveringAutorisatie, afnemerIndicatieId, persoon.getID());
            }
        }

        return resultaat;
    }

    /**
     * Zoekt een niet verlopen afnemerindicatie op een persoon.
     *
     * @param administratieveHandeling de admhnd die het peilmoment bepaalt
     * @param persoon                  de persoon waarbij wordt gezocht
     * @param leveringinformatie      element met de referentie naar het leveringsautorisatie dat de indicatie zou kunnen hebben
     * @return de afnemerIndicatieId als deze bestaat.
     */
    @Regels({Regel.BRLV0015, Regel.R1314})
    private Long vindAfnemerIndicatieOpPersoon(final AdministratieveHandeling administratieveHandeling,
        final PersoonHisVolledig persoon, final Leveringinformatie leveringinformatie)
    {
        Long afnemerindicatieId = null;
        final DatumTijdAttribuut peilMoment = administratieveHandeling.getTijdstipRegistratie();

        final PersoonView persoonView = new PersoonView(persoon, peilMoment);
        LOGGER.debug("aantal afnemerindicaties {}", persoonView.getAfnemerindicaties().size());
        for (final PersoonAfnemerindicatieView persoonAfnemerindicatie : persoonView.getAfnemerindicaties()) {
            final Integer laId = leveringinformatie.getToegangLeveringsautorisatie().getLeveringsautorisatie().getID();
            final boolean idGelijk = laId.equals(persoonAfnemerindicatie.getLeveringsautorisatie().getWaarde().getID());
            final boolean partijGelijk = persoonAfnemerindicatie.getAfnemer().getWaarde().getCode().getWaarde().
                    equals(leveringinformatie.getToegangLeveringsautorisatie().getGeautoriseerde().getPartij().getCode().getWaarde());
            if (idGelijk && partijGelijk) {
                if (isDatumEindeVolgenVerstreken(persoonAfnemerindicatie, peilMoment)) {
                    final String partijNaam =
                        leveringinformatie.getToegangLeveringsautorisatie().getGeautoriseerde().getPartij().getNaam().getWaarde();
                    LOGGER.debug(LOG_DATUM_EINDE_VOLGEN_VERSTREKEN, persoonAfnemerindicatie.getID(), partijNaam,
                        persoon.getID(), laId, peilMoment, persoonAfnemerindicatie.getStandaard()
                            .getDatumEindeVolgen());

                    LOGGER.warn(FunctioneleMelding.LEVERING_AFNEMERINDICATIE_DATUM_EINDE_VERLOPEN,
                        LOG_DATUM_EINDE_VOLGEN_VERSTREKEN, persoonAfnemerindicatie.getID(), partijNaam, persoon
                            .getID(), laId, peilMoment, persoonAfnemerindicatie.getStandaard()
                            .getDatumEindeVolgen());

                    return null;
                }

                afnemerindicatieId = persoonAfnemerindicatie.getID();
                break;
            }
        }
        return afnemerindicatieId;
    }

    /**
     * Controleert of de datum einde volgen verstreken is voor de afnemerindicatie.
     *
     * @param persoonAfnemerindicatie persoon afnemerindicatie
     * @param peilMoment              peil moment van administratieve handeling
     * @return true als datum einde volgen verstreken is, anders false.
     */
    private boolean isDatumEindeVolgenVerstreken(final PersoonAfnemerindicatie persoonAfnemerindicatie,
        final DatumTijdAttribuut peilMoment)
    {
        final DatumAttribuut datumEindeVolgen = persoonAfnemerindicatie.getStandaard().getDatumEindeVolgen();
        return datumEindeVolgen != null && datumEindeVolgen.voorOfOp(peilMoment.naarDatum());
    }

    /**
     * Registreer een melding als de persoon buiten populatie valt en een afnemer indicatie heeft.
     *
     * @param leveringAutorisatie de leveringAutorisatie
     * @param afnemerIndicatieId  afnemer indicatie id
     * @param persoonId           persoon id
     */
    private void logPersoonValtBuitenPopulatie(final Leveringinformatie leveringAutorisatie,
        final Long afnemerIndicatieId, final Integer persoonId)
    {
        final String partijNaam =
            leveringAutorisatie.getToegangLeveringsautorisatie().getGeautoriseerde().getPartij().getNaam().getWaarde();
        final Integer leveringsautorisatieId = leveringAutorisatie.getToegangLeveringsautorisatie().getLeveringsautorisatie().getID();
        LOGGER.warn(FunctioneleMelding.LEVERING_AFNEMERINDICATIE_PERSOON_BUITEN_POPULATIE,
            "Er bevindt zich een afnemerindicatie met id {} van afnemer {} op "
                + "persoon {} met leveringsautorisatie {}, terwijl de persoon buiten de populatie valt.", afnemerIndicatieId,
            partijNaam, persoonId, leveringsautorisatieId);
    }

}
