package nl.bzk.brp.datataal.handlers
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SorteervolgordeAttribuut
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Naamgebruik
import nl.bzk.brp.model.hisvolledig.impl.kern.HuwelijkGeregistreerdPartnerschapHisVolledigImpl
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig
import nl.bzk.brp.model.operationeel.kern.ActieModel
import nl.bzk.brp.model.operationeel.kern.HisPersoonAfgeleidAdministratiefModel
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Helper voor het afleiden / maken van voorkomens voor een persoon.
 */
final class GegevensAfleider {
    private static final Logger LOGGER = LoggerFactory.getLogger(GegevensAfleider.class);

    public static final List NAAMGEBRUIK_SLEUTELS = ['voornamen', 'geslachtsnaamstam', 'voorvoegsel', 'scheidingsteken', 'predicaat', 'adellijkeTitel']

    private GegevensAfleider() {
        // util klasse, geen instanties
    }

    /**
     * Leidt de samengestelde naam af van de actuele voornamen en geslachtsnaamcomponenten.
     *
     * @param builder
     * @param actie actieInhoud voor het voorkomen van samengestelde naam
     */
    static void leidSamengesteldeNaamAf(PersoonHisVolledigImplBuilder builder, ActieModel actie) {
        def persoon = builder.hisVolledigImpl

        def record = builder.nieuwSamengesteldeNaamRecord(actie)
        record
            .voornamen(persoon.voornamen.collect {it.persoonVoornaamHistorie.actueleRecord?.naam}.join(' '))
            .geslachtsnaamstam(persoon.geslachtsnaamcomponenten.collect {it.persoonGeslachtsnaamcomponentHistorie.actueleRecord.stam}.find())
            .voorvoegsel(persoon.geslachtsnaamcomponenten.collect {it.persoonGeslachtsnaamcomponentHistorie.actueleRecord.voorvoegsel}.find())
            .scheidingsteken(persoon.geslachtsnaamcomponenten.collect {it.persoonGeslachtsnaamcomponentHistorie.actueleRecord.scheidingsteken}.find())
            .indicatieAfgeleid(Boolean.TRUE)
            .indicatieNamenreeks(Boolean.FALSE)

        if (persoon.geslachtsnaamcomponenten) {
            persoon.geslachtsnaamcomponenten.collect {
                def actueleRecord = it.persoonGeslachtsnaamcomponentHistorie.actueleRecord
                if (actueleRecord.predicaat) {
                    record.predicaat(actueleRecord.predicaat.waarde)
                }
                if (actueleRecord.adellijkeTitel) {
                    record.adellijkeTitel(actueleRecord.adellijkeTitel.waarde)
                }
            }
        }

        record.eindeRecord()
    }

    /**
     * Leidt het naamgebruik af van de actuele samengestelde naam.
     *
     * @param builder
     * @param actie actieInhoud voor het voorkomen van naamgebruik
     */
    static void leidNaamgebruikAf(PersoonHisVolledigImplBuilder builder, ActieModel actie) {
        def samengesteldenaam = builder.hisVolledigImpl.persoonSamengesteldeNaamHistorie.actueleRecord

        if (samengesteldenaam) {
            builder.nieuwNaamgebruikRecord(actie)
                .voornamenNaamgebruik(samengesteldenaam.voornamen)
                .geslachtsnaamstamNaamgebruik(samengesteldenaam.geslachtsnaamstam)
                .indicatieNaamgebruikAfgeleid(Boolean.TRUE)
                .naamgebruik(Naamgebruik.EIGEN)
                .eindeRecord()
        }
    }

    /**
     * Voegt een afgeleid administratief voorkomen toe aan alle betrokken personen.
     *
     * @param builder
     * @param actie actieInhoud voor de voorkomens van afgeleidadministratief
     */
    static void pasBetrokkenenAfgeleidAdministratiefAan(PersoonHisVolledigImplBuilder builder, ActieModel actie) {
        def persoon = builder.hisVolledigImpl

        def betrokkenen = [] as Set<PersoonHisVolledigImpl>

        persoon.kindBetrokkenheid?.relatie?.ouderBetrokkenheden?.collect(betrokkenen) { it.persoon }
        persoon.ouderBetrokkenheden?.collect(betrokkenen) { it.relatie?.kindBetrokkenheid?.persoon }
        persoon.partnerBetrokkenheden?.collect(betrokkenen) { (it.relatie as HuwelijkGeregistreerdPartnerschapHisVolledigImpl)?.geefPartnerVan(it)?.persoon }

        betrokkenen.flatten().findAll {it != null && it.isIngeschrevene() }.each { PersoonHisVolledigImpl betrokkene ->
            betrokkene.persoonAfgeleidAdministratiefHistorie?.voegToe(maakAfgeleidAdministratief(betrokkene, actie, 2))
        }
    }

    /**
     * Maakt een nieuw afgeleidadministratief voorkomen voor een persoon.
     *
     * @param persoon persoon waar het afgl.administratief voorkomen naar wijst
     * @param actie actieInhoud voor het voorkomen
     * @param orde sorteervolgorde voor het voorkomen
     *
     * @return
     */
    static HisPersoonAfgeleidAdministratiefModel maakAfgeleidAdministratief(PersoonHisVolledig persoon, ActieModel actie, int orde) {
        LOGGER.info("HisPersoonAfgeleidAdministratiefModel aanmaken tijdstip registratie {} voor persoon {} en datumaanvang {}", actie.tijdstipRegistratie,
            persoon.getID(),
            actie.aanvangGeldigheid)
        new HisPersoonAfgeleidAdministratiefModel(persoon, actie.administratieveHandeling, actie.tijdstipRegistratie, new SorteervolgordeAttribuut((byte) orde), new JaNeeAttribuut(Boolean.FALSE), null, actie)
    }

    static maakDatum(def datIn) {
        final def datum
        if (datIn instanceof Number) {
            datum = datIn as Integer
        } else if (datIn instanceof String) {
            datum = datIn.replaceAll('/', '') as int
        } else {
            datum = 0
        }

        datum
    }
}
