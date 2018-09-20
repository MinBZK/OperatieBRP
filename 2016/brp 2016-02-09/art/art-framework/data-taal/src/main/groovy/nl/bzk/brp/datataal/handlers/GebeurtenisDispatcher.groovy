package nl.bzk.brp.datataal.handlers

import nl.bzk.brp.datataal.handlers.persoon.*
import nl.bzk.brp.datataal.handlers.persoon.afstamming.AdoptieHandler
import nl.bzk.brp.datataal.handlers.persoon.afstamming.ErkenningNaGeboorteHandler
import nl.bzk.brp.datataal.handlers.persoon.afstamming.GeboorteHandler
import nl.bzk.brp.datataal.handlers.persoon.afstamming.VaststellingOuderschapHandler
import nl.bzk.brp.datataal.handlers.persoon.afstamming.VerbeteringGeboorteakteHandler
import nl.bzk.brp.datataal.model.Acties
import nl.bzk.brp.datataal.model.GebeurtenisAttributen
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder

import static nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling.*

/**
 * Dispatcher voor de verschillende gebeurtenissen. Deze plek geeft
 * de mogelijkheid om een goede naam voor een gebeurtenis te hebben.
 */
class GebeurtenisDispatcher {
    private PersoonHisVolledigImplBuilder builder
    private PersoonHisVolledigImpl vorigePersoon

    /**
     * Constructor die door geeft wie er wordt beschreven. In uitzonderlijke
     * gevallen is er een oude situatie van een niet ingeschreven persoon.
     *
     * @param builder builder van de persoon die wordt beschreven
     * @param oudeSituatie mogelijke situatie van een niet ingeschreven persoon, die
     *      wordt "omgezet" naar een ingeschreven persoon
     */
    GebeurtenisDispatcher(final PersoonHisVolledigImplBuilder builder, PersoonHisVolledigImpl oudeSituatie = null) {
        this.builder = builder
        this.vorigePersoon = oudeSituatie
    }

    def geboorte(Map m = [:], @DelegatesTo(GeboorteHandler) Closure c) {
        def attrs = new GebeurtenisAttributen(m)
        attrs.soortHandeling = GEBOORTE_IN_NEDERLAND

        registreerGebeurtenis(new GeboorteHandler(attrs, builder), c)
    }

    def huwelijk(Map m = [:], @DelegatesTo(HuwelijkHandler) Closure c) {
        def attrs = new GebeurtenisAttributen(m)
        attrs.soortHandeling = VOLTREKKING_HUWELIJK_IN_NEDERLAND

        registreerGebeurtenis(new HuwelijkHandler(attrs, builder), c)
    }

    def partnerschap(Map m = [:], @DelegatesTo(GeregistreerdPartnerschapHandler) Closure c) {
        def attrs = new GebeurtenisAttributen(m)
        attrs.soortHandeling = AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND

        registreerGebeurtenis(new GeregistreerdPartnerschapHandler(attrs, builder), c)
    }

    def scheiding(Map m = [:], @DelegatesTo(ScheidingHandler) Closure c) {
        def attrs = new GebeurtenisAttributen(m)
        attrs.soortHandeling = ONTBINDING_HUWELIJK_IN_NEDERLAND

        registreerGebeurtenis(new ScheidingHandler(attrs, builder), c)
    }

    def verhuizing(Map m = [:], @DelegatesTo(VerhuizingHandler) Closure c) {
        def attrs = new GebeurtenisAttributen(m)

        registreerGebeurtenis(new VerhuizingHandler(attrs, builder), c)
    }

    def GBABijhoudingOverig(Map m = [:], @DelegatesTo(GBABijhoudingOverigHandler) Closure c) {
        def attrs = new GebeurtenisAttributen(m)

        registreerGebeurtenis(new GBABijhoudingOverigHandler(attrs, builder), c)
    }

    def overlijden(Map m = [:], @DelegatesTo(OverlijdenHandler) Closure c) {
        def attrs = new GebeurtenisAttributen(m)
        attrs.soortHandeling = OVERLIJDEN_IN_NEDERLAND

        registreerGebeurtenis(new OverlijdenHandler(attrs, builder), c)
    }

