/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.generators;

import java.util.Collection;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;

import nl.bzk.brp.bmr.generator.Generator;
import nl.bzk.brp.bmr.generator.model.dal.AbstractType;
import nl.bzk.brp.bmr.generator.model.dal.BasisInterface;
import nl.bzk.brp.bmr.generator.model.dal.BasisPersistentClass;
import nl.bzk.brp.bmr.generator.model.dal.BeanProperty;
import nl.bzk.brp.bmr.generator.model.dal.ConstraintDefinitie;
import nl.bzk.brp.bmr.generator.model.dal.Enumeratie;
import nl.bzk.brp.bmr.generator.model.dal.FactoryInterface;
import nl.bzk.brp.bmr.generator.model.dal.Interface;
import nl.bzk.brp.bmr.generator.model.dal.JavaPackage;
import nl.bzk.brp.bmr.generator.model.dal.JavaPackageModelBuilder;
import nl.bzk.brp.bmr.generator.model.dal.PersistentClass;
import nl.bzk.brp.bmr.generator.model.dal.PersistentObjectFactory;
import nl.bzk.brp.bmr.generator.utils.ArtifactBuilder;
import nl.bzk.brp.bmr.generator.utils.FileSystemAccess;
import nl.bzk.brp.ecore.bmr.AttribuutType;
import nl.bzk.brp.ecore.bmr.Domein;
import nl.bzk.brp.ecore.bmr.MetaRegister;
import nl.bzk.brp.ecore.bmr.SoortTekst;
import nl.bzk.brp.ecore.bmr.Tekst;
import nl.bzk.brp.ecore.bmr.Tuple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


/**
 * {@link Generator} implementatie die een Java domein model genereert.
 */
@Component
public class DomeinModelGenerator implements Generator {

    private static final Logger LOGGER        = LoggerFactory.getLogger(DomeinModelGenerator.class);
    private ArtifactBuilder     r;
    private String              packagePrefix = "nl.bzk.brp.domein";

    @Override
    public void generate(final MetaRegister register, final String naam, final FileSystemAccess file) {
        Domein domein = register.getDomein(naam);
        JavaPackage root = new JavaPackageModelBuilder(domein).buildJavaPackage(getPackagePrefix());
        genereerPackage(file, root);
    }

    /**
     * Formatteer teksten als java commentaarregels.
     *
     * @param teksten element waarvan de teksten worden geformatteer.
     */
    private void alsCommentaarRegels(final Iterable<Tekst> teksten) {
        for (Tekst tekst : teksten) {
            if (tekst.getSoort() == SoortTekst.DEF) {
                for (String regel : StringUtils.delimitedListToStringArray(tekst.getTekst(), "\r\n")) {
                    if (StringUtils.hasText(regel)) {
                        r.regel(" * ", regel.trim());
                    }
                }
            }
        }
    }

    private void genereerAccessors(final BeanProperty property) {
        r.regel();
        r.regel("@Override");
        r.regel("public ", property.getTypeNaam(), " ", property.getGetter(), "() {");
        r.incr();
        if (property.isCollectie()) {
            r.regel("return Collections.unmodifiableList(this.", property.getNaam(), ");");
        } else {
            r.regel("return this.", property.getNaam(), ";");
        }
        r.decr();
        r.regel("}");
        if (!property.isStatusHistorieIndicator()) {
            r.regel();
            if (property.isCollectie()) {
                r.regel("@Override");
                r.regel("public void ", property.getAdder(), "(final ", property.getType().getNaam(), " element) {");
                r.incr();
                r.regel(property.getNaam(), ".add(element);");
                r.regel("element.", property.getOpposite().getSetter(), "((", property.getOpposite().getTypeNaam(),
                        ") this);");
                r.decr();
                r.regel("}");
                r.regel();
                r.regel("@Override");
                r.regel("public void ", property.getRemover(), "(final ", property.getType().getNaam(), " element) {");
                r.incr();
                r.regel(property.getNaam(), ".remove(element);");
                r.regel("element.", property.getOpposite().getSetter(), "(null);");
                r.decr();
                r.regel("}");
            } else {
                r.regel("@Override");
                r.regel("public void ", property.getSetter(), "(", property.getTypeNaam(), " ", property.getNaam(),
                        ") {");
                r.incr().regel("this.", property.getNaam(), " = ", property.getNaam(), ";").decr();
                r.regel("}");
            }
        }
    }

