function Globals(){
    this.borderWidth=2;
    this.cellSize=40;
    this.indexes2Coords=function(row,col){
        return {"x":((this.borderWidth*2+this.cellSize)*row),
                "y":((this.borderWidth*2+this.cellSize)*col)};
    }
    this.indexes2Transform=function(row,col){
        return "translate("+((this.borderWidth*2+this.cellSize)*row)+"px,"+((this.borderWidth*2+this.cellSize)*col)+"px)";
    }
}






function Tile(number,row,col,svg){
    var target=svg;
    var styleHandle=undefined;
    var num=null;
    var numberStyleHandle=null;
    var numberTextHandle=null;
    var tileHandle=null;
    var r=row;
    var c=col;


    this.setOpacity=function(value){
        styleHandle.opacity=value;
    }
    this.setTransform=function(value){
        styleHandle.transform=value;
    }


    this.listenForMovementTurn=function(movementChainFunction){
        tileHandle.addEventListener("transitionend",movementChainFunction);
    }
    this.removeMovementListener=function(movementChainFunction){
        tileHandle.removeEventListener("transitionend",movementChainFunction);
    }


    this.getNumber=function(){
        return num;
    }
    this.setNumber=function(fnumber){
        numberTextHandle.innerHTML=fnumber;
        numberStyleHandle.classList=["n"+fnumber];
        num=fnumber;
    }

    this.updatePosition=function(tileHolder){//need to test
        r=tileHolder.getPosition().r;
        c=tileHolder.getPosition().c;
    }

    var render=function(){


        var tile=document.createElementNS('http://www.w3.org/2000/svg',"g");
        tile.style="transform: translate("+((globals.borderWidth*2+globals.cellSize)*row)+"px,"+((globals.borderWidth*2+globals.cellSize)*col)+"px)";
        tile.classList=["tile"];
        this.classList=tile.classList;
        styleHandle=tile.style;


        var border=document.createElementNS('http://www.w3.org/2000/svg',"g");
        border.setAttribute("class","border");
        border.style="transform: translate(5px,5px)";
        for(var x=0;x<2;x++){
            for(var y=0;y<2;y++){
                var cornerCircle=document.createElementNS('http://www.w3.org/2000/svg',"circle");
                cornerCircle.setAttribute("r",""+globals.borderWidth+"px");
                cornerCircle.setAttribute("cx",""+(x*globals.cellSize)+"px");
                cornerCircle.setAttribute("cy",""+(y*globals.cellSize)+"px");
                var edge=document.createElementNS('http://www.w3.org/2000/svg',"rect");
                var h=globals.cellSize;
                var w=globals.borderWidth;
                var attr="x";
                if(x==0){
                    var tmp=h;
                    h=w;
                    w=tmp;
                    attr="y";
                }
                var pos=-globals.borderWidth;
                if(y==0){
                    pos=globals.cellSize;
                }		
                edge.style="height: "+h+"px; width: "+w+"px;";
                edge.setAttribute(attr,""+pos+"px");
                border.appendChild(cornerCircle);
                border.appendChild(edge);
            }
        }
        var inner=document.createElementNS('http://www.w3.org/2000/svg',"g");
        inner.setAttribute("class","n"+num);
        numberStyleHandle=inner;
        inner.style="transform: translate(5px,5px)";
        var box=document.createElementNS('http://www.w3.org/2000/svg',"rect");
        box.style="height: "+globals.cellSize+"px; width: "+globals.cellSize+"px; stroke: black";
        var text=document.createElementNS('http://www.w3.org/2000/svg',"text");
        text.setAttribute("dy","25px");
        text.setAttribute("dx","15px");
        numberTextHandle=text;
        inner.appendChild(box); 
        inner.appendChild(text);
        tile.appendChild(border);
        tile.appendChild(inner);
        tile.addEventListener("click",function(){console.log(""+r+c+" clicked");});
        tileHandle=tile;
        svg.appendChild(tile);
    }	

    render();
    this.setNumber(number);

    
}
function TileHolder(row,col,number,svg,tileGrid){
    var transform=globals.indexes2Transform(row,col);
    var tile=new Tile(number,row,col,svg);

    this.getPosition=function(){
        return {"r":row,"c":col};
    }

    this.getTransfrom=function(){
        return transform;
    }
    this.move=function(toTileHolder,shouldIncrement){
        tile.setOpacity(0);
        tile.setTransform(toTileHolder.getTransfrom());
        var movementChainFunction=function(e){
            if(e.propertyName=="opacity"&&shouldIncrement){
                tileGrid.runNext();
            }
        }
        tile.listenForMovementTurn(movementChainFunction);
    }
}

