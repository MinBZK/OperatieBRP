/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.migratiebrp.conversie.model.Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpAdresInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBehandeldAlsNederlanderIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDeelnameEuVerkiezingenInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDerdeHeeftGezagIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsnaamcomponentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpInschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstGezagsVerhoudingGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstHuwelijkOfGpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpMigratieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNaamgebruikInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNummerverwijzingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOnderCurateleIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOverlijdenInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonAfgeleidAdministratiefInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonskaartInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpStaatloosIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpUitsluitingKiesrechtInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVastgesteldNietNederlanderIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerblijfsrechtInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerificatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerstrekkingsbeperkingIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVoornaamInhoud;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * Deze class representeert de migratie specifieke kijk op een BRP persoon. De BRP persoonslijst.
 */
@Root
public final class BrpPersoonslijst implements Persoonslijst {

    private static final String NAAMGEBRUIK_STAPEL_LABEL = "naamgebruikStapel";
    private static final String ADRES_STAPEL_LABEL = "adresStapel";
    private static final String BEHANDELD_ALS_NEDERLANDER_INDICATIE_STAPEL_LABEL = "behandeldAlsNederlanderIndicatieStapel";
    private static final String SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT_STAPEL_LABEL =
            "signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel";
    private static final String BIJHOUDING_STAPEL_LABEL = "bijhoudingStapel";
    private static final String DERDE_HEEFT_GEZAG_INDICATIE_STAPEL_LABEL = "derdeHeeftGezagIndicatieStapel";
    private static final String DEELNAME_EU_VERKIEZINGEN_STAPEL_LABEL = "deelnameEuVerkiezingenStapel";
    private static final String GEBOORTE_STAPEL_LABEL = "geboorteStapel";
    private static final String GESLACHTSAANDUIDING_STAPEL_LABEL = "geslachtsaanduidingStapel";
    private static final String GESLACHTSNAAMCOMPONENT_STAPELS_LABEL = "geslachtsnaamcomponentStapels";
    private static final String IDENTIFICATIENUMMER_STAPEL_LABEL = "identificatienummerStapel";
    private static final String MIGRATIE_STAPEL_LABEL = "migratieStapel";
    private static final String INSCHRIJVING_STAPEL_LABEL = "inschrijvingStapel";
    private static final String NATIONALITEIT_STAPELS_LABEL = "nationaliteitStapels";
    private static final String ONDER_CURATELE_INDICATIE_STAPEL_LABEL = "onderCurateleIndicatieStapel";
    private static final String OVERLIJDEN_STAPEL_LABEL = "overlijdenStapel";
    private static final String PERSOONSKAART_STAPEL_LABEL = "persoonskaartStapel";
    private static final String REISDOCUMENT_STAPELS_LABEL = "reisdocumentStapels";
    private static final String RELATIES_LABEL = "relaties";
    private static final String SAMENGESTELDE_NAAM_STAPEL_LABEL = "samengesteldeNaamStapel";
    private static final String STAATLOOS_INDICATIE_STAPEL_LABEL = "staatloosIndicatieStapel";
    private static final String UITSLUITING_KIESRECHT_STAPEL_LABEL = "uitsluitingKiesrechtStapel";
    private static final String VASTGESTELD_NIET_NEDERLANDER_INDICATIE_STAPEL_LABEL = "vastgesteldNietNederlanderIndicatieStapel";
    private static final String VERBLIJFSRECHT_STAPEL_LABEL = "verblijfsrechtStapel";
    private static final String VERIFICATIE_STAPELS_LABEL = "verificatieStapels";
    private static final String VERSTREKKINGSBEPERKING_INDICATIE_STAPEL_LABEL = "verstrekkingsbeperkingIndicatieStapel";
    private static final String VOORNAAM_STAPELS_LABEL = "voornaamStapels";
    private final Integer persoonId;
    private final BrpStapel<BrpNaamgebruikInhoud> naamgebruikStapel;
    private final BrpStapel<BrpAdresInhoud> adresStapel;
    private final BrpStapel<BrpPersoonAfgeleidAdministratiefInhoud> persoonAfgeleidAdministratiefStapel;
    private final BrpStapel<BrpBehandeldAlsNederlanderIndicatieInhoud> behandeldAlsNederlanderIndicatieStapel;
    private final BrpStapel<BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud> signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel;
    private final BrpStapel<BrpBijhoudingInhoud> bijhoudingStapel;
    private final BrpStapel<BrpDerdeHeeftGezagIndicatieInhoud> derdeHeeftGezagIndicatieStapel;
    private final BrpStapel<BrpDeelnameEuVerkiezingenInhoud> deelnameEuVerkiezingenStapel;
    private final BrpStapel<BrpGeboorteInhoud> geboorteStapel;
    private final BrpStapel<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingStapel;
    private final List<BrpStapel<BrpGeslachtsnaamcomponentInhoud>> geslachtsnaamcomponentStapels;
    private final BrpStapel<BrpIdentificatienummersInhoud> identificatienummerStapel;
    private final BrpStapel<BrpMigratieInhoud> migratieStapel;
    private final BrpStapel<BrpInschrijvingInhoud> inschrijvingStapel;
    private final List<BrpStapel<BrpNationaliteitInhoud>> nationaliteitStapels;
    private final BrpStapel<BrpNummerverwijzingInhoud> nummerverwijzingStapel;
    private final BrpStapel<BrpOnderCurateleIndicatieInhoud> onderCurateleIndicatieStapel;
    private final BrpStapel<BrpOverlijdenInhoud> overlijdenStapel;
    private final BrpStapel<BrpPersoonskaartInhoud> persoonskaartStapel;
    private final List<BrpStapel<BrpReisdocumentInhoud>> reisdocumentStapels;
    private final List<BrpRelatie> relaties;
    private final BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel;
    private final BrpStapel<BrpStaatloosIndicatieInhoud> staatloosIndicatieStapel;
    private final BrpStapel<BrpUitsluitingKiesrechtInhoud> uitsluitingKiesrechtStapel;
    private final BrpStapel<BrpVastgesteldNietNederlanderIndicatieInhoud> vastgesteldNietNederlanderIndicatieStapel;
    private final BrpStapel<BrpVerblijfsrechtInhoud> verblijfsrechtStapel;
    private final BrpStapel<BrpVerstrekkingsbeperkingIndicatieInhoud> verstrekkingsbeperkingIndicatieStapel;
    private final List<BrpStapel<BrpVoornaamInhoud>> voornaamStapels;
    private final BrpStapel<BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud> bijzondereVerblijfsrechtelijkePositieIndicatieStapel;
    private final List<BrpStapel<BrpVerificatieInhoud>> verificatieStapels;

    // IST stapels
    private final BrpStapel<BrpIstRelatieGroepInhoud> istOuder1Stapel;
    private final BrpStapel<BrpIstRelatieGroepInhoud> istOuder2Stapel;
    private final List<BrpStapel<BrpIstHuwelijkOfGpGroepInhoud>> istHuwelijkOfGpStapels;
    private final List<BrpStapel<BrpIstRelatieGroepInhoud>> istKindStapels;
    private final BrpStapel<BrpIstGezagsVerhoudingGroepInhoud> istGezagsverhoudingsStapel;

