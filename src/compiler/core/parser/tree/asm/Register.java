package compiler.core.parser.tree.asm;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 */
public class Register {

    private List<Boolean> registerUsingList;

    public Register(int numOfRegisters) {
        registerUsingList = new LinkedList<Boolean>();
        for(int i = 0; i < numOfRegisters; i++){
            registerUsingList.add(false);
        }
    }

    public void setRegisterUsing(int registerNum, boolean isUsed){
        registerUsingList.set(registerNum, isUsed);
    }

    public boolean getRegisterUsing(int registerNum){
        return registerUsingList.get(registerNum);
    }

    public int getNextUnusingRegister(){
        for(int i = 0; i < registerUsingList.size(); i++){
            if(!registerUsingList.get(i)){
                return i;
            }
        }
        return -1;
    }

}
