/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.app.support;

import java.util.HashSet;
import java.util.Set;

import nl.bzk.brp.model.logisch.kern.PersoonAanschrijvingGroep;
import nl.bzk.brp.model.logisch.kern.PersoonAdresStandaardGroep;
import nl.bzk.brp.model.logisch.kern.PersoonBijhoudingsgemeenteGroep;
import nl.bzk.brp.model.logisch.kern.PersoonGeboorteGroep;
import nl.bzk.brp.model.logisch.kern.PersoonGeslachtsaanduidingGroep;
import nl.bzk.brp.model.logisch.kern.PersoonGeslachtsnaamcomponentStandaardGroep;
import nl.bzk.brp.model.logisch.kern.PersoonIdentificatienummersGroep;
import nl.bzk.brp.model.logisch.kern.PersoonIndicatieStandaardGroep;
import nl.bzk.brp.model.logisch.kern.PersoonOpschortingGroep;
import nl.bzk.brp.model.logisch.kern.PersoonOverlijdenGroep;
import nl.bzk.brp.model.logisch.kern.PersoonSamengesteldeNaamGroep;
import nl.bzk.brp.model.logisch.kern.PersoonAdres;
import nl.bzk.brp.model.logisch.kern.PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.logisch.kern.PersoonIndicatie;
import nl.bzk.brp.model.logisch.kern.PersoonNationaliteit;
import nl.bzk.brp.model.logisch.kern.PersoonVoornaam;
import nl.bzk.brp.model.logisch.kern.Relatie;
import nl.bzk.brp.model.operationeel.kern.BetrokkenheidModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Land;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Plaats;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Predikaat;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;

import static java.lang.String.format;

/**
 * Class om een persoonslijst van een entiteit te maken, output naar een String om zeker te zijn
 * dat het hele model gebruikt wordt.
 */
public class PersoonsLijst {

    private PersoonModel persoon;
    private static final String ENTER = "\n";

    /**
     * Constructor.
     * @param model het model waarvoor de lijst is
     */
    public PersoonsLijst(final PersoonModel model) {
        persoon = model;
    }

    @Override
    public String toString() {
        return this.print(false);
    }

    /**
     * Naar simpele string.
     * @return string representatie
     */
    public String toSimpleString() {
        return this.print(true);
    }

    /**
     * Print deze persoonslijst.
     * @param flat <code>true</code> als de betrokkenheden shallow geprint mogen worden, anders <code>false</code>
     * @return de string representatie
     */
    private String print(final boolean flat) {
        StringBuilder builder = new StringBuilder();

        Geslachtsaanduiding geslacht =
                persoon.getGeslachtsaanduiding() != null ? persoon.getGeslachtsaanduiding().getGeslachtsaanduiding() :
                        Geslachtsaanduiding.ONBEKEND;

        builder.append("== PERSOONSLIJST").append(ENTER);
        builder.append(identificatieNummersToString(persoon.getIdentificatienummers())).append(ENTER);
        builder.append(soortPersoonToString(persoon.getSoort())).append(ENTER);
        builder.append(geslachtsAanduidingToString(persoon.getGeslachtsaanduiding())).append(ENTER);
        builder.append(samengesteldeNaamToString(persoon.getSamengesteldeNaam(), geslacht)).append(ENTER);
        builder.append(aanschrijvingToString(persoon.getAanschrijving(), geslacht)).append(ENTER);

        builder.append("Indicaties: ").append(ENTER);
        for (PersoonIndicatie indicatie : persoon.getIndicaties()) {
            builder.append(indicatieToString(indicatie)).append(ENTER);
        }

        builder.append("Nationaliteiten: ").append(ENTER);
        for (PersoonNationaliteit nat : persoon.getNationaliteiten()) {
            builder.append(nationaliteitToString(nat)).append(ENTER);
        }

        builder.append("Voornamen: ");
        for (PersoonVoornaam naam : persoon.getVoornamen()) {
            builder.append(voornaamToString(naam)).append(", ");
        }
        builder.append(ENTER);

        builder.append("Geslachtsnamen: ");
        for (PersoonGeslachtsnaamcomponent naam : persoon.getGeslachtsnaamcomponenten()) {
            builder.append(geslachtsnaamToString(naam, persoon.getGeslachtsaanduiding().getGeslachtsaanduiding()))
                    .append(", ");
        }
        builder.append(ENTER);

        builder.append(geboorteToString(persoon.getGeboorte())).append(ENTER);
        builder.append(overlijdenToString(persoon.getOverlijden())).append(ENTER);

        builder.append("Adressen: ").append(ENTER);
        for (PersoonAdres adres : persoon.getAdressen()) {
            builder.append(adresToString(adres)).append(ENTER);
        }

        builder.append("Betrokkheden: ").append(ENTER);
        for (BetrokkenheidModel model : persoon.getBetrokkenheden()) {
            builder.append(betrokkenheidToString(model, flat)).append(ENTER);
        }

        return builder.toString();
    }

