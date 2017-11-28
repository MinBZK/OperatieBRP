/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Voorkomen;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.Actiebron;
import nl.bzk.brp.domain.leveringmodel.Document;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.gba.dataaccess.VerConvRepository;
import nl.bzk.brp.levering.lo3.util.MetaModelUtil;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActieBron;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Basis mapper voor BRP naar Conversie model.
 * @param <G> Conversie model groep inhoud type
 */
public abstract class AbstractMapper<G extends BrpGroepInhoud> {

    /**
     * Actie partij code attribuut element.
     */
    public static final AttribuutElement ACTIE_PARTIJCODE_ELEMENT = ElementHelper.getAttribuutElement(Element.ACTIE_PARTIJCODE.getId());
    /**
     * Actiebron rechtsgrond omschrijving attribuut element.
     */
    public static final AttribuutElement ACTIEBRON_RECHTSGROND_OMSCHRIJVING_ELEMENT
            =
            ElementHelper.getAttribuutElement(Element.ACTIEBRON_RECHTSGRONDOMSCHRIJVING.getId());

    private static final AttribuutElement ACTIE_SOORT_ELEMENT = ElementHelper.getAttribuutElement(Element.ACTIE_SOORTNAAM.getId());
    private static final AttribuutElement ACTIE_TIJDSTIP_REGISTRATIE_ELEMENT =
            ElementHelper.getAttribuutElement(Element.ACTIE_TIJDSTIPREGISTRATIE.getId());
    private static final AttribuutElement ACTIE_DATUM_ONTLENING_ELEMENT = ElementHelper
            .getAttribuutElement(Element.ACTIE_DATUMONTLENING.getId());

    private static final AttribuutElement DOCUMENT_SOORT_ELEMENT = ElementHelper.getAttribuutElement(Element.DOCUMENT_SOORTNAAM.getId());
    private static final AttribuutElement DOCUMENT_AKTENUMMER_ELEMENT = ElementHelper.getAttribuutElement(Element.DOCUMENT_AKTENUMMER.getId());
    private static final AttribuutElement DOCUMENT_OMSCHRIJVING_ELEMENT = ElementHelper.getAttribuutElement(Element.DOCUMENT_OMSCHRIJVING.getId());
    private static final AttribuutElement DOCUMENT_PARTIJCODE_ELEMENT = ElementHelper.getAttribuutElement(Element.DOCUMENT_PARTIJCODE.getId());

    private final GroepElement identiteitGroepElement;
    private final GroepElement groepElement;
    private final AttribuutElement datumAanvangGeldigheidElement;
    private final AttribuutElement datumEindeGeldigheidElement;
    private final AttribuutElement tijdstipRegistratieElement;
    private final AttribuutElement tijdstipVervalElement;

    private VerConvRepository verConvRepository;

    /**
     * Constructor.
     * @param identiteitGroepElement identiteit groep element
     * @param groepElement groep element
     * @param datumAanvangGeldigheidElement datum aanvang geldigheid attribuut element
     * @param datumEindeGeldigheidElement datum einde geldigheid attribuut element
     * @param tijdstipRegistratieElement tijdstip registratie attribuut element
     * @param tijdstipVervalElement tijdstip verval attribuut element
     */
    public AbstractMapper(
            final GroepElement identiteitGroepElement,
            final GroepElement groepElement,
            final AttribuutElement datumAanvangGeldigheidElement,
            final AttribuutElement datumEindeGeldigheidElement,
            final AttribuutElement tijdstipRegistratieElement,
            final AttribuutElement tijdstipVervalElement) {
        this.identiteitGroepElement = identiteitGroepElement;
        this.groepElement = groepElement;
        this.datumAanvangGeldigheidElement = datumAanvangGeldigheidElement;
        this.datumEindeGeldigheidElement = datumEindeGeldigheidElement;
        this.tijdstipRegistratieElement = tijdstipRegistratieElement;
        this.tijdstipVervalElement = tijdstipVervalElement;
    }

