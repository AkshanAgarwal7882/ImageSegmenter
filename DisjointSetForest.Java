import java.util.HashMap;
import java.util.HashSet;
import java.awt.Color;
public class DisjointSetForest {


        public static Node[][] forest;
        public Color[][] rgbArray;
        // public static HashMap<Pixel, Integer> ParentNodes;

        

        public static class Node {
          
           /* Even though the following fields are declared public, this nested
            * class is itself private. So only the methods inside the TreeIntSet
            * class can actually instantiate TreeNode objects. */
           public Node parent; // reference to the left subtree
           public int rank;
           public Pixel pix;
           public int intDistance;
           public Color color;
           public int size;

           /**
            * A constructor for nodes midway through the Huffman tree.
            * @param frequency the combined frequency of two child nodes with letters.
            *
            */
           public Node(Pixel pix) { //constructor without letter
          
               this.parent = null;
               this.pix = pix;
               this.rank = 0;
               this.intDistance = 0;
               this.color= null;
               this.size = 0;
           }
           /**
            * A constructor for the leaf nodes of the Huffman tree.
            * @param letter- the letter assigned to this node
            * @param frequency- the frequency with which the letter appears
            */
           public Node(Node parent, Pixel pix) { //constructor with letter (for leaves)
               this.parent = parent;
               this.pix = pix;
               this.rank = parent.rank +1;
  
           }
          
     
       }


        public DisjointSetForest(Color[][] rgbArray){
            // ParentNodes= new HashMap<Pixel,Integer>();
            forest= new Node[rgbArray.length][rgbArray[0].length];
            for (int row=0;row<rgbArray.length;row++){
                for(int col = 0; col <rgbArray[0].length; col++){
                    
                    Pixel currentPixel = new Pixel(row,col,rgbArray[row][col]);
                    Node temp = new Node(currentPixel);
                    forest[row][col] = temp;
                    temp.intDistance=0;
                    // ParentNodes.put(currentPixel, 1);
                    
                }
            }

            

        }
        

        public static Node find(Pixel pix){// might need to revisit
            Node currentNode= forest[pix.getRow()][pix.getCol()];
            if(currentNode.parent == null){
                return currentNode;
            }
            Node root = find(currentNode.parent.pix);
            currentNode.parent = root;
            return root;
            
            // TraverseTree(currentNode.parent,path);

            // for(int r = 0; r <  forest.length; r++){
            //     for(int c = 0; c< forest[0].length ; c++){
            //         Node parent;
            //         Pixel temp = forest[r][c].pix;
            //         if(temp.equals(pix)){
            //             HashSet<Node> NodeSet = new HashSet<Node>();
            //             parent = (TraverseTree(forest[r][c], NodeSet));
            //             CompressPath(NodeSet, parent);
            //             return parent;
            //     }
                
            // }
        //}

            // return null;

        }

        
        // public static void CompressPath(HashSet<Node> pathNodes, Node parentNode){
        //     for(Node i: pathNodes){

        //         i.parent = parentNode;
        //     }
        // }

        // public static Node TraverseTree(Node a, HashSet<Node> path){
            
        // }

        public static void union(Pixel x, Pixel y , int dist){
            Node Nodex = find(x);
            Node Nodey = find(y);
            if(Nodex.rank == Nodey.rank){
                Nodey.parent = Nodex;
                Nodex.rank++;
                Nodex.size += Nodey.size;
                // ParentNodes.put(Nodex.pix,ParentNodes.get(Nodex.pix)+ParentNodes.get(Nodey.pix));
                // ParentNodes.remove(Nodey.pix);
                Nodex.intDistance = dist;
                return;

            }
            else if(Nodex.rank > Nodey.rank)
            {
                Nodex.parent = Nodey;
                Nodey.size += Nodex.size;
                // ParentNodes.put(Nodex.pix,ParentNodes.get(Nodex.pix)+ParentNodes.get(Nodey.pix));
                // ParentNodes.remove(Nodey.pix);
                Nodey.intDistance = dist;
                return;
            }
            else{
                Nodey.parent = Nodex;
                Nodex.size += Nodey.size;
                // ParentNodes.put(Nodey.pix,ParentNodes.get(Nodex.pix)+ParentNodes.get(Nodey.pix));
                // ParentNodes.remove(Nodex.pix);
                Nodex.intDistance = dist;
                return;
            }
            
          

        }



        

        
}
