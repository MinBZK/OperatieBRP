package nl.bzk.brp.datataal.dataaccess

import groovy.sql.Sql

import groovy.transform.TypeChecked
import groovy.transform.TypeCheckingMode
import nl.bzk.brp.dataaccess.repository.DocumentModelRepository
import nl.bzk.brp.dataaccess.repository.OnderzoekRepository
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository
import nl.bzk.brp.model.basis.HistorieEntiteit
import nl.bzk.brp.model.hisvolledig.impl.kern.OnderzoekHisVolledigImpl
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonOnderzoekHisVolledigImpl

import javax.inject.Inject
import javax.sql.DataSource
import nl.bzk.brp.dataaccess.repository.ActieRepository
import nl.bzk.brp.dataaccess.repository.AdministratieveHandelingRepository
import nl.bzk.brp.dataaccess.repository.PersoonHisVolledigRepository
import nl.bzk.brp.model.basis.AbstractFormeelHistorischMetActieVerantwoording
import nl.bzk.brp.model.basis.AbstractMaterieelHistorischMetActieVerantwoording
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel
import nl.bzk.brp.model.operationeel.kern.DocumentModel
import nl.bzk.brp.model.operationeel.kern.HisPersoonAfgeleidAdministratiefModel
import org.springframework.stereotype.Service
import org.springframework.test.util.ReflectionTestUtils
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional


/**
 * Hibernate implementatie om een persoon op te slaan.
 */
@Service
@Transactional(value = 'lezenSchrijvenTransactionManager', propagation = Propagation.REQUIRED)
class PersoonHibernatePersister implements PersoonPersister {

    @Inject
    private PersoonHisVolledigRepository persoonHisVolledigRepository

    @Inject
    private AdministratieveHandelingRepository administratieveHandelingRepository

    @Inject
    private ActieRepository actieRepository

    @Inject
    private OnderzoekRepository onderzoekRepository

    @Inject
    private DocumentModelRepository documentModelRepository

    @Inject
    private ReferentieDataRepository referentieDataRepository

    @Override
    PersoonHisVolledigImpl slaOp(final PersoonHisVolledigImpl persoon) {
        // opslaan acties en handelingen
        def persistentDocuments = slaDocumentenOp(persoon)
        def persistentHandelingen = slaHandelingenOp(persoon)
        def persistentOnderzoeken = slaOnderzoekenOp(persoon)

        koppelPersistentActies(persoon, persistentHandelingen)


        leidALaagAf(persoon)

        // voor niet ingeschrevenen hoort er geen afgeleid administratief te zijn
        if (!persoon.isIngeschrevene()) {
            ReflectionTestUtils.setField(persoon, 'hisPersoonAfgeleidAdministratiefLijst', null)
            ReflectionTestUtils.setField(persoon, 'persoonAfgeleidAdministratiefHistorie', null)
        }

        def opgeslagenPersoon = (PersoonHisVolledigImpl) persoonHisVolledigRepository.opslaanNieuwPersoon(persoon)
        verwijderBlobs(persoon)

        return opgeslagenPersoon
    }

    /*
     * Slaat de administratieve handelingen en acties van een persoon op.
     *
     * @param persoon
     * @return lijst van persisted administratieve handelingen
     */
    private List<DocumentModel> slaDocumentenOp(PersoonHisVolledigImpl persoon) {
        // opslaan acties en handelingen
        def handelingen = persoon.persoonAfgeleidAdministratiefHistorie.collect { HisPersoonAfgeleidAdministratiefModel it -> it.administratieveHandeling }
        List<DocumentModel> persistentDocuments = []

        handelingen.each { admhnd ->
            def acties = admhnd.acties

            acties.each { actie ->

                def bronnen = actie.bronnen

                bronnen.each { bron ->

                    if (bron?.document?.soort?.waarde != null && bron?.document?.soort?.waarde?.ID == null) {
                        // Hacky but a persisted document type is needed to persist the document itself
                        def soort = referentieDataRepository.laadAnySoortDocument()
                        bron.document.soort.waarde = soort
                    }

                    if (bron.document.ID == null) {
                        def doc = documentModelRepository.slaNieuwDocumentModel(bron.document)
                        persistentDocuments << doc
                    }
                }
            }
        }

        return persistentDocuments
    }

    /*
     * Slaat de administratieve handelingen en acties van een persoon op.
     *
     * @param persoon
     * @return lijst van persisted administratieve handelingen
     */
    private List<AdministratieveHandelingModel> slaHandelingenOp(PersoonHisVolledigImpl persoon) {
        // opslaan acties en handelingen
        def handelingen = persoon.persoonAfgeleidAdministratiefHistorie.collect { HisPersoonAfgeleidAdministratiefModel it -> it.administratieveHandeling }
        List<AdministratieveHandelingModel> persistentHandelingen = []

        handelingen.each { admhnd ->
            def acties = admhnd.acties
            def persistenAdmhnd = admhnd

            if (acties.any { it.ID == null}) {
                ReflectionTestUtils.setField(admhnd, 'acties', new HashSet())

                if (admhnd.ID == null) {
                    persistenAdmhnd = administratieveHandelingRepository.opslaanNieuwAdministratieveHandeling(admhnd)
                }

                acties.each {
                    if (it.ID == null) {
                        ReflectionTestUtils.setField(it, 'administratieveHandeling', persistenAdmhnd)
                        persistenAdmhnd.acties << actieRepository.opslaanNieuwActie(it)
                    } else {
                        persistenAdmhnd.acties << it
                    }
                }
            }
            persistentHandelingen << persistenAdmhnd
        }

        return persistentHandelingen
    }

