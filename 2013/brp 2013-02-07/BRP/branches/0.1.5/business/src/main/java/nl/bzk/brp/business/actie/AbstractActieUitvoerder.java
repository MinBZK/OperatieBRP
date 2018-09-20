/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie;

import java.util.List;

import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.model.logisch.BRPActie;
import nl.bzk.brp.model.validatie.Melding;

/**
 * Abstracte implementatie van een actie uitvoerder die regelt dat de actie eerst wordt gevalideert en daarna
 * uitgevoerd. Implementerende classes moeten de implementatie voor de validatie en actie verwerking implementeren.
 */
public abstract class AbstractActieUitvoerder {

    /**
     * Voert de actie uit en retourneert eventuele meldingen bij fouten.
     *
     * @param actie de actie die uitgevoerd dient te worden.
     * @param berichtContext de context waarbinnen het bericht en dus de actie uitgevoerd dient te worden.
     * @return een lijst van eventueel opgetreden fouten en/of waarschuwingen.
     */
    public final List<Melding> voerUit(final BRPActie actie, final BerichtContext berichtContext) {
        List<Melding> meldingen;
        meldingen = valideerActieGegevens(actie);
        if (meldingen == null || meldingen.isEmpty()) {
            meldingen = verwerkActie(actie, berichtContext);
        }
        return meldingen;
    }

    /**
     * Valideert de gegevens in de actie alvorens deze wordt verwerkt.
     *
     * @param actie De te valideren actie.
     * @return Lijst van eventuele validatie meldingen.
     */
    abstract List<Melding> valideerActieGegevens(final BRPActie actie);

    /**
     * Verwerkt de actie in de BRP.
     *
     * @param actie De te verwerken actie.
     * @param berichtContext de context waarbinnen het bericht en dus de actie uitgevoerd dient te worden.
     * @return Lijst van eventuele meldingen m.b.t. de verwerking.
     */
    abstract List<Melding> verwerkActie(final BRPActie actie, final BerichtContext berichtContext);

}
