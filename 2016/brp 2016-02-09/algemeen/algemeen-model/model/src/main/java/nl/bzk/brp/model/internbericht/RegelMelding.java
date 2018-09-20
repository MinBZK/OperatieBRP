/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.internbericht;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.MeldingtekstAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMeldingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RegelAttribuut;

/**
 * Melding als een regel wordt overtreden. Wordt omgezet naar JSON.
 */
public final class RegelMelding {


    @JsonProperty
    private RegelAttribuut regel;

    @JsonProperty
    private SoortMeldingAttribuut soort;

    @JsonProperty
    private MeldingtekstAttribuut melding;

    // Nodig voor json-serialisatie
    private RegelMelding() {
    }

    /**
     * Standaard constructor.
     *
     * @param regel   regel van Melding.
     * @param soort   soort van Melding.
     * @param melding melding van Melding.
     */
    public RegelMelding(final RegelAttribuut regel, final SoortMeldingAttribuut soort,
        final MeldingtekstAttribuut melding)
    {
        this.regel = regel;
        this.soort = soort;
        this.melding = melding;
    }

    /**
     * Geeft de betreffende regel.
     *
     * @return regel of {@code null}
     */
    public RegelAttribuut getRegel() {
        return this.regel;
    }

    /**
     * Geeft de soort melding.
     * @return de soort melding of {@code null}
     */
    public SoortMeldingAttribuut getSoort() {
        return this.soort;
    }

    /**
     * Geeft de melding tekst.
     * @return de tekst of {@code null}
     */
    public MeldingtekstAttribuut getMelding() {
        return this.melding;
    }

}