    /**
     * Map een LO3 Voorkomen naar een LO3 Herkomst
     * @param lo3Voorkomen lo3 voorkomen uit de BRP database
     * @param brpStapelnummer stapelnummer obv BRP logica (kind krijgt stapelnummer obv relatie id in a-laag, reisdocument ook, etc)
     * @return LO3 Herkomst
     */
    private static Lo3Herkomst mapHerkomst(final Lo3Voorkomen lo3Voorkomen, final Integer brpStapelnummer) {
        try {
            final Lo3CategorieEnum lo3Categorie = Lo3CategorieEnum.getLO3Categorie(lo3Voorkomen.getCategorie());
            final Lo3CategorieEnum actueleLo3Categorie = Lo3CategorieEnum.bepaalActueleCategorie(lo3Categorie);
            final Integer stapelnummer;
            if (brpStapelnummer != null) {
                // Dit zou dan voor CAT_04, CAT_05, CAT_12 en CAT_13 gevuld moeten zijn.
                stapelnummer = brpStapelnummer;
            } else {
                stapelnummer = bepaalStapelnummerHerkomst(actueleLo3Categorie, lo3Voorkomen);
            }

            final Integer volgnummer;

            volgnummer = bepaalVolgnummerHerkomst(actueleLo3Categorie, lo3Voorkomen);

            return new Lo3Herkomst(lo3Categorie, stapelnummer, volgnummer, lo3Voorkomen.getConversieSortering());
        } catch (final Lo3SyntaxException e) {
            throw new IllegalArgumentException("Categorie (uit LO3Voorkomen) is ongeldig.", e);
        }
    }

    private static Integer bepaalStapelnummerHerkomst(final Lo3CategorieEnum actueleLo3Categorie, final Lo3Voorkomen lo3Voorkomen) {
        final Integer stapelnummer;
        switch (actueleLo3Categorie) {
            case CATEGORIE_01:
            case CATEGORIE_02:
            case CATEGORIE_03:
            case CATEGORIE_06:
            case CATEGORIE_07:
            case CATEGORIE_08:
            case CATEGORIE_10:
            case CATEGORIE_11:
            case CATEGORIE_13:
                // Deze categorien kunnen niet meer dan 1 keer voorkomen, dus overschrijven met default waarde.
                stapelnummer = Lo3CategorieWaarde.DEFAULT_STAPEL;
                break;
            default:
                // Deze zou dus theoretisch niet meer voor moeten komen.
                stapelnummer = lo3Voorkomen.getStapelvolgnummer();
        }
        return stapelnummer;
    }

    private static Integer bepaalVolgnummerHerkomst(final Lo3CategorieEnum actueleLo3Categorie, final Lo3Voorkomen lo3Voorkomen) {
        final Integer volgnummer;
        switch (actueleLo3Categorie) {
            case CATEGORIE_07:
            case CATEGORIE_12:
            case CATEGORIE_13:
                // Deze categorieen bevatten geen historie, dus voorkomenvolgnummer overschrijven met default waarde.
                volgnummer = Lo3CategorieWaarde.DEFAULT_VOORKOMEN;
                break;
            default:
                volgnummer = lo3Voorkomen.getVoorkomenvolgnummer();
        }
        return volgnummer;
    }

    /**
     * Map een DocumentHisVolledig naar een BrpStapel<BrpDocumentInhoud>.
     * @param document model van DocumentHisVolledig
     * @param onderzoekMapper onderzoek mapper
     * @return BrpActieBron
     */
    private static BrpStapel<BrpDocumentInhoud> mapDocument(final Document document, final OnderzoekMapper onderzoekMapper) {
        if (document == null) {
            return null;
        }

        final List<BrpGroep<BrpDocumentInhoud>> documentGroepen = new ArrayList<>();
        final BrpDocumentInhoud inhoud = mapDocumentInhoud(document, onderzoekMapper);

        documentGroepen.add(new BrpGroep<>(inhoud, null, null, null, null));
        return new BrpStapel<>(documentGroepen);
    }

    /**
     * Map een DocumentModel naar een BrpDocumentInhoud.
     * @param document model van document
     * @param onderzoekMapper onderzoek mapper
     * @return BrpDocumentInhoud
     */
    private static BrpDocumentInhoud mapDocumentInhoud(final Document document, final OnderzoekMapper onderzoekMapper) {
        final BrpSoortDocumentCode soortDocumentCode =
                BrpMapperUtil.mapBrpSoortDocumentCode(
                        document.getSoortNaam(),
                        onderzoekMapper.bepaalOnderzoek(document.getiD(), DOCUMENT_SOORT_ELEMENT, true));
        final BrpString aktenummer =
                BrpMapperUtil.mapBrpString(document.getAktenummer(), onderzoekMapper.bepaalOnderzoek(document.getiD(), DOCUMENT_AKTENUMMER_ELEMENT, true));
        final BrpString omschrijving =
                BrpMapperUtil.mapBrpString(
                        document.getOmschrijving(),
                        onderzoekMapper.bepaalOnderzoek(document.getiD(), DOCUMENT_OMSCHRIJVING_ELEMENT, true));
        final BrpPartijCode partijCode =
                BrpMapperUtil.mapBrpPartijCode(
                        document.getPartijCode(),
                        onderzoekMapper.bepaalOnderzoek(document.getiD(), DOCUMENT_PARTIJCODE_ELEMENT, true));

        return new BrpDocumentInhoud(soortDocumentCode, aktenummer, omschrijving, partijCode);
    }

