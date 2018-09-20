/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generator.java;

import java.io.Writer;
import java.util.Collection;

import nl.bzk.brp.generator.GenerationPackageNames;
import nl.bzk.brp.generator.GenerationReport;
import nl.bzk.brp.generator.java.domein.Identifier;
import nl.bzk.brp.generator.java.domein.ObjectInterface;
import nl.bzk.brp.metaregister.model.Groep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional
public class JavaGroepLogischGenerator extends AbstractGroepGenerator {

    private static final String TEMPLATE_GROUP_NAME = "groepenLogisch";

    private static final String TEMPLATE_NAAM       = "groepLogisch";

    private static final Logger logger              = LoggerFactory.getLogger(JavaGroepLogischGenerator.class);

    public JavaGroepLogischGenerator() {
        setTemplateGroupName(TEMPLATE_GROUP_NAME);
        setBasePackage(GenerationPackageNames.GROEP_LOGISCH_PACKAGE);
        setOverwritable(false);
    }

    @Override
    public GenerationReport genereer() {
        GenerationReport report = new GenerationReport();
        report.setObjectType("Groepen Logisch");
        Collection<Groep> groepen = getGroepen();
        for (Groep groep : groepen) {
            Writer writer = creeerWriter(getGroepLogisch(groep));
            if(writer!=null) {
                genereerElement(writer, report, groep);
            } else {
                logger.warn("Groep " + getGroepLogisch(groep) + " overgeslagen, bestond al");
            }
        }
        return report;
    }

    @Override
    protected String getPackageInfoString() {
        return ".";
    }

    public void genereerElement(final Writer writer, final GenerationReport report, final Groep groep) {
        Identifier typeIdentifier = getGroepLogisch(groep);
        ObjectInterface type = new ObjectInterface(typeIdentifier, getJavaDoc(groep));
        type.setPackageName(basePackageName.getPackage());
        type.setJavaDoc(getJavaDoc(groep));
        type.addExtendInterface(getGroepLogischBasis(groep));

        String sourceCode = genereerGroep(type);
        int result = write(sourceCode, writer);
        logger.info("Groep:" + type.getName());
        if (result > 0) {
            report.addSucccess(type.getName().toString(), sourceCode);
        } else {
            report.addFailure(groep.getIdentCode(), "Fout bij het wegschrijven, resultaat=" + result);
        }
    }

    private String getJavaDoc(final Groep groep) {
        String format = "Interface voor groep \"%s\" van objecttype \"%s\"";
        return String.format(format, groep.getNaam(), groep.getObjectType().getNaam());
    }

    private String genereerGroep(final ObjectInterface type) {
        if (templateGroup == null || !templateGroup.isDefined(TEMPLATE_NAAM)) {
            throw new IllegalStateException("onbekende of incorrecte template: " + TEMPLATE_NAAM);
        }
        contentTemplate = templateGroup.getInstanceOf(TEMPLATE_NAAM);
        contentTemplate.add("groep", type);
        return contentTemplate.render(AbstractJavaGenerator.MAX_LINE_WIDTH);
    }

}
