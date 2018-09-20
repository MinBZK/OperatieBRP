/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.business.stappen;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Path.Node;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import nl.bzk.brp.business.stappen.BerichtContext;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObjectKern;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.validatie.Melding;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * De stap in de uitvoering van een bericht waarin wordt gecontroleerd of het binnenkomende bericht valide is.
 * Hierbij wordt gecontroleerd of de benodigde parameters aanwezig zijn, er geen tegenstrijdigheden in zitten
 * en of er geen ongeldige waardes tussen zitten. Eventueel geconstateerde invalide waardes worden, inclusief
 * bericht melding en zwaarte, toegevoegd aan de lijst van fouten binnen het antwoord.
 *
 * @param <T> Een object dat je kunt valideren, m.a.w. geannoteerd met validatie annotaties.
 * @param <X> Een BRP bericht. (bevraging of bijhouding)
 * @param <C> De context gebruikt in de stap om (tussen)resultaten door te geven aan volgende stappen.
 * @param <Y> Een berichtresultaat.
 */
public abstract class AbstractBerichtValidatieStap<T, X extends BerichtBericht, C extends BerichtContext,
    Y extends BerichtVerwerkingsResultaat> extends AbstractBerichtVerwerkingStap<X, C, Y>
{
    private static final Logger LOGGER                           = LoggerFactory.getLogger(AbstractBerichtValidatieStap.class);

    private static final String CONSTRAINT_CODE                  = "code";
    private static final String CONSTRAINT_DATABASEOBJECT        = "dbObject";
    private static final String PROPERTY_PAD_SEPERATOR           = ".";
    private static final String FOUT_BIJ_OPHALEN_OUDER_OBJECT_VAN_OBJECT_PROPERTY_DIE_NIET_VOLDOET_AAN_CONSTRAINT =
        "Fout bij ophalen ouder object van object/property ({}) die niet voldoet aan constraint.";

    /**
     * Voert de validatie uit.
     *
     * @param t Een object dat te valideren is, m.a.w. het object heeft validatie annotaties.
     * @param resultaat Resultaat waar validatie fouten eventueel in opgeslagen moeten worden.
     */
    protected void valideer(final T t, final BerichtVerwerkingsResultaat resultaat) {
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        final Set<ConstraintViolation<T>> overtredingen = validator.validate(t, Default.class);

        for (final ConstraintViolation<T> v : overtredingen) {
            final Regel regel = (Regel) v.getConstraintDescriptor().getAttributes().get(CONSTRAINT_CODE);
            final BerichtEntiteit objectDatDeRegelOvertreedt = bepaalBerichtEntiteitDatDeRegelOvertreedt(v);

            final DatabaseObjectKern databaseObject =
                    (DatabaseObjectKern) v.getConstraintDescriptor().getAttributes().get(CONSTRAINT_DATABASEOBJECT);
            final Melding toegevoegdeMelding = voegMeldingToeAanResultaat(
                    regel, objectDatDeRegelOvertreedt, resultaat, databaseObject);
            toegevoegdeMelding.setAttribuutNaam(getAttribuutNaam(v));
        }
    }

    /**
     * Bepaalt de {@link nl.bzk.brp.model.basis.BerichtEntiteit}, dus het juiste 'objecttype' dat de fout bevat.
     * Hiervoor wordt gekeken naar de bean waar de constraint in faalt en op basis van het type van die bean wordt de
     * juiste onderliggende instantie geretourneerd.
     * @param falendeConstraint de falende constraint.
     * @return de BerichtEntiteit waarin de constraint faalt of <code>null</code> indien deze niet bepaalt kan worden.
     */
    private BerichtEntiteit bepaalBerichtEntiteitDatDeRegelOvertreedt(final ConstraintViolation<T> falendeConstraint) {
        Object falendObject = falendeConstraint.getLeafBean();
        String padNaarObject = falendeConstraint.getPropertyPath().toString();

        // Indien de falende rootbean een actiebericht is, dan pad iets aanpassen naar ondersteunde methodes.
        if (falendeConstraint.getRootBean() instanceof ActieBericht) {
            padNaarObject = padNaarObject.replaceFirst("rootObjecten.0.", "rootObject");
        }

        try {
            while (!(falendObject instanceof BerichtEntiteit) && !padNaarObject.isEmpty()) {
                if (padNaarObject.contains(PROPERTY_PAD_SEPERATOR)) {
                    padNaarObject = padNaarObject.substring(0, padNaarObject.lastIndexOf(PROPERTY_PAD_SEPERATOR));
                    falendObject = PropertyUtils.getProperty(falendeConstraint.getRootBean(), padNaarObject);
                } else {
                    padNaarObject = "";
                    falendObject = falendeConstraint.getRootBean();
                }
            }
        } catch (final IllegalAccessException e) {
            LOGGER.error(FOUT_BIJ_OPHALEN_OUDER_OBJECT_VAN_OBJECT_PROPERTY_DIE_NIET_VOLDOET_AAN_CONSTRAINT, falendeConstraint.getInvalidValue());
            LOGGER.error("Geen toegang tot '{}' vanuit object {}.", padNaarObject, falendeConstraint.getRootBean());
        } catch (final InvocationTargetException e) {
            LOGGER.error(FOUT_BIJ_OPHALEN_OUDER_OBJECT_VAN_OBJECT_PROPERTY_DIE_NIET_VOLDOET_AAN_CONSTRAINT, falendeConstraint.getInvalidValue());
            LOGGER.error("Invocatie fout bij ophalen '{}' uit object {}.", padNaarObject, falendeConstraint.getRootBean());
        } catch (final NoSuchMethodException e) {
            LOGGER.error(FOUT_BIJ_OPHALEN_OUDER_OBJECT_VAN_OBJECT_PROPERTY_DIE_NIET_VOLDOET_AAN_CONSTRAINT, falendeConstraint.getInvalidValue());
            LOGGER.error("Geen getter voor ophalen property '{}' in object {}.", padNaarObject, falendeConstraint.getRootBean());
        }

        if (!(falendObject instanceof BerichtEntiteit)) {
            LOGGER.error("Gevonden object bij constraint violation is geen berichtentiteit. Falend object wordt niet "
                             + "geretourneerd met mogelijke impact op melding tekst.");
            falendObject = null;
        }
        return (BerichtEntiteit) falendObject;
    }

    /**
     * .
     *
     * @param violation .
     * @return .
     */
    private String getAttribuutNaam(final ConstraintViolation<T> violation) {
        final Iterator<Node> it = violation.getPropertyPath().iterator();
        Node lastNode = null;
        while (it.hasNext()) {
            lastNode = it.next();
        }
        if (lastNode != null) {
            // Het blijkt als de validator op een class staat ipv. op een property, de lastnode
            // wijst naar het object, met index is niet null. Maar we hebben niets aan deze informatie
            // dus laten we het weg.

            // Jammer genoeg, veel van deze groepen zien we niet terug in de xml bericht structuur, dus
            // de attribuutnaam 'gegevens' zegt de gebruiker helemaal niets.
            // TODO: We moeten hier nog steeds iets op verzinnen om java propertynamen te mappen naar xml tags.

            return lastNode.getName();
        } else {
            return "";
        }
    }

}
