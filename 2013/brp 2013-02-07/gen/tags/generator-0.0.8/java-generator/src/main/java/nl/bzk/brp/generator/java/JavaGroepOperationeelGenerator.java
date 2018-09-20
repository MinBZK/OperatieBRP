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
import nl.bzk.brp.generator.JavaGeneratorUtils;
import nl.bzk.brp.generator.java.domein.ObjectClass;
import nl.bzk.brp.generator.java.domein.ObjectInterface;
import nl.bzk.brp.metaregister.dataaccess.AttribuutDao;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.Groep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional
public class JavaGroepOperationeelGenerator extends AbstractGroepGenerator {

    private static final String TEMPLATE_GROUP_NAME = "groepenOperationeel";

    private static final String TEMPLATE_NAAM       = "groepOperationeel";

    private static final Logger logger              = LoggerFactory.getLogger(JavaGroepOperationeelGenerator.class);

    @Autowired(required = true)
    private AttribuutDao        attribuutDao;

    public JavaGroepOperationeelGenerator() {
        setTemplateGroupName(TEMPLATE_GROUP_NAME);
        setBasePackage(GenerationPackageNames.GROEP_OPERATIONEEL_PACKAGE);
    }

    @Override
    public GenerationReport genereer() {
        GenerationReport report = new GenerationReport();
        report.setObjectType("Groepen Operationeel");
        List<Groep> groepen = getGroepen();
        for (Groep groep : groepen) {
            Writer writer = creeerWriter(getTypeIdentifier(groep));
            genereerElement(writer, report, groep);
        }
        return report;
    }

    public void genereerElement(final Writer writer, final GenerationReport report, final Groep groep) {
        String typeIdentifier = getTypeIdentifier(groep);
        ObjectClass type = new ObjectClass(typeIdentifier, getTypeName(groep));

        type.setPackageName(basePackageName.getPackage());
        String extendsFromType = getGroepLogischBasis(groep);
        List<Attribuut> attributen = attribuutDao.getAttributen(groep);

        type.addImports(JavaGeneratorUtils.getImportLijst(groep, attributen));
        type.addImport("javax.persistence.Access");
        type.addImport("javax.persistence.AccessType");
        type.addImport("javax.persistence.MappedSuperclass");
        type.addImport("nl.bzk.brp.model.basis.AbstractGroep");
        type.addImport(GenerationPackageNames.GROEP_LOGISCH_BASIS_PACKAGE.createImportStatement(extendsFromType));
        type.addInterface(extendsFromType);

        for (Attribuut attribuut : attributen) {
            if (!JavaGeneratorUtils.isId(attribuut)) {
                addAttribuut(type, attribuut);
            }
        }

        String sourceCode = genereerGroep(type);
        int result = write(sourceCode, writer);
        logger.info("Groep:" + type.getName());
        if (result > 0) {
            report.addSucccess(type.getName().toString(), sourceCode);
        } else {
            report.addFailure(groep.getIdentCode(), "Fout bij het wegschrijven, resultaat=" + result);
        }
    }

    private String getTypeIdentifier(final Groep groep) {
        return formatteerGroep("Abstract%s%sGroep", groep);
    }

    private String getTypeName(final Groep groep) {
        String format = "Implementatie voor groep \"%s\" van objecttype \"%s\"";
        return String.format(format, groep.getNaam(), groep.getObjectType().getNaam());
    }

    private String genereerGroep(final ObjectInterface type) {
        if (group == null || !group.isDefined(TEMPLATE_NAAM)) {
            throw new IllegalStateException("onbekende of incorrecte template: " + TEMPLATE_NAAM);
        }
        contentTemplate = group.getInstanceOf(TEMPLATE_NAAM);
        contentTemplate.add("groep", type);
        return contentTemplate.render(AbstractJavaGenerator.MAX_LINE_WIDTH);
    }

}
