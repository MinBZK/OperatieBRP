sudo -u postgres psql brp -f stamgegevensStatisch-latin1.sql
sudo -u postgres psql brp -f stamgegevensLand-latin1.sql
sudo -u postgres psql brp -f stamgegevensPartijGemeente-latin1.sql
sudo -u postgres psql brp -f stamgegevensPlaats-latin1.sql
sudo -u postgres psql brp -f stamgegevensNationaliteit-latin1.sql
sudo -u postgres psql brp -f stamgegevensSoortNLReisdocument.sql
sudo -u postgres psql brp -f stamgegevensAutVanAfgifteReisdoc.sql
sudo -u postgres psql brp -f testdata.sql
