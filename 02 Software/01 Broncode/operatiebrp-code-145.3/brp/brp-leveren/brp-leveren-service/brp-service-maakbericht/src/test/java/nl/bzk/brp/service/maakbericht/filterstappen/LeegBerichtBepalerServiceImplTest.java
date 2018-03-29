/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import static nl.bzk.brp.domain.element.ElementHelper.getAttribuutElement;
import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;
import static nl.bzk.brp.domain.element.ElementHelper.getObjectElement;

import com.google.common.collect.Lists;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Verwerkingssoort;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.element.ElementConstants;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaModel;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.maakbericht.AutorisatieAlles;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtParameters;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtPersoonInformatie;
import nl.bzk.brp.service.maakbericht.bepaling.StatischePersoongegevens;
import org.junit.Assert;
import org.junit.Test;

/**
 * LeegBepalerServiceImplTest.
 */
public class LeegBerichtBepalerServiceImplTest {

    private LeegBerichtBepalerServiceImpl leegBepalerService = new LeegBerichtBepalerServiceImpl();

    @Test
    public void bepaalLeegIndienGeenRecords() {
        final Berichtgegevens berichtgegevens = getBerichtgegevens(TestBuilders.LEEG_PERSOON);
        leegBepalerService.execute(berichtgegevens);
        Assert.assertTrue(berichtgegevens.isLeegBericht());
    }

    @Test
    public void bepaalLeegAlleenAfgeleidAdministratief() {
        final Map<MetaModel, Verwerkingssoort> verwerkingssoortMap = new HashMap<>();

        final MetaObject metaObject = maakPersoon();
        for (MetaGroep metaGroep : metaObject.getGroepen()) {
            metaGroep.getRecords().stream()
                    .filter(metaRecord -> Element.PERSOON_AFGELEIDADMINISTRATIEF == metaRecord.getParentGroep().getGroepElement().getElement())
                    .forEach(metaRecord -> {
                        verwerkingssoortMap.put(metaRecord, Verwerkingssoort.TOEVOEGING);
                    });
        }
        final Berichtgegevens berichtgegevens = getBerichtgegevens(metaObject);
        berichtgegevens.getStatischePersoongegevens().setVerwerkingssoortMap(verwerkingssoortMap);
        //alleen wijziging op PERSOON_AFGELEIDADMINISTRATIEF: leeg
        leegBepalerService.execute(berichtgegevens);
        Assert.assertTrue(berichtgegevens.isLeegBericht());
    }

    @Test
    public void bepaalLeegAlleenAfgeleidAdministratiefEnIdentificerend() {
        final Map<MetaModel, Verwerkingssoort> verwerkingssoortMap = new HashMap<>();

        final MetaObject metaObject = maakPersoon();
        for (MetaGroep metaGroep : metaObject.getGroepen()) {
            for (MetaRecord metaRecord : metaGroep.getRecords()) {
                if (Element.PERSOON_AFGELEIDADMINISTRATIEF == metaRecord.getParentGroep().getGroepElement().getElement()) {
                    verwerkingssoortMap.put(metaRecord, Verwerkingssoort.TOEVOEGING);
                } else {
                    verwerkingssoortMap.put(metaRecord, Verwerkingssoort.IDENTIFICATIE);
                }
            }
        }
        final Berichtgegevens berichtgegevens = getBerichtgegevens(metaObject);
        berichtgegevens.getStatischePersoongegevens().setVerwerkingssoortMap(verwerkingssoortMap);
        leegBepalerService.execute(berichtgegevens);
        Assert.assertTrue(berichtgegevens.isLeegBericht());
    }

