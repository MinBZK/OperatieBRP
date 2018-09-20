/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.jibx.util;

import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.generatoren.jibx.common.JibxBinding;
import nl.bzk.brp.metaregister.model.Element;

/** Generatie utility class specifiek voor Jibx generatie. */
public final class JibxGeneratieUtil {

    /** De prefix die voor elke output file komt te staan. */
    public static final String BINDING_FILE_PREFIX    = "binding-";
    /** De file extentie van een binding file. */
    public static final String BINDING_FILE_EXTENSION = "xml";

    /** Private constructor. */
    private JibxGeneratieUtil() {
    }

    /**
     * Geef de te gebruiken bestandsnaam voor een binding.
     *
     * @param jibxBinding de jibx binding
     * @return de bestandsnaam
     */
    public static String getBestandsLocatieVoorBinding(final JibxBinding jibxBinding) {
        return jibxBinding.getMap() + "/" + BINDING_FILE_PREFIX + jibxBinding.getNaam() + "." + BINDING_FILE_EXTENSION;
    }

    /**
     * Deze functie bepaalt voor een attribuut type of het een speciale conversie vereist om het attribuut type om te
     * zetten naar xml en andersom. Indien dit het geval is dan moet in jibx een speciale mapping worden gegenereerd
     * met een value dat een serializer en deserializer functie kent.
     *
     * @param xsdType het xsd type.
     * @param javaType het java type.
     * @return true indien voor dit attribuut type speciale conversie functies moeten worden gegenereerd voor Jibx.
     */
    public static boolean bepaalIndienAttribuutTypeSpecialeConversieVereistTussenXmlEnJava(final Element xsdType,
        final JavaType javaType)
    {
        boolean resultaat = false;

        //Het xsd type string en datum vereist speciale conversie naar Java, indien in JAVA het type NIET String is.
        if ((xsdType.getNaam().equals("string")) && !javaType.equals(JavaType.STRING)) {
            resultaat = true;
        }
        return resultaat;
    }
}