    @Inject
    public void setVerConvRepository(final VerConvRepository verConvRepository) {
        this.verConvRepository = verConvRepository;
    }

    /**
     * Map.
     * @param object het te mappen object
     * @param onderzoekMapper onderzoek mapper
     * @return gemapte stapel
     */
    public final BrpStapel<G> map(final MetaObject object, final OnderzoekMapper onderzoekMapper) {
        final List<BrpGroep<G>> groepen = new ArrayList<>();

        final MetaRecord identiteitRecord = MetaModelUtil.getIdentiteitRecord(object, identiteitGroepElement);
        final Collection<MetaRecord> records = MetaModelUtil.getRecords(object, groepElement);
        if (records != null) {
            for (final MetaRecord record : records) {
                if (record.isIndicatieTbvLeveringMutaties() != null && record.isIndicatieTbvLeveringMutaties()) {
                    // Indien de indicatie voorkomen tbv levering mutaties 'Ja' is dan behoort het record niet tot de
                    // statische persoonslijst, maar enkel tot de dynamische persoonslijst.
                    continue;
                }

                final BrpGroep<G> groep = mapGroep(identiteitRecord, record, onderzoekMapper);
                if (groep != null) {
                    groepen.add(groep);
                }
            }
        }

        if (groepen.isEmpty()) {
            return null;
        } else {
            return new BrpStapel<>(groepen);
        }
    }

    /**
     * Map een BRP record naar een conversie groep.
     * @param identiteitRecord identiteit record
     * @param record BRP record
     * @param onderzoekMapper onderzoek mapper
     * @return Conversie groep
     */
    public final BrpGroep<G> mapGroep(final MetaRecord identiteitRecord, final MetaRecord record, final OnderzoekMapper onderzoekMapper) {
        final G inhoud = mapInhoud(identiteitRecord, record, onderzoekMapper);
        if (inhoud == null) {
            return null;
        } else {
            final Integer stapelNummer = getStapelNummer(identiteitRecord, record);

            final BrpHistorie brpHistorie = mapHistorie(record, onderzoekMapper);
            final BrpActie actieInhoud = mapActie(record.getActieInhoud(), onderzoekMapper, stapelNummer);
            final BrpActie actieVerval = mapActie(record.getActieVerval(), onderzoekMapper, stapelNummer);
            final BrpActie actieGeldigheid = mapActie(record.getActieAanpassingGeldigheid(), onderzoekMapper, stapelNummer);

            return new BrpGroep<>(inhoud, brpHistorie, actieInhoud, actieVerval, actieGeldigheid);
        }
    }

    /**
     * Map het BRP record naar conversie inhoud.
     * @param identiteitRecord identiteit record
     * @param record BRP record
     * @param onderzoekMapper onderzoek mapper
     * @return conversie inhoud
     */
    protected abstract G mapInhoud(final MetaRecord identiteitRecord, MetaRecord record, final OnderzoekMapper onderzoekMapper);

    /**
     * Geef de identificatie voor een stapel (binnen de lo3 categorieen).
     * @param identiteitRecord identiteit record
     * @param record BRP record
     * @return identificatie, null als geen stapel identificatie nodig is
     */
    protected Integer getStapelNummer(final MetaRecord identiteitRecord, final MetaRecord record) {
        // Hook
        return null;
    }

