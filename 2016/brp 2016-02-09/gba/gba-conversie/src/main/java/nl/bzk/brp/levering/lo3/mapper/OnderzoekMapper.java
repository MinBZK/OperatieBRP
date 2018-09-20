/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.brp.levering.lo3.util.HistorieSetUtil;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.FormeleHistorieSet;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SleutelwaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.CategorieAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.StatusOnderzoek;
import nl.bzk.brp.model.hisvolledig.kern.GegevenInOnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.OnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonOnderzoekHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisOnderzoekModel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;

/**
 * Mapt onderzoek.
 */
public final class OnderzoekMapper {

    /** LO3 Onderzoek omschrijving prefix. */
    public static final String LO3_ONDERZOEK_OMSCHRIJVING_PREFIX = "Conversie GBA: ";

    private static final int CATEGORIE_56 = 56;
    private static final int CATEGORIE_06 = 6;
    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final String BRP_ONDERZOEK_GEGEVENS_IN_ONDERZOEK = "0";

    private final Map<OnderzoekHisVolledig, Lo3Onderzoek> onderzoeken = new IdentityHashMap<>();

    /**
     * Constructor.
     *
     * @param persoonHisVolledig
     *            persoon volledig
     */
    public OnderzoekMapper(final PersoonHisVolledig persoonHisVolledig) {
        vulOnderzoeken(persoonHisVolledig.getOnderzoeken());
    }

    /**
     * Bepaal of een attribuut in een onderzoek betrokken is en lever in dat geval een Lo3Onderzoek op.
     *
     * @param entiteitId
     *            Het ID van de (database) entiteit instantie waar het attribuut onderdeel van uitmaakt.
     * @param element
     *            het element die bij een onderzoek betrokken zou moeten zijn.
     * @param elementBehoortBijGroepsOnderzoek
     *            Geeft aan of dit attribuut in onderzoek moet staan als de BRP groep in zijn geheel in onderzoek staat.
     * @return Het Lo3Onderzoek als dit attribuut in onderzoek staat, of anders <code>null</code>.
     */
    public Lo3Onderzoek bepaalOnderzoek(final Number entiteitId, final ElementEnum element, final boolean elementBehoortBijGroepsOnderzoek) {
        LOGGER.debug("bepaalOnderzoek(id={}, element={})", entiteitId, element);
        for (final Map.Entry<OnderzoekHisVolledig, Lo3Onderzoek> onderzoekEntry : onderzoeken.entrySet()) {
            final OnderzoekHisVolledig onderzoek = onderzoekEntry.getKey();
            for (final GegevenInOnderzoekHisVolledig gegevenInOnderzoek : onderzoek.getGegevensInOnderzoek()) {
                final Element elementInOnderzoek = gegevenInOnderzoek.getElement().getWaarde();
                final boolean individueelElementInOnderzoek = elementInOnderzoek.isHetzelfdeElement(element);
                final boolean elementInGroepsOnderzoek = elementBehoortBijGroepsOnderzoek && elementInOnderzoek.isHetzelfdeElement(element.getGroep());

                final boolean isInOnderzoekObvElementIdentificatie = individueelElementInOnderzoek || elementInGroepsOnderzoek;
                final Long entiteidIdAlsLong = Long.valueOf(entiteitId.longValue());
                final boolean isInOnderzoekObvEntiteitVoorkomen =
                        entiteidIdAlsLong.equals(haalWaardeUitSleutelwaardeAttribuut(gegevenInOnderzoek.getObjectSleutelGegeven()))
                                                                  || entiteidIdAlsLong.equals(
                                                                      haalWaardeUitSleutelwaardeAttribuut(
                                                                          gegevenInOnderzoek.getVoorkomenSleutelGegeven()));

                if (isInOnderzoekObvElementIdentificatie && isInOnderzoekObvEntiteitVoorkomen) {
                    LOGGER.debug("bepaalOnderzoek(id={}, element={}) -> {}", entiteitId, element, onderzoekEntry.getValue());
                    return onderzoekEntry.getValue();
                }
            }
        }

        return null;
    }

    private Long haalWaardeUitSleutelwaardeAttribuut(final SleutelwaardeAttribuut sleutelwaardeAttribuut) {

        if (sleutelwaardeAttribuut == null) {
            return null;
        }

        return sleutelwaardeAttribuut.getWaarde();
    }

