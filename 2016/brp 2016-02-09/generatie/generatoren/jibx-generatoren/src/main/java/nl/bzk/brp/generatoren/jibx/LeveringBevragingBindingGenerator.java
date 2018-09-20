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
 * Genereert bindings voor bevraging t.b.v. leveren.
 */
@Component("leveringBevragingBindingGenerator")
public class LeveringBevragingBindingGenerator extends AbstractLeveringBasisBindingGenerator {

    @Override
    public void genereer(final GeneratorConfiguratie generatorConfiguratie) {
        final Binding historieObjectTypenBinding = genereerBindingVoorHistorieObjecttypen();
        final Binding bindingLeveringObjecttypen = genereerBindingVoorObjecttypen();

        jibxWriterFactory(generatorConfiguratie).
                marshallXmlEnSchrijfWeg(JibxBinding.LEVERING_BEVRAGING_OBJECTTYPEN, bindingLeveringObjecttypen, this);
        jibxWriterFactory(generatorConfiguratie).
                marshallXmlEnSchrijfWeg(JibxBinding.LEVERING_BEVRAGING_HIS_OBJECTTYPEN, historieObjectTypenBinding, this);
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
        //Voorkomen sleutel:
        voegChoiceToeVoorXsdAttribuut(struct, ATTR_VOORKOMENSLEUTEL, "getID", true, null, null);

        if (isGroepWaarvoorObjectAttributenGewenstZijn(groep)) {
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
    }

    @Override
    protected void voegChoicesToeVoorXsdAttributenOnderObjecttypen(final Mapping objectTypeMapping) {
        //nada
    }

    @Override
    protected String getTypeNameVoorPersoonMapping() {
        return "Objecttype_PersoonDetails_Levering";
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
