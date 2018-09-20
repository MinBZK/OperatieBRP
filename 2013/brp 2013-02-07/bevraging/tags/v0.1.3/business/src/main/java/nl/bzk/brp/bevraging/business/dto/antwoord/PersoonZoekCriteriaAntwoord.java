/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.dto.antwoord;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import nl.bzk.brp.bevraging.domein.Persoon;


/**
 * Antwoord bericht DTO die de gegevens bevat van personen die aan de in het verzoek bericht opgegeven zoek criteria
 * voldoen.
 */
public class PersoonZoekCriteriaAntwoord implements BRPAntwoord {

    private final Set<Persoon> personen;

    /**
     * Constructor die de collectie van personen zet naar de opgegeven collectie. Indien de opgegeven {@code null} is,
     * wordt de collectie geinitialiseerd naar een lege collectie.
     * @param personen collectie van personen.
     */
    public PersoonZoekCriteriaAntwoord(final Collection<Persoon> personen) {
        this.personen = new HashSet<Persoon>();
        if (personen != null) {
            this.personen.addAll(personen);
        }
    }

    /**
     * Als er geen of 1 persoon in het antwoord zit.
     *
     * @param persoon enkel persoon als resultaat.
     */
    public PersoonZoekCriteriaAntwoord(final Persoon persoon) {
        // CHECKSTYLE:OFF
        // Kan dit niet omzetten naar normale if-statement vanwege feit dat call naar andere constructor op de eerste regel moet staan
        this(persoon != null ? Arrays.asList(persoon) : (Collection<Persoon>) null);
        // CHECKSTYLE:ON
    }

    /**
     * Creeert een antwoord bericht zonder personen als resultaat.
     */
    public PersoonZoekCriteriaAntwoord() {
        this((Collection<Persoon>) null);
    }

    @Override
    public Collection<Persoon> getPersonen() {
        return personen;
    }

}
