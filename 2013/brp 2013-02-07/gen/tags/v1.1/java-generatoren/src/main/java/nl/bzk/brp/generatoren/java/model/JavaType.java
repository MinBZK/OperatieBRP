/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.bzk.brp.generatoren.java.util.GeneratiePackage;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/** Een java type dat gebruikt wordt als veld type, functie parameter type, etc. */
public final class JavaType implements Cloneable {

    private static final int HASHCODE_GETAL1 = 97;
    private static final int HASHCODE_GETAL2 = 73;

    /*
     * SPECIALE GEVALLEN.
     */
    /** Speciaal geval: void. */
    public static final JavaType VOID = new JavaType("void", "dummy-package");

    /*
     * STANDAARD JAVA TYPES.
     */
    /** java.lang.String. */
    public static final JavaType STRING           = new JavaType("String", "java.lang");
    /** java.lang.Boolean. **/
    public static final JavaType BOOLEAN          = new JavaType("Boolean", "java.lang");
    /** java.lang.Byte. **/
    public static final JavaType BYTE             = new JavaType("Byte", "java.lang");
    /** java.lang.Short. **/
    public static final JavaType SHORT            = new JavaType("Short", "java.lang");
    /** java.lang.Long. **/
    public static final JavaType LONG             = new JavaType("Long", "java.lang");
    /** java.math.BigDecimal. **/
    public static final JavaType BIGDECIMAL       = new JavaType("BigDecimal", "java.math");
    /** java.lang.Integer. */
    public static final JavaType INTEGER          = new JavaType("Integer", "java.lang");
    /** java.lang.Override. */
    public static final JavaType OVERRIDE         = new JavaType("Override", "java.lang");
    /** java.lang.Override. */
    public static final JavaType SUPRESS_WARNINGS = new JavaType("SuppressWarnings", "java.lang");
    /** java.util.Collection. */
    public static final JavaType COLLECTION       = new JavaType("Collection", "java.util");
    /** java.util.List. */
    public static final JavaType LIST             = new JavaType("List", "java.util");
    /** java.util.ArrayList. */
    public static final JavaType ARRAY_LIST       = new JavaType("ArrayList", "java.util");
    /** java.util.Set. */
    public static final JavaType SET              = new JavaType("Set", "java.util");
    /** java.util.HashSet. */
    public static final JavaType HASH_SET         = new JavaType("HashSet", "java.util");
    /** java.util.TreeSet. */
    public static final JavaType TREE_SET         = new JavaType("TreeSet", "java.util");

    /*
     * SPECIFIEKE JAVA TYPES VOOR GENERATIE.
     */
    /** Super klasse van alle gegevens attribuut types. */
    public static final JavaType ABSTRACT_GEGEVENS_ATTRIBUUT_TYPE     =
        new JavaType("AbstractGegevensAttribuutType", GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());
    /** Super klasse van alle statische object types. */
    public static final JavaType ABSTRACT_STATISCH_OBJECT_TYPE        =
        new JavaType("AbstractStatischObjectType", GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());
    /** Super klasse van alle vaste waarde attribuut types. */
    public static final JavaType VASTE_WAARDE_ATRRIBUUT_TYPE          =
        new JavaType("VasteWaardeAttribuutType", GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());
    /** Super klasse van alle groep object types types. */
    public static final JavaType ABSTRACT_OBJECT_TYPE_BERICHT         =
        new JavaType("AbstractObjectTypeBericht", GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());
    /** Super klasse van alle groep bericht types. */
    public static final JavaType ABSTRACT_GROEP_BERICHT               =
        new JavaType("AbstractGroepBericht", GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());
    /** Super klasse van alle dynamische object types. */
    public static final JavaType ABSTRACT_DYNAMISCH_OBJECT_TYPE       =
        new JavaType("AbstractDynamischObjectType", GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());
    /** Super interface van alle object types. */
    public static final JavaType OBJECT_TYPE                          =
        new JavaType("ObjectType", GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());
    /** Super interface van alle groepen. */
    public static final JavaType GROEP                                =
        new JavaType("Groep", GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());
    /** Super klasse van formele historie entiteiten. */
    public static final JavaType ABSTRACT_FORMELE_HISTORIE_ENTITEIT   =
        new JavaType("AbstractFormeleHistorieEntiteit", GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());
    /** Super klasse van formele historie entiteiten. */
    public static final JavaType ABSTRACT_MATERIELE_HISTORIE_ENTITEIT =
        new JavaType("AbstractMaterieleHistorieEntiteit", GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());
    /** Versie voor his volledig objecten / serialisatie. */
    public static final JavaType ABSTRACT_VERSIE =
            new JavaType("AbstractVersie", GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.getPackage());

