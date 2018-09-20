package nl.bzk.brp.datataal.handlers.persoon

import nl.bzk.brp.datataal.handlers.GegevensAfleider
import nl.bzk.brp.datataal.model.GebeurtenisAttributen
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AangeverCodeAttribuut
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenWijzigingVerblijfCodeAttribuut
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Bijhoudingsaard
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdres
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaard
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortMigratie
import nl.bzk.brp.model.operationeel.kern.ActieModel
import nl.bzk.brp.util.hisvolledig.kern.PersoonAdresHisVolledigImplBuilder

/**
 * Handler voor de gebeurtenis verhuizing. Dit kunnen zowel binnen- als intergemeentelijke verhuizingen zijn als
 * verhuizing naar het buitenland.
 */
class VerhuizingHandler extends AbstractGebeurtenisHandler {
    protected final ActieModel verhuizingActie
    private static final def ADRES_SLEUTELS = ['straat', 'nummer', 'letter', 'woonplaats', 'postcode', 'aanvangAdreshouding']

    PersoonAdresHisVolledigImplBuilder persoonAdresBuilder

    /**
     * Constructor.
     *
     * @param m
     * @param builder
     */
    VerhuizingHandler(GebeurtenisAttributen m, def builder) {
        super(m, builder)

        if (builder.hisVolledigImpl.adressen) {
            this.persoonAdresBuilder = new PersoonAdresHisVolledigImplBuilder(builder.hisVolledigImpl.adressen[0])
        } else {
            this.persoonAdresBuilder = new PersoonAdresHisVolledigImplBuilder(builder.hisVolledigImpl)
        }
        verhuizingActie = maakActie(SoortActie.REGISTRATIE_ADRES)
    }

    @Override
    void startGebeurtenis() {
        builder.hisVolledigImpl.getPersoonAfgeleidAdministratiefHistorie().voegToe(GegevensAfleider.maakAfgeleidAdministratief(builder.hisVolledigImpl, verhuizingActie, 1))
    }

    /**
     * Binnengemeentelijke verhuizing.
     *
     * @param m
     * @return de builder
     */
    def binnenGemeente(Map<String, Object> map) {
        admhnd.soort.waarde = SoortAdministratieveHandeling.VERHUIZING_BINNENGEMEENTELIJK
        valideerMapKeys(map, ADRES_SLEUTELS, 'alleen ${keys} zijn toegestaan')

        def gemeente = builder.hisVolledigImpl.adressen.iterator().next()?.persoonAdresHistorie?.actueleRecord?.gemeente?.waarde
        if (gemeente == null) {
            throw new IllegalArgumentException('Binnen gemeentelijke verhuizing is niet mogelijk als persoon (nog) niet in een gemeente woont')
        }

        def aanvangAdreshouding = bepaalDatum(map.aanvangAdreshouding ?: verhuizingActie.aanvangGeldigheid)

        def record = persoonAdresBuilder
            .nieuwStandaardRecord(verhuizingActie)
                .soort(FunctieAdres.WOONADRES)
                .aangeverAdreshouding(referentieData.vindAangeverAdreshoudingOpCode(new AangeverCodeAttribuut('I')))
                .redenWijziging(referentieData.vindRedenWijzingVerblijfOpCode(new RedenWijzigingVerblijfCodeAttribuut('P')))
                .datumAanvangAdreshouding(aanvangAdreshouding)
                .gemeente(gemeente)
                .landGebied(referentieData.nederland)

        if (map.straat) record.naamOpenbareRuimte(map.straat as String)
        if (map.nummer) record.huisnummer(map.nummer as Integer)
        if (map.letter) record.huisletter(map.letter as String)
        if (map.postcode) record.postcode(map.postcode as String)
        if (map.woonplaats) record.woonplaatsnaam(map.woonplaats as String)

        builder.voegPersoonAdresToe(record.eindeRecord().build())
    }

