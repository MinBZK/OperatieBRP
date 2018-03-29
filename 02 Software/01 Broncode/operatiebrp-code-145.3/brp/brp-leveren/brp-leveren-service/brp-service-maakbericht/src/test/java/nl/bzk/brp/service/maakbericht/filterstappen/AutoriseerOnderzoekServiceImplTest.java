/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import static nl.bzk.brp.domain.element.ElementHelper.getAttribuutElement;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaModel;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.ParentFirstModelVisitor;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.helper.TestDatumUtil;
import nl.bzk.brp.domain.leveringmodel.persoon.OnderzoekIndex;
import nl.bzk.brp.domain.leveringmodel.persoon.Onderzoekbundel;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.maakbericht.AutorisatieAlles;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtParameters;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtPersoonInformatie;
import nl.bzk.brp.service.maakbericht.bepaling.StatischePersoongegevens;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test voor {@link AutoriseerOnderzoekServiceImpl}.
 */
public class AutoriseerOnderzoekServiceImplTest {

    private AutoriseerOnderzoekServiceImpl service = new AutoriseerOnderzoekServiceImpl();
    private Actie actieInhoud = TestVerantwoording.maakActie(1, TestDatumUtil.gisteren());


    /**
     * Volledig bericht voor afnemer : Indien het gegeven in onderzoek een geautoriseerd attribuut is en het onderzoek is geautoriseerd, dan wordt het
     * onderzoek ook getoond
     */
    @Test
    public void test_AfnemerVolledigBericht_AttribuutInOnderzoek_Geautoriseerd() {
        final MetaObject metaObject = getPersoonMetActueelOnderzoekOpAdresHuisNr();
        final Berichtgegevens berichtgegevens = maakBerichtgegevens(metaObject, Rol.AFNEMER, SoortSynchronisatie.VOLLEDIG_BERICHT, null);
        autoriseerGegevensInOnderzoek(berichtgegevens);
        autoriseerOnderzoek(berichtgegevens);

        service.execute(berichtgegevens);

        assertOnderzoeksgegevensGeautoriseerd(berichtgegevens);
    }


    /**
     * Volledig bericht voor afnemer : Indien het gegeven in onderzoek een geautoriseerd attribuut is maar het onderzoek is niet geautoriseerd, dan wordt
     * het onderzoek niet getoond.
     */
    @Test
    public void test_AfnemerVolledigBericht_AttribuutInOnderzoek_NietGeautoriseerd() {
        final MetaObject metaObject = getPersoonMetActueelOnderzoekOpAdresHuisNr();
        final Berichtgegevens berichtgegevens = maakBerichtgegevens(metaObject, Rol.AFNEMER, SoortSynchronisatie.VOLLEDIG_BERICHT, null);
        //autoriseer gegeven in onderzoek element maar niet het gegeven zelf
        MetaAttribuut gegevenInOnderzoekElementnaam = Iterables.getOnlyElement(berichtgegevens.getPersoonslijst().getModelIndex().geefAttributen(
                (getAttribuutElement(Element.GEGEVENINONDERZOEK_ELEMENTNAAM))));
        berichtgegevens.autoriseer(gegevenInOnderzoekElementnaam);

        autoriseerOnderzoek(berichtgegevens);

        service.execute(berichtgegevens);

        assertOnderzoeksgegevensNietGeautoriseerd(berichtgegevens);
    }

    /**
     * Volledig bericht voor afnemer : Indien het gegeven in onderzoek niet-geautoriseerd attribuut is en het onderzoek is niet geautoriseerd, dan wordt
     * het onderzoek niet getoond.
     */
    @Test
    public void test_AfnemerVolledigBericht_AttribuutInOnderzoek_NietGeautoriseerdViaDienst() {
        final MetaObject metaObject = getPersoonMetActueelOnderzoekOpAdresHuisNr();
        final Berichtgegevens berichtgegevens = maakBerichtgegevens(metaObject, Rol.AFNEMER, getDienstMetVerkeerdeAutorisatie(), null);

        service.execute(berichtgegevens);

        assertOnderzoeksgegevensNietGeautoriseerd(berichtgegevens);
    }

    /**
     * Afnemer krijgt onderzoek op ontbrekend gegeven nooit te zien.
     */
    @Test
    public void test_AfnemerVolledigBericht_OntbrekendGegeven_Geautoriseerd() {
        final MetaObject metaObject = getPersoonMetOnderzoekOpOntbrekendGegeven();
        final Berichtgegevens berichtgegevens = maakBerichtgegevens(metaObject, Rol.AFNEMER, SoortSynchronisatie.VOLLEDIG_BERICHT, null);
        autoriseerGegevensInOnderzoek(berichtgegevens);
        autoriseerOnderzoek(berichtgegevens);

        service.execute(berichtgegevens);

        assertOnderzoeksgegevensNietGeautoriseerd(berichtgegevens);
    }

    /**
     * Bijhouder krijgt onderzoek op ontbrekend gegeven wel te zien, mits geautoriseerd en juiste dienst.
     */
    @Test
    public void test_BijhouderVolledigBericht_OntbrekendGegeven_Geautoriseerd_JuisteDienst() {
        final MetaObject metaObject = getPersoonMetOnderzoekOpOntbrekendGegeven();
        final Berichtgegevens berichtgegevens = maakBerichtgegevens(metaObject, Rol.BIJHOUDINGSORGAAN_COLLEGE, SoortSynchronisatie.VOLLEDIG_BERICHT, null);
        berichtgegevens.getParameters().setSoortDienst(SoortDienst.GEEF_DETAILS_PERSOON);
        autoriseerGegevensInOnderzoek(berichtgegevens);
        autoriseerOnderzoek(berichtgegevens);

        service.execute(berichtgegevens);

        assertOnderzoeksgegevensGeautoriseerd(berichtgegevens);
    }


