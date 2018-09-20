/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdresregelAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AfgekorteNaamOpenbareRuimteAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeentedeelAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisletterAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisnummertoevoegingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.IdentificatiecodeAdresseerbaarObjectAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.IdentificatiecodeNummeraanduidingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieTenOpzichteVanAdresAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieomschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamOpenbareRuimteAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PostcodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AangeverAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdresAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingVerblijfAttribuut;
import nl.bzk.brp.model.basis.MaterieleHistorie;
import nl.bzk.brp.model.hisvolledig.kern.PersoonAdresHisVolledig;
import nl.bzk.brp.model.logisch.kern.HisPersoonAdresStandaardGroep;
import nl.bzk.brp.model.logisch.kern.PersoonAdresStandaardGroep;


/**
 *
 *
 */
@Entity
@Table(schema = "Kern", name = "His_PersAdres")
public class HisPersoonAdresModel extends AbstractHisPersoonAdresModel implements HisPersoonAdresStandaardGroep {

    /**
     * Default constructor t.b.v. JPA
     */
    protected HisPersoonAdresModel() {
        super();
    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param persoonAdresHisVolledig instantie van A-laag klasse.
     * @param groep                   groep
     * @param historie                historie
     * @param actieInhoud             actie inhoud
     */
    public HisPersoonAdresModel(final PersoonAdresHisVolledig persoonAdresHisVolledig,
        final PersoonAdresStandaardGroep groep, final MaterieleHistorie historie, final ActieModel actieInhoud)
    {
        super(persoonAdresHisVolledig, groep, historie, actieInhoud);
    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public HisPersoonAdresModel(final AbstractHisPersoonAdresModel kopie) {
        super(kopie);
    }

    /**
     * His persoon adres model. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param persoonAdres                persoonAdres van HisPersoonAdresModel
     * @param soort                       soort van HisPersoonAdresModel
     * @param redenWijziging              redenWijziging van HisPersoonAdresModel
     * @param aangever                    aangever van HisPersoonAdresModel
     * @param datumAanvangAdreshouding    datumAanvangAdreshouding van HisPersoonAdresModel
     * @param identificatiecodeAdresseerbaarObject
     *                                    identificatiecodeAdresseerbaarObject van HisPersoonAdresModel
     * @param identificatiecodeNummeraanduiding
     *                                    identificatiecodeNummeraanduiding van HisPersoonAdresModel
     * @param gemeente                    gemeente van HisPersoonAdresModel
     * @param naamOpenbareRuimte          naamOpenbareRuimte van HisPersoonAdresModel
     * @param afgekorteNaamOpenbareRuimte afgekorteNaamOpenbareRuimte van HisPersoonAdresModel
     * @param gemeentedeel                gemeentedeel van HisPersoonAdresModel
     * @param huisnummer                  huisnummer van HisPersoonAdresModel
     * @param huisletter                  huisletter van HisPersoonAdresModel
     * @param huisnummertoevoeging        huisnummertoevoeging van HisPersoonAdresModel
     * @param postcode                    postcode van HisPersoonAdresModel
     * @param woonplaats                  woonplaats van HisPersoonAdresModel
     * @param locatieTenOpzichteVanAdres  locatieTenOpzichteVanAdres van HisPersoonAdresModel
     * @param locatieomschrijving         locatieomschrijving van HisPersoonAdresModel
     * @param buitenlandsAdresRegel1      buitenlandsAdresRegel1 van HisPersoonAdresModel
     * @param buitenlandsAdresRegel2      buitenlandsAdresRegel2 van HisPersoonAdresModel
     * @param buitenlandsAdresRegel3      buitenlandsAdresRegel3 van HisPersoonAdresModel
     * @param buitenlandsAdresRegel4      buitenlandsAdresRegel4 van HisPersoonAdresModel
     * @param buitenlandsAdresRegel5      buitenlandsAdresRegel5 van HisPersoonAdresModel
     * @param buitenlandsAdresRegel6      buitenlandsAdresRegel6 van HisPersoonAdresModel
     * @param landgebied                  landgebied van HisPersoonAdresModel
     * @param indicatiePersoonAangetroffenOpAdres
     *                                    indicatiePersoonAangetroffenOpAdres van HisPersoonAdresModel
     * @param actieInhoud                 Actie inhoud; de actie die leidt tot dit nieuwe record.
     * @param historie                    De groep uit een bericht
     */
    public HisPersoonAdresModel(final PersoonAdresHisVolledig persoonAdres, final FunctieAdresAttribuut soort,
        final RedenWijzigingVerblijfAttribuut redenWijziging, final AangeverAttribuut aangever,
        final DatumEvtDeelsOnbekendAttribuut datumAanvangAdreshouding,
        final IdentificatiecodeAdresseerbaarObjectAttribuut identificatiecodeAdresseerbaarObject,
        final IdentificatiecodeNummeraanduidingAttribuut identificatiecodeNummeraanduiding,
        final GemeenteAttribuut gemeente, final NaamOpenbareRuimteAttribuut naamOpenbareRuimte,
        final AfgekorteNaamOpenbareRuimteAttribuut afgekorteNaamOpenbareRuimte,
        final GemeentedeelAttribuut gemeentedeel, final HuisnummerAttribuut huisnummer,
        final HuisletterAttribuut huisletter, final HuisnummertoevoegingAttribuut huisnummertoevoeging,
        final PostcodeAttribuut postcode, final NaamEnumeratiewaardeAttribuut woonplaats,
        final LocatieTenOpzichteVanAdresAttribuut locatieTenOpzichteVanAdres,
        final LocatieomschrijvingAttribuut locatieomschrijving, final AdresregelAttribuut buitenlandsAdresRegel1,
        final AdresregelAttribuut buitenlandsAdresRegel2, final AdresregelAttribuut buitenlandsAdresRegel3,
        final AdresregelAttribuut buitenlandsAdresRegel4, final AdresregelAttribuut buitenlandsAdresRegel5,
        final AdresregelAttribuut buitenlandsAdresRegel6, final LandGebiedAttribuut landgebied,
        final NeeAttribuut indicatiePersoonAangetroffenOpAdres, final ActieModel actieInhoud,
        final MaterieleHistorie historie)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        super(persoonAdres, soort, redenWijziging, aangever, datumAanvangAdreshouding,
            identificatiecodeAdresseerbaarObject, identificatiecodeNummeraanduiding, gemeente, naamOpenbareRuimte,
            afgekorteNaamOpenbareRuimte, gemeentedeel, huisnummer, huisletter, huisnummertoevoeging, postcode,
            woonplaats, locatieTenOpzichteVanAdres, locatieomschrijving, buitenlandsAdresRegel1,
            buitenlandsAdresRegel2, buitenlandsAdresRegel3, buitenlandsAdresRegel4, buitenlandsAdresRegel5,
            buitenlandsAdresRegel6, landgebied, indicatiePersoonAangetroffenOpAdres, actieInhoud, historie);
    }

    /**
     * @return de objectsleutel
     */
    @Transient
    public final String getObjectSleutel() {
        return getPersoonAdres().getID().toString();
    }
}
