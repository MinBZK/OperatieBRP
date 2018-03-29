/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering.leveringbepaling.filter;

import java.time.ZonedDateTime;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Populatie;
import nl.bzk.brp.domain.leveringmodel.Afnemerindicatie;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.springframework.stereotype.Component;


/**
 * Filter dat bepaalt of de levering door mag gaan: Levering kan alleen als een afnemer werkelijk een indicatie heeft *en* de genoemde dienst.
 */
@Component("PersoonAfnemerindicatieFilter")
final class PersoonAfnemerindicatieFilterImpl implements Leveringfilter {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final String LOG_DATUM_EINDE_VOLGEN_VERSTREKEN =
            "Datum einde volgen is verstreken voor afnemer indicatie met id {} van afnemer {} op"
                    + " persoon {} met leveringsautorisatie {} (datum peilmoment: {}, datum einde "
                    + "volgen: {}).";

    @Override
    public boolean magLeveren(final Persoonslijst persoon, final Populatie populatie, final Autorisatiebundel leveringAutorisatie) {
        boolean resultaat = true;

        if (SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE == leveringAutorisatie
                .getSoortDienst()) {
            final Long afnemerIndicatieId =
                    vindAfnemerindicatieOpPersoon(persoon, leveringAutorisatie);

            if (afnemerIndicatieId == null) {
                LOGGER.debug(
                        "Persoon {} zal niet geleverd worden voor dienst {} daar er geen (geldige) afnemerindicatie is.",
                        persoon.getMetaObject().getObjectsleutel(), leveringAutorisatie.getDienst().getId());
                resultaat = false;
            }
        }

        return resultaat;
    }

    /**
     * Zoekt een niet verlopen afnemerindicatie op een persoon.
     * @param persoon de persoon waarbij wordt gezocht
     * @param autorisatiebundel element met de referentie naar het leveringsautorisatie dat de indicatie zou kunnen hebben
     * @return de afnemerIndicatieId als deze bestaat.
     */
    @Bedrijfsregel(Regel.R1314)
    private Long vindAfnemerindicatieOpPersoon(final Persoonslijst persoon, final Autorisatiebundel autorisatiebundel) {
        Long afnemerindicatieId = null;
        final ZonedDateTime peilMoment = persoon.getAdministratieveHandeling().getTijdstipRegistratie();
        final Set<Afnemerindicatie> afnemerIndicaties = persoon.getNuNuBeeld().getGeldendeAfnemerindicaties();

        LOGGER.debug("aantal afnemerindicaties {}", afnemerIndicaties.size());
        for (final Afnemerindicatie afnemerindicatie : afnemerIndicaties) {
            final Integer laId = autorisatiebundel.getLeveringsautorisatieId();
            final boolean idGelijk = laId.equals(afnemerindicatie.getLeveringsAutorisatieId());
            final boolean partijGelijk = afnemerindicatie.getAfnemerCode().equals(autorisatiebundel.getPartij().getCode());
            if (idGelijk && partijGelijk) {
                if (isDatumEindeVolgenVerstreken(afnemerindicatie, peilMoment)) {
                    final String partijNaam = autorisatiebundel.getPartij().getNaam();
                    LOGGER.debug(LOG_DATUM_EINDE_VOLGEN_VERSTREKEN, afnemerindicatie.getVoorkomenSleutel(), partijNaam,
                            persoon.getMetaObject().getObjectsleutel(), laId, peilMoment, afnemerindicatie.getDatumEindeVolgen());
                    return null;
                }

                afnemerindicatieId = afnemerindicatie.getVoorkomenSleutel();
                break;
            }
        }
        return afnemerindicatieId;
    }

    private boolean isDatumEindeVolgenVerstreken(final Afnemerindicatie persoonAfnemerindicatie, final ZonedDateTime peilMoment) {
        if (persoonAfnemerindicatie.getDatumEindeVolgen() == null) {
            return false;
        }
        final ZonedDateTime datumEindeVolgen = DatumUtil.vanIntegerNaarLocalDate(persoonAfnemerindicatie.getDatumEindeVolgen())
                .atStartOfDay(DatumUtil.NL_ZONE_ID);
        return datumEindeVolgen.isBefore(peilMoment) || datumEindeVolgen.isEqual(peilMoment);
    }
}
