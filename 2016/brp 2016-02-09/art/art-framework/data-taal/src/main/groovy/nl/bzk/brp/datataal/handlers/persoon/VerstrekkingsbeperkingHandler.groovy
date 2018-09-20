package nl.bzk.brp.datataal.handlers.persoon
import nl.bzk.brp.datataal.handlers.GegevensAfleider
import nl.bzk.brp.datataal.model.GebeurtenisAttributen
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie
import nl.bzk.brp.model.operationeel.kern.ActieModel
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel
import nl.bzk.brp.model.operationeel.kern.HisPersoonVerstrekkingsbeperkingModel
import nl.bzk.brp.util.hisvolledig.kern.PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImplBuilder
import nl.bzk.brp.util.hisvolledig.kern.PersoonVerstrekkingsbeperkingHisVolledigImplBuilder
/**
 * Handler voor het behandelen van een gebeurtenis "registreerVerstrekkingsBeperking".
 */
class VerstrekkingsbeperkingHandler extends AbstractGebeurtenisHandler {
    private final ActieModel actieModel

    boolean ja = true
    boolean nee = false

    VerstrekkingsbeperkingHandler(GebeurtenisAttributen m, def builder) {
        super(m, builder)

        actieModel = maakActie(SoortActie.REGISTRATIE_VERSTREKKINGSBEPERKING)
    }

    VerstrekkingsbeperkingHandler(GebeurtenisAttributen m, def builder, AdministratieveHandelingModel admhnd) {
        super(m, builder, admhnd)

        actieModel = maakActie(SoortActie.REGISTRATIE_VERSTREKKINGSBEPERKING)
    }

    @Override
    void startGebeurtenis() {
        builder.hisVolledigImpl.persoonAfgeleidAdministratiefHistorie.voegToe(GegevensAfleider.maakAfgeleidAdministratief(builder.hisVolledigImpl, actieModel, 1))
    }

    def registratieBeperkingen(Map<String, Object>... beperkingenLijst) {
        beperkingenLijst.each {
            valideerMapKeys(it, ["partij", "omschrijving", "gemeenteVerordening"], "Verplichte velden niet gegeven, \"partij\", \"omschrijving\", \"gemeenteVerordening\"")

            def partij = it.partij ? referentieData.vindPartijOpCode(it.partij as Integer) : null
            def gemeenteVerordening = it.gemeenteVerordening ? referentieData.vindPartijOpCode(it.gemeenteVerordening as Integer) : null
            def omschrijving = it.omschrijving ? new OmschrijvingEnumeratiewaardeAttribuut(it.omschrijving as String) : null

            PersoonVerstrekkingsbeperkingHisVolledigImplBuilder verstrekkingBuilder =
                new PersoonVerstrekkingsbeperkingHisVolledigImplBuilder(builder.hisVolledigImpl, partij, omschrijving, gemeenteVerordening)

            def volledigImpl = verstrekkingBuilder.build()
            volledigImpl.getPersoonVerstrekkingsbeperkingHistorie().voegToe(new HisPersoonVerstrekkingsbeperkingModel(volledigImpl, actieModel))
            builder.voegPersoonVerstrekkingsbeperkingToe(volledigImpl)
        }
    }

    def vervalBeperkingen(Map<String, Object>... beperkingenLijst) {
        beperkingenLijst.each {
            valideerMapKeys(it, ["partij", "omschrijving", "gemeenteVerordening"], "Verplichte velden niet gegeven, \"partij\", \"omschrijving\", \"gemeenteVerordening\"")

            def partij = it.partij ? referentieData.vindPartijOpCode(it.partij as Integer) : null
            def gemeenteVerordening = it.gemeenteVerordening ? referentieData.vindPartijOpCode(it.gemeenteVerordening as Integer) : null
            def omschrijving = it.omschrijving ? new OmschrijvingEnumeratiewaardeAttribuut(it.omschrijving as String) : null

            def verstrekkingsbeperkingHisVolledigImpl = builder.hisVolledigImpl.verstrekkingsbeperkingen.find {
                it.partij?.waarde == partij && it.gemeenteVerordening?.waarde == gemeenteVerordening && it.omschrijvingDerde?.waarde == omschrijving?.waarde
            }

            if (verstrekkingsbeperkingHisVolledigImpl == null) {
                throw new IllegalArgumentException('Beeindiging verstrekkingsbeperking is niet mogelijk als persoon geen verstrekkingsbeperking heeft' +
                        ' voor de meegegeven waarden.')
            }

            verstrekkingsbeperkingHisVolledigImpl.getPersoonVerstrekkingsbeperkingHistorie().verval(actieModel, actieModel.getTijdstipRegistratie())
        }
    }

    def volledig(Boolean indicatie) {
        if (indicatie) {
            builder.voegPersoonIndicatieVolledigeVerstrekkingsbeperkingToe(
                new PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImplBuilder(builder.hisVolledigImpl)
                    .nieuwStandaardRecord(actieModel).waarde(Ja.J).eindeRecord().build())
        } else {
            actieModel.soort.waarde = SoortActie.BEEINDIGING_VERSTREKKINGSBEPERKING

            if (builder.hisVolledigImpl?.indicatieVolledigeVerstrekkingsbeperking?.persoonIndicatieHistorie?.heeftActueelRecord()) {
                builder.hisVolledigImpl.indicatieVolledigeVerstrekkingsbeperking.persoonIndicatieHistorie.verval(actieModel, actieModel.tijdstipRegistratie)
            }
        }
    }
}
