package compiler.core.parser.tree.asm;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 */
//Register definition
public class Register {

    private List<Boolean> registerUsingList;    //Boolean list which describes is register using now for each of registers

    public Register(int numOfRegisters) {
        registerUsingList = new LinkedList<Boolean>();
        for(int i = 0; i < numOfRegisters; i++){    //All registers are nor using initially
            registerUsingList.add(false);
        }
    }

    //Sets for the appropriate register is it using
    public void setRegisterUsing(int registerNum, boolean isUsed){
        registerUsingList.set(registerNum, isUsed);
    }

    //Returns whether the appropriate register is using
    public boolean getRegisterUsing(int registerNum){
        return registerUsingList.get(registerNum);
    }

    //Returns next disusing register or -1 if all registers are using
    public int getNextDisusingRegister(){
        for(int i = 0; i < registerUsingList.size(); i++){
            if(!registerUsingList.get(i)){
                return i;
            }
        }
        return -1;
    }

}
