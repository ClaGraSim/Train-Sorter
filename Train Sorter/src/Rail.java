import java.util.Collections;
import java.util.Stack;

// Essentially a type of stack. Contains trains, checks if correct.
public class Rail {

    int size = 0;
    int maxSize;
    Stack<Train> rail = new Stack<>();

    public Rail(int maxSize){
        this.maxSize = maxSize;
    }

    public boolean put(Train train){
        if(size <= maxSize){
            rail.push(train);
            size++;
            return true;
        } else{
            return false;
        }
    }

    public Train take(){
        if(size > 0){
            size--;
            return rail.pop();
        }
        return null;
    }

    public void shuffle(){
        Collections.shuffle(rail);
        System.out.println("Rail has been shuffled.");
    }

    public Train getFromPosition(int i){
        if(i >= 0 && i < size){
            return rail.get(i);
        }
        return null;
    }
    public boolean checkIfTrainIsCorrect(){
        Train train, nextTrain;
        boolean answer = false;
        // Only checks rail if it's completely full, if it's empty it counts as sorted
        if(size == maxSize){
            for(int i = 0; i < size - 1; i++){
                train = getFromPosition(i);
                nextTrain = getFromPosition(i + 1);
                if(train.belongsInFrontOf(nextTrain)){
                    answer = true;
                } else{
                    return false;
                }
            }
        } else if(size == 0){
            return true;
        }
        return answer;
    }

    public boolean isFull(){
        return size == maxSize;
    }

}
