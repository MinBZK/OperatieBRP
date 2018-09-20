/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.metaregister.consistency.test;

import java.util.List;

import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;
import nl.bzk.brp.metaregister.model.Tuple;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/consistency-context.xml" })
@TransactionConfiguration
@Transactional
public class DiscriminatorTupleTest extends ConsistencyTest {

    @Test
    //TODO: test code heeft veel overlap met generatoren algemeen, mergen in metaregister dataaccess project?
    public void testDiscriminators() {
        for (ObjectType objectType : this.getBmrDao().getObjectTypen()) {
            // Als er een discriminator attribuut ingesteld is.
            if (StringUtils.isNotBlank(objectType.getDiscriminatorAttribuut())) {
                Attribuut discriminatorAttribuut = null;
                final String discriminatorAttribuutString = objectType.getDiscriminatorAttribuut();
                for (Groep groep : this.getBmrDao().getGroepenVoorObjectType(objectType)) {
                    // Het moet een attribuut van de identiteit groep zijn.
                    if (IDENTITEIT.equals(groep.getNaam())) {
                        final List<Attribuut> attributen = this.getBmrDao().getAttributenVanGroep(groep);
                        for (Attribuut attribuut : attributen) {
                            // Attributen worden gematched op naam
                            if (discriminatorAttribuutString.equals(attribuut.getNaam())) {
                                discriminatorAttribuut = attribuut;
                                break;
                            }
                        }
                    }
                }

                // Er moet een discriminator attribuut gevonden zijn.
                Assert.assertNotNull(discriminatorAttribuut);

                final ObjectType discriminatorObjectType =
                        getBmrDao().getElement(discriminatorAttribuut.getType().getId(), ObjectType.class);

                // Er moeten tuples aanwezig zijn voor dit object type.
                Assert.assertFalse(discriminatorObjectType.getTuples().isEmpty());

                // Als dit een hierarchisch 'blaadje' is, moet er een tuple hiervoor bestaan.
                if (objectType.getSuperType() != null && objectType.getSubtypen().isEmpty()) {
                    Tuple discriminatorTuple = null;
                    for (Tuple tuple : discriminatorObjectType.getTuples()) {
                        // Tuples worden gematched op ident code.
                        if (tuple.getIdentCode().equals(objectType.getIdentCode())) {
                            discriminatorTuple = tuple;
                            break;
                        }
                    }

                    // Er moet een discriminator tuple gevonden zijn.
                    Assert.assertNotNull(discriminatorTuple);
                }
            }
        }
    }

}
