/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.jibx;

import nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie;
import nl.bzk.brp.generatoren.algemeen.common.BmrTargetPlatform;
import nl.bzk.brp.generatoren.algemeen.common.GeneratieUtil;
import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.generatoren.java.naamgeving.AttribuutWrapperNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.NaamgevingStrategie;
import nl.bzk.brp.generatoren.java.util.JavaGeneratieUtil;
import nl.bzk.brp.generatoren.jibx.common.JibxBinding;
import nl.bzk.brp.generatoren.jibx.model.Binding;
import nl.bzk.brp.generatoren.jibx.model.Mapping;
import nl.bzk.brp.generatoren.jibx.model._Object;
import nl.bzk.brp.generatoren.jibx.util.JibxGeneratieUtil;
import nl.bzk.brp.generatoren.jibx.util.NaamgevingUtil;
import nl.bzk.brp.metaregister.model.AttribuutType;
import nl.bzk.brp.metaregister.model.BasisType;
import nl.bzk.brp.metaregister.model.Element;
import nl.bzk.brp.metaregister.model.WaarderegelWaarde;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * Generator die voor elk attribuut type in het BMR een JiBX binding genereert.
 */
@Component("attribuutTypenJibxGenerator")
public class AttribuutTypenGenerator extends AbstractJibxGenerator {

    private NaamgevingStrategie naamgevingStrategie = new AttribuutWrapperNaamgevingStrategie();

    /** {@inheritDoc} */
    @Override
    public void genereer(final GeneratorConfiguratie generatorConfiguratie) {
        List<AttribuutType> attribuutTypen = this.getBmrDao().getAttribuutTypen();
        Binding binding = new Binding();
        this.voegJibxBindingIncludeToe(binding, JibxBinding.BASIS_TYPEN);
        for (AttribuutType attribuutType : attribuutTypen) {
            if (zitInSchemaVoorGeneratie(attribuutType)) {
                if (attribuutType.getBasisType().getId() != ID_BASISTYPE_ID) {
                    //Indien er waardes zijn, geen mapping genereren. Hiervoor worden serializers en deserializers
                    // gegenereerd door de binding util generator.
                    final List<WaarderegelWaarde> waardes = getBmrDao().getWaardeEnumeratiesVoorElement(
                            attribuutType, false, true);
                    if (waardes.isEmpty()) {
                        binding.getMappingList().add(this.maakMapping(attribuutType));
                    }
                }
            }
        }
        this.jibxWriterFactory(generatorConfiguratie).
                marshallXmlEnSchrijfWeg(JibxBinding.ATTRIBUUT_TYPEN, binding, this);
    }

    /**
     * Maak de mapping aan voor dit attribuut type.
     *
     * @param attribuutType het attribuut type
     * @return de mapping
     */
    private Mapping maakMapping(final AttribuutType attribuutType) {
        final Mapping mapping = new Mapping();
        mapping.setAbstract(true);
        final JavaType javaTypeVoorElement = this.naamgevingStrategie.getJavaTypeVoorElement(attribuutType);
        mapping.setTypeName(javaTypeVoorElement.getNaam());
        mapping.set_Class(javaTypeVoorElement.getFullyQualifiedClassName());

        //Haal het basis type op voor het java platform.
        final BasisType defaultJavaType =
                getBmrDao().getBasisTypeVoorAttribuutType(attribuutType, BmrTargetPlatform.JAVA).getBasisType();

        //Bepaal het java type: TODO, dit is eigenlijk toepassing van een hack op 'numerieke code'.
        final JavaType javaType = JavaGeneratieUtil.bepaalJavaBasisTypeVoorAttribuutType(attribuutType,
                                                                                         defaultJavaType);

        //Haal het basis type op voor het xsd platform.
        final Element xsdType =
                getBmrDao().getBasisTypeVoorAttribuutType(attribuutType, BmrTargetPlatform.XSD).getTypeImpl()
                        .getElementByBasistype();

        boolean isSpecialeConversieNodigTussenXmlEnJava =
                JibxGeneratieUtil.bepaalIndienAttribuutTypeSpecialeConversieVereistTussenXmlEnJava(xsdType, javaType);

        //Indien er waardes zijn, dan moeten we serializen en deserialize tussen een string en een enum.
        final List<WaarderegelWaarde> waardes = getBmrDao().getWaardeEnumeratiesVoorElement(
                        attribuutType, false, true);
        if (!waardes.isEmpty()) {
            isSpecialeConversieNodigTussenXmlEnJava = true;
        }

        if (isSpecialeConversieNodigTussenXmlEnJava) {
            String serializer = "nl.bzk.brp.model.binding.BindingUtilAttribuutTypen."
                    + GeneratieUtil.lowerTheFirstCharacter(attribuutType.getIdentCode()) + "NaarXml";
            String deserializer = "nl.bzk.brp.model.binding.BindingUtilAttribuutTypen.xmlNaar"
                    + attribuutType.getIdentCode();
            mapping.set_Class("nl.bzk.brp.model.basis.AbstractAttribuut");
            mapping.setObject(new _Object());
            mapping.getObject().setCreateType(
                    javaTypeVoorElement.getFullyQualifiedClassName());
            mapping.getChoiceList().add(maakValueMappingChoice("text", null, "waarde",
                                                               javaType.getFullyQualifiedClassName(),
                                                               null, null, null, null, false,
                                                               serializer,
                                                               deserializer));
        } else {
            final String mapAsType = NaamgevingUtil.converteerFullyQualifiedNaamNaarIdentifier(
                    javaType.getFullyQualifiedClassName());
            mapping.getChoiceList().add(this.maakStructureMappingChoice(null, null, null, mapAsType, false));
        }
        return mapping;
    }
}
