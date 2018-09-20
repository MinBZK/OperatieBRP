/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.business.stappen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import nl.bzk.brp.business.stappen.AbstractStappenResultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingGedeblokkeerdeMeldingBericht;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.validatie.Melding;

/**
 * DTO klasse waarin de resultaten van een berichtverwerking kunnen worden bijgehouden, zoals alle opgetreden
 * meldingen.
 */
public class BerichtVerwerkingsResultaatImpl extends AbstractStappenResultaat implements BerichtVerwerkingsResultaat {
    // de administratieve handeling (is null als er een fout is opgetreden of is een prevalidatie).
    // is veel cleaner dan elke keer uitzoeken of iets terug gedraaid wordt of niet.
    private AdministratieveHandelingModel administratieveHandeling;

    private List<AdministratieveHandelingGedeblokkeerdeMeldingBericht> overruleMeldingen;
   /**
     * Maak een resultaat aan zonder meldingen.
     */
    public BerichtVerwerkingsResultaatImpl() {
        this(null);
    }

    /**
     * Standaard constructor die direct de velden initialiseert, waarbij de resultaatcode standaard op "Goed" wordt
     * gezet en de meldingen worden geinitialiseerd naar de opgegeven meldingen of een lege lijst van meldingen indien
     * opgegeven meldingen <code>null</code> zijn.
     *
     * @param meldingen de meldingen waarvoor het berichtresultaat geinstantieerd dient te worden.
     */
    public BerichtVerwerkingsResultaatImpl(final List<Melding> meldingen) {
        super(meldingen);
    }

    /**
     * Bepaalt verwerkingsresultaat voor een bericht, gebaseerd op de meldingen reeds in
     * het resultaat. Indien deze meldingen een of meerdere fouten bevat, zal deze methode true
     * retourneren, maar als er geen fouten zijn, dan zal er false worden geretourneerd.
     *
     * @return de resultaatcode op basis van de opgegeven meldingen.
     */
    @Override
    public boolean bevatVerwerkingStoppendeFouten() {
        for (Melding melding : getMeldingen()) {
            if (melding.getSoort() == SoortMelding.DEBLOKKEERBAAR || melding.getSoort() == SoortMelding.FOUT) {
                return true;
            }
        }
        return false;
    }

    /**
     * Overrule alle overrulebare fouten. Er is vantevoren al geverifieerd dat alle fouten die overrulebaar zijn
     * geoverruled kunnen/mogen worden.
     *
     * @param teDeblokkerenMeldingen de lijst die gedowngrade moeten worden.
     * @return true als er minimaal een melding is overruled.
     */
    @Override
    public boolean overruleAlleOverrulebareFouten(final Collection<Melding> teDeblokkerenMeldingen) {
        boolean isOverruled = false;
        for (Melding melding : teDeblokkerenMeldingen) {
            if (melding.getSoort() == SoortMelding.DEBLOKKEERBAAR && getMeldingen().contains(melding)) {
                melding.overrule();
                isOverruled = true;
            }
        }
        sorteerMeldingen();

        return isOverruled;

    }

    @Override
    public List<AdministratieveHandelingGedeblokkeerdeMeldingBericht> getOverruleMeldingen() {
        return overruleMeldingen;
    }

    /**
     * Voeg een lijst van daadwerkelijk overruled meldingen. Dit is de originele die de gebruiker heeft aangeleverd.
     * Deze lijst wordt aan het eind gebruikt in de return bericht.
     *
     * @param nieuweOverruleMeldingen de lijst.
     */
    @Override
    public void voegtoeOverruleMeldingen(
            final List<AdministratieveHandelingGedeblokkeerdeMeldingBericht> nieuweOverruleMeldingen)
    {
        if (overruleMeldingen == null) {
            overruleMeldingen = new ArrayList<>(
                nieuweOverruleMeldingen);
        } else {
            overruleMeldingen.addAll(nieuweOverruleMeldingen);
        }
        Collections.sort(overruleMeldingen);
    }

    @Override
    public boolean bevatStoppendeFouten() {
        return bevatVerwerkingStoppendeFouten();
    }

    @Override
    public AdministratieveHandelingModel getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    @Override
    public void setAdministratieveHandeling(final AdministratieveHandelingModel administratieveHandeling) {
        this.administratieveHandeling = administratieveHandeling;
    }

    @Override
    public Set<Integer> getTeArchiverenPersonenIngaandBericht() {
        // default implementatie
        return Collections.emptySet();
    }

    @Override
    public Set<Integer> getTeArchiverenPersonenUitgaandBericht() {
        // default implementatie
        return Collections.emptySet();
    }
}
