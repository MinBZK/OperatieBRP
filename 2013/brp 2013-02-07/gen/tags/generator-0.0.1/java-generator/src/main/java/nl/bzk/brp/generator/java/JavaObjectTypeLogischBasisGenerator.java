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
import nl.bzk.brp.generator.java.domein.GenerationReport;
import nl.bzk.brp.generator.java.domein.Method;
import nl.bzk.brp.generator.java.domein.ObjectInterface;
import nl.bzk.brp.metaregister.dataaccess.AttribuutDao;
import nl.bzk.brp.metaregister.dataaccess.GroepDao;
import nl.bzk.brp.metaregister.dataaccess.ObjectTypeDao;
import nl.bzk.brp.metaregister.dataaccess.TekstDao;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional
public class JavaObjectTypeLogischBasisGenerator extends AbstractJavaGenerator<ObjectType> {

    private static final Logger logger              = LoggerFactory
                                                            .getLogger(JavaObjectTypeLogischBasisGenerator.class);

    private static final String TEMPLATE_GROUP_NAME = "objecttypes";

    @Autowired(required = true)
    private ObjectTypeDao       objectTypeDao;

    @Autowired(required = true)
    private GroepDao            groepDao;

    @Autowired(required = true)
    private AttribuutDao        attribuutDao;

    @Autowired(required = true)
    private TekstDao            tekstDao;

    public JavaObjectTypeLogischBasisGenerator() {
        setTemplateGroupName(TEMPLATE_GROUP_NAME);
        setBasePackage(GenerationPackageNames.OBJECTTYPE_LOGISCH_BASIS_PACKAGE);
    }

    @Override
    public GenerationReport genereer() {
        GenerationReport report = new GenerationReport();
        List<ObjectType> objectTypes = objectTypeDao.getDynamischeObjectTypes();
        for (ObjectType objectType : objectTypes) {
            Writer writer = creeerWriter(formatTypeName(objectType));
            genereerElement(writer,report, objectType);
        }

        return report;
    }

    private String genereerObjectType(final ObjectType objectType) {

        setBasePackage(GenerationPackageNames.OBJECTTYPE_LOGISCH_BASIS_PACKAGE);

        ObjectInterface basisInterface =
            new ObjectInterface(formatTypeName(objectType),tekstDao.getJavaDocForObject(objectType));

        basisInterface.setPackageName(GenerationPackageNames.OBJECTTYPE_LOGISCH_BASIS_PACKAGE.getPackage());
        basisInterface.setExtendsFrom("ObjectType");
        basisInterface.addImport("java.util.Set");
        basisInterface.addImport("nl.bzk.brp.model.basis.ObjectType");

        List<Groep> groepen = groepDao.getGroepen(objectType);
        for (Groep groep : groepen) {
            if(groep.getIdentCode().equalsIgnoreCase("Identiteit")) {
                //Identiteit groep doen we niet, maar wel de attributen ervan.
                List<Attribuut> attributen = attribuutDao.getAttributen(groep);
                for(Attribuut attribuut: attributen) {
                    if(attribuut.getNaam().equalsIgnoreCase("ID")) {
                        continue; 
                    }
                    Method accessor = new Method(attribuut.getType().getIdentCode(), "get" + attribuut.getIdentCode());
                    accessor.setJavaDoc(tekstDao.getJavaDocForObject(attribuut));
                    basisInterface.addAccessor(accessor);
                    basisInterface.addImport(JavaGeneratorUtils.getImportPackageForObjectType(attribuut.getType()).createImportStatement(attribuut.getType().getIdentCode()));
                }
                continue; //deze groep is een uitzondering waar alleen de attributen worden opgenomen
                
            }
            String groepType = groep.getIdentCode().startsWith("Persoon")?(groep.getIdentCode() + "Groep"):(objectType.getIdentCode() + groep.getIdentCode() + "Groep");
            Method accessor = new Method(groepType, "get" + groepType);
            accessor.setJavaDoc(tekstDao.getJavaDocForObject(groep));
            basisInterface.addAccessor(accessor);
            basisInterface.addImport(GenerationPackageNames.createImportStatement(
                    GenerationPackageNames.GROEP_LOGISCH_PACKAGE, groepType));
        }

        List<Attribuut> attributen = attribuutDao.getInverseAttributen(objectType);
        for (Attribuut attribuut : attributen) {
            String returnType = String.format("Set<? extends %s>", attribuut.getObjectType().getIdentCode());
            Method accessor = new Method(returnType, "get" + attribuut.getInverseAssociatieIdentCode());
            accessor.setJavaDoc(tekstDao.getJavaDocForObject(attribuut));
            basisInterface.addAccessor(accessor);
            basisInterface.addImport(JavaGeneratorUtils.getImportPackageForObjectType(attribuut.getType()).createImportStatement(attribuut.getObjectType().getIdentCode()));
        }


        return genereerObjectTypeInterfaceBasis(basisInterface);
    }

    private String formatTypeName(final ObjectType objectType) {
        return objectType.getIdentCode() + "Basis";
    }

    public void genereerElement(final Writer writer, final GenerationReport report, final ObjectType objectType) {
        logger.info("Genegereer object type " + objectType.getNaam() + ", type " + objectType.getSoortInhoud());

        try {
            String sourceCode = genereerObjectType(objectType);
            int result = write(sourceCode, writer);
            if (result > 0) {
                report.addSucccess("ObjectType", objectType.getNaam());
            } else {
                report.addFailure(objectType.getIdentCode(), "Fout bij het wegschrijven, resultaat=" + result);
            }
        } catch (IllegalStateException ex) {
            logger.error("Fout bij het genereren van objecttype " + objectType.getNaam(), verbose ? ex : null);
            report.addFailure(objectType.getNaam(), ex.getMessage());
        }
    }


    public String genereerObjectTypeInterfaceBasis(final ObjectInterface object) {
        if (group == null || !group.isDefined("objecttypeInterface")) {
            throw new IllegalStateException("Template groep niet geladen of objecttype template niet gedefinieerd");
        }
        contentTemplate = group.getInstanceOf("objecttypeInterface");
        contentTemplate.add("object", object);
        return contentTemplate.render(AbstractJavaGenerator.MAX_LINE_WIDTH);
    }

}
