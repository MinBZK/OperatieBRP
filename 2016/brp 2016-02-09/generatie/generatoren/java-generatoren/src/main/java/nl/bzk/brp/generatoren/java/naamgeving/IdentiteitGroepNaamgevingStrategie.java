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
 * Een naamgevingstrategie die gebruikt wordt voor identiteitsgroepnamen samen te stellen.
 *
 */
public class IdentiteitGroepNaamgevingStrategie extends AbstractModelSpecifiekeNaamgevingStrategie{
    @Override
    protected GeneratiePackage getModelSpecifiekePackageVoorObjectTypes() {
        return GeneratiePackage.OBJECTTYPE_LOGISCH_PACKAGE;
    }

    @Override
    protected GeneratiePackage getModelSpecifiekePackageVoorGroepen() {
        return GeneratiePackage.GROEP_LOGISCH_PACKAGE;
    }

    @Override
    protected GeneratiePackage getModelSpecifiekePackageVoorTuples() {
        throw new UnsupportedOperationException("Komt niet voor in de IdentiteitGroepNaamgevingStrategie");
    }

    @Override
    protected PrefixSuffix getModelSpecifiekePrefixSuffixVoorObjectType(final ObjectType element) {
        throw new UnsupportedOperationException("Komt niet voor in de IdentiteitGroepNaamgevingStrategie");
    }

    @Override
    protected PrefixSuffix getModelSpecifiekePrefixSuffixVoorGroep(final Groep element) {
        return new PrefixSuffix("His" + element.getObjectType().getIdentCode(), "Groep");
    }

    @Override
    protected PrefixSuffix getModelSpecifiekePrefixSuffixVoorTuple(final Tuple element) {
        throw new UnsupportedOperationException("Komt niet voor in de IdentiteitGroepNaamgevingStrategie");
    }
}
