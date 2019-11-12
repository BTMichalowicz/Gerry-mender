    /*Functions*/    
    function onEachFeature(feature, layer) {

        layer.on("mouseover", function (e) {

            var popcontent = "District "+feature.properties.DISTRICT  +
                "<br/>population : 12312312" +
                "<br/>White : 123123" +
                "<br/>Minority : 123123" +
                "<br/>Hispanic/Latino : 123123" +
                "<br/>Asian : 123123";

            lat = e.latlng.lat;
            lng = e.latlng.lng;

            popup1 = L.popup()
                .setLatLng([lat, lng])
                .setContent(popcontent)
                .openOn(map);
        });

        layer.on("mouseout", function (e) {
            $(popup1.remove());
        });

        layer.on("click", function (e) {
            $(toggleInfoSlider( feature ));



        });
    }

    // active slider menu
    function toggleSlider() {
        if (sliderState === 0) { //Open slider
            document.getElementById("slide-menu").style.width = "350px";
            sliderState = 1;
            setTimeout(moveZoomControl, 0);

        } else {
            document.getElementById("slide-menu").style.width = "0";
            sliderState = 0;
            setTimeout(moveZoomControl, 200);
        }
    }

    function moveZoomControl() {
        if (sliderState === 1) { //Open slider
            container = map.zoomControl.getContainer(),
                containerTop = 350 + 'px';
            container.style.position = 'absolute';
            container.style.left = containerTop;

        } else {
            container = map.zoomControl.getContainer(),
                containerTop = 0 + 'px';
            container.style.position = 'absolute';
            container.style.left = containerTop;
        }
    }

    function toggleInfoSlider( feature ) {
        if (infoStat === feature.properties.NAME) { //Open slider

            document.getElementById("slide-info").style.width = "0";
            infoStat = "null";

        } else {

            document.getElementById("slide-info").style.width = "350px";
            infoStat = feature.properties.NAME;
            fillOutTable(feature);

        }
    }

    function closeInfo() {
        document.getElementById("slide-info").style.width = "0";
        infoStat = "null";
    }

    function fillOutTable(feature) {
        $(document).ready(function () {

            $("#itemList tr").remove();

            var items = [
                {Attr: "Name", Amout: feature.properties.NAME},
                {Attr: "population", Amout: "50"},
                {Attr: "White", Amout: "50"},
                {Attr: "Minority", Amout: "50"},
                {Attr: "Hispanic", Amout: "50"},
                {Attr: "Asian", Amout: "50"},
                {Attr: "Republican", Amout: "50"},
                {Attr: "Democratic", Amout: "50"},

            ];

            $("#itemTemplate").tmpl(items).appendTo("#itemList tbody");
        });
    }

    function show_value(x, id){
    document.getElementById(id).innerHTML=x;
    }
