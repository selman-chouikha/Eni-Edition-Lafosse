$(document).ready(function()
{
	var idArticle;
	var over = function()
	{
		$(this).fadeTo(100,0.6);
		$(this).addClass("drag");
		$(this).attr({title: "Sélectionner la vignette puis déplacer le curseur vers le panier pour ajouter ce DVD"});
		idArticle = $(this).attr("id");
	}
	
	var out = function()
	{
		$(this).fadeTo(300, 1 );
	}
	
	$("div.img").hover(over,out);
	$("div.img").draggable({ helper: 'clone', revert: true });
	$("div.drop").droppable({
		accept: "div.img",
		tolerance: "pointer",
		drop: function(ev, ui) 
		{
			$.ajax(
			{
				type: "GET",
				url: "gestionPanier?action=ajouter",
				dataType: "xml",
				data: "idArticle="+idArticle+"&methode=ajax",
				timeout : 8000,
				success: function(xml)
				{
				
				}
			});
		}
	});
});