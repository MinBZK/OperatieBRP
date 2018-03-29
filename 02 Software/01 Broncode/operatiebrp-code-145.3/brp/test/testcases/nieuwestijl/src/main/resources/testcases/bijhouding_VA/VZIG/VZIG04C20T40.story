Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: Nevenactie Registratie verstrekkingsbeperking

Scenario:   Volledige verstrekkingsbeperking, daarna Specifieke verstrekkingsbeperking waardoor de Volledige wordt beeindigd
            LT: VZIG04C20T40

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG04C20T40-001.xls

When voer een bijhouding uit VZIG04C20T40a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG04C20T40a.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R

Then in kern heeft SELECT COUNT(id) FROM kern.admhnd de volgende gegevens:
| veld                      | waarde |
| count                     | 2      |

When voer een bijhouding uit VZIG04C20T40b.xml namens partij 'Gemeente BRP 2'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG04C20T40b.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R

Then in kern heeft SELECT COUNT(id) FROM kern.admhnd de volgende gegevens:
| veld                      | waarde |
| count                     | 3      |

Then in kern heeft select count(1) from kern.his_persverstrbeperking v where v.actieinh is not null and v.tsreg is not null and  v.tsverval is null and v.actieverval is null de volgende gegevens:
| veld                      | waarde |
| count                     | 1      |

Then in kern heeft select count(1) from kern.persverstrbeperking v where v.partij is null and v.omsderde ='Carnavalsvereniging' and v.gemverordening ='27017' de volgende gegevens:
| veld                      | waarde |
| count                     | 1      |

Then in kern heeft select count(1) from kern.persverstrbeperking de volgende gegevens:
| veld                      | waarde |
| count                     | 1      |

Then in kern heeft select count(1) from kern.his_persverstrbeperking de volgende gegevens:
| veld                      | waarde |
| count                     | 1      |

Then in kern heeft select count(1) from kern.his_persindicatie v where v.actieinh is not null and v.tsreg is not null and  v.tsverval is not null and v.actieverval is not null and v.waarde ='true' de volgende gegevens:
| veld                      | waarde |
| count                     | 1      |

Then in kern heeft select count(1) from kern.persindicatie v where v.srt ='3' and v.waarde is null and v.indag ='false' de volgende gegevens:
| veld                      | waarde |
| count                     | 1      |

Then in kern heeft select count(1) from kern.his_persindicatie de volgende gegevens:
| veld                      | waarde |
| count                     | 1      |

Then in kern heeft select count(1) from kern.persindicatie de volgende gegevens:
| veld                      | waarde |
| count                     | 1      |

Then in kern heeft select indag, waarde from kern.persindicatie de volgende gegevens:
| veld                      | waarde |
| indag                     | false      |
| waarde                    | NULL      |



Then lees persoon met anummer 5042846753 uit database en vergelijk met expected VZIG04C20T40-persoon1.xml
