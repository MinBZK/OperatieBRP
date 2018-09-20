/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import java.util.HashSet;
import java.util.Set;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Afnemersindicatie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AfnemersindicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import org.springframework.stereotype.Component;

/**
 * Preconditie controles voor categorie 14: Gezagsverhouding.
 *
 * Maakt gebruik van de {@link nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging#log Logging.log} methode.
 */
@Component
public final class Lo3AfnemersindicatiePrecondities extends AbstractLo3Precondities {

    /**
     * Controleer precondities op container niveua.
     *
     * @param afnemersindicatie
     *            afnemersindicatie
     */
    public void controleerAfnemersindicatie(final Lo3Afnemersindicatie afnemersindicatie) {
        if (afnemersindicatie.getANummer() == null) {
            // Error op elke stapel
            for (final Lo3Stapel<Lo3AfnemersindicatieInhoud> stapel : afnemersindicatie.getAfnemersindicatieStapels()) {
                Foutmelding.logMeldingFout(
                    new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_14, stapel.get(0).getLo3Herkomst().getStapel(), -1),
                    LogSeverity.ERROR,
                    SoortMeldingCode.AFN001,
                    null);
            }
        }
        for (final Lo3Stapel<Lo3AfnemersindicatieInhoud> stapel : afnemersindicatie.getAfnemersindicatieStapels()) {
            controleerStapel(stapel);
        }
    }

    /**
     * Controleer precondities op stapel niveau.
     *
     * @param stapel
     *            stapel
     */
    public void controleerStapel(final Lo3Stapel<Lo3AfnemersindicatieInhoud> stapel) {
        if (stapel == null || stapel.isEmpty()) {
            return;
        }

        controleerAfnemersindicatieGelijk(stapel);

        for (final Lo3Categorie<Lo3AfnemersindicatieInhoud> categorie : stapel) {
            controleerCategorie(categorie);
        }
    }

    private void controleerAfnemersindicatieGelijk(final Lo3Stapel<Lo3AfnemersindicatieInhoud> stapel) {
        final Set<Integer> afnemersindicaties = new HashSet<>();
        for (final Lo3Categorie<Lo3AfnemersindicatieInhoud> categorie : stapel) {
            final Integer afnemersindicatie = categorie.getInhoud().getAfnemersindicatie();
            if (afnemersindicatie != null) {
                afnemersindicaties.add(afnemersindicatie);
            }
        }

        if (afnemersindicaties.isEmpty()) {
            final Lo3Herkomst herkomt = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_14, stapel.get(0).getLo3Herkomst().getStapel(), -1);
            Foutmelding.maakMeldingFout(herkomt, LogSeverity.ERROR, SoortMeldingCode.AFN002, null).log();
        }

        if (afnemersindicaties.size() > 1) {
            final Lo3Herkomst herkomt = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_14, stapel.get(0).getLo3Herkomst().getStapel(), -1);
            Foutmelding.maakMeldingFout(herkomt, LogSeverity.ERROR, SoortMeldingCode.AFN005, null).log();
        }
    }

    /**
     * Controleer precondities op categorie niveau.
     *
     * @param categorie
     *            categorie
     */
    private void controleerCategorie(final Lo3Categorie<Lo3AfnemersindicatieInhoud> categorie) {
        final Lo3Historie historie = categorie.getHistorie();
        final Lo3Herkomst herkomst = categorie.getLo3Herkomst();

        final Lo3Datum ingangsdatumGeldigheid = historie.getIngangsdatumGeldigheid();
        controleerDatum(
            ingangsdatumGeldigheid,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.STRUC_DATUM, Lo3ElementEnum.ELEMENT_8510));
        controleerDatumNietOnbekend(ingangsdatumGeldigheid, Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.AFN003, null));

    }

}
