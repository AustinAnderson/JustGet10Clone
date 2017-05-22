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






function Tile(number){
    var styleHandle=undefined;
    var numberStyleHandle=null;
    var numberTextHandle=null;
    var tileHandle=null;


    this.getDom=function(){
        return tileHandle;
    }
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
        return number;
    }

    var setNumber=function(fnumber){
        numberTextHandle.innerHTML=fnumber;
        numberStyleHandle.classList=["n"+fnumber];
    }

    this.remove=function(){
        tileHandle.remove();
    }

    var render=function(){


        var tile=document.createElementNS('http://www.w3.org/2000/svg',"g");
        tile.classList=["tile"];
        this.classList=tile.classList;
        styleHandle=tile.style;


        var border=document.createElementNS('http://www.w3.org/2000/svg',"g");
        border.setAttribute("class","border");
        border.style="transform: translate(5px,5px)";
        var edgeStrs=[["bottom","top"],
                      ["right","left"]];
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
                edge.setAttribute("height",""+h+"px");
                edge.setAttribute("width",""+w+"px");
                edge.setAttribute(attr,""+pos+"px");
                edge.setAttribute("class",edgeStrs[x][y]+"Edge");
                border.appendChild(cornerCircle);
                border.appendChild(edge);
            }
        }
        var inner=document.createElementNS('http://www.w3.org/2000/svg',"g");
        inner.setAttribute("class","n"+number);
        numberStyleHandle=inner;
        inner.style="transform: translate(5px,5px)";
        var box=document.createElementNS('http://www.w3.org/2000/svg',"rect");
        box.setAttribute("height",""+globals.cellSize+"px");
        box.setAttribute("width",""+globals.cellSize+"px");
        box.style="stroke: black";
        var text=document.createElementNS('http://www.w3.org/2000/svg',"text");
        text.setAttribute("dy","25px");
        text.setAttribute("dx","15px");
        numberTextHandle=text;
        inner.appendChild(box); 
        inner.appendChild(text);
        tile.appendChild(border);
        tile.appendChild(inner);
        tileHandle=tile;
    }	

    render();
    setNumber(number);

    
}
function TileHolder(row,col,number,svg,tileGrid){
    var tileHolderDom=document.createElementNS('http://www.w3.org/2000/svg',"g");
    var translateX=((globals.borderWidth*2+globals.cellSize)*row);
    var translateY=((globals.borderWidth*2+globals.cellSize)*col);
    tileHolderDom.style.transform="translate("+translateX+"px,"+translateY+"px)";
    tileHolderDom.addEventListener("click",function(){console.log(""+row+col+"clicked");},true);
    tileHolderDom.setAttribute("class","bob");
    var shapeHolder=document.createElementNS('http://www.w3.org/2000/svg',"rect");
    var sideLength=(globals.borderWidth*2+globals.cellSize);
    (function(){
        var shapeHolderSideLength=""+sideLength+"px";
        shapeHolder.setAttribute("height",shapeHolderSideLength);
        shapeHolder.setAttribute("width",shapeHolderSideLength);
    }())
    shapeHolder.style.transform="translate("+globals.borderWidth+"px,"+globals.borderWidth+"px)";
    shapeHolder.style.opacity=0;


    tileHolderDom.appendChild(shapeHolder);
    var tile=new Tile(number);
    tileHolderDom.appendChild(tile.getDom());

    svg.appendChild(tileHolderDom);

    var markedForDeletion=false;

    //this is the downside of using scope alone to emulate access modifiers;
    //in java/c/c++/c#, instances of the same class can acess each other's
    //private members, which isn't the case here
    this.getTile=function(){
        return tile;
    }
    this.setTile=function(newTile){
        tile=newTile;
    }

    this.getTranslateX=function(){
        return translateX;
    }
    this.getTranslateY=function(){
        return translateY;
    }

    this.isEmpty=function(){
        //return tileHolderDom.children.length>1;//only the click rectangle if empty
        return tile===null;//only the click rectangle if empty
    }

    this.pullNewTile=function(nextNumber){
        tile=new Tile(nextNumber);
        var y=-translateY-sideLength;
        tile.setTransform("translate(0px,"+y+"px)");
        tileHolderDom.appendChild(tile.getDom());
    }

    this.pullTile=function(fromTileHolder){

        
        var y=fromTileHolder.getTranslateY()-translateY;
        tile=new Tile(fromTileHolder.getTile().getNumber());
        tile.setTransform("translate(0px,"+y+"px)");
        fromTileHolder.delete();
        tileHolderDom.appendChild(tile.getDom());

    }
    this.move=function(toTileHolder,shouldIncrement){
        var x=toTileHolder.getTranslateX()-translateX;
        var y=toTileHolder.getTranslateY()-translateY;
        tile.setOpacity(0);
        tile.setTransform("translate("+x+"px,"+y+"px)");
        tile.listenForMovementTurn(
            function(e){
                if(e.propertyName=="opacity"&&shouldIncrement){
                    tileGrid.runNext();
                }
            }
        );
        markedForDeletion=true;
    }
    this.delete=function(){
        tile.remove();
        tile=null;
    }
    this.deleteIfNeeded=function(){
        if(markedForDeletion){
            tile.remove();
            tile=null;
            markedForDeletion=false;
        }
    }
}

function TileGrid(rows,cols,svg,numbers,transitionsList,replaceList){//TODO: make singleton
    var grid=[];//of arrays of tileholders
    this.debugGetGridDeleteMe=function(){
        return grid;
    }
    for(var i=0;i<cols;i++){
        var row=[];
        for(var j=0;j<rows;j++){
            row.push(new TileHolder(j,i,numbers[i][j],svg,this));
        }
        grid.push(row);
    }
    this.resetWith=function(newTransitionList,newReplaceList){
        this.transitionsList=newTransitionList;
        this.replaceList=newReplaceList;
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
        }else{//done
            for(i=0;i<grid.length;i++){
                for(j=0;j<grid.length;j++){
                    grid[i][j].deleteIfNeeded();
                }
            }
            for(j=grid.length-1;j>=0;j--){
                for(i=grid.length-1;i>=0;i--){
                    if(grid[i][j].isEmpty()){
                        rowNum=i-1;
                        while(rowNum>=0&&grid[rowNum][j].isEmpty()){
                            rowNum--;
                        }
                        if(rowNum>=0){
                            grid[i][j].pullTile(grid[rowNum][j]);
                        }else{
                            grid[i][j].pullNewTile(replaceList.pop());
                            //new tile from the top with number from list
                        }
                    }
                }
            }
            
            for(i=0;i<grid.length;i++){
                for(j=0;j<grid.length;j++){
                    if(!grid[i][j].isEmpty()){
                        grid[i][j].getTile().setTransform("translate(0px,0px)");//why doesn't this animate!!!!????
                    }
                }
            }
            
        }
    }
    this.resetWith(transitionsList,replaceList);
}


var main=function(){
    globals=new Globals();
    var display=document.getElementById("display");

    //*
    var buildTemplate=[[1,2,3,2,4],
                       [2,4,3,1,1],
                       [1,1,1,1,1],
                       [1,2,3,1,1],
                       [1,1,1,1,1]];

    //server bfs should return these after tile is clicked

    var replaceList=[1,1,2,1,3,1,3,4,1,3,1,1,2,2];
    //*/

    var sampleTransitionList=[
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
    grid=new TileGrid(5,5,display,buildTemplate,sampleTransitionList,replaceList);

}

