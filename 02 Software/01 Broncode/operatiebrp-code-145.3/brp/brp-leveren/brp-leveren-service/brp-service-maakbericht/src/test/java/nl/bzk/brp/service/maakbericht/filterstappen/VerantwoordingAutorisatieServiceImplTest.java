/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;
import static nl.bzk.brp.domain.element.ElementHelper.getObjectElement;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

import com.google.common.collect.Iterables;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtParameters;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtPersoonInformatie;
import nl.bzk.brp.service.maakbericht.bepaling.StatischePersoongegevens;
import org.junit.Test;

/**
 * Unit test voor {@link VerantwoordingAutorisatieServiceImpl}.
 */
public class VerantwoordingAutorisatieServiceImplTest {

    private VerantwoordingAutorisatieServiceImpl service = new VerantwoordingAutorisatieServiceImpl();

    private static final AdministratieveHandeling HANDELING_1 = TestVerantwoording.maakAdministratievehandelingMetActies(100);
    private final static Actie ACTIE_INHOUD_1 = HANDELING_1.getActie(100);

    private static final AdministratieveHandeling HANDELING_2 = TestVerantwoording.maakAdministratievehandelingMetActies(200);
    private final static Actie ACTIE_INHOUD_2 = HANDELING_2.getActie(200);

    private static final AdministratieveHandeling HANDELING_3 = TestVerantwoording.maakAdministratievehandelingMetActies(300);
    private final static Actie ACTIE_INHOUD_3 = HANDELING_3.getActie(300);