    /**
     * Bijhouder krijgt onderzoek op ontbrekend gegeven wel te zien, mits geautoriseerd en juiste dienst.
     */
    @Test
    public void test_BijhouderVolledigBericht_OntbrekendGegeven_Geautoriseerd_OnjuisteDienst() {
        final MetaObject metaObject = getPersoonMetOnderzoekOpOntbrekendGegeven();
        final Berichtgegevens berichtgegevens = maakBerichtgegevens(metaObject, Rol.BIJHOUDINGSORGAAN_COLLEGE, SoortSynchronisatie.VOLLEDIG_BERICHT, null);
        berichtgegevens.getParameters().setSoortDienst(SoortDienst.GEEF_MEDEBEWONERS_VAN_PERSOON);
        autoriseerGegevensInOnderzoek(berichtgegevens);
        autoriseerOnderzoek(berichtgegevens);

        service.execute(berichtgegevens);

        assertOnderzoeksgegevensNietGeautoriseerd(berichtgegevens);
    }

    /**
     * Bijhouder krijgt onderzoek op ontbrekend gegeven niet te zien, want niet geautoriseerd.
     */
    @Test
    public void test_BijhouderVolledigBericht_OntbrekendGegeven_NietGeautoriseerd() {
        final MetaObject metaObject = getPersoonMetOnderzoekOpOntbrekendGegeven();
        final Dienst dienst = getDienstMetVerkeerdeAutorisatie();
        final Berichtgegevens berichtgegevens = maakBerichtgegevens(metaObject, Rol.BIJHOUDINGSORGAAN_COLLEGE, dienst, null);

        service.execute(berichtgegevens);

        assertOnderzoeksgegevensNietGeautoriseerd(berichtgegevens);
    }


    /**
     * Vervallen onderzoek.standaard voorkomens worden geautoriseerd v afnemers.
     */
    @Test
    public void test_AfnemerVolledigBericht_VervallenOnderzoek() {
        final MetaObject metaObject = getPersoonMetVervallenOnderzoekOpAdresHuisNr();
        final Berichtgegevens berichtgegevens = maakBerichtgegevens(metaObject, Rol.AFNEMER, SoortSynchronisatie.VOLLEDIG_BERICHT, null);

        autoriseerGegevensInOnderzoek(berichtgegevens);
        autoriseerOnderzoek(berichtgegevens);

        service.execute(berichtgegevens);

        assertOnderzoeksgegevensGeautoriseerd(berichtgegevens);
    }

    /**
     * Vervallen onderzoeken worden wel geautoriseerd v bijhouders.
     */
    @Test
    public void test_BijhouderVolledigBericht_VervallenOnderzoek() {
        final MetaObject metaObject = getPersoonMetVervallenOnderzoekOpAdresHuisNr();
        final Berichtgegevens berichtgegevens = maakBerichtgegevens(metaObject, Rol.BIJHOUDINGSORGAAN_COLLEGE, SoortSynchronisatie.VOLLEDIG_BERICHT, null);

        autoriseerGegevensInOnderzoek(berichtgegevens);
        autoriseerOnderzoek(berichtgegevens);

        service.execute(berichtgegevens);

        assertOnderzoeksgegevensGeautoriseerd(berichtgegevens);
    }

    /**
     * Beeindigde onderzoeken komen enkel in volledigberichten (voor afnemers) indien ze nog geldig zijn. Dwz datumAanvangMaterieel == null of < datumEinde
     * onderzoek.
     */
    @Test
    public void test_AfnemerVolledigBericht_BeeindigdOnderzoek_DatumAanvangMaterieelLeeg() {
        final MetaObject metaObject = getPersoonMetBeeindigdOnderzoekOpAdresHuisNr();
        final Berichtgegevens berichtgegevens = maakBerichtgegevens(metaObject, Rol.AFNEMER, SoortSynchronisatie.VOLLEDIG_BERICHT, null);
        autoriseerGegevensInOnderzoek(berichtgegevens);
        autoriseerOnderzoek(berichtgegevens);

        service.execute(berichtgegevens);

        assertOnderzoeksgegevensGeautoriseerd(berichtgegevens);
    }

    /**
     * Beeindigde onderzoeken komen enkel in volledigberichten (voor afnemers) indien ze nog geldig zijn. Dwz datumAanvangMaterieel == null of < datumEinde
     * onderzoek.
     */
    @Test
    public void test_AfnemerVolledigBericht_BeeindigdOnderzoek_DatumAanvangMaterieelVoorDatumEinde() {
        final MetaObject metaObject = getPersoonMetBeeindigdOnderzoekOpAdresHuisNr();
        final int datumAanvangmaterielePeriode = 19900101; //datum aanv mat ligt NA datum einde onderzoek
        final Berichtgegevens berichtgegevens = maakBerichtgegevens(metaObject, Rol.AFNEMER, SoortSynchronisatie.VOLLEDIG_BERICHT,
                datumAanvangmaterielePeriode);

        autoriseerGegevensInOnderzoek(berichtgegevens);
        autoriseerOnderzoek(berichtgegevens);

        service.execute(berichtgegevens);

        assertOnderzoeksgegevensGeautoriseerd(berichtgegevens);
    }

    /**
     * Beeindigde onderzoeken komen in volledigberichten voor afnemers ongeacht geldigheid (dwz datumAanvangMaterieel == null of
     * datumAanvangMaterieel < datumEinde onderzoek).
     * <p>
     */
    @Test
    public void test_AfnemerVolledigBericht_BeeindigdOnderzoek_DatumAanvangMaterieelNaDatumEinde() {
        final MetaObject metaObject = getPersoonMetBeeindigdOnderzoekOpAdresHuisNr();
        final int datumAanvangmaterielePeriode = 20110101; //datum aanv mat ligt NA datum einde onderzoek
        final Berichtgegevens berichtgegevens = maakBerichtgegevens(metaObject, Rol.AFNEMER, SoortSynchronisatie.VOLLEDIG_BERICHT,
                datumAanvangmaterielePeriode);

        autoriseerGegevensInOnderzoek(berichtgegevens);
        autoriseerOnderzoek(berichtgegevens);

        service.execute(berichtgegevens);

        assertOnderzoeksgegevensNietGeautoriseerd(berichtgegevens);
    }


