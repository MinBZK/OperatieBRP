/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.migratie;

import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebied;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocument;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocumentAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortMigratie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortMigratieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonMigratieGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.operationeel.kern.ActieBronModel;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.DocumentModel;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class BRBY0593Test {

    private final LandGebied nederland = new LandGebied(LandGebiedCodeAttribuut.NEDERLAND, null, null, null, null);
    private       LandGebied onbekend  = new LandGebied(LandGebiedCodeAttribuut.ONBEKEND, null, null, null, null);

    private static final SoortDocument MINISTERIEEL_BESLUIT =
        new SoortDocument(NaamEnumeratiewaardeAttribuut.DOCUMENT_NAAM_MINISTERIEEL_BESLUIT, null, null);
    private static final SoortDocument VOW                  =
        new SoortDocument(NaamEnumeratiewaardeAttribuut.DOCUMENT_NAAM_AMBTSHALVE_BESLUIT_VERBLIJFPLAATS, null, null);


    @Test
    public void testVoerRegelUitLandMigratieNietOpgegevenEnVerantwoordingsDocumentVOW() {
        PersoonHisVolledigImpl persoon = maakPersoon();
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setMigratie(new PersoonMigratieGroepBericht());
        persoonBericht.getMigratie().setSoortMigratie(new SoortMigratieAttribuut(SoortMigratie.EMIGRATIE));
        final List<BerichtEntiteit> berichtEntiteits =
            new BRBY0593().voerRegelUit(new PersoonView(persoon), persoonBericht, maakActie(VOW), null);

        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testVoerRegelUitLandMigratieNietOpgegevenEnVerantwoordingsDocumentGeenVOW() {
        PersoonHisVolledigImpl persoon = maakPersoon();
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setMigratie(new PersoonMigratieGroepBericht());
        persoonBericht.getMigratie().setSoortMigratie(new SoortMigratieAttribuut(SoortMigratie.EMIGRATIE));
        final List<BerichtEntiteit> berichtEntiteits =
            new BRBY0593().voerRegelUit(new PersoonView(persoon), persoonBericht, maakActie(MINISTERIEEL_BESLUIT), null);

        Assert.assertEquals(persoonBericht, berichtEntiteits.get(0));
    }

    @Test
    public void testVoerRegelUitLandMigratieIsNederlandEnVerantwoordingsDocumentVOW() {
        PersoonHisVolledigImpl persoon = maakPersoon();
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setMigratie(new PersoonMigratieGroepBericht());
        persoonBericht.getMigratie().setSoortMigratie(new SoortMigratieAttribuut(SoortMigratie.EMIGRATIE));
        persoonBericht.getMigratie().setLandGebiedMigratie(new LandGebiedAttribuut(nederland));

        final List<BerichtEntiteit> berichtEntiteits =
            new BRBY0593().voerRegelUit(new PersoonView(persoon), persoonBericht, maakActie(VOW), null);

        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testVoerRegelUitLandMigratieIsNederlandEnVerantwoordingsDocumentGeenVOW() {
        PersoonHisVolledigImpl persoon = maakPersoon();
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setMigratie(new PersoonMigratieGroepBericht());
        persoonBericht.getMigratie().setSoortMigratie(new SoortMigratieAttribuut(SoortMigratie.EMIGRATIE));
        persoonBericht.getMigratie().setLandGebiedMigratie(new LandGebiedAttribuut(nederland));

        final List<BerichtEntiteit> berichtEntiteits =
                new BRBY0593().voerRegelUit(new PersoonView(persoon), persoonBericht, maakActie(MINISTERIEEL_BESLUIT), null);

        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testVoerRegelUitLandMigratieNietOpgegevenEnVerantwoordingsDocumentNietOpgegeven() {
        PersoonHisVolledigImpl persoon = maakPersoon();
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setMigratie(new PersoonMigratieGroepBericht());
        persoonBericht.getMigratie().setSoortMigratie(new SoortMigratieAttribuut(SoortMigratie.EMIGRATIE));
        persoonBericht.getMigratie().setLandGebiedMigratie(null);

        final List<BerichtEntiteit> berichtEntiteits =
                new BRBY0593().voerRegelUit(new PersoonView(persoon), persoonBericht, maakActie(null), null);

        Assert.assertEquals(persoonBericht, berichtEntiteits.get(0));
    }

    @Test
    public void testVoerRegelUitLandMigratieOnbekendEnVerantwoordingsDocumentNietOpgegeven() {
        PersoonHisVolledigImpl persoon = maakPersoon();
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setMigratie(new PersoonMigratieGroepBericht());
        persoonBericht.getMigratie().setSoortMigratie(new SoortMigratieAttribuut(SoortMigratie.EMIGRATIE));
        persoonBericht.getMigratie().setLandGebiedMigratie(new LandGebiedAttribuut(onbekend));

        final List<BerichtEntiteit> berichtEntiteits =
                new BRBY0593().voerRegelUit(new PersoonView(persoon), persoonBericht, maakActie(null), null);

        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0593, new BRBY0593().getRegel());
    }

    private PersoonHisVolledigImpl maakPersoon() {
        return new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
    }

    private ActieModel maakActie(final SoortDocument verantwoordingDocument) {
        final ActieModel actieModel = new ActieModel(
                new SoortActieAttribuut(SoortActie.REGISTRATIE_MIGRATIE),
                new AdministratieveHandelingModel(
                        new SoortAdministratieveHandelingAttribuut(SoortAdministratieveHandeling.VERHUIZING_NAAR_BUITENLAND),
                        null,
                        null,
                        null),
                null,
                new DatumEvtDeelsOnbekendAttribuut(20140101),
                null,
                DatumTijdAttribuut.nu(), null);

        if (verantwoordingDocument != null) {
            DocumentModel documentModel = new DocumentModel(new SoortDocumentAttribuut(verantwoordingDocument));
            ActieBronModel actieBronModel = new ActieBronModel(actieModel, documentModel, null, null);
            actieModel.getBronnen().add(actieBronModel);
        }
        return actieModel;
    }
}
