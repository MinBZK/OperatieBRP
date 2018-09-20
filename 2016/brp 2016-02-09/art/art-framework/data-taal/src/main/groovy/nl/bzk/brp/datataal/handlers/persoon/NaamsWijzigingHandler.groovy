package nl.bzk.brp.datataal.handlers.persoon

import nl.bzk.brp.datataal.handlers.GegevensAfleider
import nl.bzk.brp.datataal.model.GebeurtenisAttributen
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocument
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocumentAttribuut
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonGeslachtsnaamcomponentHisVolledigImpl
import nl.bzk.brp.model.operationeel.kern.ActieBronModel
import nl.bzk.brp.model.operationeel.kern.ActieModel
import nl.bzk.brp.model.operationeel.kern.DocumentModel
import nl.bzk.brp.util.hisvolledig.kern.PersoonGeslachtsnaamcomponentHisVolledigImplBuilder

/**
 *
 */
class NaamsWijzigingHandler extends AbstractGebeurtenisHandler {

    private final ActieModel wijzigingActie

    /**
     * Constructor.
     *
     * @param attr
     * @param b persoonHisVolledig builder
     */
    NaamsWijzigingHandler(final GebeurtenisAttributen attr, final def Object b) {
        super(attr, b)

        wijzigingActie = maakActie(SoortActie.DUMMY)
    }

    @Override
    void startGebeurtenis() {
        builder.hisVolledigImpl.persoonAfgeleidAdministratiefHistorie.voegToe(GegevensAfleider.maakAfgeleidAdministratief(builder.hisVolledigImpl, wijzigingActie, 1))
    }

    def geslachtsnaam(Map<String, String> oud) {
        [wordt: {Map<String, String> nieuw ->
            PersoonGeslachtsnaamcomponentHisVolledigImpl geslachtsnaamcomponentHisVolledig = builder.hisVolledigImpl.geslachtsnaamcomponenten.find {
                def actueel = it.getPersoonGeslachtsnaamcomponentHistorie().actueleRecord
                return oud.every() { k, v ->
                    actueel.getAt(k)?.waarde == v
                }
            }

            nieuweGeslachtsnaam(geslachtsnaamcomponentHisVolledig, nieuw)
        }]
    }

    def document(Map<String, String> doc) {
        SoortDocument koninklijkBesluit =
            new SoortDocument(NaamEnumeratiewaardeAttribuut.DOCUMENT_NAAM_KONINKLIJK_BESLUIT, null, null);
        DocumentModel documentModel = new DocumentModel(new SoortDocumentAttribuut(koninklijkBesluit));

        //DocumentModel documentModel = referentieData.vindDocumentOpAktenummer(new AktenummerAttribuut(doc.aktenummer as String))

        def actieBron = new ActieBronModel(wijzigingActie, documentModel, null, new OmschrijvingEnumeratiewaardeAttribuut(doc.grond as String))

        wijzigingActie.bronnen.add(actieBron)

    }

    private void nieuweGeslachtsnaam(def geslachtsnaamcomponentHisVolledig, Map<String, String> map) {
        if (!geslachtsnaamcomponentHisVolledig) {
            throw new IllegalStateException('Er is geen geldige geslachtsnaamcomponent gevonden om te wijzigen')
        }

        wijzigingActie.soort.waarde = SoortActie.REGISTRATIE_NAAM_GESLACHT
        admhnd.soort.waarde = SoortAdministratieveHandeling.WIJZIGING_GESLACHTSNAAM

        def geslachtsnaamComponenten = ['predicaat', 'adellijkeTitel', 'stam', 'voorvoegsel', 'scheidingsteken']
        valideerMapKeys(map, geslachtsnaamComponenten, 'alleen ${keys} zijn toegestaan')

        def record = new PersoonGeslachtsnaamcomponentHisVolledigImplBuilder(geslachtsnaamcomponentHisVolledig)
            .nieuwStandaardRecord(wijzigingActie)

        record.with {
            if (map.stam) stam(map.stam as String)
            if (map.voorvoegsel) voorvoegsel(map.voorvoegsel as String)
            if (map.scheidingsteken) scheidingsteken(map.scheidingsteken as String)
            if (map.predicaat) predicaat(map.predicaat as String)
            if (map.adellijkeTitel) adellijkeTitel(map.adellijkeTitel as String)
        }

        if (!map.stam) {
            record.stam(builder.hisVolledigImpl.geslachtsnaamcomponenten.collect {it.persoonGeslachtsnaamcomponentHistorie.actueleRecord.stam?.waarde}.find())
        }

        builder.hisVolledigImpl.getAdministratieveHandelingen().add(admhnd)

        record.eindeRecord().build()

        GegevensAfleider.leidSamengesteldeNaamAf(builder, wijzigingActie)
        GegevensAfleider.leidNaamgebruikAf(builder, wijzigingActie)
        GegevensAfleider.pasBetrokkenenAfgeleidAdministratiefAan(builder, wijzigingActie)
    }

    def geslachtsnaam(Integer naam) {
        [wordt: {Map<String, String> nieuw ->
            PersoonGeslachtsnaamcomponentHisVolledigImpl geslachtsnaamcomponentHisVolledig = builder.hisVolledigImpl.geslachtsnaamcomponenten.getAt(naam - 1)

            nieuweGeslachtsnaam(geslachtsnaamcomponentHisVolledig, nieuw)
        }]
    }
}
