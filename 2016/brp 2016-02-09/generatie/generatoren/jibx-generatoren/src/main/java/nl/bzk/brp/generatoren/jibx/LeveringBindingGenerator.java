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
 * Genereert bindings voor Levering.
 */
@Component("leveringBindingGenerator")
public class LeveringBindingGenerator extends AbstractLeveringBasisBindingGenerator {

    private static final String ATTR_VERWERKINSSOORT = "verwerkingssoort";
    private static final String GETTER_VERWERKINGSSOORT = "getVerwerkingssoort";
    private static final String ENUM_VALUE_METHOD_VERWERKINGSSOORT = "getVasteWaarde";

    @Override
    public void genereer(final GeneratorConfiguratie generatorConfiguratie) {
        final Binding historieObjectTypenBinding = genereerBindingVoorHistorieObjecttypen();
        final Binding bindingLeveringObjecttypen = genereerBindingVoorObjecttypen();

        jibxWriterFactory(generatorConfiguratie).
                marshallXmlEnSchrijfWeg(JibxBinding.LEVERING_HIS_OBJECTTYPEN, historieObjectTypenBinding, this);
        jibxWriterFactory(generatorConfiguratie).
                marshallXmlEnSchrijfWeg(JibxBinding.LEVERING_OBJECTTYPEN, bindingLeveringObjecttypen, this);
    }

    @Override
    protected boolean genereerMappingVoorVerantwoordingVanPersoon() {
        return true;
    }

    @Override
    protected void voegIncludeToeVoorHistorieMappings(final Binding binding) {
        voegJibxBindingIncludeToe(binding, JibxBinding.HISTORIE_MET_VERANTWOORDING);
    }

    @Override
    protected String getMappingTypeNameVoorMaterieleHistorie() {
        return MAPPING_TYPE_NAME_MATERIELEHISTORIE_MET_VERANTWOORDING;
    }

    @Override
    protected String getMappingTypeNameVoorFormeleHistorie() {
        return MAPPING_TYPE_NAME_FORMELEHISTORIE_MET_VERANTWOORDING;
    }

    @Override
    protected void voegChoicesToeVoorXsdAttributenOnderGroepen(final Structure struct, final Groep groep) {
        voegChoiceToeVoorXsdAttribuut(struct, ATTR_VERWERKINSSOORT, GETTER_VERWERKINGSSOORT, true,
                                      null, ENUM_VALUE_METHOD_VERWERKINGSSOORT);
        if (!"Betrokkenheid".equals(groep.getObjectType().getNaam())) {
            voegChoiceToeVoorXsdAttribuut(struct, ATTR_VOORKOMENSLEUTEL, "getID", false, null, null);
            if (isGroepWaarvoorObjectAttributenGewenstZijn(groep))
            {
                voegChoiceToeVoorXsdAttribuut(struct, ATTR_OBJECTSLEUTEL, "getObjectSleutel", false, null, null);
            }
        }
    }

    @Override
    protected void voegChoicesToeVoorXsdAttributenOnderObjecttypen(final Mapping objectTypeMapping) {
        voegChoiceToeVoorXsdAttribuut(objectTypeMapping, ATTR_VERWERKINSSOORT, GETTER_VERWERKINGSSOORT, true, null,
                ENUM_VALUE_METHOD_VERWERKINGSSOORT);
    }

    @Override
    protected String getTypeNameVoorPersoonMapping() {
        return "Objecttype_DetailsPersoon_Synchronisatie";
    }

    @Override
    protected String getTestMethodVoorAttributen() {
        return LEVEREN_MAG_GELEVERD_WORDEN_FUNCTIE;
    }

    @Override
    protected String getClassNaamVanIndicatieMarshaller() {
        return "nl.bzk.brp.model.binding.HisPersoonIndicatieMapperLevering";
    }

    @Override
    protected String getTestMethodVoorInverseAssociatiesVanPersoon(final Attribuut invAttribuut) {
        //Zie ook: LeveringDeltaViewModelGenerator.genereerTestMethodFunctiesVoorInverseAssociatiesVoorLeveren().
        return "heeft" + GeneratieUtil.upperTheFirstCharacter(invAttribuut.getInverseAssociatieIdentCode())
                        + "VoorLeveren";
    }
}
