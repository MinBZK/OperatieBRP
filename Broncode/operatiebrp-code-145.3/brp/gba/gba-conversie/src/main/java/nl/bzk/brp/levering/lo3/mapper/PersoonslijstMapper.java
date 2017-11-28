/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.element.ObjectElement;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.levering.lo3.decorator.Persoon;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt een PersoonHisVolledig naar een BrpPersoonslijst.
 */
@Component
public final class PersoonslijstMapper {

    /**
     * Identiteit groep element.
     */
    public static final GroepElement IDENTITEIT_GROEP_ELEMENT = ElementHelper.getGroepElement(Element.PERSOON_IDENTITEIT.getId());

    /**
     * Kind (mijn betrokkenheid) object element.
     */
    public static final ObjectElement KIND_ELEMENT = ElementHelper.getObjectElement(Element.PERSOON_KIND.getId());
    /**
     * Kind (mijn betrokkenheid) identiteit groep element.
     */
    public static final GroepElement KIND_IDENTITEIT_GROEP_ELEMENT = ElementHelper.getGroepElement(Element.PERSOON_KIND_IDENTITEIT.getId());
    /**
     * Kind (mijn betrokkenheid) groep element.
     */
    public static final GroepElement KIND_GROEP_ELEMENT = ElementHelper.getGroepElement(Element.PERSOON_KIND_IDENTITEIT.getId());

    /**
     * Familierechtelijke betrekking object element.
     */
    public static final ObjectElement FAMILIERECHTELIJKE_BETREKKEING_ELEMENT = ElementHelper.getObjectElement(Element.FAMILIERECHTELIJKEBETREKKING.getId());
    /**
     * Familierechtelijke betrekking identiteit groep element.
     */
    public static final GroepElement FAMILIERECHTELIJKE_BETREKKEING_IDENTITEIT_GROEP_ELEMENT =
            ElementHelper.getGroepElement(Element.FAMILIERECHTELIJKEBETREKKING_IDENTITEIT.getId());

    /**
     * Gerelateerde ouder (betrokkenheid) object element.
     */
    public static final ObjectElement GERELATEERDE_OUDER_ELEMENT = ElementHelper.getObjectElement(Element.GERELATEERDEOUDER.getId());
    /**
     * Gerelateerde ouder (betrokkenheid) identiteit groep element.
     */
    public static final GroepElement GERELATEERDE_OUDER_IDENTITEIT_GROEP_ELEMENT = ElementHelper.getGroepElement(Element.GERELATEERDEOUDER_IDENTITEIT.getId());

    /**
     * Gerelateerde ouder (persoon) object element.
     */
    public static final ObjectElement GERELATEERDE_OUDER_PERSOON_ELEMENT = ElementHelper.getObjectElement(Element.GERELATEERDEOUDER_PERSOON.getId());
    /**
     * Gerelateerde ouder (persoon) identiteit groep element.
     */
    public static final GroepElement GERELATEERDE_OUDER_PERSOON_IDENTITEIT_GROEP_ELEMENT =
            ElementHelper.getGroepElement(Element.GERELATEERDEOUDER_PERSOON_IDENTITEIT.getId());

    /**
     * Ouder (mijn betrokkenheid) object element.
     */
    public static final ObjectElement OUDER_ELEMENT = ElementHelper.getObjectElement(Element.PERSOON_OUDER.getId());
    /**
     * Ouder (mijn betrokkenheid) identiteit groep element.
     */
    public static final GroepElement OUDER_IDENTITEIT_GROEP_ELEMENT = ElementHelper.getGroepElement(Element.PERSOON_OUDER_IDENTITEIT.getId());
    /**
     * Ouder (mijn betrokkenheid) groep element.
     */
    public static final GroepElement OUDER_GROEP_ELEMENT = ElementHelper.getGroepElement(Element.PERSOON_OUDER_IDENTITEIT.getId());

