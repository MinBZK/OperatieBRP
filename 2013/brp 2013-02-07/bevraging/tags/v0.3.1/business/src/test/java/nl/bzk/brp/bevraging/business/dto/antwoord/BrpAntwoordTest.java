/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.dto.antwoord;

import static nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFoutCode.ONBEKENDE_FOUT;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import nl.bzk.brp.domein.DomeinObjectFactory;
import nl.bzk.brp.domein.PersistentDomeinObjectFactory;
import nl.bzk.brp.domein.kern.Persoon;
import nl.bzk.brp.domein.kern.SoortPersoon;

import org.junit.Test;


/**
 * Unit test class voor de {@link AbstractBerichtAntwoord} class.
 */
public class BrpAntwoordTest {

    private DomeinObjectFactory factory = new PersistentDomeinObjectFactory();

    /**
     * Unit test voor de {@link AbstractBerichtAntwoord#voegFoutToe(BerichtVerwerkingsFout)} methode.
     */
    @Test
    public void testFouten() {
        BerichtAntwoord antwoord = genereerBasisAbstractAntwoord();

        assertEquals(0, antwoord.getFouten().size());

        antwoord.voegFoutToe(new BerichtVerwerkingsFout(ONBEKENDE_FOUT, BerichtVerwerkingsFoutZwaarte.INFO, "Test1"));
        antwoord.voegFoutToe(new BerichtVerwerkingsFout(ONBEKENDE_FOUT, BerichtVerwerkingsFoutZwaarte.FOUT, "Test2"));

        assertEquals(2, antwoord.getFouten().size());
    }

    @Test
    public void testFoutenVerwijdertContentNietVoorInfoFoutZwaarte() {
        BerichtAntwoord antwoord = genereerBasisAbstractAntwoord();
        Persoon persoon = factory.createPersoon();
        persoon.setSoort(SoortPersoon.INGESCHREVENE);
        antwoord.getPersonen().add(persoon);
        assertEquals(1, antwoord.getPersonen().size());
        assertEquals(0, antwoord.getFouten().size());

        antwoord.voegFoutToe(new BerichtVerwerkingsFout(ONBEKENDE_FOUT, BerichtVerwerkingsFoutZwaarte.INFO));
        assertEquals(1, antwoord.getPersonen().size());
        assertEquals(1, antwoord.getFouten().size());
    }

    @Test
    public void testFoutenVerwijdertContentNietVoorWaarschuwingFoutZwaarte() {
        BerichtAntwoord antwoord = genereerBasisAbstractAntwoord();
        Persoon persoon = factory.createPersoon();
        persoon.setSoort(SoortPersoon.INGESCHREVENE);
        antwoord.getPersonen().add(persoon);
        assertEquals(1, antwoord.getPersonen().size());
        assertEquals(0, antwoord.getFouten().size());

        antwoord.voegFoutToe(new BerichtVerwerkingsFout(ONBEKENDE_FOUT, BerichtVerwerkingsFoutZwaarte.WAARSCHUWING));
        assertEquals(1, antwoord.getPersonen().size());
        assertEquals(1, antwoord.getFouten().size());
    }

    @Test
    public void testFoutenVerwijdertContentWelVoorFoutFoutZwaarte() {
        BerichtAntwoord antwoord = genereerBasisAbstractAntwoord();
        Persoon persoon = factory.createPersoon();
        persoon.setSoort(SoortPersoon.INGESCHREVENE);
        antwoord.getPersonen().add(persoon);
        assertEquals(1, antwoord.getPersonen().size());
        assertEquals(0, antwoord.getFouten().size());

        antwoord.voegFoutToe(new BerichtVerwerkingsFout(ONBEKENDE_FOUT, BerichtVerwerkingsFoutZwaarte.FOUT));
        assertEquals(0, antwoord.getPersonen().size());
        assertEquals(1, antwoord.getFouten().size());
    }

    @Test
    public void testFoutenVerwijdertContentWelVoorSysteemFoutZwaarte() {
        BerichtAntwoord antwoord = genereerBasisAbstractAntwoord();
        Persoon persoon = factory.createPersoon();
        persoon.setSoort(SoortPersoon.INGESCHREVENE);
        antwoord.getPersonen().add(persoon);
        assertEquals(1, antwoord.getPersonen().size());
        assertEquals(0, antwoord.getFouten().size());

        antwoord.voegFoutToe(new BerichtVerwerkingsFout(ONBEKENDE_FOUT, BerichtVerwerkingsFoutZwaarte.SYSTEEM));
        assertEquals(0, antwoord.getPersonen().size());
        assertEquals(1, antwoord.getFouten().size());
    }

    /**
     * Genereert een standaard implementatie instantie van de {@link AbstractBerichtAntwoord} class.
     *
     * @return een standaard BRP antwoord.
     */
    private AbstractBerichtAntwoord genereerBasisAbstractAntwoord() {
        AbstractBerichtAntwoord antwoord = new AbstractBerichtAntwoord() {

            private final Collection<Persoon> personen = new ArrayList<Persoon>();

            @Override
            public Collection<Persoon> getPersonen() {
                return personen;
            }

            @Override
            public void wisContent() {
                personen.clear();
            }

        };
        return antwoord;
    }

}
