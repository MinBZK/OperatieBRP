/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;
import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

/**
 * De actie voor het registreren van een geslachtsnaam wijziging.
 */
//
@XmlElement("registratieGeslachtsnaam")
public final class RegistratieGeslachtsnaamActieElement extends AbstractPersoonWijzigingActieElement{

    /**
     * Maakt een RegistratieGeslachtsnaamActieElement object.
     * @param basisAttribuutGroep de basis attribuutgroep
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @param datumEindeGeldigheid datum einde geldigheid
     * @param bronnen bron referenties
     * @param persoon de persoon
     */
    public RegistratieGeslachtsnaamActieElement(
            final Map<String, String> basisAttribuutGroep,
            final DatumElement datumAanvangGeldigheid,
            final DatumElement datumEindeGeldigheid,
            final List<BronReferentieElement> bronnen,
            final PersoonGegevensElement persoon) {
        super(basisAttribuutGroep, datumAanvangGeldigheid, datumEindeGeldigheid, bronnen, persoon);

        controleerAantalGeslachtsnaamcomponenten(persoon.getGeslachtsnaamcomponenten());
    }

    private void controleerAantalGeslachtsnaamcomponenten(final List<GeslachtsnaamcomponentElement> geslachtsnaamcomponenten) {
        ValidatieHelper.controleerOpNullWaarde(geslachtsnaamcomponenten, "geslachtsnaamcomponenten");
        if (geslachtsnaamcomponenten.size() != 1) {
            throw new IllegalArgumentException("Voor deze actie wordt exact 1 geslachtsnaamcomponent verwacht");
        }
    }

    /**
     * Geef het geslachtsnaamcomponent voor deze actie.
     * @return geslachtsnaamcomponent
     */
    public GeslachtsnaamcomponentElement getGeslachtsnaamcomponent() {
        return getPersoon().getGeslachtsnaamcomponenten().get(0);
    }

    @Override
    protected List<MeldingElement> valideerSpecifiekeInhoud() {
        return VALIDATIE_OK;
    }

    @Override
    public SoortActie getSoortActie() {
        return SoortActie.REGISTRATIE_GESLACHTSNAAM;
    }

    @Override
    public BRPActie verwerk(final BijhoudingVerzoekBericht bericht, final AdministratieveHandeling administratieveHandeling) {
        final BijhoudingPersoon persoonEntiteit = getHoofdPersonen().get(0);
        final BRPActie actie;
        if (persoonEntiteit.isResultaatVanOntrelateren()) {
            actie = maakActieEntiteit(administratieveHandeling);
            persoonEntiteit
                    .wijzigPersoonSamengesteldeNaamEntiteit(getPersoon().getGeslachtsnaamcomponenten().get(0), actie, getDatumAanvangGeldigheid().getWaarde());
        } else if (persoonEntiteit.isVerwerkbaar()) {
            actie = maakActieEntiteit(administratieveHandeling);
            persoonEntiteit.wijzigPersoonGeslachtsnaamcomponentEntiteit(getPersoon().getGeslachtsnaamcomponenten().get(0), actie,
                    getDatumAanvangGeldigheid().getWaarde());
            persoonEntiteit.leidAf(actie, getDatumAanvangGeldigheid().getWaarde(), false);
        } else {
            actie = null;
        }
        return actie;
    }

    @Override
    public DatumElement getPeilDatum() {
        return getDatumAanvangGeldigheid();
    }

    @Override
    public boolean heeftInvloedOpGerelateerden() {
        return true;
    }
}
