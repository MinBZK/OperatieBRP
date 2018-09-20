/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generator.java;

import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.bzk.brp.generator.GenerationPackageNames;
import nl.bzk.brp.generator.GenerationReport;
import nl.bzk.brp.generator.JavaGeneratorUtils;
import nl.bzk.brp.generator.java.domein.Constructor;
import nl.bzk.brp.generator.java.domein.Field;
import nl.bzk.brp.generator.java.domein.Identifier;
import nl.bzk.brp.generator.java.domein.ObjectClass;
import nl.bzk.brp.generator.java.domein.ObjectInterface;
import nl.bzk.brp.metaregister.dataaccess.AttribuutDao;
import nl.bzk.brp.metaregister.dataaccess.GroepDao;
import nl.bzk.brp.metaregister.dataaccess.ObjectTypeDao;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.HistorieVastleggen;
import nl.bzk.brp.metaregister.model.ObjectType;
import nl.bzk.brp.metaregister.model.SoortInhoud;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional
public class JavaObjectTypeOperationeelBasisGenerator extends AbstractJavaGenerator<ObjectType> {

    private static final String TEMPLATE_GROUP_NAME = "objecttypesOperationeelBasis";

    private static final String TEMPLATE_NAAM       = "objecttypeOperationeelBasis";

    private static final Logger logger              = LoggerFactory
            .getLogger(JavaObjectTypeOperationeelBasisGenerator.class);

    @Autowired(required = true)
    private ObjectTypeDao       objectTypeDao;

    @Autowired(required = true)
    private AttribuutDao        attribuutDao;

    @Autowired(required = true)
    private GroepDao            groepDao;

    public JavaObjectTypeOperationeelBasisGenerator() {
        setTemplateGroupName(TEMPLATE_GROUP_NAME);
        setBasePackage(GenerationPackageNames.OBJECTTYPE_OPERATIONEEL_BASIS_PACKAGE);
    }

    @Override
    public GenerationReport genereer() {
        GenerationReport report = new GenerationReport();
        report.setObjectType("Objecttypes Operationeel Basis");
        List<ObjectType> objectTypes = objectTypeDao.getDynamischeObjectTypes();
        for (ObjectType objectType : objectTypes) {
            Writer writer = creeerWriter(getTypeIdentifier(objectType));
            genereerElement(writer, report, objectType);
        }
        return report;
    }

    private String getTypeIdentifier(final ObjectType objectType) {
        return "Abstract" + objectType.getIdentCode() + "Model";
    }

    private String getInterface(final ObjectType objectType) {
        return objectType.getIdentCode() + "Basis";
    }

