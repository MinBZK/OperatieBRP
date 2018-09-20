/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.preconditie;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.moderniseringgba.migratie.conversie.model.logging.LogRegel;
import nl.moderniseringgba.migratie.conversie.model.logging.LogSeverity;
import nl.moderniseringgba.migratie.conversie.model.logging.LogType;
import nl.moderniseringgba.migratie.conversie.proces.logging.Logging;

import org.springframework.stereotype.Component;

/**
 * Syntactische controle, controleert enkel toegestane groepen, numeriek, en maximum lengtes
 * 
 * Nota: LET OP! Gebruikt Logging.log
 */
@Component
public class Lo3SyntaxControle {

    private static final Pattern NUMERIEK_PATTERN = Pattern.compile("^[0-9]*$");

    private static final String STR_ELEMENT = "Element ";
    private static final String STR_LENGTE = "LENGTE";
    private static final String STR_HEBBEN = " hebben.";

    /**
     * Controleert de syntax van alle categorieen van een LO3-PL.
     * 
     * @param categorieWaarden
     *            lijst van de categorieen op de PL
     * @return de lijst met gecontroleerde en correcte categorieen
     * @throws OngeldigePersoonslijstException
     *             wordt gegooid als er een fout op de PL zit waardoor de PL niet geconverteerd kan worden
     */
    // CHECKSTYLE:OFF methode niet final maken, anders werkt Mockito niet goed en falen er testen in migr-sync-runtime
    public List<Lo3CategorieWaarde> controleer(final List<Lo3CategorieWaarde> categorieWaarden)
    // CHECKSTYLE:ON
            throws OngeldigePersoonslijstException {
        final List<Lo3CategorieWaarde> result = new ArrayList<Lo3CategorieWaarde>(categorieWaarden.size());

        for (final Lo3CategorieWaarde categorieWaarde : categorieWaarden) {
            final Lo3CategorieWaarde gecontroleerd = controleer(categorieWaarde);
            if (gecontroleerd != null) {
                result.add(gecontroleerd);
            }
        }

        final LogSeverity maxSeverity = Logging.getLogging().getSeverity();
        if (maxSeverity != null && maxSeverity.compareTo(LogSeverity.ERROR) >= 0) {
            throw new OngeldigePersoonslijstException();
        }

        return result;
    }

    private Lo3CategorieWaarde controleer(final Lo3CategorieWaarde categorieWaarde) {
        final List<LogRegel> logging = new ArrayList<LogRegel>();

        controleerGroepen(logging, categorieWaarde);
        controleerElementen(logging, categorieWaarde);

        if (Logging.containSeverityLevelError(logging)) {
            if (isOnjuist(categorieWaarde)) {
                // Rij weggooien
                for (final LogRegel regel : logging) {
                    if (regel.hasSeverityLevelError()) {
                        regel.setSeverity(LogSeverity.REMOVED);
                    }
                }
                Logging.log(categorieWaarde.getLo3Herkomst(), LogSeverity.WARNING, LogType.VERWERKING, "DELETE",
                        "Het onjuiste categorie voorkomen met fouten is verwijderd.");
                return null;
            }
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

    private void controleerGroepen(final List<LogRegel> logging, final Lo3CategorieWaarde categorieWaarde) {

        final List<String> groepen = categorieWaarde.getCategorie().getGroepen();
        final Lo3Herkomst herkomst = categorieWaarde.getLo3Herkomst();

        final Iterator<Map.Entry<Lo3ElementEnum, String>> elementIterator =
                categorieWaarde.getElementen().entrySet().iterator();

        while (elementIterator.hasNext()) {
            final Map.Entry<Lo3ElementEnum, String> element = elementIterator.next();
            final Lo3ElementEnum definitie = element.getKey();
            final String groep = definitie.getElementNummer().substring(0, 2);

            if (!groepen.contains(groep)) {
                final LogRegel log =
                        new LogRegel(herkomst, LogSeverity.CRITICAL, LogType.SYNTAX, "ELEMENT", "Categorie "
                                + categorieWaarde.getCategorie().getCategorie() + " mag element "
                                + definitie.getElementNummer() + " niet bevatten.");
                logging.add(log);
                Logging.log(log);
            }
        }
    }

    private void controleerElementen(final List<LogRegel> logging, final Lo3CategorieWaarde categorieWaarde) {
        final Lo3Herkomst herkomst = categorieWaarde.getLo3Herkomst();

        for (final Map.Entry<Lo3ElementEnum, String> element : categorieWaarde.getElementen().entrySet()) {
            controleerElement(logging, herkomst, element);
        }
    }

    private void controleerElement(
            final List<LogRegel> logging,
            final Lo3Herkomst herkomst,
            final Entry<Lo3ElementEnum, String> element) {
        if (element.getValue() == null) {
            return;
        }

        final Lo3ElementEnum definitie = element.getKey();

        if (definitie.getType() == Lo3ElementEnum.Type.NUMERIEK) {
            if (!NUMERIEK_PATTERN.matcher(element.getValue()).matches()) {
                final LogRegel log =
                        new LogRegel(herkomst, LogSeverity.ERROR, LogType.SYNTAX, "NUMERIEK", STR_ELEMENT
                                + definitie.getElementNummer() + " moet een numerieke waarde bevatten.");
                logging.add(log);
                Logging.log(log);
            }
        }

        if (definitie.getMinimumLengte() == definitie.getMaximumLengte()) {
            if (element.getValue().length() != definitie.getMinimumLengte()) {
                final LogRegel log =
                        new LogRegel(herkomst, LogSeverity.ERROR, LogType.SYNTAX, STR_LENGTE, STR_ELEMENT
                                + definitie.getElementNummer() + " moet exact een lengte van "
                                + definitie.getMinimumLengte() + STR_HEBBEN);
                logging.add(log);
                Logging.log(log);
            }

        } else {

            if (element.getValue().length() < definitie.getMinimumLengte()) {
                final LogRegel log =
                        new LogRegel(herkomst, LogSeverity.INFO, LogType.SYNTAX, STR_LENGTE, STR_ELEMENT
                                + definitie.getElementNummer() + " moet een minimale lengte van "
                                + definitie.getMinimumLengte() + STR_HEBBEN);
                logging.add(log);
                Logging.log(log);
            }

            if (element.getValue().length() > definitie.getMaximumLengte()) {
                final LogRegel log;
                if (definitie.getAfkappen()) {
                    element.setValue(element.getValue().substring(0, definitie.getMaximumLengte()));
                    log =
                            new LogRegel(herkomst, LogSeverity.WARNING, LogType.SYNTAX, STR_LENGTE, STR_ELEMENT
                                    + definitie.getElementNummer() + " mag een maximale lengte " + "van "
                                    + definitie.getMaximumLengte() + " hebben. De waarde is afgekapt.");
                } else {
                    log =
                            new LogRegel(herkomst, LogSeverity.ERROR, LogType.SYNTAX, STR_LENGTE, STR_ELEMENT
                                    + definitie.getElementNummer() + " mag een maximale lengte" + " van "
                                    + definitie.getMaximumLengte() + STR_HEBBEN);
                }
                logging.add(log);
                Logging.log(log);
            }
        }
    }
}
