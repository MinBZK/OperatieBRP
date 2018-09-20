/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.blobifier.serializatie;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.isOneOf;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;
import javax.inject.Inject;
import nl.bzk.brp.blobifier.repository.alleenlezen.HisPersTabelRepository;
import nl.bzk.brp.blobifier.repository.lezenenschrijven.SchrijfPersoonCacheRepository;
import nl.bzk.brp.dataaccess.test.AbstractDBUnitIntegratieTest;
import nl.bzk.brp.dataaccess.test.Data;
import nl.bzk.brp.model.hisvolledig.impl.kern.AdministratieveHandelingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonNationaliteitHisVolledig;
import nl.bzk.brp.serialisatie.persoon.PersoonHisVolledigSerializer;
import nl.bzk.brp.serialisatie.persoon.PersoonHisVolledigSmileSerializer;
import org.junit.Test;

/**
 * Tests voor {@link nl.bzk.brp.serialisatie.persoon.PersoonHisVolledigSmileSerializer}.
 */
@Data(resources = { "classpath:/data/stamgegevensStatisch.xml",
        "classpath:/data/blob/dataset.xml",
        "classpath:/data/blob/cleanup.xml",
        "classpath:/data/pre-conv-clean.xml" })
public class PersoonHisVolledigSmileSerializerIntegratieTest extends AbstractDBUnitIntegratieTest {
    @Inject
    private PersoonHisVolledigSerializer serializer;

    @Inject
    private HisPersTabelRepository repository;

    @Inject
    private SchrijfPersoonCacheRepository cacheRepository;

    @Test
    public void testOfSmileSerializerStandaardGebruiktWordt() {
        assertThat(serializer, instanceOf(PersoonHisVolledigSmileSerializer.class));
    }

    @Test
    public void serializatieRoundtripIsSuccesvol() {
        // given
        final PersoonHisVolledigImpl persoon = repository.leesGenormalizeerdModelVoorInMemoryBlob(2);

        final List<AdministratieveHandelingHisVolledigImpl> handelingen = cacheRepository.haalVerantwoordingOp(2);
        persoon.getAdministratieveHandelingen().addAll(handelingen);

        final byte[] data = serializer.serialiseer(persoon);

        logger.info(new String(data));
        // when
        final PersoonHisVolledigImpl gelezenPersoon = serializer.deserialiseer(data);

        // then
        assertEquals("Nederlandse",
            persoon.getNationaliteiten().iterator().next().getNationaliteit().getWaarde().getNaam().getWaarde());
        assertEquals("Prins",
            persoon.getGeslachtsnaamcomponenten().iterator().next().getPersoonGeslachtsnaamcomponentHistorie().iterator().next().getStam().getWaarde());

        final String voornaam =
            gelezenPersoon.getVoornamen().iterator().next().getPersoonVoornaamHistorie().iterator().next().getNaam().getWaarde();
        assertThat(voornaam, isOneOf("Paula", "Isabella", "Hendrika", "Gerarda"));

        assertEquals(1, gelezenPersoon.getAdressen().size());
        assertEquals(5, gelezenPersoon.getAdressen().iterator().next().getPersoonAdresHistorie().getAantal());

        assertEquals(1, gelezenPersoon.getIndicaties().size());
    }

    @Test
    public void serialisatieRoundtripHeeftDezelfdeActieObjecten() {
        // given
        final PersoonHisVolledigImpl persoon = repository.leesGenormalizeerdModelVoorInMemoryBlob(13);

        final List<AdministratieveHandelingHisVolledigImpl> handelingen = cacheRepository.haalVerantwoordingOp(13);
        persoon.getAdministratieveHandelingen().addAll(handelingen);

        final byte[] data = serializer.serialiseer(persoon);

        logger.info(new String(data));
        // when
        final PersoonHisVolledigImpl gelezenPersoon = serializer.deserialiseer(data);

        // then
        assertEquals(3, gelezenPersoon.getAdministratieveHandelingen().size());
        // == (id van referentie) klopt op 1 actie
        assertEquals(gelezenPersoon.getAdministratieveHandelingen().get(2).getActies().iterator().next().getID(),
            gelezenPersoon.getPersoonGeboorteHistorie().getActueleRecord().getVerantwoordingInhoud().getID());
    }

    @Test
    public void serializatieBevatActies() {
        final PersoonHisVolledigImpl persoon = repository.leesGenormalizeerdModelVoorInMemoryBlob(12);
        final byte[] data = serializer.serialiseer(persoon);

        // when
        final PersoonHisVolledig gelezenPersoon = serializer.deserialiseer(data);

        // then
        final PersoonNationaliteitHisVolledig nation = gelezenPersoon.getNationaliteiten().iterator().next();
        assertThat(nation.getPersoonNationaliteitHistorie().iterator().next().getVerantwoordingInhoud(), not(nullValue()));
    }

    @Test
    public void serializatieVanHibernateProxyInstanties() {
        final PersoonHisVolledigImpl persoon = repository.leesGenormalizeerdModelVoorInMemoryBlob(9);

        final FamilierechtelijkeBetrekkingHisVolledigImpl relatie =
            (FamilierechtelijkeBetrekkingHisVolledigImpl) persoon.getBetrokkenheden().iterator().next().getRelatie();

        for (final BetrokkenheidHisVolledigImpl betrokkenheidHisVolledig : relatie.getBetrokkenheden()) {
            if (!persoon.getID().equals(betrokkenheidHisVolledig.getPersoon().getID())) {

                final PersoonHisVolledigImpl betrokkene = betrokkenheidHisVolledig.getPersoon();
                final byte[] data = serializer.serialiseer(betrokkene);

                assertThat(data.length, greaterThan(200));
            }
        }
    }

    /**
     * Een nogal arbitraire test die de lengte verifieert van de geserialiseerde objecten.
     * Is de datalengte opeens groter geworden en kun je dat verklaren?: verhoog dan de verwachte lengte.
     * @throws Exception
     */
    @Test
    public void serializatieIsNaarByteArray() {
        // given
        final PersoonHisVolledigImpl persoon = repository.leesGenormalizeerdModelVoorInMemoryBlob(7);

        // when
        final byte[] data = serializer.serialiseer(persoon);

        // then
        final int verwachteJsonLengte = 14410;
        final int jsonMarge = 500;
        assertThat(data.length, lessThan(verwachteJsonLengte + jsonMarge));
    }
}
