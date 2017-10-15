package cn.imzfz.calculator;


import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by zfz on 2017/9/12.
 */

public class Logic {
    private TextView showView;
    private Context context;
    private String inputText, viewText;
    private Stack<String> numberStack;
    private Stack<String> operationStack;
    private Stack<Double> cal;
    private Stack<Double> calTemp;
    private int who = 0;
    private int pointCheck = 0;
    private int operation = 0;
    private double res = 0;


    public Logic() {
        numberStack = new Stack<>();
        operationStack = new Stack<>();
        cal = new Stack<>();
        numberStack.add("0");
    }


    //控制器传递的输入内容
    public void setTransStr(String inputText) {
        this.inputText = inputText;
        Log.v("inputText", inputText);
    }


    public void setviewText(String viewText) {
        this.viewText = viewText;
    }

    //判断当前是否只有0
    public boolean startZero() {
        if (numberStack.size() == 1 && numberStack.get(0).equals("0")) {
            Log.v("startZero", viewText);
            return true;
        }
        return false;
    }

    public String getInputText() {
        return inputText;
    }

    public String getViewText() {
        return viewText;
    }

    //根据输入类型的不同进行初分类
    public int classification() {
        Log.v("BBB", inputText);
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
            default:
                return 0;
        }
    }

    //逻辑检查
    public void check() {
        String t;

        if (numberStack.isEmpty() && cal.isEmpty()) {
            numberStack.add("0");
        }

        switch (classification()) {
            case 0:
                if (startZero()) {
                    Log.v("Zero", "0000");
                    who = 1;
                    return;
                }
            case 1:
                if (startZero()) {
                    viewText = "";
                    numberStack.pop();
                    Log.v("Zero", "1111");
                }
                who = 1;
                break;
            case 2:
                res = 0;
                trans();
                t = calculate();
                if (t != null) {
                    viewText = "" + res;
                }
                if (who == 1) {
                    operationStack.add("+");
                    who = 0;
                } else {
                    if (!operationStack.isEmpty())
                        operationStack.pop();
                    operationStack.add("+");
                }
                return;
            case 3:
                res = 0;
                trans();
                t = calculate();
                if (t != null) {
                    viewText = "" + res;
                }
                if (who == 1) {
                    operationStack.add("-");
                    who = 0;
                } else {
                    if (!operationStack.isEmpty())
                        operationStack.pop();
                    operationStack.add("-");
                }
                return;
            case 4:
                res = 0;
                trans();
                t = null;
                if (!operationStack.contains("+") && !operationStack.contains("-")) {
                    t = calculate();
                }
                if (t != null) {
                    viewText = "" + res;
                }
                if (who == 1) {
                    operationStack.add("x");
                    who = 0;
                } else {
                    if (!operationStack.isEmpty())
                        operationStack.pop();
                    operationStack.add("x");
                }
                return;
            case 5:
                res = 0;
                trans();
                t = null;
                if (!operationStack.contains("+") && !operationStack.contains("-")) {
                    t = calculate();
                }
                if (t != null) {
                    viewText = "" + res;
                }
                if (who == 1) {
                    operationStack.add("÷");
                    who = 0;
                } else {
                    if (!operationStack.isEmpty())
                        operationStack.pop();
                    operationStack.add("÷");
                }
                return;
            case 6:
                res = 0;
                trans();
                t = calculate();
                if (t != null) {
                    viewText = "" + res;
                }
                who = 0;
                return;
            case 7:
                viewText = "0";
                numberStack.clear();
                operationStack.clear();
                cal.clear();
                numberStack.add("0");
                pointCheck = 0;
                return;
            case 8:
                if (pointCheck != 0 || numberStack.size() >= 9) {
                    return;
                }
                pointCheck = 1;
            case 9:
                res = 0;
                trans();
                operationStack.add("(");
                return;
            case 10:
                res = 0;
                trans();
                operationStack.add(")");
                return;

        }

        if (pointCheck == 1) {
            if (numberStack.size() > 10) {
                return;
            }
        }

        numberStack.add(inputText);
        //最后一位小数点
        if (pointCheck == 0) {
            if (numberStack.size() == 10) {
                numberStack.pop();
                return;
            }
        }

        //更新的内容
        if (pointCheck == 0) {
            setText();
            checkComa();
        } else {
            viewText = viewText + inputText;
        }

        Log.v("LLLLLength", "" + viewText.length());

    }


    //退格
    public void back() {
        int length = viewText.length();
        Log.v("LLLLLength", "" + numberStack.size());

        if (pointCheck == 1) {
            if (numberStack.pop().equals(".")) {
                pointCheck = 0;
            }
            viewText = viewText.substring(0, length - 1);
        } else {
            numberStack.pop();
            setText();
            checkComa();
        }
        if (viewText.equals("")) {
            if (numberStack.isEmpty()) {
                viewText = "0";
                return;
            }
            Log.v("BBBBack", viewText);
        }

        if (operation == 1) {
            operation = 0;
            numberStack.pop();
        }
    }

    //逗号的显示
    public void checkComa() {
        String t = "";
        if (numberStack.size() > 3 && pointCheck == 0) {
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

    public void setText() {
        viewText = "";
        for (int i = 0; i < numberStack.size(); i++) {
            viewText += numberStack.elementAt(i);
        }
    }

    //将输入的数转为基本数据类型
    public void trans() {
        double r;
        String str = "";

        if (numberStack.size() > 0) {
            for (String e : numberStack) {
                str += e;
                Log.v("cal", "" + str);
            }
            numberStack.clear();
            pointCheck = 0;
            r = Double.parseDouble(str);
            cal.add(r);
        }
    }

    public String calculate() {
        String isok = null;
        double num1, num2;
        if (cal.size() >= 2) {
            num1 = cal.peek();
            num2 = cal.peek();

            switch (operationStack.pop()) {
                case "+":
                    res = num1 + num2;
                    cal.add(res);
                    break;
                case "-":
                    res = num2 - num1;
                    cal.add(res);
                    break;
                case "x":
                    res = num1 * num2;
                    cal.add(res);
                    break;
                case "÷":
                    if (num2 == 0) {
                        break;
                    }
                    res = num1 / num2;
                    cal.add(res);

                case "(":
                    res = num1 * num2;
                    cal.add(res);
                    break;
                case ")":
                    res = num1 * num2;
                    cal.add(res);
                    break;
            }
            isok = "ok";
            calculate();
        }
        return isok;
    }
}

