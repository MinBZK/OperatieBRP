/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.CategorieAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.StatusOnderzoek;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.ElementObject;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.element.ObjectElement;
import nl.bzk.brp.levering.lo3.decorator.GegevenInOnderzoek;
import nl.bzk.brp.levering.lo3.decorator.Onderzoek;
import nl.bzk.brp.levering.lo3.decorator.OnderzoekStandaardRecord;
import nl.bzk.brp.levering.lo3.decorator.Persoon;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import org.springframework.util.Assert;

/**
 * Mapt onderzoek.
 */
public final class OnderzoekMapperImpl implements OnderzoekMapper {

    /**
     * Persoon onderzoek object element.
     */
    public static final ObjectElement PERSOON_ONDERZOEK_OBJECT_ELEMENT = ElementHelper.getObjectElement(Element.ONDERZOEK.getId());

    /**
     * Object element.
     */
    public static final ObjectElement OBJECT_ELEMENT = ElementHelper.getObjectElement(Element.ONDERZOEK.getId());

    /**
     * Groep element.
     */
    public static final GroepElement GROEP_ELEMENT = ElementHelper.getGroepElement(Element.ONDERZOEK_STANDAARD.getId());

    /**
     * LO3 Onderzoek omschrijving prefix.
     */
    public static final String LO3_ONDERZOEK_OMSCHRIJVING_PREFIX = "Conversie GBA: ";

    private static final Set<SoortActie> GBA_ACTIE_SOORTEN =
            EnumSet.of(SoortActie.CONVERSIE_GBA, SoortActie.CONVERSIE_GBA_LEGE_ONJUISTE_CATEGORIE, SoortActie.CONVERSIE_GBA_MATERIELE_HISTORIE);

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final int CATEGORIE_56 = 56;
    private static final int CATEGORIE_06 = 6;

    private static final String BRP_ONDERZOEK_GEGEVENS_IN_ONDERZOEK = "0";

    private final Map<Onderzoek, Lo3Onderzoek> onderzoeken;

    /**
     * Constructor.
     * @param persoon persoon
     */
    public OnderzoekMapperImpl(final Persoon persoon) {
        onderzoeken = vulOnderzoeken(persoon.getOnderzoeken());
    }

    @Override
    public Lo3Onderzoek bepaalOnderzoek(final Long voorkomenSleutel, final AttribuutElement element, final boolean elementBehoortBijGroepsOnderzoek) {
        return bepaalOnderzoeken(voorkomenSleutel, element, elementBehoortBijGroepsOnderzoek).stream().findFirst().orElse(null);
    }

    @Override
    public Set<Lo3Onderzoek> bepaalOnderzoeken(final Long voorkomenSleutel, final AttribuutElement element, final boolean elementBehoortBijGroepsOnderzoek) {
        final Set<Lo3Onderzoek> resultaat = new HashSet<>();
        for (final Map.Entry<Onderzoek, Lo3Onderzoek> onderzoek : zoekOnderzoeken(voorkomenSleutel, element, elementBehoortBijGroepsOnderzoek)) {
            resultaat.add(onderzoek.getValue());
        }
        return resultaat;
    }


    @Override
    public Lo3Onderzoek bepaalOnderzoek(final Long objectSleutel, final AttribuutElement element, final boolean elementBehoortBijGroepsOnderzoek,
                                        final Long voorkomenSleutel, final GroepElement groepElement) {
        final Set<Map.Entry<Onderzoek, Lo3Onderzoek>> onderzoekenkOpIdentiteitAttribuut =
                zoekOnderzoeken(objectSleutel, element, elementBehoortBijGroepsOnderzoek);
        final Map.Entry<Onderzoek, Lo3Onderzoek> onderzoekOpIdentiteitAttribuut = onderzoekenkOpIdentiteitAttribuut.stream().findFirst().orElse(null);

        final Lo3Onderzoek resultaat;
        if (onderzoekOpIdentiteitAttribuut == null) {
            resultaat = null;
        } else {
            if (isLo3Onderzoek(onderzoekOpIdentiteitAttribuut.getKey())) {
                // GBA onderzoek; Controleer dat er ook voor de groep een onderzoek is
                if (bestaatOnderzoek(voorkomenSleutel, groepElement)) {
                    resultaat = onderzoekOpIdentiteitAttribuut.getValue();
                } else {
                    resultaat = null;
                }
            } else {
                // BRP onderzoek
                resultaat = onderzoekOpIdentiteitAttribuut.getValue();
            }
        }
        return resultaat;
    }


