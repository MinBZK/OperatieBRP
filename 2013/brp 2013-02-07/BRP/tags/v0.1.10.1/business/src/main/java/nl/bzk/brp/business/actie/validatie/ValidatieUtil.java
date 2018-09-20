/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie.validatie;

import java.util.Collection;
import java.util.List;

import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.basis.AbstractObjectTypeBericht;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;
import nl.bzk.brp.model.basis.AttribuutType;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import org.apache.commons.lang.StringUtils;

/** Utility class t.b.v. het uitvoeren van validaties. */
public final class ValidatieUtil {

    /** Constructor private gemaakt omdat dit een Utility class is. */
    private ValidatieUtil() {
    }

    /**
     * Controlleer dat een collectie gevuld is in een objecttype.
     * @param meldingen Lijst waaraan de melding kan worden toegevoegd.
     * @param objecttype Objecttype waar de collectie in zit.
     * @param collectie De collectie zelf.
     * @param veldNaam Naam van het veld in het bericht.
     * @return true voor niet lege waarde, false anders.
     */
    public static boolean controlleerVerplichteCollectieInObjectType(final List<Melding> meldingen,
                                                                     final AbstractObjectTypeBericht objecttype,
                                                                     final Collection collectie,
                                                                     final String veldNaam)
    {
        boolean retval = true;
        if (collectie == null || collectie.size() == 0) {
            voegMeldingVerplichtVeld(meldingen, objecttype, veldNaam);
            retval = false;
        }
        return retval;
    }

    /**
     * Controlleer dat een attribuut gevuld is in een objecttype.
     * @param meldingen Lijst waaraan de melding kan worden toegevoegd.
     * @param objecttype Objecttype waar het attribuut in zit.
     * @param attribuut Het attribuut zelf.
     * @param veldNaam Naam van het veld in het bericht.
     * @return true voor niet lege waarde, false anders.
     */
    public static boolean controlleerVerplichteVeldInObjectType(final List<Melding> meldingen,
                                                                final AbstractObjectTypeBericht objecttype,
                                                                final AttribuutType attribuut,
                                                                final String veldNaam)
    {
        boolean retval = true;
        if (attribuut == null) {
            voegMeldingVerplichtVeld(meldingen, objecttype, veldNaam);
            retval = false;
        } else {
            if (attribuut.getWaarde() == null) {
                voegMeldingVerplichtVeld(meldingen, objecttype, veldNaam);
                retval = false;
            } else if (attribuut.getWaarde() instanceof String
                       && StringUtils.isBlank((String) attribuut.getWaarde()))
            {
                voegMeldingVerplichtVeld(meldingen, objecttype, veldNaam);
                retval = false;
            }
        }
        return retval;
    }

    /**
     * Controlleer dat een attribuut van het type statisch objecttype gevuld is in een objecttype.
     * @param meldingen Lijst waaraan de melding kan worden toegevoegd.
     * @param objecttype Objecttype waar het attribuut in zit.
     * @param statischObjectType Het attribuut zelf.
     * @param veldNaam Naam van het veld in het bericht.
     * @return true voor niet lege waarde, false anders.
     */
    public static boolean controlleerVerplichteVeldInObjectType(final List<Melding> meldingen,
                                                                final AbstractObjectTypeBericht objecttype,
                                                                final AbstractStatischObjectType statischObjectType,
                                                                final String veldNaam)
    {
        boolean retval = true;
        if (statischObjectType == null) {
            voegMeldingVerplichtVeld(meldingen, objecttype, veldNaam);
            retval = false;
        }
        return retval;
    }

    /**
     * Controleert of de waarde null of leeg is. In geval het om een collection of String gaat wordt de
     * lengte ook meegenomen.
     *
     * @param meldingen Lijst waaraan de melding kan worden toegevoegd.
     * @param groep de identificeerbare groep.
     * @param attr De te controleren waarde.
     * @param veldNaam Naam van het veld dat gecontroleerd wordt, is nodig voor in de melding.
     * @return true voor niet lege waarde, false anders
     */
    public static boolean controleerVerplichteVeldInGroep(final List<Melding> meldingen,
                                                          final AbstractGroepBericht groep,
                                                          final AttribuutType attr,
                                                          final String veldNaam)
    {
        boolean retval = true;
        if (attr == null) {
            voegMeldingVerplichtVeld(meldingen, groep, veldNaam);
            retval = false;
        } else {
            if (attr.getWaarde() == null) {
                voegMeldingVerplichtVeld(meldingen, groep, veldNaam);
                retval = false;
            } else if (attr.getWaarde() instanceof String
                       && StringUtils.isBlank((String) attr.getWaarde()))
            {
                voegMeldingVerplichtVeld(meldingen, groep, veldNaam);
                retval = false;
            }
        }
        return retval;
    }