    @Test
    public void bepaalLeegToevoeging() {
        final Map<MetaModel, Verwerkingssoort> verwerkingssoortMap = new HashMap<>();

        final MetaObject metaObject = maakPersoon();
        for (MetaGroep metaGroep : metaObject.getGroepen()) {
            for (MetaRecord metaRecord : metaGroep.getRecords()) {
                if (Element.PERSOON_AFGELEIDADMINISTRATIEF == metaRecord.getParentGroep().getGroepElement().getElement()) {
                    verwerkingssoortMap.put(metaRecord, Verwerkingssoort.TOEVOEGING);
                } else {
                    verwerkingssoortMap.put(metaRecord, Verwerkingssoort.TOEVOEGING);
                }
            }
        }
        final Berichtgegevens berichtgegevens = getBerichtgegevens(metaObject);
        new AutorisatieAlles(berichtgegevens).doVisit(metaObject);
        berichtgegevens.getStatischePersoongegevens().setVerwerkingssoortMap(verwerkingssoortMap);
        leegBepalerService.execute(berichtgegevens);
        Assert.assertFalse(berichtgegevens.isLeegBericht());
    }


    @Test
    public void bepaalLeegAlleenObjectToevoeging() {
        final Map<MetaModel, Verwerkingssoort> verwerkingssoortMap = new HashMap<>();
        final MetaObject metaObject = maakPersoon();
        final Berichtgegevens berichtgegevens = getBerichtgegevens(metaObject);
        verwerkingssoortMap.put(metaObject, Verwerkingssoort.TOEVOEGING);
        berichtgegevens.getStatischePersoongegevens().setVerwerkingssoortMap(verwerkingssoortMap);
        leegBepalerService.execute(berichtgegevens);
        Assert.assertTrue(berichtgegevens.isLeegBericht());
    }