    /**
     * Bijhouders zien beeindigde onderzoeken, mits geautoriseerd voor het gegeven.
     */
    @Test
    public void test_BijhouderVolledigBericht_BeeindigdOnderzoek_BijhouderWelGeautoriseerd() {
        final MetaObject metaObject = getPersoonMetBeeindigdOnderzoekOpAdresHuisNr();
        final Berichtgegevens berichtgegevens = maakBerichtgegevens(metaObject, Rol.BIJHOUDINGSORGAAN_COLLEGE, getDienstMetAutorisatieOpHuisnummer(),
                null);
        autoriseerGegevensInOnderzoek(berichtgegevens);
        autoriseerOnderzoek(berichtgegevens);

        service.execute(berichtgegevens);

        assertOnderzoeksgegevensGeautoriseerd(berichtgegevens);
    }

    /**
     * Bijhouders zien beeindigde onderzoeken niet, indien niet geautoriseerd voor het gegeven.
     */
    @Test
    public void test_BijhouderVolledigBericht_BeeindigdOnderzoek_BijhouderNietGeautoriseerd() {

        final MetaObject metaObject = getPersoonMetBeeindigdOnderzoekOpAdresHuisNr();
        final Berichtgegevens berichtgegevens = maakBerichtgegevens(metaObject, Rol.BIJHOUDINGSORGAAN_COLLEGE, getDienstMetVerkeerdeAutorisatie(), null);

        service.execute(berichtgegevens);

        assertOnderzoeksgegevensNietGeautoriseerd(berichtgegevens);
    }

    /**
     * Indien een onderzoek niet geautoriseerd is, dan wordt het gegeven in onderzoek ook niet geautoriseerd.
     */
    @Test
    public void test_AfnemerVolledigBericht_GegevenInOnderzoekNietTonenAlsOnderzoekNietGetoondWordt() {
        final MetaObject metaObject = getPersoonMetOnderzoekOpOntbrekendGegeven();
        final Berichtgegevens berichtgegevens = maakBerichtgegevens(metaObject, Rol.AFNEMER, SoortSynchronisatie.VOLLEDIG_BERICHT, null);

        autoriseerGegevensInOnderzoek(berichtgegevens);

        service.execute(berichtgegevens);

        assertOnderzoeksgegevensNietGeautoriseerd(berichtgegevens);
    }

    /**
     * Mutatiebericht voor afnemer : Indien een gegeven in onderzoek een attribuut is en dit attribuut zit in delta, maar het onderzoek zelf zit niet in
     * delta, dan wordt het onderzoek toch getoond.
     */
    @Test
    public void test_AfnemerMutatieBericht_AttribuutInOnderzoek_AttribuutGeautoriseerdEnInDelta() {
        final MetaObject metaObject = getPersoonMetActueelOnderzoekOpAdresHuisNr();
        final Berichtgegevens berichtgegevens = maakBerichtgegevens(metaObject, Rol.AFNEMER, SoortSynchronisatie.MUTATIE_BERICHT, null);
        autoriseerGegevensInOnderzoek(berichtgegevens);
        autoriseerOnderzoek(berichtgegevens);
        voegOnderzoekToeAanDelta(berichtgegevens);

        service.execute(berichtgegevens);

        assertOnderzoeksgegevensGeautoriseerd(berichtgegevens);
        assertOnderzoeksgegevensInDelta(berichtgegevens);
    }

    /**
     * Beeindigde onderzoeken komen niet in mutatieberichten als ze geen deel uitmaken van de delta en niet geldig zijn volgens datum aanvang materiele
     * periode. Indien de datum aanvang materiele periode leeg is, dan wordt er wel geleverd.
     */
    @Test
    public void test_AfnemerMutatiebericht_BeeindigdOnderzoek_OnderzoekNietGeautoriseerd() {
        final MetaObject metaObject = getPersoonMetBeeindigdOnderzoekOpAdresHuisNr();
        final Berichtgegevens berichtgegevens = maakBerichtgegevens(metaObject, Rol.AFNEMER, SoortSynchronisatie.MUTATIE_BERICHT, 199901010);
        autoriseerGegevensInOnderzoek(berichtgegevens);

        service.execute(berichtgegevens);
        assertOnderzoeksgegevensNietGeautoriseerd(berichtgegevens);
        assertOnderzoeksgegevensNietInDelta(berichtgegevens);
    }


    /**
     * Beeindigde onderzoeken komen in mutatieberichten als het onderzoek geen deel uitmaakt van de delta, maar het gegeven in onderzoek wel.
     */
    @Test
    public void test_AfnemerMutatiebericht_BeeindigdOnderzoek_OnderzoekWelGeautoriseerd_RecordsNietInDelta() {
        final MetaObject metaObject = getPersoonMetBeeindigdOnderzoekOpAdresHuisNr();
        final Berichtgegevens berichtgegevens = maakBerichtgegevens(metaObject, Rol.AFNEMER, SoortSynchronisatie.MUTATIE_BERICHT, 19990101);

        autoriseerGegevensInOnderzoek(berichtgegevens);
        autoriseerOnderzoek(berichtgegevens);
        //voeg record toe aan delta
        berichtgegevens.getPersoonslijst().getMetaObject().accept(new DeltaVisitor(berichtgegevens, Element.PERSOON_ADRES_STANDAARD, 456));

        service.execute(berichtgegevens);

        assertOnderzoeksgegevensGeautoriseerd(berichtgegevens);
        assertOnderzoeksgegevensInDelta(berichtgegevens);
    }


    /**
     * Beeindigde onderzoeken komen in mutatieberichten indien onderzoek deel van de delta.
     */
    @Test
    public void test_AfnemerMutatiebericht_BeeindigdOnderzoek_RecordsWelInDelta() {
        final MetaObject metaObject = getPersoonMetBeeindigdOnderzoekOpAdresHuisNr();
        final Berichtgegevens berichtgegevens = maakBerichtgegevens(metaObject, Rol.AFNEMER, SoortSynchronisatie.MUTATIE_BERICHT, 19990101);
        voegOnderzoekToeAanDelta(berichtgegevens);
        autoriseerGegevensInOnderzoek(berichtgegevens);
        autoriseerOnderzoek(berichtgegevens);

        service.execute(berichtgegevens);

        assertOnderzoeksgegevensGeautoriseerd(berichtgegevens);
        assertOnderzoeksgegevensInDelta(berichtgegevens);
    }

