/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.generators.frontend;

import static java.beans.Introspector.decapitalize;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import nl.bzk.brp.ecore.bmr.Applicatie;
import nl.bzk.brp.ecore.bmr.Formulier;
import nl.bzk.brp.ecore.bmr.Frame;
import nl.bzk.brp.ecore.bmr.FrameVeld;
import nl.bzk.brp.ecore.bmr.ObjectType;
import nl.bzk.brp.ecore.bmr.SoortInhoud;
import nl.bzk.brp.ecore.bmr.Type;


/**
 * Deze class helpt om veel voorkomende waarde op te vragen.
 *
 */
public final class FrontEndGeneratorUtil {

    /** package van het domein. */
    public static final String MODEL_PACKAGE = "nl.bzk.brp.domein";

    /**
     * Constructor.
     */
    private FrontEndGeneratorUtil() {

    }

    /**
     * Haalt de bron van het formulier op, hier wordt vanuit gegaan dat de bron van de eerste frame de bron van het
     * formulier is.
     *
     * @param formulier Formulier
     * @return bron van de eerste frame in de formulier
     */
    public static String getFormulierBron(final Formulier formulier) {
        return formulier.getFrames().get(0).getBron().getObjectType().getIdentifierCode();
    }

    /**
     * Haalt de bron op van de frame.
     *
     * @param frame Frame
     * @return bron van de frame
     */
    public static String getFrameBron(final Frame frame) {
        return frame.getBron().getObjectType().getIdentifierCode();
    }

    /**
     * Haalt de naam op van het formulier.
     *
     * @param formulier Formulier
     * @return naam van het formulier
     */
    public static String getFormulierNaam(final Formulier formulier) {
        return formulier.getNaam();
    }

    /**
     * Haalt de naam op van de frame.
     *
     * @param frame Frame
     * @return naam van de frame
     */
    public static String getFrameNaam(final Frame frame) {
        return frame.getNaam();
    }

    /**
     * Geeft de type van het veld.
     *
     * @param veld FrameVeld
     * @return type van het veld
     */
    public static String getAttribuutType(final FrameVeld veld) {
        return veld.getAttribuut().getType().getIdentifierCode();
    }

    /**
     * Geeft de naam van het veld.
     *
     * @param veld FrameVeld
     * @return naam van het veld
     */
    public static String getAttribuutNaam(final FrameVeld veld) {
        return decapitalize(veld.getAttribuut().getIdentifierCode());
    }

    /**
     * Geeft de veld naam van een attribuut.
     *
     * Wanneer er geen veldNummer wordt opgegeven dan wordt de tweede veld teruggegeven, de reden hiervan is dat er
     * vanuit gegaan wordt dat de eerste veld altijd de ID is.
     *
     * @param veld FrameVeld
     * @param veldNummer Integer optioneel
     * @return de naam van de attribuut van het object
     */
    public static String getAttribuutNaamVanObjectVeld(final FrameVeld veld, final Integer veldNummer) {
        if (isDynamischVeld(veld)) {
            int veldnr = 1;

            if (veldNummer != null) {
                veldnr = veldNummer;
            }

            return decapitalize(((ObjectType) veld.getAttribuut().getType()).getAttributen().get(veldnr)
                    .getIdentifierCode());
        } else {
            throw new IllegalArgumentException("Veld heeft geen attributen");
        }
    }

    /**
     * Geeft de volledige package naar de veld type.
     *
     * @param veld FrameVeld
     * @return een string met de volledige pad naar de veld type
     */
    public static String getModelPad(final FrameVeld veld) {
        return MODEL_PACKAGE + "." + ((ObjectType) veld.getAttribuut().getType()).getSchema().getNaam().toLowerCase()
            + "." + getAttribuutType(veld);
    }

    /**
     * Geeft de pad naar de Frame Bron.
     *
     * @param frame Frame
     * @return pad zoals nl.domein.partij
     */
    public static String getModelPadVoorFrameBron(final Frame frame) {
        return MODEL_PACKAGE + "." + frame.getBron().getObjectType().getSchema().getNaam().toLowerCase() + "."
            + getFrameBron(frame);
    }

