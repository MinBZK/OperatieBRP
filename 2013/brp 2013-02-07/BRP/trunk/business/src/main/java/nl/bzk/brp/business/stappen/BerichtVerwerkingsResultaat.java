/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.bericht.ber.AdministratieveHandelingGedeblokkeerdeMeldingBericht;
import nl.bzk.brp.model.validatie.Melding;

/**
 * DTO klasse waarin de resultaten van een berichtverwerking kunnen worden bijgehouden, zoals alle opgetreden
 * meldingen.
 */
public class BerichtVerwerkingsResultaat extends AbstractStappenResultaat {

    private final List<Melding> meldingen;
    private List<AdministratieveHandelingGedeblokkeerdeMeldingBericht> overruleMeldingen = null;
    private boolean verwerkingsResultaat = true;

    /**
     * Standaard lege constructor t.b.v. JiBX en andere frameworks.
     */
    private BerichtVerwerkingsResultaat() {
        this(null);
    }

    /**
     * Standaard constructor die direct de velden initialiseert, waarbij de resultaatcode standaard op "Goed" wordt
     * gezet en de meldingen worden geinitialiseerd naar de opgegeven meldingen of een lege lijst van meldingen indien
     * opgegeven meldingen <code>null</code> zijn.
     *
     * @param meldingen de meldingen waarvoor het berichtresultaat geinstantieerd dient te worden.
     */
    public BerichtVerwerkingsResultaat(final List<Melding> meldingen) {
        this.meldingen = new ArrayList<Melding>();
        verwerkingsResultaat = true;

        if (meldingen != null) {
            this.meldingen.addAll(meldingen);
            Collections.sort(this.meldingen);
        }
    }

    /**
     * Retourneert de (onaanpasbare) lijst van meldingen.
     *
     * @return de lijst van meldingen.
     */
    @Override
    public List<Melding> getMeldingen() {
        return Collections.unmodifiableList(meldingen);
    }

    /**
     * Voegt een melding toe aan de huidige reeks van meldingen.
     *
     * @param melding De toe te voegen melding.
     */
    @Override
    public void voegMeldingToe(final Melding melding) {
        if (null != melding) {
            meldingen.add(melding);
            Collections.sort(meldingen);
        }
    }

    /**
     * Voegt een lijst van meldingen toe aan de huidige reeks van meldingen.
     *
     * @param berichtMeldingen De toe te voegen meldingen.
     */
    @Override
    public void voegMeldingenToe(final List<Melding> berichtMeldingen) {
        if (null != berichtMeldingen) {
            meldingen.addAll(berichtMeldingen);
            Collections.sort(meldingen);
        }
    }

    /**
     * Retourneert de resultaat code die aangeeft of de verwerking goed is gegaan of niet.
     *
     * @return de resultaat code van het berichtresultaat.
     */
    public boolean getVerwerkingsResultaat() {
        return verwerkingsResultaat;
    }

    /**
     * Markeert dit bericht resultaat als dat de verwerking foutief is verlopen.
     */
    public void markeerVerwerkingAlsFoutief() {
        verwerkingsResultaat = false;
    }

    /**
     * Bepaalt verwerkingsresultaat voor een bericht, gebaseerd op de meldingen reeds in
     * het resultaat. Indien deze meldingen een of meerdere fouten bevat, zal deze methode true
     * retourneren, maar als er geen fouten zijn, dan zal er false worden geretourneerd.
     *
     * @return de resultaatcode op basis van de opgegeven meldingen.
     */
    public boolean bevatVerwerkingStoppendeFouten() {
        boolean bevatFouten = false;

        for (Melding melding : meldingen) {
            if (melding.getSoort() == SoortMelding.DEBLOKKEERBAAR || melding.getSoort() == SoortMelding.FOUT)
            {
                bevatFouten = true;
                break;
            }
        }
        return bevatFouten;
    }

    /**
     * Overrule alle overrulebare fouten. Er is vantevoren al goed gekeken dat alle fouten die overrulebaar zijn
     * geoverruled kunnen/mogen worden.
     *
     * @param gevondenOverruleMeldingen de lijst die gedowngrade moeten worden.
     * @return true als er minimaal een melding is overruled.
     */
    public boolean overruleAlleOverrulebareFouten(final Collection<Melding> gevondenOverruleMeldingen) {
        // TODO refactor: overrule() aanroep zou gedaan moeten worden op this.meldingen ipv
        // gevondenOverruleMeldingen, implementatie gaat nu ervanuit dat gevondenOverruleMeldingen bestaat uit dezelfde
        // objecten als this.meldingen
        boolean isOverruled = false;
        for (Melding melding : gevondenOverruleMeldingen) {
            if (melding.getSoort() == SoortMelding.DEBLOKKEERBAAR && meldingen.contains(melding)) {
                melding.overrule();
                isOverruled = true;
            }
        }
        Collections.sort(meldingen);

        return isOverruled;

    }

    public List<AdministratieveHandelingGedeblokkeerdeMeldingBericht> getOverruleMeldingen() {
        // TODO: bolie: Hoe wordt de lijst gezet (in jibx, wordt de hele lijst gedaan of per stuk toegevoegd).
        // In beide gevallen moet de sort overheen gegooid worden.
        // Merk op dat we geen settter hebben voor de lijst.
        return overruleMeldingen;
    }

    /**
     * Voeg een lijst van daadwerkelijk overruled meldingen. Dit is de originele die de gebruiker heeft aangeleverd.
     * Deze lijst wordt aan het eind gebruikt in de return bericht.
     *
     * @param nieuweOverruleMeldingen de lijst.
     */
    public void voegtoeOverruleMeldingen(
            final List<AdministratieveHandelingGedeblokkeerdeMeldingBericht> nieuweOverruleMeldingen)
    {
        if (overruleMeldingen == null) {
            overruleMeldingen = new ArrayList<AdministratieveHandelingGedeblokkeerdeMeldingBericht>(
                    nieuweOverruleMeldingen);
        } else {
            overruleMeldingen.addAll(nieuweOverruleMeldingen);
        }
        Collections.sort(overruleMeldingen);
    }

    @Override
    public boolean isFoutief() {
        return !verwerkingsResultaat;
    }

    @Override
    public boolean isSuccesvol() {
        return verwerkingsResultaat;
    }

    @Override
    public boolean bevatStoppendeFouten() {
        return bevatVerwerkingStoppendeFouten();
    }
}
