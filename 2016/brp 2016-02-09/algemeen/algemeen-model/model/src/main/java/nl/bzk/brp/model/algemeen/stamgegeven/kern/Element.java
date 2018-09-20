/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ExpressietekstAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.IdentifierLangAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeLangAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.SynchroniseerbaarStamgegeven;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

/**
 * Het 'element' is het kern begrip in het meta model. Dit objecttype bevat bevat: - Alle elementen uit het kernschema
 * van het LGM. - Alle patroonvelden die toegevoegd worden tijdens de transformatie van LGM naar OGM. - Alle 'virtuele'
 * elementen over gerelateerde die noodzakelijk zijn voor het reconstrueren van de juridische persoonslijst.
 *
 * Deze tabel wordt gebruikt om te verwijzen naar gegevenselementen van de BRP.
 *
 *
 *
 */
@Table(schema = "Kern", name = "Element")
@Access(value = AccessType.FIELD)
@Entity
@Immutable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Element extends AbstractElement implements SynchroniseerbaarStamgegeven, ElementIdentificeerbaar {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected Element() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param naam naam van Element.
     * @param soort soort van Element.
     * @param elementNaam elementNaam van Element.
     * @param objecttype objecttype van Element.
     * @param groep groep van Element.
     * @param volgnummer volgnummer van Element.
     * @param aliasVan aliasVan van Element.
     * @param expressie expressie van Element.
     * @param autorisatie autorisatie van Element.
     * @param tabel tabel van Element.
     * @param identificatieDatabase identificatieDatabase van Element.
     * @param hisTabel hisTabel van Element.
     * @param hisIdentifierDatabase hisIdentifierDatabase van Element.
     * @param leverenAlsStamgegeven leverenAlsStamgegeven van Element.
     * @param datumAanvangGeldigheid datumAanvangGeldigheid van Element.
     * @param datumEindeGeldigheid datumEindeGeldigheid van Element.
     */
    protected Element(
        final NaamEnumeratiewaardeLangAttribuut naam,
        final SoortElement soort,
        final NaamEnumeratiewaardeAttribuut elementNaam,
        final Element objecttype,
        final Element groep,
        final VolgnummerAttribuut volgnummer,
        final Element aliasVan,
        final ExpressietekstAttribuut expressie,
        final SoortElementAutorisatie autorisatie,
        final Element tabel,
        final IdentifierLangAttribuut identificatieDatabase,
        final Element hisTabel,
        final IdentifierLangAttribuut hisIdentifierDatabase,
        final JaAttribuut leverenAlsStamgegeven,
        final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid,
        final DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        super(naam,
            soort,
            elementNaam,
            objecttype,
            groep,
            volgnummer,
            aliasVan,
            expressie,
            autorisatie,
            tabel,
            identificatieDatabase,
            hisTabel,
            hisIdentifierDatabase,
            leverenAlsStamgegeven,
            datumAanvangGeldigheid,
            datumEindeGeldigheid);
    }


    /**
     * Geeft het id van het elemnt.
     *
     * @return Het id van het element.
     */
    @Override
    public Integer getID() {
        return super.getID();
    }

    /**
     * Bepaalt of het gegeven element enum hetzelfde element betreft.
     *
     * @param element element
     * @return true, als het hetzelfde element betreft
     */
    public final boolean isHetzelfdeElement(final ElementEnum element) {
        return element != null && element.getId().equals(getID());
    }

    /**
     * Geeft de element enum name, zodat je de juiste Enum waarde kunt pakken.
     *
     * @return the string
     */
    public final String geefElementEnumName() {
        final String uppercaseElementNaam = StringUtils.upperCase(getNaam().getWaarde());
        return StringUtils.replace(uppercaseElementNaam, ".", "_");
    }


}
