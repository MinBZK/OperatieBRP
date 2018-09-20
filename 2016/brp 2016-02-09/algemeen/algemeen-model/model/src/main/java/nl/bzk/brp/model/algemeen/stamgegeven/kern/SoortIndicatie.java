/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;

/**
 * Categorisatie van Indicaties.
 *
 *
 * Voor een Persoon kunnen één of meer Indicaties van toepassing zijn, bijvoorbeeld de indicatie 'Staatloos', of
 * 'Behandeld als Nederlander'. De Soort indicatie is de typering van deze Indicaties voor een Persoon.
 *
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator")
public enum SoortIndicatie implements ElementIdentificeerbaar {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy", null),
    /**
     * Derde heeft gezag?.
     */
    INDICATIE_DERDE_HEEFT_GEZAG("Derde heeft gezag?", true),
    /**
     * Onder curatele?.
     */
    INDICATIE_ONDER_CURATELE("Onder curatele?", true),
    /**
     * Volledige verstrekkingsbeperking?.
     */
    INDICATIE_VOLLEDIGE_VERSTREKKINGSBEPERKING("Volledige verstrekkingsbeperking?", false),
    /**
     * Vastgesteld niet Nederlander?.
     */
    INDICATIE_VASTGESTELD_NIET_NEDERLANDER("Vastgesteld niet Nederlander?", true),
    /**
     * Behandeld als Nederlander?.
     */
    INDICATIE_BEHANDELD_ALS_NEDERLANDER("Behandeld als Nederlander?", true),
    /**
     * Signalering met betrekking tot verstrekken reisdocument?.
     */
    INDICATIE_SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT("Signalering met betrekking tot verstrekken reisdocument?", false),
    /**
     * Staatloos?.
     */
    INDICATIE_STAATLOOS("Staatloos?", true),
    /**
     * Bijzondere verblijfsrechtelijke positie?.
     */
    INDICATIE_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE("Bijzondere verblijfsrechtelijke positie?", false);

    private final String naam;
    private final Boolean indicatieMaterieleHistorieVanToepassing;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor SoortIndicatie
     * @param indicatieMaterieleHistorieVanToepassing IndicatieMaterieleHistorieVanToepassing voor SoortIndicatie
     */
    private SoortIndicatie(final String naam, final Boolean indicatieMaterieleHistorieVanToepassing) {
        this.naam = naam;
        this.indicatieMaterieleHistorieVanToepassing = indicatieMaterieleHistorieVanToepassing;
    }

    /**
     * Retourneert Naam van Soort indicatie.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert Materiele historie van toepassing? van Soort indicatie.
     *
     * @return Materiele historie van toepassing?.
     */
    public Boolean getIndicatieMaterieleHistorieVanToepassing() {
        return indicatieMaterieleHistorieVanToepassing;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.SOORTINDICATIE;
    }

}