    /**
     * Gerelateerde kind (betrokkenheid) object element.
     */
    public static final ObjectElement GERELATEERDE_KIND_ELEMENT = ElementHelper.getObjectElement(Element.GERELATEERDEKIND.getId());
    /**
     * Gerelateerde kind (betrokkenheid) identiteit groep element.
     */
    public static final GroepElement GERELATEERDE_KIND_IDENTITEIT_GROEP_ELEMENT = ElementHelper.getGroepElement(Element.GERELATEERDEKIND_IDENTITEIT.getId());

    /**
     * Gerelateerde kind (persoon) object element.
     */
    public static final ObjectElement GERELATEERDE_KIND_PERSOON_ELEMENT = ElementHelper.getObjectElement(Element.GERELATEERDEKIND_PERSOON.getId());
    /**
     * Gerelateerde kind (persoon) identiteit groep element.
     */
    public static final GroepElement GERELATEERDE_KIND_PERSOON_IDENTITEIT_GROEP_ELEMENT =
            ElementHelper.getGroepElement(Element.GERELATEERDEKIND_PERSOON_IDENTITEIT.getId());

    /**
     * Partner (mijn betrokkenheid) object element.
     */
    public static final ObjectElement PARTNER_ELEMENT = ElementHelper.getObjectElement(Element.PERSOON_PARTNER.getId());
    /**
     * Partner (mijn betrokkenheid) identiteit groep element.
     */
    public static final GroepElement PARTNER_IDENTITEIT_GROEP_ELEMENT = ElementHelper.getGroepElement(Element.PERSOON_PARTNER_IDENTITEIT.getId());

    /**
     * Huwelijk object element.
     */
    public static final ObjectElement HUWELIJK_ELEMENT = ElementHelper.getObjectElement(Element.HUWELIJK.getId());

    /**
     * Gerelateerde huwelijks partner (betrokkenheid) object element.
     */
    public static final ObjectElement GERELATEERDE_HUWELIJKSPARTNER_ELEMENT = ElementHelper.getObjectElement(Element.GERELATEERDEHUWELIJKSPARTNER.getId());
    /**
     * Gerelateerde huwelijks partner (betrokkenheid) identiteit groep element.
     */
    public static final GroepElement GERELATEERDE_HUWELIJKSPARTNER_IDENTITEIT_GROEP_ELEMENT =
            ElementHelper.getGroepElement(Element.GERELATEERDEHUWELIJKSPARTNER_IDENTITEIT.getId());

