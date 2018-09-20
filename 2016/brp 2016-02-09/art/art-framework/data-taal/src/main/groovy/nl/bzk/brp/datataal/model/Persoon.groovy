package nl.bzk.brp.datataal.model

import nl.bzk.brp.blobifier.service.BlobifierService
import nl.bzk.brp.dataaccess.exceptie.PersoonNietGevondenExceptie
import nl.bzk.brp.dataaccess.repository.jpa.PersoonHisVolledigJpaRepository
import nl.bzk.brp.datataal.dataaccess.PersoonPersister
import nl.bzk.brp.datataal.dataaccess.SpringBeanProvider
import nl.bzk.brp.datataal.handlers.GebeurtenisDispatcher
import nl.bzk.brp.datataal.handlers.nietingeschrevene.NietIngeschreveneHandler
import nl.bzk.brp.datataal.handlers.onbekende.OnbekendeHandler
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
import nl.bzk.brp.serialisatie.persoon.PersoonHisVolledigStringSerializer
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * De klasse Persoon is het beginpunt voor het beschrijven van personen
 * die worden opgeslagen in de database. De {@link nl.bzk.brp.datataal.execution.PersoonDSLExecutor}
 * doet de import van de static methodes automatische, dus de {@code Persoon.} prefix
 * is niet noodzakelijk bij het beschrijven van een persoon.
 */
final class Persoon {
    private static final Logger LOGGER = LoggerFactory.getLogger(Persoon)
    private static final def persoonSerializer = new PersoonHisVolledigStringSerializer()

    private Persoon() {
        // alleen de static methodes gebruiken
    }

    /**
     * Beschrijft de gebeurtenissen die de historie van een persoon beschrijven.
     *
     * @param c de beschrijving van de gebeurtenissen
     * @return de geconstrueerde persoon
     */
    static PersoonHisVolledigImpl uitGebeurtenissen(@DelegatesTo(GebeurtenisDispatcher) Closure c) {
        def builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)

        GebeurtenisDispatcher handler = new GebeurtenisDispatcher(builder)
        def clone = (Closure) c.clone()
        clone.delegate = handler
        clone.resolveStrategy = Closure.DELEGATE_ONLY
        clone()

