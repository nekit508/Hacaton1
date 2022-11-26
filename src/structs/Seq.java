package structs;

import funcs.Out0In1;
import funcs.Out0In2;
import funcs.Out1In1;
import utils.Log;

import java.util.Iterator;

@SuppressWarnings("unchecked")
public class Seq<T> {
    protected int size, listSize;
    protected T[] items;
    protected T temp1;
    protected T[] temp2;

    public int getSize(){
        return listSize;
    }

    public String toString(){
        String string = "[";
        for(T elem: items){
            if(elem != null)string += elem.toString();
            string += ", ";
        }
        string += "]";
        return string;
    }

    public Seq(){
        listSize = 0;
        items = getList();
        size = 0;
    }

    public void clear(){
        listSize = 0;
        items = getList();
        size = 0;
    }

    T[] getList(){
        return (T[])(new Object[listSize]);
    }

    T[] getList(int size){
        return (T[])(new Object[size]);
    }

    protected T[] resize(){
        T[] items = this.items;
        T[] newItems = getList();
        System.arraycopy(items, 0, newItems, 0, size);
        this.items = newItems;
        return newItems;
    }

    public T get(int ind){
        if(ind < 0) ind = ind - ind % size;
        return items[ind];
    }

    public T get(Out1In1<Boolean, T> f){
        for(int ind = 0;ind < size;ind++){
            if(f.get(get(ind))){
                return get(ind);
            }
        }
        return null;
    }

    public void add(T val){
        if(size == listSize) {
            listSize += 1;
            resize();
        }
        items[size++] = val;
    }

    public void add(Seq<T> val){
        val.each(this::add);
    }

    public T pop(){
        temp1 = items[size-1];
        items[--size] = null;
        listSize--;
        resize();
        return temp1;
    }

    public void remove(int ind){
        if(ind < 0) ind = ind - ind % size;
        size--;
        items[ind] = null;
        temp2 = getList(size);
        System.arraycopy(items, 0, temp2, 0, ind);
        System.arraycopy(items, ind+1, temp2, ind, size-ind);
        items = temp2;

        listSize--;
    }

    public void remove(Out1In1<Boolean, T> f){
        for(int ind = 0;ind < size;ind++){
            if(f.get(get(ind))){
                remove(ind);
            }
        }
    }

    public void remove(T o){
        for(int ind = 0;ind < size;ind++){
            if(get(ind).equals(o)){
                remove(ind);
            }
        }
    }

    public void toLog(){
        Log.infoln(toString());
    }

    public void each(Out0In1<T> f){
        for (int i = 0; i < items.length; i++) {
            f.get(items[i]);
        }
    }

    public void eachIndexed(Out0In2<T, Integer> f){
        for (int i = 0; i < items.length; i++) {
            f.get(items[i], i);
        }
    }

    public void eachIf(Out1In1<Boolean, T> fb, Out0In1<T> f){
        for (int i = 0; i < items.length; i++) {
            if(fb.get(items[i])) f.get(items[i]);
        }
    }

    public T getLastElement() {
        if(size == 0) return null;
        return items[size-1];
    }

    public int getIndex(T value){
        for(int i = 0;i < items.length;i++){
            if(value.equals(items[i])) return i;
        }
        return -1;
    }

    public int getIndex(Out1In1<Boolean, T> f){
        for(int i = 0;i < items.length;i++){
            if(f.get(items[i])) return i;
        }
        return -1;
    }
}
