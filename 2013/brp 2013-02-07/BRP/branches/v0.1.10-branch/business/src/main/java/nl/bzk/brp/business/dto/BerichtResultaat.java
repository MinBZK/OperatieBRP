/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.OverruleMelding;
import nl.bzk.brp.model.validatie.SoortMelding;

/**
 * DTO klasse waarin de resultaten van een berichtverwerking kunnen worden bijgehouden, zoals alle opgetreden
 * meldingen.
 */
public class BerichtResultaat {

    private final List<Melding> meldingen;
    private List<OverruleMelding> overruleMeldingen = null;
    private ResultaatCode resultaat;
    //Cross referentie nummer is verplicht.
    private String berichtCrossReferentieNummer = "onbekend";
    private JaNee  prevalidatie                 = null;

    /** Standaard lege constructor t.b.v. JiBX en andere frameworks. */
    private BerichtResultaat() {
        this(null);
    }

    /**
     * Standaard constructor die direct de velden initialiseert, waarbij de resultaatcode standaard op "Goed" wordt
     * gezet en de meldingen worden geinitialiseerd naar de opgegeven meldingen of een lege lijst van meldingen indien
     * opgegeven meldingen <code>null</code> zijn.
     *
     * @param meldingen de meldingen waarvoor het berichtresultaat geinstantieerd dient te worden.
     */
    public BerichtResultaat(final List<Melding> meldingen) {
        this.meldingen = new ArrayList<Melding>();
        resultaat = ResultaatCode.GOED;

        if (meldingen != null) {
            this.meldingen.addAll(meldingen);
        }
    }

    /**
     * Retourneert de (onaanpasbare) lijst van meldingen.
     *
     * @return de lijst van meldingen.
     */
    public List<Melding> getMeldingen() {
        return Collections.unmodifiableList(meldingen);
    }

    /**
     * Voegt een melding toe aan de huidige reeks van meldingen.
     *
     * @param melding De toe te voegen melding.
     */
    public void voegMeldingToe(final Melding melding) {
        meldingen.add(melding);
    }

    /**
     * Voegt een lijst van meldingen toe aan de huidige reeks van meldingen.
     *
     * @param berichtMeldingen De toe te voegen meldingen.
     */
    public void voegMeldingenToe(final List<Melding> berichtMeldingen) {
        meldingen.addAll(berichtMeldingen);
    }

    /**
     * Retourneert de resultaat code die aangeeft of de verwerking goed is gegaan of niet.
     *
     * @return de resultaat code van het berichtresultaat.
     */
    public ResultaatCode getResultaatCode() {
        return resultaat;
    }

    /** Markeert dit bericht resultaat als dat de verwerking foutief is verlopen. */
    public void markeerVerwerkingAlsFoutief() {
        resultaat = ResultaatCode.FOUT;
    }

    /**
     * Bepaalt (en retourneert) de {@link ResultaatCode} voor een bericht resultaat, gebaseerd op de meldingen reeds in
     * dit resultaat. Indien deze meldingen een of meerdere fouten bevat, zal deze methode {@link ResultaatCode#FOUT}
     * retourneren, maar als er geen fouten zijn, dan zal er {@link ResultaatCode#GOED} worden geretourneerd.
     *
     * @return de resultaatcode op basis van de opgegeven meldingen.
     */
    public boolean bevatVerwerkingStoppendeFouten() {
        boolean bevatFouten = false;

        for (Melding melding : meldingen) {
            if (melding.getSoort() == SoortMelding.FOUT_OVERRULEBAAR
                || melding.getSoort() == SoortMelding.FOUT_ONOVERRULEBAAR)
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
     * @param gevondenOverruleMeldingen de lijst die gedowngrade moeten worden.
     * @return true als er minimaal een melding is overruled.
     */
    public boolean overruleAlleOverrulebareFouten(final Collection<Melding> gevondenOverruleMeldingen) {
        boolean isOverruled = false;
        for (Melding melding : gevondenOverruleMeldingen) {
            if (melding.getSoort() == SoortMelding.FOUT_OVERRULEBAAR && meldingen.contains(melding)) {
                melding.overrule();
                isOverruled = true;
            }
        }
        return isOverruled;

    }

    public String getBerichtCrossReferentieNummer() {
        return berichtCrossReferentieNummer;
    }

    public void setBerichtCrossReferentieNummer(final String berichtCrossReferentieNummer) {
        this.berichtCrossReferentieNummer = berichtCrossReferentieNummer;
    }

    public List<OverruleMelding> getOverruleMeldingen() {
        return overruleMeldingen;
    }

    /**
     * Voeg een lijst van daadwerkelijk overruled meldingen. Dit is de originele die de gebruiker heeft aangeleverd.
     * Deze lijst wordt aan het eind gebruikt in de return bericht.
     * @param overrMeldingen de lijst.
     */
    public void voegtoeOverruleMeldingen(final List<OverruleMelding> overrMeldingen) {
        if (overruleMeldingen == null) {
            overruleMeldingen = new ArrayList<OverruleMelding>(overrMeldingen);
        } else {
            overruleMeldingen.addAll(overrMeldingen);
        }
    }

    public JaNee getPrevalidatie() {
        return prevalidatie;
    }

    public void setPrevalidatie(final JaNee prevalidatie) {
        this.prevalidatie = prevalidatie;
    }
}
