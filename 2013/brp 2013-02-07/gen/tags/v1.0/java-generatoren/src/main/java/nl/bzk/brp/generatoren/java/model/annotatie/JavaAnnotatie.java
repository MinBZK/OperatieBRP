/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.model.annotatie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.bzk.brp.generatoren.java.model.JavaType;

/** Deze klasse is een representatie van een annotatie in java. Bijvoorbeeld @Column, @NotNull etc. */
public class JavaAnnotatie implements Cloneable {

    private static final int PRIEM_GETAL_VOOR_HASHCODE = 31;

    private JavaType type;
    private List<AnnotatieParameter> parameters;

    /**
     * Constructor.
     *
     * @param type Type van de Annotatie.
     */
    public JavaAnnotatie(final JavaType type) {
        this(type, new AnnotatieParameter[0]);
    }

    /**
     * Constructor waarbij een annotatie wordt aangemaakt met 1 of meerdere parameters.
     *
     * @param type Type van de annotatie.
     * @param parameters De parameters voor de annotatie.
     */
    public JavaAnnotatie(final JavaType type, final AnnotatieParameter... parameters) {
        this.type = type;
        this.parameters = new ArrayList<AnnotatieParameter>(Arrays.asList(parameters));
    }

    /**
     * Voegt een parameter toe.
     * @param parameter de toe te voegen parameter.
     */
    public void voegParameterToe(final AnnotatieParameter parameter) {
        parameters.add(parameter);
    }

    public final JavaType getType() {
        return type;
    }

    public final List<AnnotatieParameter> getParameters() {
        return parameters;
    }

    /**
     * Geef de gebruikte types van deze annotatie terug.
     *
     * @return de gebruikte types.
     */
    public List<JavaType> getGebruikteTypes() {
        List<JavaType> gebruikteTypes = new ArrayList<JavaType>();
        gebruikteTypes.addAll(this.getType().getGebruikteTypes());
        for (AnnotatieParameter parameter : this.parameters) {
            gebruikteTypes.addAll(parameter.getGebruikteTypes());
        }
        return gebruikteTypes;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Object clone() throws CloneNotSupportedException {
        final JavaAnnotatie clone = (JavaAnnotatie) super.clone();
        clone.type = type;
        clone.parameters = (ArrayList<AnnotatieParameter>) ((ArrayList<AnnotatieParameter>) this.parameters).clone();
        return clone;
    }

    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JavaAnnotatie)) {
            return false;
        }

        final JavaAnnotatie that = (JavaAnnotatie) o;
        return parameters.equals(that.parameters) && type.equals(that.type);
    }

    @Override
    public final int hashCode() {
        int result = type.hashCode();
        result = PRIEM_GETAL_VOOR_HASHCODE * result + parameters.hashCode();
        return result;
    }
}
