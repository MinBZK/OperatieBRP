package nl.bzk.brp.datataal.handlers.persoon

import static nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling.OVERLIJDEN_IN_BUITENLAND

import nl.bzk.brp.datataal.handlers.GegevensAfleider
import nl.bzk.brp.datataal.model.GebeurtenisAttributen
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenEindeRelatieCodeAttribuut
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Gemeente
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebied
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaard
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie
import nl.bzk.brp.model.hisvolledig.impl.kern.HuwelijkHisVolledigImpl
import nl.bzk.brp.model.hisvolledig.impl.kern.PartnerHisVolledigImpl
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
import nl.bzk.brp.model.operationeel.kern.ActieModel
import nl.bzk.brp.util.hisvolledig.kern.GeregistreerdPartnerschapHisVolledigImplBuilder
import nl.bzk.brp.util.hisvolledig.kern.HuwelijkHisVolledigImplBuilder
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder

/**
 *
 */
class OverlijdenHandler extends AbstractGebeurtenisHandler {
    protected final ActieModel overlijdenActie


    OverlijdenHandler(final GebeurtenisAttributen attr, final PersoonHisVolledigImplBuilder b)
    {
        super(attr, b)
        overlijdenActie = maakActie(SoortActie.REGISTRATIE_OVERLIJDEN)
    }

    @Override
    void startGebeurtenis() {
        builder.hisVolledigImpl.getPersoonAfgeleidAdministratiefHistorie().voegToe(GegevensAfleider.maakAfgeleidAdministratief(builder.hisVolledigImpl, overlijdenActie, 1))

        def actueleBijhouding = builder.hisVolledigImpl.persoonBijhoudingHistorie.actueleRecord
        builder.nieuwBijhoudingRecord(overlijdenActie)
            .bijhoudingsaard(actueleBijhouding.bijhoudingsaard.waarde)
            .bijhoudingspartij(actueleBijhouding.bijhoudingspartij.waarde)
            .indicatieOnverwerktDocumentAanwezig(actueleBijhouding.indicatieOnverwerktDocumentAanwezig.waarde)
            .nadereBijhoudingsaard(NadereBijhoudingsaard.OVERLEDEN)
            .eindeRecord()
    }

    /**
     *
     * @param datum
     * @return de builder
     */
    def op(def datum) {
        def dat = bepaalDatum(datum)

        [te: {String plaats ->
            def record = builder.nieuwOverlijdenRecord(overlijdenActie)
                .datumOverlijden(dat)

            [gemeente: {def gem ->
                def gemeente = bepaalGemeente(gem)

                record.gemeenteOverlijden(gemeente)
                    .woonplaatsnaamOverlijden(plaats)
                    .landGebiedOverlijden(referentieData.nederland)
                    .eindeRecord()

                beeindigRelaties(dat, plaats, gemeente)
            },
            land: {def land ->
                def buitenland = bepaalLand(land)

                admhnd.soort.waarde = OVERLIJDEN_IN_BUITENLAND

                record.buitenlandsePlaatsOverlijden(plaats)
                    .landGebiedOverlijden(buitenland)
                    .eindeRecord()

                 beeindigRelaties(dat, plaats, land)
             }]
        }]
    }

    private void beeindigRelaties(Integer datum, String plaats, def locatie) {
        def persoon = builder.hisVolledigImpl
        persoon.partnerBetrokkenheden.each { PartnerHisVolledigImpl betr ->
            if (betr.relatie.relatieHistorie.actueleRecord?.datumEinde == null) {

                HuwelijkHisVolledigImplBuilder relatieBuilder
                if (betr.relatie instanceof HuwelijkHisVolledigImpl) {
                    relatieBuilder = new HuwelijkHisVolledigImplBuilder(betr.relatie)
                } else {
                    relatieBuilder = new GeregistreerdPartnerschapHisVolledigImplBuilder(betr.relatie)
                }

                def actueleRel = betr.relatie.relatieHistorie.actueleRecord
                def record = relatieBuilder.nieuwStandaardRecord(overlijdenActie)
                    .datumAanvang(actueleRel.datumAanvang)
                    .datumEinde(datum)
                    .redenEinde(referentieData.vindRedenEindeRelatieOpCode(new RedenEindeRelatieCodeAttribuut('O')))

                record.with {
                    if (actueleRel.buitenlandsePlaatsAanvang) buitenlandsePlaatsAanvang(actueleRel.buitenlandsePlaatsAanvang)
                    if (actueleRel.buitenlandseRegioAanvang) buitenlandseRegioAanvang(actueleRel.buitenlandseRegioAanvang)
                    if (actueleRel.landGebiedAanvang) landGebiedAanvang(actueleRel.landGebiedAanvang.waarde)
                    if (actueleRel.gemeenteAanvang) gemeenteAanvang(actueleRel.gemeenteAanvang.waarde)
                    if (actueleRel.woonplaatsnaamAanvang) woonplaatsnaamAanvang(actueleRel.woonplaatsnaamAanvang)
                }

                if (locatie instanceof LandGebied) {
                    record.buitenlandsePlaatsEinde(plaats)
                        .landGebiedEinde(locatie)
                } else if (locatie instanceof Gemeente) {
                    record.woonplaatsnaamEinde(plaats)
                        .landGebiedEinde(referentieData.nederland)
                        .gemeenteEinde(locatie)
                }

                record.eindeRecord()

                def partner = betr.relatie.geefPartnerVan(persoon).persoon as PersoonHisVolledigImpl
                if (partner?.isIngeschrevene()) {
                    partner.persoonAfgeleidAdministratiefHistorie.voegToe(GegevensAfleider.maakAfgeleidAdministratief(partner, overlijdenActie, 2))
                }
            }
        }
    }

}
