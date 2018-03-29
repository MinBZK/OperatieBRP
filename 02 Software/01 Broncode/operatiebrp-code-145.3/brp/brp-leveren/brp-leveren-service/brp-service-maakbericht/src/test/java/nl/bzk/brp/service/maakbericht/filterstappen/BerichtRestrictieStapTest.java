/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;
import static nl.bzk.brp.domain.element.ElementHelper.getObjectElement;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.maakbericht.AutorisatieAlles;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtParameters;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtPersoonInformatie;
import nl.bzk.brp.service.maakbericht.bepaling.StatischePersoongegevens;
import org.junit.Assert;
import org.junit.Test;

/**
 * CorrigeerAutorisatieVoorZoekPersoonServiceImplTest.
 */
public class BerichtRestrictieStapTest {

    private Actie actieInhoud = TestVerantwoording.maakActie(1, DatumUtil.nuAlsZonedDateTime());

    private BerichtRestrictieStap service = new BerichtRestrictieStap();

    @Test
    public void testXsdRestrictiesFilterZoekPersoonOpAdresgegevens() {
        final Persoonslijst persoonslijst = new Persoonslijst(maakPersoon(), 0L);
        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(null, AutAutUtil.zoekDienst(TestAutorisaties.metSoortDienst(SoortDienst
                .ZOEK_PERSOON_OP_ADRESGEGEVENS), SoortDienst.ZOEK_PERSOON_OP_ADRESGEGEVENS));
        maakBerichtParameters.setAutorisatiebundels(Lists.newArrayList(autorisatiebundel));
        valideer(persoonslijst, maakBerichtParameters);

    }

    @Test
    public void testXsdRestrictiesFilterZoekPersoon() {
        final Persoonslijst persoonslijst = new Persoonslijst(maakPersoon(), 0L);
        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(null, AutAutUtil.zoekDienst(TestAutorisaties.metSoortDienst(SoortDienst
                .ZOEK_PERSOON), SoortDienst.ZOEK_PERSOON));
        maakBerichtParameters.setAutorisatiebundels(Lists.newArrayList(autorisatiebundel));
        valideer(persoonslijst, maakBerichtParameters);

    }

    @Test
    public void testXsdGeefMedebewoners() {
        final Persoonslijst persoonslijst = new Persoonslijst(maakPersoonVorGeefMedebewoners(), 0L);
        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(null, AutAutUtil.zoekDienst(TestAutorisaties.metSoortDienst(SoortDienst
                .GEEF_MEDEBEWONERS_VAN_PERSOON), SoortDienst.GEEF_MEDEBEWONERS_VAN_PERSOON));
        maakBerichtParameters.setAutorisatiebundels(Lists.newArrayList(autorisatiebundel));
        valideerGeefMedebewoners(persoonslijst, maakBerichtParameters);

    }

    private void valideerGeefMedebewoners(final Persoonslijst persoonslijst, final MaakBerichtParameters maakBerichtParameters) {
        final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, persoonslijst,
                new MaakBerichtPersoonInformatie(SoortSynchronisatie.VOLLEDIG_BERICHT), maakBerichtParameters.getAutorisatiebundels().get(0), new StatischePersoongegevens());
        //autoriseer alles
        new AutorisatieAlles(berichtgegevens);
        //filter
        service.execute(berichtgegevens);

