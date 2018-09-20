/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3GroepEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.logging.LogRegel;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.util.gbav.GBACharacterSet;

import org.springframework.stereotype.Component;

/**
 * Syntactische controle, controleert enkel toegestane groepen, numeriek, en maximum lengtes
 *
 * Nota: LET OP! Gebruikt Logging.log
 */
@Component
public class Lo3SyntaxControle {

    private static final Pattern NUMERIEK_PATTERN = Pattern.compile("^[0-9]*$");

    /**
     * Controleert de syntax van alle categorieen van een LO3-PL.
     *
     * @param categorieWaarden
     *            lijst van de categorieen op de PL
     * @return de lijst met gecontroleerde en correcte categorieen
     * @throws OngeldigePersoonslijstException
     *             wordt gegooid als er een fout op de PL zit waardoor de PL niet geconverteerd kan worden
     */
    public List<Lo3CategorieWaarde> controleer(final List<Lo3CategorieWaarde> categorieWaarden) throws OngeldigePersoonslijstException {
        final List<Lo3CategorieWaarde> result = new ArrayList<>(categorieWaarden.size());
        /* methode niet final maken, anders werkt Mockito niet goed en falen er testen in migr-sync-runtime */

        for (final Lo3CategorieWaarde categorieWaarde : categorieWaarden) {
            final Lo3CategorieWaarde gecontroleerd = controleer(categorieWaarde);
            if (gecontroleerd != null) {
                result.add(gecontroleerd);
            }
        }

        final LogSeverity maxSeverity = Logging.getLogging().getSeverity();
        if (maxSeverity != null && maxSeverity.compareTo(LogSeverity.ERROR) >= 0) {
            throw new OngeldigePersoonslijstException("Persoon bevat syntax fouten");
        }

        return result;
    }

    private Lo3CategorieWaarde controleer(final Lo3CategorieWaarde categorieWaarde) {
        final Set<LogRegel> logging = new HashSet<>();

        controleerGroepen(logging, categorieWaarde);
        controleerElementen(logging, categorieWaarde);

        if (Logging.containSeverityLevelError(logging) && isOnjuist(categorieWaarde)) {
            // Rij weggooien
            for (final LogRegel regel : logging) {
                if (regel.hasSeverityLevelError()) {
                    regel.setSeverity(LogSeverity.SUPPRESSED);
                }
            }
            return null;
        }

        return categorieWaarde;

    }

    /**
     * Controleert of de opgegeven categorie het element 84.10 (indicatie onjuist) bevat en of de inhoud gevuld is. Als
     * het logniveau "CRITICAL" (kritiek) is, dan wordt er niet gekeken naar de indicatie onjuist
     *
     * @param categorieWaarde
     *            categorie
     * @return true als de categoriewaarde een waarde bevat voor element 84.10
     *
     */
    private boolean isOnjuist(final Lo3CategorieWaarde categorieWaarde) {
        final String waardeVoorOnjuist = categorieWaarde.getElementen().get(Lo3ElementEnum.ELEMENT_8410);
        return waardeVoorOnjuist != null && !"".equals(waardeVoorOnjuist);
    }

    private void controleerGroepen(final Set<LogRegel> logging, final Lo3CategorieWaarde categorieWaarde) {
        final List<Lo3GroepEnum> groepen = categorieWaarde.getCategorie().getGroepen();
        final Lo3Herkomst herkomst = categorieWaarde.getLo3Herkomst();

        for (final Entry<Lo3ElementEnum, String> element : categorieWaarde.getElementen().entrySet()) {
            final Lo3ElementEnum definitie = element.getKey();
            final Lo3GroepEnum groep = definitie.getGroep();

            if (!groepen.contains(groep)) {
                logFoutregel(logging, herkomst, definitie, LogSeverity.CRITICAL, SoortMeldingCode.ELEMENT);
            }
        }
    }

    private void logFoutregel(
        final Set<LogRegel> logging,
        final Lo3Herkomst herkomst,
        final Lo3ElementEnum definitie,
        final LogSeverity critical,
        final SoortMeldingCode element)
    {
        final LogRegel log = new LogRegel(herkomst, critical, element, definitie);
        logging.add(log);
        Logging.log(log);
    }

    private void controleerElementen(final Set<LogRegel> logging, final Lo3CategorieWaarde categorieWaarde) {
        final Lo3Herkomst herkomst = categorieWaarde.getLo3Herkomst();

        for (final Map.Entry<Lo3ElementEnum, String> element : categorieWaarde.getElementen().entrySet()) {
            controleerElement(logging, herkomst, element);
        }
    }

    private void controleerElement(final Set<LogRegel> logging, final Lo3Herkomst herkomst, final Entry<Lo3ElementEnum, String> element) {
        final String elementValue = element.getValue();
        if (elementValue == null) {
            return;
        }

        final Lo3ElementEnum definitie = element.getKey();

        if (!bevatCorrecteTeletex(elementValue)) {
            logFoutregel(logging, herkomst, definitie, LogSeverity.ERROR, SoortMeldingCode.TELETEX);
        }

        if (definitie.getType() == Lo3ElementEnum.Type.NUMERIEK && !NUMERIEK_PATTERN.matcher(elementValue).matches()) {
            logFoutregel(logging, herkomst, definitie, LogSeverity.ERROR, SoortMeldingCode.NUMERIEK);
        }

        if (definitie.getMinimumLengte() == definitie.getMaximumLengte()) {
            if (elementValue.length() != definitie.getMinimumLengte()) {
                logFoutregel(logging, herkomst, definitie, LogSeverity.ERROR, SoortMeldingCode.LENGTE);
            }

        } else {
            if (elementValue.length() < definitie.getMinimumLengte()) {
                logFoutregel(logging, herkomst, definitie, LogSeverity.INFO, SoortMeldingCode.LENGTE);
            }

            if (elementValue.length() > definitie.getMaximumLengte()) {
                if (definitie.getAfkappen()) {
                    element.setValue(elementValue.substring(0, definitie.getMaximumLengte()));
                    logFoutregel(logging, herkomst, definitie, LogSeverity.WARNING, SoortMeldingCode.LENGTE);
                } else {
                    logFoutregel(logging, herkomst, definitie, LogSeverity.ERROR, SoortMeldingCode.LENGTE);
                }
            }
        }
    }

    private boolean bevatCorrecteTeletex(final String elementValue) {
        return GBACharacterSet.isValideUnicode(elementValue)
               && !(elementValue.contains("\n") || elementValue.contains("\r") || elementValue.contains("\f"));
    }
}
