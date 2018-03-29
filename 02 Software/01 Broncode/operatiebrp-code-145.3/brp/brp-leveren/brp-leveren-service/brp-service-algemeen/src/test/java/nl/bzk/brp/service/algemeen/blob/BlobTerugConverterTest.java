/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.blob;

import java.util.Collection;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereAanduidingVerval;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.services.blobber.json.BlobRoot;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test basisfunctionaliteit Blobterugconverter; Complete test gebaseerd op volledig persoonsbeeld zie BlobTerugConversieTest
 */
public class BlobTerugConverterTest {

    @Test
    public void testTerugconversie() {

        final Actie actie1 = TestVerantwoording.maakActie(1);
        final Actie actie2 = TestVerantwoording.maakActie(2);
        final Actie actie3 = TestVerantwoording.maakActie(3);
        final Actie actie4 = TestVerantwoording.maakActie(4);

        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metId(999)
            .metObjectElement(Element.PERSOON)
            .metGroep()
                .metGroepElement(Element.PERSOON_IDENTITEIT.getId())
                .metRecord().metAttribuut(Element.PERSOON_SOORTCODE.getId(), SoortPersoon.INGESCHREVENE.getCode()).eindeRecord()
            .eindeGroep()
            .metObject()
                .metObjectElement(Element.PERSOON_ADRES.getId())
                .metId(20)
                .metGroep()
                    .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                    .metRecord()
                        .metId(15)
                        .metActieInhoud(actie1)
                        .metActieVerval(actie2)
                        .metActieAanpassingGeldigheid(actie3)
                        .metActieVervalTbvLeveringMutaties(actie4)
                        .metNadereAanduidingVerval(NadereAanduidingVerval.O.getCode())
                        .metIndicatieTbvLeveringMutaties(true)
                        .metAttribuut(Element.PERSOON_ADRES_HUISNUMMER.getId(), "2")
                        .metAttribuut(Element.PERSOON_ADRES_AFGEKORTENAAMOPENBARERUIMTE.getId(), "honk")
                    .eindeRecord()
                    .metRecord()
                        .metId(25)
                        .metActieInhoud(actie2)
                        .metDatumAanvangGeldigheid(DatumUtil.gisteren())
                        .metDatumEindeGeldigheid(DatumUtil.morgen())
                        .metAttribuut(Element.PERSOON_ADRES_HUISNUMMER.getId(), "2")
                        .metAttribuut(Element.PERSOON_ADRES_AFGEKORTENAAMOPENBARERUIMTE.getId(), "honk")
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
            .metObject()
                .metObjectElement(Element.PERSOON_KIND.getId())
                .metId(40)
                .metGroep()
                    .metGroepElement(Element.PERSOON_KIND_IDENTITEIT.getId())
                        .metRecord().metAttribuut(Element.PERSOON_KIND_ROLCODE.getId(),
                                    SoortBetrokkenheid.KIND.getCode()).eindeRecord()
                .eindeGroep()
                .metObject()
                    .metObjectElement(Element.FAMILIERECHTELIJKEBETREKKING.getId())
                    .metGroep()
                        .metGroepElement(Element.FAMILIERECHTELIJKEBETREKKING_IDENTITEIT.getId())
                            .metRecord().metAttribuut(Element.FAMILIERECHTELIJKEBETREKKING_SOORTCODE.getId(),
                                SoortRelatie.FAMILIERECHTELIJKE_BETREKKING.getCode()).eindeRecord()
                    .eindeGroep()
                    .metObject()
                        .metObjectElement(Element.GERELATEERDEOUDER.getId())
                        .metGroep()
                            .metGroepElement(Element.GERELATEERDEOUDER_IDENTITEIT.getId())
                                .metRecord().metAttribuut(Element.OUDER_ROLCODE.getId(),
                                            SoortBetrokkenheid.OUDER.getCode()).eindeRecord()
                        .eindeGroep()
                        .metObject()
                            .metObjectElement(Element.GERELATEERDEOUDER_PERSOON.getId())
                            .metGroep()
                                .metGroepElement(Element.GERELATEERDEOUDER_PERSOON_IDENTITEIT.getId())
                                    .metRecord().metAttribuut(Element.GERELATEERDEOUDER_PERSOON_SOORTCODE.getId(),
                                                SoortPersoon.INGESCHREVENE.getCode()).eindeRecord()
                            .eindeGroep()
                        .eindeObject()
                    .eindeObject()
                .eindeObject()
            .eindeObject()
        .build();
        //@formatter:on
        final BlobRoot root = BlobConverter.converteer(persoon);
        final BlobTerugConverter blobConverter = new BlobTerugConverter(root, AttribuutMapper.INSTANCE);
        final Collection<MetaObject.Builder> builders = blobConverter.geefRootMetaObjectBuilders();
        Assert.assertEquals(1, builders.size());
        final MetaObject terugConverteerdMetaObject = builders.iterator().next().build();
//        ModelAfdruk.printAfdruk(persoon);
//        ModelAfdruk.printAfdruk(terugConverteerdMetaObject);
        Assert.assertTrue(persoon.deepEquals(terugConverteerdMetaObject));
    }

    @Test
    public void testBuilderLookup() {

        //@formatter:off
        final MetaObject.Builder testBuilder = MetaObject.maakBuilder()
            .metId(999)
            .metObjectElement(Element.PERSOON)
            .metGroep()
                .metGroepElement(Element.PERSOON_IDENTITEIT.getId())
                .metRecord().metAttribuut(Element.PERSOON_SOORTCODE.getId(), SoortPersoon.INGESCHREVENE.getCode()).eindeRecord()
            .eindeGroep();
        //@formatter:on
        final MetaObject persoon = testBuilder.build();
        final BlobRoot root = BlobConverter.converteer(persoon);
        final BlobTerugConverter blobConverter = new BlobTerugConverter(root, AttribuutMapper.INSTANCE);
        final MetaObject.Builder builderInConverter = blobConverter.geefRootMetaObjectBuilders().iterator().next();
        final String keyOpPersoon = blobConverter.maakObjectBuilderKey(persoon.getObjectElement().getId(), persoon.getObjectsleutel());
        final MetaObject.Builder opzochteBuilder = blobConverter.getObjectBuilder(keyOpPersoon);
        Assert.assertTrue(builderInConverter == opzochteBuilder);
    }
}
