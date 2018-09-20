/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import java.util.List;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisletterAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisnummertoevoegingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.IdentificatiecodeNummeraanduidingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamOpenbareRuimteAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PostcodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Gemeente;
import org.springframework.stereotype.Repository;


/**
 * Repository voor de {@link nl.bzk.brp.model.operationeel.kern.PersoonModel} class.
 */
@Repository
public interface PersoonRepository {

    /**
     * Controleert of een BSN al in gebruik is.
     *
     * @param bsn De te controleren bsn.
     * @return true indien bsn al in gebruik is.
     */
    boolean isBSNAlIngebruik(final BurgerservicenummerAttribuut bsn);

    /**
     * Controleert of een A Nummer al in gebruik is.
     *
     * @param aNummer a-nummer van de te zoeken persoon
     * @return de gevonden persoon
     */
    boolean isAdministratienummerAlInGebruik(final AdministratienummerAttribuut aNummer);

    /**
     * Deze methode zoekt met volldige adres behalve postcode. Null waardes worden ook meegenomen in de zoek opdracht.
     * <code>null</code> resulteert in de query "is null".
     *
     * @param naamOpenbareRuimte   adres
     * @param huisnummer           huisnummer
     * @param huisletter           huisletter
     * @param huisnummertoevoeging huisnummertoevoeging
     * @param woonplaatsnaam       woonplaatsnaam
     * @param gemeente             gemeente
     * @return lijst van persoon ids
     */
    List<Integer> haalPersoonIdsMetWoonAdresOpViaVolledigAdres(
            final NaamOpenbareRuimteAttribuut naamOpenbareRuimte, final HuisnummerAttribuut huisnummer,
            final HuisletterAttribuut huisletter, final HuisnummertoevoegingAttribuut huisnummertoevoeging,
            final NaamEnumeratiewaardeAttribuut woonplaatsnaam, final Gemeente gemeente);

    /**
     * Zoekt op op IdentificatiecodeNummeraanduiding van adres.
     *
     * @param identificatiecodeNummeraanduiding
     *         opIdentificatiecodeNummeraanduiding van adres.
     * @return lijst van persoon ids
     */
    List<Integer> haalPersoonIdsMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(
            final IdentificatiecodeNummeraanduidingAttribuut identificatiecodeNummeraanduiding);

    /**
     * Haalt een lijst op met gelimiteerde persoon ids op basis van de opgegeven adres gegevens.
     * Wanneer huisletter of huisnummertoevoeging niet worden opgegeven wordt ook gezocht naar een persoon met een
     * adres zonder huisletter of huisnummertoevoeging.
     *
     * @param postcode             postcode
     * @param huisnummer           huisnummer
     * @param huisletter           huisletter
     * @param huisnummertoevoeging huisnummertoevoeging
     * @return een lijst van persoon ids
     */
    List<Integer> haalPersoonIdsOpMetAdresViaPostcodeHuisnummer(
            final PostcodeAttribuut postcode,
            final HuisnummerAttribuut huisnummer,
            final HuisletterAttribuut huisletter,
            final HuisnummertoevoegingAttribuut huisnummertoevoeging);

    /**
     * Haalt een minimale informatie van een persoon aan de hand van een technische sleutel.
     *
     * @param technischeSleutel de technische sleutel
     * @return de minimale persoon informatie.
     */
    PersoonInformatieDto haalPersoonInformatie(String technischeSleutel);

    /**
     * Zoekt het technisch (database) id van een persoon aan de hand van een unieke BSN.
     *
     * @param bsn het bsn
     * @return het technisch id of null als niets gevonden kon worden.
     */
    Integer zoekIdBijBSN(BurgerservicenummerAttribuut bsn);

    /**
     * Zoekt het technisch (database) is van een persoon aan de hand van een A-nummer.
     * Een 'null' return value kan betekenen dat het A-nummer niet bekend is in de database,
     * maar ook dat er meerdere personen bekend zijn met dit A-nummer.
     *
     * @param anr het a-nummer
     * @return het technisch id of null
     */
    Integer zoekIdBijAnummer(AdministratienummerAttribuut anr);
}
