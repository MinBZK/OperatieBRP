/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.builders;

import static nl.bzk.brp.domain.element.ElementHelper.getAttribuutElement;
import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Verwerkingssoort;
import nl.bzk.brp.domain.berichtmodel.BerichtElement;
import nl.bzk.brp.domain.berichtmodel.BerichtElementAttribuut;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaModel;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.helper.TestDatumUtil;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.maakbericht.AutorisatieAlles;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtParameters;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtPersoonInformatie;
import nl.bzk.brp.service.maakbericht.bepaling.StatischePersoongegevens;
import org.junit.Assert;
import org.junit.Test;

/**
 * BerichtGroepBuilderTest.
 */
public class BerichtGroepBuilderTest {

    private Actie actieInhoud = TestVerantwoording.maakActie(1, TestDatumUtil.gisteren());


    @Test
    public void testLegeStandaardGroep() {
        final MetaObject persoon = maakPersoonMetLegeStandaard();
        final Berichtgegevens berichtgegevens = maakBerichtgegevens(persoon, SoortSynchronisatie.VOLLEDIG_BERICHT);
        new AutorisatieAlles(berichtgegevens);
        final MetaGroep
                metaGroep =
                berichtgegevens.getPersoonslijst().getModelIndex().geefGroepenVanElement(getGroepElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_IDENTITEIT
                        .getId())).iterator().next();
        final BerichtElement.Builder builder = BerichtElement.builder().metNaam("test");
        BerichtGroepBuilder.build(metaGroep, berichtgegevens, builder, true);
        //1 groep
        final BerichtElement berichtElement = builder.build();
        Assert.assertEquals(1, berichtElement.getElementen().size());
        final BerichtElement berichtElementGroep = berichtElement.getElementen().get(0);
        //alleen nummer
        Assert.assertEquals(1, berichtElementGroep.getElementen().size());
    }


    @Test
    public void testGroepNietGeautoriseerd() {
        final MetaObject persoon = maakPersoonMetActieEnAttribuut();
        final Berichtgegevens berichtgegevens = maakBerichtgegevens(persoon, SoortSynchronisatie.VOLLEDIG_BERICHT);
        final MetaGroep metaGroep = berichtgegevens.getPersoonslijst().getModelIndex().geefGroepenVanElement(getGroepElement(Element.PERSOON_GEBOORTE
                .getId())).iterator().next();
        final BerichtElement.Builder builder = BerichtElement.builder().metNaam("test");
        BerichtGroepBuilder.build(metaGroep, berichtgegevens, builder, false);
        final BerichtElement berichtElement = builder.build();
        Assert.assertEquals(0, berichtElement.getElementen().size());
    }

    @Test
    public void testGroepGeautoriseerdZonderRecords() {
        final MetaObject persoon = maakPersoonLegeGroep();
        final Berichtgegevens berichtgegevens = maakBerichtgegevens(persoon, SoortSynchronisatie.VOLLEDIG_BERICHT);
        new AutorisatieAlles(berichtgegevens);
        final MetaGroep metaGroep = berichtgegevens.getPersoonslijst().getModelIndex().geefGroepenVanElement(getGroepElement(Element.PERSOON_GEBOORTE
                .getId())).iterator().next();
        final BerichtElement.Builder builder = BerichtElement.builder().metNaam("test");
        BerichtGroepBuilder.build(metaGroep, berichtgegevens, builder, false);
        final BerichtElement berichtElement = builder.build();
        Assert.assertEquals(0, berichtElement.getElementen().size());
    }

    @Test
    public void testGroepGeautoriseerdAttribuutEnActie() {
        final MetaObject persoon = maakPersoonMetActieEnAttribuut();
        final Berichtgegevens berichtgegevens = maakBerichtgegevens(persoon, SoortSynchronisatie.VOLLEDIG_BERICHT);
        new AutorisatieAlles(berichtgegevens);
        final MetaGroep metaGroep = berichtgegevens.getPersoonslijst().getModelIndex().geefGroepenVanElement(getGroepElement(Element.PERSOON_GEBOORTE
                .getId())).iterator().next();
        final BerichtElement.Builder builder = BerichtElement.builder().metNaam("test");
        BerichtGroepBuilder.build(metaGroep, berichtgegevens, builder, false);
        //1 groep
        final BerichtElement berichtElement = builder.build();
        Assert.assertEquals(1, berichtElement.getElementen().size());
        //2 records
        final BerichtElement berichtElementGroep = berichtElement.getElementen().get(0);
        //actie wordt platgeslagen (tijdstip + id hier) + attribuut huisnr, gemeentecode (uit identiteitsgroep)
        Assert.assertEquals(3, berichtElementGroep.getElementen().size());
    }

    @Test
    public void testGroepGeautoriseerdAttribuutEnActieMetStandaardGroep() {
        final MetaObject persoon = maakPersoonMetAdres();
        final Berichtgegevens berichtgegevens = maakBerichtgegevens(persoon, SoortSynchronisatie.VOLLEDIG_BERICHT);
        new AutorisatieAlles(berichtgegevens);
        final MetaGroep metaGroep = berichtgegevens.getPersoonslijst().getModelIndex()
                .geefGroepenVanElement(getGroepElement(Element.PERSOON_ADRES_STANDAARD
                        .getId())).iterator().next();
        //platgeslagen identiteit en standaard groep
        final BerichtElement.Builder builder = BerichtElement.builder().metNaam("test");
        BerichtGroepBuilder.build(metaGroep, berichtgegevens, builder, false);
        //adres met record standaardgroep en record identiteitsgroep (platgeslagen)
        final BerichtElement berichtElement = builder.build();
        Assert.assertEquals(1, berichtElement.getElementen().size());
        final BerichtElement berichtElementGroep = berichtElement.getElementen().get(0);
        //actie wordt platgeslagen (tijdstip + id hier) + attributen huisnr (standaardgroep), gemeentecode (identiteitsgroep)
        Assert.assertEquals(4, berichtElementGroep.getElementen().size());
    }

    @Test
    public void testGroepGeautoriseerdAttribuutEnActieMetOnderzoek() {
        final MetaObject persoon = maakPersoonMetOnderzoek();
        final Berichtgegevens berichtgegevens = maakBerichtgegevens(persoon, SoortSynchronisatie.VOLLEDIG_BERICHT);
        new AutorisatieAlles(berichtgegevens);

        final MetaGroep metaGroepStandaard = berichtgegevens.getPersoonslijst().getModelIndex()
                .geefGroepenVanElement(getGroepElement(Element.ONDERZOEK_STANDAARD.getId())).iterator().next();
        //onderzoek, dus separate identiteit en standaard groep
        final BerichtElement.Builder builder = BerichtElement.builder().metNaam("test");
        BerichtGroepBuilder.build(metaGroepStandaard, berichtgegevens, builder, false);
        //onderzoek met record standaardgroep maar geen identiteitsgroep (uitzondering op generieke behandeling van standaard/identiteitsgroepen)
        BerichtElement berichtElement = builder.build();
        Assert.assertEquals(1, berichtElement.getElementen().size());
        BerichtElement berichtElementGroep = berichtElement.getElementen().get(0);
        //actie wordt platgeslagen (tijdstip + id hier) + attributen omschrijving onderzoek (standaardgroep) GEEN partijcode (identiteitsgroep :
        // uitzondering onderzoeksgroep)
        Assert.assertEquals(3, berichtElementGroep.getElementen().size());

        final MetaGroep metaGroepIdentiteit = berichtgegevens.getPersoonslijst().getModelIndex()
                .geefGroepenVanElement(getGroepElement(Element.ONDERZOEK_IDENTITEIT.getId())).iterator().next();
        //onderzoek, dus separate identiteit en standaard groep
        BerichtGroepBuilder.build(metaGroepIdentiteit, berichtgegevens, builder, false);
        //onderzoek met record v identiteitsgroep (uitzondering op generieke behandeling van standaard/identiteitsgroepen)
        berichtElement = builder.build();
        //2 elementen : 1 wrapper voor standaardgroep en partijcode uit identiteitsgroep (geen wrapper)
        Assert.assertEquals(2, berichtElement.getElementen().size());
        berichtElementGroep = berichtElement.getElementen().get(1);
        //1 element v partijcode wordt niet platgeslagen in standaardgroep, maar blijft binnen identiteitsgroep
        Assert.assertEquals(0, berichtElementGroep.getElementen().size());
    }

    @Test
    public void testRecordAttributen() {
        final MetaObject persoon = maakPersoonMetActieEnAttribuut();
        final Berichtgegevens berichtgegevens = maakBerichtgegevens(persoon, SoortSynchronisatie.MUTATIE_BERICHT);
        new AutorisatieAlles(berichtgegevens);
        final MetaGroep metaGroep = berichtgegevens.getPersoonslijst().getModelIndex().geefGroepenVanElement(getGroepElement(Element.PERSOON_GEBOORTE
                .getId())).iterator().next();
        //zet verwerkingsoort
        final Verwerkingssoort verwerkingsoort = Verwerkingssoort.TOEVOEGING;
        final Map<MetaModel, Verwerkingssoort> verwerkingssoortMap = new HashMap<>();
        verwerkingssoortMap.put(metaGroep.getRecords().iterator().next(), verwerkingsoort);

        berichtgegevens.getStatischePersoongegevens().setVerwerkingssoortMap(verwerkingssoortMap);
        final BerichtElement.Builder builder = BerichtElement.builder().metNaam("test");
        BerichtGroepBuilder.build(metaGroep, berichtgegevens, builder, false);
        //1 voorkomen
        final BerichtElement berichtElement = builder.build();
        Assert.assertEquals(1, berichtElement.getElementen().size());
        //3 elementen in groepvoorkomen
        final BerichtElement berichtElementGroep = berichtElement.getElementen().get(0);
        //actie wordt platgeslagen (tijdstip + id hier) + attribuut
        Assert.assertEquals(3, berichtElementGroep.getElementen().size());
        //voorkomen sleutel attr + verwerkingsoort
        Assert.assertEquals(2, berichtElementGroep.getAttributen().size());
        //verwerkingsoort
        bevatAttribuut(berichtElementGroep.getAttributen(), BerichtConstanten.VERWERKINGSSOORT, verwerkingsoort.getNaam());
    }

    @Test
    public void testObjectSleutelLogica() {
        final MetaObject persoon = maakPersoonMetAdres();
        final Berichtgegevens berichtgegevens = maakBerichtgegevens(persoon, SoortSynchronisatie.VOLLEDIG_BERICHT);
        new AutorisatieAlles(berichtgegevens);
        final MetaGroep metaGroep = berichtgegevens.getPersoonslijst().getModelIndex()
                .geefGroepenVanElement(getGroepElement(Element.PERSOON_ADRES_STANDAARD
                        .getId())).iterator().next();
        //platgeslagen identiteit en standaard groep
        final BerichtElement.Builder builder = BerichtElement.builder().metNaam("test");
        BerichtGroepBuilder.build(metaGroep, berichtgegevens, builder, false);
        //1 voorkomen
        final BerichtElement berichtElement = builder.build();
        Assert.assertEquals(1, berichtElement.getElementen().size());
        //3 elementen in groepvoorkomen
        final BerichtElement berichtElementGroep = berichtElement.getElementen().get(0);
        //actie wordt platgeslagen (tijdstip + id hier) + gemeente identiteit attr + huisnummer
        Assert.assertEquals(4, berichtElementGroep.getElementen().size());
        //objectsleutel op groep bij associatiecode object
        bevatAttribuut(berichtElementGroep.getAttributen(), BerichtConstanten.OBJECT_SLEUTEL, "111");

    }

    @Test
    public void testRecordGeenVerwerkingsoortVolledigBericht() {
        final MetaObject persoon = maakPersoonMetActieEnAttribuut();
        final Berichtgegevens berichtgegevens = maakBerichtgegevens(persoon, SoortSynchronisatie.VOLLEDIG_BERICHT);
        new AutorisatieAlles(berichtgegevens);
        final MetaGroep metaGroep = berichtgegevens.getPersoonslijst().getModelIndex().geefGroepenVanElement(getGroepElement(Element.PERSOON_GEBOORTE
                .getId())).iterator().next();
        //zet verwerkingsoort
        final Verwerkingssoort verwerkingsoort = Verwerkingssoort.TOEVOEGING;

        final Map<MetaModel, Verwerkingssoort> verwerkingssoortMap = new HashMap<>();
        verwerkingssoortMap.put(metaGroep.getRecords().iterator().next(), verwerkingsoort);

        berichtgegevens.getStatischePersoongegevens().setVerwerkingssoortMap(verwerkingssoortMap);

        final BerichtElement.Builder builder = BerichtElement.builder().metNaam("test");
        BerichtGroepBuilder.build(metaGroep, berichtgegevens, builder, false);
        //1 groep
        final BerichtElement berichtElement = builder.build();
        Assert.assertEquals(1, berichtElement.getElementen().size());
        //2 records
        final BerichtElement berichtElementGroep = berichtElement.getElementen().get(0);
        //actie wordt platgeslagen (tijdstip + id hier) + attribuut
        Assert.assertEquals(3, berichtElementGroep.getElementen().size());
        //voorkomen sleutel attr + verwerkingsoort
        Assert.assertEquals(1, berichtElementGroep.getAttributen().size());
        //verwerkingsoort
        bevatAttribuutNiet(berichtElementGroep.getAttributen(), BerichtConstanten.VERWERKINGSSOORT, verwerkingsoort.getNaam());

    }

    private MetaObject maakPersoonMetAdres() {
        //@formatter:off
        final MetaObject metaObject = TestBuilders.maakLeegPersoon(1)
        .metObject()
            .metId(111)
            .metObjectElement(Element.PERSOON_ADRES.getId())
                .metGroep()
                .metGroepElement(Element.PERSOON_ADRES_IDENTITEIT.getId())
                    .metRecord()
                        .metId(4)
                        .metAttribuut(Element.PERSOON_ADRES_GEMEENTECODE.getId(), 3)
                    .eindeRecord()
                .eindeGroep()
                .metGroep()
                .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                    .metRecord()
                        .metId(456)
                        .metActieInhoud(actieInhoud)
                        .metAttribuut(Element.PERSOON_ADRES_HUISNUMMER.getId(), 3)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
            .build();
         //@formatter:on
        return metaObject;
    }

    private MetaObject maakPersoonMetOnderzoek() {
        //@formatter:off
        final MetaObject metaObject = TestBuilders.maakLeegPersoon(1)
            .metObject()
            .metId(111)
            .metObjectElement(Element.ONDERZOEK.getId())
                .metGroep()
                    .metGroepElement(Element.ONDERZOEK_IDENTITEIT.getId())
                        .metRecord()
                        .metId(4)
                        .metAttribuut(Element.ONDERZOEK_PARTIJCODE.getId(), 3)
                    .eindeRecord()
                .eindeGroep()
                .metGroep()
                    .metGroepElement(Element.ONDERZOEK_STANDAARD.getId())
                        .metRecord()
                        .metId(456)
                        .metActieInhoud(actieInhoud)
                        .metAttribuut(Element.ONDERZOEK_OMSCHRIJVING.getId(), "omschrijving onderzoek")
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
            .build();
        //@formatter:on
        return metaObject;
    }

    private void bevatAttribuut(final List<BerichtElementAttribuut> attributen, final String naam, final String waarde) {
        Assert.assertTrue(bevat(attributen, naam, waarde));
    }

    private void bevatAttribuutNiet(final List<BerichtElementAttribuut> attributen, final String naam, final String waarde) {
        Assert.assertFalse(bevat(attributen, naam, waarde));
    }

    private boolean bevat(final List<BerichtElementAttribuut> attributen, final String naam, final String waarde) {
        boolean gevonden = false;
        for (BerichtElementAttribuut berichtElementAttribuut : attributen) {
            if (berichtElementAttribuut.getNaam().equals(naam) && berichtElementAttribuut.getWaarde().equals(waarde)) {
                gevonden = true;
            }
        }
        return gevonden;
    }


    private Berichtgegevens maakBerichtgegevens(final MetaObject persoon, final SoortSynchronisatie soortSynchronisatie) {
        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final Persoonslijst persoonsgegevens = new Persoonslijst(persoon, 0L);
        final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, persoonsgegevens,
                new MaakBerichtPersoonInformatie(soortSynchronisatie), null, new StatischePersoongegevens());
        return berichtgegevens;
    }

    private MetaObject maakPersoonLegeGroep() {
        //@formatter:off
        final MetaObject metaObject = TestBuilders.maakLeegPersoon(1)
            .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_GEBOORTE.getId()))
                .eindeGroep()
            .eindeObject().build();
        //@formatter:on
        return metaObject;
    }


    private MetaObject maakPersoonMetLegeStandaard() {
        //@formatter:off
        final MetaObject metaObject = TestBuilders.maakLeegPersoon(1)
            .metId(1)
            .metObject()
                .metObjectElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER.getId())
                .metGroep()
                    .metGroepElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_IDENTITEIT.getId())
                        .metRecord()
                            .metAttribuut(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_NUMMER.getId(), "1")
                        .eindeRecord()
                .eindeGroep()
            .eindeObject().build();
        //@formatter:on
        return metaObject;
    }

    private MetaObject maakPersoonMetActieEnAttribuut() {
        //@formatter:off
        final MetaObject metaObject = TestBuilders.maakLeegPersoon(1)
            .metId(1)
            .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_GEBOORTE.getId()))
                    .metRecord()
                        .metId(2)
                        .metActieInhoud(actieInhoud)
                        .metAttribuut(getAttribuutElement(Element.PERSOON_GEBOORTE_DATUM), 20101010)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject().build();
        //@formatter:on
        return metaObject;
    }


}
