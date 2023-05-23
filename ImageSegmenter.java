
import java.awt.Color;
import java.util.HashSet;
import java.util.List;
import java.util.*;

public class ImageSegmenter{
   



    public static Color[][] segment(Color[][] rgbArray, double granularity) {
        

        DisjointSetForest pixelForest = new DisjointSetForest(rgbArray);
        // HashSet<Pixel> pixelSet = new HashSet<>();
        List<Edge> edgeList = new ArrayList<Edge>();
        ColorPicker colorGenerator= new ColorPicker();

        
        int[] nextRow = {-1,-1,-1,0};//coords for algorithm to add edges
        int[] nextCol = {-1,0,1,1};
        for(int c = 0; c <  rgbArray.length; c++){
            for(int r = 0; r< rgbArray[0].length ; r++){
                for(int i = 0; i <4; i ++){

                    if(pixOutOfBounds( r+ nextRow[i], c+nextCol[i], pixelForest) 
                    || pixOutOfBounds(r, c, pixelForest)){
                        
                            // System.out.println(r+ nextRow[i]+","+ c+nextCol[i]);
                        continue;

                    }
                    
                                  
                    
                    Edge newEdge = new Edge(pixelForest.forest[c][r].pix,
                    pixelForest.forest[c+ nextCol[i]][r+nextRow[i]].pix );
                    edgeList.add(newEdge);
                }
            }
        }
        

        Collections.sort(edgeList);

        for(int i=0; i<edgeList.size();i++){
            
                Pixel pix1 = edgeList.get(i).getFirstPixel();
                Pixel pix2 = edgeList.get(i).getSecondPixel();
                DisjointSetForest.Node first = new DisjointSetForest.Node(pix1);
                DisjointSetForest.Node second = new DisjointSetForest.Node(pix2);
                if(!pixelForest.find(pix1).equals(pixelForest.find(pix2))){
                    
                    int size1 = pixelForest.find(first.pix).size;
                    int size2 = pixelForest.find(second.pix).size;
                    double dist1 =  pixelForest.find(first.pix).intDistance;
                    double dist2  =  pixelForest.find(second.pix).intDistance;

                    // double intDist = (int) edgeList.get(i).getWeight();
                    if(edgeList.get(i).getWeight() < 
                    Math.min(((dist1)+(granularity/size1)),
                    ((dist2)+(granularity/size2)))){
                        pixelForest.union(pixelForest.find(first.pix).pix,pixelForest.find(second.pix).pix,(int) edgeList.get(i).getWeight());                  
                    }
                                        
                }
        
                
                    
            


        }


        /** color changing algorithm for each segment */
        
        // for(Pixel currentpix: DisjointSetForest.ParentNodes.keySet()){
        


        Color randomColor= colorGenerator.nextColor();
        //  currentpix.color = randomColor;
            

        // }

        for(int r = 0; r <  rgbArray.length; r++){
            for(int c = 0; c< rgbArray[0].length ; c++){
                if(pixOutOfBounds(r, c, pixelForest)){
                    continue;
                }
                
                DisjointSetForest.Node currentNode = pixelForest.forest[r][c];
                DisjointSetForest.Node root  =  pixelForest.find(currentNode.pix); 
                if( currentNode.parent != null){
                    if(root.color == null){
                        root.color = colorGenerator.nextColor();
                    }
                    currentNode.color =root.color;
                    rgbArray[r][c] =  currentNode.color;
                }
                else{
                    if(currentNode.color == null)
                    {
                    currentNode.color = colorGenerator.nextColor();
                    }
                    rgbArray[r][c] =  currentNode.color;
                }

                }
            }
            return rgbArray; 
        }
        
    

        // placeholder to ensure compilation -- replace this when you're done!
        
    




    public static boolean pixOutOfBounds(int r, int c, DisjointSetForest pixelForest){
        if(r < 0 || c < 0 || r >= pixelForest.forest.length|| 
        c>= pixelForest.forest[0].length ){
            return true;
        }
        return false;
    }
}

