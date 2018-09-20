/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.util;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.basis.BerichtRootObject;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieConversieGBABericht;
import nl.bzk.brp.model.bericht.kern.ActieCorrectieAdresBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieAdresBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieGeboorteBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieHuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieNaamgebruikBericht;
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
        switch (soortActie) {
            case REGISTRATIE_GEBOORTE:
                actie = new ActieRegistratieGeboorteBericht();
                break;
            case REGISTRATIE_HUWELIJK_GEREGISTREERD_PARTNERSCHAP:
                actie = new ActieRegistratieHuwelijkGeregistreerdPartnerschapBericht();
                break;
            case REGISTRATIE_NAAMGEBRUIK:
                actie = new ActieRegistratieNaamgebruikBericht();
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
        return new ActieBerichtBuilder(soort);
    }

    /**
     * Zet de datum aanvang geldigheid op de actie.
     *
     * @param datum datum aanvang geldigheid.
     * @return retourneert deze builder zelf.
     */
    public ActieBerichtBuilder setDatumAanvang(final DatumEvtDeelsOnbekendAttribuut datum) {
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
            actie.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(datum));
        }
        return this;
    }

    /**
     * Zet de datum einde geldigheid op de actie.
     *
     * @param datum datum einde geldigheid.
     * @return retourneert deze builder zelf.
     */
    public ActieBerichtBuilder setDatumEinde(final DatumEvtDeelsOnbekendAttribuut datum) {
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
            actie.setDatumEindeGeldigheid(new DatumEvtDeelsOnbekendAttribuut(datum));
        }
        return this;
    }

    /**
     * Voegt het opgegeven rootobject (persoon of relatie) toe aan de de actie.
     *
     * @param rootObject het rootobject (persoon of relatie) dat dient te worden toegevoegd.
     * @return retourneert deze builder zelf.
     */
    public ActieBerichtBuilder voegRootObjectToe(final BerichtRootObject rootObject) {
        actie.setRootObject(rootObject);
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
