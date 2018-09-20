package nl.bzk.brp.datataal.handlers.persoon

import groovy.transform.SelfType
import nl.bzk.brp.datataal.handlers.GegevensAfleider
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdellijkeTitelCodeAttribuut
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PredicaatCodeAttribuut
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonVoornaamHisVolledigImpl
import nl.bzk.brp.model.operationeel.kern.ActieModel
import nl.bzk.brp.util.hisvolledig.kern.PersoonGeslachtsnaamcomponentHisVolledigImplBuilder
import nl.bzk.brp.util.hisvolledig.kern.PersoonVoornaamHisVolledigImplBuilder

/**
 *
 */
@SelfType(AbstractGebeurtenisHandler)
trait WijzigNamenAbility {
    private final ActieModel voornaamActie = maakActie(SoortActie.REGISTRATIE_VOORNAAM)
    /**
     * Alles rondom namen van een persoon.
     * @param c
     * @return
     */
    def namen(@DelegatesTo(WijzigNamenAbility) Closure c) {
        c()

        GegevensAfleider.leidSamengesteldeNaamAf(builder, voornaamActie)
        GegevensAfleider.leidNaamgebruikAf(builder, voornaamActie)
    }

    def voornamen(String... namen) {
        def persoon = builder.hisVolledigImpl as PersoonHisVolledigImpl

        // bijwerken bestaande namen
        persoon.voornamen.eachWithIndex {PersoonVoornaamHisVolledigImpl entry, int i ->
            def nieuweNaam = namen.getAt(i)

            if (nieuweNaam) {
                new PersoonVoornaamHisVolledigImplBuilder(entry)
                    .nieuwStandaardRecord(voornaamActie)
                    .naam(nieuweNaam)
                    .eindeRecord()
                    .build()
            } else {
                entry.persoonVoornaamHistorie.beeindig(voornaamActie, voornaamActie)
            }
        }

        namen.takeRight(namen.size() - persoon.voornamen.size()).each { String naam ->
            builder.voegPersoonVoornaamToe(new PersoonVoornaamHisVolledigImplBuilder(builder.hisVolledigImpl, new VolgnummerAttribuut(persoon.voornamen.size() + 1))
                .nieuwStandaardRecord(voornaamActie)
                .naam(naam)
                .eindeRecord()
                .build())
        }
    }

    def geslachtsnaam(Map<String, String> naam) {
        def geslachtsnaamComponenten = ['predicaat', 'adellijkeTitel', 'stam', 'voorvoegsel', 'scheidingsteken']
        valideerMapKeys(naam, geslachtsnaamComponenten, 'alleen ${keys} zijn toegestaan')

        def persoon = builder.hisVolledigImpl as PersoonHisVolledigImpl
        def actie = maakActie(SoortActie.REGISTRATIE_GESLACHTSNAAM)

        def geslachtsnaamBuilder
        if (persoon.geslachtsnaamcomponenten.isEmpty()) {
            geslachtsnaamBuilder = new PersoonGeslachtsnaamcomponentHisVolledigImplBuilder(builder.hisVolledigImpl, new VolgnummerAttribuut(1))
        } else {
            new PersoonGeslachtsnaamcomponentHisVolledigImplBuilder(persoon.geslachtsnaamcomponenten.first())
        }

        def record = geslachtsnaamBuilder.nieuwStandaardRecord(actie)

        record.with {
            if (naam.stam) record.stam(naam.stam)
            if (naam.predicaat) predicaat(referentieData.vindPredicaatOpCode(new PredicaatCodeAttribuut(naam.predicaat)))
            if (naam.adellijkeTitel) adellijkeTitel(referentieData.vindAdellijkeTitelOpCode(new AdellijkeTitelCodeAttribuut(naam.adellijkeTitel)))
            if (naam.voorvoegsel) voorvoegsel(naam.voorvoegsel)
            if (naam.scheidingsteken) scheidingsteken(naam.scheidingsteken)
        }

        builder.voegPersoonGeslachtsnaamcomponentToe(record.eindeRecord().build())
    }

    def geslachtsnaam(String stam) {
        geslachtsnaam([stam: stam])
    }
}