        final Collection<MetaGroep> alleGroepen = persoonslijst.getModelIndex().geefGroepen();
        final Set<Integer> alleElementen = new HashSet<>();
        for (MetaGroep metaGroep : alleGroepen) {
            if (berichtgegevens.isGeautoriseerd(metaGroep)) {
                alleElementen.add(metaGroep.getGroepElement().getId());
            }
        }
        for (MetaObject metaObject : persoonslijst.getModelIndex().geefObjecten()) {
            if (berichtgegevens.isGeautoriseerd(metaObject)) {
                alleElementen.add(metaObject.getObjectElement().getId());
            }
        }
        Assert.assertFalse(alleElementen.contains(Element.PERSOON_GESLACHTSNAAMCOMPONENT_IDENTITEIT.getId()));
        Assert.assertFalse(alleElementen.contains(Element.PERSOON_PERSOONSKAART.getId()));
        Assert.assertTrue(alleElementen.contains(Element.PERSOON_KIND.getId()));
        Assert.assertTrue(alleElementen.contains(Element.GERELATEERDEOUDER_PERSOON_IDENTITEIT.getId()));
        Assert.assertTrue(alleElementen.contains(Element.GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS.getId()));
        Assert.assertFalse(alleElementen.contains(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE.getId()));
    }

    private MetaObject maakPersoonVorGeefMedebewoners() {
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON)
            .metGroep()
                .metGroepElement(Element.PERSOON_PERSOONSKAART.getId())
                    .metRecord()
                        .metActieInhoud(actieInhoud)
                        .metAttribuut(Element.PERSOON_PERSOONSKAART_PARTIJCODE.getId(), 12)
                    .eindeRecord()
                .eindeGroep()
            .metObject()
            .metObjectElement(Element.PERSOON_ADRES.getId())
                .metGroep()
                    .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                        .metRecord()
                            .metActieInhoud(actieInhoud)
                            .metAttribuut(Element.PERSOON_ADRES_HUISNUMMER.getId(), 3)
                            .metAttribuut(Element.PERSOON_ADRES_GEMEENTEDEEL.getId(), "noord")
                        .eindeRecord()
                .eindeGroep()
            .eindeObject()
            .metObject()
                .metObjectElement(Element.PERSOON_GESLACHTSNAAMCOMPONENT.getId())
                    .metGroep()
                        .metGroepElement(Element.PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD.getId())
                            .metRecord().metActieInhoud(actieInhoud).eindeRecord()
                    .eindeGroep()
            .eindeObject()
            .metObject()
                .metId(1)
                .metObjectElement(getObjectElement(Element.PERSOON_KIND.getId()))
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_KIND_IDENTITEIT.getId()))
                    .metRecord()
                        .metId(1)
                        .metActieInhoud(actieInhoud)
                    .eindeRecord()
                .eindeGroep()
                .metObject()
                    .metId(2)
                    .metObjectElement(getObjectElement(Element.FAMILIERECHTELIJKEBETREKKING.getId()))
                    .metGroep()
                        .metGroepElement(getGroepElement(Element.FAMILIERECHTELIJKEBETREKKING_STANDAARD.getId()))
                            .metRecord()
                                .metActieInhoud(actieInhoud)
                            .eindeRecord()
                    .eindeGroep()
                    .metObject()
                        .metId(3)
                        .metObjectElement(getObjectElement(Element.GERELATEERDEOUDER.getId()))
                        .metObject()
                            .metObjectElement(getObjectElement(Element.GERELATEERDEOUDER_PERSOON.getId()))
                            .metId(33)
                            .metGroep()
                                .metGroepElement(getGroepElement(Element.GERELATEERDEOUDER_PERSOON_IDENTITEIT.getId()))
                                .metRecord()
                                .eindeRecord()
                            .eindeGroep()
                            .metGroep()
                                .metGroepElement(getGroepElement(Element.GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS.getId()))
                                .metRecord()
                                    .metActieInhoud(actieInhoud)
                                .eindeRecord()
                            .eindeGroep()
                            .metGroep()
                                .metGroepElement(getGroepElement(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE.getId()))
                                .metRecord()
                                    .metActieInhoud(actieInhoud)
                                .eindeRecord()
                            .eindeGroep()
                        .eindeObject()
                    .eindeObject()
                .eindeObject()
            .eindeObject()
        .build();
        //@formatter:on
        return persoon;
    }

    private void valideer(final Persoonslijst persoonslijst, final MaakBerichtParameters maakBerichtParameters) {
        final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, persoonslijst,
                new MaakBerichtPersoonInformatie(SoortSynchronisatie.VOLLEDIG_BERICHT), maakBerichtParameters.getAutorisatiebundels().get(0), new StatischePersoongegevens());
        //autoriseer alles
        new AutorisatieAlles(berichtgegevens);
        //filter
        service.execute(berichtgegevens);

        final Collection<MetaGroep> alleGroepen = persoonslijst.getModelIndex().geefGroepen();
        final Set<Integer> alleElementen = new HashSet<>();
        for (MetaGroep metaGroep : alleGroepen) {
            if (berichtgegevens.isGeautoriseerd(metaGroep)) {
                alleElementen.add(metaGroep.getGroepElement().getId());
            }
        }
        for (MetaObject metaObject : persoonslijst.getModelIndex().geefObjecten()) {
            if (berichtgegevens.isGeautoriseerd(metaObject)) {
                alleElementen.add(metaObject.getObjectElement().getId());
            }
        }
        Assert.assertFalse(alleElementen.contains(Element.PERSOON_GESLACHTSNAAMCOMPONENT_IDENTITEIT.getId()));
        Assert.assertFalse(alleElementen.contains(Element.PERSOON_KIND.getId()));
        Assert.assertFalse(alleElementen.contains(Element.PERSOON_PERSOONSKAART.getId()));
    }

    private MetaObject maakPersoon() {
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON)
            .metGroep()
                .metGroepElement(Element.PERSOON_PERSOONSKAART.getId())
                    .metRecord()
                        .metActieInhoud(actieInhoud)
                        .metAttribuut(Element.PERSOON_PERSOONSKAART_PARTIJCODE.getId(), 12)
                    .eindeRecord()
                .eindeGroep()
            .metObject()
            .metObjectElement(Element.PERSOON_ADRES.getId())
                .metGroep()
                    .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                        .metRecord()
                            .metActieInhoud(actieInhoud)
                            .metAttribuut(Element.PERSOON_ADRES_HUISNUMMER.getId(), 3)
                            .metAttribuut(Element.PERSOON_ADRES_GEMEENTEDEEL.getId(), "noord")
                        .eindeRecord()
                .eindeGroep()
            .eindeObject()
            .metObject()
                .metObjectElement(Element.PERSOON_GESLACHTSNAAMCOMPONENT.getId())
                    .metGroep()
                        .metGroepElement(Element.PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD.getId())
                            .metRecord().metActieInhoud(actieInhoud).eindeRecord()
                    .eindeGroep()
            .eindeObject()
            .metObject()
                .metId(1)
                .metObjectElement(getObjectElement(Element.PERSOON_KIND.getId()))
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_KIND_IDENTITEIT.getId()))
                    .metRecord()
                        .metId(1)
                        .metActieInhoud(actieInhoud)
                    .eindeRecord()
                .eindeGroep()
                .metObject()
                    .metId(2)
                    .metObjectElement(getObjectElement(Element.FAMILIERECHTELIJKEBETREKKING.getId()))
                    .metGroep()
                        .metGroepElement(getGroepElement(Element.FAMILIERECHTELIJKEBETREKKING_STANDAARD.getId()))
                            .metRecord()
                                .metActieInhoud(actieInhoud)
                            .eindeRecord()
                    .eindeGroep()
                .eindeObject()
            .eindeObject()
        .build();
        //@formatter:on
        return persoon;
    }
}
