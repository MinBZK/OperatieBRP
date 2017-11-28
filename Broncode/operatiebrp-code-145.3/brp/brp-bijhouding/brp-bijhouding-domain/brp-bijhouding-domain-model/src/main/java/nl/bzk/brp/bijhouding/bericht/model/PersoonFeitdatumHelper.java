/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.MaterieleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdresHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonDeelnameEuVerkiezingenHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeboorteHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonInschrijvingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonOverlijdenHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonReisdocumentHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerblijfsrechtHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RelatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;

/**
 * Deze helper class biedt functionaliteit voor het bepalen van alle feitdatums van een {@link BijhoudingPersoon}.
 */
public final class PersoonFeitdatumHelper {

    private final BijhoudingPersoon persoon;

    /**
     * Maakt een nieuw PersoonFeitdatumHelper object.
     * @param persoon de persoon waarvoor de feitdatums bepaald moeten worden
     */
    public PersoonFeitdatumHelper(final BijhoudingPersoon persoon) {
        this.persoon = persoon;
    }

    /**
     * Geeft de feitdatum van deze persoon terug die komt na of op het moment van de gegeven actie element.
     * @param actieElement het actie element
     * @return de feitdatum die komt na of op het moment van de gegeven actie element
     */
    @Bedrijfsregel(Regel.R2495)
    public Feitdatum getFeitDatumDieKomtNaOfGelijkMetDitActieElement(final ActieElement actieElement) {
        for (final Feitdatum feitdatum : bepaalFeitdatums()) {
            if (feitdatum.komtNaOfGelijkMetActieElement(actieElement)) {
                return feitdatum;
            }
        }
        return null;
    }

    private List<Feitdatum> bepaalFeitdatums() {
        final List<Feitdatum> results = new ArrayList<>();
        bepaalFeitdatumsHgpRelaties(results);
        bepaalFeitdatumsReisDocumenten(results);
        bepaalFeitdatumDeelnameEUVerkiezing(results);
        bepaalFeitdatumPersoonVerblijfsrecht(results);
        bepaalFeitdatumPersoonAdres(results);
        bepaalFeitdatumOverlijden(results);
        bepaalFeitdatumInschrijving(results);
        bepaalFeitdatumGeboorte(results);
        bepaalFeitdatumsGerelateerden(results);
        bepaalFeitdatumsMaterieleGroepen(results);
        return results;
    }

    private void bepaalFeitdatumsMaterieleGroepen(final List<Feitdatum> feitdatums) {
        for (final MaterieleHistorie historie : persoon.getNietVervallenMaterieleGroepen()) {
            voegFeitdatumToeAanLijst("persoon." + historie.getClass().getSimpleName() + ".datumAanvangGeldigheid", historie.getDatumAanvangGeldigheid(),
                    feitdatums, false);
            voegFeitdatumToeAanLijst("persoon." + historie.getClass().getSimpleName() + ".datumEindeGeldigheid", historie.getDatumEindeGeldigheid(),
                    feitdatums, true);
        }
    }

    private void bepaalFeitdatumsHgpRelaties(final List<Feitdatum> feitdatums) {
        for (final Relatie relatie : persoon.getRelaties(SoortBetrokkenheid.PARTNER)) {
            final RelatieHistorie historie = getActueelHistorieVoorkomen(relatie.getRelatieHistorieSet());
            if (historie != null) {
                voegFeitdatumToeAanLijst("hgp.datumAanvang", historie.getDatumAanvang(), feitdatums, false);
                voegFeitdatumToeAanLijst("hgp.datumEinde", historie.getDatumEinde(), feitdatums, true);
            }
        }
    }

