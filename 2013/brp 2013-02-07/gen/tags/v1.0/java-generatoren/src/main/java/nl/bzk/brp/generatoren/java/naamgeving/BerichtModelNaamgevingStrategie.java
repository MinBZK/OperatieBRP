/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.naamgeving;

import nl.bzk.brp.generatoren.java.util.GeneratiePackage;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;
import nl.bzk.brp.metaregister.model.Tuple;

/**
 * Implementatie van de {@link NaamgevingStrategie} interface voor het berichten model; alle elementen/classes die
 * tot het berichten model behoren.
 */
public class BerichtModelNaamgevingStrategie extends AbstractModelSpecifiekeNaamgevingStrategie {

    /** {@inheritDoc} */
    @Override
    protected GeneratiePackage getModelSpecifiekePackageVoorObjectTypes() {
        return GeneratiePackage.OBJECTTYPE_BERICHT_PACKAGE;
    }

    /** {@inheritDoc} */
    @Override
    protected GeneratiePackage getModelSpecifiekePackageVoorGroepen() {
        return GeneratiePackage.GROEP_BERICHT_PACKAGE;
    }

    @Override
    protected GeneratiePackage getModelSpecifiekePackageVoorTuples() {
        return GeneratiePackage.OBJECTTYPE_BERICHT_PACKAGE;
    }

    /** {@inheritDoc} */
    @Override
    protected PrefixSuffix getModelSpecifiekePrefixSuffixVoorObjectType(final ObjectType element) {
        return new PrefixSuffix("", "Bericht");
    }

    /** {@inheritDoc} */
    @Override
    protected PrefixSuffix getModelSpecifiekePrefixSuffixVoorGroep(final Groep element) {
        return new PrefixSuffix(element.getObjectType().getIdentCode(), "GroepBericht");
    }

    @Override
    protected PrefixSuffix getModelSpecifiekePrefixSuffixVoorTuple(final Tuple element) {
        return new PrefixSuffix("", "Bericht");
    }

}
