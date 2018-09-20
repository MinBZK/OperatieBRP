/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generator.java;

import java.io.Writer;
import java.util.List;

import nl.bzk.brp.generator.GenerationPackageNames;
import nl.bzk.brp.generator.JavaGeneratorUtils;
import nl.bzk.brp.generator.java.domein.Field;
import nl.bzk.brp.generator.java.domein.GenerationReport;
import nl.bzk.brp.generator.java.domein.Method;
import nl.bzk.brp.generator.java.domein.ObjectClass;
import nl.bzk.brp.generator.java.domein.ObjectInterface;
import nl.bzk.brp.metaregister.dataaccess.AttribuutDao;
import nl.bzk.brp.metaregister.dataaccess.ObjectTypeDao;
import nl.bzk.brp.metaregister.dataaccess.TekstDao;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.AttribuutType;
import nl.bzk.brp.metaregister.model.BasisType;
import nl.bzk.brp.metaregister.model.ElementSoort;
import nl.bzk.brp.metaregister.model.GeneriekElement;
import nl.bzk.brp.metaregister.model.ObjectType;
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
        object.setExtendsFrom("AbstractStatischObjectType");
        object.setPackageName(GenerationPackageNames.OBJECTTYPE_OPERATIONEEL_STATISCH_PACKAGE.getPackage());
        object.addImport(GenerationPackageNames.BRP_MODEL_BASIS_PACKAGE.createImportStatement(object.getExtendsFrom()));

        object.addImport("javax.persistence.Access");
        object.addImport("javax.persistence.AccessType");
        object.addImport("javax.persistence.AttributeOverride");
        object.addImport("javax.persistence.Column");
        object.addImport("javax.persistence.Embedded");
        object.addImport("javax.persistence.Entity");
        object.addImport("javax.persistence.Id");
        object.addImport("javax.persistence.Table");

        object.addAnnotation("@Entity (name = " + objectType.getIdentDb() + ")");
        object.addAnnotation("@Table(schema = \"Kern\", name = " + objectType.getIdentCode() + "\")");
        object.addAnnotation("@Access(AccessType.FIELD)");
        List<Attribuut> attributen = attribuutDao.getAttributen(objectType);
        for (Attribuut attribuut : attributen) {
            String returnType = attribuut.getType().getIdentCode();

            String fieldName = attribuut.getIdentCode();
            if (fieldName.endsWith("ID")) {
                fieldName = objectType.getIdentCode() + "Id";
            }

            Field field = new Field(attribuut.getType().getIdentCode(), fieldName);
            if (fieldName.endsWith("id")) {
                field.addAnnotation("@Id");
                GeneriekElement basisElement = attribuut.getType();
                BasisType basisType = null;
                if(basisElement.getSoortElement().equals(ElementSoort.ATTRIBUUTTYPE)) {
                    basisType = ((AttribuutType)basisElement).getBasisType();
                }
                field.setType(DataTypeTranslator.getJavaType(basisType.getIdentCode()));
            } else {
                field.addAnnotation("@Embedded");
                field.addAnnotation("@AttributeOverride(name = \"waarde\", column = @Column(name = \""
                    + attribuut.getIdentDb().toLowerCase() + "\"))");
                object.addImport(JavaGeneratorUtils.getImportPackageForObjectType(attribuut.getType()).createImportStatement(attribuut.getType().getIdentCode()));
            }
            field.setJavaDoc(tekstDao.getJavaDocForObject(attribuut));
            
            Method accessor = new Method(returnType, "get" + GeneratorUtils.upperTheFirstCharacter(fieldName));
            accessor.setJavaDoc(tekstDao.getJavaDocForObject(attribuut));
            object.addAccessor(accessor);
            accessor.setBody("return " + field.getName());

            
            object.addAttribuut(field);
        }

        return genereerObjectTypeStatisch(object);

    }

    public String genereerObjectTypeStatisch(final ObjectInterface object) {
        if (group == null || !group.isDefined("objecttypeStatisch")) {
            throw new IllegalStateException("Template groep niet geladen of objecttype template niet gedefinieerd");
        }
        contentTemplate = group.getInstanceOf("objecttypeStatisch");
        contentTemplate.add("object", object);
        return contentTemplate.render(AbstractJavaGenerator.MAX_LINE_WIDTH);
    }

}