    /**
     * Maakt een nieuw BrpPersoonslijst object.
     *
     * Note: default access modifier, de persoonslijst kan alleen worden aangemaakt via de builder
     *
     * @param naamgebruikStapel
     *            the naamgebruik stapel
     * @param adresStapel
     *            the adres stapel
     * @param persoonAfgeleidAdministratiefStapel
     *            the persoon afgeleid administratief stapel
     * @param behandeldAlsNederlanderIndicatieStapel
     *            the behandeld als nederlander indicatie stapel
     * @param signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel
     *            the signalering met betrekking tot verstrekken reisdocument stapel
     * @param bijhoudingStapel
     *            the bijhouding stapel
     * @param derdeHeeftGezagIndicatieStapel
     *            the derde heeft gezag indicatie stapel
     * @param deelnameEuVerkiezingenStapel
     *            the deelname eu verkiezingen stapel
     * @param geboorteStapel
     *            the geboorte stapel
     * @param geslachtsaanduidingStapel
     *            the geslachtsaanduiding stapel
     * @param geslachtsnaamcomponentStapels
     *            the geslachtsnaamcomponent stapels
     * @param identificatienummerStapel
     *            the identificatienummer stapel
     * @param migratieStapel
     *            the migratie stapel
     * @param inschrijvingStapel
     *            the inschrijving stapel
     * @param nationaliteitStapels
     *            the nationaliteit stapels
     * @param nummerverwijzingStapel
     *            the nummerverwijzing stapel
     * @param onderCurateleIndicatieStapel
     *            the onder curatele indicatie stapel
     * @param overlijdenStapel
     *            the overlijden stapel
     * @param persoonskaartStapel
     *            the persoonskaart stapel
     * @param reisdocumentStapels
     *            the reisdocument stapels
     * @param relaties
     *            the relaties
     * @param samengesteldeNaamStapel
     *            the samengestelde naam stapel
     * @param staatloosIndicatieStapel
     *            the staatloos indicatie stapel
     * @param uitsluitingKiesrechtStapel
     *            the uitsluiting kiesrecht stapel
     * @param vastgesteldNietNederlanderIndicatieStapel
     *            the vastgesteld niet nederlander indicatie stapel
     * @param verblijfsrechtStapel
     *            the verblijfsrecht stapel
     * @param verstrekkingsbeperkingIndicatieStapel
     *            the verstrekkingsbeperking indicatie stapel
     * @param voornaamStapels
     *            the voornaam stapels
     * @param bijzondereVerblijfsrechtelijkePositieIndicatieStapel
     *            the bijzondere verblijfsrechtelijke positie indicatie stapel
     * @param verificatieStapels
     *            the verificatie stapels
     * @param istOuder1Stapel
     *            the ist ouder1 stapel
     * @param istOuder2Stapel
     *            the ist ouder2 stapel
     * @param istHuwelijkOfGpStapels
     *            the ist huwelijk of gp stapels
     * @param istKindStapels
     *            the ist kind stapels
     * @param istGezagsverhoudingsStapel
     *            De IST gezagsverhouding stapel
     */
    @SuppressWarnings("checkstyle:parameternumber")
    BrpPersoonslijst(
        /* Meer dan 7 parameters is in constructors van immutable model klassen getolereerd. */
        @Element(name = NAAMGEBRUIK_STAPEL_LABEL, required = false) final BrpStapel<BrpNaamgebruikInhoud> naamgebruikStapel,
        @Element(name = ADRES_STAPEL_LABEL, required = false) final BrpStapel<BrpAdresInhoud> adresStapel,
        @Element(name = "persoonAfgeleidAdministratiefStapel", required = false) final BrpStapel<BrpPersoonAfgeleidAdministratiefInhoud> persoonAfgeleidAdministratiefStapel,
        @Element(name = BEHANDELD_ALS_NEDERLANDER_INDICATIE_STAPEL_LABEL, required = false) final BrpStapel<BrpBehandeldAlsNederlanderIndicatieInhoud> behandeldAlsNederlanderIndicatieStapel,
        @Element(name = SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT_STAPEL_LABEL, required = false) final BrpStapel<BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud> signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel,
        @Element(name = BIJHOUDING_STAPEL_LABEL, required = false) final BrpStapel<BrpBijhoudingInhoud> bijhoudingStapel,
        @Element(name = DERDE_HEEFT_GEZAG_INDICATIE_STAPEL_LABEL, required = false) final BrpStapel<BrpDerdeHeeftGezagIndicatieInhoud> derdeHeeftGezagIndicatieStapel,
        @Element(name = DEELNAME_EU_VERKIEZINGEN_STAPEL_LABEL, required = false) final BrpStapel<BrpDeelnameEuVerkiezingenInhoud> deelnameEuVerkiezingenStapel,
        @Element(name = GEBOORTE_STAPEL_LABEL, required = false) final BrpStapel<BrpGeboorteInhoud> geboorteStapel,
        @Element(name = GESLACHTSAANDUIDING_STAPEL_LABEL, required = false) final BrpStapel<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingStapel,
        @ElementList(name = GESLACHTSNAAMCOMPONENT_STAPELS_LABEL, entry = "geslachtsnaamcomponentStapel", type = BrpStapel.class, required = false) final List<BrpStapel<BrpGeslachtsnaamcomponentInhoud>> geslachtsnaamcomponentStapels,
        @Element(name = IDENTIFICATIENUMMER_STAPEL_LABEL, required = false) final BrpStapel<BrpIdentificatienummersInhoud> identificatienummerStapel,
        @Element(name = MIGRATIE_STAPEL_LABEL, required = false) final BrpStapel<BrpMigratieInhoud> migratieStapel,
        @Element(name = INSCHRIJVING_STAPEL_LABEL, required = false) final BrpStapel<BrpInschrijvingInhoud> inschrijvingStapel,
        @ElementList(name = NATIONALITEIT_STAPELS_LABEL, entry = "nationaliteitStapel", type = BrpStapel.class, required = false) final List<BrpStapel<BrpNationaliteitInhoud>> nationaliteitStapels,
        @Element(name = "nummerverwijzingStapel", required = false) final BrpStapel<BrpNummerverwijzingInhoud> nummerverwijzingStapel,
        @Element(name = ONDER_CURATELE_INDICATIE_STAPEL_LABEL, required = false) final BrpStapel<BrpOnderCurateleIndicatieInhoud> onderCurateleIndicatieStapel,
        @Element(name = OVERLIJDEN_STAPEL_LABEL, required = false) final BrpStapel<BrpOverlijdenInhoud> overlijdenStapel,
        @Element(name = PERSOONSKAART_STAPEL_LABEL, required = false) final BrpStapel<BrpPersoonskaartInhoud> persoonskaartStapel,
        @ElementList(name = REISDOCUMENT_STAPELS_LABEL, entry = "reisdocumentStapel", type = BrpStapel.class, required = false) final List<BrpStapel<BrpReisdocumentInhoud>> reisdocumentStapels,
        @ElementList(name = RELATIES_LABEL, entry = "relatie", type = BrpRelatie.class, required = false) final List<BrpRelatie> relaties,
        @Element(name = SAMENGESTELDE_NAAM_STAPEL_LABEL, required = false) final BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel,
        @Element(name = STAATLOOS_INDICATIE_STAPEL_LABEL, required = false) final BrpStapel<BrpStaatloosIndicatieInhoud> staatloosIndicatieStapel,
        @Element(name = UITSLUITING_KIESRECHT_STAPEL_LABEL, required = false) final BrpStapel<BrpUitsluitingKiesrechtInhoud> uitsluitingKiesrechtStapel,
        @Element(name = VASTGESTELD_NIET_NEDERLANDER_INDICATIE_STAPEL_LABEL, required = false) final BrpStapel<BrpVastgesteldNietNederlanderIndicatieInhoud> vastgesteldNietNederlanderIndicatieStapel,
        @Element(name = VERBLIJFSRECHT_STAPEL_LABEL, required = false) final BrpStapel<BrpVerblijfsrechtInhoud> verblijfsrechtStapel,
        @Element(name = VERSTREKKINGSBEPERKING_INDICATIE_STAPEL_LABEL, required = false) final BrpStapel<BrpVerstrekkingsbeperkingIndicatieInhoud> verstrekkingsbeperkingIndicatieStapel,
        @ElementList(name = VOORNAAM_STAPELS_LABEL, entry = "voornaamStapel", type = BrpStapel.class, required = false) final List<BrpStapel<BrpVoornaamInhoud>> voornaamStapels,
        @Element(name = "bijzondereVerblijfsrechtelijkePositieIndicatieStapel", required = false) final BrpStapel<BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud> bijzondereVerblijfsrechtelijkePositieIndicatieStapel,
        @ElementList(name = VERIFICATIE_STAPELS_LABEL, entry = "verificatieStapel", type = BrpStapel.class, required = false) final List<BrpStapel<BrpVerificatieInhoud>> verificatieStapels,
        @Element(name = "istOuder1Stapel", required = false) final BrpStapel<BrpIstRelatieGroepInhoud> istOuder1Stapel,
        @Element(name = "istOuder2Stapel", required = false) final BrpStapel<BrpIstRelatieGroepInhoud> istOuder2Stapel,
        @ElementList(name = "istHuwelijkOfGpStapels", entry = "istHuwelijkOfGpStapel", type = BrpStapel.class, required = false) final List<BrpStapel<BrpIstHuwelijkOfGpGroepInhoud>> istHuwelijkOfGpStapels,
        @ElementList(name = "istKindStapels", entry = "istKindStapel", type = BrpStapel.class, required = false) final List<BrpStapel<BrpIstRelatieGroepInhoud>> istKindStapels,
        @Element(name = "istGezagsverhoudingsStapel", required = false) final BrpStapel<BrpIstGezagsVerhoudingGroepInhoud> istGezagsverhoudingsStapel)
    {
        this(null,
             naamgebruikStapel,
             adresStapel,
             persoonAfgeleidAdministratiefStapel,
             behandeldAlsNederlanderIndicatieStapel,
             signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel,
             bijhoudingStapel,
             derdeHeeftGezagIndicatieStapel,
             deelnameEuVerkiezingenStapel,
             geboorteStapel,
             geslachtsaanduidingStapel,
             geslachtsnaamcomponentStapels,
             identificatienummerStapel,
             migratieStapel,
             inschrijvingStapel,
             nationaliteitStapels,
             nummerverwijzingStapel,
             onderCurateleIndicatieStapel,
             overlijdenStapel,
             persoonskaartStapel,
             reisdocumentStapels,
             relaties,
             samengesteldeNaamStapel,
             staatloosIndicatieStapel,
             uitsluitingKiesrechtStapel,
             vastgesteldNietNederlanderIndicatieStapel,
             verblijfsrechtStapel,
             verstrekkingsbeperkingIndicatieStapel,
             voornaamStapels,
             bijzondereVerblijfsrechtelijkePositieIndicatieStapel,
             verificatieStapels,
             istOuder1Stapel,
             istOuder2Stapel,
             istHuwelijkOfGpStapels,
             istKindStapels,
             istGezagsverhoudingsStapel);
    }