    /**
     * Controleert of de waarde null of leeg is. In geval het om een collection of String gaat wordt de
     * lengte ook meegenomen.
     *
     * @param meldingen Lijst waaraan de melding kan worden toegevoegd.
     * @param groep de identificeerbare groep.
     * @param statischObjectTypeAttr De te controleren waarde wat een statisch objecttype kan zijn.
     * @param veldNaam Naam van het veld dat gecontroleerd wordt, is nodig voor in de melding.
     * @return true voor niet lege waarde, false anders
     */
    public static boolean controleerVerplichteVeldInGroep(final List<Melding> meldingen,
                                                          final AbstractGroepBericht groep,
                                                          final AbstractStatischObjectType statischObjectTypeAttr,
                                                          final String veldNaam)
    {
        boolean retval = true;
        if (statischObjectTypeAttr == null) {
            voegMeldingVerplichtVeld(meldingen, groep, veldNaam);
            retval = false;
        }
        return retval;
    }

    /**
     * Controleert of de waarde null of leeg is. In geval het om een collection of String gaat wordt de
     * lengte ook meegenomen.
     *
     * @param meldingen Lijst waaraan de melding kan worden toegevoegd.
     * @param groep de identificeerbare groep.
     * @param enumeratiewaarde De te controleren waarde wat een enum kan zijn.
     * @param veldNaam Naam van het veld dat gecontroleerd wordt, is nodig voor in de melding.
     * @return true voor niet lege waarde, false anders
     */
    public static boolean controleerVerplichteVeldInGroep(final List<Melding> meldingen,
                                                          final AbstractGroepBericht groep,
                                                          final Enum enumeratiewaarde,
                                                          final String veldNaam)
    {
        boolean retval = true;
        if (enumeratiewaarde == null) {
            voegMeldingVerplichtVeld(meldingen, groep, veldNaam);
            retval = false;
        }
        return retval;
    }

    /**
     * Controleer of de groep aanwezig is in het objecttype. Voegt een melding toe indien dit niet zo is.
     * @param meldingen De lijst waaraan een melding moet worden toegevoegd.
     * @param objecttype Objecttype waar de groep in zit.
     * @param groep De verplichte groep.
     * @param groepNaam De naam van de groep.
     * @return true indien de groep gedefinieerd is, anders false.
     */
    public static boolean controleerVerplichteGroepInObjectType(final List<Melding> meldingen,
                                                                final AbstractObjectTypeBericht objecttype,
                                                                final AbstractGroepBericht groep,
                                                                final String groepNaam)
    {
        boolean retval = true;
        if (groep == null) {
            voegMeldingVerplichtVeld(meldingen, objecttype, groepNaam);
            retval = false;
        }
        return retval;
    }

    /**
     * Voegt een melding toe voor een verplicht veld aan de lijst van meldingen.
     *
     * @param meldingen Lijst waaraan de melding kan worden toegevoegd.
     * @param groep de identificeerbare groep.
     * @param veldNaam Naam van het veld.
     */
    public static void voegMeldingVerplichtVeld(final List<Melding> meldingen, final AbstractGroepBericht groep,
            final String veldNaam)
    {
        meldingen.add(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0002,
            String.format("%s: %s", MeldingCode.ALG0002.getOmschrijving(), veldNaam),
            groep, null));
    }

    /**
     * Voegt een melding toe voor een verplicht veld aan de lijst van meldingen.
     *
     * @param meldingen Lijst van meldingen.
     * @param objecttype Het identificeerbare object type.
     * @param veldNaam Naam van het veld.
     */
    public static void voegMeldingVerplichtVeld(final List<Melding> meldingen,
                                                final AbstractObjectTypeBericht objecttype,
                                                final String veldNaam)
    {
        meldingen.add(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0002,
            String.format("%s: %s", MeldingCode.ALG0002.getOmschrijving(), veldNaam),
            objecttype, null));
    }
}
