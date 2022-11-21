import java.io.File;
import java.util.*;

public class FiniteAutomation {
    public Set<String> alphabet, allStateList, finalStates;
    public String initialState;
    public Map<Pair<String, String>, Set<String>> transitions;

    public FiniteAutomation(String fileName){
        this.allStateList = new HashSet<>();
        this.alphabet = new HashSet<>();
        this.finalStates = new HashSet<>();
        this.transitions = new HashMap<>();
        splitFile(fileName);
    }

    private void splitFile(String fileName){
        /***
         * This function will split the input automata file
         * and put the values for the allStateList, alphabet
         * initialState, finalState and transitions hashSets of
         * the input class. If
         * we do not find the input file we
         * throw an exception.
         */
        try{
            File file = new File(fileName);
            Scanner reader = new Scanner(file);
            String statesList = reader.nextLine();
            allStateList = new HashSet<>(Arrays.asList(statesList.split(" ")));
            String alphabetList = reader.nextLine();
            alphabet = new HashSet<>(Arrays.asList(alphabetList.split(" ")));
            initialState = reader.nextLine();
            String finalStatesLine = reader.nextLine();
            finalStates = new HashSet<>(Arrays.asList(finalStatesLine.split(" ")));
            while(reader.hasNextLine()){
                var transitionList =  reader.nextLine().split(" ");
                if(allStateList.contains(transitionList[0]) && allStateList.contains(transitionList[2]) && alphabet.contains(transitionList[1])) {
                    var transitionStates = new Pair<>(transitionList[0], transitionList[1]);
                    boolean found = false;
                    for (var transition: transitions.keySet()){
                        if (Objects.equals(transition.getKey(), transitionStates.getKey()) && Objects.equals(transition.getValue(), transitionStates.getValue())){
                            transitions.get(transition).add(transitionList[2]);
                            found = true;
                        }
                    }
                    if (!found){
                        Set<String> transitionStatesSet = new HashSet<>();
                        transitionStatesSet.add(transitionList[2]);
                        transitions.put(transitionStates, transitionStatesSet);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String computeData(){
        /***
         * This function will compute the
         * alphabet, initialStates, Final States and Transitions
         * into a single string using a stringBuilder so we can print
         * the results.
         */
        StringBuilder builder = new StringBuilder();
        builder.append("Alphabet: ");
        for (String s : alphabet){
            builder.append(s).append(" ");
        }
        builder.append("\nAll State List: ");
        for (String s : allStateList){
            builder.append(s).append(" ");
        }
        builder.append("\nFinal states: ");
        for (String fs : finalStates){
            builder.append(fs).append(" ");
        }
        builder.append("\nTransitions: \n");
        transitions.forEach((K, V) -> {
            builder.append("<").append(K.getKey()).append(",").append(K.getValue()).append("> -> ").append(V).append("\n");
        });
        return builder.toString();
    }

    public boolean checkIfDeterministic(){
        /***
         * This function will check if our input finite automata
         * is deterministic or not (if a pair in the transition array
         * points to multiple values)
         */
        for (var transition: this.transitions.values()){
            if (transition.size() > 1)
                return false;
        }
        return true;
    }

    public boolean checkSequence(String sequence){
        /***
         * This function will check if the sequence is valid
         * or not by checking if the combinations exist in the
         * transition set
         */
        if(sequence.length() == 0)
           return false;
        var state = initialState;
        for(int i=0;i<sequence.length();++i){
            var key = new Pair<>(state, String.valueOf(sequence.charAt(i)));
            boolean found = false;
            for (var transition: transitions.keySet()){
                if (Objects.equals(transition.getKey(), key.getKey()) && Objects.equals(transition.getValue(), key.getValue())){
                    state = transitions.get(transition).iterator().next();
                    found = true;
                }
            }
            if (!found)
                return false;
        }
        return true;
    }

}
