var strTableTd;
var short_year;
var long_year;

$('.task_table').find('.date_format_year').each(function(){
	strTableTd = $(this).text().replace(/\s/g, '');
	if(strTableTd.length>0){
		short_year = strTableTd.charAt(5) + strTableTd.charAt(6) + strTableTd.charAt(7);
		long_year = '-20' + short_year.substr(1) + ' ';
		$(this).text(strTableTd.replace(short_year, long_year));
	}
});