package nl.bzk.brp.datataal.handlers.persoon

import nl.bzk.brp.datataal.handlers.GegevensAfleider
import nl.bzk.brp.datataal.model.GebeurtenisAttributen
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie
import nl.bzk.brp.model.operationeel.kern.ActieModel
import nl.bzk.brp.util.hisvolledig.kern.PersoonIndicatieOnderCurateleHisVolledigImplBuilder

/**
 * Handler voor het behandelen van een gebeurtenis "registreerVerstrekkingsBeperking".
 */
class CurateleHandler extends AbstractGebeurtenisHandler {
    private final ActieModel actieRegistratieCuratele = maakActie(SoortActie.REGISTRATIE_CURATELE)

    CurateleHandler(GebeurtenisAttributen m, def builder) {
        super(m, builder)
    }

    @Override
    void startGebeurtenis() {
        builder.hisVolledigImpl.persoonAfgeleidAdministratiefHistorie.voegToe(GegevensAfleider.maakAfgeleidAdministratief(builder.hisVolledigImpl, actieRegistratieCuratele, 1))
    }

    def jaClosure = {
        PersoonIndicatieOnderCurateleHisVolledigImplBuilder curateleBuilder = new PersoonIndicatieOnderCurateleHisVolledigImplBuilder(builder.hisVolledigImpl)
        curateleBuilder.nieuwStandaardRecord(actieRegistratieCuratele).waarde(Ja.J).eindeRecord()
        builder.voegPersoonIndicatieOnderCurateleToe(curateleBuilder.build())
    }

    def neeClosure = {
        if (builder.hisVolledigImpl.indicatieOnderCuratele.persoonIndicatieHistorie.actueleRecord) {
            builder.hisVolledigImpl.indicatieOnderCuratele.persoonIndicatieHistorie.beeindig(actieRegistratieCuratele, actieRegistratieCuratele)
        }
    }

    def propertyMissing(final String name) {
        if (name == 'ja') { jaClosure() }
        if (name == 'nee') { neeClosure() }
    }

}
