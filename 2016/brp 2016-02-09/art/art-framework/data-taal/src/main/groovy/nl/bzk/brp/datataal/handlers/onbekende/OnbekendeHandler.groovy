package nl.bzk.brp.datataal.handlers.onbekende

import nl.bzk.brp.datataal.handlers.AbstractAdmhndHandler
import nl.bzk.brp.datataal.handlers.GegevensAfleider
import nl.bzk.brp.datataal.model.GebeurtenisAttributen
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdellijkeTitelCodeAttribuut
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PredicaatCodeAttribuut
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling
import nl.bzk.brp.model.operationeel.kern.ActieModel
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder
import org.springframework.test.util.ReflectionTestUtils

/**
 * Handler voor het beschrijven van een onbekend persoon.
 * Deze personen hebben slechts een aantal groepen: samengestelde naam, geslachtsaanduiding, geboorte en identificatienummers. Voor het vastleggen van de persoon wordt een administratieve handeling gemaakt van het type "Conversie GBA".
 */
class OnbekendeHandler extends AbstractAdmhndHandler {

    final AdministratieveHandelingModel admhnd
    final ActieModel actie

    /**
     * Maak een handler aan, met een persoonbuilder en gebruikte waardes voor de
     * basis informatie voor een administratieve handeling en of acties.
     *
     * @param attr de waardes voor adminstratieve handeling
     * @param b de persoonHisVolledigBuilder om een persoon te maken
     */
    OnbekendeHandler(final GebeurtenisAttributen attr, final PersoonHisVolledigImplBuilder b) {
        super(attr, b)

        admhnd = genereerHandeling(SoortAdministratieveHandeling.G_B_A_INITIELE_VULLING)
        actie = maakActie(SoortActie.CONVERSIE_G_B_A, admhnd)

        DatumTijdAttribuut registratieDatum = geefEvtMeegegevenRegistratieDatum()
        if (registratieDatum != null) {
            ReflectionTestUtils.setField(actie, "tijdstipRegistratie", registratieDatum);
            ReflectionTestUtils.setField(admhnd, "tijdstipRegistratie", registratieDatum);
        }

        // voeg een afgeleid adm. toe om de persoon met de PersoonHibernatePersister te kunnen opslaan
        builder.hisVolledigImpl.persoonAfgeleidAdministratiefHistorie.voegToe(GegevensAfleider.maakAfgeleidAdministratief(builder.hisVolledigImpl, actie, 1))
    }

    def geboorte(@DelegatesTo(OnbekendeHandler) Closure c) {
        c()
    }

    def op(def dat) {
        def datum = bepaalDatum(dat)
        def record = builder.nieuwGeboorteRecord(actie).datumGeboorte(datum)
        [te: { String plaats ->
            [gemeente: { def gem ->
                def gemeente = bepaalGemeente(gem)
                record.woonplaatsnaamGeboorte(plaats).gemeenteGeboorte(gemeente).landGebiedGeboorte(referentieData.nederland).eindeRecord()
            }, land  : { def land ->
                def landgebied = bepaalLand(land)
                record.buitenlandsePlaatsGeboorte(plaats).landGebiedGeboorte(landgebied).eindeRecord()
            }
            ]
        }]
    }

    def samengesteldeNaam(Map<String, String> m) {
        def samengesteldeNaamComponenten = ['voornamen', 'predicaat', 'adellijkeTitel', 'stam', 'voorvoegsel', 'scheidingsteken']
        valideerMapKeys(m, samengesteldeNaamComponenten, 'alleen ${keys} zijn toegestaan')

        def record = builder.nieuwSamengesteldeNaamRecord(actie)

        record.with {
            if (m.voornamen) voornamen(m.voornamen)
            if (m.stam) geslachtsnaamstam(m.stam)
            if (m.predicaat) predicaat(referentieData.vindPredicaatOpCode(new PredicaatCodeAttribuut(m.predicaat)))
            if (m.adellijkeTitel) adellijkeTitel(referentieData.vindAdellijkeTitelOpCode(new AdellijkeTitelCodeAttribuut(m.adellijkeTitel)))
            if (m.voorvoegsel) voorvoegsel(m.voorvoegsel)
            if (m.scheidingsteken) scheidingsteken(m.scheidingsteken)
        }

        record.indicatieAfgeleid(Boolean.FALSE).indicatieNamenreeks(Boolean.FALSE).eindeRecord()
    }

    def geslacht(String geslacht) {
        assert Geslachtsaanduiding.values().collect { it.name() }.contains(geslacht)

        builder.nieuwGeslachtsaanduidingRecord(actie).geslachtsaanduiding(Geslachtsaanduiding.valueOf(geslacht)).eindeRecord()
    }


    def identificatienummers(Map<String, ? extends Number> nums) {
        def actie = maakActie(SoortActie.REGISTRATIE_IDENTIFICATIENUMMERS, admhnd)

        assert nums.keySet().sort() == ['anummer', 'bsn']: 'anummer en bsn zijn verplicht'

        def anummer = nums.anummer as long
        def bsn = nums.bsn as int
        builder.nieuwIdentificatienummersRecord(actie).burgerservicenummer(bsn).administratienummer(anummer).eindeRecord()
    }
}