    private void genereerBasisInterface(final BasisInterface type) {
        LOGGER.debug("Genereren basis interface '{}'.", type.getNaam());
        r.regel("package ", type.getPackageNaam(), ";");
        r.regel();
        for (String imp : type.getImports()) {
            r.regel("import ", imp, ";");
        }
        r.regel();
        r.regel("/**");
        alsCommentaarRegels(type.getObjectType().getTeksten());
        r.regel(" */");
        r.regel("public interface ", type.getNaam(), (type.getSuperType() != null ? " extends "
            + type.getSuperType().getNaam() : ""), " {");
        r.incr();
        for (BeanProperty property : type.getProperties()) {
            r.regel();
            r.regel(property.getTypeNaam(), " ", property.getGetter(), "();");
            if (!property.isStatusHistorieIndicator()) {
                r.regel();
                if (property.isCollectie()) {
                    r.regel("void ", property.getAdder(), "(final ", property.getType().getNaam(), " element);");
                    r.regel();
                    r.regel("void ", property.getRemover(), "(final ", property.getType().getNaam(), " element);");
                } else {
                    r.regel("void ", property.getSetter(), "(", property.getTypeNaam(), " ", property.getNaam(), ");");
                }
            }
        }
        r.decr();
        r.regel("}");
    }

    private void genereerBasisPersistentClass(final BasisPersistentClass type) {
        LOGGER.debug("Genereren basis persistent class '{}'.", type.getNaam());
        r.regel("package ", type.getPackageNaam(), ";");
        r.regel();
        r.regel("import javax.persistence.MappedSuperclass;");
        if (type.getSuperType() == null) {
            r.regel("import javax.persistence.Table;");
        }
        for (String imp : type.getImports()) {
            r.regel("import ", imp, ";");
        }
        r.regel();
        r.regel("import org.hibernate.annotations.Where;");
        r.regel();
        r.regel("@MappedSuperclass");
        if (type.getSuperType() == null) {
            r.regel("@Access(AccessType.FIELD)");
            r.regel("@Table(name = \"", type.getDatabaseNaam(), "\", schema = \"", type.getSchemaNaam(), "\")");
        }
        r.regel("public abstract class ", type.getNaam(), (type.getSuperType() != null ? " extends "
            + type.getSuperType().getNaam() : ""), " implements ", type.getInterface().getNaam(), " {");
        r.incr();
        /*
         * Genereer fields.
         */
        for (BeanProperty property : type.getProperties()) {
            genereerField(type, property);
        }
        /*
         * Genereer default no-args constructor, vereist voor JPA.
         */
        r.regel();
        r.regel("/**");
        r.regel(" * No-args constructor, vereist voor JPA.");
        r.regel(" */");
        r.regel("public ", type.getNaam(), "() {");
        r.incr().regel("super();").decr();
        r.regel("}");
        /*
         * Genereer accessors.
         */
        for (BeanProperty property : type.getProperties()) {
            genereerAccessors(property);
        }
        r.decr();
        r.regel("}");
    }

