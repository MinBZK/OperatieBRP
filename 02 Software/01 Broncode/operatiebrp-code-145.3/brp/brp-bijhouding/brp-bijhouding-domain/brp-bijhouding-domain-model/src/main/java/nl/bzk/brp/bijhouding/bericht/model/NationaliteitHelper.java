/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.MaterieleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Nationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteitHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.util.common.DatumUtil;

/**
 * NationaliteitHelper
 */
public class NationaliteitHelper {
    private final BijhoudingPersoon bijhoudingsPersoon;
    private final List<PersoonNationaliteit> nationaliteiten;
    private final Map<String, Integer> aantalKeerDatNationaliteitVoorKomt;
    private final Map<String, Integer> aantalKeerDatNationaliteitBeeindigdWordt;

    /**
     * Organiseert alle nationaliteiten zowel in de database als in het bericht.
     * @param bijhoudingsPersoon persoon waarvoor de nationaliteiten georganiseerd moeten worden
     */
    NationaliteitHelper(final BijhoudingPersoon bijhoudingsPersoon) {
        this.bijhoudingsPersoon = bijhoudingsPersoon;
        nationaliteiten = new LinkedList<>();
        aantalKeerDatNationaliteitVoorKomt = new HashMap<>();
        aantalKeerDatNationaliteitBeeindigdWordt = new HashMap<>();

        verwerkNationaliteitenInDatabase();
        verwerkNationaliteitenInBericht();
    }

    private void verwerkNationaliteitenInDatabase() {
        for (final PersoonNationaliteit nationaliteit : bijhoudingsPersoon.getPersoonNationaliteitSet()) {
            final PersoonNationaliteit kopieNationaliteit =
                    new PersoonNationaliteit(new Persoon(SoortPersoon.PSEUDO_PERSOON), nationaliteit.getNationaliteit());
            for (final PersoonNationaliteitHistorie historie : nationaliteit.getPersoonNationaliteitHistorieSet()) {
                final PersoonNationaliteitHistorie kopieHistorie = new PersoonNationaliteitHistorie(kopieNationaliteit);
                kopieHistorie.setDatumAanvangGeldigheid(historie.getDatumAanvangGeldigheid());
                kopieHistorie.setDatumEindeGeldigheid(historie.getDatumEindeGeldigheid());
                kopieHistorie.setDatumTijdRegistratie(historie.getDatumTijdRegistratie());
                kopieHistorie.setDatumTijdVerval(historie.getDatumTijdVerval());
                kopieNationaliteit.addPersoonNationaliteitHistorie(kopieHistorie);
            }
            nationaliteiten.add(kopieNationaliteit);
            updateAantalKeerNationaliteitVoorKomt(kopieNationaliteit);
        }
    }

    private void verwerkNationaliteitenInBericht() {
        final List<NationaliteitElement> registratieNationaliteitElementen = new LinkedList<>();
        final List<NationaliteitElement> beeindigingNationaliteitElementen = new LinkedList<>();
        bijhoudingsPersoon.getPersoonElementen().stream().filter(PersoonElement::heeftNationaliteiten).forEach(element -> {
            final NationaliteitElement nationaliteitElement = element.getNationaliteit();
            if (nationaliteitElement.isBeeindiging()) {
                beeindigingNationaliteitElementen.add(nationaliteitElement);
            } else {
                registratieNationaliteitElementen.add(nationaliteitElement);
            }
        });

        // Nieuwe registraties toevoegen als nationaliteit
        for (final NationaliteitElement element : registratieNationaliteitElementen) {
            PersoonNationaliteit nationaliteit = zoekBestaandeNationaliteit(element);
            if (nationaliteit == null) {
                nationaliteit =
                        new PersoonNationaliteit(new Persoon(SoortPersoon.PSEUDO_PERSOON),
                                new Nationaliteit("dummy", element.getNationaliteitCode().getWaarde()));
                nationaliteiten.add(nationaliteit);
            }

            updateAantalKeerNationaliteitVoorKomt(nationaliteit);
            final PersoonNationaliteitHistorie historie = new PersoonNationaliteitHistorie(nationaliteit);
            historie.setDatumAanvangGeldigheid(element.getDatumAanvangGeldigheidRegistratieNationaliteitActie());
            nationaliteit.addPersoonNationaliteitHistorie(historie);
        }

        // Nationaliteiten beeindigen
        for (final NationaliteitElement element : beeindigingNationaliteitElementen) {
            final PersoonNationaliteit nationaliteit = zoekBestaandeNationaliteit(element);
            updateAantalKeerNationaliteitBeeindigdWordt(nationaliteit);
            final PersoonNationaliteitHistorie historie =
                    FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(nationaliteit.getPersoonNationaliteitHistorieSet());
            if (historie != null) {
                historie.setDatumEindeGeldigheid(element.getDatumEindeGeldigheidRegistratieNationaliteitActie());
            }
        }
    }

