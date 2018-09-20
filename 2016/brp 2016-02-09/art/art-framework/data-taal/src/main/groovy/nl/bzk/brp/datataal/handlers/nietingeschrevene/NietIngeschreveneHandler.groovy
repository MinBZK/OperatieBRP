package nl.bzk.brp.datataal.handlers.nietingeschrevene

import nl.bzk.brp.datataal.handlers.GegevensAfleider
import nl.bzk.brp.datataal.handlers.AbstractAdmhndHandler
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
 * Handler voor het beschrijven van een niet ingeschreven persoon.
 * Deze personen hebben slechts een paar groepen: samengesteldenaam,
 * geslachtsaanduiding en geboorte. Voor het vastleggen van de persoon
 * wordt een "dummy" administratieve handeling gemaakt van het type "Conversie GBA".
 */
class NietIngeschreveneHandler extends AbstractAdmhndHandler {
    final AdministratieveHandelingModel admhnd
    final ActieModel actie

    NietIngeschreveneHandler(final GebeurtenisAttributen attr, final PersoonHisVolledigImplBuilder b) {
        super(attr, b)

        admhnd = genereerHandeling(SoortAdministratieveHandeling.G_B_A_INITIELE_VULLING)
        actie = maakActie(SoortActie.CONVERSIE_G_B_A, admhnd)

        DatumTijdAttribuut registratieDatum = geefEvtMeegegevenRegistratieDatum()
        if (registratieDatum != null) {
            ReflectionTestUtils.setField(actie, "tijdstipRegistratie", registratieDatum)
            ReflectionTestUtils.setField(admhnd, "tijdstipRegistratie", registratieDatum)
        }

        // voeg een afgeleid adm. toe om de persoon met de PersoonHibernatePersister te kunnen opslaan
        builder.hisVolledigImpl.persoonAfgeleidAdministratiefHistorie.voegToe(GegevensAfleider.maakAfgeleidAdministratief(builder.hisVolledigImpl, actie, 1))
    }

    def geboorte(@DelegatesTo(NietIngeschreveneHandler) Closure c) {
        c()
    }

    def op(def dat) {
        def datum = bepaalDatum(dat)
        def record = builder.nieuwGeboorteRecord(actie)
            .datumGeboorte(datum)

        [te: {String plaats ->
            [gemeente: {def gem ->
                def gemeente = bepaalGemeente(gem)

                record
                    .woonplaatsnaamGeboorte(plaats)
                    .gemeenteGeboorte(gemeente)
                    .landGebiedGeboorte(referentieData.nederland)
                    .eindeRecord()
            },
            land: {def land ->
                def landgebied = bepaalLand(land)
                record.buitenlandsePlaatsGeboorte(plaats)
                    .landGebiedGeboorte(landgebied)
                    .eindeRecord()
            }]
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
}
