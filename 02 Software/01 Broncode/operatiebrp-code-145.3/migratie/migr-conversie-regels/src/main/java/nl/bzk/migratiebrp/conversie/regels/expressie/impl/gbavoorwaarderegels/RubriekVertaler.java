/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import nl.bzk.migratiebrp.conversie.regels.expressie.impl.BrpType;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaRubriekNaarBrpTypeVertaler;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaRubriekOnbekendExceptie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaVoorwaardeOnvertaalbaarExceptie;

/**
 * Vertaling van rubrieken.
 */
public class RubriekVertaler {

    private final VertaalWaardeVanRubriek vertaler;
    private final GbaRubriekNaarBrpTypeVertaler gbaRubriekNaarBrpTypeVertaler;

    /**
     * Constructor.
     * @param gbaRubriekNaarBrpTypeVertaler rubriekvertaler
     * @param vertaler vertaler van de waarde van de rubriek naar BRP
     */
    public RubriekVertaler(final GbaRubriekNaarBrpTypeVertaler gbaRubriekNaarBrpTypeVertaler, final VertaalWaardeVanRubriek vertaler) {
        this.gbaRubriekNaarBrpTypeVertaler = gbaRubriekNaarBrpTypeVertaler;
        this.vertaler = vertaler;
    }

    /**
     * Geef brp type van gba rubriek.
     * @param ruweWaarde gba rubriek
     * @return brp element
     * @throws GbaRubriekOnbekendExceptie geen vertaling mogelijk van gegeven rubriek
     */
    public final BrpType[] vertaalGbaRubriekNaarBrpType(final String ruweWaarde) throws GbaRubriekOnbekendExceptie {
        return gbaRubriekNaarBrpTypeVertaler.vertaalGbaRubriekNaarBrpType(ruweWaarde);
    }

    /**
     * Maakt een lijst van waarden.
     * @param waarden te vertalen tekst
     * @return een lijst van waarden
     * @throws GbaVoorwaardeOnvertaalbaarExceptie bij onvertaalbare voorwaarde
     */
    public final String maakLijstVanWaarden(final String waarden) throws GbaVoorwaardeOnvertaalbaarExceptie {
        final String[] losseWaarden =
                waarden.replaceAll(" OFVGL ", GbaVoorwaardeConstanten.SPLIT_CHARACTER)
                        .replaceAll(" ENVGL ", GbaVoorwaardeConstanten.SPLIT_CHARACTER)
                        .split(GbaVoorwaardeConstanten.SPLIT_CHARACTER);
        final StringBuilder lijst = new StringBuilder();
        for (final String losseWaarde : losseWaarden) {
            final String waarde = vertaalWaardeVanRubriekOfAndereRubriek(losseWaarde);
            if (waarde != null && !"".equals(waarde)) {
                lijst.append(waarde);
                lijst.append(", ");
            }
        }
        return lijst.toString().replaceAll(",\\ $", "");
    }

    /**
     * Vertaal de rubriek inhoud.
     * @param ruweWaarde de rubriek inhoud
     * @return de vertaalde waarde
     * @throws GbaVoorwaardeOnvertaalbaarExceptie bij onvertaalbare voorwaarde
     */
    public final String vertaalWaardeVanRubriekOfAndereRubriek(final String ruweWaarde)
            throws GbaVoorwaardeOnvertaalbaarExceptie {
        return vertaalWaardeVanRubriekOfAndereRubriek(ruweWaarde, false);
    }

    /**
     * Vertaal de rubriek inhoud.
     * @param ruweWaarde de rubriek inhoud
     * @param sleutelRubriek element gezocht voor de linkerzijde van de vergelijking
     * @return de vertaalde waarde
     * @throws GbaVoorwaardeOnvertaalbaarExceptie bij onvertaalbare voorwaarde
     */
    public final String vertaalWaardeVanRubriekOfAndereRubriek(final String ruweWaarde, final boolean sleutelRubriek)
            throws GbaVoorwaardeOnvertaalbaarExceptie {

        final String resultaat;
        if (ruweWaarde.matches("\\d{2}\\.\\d{2}\\.\\d{2}")) {
            final BrpType[] brpTypen;
            try {
                brpTypen = gbaRubriekNaarBrpTypeVertaler.vertaalGbaRubriekNaarBrpType(ruweWaarde);
            } catch (final GbaRubriekOnbekendExceptie gbaRubriekOnbekendExceptie) {
                throw new GbaVoorwaardeOnvertaalbaarExceptie("Gegeven rubriek in voorwaarde kan niet worden gevonden", gbaRubriekOnbekendExceptie);
            }
            if (brpTypen.length != 1) {
                throw new GbaVoorwaardeOnvertaalbaarExceptie(
                        "Een vergelijking met LO3 rubriek die meerdere BRP expressies oplevert wordt niet ondersteund.");
            } else {
                if (brpTypen[0].isLijst() && !sleutelRubriek) {
                    throw new GbaVoorwaardeOnvertaalbaarExceptie(
                            "Een vergelijking met andere rubriek (die een lijst oplevert) wordt in deze situatie niet ondersteund.");
                } else {
                    resultaat = brpTypen[0].getType();
                }
            }
        } else {
            resultaat = vertaler.andThen(WaardeFormatter::format).apply(ruweWaarde);
        }
        return resultaat;
    }
}