    /**
     * Geeft de pad naar de Formulier Bron.
     *
     * @param formulier Formulier
     * @return pad zoals nl.domein.partij
     */
    public static String getModelPadVoorFormulierBron(final Formulier formulier) {
        return MODEL_PACKAGE + "."
            + formulier.getFrames().get(0).getBron().getObjectType().getSchema().getNaam().toLowerCase() + "."
            + getFormulierBron(formulier);
    }

    /**
     * Controlleer of een veld een enum veld is.
     *
     * @param veld FrameVeld
     * @return true als het een enum veld is
     */
    public static boolean isEnumVeld(final FrameVeld veld) {
        Type veldType = veld.getAttribuut().getType();
        if (veldType instanceof ObjectType && ((ObjectType) veldType).getSoortInhoud() == SoortInhoud.X) {
            return true;
        }
        return false;
    }

    /**
     * Controlleer of een veld een dynamische stamgegevens veld is.
     *
     * @param veld FrameVeld
     * @return true als het een dynamische stamgegevens veld is
     */
    public static boolean isDynamischVeld(final FrameVeld veld) {
        Type veldType = veld.getAttribuut().getType();
        if (veldType instanceof ObjectType && ((ObjectType) veldType).getSoortInhoud() == SoortInhoud.S) {
            return true;
        }
        return false;
    }

    /**
     * Haal de velden op die bestaan uit Enum waarden voor een applicatie.
     *
     * @param applicatie Applicatie
     * @return een set met unique stamgegevens FrameVeld
     */
    public static List<FrameVeld> getStamgegevensVeldenVoorApplicatie(final Applicatie applicatie) {
        HashMap<String, FrameVeld> stamgegevensVelden = new HashMap<String, FrameVeld>();

        for (Formulier formulier : applicatie.getFormulieren()) {
            for (Frame frame : formulier.getFrames()) {
                stamgegevensVelden.putAll(getStamgegevensVeldenMapVoorFrame(frame));
            }
        }

        List<FrameVeld> resultaat = new ArrayList<FrameVeld>();
        resultaat.addAll(stamgegevensVelden.values());

        return resultaat;
    }

    /**
     * Haal de velden op die bestaan uit Enum waarden voor een frame.
     *
     * @param frame Frame
     * @return een set met unique stamgegevens FrameVeld
     */
    public static List<FrameVeld> getStamgegevensVeldenVoorFrame(final Frame frame) {
        List<FrameVeld> resultaat = new ArrayList<FrameVeld>();
        resultaat.addAll(getStamgegevensVeldenMapVoorFrame(frame).values());

        return resultaat;
    }

    /**
     * Controlleer of de frame de hoofd frame is.
     *
     * @param frame Frame
     * @return true als de formulier bron gelijk is aan de frame bron
     */
    public static boolean isHoofdFrame(final Frame frame) {
        return FrontEndGeneratorUtil.getFormulierBron(frame.getFormulier()).equals(
                FrontEndGeneratorUtil.getFrameBron(frame));
    }

    /**
     * Sorteerd de lijst van classes en maak er een gesorteerde import lijst van.
     *
     * @param imports lijst van classes
     * @return een multi regel string met aan het begin van de regel een import statement
     */
    public static String sorteerEnMaakImportStrings(final List<String> imports) {
        // Zorgt ervoor dat de imports op de juiste volgorde staan
        Collections.sort(imports);

        String importString = "";
        for (String importRegel : imports) {
            importString = importString + "import " + importRegel + ";\n";
        }

        return importString;
    }

    /**
     * Geeft alle velden terug die stamgegevens zijn.
     *
     * @param frame Frame
     * @return een HashMap met stamgegevens
     */
    private static HashMap<String, FrameVeld> getStamgegevensVeldenMapVoorFrame(final Frame frame) {
        HashMap<String, FrameVeld> stamgegevensVelden = new HashMap<String, FrameVeld>();
        for (FrameVeld veld : frame.getVelden()) {
            String sleutel = getAttribuutType(veld);
            if (isEnumVeld(veld) || isDynamischVeld(veld)) {
                stamgegevensVelden.put(sleutel, veld);
            }
        }
        return stamgegevensVelden;
    }

}
