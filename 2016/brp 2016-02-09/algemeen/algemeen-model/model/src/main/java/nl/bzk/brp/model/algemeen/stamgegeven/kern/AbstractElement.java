/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ExpressietekstAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.IdentifierLangAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeLangAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.basis.BestaansPeriodeStamgegeven;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import nl.bzk.brp.model.basis.SynchroniseerbaarStamgegeven;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
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
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@Immutable
@Generated(value = "nl.bzk.brp.generatoren.java.DynamischeStamgegevensGenerator")
@MappedSuperclass
public abstract class AbstractElement implements SynchroniseerbaarStamgegeven, BestaansPeriodeStamgegeven, ElementIdentificeerbaar {

    @Id
    @JsonProperty
    private Integer iD;

    @Embedded
    @AttributeOverride(name = NaamEnumeratiewaardeLangAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Naam"))
    @JsonProperty
    private NaamEnumeratiewaardeLangAttribuut naam;

    @Column(name = "Srt")
    private SoortElement soort;

    @Embedded
    @AttributeOverride(name = NaamEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "ElementNaam"))
    private NaamEnumeratiewaardeAttribuut elementNaam;

    @ManyToOne
    @JoinColumn(name = "Objecttype")
    @Fetch(value = FetchMode.JOIN)
    private Element objecttype;

    @ManyToOne
    @JoinColumn(name = "Groep")
    @Fetch(value = FetchMode.JOIN)
    private Element groep;

    @Embedded
    @AttributeOverride(name = VolgnummerAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Volgnr"))
    private VolgnummerAttribuut volgnummer;

    @ManyToOne
    @JoinColumn(name = "AliasVan")
    @Fetch(value = FetchMode.JOIN)
    private Element aliasVan;

    @Embedded
    @AttributeOverride(name = ExpressietekstAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Expressie"))
    private ExpressietekstAttribuut expressie;

    @Column(name = "Autorisatie")
    private SoortElementAutorisatie autorisatie;

    @ManyToOne
    @JoinColumn(name = "Tabel")
    @Fetch(value = FetchMode.JOIN)
    private Element tabel;

    @Embedded
    @AttributeOverride(name = IdentifierLangAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IdentDb"))
    private IdentifierLangAttribuut identificatieDatabase;

    @ManyToOne
    @JoinColumn(name = "HisTabel")
    @Fetch(value = FetchMode.JOIN)
    private Element hisTabel;

    @Embedded
    @AttributeOverride(name = IdentifierLangAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "HisIdentDB"))
    private IdentifierLangAttribuut hisIdentifierDatabase;

    @Embedded
    @AttributeOverride(name = JaAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "LeverenAlsStamgegeven"))
    private JaAttribuut leverenAlsStamgegeven;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "dataanvgel"))
    private DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "dateindegel"))
    private DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractElement() {
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
    protected AbstractElement(
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
        this.naam = naam;
        this.soort = soort;
        this.elementNaam = elementNaam;
        this.objecttype = objecttype;
        this.groep = groep;
        this.volgnummer = volgnummer;
        this.aliasVan = aliasVan;
        this.expressie = expressie;
        this.autorisatie = autorisatie;
        this.tabel = tabel;
        this.identificatieDatabase = identificatieDatabase;
        this.hisTabel = hisTabel;
        this.hisIdentifierDatabase = hisIdentifierDatabase;
        this.leverenAlsStamgegeven = leverenAlsStamgegeven;
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
        this.datumEindeGeldigheid = datumEindeGeldigheid;

    }

    /**
     * Retourneert ID van Element.
     *
     * @return ID.
     */
    protected Integer getID() {
        return iD;
    }

    /**
     * Retourneert Naam van Element.
     *
     * @return Naam.
     */
    public final NaamEnumeratiewaardeLangAttribuut getNaam() {
        return naam;
    }

    /**
     * Retourneert Soort van Element.
     *
     * @return Soort.
     */
    public final SoortElement getSoort() {
        return soort;
    }

    /**
     * Retourneert Element naam van Element.
     *
     * @return Element naam.
     */
    public final NaamEnumeratiewaardeAttribuut getElementNaam() {
        return elementNaam;
    }

    /**
     * Retourneert Objecttype van Element.
     *
     * @return Objecttype.
     */
    public final Element getObjecttype() {
        return objecttype;
    }

    /**
     * Retourneert Groep van Element.
     *
     * @return Groep.
     */
    public final Element getGroep() {
        return groep;
    }

    /**
     * Retourneert Volgnummer van Element.
     *
     * @return Volgnummer.
     */
    public final VolgnummerAttribuut getVolgnummer() {
        return volgnummer;
    }

    /**
     * Retourneert Alias van van Element.
     *
     * @return Alias van.
     */
    public final Element getAliasVan() {
        return aliasVan;
    }

    /**
     * Retourneert Expressie van Element.
     *
     * @return Expressie.
     */
    public final ExpressietekstAttribuut getExpressie() {
        return expressie;
    }

    /**
     * Retourneert Autorisatie van Element.
     *
     * @return Autorisatie.
     */
    public final SoortElementAutorisatie getAutorisatie() {
        return autorisatie;
    }

    /**
     * Retourneert Tabel van Element.
     *
     * @return Tabel.
     */
    public final Element getTabel() {
        return tabel;
    }

    /**
     * Retourneert Identificatie database van Element.
     *
     * @return Identificatie database.
     */
    public final IdentifierLangAttribuut getIdentificatieDatabase() {
        return identificatieDatabase;
    }

    /**
     * Retourneert Historie tabel van Element.
     *
     * @return Historie tabel.
     */
    public final Element getHisTabel() {
        return hisTabel;
    }

    /**
     * Retourneert Historie identificatie database van Element.
     *
     * @return Historie identificatie database.
     */
    public final IdentifierLangAttribuut getHisIdentifierDatabase() {
        return hisIdentifierDatabase;
    }

    /**
     * Retourneert Leveren als stamgegeven van Element.
     *
     * @return Leveren als stamgegeven.
     */
    public final JaAttribuut getLeverenAlsStamgegeven() {
        return leverenAlsStamgegeven;
    }

    /**
     * Retourneert de datum aanvang geldigheid voor Element.
     *
     * @return Datum aanvang geldigheid voor Element
     */
    public final DatumEvtDeelsOnbekendAttribuut getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Retourneert de datum einde geldigheid voor Element.
     *
     * @return Datum einde geldigheid voor Element
     */
    public final DatumEvtDeelsOnbekendAttribuut getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.ELEMENT;
    }

}
