package cn.imzfz.calculator;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Stack;

/**
 * Created by zfz on 2017/9/26.
 */

public class Logic_New {

    private String inputText, viewText;
    private Stack<String> numberStack;
    private Stack<String> operationStack;
    private Stack<String> cal;
    private Context context;
    //检查小数点
    private boolean hasDot = false;
    //判断下一个应该输入的类型  0 for number, 1 for operation
    private int nowType = 0;

    public Logic_New(Context context) {
        numberStack = new Stack<>();
        operationStack = new Stack<>();
        cal = new Stack<>();
        viewText = "0";
        inputText = "";
        numberStack.add("0");
        this.context = context;
    }

    public String getViewText() {
        return viewText;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    public int classification() {
        Log.v("IIIInput", inputText);
        switch (inputText) {
            case "0":
                return 0;
            case "1":
                return 1;
            case "2":
                return 1;
            case "3":
                return 1;
            case "4":
                return 1;
            case "5":
                return 1;
            case "6":
                return 1;
            case "7":
                return 1;
            case "8":
                return 1;
            case "9":
                return 1;
            case "+":
                return 2;
            case "-":
                return 3;
            case "x":
                return 4;
            case "÷":
                return 5;
            case "=":
                return 6;
            case "AC":
                return 7;
            case ".":
                return 8;
            case "(":
                return 9;
            case ")":
                return 10;
            case "%":
                return 11;
            case "x²":
                return 12;
            case "x³":
                return 13;
            case "e^x":
                return 14;
            case "10^x":
                return 15;
            case "1/x":
                return 16;
            case "²√":
                return 17;
            case "³√":
                return 18;
            case "log10":
                return 19;
            case "ln":
                return 26;
            case "x!":
                return 20;
            case "sin":
                return 21;
            case "cos":
                return 22;
            case "tan":
                return 23;
            case "e":
                return 24;
            case "π":
                return 25;
            case "+/-":
                return 27;
            default:
                return 0;
        }
    }

    public void calculate() {
        try {
            switch (classification()) {
                case 0:
                    inputZero();
                    break;
                case 1:
                    inputNumber();
                    break;
                case 2:
                    Log.v("PPPPPlus", "ppp");
                    inputPlus();
                    break;
                case 3:
                    Log.v("MMMinus", "mmm");
                    inputMinus();
                    break;
                case 4:
                    Log.v("MMMinus", "mmm");
                    inputMultiple();
                    break;
                case 5:
                    Log.v("MMMinus", "mmm");
                    inputDivide();
                    break;
                case 6:
                    inputEqual();
                    break;
                case 7:
                    inputAC();
                    break;
                case 8:
                    inputDot();
                    break;
                case 9:
                    inputBrackets();
                    break;
                case 10:
                    inputBrackets();
                    break;
                case 11:
                    inputPercent();
                    break;
                case 12:
                    inputPow();
                    break;
                case 13:
                    inputCubic();
                    break;
                case 14:
                    inputPowe();
                    break;
                case 15:
                    inputPow10();
                    break;
                case 16:
                    inputReciprocal();
                    break;
                case 17:
                    inputSqrt();
                    break;
                case 18:
                    inputCbrt();
                    break;
                case 19:
                    inputLog10();
                    break;
                case 21:
                    inputSin();
                    break;
                case 20:
                    inputFactorial();
                    break;
                case 22:
                    inputCos();
                    break;
                case 23:
                    inputTan();
                    break;
                case 24:
                    inputE();
                    break;
                case 25:
                    inputPi();
                    break;
                case 26:
                    inputLn();
                    break;
                case 27:
                    inputAm();
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
            viewText = "错误";
        }
    }

    //横竖屏长度
    public int lengthLimited() {
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (hasDot && numberStack.size() > 9) {
                return -1;
            }
            if (!hasDot && numberStack.size() == 9) {
                return -1;
            }
        }
        return 0;
    }

    //检查是否只有一个0
    public boolean startZero() {
        if (numberStack.contains("0") && numberStack.size() == 1) {
            return true;
        }
        return false;
    }

    //输入0
    public void inputZero() {
        if (!startZero()) {
            if(operationStack.isEmpty() && numberStack.isEmpty()){
                numberStack.clear();
                numberStack.add("0");
                cal.clear();
                nowType = 0;
                return;
            }

            if (lengthLimited() != -1) {
                viewText += "0";
                numberStack.add("0");
                nowType = 0;
            }

        }
        checkComa();
    }

    //输入数字
    public void inputNumber() {

        if(operationStack.isEmpty() && numberStack.isEmpty()){
            numberStack.clear();
            numberStack.add("0");
            cal.clear();
        }

        if (startZero()) {
            numberStack.pop();
            viewText = "";
        }
        if (nowType == 1) {
            viewText = "";
            nowType = 0;
        }
        if (lengthLimited() != -1) {
            numberStack.add(inputText);
            viewText += inputText;
            checkComa();
        }
    }

    public void inputDot() {
        if(viewText.contains(".")){
            hasDot = true;
        }
        if (!hasDot && lengthLimited() != -1) {
            numberStack.add(".");
            viewText += ".";
            hasDot = true;
        }
        if (numberStack.size() == 10) {
            numberStack.pop();
            viewText = viewText.substring(0, viewText.length() - 1);
        }
    }

    public void inputAC() {
        numberStack.clear();
        numberStack.add("0");
        operationStack.clear();
        cal.clear();
        viewText = "0";
        hasDot = false;
        nowType = 0;
    }

    public void inputPlus() {
        trans();
        if (!operationStack.isEmpty() && nowType == 0) {
            if (!operationStack.peek().equals("(")) {
                cal.add(operationStack.pop());
                calculation();
            }
        }
        if(nowType == 1){
            operationStack.pop();
            operationStack.add("+");
        }

        if (nowType == 0) {
            operationStack.add("+");
            nowType = 1;
        }
    }

    public void inputMinus() {
        trans();
        if (!operationStack.isEmpty() && nowType == 0) {
            if (!operationStack.peek().equals("(")) {
                cal.add(operationStack.pop());
                calculation();
            }
        }
        if(nowType == 1){
            operationStack.pop();
            operationStack.add("-");
        }

        if (nowType == 0) {
            operationStack.add("-");
            nowType = 1;
        }
    }

    public void inputMultiple() {
        trans();
        if (!operationStack.isEmpty() && nowType == 0) {
            if (operationStack.peek().equals("x") || operationStack.peek().equals("÷")) {
                cal.add(operationStack.pop());
                calculation();
            }
        }
        if (nowType == 1) {
            operationStack.pop();
            operationStack.add("x");
        }

        if (nowType == 0) {
            operationStack.add("x");
            nowType = 1;
        }
    }

    public void inputDivide() {
        trans();
        if (!operationStack.isEmpty() && nowType == 0) {
            if (operationStack.peek().equals("x") || operationStack.peek().equals("÷")) {
                cal.add(operationStack.pop());
                calculation();
            }
        }
        if (nowType == 1) {
            operationStack.pop();
            operationStack.add("÷");
        }
        if (nowType == 0) {
            operationStack.add("÷");
            nowType = 1;
        }
    }

    public void inputEqual() {
        trans();
        int left = 0, right = 0;

        for(String e : operationStack){
            if(e.equals("("))
                left++;
            if(e.equals(")"))
                right++;
        }

        for(int i = 0; i < left - right; i++){
            inputText = ")";
            inputBrackets();
        }


        while (!operationStack.isEmpty()) {
            cal.add(operationStack.pop());
        }
        while (cal.size() > 1) {
            calculation();
        }
    }

    public void inputPercent() {
        trans();
        if (!cal.isEmpty()) {
            BigDecimal num = new BigDecimal(cal.pop());
            num = num.divide(new BigDecimal(100));
            cal.add(num + "");
            viewText = cal.peek();
        }
    }


    public void inputPow() {
        try {
            trans();
            if (!cal.isEmpty()) {
                BigDecimal x = new BigDecimal(Math.pow(Double.parseDouble(cal.pop()), 2));
                cal.add(x + "");
                viewText = x + "";
                calculation();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void inputCubic() {
        try {
            trans();
            if (!cal.isEmpty()) {
                BigDecimal x = new BigDecimal(Math.pow(Double.parseDouble(cal.pop()), 3));
                cal.add(x + "");
                viewText = x + "";
                calculation();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inputPowe() {
        try {
            trans();
            if (!cal.isEmpty()) {
                BigDecimal x = new BigDecimal(Math.pow(Math.E, Double.parseDouble(cal.pop())));
                cal.add(x + "");
                viewText = x + "";
                calculation();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inputPow10() {
        try {
            trans();
            if (!cal.isEmpty()) {
                BigDecimal x = new BigDecimal(Math.pow(10, Double.parseDouble(cal.pop())));
                cal.add(x + "");
                viewText = x + "";
                calculation();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inputReciprocal() {
        try {
            BigDecimal t = new BigDecimal(1);
            trans();
            if (!cal.isEmpty()) {
                BigDecimal x = t.divide(new BigDecimal(cal.pop()), 15, BigDecimal.ROUND_HALF_DOWN);
                cal.add(x + "");
                viewText = x + "";
                calculation();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inputSqrt() {
        try {
            trans();
            if (!cal.isEmpty()) {
                BigDecimal x = new BigDecimal(Math.sqrt(Double.parseDouble(cal.pop())));
                cal.add(x + "");
                viewText = x + "";
                calculation();
            }
        } catch (Exception e) {
            e.printStackTrace();
            viewText = "错误";
        }
    }

    public void inputCbrt() {
        try {
            trans();
            if (!cal.isEmpty()) {
                BigDecimal x = new BigDecimal(Math.cbrt(Double.parseDouble(cal.pop())));
                cal.add(x + "");
                viewText = x + "";
                calculation();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inputLog10() {
        try {
            trans();
            if (!cal.isEmpty()) {
                BigDecimal x = new BigDecimal(Math.log10(Double.parseDouble(cal.pop())));
                cal.add(x + "");
                viewText = x + "";
                calculation();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inputLn() {
        try {
            trans();
            if (!cal.isEmpty()) {
                BigDecimal x = new BigDecimal(Math.log(Double.parseDouble(cal.pop())));
                cal.add(x + "");
                viewText = x + "";
                calculation();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inputSin() {
        try {
            trans();
            if (!cal.isEmpty()) {
                BigDecimal x = new BigDecimal(Math.sin(Double.parseDouble(cal.pop())));
                cal.add(x + "");
                viewText = x + "";
                calculation();
            }
        } catch (Exception e) {
            e.printStackTrace();
            viewText = "错误";
        }
    }

    public void inputFactorial() {
        int res = 1;
        try {
            trans();
            if (!cal.isEmpty()) {
                for(int i = Integer.parseInt(cal.pop()); i > 1; i--){
                    res *= i;
                }
                BigDecimal x = new BigDecimal(res);
                cal.add(x + "");
                viewText = x + "";
                calculation();
            }
        } catch (Exception e) {
            e.printStackTrace();
            viewText = "错误";
        }
    }

    public void inputCos() {
        try {
            trans();
            if (!cal.isEmpty()) {
                BigDecimal x = new BigDecimal(Math.cos(Double.parseDouble(cal.pop())));
                cal.add(x + "");
                viewText = x + "";
                calculation();
            }
        } catch (Exception e) {
            e.printStackTrace();
            viewText = "错误";
        }
    }

    public void inputTan() {
        try {
            trans();
            if (!cal.isEmpty()) {
                BigDecimal x = new BigDecimal(Math.tan(Double.parseDouble(cal.pop())));
                cal.add(x + "");
                viewText = x + "";
                calculation();
            }
        } catch (Exception e) {
            e.printStackTrace();
            viewText = "错误";
        }
    }

    public void inputE() {
        try {
            if(operationStack.isEmpty() && numberStack.isEmpty()){
                numberStack.clear();
                numberStack.add("0");
                cal.clear();
            }

            if (startZero()) {
                numberStack.pop();
                viewText = "";
            }
            if (nowType == 1) {
                viewText = "";
                nowType = 0;
            }
            if (lengthLimited() != -1) {
                numberStack.add(Math.E + "");
                viewText += Math.E + "";
       //         checkComa();
            }
            trans();

            /*if (startZero()) {
                numberStack.pop();
                viewText = "";
            }
            numberStack.add(Math.E + "");
            viewText = Math.E + "";*/
        } catch (Exception e) {
            e.printStackTrace();
            viewText = "错误";
        }
    }

    public void inputPi() {
        try {

            if(operationStack.isEmpty() && numberStack.isEmpty()){
                numberStack.clear();
                numberStack.add("0");
                cal.clear();
            }

            if (startZero()) {
                numberStack.pop();
                viewText = "";
            }
            if (nowType == 1) {
                viewText = "";
                nowType = 0;
            }
            if (lengthLimited() != -1) {
                numberStack.add(Math.PI + "");
                viewText += Math.PI + "";
                //         checkComa();
            }
            trans();


           /* if (startZero()) {
                numberStack.pop();
                viewText = "";
            }
            numberStack.add(Math.PI + "");
            viewText = Math.PI + "";
            trans();*/
        } catch (Exception e) {
            e.printStackTrace();
            viewText = "错误";
        }
    }

    public void inputAm(){
        try {
            trans();
            if (!cal.isEmpty()) {
                BigDecimal x = new BigDecimal(0 - Double.parseDouble(cal.pop()));
                cal.add(x + "");
                viewText = x + "";
                calculation();
            }
        } catch (Exception e) {
            e.printStackTrace();
            viewText = "错误";
        }
    }

    public void inputBrackets() {
        trans();

        if(operationStack.isEmpty() && numberStack.isEmpty()){
            numberStack.clear();
            numberStack.add("0");
            cal.clear();
        }

        if (inputText.equals("(")) {
            operationStack.add("(");
        }
        if (inputText.equals(")")) {
            while (!operationStack.peek().equals("(")) {
                cal.add(operationStack.pop());
            }
            operationStack.pop();
        }
    }

    public void back() {
        if (nowType == 0) {
            String pop = numberStack.pop();
            if (pop.equals(".")) {
                hasDot = false;
            }

            if (viewText.length() == 0) {
                return;
            }

            if (viewText.length() == 1) {
                numberStack.add("0");
                viewText = "0";
            } else {
                viewText = viewText.substring(0, viewText.length() - 1);
            }
            checkComa();
        }
    }

    public void trans() {
        double r;
        String str = "";
        Log.v("Trans", "trans");

        try {
            if (numberStack.size() > 0) {
                for (String e : numberStack) {
                    str += e;
                    Log.v("cal", "" + str);
                }
                numberStack.clear();
                hasDot = false;
                //        r = Double.parseDouble(str);
                cal.add(str);
    //            nowType = 1;
            }
        } catch (Exception e) {
            viewText = "错误";
        }
    }

    //最后检查格式
    public void checkComa() {
        String t = "";

        Log.v("Coma", numberStack.size() + "");
        if (numberStack.size() <= 3 && !hasDot) {
            for (int i = 0; i < numberStack.size(); i++) {
                t += numberStack.elementAt(i);
            }
            viewText = t;
            return;
        }
        if (numberStack.size() > 3 && !hasDot) {
            Log.v("SSSizer", numberStack.size() + "");
            for (int i = 0; i < numberStack.size(); i++) {
                if ((numberStack.size() - i) % 3 == 0 && i != 0 && !numberStack.peek().equals(".")) {
                    Log.v("coma", "" + numberStack.size());
                    t += ",";
                }
                t += numberStack.elementAt(i);
            }
            viewText = t;
        }
    }

    public void calculation() {
        BigDecimal num1, num2;
        BigDecimal res = new BigDecimal(0);
        DecimalFormat format0 = new DecimalFormat("###,###.###");
        DecimalFormat format1 = new DecimalFormat("###,###,###");
        DecimalFormat format2 = new DecimalFormat();
        format2.applyPattern("0.0000E000");

        try {
            for (int i = 0; i < cal.size(); i++) {
                switch (cal.get(i)) {
                    case "+":
                        num1 = new BigDecimal(cal.get(i - 1));
                        num2 = new BigDecimal(cal.get(i - 2));
                        res = num1.add(num2);
                        format(i, res);
                        viewText = res + "";
                        break;
                    case "-":
                        num1 = new BigDecimal(cal.get(i - 1));
                        num2 = new BigDecimal(cal.get(i - 2));
                        res = num2.subtract(num1);
                        format(i, res);
                        viewText = res + "";
                        break;
                    case "x":
                        num1 = new BigDecimal(cal.get(i - 1));
                        num2 = new BigDecimal(cal.get(i - 2));
                        res = num2.multiply(num1);
                        format(i, res);
                        viewText = res + "";
                        break;
                    case "÷":
                        num1 = new BigDecimal(cal.get(i - 1));
                        num2 = new BigDecimal(cal.get(i - 2));
                        res = num2.divide(num1, 5, BigDecimal.ROUND_HALF_DOWN);
                        format(i, res);
                        viewText = res + "";
                        break;

                }
            }

            res = new BigDecimal(viewText);
            if (res.doubleValue() < 0.0000001 && res.doubleValue() > 0) {
                viewText = format0.format(res);
            }

            if (!viewText.contains(".")) {
                viewText = format1.format(res);
            }
            if (res.doubleValue() > 999999999) {
                viewText = format2.format(res);
            }
        } catch (Exception e) {
            cal.clear();
            operationStack.clear();
            numberStack.clear();
            viewText = "错误";
        }
    }

    public void format(int index, BigDecimal res) {
        int i = 0;
        cal.set(index, res.toString());
        for (Iterator it = cal.iterator(); it.hasNext() && i < 2; ) {
            cal.remove(index - 2);
            i++;
        }
    }
}
