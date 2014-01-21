(function(window, undefined) {
  var dictionary = {
    "dc7bc79a-d7e8-43e4-9e8e-fafd6c337db9": "RestaurantList",
    "ee42fa65-35a0-406a-aa4f-692c42798abb": "RestaurantDetail",
    "dfaebb38-c9ba-465e-ac32-994348c2d8c1": "SearchFavorite",
    "d12245cc-1680-458d-89dd-4f0d7fb22724": "FavoriteList",
    "f39803f7-df02-4169-93eb-7547fb8c961a": "Template 1",
    "5b810b84-27cf-496b-941d-d92c68c71c28": "sandbox"
  };

  var uriRE = /^(\/#)?(screens|templates|masters)\/(.*)(\.html)?/;
  window.lookUpURL = function(fragment) {
    var matches = uriRE.exec(fragment || "") || [],
        folder = matches[2] || "",
        canvas = matches[3] || "",
        name, url;
    if(dictionary.hasOwnProperty(canvas)) { /* search by name */
      url = folder + "/" + canvas;
    }
    return url;
  };

  window.lookUpName = function(fragment) {
    var matches = uriRE.exec(fragment || "") || [],
        folder = matches[2] || "",
        canvas = matches[3] || "",
        name, canvasName;
    if(dictionary.hasOwnProperty(canvas)) { /* search by name */
      canvasName = dictionary[canvas];
    }
    return canvasName;
  };
})(window);