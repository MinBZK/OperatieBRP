/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie;

import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Element;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FormeleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.GegevenInOnderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.HistorieUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Onderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortAdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.StatusOnderzoek;

/**
 * Mapper om op algemene wijze BRP Onderzoek entiteiten te vertalen naar onderzoeken op individuele attributen in het
 * migratie model.
 */
public final class BrpOnderzoekMapperImpl implements BrpOnderzoekMapper {
    private static final Set<SoortAdministratieveHandeling> ONDERZOEK_UIT_LO3_AH = new HashSet<>();
    private static final String BRP_ONDERZOEK_GEGEVENS_IN_ONDERZOEK = "0";
    private final Map<Onderzoek, Lo3Onderzoek> onderzoeken = new IdentityHashMap<>();

    /**
     * Maak een BrpOnderzoekMapperImpl aan op basis van een lijst Onderzoek entiteiten.
     * 
     * @param onderzoeken
     *            de Onderzoek entiteiten
     */
    public BrpOnderzoekMapperImpl(final List<Onderzoek> onderzoeken) {
        ONDERZOEK_UIT_LO3_AH.add(SoortAdministratieveHandeling.GBA_INITIELE_VULLING);
        ONDERZOEK_UIT_LO3_AH.add(SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL);
        ONDERZOEK_UIT_LO3_AH.add(SoortAdministratieveHandeling.GBA_INFRASTRUCTURELE_WIJZIGING);
        ONDERZOEK_UIT_LO3_AH.add(SoortAdministratieveHandeling.GBA_BIJHOUDING_OVERIG);
        ONDERZOEK_UIT_LO3_AH.add(SoortAdministratieveHandeling.GBA_AFVOEREN_PL);
        ONDERZOEK_UIT_LO3_AH.add(SoortAdministratieveHandeling.GBA_A_NUMMER_WIJZIGING);

        vulOnderzoeken(onderzoeken);
    }

    @Override
    public Lo3Onderzoek bepaalOnderzoek(final Object entiteit, final Element element, final boolean elementBehoortBijGroepsOnderzoek) {
        if (element != null) {
            for (final Onderzoek onderzoek : onderzoeken.keySet()) {
                for (final GegevenInOnderzoek gegevenInOnderzoek : onderzoek.getGegevenInOnderzoekSet()) {
                    final boolean individueelElementInOnderzoek = element == gegevenInOnderzoek.getSoortGegeven();
                    final boolean elementInGroepsOnderzoek =
                            elementBehoortBijGroepsOnderzoek && element.getGroep() == gegevenInOnderzoek.getSoortGegeven();
                    if (gegevenInOnderzoek.getObjectOfVoorkomen() == entiteit && (individueelElementInOnderzoek || elementInGroepsOnderzoek)) {
                        return onderzoeken.get(onderzoek);
                    }
                }
            }
        }

        return null;
    }

    private void vulOnderzoeken(final List<Onderzoek> brpOnderzoeken) {
        for (final Onderzoek onderzoek : brpOnderzoeken) {
            final FormeleHistorie actueleOnderzoekHistorie = HistorieUtil.getActueelHistorieVoorkomen(onderzoek.getOnderzoekHistorieSet());
            if (actueleOnderzoekHistorie != null) {
                final SoortAdministratieveHandeling soortAdministratieveHandeling = onderzoek.getAdministratieveHandeling().getSoort();

                final String lo3GegevensInOnderzoek =
                        ONDERZOEK_UIT_LO3_AH.contains(soortAdministratieveHandeling) ? bepaalLo3GegevensInOnderzoek(onderzoek.getOmschrijving())
                                                                                    : BRP_ONDERZOEK_GEGEVENS_IN_ONDERZOEK;
                final Lo3Datum datumIngangOnderzoek = new BrpDatum(onderzoek.getDatumAanvang(), null).converteerNaarLo3Datum();

                final boolean isOnderzoekBeindigd = StatusOnderzoek.AFGESLOTEN.equals(onderzoek.getStatusOnderzoek());
                final Lo3Datum datumEindeOnderzoek = isOnderzoekBeindigd ? new BrpDatum(onderzoek.getDatumEinde(), null).converteerNaarLo3Datum() : null;

                final Lo3Onderzoek lo3Onderzoek =
                        new Lo3Onderzoek(new Lo3Integer(lo3GegevensInOnderzoek, null), datumIngangOnderzoek, datumEindeOnderzoek);

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
