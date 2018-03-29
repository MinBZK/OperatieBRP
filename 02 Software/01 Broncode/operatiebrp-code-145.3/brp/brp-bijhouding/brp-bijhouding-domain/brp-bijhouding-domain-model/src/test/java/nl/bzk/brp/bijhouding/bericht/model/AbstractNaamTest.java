/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.mockito.Mockito.when;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Voorvoegsel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.VoorvoegselSleutel;
import org.junit.Before;

/**
 * Abstract test class voor alle unittesten die te maken hebben met naam gegevens.
 */
public abstract class AbstractNaamTest extends AbstractElementTest {
    private static final String VOORVOEGSEL = "van";
    private static final String ONGELDIG_VOORVOEGSEL = "bla";
    private static final Character SCHEIDINGSTEKEN = ' ';
    static final StringElement VOORVOEGSEL_ELEMENT = new StringElement(VOORVOEGSEL);
    static final StringElement ONGELDIG_STRING_ELEMENT = new StringElement(ONGELDIG_VOORVOEGSEL);
    static final CharacterElement SCHEIDINGSTEKEN_ELEMENT = new CharacterElement(SCHEIDINGSTEKEN);
    static final StringElement PREDICAAT_ELEMENT = new StringElement("K");
    static final StringElement ADELIJKE_TITEL_ELEMENT = new StringElement("B");
    static final StringElement VOORNAMEN_ELEMENT = new StringElement("voornaam");
    static final StringElement GESLACHTSNAAMSTAM_ELEMENT = new StringElement("geslachtsnaamstam");

    @Before
    public void setupAbstractNaamTest() {
        final VoorvoegselSleutel sleutel = new VoorvoegselSleutel(SCHEIDINGSTEKEN, VOORVOEGSEL);
        when(getDynamischeStamtabelRepository().getVoorvoegselByVoorvoegselSleutel(sleutel)).thenReturn(new Voorvoegsel(sleutel));
        when(
            getDynamischeStamtabelRepository().getVoorvoegselByVoorvoegselSleutel(new VoorvoegselSleutel(SCHEIDINGSTEKEN, ONGELDIG_VOORVOEGSEL)))
                                                                                                                                                 .thenReturn(
                                                                                                                                                     null);
    }
}