    /**
     * Gerelateerde huwelijks partner (persoon) object element.
     */
    public static final ObjectElement GERELATEERDE_HUWELIJKSPARTNER_PERSOON_ELEMENT =
            ElementHelper.getObjectElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON.getId());
    /**
     * Gerelateerde huwelijks partner (persoon) identiteit groep element.
     */
    public static final GroepElement GERELATEERDE_HUWELIJKSPARTNER_PERSOON_IDENTITEIT_GROEP_ELEMENT =
            ElementHelper.getGroepElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTITEIT.getId());

    /**
     * Geregistreerd partnerschap object element.
     */
    public static final ObjectElement GEREGISTREERD_PARTNERSCHAP_ELEMENT = ElementHelper.getObjectElement(Element.GEREGISTREERDPARTNERSCHAP.getId());

    /**
     * Geregistreerde partner (betrokkenheid) object element.
     */
    public static final ObjectElement GERELATEERDE_GEREGISTREERDEPARTNER_ELEMENT =
            ElementHelper.getObjectElement(Element.GERELATEERDEGEREGISTREERDEPARTNER.getId());
    /**
     * Geregistreerde partner (betrokkenheid) identiteit groep element.
     */
    public static final GroepElement GERELATEERDE_GEREGISTREERDEPARTNER_IDENTITEIT_GROEP_ELEMENT =
            ElementHelper.getGroepElement(Element.GERELATEERDEGEREGISTREERDEPARTNER_IDENTITEIT.getId());

    /**
     * Geregistreerde partner (persoon) object element.
     */
    public static final ObjectElement GERELATEERDE_GEREGISTREERDEPARTNER_PERSOON_ELEMENT =
            ElementHelper.getObjectElement(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON.getId());
    /**
     * Geregistreerde partner (persoon) identiteit groep element.
     */
    public static final GroepElement GERELATEERDE_GEREGISTREERDEPARTNER_PERSOON_IDENTITEIT_GROEP_ELEMENT =
            ElementHelper.getGroepElement(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTITEIT.getId());

    private final AdressenMapper adressenMapper;
    private final PersoonBijhoudingMapper bijhoudingMapper;
    private final BuitenlandsPersoonsnummersMapper buitenlandsPersoonsnummersMapper;
    private final PersoonDeelnameEuVerkiezingenMapper deelnameEuVerkiezingenMapper;
    private final PersoonGeboorteMapper geboorteMapper;
    private final PersoonGeslachtsaanduidingMapper geslachtsaanduidingMapper;
    private final GeslachtsnaamcomponentenMapper geslachtsnaamcomponentenMapper;
    private final PersoonIdentificatienummersMapper identificatienummersMapper;
    private final PersoonInschrijvingMapper inschrijvingMapper;
    private final PersoonMigratieMapper migratieMapper;
    private final PersoonNaamgebruikMapper naamgebruikMapper;
    private final NationaliteitenMapper nationaliteitenMapper;
    private final PersoonNummerverwijzingMapper nummerverwijzingMapper;
    private final PersoonOverlijdenMapper overlijdenMapper;
    private final PersoonAfgeleidAdministratiefMapper persoonAfgeleidAdministratiefMapper;
    private final PersoonPersoonskaartMapper persoonskaartMapper;
    private final ReisdocumentenMapper reisdocumentenMapper;
    private final PersoonSamengesteldeNaamMapper samengesteldeNaamMapper;
    private final PersoonUitsluitingKiesrechtMapper uitsluitingKiesrechtMapper;
    private final PersoonVerblijfsrechtMapper verblijfsrechtMapper;
    private final VerificatiesMapper verificatiesMapper;
    private final VoornamenMapper voornamenMapper;
    private final BrpIstGezagsverhoudingMapper istGezagsverhoudingMapper;
    private final BrpIstHuwelijkOfGpMapper istHuwelijkOfGpMapper;
    private final BrpIstKindMapper istKindMapper;
    private final BrpIstOuder1Mapper istOuder1Mapper;
    private final BrpIstOuder2Mapper istOuder2Mapper;

    private final PersoonIndicatiesBehandeldAlsNederlanderMapper persoonIndicatiesBehandeldAlsNederlanderMapper;
    private final PersoonIndicatiesBijzondereVerblijfsrechtelijkePositieMapper persoonIndicatiesBijzondereVerblijfsrechtelijkePositieMapper;
    private final PersoonIndicatiesDerdeHeeftGezagMapper persoonIndicatiesDerdeHeeftGezagMapper;
    private final PersoonIndicatiesOnderCurateleMapper persoonIndicatiesOnderCurateleMapper;
    private final PersoonIndicatiesOnverwerktDocumentAanwezigMapper persoonIndicatiesOnverwerktDocumentAanwezigMapper;
    private final PersoonIndicatiesSignaleringMetBetrekkingTotVerstrekkenReisdocumentMapper
            persoonIndicatiesSignaleringMetBetrekkingTotVerstrekkenReisdocumentMapper;
    private final PersoonIndicatiesStaatloosMapper persoonIndicatiesStaatloosMapper;
    private final PersoonIndicatiesVastgesteldNietNederlanderMapper persoonIndicatiesVastgesteldNietNederlanderMapper;
    private final PersoonIndicatiesVerstrekkingsbeperkingMapper persoonIndicatiesVerstrekkingsbeperkingMapper;

    /**
     * Constructor.
     * @param adressenMapper adressen mapper
     * @param bijhoudingMapper bijhouding mapper
     * @param buitenlandsPersoonsnummersMapper buitenlands persoonsnummer mapper
     * @param deelnameEuVerkiezingenMapper deelname eu verkiezingen mapper
     * @param geboorteMapper geboorte mapper
     * @param geslachtsaanduidingMapper geslachtsaanduiding mapper
     * @param geslachtsnaamcomponentenMapper geslachtsnaamcomponenten mapper
     * @param identificatienummersMapper idenntificatienummer mapper
     * @param inschrijvingMapper inschrijving mapper
     * @param migratieMapper migratie mapper
     * @param naamgebruikMapper naamgebruik mapper
     * @param nationaliteitenMapper nationaliteiten mapper
     * @param nummerverwijzingMapper nummerverwijzing mapper
     * @param overlijdenMapper overlijden mapper
     * @param persoonAfgeleidAdministratiefMapper persoon afgeleid administratief mapper
     * @param persoonskaartMapper persoonskaart mapper
     * @param reisdocumentenMapper reisdocumenten mapper
     * @param samengesteldeNaamMapper samengestelde naam mapper
     * @param uitsluitingKiesrechtMapper uitsluiting kiesrecht mapper
     * @param verblijfsrechtMapper verblijfsrecht mapper
     * @param verificatiesMapper verificaties mapper
     * @param voornamenMapper voornamen mapper
     * @param istGezagsverhoudingMapper IST gezagsverhouding mapper
     * @param istHuwelijkOfGpMapper IST huwelijk mapper
     * @param istKindMapper IST kind mapper
     * @param istOuder1Mapper IST ouder mapper
     * @param istOuder2Mapper IST oduer mapper
     * @param persoonIndicatiesBehandeldAlsNederlanderMapper indicatie behandeld als Nederlander mapper
     * @param persoonIndicatiesBijzondereVerblijfsrechtelijkePositieMapper indicatie bijzondere berblijfsrechtelijk positie mapper
     * @param persoonIndicatiesDerdeHeeftGezagMapper indicatie derde heeft gezag mapper
     * @param persoonIndicatiesOnderCurateleMapper indicatie onder curatele mapper
     * @param persoonIndicatiesOnverwerktDocumentAanwezigMapper indicatie onverwerkt document aanwezig mapper
     * @param persoonIndicatiesSignaleringMetBetrekkingTotVerstrekkenReisdocumentMapper indicatie signalering met betrekking tot verstrekken reisdocument
     * mapper
     * @param persoonIndicatiesStaatloosMapper indicatie staatloos mapper
     * @param persoonIndicatiesVastgesteldNietNederlanderMapper indicatie vastgesteld niet Nederlander mapper
     * @param persoonIndicatiesVerstrekkingsbeperkingMapper indicatie verstrekkingsbeperking mapper
     */
    //
    /*
     * squid:S00107 Methods should not have too many parameters
     *
     * Terecht, geaccepteerd voor deze klasse.
     */
    @Inject
    public PersoonslijstMapper(final AdressenMapper adressenMapper,  final PersoonBijhoudingMapper bijhoudingMapper,
                               final BuitenlandsPersoonsnummersMapper buitenlandsPersoonsnummersMapper,
                                final PersoonDeelnameEuVerkiezingenMapper deelnameEuVerkiezingenMapper,
                                final PersoonGeboorteMapper geboorteMapper,
                                final PersoonGeslachtsaanduidingMapper geslachtsaanduidingMapper,
                                final GeslachtsnaamcomponentenMapper geslachtsnaamcomponentenMapper,
                                final PersoonIdentificatienummersMapper identificatienummersMapper,
                                final PersoonInschrijvingMapper inschrijvingMapper,  final PersoonMigratieMapper migratieMapper,
                                final PersoonNaamgebruikMapper naamgebruikMapper,  final NationaliteitenMapper nationaliteitenMapper,
                                final PersoonNummerverwijzingMapper nummerverwijzingMapper,  final PersoonOverlijdenMapper overlijdenMapper,
                                final PersoonAfgeleidAdministratiefMapper persoonAfgeleidAdministratiefMapper,
                                final PersoonPersoonskaartMapper persoonskaartMapper,  final ReisdocumentenMapper reisdocumentenMapper,
                                final PersoonSamengesteldeNaamMapper samengesteldeNaamMapper,
                                final PersoonUitsluitingKiesrechtMapper uitsluitingKiesrechtMapper,
                                final PersoonVerblijfsrechtMapper verblijfsrechtMapper,  final VerificatiesMapper verificatiesMapper,
                                final VoornamenMapper voornamenMapper,  final BrpIstGezagsverhoudingMapper istGezagsverhoudingMapper,
                                final BrpIstHuwelijkOfGpMapper istHuwelijkOfGpMapper,  final BrpIstKindMapper istKindMapper,
                                final BrpIstOuder1Mapper istOuder1Mapper,  final BrpIstOuder2Mapper istOuder2Mapper,
                                final PersoonIndicatiesBehandeldAlsNederlanderMapper persoonIndicatiesBehandeldAlsNederlanderMapper,
                                final PersoonIndicatiesBijzondereVerblijfsrechtelijkePositieMapper
                                       persoonIndicatiesBijzondereVerblijfsrechtelijkePositieMapper,
                                final PersoonIndicatiesDerdeHeeftGezagMapper persoonIndicatiesDerdeHeeftGezagMapper,
                                final PersoonIndicatiesOnderCurateleMapper persoonIndicatiesOnderCurateleMapper,
                                final PersoonIndicatiesOnverwerktDocumentAanwezigMapper persoonIndicatiesOnverwerktDocumentAanwezigMapper,
                                final PersoonIndicatiesSignaleringMetBetrekkingTotVerstrekkenReisdocumentMapper
                                       persoonIndicatiesSignaleringMetBetrekkingTotVerstrekkenReisdocumentMapper,
                                final PersoonIndicatiesStaatloosMapper persoonIndicatiesStaatloosMapper,
                                final PersoonIndicatiesVastgesteldNietNederlanderMapper persoonIndicatiesVastgesteldNietNederlanderMapper,
                                final PersoonIndicatiesVerstrekkingsbeperkingMapper persoonIndicatiesVerstrekkingsbeperkingMapper) {
        this.adressenMapper = adressenMapper;
        this.bijhoudingMapper = bijhoudingMapper;
        this.buitenlandsPersoonsnummersMapper = buitenlandsPersoonsnummersMapper;
        this.deelnameEuVerkiezingenMapper = deelnameEuVerkiezingenMapper;
        this.geboorteMapper = geboorteMapper;
        this.geslachtsaanduidingMapper = geslachtsaanduidingMapper;
        this.geslachtsnaamcomponentenMapper = geslachtsnaamcomponentenMapper;
        this.identificatienummersMapper = identificatienummersMapper;
        this.inschrijvingMapper = inschrijvingMapper;
        this.migratieMapper = migratieMapper;
        this.naamgebruikMapper = naamgebruikMapper;
        this.nationaliteitenMapper = nationaliteitenMapper;
        this.nummerverwijzingMapper = nummerverwijzingMapper;
        this.overlijdenMapper = overlijdenMapper;
        this.persoonAfgeleidAdministratiefMapper = persoonAfgeleidAdministratiefMapper;
        this.persoonskaartMapper = persoonskaartMapper;
        this.reisdocumentenMapper = reisdocumentenMapper;
        this.samengesteldeNaamMapper = samengesteldeNaamMapper;
        this.uitsluitingKiesrechtMapper = uitsluitingKiesrechtMapper;
        this.verblijfsrechtMapper = verblijfsrechtMapper;
        this.verificatiesMapper = verificatiesMapper;
        this.voornamenMapper = voornamenMapper;
        this.istGezagsverhoudingMapper = istGezagsverhoudingMapper;
        this.istHuwelijkOfGpMapper = istHuwelijkOfGpMapper;
        this.istKindMapper = istKindMapper;
        this.istOuder1Mapper = istOuder1Mapper;
        this.istOuder2Mapper = istOuder2Mapper;
        this.persoonIndicatiesBehandeldAlsNederlanderMapper = persoonIndicatiesBehandeldAlsNederlanderMapper;
        this.persoonIndicatiesBijzondereVerblijfsrechtelijkePositieMapper = persoonIndicatiesBijzondereVerblijfsrechtelijkePositieMapper;
        this.persoonIndicatiesDerdeHeeftGezagMapper = persoonIndicatiesDerdeHeeftGezagMapper;
        this.persoonIndicatiesOnderCurateleMapper = persoonIndicatiesOnderCurateleMapper;
        this.persoonIndicatiesOnverwerktDocumentAanwezigMapper = persoonIndicatiesOnverwerktDocumentAanwezigMapper;
        this.persoonIndicatiesSignaleringMetBetrekkingTotVerstrekkenReisdocumentMapper =
                persoonIndicatiesSignaleringMetBetrekkingTotVerstrekkenReisdocumentMapper;
        this.persoonIndicatiesStaatloosMapper = persoonIndicatiesStaatloosMapper;
        this.persoonIndicatiesVastgesteldNietNederlanderMapper = persoonIndicatiesVastgesteldNietNederlanderMapper;
        this.persoonIndicatiesVerstrekkingsbeperkingMapper = persoonIndicatiesVerstrekkingsbeperkingMapper;
    }

    /**
     * Map de objecten.
     * @param persoonslijst De persoonsgegevens, welke omgezet moet worden naar een BrpPersoonslijst.
     * @param istStapels De IST stapels
     * @return BrpPersoonslijst het gemapte object.
     */
    public BrpPersoonslijst map(final Persoonslijst persoonslijst, final Set<Stapel> istStapels) {
        // Builder
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();

        // Persoonsgegevens
        final MetaObject persoonObject = persoonslijst.getMetaObject();

        // OnderzoekMapper
        final Persoon persoon = new Persoon(persoonObject);
        final OnderzoekMapper onderzoekMapper = new OnderzoekMapperImpl(persoon);

        // Mapping
        builder.adresStapel(geefEersteStapel(adressenMapper.map(persoonObject, onderzoekMapper)));
        builder.buitenlandsPersoonsnummerStapels(buitenlandsPersoonsnummersMapper.map(persoonObject, onderzoekMapper));

        builder.behandeldAlsNederlanderIndicatieStapel(geefEersteStapel(persoonIndicatiesBehandeldAlsNederlanderMapper.map(persoonObject, onderzoekMapper)));
        builder.bijzondereVerblijfsrechtelijkePositieIndicatieStapel(
                geefEersteStapel(persoonIndicatiesBijzondereVerblijfsrechtelijkePositieMapper.map(persoonObject, onderzoekMapper)));
        builder.derdeHeeftGezagIndicatieStapel(geefEersteStapel(persoonIndicatiesDerdeHeeftGezagMapper.map(persoonObject, onderzoekMapper)));
        builder.onderCurateleIndicatieStapel(geefEersteStapel(persoonIndicatiesOnderCurateleMapper.map(persoonObject, onderzoekMapper)));
        builder.onverwerktDocumentAanwezigIndicatieStapel(
                geefEersteStapel(persoonIndicatiesOnverwerktDocumentAanwezigMapper.map(persoonObject, onderzoekMapper)));
        builder.signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel(
                geefEersteStapel(persoonIndicatiesSignaleringMetBetrekkingTotVerstrekkenReisdocumentMapper.map(persoonObject, onderzoekMapper)));
        builder.staatloosIndicatieStapel(geefEersteStapel(persoonIndicatiesStaatloosMapper.map(persoonObject, onderzoekMapper)));
        builder.vastgesteldNietNederlanderIndicatieStapel(
                geefEersteStapel(persoonIndicatiesVastgesteldNietNederlanderMapper.map(persoonObject, onderzoekMapper)));
        builder.verstrekkingsbeperkingIndicatieStapel(geefEersteStapel(persoonIndicatiesVerstrekkingsbeperkingMapper.map(persoonObject, onderzoekMapper)));

        builder.bijhoudingStapel(bijhoudingMapper.map(persoonObject, onderzoekMapper));
        builder.deelnameEuVerkiezingenStapel(deelnameEuVerkiezingenMapper.map(persoonObject, onderzoekMapper));
        builder.geboorteStapel(geboorteMapper.map(persoonObject, onderzoekMapper));
        builder.geslachtsaanduidingStapel(geslachtsaanduidingMapper.map(persoonObject, onderzoekMapper));
        builder.geslachtsnaamcomponentStapels(geslachtsnaamcomponentenMapper.map(persoonObject, onderzoekMapper));
        builder.identificatienummersStapel(identificatienummersMapper.map(persoonObject, onderzoekMapper));
        builder.inschrijvingStapel(inschrijvingMapper.map(persoonObject, onderzoekMapper));
        builder.migratieStapel(migratieMapper.map(persoonObject, onderzoekMapper));
        builder.naamgebruikStapel(naamgebruikMapper.map(persoonObject, onderzoekMapper));
        builder.nationaliteitStapels(nationaliteitenMapper.map(persoonObject, onderzoekMapper));
        builder.nummerverwijzingStapel(nummerverwijzingMapper.map(persoonObject, onderzoekMapper));
        builder.overlijdenStapel(overlijdenMapper.map(persoonObject, onderzoekMapper));
        builder.persoonAfgeleidAdministratiefStapel(persoonAfgeleidAdministratiefMapper.map(persoonObject, onderzoekMapper));
        builder.persoonskaartStapel(persoonskaartMapper.map(persoonObject, onderzoekMapper));
        builder.reisdocumentStapels(reisdocumentenMapper.map(persoonObject, onderzoekMapper));
        builder.samengesteldeNaamStapel(samengesteldeNaamMapper.map(persoonObject, onderzoekMapper));
        builder.uitsluitingKiesrechtStapel(uitsluitingKiesrechtMapper.map(persoonObject, onderzoekMapper));
        builder.verblijfsrechtStapel(verblijfsrechtMapper.map(persoonObject, onderzoekMapper));
        builder.verificatieStapels(verificatiesMapper.map(persoonObject, onderzoekMapper));
        builder.voornaamStapels(voornamenMapper.map(persoonObject, onderzoekMapper));

        // TODO: Relaties (BOP Stap 4.3)

        // IST
        if (!istStapels.isEmpty()) {
            builder.istGezagsverhoudingStapel(istGezagsverhoudingMapper.map(istStapels));
            builder.istHuwelijkOfGpStapels(istHuwelijkOfGpMapper.map(istStapels));
            builder.istKindStapels(istKindMapper.map(istStapels));
            builder.istOuder1Stapel(istOuder1Mapper.map(istStapels));
            builder.istOuder2Stapel(istOuder2Mapper.map(istStapels));
        }

        // Build
        return builder.build();
    }

    private <T extends BrpGroepInhoud> BrpStapel<T> geefEersteStapel(final List<BrpStapel<T>> list) {
        return list.stream().findFirst().orElse(null);
    }

}