    private BrpHistorie mapHistorie(final MetaRecord record, final OnderzoekMapper onderzoekMapper) {
        // Als dit een formele historie betreft dan is er geen datum aanvang of datum einde
        final BrpDatum aanvang =
                datumAanvangGeldigheidElement == null ? null
                        : BrpMapperUtil.mapBrpDatum(
                                record.getDatumAanvangGeldigheid(),
                                onderzoekMapper.bepaalOnderzoek(
                                        record.getVoorkomensleutel(),
                                        datumAanvangGeldigheidElement,
                                        true));
        final BrpDatum einde =
                datumEindeGeldigheidElement == null ? null
                        : BrpMapperUtil.mapBrpDatum(
                                record.getDatumEindeGeldigheid(),
                                onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), datumEindeGeldigheidElement, true));

        final BrpDatumTijd registratie =
                BrpMapperUtil.mapBrpDatumTijd(
                        record.getTijdstipRegistratieAttribuut(),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), tijdstipRegistratieElement, true));

        final BrpDatumTijd verval =
                BrpMapperUtil.mapBrpDatumTijd(
                        record.getDatumTijdVervalAttribuut(),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), tijdstipVervalElement, true));

        final BrpCharacter brpNadereAanduidingVerval = BrpMapperUtil.mapNadereAanduidingVerval(record.getNadereAanduidingVerval());

        return new BrpHistorie(aanvang, einde, registratie, verval, brpNadereAanduidingVerval);
    }

    /**
     * Map een BRP database actie naar een BRP conversiemodel actie.
     * @param actie BRP database actie
     * @param onderzoekMapper onderzoek mapper
     * @param stapelNummer stapel nummer
     * @return BRP conversiemodel actie
     */
    private BrpActie mapActie(final Actie actie, final OnderzoekMapper onderzoekMapper, final Integer stapelNummer) {
        if (actie == null) {
            return null;
        }
        final BrpSoortActieCode soortActieCode =
                BrpMapperUtil.mapBrpSoortActieCode(actie.getSoort(), onderzoekMapper.bepaalOnderzoek(actie.getId(), ACTIE_SOORT_ELEMENT, true));
        final BrpPartijCode partijCode =
                BrpMapperUtil.mapBrpPartijCode(actie.getPartijCode(), onderzoekMapper.bepaalOnderzoek(actie.getId(), ACTIE_PARTIJCODE_ELEMENT, true));
        final BrpDatumTijd datumTijdRegistratie =
                BrpMapperUtil.mapBrpDatumTijd(
                        actie.getTijdstipRegistratie(),
                        onderzoekMapper.bepaalOnderzoek(actie.getId(), ACTIE_TIJDSTIP_REGISTRATIE_ELEMENT, true));
        final BrpDatum datumOntlening =
                BrpMapperUtil.mapBrpDatum(actie.getDatumOntlening(), onderzoekMapper.bepaalOnderzoek(actie.getId(), ACTIE_DATUM_ONTLENING_ELEMENT, true));

        final List<BrpActieBron> actieBronnen = mapActiebronnen(actie.getBronnen(), onderzoekMapper);

        // BLAUW-6185
        final Lo3Voorkomen lo3Voorkomen = verConvRepository.zoekLo3VoorkomenVoorActie(actie.getId());
        final Lo3Herkomst lo3Herkomst;
        if (lo3Voorkomen != null) {
            lo3Herkomst = mapHerkomst(lo3Voorkomen, stapelNummer);
        } else if (stapelNummer != null) {
            lo3Herkomst = new Lo3Herkomst(null, stapelNummer, Lo3CategorieWaarde.DEFAULT_VOORKOMEN);
        } else {
            lo3Herkomst = null;
        }

        return new BrpActie(actie.getId(), soortActieCode, partijCode, datumTijdRegistratie, datumOntlening, actieBronnen, 0, lo3Herkomst);
    }

    /**
     * Map een set van ActieBronHisVolledig naar een Lijst van BrpActieBron.
     * @param actiebronnen set van ActieBronHisVolledig
     * @param onderzoekMapper onderzoek mapper
     * @return Lijst van BrpActieBron
     */
    private List<BrpActieBron> mapActiebronnen(final Set<Actiebron> actiebronnen, final OnderzoekMapper onderzoekMapper) {
        if (actiebronnen == null || actiebronnen.isEmpty()) {
            return Collections.emptyList();
        }

        final List<BrpActieBron> result = new ArrayList<>();
        for (final Actiebron actiebron : actiebronnen) {
            result.add(mapActiebron(actiebron, onderzoekMapper));
        }

        return result;
    }

    /**
     * Map een ActieBronHisVolledig naar een BrpActieBron.
     * @param actiebron actiebron
     * @param onderzoekMapper onderzoek mapper
     * @return BrpActieBron
     */
    private BrpActieBron mapActiebron(final Actiebron actiebron, final OnderzoekMapper onderzoekMapper) {
        final BrpStapel<BrpDocumentInhoud> documentStapel = mapDocument(actiebron.getDocument(), onderzoekMapper);

        final BrpString rechtsgrondOmschrijving =
                BrpMapperUtil.mapBrpString(
                        actiebron.getRechtsgrondomschrijving(),
                        onderzoekMapper.bepaalOnderzoek(actiebron.getId(), ACTIEBRON_RECHTSGROND_OMSCHRIJVING_ELEMENT, false));

        return new BrpActieBron(documentStapel, rechtsgrondOmschrijving);
    }

}
