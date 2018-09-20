/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util;

import java.util.Arrays;
import java.util.Date;

import nl.bzk.brp.model.algemeen.attribuuttype.ber.SysteemNaamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReferentienummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.BerichtRootObject;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieBronBericht;
import nl.bzk.brp.model.bericht.kern.ActieCorrectieAdresBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieAdresBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieGeboorteBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieHuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieNaamgebruikBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieNationaliteitBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieOverlijdenBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.DocumentBericht;
import nl.bzk.brp.model.bericht.kern.DocumentStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.HandelingCorrectieAdresBericht;
import nl.bzk.brp.model.bericht.kern.HandelingGeboorteInNederlandBericht;
import nl.bzk.brp.model.bericht.kern.HandelingOverlijdenInBuitenlandBericht;
import nl.bzk.brp.model.bericht.kern.HandelingVerhuizingBinnengemeentelijkBericht;
import nl.bzk.brp.model.bericht.kern.HandelingVoltrekkingHuwelijkInNederlandBericht;


/**
 * Utility klasse voor het maken van subklasses van {@link nl.bzk.brp.model.bericht.kern.ActieBericht} en andere
 * bericht specifieke klasses ten behoeve van tests.
 */
public final class BerichtBuilder {

    private static final String VERKEERDE_ACTIE_MESSAGE = "Verkeerde type actie: ";

    /**
     * Utility klasses dienen niet geinstantieerd te worden en hebben daarom een private constructor.
     */
    private BerichtBuilder() {
    }

    public static ActieRegistratieGeboorteBericht bouwActieRegistratieGeboorte(final Integer datumAanvang,
            final Integer datumEinde, final Partij partij, final BerichtRootObject rootObject)
    {
        ActieRegistratieGeboorteBericht actie = new ActieRegistratieGeboorteBericht();
        vulActieAttributen(actie, datumAanvang, datumEinde, partij, rootObject, "id.act.geb");
        return actie;
    }

    public static ActieRegistratieNationaliteitBericht bouwActieRegistratieNationaliteit(final Integer datumAanvang,
            final Integer datumEinde, final Partij partij, final BerichtRootObject rootObject)
    {
        ActieRegistratieNationaliteitBericht actie = new ActieRegistratieNationaliteitBericht();
        vulActieAttributen(actie, datumAanvang, datumEinde, partij, rootObject, "id.act.nat");
        return actie;
    }

    public static ActieRegistratieAdresBericht bouwActieRegistratieAdres(final Integer datumAanvang,
            final Integer datumEinde, final Partij partij, final BerichtRootObject rootObject)
    {
        ActieRegistratieAdresBericht actie = new ActieRegistratieAdresBericht();
        vulActieAttributen(actie, datumAanvang, datumEinde, partij, rootObject, "id.act.verh");
        return actie;
    }

    public static ActieRegistratieOverlijdenBericht bouwActieRegistratieOverlijden(final Integer datumAanvang,
            final Integer datumEinde, final Partij partij, final BerichtRootObject rootObject)
    {
        ActieRegistratieOverlijdenBericht actie = new ActieRegistratieOverlijdenBericht();
        vulActieAttributen(actie, datumAanvang, datumEinde, partij, rootObject, "id.act.overl");
        return actie;
    }

    public static ActieRegistratieHuwelijkGeregistreerdPartnerschapBericht bouwActieRegistratieHuwelijk(
            final Integer datumAanvang, final Integer datumEinde, final Partij partij,
            final BerichtRootObject rootObject)
    {
        ActieRegistratieHuwelijkGeregistreerdPartnerschapBericht actie =
                new ActieRegistratieHuwelijkGeregistreerdPartnerschapBericht();
        vulActieAttributen(actie, datumAanvang, datumEinde, partij, rootObject, "id.act.huw");
        return actie;
    }

    private static void vulHandeling(final AdministratieveHandelingBericht hand, final Partij partij,
            final Date tijdstipRegistratie, final String id)
    {
        if (null != partij) {
            hand.setPartij(new PartijAttribuut(partij));
        }
        if (null != tijdstipRegistratie) {
            hand.setTijdstipRegistratie(new DatumTijdAttribuut(tijdstipRegistratie));
        }
        hand.setCommunicatieID(PrefixBuilder.getPrefix() + id);
    }

    public static HandelingGeboorteInNederlandBericht bouwHandelingGeboorte(final Partij partij,
            final Date tijdstipRegistratie, final ActieBericht... acties)
    {
        HandelingGeboorteInNederlandBericht hand = new HandelingGeboorteInNederlandBericht();
        if (acties != null) {
            for (int i = 0; i < acties.length; i++) {
                if (i == 0) {
                    if (!(acties[i] instanceof ActieRegistratieGeboorteBericht)) {
                        throw new IllegalArgumentException(VERKEERDE_ACTIE_MESSAGE + acties[i]);
                    }
                } else {
                    if (!(acties[i] instanceof ActieRegistratieNationaliteitBericht)) {
                        throw new IllegalArgumentException(VERKEERDE_ACTIE_MESSAGE + acties[i]);
                    }
                }
            }
            hand.setActies(Arrays.asList(acties));
        }
        vulHandeling(hand, partij, tijdstipRegistratie, "id.hand.geb");
        return hand;
    }

