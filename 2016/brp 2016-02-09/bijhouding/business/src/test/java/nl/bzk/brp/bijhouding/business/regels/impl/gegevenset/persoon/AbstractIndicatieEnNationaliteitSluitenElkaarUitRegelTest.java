/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon;

import static java.util.Arrays.asList;
import static nl.bzk.brp.util.StatischeObjecttypeBuilder.NATIONALITEIT_NEDERLANDS;
import static nl.bzk.brp.util.StatischeObjecttypeBuilder.NATIONALITEIT_SLOWAAKS;

import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIndicatieBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIndicatieStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonNationaliteitHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonNationaliteitHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test voor de bedrijfsregels rond uitsluiting van indicaties en nationaliteit.
 */
public abstract class AbstractIndicatieEnNationaliteitSluitenElkaarUitRegelTest {

    private final AbstractIndicatieEnNationaliteitSluitenElkaarUitRegel regel = getRegelKlasse();

    protected abstract AbstractIndicatieEnNationaliteitSluitenElkaarUitRegel getRegelKlasse();

    protected abstract Regel getVerwachtteRegel();

    protected abstract SoortIndicatie getIndicatie();

    @Test
    public void testMetNederlandseNationaliteitEnNieuweSituatieMetIndicatie() {
        final PersoonView bestaandeSituatie = bouwHuidigePersoonMetNationaliteit(NATIONALITEIT_NEDERLANDS.getWaarde());
        final PersoonBericht nieuweSituatie = bouwNieuweSituatieMetIndicatie();

        final List<BerichtEntiteit> berichtEntiteiten = regel.voerRegelUit(bestaandeSituatie, nieuweSituatie);
        Assert.assertEquals(1, berichtEntiteiten.size());
        Assert.assertEquals(nieuweSituatie.getIndicaties().iterator().next(), berichtEntiteiten.get(0));
    }

    @Test
    public void testMetVreemdeNationaliteitEnNieuweSituatieMetIndicatie() {
        final PersoonView bestaandeSituatie = bouwHuidigePersoonMetNationaliteit(NATIONALITEIT_SLOWAAKS.getWaarde());
        final PersoonBericht nieuweSituatie = bouwNieuweSituatieMetIndicatie();

        final List<BerichtEntiteit> berichtEntiteiten = regel.voerRegelUit(bestaandeSituatie, nieuweSituatie);
        if (regel.heeftTeCheckenNationaliteit(NATIONALITEIT_SLOWAAKS.getWaarde().getCode())) {
            Assert.assertEquals(1, berichtEntiteiten.size());
        } else {
            Assert.assertEquals(0, berichtEntiteiten.size());
        }
    }

    @Test
    public void testMetVreemdeNationaliteitEnNieuweSituatieZonderIndicatie() {
        final PersoonView bestaandeSituatie = bouwHuidigePersoonMetNationaliteit(NATIONALITEIT_SLOWAAKS.getWaarde());
        final PersoonBericht nieuweSituatie = bouwNieuweSituatieZonderIndicatie();

        final List<BerichtEntiteit> berichtEntiteiten = regel.voerRegelUit(bestaandeSituatie, nieuweSituatie);
        Assert.assertEquals(0, berichtEntiteiten.size());
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(getVerwachtteRegel(), regel.getRegel());
    }

    private PersoonBericht bouwNieuweSituatieZonderIndicatie() {
        return bouwNieuweSituatie(false);
    }
    private PersoonBericht bouwNieuweSituatieMetIndicatie() {
        return bouwNieuweSituatie(true);
    }

    private PersoonBericht bouwNieuweSituatie(final boolean voegIndicatieToe) {
        final PersoonBericht bericht = new PersoonBericht();

        if (voegIndicatieToe) {
            final PersoonIndicatieBericht indicatie = new PersoonIndicatieBericht(
                new SoortIndicatieAttribuut(getIndicatie()));
            final PersoonIndicatieStandaardGroepBericht standaardGroepBericht = new PersoonIndicatieStandaardGroepBericht();
            standaardGroepBericht.setWaarde(new JaAttribuut(Ja.J));
            indicatie.setStandaard(standaardGroepBericht);

            bericht.setIndicaties(asList(indicatie));
        }

        return bericht;
    }

    private PersoonView bouwHuidigePersoonMetNationaliteit(final Nationaliteit... nations) {
        final PersoonHisVolledigImpl persoonHisVolledig =
                new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();

        for (final Nationaliteit nation : nations) {
            final PersoonNationaliteitHisVolledigImpl persoonNation =
                    new PersoonNationaliteitHisVolledigImplBuilder(persoonHisVolledig, nation).
                            nieuwStandaardRecord(DatumAttribuut.vandaag().getWaarde(), null, DatumAttribuut.vandaag().getWaarde())
                            .eindeRecord().build();

            persoonHisVolledig.getNationaliteiten().add(persoonNation);
        }

        return new PersoonView(persoonHisVolledig);
    }
}