    private boolean bestaatOnderzoek(final Long voorkomenSleutel, final GroepElement groepElement) {
        for (final Map.Entry<Onderzoek, Lo3Onderzoek> onderzoekEntry : onderzoeken.entrySet()) {
            final Onderzoek onderzoek = onderzoekEntry.getKey();
            for (final GegevenInOnderzoek gegevenInOnderzoek : onderzoek.getGegevensInOnderzoek()) {
                final boolean isInOnderzoekObvElementIdentificatie = gegevenInOnderzoek.getElement().equals(groepElement);
                final boolean isInOnderzoekObvEntiteitVoorkomen = voorkomenSleutel.equals(gegevenInOnderzoek.getVoorkomenSleutelGegeven());
                if (isInOnderzoekObvElementIdentificatie && isInOnderzoekObvEntiteitVoorkomen) {
                    return true;
                }
            }
        }
        return false;
    }


    private Set<Map.Entry<Onderzoek, Lo3Onderzoek>> zoekOnderzoeken(final Long voorkomenSleutel, final AttribuutElement element,
                                                                    final boolean elementBehoortBijGroepsOnderzoek) {

        final Set<Map.Entry<Onderzoek, Lo3Onderzoek>> resultaat = new HashSet<>();

        LOGGER.debug("bepaalOnderzoek(voorkomenSleutel={}, element={})", voorkomenSleutel, element);
        for (final Map.Entry<Onderzoek, Lo3Onderzoek> onderzoekEntry : onderzoeken.entrySet()) {
            final Onderzoek onderzoek = onderzoekEntry.getKey();
            final boolean isGbaOnderzoek = isLo3Onderzoek(onderzoek);
            for (final GegevenInOnderzoek gegevenInOnderzoek : onderzoek.getGegevensInOnderzoek()) {
                final ElementObject elementInOnderzoek = gegevenInOnderzoek.getElement();
                final boolean individueelElementInOnderzoek = elementInOnderzoek.equals(element);
                // Bepaal of dit element in onderzoek staat omdat de groep in onderzoek staat.
                // Dit geldt niet voor GBA onderzoeken.
                final boolean elementInGroepsOnderzoek = !isGbaOnderzoek && elementBehoortBijGroepsOnderzoek && elementInOnderzoek.equals(element.getGroep());

                final boolean isInOnderzoekObvElementIdentificatie = individueelElementInOnderzoek || elementInGroepsOnderzoek;
                final boolean isInOnderzoekObvEntiteitVoorkomen = voorkomenSleutel.equals(gegevenInOnderzoek.getObjectSleutelGegeven())
                        || voorkomenSleutel.equals(gegevenInOnderzoek.getVoorkomenSleutelGegeven());

                if (isInOnderzoekObvElementIdentificatie && isInOnderzoekObvEntiteitVoorkomen) {
                    LOGGER.debug("bepaalOnderzoek(voorkomenSleutel={}, element={}) -> {}", voorkomenSleutel, element, onderzoekEntry.getValue());
                    resultaat.add(onderzoekEntry);
                }
            }
        }

        return resultaat;
    }


    private boolean isLo3Onderzoek(final Onderzoek onderzoek) {
        final SoortActie soortActie = onderzoek.getStandaard().iterator().next().getActieInhoud().getSoort();
        return GBA_ACTIE_SOORTEN.contains(soortActie);
    }

    private static Map<Onderzoek, Lo3Onderzoek> vulOnderzoeken(final Collection<Onderzoek> onderzoeken) {
        final Map<Onderzoek, Lo3Onderzoek> resultaat = new IdentityHashMap<>();

        for (final Onderzoek onderzoek : onderzoeken) {

            final OnderzoekStandaardRecord onderzoekActueel = bepaalLaatsteRecord(onderzoek.getStandaard());
            Assert.notNull(onderzoekActueel, "Geen actueel record voor onderzoek gevonden.");
            if (onderzoekActueel != null) {
                final String lo3GegevensInOnderzoek = bepaalGegevensInOnderzoek(onderzoekActueel);
                final Lo3Datum datumIngangOnderzoek = new BrpDatum(onderzoekActueel.getDatumAanvang(), null).converteerNaarLo3Datum();

                final boolean isOnderzoekBeindigd = StatusOnderzoek.AFGESLOTEN.getNaam().equals(onderzoekActueel.getStatus());
                final Lo3Datum datumEindeOnderzoek = isOnderzoekBeindigd ? new BrpDatum(onderzoekActueel.getDatumEinde(), null).converteerNaarLo3Datum() : null;

                final Lo3Onderzoek lo3Onderzoek = new Lo3Onderzoek(new Lo3Integer(lo3GegevensInOnderzoek, null), datumIngangOnderzoek, datumEindeOnderzoek);

                resultaat.put(onderzoek, lo3Onderzoek);
            }
        }
        return resultaat;
    }

