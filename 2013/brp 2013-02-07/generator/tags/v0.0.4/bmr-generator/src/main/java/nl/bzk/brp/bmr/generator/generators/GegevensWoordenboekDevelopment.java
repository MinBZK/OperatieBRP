/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.generators;

import javax.inject.Inject;

import nl.bzk.brp.bmr.generator.AbstractGenerator;
import nl.bzk.brp.bmr.generator.utils.FileSystemAccess;
import nl.bzk.brp.ecore.bmr.MetaRegister;

import org.springframework.stereotype.Service;


@Service
public class GegevensWoordenboekDevelopment extends AbstractGenerator {

    @Inject
    private GegevensWoordenboek woordenboek;

    @Override
    public void generate(final MetaRegister register, final String naam, final FileSystemAccess directory) {
        woordenboek.setDoelGroep(DoelGroep.DEVELOPERS);
        woordenboek.generate(register, naam, directory);
    }
}