    /**
     * Intergemeentelijke verhuizing.
     *
     * @param gem
     * @return de builder
     */
    def naarGemeente(Map<String, Object> map, def gem) {
        admhnd.soort.waarde = SoortAdministratieveHandeling.VERHUIZING_INTERGEMEENTELIJK
        valideerMapKeys(map, ADRES_SLEUTELS, 'alleen ${keys} zijn toegestaan')

        final def gemeente = bepaalGemeente(gem)

        def aanvangAdreshouding = bepaalDatum(map.aanvangAdreshouding ?: verhuizingActie.aanvangGeldigheid)

        def record = persoonAdresBuilder
            .nieuwStandaardRecord(verhuizingActie)
                .soort(FunctieAdres.WOONADRES)
                .datumAanvangAdreshouding(aanvangAdreshouding)
                .aangeverAdreshouding(referentieData.vindAangeverAdreshoudingOpCode(new AangeverCodeAttribuut('I')))
                .redenWijziging(referentieData.vindRedenWijzingVerblijfOpCode(new RedenWijzigingVerblijfCodeAttribuut('P')))
                .gemeente(gemeente)
                .landGebied(referentieData.nederland)

        if (map.straat) record.naamOpenbareRuimte(map.straat as String)
        if (map.nummer) record.huisnummer(map.nummer as Integer)
        if (map.letter) record.huisletter(map.letter as String)
        if (map.postcode) record.postcode(map.postcode as String)
        if (map.woonplaats) record.woonplaatsnaam(map.woonplaats as String)

        builder.voegPersoonAdresToe(record.eindeRecord().build())
        builder.nieuwBijhoudingRecord(verhuizingActie)
            .bijhoudingspartij(admhnd.partij.waarde)
            .bijhoudingsaard(Bijhoudingsaard.INGEZETENE)
            .nadereBijhoudingsaard(NadereBijhoudingsaard.ACTUEEL)
            .indicatieOnverwerktDocumentAanwezig(JaNeeAttribuut.NEE)
            .eindeRecord()
    }

    /**
     * migratie
     *
     * @param m
     * @return de builder
     */
    def naarBuitenland(Map<String, Object> map, String land) {
        admhnd.soort.waarde = SoortAdministratieveHandeling.VERHUIZING_NAAR_BUITENLAND
        verhuizingActie.soort.waarde = SoortActie.REGISTRATIE_MIGRATIE

        def aanvangAdreshouding = bepaalDatum(map.aanvangAdreshouding ?: verhuizingActie.aanvangGeldigheid)

        def record = persoonAdresBuilder
                .nieuwStandaardRecord(verhuizingActie)
                .soort(FunctieAdres.WOONADRES)
                .datumAanvangAdreshouding(aanvangAdreshouding)
                .aangeverAdreshouding(referentieData.vindAangeverAdreshoudingOpCode(new AangeverCodeAttribuut('I')))
                .redenWijziging(referentieData.vindRedenWijzingVerblijfOpCode(new RedenWijzigingVerblijfCodeAttribuut('P')))
                .datumAanvangAdreshouding(verhuizingActie.aanvangGeldigheid)
                .landGebied(referentieData.vindLandOpNaam(land))

        if (map.adres) {
            def regels = []

            if (map.adres instanceof List) {
                regels.addAll(map.adres)
            } else if (map.adres instanceof String) {
                regels.addAll(map.adres.split(','))
            }

            regels.eachWithIndex{ it, index ->
                record.invokeMethod("buitenlandsAdresRegel${index + 1}", it)
            }
        }

        builder.voegPersoonAdresToe(record.eindeRecord().build())

        builder.nieuwMigratieRecord(verhuizingActie)
            .soortMigratie(SoortMigratie.EMIGRATIE)
            .aangeverMigratie(referentieData.vindAangeverAdreshoudingOpCode(new AangeverCodeAttribuut('I')))
            .redenWijzigingMigratie(referentieData.vindRedenWijzingVerblijfOpCode(new RedenWijzigingVerblijfCodeAttribuut('P')))
            .landGebiedMigratie(referentieData.vindLandOpNaam(land))
            .eindeRecord()

        builder.hisVolledigImpl.persoonBijhoudingHistorie.beeindig(verhuizingActie, verhuizingActie)
    }

    def verstrekkingsbeperking(@DelegatesTo(VerstrekkingsbeperkingHandler) Closure c) {
        def clone = (Closure) c.clone()

        clone.delegate = new VerstrekkingsbeperkingHandler(this.handelingDefaults, this.builder, this.admhnd)
        clone.resolveStrategy = Closure.DELEGATE_ONLY

        clone.call()
    }
}