    private static OnderzoekStandaardRecord bepaalLaatsteRecord(final Collection<OnderzoekStandaardRecord> records) {
        OnderzoekStandaardRecord laatste = null;

        for (final OnderzoekStandaardRecord onderzoek : records) {
            if ((laatste == null) || (onderzoek.getVoorkomenSleutel() > laatste.getVoorkomenSleutel())) {
                laatste = onderzoek;
            }
        }
        return laatste;
    }

    /**
     * Bepaal puur op de acties en de 'groep' element enum het onderzoek.
     * @param acties actie id's
     * @param objectSleutels object sleutels
     * @param voorkomenSleutels voorkomen sleutels
     * @param groepElement groep element
     * @return onderzoek
     */
    @Override
    public Lo3Onderzoek bepaalActueelOnderzoek(final List<Long> acties, final List<Long> objectSleutels, final List<Long> voorkomenSleutels,
                                               final GroepElement groepElement) {
        for (final Map.Entry<Onderzoek, Lo3Onderzoek> onderzoekEntry : onderzoeken.entrySet()) {
            final Onderzoek onderzoek = onderzoekEntry.getKey();

            if (isOnderzoekObvGegevens(onderzoek.getGegevensInOnderzoek(), objectSleutels, voorkomenSleutels, groepElement)
                    && isOnderzoekObvActieInhoud(onderzoek.getStandaard(), acties)) {
                // Relatie uitzondering
                final Lo3Onderzoek lo3Onderzoek = onderzoekEntry.getValue();

                if (!isRelatieUitzondering(groepElement, lo3Onderzoek)) {
                    return lo3Onderzoek;
                }
            }
        }

        return null;
    }

    /**
     * Als een onderzoek wordt gezocht over een huwelijk of geregistreerd partnerschap dan moeten
     * onderzoeken die voortkomen uit categorie 06 worden genegeerd.
     * @param groepElement groep element
     * @param onderzoek lo3 onderzoek
     * @return true als het de uitzondering betreft, anders false
     */
    private boolean isRelatieUitzondering(final GroepElement groepElement, final Lo3Onderzoek onderzoek) {
        return HuwelijkRelatieMapper.GROEP_ELEMENT.equals(groepElement) || (GeregistreerdPartnerschapRelatieMapper.GROEP_ELEMENT.equals(groepElement)
                && ((onderzoek.getOnderzoekCategorienummer() == CATEGORIE_06) || (onderzoek.getOnderzoekCategorienummer() == CATEGORIE_56)));
    }

    private boolean isOnderzoekObvGegevens(final Collection<GegevenInOnderzoek> gegevensInOnderzoek, final List<Long> objectSleutels,
                                           final List<Long> voorkomenSleutels, final GroepElement groepElement) {
        for (final GegevenInOnderzoek gegevenInOnderzoek : gegevensInOnderzoek) {
            final GroepElement groepVanElementInOnderzoek;
            if (gegevenInOnderzoek.getElement() instanceof GroepElement) {
                groepVanElementInOnderzoek = (GroepElement) gegevenInOnderzoek.getElement();
            } else {
                final AttribuutElement elementInOnderzoek = (AttribuutElement) gegevenInOnderzoek.getElement();
                groepVanElementInOnderzoek = elementInOnderzoek.getGroep();
            }

            final boolean isOnderzoekObvElement = groepElement.equals(groepVanElementInOnderzoek);
            final boolean isOnderzoekObvVoorkomen =
                    (gegevenInOnderzoek.getVoorkomenSleutelGegeven() != null) && voorkomenSleutels.contains(gegevenInOnderzoek.getVoorkomenSleutelGegeven());
            final boolean isOnderzoekObvObject =
                    (gegevenInOnderzoek.getObjectSleutelGegeven() != null) && objectSleutels.contains(gegevenInOnderzoek.getObjectSleutelGegeven());
            final boolean isOnderzoekObvSleutel = isOnderzoekObvVoorkomen || isOnderzoekObvObject;

            if (isOnderzoekObvElement && isOnderzoekObvSleutel) {
                return true;
            }
        }

        return false;
    }

