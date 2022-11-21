public class Pair<L,R>{
    private L key;
    private R value;

    public Pair(L l, R r){
        this.key = l;
        this.value = r;
    }
    public L getKey(){
        return this.key;
    }
    public R getValue(){
        return this.value;
    }

}
