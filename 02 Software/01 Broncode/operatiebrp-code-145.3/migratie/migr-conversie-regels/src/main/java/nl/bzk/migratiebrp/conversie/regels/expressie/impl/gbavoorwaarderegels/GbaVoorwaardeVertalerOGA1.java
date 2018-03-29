/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import java.util.Arrays;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.BrpType;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaRubriekOnbekendExceptie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaVoorwaardeOnvertaalbaarExceptie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.Criterium;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.ElementWaarde;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.Expressie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.OngelijkEenLijstOperator;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.OngelijkEenOperator;

/**
 * Vertalen van voorwaarden met OGA1.
 */
public class GbaVoorwaardeVertalerOGA1 implements GbaVoorwaardeVertaler {

    private final String[] delen;
    private final BrpType brpType;
    private final boolean verzameling;
    private final RubriekVertaler vertaler;

    /**
     * Constructor.
     * @param delen delen van de voorwaarde
     * @param brpType vertaalde element
     * @param verzameling betreft vergelijking met verzameling
     * @param vertaler vertaling van rubriekgegevens
     */
    public GbaVoorwaardeVertalerOGA1(final String[] delen, final BrpType brpType, final boolean verzameling, final RubriekVertaler vertaler) {
        this.delen = Arrays.copyOf(delen, delen.length);
        this.brpType = brpType;
        this.verzameling = verzameling;
        this.vertaler = vertaler;
    }

    @Override
    public final Expressie verwerk() throws GbaVoorwaardeOnvertaalbaarExceptie {
        final Expressie resultaat;
        if (verzameling) {
            resultaat = verwerkVerzameling();
        } else {
            resultaat = verwerkGeenVerzameling();
        }
        return resultaat;
    }

    private Expressie verwerkVerzameling() throws GbaVoorwaardeOnvertaalbaarExceptie {
        return new ElementWaarde(
                new Criterium(
                        brpType.getType(),
                        new OngelijkEenLijstOperator(),
                        vertaler.maakLijstVanWaarden(delen[GbaVoorwaardeConstanten.DEEL_REST])));
    }

    private Expressie verwerkGeenVerzameling() throws GbaVoorwaardeOnvertaalbaarExceptie {
        final Expressie resultaat;
        if (brpType.isLijst()) {
            resultaat = verwerkLijst();
        } else {
            resultaat = verwerkGeenLijst();
        }
        return resultaat;
    }

    private Expressie verwerkLijst() throws GbaVoorwaardeOnvertaalbaarExceptie {
        return verwerkWaarde(delen[GbaVoorwaardeConstanten.DEEL_REST]);
    }

    private Expressie verwerkGeenLijst() throws GbaVoorwaardeOnvertaalbaarExceptie {
        final Expressie resultaat;
        final String ruweWaarde = delen[GbaVoorwaardeConstanten.DEEL_REST];
        if (ruweWaarde.matches("\\d{2}\\.\\d{2}\\.\\d{2}")) {
            resultaat = verwerkRubriek(ruweWaarde);
        } else {
            resultaat = verwerkWaarde(ruweWaarde);
        }
        return resultaat;
    }

    private Expressie verwerkRubriek(final String ruweWaarde) throws GbaVoorwaardeOnvertaalbaarExceptie {
        final Expressie resultaat;
        final BrpType[] brpTypen = bepaalBrpTypen(ruweWaarde);
        if (brpTypen.length == 1 && brpTypen[0].isLijst()) {
            resultaat = verwerkRubriekDraaiParametersOm(ruweWaarde);
        } else {
            resultaat = verwerkWaarde(ruweWaarde);
        }
        return resultaat;
    }

    private BrpType[] bepaalBrpTypen(final String ruweWaarde) throws GbaVoorwaardeOnvertaalbaarExceptie {
        final BrpType[] brpTypen;
        try {
            brpTypen = vertaler.vertaalGbaRubriekNaarBrpType(ruweWaarde);
        } catch (GbaRubriekOnbekendExceptie gbaRubriekOnbekendExceptie) {
            throw new GbaVoorwaardeOnvertaalbaarExceptie("Rubriek uit voorwaarde kan niet worden gevonden", gbaRubriekOnbekendExceptie);
        }
        return brpTypen;
    }

    private Expressie verwerkRubriekDraaiParametersOm(final String ruweWaarde) throws GbaVoorwaardeOnvertaalbaarExceptie {
        final Expressie resultaat;
        resultaat =
                new ElementWaarde(
                        new Criterium(
                                vertaler.vertaalWaardeVanRubriekOfAndereRubriek(ruweWaarde, true),
                                new OngelijkEenOperator(),
                                brpType.getType()));
        return resultaat;
    }

    private Expressie verwerkWaarde(final String ruweWaarde) throws GbaVoorwaardeOnvertaalbaarExceptie {
        return new ElementWaarde(
                new Criterium(brpType.getType(), new OngelijkEenOperator(), vertaler.vertaalWaardeVanRubriekOfAndereRubriek(ruweWaarde)));
    }
}
