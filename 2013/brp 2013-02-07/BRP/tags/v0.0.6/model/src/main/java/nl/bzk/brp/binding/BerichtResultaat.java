/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Model class voor het xsd type BerichtResultaat.
 */
public class BerichtResultaat {

    private List<Melding> meldingen;
    private ResultaatCode resultaat;

    /**
     * Standaard lege constructor t.b.v. JiBX en andere frameworks.
     */
    private BerichtResultaat() {
        this(null);
    }

    /**
     * Standaard constructor die direct de velden initialiseert, waarbij de resultaatcode wordt bepaald op basis van de
     * meldingen waarvoor het BerichtResultaat wordt geinstantieerd. Indien er een of meerdere meldingen zijn die een
     * fout representeren, dan zal de ResultaatCode op {@link ResultaatCode.FOUT} worden gezet en anders op {@link
     * ResultaatCode.GOED}.
     *
     * @param meldingen de meldingen waarvoor het berichtresultaat geinstantieerd dient te worden.
     */
    public BerichtResultaat(final List<Melding> meldingen) {
        if (meldingen == null) {
            this.meldingen = null;
        } else {
            this.meldingen = new ArrayList<Melding>();
            this.meldingen.addAll(meldingen);
        }
        resultaat = bepaalResultaatCode();
    }

    /**
     * Retourneert de lijst van meldingen. Indien deze <code>null</code> is, wordt ook <code>null</code> geretourneerd.
     *
     * @return de lijst van meldingen.
     */
    public List<Melding> getMeldingen() {
        if (meldingen == null) {
            return null;
        }

        return Collections.unmodifiableList(meldingen);
    }

    /**
     * Voegt een melding toe aan de huidige reeks van meldingen.
     *
     * @param melding De toe te voegen melding.
     */
    public void voegMeldingToe(final Melding melding) {
        if (meldingen == null) {
            meldingen = new ArrayList<Melding>();
        }
        meldingen.add(melding);
        resultaat = bepaalResultaatCode();
    }

    /**
     * Voegt een lijst van meldingen toe aan de huidige reeks van meldingen.
     *
     * @param berichtMeldingen De toe te voegen meldingen.
     */
    public void voegMeldingenToe(final List<Melding> berichtMeldingen) {
        if (meldingen == null) {
            meldingen = new ArrayList<Melding>();
        }
        this.meldingen.addAll(berichtMeldingen);
        resultaat = bepaalResultaatCode();
    }

    /**
     * Retourneert de resultaat code die aangeeft of er een fout is opgetreden of niet.
     *
     * @return de resultaat code van het berichtresultaat.
     */
    public ResultaatCode getResultaat() {
        return resultaat;
    }

    /**
     * Bepaalt (en retourneert) de {@link ResultaatCode} voor een bericht resultaat, gebaseerd op de meldingen reeds in
     * dit resultaat. Indien deze meldingen een of meerdere fouten bevat, zal deze methode {@link ResultaatCode.FOUT}
     * retourneren, maar als er geen fouten zijn, dan zal er {@link ResultaatCode.GOED} worden geretourneerd.
     *
     * @return de resultaatcode op basis van de opgegeven meldingen.
     */
    private ResultaatCode bepaalResultaatCode() {
        ResultaatCode resultaatCode = ResultaatCode.GOED;

        if (meldingen != null) {
            for (Melding melding : meldingen) {
                if (melding.getSoort() == SoortMelding.FOUT_OVERRULEBAAR
                        || melding.getSoort() == SoortMelding.FOUT_ONOVERRULEBAAR)
                {
                    resultaatCode = ResultaatCode.FOUT;
                    break;
                }
            }
        }
        return resultaatCode;
    }

}
