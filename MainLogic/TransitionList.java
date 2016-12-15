package MainLogic;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
public class TransitionList{
    //has to preserve order
    private LinkedHashMap<CellHolder,ArrayList<CellHolder>> map=new LinkedHashMap<CellHolder,ArrayList<CellHolder>>();
    public void addTransition(Transition t){
        if(map.get(t.to)==null){
            map.put(t.to,new ArrayList<CellHolder>());
        }
        map.get(t.to).add(t.from);
    }
    public void animate(Grid which){
        System.out.print("\u001b[2J\u001b[H");
        for(CellHolder h: map.keySet()){
            List<CellHolder> fromList=map.get(h);
            for(int i=0;i<fromList.size();i++){
                fromList.get(i).clear();
            }
            which.update();
            which.sleep(50);
        }
    }
    public String print(){
        StringBuilder builder=new StringBuilder();
        builder.append("[\n");
        for(CellHolder h: map.keySet()){
            builder.append("{ to: "+h+",\n");
            builder.append("  from: [\n");
            List<CellHolder> fromList=map.get(h);
            for(int i=0;i<fromList.size()-1;i++){
                builder.append("           "+fromList.get(i)+",\n");
            }
            builder.append("           "+fromList.get(fromList.size()-1)+"\n");
            builder.append("        ]\n");
            builder.append("},");
        }
        builder.deleteCharAt(builder.toString().length()-1);//extra comma
        builder.append("\n]");
        return builder.toString();
    }
}
