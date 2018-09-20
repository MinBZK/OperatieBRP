/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.impl.gen;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import nl.bzk.brp.model.attribuuttype.GeslachtsnaamComponent;
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.attribuuttype.ScheidingsTeken;
import nl.bzk.brp.model.attribuuttype.Voornaam;
import nl.bzk.brp.model.attribuuttype.Voorvoegsel;
import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.groep.interfaces.gen.PersoonAanschrijvingGroepBasis;
import nl.bzk.brp.model.objecttype.statisch.Predikaat;
import nl.bzk.brp.model.objecttype.statisch.StatusHistorie;
import nl.bzk.brp.model.objecttype.statisch.WijzeGebruikGeslachtsnaam;
import org.hibernate.annotations.Type;


/**
 * .
 *
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonAanschrijvingGroepMdl extends AbstractGroep implements
        PersoonAanschrijvingGroepBasis
{

    @Column(name = "gebrgeslnaamegp")
    @Enumerated
    private WijzeGebruikGeslachtsnaam gebruikGeslachtsnaam;

    @Column(name = "indaanschrmetadellijketitels")
    @Type(type = "JaNee")
    private JaNee                     indAanschrijvenMetAdellijkeTitel;

    @Column(name = "indaanschralgoritmischafgele")
    @Type(type = "JaNee")
    private JaNee                     indAanschrijvingAlgorthmischAfgeleid;

    @ManyToOne
    @JoinColumn(name = "predikaataanschr")
    private Predikaat                 predikaat;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "voornamenaanschr"))
    private Voornaam                  voornamen;

    @AttributeOverride(name = "waarde", column = @Column(name = "voorvoegselaanschr"))
    private Voorvoegsel               voorvoegsel;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "scheidingstekenaanschr"))
    private ScheidingsTeken           scheidingsteken;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "geslnaamaanschr"))
    private GeslachtsnaamComponent    geslachtsnaam;

    @Transient
    private StatusHistorie            statusHistorie;

    @Override
    public WijzeGebruikGeslachtsnaam getGebruikGeslachtsnaam() {
        return gebruikGeslachtsnaam;
    }

    @Override
    public JaNee getIndAanschrijvenMetAdellijkeTitel() {
        return indAanschrijvenMetAdellijkeTitel;
    }

    @Override
    public JaNee getIndAanschrijvingAlgorthmischAfgeleid() {
        return indAanschrijvingAlgorthmischAfgeleid;
    }

    @Override
    public Predikaat getPredikaat() {
        return predikaat;
    }

    @Override
    public Voornaam getVoornamen() {
        return voornamen;
    }

    @Override
    public Voorvoegsel getVoorvoegsel() {
        return voorvoegsel;
    }

    @Override
    public ScheidingsTeken getScheidingsteken() {
        return scheidingsteken;
    }

    @Override
    public GeslachtsnaamComponent getGeslachtsnaam() {
        return geslachtsnaam;
    }

    @Override
    public StatusHistorie getStatusHistorie() {
        return statusHistorie;
    }
}