    /**
     * Maak een nieuwe brp persoonslijst.
     *
     * Note: default access modifier, de persoonslijst kan alleen worden aangemaakt via de builder
     *
     * @param persoonId
     *            the persoon id
     * @param naamgebruikStapel
     *            the naamgebruik stapel
     * @param adresStapel
     *            the adres stapel
     * @param persoonAfgeleidAdministratiefStapel
     *            the persoon afgeleid administratief stapel
     * @param behandeldAlsNederlanderIndicatieStapel
     *            the behandeld als nederlander indicatie stapel
     * @param signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel
     *            the signalering met betrekking tot verstrekken reisdocument stapel
     * @param bijhoudingStapel
     *            the bijhouding stapel
     * @param derdeHeeftGezagIndicatieStapel
     *            the derde heeft gezag indicatie stapel
     * @param deelnameEuVerkiezingenStapel
     *            the deelname eu verkiezingen stapel
     * @param geboorteStapel
     *            the geboorte stapel
     * @param geslachtsaanduidingStapel
     *            the geslachtsaanduiding stapel
     * @param geslachtsnaamcomponentStapels
     *            the geslachtsnaamcomponent stapels
     * @param identificatienummerStapel
     *            the identificatienummer stapel
     * @param migratieStapel
     *            the migratie stapel
     * @param inschrijvingStapel
     *            the inschrijving stapel
     * @param nationaliteitStapels
     *            the nationaliteit stapels
     * @param nummerverwijzingStapel
     *            the nummerverwijzing stapel
     * @param onderCurateleIndicatieStapel
     *            the onder curatele indicatie stapel
     * @param overlijdenStapel
     *            the overlijden stapel
     * @param persoonskaartStapel
     *            the persoonskaart stapel
     * @param reisdocumentStapels
     *            the reisdocument stapels
     * @param relaties
     *            the relaties
     * @param samengesteldeNaamStapel
     *            the samengestelde naam stapel
     * @param staatloosIndicatieStapel
     *            the staatloos indicatie stapel
     * @param uitsluitingKiesrechtStapel
     *            the uitsluiting kiesrecht stapel
     * @param vastgesteldNietNederlanderIndicatieStapel
     *            the vastgesteld niet nederlander indicatie stapel
     * @param verblijfsrechtStapel
     *            the verblijfsrecht stapel
     * @param verstrekkingsbeperkingIndicatieStapel
     *            the verstrekkingsbeperking indicatie stapel
     * @param voornaamStapels
     *            the voornaam stapels
     * @param bijzondereVerblijfsrechtelijkePositieIndicatieStapel
     *            the bijzondere verblijfsrechtelijke positie indicatie stapel
     * @param verificatieStapels
     *            the verificatie stapels
     * @param istOuder1Stapel
     *            the ist ouder1 stapel
     * @param istOuder2Stapel
     *            the ist ouder2 stapel
     * @param istHuwelijkOfGpStapels
     *            the ist huwelijk of gp stapels
     * @param istKindStapels
     *            the ist kind stapels
     * @param istGezagsverhoudingsStapel
     *            De IST gezagsverhouding stapel
     */
    @SuppressWarnings("checkstyle:parameternumber")
    BrpPersoonslijst(
        /* Meer dan 7 parameters is in constructors van immutable model klassen getolereerd. */
        @Element(name = "persoonId", required = false) final Integer persoonId,
        @Element(name = NAAMGEBRUIK_STAPEL_LABEL, required = false) final BrpStapel<BrpNaamgebruikInhoud> naamgebruikStapel,
        @Element(name = ADRES_STAPEL_LABEL, required = false) final BrpStapel<BrpAdresInhoud> adresStapel,
        @Element(name = "persoonAfgeleidAdministratiefStapel", required = false) final BrpStapel<BrpPersoonAfgeleidAdministratiefInhoud> persoonAfgeleidAdministratiefStapel,
        @Element(name = BEHANDELD_ALS_NEDERLANDER_INDICATIE_STAPEL_LABEL, required = false) final BrpStapel<BrpBehandeldAlsNederlanderIndicatieInhoud> behandeldAlsNederlanderIndicatieStapel,
        @Element(name = SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT_STAPEL_LABEL, required = false) final BrpStapel<BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud> signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel,
        @Element(name = BIJHOUDING_STAPEL_LABEL, required = false) final BrpStapel<BrpBijhoudingInhoud> bijhoudingStapel,
        @Element(name = DERDE_HEEFT_GEZAG_INDICATIE_STAPEL_LABEL, required = false) final BrpStapel<BrpDerdeHeeftGezagIndicatieInhoud> derdeHeeftGezagIndicatieStapel,
        @Element(name = DEELNAME_EU_VERKIEZINGEN_STAPEL_LABEL, required = false) final BrpStapel<BrpDeelnameEuVerkiezingenInhoud> deelnameEuVerkiezingenStapel,
        @Element(name = GEBOORTE_STAPEL_LABEL, required = false) final BrpStapel<BrpGeboorteInhoud> geboorteStapel,
        @Element(name = GESLACHTSAANDUIDING_STAPEL_LABEL, required = false) final BrpStapel<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingStapel,
        @ElementList(name = GESLACHTSNAAMCOMPONENT_STAPELS_LABEL, entry = "geslachtsnaamcomponentStapel", type = BrpStapel.class, required = false) final List<BrpStapel<BrpGeslachtsnaamcomponentInhoud>> geslachtsnaamcomponentStapels,
        @Element(name = IDENTIFICATIENUMMER_STAPEL_LABEL, required = false) final BrpStapel<BrpIdentificatienummersInhoud> identificatienummerStapel,
        @Element(name = MIGRATIE_STAPEL_LABEL, required = false) final BrpStapel<BrpMigratieInhoud> migratieStapel,
        @Element(name = INSCHRIJVING_STAPEL_LABEL, required = false) final BrpStapel<BrpInschrijvingInhoud> inschrijvingStapel,
        @ElementList(name = NATIONALITEIT_STAPELS_LABEL, entry = "nationaliteitStapel", type = BrpStapel.class, required = false) final List<BrpStapel<BrpNationaliteitInhoud>> nationaliteitStapels,
        @Element(name = "nummerverwijzingStapel", required = false) final BrpStapel<BrpNummerverwijzingInhoud> nummerverwijzingStapel,
        @Element(name = ONDER_CURATELE_INDICATIE_STAPEL_LABEL, required = false) final BrpStapel<BrpOnderCurateleIndicatieInhoud> onderCurateleIndicatieStapel,
        @Element(name = OVERLIJDEN_STAPEL_LABEL, required = false) final BrpStapel<BrpOverlijdenInhoud> overlijdenStapel,
        @Element(name = PERSOONSKAART_STAPEL_LABEL, required = false) final BrpStapel<BrpPersoonskaartInhoud> persoonskaartStapel,
        @ElementList(name = REISDOCUMENT_STAPELS_LABEL, entry = "reisdocumentStapel", type = BrpStapel.class, required = false) final List<BrpStapel<BrpReisdocumentInhoud>> reisdocumentStapels,
        @ElementList(name = RELATIES_LABEL, entry = "relatie", type = BrpRelatie.class, required = false) final List<BrpRelatie> relaties,
        @Element(name = SAMENGESTELDE_NAAM_STAPEL_LABEL, required = false) final BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel,
        @Element(name = STAATLOOS_INDICATIE_STAPEL_LABEL, required = false) final BrpStapel<BrpStaatloosIndicatieInhoud> staatloosIndicatieStapel,
        @Element(name = UITSLUITING_KIESRECHT_STAPEL_LABEL, required = false) final BrpStapel<BrpUitsluitingKiesrechtInhoud> uitsluitingKiesrechtStapel,
        @Element(name = VASTGESTELD_NIET_NEDERLANDER_INDICATIE_STAPEL_LABEL, required = false) final BrpStapel<BrpVastgesteldNietNederlanderIndicatieInhoud> vastgesteldNietNederlanderIndicatieStapel,
        @Element(name = VERBLIJFSRECHT_STAPEL_LABEL, required = false) final BrpStapel<BrpVerblijfsrechtInhoud> verblijfsrechtStapel,
        @Element(name = VERSTREKKINGSBEPERKING_INDICATIE_STAPEL_LABEL, required = false) final BrpStapel<BrpVerstrekkingsbeperkingIndicatieInhoud> verstrekkingsbeperkingIndicatieStapel,
        @ElementList(name = VOORNAAM_STAPELS_LABEL, entry = "voornaamStapel", type = BrpStapel.class, required = false) final List<BrpStapel<BrpVoornaamInhoud>> voornaamStapels,
        @Element(name = "bijzondereVerblijfsrechtelijkePositieIndicatieStapel", required = false) final BrpStapel<BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud> bijzondereVerblijfsrechtelijkePositieIndicatieStapel,
        @ElementList(name = VERIFICATIE_STAPELS_LABEL, entry = "verificatieStapel", type = BrpStapel.class, required = false) final List<BrpStapel<BrpVerificatieInhoud>> verificatieStapels,
        @Element(name = "istOuder1Stapel", required = false) final BrpStapel<BrpIstRelatieGroepInhoud> istOuder1Stapel,
        @Element(name = "istOuder2Stapel", required = false) final BrpStapel<BrpIstRelatieGroepInhoud> istOuder2Stapel,
        @ElementList(name = "istHuwelijkOfGpStapels", entry = "istHuwelijkOfGpStapel", type = BrpStapel.class, required = false) final List<BrpStapel<BrpIstHuwelijkOfGpGroepInhoud>> istHuwelijkOfGpStapels,
        @ElementList(name = "istKindStapels", entry = "istKindStapel", type = BrpStapel.class, required = false) final List<BrpStapel<BrpIstRelatieGroepInhoud>> istKindStapels,
        @Element(name = "istGezagsverhoudingsStapel", required = false) final BrpStapel<BrpIstGezagsVerhoudingGroepInhoud> istGezagsverhoudingsStapel)
    {
        super();
        this.persoonId = persoonId;
        this.naamgebruikStapel = naamgebruikStapel;
        this.adresStapel = adresStapel;
        this.persoonAfgeleidAdministratiefStapel = persoonAfgeleidAdministratiefStapel;
        this.behandeldAlsNederlanderIndicatieStapel = behandeldAlsNederlanderIndicatieStapel;
        this.signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel = signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel;
        this.bijhoudingStapel = bijhoudingStapel;
        this.derdeHeeftGezagIndicatieStapel = derdeHeeftGezagIndicatieStapel;
        this.deelnameEuVerkiezingenStapel = deelnameEuVerkiezingenStapel;
        this.geboorteStapel = geboorteStapel;
        this.geslachtsaanduidingStapel = geslachtsaanduidingStapel;
        this.geslachtsnaamcomponentStapels = geslachtsnaamcomponentStapels;
        this.identificatienummerStapel = identificatienummerStapel;
        this.migratieStapel = migratieStapel;
        this.inschrijvingStapel = inschrijvingStapel;
        this.nationaliteitStapels = nationaliteitStapels;
        this.nummerverwijzingStapel = nummerverwijzingStapel;
        this.onderCurateleIndicatieStapel = onderCurateleIndicatieStapel;
        this.overlijdenStapel = overlijdenStapel;
        this.persoonskaartStapel = persoonskaartStapel;
        this.reisdocumentStapels = reisdocumentStapels;
        this.relaties = relaties;
        this.samengesteldeNaamStapel = samengesteldeNaamStapel;
        this.staatloosIndicatieStapel = staatloosIndicatieStapel;
        this.uitsluitingKiesrechtStapel = uitsluitingKiesrechtStapel;
        this.vastgesteldNietNederlanderIndicatieStapel = vastgesteldNietNederlanderIndicatieStapel;
        this.verblijfsrechtStapel = verblijfsrechtStapel;
        this.verstrekkingsbeperkingIndicatieStapel = verstrekkingsbeperkingIndicatieStapel;
        this.voornaamStapels = voornaamStapels;
        this.bijzondereVerblijfsrechtelijkePositieIndicatieStapel = bijzondereVerblijfsrechtelijkePositieIndicatieStapel;
        this.verificatieStapels = verificatieStapels;

        this.istOuder1Stapel = istOuder1Stapel;
        this.istOuder2Stapel = istOuder2Stapel;
        this.istHuwelijkOfGpStapels = istHuwelijkOfGpStapels;
        this.istKindStapels = istKindStapels;
        this.istGezagsverhoudingsStapel = istGezagsverhoudingsStapel;

    }