    private void bepaalFeitdatumsReisDocumenten(final List<Feitdatum> feitdatums) {
        for (final PersoonReisdocument reisdocument : persoon.getPersoonReisdocumentSet()) {
            final PersoonReisdocumentHistorie historie = getActueelHistorieVoorkomen(reisdocument.getPersoonReisdocumentHistorieSet());
            if (historie != null) {
                voegFeitdatumToeAanLijst("reisdocument.datumIngangDocument", historie.getDatumIngangDocument(), feitdatums, false);
                voegFeitdatumToeAanLijst("reisdocument.datumUitgifte", historie.getDatumUitgifte(), feitdatums, false);
                voegFeitdatumToeAanLijst("reisdocument.datumInhoudingOfVermissing", historie.getDatumInhoudingOfVermissing(), feitdatums, false);
            }
        }
    }

    private void bepaalFeitdatumDeelnameEUVerkiezing(final List<Feitdatum> feitdatums) {
        final PersoonDeelnameEuVerkiezingenHistorie historie = getActueelHistorieVoorkomen(persoon.getPersoonDeelnameEuVerkiezingenHistorieSet());
        if (historie != null) {
            voegFeitdatumToeAanLijst("persoon.datumAanleidingAanpassingDeelnameEuVerkiezingen", historie.getDatumAanleidingAanpassingDeelnameEuVerkiezingen(),
                    feitdatums, false);
        }
    }

    private void bepaalFeitdatumPersoonVerblijfsrecht(final List<Feitdatum> feitdatums) {
        final PersoonVerblijfsrechtHistorie historie = getActueelHistorieVoorkomen(persoon.getPersoonVerblijfsrechtHistorieSet());
        if (historie != null) {
            voegFeitdatumToeAanLijst("persoon.datumMededelingVerblijfsrecht", historie.getDatumMededelingVerblijfsrecht(), feitdatums, false);
        }
    }

    private void bepaalFeitdatumPersoonAdres(final List<Feitdatum> feitdatums) {
        if (!persoon.getPersoonAdresSet().isEmpty()) {
            final PersoonAdresHistorie historie = getActueelHistorieVoorkomen(persoon.getPersoonAdresSet().iterator().next().getPersoonAdresHistorieSet());
            if (historie != null) {
                voegFeitdatumToeAanLijst("persoonAdres.datumAanvangAdreshouding", historie.getDatumAanvangAdreshouding(), feitdatums, false);
            }
        }
    }

    private void bepaalFeitdatumOverlijden(final List<Feitdatum> feitdatums) {
        final PersoonOverlijdenHistorie historie = getActueelHistorieVoorkomen(persoon.getPersoonOverlijdenHistorieSet());
        if (historie != null) {
            voegFeitdatumToeAanLijst("persoon.datumOverlijden", historie.getDatumOverlijden(), feitdatums, false);
        }
    }

    private void bepaalFeitdatumInschrijving(final List<Feitdatum> feitdatums) {
        final PersoonInschrijvingHistorie historie = getActueelHistorieVoorkomen(persoon.getPersoonInschrijvingHistorieSet());
        if (historie != null) {
            voegFeitdatumToeAanLijst("persoon.datumInschrijving", historie.getDatumInschrijving(), feitdatums, false);
        }
    }

    private void bepaalFeitdatumGeboorte(final List<Feitdatum> feitdatums) {
        bepaalFeitdatumGeboorte(persoon, feitdatums);
    }

    private static void bepaalFeitdatumGeboorte(final Persoon persoonOfGerelateerde, final List<Feitdatum> feitdatums) {
        final PersoonGeboorteHistorie historie = getActueelHistorieVoorkomen(persoonOfGerelateerde.getPersoonGeboorteHistorieSet());
        if (historie != null) {
            voegFeitdatumToeAanLijst("persoon.datumGeboorte", historie.getDatumGeboorte(), feitdatums, false);
        }
    }