    private PersoonNationaliteit zoekBestaandeNationaliteit(final NationaliteitElement element) {
        final String code = element.getNationaliteitCodeAsString();
        return nationaliteiten.stream().filter(nationaliteit -> nationaliteit.getNationaliteit().getCode().equals(code)).findFirst().orElse(null);
    }

    private void updateAantalKeerNationaliteitVoorKomt(final PersoonNationaliteit nationaliteit) {
        final String nationaliteitCode = nationaliteit.getNationaliteit().getCode();
        aantalKeerDatNationaliteitVoorKomt.merge(nationaliteitCode, 1, (a, b) -> a + b);
    }

    private void updateAantalKeerNationaliteitBeeindigdWordt(final PersoonNationaliteit nationaliteit) {
        final String nationaliteitCode = nationaliteit.getNationaliteit().getCode();
        aantalKeerDatNationaliteitBeeindigdWordt.merge(nationaliteitCode, 1, (a, b) -> a + b);
    }

    /**
     * Heeft de persoon de Nederlandse nationaliteit op de opgegeven peildatum.
     * @param peildatum peildatum
     * @return true als de persoon de Nederlandse nationaliteit heeft op de opgegeven peildatum
     */
    boolean heeftNederlandseNationaliteit(final int peildatum) {
        return heeftNationaliteit(Nationaliteit.NEDERLANDSE, peildatum);
    }

    /**
     * heeft de dag voor peildatum nog wel de nederlandse nationaliteit maar op peildatum niet meer.
     * @param peildatum peildatum
     * @return true als deze is vervallen op peildatum
     */
    boolean verliestNederlandseNationaliteit(final int peildatum) {
        return heeftNederlandseNationaliteit(peildatum - 1) && !heeftNederlandseNationaliteit(peildatum);
    }

    /**
     * Heeft de persoon de opgegeven nationaliteit op de opgegeven peildatum.
     * @param nationaliteitCode de nationaliteit code
     * @param peildatum de peildatum
     * @return true als de persoon de opgegeven nationaliteit heeft op de opgegeven peildatum
     */
    boolean heeftNationaliteit(final String nationaliteitCode, final int peildatum) {
        final Optional<PersoonNationaliteit> nationaliteit = zoekNationaliteit(nationaliteitCode);
        return nationaliteit.filter(persoonNationaliteit ->
                MaterieleHistorie.getGeldigVoorkomenOpPeildatum(persoonNationaliteit.getPersoonNationaliteitHistorieSet(), peildatum) != null).isPresent();
    }

    boolean heeftNationaliteitAl(final String nationaliteitCode, final int peildatum) {
        final Optional<PersoonNationaliteit> nationaliteit = zoekNationaliteit(nationaliteitCode);
        if (nationaliteit.isPresent() && aantalKeerDatNationaliteitVoorKomt.get(nationaliteitCode) > 1) {
            int aantalKeerGeldig = 0;
            for (PersoonNationaliteitHistorie historie : FormeleHistorieZonderVerantwoording
                    .getNietVervallenVoorkomens(nationaliteit.get().getPersoonNationaliteitHistorieSet())) {
                if (DatumUtil.valtDatumBinnenPeriode(peildatum, historie.getDatumAanvangGeldigheid(), historie.getDatumEindeGeldigheid())) {
                    aantalKeerGeldig++;
                }
            }
            return aantalKeerGeldig > 1;
        }
        return false;
    }

    /**
     * Geeft aan of de persoon op de peildatum nog een geldige nationaliteit heeft.
     * @param peilDatum de peildatum
     * @return true als de persoon op de peildatum een geldige nationaliteit heeft, anders false.
     */
    boolean heeftGeldigeNationaliteit(final int peilDatum) {
        return nationaliteiten.stream()
                .filter(nationaliteit -> MaterieleHistorie.getGeldigVoorkomenOpPeildatum(nationaliteit.getPersoonNationaliteitHistorieSet(), peilDatum) != null)
                .count() > 0;
    }

    /**
     * Geeft aan of de nationaliteit meerdere keren beeindigd is/wordt.
     * @param element het nationaliteitElement waarmee de nationaliteit wordt beeindigd
     * @return true als de nationaliteit uit het {@link NationaliteitElement} meer dan 1x beeinding wordt.
     */
    boolean isNationaliteitMeerdereKerenBeeindigd(final NationaliteitElement element) {
        final Integer aantal = this.aantalKeerDatNationaliteitBeeindigdWordt.get(element.getNationaliteitCodeAsString());
        return aantal != null &&  aantal > 1;
    }

    private Optional<PersoonNationaliteit> zoekNationaliteit(final String nationaliteitCode) {
        return nationaliteiten.stream().filter(nationaliteit -> nationaliteit.getNationaliteit().getCode().equals(nationaliteitCode)).findFirst();
    }
}
