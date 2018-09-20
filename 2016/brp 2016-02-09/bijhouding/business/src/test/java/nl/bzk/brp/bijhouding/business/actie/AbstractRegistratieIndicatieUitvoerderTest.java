/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.actie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.bijhouding.business.regels.Verwerkingsregel;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.model.HisVolledigRootObject;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIndicatieBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonIndicatieHisVolledig;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Een abstracte test super klasse voor alle registratie indicatie uitvoerder tests.
 */
public abstract class AbstractRegistratieIndicatieUitvoerderTest<A extends ActieBericht> {

    protected static final Integer DATUM_GEBOORTE = 20120101;
    private static final String TECHNISCHE_SLEUTEL = "123456789";
    private static final String MODEL = "model";
    private AbstractRegistratieIndicatieUitvoerder uitvoerder;
    private PersoonHisVolledigImpl persoonHisVolledig;
    private PersoonIndicatieHisVolledigImpl<?> indicatieHisVolledig;

    protected abstract AbstractRegistratieIndicatieUitvoerder maakNieuweUitvoerder();

    protected abstract A maakNieuwActieBericht();

    protected abstract SoortIndicatie getSoortIndicatie();

    protected abstract void voegNieuweIndicatieHisVolledigToe();

    protected abstract void verwijderIndicatie();

    @Before
    public void init() {
        this.uitvoerder = maakNieuweUitvoerder();

        // A
        uitvoerder.setActieBericht(maakNieuwActieBericht());

        // B
        final PersoonBericht persoonBericht = new PersoonBericht();
        final List<PersoonIndicatieBericht> indicaties = new ArrayList<>();
        final PersoonIndicatieBericht indicatie = new PersoonIndicatieBericht(new SoortIndicatieAttribuut(getSoortIndicatie()));
        indicaties.add(indicatie);
        persoonBericht.setIndicaties(indicaties);
        persoonBericht.setObjectSleutel(TECHNISCHE_SLEUTEL);
        ReflectionTestUtils.setField(uitvoerder, "berichtRootObject", persoonBericht);

        // M
        this.persoonHisVolledig = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        this.voegNieuweIndicatieHisVolledigToe();
        this.indicatieHisVolledig = this.persoonHisVolledig.getIndicaties().iterator().next();

        ReflectionTestUtils.setField(uitvoerder, "modelRootObject", persoonHisVolledig);

        // Context
        final BijhoudingBerichtContext berichtContext = new BijhoudingBerichtContext(new BerichtenIds(1L, 2L), null, null, null);
        ReflectionTestUtils.setField(uitvoerder, "context", berichtContext);

        final Map<String, HisVolledigRootObject> bestaandeBijhoudingsRootObjecten =
                new HashMap<String, HisVolledigRootObject>();
        bestaandeBijhoudingsRootObjecten.put(TECHNISCHE_SLEUTEL, persoonHisVolledig);
        ReflectionTestUtils.setField(berichtContext, "bestaandeBijhoudingsRootObjecten", bestaandeBijhoudingsRootObjecten);
    }

    public PersoonHisVolledigImpl getPersoon() {
        return persoonHisVolledig;
    }

    @Test
    public void testVerzamelVerwerkingsregels() throws Exception {
        Assert.assertEquals(0, getVerwerkingsregels().size());

        uitvoerder.verzamelVerwerkingsregels();

        Assert.assertEquals(1, getVerwerkingsregels().size());
    }

    @Test
    public void testBepaalPersoonIndicatieHisVolledigBestaand() {
        uitvoerder.verzamelVerwerkingsregels();
        final PersoonIndicatieHisVolledig<?> gebruikteHisVolledig = (PersoonIndicatieHisVolledig<?>) ReflectionTestUtils.getField(
                getVerwerkingsregels().get(0), MODEL);
        Assert.assertEquals(this.indicatieHisVolledig, gebruikteHisVolledig);
    }

    @Test
    public void testBepaalPersoonIndicatieHisVolledigNieuw() {
        this.verwijderIndicatie();
        uitvoerder.verzamelVerwerkingsregels();
        final PersoonIndicatieHisVolledig<?> gebruikteHisVolledig = (PersoonIndicatieHisVolledig<?>) ReflectionTestUtils.getField(
                getVerwerkingsregels().get(0), MODEL);
        Assert.assertNotEquals(this.indicatieHisVolledig, gebruikteHisVolledig);
    }

    @SuppressWarnings("unchecked")
    private List<Verwerkingsregel> getVerwerkingsregels() {
        return (List<Verwerkingsregel>) ReflectionTestUtils.getField(uitvoerder, "verwerkingsregels");
    }

}
