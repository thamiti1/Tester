package taskone;

import java.util.List;
import java.util.ArrayList;

class StringList {
    
    List<String> strings = new ArrayList<String>();

    //synchronized was the original
    public void add(String str) {
        int pos = strings.indexOf(str);
        if (pos < 0) {
            strings.add(str);
        }
    }

    public boolean contains(String str) {
        return strings.indexOf(str) >= 0;
    }

    public int size() {
        return strings.size();
    }

    public String toString() {
        return strings.toString();
    }

    public String pop () {
        if (this.size() == 0)
            return "null";

        String val = strings.get(this.size()-1);
        strings.remove(this.size()-1);
        return val;
    }

    public String count () {
        return Integer.toString(this.size());
    }

    public String display () {
        String val = "";
        for (String s : strings)
            val = val + s + "\n";
        return val;
    }

    public boolean switch_elements (String in) {
        String val[] = in.split(" ");
        int num1 = Integer.parseInt(val[0]);
        int num2 = Integer.parseInt(val[1]);
        if (num1 < 0 || num1 >= this.size())
            return false;
        if (num2 < 0 || num2 >= this.size())
            return false;
        String tempString = strings.get(num1);
        strings.set(num1, strings.get(num2));
        strings.set(num2, tempString);
        return true;
    }

}