    /*
     * SPECIFIEKE JAVA TYPES VOOR JPA / HIBERNATE.
     */
    /** JPA annotatie. */
    public static final JavaType EMBEDDABLE           = new JavaType("Embeddable", "javax.persistence");
    /** JPA annotatie. */
    public static final JavaType EMBEDDED             = new JavaType("Embedded", "javax.persistence");
    /** JPA annotatie. */
    public static final JavaType ATTRIBUTE_OVERRIDE   = new JavaType("AttributeOverride", "javax.persistence");
    /** JPA annotatie. */
    public static final JavaType COLUMN               = new JavaType("Column", "javax.persistence");
    /** JPA annotatie. */
    public static final JavaType TABLE                = new JavaType("Table", "javax.persistence");
    /** JPA annotatie. */
    public static final JavaType ENTITY               = new JavaType("Entity", "javax.persistence");
    /** JPA annotatie. */
    public static final JavaType ACCESS               = new JavaType("Access", "javax.persistence");
    /** JPA annotatie. */
    public static final JavaType ACCESS_TYPE          = new JavaType("AccessType", "javax.persistence");
    /** JPA annotatie. */
    public static final JavaType ENTITY_LISTENERS     = new JavaType("EntityListeners", "javax.persistence");
    /** JPA annotatie. */
    public static final JavaType MANY_TO_ONE          = new JavaType("ManyToOne", "javax.persistence");
    /** JPA annotatie. */
    public static final JavaType ONE_TO_MANY          = new JavaType("OneToMany", "javax.persistence");
    /** JPA annotatie. */
    public static final JavaType JOIN_COLUMN          = new JavaType("JoinColumn", "javax.persistence");
    /** JPA annotatie. */
    public static final JavaType ID                   = new JavaType("Id", "javax.persistence");
    /** JPA annotatie. */
    public static final JavaType TRANSIENT            = new JavaType("Transient", "javax.persistence");
    /** JPA annotatie. */
    public static final JavaType ENUMERATED           = new JavaType("Enumerated", "javax.persistence");
    /** JPA annotatie. */
    public static final JavaType SEQUENCE_GENERATOR   = new JavaType("SequenceGenerator", "javax.persistence");
    /** JPA annotatie. */
    public static final JavaType GENERATED_VALUE      = new JavaType("GeneratedValue", "javax.persistence");
    /** JPA annotatie. */
    public static final JavaType GENERATION_TYPE      = new JavaType("GenerationType", "javax.persistence");
    /** JPA annotatie. */
    public static final JavaType MAPPED_SUPER_CLASS   = new JavaType("MappedSuperclass", "javax.persistence");
    /** JPA annotatie. */
    public static final JavaType DISCRIMINATOR_VALUE  = new JavaType("DiscriminatorValue", "javax.persistence");
    /** JPA annotatie. */
    public static final JavaType INHERITANCE          = new JavaType("Inheritance", "javax.persistence");
    /** JPA annotatie. */
    public static final JavaType DISCRIMINATOR_COLUMN = new JavaType("DiscriminatorColumn", "javax.persistence");
    /** JPA annotatie. */
    public static final JavaType FETCH_TYPE           = new JavaType("FetchType", "javax.persistence");
    /** JPA annotatie. */
    public static final JavaType CASCADE_TYPE         = new JavaType("CascadeType", "javax.persistence");
    /** JPA annotatie. */
    public static final JavaType INHERITANCE_TYPE     = new JavaType("InheritanceType", "javax.persistence");
    /** JPA annotatie. */
    public static final JavaType DISCRIMINATOR_TYPE   = new JavaType("DiscriminatorType", "javax.persistence");

