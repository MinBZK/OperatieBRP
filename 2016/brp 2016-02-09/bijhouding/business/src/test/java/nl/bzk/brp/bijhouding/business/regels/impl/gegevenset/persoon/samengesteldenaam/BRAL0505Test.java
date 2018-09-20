/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.samengesteldenaam;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoorvoegselAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.FamilierechtelijkeBetrekkingView;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonSamengesteldeNaamModel;
import nl.bzk.brp.util.RelatieTestUtil;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Unit test klasse voor de regel {@link nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.samengesteldenaam.BRAL0505}.
 */
public class BRAL0505Test {


    @Test
    public void testHeeftNamenReeksEnGeenVoorvoegsel() {
        final FamilierechtelijkeBetrekkingView familierechtelijkeBetrekking =
                bouwFamilierechtelijkeBetrekking(true, null);

        final List<BerichtEntiteit> berichtEntiteits =
                new BRAL0505().voerRegelUit(familierechtelijkeBetrekking, bouwFamilierechtelijkBetrekkingBericht());

        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testHeeftNamenReeksEnVoorvoegsel() {
        final FamilierechtelijkeBetrekkingView familierechtelijkeBetrekking =
                bouwFamilierechtelijkeBetrekking(true, "po");

        final List<BerichtEntiteit> berichtEntiteits =
                new BRAL0505().voerRegelUit(familierechtelijkeBetrekking, bouwFamilierechtelijkBetrekkingBericht());

        Assert.assertEquals(1, berichtEntiteits.size());
        Assert.assertEquals("99", berichtEntiteits.get(0).getObjectSleutel());
    }

    @Test
    public void testHeeftGeenNamenReeksEnVoorvoegsel() {
        final FamilierechtelijkeBetrekkingView familierechtelijkeBetrekking =
                bouwFamilierechtelijkeBetrekking(false, "po");

        final List<BerichtEntiteit> berichtEntiteits =
                new BRAL0505().voerRegelUit(familierechtelijkeBetrekking, bouwFamilierechtelijkBetrekkingBericht());

        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    private FamilierechtelijkeBetrekkingBericht bouwFamilierechtelijkBetrekkingBericht()
    {
        final FamilierechtelijkeBetrekkingBericht relatie = new FamilierechtelijkeBetrekkingBericht();
        final PersoonBericht persoon = new PersoonBericht();
        persoon.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        persoon.setObjectSleutel("99");

        final BetrokkenheidBericht kindBericht = new KindBericht();
        kindBericht.setRelatie(relatie);
        kindBericht.setPersoon(persoon);
        relatie.setBetrokkenheden(Arrays.asList(kindBericht));

        return relatie;
    }

    private FamilierechtelijkeBetrekkingView bouwFamilierechtelijkeBetrekking(final boolean indNamenReeks, final String voorvoegsel) {
        final ActieModel actieModel =
            new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), null, null,
                           new DatumEvtDeelsOnbekendAttribuut(
                               20120101), null, new DatumTijdAttribuut(new Date()), null);

        final PersoonHisVolledigImpl ouder = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));


        final PersoonHisVolledigImpl kind = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwSamengesteldeNaamRecord(20120101, null, 20120101)
                .indicatieNamenreeks(indNamenReeks)
                .eindeRecord()
                .build();

        if (StringUtils.isNotBlank(voorvoegsel)) {
            final HisPersoonSamengesteldeNaamModel actueleRecord =
                    kind.getPersoonSamengesteldeNaamHistorie().getActueleRecord();
            ReflectionTestUtils.setField(actueleRecord, "voorvoegsel", new VoorvoegselAttribuut(voorvoegsel));
        }

        RelatieTestUtil.bouwFamilieRechtelijkeBetrekking(ouder, null, kind, 20120101, actieModel);

        return new FamilierechtelijkeBetrekkingView(
                RelatieTestUtil.haalFamilieRechtelijkeBetrekkingUitPersoonBetrokkenhedenWaarPersoonKindInIs(kind),
                DatumTijdAttribuut.nu(),
                DatumAttribuut.vandaag()
        );
    }

}