    private boolean isOnderzoekObvActieInhoud(final Collection<OnderzoekStandaardRecord> onderzoekHistorie, final List<Long> acties) {
        for (final OnderzoekStandaardRecord hisOnderzoek : onderzoekHistorie) {
            if (acties.contains(hisOnderzoek.getActieInhoud().getId())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Bepaal historisch onderzoek.
     * @param acties acties
     * @param objectSleutels object sleutels
     * @param voorkomenSleutels voorkomensleutels
     * @param groepElement groep element
     * @return historisch onderzoek
     */
    @Override
    public Lo3Onderzoek bepaalHistorischOnderzoek(final List<Long> acties, final List<Long> objectSleutels, final List<Long> voorkomenSleutels,
                                                  final GroepElement groepElement) {
        for (final Map.Entry<Onderzoek, Lo3Onderzoek> onderzoekEntry : onderzoeken.entrySet()) {
            final Onderzoek onderzoek = onderzoekEntry.getKey();

            if (isOnderzoekObvGegevens(onderzoek.getGegevensInOnderzoek(), objectSleutels, voorkomenSleutels, groepElement)) {
                final OnderzoekStandaardRecord hisOnderzoek = bepaalOnderzoekObvActieVerval(onderzoek.getStandaard(), acties);

                if (hisOnderzoek != null) {
                    return maakLo3Onderzoek(hisOnderzoek);
                }
            }
        }

        return null;
    }

    private OnderzoekStandaardRecord bepaalOnderzoekObvActieVerval(final Collection<OnderzoekStandaardRecord> onderzoekHistorie, final List<Long> acties) {
        for (final OnderzoekStandaardRecord hisOnderzoek : onderzoekHistorie) {
            if (((hisOnderzoek.getActieVervalTbvLeveringMutaties() != null) && acties.contains(hisOnderzoek.getActieVervalTbvLeveringMutaties().getId()))
                    || ((hisOnderzoek.getActieVerval() != null) && acties.contains(hisOnderzoek.getActieVerval().getId()))) {
                return hisOnderzoek;
            }
        }

        return null;
    }

    private Lo3Onderzoek maakLo3Onderzoek(final OnderzoekStandaardRecord hisOnderzoek) {
        final String gegevensInOnderzoek = bepaalGegevensInOnderzoek(hisOnderzoek);
        final Lo3Datum datumIngang = new BrpDatum(hisOnderzoek.getDatumAanvang(), null).converteerNaarLo3Datum();
        final Lo3Datum datumEinde = hisOnderzoek.getDatumEinde() == null ? null : new BrpDatum(hisOnderzoek.getDatumEinde(), null).converteerNaarLo3Datum();

        return new Lo3Onderzoek(new Lo3Integer(gegevensInOnderzoek, null), datumIngang, datumEinde);
    }

    private static String bepaalGegevensInOnderzoek(final OnderzoekStandaardRecord hisOnderzoek) {
        final CategorieAdministratieveHandeling categorieAdministratieveHandeling =
                hisOnderzoek.getActieInhoud().getAdministratieveHandeling().getSoort().getCategorie();
        final boolean isOnderzoekUitConversieVanuitLo3 = CategorieAdministratieveHandeling.GBA_INITIELE_VULLING.equals(categorieAdministratieveHandeling)
                || CategorieAdministratieveHandeling.GBA_SYNCHRONISATIE.equals(categorieAdministratieveHandeling);

        final String resultaat;
        if (isOnderzoekUitConversieVanuitLo3) {
            final String onderzoekOmschrijving = hisOnderzoek.getOmschrijving();
            if (onderzoekOmschrijving.startsWith(LO3_ONDERZOEK_OMSCHRIJVING_PREFIX)) {
                resultaat = onderzoekOmschrijving.substring(LO3_ONDERZOEK_OMSCHRIJVING_PREFIX.length());
            } else {
                resultaat = BRP_ONDERZOEK_GEGEVENS_IN_ONDERZOEK;
            }
        } else {
            resultaat = BRP_ONDERZOEK_GEGEVENS_IN_ONDERZOEK;
        }

        return resultaat;
    }

}
