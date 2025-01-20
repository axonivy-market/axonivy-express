function updateSelectedIConDisplay(iconCode, type, clientId) {
  var iconDisplayJQ = $(PrimeFaces.escapeClientId(clientId + ":awesome-icon-display"));
  updateIconDisplay(iconDisplayJQ, iconCode, type);
  var iconDisplayForLink = $(PrimeFaces.escapeClientId(clientId + ":selection-icon-display"));
  updateIconDisplay(iconDisplayForLink, iconCode, type);

  var iconHiddenValueJQ = $(PrimeFaces.escapeClientId(clientId + ":awesome-icon-hidden-value"));
  iconHiddenValueJQ.val(iconCode);
}

function updateIconDisplay(iconDisplayJQ, iconCode, type) {
  if (iconDisplayJQ === undefined || iconDisplayJQ.length == 0) {
    return;
  }
  iconDisplayJQ.removeClass();
  iconDisplayJQ.toggleClass(type === "awesome" ? "fa fa-2x" : "si si-xl");
  iconDisplayJQ.toggleClass("vertical-align-middle");
  iconDisplayJQ.addClass(iconCode);
}

function loadStreamlineIcon(clientId) {
  var iconsStylesheet = Object.values(document.styleSheets).filter(sheet => sheet.href?.includes("StreamlineIcons.css"))[0];
  var icons = Object.values(iconsStylesheet.rules).filter(rule => rule.selectorText?.startsWith(".si-"));
  icons.sort((a, b) => (a.selectorText > b.selectorText) ? 1 : -1);

  var selectionIconDialogId = clientId + "-select-icon-dialog";
  var container = document.getElementById(clientId + ":icons-selection-form:icons");
  container.innerHTML = "";
  icons.forEach(icon => {
    var iconCode = icon.selectorText.substring(1, icon.selectorText.length - 8);
    var iconImage = document.createElement("i");
    iconImage.className = "si " + iconCode + " si-lg";
    var iconTitle = document.createElement("p");
    iconTitle.appendChild(document.createTextNode("si " + iconCode));
    
    var iconAnchor = document.createElement("a");
    iconAnchor.className = "icon-selection-dialog-selecting-icon";
    iconAnchor.appendChild(iconImage);
    iconAnchor.title = iconCode;
    iconAnchor.onclick = function() {
      updateSelectedIConDisplay(iconCode, "streamline", clientId);
      PF(selectionIconDialogId).hide();
      return false;
    };
    
    container.appendChild(iconAnchor);
  });
  
  var searchField = document.getElementById(clientId + ":search-icon-name-field");
  searchIconByName(searchField);
  PF(selectionIconDialogId).initPosition();
}

function searchIconByName(element) {
  var keyword = element.value.toLowerCase();
  var icons = $(".icon-selection-dialog-selecting-icon");
  for (i = 0; i < icons.length; i++) {
    var icon = icons[i].innerHTML;
    if (icon.indexOf(keyword) > -1 || icon.split("-").join(" ").indexOf(keyword) > -1) {
      icons[i].style.display= "";
    } else {
    icons[i].style.display= "none";
    }
  }
}