    private static final MetaObject ROOT_OBJECT = MetaObject.maakBuilder()
            .metId(1)
            .metObjectElement(getObjectElement(Element.PERSOON.getId()))
            .metGroep()
            .metGroepElement(getGroepElement(Element.PERSOON_IDENTIFICATIENUMMERS.getId()))
            .metRecord()
            .metActieInhoud(ACTIE_INHOUD_1)
            .metActieVerval(ACTIE_INHOUD_2)
            .metActieAanpassingGeldigheid(ACTIE_INHOUD_3)
            .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId()), "123456789")
            .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_ACTIEINHOUD.getId()),
                    HANDELING_1.getActies().iterator().next())
            .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_ACTIEVERVAL.getId()),
                    HANDELING_2.getActies().iterator().next())
            .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_ACTIEAANPASSINGGELDIGHEID.getId()),
                    HANDELING_3.getActies().iterator().next())
            .eindeRecord()
            .eindeGroep()
            .eindeObject()
            .build();
    // @formatter:on

    @Test
    public void autoriseerVerantwoordingen() throws Exception {
        final Persoonslijst persoonslijst = new Persoonslijst(ROOT_OBJECT, 0L);
        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, persoonslijst,
                new MaakBerichtPersoonInformatie(SoortSynchronisatie.VOLLEDIG_BERICHT), null, new StatischePersoongegevens());
        berichtgegevens.autoriseer(Iterables.getOnlyElement(ROOT_OBJECT.getGroepen()).getRecords().iterator().next());
        berichtgegevens.autoriseer(Iterables.getOnlyElement(ROOT_OBJECT.getGroepen()).getRecords().iterator().next());
        berichtgegevens.autoriseer(Iterables.getOnlyElement(ROOT_OBJECT.getGroepen()).getRecords().iterator().next()
                .getAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId())));
        berichtgegevens.autoriseer(Iterables.getOnlyElement(ROOT_OBJECT.getGroepen()).getRecords().iterator().next()
                .getAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_ACTIEINHOUD.getId())));
        berichtgegevens.getParameters().setAdministratieveHandelingId(100L);

        service.execute(berichtgegevens);

        assertThat(berichtgegevens.getGeautoriseerdeActies().size(), is(1));
        assertThat(berichtgegevens.getGeautoriseerdeHandelingen().size(), is(1));
        assertThat(berichtgegevens.getGeautoriseerdeActies(), contains(ACTIE_INHOUD_1));
        assertThat(berichtgegevens.getGeautoriseerdeActies(), not(contains(ACTIE_INHOUD_2)));
    }

    @Test
    public void autoriseerVerantwoordingenAlleActiesGeautoriseerd() throws Exception {
        final Persoonslijst persoonslijst = new Persoonslijst(ROOT_OBJECT, 0L);
        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, persoonslijst,
                new MaakBerichtPersoonInformatie(SoortSynchronisatie.VOLLEDIG_BERICHT), null, new StatischePersoongegevens());
        berichtgegevens.autoriseer(Iterables.getOnlyElement(ROOT_OBJECT.getGroepen()).getRecords().iterator().next());
        berichtgegevens.autoriseer(Iterables.getOnlyElement(ROOT_OBJECT.getGroepen()).getRecords().iterator().next());
        berichtgegevens.autoriseer(Iterables.getOnlyElement(ROOT_OBJECT.getGroepen()).getRecords().iterator().next()
                .getAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId())));
        berichtgegevens.autoriseer(Iterables.getOnlyElement(ROOT_OBJECT.getGroepen()).getRecords().iterator().next()
                .getAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_ACTIEINHOUD.getId())));
        berichtgegevens.autoriseer(Iterables.getOnlyElement(ROOT_OBJECT.getGroepen()).getRecords().iterator().next()
                .getAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_ACTIEVERVAL.getId())));
        berichtgegevens.autoriseer(Iterables.getOnlyElement(ROOT_OBJECT.getGroepen()).getRecords().iterator().next()
                .getAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_ACTIEAANPASSINGGELDIGHEID.getId())));
        berichtgegevens.getParameters().setAdministratieveHandelingId(100L);

        service.execute(berichtgegevens);

        assertThat(berichtgegevens.getGeautoriseerdeActies().size(), is(3));
        assertThat(berichtgegevens.getGeautoriseerdeHandelingen().size(), is(3));
    }

    @Test
    public void autoriseerVerantwoordingenGeenGedeeldeActies() throws Exception {
        final Persoonslijst persoonslijst = new Persoonslijst(ROOT_OBJECT, 0L);
        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, persoonslijst,
                new MaakBerichtPersoonInformatie(SoortSynchronisatie.VOLLEDIG_BERICHT), null, new StatischePersoongegevens());
        berichtgegevens.autoriseer(Iterables.getOnlyElement(ROOT_OBJECT.getGroepen()).getRecords().iterator().next());
        berichtgegevens.autoriseer(Iterables.getOnlyElement(ROOT_OBJECT.getGroepen()).getRecords().iterator().next());
        berichtgegevens.getParameters().setAdministratieveHandelingId(100L);

        service.execute(berichtgegevens);

        assertThat(berichtgegevens.getGeautoriseerdeActies().size(), is(0));
        assertThat(berichtgegevens.getGeautoriseerdeHandelingen().size(), is(0));
    }

    @Test
    public void autoriseertGeenAndereVerantwoordingenInMutatiebericht() {
        final Persoonslijst persoonslijst = new Persoonslijst(ROOT_OBJECT, 0L);
        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        AdministratieveHandeling administratieveHandeling = TestVerantwoording.maakAdministratievehandelingMetActies(3);
        maakBerichtParameters.setAdministratieveHandelingId(administratieveHandeling.getId());
        final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, persoonslijst,
                new MaakBerichtPersoonInformatie(SoortSynchronisatie.MUTATIE_BERICHT), null, new StatischePersoongegevens());

        service.execute(berichtgegevens);

        assertThat(berichtgegevens.getGeautoriseerdeActies().size(), is(0));
        assertThat(berichtgegevens.getGeautoriseerdeHandelingen().size(), is(0));
    }

    @Test
    public void autoriseertGeenAndereVerantwoordingenInMutatieberichtZelfdeAdmHnd() {
        final Persoonslijst persoonslijst = new Persoonslijst(ROOT_OBJECT, 0L);
        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, persoonslijst,
                new MaakBerichtPersoonInformatie(SoortSynchronisatie.MUTATIE_BERICHT), null, new StatischePersoongegevens());
        berichtgegevens.autoriseer(Iterables.getOnlyElement(ROOT_OBJECT.getGroepen()).getRecords().iterator().next());
        berichtgegevens.autoriseer(Iterables.getOnlyElement(ROOT_OBJECT.getGroepen()).getRecords().iterator().next());
        berichtgegevens.autoriseer(Iterables.getOnlyElement(ROOT_OBJECT.getGroepen()).getRecords().iterator().next()
                .getAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_ACTIEINHOUD.getId())));
        berichtgegevens.getParameters().setAdministratieveHandelingId(100L);

        service.execute(berichtgegevens);

        assertThat(berichtgegevens.getGeautoriseerdeActies().size(), is(1));
        assertThat(berichtgegevens.getGeautoriseerdeHandelingen().size(), is(1));
        assertThat(berichtgegevens.getGeautoriseerdeActies(), contains(ACTIE_INHOUD_1));
        assertThat(berichtgegevens.getGeautoriseerdeActies(), not(contains(ACTIE_INHOUD_2)));
    }

}
