package nl.bzk.brp.datataal.handlers.persoon.afstamming

import nl.bzk.brp.datataal.handlers.GegevensAfleider
import nl.bzk.brp.datataal.handlers.persoon.VerstrekkingsbeperkingHandler
import nl.bzk.brp.datataal.model.GebeurtenisAttributen
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdellijkeTitelCodeAttribuut
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PredicaatCodeAttribuut
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenVerkrijgingCodeAttribuut
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenWijzigingVerblijfCodeAttribuut
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Bijhoudingsaard
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdres
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaard
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
import nl.bzk.brp.model.operationeel.kern.ActieModel
import nl.bzk.brp.model.operationeel.kern.HisPersoonAdresModel
import nl.bzk.brp.util.hisvolledig.kern.FamilierechtelijkeBetrekkingHisVolledigImplBuilder
import nl.bzk.brp.util.hisvolledig.kern.KindHisVolledigImplBuilder
import nl.bzk.brp.util.hisvolledig.kern.OuderHisVolledigImplBuilder
import nl.bzk.brp.util.hisvolledig.kern.PersoonAdresHisVolledigImplBuilder
import nl.bzk.brp.util.hisvolledig.kern.PersoonGeslachtsnaamcomponentHisVolledigImplBuilder
import nl.bzk.brp.util.hisvolledig.kern.PersoonNationaliteitHisVolledigImplBuilder
import nl.bzk.brp.util.hisvolledig.kern.PersoonVoornaamHisVolledigImplBuilder

/**
 * Handler voor het behandelen van een gebeurtenis "GEBOORTE".
 */
class GeboorteHandler extends AbstractAfstammingHandler {
    private final ActieModel geboorteActie

    def onbekend = null

    GeboorteHandler(GebeurtenisAttributen m, def builder) {
        super(m, builder)

        geboorteActie = maakActie(SoortActie.REGISTRATIE_GEBOORTE)
    }

    @Override
    ActieModel getOuderActie() {
        return geboorteActie
    }

    @Override
    void startGebeurtenis() {
        // bijhouding voorkomen
        builder.nieuwBijhoudingRecord(geboorteActie).bijhoudingsaard(Bijhoudingsaard.INGEZETENE)
            .nadereBijhoudingsaard(NadereBijhoudingsaard.ACTUEEL)
            .bijhoudingspartij(geboorteActie.partij.waarde)
            .indicatieOnverwerktDocumentAanwezig(Boolean.FALSE)
            .eindeRecord()

        // nationaliteit voorkomen
        builder.voegPersoonNationaliteitToe(
            new PersoonNationaliteitHisVolledigImplBuilder(referentieData.vindNationaliteitOpCode(1 as Short))
                .nieuwStandaardRecord(geboorteActie)
                .redenVerkrijging(referentieData.vindRedenVerkregenNlNationaliteitOpCode(new RedenVerkrijgingCodeAttribuut(17 as Short)))
                .eindeRecord()
            .build())

        builder.hisVolledigImpl.persoonAfgeleidAdministratiefHistorie.voegToe(GegevensAfleider.maakAfgeleidAdministratief(builder.hisVolledigImpl, geboorteActie, 1))
    }

    def jongen() {
        geslacht('MAN')
    }

    def meisje() {
        geslacht('VROUW')
    }

    def geslacht(String geslacht) {
        assert Geslachtsaanduiding.values().collect { it.name() }.contains(geslacht)

        builder.nieuwGeslachtsaanduidingRecord(geboorteActie).geslachtsaanduiding(Geslachtsaanduiding.valueOf(geslacht)).eindeRecord()
    }

    def identificatienummers(Map<String, ? extends Number> nums) {
        def actie = maakActie(SoortActie.REGISTRATIE_IDENTIFICATIENUMMERS)

        assert nums.keySet().sort() == ['anummer', 'bsn'] : 'anummer en bsn zijn verplicht'

        def anummer = nums.anummer as long
        def bsn = nums.bsn as int
        builder.nieuwIdentificatienummersRecord(actie).burgerservicenummer(bsn).administratienummer(anummer).eindeRecord()
    }

