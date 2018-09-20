package nl.bzk.brp.datataal.handlers.persoon

import static nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling.BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND
import static nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling.BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND
import static nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling.ONTBINDING_HUWELIJK_IN_BUITENLAND
import static nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling.ONTBINDING_HUWELIJK_IN_NEDERLAND

import nl.bzk.brp.datataal.handlers.GegevensAfleider
import nl.bzk.brp.datataal.model.GebeurtenisAttributen
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenEindeRelatieCodeAttribuut
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie
import nl.bzk.brp.model.hisvolledig.impl.kern.HuwelijkHisVolledigImpl
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
import nl.bzk.brp.model.operationeel.kern.ActieModel
import nl.bzk.brp.util.hisvolledig.kern.GeregistreerdPartnerschapHisVolledigImplBuilder
import nl.bzk.brp.util.hisvolledig.kern.HuwelijkHisVolledigImplBuilder
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder

/**
 *
 */
class ScheidingHandler extends AbstractGebeurtenisHandler {
    protected final ActieModel scheidingActie
    protected def relatieBuilder
    protected def relatie


    ScheidingHandler(final GebeurtenisAttributen attr, final PersoonHisVolledigImplBuilder b)
    {
        super(attr, b)

        scheidingActie = maakActie(SoortActie.VERVAL_HUWELIJK_GEREGISTREERD_PARTNERSCHAP)
    }

    @Override
    void startGebeurtenis() {
        builder.hisVolledigImpl.getPersoonAfgeleidAdministratiefHistorie().voegToe(GegevensAfleider.maakAfgeleidAdministratief(builder.hisVolledigImpl, scheidingActie, 1))
    }

    def van(PersoonHisVolledigImpl partner) {
        relatie = builder.hisVolledigImpl.huwelijkGeregistreerdPartnerschappen.find {
            it.relatieHistorie.actueleRecord != null &&
                it.geefPartnerVan(builder.hisVolledigImpl).persoon.ID == partner.ID
        }

        if (relatie instanceof HuwelijkHisVolledigImpl) {
            admhnd.soort.waarde = ONTBINDING_HUWELIJK_IN_NEDERLAND
            relatieBuilder = new HuwelijkHisVolledigImplBuilder(relatie)
        } else {
            admhnd.soort.waarde = BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND
            relatieBuilder = new GeregistreerdPartnerschapHisVolledigImplBuilder(relatie)
        }

        if (!relatie) {
            throw new IllegalStateException("Er is geen bestaand huwelijk/partnerschap met partner, BSN: ${partner.persoonIdentificatienummersHistorie.actueleRecord?.burgerservicenummer}")
        } else {
            if (partner.isIngeschrevene()) {
                partner.persoonAfgeleidAdministratiefHistorie.voegToe(GegevensAfleider.maakAfgeleidAdministratief(partner, scheidingActie, 1))
                GegevensAfleider.leidNaamgebruikAf(new PersoonHisVolledigImplBuilder(partner), scheidingActie)
            }

            GegevensAfleider.leidNaamgebruikAf(builder, scheidingActie)
        }
    }

    /**
     *
     * @param datum
     * @return de builder
     */
    def op(def datum) {
        if (!relatie) {
            throw new IllegalStateException('Geef eerst op van wie de scheiding is met van(persoon)')
        }

        def dat = bepaalDatum(datum)

        [te: {String plaats ->
            [gemeente: {def gem ->
                def gemeente = bepaalGemeente(gem)

                relatie = relatieBuilder
                    .nieuwStandaardRecord(scheidingActie)
                    .woonplaatsnaamEinde(plaats)
                    .gemeenteEinde(gemeente)
                    .landGebiedEinde(referentieData.nederland)
                    .redenEinde(referentieData.vindRedenEindeRelatieOpCode(new RedenEindeRelatieCodeAttribuut('S')))

                    .datumEinde(dat)
                    .eindeRecord()
                    .build()
            },
            land: {def land ->
                def buitenland = bepaalLand(land)

                admhnd.soort.waarde = admhnd.soort.waarde.name().contains('HUWELIJK') ? ONTBINDING_HUWELIJK_IN_BUITENLAND : BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND

                relatie = relatieBuilder
                    .nieuwStandaardRecord(scheidingActie)
                    .landGebiedEinde(buitenland)
                    .buitenlandsePlaatsEinde(plaats)
                    .redenEinde(referentieData.vindRedenEindeRelatieOpCode(new RedenEindeRelatieCodeAttribuut('V')))
                    .datumEinde(dat)
                    .eindeRecord()
                    .build()
            }]
        }]
    }
}
