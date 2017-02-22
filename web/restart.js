function Tile(tileHolder,number,row,col){
    this.classList;
    this.transform;
    this.opacity;
    var id=null;
    var numberStyleHandle=null;
    var numberTextHandle=null;


    this.setNumber=function(fnumber){
        numberTextHandle=fnumber;
        numberStyleHandle=["n"+fnumber];
        num=fnumber;
    }


    var start=function(){
        if(id!==null){
            var element=document.getElementById(id);
            if(element!==null){
                element.remove();
            }
        }
        


        var width=2;
        var space=40;
        var tile=document.createElementNS('http://www.w3.org/2000/svg',"g");
        tile.style="transform: translate("+((width*2+space)*row)+"px,"+((width*2+space)*col)+"px)";
        id="t"+col+row;
        tile.classList=["tile"];

        this.classList=tile.classList;
        this.transform=tile.style.transfrom;
        this.opacity=tile.style.opacity;


        var border=document.createElementNS('http://www.w3.org/2000/svg',"g");
        border.setAttribute("class","border");
        border.style="transform: translate(5px,5px)";
        for(var x=0;x<2;x++){
            for(var y=0;y<2;y++){
                var cornerCircle=document.createElementNS('http://www.w3.org/2000/svg',"circle");
                cornerCircle.setAttribute("r",""+width+"px");
                cornerCircle.setAttribute("cx",""+(x*space)+"px");
                cornerCircle.setAttribute("cy",""+(y*space)+"px");
                var edge=document.createElementNS('http://www.w3.org/2000/svg',"rect");
                var h=space;
                var w=width;
                var attr="x";
                if(x==0){
                    var tmp=h;
                    h=w;
                    w=tmp;
                    attr="y";
                }
                var pos=-width;
                if(y==0){
                    pos=space;
                }		
                edge.style="height: "+h+"px; width: "+w+"px;";
                edge.setAttribute(attr,""+pos+"px");
                border.appendChild(cornerCircle);
                border.appendChild(edge);
            }
        }
        var inner=document.createElementNS('http://www.w3.org/2000/svg',"g");
        inner.setAttribute("class","n"+num);
        numberStyleHandle=inner.classList;
        inner.style="transform: translate(5px,5px)";
        var box=document.createElementNS('http://www.w3.org/2000/svg',"rect");
        box.style="height: 40px; width: 40px; stroke: black";
        var text=document.createElementNS('http://www.w3.org/2000/svg',"text");
        text.setAttribute("dy","25px");
        text.setAttribute("dx","15px");
        text.innerHTML="";
        numberTextHandle=text.innerHTML;
        inner.appendChild(box);
        inner.appendChild(text);
        tile.appendChild(border);
        tile.appendChild(inner);
        tile.addEventListener("click",function(){console.log(this.id+" clicked");});
        
        document.getElementById("display").appendChild(createTile());
    }	

    start();
    this.setNumber(number);
    
}

function TileHolder(row,col,number){
    
}


function TileGrid(rows,cols){
    var grid=[];//of arrays of tileholders
    for(var i=0;i<cols;i++){
        var row=[];
        for(var j=0;j<rows;j++){
            row.push(new TileHolder(j,i));
        }
        grid.push(row);
    }
}


}
var main=function(){

}
