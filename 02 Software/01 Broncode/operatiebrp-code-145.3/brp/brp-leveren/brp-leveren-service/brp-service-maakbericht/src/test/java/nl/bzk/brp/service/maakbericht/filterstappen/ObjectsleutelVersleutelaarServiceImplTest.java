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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutel;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutelService;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutelServiceImpl;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.ParentFirstModelVisitor;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtParameters;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtPersoonInformatie;
import nl.bzk.brp.service.maakbericht.bepaling.StatischePersoongegevens;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class ObjectsleutelVersleutelaarServiceImplTest {


    @InjectMocks
    private ObjectsleutelVersleutelaarServiceImpl versleutelaarService;

    @Mock
    private ObjectSleutelService objectSleutelService;

    @Test
    public void test() {

        //@formatter:off
        final Actie actie = TestVerantwoording.maakActie(1);
        final MetaObject persoon = MetaObject.maakBuilder()
            .metId(1)
            .metObjectElement(Element.PERSOON.getId())
            .metGroep()
                .metGroepElement(Element.PERSOON_VERSIE.getId())
                .metRecord().metAttribuut(getAttribuutElement(Element.PERSOON_VERSIE_LOCK), 123L).eindeRecord()
                .eindeGroep()
            .metGroep()
                .metGroepElement(Element.PERSOON_AFGELEIDADMINISTRATIEF.getId())
                    .metRecord().metActieInhoud(actie).eindeRecord()
            .eindeGroep()
            .metObject()
                .metObjectElement(Element.PERSOON_ADRES.getId()).metId(2)
            .eindeObject()
            .metObject()
                .metObjectElement(getObjectElement(Element.PERSOON_KIND))
                .metObject()
                    .metObjectElement(getObjectElement(Element.FAMILIERECHTELIJKEBETREKKING))
                    .metObject()
                        .metObjectElement(getObjectElement(Element.GERELATEERDEOUDER))
                        .metGroep()
                            .metGroepElement(getGroepElement(Element.GERELATEERDEOUDER_OUDERSCHAP))
                            .metRecord()
                                .metId(0)
                                .metActieInhoud(actie)
                                .metAttribuut(getAttribuutElement(Element.GERELATEERDEOUDER_OUDERSCHAP_DATUMAANVANGGELDIGHEID), 20100101)
                            .eindeRecord()
                        .eindeGroep()
                        .metObject()
                            .metObjectElement(getObjectElement(Element.GERELATEERDEOUDER_PERSOON))
                            .metId(3)
                        .eindeObject()
                    .eindeObject()
                .eindeObject()
            .eindeObject()
        .build();
        //@formatter:on

        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 0L);

        final Partij partij = TestPartijBuilder.maakBuilder().metCode("000123").build();
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(new ToegangLeveringsAutorisatie(new PartijRol(partij, Rol.AFNEMER),
                TestAutorisaties.metSoortDienst(SoortDienst.SYNCHRONISATIE_PERSOON)), null);

        final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, persoonslijst,
                new MaakBerichtPersoonInformatie(SoortSynchronisatie.VOLLEDIG_BERICHT), autorisatiebundel, new StatischePersoongegevens());

        persoonslijst.getMetaObject().accept(new ParentFirstModelVisitor() {
            @Override
            protected void doVisit(final MetaObject object) {
                berichtgegevens.autoriseer(object);
            }
        });

        maakBerichtParameters.setAutorisatiebundels(Lists.newArrayList(autorisatiebundel));

        final ObjectSleutel persoonObjectSleutel1 = new ObjectSleutelServiceImpl().maakPersoonObjectSleutel(1, 123);
        final ObjectSleutel persoonObjectSleutel3 = new ObjectSleutelServiceImpl().maakPersoonObjectSleutel(3, 123);

        Mockito.when(objectSleutelService.maakPersoonObjectSleutel(1, 123)).thenReturn(persoonObjectSleutel1);
        Mockito.when(objectSleutelService.maakPersoonObjectSleutel(3, 123)).thenReturn(persoonObjectSleutel3);

        versleutelaarService.execute(berichtgegevens);

        Mockito.verify(objectSleutelService, Mockito.never()).maakPersoonObjectSleutel(2, 123);

        Assert.assertNotNull(berichtgegevens.getVersleuteldeObjectSleutel(persoon));
        Assert.assertNull(berichtgegevens.getVersleuteldeObjectSleutel(berichtgegevens.getPersoonslijst().getModelIndex()
                .geefObjecten(getObjectElement(Element.PERSOON_ADRES.getId())).iterator().next()));
        Assert.assertNotNull(berichtgegevens.getVersleuteldeObjectSleutel(berichtgegevens.getPersoonslijst().getModelIndex()
                .geefObjecten(getObjectElement(Element.GERELATEERDEOUDER_PERSOON.getId())).iterator().next()));


    }
}
