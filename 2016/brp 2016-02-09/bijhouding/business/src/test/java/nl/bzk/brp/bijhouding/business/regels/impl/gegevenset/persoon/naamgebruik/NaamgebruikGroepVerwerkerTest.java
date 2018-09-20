/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.naamgebruik;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GeslachtsnaamstamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornamenAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNaamgebruikGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class NaamgebruikGroepVerwerkerTest {

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.VR00009, new NaamgebruikGroepVerwerker(null, null, null).getRegel());
    }

    @Test
    public void testVerwerking() {
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setNaamgebruik(new PersoonNaamgebruikGroepBericht());
        persoonBericht.getNaamgebruik().setGeslachtsnaamstamNaamgebruik(new GeslachtsnaamstamAttribuut("foo"));
        persoonBericht.getNaamgebruik().setScheidingstekenNaamgebruik(new ScheidingstekenAttribuut("*"));
        persoonBericht.getNaamgebruik().setVoornamenNaamgebruik(new VoornamenAttribuut("bar"));

        PersoonHisVolledigImpl persoonHisVolledig =
                new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .build();

        ActieModel actie = new ActieModel(new SoortActieAttribuut(SoortActie.REGISTRATIE_NAAMGEBRUIK),
                null, null, new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()), null, DatumTijdAttribuut.nu(), null);


        Assert.assertTrue(persoonHisVolledig.getPersoonNaamgebruikHistorie().isLeeg());

        new NaamgebruikGroepVerwerker(persoonBericht, persoonHisVolledig, actie).neemBerichtDataOverInModel();

        Assert.assertEquals(1, persoonHisVolledig.getPersoonNaamgebruikHistorie().getAantal());
        Assert.assertEquals(new GeslachtsnaamstamAttribuut("foo"),
               persoonHisVolledig.getPersoonNaamgebruikHistorie().getActueleRecord().getGeslachtsnaamstamNaamgebruik());
    }

    @Test
    public void testVerzamelAfleidingsregels() {
        final NaamgebruikGroepVerwerker groepVerwerker =
                new NaamgebruikGroepVerwerker(null, null, null);
        groepVerwerker.verzamelAfleidingsregels();

        Assert.assertEquals(1, groepVerwerker.getAfleidingsregels().size());
        Assert.assertTrue(groepVerwerker.getAfleidingsregels().get(0) instanceof NaamgebruikAfleiding);
    }
}
