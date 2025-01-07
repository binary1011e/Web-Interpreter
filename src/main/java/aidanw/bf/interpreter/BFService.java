package aidanw.bf.interpreter;

import loxinterpreter.lox.Lox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import BrainF.BF;
@Service
public class BFService {

    public static void interpretCode(Code code) {
        if (code.getInputCode() != null) {
            if ("Lox".equals(code.getLox())) {
                Lox lox = new Lox();
                code.setOutputCode(lox.run(code.getInputCode()));
            } else {
                BF bf = new BF(code.getInputCode(), code.getInput());
                code.setOutputCode(bf.run());
            }
        }
    }
}