function TileGrid(rows,cols,svg,numbers,transitionsList){//TODO: make singleton
    this.testUpdate=function(id){
        grid[0][0].setId("2");
    }
    var grid=[];//of arrays of tileholders
    for(var i=0;i<cols;i++){
        var row=[];
        for(var j=0;j<rows;j++){
            row.push(new TileHolder(j,i,numbers[i][j],svg,this));
        }
        grid.push(row);
    }
    this.resetWith=function(newList){
        this.transitionsList=newList;
        this.currentTransition=0;
    },
    this.runNext=function(){
        if(this.currentTransition<this.transitionsList.length){
            var concurrentTransitionList=this.transitionsList[this.currentTransition];
            var frow=concurrentTransitionList[0].fromNdxs.x;
            var fcol=concurrentTransitionList[0].fromNdxs.y;
            var trow=concurrentTransitionList[0].toNdxs.x;
            var tcol=concurrentTransitionList[0].toNdxs.y;
            grid[frow][fcol].move(grid[trow][tcol],true);
            for(var i=1;i<concurrentTransitionList.length;i++){
                var frow=concurrentTransitionList[i].fromNdxs.x;
                var fcol=concurrentTransitionList[i].fromNdxs.y;
                var trow=concurrentTransitionList[i].toNdxs.x;
                var tcol=concurrentTransitionList[i].toNdxs.y;
                grid[frow][fcol].move(grid[trow][tcol],false);
            }
            this.currentTransition++;
        }else{
            console.log("done");
        }
    }
    this.resetWith(transitionsList);
}


var main=function(){
    globals=new Globals();
    var display=document.getElementById("display");

    var buildTemplate=[[1,2,3,2,4],
                       [2,4,3,1,1],
                       [1,1,1,1,1],
                       [1,2,3,1,1],
                       [1,1,1,1,1]];
    var sampleTransitionList=[
    //server bfs should return this after tile is clicked
        [
            {'fromNdxs': {'x':2,'y':0},'toNdxs': {'x':2,'y':1}}
        ],
        [
            {'fromNdxs': {'x':3,'y':0},'toNdxs': {'x':4,'y':0}},
            {'fromNdxs': {'x':2,'y':1},'toNdxs': {'x':2,'y':2}}
        ],
        [
            {'fromNdxs': {'x':4,'y':0},'toNdxs': {'x':4,'y':1}},
            {'fromNdxs': {'x':2,'y':2},'toNdxs': {'x':2,'y':3}},
            {'fromNdxs': {'x':1,'y':3},'toNdxs': {'x':2,'y':3}}
        ],
        [
            {'fromNdxs': {'x':4,'y':1},'toNdxs': {'x':4,'y':2}},
            {'fromNdxs': {'x':1,'y':4},'toNdxs': {'x':2,'y':4}},
            {'fromNdxs': {'x':2,'y':3},'toNdxs': {'x':3,'y':3}}
        ],
        [
            {'fromNdxs': {'x':4,'y':2},'toNdxs': {'x':4,'y':3}},
            {'fromNdxs': {'x':2,'y':4},'toNdxs': {'x':3,'y':4}},
            {'fromNdxs': {'x':3,'y':3},'toNdxs': {'x':3,'y':4}}
        ],
        [
            {'fromNdxs': {'x':4,'y':3},'toNdxs': {'x':4,'y':4}},
            {'fromNdxs': {'x':3,'y':4},'toNdxs': {'x':4,'y':4}}
        ]
    ];
    grid=new TileGrid(5,5,display,buildTemplate,sampleTransitionList);

}