    // embedded
    private String soortPersoonToString(final SoortPersoon soortPersoon) {
        if (soortPersoon == null) {
            return "[soort]";
        }
        return soortPersoon.getNaam();
    }

    private String identificatieNummersToString(PersoonIdentificatienummersGroep identificatienummers) {
        if (identificatienummers == null) {
            return "[identificatienummers]";
        }

        String bsn = identificatienummers.getBurgerservicenummer() != null ?
                identificatienummers.getBurgerservicenummer().getWaarde().toString() : "[BSN]";
        String anr = identificatienummers.getAdministratienummer() != null ?
                identificatienummers.getAdministratienummer().getWaarde().toString() : "[ANR]";
        return format("%s (%s)", bsn, anr);
    }

    private String geslachtsAanduidingToString(PersoonGeslachtsaanduidingGroep geslachtsaanduiding) {
        if (geslachtsaanduiding == null || geslachtsaanduiding.getGeslachtsaanduiding() == null) {
            return "[geslachtsaanduiding]";
        }
        return geslachtsaanduiding.getGeslachtsaanduiding().getNaam();
    }

    private String samengesteldeNaamToString(PersoonSamengesteldeNaamGroep samengesteldeNaam,
                                             Geslachtsaanduiding geslacht)
    {
        if (samengesteldeNaam == null) {
            return "[samengesteldenaam]";
        }

        String voornaam = samengesteldeNaam.getVoornamen() != null ? samengesteldeNaam.getVoornamen().getWaarde() : "";
        String geslachtsnaam =
                samengesteldeNaam.getGeslachtsnaam() != null ? samengesteldeNaam.getGeslachtsnaam().getWaarde() : "";
        String voorvoegsel =
                samengesteldeNaam.getVoorvoegsel() != null ? samengesteldeNaam.getVoorvoegsel().getWaarde() : "";

        return format("%1$s %5$s %4$s, %3$s %2$s", adellijkeTitelToString(
                samengesteldeNaam.getAdellijkeTitel(), geslacht),
                      predikaatToString(samengesteldeNaam.getPredikaat(), geslacht),
                      voornaam,
                      geslachtsnaam,
                      voorvoegsel);
    }

    private String aanschrijvingToString(PersoonAanschrijvingGroep aanschrijving, Geslachtsaanduiding geslacht) {
        if (aanschrijving == null) {
            return "[aanschrijving]";
        }

        String voornaam = aanschrijving.getVoornamenAanschrijving() != null ? aanschrijving.getVoornamenAanschrijving().getWaarde() : "";
        String geslachtsnaam =
                aanschrijving.getGeslachtsnaamAanschrijving() != null ? aanschrijving.getGeslachtsnaamAanschrijving().getWaarde() : "";
        String voorvoegsel = aanschrijving.getVoorvoegselAanschrijving() != null ? aanschrijving.getVoorvoegselAanschrijving().getWaarde() : "";

        return format("Aanschrijven als: %1$s %5$s %4$s, %3$s %2$s", adellijkeTitelToString(
                aanschrijving.getAdellijkeTitelAanschrijving(), geslacht),
                      predikaatToString(aanschrijving.getPredikaatAanschrijving(), geslacht),
                      voornaam,
                      geslachtsnaam,
                      voorvoegsel);
    }

    private String geboorteToString(PersoonGeboorteGroep geboorte) {
        if (geboorte == null) {
            return "[geboorte]";
        }

        String datum =
                geboorte.getDatumGeboorte() != null ? geboorte.getDatumGeboorte().getWaarde().toString() : "[datum]";
        String omschr = geboorte.getOmschrijvingLocatieGeboorte() != null ?
                geboorte.getOmschrijvingLocatieGeboorte().getWaarde() : "[omschr]";

        return format("Geboren op %s te %s, %s (%s)", datum,
                      plaatsToString(geboorte.getWoonplaatsGeboorte()), landToString(geboorte.getLandGeboorte()),
                      omschr);
    }

