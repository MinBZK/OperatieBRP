/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba.ws;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import nl.bzk.brp.delivery.bevraging.gba.ws.vraag.Vraag;
import nl.bzk.brp.delivery.bevraging.gba.ws.vraag.VraagPL;
import nl.bzk.brp.delivery.bevraging.gba.ws.vraag.Zoekparameter;

public class Vragen {
    public static Zoekparameter param(final int rubriek, final String waarde) {
        Zoekparameter zoekparameter = new Zoekparameter();
        zoekparameter.setRubrieknummer(rubriek);
        zoekparameter.setZoekwaarde(waarde);
        return zoekparameter;
    }

    public static Vraag persoonsvraag(final Integer masker, final Zoekparameter... parameters) {
        return persoonsvraag(Collections.singletonList(masker), Arrays.asList(parameters));
    }

    public static Vraag persoonsvraag(final List<Integer> masker, final Zoekparameter parameter) {
        return persoonsvraag(masker, Collections.singletonList(parameter));
    }

    public static Vraag persoonsvraag(final List<Integer> masker, final List<Zoekparameter> parameters) {
        Vraag vraag = vraag(masker, parameters);
        vraag.setIndicatieAdresvraag((byte) 0);
        return vraag;
    }

    public static Vraag adresvraag(final Integer masker, final Zoekparameter... parameters) {
        return adresvraag(Collections.singletonList(masker), Arrays.asList(parameters));
    }

    public static Vraag adresvraag(final List<Integer> masker, final Zoekparameter parameter) {
        return adresvraag(masker, Collections.singletonList(parameter));
    }

    public static Vraag adresvraag(final List<Integer> masker, final Zoekparameter... parameters) {
        return adresvraag(masker, Arrays.asList(parameters));
    }

    public static Vraag adresvraag(final List<Integer> masker, final List<Zoekparameter> parameters) {
        Vraag vraag = vraag(masker, parameters);
        vraag.setIndicatieAdresvraag((byte) 1);
        return vraag;
    }

    public static VraagPL vraagPLVraag(final Zoekparameter... parameters) {
        return vraagPL(Arrays.asList(parameters));
    }

    public static Vraag vraag(final List<Integer> masker, final List<Zoekparameter> parameters) {
        Vraag vraag = new Vraag();
        vraag.setMasker(masker);
        vraag.setIndicatieZoekenInHistorie((byte) 0);
        vraag.setParameters(parameters);
        return vraag;
    }

    public static VraagPL vraagPL(final List<Zoekparameter> parameters) {
        VraagPL vraagPL = new VraagPL();
        vraagPL.setParameters(parameters);
        return vraagPL;
    }
}
