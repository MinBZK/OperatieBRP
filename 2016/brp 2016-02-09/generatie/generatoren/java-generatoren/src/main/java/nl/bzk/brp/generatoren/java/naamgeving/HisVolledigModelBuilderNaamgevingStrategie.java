/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.naamgeving;

import nl.bzk.brp.generatoren.java.util.GeneratiePackage;
import nl.bzk.brp.metaregister.model.ObjectType;

/**
 * Naamgeving strategie voor het model dat gegenereerd wordt voor his volledig.
 */
public class HisVolledigModelBuilderNaamgevingStrategie extends HisVolledigModelNaamgevingStrategie {

    @Override
    protected GeneratiePackage getModelSpecifiekePackageVoorObjectTypes() {
        return GeneratiePackage.BRP_BASEPACKAGE_UTIL_HISVOLLEDIG;
    }

    @Override
    protected PrefixSuffix getModelSpecifiekePrefixSuffixVoorObjectType(final ObjectType element) {
        PrefixSuffix prefixSuffix = super.getModelSpecifiekePrefixSuffixVoorObjectType(element);
        return new PrefixSuffix(prefixSuffix.getPrefix(), prefixSuffix.getSuffix() + "Builder");
    }

}
