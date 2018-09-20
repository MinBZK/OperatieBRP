/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.impl.gen;

import nl.bzk.brp.model.attribuuttype.Adresregel;
import nl.bzk.brp.model.attribuuttype.Adresseerbaarobject;
import nl.bzk.brp.model.attribuuttype.AfgekorteNaamOpenbareRuimte;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Gemeentedeel;
import nl.bzk.brp.model.attribuuttype.Huisletter;
import nl.bzk.brp.model.attribuuttype.Huisnummer;
import nl.bzk.brp.model.attribuuttype.Huisnummertoevoeging;
import nl.bzk.brp.model.attribuuttype.IdentificatiecodeNummerAanduiding;
import nl.bzk.brp.model.attribuuttype.LocatieOmschrijving;
import nl.bzk.brp.model.attribuuttype.LocatieTovAdres;
import nl.bzk.brp.model.attribuuttype.NaamOpenbareRuimte;
import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.groep.interfaces.gen.PersoonAdresStandaardGroepBasis;
import nl.bzk.brp.model.objecttype.statisch.AangeverAdreshoudingIdentiteit;
import nl.bzk.brp.model.objecttype.statisch.FunctieAdres;
import nl.bzk.brp.model.objecttype.statisch.Land;
import nl.bzk.brp.model.objecttype.statisch.Partij;
import nl.bzk.brp.model.objecttype.statisch.Plaats;
import nl.bzk.brp.model.objecttype.statisch.RedenWijzigingAdres;


/**
 * Implementatie voor standaard groep van persoon adres.
 *
 */
public abstract class AbstractPersoonAdresGroepMdl extends AbstractGroep implements PersoonAdresStandaardGroepBasis {

    private FunctieAdres                      soort;
    private Datum                             datumAanvangAdreshouding;
    private RedenWijzigingAdres               redenwijziging;
    private AangeverAdreshoudingIdentiteit    aangeverAdreshouding;
    private Adresseerbaarobject               adresseerbaarObject;
    private IdentificatiecodeNummerAanduiding identificatiecodeNummeraanduiding;
    private Partij                            gemeente;
    private NaamOpenbareRuimte                naamOpenbareRuimte;
    private AfgekorteNaamOpenbareRuimte       afgekorteNaamOpenbareRuimte;
    private Gemeentedeel                      gemeentedeel;
    private Huisnummer                        huisnummer;
    private Huisletter                        huisletter;
    private Huisnummertoevoeging              huisnummertoevoeging;
    private Plaats                            woonplaats;
    private LocatieTovAdres                   locatietovAdres;
    private LocatieOmschrijving               locatieOmschrijving;
    private Adresregel                        buitenlandsAdresRegel1;
    private Adresregel                        buitenlandsAdresRegel2;
    private Adresregel                        buitenlandsAdresRegel3;
    private Adresregel                        buitenlandsAdresRegel4;
    private Adresregel                        buitenlandsAdresRegel5;
    private Adresregel                        buitenlandsAdresRegel6;
    private Land                              land;
    private Datum                             datumVertrekUitNederland;

    @Override
    public FunctieAdres getSoort() {
        return soort;
    }

    @Override
    public Datum getDatumAanvangAdreshouding() {
        return datumAanvangAdreshouding;
    }

    @Override
    public RedenWijzigingAdres getRedenWijziging() {
        return redenwijziging;
    }

    @Override
    public AangeverAdreshoudingIdentiteit getAangeverAdresHouding() {
        return aangeverAdreshouding;
    }

    @Override
    public Adresseerbaarobject getAdresseerbaarObject() {
        return adresseerbaarObject;
    }

    @Override
    public IdentificatiecodeNummerAanduiding getIdentificatiecodeNummeraanduiding() {
        return identificatiecodeNummeraanduiding;
    }

    @Override
    public Partij getGemeente() {
        return gemeente;
    }

    @Override
    public NaamOpenbareRuimte getNaamOpenbareRuimte() {
        return naamOpenbareRuimte;
    }

    @Override
    public AfgekorteNaamOpenbareRuimte getAfgekorteNaamOpenbareRuimte() {
        return afgekorteNaamOpenbareRuimte;
    }

    @Override
    public Gemeentedeel getGemeentedeel() {
        return gemeentedeel;
    }

    @Override
    public Huisnummer getHuisnummer() {
        return huisnummer;
    }

    @Override
    public Huisletter getHuisletter() {
        return huisletter;
    }

    @Override
    public Huisnummertoevoeging getHuisnummertoevoeging() {
        return huisnummertoevoeging;
    }

    @Override
    public Plaats getWoonplaats() {
        return woonplaats;
    }

    @Override
    public LocatieTovAdres getLocatietovAdres() {
        return locatietovAdres;
    }

    @Override
    public LocatieOmschrijving getLocatieOmschrijving() {
        return locatieOmschrijving;
    }

    @Override
    public Adresregel getBuitenlandsAdresRegel1() {
        return buitenlandsAdresRegel1;
    }

    @Override
    public Adresregel getBuitenlandsAdresRegel2() {
        return buitenlandsAdresRegel2;
    }

    @Override
    public Adresregel getBuitenlandsAdresRegel3() {
        return buitenlandsAdresRegel3;
    }

    @Override
    public Adresregel getBuitenlandsAdresRegel4() {
        return buitenlandsAdresRegel4;
    }

    @Override
    public Adresregel getBuitenlandsAdresRegel5() {
        return buitenlandsAdresRegel5;
    }

    @Override
    public Adresregel getBuitenlandsAdresRegel6() {
        return buitenlandsAdresRegel6;
    }

    @Override
    public Land getLand() {
        return land;
    }

    @Override
    public Datum getDatumVertrekUitNederland() {
        return datumVertrekUitNederland;
    }

}
