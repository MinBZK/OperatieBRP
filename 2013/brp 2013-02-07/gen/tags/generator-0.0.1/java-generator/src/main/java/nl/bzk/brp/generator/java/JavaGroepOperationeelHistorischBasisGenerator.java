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
import nl.bzk.brp.generator.java.domein.ObjectClass;
import nl.bzk.brp.generator.java.domein.ObjectInterface;
import nl.bzk.brp.metaregister.dataaccess.GroepDao;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.HistorieVastleggen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional
public class JavaGroepOperationeelHistorischBasisGenerator extends AbstractJavaGenerator<Groep> {

    private static final String TEMPLATE_GROUP_NAME = "groepenOperationeelHistorischBasis";

    private static final String TEMPLATE_NAAM       = "groepOperationeelHistorischBasis";

    private static final Logger logger              =
                                                        LoggerFactory
                                                                .getLogger(JavaGroepOperationeelHistorischBasisGenerator.class);

    @Autowired(required = true)
    private GroepDao            groepDao;

    public JavaGroepOperationeelHistorischBasisGenerator() {
        setTemplateGroupName(TEMPLATE_GROUP_NAME);
        setBasePackage(GenerationPackageNames.GROEP_OPERATIONEEL_HISTORISCH_BASIS_PACKAGE);
    }

    @Override
    public GenerationReport genereer() {
        GenerationReport report = new GenerationReport();
        report.setObjectType("Groepen Operationeel Historisch Basis");
        List<Groep> groepen = groepDao.getDynamischHistorisch();
        for (Groep groep : groepen) {
            Writer writer = creeerWriter(getTypeIdentifier(groep));
            genereerElement(writer, report, groep);
        }
        return report;
    }

    public void genereerElement(final Writer writer, final GenerationReport report, final Groep groep) {
        ObjectClass type = new ObjectClass(getTypeIdentifier(groep), getTypeName(groep));

        type.setPackageName(basePackageName.getPackage());
        String extendsFromType = getExtendsFromType(groep);
        String fieldType = JavaGeneratorUtils.getJavaType(groep.getObjectType());

        type.addImport(GenerationPackageNames.GROEP_OPERATIONEEL_PACKAGE.createImportStatement(extendsFromType));
        type.addImport(GenerationPackageNames.OBJECTTYPE_OPERATIONEEL_PACKAGE.createImportStatement("ActieModel"));
        type.addImport(GenerationPackageNames.OBJECTTYPE_OPERATIONEEL_PACKAGE.createImportStatement(fieldType));
        type.setExtendsFrom(extendsFromType);
        Field field = new Field(fieldType, groep.getObjectType().getIdentCode());
        type.addAttribuut(field);

        String sourceCode =
            genereerGroep(type, getIdentDb(groep), groep.getObjectType().getIdentDb(), heeftMaterieleHistorie(groep));

        int result = write(sourceCode, writer);
        logger.info("Groep:" + type.getName());
        if (result > 0) {
            report.addSucccess(type.getName().toString(), sourceCode);
        } else {
            report.addFailure(groep.getIdentCode(), "Fout bij het wegschrijven, resultaat=" + result);
        }
    }

    private String getIdentDb(final Groep groep) {
        String resultaat = groep.getObjectType().getIdentDb();
        if (!"Standaard".equals(groep.getIdentDb())) {
            resultaat += groep.getIdentDb();
        }
        return resultaat;
    }

    private boolean heeftMaterieleHistorie(final Groep groep) {
        return groep.getHistorieVastleggen() == HistorieVastleggen.BEIDE.getCode();
    }

    private String getExtendsFromType(final Groep groep) {
        return formatType("Abstract%s%sGroep", groep);
    }

    private String getTypeIdentifier(final Groep groep) {
        return formatType("Abstract%s%sHisModel", groep);
    }

    private String formatType(final String format, final Groep groep) {
        return String.format(format, groep.getObjectType().getIdentCode(), groep.getIdentCode());
    }

    private String getTypeName(final Groep groep) {
        String format = "Implementatie voor groep \"%s\" van objecttype \"%s\"";
        return String.format(format, groep.getNaam(), groep.getObjectType().getNaam());
    }

    private String genereerGroep(final ObjectInterface type, final String identDb, final String identDbObjectType,
            final Boolean heeftMaterieleHistorie)
    {
        if (group == null || !group.isDefined(TEMPLATE_NAAM)) {
            throw new IllegalStateException("onbekende of incorrecte template: " + TEMPLATE_NAAM);
        }
        contentTemplate = group.getInstanceOf(TEMPLATE_NAAM);
        contentTemplate.add("groep", type);
        contentTemplate.add("identDb", identDb);
        contentTemplate.add("identDbObjectType", identDbObjectType);
        contentTemplate.add("heeftMaterieleHistorie", heeftMaterieleHistorie);
        return contentTemplate.render(AbstractJavaGenerator.MAX_LINE_WIDTH);
    }

}
