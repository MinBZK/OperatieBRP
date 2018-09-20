/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.synchronisatie.stappen.stamgegeven;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import nl.bzk.brp.levering.dataaccess.repository.alleenlezen.StamTabelRepository;
import nl.bzk.brp.levering.synchronisatie.dto.synchronisatie.SynchronisatieBerichtContext;
import nl.bzk.brp.levering.synchronisatie.dto.synchronisatie.SynchronisatieResultaat;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.StamgegevenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AbstractElement;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Bijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.basis.SynchroniseerbaarStamgegeven;
import nl.bzk.brp.model.bericht.ber.BerichtParametersGroepBericht;
import nl.bzk.brp.model.synchronisatie.GeefSynchronisatieStamgegevenBericht;
import nl.bzk.brp.model.validatie.Melding;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.util.ReflectionUtils;

/**
 *
 */
public class BerichtVerwerkingsStapTest {

    @Mock
    private StamTabelRepository stamTabelRepository;

    @InjectMocks
    private BerichtVerwerkingsStap berichtVerwerkingsStap;

    private SynchronisatieBerichtContext context;

    @Before
    public final void init() {
        context = Mockito.mock(SynchronisatieBerichtContext.class);
        berichtVerwerkingsStap = new BerichtVerwerkingsStap();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public final void testOpgevraagdeStamgegevenAttrIsNull() {
        final GeefSynchronisatieStamgegevenBericht bericht = new GeefSynchronisatieStamgegevenBericht();
        bericht.setParameters(new BerichtParametersGroepBericht());

        berichtVerwerkingsStap.voerStapUit(bericht, context, new SynchronisatieResultaat(new ArrayList<Melding>()));

        //Geen nullpointer...
    }

    @Test
    public final void testOpvragenEnumeratieStamgegeven() {
        final GeefSynchronisatieStamgegevenBericht bericht = new GeefSynchronisatieStamgegevenBericht();
        bericht.setParameters(new BerichtParametersGroepBericht());
        bericht.getParameters().setStamgegeven(new StamgegevenAttribuut("BijhoudingsaardTabel"));

        final SynchronisatieResultaat resultaat = new SynchronisatieResultaat(new ArrayList<Melding>());
        berichtVerwerkingsStap.voerStapUit(bericht, context, resultaat);

        Assert.assertFalse(resultaat.getStamgegeven().isEmpty());
        Assert.assertEquals(Bijhoudingsaard.class, resultaat.getStamgegeven().get(0).getClass());
    }

    @Test
    public final void testOpvragenElementStamgegeven() throws NoSuchFieldException {
        final GeefSynchronisatieStamgegevenBericht bericht = new GeefSynchronisatieStamgegevenBericht();
        bericht.setParameters(new BerichtParametersGroepBericht());
        bericht.getParameters().setStamgegeven(new StamgegevenAttribuut("ElementTabel"));

        final SynchronisatieResultaat resultaat = new SynchronisatieResultaat(new ArrayList<Melding>());
        final Field leverendAlsStamgegevenField = AbstractElement.class.getDeclaredField("leverenAlsStamgegeven");
        leverendAlsStamgegevenField.setAccessible(true);
        final Field volgnummerField = AbstractElement.class.getDeclaredField("volgnummer");
        volgnummerField.setAccessible(true);

        final Answer<List<? extends SynchroniseerbaarStamgegeven>> partijen =
            new Answer<List<? extends SynchroniseerbaarStamgegeven>>() {
                @Override
                public List<? extends SynchroniseerbaarStamgegeven> answer(final InvocationOnMock invocation) {
                    final List<Element> partijen = new LinkedList<>();

                    final Element nietLeveren = new MyElement();
                    partijen.add(nietLeveren);

                    final Element e1 = new MyElement();
                    ReflectionUtils.setField(leverendAlsStamgegevenField, e1, new JaAttribuut(Ja.J));
                    ReflectionUtils.setField(volgnummerField, e1, new VolgnummerAttribuut(13));
                    partijen.add(e1);

                    final Element e2 = new MyElement();
                    ReflectionUtils.setField(leverendAlsStamgegevenField, e2, new JaAttribuut(Ja.J));
                    ReflectionUtils.setField(volgnummerField, e2, new VolgnummerAttribuut(5));
                    partijen.add(e2);

                    final Element e3 = new MyElement();
                    ReflectionUtils.setField(leverendAlsStamgegevenField, e3, new JaAttribuut(Ja.J));
                    ReflectionUtils.setField(volgnummerField, e3, new VolgnummerAttribuut(8));
                    partijen.add(e3);
                    return partijen;
                }
            };
        Mockito.when(stamTabelRepository.vindAlleStamgegevens(Element.class)).thenAnswer(partijen);
        berichtVerwerkingsStap.voerStapUit(bericht, context, resultaat);

        Assert.assertEquals(3, resultaat.getStamgegeven().size());
        Assert.assertEquals(5, ((Element) resultaat.getStamgegeven().get(0)).getVolgnummer().getWaarde().intValue());
        Assert.assertEquals(8, ((Element) resultaat.getStamgegeven().get(1)).getVolgnummer().getWaarde().intValue());
        Assert.assertEquals(13, ((Element) resultaat.getStamgegeven().get(2)).getVolgnummer().getWaarde().intValue());
    }

    @Test
    public final void testOpvragenDatabaseStamgegeven() {
        final GeefSynchronisatieStamgegevenBericht bericht = new GeefSynchronisatieStamgegevenBericht();
        bericht.setParameters(new BerichtParametersGroepBericht());
        bericht.getParameters().setStamgegeven(new StamgegevenAttribuut("PartijTabel"));

        final SynchronisatieResultaat resultaat = new SynchronisatieResultaat(new ArrayList<Melding>());
        berichtVerwerkingsStap.voerStapUit(bericht, context, resultaat);

        final Answer<List<? extends SynchroniseerbaarStamgegeven>> partijen =
            new Answer<List<? extends SynchroniseerbaarStamgegeven>>() {
                @Override
                public List<? extends SynchroniseerbaarStamgegeven> answer(final InvocationOnMock invocation) {
                    final List<Partij> partijen = new ArrayList<>();
                    partijen.add(TestPartijBuilder.maker().maak());
                    return partijen;
                }
            };
        Mockito.when(stamTabelRepository.vindAlleStamgegevens(Partij.class)).thenAnswer(partijen);

        berichtVerwerkingsStap.voerStapUit(bericht, context, resultaat);

        Assert.assertFalse(resultaat.getStamgegeven().isEmpty());
    }

    private class MyElement extends Element {
    }
}
