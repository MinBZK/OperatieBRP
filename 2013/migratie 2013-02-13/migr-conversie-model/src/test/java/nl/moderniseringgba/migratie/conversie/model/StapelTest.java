/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model;

import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;

import org.junit.Test;

/**

 * 
 */
public class StapelTest {

    @Test(expected = NullPointerException.class)
    public void testNullpointer() {
        new Lo3Stapel<Lo3NationaliteitInhoud>(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgument() {
        final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorien =
                new ArrayList<Lo3Categorie<Lo3NationaliteitInhoud>>();
        new Lo3Stapel<Lo3NationaliteitInhoud>(categorien);
    }
}
