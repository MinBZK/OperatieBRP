/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie;

import java.sql.Timestamp;
import java.util.IdentityHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Entiteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.GegevenInOnderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.GegevenInOnderzoekHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Onderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.OnderzoekHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RelatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.StatusOnderzoek;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAttribuutMetOnderzoek;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNaamgebruikGeslachtsnaamstam;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;

/**
 * Mapper om op algemene wijze een onderzoek op gegevens te vertalen naar BRP entiteiten.
 */
public final class OnderzoekMapperImpl implements OnderzoekMapper {

    /**
     * Prefix string die wordt gebruikt in de omschrijving van een onderzoek dat uit LO3 is geconverteerd.
     */
    public static final String LO3_ONDERZOEK_OMSCHRIJVING_PREFIX = "Conversie GBA: ";

    private final Map<Lo3Onderzoek, Onderzoek> onderzoekMap = new IdentityHashMap<>();
    private final Persoon persoon;
    private final Partij partij;

    /**
     * Constructor voor de onderzoek mapper waarbij de persoon wordt meegegeven waaraan het onderzoek gekoppeld gaat
     * worden.
     * @param persoon de persoon waaraan het onderzoek gekoppeld gaat worden
     * @param partij de partij
     */
    public OnderzoekMapperImpl(final Persoon persoon, final Partij partij) {
        this.persoon = persoon;
        this.partij = partij;
    }

    @Override
    public Set<Onderzoek> getOnderzoekSet() {
        return new LinkedHashSet<>(onderzoekMap.values());
    }

    @Override
    public void mapOnderzoek(final Entiteit deltaEntiteit, final BrpAttribuutMetOnderzoek attribuut, final Element soortGegeven) {
        if (soortGegeven != null && attribuut != null && attribuut.isInhoudelijkGevuld()) {
            mapOnderzoek(deltaEntiteit, soortGegeven, attribuut.getOnderzoek());
        }
    }

    private void mapOnderzoek(final Entiteit deltaEntiteit, final Element soortGegeven, final Lo3Onderzoek lo3Onderzoek) {
        if (lo3Onderzoek != null) {
            final Onderzoek onderzoek;
            if (!onderzoekMap.containsKey(lo3Onderzoek)) {
                onderzoek = maakOnderzoek(lo3Onderzoek);
                onderzoekMap.put(lo3Onderzoek, onderzoek);
            } else {
                onderzoek = onderzoekMap.get(lo3Onderzoek);
            }

            // Onderzoek op tsreg van RelatieHistorie hoeft bij FRB niet gedaan te worden.
            if (!isEntiteitEenRelatieHistorieEnSoortGegevenTijdstipRegistratie(deltaEntiteit, soortGegeven)) {
                final GegevenInOnderzoek gegevenInOnderzoek = new GegevenInOnderzoek(onderzoek, soortGegeven);
                final GegevenInOnderzoekHistorie
                        gegevenInOnderzoekHistorie =
                        new GegevenInOnderzoekHistorie(gegevenInOnderzoek, onderzoek.getOnderzoekHistorieSet().iterator().next().getActieInhoud());
                gegevenInOnderzoek.addGegevenInOnderzoekHistorie(gegevenInOnderzoekHistorie);
                gegevenInOnderzoek.setEntiteitOfVoorkomen(deltaEntiteit);

                if (!bevatGelijkGegeven(onderzoek.getGegevenInOnderzoekSet(), gegevenInOnderzoek)) {
                    onderzoek.addGegevenInOnderzoek(gegevenInOnderzoek);
                }
            }
        }
    }

    private boolean isEntiteitEenRelatieHistorieEnSoortGegevenTijdstipRegistratie(final Entiteit deltaEntiteit, final Element soortGegeven) {
        boolean result = false;
        if (deltaEntiteit instanceof RelatieHistorie) {
            final Relatie relatie = ((RelatieHistorie) deltaEntiteit).getRelatie();
            result = SoortRelatie.FAMILIERECHTELIJKE_BETREKKING.equals(relatie.getSoortRelatie()) && Element.RELATIE_TIJDSTIPREGISTRATIE.equals
                    (soortGegeven);
        }
        return result;
    }

    @Override
    public void mapOnderzoeken(final Entiteit entiteit, final BrpNaamgebruikGeslachtsnaamstam naamgebruikGeslachtsnaamstam, final Element soortGegeven) {
        if (naamgebruikGeslachtsnaamstam == null || naamgebruikGeslachtsnaamstam.getOnderzoeken() == null) {
            return;
        }
        for (final Lo3Onderzoek lo3Onderzoek : naamgebruikGeslachtsnaamstam.getOnderzoeken()) {
            mapOnderzoek(entiteit, soortGegeven, lo3Onderzoek);
        }
    }

    private boolean bevatGelijkGegeven(final Set<GegevenInOnderzoek> gegevenInOnderzoekSet, final GegevenInOnderzoek gegevenInOnderzoek) {
        for (final GegevenInOnderzoek gegevenInSet : gegevenInOnderzoekSet) {
            if (gegevenInSet.getEntiteitOfVoorkomen() == gegevenInOnderzoek.getEntiteitOfVoorkomen()
                    && gegevenInSet.getSoortGegeven() == gegevenInOnderzoek.getSoortGegeven()) {
                return true;
            }
        }

        return false;
    }

    private Onderzoek maakOnderzoek(final Lo3Onderzoek lo3Onderzoek) {
        final BRPActie actie = maakBrpActie();
        final Onderzoek onderzoek = new Onderzoek(partij, persoon);
        persoon.addOnderzoek(onderzoek);
        onderzoek.setVoortgekomenUitNietActueelVoorkomen(lo3Onderzoek.getLo3Herkomst() == null || !lo3Onderzoek.getLo3Herkomst().isLo3ActueelVoorkomen());

        final Integer datumAanvang = BrpDatum.fromLo3Datum(lo3Onderzoek.getDatumIngangOnderzoek()).getWaarde();
        final StatusOnderzoek statusOnderzoek;
        final Integer datumEinde;
        if (lo3Onderzoek.getDatumEindeOnderzoek() != null) {
            datumEinde = BrpDatum.fromLo3Datum(lo3Onderzoek.getDatumEindeOnderzoek()).getWaarde();
            statusOnderzoek = StatusOnderzoek.AFGESLOTEN;
        } else {
            datumEinde = null;
            statusOnderzoek = StatusOnderzoek.IN_UITVOERING;
        }

        final OnderzoekHistorie historie = new OnderzoekHistorie(datumAanvang, statusOnderzoek, onderzoek);
        historie.setOmschrijving(LO3_ONDERZOEK_OMSCHRIJVING_PREFIX + lo3Onderzoek.getAanduidingGegevensInOnderzoekCode());
        historie.setDatumTijdRegistratie(actie.getDatumTijdRegistratie());
        historie.setDatumEinde(datumEinde);
        historie.setActieInhoud(actie);

        onderzoek.addOnderzoekHistorie(historie);
        return onderzoek;
    }

    private BRPActie maakBrpActie() {
        final AdministratieveHandeling administratieveHandeling = getAdministratieveHandeling();
        final Timestamp datumtijdstempel =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoon.getPersoonInschrijvingHistorieSet()).getDatumtijdstempel();
        return new BRPActie(SoortActie.CONVERSIE_GBA, administratieveHandeling, administratieveHandeling.getPartij(), datumtijdstempel);
    }

    private AdministratieveHandeling getAdministratieveHandeling() {
        return FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoon.getPersoonAfgeleidAdministratiefHistorieSet())
                .getAdministratieveHandeling();
    }
}
