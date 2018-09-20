/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.app.support;

import static java.lang.String.format;

import java.util.HashSet;
import java.util.Set;

import nl.bzk.copy.model.groep.logisch.PersoonAanschrijvingGroep;
import nl.bzk.copy.model.groep.logisch.PersoonAdresStandaardGroep;
import nl.bzk.copy.model.groep.logisch.PersoonBijhoudingsgemeenteGroep;
import nl.bzk.copy.model.groep.logisch.PersoonGeboorteGroep;
import nl.bzk.copy.model.groep.logisch.PersoonGeslachtsaanduidingGroep;
import nl.bzk.copy.model.groep.logisch.PersoonGeslachtsnaamcomponentStandaardGroep;
import nl.bzk.copy.model.groep.logisch.PersoonIdentificatienummersGroep;
import nl.bzk.copy.model.groep.logisch.PersoonIndicatieStandaardGroep;
import nl.bzk.copy.model.groep.logisch.PersoonOpschortingGroep;
import nl.bzk.copy.model.groep.logisch.PersoonOverlijdenGroep;
import nl.bzk.copy.model.groep.logisch.PersoonSamengesteldeNaamGroep;
import nl.bzk.copy.model.groep.logisch.RelatieStandaardGroep;
import nl.bzk.copy.model.objecttype.logisch.PersoonAdres;
import nl.bzk.copy.model.objecttype.logisch.PersoonGeslachtsnaamcomponent;
import nl.bzk.copy.model.objecttype.logisch.PersoonIndicatie;
import nl.bzk.copy.model.objecttype.logisch.PersoonNationaliteit;
import nl.bzk.copy.model.objecttype.logisch.PersoonVoornaam;
import nl.bzk.copy.model.objecttype.logisch.Relatie;
import nl.bzk.copy.model.objecttype.operationeel.BetrokkenheidModel;
import nl.bzk.copy.model.objecttype.operationeel.PersoonModel;
import nl.bzk.copy.model.objecttype.operationeel.statisch.AdellijkeTitel;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Geslachtsaanduiding;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Land;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Plaats;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Predikaat;
import nl.bzk.copy.model.objecttype.operationeel.statisch.SoortPersoon;

/**
 * Class om een persoonslijst van een entiteit te maken, output naar een String om zeker te zin dat het hele model gebruikt wordt.
 */
public class PersoonsLijst {

    private PersoonModel persoon;
    private final String ENTER = "\n";

    public PersoonsLijst(PersoonModel model) {
        persoon = model;
    }

    public String toString() {
              return this.print(false);
    }

    public String toSimpleString() {
        return  this.print(true);
    }

