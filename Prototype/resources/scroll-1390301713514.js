(function(window, undefined) {

    /*********************** START STATIC ACCESS METHODS ************************/

    jQuery.extend(jimMobile, {
        "loadScrollBars": function() {
            jQuery(".s-dc7bc79a-d7e8-43e4-9e8e-fafd6c337db9 .ui-page").overscroll({ showThumbs:true, direction:'multi' });
            jQuery(".s-ee42fa65-35a0-406a-aa4f-692c42798abb .ui-page").overscroll({ showThumbs:true, direction:'multi' });
            jQuery(".s-dfaebb38-c9ba-465e-ac32-994348c2d8c1 .ui-page").overscroll({ showThumbs:true, direction:'multi' });
            jQuery(".s-dfaebb38-c9ba-465e-ac32-994348c2d8c1 #s-Category_3").overscroll({ showThumbs:false, direction:'vertical' });
            jQuery(".s-d12245cc-1680-458d-89dd-4f0d7fb22724 .ui-page").overscroll({ showThumbs:true, direction:'multi' });
         }
    });

    /*********************** END STATIC ACCESS METHODS ************************/

}) (window);