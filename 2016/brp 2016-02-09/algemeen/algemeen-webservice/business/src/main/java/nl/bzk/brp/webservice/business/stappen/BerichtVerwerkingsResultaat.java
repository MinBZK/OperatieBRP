/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.business.stappen;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import nl.bzk.brp.business.stappen.StappenResultaat;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingGedeblokkeerdeMeldingBericht;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.validatie.Melding;

/**
 * Het resultaat van een bericht verwerking wordt hier opgeslagen voor terugkoppeling in bijvoorbeeld het antwoord
 * bericht.
 */
public interface BerichtVerwerkingsResultaat extends StappenResultaat {

    /**
     * Bepaalt verwerkingsresultaat voor een bericht, gebaseerd op de meldingen reeds in
     * het resultaat. Indien deze meldingen een of meerdere fouten bevat, zal deze methode true
     * retourneren, maar als er geen fouten zijn, dan zal er false worden geretourneerd.
     *
     * @return de resultaatcode op basis van de opgegeven meldingen.
     */
    boolean bevatVerwerkingStoppendeFouten();

    /**
     * Overrule alle overrulebare fouten. Er is vantevoren al goed gekeken dat alle fouten die overrulebaar zijn
     * geoverruled kunnen/mogen worden.
     *
     * @param gevondenOverruleMeldingen de lijst die gedowngrade moeten worden.
     * @return true als er minimaal een melding is overruled.
     */
    boolean overruleAlleOverrulebareFouten(Collection<Melding> gevondenOverruleMeldingen);

    /**
     * Geef de lijst met gedeblokkeerde meldingen.
     *
     * @return lijst geblokkeerde meldingen.
     */
    List<AdministratieveHandelingGedeblokkeerdeMeldingBericht> getOverruleMeldingen();

    /**
     * Voeg een lijst van daadwerkelijk overruled meldingen. Dit is de originele die de gebruiker heeft aangeleverd.
     * Deze lijst wordt aan het eind gebruikt in de return bericht.
     *
     * @param nieuweOverruleMeldingen de lijst.
     */
    void voegtoeOverruleMeldingen(
            List<AdministratieveHandelingGedeblokkeerdeMeldingBericht> nieuweOverruleMeldingen);

    @Override
    boolean bevatStoppendeFouten();

    /**
     * Geef de administratieve handeling.
     *
     * @return de administratieve handeling.
     */
    AdministratieveHandelingModel getAdministratieveHandeling();

    /**
     * Bewaar de administratieve handeling in het resultaat.
     *
     * @param administratieveHandeling de administratieve handeling.
     */
    void setAdministratieveHandeling(AdministratieveHandelingModel administratieveHandeling);

    /**
     * Retourneert de te archiveren personen v.w.b. het inkomende bericht.
     * @return lijst van persoon id's die gearchiveerd moeten worden.
     */
    Set<Integer> getTeArchiverenPersonenIngaandBericht();

    /**
     * Retourneert de te archiveren personen v.w.b. het uitgaand bericht.
     * @return lijst van persoon id's die gearchiveerd moeten worden.
     */
    Set<Integer> getTeArchiverenPersonenUitgaandBericht();
}
