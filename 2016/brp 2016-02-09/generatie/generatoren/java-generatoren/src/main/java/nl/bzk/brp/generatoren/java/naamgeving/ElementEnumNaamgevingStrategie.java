/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.naamgeving;

import nl.bzk.brp.generatoren.algemeen.common.BmrElementSoort;
import nl.bzk.brp.generatoren.java.util.GeneratiePackage;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;
import nl.bzk.brp.metaregister.model.Tuple;

/**
 *
 */
public class ElementEnumNaamgevingStrategie extends AbstractModelSpecifiekeNaamgevingStrategie {

    private final BmrElementSoort bmrElementSoort;

    public ElementEnumNaamgevingStrategie(final BmrElementSoort bmrElementSoort) {
        this.bmrElementSoort = bmrElementSoort;
    }

    @Override
    protected GeneratiePackage getModelSpecifiekePackageVoorObjectTypes() {
        return GeneratiePackage.STAMGEGEVEN_DYNAMISCH_PACKAGE;
    }

    @Override
    protected GeneratiePackage getModelSpecifiekePackageVoorGroepen() {
        throw new UnsupportedOperationException("We verwachten hier alleen het element 'Element'..");
    }

    @Override
    protected GeneratiePackage getModelSpecifiekePackageVoorTuples() {
        throw new UnsupportedOperationException("We verwachten hier alleen het element 'Element'..");
    }

    @Override
    protected PrefixSuffix getModelSpecifiekePrefixSuffixVoorObjectType(
        final ObjectType element)
    {
        final String prefix;
        if (BmrElementSoort.OBJECTTYPE.equals(bmrElementSoort)) {
            prefix = "ObjectType";
        } else if (BmrElementSoort.GROEP.equals(bmrElementSoort)) {
            prefix = "Groep";
        } else if (BmrElementSoort.ATTRIBUUT.equals(bmrElementSoort)) {
            prefix = "Attribuut";
        } else {
            throw new UnsupportedOperationException("Onbekend element soort.");
        }

        return new PrefixSuffix(prefix, "");
    }

    @Override
    protected PrefixSuffix getModelSpecifiekePrefixSuffixVoorGroep(
        final Groep element)
    {
        throw new UnsupportedOperationException("We verwachten hier alleen het element 'Element'..");
    }

    @Override
    protected PrefixSuffix getModelSpecifiekePrefixSuffixVoorTuple(
        final Tuple element)
    {
        throw new UnsupportedOperationException("We verwachten hier alleen het element 'Element'..");
    }

}
