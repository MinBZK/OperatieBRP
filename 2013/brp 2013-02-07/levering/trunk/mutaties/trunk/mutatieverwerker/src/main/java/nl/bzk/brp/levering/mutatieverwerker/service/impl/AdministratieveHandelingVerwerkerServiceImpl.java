/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatieverwerker.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.levering.mutatieverwerker.model.AdministratieveHandelingMutatie;
import nl.bzk.brp.levering.mutatieverwerker.repository.AdministratieveHandelingVerwerkerRepository;
import nl.bzk.brp.levering.mutatieverwerker.service.AdministratieveHandelingStappenVerwerker;
import nl.bzk.brp.levering.mutatieverwerker.service.AdministratieveHandelingVerwerkerService;
import nl.bzk.brp.levering.mutatieverwerker.service.AdministratieveHandelingVerwerkingContext;
import org.springframework.stereotype.Service;

/**
 * Implementatie klasse van de AdministratieveHandendelingVerwerkerService. Verzorgt de verwerking van mutaties/
 * administratieve handelingen.
 */
@Service
public class AdministratieveHandelingVerwerkerServiceImpl implements AdministratieveHandelingVerwerkerService {

    @Inject
    private AdministratieveHandelingVerwerkerRepository administratieveHandelingVerwerkerRepository;

    @Inject
    private AdministratieveHandelingStappenVerwerker administratieveHandelingStappenVerwerker;

    @Override
    public void verwerkAdministratieveHandeling(final Long administratieveHandelingId) {

        final AdministratieveHandelingMutatie administratieveHandelingMutatie =
                maakAdministratieveHandelingMutatie(administratieveHandelingId);

        startStappenVerwerking(administratieveHandelingMutatie);
    }

    /**
     * Maakt een AdministratieveHandelingMutatie object, met daarin het id van de administratieve handeling en de
     * lijst van bsn's van de betrokken personen.
     *
     * @param administratieveHandelingId Het id van de administratieve handeling.
     * @return Het AdministratieveHandelingMutatie object.
     */
    private AdministratieveHandelingMutatie maakAdministratieveHandelingMutatie(final Long administratieveHandelingId) {
        final List<String> betrokkenPersonenBsns = haalBetrokkenPersonenBsns(administratieveHandelingId);

        return new AdministratieveHandelingMutatie(administratieveHandelingId, betrokkenPersonenBsns);
    }

    /**
     * Haalt een lijst van bsn's van betrokken personen op voor een administratieve handeling.
     *
     * @param administratieveHandelingId Het id van de administratieve handeling.
     * @return De lijst van bsn's als strings.
     */
    private List<String> haalBetrokkenPersonenBsns(final Long administratieveHandelingId) {
        final List<Integer> betrokkenPersonenBsns =
                administratieveHandelingVerwerkerRepository
                        .haalAdministratieveHandelingPersoonBsns(administratieveHandelingId);
        return converteerBsnIntegerLijstNaarStrings(betrokkenPersonenBsns);
    }

    /**
     * Converteerd de lijst van Integer bsn's naar String bsn's, dit omdat de database Integers levert en de
     * AdministratieveHandelingMutatie een lijst van Strings nodig heeft.
     *
     * @param betrokkenPersonenBsns De lijst van bsn's als Integers.
     * @return De lijst van bsn's als Strings.
     */
    private List<String> converteerBsnIntegerLijstNaarStrings(final List<Integer> betrokkenPersonenBsns) {
        final List<String> betrokkenPersonenBsnsAlsStrings = new ArrayList<String>();
        for (Integer betrokkenPersoonBsn : betrokkenPersonenBsns) {
            betrokkenPersonenBsnsAlsStrings.add(String.valueOf(betrokkenPersoonBsn));
        }
        return betrokkenPersonenBsnsAlsStrings;
    }

    /**
     * Start de stappenverwerking voor een administratieve handeling mutatie.
     *
     * @param administratieveHandelingMutatie
     *         De administratieve handeling mutatie.
     */
    private void startStappenVerwerking(final AdministratieveHandelingMutatie administratieveHandelingMutatie) {
        administratieveHandelingStappenVerwerker.verwerk(
                administratieveHandelingMutatie, new AdministratieveHandelingVerwerkingContext());
    }


}
