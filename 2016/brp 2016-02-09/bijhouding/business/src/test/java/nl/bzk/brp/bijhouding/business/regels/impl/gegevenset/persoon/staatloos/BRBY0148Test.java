/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.staatloos;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIndicatieBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieDerdeHeeftGezagHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieStaatloosHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.hisvolledig.kern.PersoonIndicatieDerdeHeeftGezagHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonIndicatieStaatloosHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Unit test voor de {@link BRBY0148} bedrijfsregel.
 */
public class BRBY0148Test {

    private BRBY0148 brby0148;

    @Before
    public void init() {
        brby0148 = new BRBY0148();
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0148, brby0148.getRegel());
    }

    @Test
    public void testHuidigeSituatieNullZonderRegistratieIndicaties() {
        final List<BerichtEntiteit> berichtEntiteiten = brby0148.voerRegelUit(null, null, null, null);
        Assert.assertEquals(0, berichtEntiteiten.size());
    }

    @Test
    public void testPersoonMetIndicatieGezagDerdeZonderRegistratieIndicaties() {
        final PersoonView huidigePersoon = maakHuidigePersoon(SoortIndicatie.INDICATIE_STAATLOOS);
        final PersoonBericht persoonBericht = maakNieuweSituatie(null);
        final List<BerichtEntiteit> berichtEntiteiten =
            brby0148.voerRegelUit(huidigePersoon, persoonBericht, null, null);
        Assert.assertEquals(0, berichtEntiteiten.size());
    }

    @Test
    public void testPersoonZonderIndicatieStaatsloosZonderRegistratieIndicatieStaatsloos() {
        final PersoonView huidigePersoon = maakHuidigePersoon(null);
        final PersoonBericht persoonBericht = maakNieuweSituatie(null);
        persoonBericht.setIndicaties(null);
        final List<BerichtEntiteit> berichtEntiteiten =
            brby0148.voerRegelUit(huidigePersoon, persoonBericht, null, null);
        Assert.assertEquals(0, berichtEntiteiten.size());
    }

    @Test
    public void testPersoonMetIndicatieStaatsloosMetRegistratieIndicatieGezagDerde() {
        final PersoonView huidigePersoon = maakHuidigePersoon(SoortIndicatie.INDICATIE_STAATLOOS);
        final PersoonBericht persoonBericht = maakNieuweSituatie(SoortIndicatie.INDICATIE_DERDE_HEEFT_GEZAG);
        final List<BerichtEntiteit> berichtEntiteiten =
            brby0148.voerRegelUit(huidigePersoon, persoonBericht, null, null);
        Assert.assertEquals(0, berichtEntiteiten.size());
    }

    @Test
    public void testPersoonMetIndicatieGezagDerdeMetRegistratieIndicatieStaatsloos() {
        final PersoonView huidigePersoon = maakHuidigePersoon(SoortIndicatie.INDICATIE_DERDE_HEEFT_GEZAG);
        final PersoonBericht persoonBericht = maakNieuweSituatie(SoortIndicatie.INDICATIE_STAATLOOS);
        final List<BerichtEntiteit> berichtEntiteiten =
            brby0148.voerRegelUit(huidigePersoon, persoonBericht, null, null);
        Assert.assertEquals(0, berichtEntiteiten.size());
    }

    @Test
    public void testPersoonMetIndicatieStaatsloosMetRegistratieIndicatieStaatsloos() {
        final PersoonView huidigePersoon = maakHuidigePersoon(SoortIndicatie.INDICATIE_STAATLOOS);
        final PersoonBericht persoonBericht = maakNieuweSituatie(SoortIndicatie.INDICATIE_STAATLOOS);
        final List<BerichtEntiteit> berichtEntiteiten =
            brby0148.voerRegelUit(huidigePersoon, persoonBericht, null, null);
        Assert.assertEquals(1, berichtEntiteiten.size());
    }

    private PersoonBericht maakNieuweSituatie(final SoortIndicatie indicatieSoort) {
        PersoonBericht persoonBericht = new PersoonBericht();
        if (indicatieSoort != null) {
            persoonBericht.setIndicaties(new ArrayList<PersoonIndicatieBericht>());
            PersoonIndicatieBericht indicatieStaatsloos =
                new PersoonIndicatieBericht(new SoortIndicatieAttribuut(indicatieSoort));
            persoonBericht.getIndicaties().add(indicatieStaatsloos);
        }
        return persoonBericht;
    }

    private PersoonHisVolledigImpl maakHuidigePersoonHisVolledig(final SoortIndicatie indicatieSoort) {
        PersoonHisVolledigImpl persoon =
            new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));

        if (indicatieSoort != null) {
            if (indicatieSoort == SoortIndicatie.INDICATIE_DERDE_HEEFT_GEZAG) {
                PersoonIndicatieDerdeHeeftGezagHisVolledigImpl indicatie =
                        new PersoonIndicatieDerdeHeeftGezagHisVolledigImplBuilder()
                                .nieuwStandaardRecord(20110101, null, 20110101).waarde(Ja.J).eindeRecord().build();
                persoon.setIndicatieDerdeHeeftGezag(indicatie);
            } else {
                PersoonIndicatieStaatloosHisVolledigImpl indicatie =
                        new PersoonIndicatieStaatloosHisVolledigImplBuilder()
                                .nieuwStandaardRecord(20110101, null, 20110101).waarde(Ja.J).eindeRecord().build();
                persoon.setIndicatieStaatloos(indicatie);
            }
        }

        return persoon;
    }

    private PersoonView maakHuidigePersoon(final SoortIndicatie indicatieSoort) {
        return new PersoonView(maakHuidigePersoonHisVolledig(indicatieSoort));
    }

}
