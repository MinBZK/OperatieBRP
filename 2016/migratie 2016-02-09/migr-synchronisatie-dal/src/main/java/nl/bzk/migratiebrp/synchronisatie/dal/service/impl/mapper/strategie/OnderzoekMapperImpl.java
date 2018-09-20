/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie;

import java.util.IdentityHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAttribuutMetOnderzoek;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.DeltaEntiteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Element;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FormeleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.GegevenInOnderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Onderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.OnderzoekAfgeleidAdministratiefHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.OnderzoekHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PartijOnderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PartijOnderzoekHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonOnderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonOnderzoekHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPartijOnderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoonOnderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.StatusOnderzoek;

/**
 * Mapper om op algemene wijze een onderzoek op gegevens te vertalen naar BRP entiteiten.
 */
public class OnderzoekMapperImpl implements OnderzoekMapper {

    /** Prefix string die wordt gebruikt in de omschrijving van een onderzoek dat uit LO3 is geconverteerd. */
    public static final String LO3_ONDERZOEK_OMSCHRIJVING_PREFIX = "Conversie GBA: ";

    private final Map<Lo3Onderzoek, Onderzoek> onderzoekMap = new IdentityHashMap<>();
    private final Persoon persoon;

    /**
     * Constructor voor de onderzoek mapper waarbij de persoon wordt meegegeven waaraan het onderzoek gekoppeld gaat
     * worden.
     * 
     * @param persoon
     *            de persoon waaraan het onderzoek gekoppeld gaat worden
     */
    public OnderzoekMapperImpl(final Persoon persoon) {
        this.persoon = persoon;
    }

    @Override
    public final Set<Onderzoek> getOnderzoekSet() {
        final Set<Onderzoek> onderzoekSet = new LinkedHashSet<>();

        for (final Onderzoek onderzoek : onderzoekMap.values()) {
            onderzoekSet.add(onderzoek);
        }
        return onderzoekSet;
    }

    @Override
    public final void mapOnderzoek(final DeltaEntiteit deltaEntiteit, final BrpAttribuutMetOnderzoek attribuut, final Element soortGegeven) {
        if (soortGegeven == null) {
            return;
        }

        final Lo3Onderzoek lo3Onderzoek = attribuut != null ? attribuut.getOnderzoek() : null;

        if (lo3Onderzoek != null) {
            final Onderzoek onderzoek;
            if (!onderzoekMap.containsKey(lo3Onderzoek)) {
                onderzoek = maakOnderzoek(lo3Onderzoek);
                onderzoekMap.put(lo3Onderzoek, onderzoek);
            } else {
                onderzoek = onderzoekMap.get(lo3Onderzoek);
            }

            final GegevenInOnderzoek gegevenInOnderzoek = new GegevenInOnderzoek(onderzoek, soortGegeven);
            gegevenInOnderzoek.setObjectOfVoorkomen(deltaEntiteit);

            if (!bevatGelijkGegeven(onderzoek.getGegevenInOnderzoekSet(), gegevenInOnderzoek)) {
                onderzoek.addGegevenInOnderzoek(gegevenInOnderzoek);
            }
        }
    }

    private boolean bevatGelijkGegeven(final Set<GegevenInOnderzoek> gegevenInOnderzoekSet, final GegevenInOnderzoek gegevenInOnderzoek) {
        for (final GegevenInOnderzoek gegevenInSet : gegevenInOnderzoekSet) {
            if (gegevenInSet.getObjectOfVoorkomen() == gegevenInOnderzoek.getObjectOfVoorkomen()
                && gegevenInSet.getSoortGegeven() == gegevenInOnderzoek.getSoortGegeven())
            {
                return true;
            }
        }

        return false;
    }

