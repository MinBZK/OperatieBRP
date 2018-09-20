/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generator.java;

import java.io.Writer;
import java.util.List;
import java.math.BigInteger;

import nl.bzk.brp.generator.GenerationPackageNames;
import nl.bzk.brp.generator.GenerationReport;
import nl.bzk.brp.generator.JavaGeneratorUtils;
import nl.bzk.brp.generator.java.domein.Constructor;
import nl.bzk.brp.generator.java.domein.Field;
import nl.bzk.brp.generator.java.domein.Identifier;
import nl.bzk.brp.generator.java.domein.Method;
import nl.bzk.brp.generator.java.domein.ObjectClass;
import nl.bzk.brp.generator.java.domein.ObjectInterface;
import nl.bzk.brp.metaregister.dataaccess.AttribuutDao;
import nl.bzk.brp.metaregister.dataaccess.ObjectTypeDao;
import nl.bzk.brp.metaregister.dataaccess.TekstDao;
import nl.bzk.brp.metaregister.dataaccess.AttribuutTypeDao;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.HistorieVastleggen;
import nl.bzk.brp.metaregister.model.ObjectType;
import nl.bzk.brp.metaregister.model.AttribuutType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional
public class JavaObjectTypeStatischGenerator extends AbstractJavaGenerator<ObjectType> {

    private static final Logger logger              = LoggerFactory.getLogger(JavaObjectTypeStatischGenerator.class);

    private static final String TEMPLATE_GROUP_NAME = "objecttypes";

    @Autowired(required = true)
    private ObjectTypeDao       objectTypeDao;

    @Autowired(required = true)
    private AttribuutDao        attribuutDao;

    @Autowired(required = true)
    private AttribuutTypeDao attribuutTypeDao;

    @Autowired(required = true)
    private TekstDao            tekstDao;

    public JavaObjectTypeStatischGenerator() {
        setTemplateGroupName(TEMPLATE_GROUP_NAME);
        setBasePackage(GenerationPackageNames.OBJECTTYPE_OPERATIONEEL_STATISCH_PACKAGE);
    }

    @Override
    public GenerationReport genereer() {
        GenerationReport report = new GenerationReport();

        List<ObjectType> objectTypes = objectTypeDao.getStatischeObjectTypes();
        for (ObjectType objectType : objectTypes) {
            Writer writer = creeerWriter(objectType.getIdentCode());
            genereerElement(writer, report, objectType);
        }

        return report;
    }

    @Override
    protected String getPackageInfoString() {
        return ".";
    }

    @Override
    public void genereerElement(final Writer writer, final GenerationReport report, final ObjectType objectType) {
        logger.info("Genegereer object type " + objectType.getNaam() + ", type " + objectType.getSoortInhoud());

        try {
            String sourceCode = genereerObjectType(objectType);
            int result = write(sourceCode, writer);
            logger.info("ObjectType:" + objectType.getNaam());
            if (result > 0) {
                report.addSucccess(objectType.getIdentCode(), sourceCode);
            } else {
                report.addFailure(objectType.getIdentCode(), "Fout bij het wegschrijven, resultaat=" + result);
            }
        } catch (IllegalStateException ex) {
            logger.error("Fout bij het genereren van objecttype " + objectType.getNaam(), verbose ? ex : null);
            report.addFailure(objectType.getNaam(), ex.getMessage());
        }
    }

