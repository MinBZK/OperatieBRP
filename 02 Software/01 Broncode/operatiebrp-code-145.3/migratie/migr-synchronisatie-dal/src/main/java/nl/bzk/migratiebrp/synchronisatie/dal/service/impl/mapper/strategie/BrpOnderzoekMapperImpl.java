/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie;

import java.util.Collection;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.GegevenInOnderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Onderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.StatusOnderzoek;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;

/**
 * Mapper om op algemene wijze BRP Onderzoek entiteiten te vertalen naar onderzoeken op individuele attributen in het
 * migratie model.
 */
public final class BrpOnderzoekMapperImpl implements BrpOnderzoekMapper {
    private static final String BRP_ONDERZOEK_GEGEVENS_IN_ONDERZOEK = "0";
    private final Map<Onderzoek, Lo3Onderzoek> onderzoeken = new IdentityHashMap<>();

    /**
     * Maak een BrpOnderzoekMapperImpl aan op basis van een lijst Onderzoek entiteiten.
     * @param onderzoeken de Onderzoek entiteiten
     */
    public BrpOnderzoekMapperImpl(final Collection<Onderzoek> onderzoeken) {
        vulOnderzoeken(onderzoeken);
    }

    @Override
    public Lo3Onderzoek bepaalOnderzoek(final Object entiteit, final Element element, final boolean elementBehoortBijGroepsOnderzoek) {
        final Set<Lo3Onderzoek> lo3Onderzoeken = bepaalOnderzoeken(entiteit, element, elementBehoortBijGroepsOnderzoek);
        if (!lo3Onderzoeken.isEmpty()) {
            return lo3Onderzoeken.iterator().next();
        }
        return null;
    }

    @Override
    public Set<Lo3Onderzoek> bepaalOnderzoeken(final Object entiteit, final Element element, final boolean elementBehoortBijGroepsOnderzoek) {
        final Set<Lo3Onderzoek> lo3Onderzoeken = new HashSet<>();
        if (element != null) {
            for (final Map.Entry<Onderzoek, Lo3Onderzoek> onderzoekEntry : onderzoeken.entrySet()) {
                final Onderzoek onderzoek = onderzoekEntry.getKey();
                final boolean isLo3Onderzoek = isLo3Onderzoek(onderzoek);
                for (final GegevenInOnderzoek gegevenInOnderzoek : onderzoek.getGegevenInOnderzoekSet()) {
                    voegOnderzoekToe(entiteit, element, elementBehoortBijGroepsOnderzoek, lo3Onderzoeken, onderzoekEntry, isLo3Onderzoek, gegevenInOnderzoek);
                }
            }
        }
        return lo3Onderzoeken;

    }

    private void voegOnderzoekToe(final Object entiteit, final Element element, final boolean elementBehoortBijGroepsOnderzoek,
                                  final Set<Lo3Onderzoek> lo3Onderzoeken, final Map.Entry<Onderzoek, Lo3Onderzoek> onderzoekEntry, final boolean isLo3Onderzoek,
                                  final GegevenInOnderzoek gegevenInOnderzoek) {
        final boolean individueelElementInOnderzoek = element == gegevenInOnderzoek.getSoortGegeven();
        final boolean elementInGroepsOnderzoek = !isLo3Onderzoek && elementBehoortBijGroepsOnderzoek && element.getGroep() == gegevenInOnderzoek
                .getSoortGegeven();
        if (gegevenInOnderzoek.getEntiteitOfVoorkomen() == entiteit && (individueelElementInOnderzoek || elementInGroepsOnderzoek)) {
            lo3Onderzoeken.add(onderzoekEntry.getValue());
        }
    }

    private boolean isLo3Onderzoek(final Onderzoek onderzoek) {
        final SoortActie soortActie =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(onderzoek.getOnderzoekHistorieSet()).getActieInhoud().getSoortActie();
        switch (soortActie) {
            case CONVERSIE_GBA:
            case CONVERSIE_GBA_LEGE_ONJUISTE_CATEGORIE:
            case CONVERSIE_GBA_MATERIELE_HISTORIE:
                return true;
            default:
                return false;
        }
    }

    private void vulOnderzoeken(final Collection<Onderzoek> brpOnderzoeken) {
        for (final Onderzoek onderzoek : brpOnderzoeken) {
            final FormeleHistorie actueleOnderzoekHistorie =
                    FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(onderzoek.getOnderzoekHistorieSet());

            if (actueleOnderzoekHistorie != null) {
                final String lo3GegevensInOnderzoek = bepaalLo3GegevensInOnderzoek(onderzoek.getOmschrijving());
                final Lo3Datum datumIngangOnderzoek = new BrpDatum(onderzoek.getDatumAanvang(), null).converteerNaarLo3Datum();

                final boolean isOnderzoekBeindigd = StatusOnderzoek.AFGESLOTEN.equals(onderzoek.getStatusOnderzoek());
                final Lo3Datum datumEindeOnderzoek = isOnderzoekBeindigd ? new BrpDatum(onderzoek.getDatumEinde(), null).converteerNaarLo3Datum() : null;

                final Lo3Onderzoek lo3Onderzoek = new Lo3Onderzoek(new Lo3Integer(lo3GegevensInOnderzoek, null), datumIngangOnderzoek, datumEindeOnderzoek);

                onderzoeken.put(onderzoek, lo3Onderzoek);
            }
        }
    }

    private String bepaalLo3GegevensInOnderzoek(final String onderzoekOmschrijving) {
        String resultaat = BRP_ONDERZOEK_GEGEVENS_IN_ONDERZOEK;

        if (onderzoekOmschrijving.startsWith(OnderzoekMapperImpl.LO3_ONDERZOEK_OMSCHRIJVING_PREFIX)) {
            resultaat = onderzoekOmschrijving.substring(OnderzoekMapperImpl.LO3_ONDERZOEK_OMSCHRIJVING_PREFIX.length());
        }

        return resultaat;
    }
}