    private Onderzoek maakOnderzoek(final Lo3Onderzoek lo3Onderzoek) {
        final BRPActie actie = maakBrpActie();

        final Onderzoek onderzoek = new Onderzoek();
        onderzoek.setVoortgekomenUitNietActueelVoorkomen(!lo3Onderzoek.getLo3Herkomst().isLo3ActueelVoorkomen());
        onderzoek.setAdministratieveHandeling(persoon.getAdministratieveHandeling());
        onderzoek.setDatumAanvang(BrpDatum.fromLo3Datum(lo3Onderzoek.getDatumIngangOnderzoek()).getWaarde());
        onderzoek.setVerwachteAfhandelDatum(0);

        final String lo3GegevensCode = lo3Onderzoek.getAanduidingGegevensInOnderzoekCode();
        onderzoek.setOmschrijving(LO3_ONDERZOEK_OMSCHRIJVING_PREFIX + lo3GegevensCode);

        if (lo3Onderzoek.getDatumEindeOnderzoek() != null) {
            onderzoek.setDatumEinde(BrpDatum.fromLo3Datum(lo3Onderzoek.getDatumEindeOnderzoek()).getWaarde());
            onderzoek.setStatusOnderzoek(StatusOnderzoek.AFGESLOTEN);
        } else {
            onderzoek.setStatusOnderzoek(StatusOnderzoek.IN_UITVOERING);
        }

        voegOnderzoekHistorieToe(onderzoek, actie);
        voegAfgeleidAdministratiefHistorieToe(onderzoek, actie);

        // Koppel de persoon als direct betrokken persoon aan het onderzoek
        voegOnderzoekAanPersoonToe(onderzoek, actie);

        // Koppel de bijhoudingsgemeente als eigenaar van het onderzoek
        voegOnderzoekAanPartijToe(onderzoek, actie);
        return onderzoek;
    }

    private BRPActie maakBrpActie() {
        final AdministratieveHandeling administratieveHandeling = persoon.getAdministratieveHandeling();
        return new BRPActie(
            SoortActie.CONVERSIE_GBA,
            administratieveHandeling,
            administratieveHandeling.getPartij(),
            persoon.getDatumtijdstempel());
    }

    private void voegOnderzoekHistorieToe(final Onderzoek onderzoek, final BRPActie actie) {
        final OnderzoekHistorie historie =
                new OnderzoekHistorie(onderzoek.getDatumAanvang(), onderzoek.getStatusOnderzoek(), onderzoek.getVerwachteAfhandelDatum(), onderzoek);
        historie.setDatumEinde(onderzoek.getDatumEinde());
        historie.setOmschrijving(onderzoek.getOmschrijving());
        vulOnderzoekHistorie(historie, actie);

        onderzoek.addOnderzoekHistorie(historie);
    }

    private void vulOnderzoekHistorie(final FormeleHistorie historie, final BRPActie actie) {
        historie.setDatumTijdRegistratie(actie.getDatumTijdRegistratie());
        historie.setActieInhoud(actie);
    }

    private void voegAfgeleidAdministratiefHistorieToe(final Onderzoek onderzoek, final BRPActie actie) {
        final OnderzoekAfgeleidAdministratiefHistorie historie =
                new OnderzoekAfgeleidAdministratiefHistorie(onderzoek, onderzoek.getAdministratieveHandeling());
        vulOnderzoekHistorie(historie, actie);

        onderzoek.addOnderzoekAfgeleidAdministratiefHistorie(historie);
    }

    private void voegOnderzoekAanPersoonToe(final Onderzoek onderzoek, final BRPActie actie) {
        final PersoonOnderzoek persoonOnderzoek = new PersoonOnderzoek(persoon, onderzoek);
        persoonOnderzoek.setSoortPersoonOnderzoek(SoortPersoonOnderzoek.DIRECT);

        final PersoonOnderzoekHistorie historie = new PersoonOnderzoekHistorie(persoonOnderzoek, persoonOnderzoek.getSoortPersoonOnderzoek());
        vulOnderzoekHistorie(historie, actie);
        persoonOnderzoek.addPersoonOnderzoekHistorie(historie);

        onderzoek.addPersoonOnderzoek(persoonOnderzoek);
        persoon.addPersoonOnderzoek(persoonOnderzoek);
    }

    private void voegOnderzoekAanPartijToe(final Onderzoek onderzoek, final BRPActie actie) {
        final Partij partij = persoon.getAdministratieveHandeling().getPartij();
        final PartijOnderzoek partijOnderzoek = new PartijOnderzoek(partij, onderzoek);
        partijOnderzoek.setSoortPartijOnderzoek(SoortPartijOnderzoek.EIGENAAR);

        final PartijOnderzoekHistorie historie = new PartijOnderzoekHistorie(partijOnderzoek, partijOnderzoek.getSoortPartijOnderzoek());
        vulOnderzoekHistorie(historie, actie);
        partijOnderzoek.addPartijOnderzoekHistorie(historie);

        onderzoek.addPartijOnderzoek(partijOnderzoek);
    }
}