    private String opschortingToString(PersoonOpschortingGroep opschorting) {
        return "";
    }

    private String bijhoudenGemeenteToString(PersoonBijhoudingsgemeenteGroep gemeente) {
        return "";
    }

    private String overlijdenToString(PersoonOverlijdenGroep overlijden) {
        if (overlijden == null) {
            return "[overlijden]";
        }

        String datum =
                overlijden.getDatumOverlijden() != null ? overlijden.getDatumOverlijden().getWaarde().toString() : "?";
        String omschr = overlijden.getOmschrijvingLocatieOverlijden() != null ?
                overlijden.getOmschrijvingLocatieOverlijden().getWaarde() : "";

        return format("Overleden op %s te %s,%s (%s)", datum,
                      plaatsToString(overlijden.getWoonplaatsOverlijden()),
                      landToString(overlijden.getLandOverlijden()),
                      omschr);
    }

    private String relatieToString(Relatie relatie) {
        if (relatie == null) {
            return "[relatie]";
        }

        String naam = relatie.getSoort() != null ? relatie.getSoort().getNaam() : "[naam]";

        return format("%s", naam);
    }

    // collections
    private String indicatieToString(PersoonIndicatie indicatie) {
        PersoonIndicatieStandaardGroep gegevens = indicatie.getStandaard();

        if (gegevens == null) {
            return "[indicatie]";
        }
        String waarde = gegevens.getWaarde() != null ? gegevens.getWaarde().name() : "[waarde]";

        return format("* %s", waarde);
    }

    private String nationaliteitToString(PersoonNationaliteit nationaliteit) {

        String naam =
                nationaliteit.getNationaliteit() != null ? nationaliteit.getNationaliteit().getNaam().getWaarde() : "";
        String reden = nationaliteit.getStandaard() != null &&
                nationaliteit.getStandaard().getRedenVerkrijging() != null ?
                nationaliteit.getStandaard().getRedenVerkrijging().getOmschrijving().getWaarde() : "";

        return format("* %s (%s)", naam, reden);
    }

    private String voornaamToString(PersoonVoornaam voornaam) {
        return format("%s", voornaam.getStandaard().getNaam().getWaarde());
    }

    private String geslachtsnaamToString(PersoonGeslachtsnaamcomponent geslachtsnaam, Geslachtsaanduiding geslacht) {
        if (geslachtsnaam == null || geslachtsnaam.getStandaard() == null) {
            return "[geslachtsnaam]";
        }

        final PersoonGeslachtsnaamcomponentStandaardGroep gegevens = geslachtsnaam.getStandaard();

        String naam = gegevens.getNaam() != null ? gegevens.getNaam().getWaarde() : "-";
        String voorvoegsel = gegevens.getVoorvoegsel() != null ? gegevens.getVoorvoegsel().getWaarde() : "";

        return format("%1$s %4$s %2$s %3$s", adellijkeTitelToString(gegevens.getAdellijkeTitel(), geslacht),
                      naam, predikaatToString(gegevens.getPredikaat(), geslacht),
                      voorvoegsel);
    }

    private String betrokkenheidToString(final BetrokkenheidModel betrokkenheid, Boolean flat) {
        if (betrokkenheid == null) {
            return "[betrokkenheid]";
        }

        StringBuilder builder = new StringBuilder();
        if (!flat) {
            Set<BetrokkenheidModel> betrokkenen = new HashSet<BetrokkenheidModel>();

            Set<BetrokkenheidModel> ouderBetrokkenheden = new HashSet<BetrokkenheidModel>();
            BetrokkenheidModel kindBetrokkenheid = null;
            BetrokkenheidModel partnerBetrokkenheid = null;
            for(BetrokkenheidModel betrokkenheidRelatie : betrokkenheid.getRelatie().getBetrokkenheden()){
                switch (betrokkenheidRelatie.getRol()) {
                    case KIND:
                        kindBetrokkenheid = betrokkenheidRelatie;
                        break;
                    case OUDER:
                        ouderBetrokkenheden.add(betrokkenheidRelatie);
                        break;
                    case PARTNER:
                        partnerBetrokkenheid = betrokkenheidRelatie;
                        break;
                    default:
                        break;
                }
            }

            switch (betrokkenheid.getRol()) {
                case KIND:
                    betrokkenen.addAll(ouderBetrokkenheden);
                    break;
                case OUDER:
                    betrokkenen.add(kindBetrokkenheid);
                    break;
                case PARTNER:
                    betrokkenen.add(partnerBetrokkenheid);
                    break;
                default:
                    break;
            }

            builder.append(" (").append(betrokkenheid.getRelatie().getBetrokkenheden().size()).append(")-> ");
            builder.append("{").append(ENTER);
            for (BetrokkenheidModel gerelateerde : betrokkenen) {
                builder.append("  ").append(betrokkenheidToString(gerelateerde, true)).append(gerelateerdeToString(
                        gerelateerde.getPersoon()));
            }
            builder.append("}");
        }

        return format("* %s, %s%s", betrokkenheid.getRol(), (!flat ? relatieToString(betrokkenheid.getRelatie()) : ""),
                      builder.toString());
    }