    /**
     * Mutatiebericht voor afnemer : Indien een gegeven in onderzoek een attribuut is en dit attribuut zit in delta, maar het onderzoek zelf zit niet in
     * delta, dan wordt het onderzoek toch getoond.
     */
    @Test
    public void test_AfnemerMutatieBericht_AttribuutInOnderzoek_GeautoriseerdEnNietInDelta() {
        final MetaObject metaObject = getPersoonMetActueelOnderzoekOpAdresHuisNr();
        final Berichtgegevens berichtgegevens = maakBerichtgegevens(metaObject, Rol.AFNEMER, SoortSynchronisatie.MUTATIE_BERICHT, null);
        autoriseerGegevensInOnderzoek(berichtgegevens);
        autoriseerOnderzoek(berichtgegevens);
        //voeg record toe aan delta
        berichtgegevens.getPersoonslijst().getMetaObject().accept(new DeltaVisitor(berichtgegevens, Element.PERSOON_ADRES_STANDAARD, 456));

        service.execute(berichtgegevens);

        assertOnderzoeksgegevensGeautoriseerd(berichtgegevens);
        assertOnderzoeksgegevensInDelta(berichtgegevens);
    }

    /**
     * Mutatiebericht voor afnemer : Indien een gegeven in onderzoek een attribuut is en dit attribuut zit NIET in delta maar is WEL een identificerende
     * groep, dan wordt het onderzoek getoond.
     */
    @Test
    public void test_AfnemerMutatieBericht_AttribuutInOnderzoek_Geautoriseerd_NietInDelta_IdentificerendeGroep() {

        //@formatter:off
        final MetaObject metaObject = TestBuilders.maakLeegPersoon(1)

            .metGroep()
                .metGroepElement(Element.PERSOON_GEBOORTE.getId())
                .metRecord()
                    .metId(456)
                    .metActieInhoud(actieInhoud)
                    .metAttribuut(Element.PERSOON_GEBOORTE_GEMEENTECODE.getId(), 3)
                .eindeRecord()
            .eindeGroep()
            .metObject()
                .metObjectElement(Element.ONDERZOEK.getId())
                .metGroep()
                    .metGroepElement(Element.ONDERZOEK_STANDAARD.getId())
                    .metRecord()
                        .metActieInhoud(actieInhoud)
                    .eindeRecord()
                .eindeGroep()
                .metObject()
                    .metObjectElement(Element.GEGEVENINONDERZOEK.getId())
                    .metGroep()
                        .metGroepElement(Element.GEGEVENINONDERZOEK_IDENTITEIT.getId())
                        .metRecord()
                            .metActieInhoud(actieInhoud)
                            .metAttribuut(Element.GEGEVENINONDERZOEK_ELEMENTNAAM.getId(), Element.PERSOON_GEBOORTE_GEMEENTECODE.getNaam())
                            .metAttribuut(Element.GEGEVENINONDERZOEK_VOORKOMENSLEUTELGEGEVEN.getId(), 456)
                        .eindeRecord()
                    .eindeGroep()
                .eindeObject()
            .eindeObject()
        .build();
        //@formatter:on
        final Berichtgegevens berichtgegevens = maakBerichtgegevens(metaObject, Rol.AFNEMER, SoortSynchronisatie.MUTATIE_BERICHT, null);
        autoriseerGegevensInOnderzoek(berichtgegevens);
        autoriseerOnderzoek(berichtgegevens);

        service.execute(berichtgegevens);

        assertOnderzoeksgegevensGeautoriseerd(berichtgegevens);
        //onderzoek zit in delta voor mutatiebericht : attr in onderzoek zit niet in delta maar maakt wel deel uit v identificerende groep
        assertOnderzoeksgegevensInDelta(berichtgegevens);
    }

    /**
     * Mutatiebericht voor afnemer : Indien een gegeven in onderzoek een attribuut is en dit attribuut zit NIET in delta maar is GEEN identificerende
     * groep, dan wordt het onderzoek getoond.
     */
    @Test
    public void test_AfnemerMutatieBericht_AttribuutInOnderzoek_Geautoriseerd_NietInDelta_GeenIdentificerendeGroep() {

        //@formatter:off
        final MetaObject metaObject = TestBuilders.maakLeegPersoon(1)
            .metGroep()
                .metGroepElement(Element.PERSOON_NAAMGEBRUIK.getId())
                .metRecord()
                    .metId(456)
                    .metActieInhoud(actieInhoud)
                    .metAttribuut(Element.PERSOON_NAAMGEBRUIK_ADELLIJKETITELCODE.getId(), "K")
                .eindeRecord()
            .eindeGroep()
            .metObject()
                .metObjectElement(Element.ONDERZOEK.getId())
                .metGroep()
                    .metGroepElement(Element.ONDERZOEK_STANDAARD.getId())
                    .metRecord()
                        .metActieInhoud(actieInhoud)
                    .eindeRecord()
                .eindeGroep()
                .metObject()
                    .metObjectElement(Element.GEGEVENINONDERZOEK.getId())
                    .metGroep()
                        .metGroepElement(Element.GEGEVENINONDERZOEK_IDENTITEIT.getId())
                        .metRecord()
                            .metId(4567)
                            .metActieInhoud(actieInhoud)
                            .metAttribuut(Element.GEGEVENINONDERZOEK_ELEMENTNAAM.getId(), Element.PERSOON_NAAMGEBRUIK_ADELLIJKETITELCODE.getNaam())
                            .metAttribuut(Element.GEGEVENINONDERZOEK_VOORKOMENSLEUTELGEGEVEN.getId(), 456)
                        .eindeRecord()
                    .eindeGroep()
                .eindeObject()
            .eindeObject()
        .build();
        //@formatter:on
        final Berichtgegevens berichtgegevens = maakBerichtgegevens(metaObject, Rol.AFNEMER, SoortSynchronisatie.MUTATIE_BERICHT, null);
        autoriseerGegevensInOnderzoek(berichtgegevens);
        autoriseerOnderzoek(berichtgegevens);

        service.execute(berichtgegevens);

        assertOnderzoeksgegevensGeautoriseerd(berichtgegevens);
        //onderzoek zit niet in delta voor mutatiebericht : attr in onderzoek zit niet in delta en maakt geen deel uit v identificerende groep
        assertOnderzoeksgegevensNietInDelta(berichtgegevens);
    }