    @Test
    public void bepalingVoorVolledigBericht() {
        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, null,
                new MaakBerichtPersoonInformatie(SoortSynchronisatie.VOLLEDIG_BERICHT), null, new StatischePersoongegevens());
        leegBepalerService.execute(berichtgegevens);
        Assert.assertTrue(berichtgegevens.isLeegBericht());
    }

    @Test
    public void bepalingVoorVolledigBerichtNietLeeg() {
        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, null,
                new MaakBerichtPersoonInformatie(SoortSynchronisatie.VOLLEDIG_BERICHT), null, new StatischePersoongegevens());
        berichtgegevens.autoriseer(maakPersoon());
        leegBepalerService.execute(berichtgegevens);
        Assert.assertFalse(berichtgegevens.isLeegBericht());
    }

    @Test
    public void bepalingVoorMutatieberichtMetMeldingVerstrekkingsbeperking() {
        final MetaObject metaObject = maakPersoon();
        final Persoonslijst pl = new Persoonslijst(metaObject, 1L);
        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, null,
                new MaakBerichtPersoonInformatie(SoortSynchronisatie.MUTATIE_BERICHT), null, new StatischePersoongegevens());
        berichtgegevens.setMutatieberichtMetMeldingVerstrekkingsbeperking(true);
        berichtgegevens.autoriseer(maakPersoon());
        final MetaRecord identificatieNrsRecord =
                pl.getModelIndex().geefGroepenVanElement(getGroepElement(Element.PERSOON_IDENTIFICATIENUMMERS)).iterator().next()
                        .getRecords().iterator().next();
        berichtgegevens.autoriseer(identificatieNrsRecord);
        leegBepalerService.execute(berichtgegevens);
        Assert.assertFalse(berichtgegevens.isLeegBericht());
    }

    @Test
    public void bepalingVoorMutatieberichtMetMeldingVerstrekkingsbeperkingGeenIdentiferendeGroepen() {
        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, null,
                new MaakBerichtPersoonInformatie(SoortSynchronisatie.MUTATIE_BERICHT), null, new StatischePersoongegevens());
        berichtgegevens.setMutatieberichtMetMeldingVerstrekkingsbeperking(true);
        berichtgegevens.autoriseer(TestBuilders.LEEG_PERSOON);
        leegBepalerService.execute(berichtgegevens);
        Assert.assertTrue(berichtgegevens.isLeegBericht());
    }

    private MetaObject maakPersoon() {
        final ZonedDateTime nu = DatumUtil.nuAlsZonedDateTime();
        final Actie actie = TestVerantwoording.maakActie(1, nu);

        final MetaGroep.Builder identificatieNummersGroepBuilder = new MetaGroep.Builder();
        identificatieNummersGroepBuilder.metGroepElement(getGroepElement(Element.PERSOON_IDENTIFICATIENUMMERS));

        final MetaRecord.Builder identificatieNummersRecordBuilder = new MetaRecord.Builder(identificatieNummersGroepBuilder)
                .metId(1).metActieInhoud(actie);
        identificatieNummersGroepBuilder.metRecords(Lists.newArrayList(identificatieNummersRecordBuilder));

        //Persoon.Afgeleidadministratief groep
        final MetaGroep.Builder afgeleidAdministratieGroepBuilder = new MetaGroep.Builder();
        afgeleidAdministratieGroepBuilder.metGroepElement(getGroepElement(Element.PERSOON_AFGELEIDADMINISTRATIEF));
        final MetaRecord.Builder afgeleidAdministratieRecordBuilder = new MetaRecord.Builder(afgeleidAdministratieGroepBuilder)
                .metId(1).metActieInhoud(actie);
        afgeleidAdministratieGroepBuilder.metRecords(Lists.newArrayList(afgeleidAdministratieRecordBuilder));

        //Persoon.Identiteit groep
        final MetaGroep.Builder persoonIdentiteitGroepBuilder = new MetaGroep.Builder();
        persoonIdentiteitGroepBuilder.metGroepElement(getGroepElement(Element.PERSOON_IDENTITEIT));
        final MetaRecord.Builder persoonIdentiteitRecordBuilder = new MetaRecord.Builder(persoonIdentiteitGroepBuilder)
                .metId(1).metAttribuut(getAttribuutElement(Element.PERSOON_SOORTCODE), SoortPersoon.INGESCHREVENE.getCode());
        persoonIdentiteitGroepBuilder.metRecords(Lists.newArrayList(persoonIdentiteitRecordBuilder));

        //GegevenInOnderzoek groep
        final MetaGroep.Builder onderzoekGroepBuilder = new MetaGroep.Builder();
        onderzoekGroepBuilder.metGroepElement(getGroepElement(Element.GEGEVENINONDERZOEK_IDENTITEIT));
        final MetaRecord.Builder metaRecordBuilderOnderzoek = new MetaRecord.Builder(onderzoekGroepBuilder)
                .metActieInhoud(actie)
                .metAttribuut(getAttribuutElement(Element.GEGEVENINONDERZOEK_ELEMENTNAAM), getAttribuutElement
                        (Element.PERSOON_GEBOORTE_DATUM).getNaam())
                .metAttribuut(getAttribuutElement(Element.GEGEVENINONDERZOEK_VOORKOMENSLEUTELGEGEVEN), 111L);
        onderzoekGroepBuilder.metRecords(Lists.newArrayList(metaRecordBuilderOnderzoek));

        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(ElementConstants.PERSOON)
            .metGroepen(Lists.newArrayList(persoonIdentiteitGroepBuilder, identificatieNummersGroepBuilder, afgeleidAdministratieGroepBuilder))
            .metObjecten(Lists.newArrayList(
                 MetaObject.maakBuilder()
                            .metObjectElement(getObjectElement(Element.ONDERZOEK))
                            .metId(2221)
                            .metGroep()
                                .metGroepElement(getGroepElement(Element.ONDERZOEK_STANDAARD))
                                .metRecord().metActieInhoud(actie).eindeRecord()
                            .eindeGroep()
                        .metObject()
                            .metId(123)
                            .metObjectElement(getObjectElement(Element.GEGEVENINONDERZOEK))
                            .metGroepen(Lists.newArrayList(onderzoekGroepBuilder))
                        .eindeObject()
            )).build();
        //@formatter:on

        return persoon;
    }

    private Berichtgegevens getBerichtgegevens(final MetaObject metaObject) {
        final Persoonslijst persoonslijst = new Persoonslijst(metaObject, 0L);
        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        return new Berichtgegevens(maakBerichtParameters, persoonslijst, new MaakBerichtPersoonInformatie(SoortSynchronisatie.MUTATIE_BERICHT),
                null, new StatischePersoongegevens());
    }
}
