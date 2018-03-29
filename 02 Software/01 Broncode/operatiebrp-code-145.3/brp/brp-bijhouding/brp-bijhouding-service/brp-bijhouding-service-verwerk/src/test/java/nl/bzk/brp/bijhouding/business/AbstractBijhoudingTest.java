/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business;

import static nl.bzk.brp.bijhouding.bericht.model.BmrAttribuutEnum.COMMUNICATIE_ID_ATTRIBUUT;
import static nl.bzk.brp.bijhouding.bericht.model.BmrAttribuutEnum.OBJECTTYPE_ATTRIBUUT;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.bijhouding.bericht.model.AdministratieveHandelingAntwoordElement;
import nl.bzk.brp.bijhouding.bericht.model.AdministratieveHandelingElement;
import nl.bzk.brp.bijhouding.bericht.model.AdministratieveHandelingElementSoort;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingAntwoordBericht;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingBerichtSoort;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBerichtImpl;
import nl.bzk.brp.bijhouding.bericht.model.DatumTijdElement;
import nl.bzk.brp.bijhouding.bericht.model.ParametersElement;
import nl.bzk.brp.bijhouding.bericht.model.ResultaatElement;
import nl.bzk.brp.bijhouding.bericht.model.StringElement;
import nl.bzk.brp.bijhouding.bericht.model.StuurgegevensElement;

/**
 * Abstract class ter ondersteuning van de test.
 */
public abstract class AbstractBijhoudingTest {
    private static final Map<String, String> ATTRIBUTEN = new HashMap<>();
    private static final StringElement GEMEENTE_PARTIJ_STRING = new StringElement("053001");
    private static final StringElement BRP_PARTIJ_STRING = new StringElement("199903");
    private static final StringElement VERZOEK_REFERENTIENUMMER = new StringElement("88409eeb-1aa5-43fc-8614-43055123a165");
    private ZonedDateTime tijdstipOntvangst;

    public AbstractBijhoudingTest() {
        ATTRIBUTEN.put(OBJECTTYPE_ATTRIBUUT.toString(), "objecttype");
        ATTRIBUTEN.put(COMMUNICATIE_ID_ATTRIBUUT.toString(), "ci_test");
        tijdstipOntvangst = DatumUtil.nuAlsZonedDateTime();
    }

    protected BijhoudingVerzoekBericht maakBijhoudingVerzoekBericht() {
        return new BijhoudingVerzoekBerichtImpl(
            ATTRIBUTEN,
            BijhoudingBerichtSoort.REGISTREER_HUWELIJK_GP,
            maakVerzoekStuurgegevens(),
            maakParameters(),
            new AdministratieveHandelingElement(
                ATTRIBUTEN,
                AdministratieveHandelingElementSoort.VOLTREKKING_HUWELIJK_IN_NEDERLAND,
                GEMEENTE_PARTIJ_STRING,
                null,
                null,
                null,
                Collections.emptyList(),
                null));
    }

    protected BijhoudingAntwoordBericht maakAntwoordBericht() {
        return new BijhoudingAntwoordBericht(
            ATTRIBUTEN,
            BijhoudingBerichtSoort.REGISTREER_HUWELIJK_GP_ANTWOORD,
            maakAntwoordStuurgegevens(),
            maakResultaat(),
            Collections.emptyList(),
            new AdministratieveHandelingAntwoordElement(
                ATTRIBUTEN,
                AdministratieveHandelingElementSoort.VOLTREKKING_HUWELIJK_IN_NEDERLAND,
                GEMEENTE_PARTIJ_STRING,
                new DatumTijdElement(tijdstipOntvangst),
                null,
                null,
                null,
                null));
    }

    protected BijhoudingAntwoordBericht maakAntwoordBerichtMetResultaatZonderBijhouding() {
        final ResultaatElement resultaatElement = maakResultaat();
        return new BijhoudingAntwoordBericht(
                ATTRIBUTEN,
                BijhoudingBerichtSoort.REGISTREER_HUWELIJK_GP_ANTWOORD,
                maakAntwoordStuurgegevens(),
                new ResultaatElement(ATTRIBUTEN, new StringElement("Geslaagd"), null, new StringElement("Deblokkeerbaar")),
                Collections.emptyList(),
                new AdministratieveHandelingAntwoordElement(
                        ATTRIBUTEN,
                        AdministratieveHandelingElementSoort.VOLTREKKING_HUWELIJK_IN_NEDERLAND,
                        GEMEENTE_PARTIJ_STRING,
                        new DatumTijdElement(tijdstipOntvangst),
                        null,
                        null,
                        null,
                        null));
    }

    protected ZonedDateTime getTijdstipOntvangst() {
        return tijdstipOntvangst;
    }

    protected Map<String, String> getAttributen() {
        return ATTRIBUTEN;
    }

    private StuurgegevensElement maakVerzoekStuurgegevens() {
        return new StuurgegevensElement(
            ATTRIBUTEN,
            GEMEENTE_PARTIJ_STRING,
            null,
            null,
            VERZOEK_REFERENTIENUMMER,
            null,
            new DatumTijdElement(tijdstipOntvangst));
    }

    private StuurgegevensElement maakAntwoordStuurgegevens() {
        return new StuurgegevensElement(ATTRIBUTEN, BRP_PARTIJ_STRING, null, null, new StringElement(
            "88409eeb-1aa5-43fc-8614-43055123a199"), VERZOEK_REFERENTIENUMMER, new DatumTijdElement(tijdstipOntvangst));
    }

    private ResultaatElement maakResultaat() {
        return new ResultaatElement(ATTRIBUTEN, new StringElement("Geslaagd"), new StringElement("Verwerkt"), new StringElement("Deblokkeerbaar"));
    }

    private ParametersElement maakParameters() {
        return new ParametersElement(ATTRIBUTEN, new StringElement("Bijhouding"));
    }
}
