/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.verconv;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3GroepAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3RubriekExclCategorieEnGroepAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3Severity;
import nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3SeverityAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3SoortMeldingAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.verconv.LO3MeldingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.verconv.LO3VoorkomenHisVolledigImpl;

/**
 * Builder klasse voor LO3 Melding.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class LO3MeldingHisVolledigImplBuilder {

    private LO3MeldingHisVolledigImpl hisVolledigImpl;

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param lO3Voorkomen lO3Voorkomen van LO3 Melding.
     * @param soort soort van LO3 Melding.
     * @param logSeverity logSeverity van LO3 Melding.
     * @param groep groep van LO3 Melding.
     * @param rubriek rubriek van LO3 Melding.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public LO3MeldingHisVolledigImplBuilder(
        final LO3VoorkomenHisVolledigImpl lO3Voorkomen,
        final LO3SoortMelding soort,
        final LO3Severity logSeverity,
        final LO3GroepAttribuut groep,
        final LO3RubriekExclCategorieEnGroepAttribuut rubriek,
        final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        this.hisVolledigImpl =
                new LO3MeldingHisVolledigImpl(lO3Voorkomen, new LO3SoortMeldingAttribuut(soort), new LO3SeverityAttribuut(logSeverity), groep, rubriek);
        if (hisVolledigImpl.getSoort() != null) {
            hisVolledigImpl.getSoort().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getLogSeverity() != null) {
            hisVolledigImpl.getLogSeverity().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getGroep() != null) {
            hisVolledigImpl.getGroep().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getRubriek() != null) {
            hisVolledigImpl.getRubriek().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param lO3Voorkomen lO3Voorkomen van LO3 Melding.
     * @param soort soort van LO3 Melding.
     * @param logSeverity logSeverity van LO3 Melding.
     * @param groep groep van LO3 Melding.
     * @param rubriek rubriek van LO3 Melding.
     */
    public LO3MeldingHisVolledigImplBuilder(
        final LO3VoorkomenHisVolledigImpl lO3Voorkomen,
        final LO3SoortMelding soort,
        final LO3Severity logSeverity,
        final LO3GroepAttribuut groep,
        final LO3RubriekExclCategorieEnGroepAttribuut rubriek)
    {
        this(lO3Voorkomen, soort, logSeverity, groep, rubriek, false);
    }

    /**
     * Bouw het his volledig object.
     *
     * @return het his volledig object
     */
    public LO3MeldingHisVolledigImpl build() {
        return hisVolledigImpl;
    }

}
