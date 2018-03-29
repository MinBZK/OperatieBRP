/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.blob;

import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereAanduidingVerval;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.services.blobber.BlobException;
import nl.bzk.algemeenbrp.services.blobber.json.AfnemerindicatiesBlob;
import nl.bzk.algemeenbrp.services.blobber.json.PersoonBlob;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.ModelAfdruk;
import nl.bzk.brp.domain.leveringmodel.ModelIndex;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

/**
 */
public class PersoonslijstFactoryTest {

    private static final int HOOFDPERSOON_ACTIE1 = 11;
    private static final int KIND_BETR_ACTIE2 = 22;
    private static final int KIND_ACTIE_TBV_LEVERMUTATIES = 33;
    private static final int KIND_ACTIE_AANPASSING_GELDIGHEID = 44;
    private static final Number MAMMA_BETR_ID = 453543;
    private static final int MAMA_ID = 999;
    private static final int DATUM_EINDE_GELDIGHEID = 20010101;
    private static final int DATUM_AANVANG_GELDIGHEID = 20020101;
    private static final int MAMMA_ACTIE1 = 456;
    private static final int MAMMA_ACTIE2 = 789;

    @Test
    public void testMaak() throws IOException, BlobException {
        final PersoonData persData = new PersoonData(maakPersoonBlob(), maakAfnemerindicaties(), 0L);
        final Persoonslijst persoonslijst = PersoonslijstFactory.maak(persData);

        Assert.assertNotNull(persoonslijst);
        final ModelIndex modelIndex = persoonslijst.getModelIndex();
        Assert.assertEquals(1, modelIndex
                .geefObjecten(ElementHelper.getObjectElement(Element.GERELATEERDEOUDER_PERSOON.getId())).size());
        Assert.assertEquals(1, modelIndex
                .geefGroepenVanElement(getGroepElement(Element.GERELATEERDEOUDER_PERSOON_IDENTITEIT.getId())).size());
        Assert.assertEquals(1, modelIndex
                .geefGroepenVanElement(getGroepElement(Element.PERSOON_AFNEMERINDICATIE_STANDAARD.getId())).size());

        final MetaGroep groep = modelIndex.geefGroepenVanElement(getGroepElement(Element.PERSOON_NATIONALITEIT_STANDAARD.getId())).iterator().next();
        final MetaRecord record = groep.getRecords().iterator().next();
        Assert.assertEquals(DATUM_EINDE_GELDIGHEID, record.getDatumEindeGeldigheid().intValue());
        Assert.assertEquals(DATUM_AANVANG_GELDIGHEID, record.getDatumAanvangGeldigheid().intValue());
        Assert.assertEquals(KIND_ACTIE_TBV_LEVERMUTATIES, record.getActieVervalTbvLeveringMutaties().getId().intValue());
        Assert.assertEquals(KIND_ACTIE_AANPASSING_GELDIGHEID, record.getActieAanpassingGeldigheid().getId().intValue());
        Assert.assertEquals(NadereAanduidingVerval.S.getCode(), record.getNadereAanduidingVerval());
        //3 handelingen, 2 op hoofdpersoon
        Assert.assertEquals(2, persoonslijst.getAdministratieveHandelingen().size());
        Assert.assertEquals(removeLineEndings(IOUtils.toString(new ClassPathResource("/data/PersoonsgegevensFactoryTest.maak.txt").getInputStream())),
                removeLineEndings(ModelAfdruk
                        .maakAfdruk(persoonslijst.getMetaObject())));


    }

    private String removeLineEndings(String value) {
        return value.replace("\n", "").replace("\r", "");
    }

