/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.internbericht;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReferentienummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.EffectAfnemerindicaties;
import nl.bzk.brp.model.basis.BrpObject;


/**
 * Bericht waarmee de interne service voor het plaatsen van afnemerindicaties kan worden aangeroepen. Wordt omgezet naar JSON.
 */
public final class AfnemerindicatieOnderhoudOpdracht implements BrpObject {

    @JsonProperty
    private EffectAfnemerindicaties effectAfnemerindicatie;

    @JsonProperty
    private Integer persoonId;

    @JsonProperty
    private NaamEnumeratiewaardeAttribuut abonnementNaam;

    @JsonProperty
    private PartijCodeAttribuut partijCode;

    @JsonProperty
    private ReferentienummerAttribuut referentienummer;

    @JsonProperty
    private Integer dienstId;

    public EffectAfnemerindicaties getEffectAfnemerindicatie() {
        return effectAfnemerindicatie;
    }

    public void setEffectAfnemerindicatie(final EffectAfnemerindicaties effectAfnemerindicatie) {
        this.effectAfnemerindicatie = effectAfnemerindicatie;
    }

    public Integer getPersoonId() {
        return persoonId;
    }

    public void setPersoonId(final Integer persoonId) {
        this.persoonId = persoonId;
    }

    public NaamEnumeratiewaardeAttribuut getAbonnementNaam() {
        return abonnementNaam;
    }

    @Deprecated
    public void setAbonnementNaam(final NaamEnumeratiewaardeAttribuut abonnementNaam) {
        this.abonnementNaam = abonnementNaam;
    }

    public PartijCodeAttribuut getPartijCode() {
        return partijCode;
    }

    public void setPartijCode(final PartijCodeAttribuut partijCode) {
        this.partijCode = partijCode;
    }

    public ReferentienummerAttribuut getReferentienummer() {
        return referentienummer;
    }

    public void setReferentienummer(final ReferentienummerAttribuut referentienummer) {
        this.referentienummer = referentienummer;
    }

    public Integer getDienstId() {
        return dienstId;
    }

    public void setDienstId(final Integer dienstId) {
        this.dienstId = dienstId;
    }
}