    private void genereerConstraintDefinitie(final ConstraintDefinitie type) {
        LOGGER.debug("Genereren package '{}'.", type.getQualifiedNaam());
        r = new ArtifactBuilder();
        r.regel("package ", type.getPackageNaam(), ";");
        r.regel();
        r.regel("import static java.lang.annotation.ElementType.ANNOTATION_TYPE;");
        r.regel("import static java.lang.annotation.ElementType.CONSTRUCTOR;");
        r.regel("import static java.lang.annotation.ElementType.FIELD;");
        r.regel("import static java.lang.annotation.ElementType.METHOD;");
        r.regel("import static java.lang.annotation.ElementType.PARAMETER;");
        r.regel("import static java.lang.annotation.RetentionPolicy.RUNTIME;");
        r.regel();
        r.regel("import java.lang.annotation.Documented;");
        r.regel("import java.lang.annotation.Retention;");
        r.regel("import java.lang.annotation.Target;");
        r.regel();
        r.regel("import javax.validation.Constraint;");
        r.regel("import javax.validation.Payload;");
        if (type.getSizeAnnotatie() != null) {
            r.regel("import javax.validation.constraints.Size;");
        }
        r.regel();
        r.regel();
        r.regel("/**");
        AttribuutType attribuutType = type.getAttribuutType();
        r.regel(" * AbstractConstraint annotatie voor velden van het attribuut type ",
                attribuutType.getIdentifierCode(), ", basis type ", type.getStandardJavaType().getNaam());
        r.regel(" */");
        r.regel("@Documented");
        r.regel("@Constraint(validatedBy = {})");
        r.regel("@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })");
        r.regel("@Retention(RUNTIME)");
        if (type.getSizeAnnotatie() != null) {
            r.regel(type.getSizeAnnotatie());
        }
        r.regel("public @interface ", type.getNaam(), " {");
        r.regel();
        r.incr();
        r.regel("String message() default \"{", type.getQualifiedNaam(), ".message}\";");
        r.regel("Class<?>[] groups() default {};");
        r.regel("Class<? extends Payload>[] payload() default {};");
        r.decr();
        r.regel("}");
    }

    private void genereerEnumeratie(final Enumeratie type) {
        LOGGER.debug("Genereren enumeratie '{}'.", type.getNaam());
        r.regel("package ", type.getPackageNaam(), ";");
        r.regel();
        r.regel("/**");
        alsCommentaarRegels(type.getObjectType().getTeksten());
        r.regel(" */");
        r.regel("public enum ", type.getNaam(), " {");
        r.incr();
        r.regel();
        r.regel("/**");
        r.regel(" * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de"
            + " database bij 1.");
        r.regel(" */");
        StringBuilder value = new StringBuilder();
        for (int i = 0; i < type.getProperties().size(); i++) {
            value.append("null");
            if (i < type.getProperties().size() - 1) {
                value.append(", ");
            }
        }
        r.regel("DUMMY(", value, "),");
        List<Tuple> tuples = type.getObjectType().getTuples();
        for (Tuple tuple : tuples) {
            String separator = ",";
            if (tuple.equals(tuples.get(tuples.size() - 1))) {
                separator = ";";
            }
            r.regel("/**");
            if (tuple.getOmschrijving() != null) {
                r.regel(" * ", tuple.getOmschrijving());
            } else {
                r.regel(" * ", tuple.getNaam(), ".");
            }
            r.regel(" */");
            value = new StringBuilder();
            if (tuple.getCode() != null) {
                value.append("\"").append(tuple.getCode()).append("\", ");
            }
            value.append("\"").append(tuple.getNaam()).append("\"");
            r.regel(type.enumValue(tuple.getNaam()), "(", value, ")", separator);
        }
        r.regel();
        if (type.getObjectType().hasCode()) {
            r.regel("private String code;");
        }
        r.regel("private String naam;");
        r.regel();
        r.regel("/**");
        r.regel(" * Private constructor voor eenmalige instantiatie in deze file.");
        r.regel(" *");
        if (type.getObjectType().hasCode()) {
            r.regel(" * @param code De code van de waarde.");
        }
        r.regel(" * @param naam De naam van de waarde.");
        r.regel(" */");
        StringBuilder parameters = new StringBuilder();
        if (type.getObjectType().hasCode()) {
            parameters.append("final String code, ");
        }
        parameters.append("final String naam");
        r.regel("private ", type.getNaam(), "(", parameters, ") {");
        r.incr();
        if (type.getObjectType().hasCode()) {
            r.regel("this.code = code;");
        }
        r.regel("this.naam = naam;");
        r.decr();
        r.regel("}");
        r.regel();
        if (type.getObjectType().hasCode()) {
            r.regel("public String getCode() {");
            r.incr().regel("return this.code;").decr();
            r.regel("}");
            r.regel();
        }
        r.regel("public String getNaam() {");
        r.incr().regel("return this.naam;").decr();
        r.regel("}");
        r.regel();
        r.decr();
        r.regel("}");
    }

