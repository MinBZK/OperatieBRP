package nl.bzk.brp.datataal.handlers.persoon
import nl.bzk.brp.datataal.handlers.GegevensAfleider
import nl.bzk.brp.datataal.model.GebeurtenisAttributen
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenWijzigingVerblijfCodeAttribuut
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Bijhoudingsaard
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaard
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortMigratie
import nl.bzk.brp.model.operationeel.kern.ActieModel
/**
 * Handler voor de gebeurtenis beeindig adres.
 */
class BeeindigAdresHandler extends AbstractGebeurtenisHandler {
    protected ActieModel actie

    /**
     * Constructor.
     *
     * @param m
     * @param builder
     */
    BeeindigAdresHandler(GebeurtenisAttributen m, def builder) {
        super(m, builder)
        actie = maakActie(SoortActie.REGISTRATIE_MIGRATIE)
    }

    @Override
    void startGebeurtenis() {

    }

    @Override
    void eindeGebeurtenis() {
        builder.hisVolledigImpl.getPersoonAfgeleidAdministratiefHistorie()
                .voegToe(GegevensAfleider.maakAfgeleidAdministratief(builder.hisVolledigImpl, actie, 1))

        builder.nieuwMigratieRecord(actie).soortMigratie(SoortMigratie.EMIGRATIE)
                .redenWijzigingMigratie(referentieData.vindRedenWijzingVerblijfOpCode(RedenWijzigingVerblijfCodeAttribuut
                .PERSOON_REDEN_WIJZIGING_ADRES_CODE_AMBTSHALVE_CODE))
                .eindeRecord()

        builder.nieuwBijhoudingRecord(actie)
        // Bijhoudingspartij = Minister = code: 199901
                .bijhoudingspartij(referentieData.vindPartijOpCode(199901))
                .bijhoudingsaard(Bijhoudingsaard.NIET_INGEZETENE)
                .nadereBijhoudingsaard(NadereBijhoudingsaard.ACTUEEL)
                .indicatieOnverwerktDocumentAanwezig(JaNeeAttribuut.NEE)
                .eindeRecord()

        def adres = builder.hisVolledigImpl.adressen.iterator().next();
        if (adres == null) {
            throw new IllegalArgumentException('Beeindiging adres is niet mogelijk als persoon geen adres heeft')
        }

        def actueelMigratieRecord = builder.hisVolledigImpl.persoonMigratieHistorie.actueleRecord
        adres.persoonAdresHistorie?.beeindig(actueelMigratieRecord, actie)
    }

    /**
     * Op welke datum.
     * @param de datum
     * @return de builder
     */
    def op(def datum) {
        actie = maakActie(SoortActie.REGISTRATIE_MIGRATIE, admhnd, bepaalDatum(datum))
    }
}
