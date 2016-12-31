var updater = null;
var url = null;

  function getStatus(urlApplication)
  {
    $('charger').disabled = true;
    $('conteneur_erreur').style.display = "none";
    $('image_article').style.display = "none";
    $('erreurs').innerHTML = "";
    
    url = urlApplication;
    updater = new Ajax.PeriodicalUpdater('status', url + 'upload',{asynchronous:true, frequency:0.1, method: 'get', parameters: 'action=status', onFailure: reportError});
    return true;
  }

  function reportError(message)
  {
    $('charger').disabled = false;
    $('conteneur_erreur').style.display = "block";
    $('erreurs').innerHTML += "<li>&nbsp;" + message + "</li>";
  }

  function killUpdate(message)
  {
    $('charger').disabled = false;

    updater.stop();
    if(message != '')
    {
      $('status').innerHTML = '<div class="error"><b>Error processing results: ' + message + '</b></div>';
    }
    else
    {
      new Ajax.Updater('status', url + 'upload',{asynchronous:true, method: 'get', parameters: 'action=status', onFailure: reportError});
    }
  }
  
  function afficherVignette(photo,vignette)
  {
  	 $('image_article').style.display = "block";
  	 $('photoarticle').value = photo;
  	 $('vignettearticle').value = vignette;
  	 $('image').innerHTML = "<img src='" + url + "images/articles/" + photo +"'/>";
  	 $('vignette').innerHTML = "<img src='" + url + "images/articles/" + vignette +"'/>";
  }
 