#!/usr/bin/perl

use strict;
use Encode;
use Spreadsheet::ParseExcel;

my $sourcename = $ARGV[0] or die "invocation: $0 <source file> <stage> <excel>\n";
my $stage = $ARGV[1] or die "invocation: $0 <source file> <stage> <excel>\n";
my $excel = $ARGV[2] or die "invocation: $0 <source file> <stage> <excel>\n";

my $source_excel = new Spreadsheet::ParseExcel;
my $source_book = $source_excel->Parse($sourcename) or die "Could not open source Excel file $sourcename: $!";
my $storage_book;

# only first sheet
my $source_sheet = $source_book->{Worksheet}[0];
next unless defined $source_sheet->{MaxRow};
next unless $source_sheet->{MinRow} <= $source_sheet->{MaxRow};
next unless defined $source_sheet->{MaxCol};
next unless $source_sheet->{MinCol} <= $source_sheet->{MaxCol};

my $current_cat;
my $current_grp;
foreach my $row_index ($source_sheet->{MinRow} .. $source_sheet->{MaxRow}) {
	foreach my $col_index ($source_sheet->{MinCol} .. $source_sheet->{MaxCol}) {
		my $source_cell = $source_sheet->{Cells}[$row_index][$col_index];

		if ($col_index == 1) {
			if ($source_cell) {
				$current_cat = Encode::decode( 'ISO-8859-1', Encode::encode_utf8( $source_cell->Value ) );
			}
		} elsif ($col_index == 2) {
			if (! $source_cell) {
				$current_grp = $current_cat;
			}
		}

		if ($source_cell) {
			my $value = Encode::decode( 'ISO-8859-1', Encode::encode_utf8( $source_cell->Value ) );
			$_=$value;
			if ( m/(VandaagMinus)([^}]*)/ || m/(VandaagPlus)([^}]*)/ ) {
				my $f = $1;
				my $num = $2;

				my $days=$num*24*60*60;
				my ($day, $month, $year);
				if ( $f =~ m/VandaagMinus/ ) {
					($day, $month, $year) = (localtime(time()-$days))[3,4,5];
				} else {
					($day, $month, $year) = (localtime(time()+$days))[3,4,5];
				}
				$value = sprintf("%04d%02d%02d", ($year+1900), ($month+1), $day);
				system("bash", "save_to_placeholder", "$stage", "$excel", "dummy", "$f$num", "$current_grp.$current_cat" );
			}

			print  $value, ";";
		} else {
			print  ";";
		}
		
		undef $source_cell;
	}
	print "\n";
} 

undef $source_sheet;
undef $storage_book;
undef $source_book;
undef $source_excel;