    def verstrekkingsbeperking(@DelegatesTo(VerstrekkingsbeperkingHandler) Closure c) {
        def clone = (Closure) c.clone()

        clone.delegate = new VerstrekkingsbeperkingHandler(this.handelingDefaults, this.builder, this.admhnd)
        clone.resolveStrategy = Closure.DELEGATE_ONLY

        clone.call()
    }

    /**
     * Alles rondom namen van een persoon.
     * @param c
     * @return
     */
    def namen(@DelegatesTo(GeboorteHandler) Closure c) {
        c()

        if (builder.hisVolledigImpl.persoonSamengesteldeNaamHistorie.aantal == 0) {
            GegevensAfleider.leidSamengesteldeNaamAf(builder, geboorteActie)
        }

        GegevensAfleider.leidNaamgebruikAf(builder, geboorteActie)
    }

    def voornamen(String... namen) {
        namen.eachWithIndex { String naam, idx ->
            builder.voegPersoonVoornaamToe(new PersoonVoornaamHisVolledigImplBuilder(builder.hisVolledigImpl, new VolgnummerAttribuut(idx + 1))
                .nieuwStandaardRecord(geboorteActie)
                .naam(naam)
                .eindeRecord()
                .build())
        }
    }

    def geslachtsnaam(Map<String, String> naam) {
        if (aantalGeslachtsnaamComponenten > 0) {
            throw new IllegalStateException('Er is reeds een geslachtsnaam vastgelegd')
        }

        def geslachtsnaamComponenten = ['predicaat', 'adellijkeTitel', 'stam', 'voorvoegsel', 'scheidingsteken']

        valideerMapKeys(naam, geslachtsnaamComponenten, 'alleen ${keys} zijn toegestaan')

        def record = new PersoonGeslachtsnaamcomponentHisVolledigImplBuilder(new VolgnummerAttribuut(1))
            .nieuwStandaardRecord(geboorteActie)

        record.with {
            if (naam.stam) stam(naam.stam)
            if (naam.predicaat) predicaat(referentieData.vindPredicaatOpCode(new PredicaatCodeAttribuut(naam.predicaat)))
            if (naam.adellijkeTitel) adellijkeTitel(referentieData.vindAdellijkeTitelOpCode(new AdellijkeTitelCodeAttribuut(naam.adellijkeTitel)))
            if (naam.voorvoegsel) voorvoegsel(naam.voorvoegsel)
            if (naam.scheidingsteken) scheidingsteken(naam.scheidingsteken)
        }

        builder.voegPersoonGeslachtsnaamcomponentToe(record.eindeRecord().build())
    }

    def geslachtsnaam(String naam) {
        geslachtsnaam([stam: naam])
    }