    /**
     * Valideer de BRP persoonslijst.
     */
    @SuppressWarnings("checkstyle:executablestatementcount")
    public void valideer() {
        /* Executable Statement Size. Er moeten nu eenmaal veel verschillende stapels worden gevalideerd. */
        // inhoud validatie (in meeste gevallen no-op)
        BrpPersoonslijstValidator.valideerInhoud(naamgebruikStapel);
        BrpPersoonslijstValidator.valideerInhoud(adresStapel);
        BrpPersoonslijstValidator.valideerInhoud(persoonAfgeleidAdministratiefStapel);
        BrpPersoonslijstValidator.valideerInhoud(behandeldAlsNederlanderIndicatieStapel);
        BrpPersoonslijstValidator.valideerInhoud(signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel);
        BrpPersoonslijstValidator.valideerInhoud(bijhoudingStapel);
        BrpPersoonslijstValidator.valideerInhoud(derdeHeeftGezagIndicatieStapel);
        BrpPersoonslijstValidator.valideerInhoud(deelnameEuVerkiezingenStapel);
        BrpPersoonslijstValidator.valideerInhoud(geboorteStapel);
        BrpPersoonslijstValidator.valideerInhoud(geslachtsaanduidingStapel);
        BrpPersoonslijstValidator.valideerInhoud(geslachtsnaamcomponentStapels);
        BrpPersoonslijstValidator.valideerInhoud(identificatienummerStapel);
        BrpPersoonslijstValidator.valideerInhoud(migratieStapel);
        BrpPersoonslijstValidator.valideerInhoud(inschrijvingStapel);
        BrpPersoonslijstValidator.valideerInhoud(nationaliteitStapels);
        BrpPersoonslijstValidator.valideerInhoud(nummerverwijzingStapel);
        BrpPersoonslijstValidator.valideerInhoud(onderCurateleIndicatieStapel);
        BrpPersoonslijstValidator.valideerInhoud(overlijdenStapel);
        BrpPersoonslijstValidator.valideerInhoud(persoonskaartStapel);
        BrpPersoonslijstValidator.valideerInhoud(reisdocumentStapels);
        BrpPersoonslijstValidator.valideerInhoud(samengesteldeNaamStapel);
        BrpPersoonslijstValidator.valideerInhoud(staatloosIndicatieStapel);
        BrpPersoonslijstValidator.valideerInhoud(uitsluitingKiesrechtStapel);
        BrpPersoonslijstValidator.valideerInhoud(vastgesteldNietNederlanderIndicatieStapel);
        BrpPersoonslijstValidator.valideerInhoud(verblijfsrechtStapel);
        BrpPersoonslijstValidator.valideerInhoud(verificatieStapels);
        BrpPersoonslijstValidator.valideerInhoud(verstrekkingsbeperkingIndicatieStapel);
        BrpPersoonslijstValidator.valideerInhoud(voornaamStapels);

        // valideer relaties
        for (final BrpRelatie relatie : relaties) {
            relatie.valideer();
        }

        // groepsoverstijgende validaties
        BrpPersoonslijstValidator.valideerGeprivilegieerde(bijzondereVerblijfsrechtelijkePositieIndicatieStapel, nationaliteitStapels);
        BrpPersoonslijstValidator.valideerActueleGeslachtsnaam(samengesteldeNaamStapel);
        BrpPersoonslijstValidator.valideerBehandeldAlsNederlanderVastgesteldNietNederlander(
            behandeldAlsNederlanderIndicatieStapel,
            vastgesteldNietNederlanderIndicatieStapel);

        // Valideer IST vulling
        BrpPersoonslijstValidator.valideerInhoud(istOuder1Stapel);
        BrpPersoonslijstValidator.valideerInhoud(istOuder2Stapel);
        BrpPersoonslijstValidator.valideerIstInhoud(istHuwelijkOfGpStapels);
        BrpPersoonslijstValidator.valideerIstInhoud(istKindStapels);
        BrpPersoonslijstValidator.valideerInhoud(istGezagsverhoudingsStapel);
    }

