package com.example.calculate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Stack;


/*
    尚未解决的问题：6√4，2(2+1)
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int[] numberIds = new int[]{R.id.zero, R.id.one, R.id.two, R.id.three, R.id.four, R.id.five, R.id.six, R.id.seven, R.id.eight, R.id.nine, R.id.dot};
    private int[] operationIds = new int[]{R.id.plus, R.id.minus, R.id.mul, R.id.div, R.id.mode, R.id.equal, R.id.clear, R.id.backspace, R.id.square, R.id.zuo, R.id.you, R.id.extract, R.id.jie};
    private Button[] numberBtns = new Button[numberIds.length];
    private Button[] operationBtns = new Button[operationIds.length];
    private String str = "";
    private static final String INFINITY = "Infinity";
    private TextView mEditText1;        //显示表达式
    private TextView mEditText2;        //显示计算结果
    private static double op1 = 0, op2 = 0;
    private static ArrayList<String> exp = new ArrayList<>();
    private static Stack<Object> calcStack = new Stack<>();//用于存储逆波兰表达式
    private double result;
    private boolean isOperationFirstClicked = true;     //防止连续输入运算符


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText1 = (TextView) findViewById(R.id.edit1);
        mEditText2 = (TextView) findViewById(R.id.edit2);
        mEditText1.setMovementMethod(ScrollingMovementMethod.getInstance());
        mEditText2.setMovementMethod(ScrollingMovementMethod.getInstance());
        for (int i = 0; i < numberIds.length; i++) {
            numberBtns[i] = (Button) findViewById(numberIds[i]);
            numberBtns[i].setOnClickListener(this);
        }
        for (int i = 0; i < operationIds.length; i++) {
            operationBtns[i] = (Button) findViewById(operationIds[i]);
            operationBtns[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zero:
                str += "0";
                mEditText2.setText(str);
                isOperationFirstClicked = true;         //输入数字后才可以输入运算符
                break;
            case R.id.one:
                str += "1";
                mEditText2.setText(str);
                isOperationFirstClicked = true;
                break;
            case R.id.two:
                str += "2";
                mEditText2.setText(str);
                isOperationFirstClicked = true;
                break;
            case R.id.three:
                str += "3";
                mEditText2.setText(str);
                isOperationFirstClicked = true;
                break;
            case R.id.four:
                str += "4";
                mEditText2.setText(str);
                isOperationFirstClicked = true;
                break;
            case R.id.five:
                str += "5";
                mEditText2.setText(str);
                isOperationFirstClicked = true;
                break;
            case R.id.six:
                str += "6";
                mEditText2.setText(str);
                isOperationFirstClicked = true;
                break;
            case R.id.seven:
                str += "7";
                mEditText2.setText(str);
                isOperationFirstClicked = true;
                break;
            case R.id.eight:
                str += "8";
                mEditText2.setText(str);
                isOperationFirstClicked = true;
                break;
            case R.id.nine:
                str += "9";
                mEditText2.setText(str);
                isOperationFirstClicked = true;
                break;
            case R.id.dot:
                str += ".";
                mEditText2.setText(str);
                break;
            case R.id.zuo:
                str += "(";
                mEditText2.setText(str);
                isOperationFirstClicked = true;
                break;
            case R.id.you:
                str += ")";
                mEditText2.setText(str);
                isOperationFirstClicked = true;
                break;
            case R.id.square:
                if (isOperationFirstClicked) {
                    str += "^";
                    isOperationFirstClicked = false;
                    mEditText2.setText(str);
                } else {
                    Toast.makeText(MainActivity.this, "你的输入有误，请重新输入", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.extract:
                str += "√";
                isOperationFirstClicked = false;
                mEditText2.setText(str);
                break;
            case R.id.clear:
                str = "";
                isOperationFirstClicked = true;
                mEditText2.setText(str);
                mEditText1.setText(str);
                exp.clear();        //清空逆波兰表达式
                calcStack.clear();  //清空栈
                break;
            case R.id.backspace:
                if (str == null || str.equals("") || str.length() - 1 == 0) {
                    str = "";
                } else {
                    str = str.substring(0, str.length() - 1);
                    isOperationFirstClicked = isNum(str.charAt(str.length() - 1));       //如果删除后的最后一位是数字，接着才可以输入运算符
                }
                mEditText2.setText(str);
                break;
            case R.id.jie:
                if (isOperationFirstClicked) {
                    str += "!";
                    mEditText2.setText(str);
                } else {
                    Toast.makeText(MainActivity.this, "你的输入有误，请重新输入", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.mode:
                if (isOperationFirstClicked) {
                    str += "%";
                    isOperationFirstClicked = false;
                    mEditText2.setText(str);
                } else {
                    Toast.makeText(MainActivity.this, "你的输入有误，请重新输入", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.div:
                if (isOperationFirstClicked) {
                    str += "/";
                    isOperationFirstClicked = false;
                    mEditText2.setText(str);
                } else {
                    Toast.makeText(MainActivity.this, "你的输入有误，请重新输入", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.mul:
                if (isOperationFirstClicked) {
                    str += "*";
                    isOperationFirstClicked = false;
                    mEditText2.setText(str);
                } else {
                    Toast.makeText(MainActivity.this, "你的输入有误，请重新输入", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.minus:
                if (isOperationFirstClicked) {
                    str += "-";
                    isOperationFirstClicked = false;
                    mEditText2.setText(str);
                } else {
                    Toast.makeText(MainActivity.this, "你的输入有误，请重新输入", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.plus:
                if (isOperationFirstClicked) {
                    str += "+";
                    isOperationFirstClicked = false;
                    mEditText2.setText(str);
                } else {
                    Toast.makeText(MainActivity.this, "你的输入有误，请重新输入", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.equal:
                try {
                    transfer(str);
                    result = compute();
                    if (String.valueOf(result).equals(INFINITY)) {
                        Toast.makeText(MainActivity.this, "结果太大，脑子算不过来啦", Toast.LENGTH_SHORT).show();
                        isOperationFirstClicked = true;
                        mEditText2.setText("");
                        mEditText1.setText("");
                        str = "";
                        exp.clear();
                    }
                    //如果输入有误的话让str=""，接着就初始化，让用户重新输入
                    else if (str.equals("")) {
                        isOperationFirstClicked = true;
                        mEditText2.setText("");
                        mEditText1.setText("");
                        exp.clear();
                    } else {
                        String s = str + "=";
                        mEditText1.setText(s);                          //显示表达式
                        mEditText2.setText(String.valueOf(result));     //显示结果
                        str = String.valueOf(result);
                        exp.clear();
                    }
                } catch (Exception e) {                                 //如果出现错误则初始化
                    isOperationFirstClicked = true;
                    mEditText2.setText("出错啦");
                    mEditText1.setText("");
                    str = "";
                    exp.clear();
                }
                break;
        }
    }


    //设置运算符的优先级
    public static int operatorPriority(Object o) {
        Character c = (Character) o;
        switch (c) {
            case '+':
                return 1;
            case '-':
                return 1;
            case '*':
                return 2;
            case '/':
                return 2;
            case '%':
                return 2;
            case '^':
                return 4;
            case '√':
                return 4;
            case '!':
                return 3;
            default:
                return 0;
        }
    }

    //判断字符是否是运算符
    public static boolean isOperator(Object o) {
        Character c = (Character) o;
        String s = "+-*/%^!√";
        return s.indexOf(c) != -1;
    }

    public static boolean isOperator(String s) {
        String operator = "+-*/%^!√";
        return operator.contains(s);
    }

    //判断字符是否是阿拉伯数字
    public static boolean isNum(Object o) {
        Character c = (Character) o;
        return (c >= '0' && c <= '9');
    }

    public static boolean isNum(String s) {

        // 通过捕捉NumberFormatException来判断字符串是有效的数字

        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //把中缀表达式转化为后缀表达式
    public static void transfer(String expression) {
        char[] arr = expression.toCharArray();

        for (int i = 0; i < arr.length; i++) {
            if (isNum(arr[i]) || (i == 0 && arr[i] == '-')) {
            /*
             *这部分代码的作用是提取输入的算术表达式（中缀表达式）中的
             *有效数字，包括整数.浮点数和负数。
             */
                int index;
                for (index = i + 1; index < arr.length; index++) {
                    if (isOperator(arr[index]) || arr[index] == '(' || arr[index] == ')')
                        break;
                }
                exp.add(String.valueOf(arr, i, index - i));
                i = index - 1;                                          //减 1 是因为 for 循环里 i 还会自增 1
            } else if (isOperator(arr[i])) {
                if (calcStack.empty()) {                             //如果栈为空这运算符直接进栈
                    calcStack.push(arr[i]);
                    Log.d("calcStack",calcStack.peek().toString());
                }
                else {
                    if (operatorPriority(arr[i]) >= operatorPriority(calcStack.peek()))     //优先级大的进栈
                        calcStack.push(arr[i]);
                    else {
                        for (int j = 1; j <= calcStack.size(); j++)     //优先级小的先把栈顶的运算符出栈添加到后缀表达式中，然后再进栈
                            exp.add(calcStack.pop().toString());
                        calcStack.push(arr[i]);
                    }
                }
            } else if (arr[i] == '(') {                                   //左括号直接进栈
                calcStack.push(arr[i]);
                if (arr[i + 1] == '-') {
                    int index;
                    for (index = i + 2; index < arr.length; index++) {
                        if (isOperator(arr[index]) || arr[index] == ')')   //提取负号后面的有效数字
                            break;
                    }
                    exp.add(String.valueOf(arr, i + 1, index - (i + 1))); //i-1 是因为不包括左括号
                    i = index - 1;                                        //减 1 是因为 for 循环里 i 还会自增 1
                }
            } else if (arr[i] == ')') {                                   //如果是右括号则把栈顶的运算符出栈，直到遇到右括号停止
                int len = calcStack.size();
                for (int k = 1; k <= len; k++) {
                    if (calcStack.peek().toString().equals("(")) {
                        calcStack.pop();
                        break;
                    }
                    exp.add(calcStack.pop().toString());
                }
            }
        }
        if (!calcStack.empty()) {                                          //如果最后栈不为空则依次出栈添加到后缀表达式中 
            int len = calcStack.size();
            for (int i = 0; i < len; i++)
                exp.add(calcStack.pop().toString());
        }
    }

    public double compute() {
        double temp = 0;
        Arith arith = new Arith();
        //如果表达式为空则返回0，否则多次按“=”会崩溃
        if (exp.isEmpty()) {
            return 0;
        }
        for (String atom : exp) {                       //遍历后缀表达式
            if (isNum(atom))
                calcStack.push(atom);                   //如果是数字则直接进栈
            else if (isOperator(atom)) {
                op1 = Double.parseDouble(calcStack.pop().toString());
                if (calcStack.isEmpty()) {              //如果出栈一个数后，栈为空（比如输入3！时），则把op2设为0，否则会抛出空栈异常
                    op2 = 0;
                } else {
                    op2 = Double.parseDouble(calcStack.pop().toString());
                }

                switch (atom) {
                    case "+":
                        temp = arith.add(op1, op2);
                        break;
                    case "-":
                        temp = arith.sub(op2, op1);
                        break;
                    case "*":
                        temp = arith.mul(op1, op2);
                        break;
                    case "/":
                        if (op1 == 0) {
                            Toast.makeText(MainActivity.this, "除数不能为0，请重新输入", Toast.LENGTH_SHORT).show();
                            str = "";
                        } else {
                            temp = op2 / op1;
                        }
                        break;
                    case "%":
                        temp = op2 % op1;
                        break;
                    case "^":
                        temp = Math.pow(op2, op1);
                        break;
                    case "!":
                        //判断是否是小数，如果是小数则不能求阶
                        boolean isInteger = op1 % 1 == 0;
                        if (op1 <= 0) {
                            Toast.makeText(MainActivity.this, "非正整数无法求阶乘,请重新输入", Toast.LENGTH_SHORT).show();
                            str = "";
                            break;
                        } else if (!isInteger) {
                            Toast.makeText(MainActivity.this, "非正整数无法求阶乘，请重新输入", Toast.LENGTH_SHORT).show();
                            str = "";
                            break;
                        } else if (op1 >= 170) {
                            Toast.makeText(MainActivity.this, "要求运算的结果太大，脑子算不过来啦，请重新输入", Toast.LENGTH_SHORT).show();
                            str = "";
                            break;
                        } else if (op2 == 0) {
                            double x = 1;
                            double res = 1;
                            for (int i = 0; i < op1; i++) {
                                res *= x;
                                x++;
                            }
                            temp = res;
                            break;
                        } else {          //如果op2不为空的话则需要先把op2进栈再把运算结果进栈
                            double x = 1;
                            double res = 1;
                            for (int i = 0; i < op1; i++) {
                                res *= x;
                                x++;
                            }
                            temp = res;
                            calcStack.push(op2);
                            break;
                        }
                    case "√":
                        if (op1 < 0) {
                            Toast.makeText(MainActivity.this, "负数无法求算数平方根,请重新输入", Toast.LENGTH_SHORT).show();
                            str = "";
                            break;
                        } else {
                            if (op2 == 0) {
                                temp = Math.sqrt(op1);
                            } else {
                                temp = Math.sqrt(op1);
                                calcStack.push(op2);
                            }
                            break;
                        }

                }
                calcStack.push(temp);
            }
        }

        return Double.parseDouble(calcStack.pop().toString());
    }

    //解决double计算溢出
    class Arith {

        private Arith() {
        }

        public double add(double d1, double d2) {
            BigDecimal b1 = new BigDecimal(Double.toString(d1));
            BigDecimal b2 = new BigDecimal(Double.toString(d2));
            return b1.add(b2).doubleValue();
        }

        public double sub(double d1, double d2) {
            BigDecimal b1 = new BigDecimal(Double.toString(d1));
            BigDecimal b2 = new BigDecimal(Double.toString(d2));
            return b1.subtract(b2).doubleValue();
        }

        public double mul(double d1, double d2) {
            BigDecimal b1 = new BigDecimal(Double.toString(d1));
            BigDecimal b2 = new BigDecimal(Double.toString(d2));
            return b1.multiply(b2).doubleValue();
        }

    }

}