    private void bepaalFeitdatumsGerelateerden(final List<Feitdatum> feitdatums) {
        for (final Betrokkenheid ikAlsPartnerBetrokkenheid : persoon.getActueleBetrokkenheidSet(SoortBetrokkenheid.PARTNER)) {
            final Betrokkenheid partnerBetrokkenheid = ikAlsPartnerBetrokkenheid.getRelatie().getAndereBetrokkenheid(ikAlsPartnerBetrokkenheid.getPersoon());
            bepaalFeitdatumsGerelateerde("partner", feitdatums, partnerBetrokkenheid.getPersoon(), false);
        }
        for (final Betrokkenheid ouderBetrokkenheid : persoon.getActueleOuders()) {
            bepaalFeitdatumsGerelateerde("ouder", feitdatums, ouderBetrokkenheid.getPersoon(), false);
        }
        for (final Betrokkenheid kindBetrokkenheid : persoon.getActueleKinderen()) {
            bepaalFeitdatumsGerelateerde("kind", feitdatums, kindBetrokkenheid.getPersoon(), true);
        }
    }

    private static void bepaalFeitdatumsGerelateerde(final String naam, final List<Feitdatum> feitdatums, final Persoon gerelateerdePersoon,
                                                     final boolean isKind) {
        if(gerelateerdePersoon!=null) {
            bepaalFeitdatumsMaterieleHistorie(naam + ".identificatienummers", gerelateerdePersoon.getPersoonIDHistorieSet(), feitdatums);
            bepaalFeitdatumGeboorte(gerelateerdePersoon, feitdatums);
            bepaalFeitdatumsMaterieleHistorie(naam + ".samengesteldeNaam", gerelateerdePersoon.getPersoonSamengesteldeNaamHistorieSet(), feitdatums);
            if (!isKind) {
                bepaalFeitdatumsMaterieleHistorie(naam + ".geslachtsaanduiding", gerelateerdePersoon.getPersoonGeslachtsaanduidingHistorieSet(), feitdatums);
            }
        }
    }

    private static <T extends MaterieleHistorie> void bepaalFeitdatumsMaterieleHistorie(final String naam, final Set<T> voorkomens,
                                                                                        final List<Feitdatum> feitdatums) {
        for (final T voorkomen : voorkomens) {
            if (!voorkomen.isVervallen()) {
                voegFeitdatumToeAanLijst(naam + ".datumAanvangGeldigheid", voorkomen.getDatumAanvangGeldigheid(), feitdatums, false);
                voegFeitdatumToeAanLijst(naam + ".datumEindeGeldigheid", voorkomen.getDatumEindeGeldigheid(), feitdatums, true);
            }
        }
    }

    private static void voegFeitdatumToeAanLijst(final String naam, final Integer feitdatum, final List<Feitdatum> feitdatums, boolean isDatumEinde) {
        if (feitdatum != null) {
            if (isDatumEinde) {
                feitdatums.add(new Feitdatum(naam, feitdatum - 1));
            } else {
                feitdatums.add(new Feitdatum(naam, feitdatum));
            }
        }
    }

    private static <T extends FormeleHistorieZonderVerantwoording> T getActueelHistorieVoorkomen(final Set<T> historieSet) {
        return FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(historieSet);
    }

    /**
     * Een feitdatum is een datum op de persoonlijst van een gegeven op die persoonlijst. Een persoonslijst bevat meerdere feitdatums.
     */
    public static final class Feitdatum {

        private final String naam;
        private final int feitdatumWaarde;

        private Feitdatum(final String naam, final int feitdatumWaarde) {
            this.naam = naam;
            this.feitdatumWaarde = feitdatumWaarde;
        }

        private boolean komtNaOfGelijkMetActieElement(final ActieElement actieElement) {
            final int datumAanvangGeldigheid = actieElement.getDatumAanvangGeldigheid() == null ? 0 : actieElement.getDatumAanvangGeldigheid().getWaarde();
            final int peilDatum = actieElement.getPeilDatum() == null ? 0 : actieElement.getPeilDatum().getWaarde();

            return komtNaOfGelijkMet(datumAanvangGeldigheid) || komtNaOfGelijkMet(peilDatum);
        }

        private boolean komtNaOfGelijkMet(final int datum) {
            return datum > 0 && feitdatumWaarde > 0 && datum <= feitdatumWaarde;
        }

        /**
         * De naam van de feitdatum.
         * @return naam van de feitdatum
         */
        public String getNaam() {
            return naam;
        }
    }
}
