/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.algemeen.service;

import java.util.Set;
import nl.bzk.brp.levering.algemeen.service.impl.ToekomstigeActieServiceImpl;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OntleningstoelichtingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.bericht.kern.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonAdresHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAdresModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAfgeleidAdministratiefModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeboorteModel;

import nl.bzk.brp.util.VerantwoordingTestUtil;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonAntwoordPersoon;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class ToekomstigeActieServiceTest {

    private final ToekomstigeActieService toekomstigeActieService = new ToekomstigeActieServiceImpl();

    @Test
    public void testGeefToekomstigeActieIdsFormeleGroep() {
        // Maak een test persoon
        final PersoonHisVolledigImpl persoonHisVolledig = TestPersoonAntwoordPersoon.maakAntwoordPersoon();
        final AdministratieveHandelingModel huidigeHandeling = persoonHisVolledig.getPersoonAfgeleidAdministratiefHistorie().getActueleRecord()
            .getAdministratieveHandeling();

        // Er zijn nog geen toekomstige administratieve handelingen / acties
        final Set<Long> actieIdsVoor = toekomstigeActieService.geefToekomstigeActieIds(huidigeHandeling, persoonHisVolledig);
        Assert.assertEquals(0, actieIdsVoor.size());

        // Maak toekomstige administratieve handeling / actie
        pasGeboorteGroepAan(persoonHisVolledig, DatumTijdAttribuut.over24Uur());

        final Set<Long> actieIdsNa = toekomstigeActieService.geefToekomstigeActieIds(huidigeHandeling, persoonHisVolledig);
        Assert.assertEquals(1, actieIdsNa.size());
    }

    @Test
    public void testGeefToekomstigeActieIdsMaterieleGroep() {
        // Maak een test persoon
        final PersoonHisVolledigImpl persoonHisVolledig = TestPersoonAntwoordPersoon.maakAntwoordPersoon();
        final PersoonAdresHisVolledigImpl adresHisVolledig = persoonHisVolledig.getAdressen().iterator().next();
        final AdministratieveHandelingModel huidigeHandeling = persoonHisVolledig.getPersoonAfgeleidAdministratiefHistorie().getActueleRecord()
            .getAdministratieveHandeling();

        // De persoon heeft nu 1 adres record.
        Assert.assertEquals(1, adresHisVolledig.getPersoonAdresHistorie().getAantal());

        // Er zijn nog geen toekomstige administratieve handelingen / acties
        final Set<Long> actieIdsVoor = toekomstigeActieService.geefToekomstigeActieIds(huidigeHandeling, persoonHisVolledig);
        Assert.assertEquals(0, actieIdsVoor.size());

        // Maak toekomstige administratieve handeling / actie
        pasAdresGroepAan(persoonHisVolledig, adresHisVolledig, DatumTijdAttribuut.over24Uur());

        // De persoon heeft nu 3 adres records.
        Assert.assertEquals(3, adresHisVolledig.getPersoonAdresHistorie().getAantal());

        final Set<Long> actieIdsNa = toekomstigeActieService.geefToekomstigeActieIds(huidigeHandeling, persoonHisVolledig);
        Assert.assertEquals(1, actieIdsNa.size());
    }

    @Test
    public void testGeefToekomstigeActieIdsPlusHuidigeHandeling() {
        // Maak een test persoon
        final PersoonHisVolledigImpl persoonHisVolledig = TestPersoonAntwoordPersoon.maakAntwoordPersoon();
        final PersoonAdresHisVolledigImpl adresHisVolledig = persoonHisVolledig.getAdressen().iterator().next();

        // Maak toekomstige administratieve handeling / actie
        pasGeboorteGroepAan(persoonHisVolledig, DatumTijdAttribuut.terug24Uur());

        final AdministratieveHandelingModel huidigeHandeling = persoonHisVolledig.getPersoonAfgeleidAdministratiefHistorie().getActueleRecord()
            .getAdministratieveHandeling();

        final Set<Long> actieIdsVoor = toekomstigeActieService.geefToekomstigeActieIdsPlusHuidigeHandeling(huidigeHandeling, persoonHisVolledig);
        Assert.assertEquals(1, actieIdsVoor.size());

        // Maak toekomstige administratieve handeling / actie
        pasAdresGroepAan(persoonHisVolledig, adresHisVolledig, DatumTijdAttribuut.over24Uur());

        final Set<Long> actieIdsNa = toekomstigeActieService.geefToekomstigeActieIdsPlusHuidigeHandeling(huidigeHandeling, persoonHisVolledig);
        Assert.assertEquals(2, actieIdsNa.size());
    }

    @Test
    public void geeftToekomstigeIdsPlusHuidigeVanEersteHandeling() {
        // Maak een test persoon
        final PersoonHisVolledigImpl persoonHisVolledig = TestPersoonAntwoordPersoon.maakAntwoordPersoon();
        final PersoonAdresHisVolledigImpl adresHisVolledig = persoonHisVolledig.getAdressen().iterator().next();
        final AdministratieveHandelingModel eersteHandeling = persoonHisVolledig.getPersoonAfgeleidAdministratiefHistorie().getActueleRecord().getAdministratieveHandeling();

        // Maak toekomstige administratieve handeling / actie
        pasAdresGroepAan(persoonHisVolledig, adresHisVolledig, DatumTijdAttribuut.over24Uur());

        final Set<Long> actieIdsVoor = toekomstigeActieService.geefToekomstigeActieIdsPlusHuidigeHandeling(eersteHandeling, persoonHisVolledig);
        Assert.assertEquals(2, actieIdsVoor.size());
    }

    @Test
    public void testGeefToekomstigeActieIdsPlusHuidigeHandelingVoorEersteAdministratieveHandeling() {
        // Maak een test persoon
        final PersoonHisVolledigImpl persoonHisVolledig = TestPersoonAntwoordPersoon.maakAntwoordPersoon();
        final AdministratieveHandelingModel huidigeHandeling = persoonHisVolledig.getPersoonAfgeleidAdministratiefHistorie().getActueleRecord()
            .getAdministratieveHandeling();

        // Er zijn nog geen toekomstige administratieve handelingen / acties
        final Set<Long> actieIds = toekomstigeActieService.geefToekomstigeActieIdsPlusHuidigeHandeling(huidigeHandeling, persoonHisVolledig);
        Assert.assertEquals(1, actieIds.size());
    }

    /**
     * Past de adres groep aan, dit is een groep met materiele historie.
     *
     * @param persoonHisVolledig de persoon his volledig
     * @param adresHisVolledig   de adres his volledig
     */
    private void pasAdresGroepAan(final PersoonHisVolledigImpl persoonHisVolledig, final PersoonAdresHisVolledigImpl adresHisVolledig,
        final DatumTijdAttribuut tijdstipRegistratie)
    {
        // Pas adres groep aan
        final AdministratieveHandelingModel administratieveHandeling = VerantwoordingTestUtil
            .bouwAdministratieveHandeling(SoortAdministratieveHandeling.VERHUIZING_INTERGEMEENTELIJK, null, new OntleningstoelichtingAttribuut("Ontleend"
                    + " via een papiertje."),
                tijdstipRegistratie);
        zetID(administratieveHandeling, 200L);
        final ActieModel actieVerhuizing = new ActieModel(
            null, administratieveHandeling, null, new DatumEvtDeelsOnbekendAttribuut(20140101), null, tijdstipRegistratie, null
        );
        administratieveHandeling.getActies().add(actieVerhuizing);
        zetID(actieVerhuizing, 1001L);
        final PersoonAdresStandaardGroepBericht adresStandaardGroepBericht = new PersoonAdresStandaardGroepBericht();
        adresStandaardGroepBericht.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20140105));
        final HisPersoonAdresModel nieuwAdres = new HisPersoonAdresModel(adresHisVolledig, adresStandaardGroepBericht, adresStandaardGroepBericht,
            actieVerhuizing);
        adresHisVolledig.getPersoonAdresHistorie().voegToe(nieuwAdres);

        // Werk de afgeleid administratief bij:
        final HisPersoonAfgeleidAdministratiefModel afgeleidAdministratief
            = new HisPersoonAfgeleidAdministratiefModel(persoonHisVolledig, administratieveHandeling, administratieveHandeling.getTijdstipRegistratie(),
                                                        null, null, null, actieVerhuizing);
        zetID(afgeleidAdministratief, 300);
        persoonHisVolledig.getPersoonAfgeleidAdministratiefHistorie().voegToe(afgeleidAdministratief);
    }

    /**
     * Past de geboorte groep aan, dit is een groep met formele historie.
     *
     * @param persoonHisVolledig de persoon his volledig
     */
    private void pasGeboorteGroepAan(final PersoonHisVolledigImpl persoonHisVolledig, final DatumTijdAttribuut tijdstipRegistratie) {
        // Pas de geboorte groep aan
        final AdministratieveHandelingModel administratieveHandeling = VerantwoordingTestUtil
            .bouwAdministratieveHandeling(SoortAdministratieveHandeling.CORRECTIE_AFSTAMMING, null, new OntleningstoelichtingAttribuut("Ontleend via de "
                    + "rechtbank."),
                tijdstipRegistratie);
        zetID(administratieveHandeling, 400L);
        final ActieModel actieGeboorteCorrectie = new ActieModel(
            null, administratieveHandeling, null, new DatumEvtDeelsOnbekendAttribuut(20140102), null, tijdstipRegistratie, null
        );
        administratieveHandeling.getActies().add(actieGeboorteCorrectie);
        zetID(actieGeboorteCorrectie, 2001L);
        final PersoonGeboorteGroepBericht geboorteGroepBericht = new PersoonGeboorteGroepBericht();
        final HisPersoonGeboorteModel hisPersoonGeboorteModel = new HisPersoonGeboorteModel(
            persoonHisVolledig, geboorteGroepBericht, actieGeboorteCorrectie
        );
        persoonHisVolledig.getPersoonGeboorteHistorie().voegToe(hisPersoonGeboorteModel);

        // Werk de afgeleid administratief bij:
        final HisPersoonAfgeleidAdministratiefModel afgeleidAdministratief = new HisPersoonAfgeleidAdministratiefModel(persoonHisVolledig,
            administratieveHandeling, administratieveHandeling.getTijdstipRegistratie(), null, null, null,
            actieGeboorteCorrectie);
        zetID(afgeleidAdministratief, 500);
        persoonHisVolledig.getPersoonAfgeleidAdministratiefHistorie().voegToe(afgeleidAdministratief);
    }

    private void zetID(final ModelIdentificeerbaar modelIdentificeerbaar, final Number id) {
        ReflectionTestUtils.setField(modelIdentificeerbaar, "iD", id);
    }
}
