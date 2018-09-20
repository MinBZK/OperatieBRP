package nl.bzk.brp.datataal.handlers.persoon

import static nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling.AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND
import static nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_BUITENLAND

import nl.bzk.brp.datataal.handlers.GegevensAfleider
import nl.bzk.brp.datataal.model.GebeurtenisAttributen
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdellijkeTitelCodeAttribuut
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PredicaatCodeAttribuut
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Naamgebruik
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
import nl.bzk.brp.model.operationeel.kern.ActieModel
import nl.bzk.brp.model.operationeel.kern.HisPersoonSamengesteldeNaamModel
import nl.bzk.brp.util.hisvolledig.kern.PartnerHisVolledigImplBuilder
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder.PersoonHisVolledigImplBuilderNaamgebruik

/**
 * Handler voor de gebeurtenis huwelijk.
 */
class AbstractHuwelijkPartnerschapHandler extends AbstractGebeurtenisHandler {
    protected final def relatieBuilder
    protected ActieModel relatieActie
    protected def relatie

    /**
     * Constructor.
     *
     * @param m
     * @param builder
     */
    AbstractHuwelijkPartnerschapHandler(GebeurtenisAttributen m, PersoonHisVolledigImplBuilder builder, def relBuilder) {
        super(m, builder)
        relatieBuilder = relBuilder
        relatieActie = maakActie(SoortActie.REGISTRATIE_AANVANG_HUWELIJK_GEREGISTREERD_PARTNERSCHAP)
    }

    @Override
    void startGebeurtenis() {
        builder.hisVolledigImpl.getPersoonAfgeleidAdministratiefHistorie().voegToe(GegevensAfleider.maakAfgeleidAdministratief(builder.hisVolledigImpl, relatieActie, 1))
    }

    /**
     *
     * @param datum
     * @return de builder
     */
    def op(def datum) {
        def dat = bepaalDatum(datum)

        [te: {String plaats ->
            [gemeente: {def gem ->
                def gemeente = bepaalGemeente(gem)

                relatie = relatieBuilder
                    .nieuwStandaardRecord(relatieActie)
                    .woonplaatsnaamAanvang(plaats)
                    .gemeenteAanvang(gemeente)
                    .landGebiedAanvang(referentieData.nederland)
                    .datumAanvang(dat)
                    .eindeRecord()
                    .build()
            },
            land: {def land ->
                def buitenland = bepaalLand(land)

                admhnd.soort.waarde = admhnd.soort.waarde.name().contains('HUWELIJK') ? VOLTREKKING_HUWELIJK_IN_BUITENLAND : AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND

                relatie = relatieBuilder
                    .nieuwStandaardRecord(relatieActie)
                    .landGebiedAanvang(buitenland)
                    .buitenlandsePlaatsAanvang(plaats)
                    .datumAanvang(dat)
                    .eindeRecord()
                    .build()
            }]
        }]
    }

    /**
     * Huwelijk met wie.
     *
     * @param persoon partner met wie het huwelijk is
     * @return de builder
     */
    def met(def persoon) {
        if (relatie == null) {
            throw new IllegalStateException('Geef eerst op wanneer en waar het huwelijk heeft plaats gevonden met op().te().gemeente()')
        }

        if (persoon?.isIngeschrevene()) {
            persoon.persoonAfgeleidAdministratiefHistorie.voegToe(GegevensAfleider.maakAfgeleidAdministratief(persoon, relatieActie, 1))
        }

        new PartnerHisVolledigImplBuilder(relatie, builder.hisVolledigImpl).metVerantwoording(relatieActie).build()
        new PartnerHisVolledigImplBuilder(relatie, persoon).metVerantwoording(relatieActie).build()
    }

