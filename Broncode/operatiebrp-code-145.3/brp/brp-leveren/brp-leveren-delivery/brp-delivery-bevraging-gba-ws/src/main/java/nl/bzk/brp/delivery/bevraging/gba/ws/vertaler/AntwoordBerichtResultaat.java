/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba.ws.vertaler;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;

/**
 * Enum met de resultaat waarden voor de Ad Hoc webservice.
 */
public enum AntwoordBerichtResultaat {

    OK("A", 0, ""),

    NIET_GEVONDEN("G", 33, "Geen gegevens gevonden", Regel.R1403),

    TECHNISCHE_FOUT_ALGEMEEN("X", 1, "Technische fout", Regel.ALG0001, Regel.R1274, Regel.R2265, Regel.R2266, Regel.R2267, Regel.R2281, Regel.R2295,
            Regel.R2297, Regel.R2389, Regel.R2610),

    TECHNISCHE_FOUT_032("P", 32, "Te veel zoekresultaten", Regel.R2289, Regel.R2392),

    TECHNISCHE_FOUT_031("T", 31, "Zoekproces afgebroken", Regel.R2284, Regel.R2285, Regel.R2340),

    TECHNISCHE_FOUT_013("X", 13, "Geen actuele autorisatietabelregel", Regel.R2405, Regel.R2343),

    TECHNISCHE_FOUT_014("X", 14, "Niet geautoriseerd voor GBA ad hoc vragen"),

    TECHNISCHE_FOUT_018("X", 18, "Niet toegestaan zoekcriterium gebruikt: %s", Regel.R2290),
    TECHNISCHE_FOUT_018_HISTORISCH_LEEG("X", 18, "Niet toegestaan leeg zoekcriterium gebruikt voor historische zoekvraag: %s", Regel.R2290),

    TECHNISCHE_FOUT_019("X", 19, "Geen correcte persoonsidentificatie", Regel.R2288),

    TECHNISCHE_FOUT_020("X", 20, "Geen correcte persoons- of adresidentificatie", Regel.R2373, Regel.R2374, Regel.R2375),

    TECHNISCHE_FOUT_022("X", 22, "Numeriek zoekcriterium %s bevat geen numerieke waarde", Regel.R2308),

    TECHNISCHE_FOUT_024("X", 24, "Dubbel rubrieknummer in zoekcriteria niet toegestaan: %s"),

    TECHNISCHE_FOUT_025("X", 25, "Dubbel rubrieknummer in masker niet toegestaan: %s"),

    TECHNISCHE_FOUT_026("X", 26, "Onjuiste lengte voor rubriek: %s", Regel.R2311),

    TECHNISCHE_FOUT_027("X", 27, "Niet geautoriseerd voor opvragen PL"),

    TECHNISCHE_FOUT_037("Z", 37, "Geen PL-en die aan de verstrekkingcondities voldoen", Regel.R1640);

    private final String letter;
    private final int code;
    private final String omschrijving;
    private final List<Regel> regels;

    AntwoordBerichtResultaat(final String letter, final int code, final String omschrijving, final Regel... regels) {
        this.letter = letter;
        this.code = code;
        this.omschrijving = omschrijving;
        this.regels = Collections.unmodifiableList(Arrays.asList(regels));
    }

    /**
     * Bepaalt op basis van de regel het bijbehorende resultaat.
     * @param regel de regel waarop het resultaat wordt bepaald
     * @return het resultaat
     * @throws IllegalArgumentException in het geval er een ongeldige regel wordt gebruikt of een regel in meerdere resultaten voorkomt
     */
    public static Optional<AntwoordBerichtResultaat> of(final Regel regel) {
        return Arrays.stream(values())
                .filter(adHocWebserviceResultaat -> !adHocWebserviceResultaat.getRegels().stream()
                        .filter(regel::equals)
                        .collect(Collectors.toList())
                        .isEmpty())
                .findFirst();
    }

    /**
     * Geeft de letter van het resultaat.
     * @return de letter van het resultaat
     */
    public String getLetter() {
        return letter;
    }

    /**
     * Geeft de code van het resultaat.
     * @return de code van het resultaat
     */
    public int getCode() {
        return code;
    }

    /**
     * Geeft de omschrijving van het resultaat.
     * @param args lijst van argumenten die gesubstitueerd worden in de omschrijving
     * @return de omschrijving van het resultaat
     */
    public String getOmschrijving(String... args) {
        if (args == null) {
            return omschrijving;
        } else if (args.length == 0) {
            return String.format(omschrijving, "");
        } else {
            return String.format(omschrijving, (Object[]) args);
        }
    }

    /**
     * Geeft de lijst van regels van het resultaat.
     * @return de lijst van regels van het resultaat
     */
    public List<Regel> getRegels() {
        return regels;
    }
}
