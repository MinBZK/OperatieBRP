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
import nl.bzk.brp.generator.JavaGeneratorUtils;
import nl.bzk.brp.generator.java.domein.Field;
import nl.bzk.brp.generator.java.domein.Identifier;
import nl.bzk.brp.generator.java.domein.ObjectClass;
import nl.bzk.brp.generator.java.domein.ObjectInterface;
import nl.bzk.brp.metaregister.dataaccess.ObjectTypeDao;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.Laag;
import nl.bzk.brp.metaregister.model.ObjectType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional
public class JavaGroepOperationeelHistorischGenerator extends AbstractGroepGenerator {

    private static final String TEMPLATE_GROUP_NAME = "groepenOperationeelHistorisch";

    private static final String TEMPLATE_NAAM       = "groepOperationeelHistorisch";

    private static final Logger logger              = LoggerFactory
                                                            .getLogger(JavaGroepOperationeelHistorischGenerator.class);

    @Autowired(required = true)
    private ObjectTypeDao objectTypeDao;

    public JavaGroepOperationeelHistorischGenerator() {
        setTemplateGroupName(TEMPLATE_GROUP_NAME);
        setBasePackage(GenerationPackageNames.GROEP_OPERATIONEEL_HISTORISCH_PACKAGE);
    }

    @Override
    protected String getPackageInfoString() {
        return ".";
    }

    @Override
    public GenerationReport genereer() {
        GenerationReport report = new GenerationReport();
        report.setObjectType("Groepen Operationeel Historisch");
        Collection<Groep> groepen = getGroepen();
        for (Groep groep : groepen) {
            //@TODO, tijdelijk geen groepen genereren die expliciet niet in het LGM zitten.
            //Momenteel zijn dit alle indicaties die als groep in het BMR zitten. (?)
            if (!('N' == groep.getInLgm())) {
                Writer writer = creeerWriter(getTypeIdentifier(groep));
                genereerElement(writer, report, groep);
            }
        }
        return report;
    }

    public void genereerElement(final Writer writer, final GenerationReport report, final Groep groep) {
        ObjectClass type = new ObjectClass(getTypeIdentifier(groep), getTypeName(groep));

        type.setPackageName(basePackageName.getPackage());
        Identifier extendsFromType = getExtendsFromType(groep);
        ObjectType objectType = groep.getObjectType();
        String fieldType = JavaGeneratorUtils.getJavaType(objectType);

        type.setExtendsFrom(extendsFromType);
        Field field = new Field(fieldType, objectType.getIdentCode());
        type.addAttribuut(field);

        String sourceCode =
            genereerGroep(type, getSuperSuperType(groep), objectType.getIdentCode(), getIdentDb(groep));

        int result = write(sourceCode, writer);
        logger.info("Groep:" + type.getName());
        if (result > 0) {
            report.addSucccess(type.getName().toString(), sourceCode);
        } else {
            report.addFailure(groep.getIdentCode(), "Fout bij het wegschrijven, resultaat=" + result);
        }
    }

    private String getIdentDb(final Groep groep) {
        if (groep.getHistorieVastleggen() == 'B' || groep.getHistorieVastleggen() == 'F') {
            Laag.OPERATIONEEL.set();
            ObjectType hisTabel = objectTypeDao.getBySyncId(groep.getSyncid());
            Laag.LOGISCH.set();
            return hisTabel.getIdentDb();
        } else {
            return null;
        }
    }

    private String getSuperSuperType(final Groep groep) {
        return formatteerGroep("Abstract%s%sGroep", groep).getUpperCamel();
    }

    private Identifier getExtendsFromType(final Groep groep) {
        return formatteerGroep("Abstract%s%sHisModel", groep);
    }

    private String getTypeIdentifier(final Groep groep) {
        return formatteerGroep("%s%sHisModel", groep).getUpperCamel();
    }

    private String getTypeName(final Groep groep) {
        String format = "Implementatie voor groep \"%s\" van objecttype \"%s\"";
        return String.format(format, groep.getNaam(), groep.getObjectType().getNaam());
    }

    private String genereerGroep(final ObjectInterface type, final String superSuper, final String objectType,
            final String identDb)
    {
        if (templateGroup == null || !templateGroup.isDefined(TEMPLATE_NAAM)) {
            throw new IllegalStateException("onbekende of incorrecte template: " + TEMPLATE_NAAM);
        }
        contentTemplate = templateGroup.getInstanceOf(TEMPLATE_NAAM);
        contentTemplate.add("groep", type);
        contentTemplate.add("superSuper", superSuper);
        contentTemplate.add("objectType", new Identifier(objectType));
        contentTemplate.add("identDb", identDb);
        return contentTemplate.render(AbstractJavaGenerator.MAX_LINE_WIDTH);
    }

}
