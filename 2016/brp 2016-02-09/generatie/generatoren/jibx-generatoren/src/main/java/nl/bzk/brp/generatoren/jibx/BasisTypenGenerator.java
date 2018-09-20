/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.jibx;

import java.util.List;

import nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie;
import nl.bzk.brp.generatoren.algemeen.common.BmrTargetPlatform;
import nl.bzk.brp.generatoren.java.util.GeneratiePackage;
import nl.bzk.brp.generatoren.jibx.common.JibxBinding;
import nl.bzk.brp.generatoren.jibx.model.Binding;
import nl.bzk.brp.generatoren.jibx.model.Mapping;
import nl.bzk.brp.generatoren.jibx.util.NaamgevingUtil;
import nl.bzk.brp.metaregister.model.BasisType;

import org.springframework.stereotype.Component;


/**
 * Generator die voor elk basis type in het BMR een JiBX binding genereert.
 *
 * Aanname: de klasse 'AbstractAttribuut' met het veld 'waarde' is vanuit het BRP model beschikbaar.
 */
@Component("basisTypenJibxGenerator")
public class BasisTypenGenerator extends AbstractJibxGenerator {

    /**
     * {@inheritDoc}
     */
    @Override
    public void genereer(final GeneratorConfiguratie generatorConfiguratie) {
        List<BasisType> javaBasisTypen = this.getBmrDao().getBasisTypen(BmrTargetPlatform.JAVA);

        Binding binding = new Binding();

        for (BasisType javaBasisType : javaBasisTypen) {
            binding.getMappingList().add(this.maakMapping(javaBasisType));
        }
        this.jibxWriterFactory(generatorConfiguratie).marshallXmlEnSchrijfWeg(JibxBinding.BASIS_TYPEN, binding, this);
    }

    /**
     * Maak de mapping aan voor dit basis type.
     *
     * @param basisType het basis type
     * @return de mapping
     */
    private Mapping maakMapping(final BasisType basisType) {
        Mapping mapping = new Mapping();
        mapping.setAbstract(true);
        mapping.setTypeName(NaamgevingUtil.converteerFullyQualifiedNaamNaarIdentifier(basisType.getNaam()));
        mapping.set_Class(GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.createImportClass("AbstractAttribuut"));
        String format = null;
        String type = basisType.getNaam();

        mapping.getChoiceList().add(this.maakValueMappingChoice("text", null, "waarde", type,
                                                                null, format, null, null, false, null));
        return mapping;
    }

}