    private PersoonBlob maakPersoonBlob() throws BlobException {

        final ZonedDateTime datumTijdAttribuut = ZonedDateTime.of(2011, 6, 8, 2, 10, 5, 0, DatumUtil.BRP_ZONE_ID);
        final Actie actie1 = TestVerantwoording.maakActie(HOOFDPERSOON_ACTIE1, datumTijdAttribuut);
        final Actie actie2 = TestVerantwoording.maakActie(KIND_BETR_ACTIE2, datumTijdAttribuut);
        final Actie actieTbvLevMuts = TestVerantwoording.maakActie(KIND_ACTIE_TBV_LEVERMUTATIES, datumTijdAttribuut);
        final Actie actieAanpassingGeldigheid = TestVerantwoording.maakActie(KIND_ACTIE_AANPASSING_GELDIGHEID, datumTijdAttribuut);

        final ZonedDateTime datumTijdAttribuutOuder = ZonedDateTime.of(2001, 3, 4, 5, 30, 25, 0, DatumUtil.BRP_ZONE_ID);
        final Actie actie1Ouder = TestVerantwoording.maakActie(MAMMA_ACTIE1, datumTijdAttribuutOuder);
        final Actie actie2Ouder = TestVerantwoording.maakActie(MAMMA_ACTIE2, datumTijdAttribuutOuder);

        //@formatter:off
        final MetaObject metaPersoon = MetaObject.maakBuilder()
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
                        .metAttribuut(Element.PERSOON_ADRES_HUISNUMMER.getId(), 2)
                    .eindeRecord()
                    .metRecord()
                        .metId(25)
                        .metActieInhoud(actie1)
                        .metActieVerval(actie1)
                        .metAttribuut(Element.PERSOON_ADRES_HUISNUMMER.getId(), 20)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
            .metObject()
                .metObjectElement(Element.PERSOON_NATIONALITEIT.getId())
                .metGroep()
                    .metGroepElement(Element.PERSOON_NATIONALITEIT_IDENTITEIT.getId())
                    .metRecord().metId(1).eindeRecord()
                .eindeGroep()
                .metGroep()
                    .metGroepElement(Element.PERSOON_NATIONALITEIT_STANDAARD.getId())
                    .metRecord()
                        .metDatumAanvangGeldigheid(DATUM_AANVANG_GELDIGHEID)
                        .metDatumEindeGeldigheid(DATUM_EINDE_GELDIGHEID)
                        .metActieInhoud(actie1)
                        .metActieVervalTbvLeveringMutaties(actieTbvLevMuts)
                        .metNadereAanduidingVerval(NadereAanduidingVerval.S.getCode())
                        .metActieAanpassingGeldigheid(actieAanpassingGeldigheid)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
            .metObject()
                .metObjectElement(Element.PERSOON_KIND.getId())
                .metId(40)
                .metGroep()
                    .metGroepElement(Element.PERSOON_KIND_IDENTITEIT.getId())
                        .metRecord().metActieInhoud(actie2).metAttribuut(Element.PERSOON_KIND_ROLCODE.getId(),
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
                        .metId(MAMMA_BETR_ID)
                        .metObjectElement(Element.GERELATEERDEOUDER.getId())
                        .metGroep()
                            .metGroepElement(Element.GERELATEERDEOUDER_IDENTITEIT.getId())
                                .metRecord().metActieInhoud(actie1).metAttribuut(Element.GERELATEERDEOUDER_ROLCODE.getId(),
                                            SoortBetrokkenheid.OUDER.getCode()).eindeRecord()
                        .eindeGroep()
                        .metObject()
                            .metId(MAMA_ID)
                            .metObjectElement(Element.GERELATEERDEOUDER_PERSOON.getId())
                            .metGroep()
                                .metGroepElement(Element.GERELATEERDEOUDER_PERSOON_IDENTITEIT.getId())
                                .metRecord()
                                    .metAttribuut(Element.GERELATEERDEOUDER_PERSOON_SOORTCODE.getId(), SoortPersoon.INGESCHREVENE.getCode())
                                .eindeRecord()
                            .eindeGroep()
                            .metGroep()
                                .metGroepElement(Element.GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS.getId())
                                .metRecord()
                                    .metId(15)
                                    .metActieInhoud(actie1Ouder)
                                    .metAttribuut(Element.GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId(), "000123456")
                                    .metAttribuut(Element.GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getId(), "0000654321")
                                .eindeRecord()
                                .metRecord()
                                    .metId(25)
                                    .metActieInhoud(actie1Ouder)
                                    .metActieVerval(actie2Ouder)
                                    .metAttribuut(Element.GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId(), "000123456")
                                    .metAttribuut(Element.GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getId(), "0000654321")
                                .eindeRecord()
                            .eindeGroep()
                        .eindeObject()
                    .eindeObject()
                .eindeObject()
            .eindeObject()
        .build();
        //@formatter:on

        //@formatter:off
        final MetaObject metaHandelingMetActiesOpHoofdPersoon = MetaObject.maakBuilder()
            .metObjectElement(Element.ADMINISTRATIEVEHANDELING.getId())
            .metId(2)
            .metGroep()
                .metGroepElement(Element.ADMINISTRATIEVEHANDELING_IDENTITEIT.getId())
                .metRecord()
                    .metAttribuut(Element.ADMINISTRATIEVEHANDELING_SOORTNAAM.getId(), SoortAdministratieveHandeling.AANVANG_ONDERZOEK.getId())
                    .metAttribuut(Element.ADMINISTRATIEVEHANDELING_PARTIJCODE.getId(), "000123")
                    .metAttribuut(Element.ADMINISTRATIEVEHANDELING_TIJDSTIPREGISTRATIE.getId(), datumTijdAttribuut)
                    .metAttribuut(Element.ADMINISTRATIEVEHANDELING_TOELICHTINGONTLENING.getId(), "toelichting")
                .eindeRecord()
            .eindeGroep()
            .metObject()
                .metObjectElement(Element.ACTIE.getId())
                .metId(HOOFDPERSOON_ACTIE1)
                .metGroep()
                    .metGroepElement(Element.ACTIE_IDENTITEIT.getId())
                    .metRecord()
                        .metAttribuut(Element.ACTIE_SOORTNAAM.getId(), SoortActie.BEEINDIGING_BEHANDELD_ALS_NEDERLANDER.getId())
                        .metAttribuut(Element.ACTIE_PARTIJCODE.getId(), "000123")
                        .metAttribuut(Element.ACTIE_TIJDSTIPREGISTRATIE.getId(), datumTijdAttribuut)
                        .metAttribuut(Element.ACTIE_DATUMONTLENING.getId(), 20010101)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
            .metObject()
                .metObjectElement(Element.ACTIE.getId())
                .metId(HOOFDPERSOON_ACTIE1)
                .metGroep()
                    .metGroepElement(Element.ACTIE_IDENTITEIT.getId())
                    .metRecord()
                        .metAttribuut(Element.ACTIE_SOORTNAAM.getId(), SoortActie.BEEINDIGING_BEHANDELD_ALS_NEDERLANDER.getId())
                        .metAttribuut(Element.ACTIE_PARTIJCODE.getId(), "000123")
                        .metAttribuut(Element.ACTIE_TIJDSTIPREGISTRATIE.getId(), datumTijdAttribuut)
                        .metAttribuut(Element.ACTIE_DATUMONTLENING.getId(), 20010101)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
            .metObject()
                .metObjectElement(Element.ACTIE.getId())
                .metId(MAMMA_ACTIE1)
                .metGroep()
                    .metGroepElement(Element.ACTIE_IDENTITEIT.getId())
                    .metRecord()
                        .metAttribuut(Element.ACTIE_SOORTNAAM.getId(), SoortActie.BEEINDIGING_BEHANDELD_ALS_NEDERLANDER.getId())
                        .metAttribuut(Element.ACTIE_PARTIJCODE.getId(), "000123")
                        .metAttribuut(Element.ACTIE_TIJDSTIPREGISTRATIE.getId(), datumTijdAttribuut)
                        .metAttribuut(Element.ACTIE_DATUMONTLENING.getId(), 20010101)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
              .metObject()
                .metObjectElement(Element.ACTIE.getId())
                .metId(MAMMA_ACTIE2)
                .metGroep()
                    .metGroepElement(Element.ACTIE_IDENTITEIT.getId())
                    .metRecord()
                        .metAttribuut(Element.ACTIE_SOORTNAAM.getId(), SoortActie.BEEINDIGING_BEHANDELD_ALS_NEDERLANDER.getId())
                        .metAttribuut(Element.ACTIE_PARTIJCODE.getId(), "000123")
                        .metAttribuut(Element.ACTIE_TIJDSTIPREGISTRATIE.getId(), datumTijdAttribuut)
                        .metAttribuut(Element.ACTIE_DATUMONTLENING.getId(), 20010101)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
            .metObject()
                .metObjectElement(Element.ACTIE.getId())
                .metId(KIND_ACTIE_TBV_LEVERMUTATIES)
                .metGroep()
                    .metGroepElement(Element.ACTIE_IDENTITEIT.getId())
                    .metRecord()
                        .metAttribuut(Element.ACTIE_SOORTNAAM.getId(), SoortActie.BEEINDIGING_STAATLOOS.getId())
                        .metAttribuut(Element.ACTIE_PARTIJCODE.getId(), "000123")
                        .metAttribuut(Element.ACTIE_TIJDSTIPREGISTRATIE.getId(), datumTijdAttribuut)
                        .metAttribuut(Element.ACTIE_DATUMONTLENING.getId(), 20010101)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
            .metObject()
                .metObjectElement(Element.ACTIE.getId())
                .metId(KIND_ACTIE_AANPASSING_GELDIGHEID)
                .metGroep()
                    .metGroepElement(Element.ACTIE_IDENTITEIT.getId())
                    .metRecord()
                        .metAttribuut(Element.ACTIE_SOORTNAAM.getId(), SoortActie.BEEINDIGING_STAATLOOS.getId())
                        .metAttribuut(Element.ACTIE_PARTIJCODE.getId(), "000123")
                        .metAttribuut(Element.ACTIE_TIJDSTIPREGISTRATIE.getId(), datumTijdAttribuut)
                        .metAttribuut(Element.ACTIE_DATUMONTLENING.getId(), 20010101)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
        .build();


         final MetaObject metaHandelingBetrokkenheid = MetaObject.maakBuilder()
            .metObjectElement(Element.ADMINISTRATIEVEHANDELING.getId())
            .metId(4)
            .metGroep()
                .metGroepElement(Element.ADMINISTRATIEVEHANDELING_IDENTITEIT.getId())
                .metRecord()
                    .metAttribuut(Element.ADMINISTRATIEVEHANDELING_SOORTNAAM.getId(), SoortAdministratieveHandeling.AANVANG_ONDERZOEK.getId())
                    .metAttribuut(Element.ADMINISTRATIEVEHANDELING_PARTIJCODE.getId(), "000123")
                    .metAttribuut(Element.ADMINISTRATIEVEHANDELING_TIJDSTIPREGISTRATIE.getId(), datumTijdAttribuut)
                    .metAttribuut(Element.ADMINISTRATIEVEHANDELING_TOELICHTINGONTLENING.getId(), "toelichting")
                .eindeRecord()
            .eindeGroep()
            .metObject()
                .metObjectElement(Element.ACTIE.getId())
                .metId(KIND_BETR_ACTIE2)
                .metGroep()
                    .metGroepElement(Element.ACTIE_IDENTITEIT.getId())
                    .metRecord()
                        .metAttribuut(Element.ACTIE_SOORTNAAM.getId(), SoortActie.BEEINDIGING_BEHANDELD_ALS_NEDERLANDER.getId())
                        .metAttribuut(Element.ACTIE_PARTIJCODE.getId(), "000123")
                        .metAttribuut(Element.ACTIE_TIJDSTIPREGISTRATIE.getId(), datumTijdAttribuut)
                        .metAttribuut(Element.ACTIE_DATUMONTLENING.getId(), 20010101)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
            .build();


        final MetaObject metaHandelingZonderActiesOpHoofdPersoon = MetaObject.maakBuilder()
            .metObjectElement(Element.ADMINISTRATIEVEHANDELING.getId())
            .metId(3)
            .metGroep()
                .metGroepElement(Element.ADMINISTRATIEVEHANDELING_IDENTITEIT.getId())
                .metRecord()
                    .metAttribuut(Element.ADMINISTRATIEVEHANDELING_SOORTNAAM.getId(), SoortAdministratieveHandeling.AANVANG_ONDERZOEK.getId())
                    .metAttribuut(Element.ADMINISTRATIEVEHANDELING_PARTIJCODE.getId(), "000123")
                    .metAttribuut(Element.ADMINISTRATIEVEHANDELING_TIJDSTIPREGISTRATIE.getId(), datumTijdAttribuut)
                    .metAttribuut(Element.ADMINISTRATIEVEHANDELING_TOELICHTINGONTLENING.getId(), "toelichting")
                .eindeRecord()
            .eindeGroep()
            .metObject()
                .metObjectElement(Element.ACTIE.getId())
                .metId(-1)
                .metGroep()
                    .metGroepElement(Element.ACTIE_IDENTITEIT.getId())
                    .metRecord()
                        .metAttribuut(Element.ACTIE_SOORTNAAM.getId(), SoortActie.BEEINDIGING_BEHANDELD_ALS_NEDERLANDER.getId())
                        .metAttribuut(Element.ACTIE_PARTIJCODE.getId(), "000123")
                        .metAttribuut(Element.ACTIE_TIJDSTIPREGISTRATIE.getId(), datumTijdAttribuut)
                        .metAttribuut(Element.ACTIE_DATUMONTLENING.getId(), 20010101)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
            .build();

        //@formatter:on

        final PersoonBlob persoonBlob = new PersoonBlob();
        persoonBlob.setPersoonsgegevens(BlobConverter.converteer(metaPersoon));
        persoonBlob.setVerantwoording(Lists.newArrayList(BlobConverter.converteer(metaHandelingMetActiesOpHoofdPersoon), BlobConverter.converteer
                (metaHandelingZonderActiesOpHoofdPersoon), BlobConverter.converteer(metaHandelingBetrokkenheid)));
        return persoonBlob;
    }

    private AfnemerindicatiesBlob maakAfnemerindicaties() throws BlobException {
        final MetaObject.Builder builder = TestBuilders.maakAfnemerindicatie(12, "000124");
        final AfnemerindicatiesBlob afnemerindicatiesBlob = new AfnemerindicatiesBlob();
        afnemerindicatiesBlob.setAfnemerindicaties(Lists.newLinkedList());
        afnemerindicatiesBlob.getAfnemerindicaties().add(BlobConverter.converteer(builder.build()));
        return afnemerindicatiesBlob;
    }
}
