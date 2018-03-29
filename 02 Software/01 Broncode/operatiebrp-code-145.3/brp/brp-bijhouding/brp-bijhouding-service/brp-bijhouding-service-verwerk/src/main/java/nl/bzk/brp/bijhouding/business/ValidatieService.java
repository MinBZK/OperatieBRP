/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;
import nl.bzk.brp.bijhouding.bericht.model.MeldingElement;

/**
 * Interface voor het valideren van bijhoudingsberichten.
 */
public interface ValidatieService {
    /**
     * Valideert het gegeven bericht.
     *
     * @param bericht
     *            het bericht
     * @return de lijst met meldingen
     */
    List<MeldingElement> valideer(BijhoudingVerzoekBericht bericht);

    /**
     * Controleert of de verwerking van het bericht kan door gaan mbv de eerder gevonden meldingen. Als er een melding
     * is met het soort {@link SoortMelding#FOUT}, dan kan de verwerking niet verder gaan.
     *
     * @param meldingen de lijst van meldingen die op een eerder moment is vastgesteld
     * @return true als er geen meldingen zijn met het soort {@link SoortMelding#FOUT}, anders false
     */
    boolean kanVerwerkingDoorgaan(List<MeldingElement> meldingen);

    /**
     * Bepaal het hoogste meldingniveau van de meegegeven lijst van meldingen.
     *
     * @param meldingen de lijst van meldingen waarvan de hoogste meldingniveau bepaalt moet worden
     * @return een {@link nl.bzk.brp.bijhouding.bericht.model.StringElement} met daarin de
     *         {@link SoortMelding#getNaam()} van het hoogste meldingniveau
     */
    SoortMelding bepaalHoogsteMeldingNiveau(List<MeldingElement> meldingen);
}
