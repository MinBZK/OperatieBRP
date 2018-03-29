/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.algemeen.Populatie;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;


/**
 * Maakt voor een gegeven persoon een lijst met specifieke meldingen, waarvoor geldt dat het {@link SoortMelding} een waarschuwing is.
 */
public interface MeldingBepalerService {

    /**
     * Maakt lijst met meldingen van het {@link SoortMelding} waarschuwing aan voor lijst personen met referentie naar iedere persoon via het communicatie ID.
     * @param bijgehoudenPersoonList lijst met bijgehouden personen
     * @return meldingen.
     */
    List<Melding> geefWaarschuwingen(final List<BijgehoudenPersoon> bijgehoudenPersoonList);


    /**
     * Maakt lijst met eventuele meldingen van het {@link SoortMelding} waarschuwing aan voor een persoon op basis van een {@link BijgehoudenPersoon}
     * inclusief een referentie naar de persoon via het communicatieID.
     * @param bijgehoudenPersoon {@link BijgehoudenPersoon}
     * @return meldingen.
     */
    List<Melding> geefWaarschuwingen(BijgehoudenPersoon bijgehoudenPersoon);

    /**
     * Maakt eventuele meldingen van het {@link SoortMelding} waarschuwing gegeven een persoon en autorisaties,
     * inclusief een referentie naar de persoon via het communicatieID.
     * @param bijgehoudenPersoon de bijgehouden persoon
     * @param populatie populatie waartoe persoon behoort
     * @param autorisatiebundel autorisatiebundel
     * @return meldingen.
     */
    List<Melding> geefWaarschuwingen(final BijgehoudenPersoon bijgehoudenPersoon, final Populatie populatie, final Autorisatiebundel autorisatiebundel);


}
