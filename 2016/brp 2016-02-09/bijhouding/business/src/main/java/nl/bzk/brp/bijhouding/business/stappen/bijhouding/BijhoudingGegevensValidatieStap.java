/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.bijhouding;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.Resultaat;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.ResultaatMelding;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObjectKern;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.model.logisch.kern.Actie;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Component;


/**
 * De stap in de uitvoering van een bericht waarin wordt gecontroleerd of het binnenkomende bericht valide is.
 * Hierbij wordt gecontroleerd of de benodigde parameters aanwezig zijn, er geen tegenstrijdigheden in zitten
 * en of er geen ongeldige waardes tussen zitten. Eventueel geconstateerde invalide waardes worden, inclusief
 * bericht melding en zwaarte, toegevoegd aan de lijst van fouten binnen het antwoord.
 */
@Component
public class BijhoudingGegevensValidatieStap {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final String CONSTRAINT_CODE                                                                   = "code";
    private static final String CONSTRAINT_DATABASEOBJECT                                                         = "dbObject";
    private static final String PROPERTY_PAD_SEPERATOR                                                            = ".";
    private static final String FOUT_BIJ_OPHALEN_OUDER_OBJECT_VAN_OBJECT_PROPERTY_DIE_NIET_VOLDOET_AAN_CONSTRAINT =
        "Fout bij ophalen ouder object van object/property ({}) die niet voldoet aan constraint.";

    @Inject
    private MeldingFactory meldingFactory;

    /**
     * Voert de stap uit.
     * @param bericht het bijhoudingsbericht
     * @return meldinglijst de lijst van meldingen
     */
    public Resultaat voerStapUit(final BijhoudingsBericht bericht) {
        final Set<ResultaatMelding> resultaatMeldingen = new HashSet<>();

        for (Actie actie : bericht.getAdministratieveHandeling().getActies()) {
            if (actie.getRootObject() == null) {
                resultaatMeldingen.add(ResultaatMelding.builder()
                    .withRegel(Regel.ALG0002)
                    .withMeldingTekst(String.format("%s: %s", Regel.ALG0002.getOmschrijving(), "betrokkenen"))
                    .build());
            } else {
                resultaatMeldingen.addAll(valideer(actie));
            }
        }

        return Resultaat.builder().withMeldingen(resultaatMeldingen).build();
    }

    /**
     * Voert de validatie uit.
     *
     * @param actie De te valideren actie (het object heeft validatie-annotaties).
     * @return de lijst van meldingen
     */
    protected List<ResultaatMelding> valideer(final Actie actie) {
        final List<ResultaatMelding> resultaatMeldingen = new ArrayList<>();

        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        final Set<ConstraintViolation<Actie>> violations = validator.validate(actie, Default.class);

        for (final ConstraintViolation<Actie> violation : violations) {
            final Regel regel = (Regel) violation.getConstraintDescriptor().getAttributes().get(CONSTRAINT_CODE);
            final BerichtEntiteit objectDatDeRegelOvertreedt = bepaalBerichtEntiteitDieDeRegelOvertreedt(violation);

            final DatabaseObjectKern databaseObject =
                (DatabaseObjectKern) violation.getConstraintDescriptor().getAttributes().get(CONSTRAINT_DATABASEOBJECT);
            final ResultaatMelding toegevoegdeMelding = meldingFactory.maakResultaatMelding(
                regel, objectDatDeRegelOvertreedt, databaseObject, getAttribuutNaam(violation));
            resultaatMeldingen.add(toegevoegdeMelding);
        }

        return resultaatMeldingen;
    }

    /**
     * Bepaalt de {@link nl.bzk.brp.model.basis.BerichtEntiteit}, dus het juiste 'objecttype' dat de fout bevat.
     * Hiervoor wordt gekeken naar de bean waar de constraint in faalt en op basis van het type van die bean wordt de
     * juiste onderliggende instantie geretourneerd.
     * @param falendeConstraint de falende constraint.
     * @return de BerichtEntiteit waarin de constraint faalt of <code>null</code> indien deze niet bepaalt kan worden.
     */
    private BerichtEntiteit bepaalBerichtEntiteitDieDeRegelOvertreedt(final ConstraintViolation<Actie> falendeConstraint) {
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
     * Geeft de naam van het attribuut terug.
     *
     * @param violation de overtreding.
     * @return .
     */
    private String getAttribuutNaam(final ConstraintViolation<Actie> violation) {
        final Iterator<Path.Node> it = violation.getPropertyPath().iterator();
        Path.Node lastNode = null;
        while (it.hasNext()) {
            lastNode = it.next();
        }
        if (lastNode != null) {
            // Het blijkt als de validator op een class staat ipv. op een property, de lastnode
            // wijst naar het object, met index is niet null. Maar we hebben niets aan deze informatie
            // dus laten we het weg.

            return lastNode.getName();
        } else {
            return "";
        }
    }

}