    private String print(Boolean flat) {
        StringBuilder builder = new StringBuilder();

        Geslachtsaanduiding geslacht = persoon.getGeslachtsaanduiding() != null ? persoon.getGeslachtsaanduiding().getGeslachtsaanduiding() : Geslachtsaanduiding.ONBEKEND;

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
        for (PersoonVoornaam naam : persoon.getPersoonVoornaam()) {
            builder.append(voornaamToString(naam)).append(", ");
        }
        builder.append(ENTER);

        builder.append("Geslachtsnamen: ");
        for (PersoonGeslachtsnaamcomponent naam : persoon.getGeslachtsnaamcomponenten()) {
            builder.append(geslachtsnaamToString(naam, persoon.getGeslachtsaanduiding().getGeslachtsaanduiding())).append(", ");
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
        if(soortPersoon == null) return "[soort]";
        return soortPersoon.getNaam();
    }

    private String identificatieNummersToString(PersoonIdentificatienummersGroep identificatienummers) {
        if(identificatienummers == null) return "[identificatienummers]";

        String bsn = identificatienummers.getBurgerservicenummer() != null ? identificatienummers.getBurgerservicenummer().getWaarde() : "[BSN]";
        String anr = identificatienummers.getAdministratienummer() != null ? identificatienummers.getAdministratienummer().getWaarde().toString() : "[ANR]";
        return format("%s (%s)", bsn, anr);
    }

    private String geslachtsAanduidingToString(PersoonGeslachtsaanduidingGroep geslachtsaanduiding) {
        if (geslachtsaanduiding == null || geslachtsaanduiding.getGeslachtsaanduiding() == null) return "[geslachtsaanduiding]";
        return geslachtsaanduiding.getGeslachtsaanduiding().getNaam();
    }

    private String samengesteldeNaamToString(PersoonSamengesteldeNaamGroep samengesteldeNaam, Geslachtsaanduiding geslacht) {
        if(samengesteldeNaam == null) return "[samengesteldenaam]";

        String voornaam = samengesteldeNaam.getVoornamen() != null ? samengesteldeNaam.getVoornamen().getWaarde() : "";
        String geslachtsnaam = samengesteldeNaam.getGeslachtsnaam() != null ? samengesteldeNaam.getGeslachtsnaam().getWaarde() : "";
        String voorvoegsel = samengesteldeNaam.getVoorvoegsel() != null ? samengesteldeNaam.getVoorvoegsel().getWaarde() : "";

        return format("%1$s %5$s %4$s, %3$s %2$s", adellijkeTitelToString(
                samengesteldeNaam.getAdellijkeTitel(), geslacht),
                      predikaatToString(samengesteldeNaam.getPredikaat(), geslacht),
                      voornaam,
                      geslachtsnaam,
                      voorvoegsel);
    }

    private String aanschrijvingToString(PersoonAanschrijvingGroep aanschrijving, Geslachtsaanduiding geslacht) {
        if (aanschrijving == null) return "[aanschrijving]";

        String voornaam = aanschrijving.getVoornamen() != null ? aanschrijving.getVoornamen().getWaarde() : "";
        String geslachtsnaam = aanschrijving.getGeslachtsnaam() != null ? aanschrijving.getGeslachtsnaam().getWaarde() : "";
        String voorvoegsel = aanschrijving.getVoorvoegsel() != null ? aanschrijving.getVoorvoegsel().getWaarde() : "";

        return format("Aanschrijven als: %1$s %5$s %4$s, %3$s %2$s", adellijkeTitelToString(
                aanschrijving.getAdellijkeTitel(), geslacht),
                      predikaatToString(aanschrijving.getPredikaat(), geslacht),
                      voornaam,
                      geslachtsnaam,
                      voorvoegsel);
    }

    private String geboorteToString(PersoonGeboorteGroep geboorte) {
        if (geboorte == null) return "[geboorte]";

        String datum = geboorte.getDatumGeboorte() != null ? geboorte.getDatumGeboorte().getWaarde().toString() : "[datum]";
        String omschr = geboorte.getOmschrijvingGeboorteLocatie() != null ? geboorte.getOmschrijvingGeboorteLocatie().getWaarde() : "[omschr]";

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
        if(overlijden == null) return "[overlijden]";

        String datum = overlijden.getDatumOverlijden() != null ? overlijden.getDatumOverlijden().getWaarde().toString() : "?";
        String omschr = overlijden.getOmschrijvingLocatieOverlijden() != null ? overlijden.getOmschrijvingLocatieOverlijden().getWaarde() : "";

        return format("Overleden op %s te %s,%s (%s)", datum,
                      plaatsToString(overlijden.getWoonplaatsOverlijden()),
                      landToString(overlijden.getLandOverlijden()),
                      omschr);
    }

    private String relatieToString(Relatie relatie) {
        if(relatie==null) return "[relatie]";

        RelatieStandaardGroep gegevens = relatie.getGegevens();

        String naam = relatie.getSoort() != null ? relatie.getSoort().getNaam() : "[naam]";
        String aanvang = gegevens != null && gegevens.getDatumAanvang() != null ? gegevens.getDatumAanvang().getWaarde().toString() : "?";

        return format("%s sinds %s", naam, aanvang);
    }

    // collections
    private String indicatieToString(PersoonIndicatie indicatie) {
        PersoonIndicatieStandaardGroep gegevens = indicatie.getGegevens();

        if (gegevens == null) return "[indicatie]";
        String soort =      gegevens.getSoort() != null ? gegevens.getSoort().getNaam() : "[soort]";
        String waarde = gegevens.getWaarde() != null ? gegevens.getWaarde().name() : "[waarde]";

        return format("* %s - %s", soort, waarde );
    }

    private String nationaliteitToString(PersoonNationaliteit nationaliteit) {

        String naam = nationaliteit.getNationaliteit() != null ? nationaliteit.getNationaliteit().getNaam().getWaarde() : "";
        String reden = nationaliteit.getGegevens() != null && nationaliteit.getGegevens().getRedenVerkregenNlNationaliteit() != null ? nationaliteit.getGegevens().getRedenVerkregenNlNationaliteit().getOmschrijving().getWaarde(): "";

        return format("* %s (%s)", naam, reden);
    }

    private String voornaamToString(PersoonVoornaam voornaam) {
        return format("%s", voornaam.getGegevens().getVoornaam().getWaarde());
    }

    private String geslachtsnaamToString(PersoonGeslachtsnaamcomponent geslachtsnaam, Geslachtsaanduiding geslacht) {
        if(geslachtsnaam==null || geslachtsnaam.getGegevens() == null) return "[geslachtsnaam]";

        final PersoonGeslachtsnaamcomponentStandaardGroep gegevens = geslachtsnaam.getGegevens();

        String naam = gegevens.getNaam() != null ? gegevens.getNaam().getWaarde() : "-";
        String voorvoegsel = gegevens.getVoorvoegsel() != null ? gegevens.getVoorvoegsel().getWaarde() : "";

        return format("%1$s %4$s %2$s %3$s", adellijkeTitelToString(gegevens.getAdellijkeTitel(), geslacht),
                      naam, predikaatToString(gegevens.getPredikaat(), geslacht),
                      voorvoegsel);
    }

    private String betrokkenheidToString(final BetrokkenheidModel betrokkenheid, Boolean flat) {
        if(betrokkenheid == null) return "[betrokkenheid]";

        StringBuilder builder = new StringBuilder();
        if (!flat) {
            Set<BetrokkenheidModel> betrokkenen = new HashSet<BetrokkenheidModel>();

            switch (betrokkenheid.getRol()) {
                case KIND:
                    betrokkenen.addAll(betrokkenheid.getRelatie().getOuderBetrokkenheden());
                    break;
                case OUDER:
                    betrokkenen.add(betrokkenheid.getRelatie().getKindBetrokkenheid());
                    break;
                case PARTNER:
                    betrokkenen.add(betrokkenheid.getRelatie().getPartnerBetrokkenheid());
                    break;
                default:
                    break;
            }

            builder.append(" (").append(betrokkenheid.getRelatie().getBetrokkenheden().size()).append(")-> ");
            builder.append("{").append(ENTER);
            for(BetrokkenheidModel gerelateerde : betrokkenen) {
                builder.append("  ").append(betrokkenheidToString(gerelateerde, true)).append(gerelateerdeToString(
                        gerelateerde.getBetrokkene()));
            }
            builder.append("}");
        }

        return format("* %s, %s%s", betrokkenheid.getRol(), (!flat ? relatieToString(betrokkenheid.getRelatie()): ""), builder.toString());
    }

    private String gerelateerdeToString(final PersoonModel gerelateerde) {
        StringBuilder builder = new StringBuilder();

        Geslachtsaanduiding geslacht = gerelateerde.getGeslachtsaanduiding() != null ? gerelateerde.getGeslachtsaanduiding().getGeslachtsaanduiding() : Geslachtsaanduiding.ONBEKEND;

        builder.append(identificatieNummersToString(gerelateerde.getIdentificatienummers())).append(" [");
        builder.append(geslachtsAanduidingToString(gerelateerde.getGeslachtsaanduiding())).append("] ");
        builder.append(samengesteldeNaamToString(gerelateerde.getSamengesteldeNaam(), geslacht)).append(" - ");
        builder.append(geboorteToString(gerelateerde.getGeboorte())).append(ENTER);

        return builder.toString();
    }

    private String adresToString(PersoonAdres adres) {
        final PersoonAdresStandaardGroep gegevens = adres.getGegevens();

        String soort = gegevens.getSoort() != null ? gegevens.getSoort().getNaam() : "[soort]";
        String naamRuimte = gegevens.getNaamOpenbareRuimte() != null ? gegevens.getNaamOpenbareRuimte().getWaarde() : "?";
        String huisnummer = gegevens.getHuisnummer() != null ? gegevens.getHuisnummer().getWaarde().toString() : "";
        String huisletter = gegevens.getHuisletter() != null ? gegevens.getHuisletter().getWaarde() : "";
        String toevoeging = gegevens.getHuisnummertoevoeging() != null ? gegevens.getHuisnummertoevoeging().getWaarde() : "";
        String postcode = gegevens.getPostcode() != null ? gegevens.getPostcode().getWaarde() : "0000XX";
        String woonplaats = gegevens.getWoonplaats() != null ? plaatsToString(gegevens.getWoonplaats()) : "-";
        String datumAanvang = gegevens.getDatumAanvangAdreshouding() != null ? gegevens.getDatumAanvangAdreshouding().getWaarde().toString() : "?";


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
        if(titel==null) return "[adelijketitel]";

        if(geslacht.equals(Geslachtsaanduiding.VROUW)) {
            return titel.getNaamVrouwelijk().getWaarde();
        } else {
            return titel.getNaamMannelijk().getWaarde();
        }
    }

    private String predikaatToString(Predikaat predikaat, Geslachtsaanduiding geslacht) {
        if(predikaat==null) return "[predikaat]";

        if(geslacht.equals(Geslachtsaanduiding.VROUW)) {
            return predikaat.getNaamVrouwelijk().getWaarde();
        } else {
            return predikaat.getNaamMannelijk().getWaarde();
        }
    }

    private String landToString(Land land) {
        if(land == null) return "[land]";
        return land.getNaam().getWaarde();
    }

    private String partijToString(Partij partij) {
        if(partij==null) return "[partij]";
        return partij.getNaam().getWaarde();
    }

    private String plaatsToString(Plaats plaats) {
        if(plaats==null)return "[plaats]";
        return plaats.getNaam().getWaarde();
    }
}
