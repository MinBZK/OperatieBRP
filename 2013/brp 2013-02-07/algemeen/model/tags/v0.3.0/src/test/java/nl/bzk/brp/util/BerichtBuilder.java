/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util;

import java.util.Arrays;
import java.util.Date;

import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Applicatienaam;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Organisatienaam;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Sleutelwaardetekst;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieBronBericht;
import nl.bzk.brp.model.bericht.kern.ActieCorrectieAdresBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieAanschrijvingBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieAdresBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieGeboorteBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieHuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieNationaliteitBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieOverlijdenBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.DocumentBericht;
import nl.bzk.brp.model.bericht.kern.DocumentStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.HandelingCorrectieAdresNederlandBericht;
import nl.bzk.brp.model.bericht.kern.HandelingInschrijvingDoorGeboorteBericht;
import nl.bzk.brp.model.bericht.kern.HandelingRegistratieBinnengemeentelijkeVerhuizingBericht;
import nl.bzk.brp.model.bericht.kern.HandelingRegistratieOverlijdenBuitenlandBericht;
import nl.bzk.brp.model.bericht.kern.HandelingSluitingHuwelijkNederlandBericht;

/**
 * Utility klasse voor het maken van subklasses van {@link nl.bzk.brp.model.bericht.kern.ActieBericht} en andere
 * bericht specifieke klasses ten behoeve van tests.
 */
public final class BerichtBuilder {

    /** Utility klasses dienen niet geinstantieerd te worden en hebben daarom een private constructor. */
    private BerichtBuilder() {
    }

    private static void vulActieAttributen(final ActieBericht actie, final Integer datumAanvang,
        final Integer datumEinde, final Partij partij, final RootObject rootObject, final String id)
    {
        if (null != datumAanvang) {
            actie.setDatumAanvangGeldigheid(new Datum(datumAanvang));
        }
        if (null != datumEinde) {
            actie.setDatumEindeGeldigheid(new Datum(datumEinde));
        }

        if (null != partij) {
            actie.setPartij(partij);
        }
        if (null != rootObject) {
            actie.setRootObjecten(Arrays.asList(rootObject));
        }
        actie.setCommunicatieID(PrefixBuilder.getPrefix() + id);
    }

    public static ActieRegistratieGeboorteBericht bouwActieRegistratieGeboorte(final Integer datumAanvang,
        final Integer datumEinde, final Partij partij, final RootObject rootObject)
    {
        String prefix = PrefixBuilder.getPrefix();
        ActieRegistratieGeboorteBericht actie = new ActieRegistratieGeboorteBericht();
        vulActieAttributen(actie, datumAanvang, datumEinde, partij, rootObject, "id.act.geb");
        return actie;
    }

    public static ActieRegistratieNationaliteitBericht bouwActieRegistratieNationaliteit(final Integer datumAanvang,
        final Integer datumEinde, final Partij partij, final RootObject rootObject)
    {
        ActieRegistratieNationaliteitBericht actie = new ActieRegistratieNationaliteitBericht();
        vulActieAttributen(actie, datumAanvang, datumEinde, partij, rootObject, "id.act.nat");
        return actie;
    }

    public static ActieRegistratieAdresBericht bouwActieRegistratieAdres(final Integer datumAanvang,
        final Integer datumEinde, final Partij partij, final RootObject rootObject)
    {
        ActieRegistratieAdresBericht actie = new ActieRegistratieAdresBericht();
        vulActieAttributen(actie, datumAanvang, datumEinde, partij, rootObject, "id.act.verh");
        return actie;
    }

    public static ActieRegistratieOverlijdenBericht bouwActieRegistratieOverlijden(final Integer datumAanvang,
        final Integer datumEinde, final Partij partij, final RootObject rootObject)
    {
        ActieRegistratieOverlijdenBericht actie = new ActieRegistratieOverlijdenBericht();
        vulActieAttributen(actie, datumAanvang, datumEinde, partij, rootObject, "id.act.overl");
        return actie;
    }

    public static ActieRegistratieHuwelijkBericht bouwActieRegistratieHuwelijk(final Integer datumAanvang,
        final Integer datumEinde, final Partij partij, final RootObject rootObject)
    {
        ActieRegistratieHuwelijkBericht actie = new ActieRegistratieHuwelijkBericht();
        vulActieAttributen(actie, datumAanvang, datumEinde, partij, rootObject, "id.act.huw");
        return actie;
    }

    private static void vulHandeling(final AdministratieveHandelingBericht hand, final Partij partij,
        final Date tijdstipOntlening, final Date tijdstipRegistratie, final String id)
    {
        if (null != partij) {
            hand.setPartij(partij);
        }
        if (null != tijdstipOntlening) {
            hand.setTijdstipOntlening(new DatumTijd(tijdstipOntlening));
        }
        if (null != tijdstipRegistratie) {
            hand.setTijdstipRegistratie(new DatumTijd(tijdstipRegistratie));
        }
        hand.setCommunicatieID(PrefixBuilder.getPrefix() + id);
    }

    public static HandelingInschrijvingDoorGeboorteBericht bouwHandelingGeboorte(final Partij partij,
        final Date tijdstipOntlening, final Date tijdstipRegistratie, final ActieBericht... acties)
    {
        HandelingInschrijvingDoorGeboorteBericht hand = new HandelingInschrijvingDoorGeboorteBericht();
        if (acties != null) {
            for (int i = 0; i < acties.length; i++) {
                if (i == 0) {
                    if (!(acties[i] instanceof ActieRegistratieGeboorteBericht)) {
                        throw new RuntimeException("Verkeerde type actie: " + acties[i]);
                    }
                } else {
                    if (!(acties[i] instanceof ActieRegistratieNationaliteitBericht)) {
                        throw new RuntimeException("Verkeerde type actie: " + acties[i]);
                    }
                }
            }
            hand.setActies(Arrays.asList(acties));
        }
        vulHandeling(hand, partij, tijdstipOntlening, tijdstipRegistratie, "id.hand.geb");
        return hand;
    }

