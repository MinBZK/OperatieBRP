/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.functie;

import java.time.LocalDate;
import java.util.List;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.expressie.Context;
import nl.bzk.brp.domain.expressie.DatumLiteral;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieRuntimeException;
import nl.bzk.brp.domain.expressie.ExpressieTaalConstanten;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.GetalLiteral;
import nl.bzk.brp.domain.expressie.signatuur.SignatuurOptie;
import nl.bzk.brp.domain.expressie.signatuur.SimpeleSignatuur;
import org.springframework.stereotype.Component;

/**
 * Implementatie van de functie SELECTIE_DATUM.
 * <br>
 * De functie SELECTIE_DATUM() geeft de datum waarvoor de selectie wordt uitgevoerd.
 * Deze functie is alleen beschikbaar in de context van het uitvoeren van selecties.
 * De functie SELECTIE_DATUM (jaarverschil) geeft de datum plus of min het gegeven jaarverschil.
 * Het opgegeven aantal jaren wordt dan bij de datum van de selectie opgeteld.
 * Dit biedt een van de mogelijkheden om een leeftijd te controleren.
 */
@Component
@FunctieKeyword("SELECTIE_DATUM")
final class SelectiedatumFunctie extends AbstractFunctie {

    /**
     * Constructor voor de functie.
     */
    SelectiedatumFunctie() {
        super(new SignatuurOptie(
                new SimpeleSignatuur(),
                new SimpeleSignatuur(ExpressieType.GETAL)
        ));
    }

    @Override
    public Expressie evalueer(final List<Expressie> argumenten, final Context context) {

        final Integer selectiedatum = context.getProperty(ExpressieTaalConstanten.PROPERTY_DATUM_START_SELECTIE);
        if (selectiedatum == null) {
            throw new ExpressieRuntimeException("Geen waarde gevonden voor SELECTIE_DATUM()");
        }
        if (argumenten.isEmpty()) {
            return new DatumLiteral(selectiedatum);
        } else {
            final GetalLiteral jaarVerschil = getArgument(argumenten, 0);
            final LocalDate localDate = DatumUtil.vanIntegerNaarLocalDate(selectiedatum).plusYears(jaarVerschil.getWaarde());
            return new DatumLiteral(localDate.atStartOfDay(DatumUtil.BRP_ZONE_ID));
        }
    }

    @Override
    public ExpressieType getType(final List<Expressie> argumenten, final Context context) {
        return ExpressieType.DATUM;
    }
}
