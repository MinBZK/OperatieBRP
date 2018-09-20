/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.bedrijfsregels;

import java.util.List;

import nl.bzk.brp.pocmotor.model.RootObject;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.FunctieAdres;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.PersoonAdresStandaard;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Persoon;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.PersoonAdres;
import nl.bzk.brp.pocmotor.util.VergelijkingsUtil;

public class VerhuizingNaarHetzelfdeAdres implements BedrijfsRegel<Persoon> {

    @Override
    public BedrijfsRegelFout executeer(final Persoon nieuweSituatie, final List<RootObject> huidigeSituatie) {
        final PersoonAdres huidigeWoonAdres = getHuidigeWoonAdres(huidigeSituatie);
        PersoonAdres nieuweWoonAdres = null;
        for (PersoonAdres persoonAdres : nieuweSituatie.getAdressen()) {
            if (FunctieAdres.WOONADRES == persoonAdres.getStandaard().getSoort()) {
                nieuweWoonAdres = persoonAdres;
            }
        }
        return checkVerhuizingNaarHetzelfdeAdres(huidigeWoonAdres, nieuweWoonAdres);
    }

    private BedrijfsRegelFout checkVerhuizingNaarHetzelfdeAdres(final PersoonAdres huidigeWoonAdres,
                                                                final PersoonAdres nieuweWoonAdres) {
        BedrijfsRegelFout fout = null;
        final PersoonAdresStandaard nieuweStandaard = nieuweWoonAdres.getStandaard();
        final PersoonAdresStandaard huidigeStandaard = huidigeWoonAdres.getStandaard();

        //Verschillende postcodes is een verhuizing
        boolean isPostCodeGewijzigd = !VergelijkingsUtil.zijnAttributenGelijkOfNietGedefinieerd(
                huidigeStandaard.getPostcode(), nieuweStandaard.getPostcode()
        );

        boolean isHuisNummerGewijzigd = !VergelijkingsUtil.zijnAttributenGelijkOfNietGedefinieerd(
                huidigeStandaard.getHuisnummer(), nieuweStandaard.getHuisnummer()
        );

        boolean isHuisNummerToevoegingGewijzigd = !VergelijkingsUtil.zijnAttributenGelijkOfNietGedefinieerd(
                huidigeStandaard.getHuisnummertoevoeging(), nieuweStandaard.getHuisnummertoevoeging()
        );

        boolean isHuisLetterGewijzigd = !VergelijkingsUtil.zijnAttributenGelijkOfNietGedefinieerd(
                huidigeStandaard.getHuisletter(), nieuweStandaard.getHuisletter()
        );

        if (!(isPostCodeGewijzigd || isHuisNummerGewijzigd ||
                isHuisNummerToevoegingGewijzigd || isHuisLetterGewijzigd)) {
            fout = new BedrijfsRegelFout(
                    "VERHUIZING_NAAR_HETZELFDE_ADRES", BedrijfsRegelFoutErnst.WAARSCHUWING,
                    "Deze persoon woont al op het opgegeven adres."
            );

        }
        return fout;
    }

    private PersoonAdres getHuidigeWoonAdres(final List<RootObject> huidigeSituatie) {
        for (RootObject rootObject : huidigeSituatie) {
            if (rootObject instanceof Persoon) {
                Persoon persoon = (Persoon) rootObject;
                for (PersoonAdres persoonAdres : persoon.getAdressen()) {
                    if (FunctieAdres.WOONADRES == persoonAdres.getStandaard().getSoort()) {
                        return persoonAdres;
                    }
                }
            }
        }
        return null;
    }
}
