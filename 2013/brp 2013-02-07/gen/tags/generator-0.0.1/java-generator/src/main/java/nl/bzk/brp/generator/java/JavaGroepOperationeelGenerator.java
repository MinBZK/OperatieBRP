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
import nl.bzk.brp.metaregister.dataaccess.AttribuutDao;
import nl.bzk.brp.metaregister.dataaccess.AttribuutTypeDao;
import nl.bzk.brp.metaregister.dataaccess.GroepDao;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.AttribuutType;
import nl.bzk.brp.metaregister.model.ElementSoort;
import nl.bzk.brp.metaregister.model.GeneriekElement;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.SoortInhoud;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional
public class JavaGroepOperationeelGenerator extends AbstractJavaGenerator<Groep> {

    private static final String TEMPLATE_GROUP_NAME = "groepenOperationeel";

    private static final String TEMPLATE_NAAM       = "groepOperationeel";

    private static final Logger logger              = LoggerFactory.getLogger(JavaGroepOperationeelGenerator.class);

    @Autowired(required = true)
    private GroepDao            groepDao;

    @Autowired(required = true)
    private AttribuutDao        attribuutDao;

    @Autowired(required = true)
    private AttribuutTypeDao    attribuutTypeDao;

    public JavaGroepOperationeelGenerator() {
        setTemplateGroupName(TEMPLATE_GROUP_NAME);
        setBasePackage(GenerationPackageNames.GROEP_OPERATIONEEL_PACKAGE);
    }

    @Override
    public GenerationReport genereer() {
        GenerationReport report = new GenerationReport();
        report.setObjectType("Groepen Operationeel");
        List<Groep> groepen = groepDao.getDynamischHistorisch();
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
        String extendsFromType = getExtendsFromType(groep);
        List<Attribuut> attributen = attribuutDao.getAttributen(groep);

        type.addImports(JavaGeneratorUtils.getImportLijst(groep, attributen));
        type.addImport("javax.persistence.Access");
        type.addImport("javax.persistence.AccessType");
        type.addImport("javax.persistence.MappedSuperclass");
        type.addImport("nl.bzk.brp.model.basis.AbstractGroep");
        type.addImport(GenerationPackageNames.GROEP_LOGISCH_BASIS_PACKAGE.createImportStatement(extendsFromType));
        type.addInterface(extendsFromType);

        for (Attribuut attribuut : attributen) {
            addAttribuut(type, attribuut);
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

    private void addAttribuut(final ObjectClass type, final Attribuut attribuut) {
        GeneriekElement typeElement = attribuut.getType();
        String javaType = JavaGeneratorUtils.getJavaType(typeElement);
        String column = attribuut.getIdentDb();
        Field field = new Field(javaType, attribuut.getIdentCode());
        switch (ElementSoort.getSoort(typeElement)) {
            case ATTRIBUUTTYPE:
                AttribuutType attribuutType = attribuutTypeDao.cast(typeElement);
                if ("Boolean".equals(attribuutType.getBasisType().getNaam())) {
                    field.addAnnotation("@Column(name = \"%s\")", column);
                    type.addImport("javax.persistence.Column");
                    field.addAnnotation("@Type(type = \"%s\")", javaType);
                    type.addImport("org.hibernate.annotations.Type");
                } else {
                    field.addAnnotation("@Embedded");
                    type.addImport("javax.persistence.Embedded");
                    field.addAnnotation("@AttributeOverride(name = \"waarde\", column = @Column(name = \"%s\"))", column);
                    type.addImport("javax.persistence.AttributeOverride");
                    type.addImport("javax.persistence.Column");
                }
                break;
            case OBJECTTYPE: switch (SoortInhoud.getSoort(typeElement)) {
                case ENUMERATIE:
                    field.addAnnotation("@Column(name = \"%s\")", column);
                    type.addImport("javax.persistence.Column");
                    field.addAnnotation("@Enumerated");
                    type.addImport("javax.persistence.Enumerated");
                    break;
                case STATISCH:
                    field.addAnnotation("@ManyToOne");
                    type.addImport("javax.persistence.ManyToOne");
                    field.addAnnotation("@JoinColumn(name = \"%s\")", column);
                    type.addImport("javax.persistence.JoinColumn");
                    break;
                case DYNAMISCH:
                    field.addAnnotation("@OneToOne");
                    type.addImport("javax.persistence.OneToOne");
                    field.addAnnotation("@JoinColumn(name = \"%s\")", column);
                    type.addImport("javax.persistence.JoinColumn");
                    break;
                default:
                    throw new RuntimeException("onbekende inhoud: " + SoortInhoud.getSoort(typeElement));
            }
        }
        type.addAttribuut(field);
    }

    private String getExtendsFromType(final Groep groep) {
        return formatType("%s%sGroepBasis", groep);
    }

    private String getTypeIdentifier(final Groep groep) {
        return formatType("Abstract%s%sGroep", groep);
    }

    private String formatType(final String format, final Groep groep) {
        return String.format(format, groep.getObjectType().getIdentCode(), groep.getIdentCode());
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
