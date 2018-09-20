/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Embeddable;
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
import nl.bzk.brp.model.logisch.kern.PersoonAdresStandaardGroep;


/**
 * Voor de modellering van buitenlands adres waren enkele opties: - Adres in een attribuut met 'regelovergang' tekens Nadeel: Regelovergangtekens zijn niet
 * platformonafhankelijk en het maximale aantal regels is niet goed af te dwingen. - Adres uitsplitsen volgens een of andere norm (wordt naar gezocht) RNI
 * heeft een actie gestart om te kijken of binnen Europa een werkbare standaard te vinden is. Wereldwijd gaat niet lukken. (Voorlopig) nog geen optie. -
 * Adres per regel opnemen. - Adresregels in een aparte tabel. Is ook mogelijk mits aantal regels beperkt wordt. Uiteindelijk is gekozen voor opname per
 * regel. Dat lijkt minder flexibel dan een vrij veld waarin meerdere regels geplaatst kunnen worden. Het geeft de afnemer echter wel duidelijkheid over
 * het maximale aantal regels en het maximale aantal karakters per regel dat deze kan verwachten. Het aantal zes is afkomstig uit onderzoek door de RNI
 * inzake de maximale grootte van internationale adressen. RvdP 5 september 2011, verplaatst naar nieuwe groep standaard op 13 jan 2012.
 */
@Embeddable
public class PersoonAdresStandaardGroepModel extends AbstractPersoonAdresStandaardGroepModel implements
    PersoonAdresStandaardGroep
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected PersoonAdresStandaardGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param soort                       soort van Standaard.
     * @param redenWijziging              redenWijziging van Standaard.
     * @param aangever                    aangever van Standaard.
     * @param datumAanvangAdreshouding    datumAanvangAdreshouding van Standaard.
     * @param identificatiecodeAdresseerbaarObject
     *                                    identificatiecodeAdresseerbaarObject van Standaard.
     * @param identificatiecodeNummeraanduiding
     *                                    identificatiecodeNummeraanduiding van Standaard.
     * @param gemeente                    gemeente van Standaard.
     * @param naamOpenbareRuimte          naamOpenbareRuimte van Standaard.
     * @param afgekorteNaamOpenbareRuimte afgekorteNaamOpenbareRuimte van Standaard.
     * @param gemeentedeel                gemeentedeel van Standaard.
     * @param huisnummer                  huisnummer van Standaard.
     * @param huisletter                  huisletter van Standaard.
     * @param huisnummertoevoeging        huisnummertoevoeging van Standaard.
     * @param postcode                    postcode van Standaard.
     * @param woonplaats                  woonplaats van Standaard.
     * @param locatieTenOpzichteVanAdres  locatieTenOpzichteVanAdres van Standaard.
     * @param locatieomschrijving         locatieomschrijving van Standaard.
     * @param buitenlandsAdresRegel1      buitenlandsAdresRegel1 van Standaard.
     * @param buitenlandsAdresRegel2      buitenlandsAdresRegel2 van Standaard.
     * @param buitenlandsAdresRegel3      buitenlandsAdresRegel3 van Standaard.
     * @param buitenlandsAdresRegel4      buitenlandsAdresRegel4 van Standaard.
     * @param buitenlandsAdresRegel5      buitenlandsAdresRegel5 van Standaard.
     * @param buitenlandsAdresRegel6      buitenlandsAdresRegel6 van Standaard.
     * @param landGebied                  landGebied van Standaard.
     * @param indicatiePersoonNietAangetroffenOpAdres
     *                                    indicatiePersoonNietAangetroffenOpAdres van Standaard.
     */
    public PersoonAdresStandaardGroepModel(final FunctieAdresAttribuut soort,
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
        final AdresregelAttribuut buitenlandsAdresRegel6, final LandGebiedAttribuut landGebied,
        final NeeAttribuut indicatiePersoonNietAangetroffenOpAdres)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        super(soort, redenWijziging, aangever, datumAanvangAdreshouding, identificatiecodeAdresseerbaarObject,
            identificatiecodeNummeraanduiding, gemeente, naamOpenbareRuimte, afgekorteNaamOpenbareRuimte,
            gemeentedeel, huisnummer, huisletter, huisnummertoevoeging, postcode, woonplaats,
            locatieTenOpzichteVanAdres, locatieomschrijving, buitenlandsAdresRegel1, buitenlandsAdresRegel2,
            buitenlandsAdresRegel3, buitenlandsAdresRegel4, buitenlandsAdresRegel5, buitenlandsAdresRegel6,
            landGebied, indicatiePersoonNietAangetroffenOpAdres);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonAdresStandaardGroep te kopieren groep.
     */
    public PersoonAdresStandaardGroepModel(final PersoonAdresStandaardGroep persoonAdresStandaardGroep) {
        super(persoonAdresStandaardGroep);
    }

}