    @Override
    public void genereerElement(final Writer writer, final GenerationReport report, final ObjectType objectType) {
        ObjectClass type = new ObjectClass(getTypeIdentifier(objectType), "TODO");

        type.setPackageName(basePackageName.getPackage());

        type.addImport(GenerationPackageNames.OBJECTTYPE_OPERATIONEEL_PACKAGE.createImportStatement("ActieModel"));
        type.addImport("nl.bzk.brp.model.basis.AbstractDynamischObjectType");
        type.addImport("nl.bzk.brp.model.objecttype.operationeel.statisch.StatusHistorie");
        type.setExtendsFrom("AbstractDynamischObjectType");
        type.addInterface(getInterface(objectType));

        String stam = objectType.getIdentCode();
        {
            String upperIdentCode = stam.toUpperCase();
            Field field = new Field("Long", "id");
            field.addAnnotation("@Id");
            field.addAnnotation("@SequenceGenerator(name = \"%s\", sequenceName = \"Kern.seq_%s\")", upperIdentCode, objectType.getIdentDb());
            field.addAnnotation("@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = \"%s\")", upperIdentCode);
            type.addAttribuut(field);
        }

        Constructor constructor = new Constructor(type.getName());
        type.addConstructor(constructor);

        List<Identifier> groepNamen = new ArrayList<Identifier>();
        List<Identifier> inlines = new ArrayList<Identifier>();
        List<Groep> groepen = groepDao.getGroepen(objectType);
        for (Groep groep : groepen) {
            if (groep.getHistorieVastleggen() == HistorieVastleggen.GEEN.getCode()) {
                List<Attribuut> attrubuten = attribuutDao.getAttributen(groep);
                for (Attribuut attribuut : attrubuten) {
                    if (!JavaGeneratorUtils.isId(attribuut)) {
                        addAttribuut(type, attribuut);
                        if (SoortInhoud.DYNAMISCH.isSoort(attribuut.getType())) {
                            constructor.addParameter(attribuut.getIdentCode());
                        } else {
                            inlines.add(new Identifier(attribuut.getIdentCode()));
                        }
                    }
                }
            } else {
                groepNamen.add(new Identifier(groep.getIdentCode()));
                Field field = new Field(getGroepOperationeelActueel(groep), groep.getIdentCode() + "Groep");
                field.addAnnotation("@Embedded");
                if (groep.getVerplicht() == 'J') {
                    field.addAnnotation("@NotNull");
                }
                type.addAttribuut(field);
            }
        }

        List<Attribuut> statusHistorieAttributen = attribuutDao.getStatusHistorieAttributen(objectType);
        for (Attribuut attribuut : statusHistorieAttributen) {
            Groep groep = groepDao.getBySyncId(attribuut.getOrgSyncid());
            Field field = new Field(attribuut.getType().getIdentCode(), groep.getIdentCode() + "StatusHis");
            field.addAnnotation("@Column(name = \"%s\")", attribuut.getIdentDb());
            field.addAnnotation("@Enumerated(value = EnumType.STRING)");
            field.addAnnotation("@NotNull");
            field.setInitializer("StatusHistorie.X");
            type.addAttribuut(field);
        }

        List<Attribuut> inverseAttributen = attribuutDao.getInverseAttributen(objectType);
        Set<ObjectType> componentTypes = new HashSet<ObjectType>(objectTypeDao.getComponentTypes());
        for (Attribuut attribuut : inverseAttributen) {
            ObjectType inverseObjectType = attribuut.getObjectType();
            String javaType = JavaGeneratorUtils.getJavaType(inverseObjectType);
            Field field = new Field("Set<" + javaType + ">", attribuut.getInverseAssociatieIdentCode());
            if (componentTypes.contains(inverseObjectType)) {
                field.addAnnotation("@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)");
            } else {
                field.addAnnotation("@OneToMany(cascade ="
                        + " { CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.REMOVE },\n"
                        + "           fetch = FetchType.EAGER)");
            }
            field.addAnnotation("@JoinColumn(name = \"%s\")", attribuut.getIdentDb());
            field.setInitializer("new HashSet<%s>()", javaType);
            type.addAttribuut(field);
        }

        String sourceCode = genereerGroep(type, stam, inlines, groepNamen);

        int result = write(sourceCode, writer);
        logger.info("Groep:" + type.getName());
        if (result > 0) {
            report.addSucccess(type.getName().toString(), sourceCode);
        } else {
            report.addFailure(stam, "Fout bij het wegschrijven, resultaat=" + result);
        }
    }

    private String genereerGroep(final ObjectInterface type, final String stam, final List<Identifier> inlines, final List<Identifier> groepen) {
        if (group == null || !group.isDefined(TEMPLATE_NAAM)) {
            throw new IllegalStateException("onbekende of incorrecte template: " + TEMPLATE_NAAM);
        }
        contentTemplate = group.getInstanceOf(TEMPLATE_NAAM);
        contentTemplate.add("type", type);
        contentTemplate.add("stam", new Identifier(stam));
        contentTemplate.add("inlines", inlines);
        contentTemplate.add("groepen", groepen);
        return contentTemplate.render(AbstractJavaGenerator.MAX_LINE_WIDTH);
    }

}