    /**
     * Mutatiebericht voor afnemer : Indien een gegeven in onderzoek een groep is en deze groep zit in delta, maar het onderzoek zit niet in delta, dan
     * wordt het onderzoek toch getoond.
     */
    @Test
    public void test_AfnemerMutatieBericht_GroepInOnderzoek_Geautoriseerd_EnGroepInDelta() {
        //@formatter:off
        final MetaObject metaObject = TestBuilders.maakLeegPersoon(1)
            .metObject()
                .metId(111)
                .metObjectElement(Element.PERSOON_ADRES.getId())
                .metGroep()
                    .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                    .metRecord()
                        .metId(456)
                        .metActieInhoud(actieInhoud)
                        .metAttribuut(Element.PERSOON_ADRES_HUISNUMMER.getId(), 3)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
            .metObject()
                .metObjectElement(Element.ONDERZOEK.getId())
                .metGroep()
                    .metGroepElement(Element.ONDERZOEK_STANDAARD.getId())
                    .metRecord()
                        .metActieInhoud(actieInhoud)
                    .eindeRecord()
                .eindeGroep()
                .metObject()
                    .metObjectElement(Element.GEGEVENINONDERZOEK.getId())
                    .metGroep()
                        .metGroepElement(Element.GEGEVENINONDERZOEK_IDENTITEIT.getId())
                        .metRecord()
                            .metActieInhoud(actieInhoud)
                            .metAttribuut(Element.GEGEVENINONDERZOEK_ELEMENTNAAM.getId(), Element.PERSOON_ADRES_STANDAARD.getNaam())
                            .metAttribuut(Element.GEGEVENINONDERZOEK_VOORKOMENSLEUTELGEGEVEN.getId(), 456)
                        .eindeRecord()
                    .eindeGroep()
                .eindeObject()
            .eindeObject()
        .build();
        //@formatter:on

        final Berichtgegevens berichtgegevens = maakBerichtgegevens(metaObject, Rol.AFNEMER, SoortSynchronisatie.MUTATIE_BERICHT, null);
        autoriseerGegevensInOnderzoek(berichtgegevens);
        autoriseerOnderzoek(berichtgegevens);
        new AutorisatieAlles(berichtgegevens).doVisit(metaObject);
        //voeg record toe aan delta
        berichtgegevens.getPersoonslijst().getMetaObject().accept(new DeltaVisitor(berichtgegevens, Element.PERSOON_ADRES_STANDAARD, 456));

        service.execute(berichtgegevens);

        assertOnderzoeksgegevensGeautoriseerd(berichtgegevens);
        assertOnderzoeksgegevensInDelta(berichtgegevens);
    }

    /**
     * Mutatiebericht voor afnemer : Indien een gegeven in onderzoek een object is en dit object zit in delta, maar het onderzoek zit niet in delta, dan
     * wordt het onderzoek toch getoond.
     */
    @Test
    public void test_AfnemerMutatieBericht_ObjectInOnderzoek_GeautoriseerdEnInDelta() {
        //@formatter:off
        final MetaObject metaObject = TestBuilders.maakLeegPersoon(1)
            .metObject()
                .metId(111)
                .metObjectElement(Element.PERSOON_ADRES.getId())
                .metGroep()
                    .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                    .metRecord()
                        .metId(456)
                        .metActieInhoud(actieInhoud)
                        .metAttribuut(Element.PERSOON_ADRES_HUISNUMMER.getId(), 3)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
            .metObject()
                .metObjectElement(Element.ONDERZOEK.getId())
                .metGroep()
                    .metGroepElement(Element.ONDERZOEK_STANDAARD.getId())
                    .metRecord()
                        .metActieInhoud(actieInhoud)
                    .eindeRecord()
                .eindeGroep()
                .metObject()
                    .metObjectElement(Element.GEGEVENINONDERZOEK.getId())
                    .metGroep()
                        .metGroepElement(Element.GEGEVENINONDERZOEK_IDENTITEIT.getId())
                        .metRecord()
                            .metActieInhoud(actieInhoud)
                            .metAttribuut(Element.GEGEVENINONDERZOEK_ELEMENTNAAM.getId(), Element.PERSOON_ADRES.getNaam())
                            .metAttribuut(Element.GEGEVENINONDERZOEK_OBJECTSLEUTELGEGEVEN.getId(), 111)
                        .eindeRecord()
                    .eindeGroep()
                .eindeObject()
            .eindeObject()
        .build();
        //@formatter:on

        final Berichtgegevens berichtgegevens = maakBerichtgegevens(metaObject, Rol.AFNEMER, SoortSynchronisatie.MUTATIE_BERICHT, null);
        autoriseerGegevensInOnderzoek(berichtgegevens);
        autoriseerOnderzoek(berichtgegevens);
        //voeg record toe aan delta
        berichtgegevens.getPersoonslijst().getMetaObject().accept(new DeltaVisitor(berichtgegevens, Element.PERSOON_ADRES_STANDAARD, 456));

        service.execute(berichtgegevens);

        assertOnderzoeksgegevensGeautoriseerd(berichtgegevens);
        assertOnderzoeksgegevensInDelta(berichtgegevens);
    }

    /**
     * Mutatiebericht v afnemer : g.i.o. = attr, niet geautoriseerd v attribuut, wel voor onderzoek -> geen autorisatie onderzoek.
     */
    @Test
    public void test_AfnemerMutatieBericht_AttribuutInOnderzoek_NietGeautoriseerd() {
        final MetaObject metaObject = getPersoonMetActueelOnderzoekOpAdresHuisNr();
        final Berichtgegevens berichtgegevens = maakBerichtgegevens(metaObject, Rol.AFNEMER, SoortSynchronisatie.MUTATIE_BERICHT, null);
        //autoriseer gegeven in onderzoek element maar niet het gegeven zelf
        MetaAttribuut gegevenInOnderzoekElementnaam = Iterables.getOnlyElement(berichtgegevens.getPersoonslijst().getModelIndex().geefAttributen(
                (getAttribuutElement(Element.GEGEVENINONDERZOEK_ELEMENTNAAM))));
        berichtgegevens.autoriseer(gegevenInOnderzoekElementnaam);

        autoriseerOnderzoek(berichtgegevens);

        service.execute(berichtgegevens);

        assertOnderzoeksgegevensNietGeautoriseerd(berichtgegevens);
        assertOnderzoeksgegevensNietInDelta(berichtgegevens);
    }

