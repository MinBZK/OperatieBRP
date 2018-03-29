/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.bepaling;

import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtParameters;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtPersoonInformatie;
import org.junit.Assert;
import org.junit.Test;

/**
 */
public class MutatieleveringDeltabepalingServiceImplTest {

    private MutatieleveringDeltabepalingServiceImpl service = new MutatieleveringDeltabepalingServiceImpl();

    /**
     * Record in delta want 1 in (1)
     */
    @Test
    public void testActieinhoudInDelta() {

        final AdministratieveHandeling administratieveHandeling = TestVerantwoording.maakAdministratievehandelingMetActies(1);
        final Actie actieInhoud = administratieveHandeling.getActie(1);
        final Actie actieVerval = administratieveHandeling.getActie(2);
        final Actie actieAanpassingGeldigheid = administratieveHandeling.getActie(3);
        final Actie actieVervalTbvLevermutaties = administratieveHandeling.getActie(4);

        //@formatter:off
        final MetaObject persoon = TestBuilders.maakLeegPersoon()
            .metObject()
                .metObjectElement(Element.PERSOON_ADRES.getId())
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_ADRES_STANDAARD))
                    .metRecord()
                        .metId(100)
                        .metActieInhoud(actieInhoud)
                    .eindeRecord()
                    .metRecord()
                        .metId(200)
                        .metActieVerval(actieVerval)
                    .eindeRecord()
                    .metRecord()
                        .metId(300)
                        .metActieInhoud(actieInhoud)
                        .metActieAanpassingGeldigheid(actieAanpassingGeldigheid)
                    .eindeRecord()
                    .metRecord()
                        .metId(400)
                        .metActieVervalTbvLeveringMutaties(actieVervalTbvLevermutaties)
                    .eindeRecord()
                    .metRecord()
                        .metId(500)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
        .eindeObject().build();
        //@formatter:on

        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 0L);
        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();

        maakBerichtParameters.setAdministratieveHandelingId(administratieveHandeling.getId());

        final Set<MetaGroep> groepen = persoonslijst.getModelIndex().geefGroepenVanElement(getGroepElement(Element.PERSOON_ADRES_STANDAARD));
        final Map<Long, MetaRecord> recordMap = getRecordMap(groepen);

        final Set<MetaRecord> deltaRecords = service.execute(persoonslijst);

        Assert.assertTrue(deltaRecords.contains(recordMap.get(100L)));
        Assert.assertTrue(deltaRecords.contains(recordMap.get(200L)));
        Assert.assertTrue(deltaRecords.contains(recordMap.get(300L)));
        Assert.assertTrue(deltaRecords.contains(recordMap.get(400L)));
        Assert.assertFalse(deltaRecords.contains(recordMap.get(500L)));
    }

    /**
     * Record zit niet in delta want 1 niet in (2)
     */
    @Test
    public void testActieinhoudNietInDelta() {

        final AdministratieveHandeling administratieveHandeling = TestVerantwoording.maakAdministratievehandelingMetActies(1);
        final Actie actieInhoud = administratieveHandeling.getActie(1);
        final Actie actieVerval = administratieveHandeling.getActie(2);
        final Actie actieAanpassingGeldigheid = administratieveHandeling.getActie(3);
        final Actie actieVervalTbvLevermutaties = administratieveHandeling.getActie(4);

        //@formatter:off
        final MetaObject persoon = TestBuilders.maakLeegPersoon()
            .metObject()
                .metObjectElement(Element.PERSOON_ADRES.getId())
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_ADRES_STANDAARD))
                    .metRecord()
                        .metId(100)
                        .metActieInhoud(actieInhoud)
                    .eindeRecord()
                    .metRecord()
                        .metId(200)
                        .metActieInhoud(actieInhoud)
                        .metActieVerval(actieVerval)
                    .eindeRecord()
                    .metRecord()
                        .metId(300)
                        .metActieInhoud(actieInhoud)
                        .metActieAanpassingGeldigheid(actieAanpassingGeldigheid)
                    .eindeRecord()
                    .metRecord()
                        .metId(400)
                        .metActieInhoud(actieInhoud)
                        .metActieVervalTbvLeveringMutaties(actieVervalTbvLevermutaties)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
        .eindeObject().build();
        //@formatter:on
        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 0L);
        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();

        final AdministratieveHandeling administratieveHandeling2 = TestVerantwoording.maakAdministratievehandelingMetActies(2222);
        maakBerichtParameters.setAdministratieveHandelingId(administratieveHandeling2.getId());
        final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, persoonslijst,
                new MaakBerichtPersoonInformatie(SoortSynchronisatie.MUTATIE_BERICHT), null, new StatischePersoongegevens());
        final Set<MetaGroep> groepen = persoonslijst.getModelIndex().geefGroepenVanElement(getGroepElement(Element.PERSOON_ADRES_STANDAARD));
        final Map<Long, MetaRecord> recordMap = getRecordMap(groepen);
        for (MetaRecord record : recordMap.values()) {
            berichtgegevens.autoriseer(record);
        }

        final Set<MetaRecord> deltaRecords = service.execute(persoonslijst);

        Assert.assertTrue(deltaRecords.contains(recordMap.get(100L)));
        Assert.assertTrue(deltaRecords.contains(recordMap.get(200L)));
        Assert.assertTrue(deltaRecords.contains(recordMap.get(300L)));
        Assert.assertTrue(deltaRecords.contains(recordMap.get(400L)));
    }


    private Map<Long, MetaRecord> getRecordMap(Set<MetaGroep> groepen) {
        final Map<Long, MetaRecord> map = Maps.newHashMap();
        for (MetaGroep groep : groepen) {
            for (MetaRecord metaRecord : groep.getRecords()) {
                map.put(metaRecord.getVoorkomensleutel(), metaRecord);
            }
        }
        return map;
    }
}