    private String gerelateerdeToString(final PersoonModel gerelateerde) {
        StringBuilder builder = new StringBuilder();

        Geslachtsaanduiding geslacht = gerelateerde.getGeslachtsaanduiding() != null ?
                gerelateerde.getGeslachtsaanduiding().getGeslachtsaanduiding() : Geslachtsaanduiding.ONBEKEND;

        builder.append(identificatieNummersToString(gerelateerde.getIdentificatienummers())).append(" [");
        builder.append(geslachtsAanduidingToString(gerelateerde.getGeslachtsaanduiding())).append("] ");
        builder.append(samengesteldeNaamToString(gerelateerde.getSamengesteldeNaam(), geslacht)).append(" - ");
        builder.append(geboorteToString(gerelateerde.getGeboorte())).append(ENTER);

        return builder.toString();
    }

    private String adresToString(PersoonAdres adres) {
        final PersoonAdresStandaardGroep gegevens = adres.getStandaard();

        String soort = gegevens.getSoort() != null ? gegevens.getSoort().getNaam() : "[soort]";
        String naamRuimte =
                gegevens.getNaamOpenbareRuimte() != null ? gegevens.getNaamOpenbareRuimte().getWaarde() : "?";
        String huisnummer = gegevens.getHuisnummer() != null ? gegevens.getHuisnummer().getWaarde().toString() : "";
        String huisletter = gegevens.getHuisletter() != null ? gegevens.getHuisletter().getWaarde() : "";
        String toevoeging =
                gegevens.getHuisnummertoevoeging() != null ? gegevens.getHuisnummertoevoeging().getWaarde() : "";
        String postcode = gegevens.getPostcode() != null ? gegevens.getPostcode().getWaarde() : "0000XX";
        String woonplaats = gegevens.getWoonplaats() != null ? plaatsToString(gegevens.getWoonplaats()) : "-";
        String datumAanvang = gegevens.getDatumAanvangAdreshouding() != null ?
                gegevens.getDatumAanvangAdreshouding().getWaarde().toString() : "?";


        return format("* %s: %s %s%s%s, %s %s (vanaf %s)",
                      soort,
                      naamRuimte,
                      huisnummer,
                      huisletter,
                      toevoeging,
                      postcode,
                      woonplaats,
                      datumAanvang);
    }


    // statisch
    private String adellijkeTitelToString(AdellijkeTitel titel, Geslachtsaanduiding geslacht) {
        if (titel == null) {
            return "[adelijketitel]";
        }

        if (geslacht.equals(Geslachtsaanduiding.VROUW)) {
            return titel.getNaamVrouwelijk().getWaarde();
        } else {
            return titel.getNaamMannelijk().getWaarde();
        }
    }

    private String predikaatToString(Predikaat predikaat, Geslachtsaanduiding geslacht) {
        if (predikaat == null) {
            return "[predikaat]";
        }

        if (geslacht.equals(Geslachtsaanduiding.VROUW)) {
            return predikaat.getNaamVrouwelijk().getWaarde();
        } else {
            return predikaat.getNaamMannelijk().getWaarde();
        }
    }

    private String landToString(Land land) {
        if (land == null) {
            return "[land]";
        }
        return land.getNaam().getWaarde();
    }

    private String partijToString(Partij partij) {
        if (partij == null) {
            return "[partij]";
        }
        return partij.getNaam().getWaarde();
    }

    private String plaatsToString(Plaats plaats) {
        if (plaats == null) {
            return "[plaats]";
        }
        return plaats.getNaam().getWaarde();
    }
}