    /**
     * Mutatiebericht v afnemer : g.i.o. = attr, niet geautoriseerd v attribuut & onderzoek -> geen autorisatie onderzoek
     */
    @Test
    public void test_AfnemerMutatieBericht_AttribuutInOnderzoek_NietGeautoriseerdViaDienst() {
        final MetaObject metaObject = getPersoonMetActueelOnderzoekOpAdresHuisNr();
        final Berichtgegevens berichtgegevens = maakBerichtgegevens(metaObject, Rol.AFNEMER, SoortSynchronisatie.MUTATIE_BERICHT, null);

        service.execute(berichtgegevens);

        assertOnderzoeksgegevensNietGeautoriseerd(berichtgegevens);
        assertOnderzoeksgegevensNietInDelta(berichtgegevens);
    }


    /**
     * Mutatiebericht v bijhouder : g.i.o. = attr, geautoriseerd v attribuut & onderzoek + onderzoek in delta -> wel autorisatie onderzoek
     */
    @Test
    public void test_BijhouderMutatieBericht_AttribuutInOnderzoek_GeautoriseerdEnInDelta() {
        final MetaObject metaObject = getPersoonMetActueelOnderzoekOpAdresHuisNr();
        final Berichtgegevens berichtgegevens = maakBerichtgegevens(metaObject, Rol.BIJHOUDINGSORGAAN_COLLEGE, SoortSynchronisatie.MUTATIE_BERICHT, null);
        autoriseerGegevensInOnderzoek(berichtgegevens);
        autoriseerOnderzoek(berichtgegevens);
        voegOnderzoekToeAanDelta(berichtgegevens);

        service.execute(berichtgegevens);

        assertOnderzoeksgegevensGeautoriseerd(berichtgegevens);
        assertOnderzoeksgegevensInDelta(berichtgegevens);
    }

    /**
     * Mutatiebericht v bijhouder : g.i.o. = attr, niet geautoriseerd v attribuut, wel voor onderzoek / onderzoek niet in delta-> wel autorisatie
     * onderzoek
     */
    @Test
    public void test_BijhouderMutatieBericht_AttribuutInOnderzoek_GeautoriseerdEnNietInDelta() {
        final MetaObject metaObject = getPersoonMetActueelOnderzoekOpAdresHuisNr();
        final Berichtgegevens berichtgegevens = maakBerichtgegevens(metaObject, Rol.BIJHOUDINGSORGAAN_COLLEGE, SoortSynchronisatie.MUTATIE_BERICHT, null);
        autoriseerGegevensInOnderzoek(berichtgegevens);
        autoriseerOnderzoek(berichtgegevens);

        service.execute(berichtgegevens);

        assertOnderzoeksgegevensGeautoriseerd(berichtgegevens);
        //onderzoeksgegevens komen niet in mutatiebericht
        assertOnderzoeksgegevensNietInDelta(berichtgegevens);
    }


    private MetaObject getPersoonMetActueelOnderzoekOpAdresHuisNr() {
        //@formatter:off
        final MetaObject metaObject = TestBuilders.maakLeegPersoon(1)
            .metObject()
                .metId(111)
                .metObjectElement(Element.PERSOON_ADRES.getId())
                .metGroep()
                    .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                    .metRecord()
                        .metId(456)
                        .metActieInhoud(actieInhoud)
                        .metAttribuut(Element.PERSOON_ADRES_HUISNUMMER.getId(), 3)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
            .metObject()
                .metObjectElement(Element.ONDERZOEK.getId())
                .metGroep()
                    .metGroepElement(Element.ONDERZOEK_STANDAARD.getId())
                    .metRecord()
                        .metActieInhoud(actieInhoud)
                    .eindeRecord()
                .eindeGroep()
                .metObject()
                    .metObjectElement(Element.GEGEVENINONDERZOEK.getId())
                    .metGroep()
                        .metGroepElement(Element.GEGEVENINONDERZOEK_IDENTITEIT.getId())
                        .metRecord()
                            .metActieInhoud(actieInhoud)
                            .metAttribuut(Element.GEGEVENINONDERZOEK_ELEMENTNAAM.getId(), Element.PERSOON_ADRES_HUISNUMMER.getNaam())
                            .metAttribuut(Element.GEGEVENINONDERZOEK_VOORKOMENSLEUTELGEGEVEN.getId(), 456)
                        .eindeRecord()
                    .eindeGroep()
                .eindeObject()
            .eindeObject()
        .build();
        //@formatter:on
        return metaObject;
    }

    private MetaObject getPersoonMetVervallenOnderzoekOpAdresHuisNr() {
        //@formatter:off
        final MetaObject metaObject = TestBuilders.maakLeegPersoon(1)
            .metObject()
                .metId(111)
                .metObjectElement(Element.PERSOON_ADRES.getId())
                .metGroep()
                    .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                    .metRecord()
                        .metId(456)
                        .metActieInhoud(actieInhoud)
                        .metAttribuut(Element.PERSOON_ADRES_HUISNUMMER.getId(), 3)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
            .metObject()
                .metObjectElement(Element.ONDERZOEK.getId())
                .metGroep()
                    .metGroepElement(Element.ONDERZOEK_STANDAARD.getId())
                    .metRecord()
                        .metActieVerval(actieInhoud)
                    .eindeRecord()
                .eindeGroep()
                .metObject()
                    .metObjectElement(Element.GEGEVENINONDERZOEK.getId())
                    .metGroep()
                        .metGroepElement(Element.GEGEVENINONDERZOEK_IDENTITEIT.getId())
                        .metRecord()
                            .metActieInhoud(actieInhoud)
                            .metAttribuut(Element.GEGEVENINONDERZOEK_ELEMENTNAAM.getId(), Element.PERSOON_ADRES_HUISNUMMER.getNaam())
                            .metAttribuut(Element.GEGEVENINONDERZOEK_VOORKOMENSLEUTELGEGEVEN.getId(), 456)
                        .eindeRecord()
                    .eindeGroep()
                .eindeObject()
            .eindeObject()
        .build();
        //@formatter:on
        return metaObject;
    }


