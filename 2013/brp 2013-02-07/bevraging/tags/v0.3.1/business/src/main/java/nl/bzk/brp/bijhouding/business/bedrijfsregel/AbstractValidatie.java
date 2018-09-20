/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.bedrijfsregel;

import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFoutCode;
import nl.bzk.brp.bevraging.business.dto.verzoek.BerichtVerzoek;


/**
 * Superklasse voor de java implementatie van een bedrijfsregel.
 * Voor het valideren van bedrijfsregels wordt het Command pattern gebruikt.
 * Dit is de gemeenschappelijke superklasse van alle bedrijfsregel validaties.
 * Het is de Command interface uit het Command pattern.
 *
 * @param <T> het berichtverzoektype waarvoor deze validatie geschikt is
 */
public abstract class AbstractValidatie<T extends BerichtVerzoek<?>> {

    private T verzoek;

    /**
     * Voert de validatie uit. Het is de execute methode uit het Command pattern.
     *
     * @return {@link ValidatieResultaat#CONFORM} als aan de bedrijfsregel voldaan is, anders
     *         {@link ValidatieResultaat#AFWIJKEND}
     */
    public abstract ValidatieResultaat voerUit();

    /**
     * @return de foutcode die hoort bij deze validatie
     */
    public abstract BerichtVerwerkingsFoutCode getFoutCode();

    public T getVerzoek() {
        return verzoek;
    }

    @SuppressWarnings("unchecked")
    public void setVerzoek(final BerichtVerzoek<?> verzoek) {
        this.verzoek = (T) verzoek;
    }

}