    private String genereerObjectType(final ObjectType objectType) {
        ObjectClass object = new ObjectClass(objectType.getIdentCode(), tekstDao.getFullJavaDocForObject(objectType));
        object.setExtendsFrom(objectType.getSoortInhoud());
        object.setPackageName(GenerationPackageNames.OBJECTTYPE_OPERATIONEEL_STATISCH_PACKAGE.getPackage());
        object.addImport(GenerationPackageNames.BRP_MODEL_BASIS_PACKAGE.createImportStatement(new Identifier(object.getExtendsFrom())));

        object.addImport("javax.persistence.Access");
        object.addImport("javax.persistence.AccessType");
        object.addImport("javax.persistence.Column");
        object.addImport("javax.persistence.Entity");
        object.addImport("javax.persistence.Id");
        object.addImport("javax.persistence.Table");

        object.addAnnotation("@Entity");
        object.addAnnotation("@Table(schema = \"" + objectType.getOuder().getNaam() + "\", name = \"" + objectType.getIdentDb() + "\")");
        object.addAnnotation("@Access(AccessType.FIELD)");
        List<Attribuut> attributen = attribuutDao.getAttributen(objectType);
        Constructor constructor = new Constructor(object.getName());
        constructor.setVisibility("private");
        constructor.setJavaDoc("Private constructor t.b.v. Hibernate.");
        boolean attribuutVanSoortAttribuutTypeAanwezig = false;
        for (Attribuut attribuut : attributen) {
            String returnType = GeneratorUtils.toUpperCamel(attribuut.getType().getIdentCode());

            Field field = new Field(GeneratorUtils.toUpperCamel(attribuut.getType().getIdentCode()), attribuut.getIdentCode());
            field.setJavaDoc(tekstDao.getJavaDocForObject(attribuut));
            if (attribuut.getIdentCode().endsWith("ID")) {
                field.addAnnotation("@Id");
                field.addAnnotation("@Column (name = \"%s\")", attribuut.getIdentDb().toLowerCase());
                //TODO: Hier wordt gekeken naar het basistype van het ID attribuut, moet in de toekomst omgezet worden
                //TODO: zodat dit via de target tabel verloopt.
                final AttribuutType attribuutType = attribuutTypeDao.cast(attribuut.getType());
                final String naamBasisType = attribuutType.getBasisType().getNaam().toLowerCase();
                String javaType = DataTypeTranslator.getJavaType(naamBasisType);
                field.setType(javaType);
                if (javaType.equals(BigInteger.class.getSimpleName())) {
                    object.addImport("java.math.BigInteger");
                }
                returnType = field.getType();
            } else {
                if ("AT".equals(attribuut.getType().getSoortElement().getCode())) {
                    attribuutVanSoortAttribuutTypeAanwezig = true;
                    object.addImport(JavaGeneratorUtils.getImportPackageForObjectType(attribuut.getType())
                        .createImportStatement(new Identifier(attribuut.getType().getIdentCode())));
                    field.addAnnotation("@Embedded");
                    field.addAnnotation("@AttributeOverride(name = \"waarde\", column = @Column(name = \""
                            + attribuut.getIdentDb().toLowerCase() + "\"))");
                } else if ("OT".equals(attribuut.getType().getSoortElement().getCode()) &&
                           'X' == attribuut.getType().getSoortInhoud()) {
                    //Dit is een enum attribuut
                    field.addAnnotation("@Column(name = \""+attribuut.getIdentDb().toLowerCase()+"\")");
                } else if ("OT".equals(attribuut.getType().getSoortElement().getCode())) {
                    //Voeg annotaties toe voor associatie.
                    object.addImport("javax.persistence.ManyToOne");
                    object.addImport("javax.persistence.JoinColumn");
                    if ('D' == attribuut.getType().getSoortInhoud()) {
                       object.addImport(JavaGeneratorUtils.getImportPackageForObjectType(attribuut.getType())
                               .createImportStatement(new Identifier(attribuut.getType().getIdentCode())));
                        final String targetEntityClass = attribuut.getType().getIdentCode()+"Model";
                        field.addAnnotation("@ManyToOne(targetEntity = "+targetEntityClass+".class)");
                        object.addImport(GenerationPackageNames.createImportStatement(
                                GenerationPackageNames.OBJECTTYPE_OPERATIONEEL_PACKAGE, targetEntityClass));
                    } else {
                        field.addAnnotation("@ManyToOne");
                    }
                    field.addAnnotation("@JoinColumn(name = \""+attribuut.getIdentDb().toLowerCase()+"\")");
                } else {
                    throw new UnsupportedOperationException("Generator ondersteunt geen attributen van een ander type dan 'OT' of 'AT'");
                }
                if (attribuut.getHistorieVastleggen() != null) {
                    if (attribuut.getHistorieVastleggen() != HistorieVastleggen.GEEN.getCode()) {
                        Method setter = new Method("set" + field.getName().getUpperCamel());
                        setter.addParameter(new Identifier(attribuut.getIdentCode()));
                        object.addModifier(setter);
                    } else {
                        logger.info("History voor" + attribuut.getIdentCode() + " is: " + attribuut.getHistorieVastleggen().charValue());
                    }
                } else {
                    logger.info("Geen history voor" + attribuut.getIdentCode());
                }
            }
            logger.info("History voor object: " + objectType.getHistorieVastleggen());
            Method accessor = new Method(returnType, "get" + field.getName().getUpperCamel());
            accessor.setJavaDoc(tekstDao.getJavaDocForObject(attribuut));
            object.addAccessor(accessor);
            accessor.setBody("return " + field.getName().getLowerCamel());

            object.addAttribuut(field);
        }
        //Voorkom unused imports:
        if (attribuutVanSoortAttribuutTypeAanwezig) {
            object.addImport("javax.persistence.AttributeOverride");
            object.addImport("javax.persistence.Embedded");
        }

        object.addConstructor(constructor);
        return genereerObjectTypeStatisch(object);
    }

    public String genereerObjectTypeStatisch(final ObjectInterface object) {
        if (templateGroup == null || !templateGroup.isDefined("objecttypeStatisch")) {
            throw new IllegalStateException("Template groep niet geladen of objecttype template niet gedefinieerd");
        }
        contentTemplate = templateGroup.getInstanceOf("objecttypeStatisch");
        contentTemplate.add("object", object);
        contentTemplate.add("metaregisterVersie", super.getMetaregisterVersie());
        return contentTemplate.render(AbstractJavaGenerator.MAX_LINE_WIDTH);
    }

}