    private MetaObject getPersoonMetBeeindigdOnderzoekOpAdresHuisNr() {
        //@formatter:off
        final MetaObject metaObject = TestBuilders.maakLeegPersoon(1)
            .metObject()
                .metId(111)
                .metObjectElement(Element.PERSOON_ADRES.getId())
                .metGroep()
                    .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                    .metRecord()
                        .metId(456)
                        .metActieInhoud(actieInhoud)
                        .metAttribuut(Element.PERSOON_ADRES_HUISNUMMER.getId(), 3)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
            .metObject()
                .metObjectElement(Element.ONDERZOEK.getId())
                .metGroep()
                    .metGroepElement(Element.ONDERZOEK_STANDAARD.getId())
                    .metRecord()
                        .metActieInhoud(actieInhoud)
                        .metAttribuut(Element.ONDERZOEK_DATUMEINDE.getId(), 20000101)
                    .eindeRecord()
                .eindeGroep()
                .metObject()
                    .metObjectElement(Element.GEGEVENINONDERZOEK.getId())
                    .metGroep()
                        .metGroepElement(Element.GEGEVENINONDERZOEK_IDENTITEIT.getId())
                        .metRecord()
                            .metActieInhoud(actieInhoud)
                            .metAttribuut(Element.GEGEVENINONDERZOEK_ELEMENTNAAM.getId(), Element.PERSOON_ADRES_HUISNUMMER.getNaam())
                            .metAttribuut(Element.GEGEVENINONDERZOEK_VOORKOMENSLEUTELGEGEVEN.getId(), 456)
                        .eindeRecord()
                    .eindeGroep()
                .eindeObject()
            .eindeObject()
        .build();
        //@formatter:on
        return metaObject;
    }

    private MetaObject getPersoonMetOnderzoekOpOntbrekendGegeven() {
        //@formatter:off
        final MetaObject metaObject = TestBuilders.maakLeegPersoon(1)
            .metObject()
                .metObjectElement(Element.ONDERZOEK.getId())
                .metGroep()
                    .metGroepElement(Element.ONDERZOEK_STANDAARD.getId())
                    .metRecord()
                        .metActieInhoud(actieInhoud)
                    .eindeRecord()
                .eindeGroep()
                .metObject()
                    .metObjectElement(Element.GEGEVENINONDERZOEK.getId())
                    .metGroep()
                        .metGroepElement(Element.GEGEVENINONDERZOEK_IDENTITEIT.getId())
                        .metRecord()
                            .metActieInhoud(actieInhoud)
                            .metAttribuut(Element.GEGEVENINONDERZOEK_ELEMENTNAAM.getId(), Element.PERSOON_ADRES_HUISNUMMER.getNaam())
                        .eindeRecord()
                    .eindeGroep()
                .eindeObject()
            .eindeObject()
        .build();
        //@formatter:on
        return metaObject;
    }

    private void voegOnderzoekToeAanDelta(final Berichtgegevens berichtgegevens) {
        for (final Onderzoekbundel onderzoekbundel : berichtgegevens.getPersoonslijst()
                .getOnderzoekIndex().getGegevensInOnderzoek().keySet()) {
            final MetaObject onderzoek = onderzoekbundel.getOnderzoek();
            for (MetaGroep metaGroep : onderzoek.getGroepen()) {
                for (MetaRecord metaRecord : metaGroep.getRecords()) {
                    berichtgegevens.addDeltaRecord(metaRecord);
                }
            }
        }
    }

    private void autoriseerOnderzoek(final Berichtgegevens berichtgegevens) {
        final OnderzoekIndex onderzoekIndex = berichtgegevens.getPersoonslijst().getOnderzoekIndex();
        final MetaObject onderzoek = onderzoekIndex.getAlleGegevensInOnderzoek().get(0).getOnderzoek();
        for (MetaGroep metaGroep : onderzoek.getGroepen()) {
            for (MetaRecord metaRecord : metaGroep.getRecords()) {
                berichtgegevens.autoriseer(metaRecord);
            }
        }
    }

    private void autoriseerGegevensInOnderzoek(final Berichtgegevens berichtgegevens) {
        final OnderzoekIndex onderzoekIndex = berichtgegevens.getPersoonslijst().getOnderzoekIndex();
        //autoriseer gegeven in onderzoek element
        MetaAttribuut gegevenInOnderzoekElementnaam = Iterables.getOnlyElement(berichtgegevens.getPersoonslijst().getModelIndex().geefAttributen(
                (getAttribuutElement(Element.GEGEVENINONDERZOEK_ELEMENTNAAM))));
        berichtgegevens.autoriseer(gegevenInOnderzoekElementnaam);

        for (MetaModel metaModel : onderzoekIndex.getGegevensInOnderzoek().values()) {
            //autoriseer het aangewezen attribuut
            berichtgegevens.autoriseer(metaModel);
        }
    }

    private void assertOnderzoeksgegevensGeautoriseerd(final Berichtgegevens berichtgegevens) {
        final Persoonslijst persoonslijst = berichtgegevens.getPersoonslijst();
        final Onderzoekbundel onderzoekbundel = persoonslijst.getOnderzoekIndex().getAlleGegevensInOnderzoek().iterator().next();
        //onderzoek bevat geg. in ond.
        Assert.assertFalse(persoonslijst.getOnderzoekIndex().getAlleGegevensInOnderzoek().isEmpty());
        //onderzoek is geautoriseerd
        Assert.assertTrue(berichtgegevens.getGeautoriseerdeObjecten().contains(onderzoekbundel.getOnderzoek()));
        Assert.assertTrue(berichtgegevens.isGeautoriseerd(onderzoekbundel.getOnderzoek()));
        // gegeven in onderzoek is geautoriseerd
        Assert.assertTrue(berichtgegevens.isGeautoriseerd(onderzoekbundel.getGegevenInOnderzoek()));
    }