        return builder.build()
    }

    /**
     * Maak een niet ingeschreven persoon. Deze kan alleen waardes voor de groepen geboorte,
     * samengestelde naam en geslachtsaanduiding hebben. Deze worden gevuld door aanroepen
     * in de {@link NietIngeschreveneHandler}.
     *
     * @param c beschrijving van de niet ingeschrevene
     * @return de geconstrueerde persoon
     */
    static PersoonHisVolledigImpl nietIngeschrevene(Map m = [:], @DelegatesTo(NietIngeschreveneHandler) Closure c) {
        def builder = new PersoonHisVolledigImplBuilder(SoortPersoon.NIET_INGESCHREVENE)

        NietIngeschreveneHandler handler = new NietIngeschreveneHandler(new GebeurtenisAttributen(m), builder)

        def clone = (Closure) c.clone()
        clone.delegate = handler
        clone.resolveStrategy = Closure.DELEGATE_ONLY
        clone()

        return builder.build()
    }

    /**
     * Maak een onbekende persoon. Deze kan alleen waarden voor de groepen ids, geboorte, samengestelde naam en geslachtsaanduiding hebben.
     * Deze worden gevuld door aanroepen in de {@link OnbekendeHandler}.
     *
     * @param c beschrijving van de onbekende
     * @return de geconstrueerde persoon
     */
    static PersoonHisVolledigImpl onbekende(Map m = [:], @DelegatesTo(OnbekendeHandler) Closure c) {
        def builder = new PersoonHisVolledigImplBuilder(SoortPersoon.ONBEKEND);

        OnbekendeHandler handler = new OnbekendeHandler(new GebeurtenisAttributen(m), builder)

        def clone = (Closure) c.clone()
        clone.delegate = handler
        clone.resolveStrategy = Closure.DELEGATE_ONLY
        clone()

        return builder.build()
    }

    /**
     * Beschrijft de gebeurtenissen die de historie van een persoon beschrijven.
     *
     * @param nietIngeschrevene de niet ingeschreven persoon die wordt ingeschreven
     * @param c de beschrijving van de gebeurtenissen
     * @return de geconstrueerde persoon
     */
    static PersoonHisVolledigImpl ingeschreveneVan(PersoonHisVolledigImpl nietIngeschrevene, @DelegatesTo(GebeurtenisDispatcher) Closure c) {
        assert !nietIngeschrevene.isIngeschrevene(): 'Personen van de soort INGESCHREVENE kunnen niet meer ingeschrevene worden'

        def builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)

        GebeurtenisDispatcher handler = new GebeurtenisDispatcher(builder, nietIngeschrevene)
        def clone = (Closure) c.clone()
        clone.delegate = handler
        clone.resolveStrategy = Closure.DELEGATE_ONLY
        clone()

        return builder.build()
    }

    /**
     * Haalt een bestaande persoon op uit de database.
     * Dit kan op basis van de persoonId in de database, of de BSN van de persoon.
     *
     * @param data
     * @return de gezochte persoon
     *
     * @see #metBsn(java.lang.Integer)
     * @see #metId(java.lang.Integer)
     */
    static PersoonHisVolledigImpl uitDatabase(Map<String, Integer> data) {
        // valideer input
        assert data.keySet().intersect(['persoon', 'bsn']).sort() == data.keySet().sort(): 'Voor het opzoeken van een persoon kunnen de opties: [persoon, bsn] worden gebruikt'

        if (data.containsKey('persoon')) {
            return metId(data.persoon as Integer)
        } else if (data.containsKey('bsn')) {
            return metBsn(data.bsn as Integer)
        }

        return null
    }

    /**
     * Haalt een bestaande persoon op uit de database obv burgerservicenummer.
     *
     * @param bsn de BSN van de persoon
     * @return de gezochte persoon
     *
     * @see #uitDatabase(java.util.Map)
     */
    static PersoonHisVolledigImpl metBsn(Integer bsn) {
        PersoonHisVolledigJpaRepository persoonRepository = SpringBeanProvider.getBean(PersoonHisVolledigJpaRepository)
        def persoon = (PersoonHisVolledigImpl) persoonRepository.vindPersoonMetBsn(bsn)

        if (!persoon) {
            throw new PersoonNietGevondenExceptie("Persoon met BSN:$bsn is niet gevonden")
        }

        persoon
    }

    /**
     * Haalt een bestaande persoon op uit de database obv ID.
     *
     * @param id de ID van de persoon
     * @return de gezochte persoon
     *
     * @see #uitDatabase(java.util.Map)
     */
    static PersoonHisVolledigImpl metId(Integer id) {
        PersoonHisVolledigJpaRepository persoonRepository = SpringBeanProvider.getBean(PersoonHisVolledigJpaRepository)
        def persoon = (PersoonHisVolledigImpl) persoonRepository.leesGenormalizeerdModel(id)

        if (!persoon) {
            throw new PersoonNietGevondenExceptie("Persoon met database id:$id is niet gevonden")
        }

        persoon
    }

    /**
     * Voeg nieuwe handelingen toe aan een bestaande persoon. Geeft hiermee de mogelijkheid
     * om de historie van een persoon uit te breiden.
     *
     * @param persoon De te wijzigen persoon
     * @param c beschrijving van de nieuwe gebeurtenissen
     */
    static void nieuweGebeurtenissenVoor(PersoonHisVolledigImpl persoon,
                                         @DelegatesTo(value = GebeurtenisDispatcher, strategy = Closure.DELEGATE_ONLY) Closure c) {
        def builder = new PersoonHisVolledigImplBuilder(persoon)

        GebeurtenisDispatcher handler = new GebeurtenisDispatcher(builder)
        def clone = (Closure) c.clone()
        clone.delegate = handler
        clone.resolveStrategy = Closure.DELEGATE_ONLY
        clone()

        builder.build()
    }

    /**
     * Slaat een persoon op, en geeft de persistent versie terug.
     *
     * @param pers de persoon die opgeslagen moet worden
     * @return de opgeslagen versie
     */
    static PersoonHisVolledigImpl slaOp(PersoonHisVolledigImpl pers) {
        def persoon = SpringBeanProvider.getBean(PersoonPersister).slaOp(pers)

//        try {
//            def jsonBytes = persoonSerializer.serialiseer(persoon)
//            LOGGER.info('De volgende persoon is gebouwd: {}', new String(jsonBytes))
//        } catch (SerialisatieExceptie exceptie) {
//            LOGGER.warn('Error serializing: {}', exceptie);
//        }

        persoon
    }

    /**
     * Maakt van een persoon een blob en slaat deze op.
     *
     * @param pers de persoon om te blobify'en
     */
    static void maakBlobVoor(PersoonHisVolledigImpl pers) {
        def blobifier = SpringBeanProvider.getBean(BlobifierService)

        blobifier.blobify(pers, false)
    }

    /**
     * Geeft de waarde van vandaag.
     *
     * @return integer representatie van de huidige datum
     */
    static Integer vandaag() {
        DatumAttribuut.vandaag().waarde
    }

    /**
     * Geeft de waarde van morgen.
     *
     * @return integer representatie van morgen
     */
    static Integer morgen() {
        DatumAttribuut.morgen().waarde
    }

    /**
     * Geeft de waarde van gisteren.
     *
     * @return integer representatie van gisteren
     */
    static Integer gisteren() {
        DatumAttribuut.gisteren().waarde
    }
}