    private void genereerField(final BasisPersistentClass type, final BeanProperty property) {
        r.regel();
        if (property.isIdentifier()) {
            r.regel("@Id");
            r.regel("@SequenceGenerator(name = \"", type.getSequenceGeneratorName(), "\", sequenceName = \"",
                    type.getSequenceNaam(), "\")");
            r.regel("@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = \"",
                    type.getSequenceGeneratorName(), "\")");
        } else {
            PersistentClass implementatie = null;
            if (property.isManyToOne()) {
                implementatie = ((Interface) property.getType()).getImplementation();
                r.regel("@ManyToOne(targetEntity = ", implementatie.getNaam(), ".class)");
                r.regel("@JoinColumn(name = \"", property.getDatabaseNaam(), "\")");
            } else if (property.isCollectie()) {
                implementatie = ((Interface) property.getType()).getImplementation();
                r.regel("@OneToMany(targetEntity = ", implementatie.getNaam(), ".class, mappedBy = \"", property
                        .getOpposite().getNaam(), "\")");
                if (!implementatie.getHistorieStatussen().isEmpty()) {
                    r.regel("@Where(clause = \"", genereerWhereClause(implementatie.getHistorieStatussen()), "\")");
                }
            } else {
                if (!property.getNaam().equalsIgnoreCase(property.getDatabaseNaam())) {
                    r.regel("@Column(name = \"", property.getDatabaseNaam(), "\")");
                }
            }
        }
        if (property.isVerplicht() && !property.isIdentifier()) {
            r.regel("@NotNull");
        }
        if (property.getConstraintDefinitie() != null) {
            r.regel("@", property.getConstraintDefinitie().getNaam());
        }
        r.regel("private ", property.getTypeNaam(), " ", property.getNaam(), fieldInitieleWaarde(property), ";");
    }

    private CharSequence genereerWhereClause(final Collection<BeanProperty> historieStatussen) {
        return Joiner.on(" AND ").join(Collections2.transform(historieStatussen, new Function<BeanProperty, String>() {

            @Override
            public String apply(final BeanProperty property) {
                StringBuilder resultaat = new StringBuilder();
                return resultaat.append(property.getDatabaseNaam()).append(" = ").append("'A'").toString();
            }
        }));
    }

    /**
     * @param property
     * @return
     */
    private CharSequence fieldInitieleWaarde(final BeanProperty property) {
        StringBuilder resultaat = new StringBuilder();
        if (property.isStatusHistorieIndicator() || property.isCollectie()) {
            resultaat.append(" = ");
        }
        if (property.isStatusHistorieIndicator()) {
            resultaat.append("\"A\"");
        } else if (property.isCollectie()) {
            resultaat.append("new Array");
            resultaat.append(property.getTypeNaam());
            resultaat.append("()");
        }
        return resultaat;
    }

    private void genereerInterface(final Interface type) {
        LOGGER.debug("Genereren interface extension point '{}'.", type.getNaam());
        r.regel("package ", type.getPackageNaam(), ";");
        r.regel();
        r.regel("import ", type.getSuperType().getQualifiedNaam(), ";");
        r.regel();
        r.regel("public interface ", type.getNaam(), " extends ", type.getSuperType().getNaam(), " {");
        r.regel("}");
    }

    private CharSequence genereerJavaType(final AbstractType type) {
        r = new ArtifactBuilder();

        /*
         * De volgorde hieronder is belangrijk, omdat sommige typen subtypen van andere typen zijn. Het meest concrete
         * type moet daarom eerst komen.
         */
        if (type instanceof Interface) {
            genereerInterface((Interface) type);
        } else if (type instanceof BasisInterface) {
            genereerBasisInterface((BasisInterface) type);
        } else if (type instanceof PersistentClass) {
            genereerPersistentClass((PersistentClass) type);
        } else if (type instanceof BasisPersistentClass) {
            genereerBasisPersistentClass((BasisPersistentClass) type);
        } else if (type instanceof Enumeratie) {
            genereerEnumeratie((Enumeratie) type);
        } else if (type instanceof ConstraintDefinitie) {
            genereerConstraintDefinitie((ConstraintDefinitie) type);
        } else if (type instanceof FactoryInterface) {
            genereerFactoryInterface((FactoryInterface) type);
        } else if (type instanceof PersistentObjectFactory) {
            genereerPersistentObjectFactory((PersistentObjectFactory) type);
        } else {
            LOGGER.error("{} is geen geldig AbstractJavaType.", type.getClass().getName());
        }
        return r;
    }

