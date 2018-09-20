/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.operationeel;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.attribuuttype.AanduidingAdresseerbaarObject;
import nl.bzk.brp.model.attribuuttype.AanduidingBijHuisnummer;
import nl.bzk.brp.model.attribuuttype.Adresregel;
import nl.bzk.brp.model.attribuuttype.AfgekorteNaamOpenbareRuimte;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Gemeentedeel;
import nl.bzk.brp.model.attribuuttype.Huisletter;
import nl.bzk.brp.model.attribuuttype.Huisnummer;
import nl.bzk.brp.model.attribuuttype.Huisnummertoevoeging;
import nl.bzk.brp.model.attribuuttype.IdentificatiecodeNummeraanduiding;
import nl.bzk.brp.model.attribuuttype.LocatieOmschrijving;
import nl.bzk.brp.model.attribuuttype.NaamOpenbareRuimte;
import nl.bzk.brp.model.attribuuttype.Postcode;
import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.groep.logisch.basis.PersoonAdresStandaardGroepBasis;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AangeverAdreshoudingIdentiteit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.FunctieAdres;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Land;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Plaats;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenWijzigingAdres;


/**
 * Implementatie voor standaard groep van persoon adres.
 *
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonAdresStandaardGroep extends AbstractGroep implements
        PersoonAdresStandaardGroepBasis
{

    @Column(name = "Srt")
    @Enumerated
    private FunctieAdres                      soort;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatAanvAdresh"))
    private Datum                             datumAanvangAdreshouding;

    @ManyToOne
    @JoinColumn(name = "RdnWijz")
    private RedenWijzigingAdres               redenwijziging;

    @Enumerated
    @Column(name = "AangAdresh")
    private AangeverAdreshoudingIdentiteit    aangeverAdreshouding;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "adresseerbaarObject"))
    private AanduidingAdresseerbaarObject               adresseerbaarObject;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "IdentcodeNraand"))
    private IdentificatiecodeNummeraanduiding identificatiecodeNummeraanduiding;

    @ManyToOne
    @JoinColumn(name = "Gem")
    private Partij                            gemeente;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "NOR"))
    private NaamOpenbareRuimte                naamOpenbareRuimte;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "AfgekorteNOR"))
    private AfgekorteNaamOpenbareRuimte       afgekorteNaamOpenbareRuimte;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Gemdeel"))
    private Gemeentedeel                      gemeentedeel;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Huisnr"))
    private Huisnummer                        huisnummer;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "huisletter"))
    private Huisletter                        huisletter;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "postcode"))
    private Postcode                          postcode;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Huisnrtoevoeging"))
    private Huisnummertoevoeging              huisnummertoevoeging;

    @ManyToOne
    @JoinColumn(name = "Wpl")
    private Plaats                            woonplaats;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "LoctovAdres"))
    private AanduidingBijHuisnummer                   locatietovAdres;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "LocOms"))
    private LocatieOmschrijving               locatieOmschrijving;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "BLAdresRegel1"))
    private Adresregel                        buitenlandsAdresRegel1;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "BLAdresRegel2"))
    private Adresregel                        buitenlandsAdresRegel2;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "BLAdresRegel3"))
    private Adresregel                        buitenlandsAdresRegel3;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "BLAdresRegel4"))
    private Adresregel                        buitenlandsAdresRegel4;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "BLAdresRegel5"))
    private Adresregel                        buitenlandsAdresRegel5;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "BLAdresRegel6"))
    private Adresregel                        buitenlandsAdresRegel6;

    @ManyToOne
    @JoinColumn(name = "Land")
    private Land                              land;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatVertrekUitNederland"))
    private Datum                             datumVertrekUitNederland;

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractPersoonAdresStandaardGroep() {

    }

    /**
     * .
     *
     * @param persoonAdresStandaardGroepBasis PersoonAdresStandaardGroepBasis
     */
    protected AbstractPersoonAdresStandaardGroep(final PersoonAdresStandaardGroepBasis persoonAdresStandaardGroepBasis)
    {
        soort = persoonAdresStandaardGroepBasis.getSoort();
        datumAanvangAdreshouding = persoonAdresStandaardGroepBasis.getDatumAanvangAdreshouding();
        redenwijziging = persoonAdresStandaardGroepBasis.getRedenWijziging();
        aangeverAdreshouding = persoonAdresStandaardGroepBasis.getAangeverAdresHouding();
        adresseerbaarObject = persoonAdresStandaardGroepBasis.getAanduidingAdresseerbaarObject();
        identificatiecodeNummeraanduiding = persoonAdresStandaardGroepBasis.getIdentificatiecodeNummeraanduiding();
        gemeente = persoonAdresStandaardGroepBasis.getGemeente();
        naamOpenbareRuimte = persoonAdresStandaardGroepBasis.getNaamOpenbareRuimte();
        afgekorteNaamOpenbareRuimte = persoonAdresStandaardGroepBasis.getAfgekorteNaamOpenbareRuimte();
        gemeentedeel = persoonAdresStandaardGroepBasis.getGemeentedeel();
        huisnummer = persoonAdresStandaardGroepBasis.getHuisnummer();
        huisletter = persoonAdresStandaardGroepBasis.getHuisletter();
        postcode = persoonAdresStandaardGroepBasis.getPostcode();
        huisnummertoevoeging = persoonAdresStandaardGroepBasis.getHuisnummertoevoeging();
        woonplaats = persoonAdresStandaardGroepBasis.getWoonplaats();
        locatietovAdres = persoonAdresStandaardGroepBasis.getLocatietovAdres();
        locatieOmschrijving = persoonAdresStandaardGroepBasis.getLocatieOmschrijving();
        buitenlandsAdresRegel1 = persoonAdresStandaardGroepBasis.getBuitenlandsAdresRegel1();
        buitenlandsAdresRegel2 = persoonAdresStandaardGroepBasis.getBuitenlandsAdresRegel2();
        buitenlandsAdresRegel3 = persoonAdresStandaardGroepBasis.getBuitenlandsAdresRegel3();
        buitenlandsAdresRegel4 = persoonAdresStandaardGroepBasis.getBuitenlandsAdresRegel4();
        buitenlandsAdresRegel5 = persoonAdresStandaardGroepBasis.getBuitenlandsAdresRegel5();
        buitenlandsAdresRegel6 = persoonAdresStandaardGroepBasis.getBuitenlandsAdresRegel6();
        land = persoonAdresStandaardGroepBasis.getLand();
        datumVertrekUitNederland = persoonAdresStandaardGroepBasis.getDatumVertrekUitNederland();
    }

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
    public AanduidingAdresseerbaarObject getAanduidingAdresseerbaarObject() {
        return adresseerbaarObject;
    }

    @Override
    public IdentificatiecodeNummeraanduiding getIdentificatiecodeNummeraanduiding() {
        return identificatiecodeNummeraanduiding;
    }

    @Override
    public Partij getGemeente() {
        return gemeente;
    }

    @Override
    public Postcode getPostcode() {
        return postcode;
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
    public AanduidingBijHuisnummer getLocatietovAdres() {
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