    public static HandelingSluitingHuwelijkNederlandBericht bouwHandelingHuwelijk(
        final Partij partij, final Date tijdstipOntlening, final Date tijdstipRegistratie,
        final ActieBericht... acties)
    {
        HandelingSluitingHuwelijkNederlandBericht hand = new HandelingSluitingHuwelijkNederlandBericht();
        if (acties != null) {
            for (int i = 0; i < acties.length; i++) {
                if (i == 0) {
                    if (!(acties[i] instanceof ActieRegistratieHuwelijkBericht)) {
                        throw new RuntimeException("Verkeerde type actie: " + acties[i]);
                    }
                } else {
                    if (acties[i] instanceof ActieRegistratieAanschrijvingBericht) {
                        throw new RuntimeException("Verkeerde type actie: " + acties[i]);
                    }
                }
            }
            hand.setActies(Arrays.asList(acties));
        }
        vulHandeling(hand, partij, tijdstipOntlening, tijdstipRegistratie, "id.hand.huw");
        return hand;
    }


    public static HandelingRegistratieOverlijdenBuitenlandBericht bouwHandelingOverlijden(
        final Partij partij, final Date tijdstipOntlening, final Date tijdstipRegistratie,
        final ActieBericht... acties)
    {
        HandelingRegistratieOverlijdenBuitenlandBericht hand = new HandelingRegistratieOverlijdenBuitenlandBericht();
        if (acties != null) {
            if (!(acties[0] instanceof ActieRegistratieOverlijdenBericht)) {
                throw new RuntimeException("Verkeerde type actie: " + acties[0]);
            }
            if (acties.length > 1) {
                throw new RuntimeException("Verkeerde type actie: " + acties[1]);
            }
            hand.setActies(Arrays.asList(acties));
        }
        vulHandeling(hand, partij, tijdstipOntlening, tijdstipRegistratie, "id.hand.overl");
        return hand;
    }

    public static HandelingRegistratieBinnengemeentelijkeVerhuizingBericht bouwHandelingVerhuizing(
        final Partij partij, final Date tijdstipOntlening, final Date tijdstipRegistratie,
        final ActieBericht... acties)
    {
        HandelingRegistratieBinnengemeentelijkeVerhuizingBericht hand =
            new HandelingRegistratieBinnengemeentelijkeVerhuizingBericht();
        if (acties != null) {
            if (!(acties[0] instanceof ActieRegistratieAdresBericht)) {
                throw new RuntimeException("Verkeerde type actie: " + acties[0]);
            }
            if (acties.length > 1) {
                throw new RuntimeException("Verkeerde type actie: " + acties[1]);
            }
            hand.setActies(Arrays.asList(acties));
        }
        vulHandeling(hand, partij, tijdstipOntlening, tijdstipRegistratie, "id.hand.verh");
        return hand;
    }

    public static HandelingCorrectieAdresNederlandBericht bouwHandelingCorrectieAdres(
        final Partij partij, final Date tijdstipOntlening, final Date tijdstipRegistratie,
        final ActieBericht... acties)
    {
        HandelingCorrectieAdresNederlandBericht hand = new HandelingCorrectieAdresNederlandBericht();
        if (acties != null) {
            for (int i = 0; i < acties.length; i++) {
                if (!(acties[i] instanceof ActieCorrectieAdresBericht)) {
                    throw new RuntimeException("Verkeerde type actie: " + acties[i]);
                }
            }
            hand.setActies(Arrays.asList(acties));
        }
        vulHandeling(hand, partij, tijdstipOntlening, tijdstipRegistratie, "id.hand.adrcor");
        return hand;
    }

    public static ActieBronBericht bouwActieBron(final String soortNaam, final String partijCode, final Partij partij) {
        ActieBronBericht bron = new ActieBronBericht();
        bron.setDocument(new DocumentBericht());
        bron.getDocument().setSoortNaam(soortNaam);
        bron.getDocument().setStandaard(new DocumentStandaardGroepBericht());
        if (null != partijCode) {
            bron.getDocument().getStandaard().setPartijCode(partijCode);
        }
        if (null != partij) {
            bron.getDocument().getStandaard().setPartij(partij);
        }
        bron.setCommunicatieID(PrefixBuilder.getPrefix() + "id.bron");
        return bron;
    }

    public static BerichtStuurgegevensGroepBericht bouwStuurGegegevens(final String applicatie,
        final String organisatie, final String referentienummer, final String crossReferentienummer,
        final String functie)
    {
        BerichtStuurgegevensGroepBericht stuurgegevens = new BerichtStuurgegevensGroepBericht();
        if (null != applicatie) {
            stuurgegevens.setApplicatie(new Applicatienaam(applicatie));
        }
        if (null != crossReferentienummer) {
            stuurgegevens.setCrossReferentienummer(new Sleutelwaardetekst(crossReferentienummer));
        }
        if (null != functie) {
            stuurgegevens.setFunctie(functie);
        }
        if (null != organisatie) {
            stuurgegevens.setOrganisatie(new Organisatienaam(organisatie));
        }
        if (null != referentienummer) {
            stuurgegevens.setReferentienummer(new Sleutelwaardetekst(referentienummer));
        }
        stuurgegevens.setCommunicatieID(PrefixBuilder.getPrefix() + "id.stuur");
        return stuurgegevens;
    }

}
