/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen;

import java.util.List;

import nl.bzk.brp.model.validatie.Melding;


/**
 * Interface voor het resultaat van een stap in een stappenverwerker.
 */
public interface StappenResultaat {

    /**
     * Methode die nagaat of het resultaat van de stap fouten bevat.
     *
     * @return Geeft <code>true</code> terug als er fouten in het resultaat zijn gevonden.
     * @see #isSuccesvol()
     * @see #bevatStoppendeFouten()
     */
    boolean isFoutief();

    /**
     * Methode die nagaat of het resultaat van de stap succesvol is.
     * Logischer wijs is het resultaat van deze methode het tegenovergestelde van {@link #isFoutief()}.
     *
     * @return Geeft <code>true</code> terug als er geen fouten zijn gevonden.
     * @see #isFoutief()
     */
    boolean isSuccesvol();

    /**
     * Ga na of het resultaat fouten bevat die het proces van verwerken moet stoppen.
     * Is het resultaat van deze methode <code>true</code>, is logischer wijs het resultaat van
     * {@link #isFoutief()} ook <code>true</code>.
     *
     * @return Geeft <code>true</code> terug als er meldingen zijn gevonden die het proces moeten stoppen.
     */
    boolean bevatStoppendeFouten();

    /**
     * Geef de lijst van meldingen it de stappen terug.
     *
     * @return lijst van meldingen.
     */
    List<Melding> getMeldingen();

    /**
     * Voeg een melding toe aan de lijst.
     *
     * @param melding De melding die moet worden toegevoegd.
     */
    void voegMeldingToe(final Melding melding);

    /**
     * Deblokker een melding.
     *
     * @param melding De te deblokkeren melding.
     */
    void deblokkeer(final Melding melding);

    /**
     * Voeg een lijst van meldingen toe.
     *
     * @param lijstMeldingen De lijst met meldingen die moet worden toegevoegd.
     */
    void voegMeldingenToe(final List<Melding> lijstMeldingen);

    /**
     * Sorteer de meldingen, zodat fouten en waarschuwingen niet door elkaar staan.
     */
    void sorteerMeldingen();
}
