/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.generators.frontend;

import java.util.List;

import nl.bzk.brp.bmr.generator.AbstractGenerator;
import nl.bzk.brp.bmr.generator.Generator;
import nl.bzk.brp.bmr.generator.model.dal.Enumeratie;
import nl.bzk.brp.bmr.generator.utils.ArtifactBuilder;
import nl.bzk.brp.bmr.generator.utils.FileSystemAccess;
import nl.bzk.brp.ecore.bmr.Applicatie;
import nl.bzk.brp.ecore.bmr.Formulier;
import nl.bzk.brp.ecore.bmr.Frame;
import nl.bzk.brp.ecore.bmr.FrameVeld;
import nl.bzk.brp.ecore.bmr.ModelElement;
import nl.bzk.brp.ecore.bmr.ObjectType;
import nl.bzk.brp.ecore.bmr.SoortInhoud;
import nl.bzk.brp.ecore.bmr.Tuple;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * {@link Generator} implementatie voor message.properties.
 */
@Component
public class MessagePropertiesGenerator extends AbstractGenerator implements Generator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessagePropertiesGenerator.class);

    private ArtifactBuilder     r;

    private List<FrameVeld>     stamgegevensVelden;

    @Override
    public void generate(final ModelElement element, final FileSystemAccess file) {
        if (!(element instanceof Applicatie)) {
            throw new IllegalArgumentException("Kan alleen code genereren uit een Applicatie element.");
        }
        Applicatie applicatie = (Applicatie) element;
        LOGGER.info("Text vertalingen genereren voor '{}' applicatie", applicatie.getNaam());

        stamgegevensVelden = FrontEndGeneratorUtil.getStamgegevensVeldenVoorApplicatie(applicatie);

        genereerMessageProperties(file, applicatie);
    }

    /**
     * Genereer vertalingen.
     *
     * @param file FileSystemAccess
     * @param applicatie de applicatie waarvoor het gegenereerd moet worden
     */
    private void genereerMessageProperties(final FileSystemAccess file, final Applicatie applicatie) {
        String messsagePropertiesFileNaam = "messages/generatedmessages.properties";

        LOGGER.debug("Genereren text vertalingen '{}'.", messsagePropertiesFileNaam);

        r = new ArtifactBuilder();

        for (Formulier formulier : applicatie.getFormulieren()) {
            // Menu vertaling
            r.regel("#Velden voor formulier ", formulier.getNaam());
            r.regel(getMessagePropertieMenu(formulier), "=", StringEscapeUtils.escapeJava(formulier.getNaam()));

            for (Frame frame : formulier.getFrames()) {
                r.regel("#Velden voor frame ", frame.getBron().getObjectType().getNaam());
                // Tab vertaling
                r.regel(getMessagePropertieTab(frame), "=",
                        StringEscapeUtils.escapeJava(frame.getBron().getObjectType().getNaam()));

                // Entity vertaling
                r.regel(getMessagePropertieEntity(frame), "=",
                        StringEscapeUtils.escapeJava(frame.getBron().getObjectType().getNaam()));

                // Velden vertaling
                for (FrameVeld veld : frame.getVelden()) {
                    r.regel(getMessagePropertieVeld(veld), "=", StringEscapeUtils.escapeJava(veld.getNaam()));
                }
                r.regel();
            }
        }

        // Stamgegevens vertaling
        r.regel("#Velden voor statische stamgegevens");
        for (FrameVeld veld : stamgegevensVelden) {
            ObjectType objectType = (ObjectType) veld.getAttribuut().getType();
            if ((objectType.getSoortInhoud() == SoortInhoud.X) && (!objectType.getTuples().isEmpty())) {
                Enumeratie enumeratie = new Enumeratie(objectType);
                r.regel(getEnumPrefix(veld), "DUMMY=Geen");
                for (Tuple tuple : objectType.getTuples()) {
                    r.regel(getEnumPrefix(veld), enumeratie.enumValue(tuple.getNaam()), "=",
                            StringEscapeUtils.escapeJava(tuple.getNaam()));
                }
                r.regel();
            }
        }

        file.generateFile(messsagePropertiesFileNaam, r);
    }

    /**
     * Haalt de prefix op voor een enum item.
     *
     * @param veld FrameVeld
     * @return de propertie prefix
     */
    public static String getEnumPrefix(final FrameVeld veld) {
        return "enum." + FrontEndGeneratorUtil.getAttribuutType(veld) + ".";
    }

    /**
     * Bouwt de propertie op voor een menu item.
     *
     * @param formulier Formulier
     * @return menu message propertie sleutel
     */
    public static String getMessagePropertieMenu(final Formulier formulier) {
        return "menu." + FrontEndGeneratorUtil.getFormulierNaam(formulier);
    }

    /**
     * Bouwt de propertie op voor een tab.
     *
     * @param frame Frame
     * @return tab message propertie sleutel
     */
    public static String getMessagePropertieTab(final Frame frame) {
        return "tab." + FrontEndGeneratorUtil.getFormulierNaam(frame.getFormulier()) + "."
            + FrontEndGeneratorUtil.getFrameNaam(frame).toLowerCase();
    }

    /**
     * Bouwt de propertie op voor een entity.
     *
     * @param frame Frame
     * @return entity message propertie sleutel
     */
    public static String getMessagePropertieEntity(final Frame frame) {
        return "entity." + FrontEndGeneratorUtil.getFrameBron(frame);
    }

    /**
     * Bouwt de propertie op voor een veld.
     *
     * @param veld FrameVeld
     * @return veld message propertie sleutel
     */
    public static String getMessagePropertieVeld(final FrameVeld veld) {
        return "veld." + FrontEndGeneratorUtil.getFormulierNaam(veld.getFrame().getFormulier()) + "."
            + FrontEndGeneratorUtil.getFrameNaam(veld.getFrame()) + "." + veld.getAttribuut().getIdentifierCode();
    }

}
