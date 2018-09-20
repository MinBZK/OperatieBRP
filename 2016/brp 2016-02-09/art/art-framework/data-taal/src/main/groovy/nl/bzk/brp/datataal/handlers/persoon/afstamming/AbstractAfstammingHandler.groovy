package nl.bzk.brp.datataal.handlers.persoon.afstamming

import nl.bzk.brp.datataal.handlers.persoon.AbstractGebeurtenisHandler
import nl.bzk.brp.datataal.model.GebeurtenisAttributen
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie
import nl.bzk.brp.model.operationeel.kern.ActieModel
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder
import nl.bzk.brp.util.hisvolledig.kern.PersoonIndicatieBehandeldAlsNederlanderHisVolledigImplBuilder
import nl.bzk.brp.util.hisvolledig.kern.PersoonIndicatieStaatloosHisVolledigImplBuilder
import nl.bzk.brp.util.hisvolledig.kern.PersoonNationaliteitHisVolledigImplBuilder

/**
 *
 */
abstract class AbstractAfstammingHandler extends AbstractGebeurtenisHandler {

    AbstractAfstammingHandler(final GebeurtenisAttributen attr, final PersoonHisVolledigImplBuilder b) {
        super(attr, b)
    }

    protected int getAantalGeslachtsnaamComponenten() {
        return builder.hisVolledigImpl.geslachtsnaamcomponenten.size()
    }

    def nationaliteiten(String... nats) {
        def actie = maakActie(SoortActie.REGISTRATIE_NATIONALITEIT)

        nats.each {
            def natBuilder = new PersoonNationaliteitHisVolledigImplBuilder(builder.hisVolledigImpl, referentieData.vindNationaliteitOpNaam(it))
            natBuilder.nieuwStandaardRecord(actie).eindeRecord()
            builder.voegPersoonNationaliteitToe(natBuilder.build())
        }
    }

    def staatloos(Boolean indicatie = Boolean.TRUE) {
        if (indicatie) {
            def staatloosActie = maakActie(SoortActie.REGISTRATIE_STAATLOOS)
            def staatloosIndicatie = new PersoonIndicatieStaatloosHisVolledigImplBuilder(builder.hisVolledigImpl)
                .nieuwStandaardRecord(staatloosActie).waarde(Ja.J).eindeRecord().build()
            builder.voegPersoonIndicatieStaatloosToe(staatloosIndicatie)
        }
    }

    def behandeldAlsNederlander(Boolean indicatie = Boolean.TRUE) {
        if (indicatie) {
            def actie = maakActie(SoortActie.REGISTRATIE_BEHANDELD_ALS_NEDERLANDER)
            builder.voegPersoonIndicatieBehandeldAlsNederlanderToe(
                new PersoonIndicatieBehandeldAlsNederlanderHisVolledigImplBuilder(builder.hisVolledigImpl)
                    .nieuwStandaardRecord(actie).waarde(Ja.J).eindeRecord().build())
        }
    }

    abstract ActieModel getOuderActie();
}