    def naamswijziging(Map m = [:], @DelegatesTo(NaamsWijzigingHandler) Closure c) {
        def attrs = new GebeurtenisAttributen(m)
        attrs.soortHandeling = DUMMY

        registreerGebeurtenis(new NaamsWijzigingHandler(attrs, builder), c)
    }

    def geslachtswijziging(Map m = [:], @DelegatesTo(NaamsWijzigingHandler) Closure c) {
        def attrs = new GebeurtenisAttributen(m)
        attrs.soortHandeling = WIJZIGING_GESLACHTSAANDUIDING

        registreerGebeurtenis(new GeslachtsWijzigingHandler(attrs, builder), c)
    }

    def verbeteringGeboorteakte(Map m = [:], @DelegatesTo(VerbeteringGeboorteakteHandler) Closure c) {
        def attrs = new GebeurtenisAttributen(m)
        attrs.soortHandeling = VERBETERING_GEBOORTEAKTE

        registreerGebeurtenis(new VerbeteringGeboorteakteHandler(attrs, builder), c)
    }

    def verstrekkingsbeperking(Map m = [:], @DelegatesTo(VerstrekkingsbeperkingHandler) Closure c) {
        def attrs = new GebeurtenisAttributen(m)
        attrs.soortHandeling = WIJZIGING_VERSTREKKINGSBEPERKING

        registreerGebeurtenis(new VerstrekkingsbeperkingHandler(attrs, builder), c)
    }

    def wijzigingGezag(Map m = [:], @DelegatesTo(WijzigingGezagHandler) Closure c) {
        def attrs = new GebeurtenisAttributen(m)
        attrs.soortHandeling = WIJZIGING_GEZAG

        registreerGebeurtenis(new WijzigingGezagHandler(attrs, builder), c)
    }

    def curatele(Map m = [:], @DelegatesTo(CurateleHandler) Closure c) {
        if (!m.aanvang) {
            throw new IllegalArgumentException("aanvang is verplicht voor deze gebeurtenis")
        }
        def attrs = new GebeurtenisAttributen(m)
        attrs.soortHandeling = WIJZIGING_CURATELE

        registreerGebeurtenis(new CurateleHandler(attrs, builder), c)
    }

    def afnemerindicaties(Map m = [:], @DelegatesTo(AfnemerIndicatieHandler) Closure c) {
        def attrs = new GebeurtenisAttributen(m)
        attrs.soortHandeling = PLAATSING_AFNEMERINDICATIE

        registreerGebeurtenis(new AfnemerIndicatieHandler(attrs, builder), c)
    }

    def vaderVastgesteld(Map m = [:], @DelegatesTo(VaststellingOuderschapHandler) Closure c) {
        def attrs = new GebeurtenisAttributen(m)
        attrs.soortHandeling = VASTSTELLING_OUDERSCHAP

        registreerGebeurtenis(new VaststellingOuderschapHandler(attrs, builder), c)
    }

    def erkend(Map m = [:], @DelegatesTo(ErkenningNaGeboorteHandler) Closure c) {
        def attrs = new GebeurtenisAttributen(m)
        attrs.soortHandeling = ERKENNING_NA_GEBOORTE

        registreerGebeurtenis(new ErkenningNaGeboorteHandler(attrs, builder), c)
    }

    def geadopteerd(Map m = [:], @DelegatesTo(AdoptieHandler) Closure c) {
        def attrs = new GebeurtenisAttributen(m)
        attrs.soortHandeling = ADOPTIE_INGEZETENE

        registreerGebeurtenis(new AdoptieHandler(attrs, builder), c)
    }

    def beeindigAdres(Map m = [:], @DelegatesTo(BeeindigAdresHandler) Closure c) {
        def attrs = new GebeurtenisAttributen(m)
        attrs.soortHandeling = VERHUIZING_NAAR_BUITENLAND

        registreerGebeurtenis(new BeeindigAdresHandler(attrs, builder), c)
    }


