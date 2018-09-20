package nl.bzk.brp.datataal.handlers.persoon

import nl.bzk.brp.datataal.handlers.GegevensAfleider
import nl.bzk.brp.datataal.model.GebeurtenisAttributen
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeLangAttribuut
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OnderzoekOmschrijvingAttribuut
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SleutelwaardeAttribuut
import nl.bzk.brp.model.algemeen.stamgegeven.kern.*
import nl.bzk.brp.model.basis.ElementIdentificeerbaar
import nl.bzk.brp.model.basis.GerelateerdIdentificeerbaar
import nl.bzk.brp.model.basis.HistorieEntiteit
import nl.bzk.brp.model.basis.ModelIdentificeerbaar
import nl.bzk.brp.model.bericht.kern.OnderzoekStandaardGroepBericht
import nl.bzk.brp.model.hisvolledig.impl.kern.GegevenInOnderzoekHisVolledigImpl
import nl.bzk.brp.model.hisvolledig.impl.kern.OnderzoekHisVolledigImpl
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonOnderzoekHisVolledigImpl
import nl.bzk.brp.model.hisvolledig.kern.PersoonOnderzoekHisVolledig
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView
import nl.bzk.brp.model.operationeel.kern.ActieModel
import nl.bzk.brp.model.operationeel.kern.HisOnderzoekModel
import nl.bzk.brp.util.PersoonHisVolledigViewUtil
import nl.bzk.brp.util.hisvolledig.kern.OnderzoekHisVolledigImplBuilder
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder

/**
 * Handler voor het aanmaken van onderzoeken.
 *
 */
class OnderzoekBeeindigdHandler extends AbstractGebeurtenisHandler {
    protected ActieModel onderzoekActie;
    protected OnderzoekHisVolledigImpl onderzoek
    protected OnderzoekHisVolledigImplBuilder onderzoekBuilder = new OnderzoekHisVolledigImplBuilder()

    OnderzoekBeeindigdHandler(final GebeurtenisAttributen attr, final PersoonHisVolledigImplBuilder b) {
        super(attr, b)
        onderzoekActie = maakActie(SoortActie.WIJZIGING_ONDERZOEK)
    }

    @Override
    void startGebeurtenis() {
    }

    /**
     * Voeg een onderzoek voorkomen toe aan de historie, zet huidige status op gestaakt.
     *
     * @param datum
     * @return
     */
    def gestaaktOp(Map map) {
        onderzoekActie.soort.waarde = SoortActie.WIJZIGING_ONDERZOEK
        admhnd.soort.waarde = SoortAdministratieveHandeling.BEEINDIGING_ONDERZOEK

        Integer eindDatum = map.eindDatum ? bepaalDatum(map.eindDatum) : null

        PersoonOnderzoekHisVolledig persoonOnderzoek = builder.hisVolledigImpl.onderzoeken.iterator().next()
        HisOnderzoekModel historieModel = persoonOnderzoek.onderzoek.onderzoekHistorie.actueleRecord

        persoonOnderzoek.onderzoek.onderzoekHistorie.verval(onderzoekActie, geefRegistratieDatum() ?: admhnd.tijdstipRegistratie)

        builder.hisVolledigImpl.getAdministratieveHandelingen().add(admhnd)
        builder.hisVolledigImpl.getPersoonAfgeleidAdministratiefHistorie()
            .voegToe(GegevensAfleider.maakAfgeleidAdministratief(builder.hisVolledigImpl, onderzoekActie, 1))

        def bericht = new OnderzoekStandaardGroepBericht()
        bericht.datumAanvang = historieModel.datumAanvang
        bericht.omschrijving = historieModel.omschrijving
        bericht.verwachteAfhandeldatum = historieModel.verwachteAfhandeldatum
        bericht.datumEinde = new DatumEvtDeelsOnbekendAttribuut(eindDatum)
        bericht.status = new StatusOnderzoekAttribuut(StatusOnderzoek.GESTAAKT)

        def record = new HisOnderzoekModel(persoonOnderzoek.onderzoek, bericht, onderzoekActie)
        persoonOnderzoek.onderzoek.onderzoekHistorie.voegToe(record)
    }

    /**
     * Voeg een onderzoek voorkomen toe aan de historie, zet huidige status op afgesloten.
     *
     * @param datum
     * @return
     */
    def afgeslotenOp(Map map) {
        onderzoekActie.soort.waarde = SoortActie.WIJZIGING_ONDERZOEK
        admhnd.soort.waarde = SoortAdministratieveHandeling.BEEINDIGING_ONDERZOEK

        Integer eindDatum = map.eindDatum ? bepaalDatum(map.eindDatum) : null

        PersoonOnderzoekHisVolledig persoonOnderzoek = builder.hisVolledigImpl.onderzoeken.iterator().next()
        HisOnderzoekModel historieModel = persoonOnderzoek.onderzoek.onderzoekHistorie.actueleRecord

        persoonOnderzoek.onderzoek.onderzoekHistorie.verval(onderzoekActie, geefRegistratieDatum() ?: admhnd.tijdstipRegistratie)

        builder.hisVolledigImpl.getAdministratieveHandelingen().add(admhnd)
        builder.hisVolledigImpl.getPersoonAfgeleidAdministratiefHistorie()
            .voegToe(GegevensAfleider.maakAfgeleidAdministratief(builder.hisVolledigImpl, onderzoekActie, 1))

        def bericht = new OnderzoekStandaardGroepBericht()
        bericht.datumAanvang = historieModel.datumAanvang
        bericht.omschrijving = historieModel.omschrijving
        bericht.verwachteAfhandeldatum = historieModel.verwachteAfhandeldatum
        bericht.datumEinde = new DatumEvtDeelsOnbekendAttribuut(eindDatum)
        bericht.status = new StatusOnderzoekAttribuut(StatusOnderzoek.AFGESLOTEN)

        def record = new HisOnderzoekModel(persoonOnderzoek.onderzoek, bericht, onderzoekActie)
        persoonOnderzoek.onderzoek.onderzoekHistorie.voegToe(record)
    }
}
