
(function ($) {
	"use strict";
	$('.column100').on('mouseover',function(){
		var table1 = $(this).parent().parent().parent();
		var table2 = $(this).parent().parent();
		var verTable = $(table1).data('vertable')+"";
		var column = $(this).data('column') + ""; 

		$(table2).find("."+column).addClass('hov-column-'+ verTable);
		$(table1).find(".row100.head ."+column).addClass('hov-column-head-'+ verTable);
	});

	$('.column100').on('mouseout',function(){
		var table1 = $(this).parent().parent().parent();
		var table2 = $(this).parent().parent();
		var verTable = $(table1).data('vertable')+"";
		var column = $(this).data('column') + ""; 

		$(table2).find("."+column).removeClass('hov-column-'+ verTable);
		$(table1).find(".row100.head ."+column).removeClass('hov-column-head-'+ verTable);
	});

})(jQuery);

function modal_show() {
	$('#modal_wrap').show();
	$('div.nav-see').removeClass('non_');
	$('div.nav-search').addClass('active');
}

function addHTML(post){
	return `
				<tr class="row100 body" onClick="loadPost(${post.id})">
					<td class="cell100 column1">${post.title}</td>
					<td class="cell100 column2">${post.author}</td>
					<td class="cell100 column3">${post.modifiedAt}</td>
				</tr>
			`;
}

function loadPost(id){
	window.location.href = "/board/post/" + id;
}