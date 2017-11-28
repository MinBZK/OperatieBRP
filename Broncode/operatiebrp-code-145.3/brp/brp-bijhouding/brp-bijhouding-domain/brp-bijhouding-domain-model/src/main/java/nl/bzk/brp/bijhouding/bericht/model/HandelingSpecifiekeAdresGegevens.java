/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Aangever;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdresHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenWijzigingVerblijf;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdres;

/**
 * Helper class voor BijhoudingPersoon die handeling specifieke adresgegevens bevat.
 */
final class HandelingSpecifiekeAdresGegevens {
    private final AdresElement adresElement;
    private RedenWijzigingVerblijf redenwijziging;
    private Gemeente gemeente;
    private Integer datumAanvangAdreshouding;
    private SoortAdres soortAdres;
    private Aangever aangeverAdreshouding;
    private Boolean indicatiePersoonAangetroffenOpAdres;

    HandelingSpecifiekeAdresGegevens(final AdresElement adresElement) {
        this.adresElement = adresElement;
        if (adresElement.getAangeverAdreshoudingCode() != null) {
            this.aangeverAdreshouding =
                    adresElement.getDynamischeStamtabelRepository().getAangeverByCode(adresElement.getAangeverAdreshoudingCode().getWaarde());
        }
    }

    HandelingSpecifiekeAdresGegevens redenwijziging(final RedenWijzigingVerblijf redenwijziging) {
        this.redenwijziging = redenwijziging;
        return this;
    }

    HandelingSpecifiekeAdresGegevens gemeente(final Gemeente gemeente) {
        this.gemeente = gemeente;
        return this;
    }

    HandelingSpecifiekeAdresGegevens datumAanvangAdreshouding(final Integer datumAanvangAdreshouding) {
        this.datumAanvangAdreshouding = datumAanvangAdreshouding;
        return this;
    }

    HandelingSpecifiekeAdresGegevens soortAdres(final SoortAdres soortAdres) {
        this.soortAdres = soortAdres;
        return this;
    }

    HandelingSpecifiekeAdresGegevens indicatiePersoonAangetroffenOpAdres(final Boolean indicatiePersoonAangetroffenOpAdres) {
        this.indicatiePersoonAangetroffenOpAdres = indicatiePersoonAangetroffenOpAdres;
        return this;
    }

    PersoonAdresHistorie build(final PersoonAdres persoonAdres, final LandOfGebied landOfGebied) {
        final PersoonAdresHistorie result = new PersoonAdresHistorie(persoonAdres, soortAdres, landOfGebied, redenwijziging);
        result.setGemeente(gemeente);
        result.setDatumAanvangAdreshouding(datumAanvangAdreshouding);
        result.setAangeverAdreshouding(aangeverAdreshouding);
        result.setIndicatiePersoonAangetroffenOpAdres(indicatiePersoonAangetroffenOpAdres);
        //gegevens die altijd uit het bericht worden overgenomen en niet specifiek zijn per handeling
        result.setNaamOpenbareRuimte(BmrAttribuut.getWaardeOfNull(adresElement.getNaamOpenbareRuimte()));
        result.setAfgekorteNaamOpenbareRuimte(BmrAttribuut.getWaardeOfNull(adresElement.getAfgekorteNaamOpenbareRuimte()));
        result.setPostcode(BmrAttribuut.getWaardeOfNull(adresElement.getPostcode()));
        result.setWoonplaatsnaam(BmrAttribuut.getWaardeOfNull(adresElement.getWoonplaatsnaam()));
        result.setHuisletter(BmrAttribuut.getWaardeOfNull(adresElement.getHuisletter()));
        if (adresElement.getHuisnummer() != null) {
            result.setHuisnummer(Integer.parseInt(adresElement.getHuisnummer().getWaarde()));
        }
        result.setHuisnummertoevoeging(BmrAttribuut.getWaardeOfNull(adresElement.getHuisnummertoevoeging()));
        result.setIdentificatiecodeAdresseerbaarObject(BmrAttribuut.getWaardeOfNull(adresElement.getIdentificatiecodeAdresseerbaarObject()));
        result.setIdentificatiecodeNummeraanduiding(BmrAttribuut.getWaardeOfNull(adresElement.getIdentificatiecodeNummeraanduiding()));
        result.setLocatieOmschrijving(BmrAttribuut.getWaardeOfNull(adresElement.getLocatieomschrijving()));
        result.setLocatietovAdres(BmrAttribuut.getWaardeOfNull(adresElement.getLocatieTenOpzichteVanAdres()));
        return result;
    }
}