    /**
     * Doet een vestiging in Nederland voor een niet ingeschreven persoon.
     *
     * @param m attributen voor een administratieve handeling
     * @param c closure met beschrijving van de gebeurtenis
     * @return de ingeschreven versie van de opgegeven (niet ingeschreven) persoon
     */
    def vestigingInNederland(Map m = [:], @DelegatesTo(VestigingInNederlandHandler) Closure c) {
        def persoon = this.vorigePersoon
        assert !persoon.isIngeschrevene() : 'Personen van de soort INGESCHREVENE hebben reeds de Nederlandse nationaliteit'

        def attrs = new GebeurtenisAttributen(m)
        attrs.soortHandeling = VESTIGING_NIET_INGESCHREVENE

        registreerGebeurtenis(new VestigingInNederlandHandler(attrs, builder, persoon), c)
    }

    /**
     * Verklaart het huwelijk nietig
     *
     * @param m attributen voor een administratieve handeling
     * @param c closure met beschrijving van de gebeurtenis
     */
    def nietigVerklaringHuwelijk(Map m = [:], @DelegatesTo(NietigVerklaringHuwelijkHandler) Closure c) {
        def attrs = new GebeurtenisAttributen(m)
        attrs.soortHandeling = NIETIGVERKLARING_HUWELIJK

        registreerGebeurtenis(new NietigVerklaringHuwelijkHandler(attrs, builder), c);
    }

    /**
     * Plaats een onderzoek op een persoon
     *
     * @param m attributen voor een administratieve handeling
     * @param c closure met beschrijving van de gebeurtenis
     */
    def onderzoek(Map m = [:], @DelegatesTo(OnderzoekHandler) Closure c) {
        def attrs = new GebeurtenisAttributen(m)
        attrs.soortHandeling = AANVANG_ONDERZOEK

        registreerGebeurtenis(new OnderzoekHandler(attrs, builder), c)
    }

    /**
     * Plaats een onderzoek op een persoon
     *
     * @param m attributen voor een administratieve handeling
     * @param c closure met beschrijving van de gebeurtenis
     */
    def onderzoekAangemaakt(Map m = [:], @DelegatesTo(OnderzoekAangemaaktHandler) Closure c) {
        def attrs = new GebeurtenisAttributen(m)
        attrs.soortHandeling = AANVANG_ONDERZOEK

        registreerGebeurtenis(new OnderzoekAangemaaktHandler(attrs, builder), c)
    }

    /**
     * Plaats een onderzoek op een persoon
     *
     * @param m attributen voor een administratieve handeling
     * @param c closure met beschrijving van de gebeurtenis
     */
    def onderzoekBeeindigd(Map m = [:], @DelegatesTo(OnderzoekBeeindigdHandler) Closure c) {
        def attrs = new GebeurtenisAttributen(m)
        attrs.soortHandeling = AANVANG_ONDERZOEK

        registreerGebeurtenis(new OnderzoekBeeindigdHandler(attrs, builder), c)
    }

    /**
     * Plaats een onderzoek op een persoon
     *
     * @param m attributen voor een administratieve handeling
     * @param c closure met beschrijving van de gebeurtenis
     */
    def onderzoekGewijzigd(Map m = [:], @DelegatesTo(OnderzoekGewijzigdHandler) Closure c) {
        def attrs = new GebeurtenisAttributen(m)
        attrs.soortHandeling = WIJZIGING_ONDERZOEK

        registreerGebeurtenis(new OnderzoekGewijzigdHandler(attrs, builder), c)
    }

    /**
     * Gebruikt de handler om de gebeurtenis vast te leggen.
     *
     * @param handler de delegate voor de closure
     * @param c closure met beschrijving van de gebeurtenis
     * @return de acties die het resultaat zijn van de gebeurtenis
     */
    private static Acties registreerGebeurtenis(AbstractGebeurtenisHandler handler, @DelegatesTo(AbstractGebeurtenisHandler) Closure c) {
        handler.startGebeurtenis()

        def copy = (Closure) c.clone()
        copy.delegate = handler
        copy.resolveStrategy = Closure.DELEGATE_ONLY
        copy()

        handler.eindeGebeurtenis()

        handler.acties
    }
}