    def samengesteldeNaam(Map<String, String> m) {
        def samengesteldeNaamComponenten = ['voornamen', 'predicaat', 'adellijkeTitel', 'stam', 'voorvoegsel', 'scheidingsteken']
        valideerMapKeys(m, samengesteldeNaamComponenten, 'alleen ${keys} zijn toegestaan')

        def record = builder.nieuwSamengesteldeNaamRecord(geboorteActie)

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

    def ouders(Map<String, PersoonHisVolledigImpl> oudersMap) {
        valideerMapKeys(oudersMap, ['moeder', 'vader'], "Ouders kunnen alleen 'moeder' en 'vader' bevatten")

        def moeder = oudersMap.moeder
        def vader  = oudersMap.vader
        def familie = new FamilierechtelijkeBetrekkingHisVolledigImplBuilder().nieuwStandaardRecord(geboorteActie).eindeRecord().build()

        def kind = new KindHisVolledigImplBuilder(familie, builder.hisVolledigImpl).metVerantwoording(geboorteActie).build()
        builder.voegBetrokkenheidToe(kind)

        if (oudersMap.containsKey('moeder')) {
            // moeder
            if (moeder?.isIngeschrevene()) {
                moeder.persoonAfgeleidAdministratiefHistorie.voegToe(GegevensAfleider.maakAfgeleidAdministratief(moeder, geboorteActie, 2))
            }
            familie.betrokkenheden << new OuderHisVolledigImplBuilder(familie, moeder)
                .metVerantwoording(geboorteActie)
                .nieuwOuderschapRecord(geboorteActie)
                .indicatieOuderUitWieKindIsGeboren(true)
                .indicatieOuder(Ja.J)
                .eindeRecord()
                .build()
        }

        // adresgevende ouder
        if (moeder && moeder.isIngeschrevene() && !moeder.adressen.isEmpty() && builder.hisVolledigImpl.adressen.isEmpty()) {

            def record = new PersoonAdresHisVolledigImplBuilder(builder.hisVolledigImpl).nieuwStandaardRecord(geboorteActie)
            record
                .soort(FunctieAdres.WOONADRES)
                .datumAanvangAdreshouding(geboorteActie.datumAanvangGeldigheid)
                .redenWijziging(referentieData.vindRedenWijzingVerblijfOpCode(new RedenWijzigingVerblijfCodeAttribuut('A')))

            HisPersoonAdresModel adres = moeder.adressen.first().persoonAdresHistorie.actueleRecord
            adres.with {
                if (landGebied) record.landGebied(landGebied.waarde)
                if (gemeente) record.gemeente(gemeente.waarde)
                if (woonplaatsnaam) record.woonplaatsnaam(woonplaatsnaam)
                if (postcode) record.postcode(postcode)
                if (naamOpenbareRuimte) record.naamOpenbareRuimte(naamOpenbareRuimte)
                if (afgekorteNaamOpenbareRuimte) record.afgekorteNaamOpenbareRuimte(adres.afgekorteNaamOpenbareRuimte)
                if (huisnummer) record.huisnummer(huisnummer)
                if (huisletter) record.huisletter(huisletter)
                if (huisnummertoevoeging) record.huisnummertoevoeging(huisnummertoevoeging)
            }

            builder.voegPersoonAdresToe(record.eindeRecord().build())
        }

        // vader
        if (oudersMap.containsKey('vader')) {
            if (vader?.isIngeschrevene()) {
                vader.persoonAfgeleidAdministratiefHistorie.voegToe(GegevensAfleider.maakAfgeleidAdministratief(vader, geboorteActie, 2))
            }
            familie.betrokkenheden << new OuderHisVolledigImplBuilder(familie, vader)
                .metVerantwoording(geboorteActie)
                .nieuwOuderschapRecord(geboorteActie)
                .indicatieOuder(Ja.J)
                .eindeRecord().build()
        }
    }

    def ouders(PersoonHisVolledigImpl ouder1 = onbekend, PersoonHisVolledigImpl ouder2 = onbekend) {
        if (!(ouder1 || ouder2)) {
            this.ouders([:])
        } else {
            this.ouders(moeder: ouder1, vader: ouder2)
        }
    }

    def erkendDoor(PersoonHisVolledigImpl vader) {
        this.admhnd.soort.waarde = SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND_MET_ERKENNING

        def kindBetr = builder.hisVolledigImpl.kindBetrokkenheid
        if (!kindBetr) {
            throw new IllegalStateException('Leg eerst de moeder vast')
        }
        if (kindBetr.relatie.ouderBetrokkenheden.size() > 1) {
            throw new IllegalStateException('Er kan niet erkend worden als er al 2 ouders zijn')
        }

        def erkenningActie = maakActie(SoortActie.REGISTRATIE_OUDER)

        def familie = kindBetr.relatie
        familie.betrokkenheden << new OuderHisVolledigImplBuilder(familie, vader)
            .metVerantwoording(erkenningActie)
            .nieuwOuderschapRecord(erkenningActie)
            .indicatieOuder(Ja.J)
            .eindeRecord().build()
    }

    def op(def dat) {
        [te: {String plaats ->
            [gemeente: {def gem ->
                def datum = bepaalDatum(dat)
                def gemeente = bepaalGemeente(gem)

                builder.nieuwGeboorteRecord(geboorteActie)
                    .datumGeboorte(datum)
                    .woonplaatsnaamGeboorte(plaats)
                    .gemeenteGeboorte(gemeente)
                    .landGebiedGeboorte(referentieData.nederland)
                    .eindeRecord()

                builder.nieuwInschrijvingRecord(geboorteActie)
                    .datumInschrijving(datum)
                    .datumtijdstempel(geboorteActie.tijdstipRegistratie)
                    .versienummer(1L)
                    .eindeRecord()
            }]
        }]
    }
}