    /**
     * Geef de waarde van persoon id.
     *
     * @return persoon id
     */
    @Element(name = "persoonId", required = false)
    public Integer getPersoonId() {
        return persoonId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.conversie.model.Persoonslijst#getActueelAdministratienummer()
     */
    @Override
    public Long getActueelAdministratienummer() {
        if (getIdentificatienummerStapel() != null) {
            final BrpGroep<BrpIdentificatienummersInhoud> brpGroep = getIdentificatienummerStapel().getActueel();
            if (brpGroep != null) {
                return BrpLong.unwrap(brpGroep.getInhoud().getAdministratienummer());
            }
        }
        return null;
    }

    /**
     * Geef de waarde van naamgebruik stapel.
     *
     * @return naamgebruik stapel
     */
    @Element(name = NAAMGEBRUIK_STAPEL_LABEL, required = false)
    public BrpStapel<BrpNaamgebruikInhoud> getNaamgebruikStapel() {
        return naamgebruikStapel;
    }

    /**
     * Geef de waarde van adres stapel.
     *
     * @return adres stapel
     */
    @Element(name = ADRES_STAPEL_LABEL, required = false)
    public BrpStapel<BrpAdresInhoud> getAdresStapel() {
        return adresStapel;
    }

    /**
     * Geef de waarde van persoon afgeleid administratief stapel.
     *
     * @return persoon afgeleid administratief stapel
     */
    @Element(name = "persoonAfgeleidAdministratiefStapel", required = false)
    public BrpStapel<BrpPersoonAfgeleidAdministratiefInhoud> getPersoonAfgeleidAdministratiefStapel() {
        return persoonAfgeleidAdministratiefStapel;
    }

    /**
     * Geef de waarde van behandeld als nederlander indicatie stapel.
     *
     * @return behandeld als nederlander indicatie stapel
     */
    @Element(name = BEHANDELD_ALS_NEDERLANDER_INDICATIE_STAPEL_LABEL, required = false)
    public BrpStapel<BrpBehandeldAlsNederlanderIndicatieInhoud> getBehandeldAlsNederlanderIndicatieStapel() {
        return behandeldAlsNederlanderIndicatieStapel;
    }

    /**
     * Geef de waarde van signalering met betrekking tot verstrekken reisdocument stapel.
     *
     * @return signalering met betrekking tot verstrekken reisdocument stapel
     */
    @Element(name = SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT_STAPEL_LABEL, required = false)
    public BrpStapel<BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud> getSignaleringMetBetrekkingTotVerstrekkenReisdocumentStapel() {
        return signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel;
    }

    /**
     * Geef de waarde van bijhouding stapel.
     *
     * @return bijhouding stapel
     */
    @Element(name = BIJHOUDING_STAPEL_LABEL, required = false)
    public BrpStapel<BrpBijhoudingInhoud> getBijhoudingStapel() {
        return bijhoudingStapel;
    }

    /**
     * Geef de waarde van derde heeft gezag indicatie stapel.
     *
     * @return derde heeft gezag indicatie stapel
     */
    @Element(name = DERDE_HEEFT_GEZAG_INDICATIE_STAPEL_LABEL, required = false)
    public BrpStapel<BrpDerdeHeeftGezagIndicatieInhoud> getDerdeHeeftGezagIndicatieStapel() {
        return derdeHeeftGezagIndicatieStapel;
    }

    /**
     * Geef de waarde van deelname eu verkiezingen stapel.
     *
     * @return deelname eu verkiezingen stapel
     */
    @Element(name = DEELNAME_EU_VERKIEZINGEN_STAPEL_LABEL, required = false)
    public BrpStapel<BrpDeelnameEuVerkiezingenInhoud> getDeelnameEuVerkiezingenStapel() {
        return deelnameEuVerkiezingenStapel;
    }

    /**
     * Geef de waarde van geboorte stapel.
     *
     * @return geboorte stapel
     */
    @Element(name = GEBOORTE_STAPEL_LABEL, required = false)
    public BrpStapel<BrpGeboorteInhoud> getGeboorteStapel() {
        return geboorteStapel;
    }

    /**
     * Geef de waarde van geslachtsaanduiding stapel.
     *
     * @return geslachtsaanduiding stapel
     */
    @Element(name = GESLACHTSAANDUIDING_STAPEL_LABEL, required = false)
    public BrpStapel<BrpGeslachtsaanduidingInhoud> getGeslachtsaanduidingStapel() {
        return geslachtsaanduidingStapel;
    }

    /**
     * Geef de waarde van geslachtsnaamcomponent stapels.
     *
     * @return geslachtsnaamcomponent stapels
     */
    @ElementList(name = GESLACHTSNAAMCOMPONENT_STAPELS_LABEL, entry = "geslachtsnaamcomponentStapel", type = BrpStapel.class, required = false)
    public List<BrpStapel<BrpGeslachtsnaamcomponentInhoud>> getGeslachtsnaamcomponentStapels() {
        return geslachtsnaamcomponentStapels;
    }

    /**
     * Geef de waarde van identificatienummer stapel.
     *
     * @return identificatienummer stapel
     */
    @Element(name = IDENTIFICATIENUMMER_STAPEL_LABEL, required = false)
    public BrpStapel<BrpIdentificatienummersInhoud> getIdentificatienummerStapel() {
        return identificatienummerStapel;
    }

    /**
     * Geef de waarde van migratie stapel.
     *
     * @return migratie stapel
     */
    @Element(name = MIGRATIE_STAPEL_LABEL, required = false)
    public BrpStapel<BrpMigratieInhoud> getMigratieStapel() {
        return migratieStapel;
    }

    /**
     * Geef de waarde van inschrijving stapel.
     *
     * @return inschrijving stapel
     */
    @Element(name = INSCHRIJVING_STAPEL_LABEL, required = false)
    public BrpStapel<BrpInschrijvingInhoud> getInschrijvingStapel() {
        return inschrijvingStapel;
    }

    /**
     * Geef de waarde van nationaliteit stapels.
     *
     * @return nationaliteit stapels
     */
    @ElementList(name = NATIONALITEIT_STAPELS_LABEL, entry = "nationaliteitStapel", type = BrpStapel.class, required = false)
    public List<BrpStapel<BrpNationaliteitInhoud>> getNationaliteitStapels() {
        return nationaliteitStapels;
    }

    /**
     * Geef de waarde van nummerverwijzing stapel.
     *
     * @return nummerverwijzing stapel
     */
    @Element(name = "nummerverwijzingStapel", required = false)
    public BrpStapel<BrpNummerverwijzingInhoud> getNummerverwijzingStapel() {
        return nummerverwijzingStapel;
    }

    /**
     * Geef de waarde van onder curatele indicatie stapel.
     *
     * @return onder curatele indicatie stapel
     */
    @Element(name = ONDER_CURATELE_INDICATIE_STAPEL_LABEL, required = false)
    public BrpStapel<BrpOnderCurateleIndicatieInhoud> getOnderCurateleIndicatieStapel() {
        return onderCurateleIndicatieStapel;
    }

    /**
     * Geef de waarde van overlijden stapel.
     *
     * @return overlijden stapel
     */
    @Element(name = OVERLIJDEN_STAPEL_LABEL, required = false)
    public BrpStapel<BrpOverlijdenInhoud> getOverlijdenStapel() {
        return overlijdenStapel;
    }

    /**
     * Geef de waarde van persoonskaart stapel.
     *
     * @return persoonskaart stapel
     */
    @Element(name = PERSOONSKAART_STAPEL_LABEL, required = false)
    public BrpStapel<BrpPersoonskaartInhoud> getPersoonskaartStapel() {
        return persoonskaartStapel;
    }

    /**
     * Geef de waarde van reisdocument stapels.
     *
     * @return reisdocument stapels
     */
    @ElementList(name = REISDOCUMENT_STAPELS_LABEL, entry = "reisdocumentStapel", type = BrpStapel.class, required = false)
    public List<BrpStapel<BrpReisdocumentInhoud>> getReisdocumentStapels() {
        return reisdocumentStapels;
    }

    /**
     * Geef de waarde van relaties.
     *
     * @return relaties
     */
    @ElementList(name = RELATIES_LABEL, entry = "relatie", type = BrpRelatie.class, required = false)
    public List<BrpRelatie> getRelaties() {
        return relaties;
    }

    /**
     * Doorzoekt de lijst met relatie stapels en geeft alleen die stapels terug die van het gewenste soort zijn. Als de
     * gewenste soort niet wordt gevonden wordt een lege lijst geretourneerd.
     *
     * @param soortRelatieCode
     *            de gewenste soort relatie, mag niet null zijn
     * @return een lijst met relatie stapels van het gewenste soort
     */
    public List<BrpRelatie> getRelaties(final BrpSoortRelatieCode soortRelatieCode) {
        if (soortRelatieCode == null) {
            throw new NullPointerException("soortRelatieCode mag niet null zijn");
        }
        final List<BrpRelatie> result = new ArrayList<>();
        for (final BrpRelatie relatie : getRelaties()) {
            if (relatie.getSoortRelatieCode().equals(soortRelatieCode)) {
                result.add(relatie);
            }
        }
        return result;
    }

    /**
     * Geef de waarde van samengestelde naam stapel.
     *
     * @return samengestelde naam stapel
     */
    @Element(name = SAMENGESTELDE_NAAM_STAPEL_LABEL, required = false)
    public BrpStapel<BrpSamengesteldeNaamInhoud> getSamengesteldeNaamStapel() {

        return samengesteldeNaamStapel;
    }

    /**
     * Geef de waarde van staatloos indicatie stapel.
     *
     * @return staatloos indicatie stapel
     */
    @Element(name = STAATLOOS_INDICATIE_STAPEL_LABEL, required = false)
    public BrpStapel<BrpStaatloosIndicatieInhoud> getStaatloosIndicatieStapel() {
        return staatloosIndicatieStapel;
    }

    /**
     * Geef de waarde van uitsluiting kiesrecht stapel.
     *
     * @return uitsluiting kiesrecht stapel
     */
    @Element(name = UITSLUITING_KIESRECHT_STAPEL_LABEL, required = false)
    public BrpStapel<BrpUitsluitingKiesrechtInhoud> getUitsluitingKiesrechtStapel() {
        return uitsluitingKiesrechtStapel;
    }

    /**
     * Geef de waarde van vastgesteld niet nederlander indicatie stapel.
     *
     * @return vastgesteld niet nederlander indicatie stapel
     */
    @Element(name = VASTGESTELD_NIET_NEDERLANDER_INDICATIE_STAPEL_LABEL, required = false)
    public BrpStapel<BrpVastgesteldNietNederlanderIndicatieInhoud> getVastgesteldNietNederlanderIndicatieStapel() {
        return vastgesteldNietNederlanderIndicatieStapel;
    }

    /**
     * Geef de waarde van verblijfsrecht stapel.
     *
     * @return verblijfsrecht stapel
     */
    @Element(name = VERBLIJFSRECHT_STAPEL_LABEL, required = false)
    public BrpStapel<BrpVerblijfsrechtInhoud> getVerblijfsrechtStapel() {
        return verblijfsrechtStapel;
    }

    /**
     * Geef de waarde van verstrekkingsbeperking indicatie stapel.
     *
     * @return verstrekkingsbeperking indicatie stapel
     */
    @Element(name = VERSTREKKINGSBEPERKING_INDICATIE_STAPEL_LABEL, required = false)
    public BrpStapel<BrpVerstrekkingsbeperkingIndicatieInhoud> getVerstrekkingsbeperkingIndicatieStapel() {
        return verstrekkingsbeperkingIndicatieStapel;
    }

    /**
     * Geef de waarde van voornaam stapels.
     *
     * @return voornaam stapels
     */
    @ElementList(name = VOORNAAM_STAPELS_LABEL, entry = "voornaamStapel", type = BrpStapel.class, required = false)
    public List<BrpStapel<BrpVoornaamInhoud>> getVoornaamStapels() {
        return voornaamStapels;
    }

    /**
     * Geef de waarde van bijzondere verblijfsrechtelijke positie indicatie stapel.
     *
     * @return bijzondere verblijfsrechtelijke positie indicatie stapel
     */
    @Element(name = "bijzondereVerblijfsrechtelijkePositieIndicatieStapel", required = false)
    public BrpStapel<BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud> getBijzondereVerblijfsrechtelijkePositieIndicatieStapel() {
        return bijzondereVerblijfsrechtelijkePositieIndicatieStapel;
    }

    /**
     * Geef de waarde van verificatie stapels.
     *
     * @return verificatie stapels
     */
    @ElementList(name = VERIFICATIE_STAPELS_LABEL, entry = "verificatieStapel", type = BrpStapel.class, required = false)
    public List<BrpStapel<BrpVerificatieInhoud>> getVerificatieStapels() {
        return verificatieStapels;
    }

    /**
     * Geef de waarde van ist ouder1 stapel.
     *
     * @return ist ouder1 stapel
     */
    @Element(name = "istOuder1Stapel", required = false)
    public BrpStapel<BrpIstRelatieGroepInhoud> getIstOuder1Stapel() {
        return istOuder1Stapel;
    }

    /**
     * Geef de waarde van ist ouder2 stapel.
     *
     * @return ist ouder2 stapel
     */
    @Element(name = "istOuder2Stapel", required = false)
    public BrpStapel<BrpIstRelatieGroepInhoud> getIstOuder2Stapel() {
        return istOuder2Stapel;
    }

    /**
     * Geef de waarde van ist huwelijk of gp stapels.
     *
     * @return ist huwelijk of gp stapels
     */
    @ElementList(name = "istHuwelijkOfGpStapels", entry = "istHuwelijkOfGpStapel", type = BrpStapel.class, required = false)
    public List<BrpStapel<BrpIstHuwelijkOfGpGroepInhoud>> getIstHuwelijkOfGpStapels() {
        return istHuwelijkOfGpStapels;
    }

    /**
     * Geef de waarde van ist kind stapels.
     *
     * @return ist kind stapels
     */
    @ElementList(name = "istKindStapels", entry = "istKindStapel", type = BrpStapel.class, required = false)
    public List<BrpStapel<BrpIstRelatieGroepInhoud>> getIstKindStapels() {
        return istKindStapels;
    }

    /**
     * Geef de waarde van ist gezagsverhoudings stapel.
     *
     * @return ist gezagsverhoudings stapel
     */
    @Element(name = "istGezagsverhoudingsStapel", required = false)
    public BrpStapel<BrpIstGezagsVerhoudingGroepInhoud> getIstGezagsverhoudingsStapel() {
        return istGezagsverhoudingsStapel;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpPersoonslijst)) {
            return false;
        }
        final BrpPersoonslijst castOther = (BrpPersoonslijst) other;
        return new EqualsBuilder().append(naamgebruikStapel, castOther.naamgebruikStapel)
                                  .append(adresStapel, castOther.adresStapel)
                                  .append(persoonAfgeleidAdministratiefStapel, castOther.persoonAfgeleidAdministratiefStapel)
                                  .append(behandeldAlsNederlanderIndicatieStapel, castOther.behandeldAlsNederlanderIndicatieStapel)
                                  .append(
                                      signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel,
                                      castOther.signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel)
                                  .append(bijhoudingStapel, castOther.bijhoudingStapel)
                                  .append(derdeHeeftGezagIndicatieStapel, castOther.derdeHeeftGezagIndicatieStapel)
                                  .append(deelnameEuVerkiezingenStapel, castOther.deelnameEuVerkiezingenStapel)
                                  .append(geboorteStapel, castOther.geboorteStapel)
                                  .append(geslachtsaanduidingStapel, castOther.geslachtsaanduidingStapel)
                                  .append(geslachtsnaamcomponentStapels, castOther.geslachtsnaamcomponentStapels)
                                  .append(identificatienummerStapel, castOther.identificatienummerStapel)
                                  .append(migratieStapel, castOther.migratieStapel)
                                  .append(inschrijvingStapel, castOther.inschrijvingStapel)
                                  .append(nationaliteitStapels, castOther.nationaliteitStapels)
                                  .append(onderCurateleIndicatieStapel, castOther.onderCurateleIndicatieStapel)
                                  .append(overlijdenStapel, castOther.overlijdenStapel)
                                  .append(persoonskaartStapel, castOther.persoonskaartStapel)
                                  .append(reisdocumentStapels, castOther.reisdocumentStapels)
                                  .append(relaties, castOther.relaties)
                                  .append(samengesteldeNaamStapel, castOther.samengesteldeNaamStapel)
                                  .append(staatloosIndicatieStapel, castOther.staatloosIndicatieStapel)
                                  .append(uitsluitingKiesrechtStapel, castOther.uitsluitingKiesrechtStapel)
                                  .append(vastgesteldNietNederlanderIndicatieStapel, castOther.vastgesteldNietNederlanderIndicatieStapel)
                                  .append(verificatieStapels, castOther.verificatieStapels)
                                  .append(verblijfsrechtStapel, castOther.verblijfsrechtStapel)
                                  .append(verstrekkingsbeperkingIndicatieStapel, castOther.verstrekkingsbeperkingIndicatieStapel)
                                  .append(voornaamStapels, castOther.voornaamStapels)
                                  .append(istOuder1Stapel, castOther.istOuder1Stapel)
                                  .append(istOuder2Stapel, castOther.istOuder2Stapel)
                                  .append(istHuwelijkOfGpStapels, castOther.istHuwelijkOfGpStapels)
                                  .append(istKindStapels, castOther.istKindStapels)
                                  .append(istGezagsverhoudingsStapel, castOther.istGezagsverhoudingsStapel)
                                  .isEquals();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(naamgebruikStapel)
                                    .append(adresStapel)
                                    .append(persoonAfgeleidAdministratiefStapel)
                                    .append(behandeldAlsNederlanderIndicatieStapel)
                                    .append(signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel)
                                    .append(bijhoudingStapel)
                                    .append(derdeHeeftGezagIndicatieStapel)
                                    .append(deelnameEuVerkiezingenStapel)
                                    .append(geboorteStapel)
                                    .append(geslachtsaanduidingStapel)
                                    .append(geslachtsnaamcomponentStapels)
                                    .append(identificatienummerStapel)
                                    .append(migratieStapel)
                                    .append(inschrijvingStapel)
                                    .append(nationaliteitStapels)
                                    .append(onderCurateleIndicatieStapel)
                                    .append(overlijdenStapel)
                                    .append(persoonskaartStapel)
                                    .append(reisdocumentStapels)
                                    .append(relaties)
                                    .append(samengesteldeNaamStapel)
                                    .append(staatloosIndicatieStapel)
                                    .append(uitsluitingKiesrechtStapel)
                                    .append(vastgesteldNietNederlanderIndicatieStapel)
                                    .append(verblijfsrechtStapel)
                                    .append(verificatieStapels)
                                    .append(verstrekkingsbeperkingIndicatieStapel)
                                    .append(voornaamStapels)
                                    .append(istOuder1Stapel)
                                    .append(istOuder2Stapel)
                                    .append(istHuwelijkOfGpStapels)
                                    .append(istKindStapels)
                                    .append(istGezagsverhoudingsStapel)
                                    .toHashCode();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append(NAAMGEBRUIK_STAPEL_LABEL, naamgebruikStapel)
                                                                          .append(ADRES_STAPEL_LABEL, adresStapel)
                                                                          .append("afgeleidAdministratiefStapel", persoonAfgeleidAdministratiefStapel)
                                                                          .append(
                                                                              BEHANDELD_ALS_NEDERLANDER_INDICATIE_STAPEL_LABEL,
                                                                              behandeldAlsNederlanderIndicatieStapel)
                                                                          .append(
                                                                              SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT_STAPEL_LABEL,
                                                                              signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel)
                                                                          .append(BIJHOUDING_STAPEL_LABEL, bijhoudingStapel)
                                                                          .append(DERDE_HEEFT_GEZAG_INDICATIE_STAPEL_LABEL, derdeHeeftGezagIndicatieStapel)
                                                                          .append(DEELNAME_EU_VERKIEZINGEN_STAPEL_LABEL, deelnameEuVerkiezingenStapel)
                                                                          .append(GEBOORTE_STAPEL_LABEL, geboorteStapel)
                                                                          .append(GESLACHTSAANDUIDING_STAPEL_LABEL, geslachtsaanduidingStapel)
                                                                          .append(GESLACHTSNAAMCOMPONENT_STAPELS_LABEL, geslachtsnaamcomponentStapels)
                                                                          .append(IDENTIFICATIENUMMER_STAPEL_LABEL, identificatienummerStapel)
                                                                          .append(MIGRATIE_STAPEL_LABEL, migratieStapel)
                                                                          .append(INSCHRIJVING_STAPEL_LABEL, inschrijvingStapel)
                                                                          .append(NATIONALITEIT_STAPELS_LABEL, nationaliteitStapels)
                                                                          .append(ONDER_CURATELE_INDICATIE_STAPEL_LABEL, onderCurateleIndicatieStapel)
                                                                          .append(OVERLIJDEN_STAPEL_LABEL, overlijdenStapel)
                                                                          .append(PERSOONSKAART_STAPEL_LABEL, persoonskaartStapel)
                                                                          .append(REISDOCUMENT_STAPELS_LABEL, reisdocumentStapels)
                                                                          .append(RELATIES_LABEL, relaties)
                                                                          .append(SAMENGESTELDE_NAAM_STAPEL_LABEL, samengesteldeNaamStapel)
                                                                          .append(STAATLOOS_INDICATIE_STAPEL_LABEL, staatloosIndicatieStapel)
                                                                          .append(UITSLUITING_KIESRECHT_STAPEL_LABEL, uitsluitingKiesrechtStapel)
                                                                          .append(
                                                                              VASTGESTELD_NIET_NEDERLANDER_INDICATIE_STAPEL_LABEL,
                                                                              vastgesteldNietNederlanderIndicatieStapel)
                                                                          .append(VERBLIJFSRECHT_STAPEL_LABEL, verblijfsrechtStapel)
                                                                          .append(VERIFICATIE_STAPELS_LABEL, verificatieStapels)
                                                                          .append(
                                                                              VERSTREKKINGSBEPERKING_INDICATIE_STAPEL_LABEL,
                                                                              verstrekkingsbeperkingIndicatieStapel)
                                                                          .append(VOORNAAM_STAPELS_LABEL, voornaamStapels)
                                                                          .append("ouder1Stapel", istOuder1Stapel)
                                                                          .append("ouder2Stapel", istOuder2Stapel)
                                                                          .append("huwelijkOfGpStapels", istHuwelijkOfGpStapels)
                                                                          .append("kindStapels", istKindStapels)
                                                                          .append("gezagsverhoudingStapel", istGezagsverhoudingsStapel)
                                                                          .toString();
    }
}