    /** Hibernate annotatie. */
    public static final JavaType FETCH      = new JavaType("Fetch", "org.hibernate.annotations");
    /** Hibernate annotatie. */
    public static final JavaType FETCH_MODE = new JavaType("FetchMode", "org.hibernate.annotations");
    /** Hibernate annotatie. */
    public static final JavaType TYPE       = new JavaType("Type", "org.hibernate.annotations");
    /** Hibernate annotatie. */
    public static final JavaType SORT       = new JavaType("Sort", "org.hibernate.annotations");
    /** Hibernate annotatie. */
    public static final JavaType SORT_TYPE  = new JavaType("SortType", "org.hibernate.annotations");

    /*
     * SPECIFIEKE JAVA TYPES VOOR JSON.
     */
    /** JsonProperty annotatie. */
    public static final JavaType JSON_PROPERTY = new JavaType("JsonProperty", "com.fasterxml.jackson.annotation");
    /** JsonCreator annotatie. */
    public static final JavaType JSON_CREATOR  = new JavaType("JsonCreator", "com.fasterxml.jackson.annotation");


    /** Lijst van numerieke JavaTypes; java types die java.lang.Number extenden. */
    private static final List<JavaType> NUMERIEKE_JAVATYPES = Arrays.asList(SHORT, INTEGER, LONG, BYTE, BIGDECIMAL);

    private String   naam;
    private String   packagePad;
    // Zou eigenlijk list moeten zijn, maar is (tot nu toe) niet nodig.
    private JavaType genericParameter;
    private boolean  genericParameterSubtypesToegestaan;

    /**
     * Maak een nieuwe instantie van een JavaType.
     *
     * @param naam de klasse naam
     * @param packagePad het package pad
     */
    public JavaType(final String naam, final String packagePad) {
        this(naam, packagePad, null);
    }

    /**
     * Maak een nieuwe instantie van een JavaType.
     *
     * @param javaType een java type
     * @param genericParameter het generic parameter type
     */
    public JavaType(final JavaType javaType, final JavaType genericParameter) {
        this(javaType, genericParameter, false);
    }

    /**
     * Maak een nieuwe instantie van een JavaType.
     *
     * @param javaType een java type
     * @param genericParameter het generic parameter type
     * @param genericParameterSubtypesToegestaan of er ook subtypes van de generic parameter zijn toegestaan of niet.
     * Dus of de 'upper bounded' wildcard gebruikt dient te worden voor de genericParameter (
     * <code><? extends genericParameter></code> of <code><genericParameter></code>).
     */
    public JavaType(final JavaType javaType, final JavaType genericParameter,
        final boolean genericParameterSubtypesToegestaan)
    {
        this(javaType.getNaam(), javaType.getPackagePad(), genericParameter, genericParameterSubtypesToegestaan);
    }

    /**
     * Maak een nieuwe instantie van een JavaType.
     *
     * @param naam de klasse naam
     * @param packagePad het package pad
     * @param genericParameter het generic parameter type
     */
    public JavaType(final String naam, final String packagePad, final JavaType genericParameter) {
        this(naam, packagePad, genericParameter, false);
    }

    /**
     * Maak een nieuwe instantie van een JavaType.
     *
     * @param naam de klasse naam
     * @param packagePad het package pad
     * @param genericParameter het generic parameter type
     * @param genericParameterSubtypesToegestaan of er ook subtypes van de generic parameter zijn toegestaan of niet.
     * Dus of de 'upper bounded' wildcard gebruikt dient te worden voor de genericParameter (
     * <code><? extends genericParameter></code> of <code><genericParameter></code>).
     */
    public JavaType(final String naam, final String packagePad, final JavaType genericParameter,
        final boolean genericParameterSubtypesToegestaan)
    {
        this.naam = naam;
        this.packagePad = packagePad;
        this.genericParameter = genericParameter;
        this.genericParameterSubtypesToegestaan = genericParameterSubtypesToegestaan;
    }