    /*
     * Vervangt referenties naar acties door de persistent equivalent daarvan, zodat de persoon
     * succesvol kan worden opgeslagen door Hibernate.
     *
     * @param persoon de persoon instantie
     * @param persistentHandelingen de persistent administratieve handelingen
     */
    @TypeChecked(TypeCheckingMode.SKIP)
    private void koppelPersistentActies(PersoonHisVolledigImpl persoon, List<AdministratieveHandelingModel> persistentHandelingen) {
        def persistentActies = persistentHandelingen.collect { it.acties }.flatten()

        persoon.voorkomens.each { voorkomen ->
            koppelActiesOpHistorieVoorkomen(voorkomen, persistentActies)

            if (voorkomen instanceof HisPersoonAfgeleidAdministratiefModel) {
                def persistentHandeling = persistentHandelingen.find { it.gelijk(voorkomen.administratieveHandeling) }
                if (persistentHandeling) {
                    ReflectionTestUtils.setField(voorkomen, 'administratieveHandeling', persistentHandeling)
                }
            }
        }

        persoon.onderzoeken.each { onderzoek ->
            onderzoek.onderzoek.onderzoekHistorie.each { voorkomen ->
                koppelActiesOpHistorieVoorkomen(voorkomen, persistentActies)
            }
        }
    }

    private void koppelActiesOpHistorieVoorkomen(HistorieEntiteit voorkomen, def persistentActies) {
        if (voorkomen instanceof AbstractFormeelHistorischMetActieVerantwoording) {
            def entiteit = (AbstractFormeelHistorischMetActieVerantwoording) voorkomen
            entiteit.verantwoordingInhoud = persistentActies.find {
                it.gelijk(entiteit.verantwoordingInhoud)
            } ?: entiteit.verantwoordingInhoud
            entiteit.verantwoordingVerval = persistentActies.find {
                it.gelijk(entiteit.verantwoordingVerval)
            } ?: entiteit.verantwoordingVerval

        } else if (voorkomen instanceof AbstractMaterieelHistorischMetActieVerantwoording) {
            def entiteit = (AbstractMaterieelHistorischMetActieVerantwoording) voorkomen
            entiteit.verantwoordingAanpassingGeldigheid = persistentActies.find {
                it.gelijk(entiteit.verantwoordingAanpassingGeldigheid)
            } ?: entiteit.verantwoordingAanpassingGeldigheid
            entiteit.verantwoordingInhoud = persistentActies.find {
                it.gelijk(entiteit.verantwoordingInhoud)
            } ?: entiteit.verantwoordingInhoud
            entiteit.verantwoordingVerval = persistentActies.find {
                it.gelijk(entiteit.verantwoordingVerval)
            } ?: entiteit.verantwoordingVerval
        }
    }

    /*
     * Leidt de van een persoon de A-laag af, zodat de opslag ervan goed gaat.
     *
     * @param persoon
     */
    private void leidALaagAf(PersoonHisVolledigImpl persoon) {
        persoon.leidALaagAf()

        persoon.with {
            voornamen?.each { it.leidALaagAf() }
            geslachtsnaamcomponenten?.each { it.leidALaagAf() }
            verificaties?.each { it.leidALaagAf() }
            nationaliteiten?.each { it.leidALaagAf() }
            adressen?.each { it.leidALaagAf() }
            reisdocumenten?.each { it.leidALaagAf() }

            onderzoeken?.each { it.leidALaagAf() }
            verstrekkingsbeperkingen?.each { it.leidALaagAf() }
            afnemerindicaties?.each { it.leidALaagAf() }

            //indicaties
            indicatieDerdeHeeftGezag?.leidALaagAf()
            indicatieOnderCuratele?.leidALaagAf()
            indicatieVolledigeVerstrekkingsbeperking?.leidALaagAf()
            indicatieVastgesteldNietNederlander?.leidALaagAf()
            indicatieBehandeldAlsNederlander?.leidALaagAf()
            indicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument?.leidALaagAf()
            indicatieStaatloos?.leidALaagAf()
            indicatieBijzondereVerblijfsrechtelijkePositie?.leidALaagAf()
        }
    }

    /**
     * Verwijdert de bestaande BLOB's (Json representatie) van een persoon en
     * zijn betrokken personen, zodat de software de correcte (db) vorm van de persoon
     * gebruikt en niet zijn "oude" blob.
     *
     * @param persoonHisVolledig
     */
    void verwijderBlobs(final PersoonHisVolledigImpl persoonHisVolledig) {
        Set<Integer> personen = [] as Set

        persoonHisVolledig.betrokkenheden.each { betr ->
            betr.relatie.betrokkenheden.collect(personen) { it.persoon?.ID }
        }

        def sql = new Sql(SpringBeanProvider.getBean('lezenSchrijvenDataSource', DataSource))

        personen.grep().each { persId ->
            sql.execute('DELETE FROM kern.perscache WHERE pers = ?', persId)
        }
    }

    /*
     * Slaat de onderzoeken van een persoon op.
     *
     * @param persoon
     */
    def List<OnderzoekHisVolledigImpl> slaOnderzoekenOp(PersoonHisVolledigImpl persoon) {
        def onderzoeken = persoon.onderzoeken.collect { PersoonOnderzoekHisVolledigImpl it -> it.onderzoek }
        def List<OnderzoekHisVolledigImpl> persistentOnderzoeken = []

        onderzoeken.each { OnderzoekHisVolledigImpl onderzoek ->
            if (onderzoek.ID == null) {
                persistentOnderzoeken << onderzoekRepository.slaNieuwOnderzoekOp(onderzoek)
            }
        }

        return persistentOnderzoeken
    }
}