    private void vulOnderzoeken(final Set<? extends PersoonOnderzoekHisVolledig> set) {
        for (final PersoonOnderzoekHisVolledig onderzoek : set) {

            final HisOnderzoekModel onderzoekActueel = HistorieSetUtil.getLaatsteRecord(onderzoek.getOnderzoek().getOnderzoekHistorie());

            final String lo3GegevensInOnderzoek = bepaalGegevensInOnderzoek(onderzoekActueel);
            final Lo3Datum datumIngangOnderzoek = new BrpDatum(onderzoekActueel.getDatumAanvang().getWaarde(), null).converteerNaarLo3Datum();

            final boolean isOnderzoekBeindigd = StatusOnderzoek.AFGESLOTEN.equals(onderzoekActueel.getStatus().getWaarde());
            final Lo3Datum datumEindeOnderzoek =
                    isOnderzoekBeindigd ? new BrpDatum(onderzoekActueel.getDatumEinde().getWaarde(), null).converteerNaarLo3Datum() : null;

            final Lo3Onderzoek lo3Onderzoek = new Lo3Onderzoek(new Lo3Integer(lo3GegevensInOnderzoek, null), datumIngangOnderzoek, datumEindeOnderzoek);

            if (LOGGER.isDebugEnabled()) {
                final StringBuilder sb = new StringBuilder();
                sb.append("Onderzoek: " + lo3Onderzoek + " -> ");
                boolean first = true;
                for (final GegevenInOnderzoekHisVolledig gio : onderzoek.getOnderzoek().getGegevensInOnderzoek()) {
                    if (first) {
                        first = false;
                    } else {
                        sb.append(", ");
                    }
                    sb.append(gio.getElement().getWaarde().geefElementEnumName());
                    sb.append(" (");
                    if (gio.getObjectSleutelGegeven() != null && gio.getObjectSleutelGegeven().getWaarde() != null) {
                        sb.append("object=").append(gio.getObjectSleutelGegeven().getWaarde());
                    }
                    if (gio.getVoorkomenSleutelGegeven() != null && gio.getVoorkomenSleutelGegeven().getWaarde() != null) {
                        sb.append("voorkomen=").append(gio.getVoorkomenSleutelGegeven().getWaarde());
                    }
                    sb.append(")");
                }
                LOGGER.debug(sb.toString());
            }
            onderzoeken.put(onderzoek.getOnderzoek(), lo3Onderzoek);
        }
    }