    public String getNaam() {
        return naam;
    }

    public String getPackagePad() {
        return packagePad;
    }

    /**
     * Retourneert de 'Fully Qualified Class Name' van het JavaType. Dit is dus de naam, voorafgegaand aan het pad
     * naar de Java klasse.
     *
     * @return de 'Fully Qualified Class Name' van het JavaType.
     */
    public String getFullyQualifiedClassName() {
        return this.packagePad + "." + this.naam;
    }

    /**
     * Geeft aan of dit JavaType een generified parameter heeft (bijvoorbeeld bij collecties).
     *
     * @return boolean die aangeeft of dit JavaType een generified parameter heeft.
     */
    public boolean isGeparametriseerd() {
        return this.genericParameter != null;
    }

    public JavaType getGenericParameter() {
        return genericParameter;
    }

    /**
     * Geeft aan of er ook subtypes voor de generic parameters zijn toegestaan of niet. Dus of de 'upper bounded'
     * wildcard gebruikt dient te worden voor de genericParameter. Het geeft dus aan of het
     * <code><? extends genericParameter></code> moet zijn of <code><genericParameter></code>.
     *
     * @return of er ook subtypes voor de generic parameters zijn toegestaan of niet.
     */
    public boolean isGenericParameterSubtypesToegestaan() {
        return genericParameterSubtypesToegestaan;
    }

    /**
     * Maak een nieuwe instantie van een JavaType vanuit een fully qualified klass name.
     *
     * @param fullyQualifiedClassName de volledige klasse naam, inclusief pad
     * @return het java type
     */
    public static JavaType bouwVoorFullyQualifiedClassName(final String fullyQualifiedClassName) {
        int lastDot = fullyQualifiedClassName.lastIndexOf('.');
        String naam = fullyQualifiedClassName.substring(lastDot + 1);
        String packagePad = fullyQualifiedClassName.substring(0, lastDot);
        return new JavaType(naam, packagePad);
    }

    @Override
    public String toString() {
        String toString = "JavaType: " + this.packagePad + "." + this.naam;
        if (this.genericParameter != null) {
            toString += "<";
            if (genericParameterSubtypesToegestaan) {
                toString += "? extends ";
            }
            toString += this.genericParameter + ">";
        }
        return toString;
    }

    /**
     * Haal de gebruikte types voor de JavaType op.
     * Dat wil zeggen: zichzelf en alle eventueel geneste generic parameter types.
     *
     * @return de gebruikte types.
     */
    public List<JavaType> getGebruikteTypes() {
        List<JavaType> gebruikteTypes = new ArrayList<JavaType>();
        if (this.genericParameter != null) {
            gebruikteTypes.addAll(this.genericParameter.getGebruikteTypes());
        }
        gebruikteTypes.add(this);
        return gebruikteTypes;
    }

    /**
     * Geeft aan of dit javatype een numeriek java type is of niet.
     * @return indicatie of dit javatype een numeriek java type is of niet.
     */
    public boolean isNumeriek() {
        return NUMERIEKE_JAVATYPES.contains(this);
    }

    /** {@inheritDoc} */
    @Override
    public Object clone() throws CloneNotSupportedException {
        final JavaType clone = (JavaType) super.clone();
        clone.naam = this.naam;
        clone.packagePad = this.packagePad;
        if (this.genericParameter != null) {
            clone.genericParameter = (JavaType) this.genericParameter.clone();
        }
        clone.genericParameterSubtypesToegestaan = this.genericParameterSubtypesToegestaan;
        return clone;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(HASHCODE_GETAL1, HASHCODE_GETAL2)
            .append(genericParameter).append(naam).append(packagePad)
            .append(genericParameterSubtypesToegestaan).toHashCode();
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        JavaType other = (JavaType) obj;
        return new EqualsBuilder()
            .append(this.genericParameter, other.genericParameter)
            .append(this.naam, other.naam)
            .append(this.packagePad, other.packagePad)
            .append(this.genericParameterSubtypesToegestaan, other.genericParameterSubtypesToegestaan)
            .isEquals();
    }

}
