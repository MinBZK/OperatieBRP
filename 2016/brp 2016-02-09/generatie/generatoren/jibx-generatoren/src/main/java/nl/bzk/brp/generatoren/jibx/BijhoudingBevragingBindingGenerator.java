/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.jibx;

import nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie;
import nl.bzk.brp.generatoren.algemeen.common.GeneratieUtil;
import nl.bzk.brp.generatoren.jibx.common.JibxBinding;
import nl.bzk.brp.generatoren.jibx.model.Binding;
import nl.bzk.brp.generatoren.jibx.model.Mapping;
import nl.bzk.brp.generatoren.jibx.model.Structure;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.Groep;
import org.springframework.stereotype.Component;

/**
 * Genereert bindings voor bevraging t.b.v. bijhouden.
 */
@Component("bijhoudingBevragingBindingGenerator")
public class BijhoudingBevragingBindingGenerator extends AbstractLeveringBasisBindingGenerator {

    @Override
    public void genereer(final GeneratorConfiguratie generatorConfiguratie) {
        final Binding historieObjectTypenBinding = genereerBindingVoorHistorieObjecttypen();
        final Binding bindingLeveringObjecttypen = genereerBindingVoorObjecttypen();

        jibxWriterFactory(generatorConfiguratie).
                marshallXmlEnSchrijfWeg(JibxBinding.BIJHOUDING_BEVRAGING_OBJECTTYPEN, bindingLeveringObjecttypen, this);
        jibxWriterFactory(generatorConfiguratie).
                marshallXmlEnSchrijfWeg(JibxBinding.BIJHOUDING_BEVRAGING_HIS_OBJECTTYPEN, historieObjectTypenBinding, this);

    }

    @Override
    protected boolean genereerMappingVoorVerantwoordingVanPersoon() {
        return false;
    }

    @Override
    protected void voegIncludeToeVoorHistorieMappings(final Binding binding) {
        voegJibxBindingIncludeToe(binding, JibxBinding.HISTORIE);
    }

    @Override
    protected String getMappingTypeNameVoorMaterieleHistorie() {
        return MAPPING_TYPE_NAME_MATERIELEHISTORIE;
    }

    @Override
    protected String getMappingTypeNameVoorFormeleHistorie() {
        return MAPPING_TYPE_NAME_FORMELEHISTORIE;
    }

    @Override
    protected void voegChoicesToeVoorXsdAttributenOnderGroepen(final Structure struct, final Groep groep) {
        /**
         * Standaard groep: bovenliggende objecttype heeft A laag tabel dus objectsleutel attribuut nodig..
         * Identiteit groep: bovenliggende objecttype heeft A laag tabel dus objectsleutel attribuut nodig.
         * Subtype: hebben meestal een aparte groep.
         */
        if ((isStandaardGroep(groep) || isIdentiteitGroep(groep))
            && !isSubtype(groep.getObjectType())
            && !"Relatie".equals(groep.getObjectType().getIdentCode()))
        {
            // Voor de objectsleutel moeten we terug navigeren naar het A-Laag object. (back reference)
            final Structure.Choice choice =
                                    maakStructureStructureChoice(null,
                                                                 "get" + groep.getObjectType().getIdentCode(),
                                                                 null, null, false);
            //Zet het type naar de implementatie zodat jibx alle getters kan vinden.
            choice.getStructure().getProperty().setType(
                    hisVolledigModelNaamgevingStrategie.getJavaTypeVoorElement(
                            groep.getObjectType()).getFullyQualifiedClassName());

            voegChoiceToeVoorXsdAttribuut(choice.getStructure(), ATTR_OBJECTSLEUTEL, "getID", true, null, null);

            struct.getChoiceList().add(choice);


        }

        //Voorkomen sleutel:
        voegChoiceToeVoorXsdAttribuut(struct, ATTR_VOORKOMENSLEUTEL, "getID", true, null, null);
    }

    @Override
    protected void voegChoicesToeVoorXsdAttributenOnderObjecttypen(final Mapping objectTypeMapping) {
        voegChoiceToeVoorXsdAttribuut(objectTypeMapping, "objectSleutel", "getObjectSleutel", false, null, null);
    }

    @Override
    protected String getTypeNameVoorPersoonMapping() {
        return "Objecttype_PersoonDetails_Levering";
    }

    @Override
    protected String getTestMethodVoorAttributen() {
        return null;
    }

    @Override
    protected String getClassNaamVanIndicatieMarshaller() {
        return "nl.bzk.brp.model.binding.HisPersoonIndicatieMapperBevraging";
    }

    @Override
    protected String getTestMethodVoorInverseAssociatiesVanPersoon(final Attribuut invAttribuut) {
        return "heeft" + GeneratieUtil.upperTheFirstCharacter(invAttribuut.getInverseAssociatieIdentCode());
    }
}