    def naamgebruik(PersoonHisVolledigImpl persoon2 = null, String... gebruik) {
        if (relatie == null || relatie.betrokkenheden.isEmpty()) {
            throw new IllegalStateException('Geef eerst op met welke partner is gehuwd')
        }

        def echtGebruik
        if (gebruik.size() > 1) {
            echtGebruik = gebruik
        } else {
            echtGebruik = gebruik[0].split(',')
        }

        echtGebruik = echtGebruik.collect { it.trim().toUpperCase() }.grep().join('_')

        def naamGebruik = Naamgebruik.valueOf(echtGebruik)
        def actie = maakActie(SoortActie.REGISTRATIE_NAAMGEBRUIK)
        def record = builder.nieuwNaamgebruikRecord(actie)
        def zelf = builder.hisVolledigImpl

        if (persoon2) {
            if (persoon2.isIngeschrevene()) {
                zelf = persoon2
                record = new PersoonHisVolledigImplBuilder(persoon2).nieuwNaamgebruikRecord(actie)
            } else {
                // geen aanpassingen op een persoon die geen ingeschrevene is
                logger.warn 'Er is een wijziging beschreven op de niet ingeschreven persooon (id={})', persoon2.ID
                return
            }
        }

        def partner = (PersoonHisVolledigImpl) relatie.geefPartnerVan(zelf).persoon
        def partnerNamen = partner.persoonSamengesteldeNaamHistorie.actueleRecord
        def eigenNamen = zelf.persoonSamengesteldeNaamHistorie.actueleRecord

        record.indicatieNaamgebruikAfgeleid(Boolean.TRUE)
            .naamgebruik(naamGebruik)
            .voorvoegselNaamgebruik(eigenNamen.voorvoegsel)
            .scheidingstekenNaamgebruik(eigenNamen.scheidingsteken)

        record.with {
            switch (naamGebruik) {
                case Naamgebruik.PARTNER:
                    zetNamenOver(record, partnerNamen)
                    break
                case Naamgebruik.PARTNER_EIGEN:
                    zetNamenOver(record, partnerNamen, eigenNamen)
                    break
                case Naamgebruik.EIGEN_PARTNER:
                    zetNamenOver(record, eigenNamen, partnerNamen)
                    break
                case Naamgebruik.EIGEN:
                    zetNamenOver(record, eigenNamen)
                    break
                default:
                    zetNamenOver(record, eigenNamen)
                    break
            }
        }

        record.eindeRecord()
    }

    protected void zetNamenOver(def PersoonHisVolledigImplBuilderNaamgebruik record, HisPersoonSamengesteldeNaamModel... namen) {
        record.geslachtsnaamstamNaamgebruik(namen.collect { it.geslachtsnaamstam?.waarde}.grep().join(' '))
            .voornamenNaamgebruik(namen.collect { it.voornamen?.waarde}.grep().join(' '))

        def titels = namen.collect { it.adellijkeTitel?.waarde }.grep().sort { it.iD }
        if (titels) {
            record.adellijkeTitelNaamgebruik(titels.first().code.waarde)
        }

        def predicaten = namen.collect { it.predicaat?.waarde }.grep().sort { it.iD }
        if (predicaten) {
            record.predicaatNaamgebruik(predicaten.first().code.waarde)
        }
    }

    def naamgebruik(Map<String, String> map, PersoonHisVolledigImpl partner = null) {
        def record
        def actie = maakActie(SoortActie.REGISTRATIE_NAAMGEBRUIK)

        if (partner) {
            if (partner.isIngeschrevene()) {
                def persoonBuilder = new PersoonHisVolledigImplBuilder(partner)
                record = persoonBuilder.nieuwNaamgebruikRecord(actie)
            } else {
                // geen aanpassingen op een niet ingeschrevene
                logger.warn 'Er is een wijziging beschreven op de niet ingeschreven persooon (bsn={})', partner.persoonIdentificatienummersHistorie.actueleRecord?.burgerservicenummer
                return
            }
        } else {
            record = builder.nieuwNaamgebruikRecord(actie)
        }

        vulNaamgebruik(map, record)

        record.naamgebruik(Naamgebruik.EIGEN)
            .indicatieNaamgebruikAfgeleid(Boolean.FALSE)
            .eindeRecord()
    }

    private void vulNaamgebruik(Map<String, String> map, PersoonHisVolledigImplBuilder.PersoonHisVolledigImplBuilderNaamgebruik record) {
        valideerMapKeys(map, GegevensAfleider.NAAMGEBRUIK_SLEUTELS, 'gebruik alleen ${keys} voor naamgebruik')

        record.with {
            if (map.voorvoegsel) record.voorvoegselNaamgebruik(map.voorvoegsel)
            if (map.voornamen) record.voorvoegselNaamgebruik(map.voornamen)
            if (map.geslachtsnaamstam) record.geslachtsnaamstamNaamgebruik(map.geslachtsnaamstam)
            if (map.scheidingsteken) record.scheidingstekenNaamgebruik(map.scheidingsteken)
            if (map.predicaat) record.predicaatNaamgebruik(referentieData.vindPredicaatOpCode(new PredicaatCodeAttribuut(map.predicaat)))
            if (map.adellijkeTitel) record.adellijkeTitelNaamgebruik(referentieData.vindAdellijkeTitelOpCode(new AdellijkeTitelCodeAttribuut(map.adellijkeTitel)))
        }
    }
}