    public static HandelingVoltrekkingHuwelijkInNederlandBericht bouwHandelingHuwelijk(final Partij partij,
            final Date tijdstipRegistratie, final ActieBericht... acties)
    {
        HandelingVoltrekkingHuwelijkInNederlandBericht hand = new HandelingVoltrekkingHuwelijkInNederlandBericht();
        if (acties != null) {
            for (int i = 0; i < acties.length; i++) {
                if (i == 0) {
                    if (!(acties[i] instanceof ActieRegistratieHuwelijkGeregistreerdPartnerschapBericht)) {
                        throw new IllegalArgumentException(VERKEERDE_ACTIE_MESSAGE + acties[i]);
                    }
                } else {
                    if (acties[i] instanceof ActieRegistratieNaamgebruikBericht) {
                        throw new IllegalArgumentException(VERKEERDE_ACTIE_MESSAGE + acties[i]);
                    }
                }
            }
            hand.setActies(Arrays.asList(acties));
        }
        vulHandeling(hand, partij, tijdstipRegistratie, "id.hand.huw");
        return hand;
    }

    public static HandelingOverlijdenInBuitenlandBericht bouwHandelingOverlijden(final Partij partij,
            final Date tijdstipRegistratie, final ActieBericht... acties)
    {
        HandelingOverlijdenInBuitenlandBericht hand = new HandelingOverlijdenInBuitenlandBericht();
        if (acties != null) {
            if (!(acties[0] instanceof ActieRegistratieOverlijdenBericht)) {
                throw new IllegalArgumentException(VERKEERDE_ACTIE_MESSAGE + acties[0]);
            }
            if (acties.length > 1) {
                throw new IllegalArgumentException(VERKEERDE_ACTIE_MESSAGE + acties[1]);
            }
            hand.setActies(Arrays.asList(acties));
        }
        vulHandeling(hand, partij, tijdstipRegistratie, "id.hand.overl");
        return hand;
    }

    public static HandelingVerhuizingBinnengemeentelijkBericht bouwHandelingVerhuizing(final Partij partij,
            final Date tijdstipRegistratie, final ActieBericht... acties)
    {
        HandelingVerhuizingBinnengemeentelijkBericht hand = new HandelingVerhuizingBinnengemeentelijkBericht();
        if (acties != null) {
            if (!(acties[0] instanceof ActieRegistratieAdresBericht)) {
                throw new IllegalArgumentException(VERKEERDE_ACTIE_MESSAGE + acties[0]);
            }
            if (acties.length > 1) {
                throw new IllegalArgumentException(VERKEERDE_ACTIE_MESSAGE + acties[1]);
            }
            hand.setActies(Arrays.asList(acties));
        }
        vulHandeling(hand, partij, tijdstipRegistratie, "id.hand.verh");
        return hand;
    }

    public static HandelingCorrectieAdresBericht bouwHandelingCorrectieAdres(final Partij partij,
            final Date tijdstipRegistratie, final ActieBericht... acties)
    {
        HandelingCorrectieAdresBericht hand = new HandelingCorrectieAdresBericht();
        if (acties != null) {
            for (ActieBericht actie : acties) {
                if (!(actie instanceof ActieCorrectieAdresBericht)) {
                    throw new IllegalArgumentException(VERKEERDE_ACTIE_MESSAGE + actie);
                }
            }
            hand.setActies(Arrays.asList(acties));
        }
        vulHandeling(hand, partij, tijdstipRegistratie, "id.hand.adrcor");
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
            bron.getDocument().getStandaard().setPartij(new PartijAttribuut(partij));
        }
        bron.getDocument().setCommunicatieID(PrefixBuilder.getPrefix() + "id.bron");
        return bron;
    }

    public static BerichtStuurgegevensGroepBericht bouwStuurGegegevens(final String zendendeSysteem,
            final String zendendePartij, final String referentienummer, final String crossReferentienummer)
    {
        BerichtStuurgegevensGroepBericht stuurgegevens = new BerichtStuurgegevensGroepBericht();
        if (null != zendendeSysteem) {
            stuurgegevens.setZendendeSysteem(new SysteemNaamAttribuut(zendendeSysteem));
        }
        if (null != crossReferentienummer) {
            stuurgegevens.setCrossReferentienummer(new ReferentienummerAttribuut(crossReferentienummer));
        }
        if (null != zendendePartij) {
            // @TODO BMR28
            // TODO: zendendePartij gebruiken ipv default partij ministerie BZK
            stuurgegevens.setZendendePartij(StatischeObjecttypeBuilder.PARTIJ_MINISTERIE_BZK);
        }
        if (null != referentienummer) {
            stuurgegevens.setReferentienummer(new ReferentienummerAttribuut(referentienummer));
        }
        stuurgegevens.setCommunicatieID(PrefixBuilder.getPrefix() + "id.stuur");
        return stuurgegevens;
    }

    private static void vulActieAttributen(final ActieBericht actie, final Integer datumAanvang,
            final Integer datumEinde, final Partij partij, final BerichtRootObject rootObject, final String id)
    {
        if (null != datumAanvang) {
            actie.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(datumAanvang));
        }
        if (null != datumEinde) {
            actie.setDatumEindeGeldigheid(new DatumEvtDeelsOnbekendAttribuut(datumEinde));
        }

        if (null != partij) {
            actie.setPartij(new PartijAttribuut(partij));
        }
        if (null != rootObject) {
            actie.setRootObject(rootObject);
        }
        actie.setCommunicatieID(PrefixBuilder.getPrefix() + id);
    }

}