    private void genereerPersistentObjectFactory(final PersistentObjectFactory factory) {
        LOGGER.debug("Genereren PersistentObjectFactory '{}'.", factory.getQualifiedNaam());
        r.regel("package ", factory.getPackageNaam(), ";");
        r.regel();
        r.regel();
        r.regel("import java.util.Map;");
        r.regel("import java.util.HashMap;");
        r.regel();
        for (String imp : factory.getImports()) {
            r.regel("import ", imp, ";");
        }
        r.regel();
        r.regel("/**");
        r.regel(" * Persistent object implementatie van de {@link ", factory.getFactoryInterface().getNaam(),
                "} interface.");
        r.regel(" */");
        r.regel("public class ", factory.getNaam(), " implements ", factory.getFactoryInterface().getNaam(), " {");
        r.regel();
        r.incr();
        r.regel("private Map<Class<?>, Class<?>> implementaties = new HashMap<Class<?>, Class<?>>();");
        r.regel("{");
        r.incr();
        for (AbstractType type : factory.getFactoryInterface().getPackage().getAllExtensionPoints()) {
            if (type instanceof Interface) {
                Interface interface_ = (Interface) type;
                r.regel("implementaties.put(", interface_.getNaam(), ".class, ", interface_.getImplementation()
                        .getNaam(), ".class);");
            }
        }
        r.decr();
        r.regel("}");
        r.regel();
        r.regel("/**");
        r.regel(" * {@inheritDoc}.");
        r.regel(" */");
        r.regel("@Override");
        r.regel("public Class<?> getImplementatie(final Class<?> clazz) {");
        r.incr();
        r.regel("if (!clazz.isInterface()) {");
        r.incr().regel("throw new IllegalArgumentException(clazz.getName() + \" is geen interface\");").decr();
        r.regel("}");
        r.regel("Class<?> implementatie = implementaties.get(clazz);");
        r.regel("if (implementatie == null) {");
        r.incr();
        r.regel("throw new IllegalArgumentException(clazz.getName()");
        r.regel("      + \" is geen domein model type behorende tot het schema 'kern'\");");
        r.decr();
        r.regel("}");
        r.regel("return implementatie;");
        r.decr();
        r.regel("}");
        r.regel();
        r.regel("/**");
        r.regel(" * {@inheritDoc}.");
        r.regel(" */");
        r.regel("@SuppressWarnings(\"unchecked\")");
        r.regel("@Override");
        r.regel("public <T> T create(final Class<T> clazz) {");
        r.incr();
        r.regel("try {");
        r.incr().regel("return (T) getImplementatie(clazz).newInstance();").decr();
        r.regel("} catch (InstantiationException e) {");
        r.incr().regel("throw new IllegalArgumentException(e);").decr();
        r.regel("} catch (IllegalAccessException e) {");
        r.incr().regel("throw new IllegalArgumentException(e);").decr();
        r.regel("}");
        r.decr();
        r.regel("}");
        for (AbstractType type : factory.getFactoryInterface().getPackage().getAllExtensionPoints()) {
            if (type instanceof Interface) {
                Interface interface_ = (Interface) type;
                r.regel();
                r.regel("/**");
                r.regel(" * {@inheritDoc}.");
                r.regel(" */");
                r.regel("@Override");
                r.regel("public ", interface_.getNaam(), " create", interface_.getNaam(), "() {");
                r.incr();
                r.regel("return create(", interface_.getNaam(), ".class);");
                r.decr();
                r.regel("}");
            }
        }
        r.decr();
        r.regel("}");
    }

