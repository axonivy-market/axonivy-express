function renderStartMenus() {
  if (window.parent.Portal) {
    $('.js-start-with-portal').removeClass('hidden');
  } else {
    $('.js-start-without-portal').removeClass('hidden');
  }
}