    /**
     * Bepaal puur op de acties en de 'groep' element enum het onderzoek.
     *
     * @param acties
     *            actie id's
     * @param objectSleutels
     *            object sleutels
     * @param voorkomenSleutels
     *            voorkomen sleutels
     * @param groepElement
     *            groep element
     * @return onderzoek
     */
    public Lo3Onderzoek bepaalActueelOnderzoek(
        final List<Long> acties,
        final List<Long> objectSleutels,
        final List<Long> voorkomenSleutels,
        final ElementEnum groepElement)
    {
        for (final Map.Entry<OnderzoekHisVolledig, Lo3Onderzoek> onderzoekEntry : onderzoeken.entrySet()) {
            final OnderzoekHisVolledig onderzoek = onderzoekEntry.getKey();

            if (isOnderzoekObvGegevens(onderzoek.getGegevensInOnderzoek(), objectSleutels, voorkomenSleutels, groepElement)
                && isOnderzoekObvActieInhoud(onderzoek.getOnderzoekHistorie(), acties))
            {
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
     * Als een onderzoek wordt gezocht over een huwelijk of geregistreerd partnerschap dan moeten onderzoeken die
     * voortkomen uit categorie 06 worden genegeerd.
     *
     * @param groepElement
     *            groep element
     * @param onderzoek
     *            lo3 onderzoek
     * @return true als het de uitzondering betreft, anders false
     */
    private boolean isRelatieUitzondering(final ElementEnum groepElement, final Lo3Onderzoek onderzoek) {
        return (groepElement == ElementEnum.HUWELIJK_STANDAARD || groepElement == ElementEnum.GEREGISTREERDPARTNERSCHAP_STANDAARD)
               && (onderzoek.getOnderzoekCategorienummer() == CATEGORIE_06 || onderzoek.getOnderzoekCategorienummer() == CATEGORIE_56);
    }

    private boolean isOnderzoekObvGegevens(
        final Set<? extends GegevenInOnderzoekHisVolledig> gegevensInOnderzoek,
        final List<Long> objectSleutels,
        final List<Long> voorkomenSleutels,
        final ElementEnum groepElement)
    {
        for (final GegevenInOnderzoekHisVolledig gegevenInOnderzoek : gegevensInOnderzoek) {
            final ElementEnum elementInOnderzoek = ElementEnum.valueOf(gegevenInOnderzoek.getElement().getWaarde().geefElementEnumName());
            final ElementEnum groepVanElementInOnderzoek = elementInOnderzoek.getGroep();

            final boolean isOnderzoekObvElement = groepElement == groepVanElementInOnderzoek;
            final boolean isOnderzoekObvVoorkomen =
                    gegevenInOnderzoek.getVoorkomenSleutelGegeven() != null
                                                    && voorkomenSleutels.contains(gegevenInOnderzoek.getVoorkomenSleutelGegeven().getWaarde());
            final boolean isOnderzoekObvObject =
                    gegevenInOnderzoek.getObjectSleutelGegeven() != null
                                                 && objectSleutels.contains(gegevenInOnderzoek.getObjectSleutelGegeven().getWaarde());
            final boolean isOnderzoekObvSleutel = isOnderzoekObvVoorkomen || isOnderzoekObvObject;

            if (isOnderzoekObvElement && isOnderzoekObvSleutel) {
                return true;
            }
        }

        return false;
    }

    private boolean isOnderzoekObvActieInhoud(final FormeleHistorieSet<HisOnderzoekModel> onderzoekHistorie, final List<Long> acties) {
        for (final HisOnderzoekModel hisOnderzoek : onderzoekHistorie) {
            if (acties.contains(hisOnderzoek.getVerantwoordingInhoud().getID())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Bepaal historisch onderzoek.
     *
     * @param acties
     *            acties
     * @param objectSleutels
     *            object sleutels
     * @param voorkomenSleutels
     *            voorkomensleutels
     * @param groepElement
     *            groep element
     * @return historisch onderzoek
     */
    public Lo3Onderzoek bepaalHistorischOnderzoek(
        final List<Long> acties,
        final List<Long> objectSleutels,
        final List<Long> voorkomenSleutels,
        final ElementEnum groepElement)
    {
        for (final Map.Entry<OnderzoekHisVolledig, Lo3Onderzoek> onderzoekEntry : onderzoeken.entrySet()) {
            final OnderzoekHisVolledig onderzoek = onderzoekEntry.getKey();

            if (isOnderzoekObvGegevens(onderzoek.getGegevensInOnderzoek(), objectSleutels, voorkomenSleutels, groepElement)) {
                final HisOnderzoekModel hisOnderzoek = bepaalOnderzoekObvActieVerval(onderzoek.getOnderzoekHistorie(), acties);

                if (hisOnderzoek != null) {
                    return maakLo3Onderzoek(hisOnderzoek);
                }
            }
        }

        return null;
    }

    private HisOnderzoekModel bepaalOnderzoekObvActieVerval(final FormeleHistorieSet<HisOnderzoekModel> onderzoekHistorie, final List<Long> acties) {
        for (final HisOnderzoekModel hisOnderzoek : onderzoekHistorie) {
            if (hisOnderzoek.getVerantwoordingVervalTbvLeveringMutaties() != null
                && acties.contains(hisOnderzoek.getVerantwoordingVervalTbvLeveringMutaties().getID())
                || hisOnderzoek.getVerantwoordingVerval() != null && acties.contains(hisOnderzoek.getVerantwoordingVerval().getID()))
            {
                return hisOnderzoek;
            }
        }

        return null;
    }

    private Lo3Onderzoek maakLo3Onderzoek(final HisOnderzoekModel hisOnderzoek) {
        final String gegevensInOnderzoek = bepaalGegevensInOnderzoek(hisOnderzoek);
        final Lo3Datum datumIngang = new BrpDatum(hisOnderzoek.getDatumAanvang().getWaarde(), null).converteerNaarLo3Datum();
        final Lo3Datum datumEinde =
                hisOnderzoek.getDatumEinde() == null ? null : new BrpDatum(hisOnderzoek.getDatumEinde().getWaarde(), null).converteerNaarLo3Datum();

        return new Lo3Onderzoek(new Lo3Integer(gegevensInOnderzoek, null), datumIngang, datumEinde);
    }

    private String bepaalGegevensInOnderzoek(final HisOnderzoekModel hisOnderzoek) {
        final CategorieAdministratieveHandeling categorieAdministratieveHandeling =
                hisOnderzoek.getVerantwoordingInhoud().getAdministratieveHandeling().getSoort().getWaarde().getCategorieAdministratieveHandeling();
        final boolean isOnderzoekUitConversieVanuitLo3 =
                CategorieAdministratieveHandeling.G_B_A_INITIELE_VULLING.equals(
                    categorieAdministratieveHandeling) || CategorieAdministratieveHandeling.G_B_A_SYNCHRONISATIE.equals(categorieAdministratieveHandeling);

        final String resultaat;
        if (isOnderzoekUitConversieVanuitLo3) {
            final String onderzoekOmschrijving = hisOnderzoek.getOmschrijving().getWaarde();
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