    private void genereerFactoryInterface(final FactoryInterface factory) {
        LOGGER.debug("Genereren FactoryInterface '{}'.", factory.getQualifiedNaam());
        r.regel("package ", factory.getPackageNaam(), ";");
        r.regel();
        r.regel();
        for (String imp : factory.getImports()) {
            r.regel("import ", imp, ";");
        }
        r.regel();
        r.regel("/**");
        r.regel(" * Factory voor domein objecten.");
        r.regel(" */");
        r.regel("public interface ", factory.getNaam(), " {");
        r.incr();
        r.regel("/**");
        r.regel(" * Cre&euml;ert een nieuwe instantie van het opgegeven type.");
        r.regel(" *");
        r.regel(" * @param clazz Het type waarvan een instantie gecre&euml;erd wordt.");
        r.regel(" * @return De instantie van het opgegeven type.");
        r.regel(" */");
        r.regel("<T> T create(Class<T> clazz);");
        r.regel();
        r.regel("/**");
        r.regel(" * Geeft de implementatie van het opgegeven type.");
        r.regel(" *");
        r.regel(" * @param clazz Het type waarvan de implementatie gevraagd wordt. Het moet een interface zijn van domein model type");
        r.regel(" *            van het schema '", factory.getPackage().getNaam(), "'.");
        r.regel(" * @return De implementatieklasse van het opgegeven type.");
        r.regel(" */");
        r.regel("Class<?> getImplementatie(Class<?> clazz);");
        for (AbstractType type : factory.getPackage().getAllExtensionPoints()) {
            if (type instanceof Interface) {
                Interface interface_ = (Interface) type;
                r.regel();
                r.regel("/**");
                r.regel(" * Cre&euml;ert een nieuwe instantie van het type {@link ", interface_.getNaam(), "}.");
                r.regel(" */");
                r.regel(interface_.getNaam(), " create", interface_.getNaam(), "();");
            }
        }
        r.decr();
        r.regel("}");
    }

    private void genereerPackage(final FileSystemAccess file, final JavaPackage pakkage) {
        LOGGER.debug("Genereren package '{}'.", pakkage.getQualifiedNaam());
        if (!pakkage.getTypes().isEmpty()) {
            file.generateFile(pakkage.getPad() + "/package-info.java", genereerPackageInfo(pakkage));
        }
        for (JavaPackage subPackage : pakkage.getSubPackages()) {
            genereerPackage(file, subPackage);
        }
        for (AbstractType type : pakkage.getTypes()) {
            if ((type.isExtensionPoint() && file.exists(type.getPad()))) {
                LOGGER.debug("File '{}' bestaat al in de source directory en wordt niet opnieuw gegenereerd.",
                        type.getPad());
            } else {
                file.generateFile(type.getPad(), genereerJavaType(type));
            }
        }
    }

    /**
     * Genereer de package-info file.
     *
     * @param schema het schema waarvoor het package gegenereerd wordt.
     * @return de gegenereerde file.
     */
    private CharSequence genereerPackageInfo(final JavaPackage schema) {
        r = new ArtifactBuilder();
        LOGGER.debug("Genereren Java package package-info file'{}.{}'.", schema.getQualifiedNaam(), "package-info.java");
        r.regel("package ", schema.getQualifiedNaam(), ";");
        return r;
    }

    private void genereerPersistentClass(final PersistentClass type) {
        LOGGER.debug("Genereren persistent class extension point '{}'.", type.getNaam());
        r.regel("package ", type.getPackageNaam(), ";");
        r.regel();
        r.regel("import javax.persistence.Entity;");
        r.regel("import javax.persistence.Table;");
        r.regel();
        r.regel("import ", type.getSuperType().getQualifiedNaam(), ";");
        r.regel("import ", type.getInterface().getQualifiedNaam(), ";");
        r.regel();
        r.regel("@Entity");
        r.regel("@Table(name = \"", type.getDatabaseNaam(), "\", schema=\"", type.getSchemaNaam(), "\")");
        r.regel("public class ", type.getNaam(), " extends ", type.getSuperType().getNaam(), " implements ", type
                .getInterface().getNaam(), " {");
        r.regel("}");
    }

    private String getPackagePrefix() {
        return packagePrefix;
    }
}
