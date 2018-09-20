/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.service;

import java.util.List;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;

/**
 */
public interface PersoonService {

    /**
     * Verifieert of een persoon opgegeven in het bericht identiek is aan een persoon opgehaald uit de database.
     * Let op: we gaan vanuit dat in het bericht een persoon wordt geidentificeerd adh. technische sleutel OF
     * identicatienummers.burgerServiceNummer OF identicatienummers.adminstratieveServiceNummer
     * <br/>
     * Personen met referentieID wordt nog niet ondersteund (heeft communicatieMap nodig.
     * <br/>
     * Zijn alle velden hierboven leeg, gaan we vanuit dat niet om een niet ingeschreve gaat en dan is het
     * per definitie niet gelijk.
     * @param persoonBericht de persoon uit het bericht
     * @param persoonModel de persoon uit het operationeel model.
     * @return true als identiek, false anders.
     */
    boolean isPersoonBerichtIdentiekAlsPersoonModel(final PersoonBericht persoonBericht,
            final PersoonModel persoonModel);

    /**
     * Bouw een technische sleutel adhv. een persoonModel.
     * @param persoonModel de persoon
     * @return de technische sleutel.
     */
    String maakTechnischeSleutelVanPersoonModel(final PersoonModel persoonModel);

    /**
     * Test of een persoon in het bericht 'identiek' is als een ander persoon in het bericht.
     * Dit wordt getest aan de hand van referentieID <-> communicatieID combinatie of
     * dat de beide personen dezelfde technische sleutels.
     * @param persoon1 de ene persoon.
     * @param persoon2 de ander persoon.
     * @return true als ze verwijzen naar dezelfde persoon, false anders.
     */
    boolean isIdentiekPersoon(final PersoonBericht persoon1, final PersoonBericht persoon2);

    /**
     * Test of een persoon uit een neven actie voorkomt in de hoofdpersonen uit de hoofdactie.
     * @param nevenActiePersoon de persoon uit een neven actie
     * @param hoofdPersonen de lijst van personen uit de hoofdactie (altijd 1, behalve bij huwelijk/parnetschap)
     * @return true als de persoon uit neven actie verwijst naar een persoon uit de hoofdacte, false anders.
     */
    boolean isPersoonNevenActieIdentiekHoofdPersoon(
            final PersoonBericht nevenActiePersoon, final List<PersoonBericht> hoofdPersonen);

}
