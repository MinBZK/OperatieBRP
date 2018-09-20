/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.internbericht;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReferentienummerAttribuut;
import nl.bzk.brp.model.basis.BrpObject;


/**
 * Antwoordbericht voor de interne service voor het plaatsen van afnemerindicaties. Wordt omgezet naar JSON.
 */
public final class AfnemerindicatieOnderhoudAntwoord implements BrpObject {

    @JsonProperty
    private Boolean succesvol = false;

    @JsonProperty
    private List<RegelMelding> meldingen;

    @JsonProperty
    private ReferentienummerAttribuut referentienummer;

    public Boolean getSuccesvol() {
        return succesvol;
    }

    public void setSuccesvol(final Boolean succesvol) {
        this.succesvol = succesvol;
    }

    public List<RegelMelding> getMeldingen() {
        return meldingen;
    }

    public void setMeldingen(final List<RegelMelding> meldingen) {
        this.meldingen = meldingen;
    }

    public ReferentienummerAttribuut getReferentienummer() {
        return referentienummer;
    }

    public void setReferentienummer(final ReferentienummerAttribuut referentienummer) {
        this.referentienummer = referentienummer;
    }

}
