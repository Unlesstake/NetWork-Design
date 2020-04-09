var map = new BMap.Map("allmap", {
    minZoom:4,maxZoom:25,//缩放设置
    enableMapClick: false //关闭自带标签
}); // 创建Map实例
map.centerAndZoom("重庆", 11); // 初始化地图,设置中心点坐标和地图级别
//添加地图类型控件
map.addControl(new BMap.MapTypeControl({
    mapTypes: [
        BMAP_NORMAL_MAP,
        BMAP_HYBRID_MAP
    ]
}));
map.setCurrentCity("重庆"); // 设置地图显示的城市 此项是必须设置的
map.enableScrollWheelZoom(true);
var mapstyle={style:"googlelite"};
map.setMapStyle(mapstyle);

function getdistrict() {
    getBoundary("重庆市石柱土家族自治县");
    getBoundary("重庆市江北区");
    getBoundary("重庆市渝中区");
    getBoundary("重庆市万州区");
    getBoundary("重庆市涪陵区");
    getBoundary("重庆市大渡口区");
    getBoundary("重庆市沙坪坝区");
    getBoundary("重庆市九龙坡区");
    getBoundary("重庆市南岸区");
    getBoundary("重庆市北碚区");
    getBoundary("重庆市綦江区");
    getBoundary("重庆市大足区");
    getBoundary("重庆市渝北区");
    getBoundary("重庆市巴南区");
    getBoundary("重庆市黔江区");
    getBoundary("重庆市长寿区");
    getBoundary("重庆市江津区");
    getBoundary("重庆市合川区");
    getBoundary("重庆市永川区");
    getBoundary("重庆市南川区");
    getBoundary("重庆市璧山区");
    getBoundary("重庆市铜梁区");
    getBoundary("重庆市潼南区");
    getBoundary("重庆市荣昌区");
    getBoundary("重庆市开州区");
    getBoundary("重庆市梁平区");
    getBoundary("重庆市武隆区");
    getBoundary("重庆市城口县");
    getBoundary("重庆市丰都县");
    getBoundary("重庆市垫江县");
    getBoundary("重庆市忠县");
    getBoundary("重庆市云阳县");
    getBoundary("重庆市奉节县");
    getBoundary("重庆市巫山县");
    getBoundary("重庆市巫溪县");
    getBoundary("重庆市秀山土家族苗族自治县");
    getBoundary("重庆市酉阳土家族苗族自治县");
    getBoundary("重庆市彭水苗族土家族自治县");
    getBoundary("四川省");

}

function getBoundary(districtname) {
    var plys = [];
    var bdary = new BMap.Boundary();
    var line = bdary.get(districtname, function(rs) {
        var district = new Object();
        var count = rs.boundaries.length;
        //建立多边形覆盖物
        for(var i = 0; i < count; i++) {
            district.ply = new BMap.Polygon(rs.boundaries[i], {
                strokeWeight: 2,
                strokeOpacity: 0.8,
                StrokeStyle: "solid",
                strokeColor: "#1abc9c",
                fillColor: "#ffffff",
                fillOpacity: 0.2
            });
            plys.push(district.ply);
            map.addOverlay(district.ply); //添加覆盖物
        }
        district.ply.disableMassClear(); //添加不可去除标签
        return district;
    });
}
setTimeout(function() {
    getdistrict();
}, 100);