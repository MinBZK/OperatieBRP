/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generator.java;

import java.io.Writer;
import java.util.List;

import nl.bzk.brp.generator.GenerationPackageNames;
import nl.bzk.brp.generator.GenerationReport;
import nl.bzk.brp.generator.java.domein.ObjectInterface;
import nl.bzk.brp.metaregister.dataaccess.ObjectTypeDao;
import nl.bzk.brp.metaregister.dataaccess.TekstDao;
import nl.bzk.brp.metaregister.model.ObjectType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional
public class JavaObjectTypeLogischGenerator extends AbstractJavaGenerator<ObjectType> {

    private static final Logger logger              = LoggerFactory.getLogger(JavaObjectTypeLogischGenerator.class);

    private static final String TEMPLATE_GROUP_NAME = "objecttypes";

    @Autowired(required = true)
    private ObjectTypeDao       objectTypeDao;

    @Autowired(required = true)
    private TekstDao            tekstDao;

    public JavaObjectTypeLogischGenerator() {
        setTemplateGroupName(TEMPLATE_GROUP_NAME);
        setBasePackage(GenerationPackageNames.OBJECTTYPE_LOGISCH_PACKAGE);
        setOverwritable(false); //Dit is usercode, dus mag niet zo maar overschreven worden bij generatie!
    }

    @Override
    public GenerationReport genereer() {
        GenerationReport report = new GenerationReport();

        List<ObjectType> objectTypes = objectTypeDao.getDynamischeObjectTypes();
        for (ObjectType objectType : objectTypes) {
            Writer writer = creeerWriter(objectType.getIdentCode());
            if(writer!= null) {
                genereerElement(writer, report, objectType);
            } else {
                logger.warn("Overslaan van generatie voor objectType " + objectType.getIdentCode());
            }
        }

        return report;
    }

    public void genereerElement(final Writer writer, final GenerationReport report, final ObjectType objectType) {
        logger.info("Genegereer object type " + objectType.getIdentCode() + ", type " + objectType.getSoortInhoud());

        try {
            String sourceCode = genereerObjectType(objectType);
            int result = write(sourceCode, writer);
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
        ObjectInterface objectInterface =
            new ObjectInterface(objectType.getIdentCode(), objectType.getBeschrijving() != null
                ? objectType.getBeschrijving() : objectType.getNaam());
        String interfaceDefinition = objectInterface.getName() + "Basis";
        objectInterface.addInterface(interfaceDefinition);
        objectInterface.addInterface("RootObject");
        objectInterface.setJavaDoc(tekstDao.getJavaDocForObject(objectType));
        objectInterface.setPackageName(GenerationPackageNames.OBJECTTYPE_LOGISCH_PACKAGE.getPackage());
        objectInterface.addImport("nl.bzk.brp.model.RootObject");
        objectInterface.addImport(GenerationPackageNames.createImportStatement(
                GenerationPackageNames.OBJECTTYPE_LOGISCH_BASIS_PACKAGE, interfaceDefinition));
        return genereerObjectTypeInterface(objectInterface);
    }

    public String genereerObjectTypeInterface(final ObjectInterface object) {
        if (group == null || !group.isDefined("objecttypeInterface")) {
            throw new IllegalStateException("Template groep niet geladen of objecttype template niet gedefinieerd");
        }
        contentTemplate = group.getInstanceOf("objecttypeInterface");
        contentTemplate.add("object", object);
        return contentTemplate.render(AbstractJavaGenerator.MAX_LINE_WIDTH);
    }

}
