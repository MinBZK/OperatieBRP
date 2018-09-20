/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.util;

import java.util.ArrayList;

import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieConversieGBABericht;
import nl.bzk.brp.model.bericht.kern.ActieCorrectieAdresBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieAanschrijvingBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieAdresBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieGeboorteBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieHuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieNationaliteitBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieOverlijdenBericht;

/** Helper class voor het bouwen en instantieren van een {@link ActieBericht} instantie. */
public final class ActieBerichtBuilder {

    private final ActieBericht actie;

    /**
     * Helper class kan niet zo maar geinitialiseerd worden, hiervoor moet de statische methode
     * {@link #bouwNieuweActie(SoortActie)} aangeroepen worden.
     *
     * @param soortActie de soort van de actie.
     */
    private ActieBerichtBuilder(final SoortActie soortActie) {
        switch(soortActie) {
            case REGISTRATIE_GEBOORTE:
                actie = new ActieRegistratieGeboorteBericht();
                break;
            case REGISTRATIE_HUWELIJK:
                actie = new ActieRegistratieHuwelijkBericht();
                break;
            case REGISTRATIE_AANSCHRIJVING:
                actie = new ActieRegistratieAanschrijvingBericht();
                break;
            case CORRECTIE_ADRES:
                actie = new ActieCorrectieAdresBericht();
                break;
            case REGISTRATIE_NATIONALITEIT:
                actie = new ActieRegistratieNationaliteitBericht();
                break;
            case REGISTRATIE_OVERLIJDEN:
                actie = new ActieRegistratieOverlijdenBericht();
                break;
            case DUMMY:
                actie = new ActieConversieGBABericht();
                break;
            case REGISTRATIE_ADRES:
                actie = new ActieRegistratieAdresBericht();
                break;
            default:
                throw new IllegalArgumentException("Soort wordt nog niet ondersteund.");
        }
    }

    /**
     * Instantieert een nieuwe instantie van deze builder en de onderliggende actie.
     *
     * @param soort de soort actie die geinstantieerd dient te worden.
     * @return een nieuwe actie builder instantie.
     */
    public static ActieBerichtBuilder bouwNieuweActie(final SoortActie soort) {
        ActieBerichtBuilder builder = new ActieBerichtBuilder(soort);
        return builder;
    }

    /**
     * Zet de datum aanvang geldigheid op de actie.
     *
     * @param datum datum aanvang geldigheid.
     * @return retourneert deze builder zelf.
     */
    public ActieBerichtBuilder setDatumAanvang(final Datum datum) {
        if (datum != null) {
            actie.setDatumAanvangGeldigheid(datum);
        }
        return this;
    }

    /**
     * Zet de datum aanvang geldigheid op de actie.
     *
     * @param datum datum aanvang geldigheid.
     * @return retourneert deze builder zelf.
     */
    public ActieBerichtBuilder setDatumAanvang(final Integer datum) {
        if (datum != null) {
            actie.setDatumAanvangGeldigheid(new Datum(datum));
        }
        return this;
    }

    /**
     * Zet de datum einde geldigheid op de actie.
     *
     * @param datum datum einde geldigheid.
     * @return retourneert deze builder zelf.
     */
    public ActieBerichtBuilder setDatumEinde(final Datum datum) {
        if (datum != null) {
            actie.setDatumEindeGeldigheid(datum);
        }
        return this;
    }

    /**
     * Zet de datum einde geldigheid op de actie.
     *
     * @param datum datum einde geldigheid.
     * @return retourneert deze builder zelf.
     */
    public ActieBerichtBuilder setDatumEinde(final Integer datum) {
        if (datum != null) {
            actie.setDatumEindeGeldigheid(new Datum(datum));
        }
        return this;
    }

    /**
     * Voegt het opgegeven rootobject (persoon of relatie) toe aan de lijst van rootobjecten in de actie.
     *
     * @param rootObject het rootobject (persoon of relatie) dat dient te worden toegevoegd.
     * @return retourneert deze builder zelf.
     */
    public ActieBerichtBuilder voegRootObjectToe(final RootObject rootObject) {
        if (actie.getRootObjecten() == null) {
            actie.setRootObjecten(new ArrayList<RootObject>());
        }
        actie.getRootObjecten().add(rootObject);
        return this;
    }

    /**
     * Retourneert de middels deze builder geinstantieerde en geconfigureerde actie.
     *
     * @return de actie.
     */
    public ActieBericht getActie() {
        return actie;
    }
}