    private void assertOnderzoeksgegevensNietGeautoriseerd(final Berichtgegevens berichtgegevens) {
        final Persoonslijst persoonslijst = berichtgegevens.getPersoonslijst();
        final Onderzoekbundel onderzoekbundel = persoonslijst.getOnderzoekIndex().getAlleGegevensInOnderzoek().iterator().next();

        Assert.assertFalse(persoonslijst.getOnderzoekIndex().getAlleGegevensInOnderzoek().isEmpty());
        Assert.assertFalse(berichtgegevens.getGeautoriseerdeObjecten().contains(onderzoekbundel.getOnderzoek()));
        Assert.assertFalse(berichtgegevens.isGeautoriseerd(onderzoekbundel.getGegevenInOnderzoek()));
        Assert.assertFalse(berichtgegevens.isGeautoriseerd(onderzoekbundel.getOnderzoek()));
    }

    private void assertOnderzoeksgegevensNietInDelta(final Berichtgegevens berichtgegevens) {
        MetaGroep gioIdentiteit = Iterables.getOnlyElement(berichtgegevens.getPersoonslijst().getModelIndex().geefGroepenVanElement(
                ElementHelper.getGroepElement(Element.GEGEVENINONDERZOEK_IDENTITEIT)));
        MetaRecord gioRecord = Iterables.getOnlyElement(gioIdentiteit.getRecords());
        Assert.assertFalse(berichtgegevens.getDeltaRecords().contains(gioRecord));
    }

    private void assertOnderzoeksgegevensInDelta(final Berichtgegevens berichtgegevens) {
        MetaGroep gioIdentiteit = Iterables.getOnlyElement(berichtgegevens.getPersoonslijst().getModelIndex().geefGroepenVanElement(
                ElementHelper.getGroepElement(Element.GEGEVENINONDERZOEK_IDENTITEIT)));
        MetaRecord gioRecord = Iterables.getOnlyElement(gioIdentiteit.getRecords());
        Assert.assertTrue(berichtgegevens.getDeltaRecords().contains(gioRecord));
    }

    private Dienst getDienstMetAutorisatieOpHuisnummer() {
        final TestAutorisaties.GroepDefinitie groepDefinitie = new TestAutorisaties.GroepDefinitie();
        groepDefinitie.element = Element.PERSOON_ADRES_STANDAARD;
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties
                .maakLeveringsautorisatie(Element.PERSOON_ADRES_HUISNUMMER, SoortDienst.ATTENDERING, groepDefinitie);
        return AutAutUtil.zoekDienst(leveringsautorisatie, SoortDienst.ATTENDERING);
    }

    private Dienst getDienstMetVerkeerdeAutorisatie() {
        final TestAutorisaties.GroepDefinitie groepDefinitie = new TestAutorisaties.GroepDefinitie();
        groepDefinitie.element = Element.PERSOON_GEBOORTE;
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties
                .maakLeveringsautorisatie(Element.PERSOON_GEBOORTE_DATUM, SoortDienst.ATTENDERING, groepDefinitie);
        return AutAutUtil.zoekDienst(leveringsautorisatie, SoortDienst.ATTENDERING);
    }


    private Berichtgegevens maakBerichtgegevens(final MetaObject metaObject, final Rol rol, final Dienst dienst, final Integer
            datumAanvangMaterielePeriode) {
        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final Persoonslijst persoonslijst = new Persoonslijst(metaObject, 0L);
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(
                TestAutorisaties.maak(rol, dienst), dienst);
        maakBerichtParameters.setAutorisatiebundels(Lists.newArrayList(autorisatiebundel));
        final MaakBerichtPersoonInformatie maakBerichtPersoonInformatie = new MaakBerichtPersoonInformatie(SoortSynchronisatie.VOLLEDIG_BERICHT);
        maakBerichtPersoonInformatie.setDatumAanvangmaterielePeriode(datumAanvangMaterielePeriode);
        Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, persoonslijst, maakBerichtPersoonInformatie, autorisatiebundel,
                new StatischePersoongegevens());
        return berichtgegevens;
    }

    private Berichtgegevens maakBerichtgegevens(MetaObject metaObject, Rol rol, SoortSynchronisatie soortSynchronisatie, final Integer
            datumAanvangMaterielePeriode) {
        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final Persoonslijst persoonslijst = new Persoonslijst(metaObject, 0L);
        final Dienst dienst = AutAutUtil.zoekDienst(TestAutorisaties.metSoortDienst(SoortDienst.SYNCHRONISATIE_PERSOON), SoortDienst
                .SYNCHRONISATIE_PERSOON);
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(
                TestAutorisaties.maak(rol, dienst), dienst);
        maakBerichtParameters.setAutorisatiebundels(Lists.newArrayList(autorisatiebundel));
        final MaakBerichtPersoonInformatie maakBerichtPersoonInformatie = new MaakBerichtPersoonInformatie(soortSynchronisatie);
        maakBerichtPersoonInformatie.setDatumAanvangmaterielePeriode(datumAanvangMaterielePeriode);
        Berichtgegevens berichtgegevens =
                new Berichtgegevens(maakBerichtParameters, persoonslijst, maakBerichtPersoonInformatie, autorisatiebundel, new StatischePersoongegevens());
        return berichtgegevens;
    }

    private static final class DeltaVisitor extends ParentFirstModelVisitor {

        private final Berichtgegevens berichtgegevens;
        private final Element element;
        private final Long voorkomenSleutel;

        DeltaVisitor(final Berichtgegevens berichtgegevens, final Element element, final long voorkomenSleutel) {
            this.berichtgegevens = berichtgegevens;
            this.element = element;
            this.voorkomenSleutel = voorkomenSleutel;
        }

        @Override
        protected void doVisit(final MetaRecord metaRecord) {
            if (metaRecord.getParentGroep().getGroepElement().getId().intValue() == element.getId() && voorkomenSleutel.longValue() == metaRecord
                    .getVoorkomensleutel()) {
                berichtgegevens.autoriseer(metaRecord);
                berichtgegevens.addDeltaRecord(metaRecord);
            }
        }
    }
}
